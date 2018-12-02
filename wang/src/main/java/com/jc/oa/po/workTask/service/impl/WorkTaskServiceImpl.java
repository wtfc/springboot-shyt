package com.jc.oa.po.workTask.service.impl;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.common.domain.Remind;
import com.jc.oa.common.service.IRemindService;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.anno.service.IAnnoService;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.diary.service.IDiaryService;
import com.jc.oa.po.workTask.dao.IWorkTaskDao;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;
import com.jc.oa.po.workTask.domain.WorkTaskRelevance;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;
import com.jc.oa.po.workTask.service.IWorkTaskHistoryService;
import com.jc.oa.po.workTask.service.IWorkTaskRelevanceService;
import com.jc.oa.po.workTask.service.IWorkTaskService;
import com.jc.oa.po.workTask.service.IWorkTaskfinishService;
import com.jc.system.CustomException;
import com.jc.system.DBException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;
import com.jc.system.notice.NoticeMsgUtil;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */
@Service
public class WorkTaskServiceImpl extends BaseServiceImpl<WorkTask> implements IWorkTaskService{
	
	private IWorkTaskDao worktaskDao;
	    
	@Autowired
	private IWorkTaskHistoryService workTaskHistoryService;
	
	@Autowired
	private IUserService userService;
	
	
	@Autowired
	private IDiaryService diaryService;
	
	@Autowired
	private IWorkTaskfinishService workTaskfinishService;
	
	@Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IInteractFacadeService interactFacadeService;
	
	@Autowired
	private IRemindService remindService;
	
	@Autowired
	private IAnnoService annoService;
	
	@Autowired
	private IUploadService uploadService;
	
	@Autowired
	private IWorkTaskRelevanceService workTaskRelevanceService;
	
	public WorkTaskServiceImpl(){}
	
	@Autowired
	public WorkTaskServiceImpl(IWorkTaskDao worktaskDao){
		super(worktaskDao);
		this.worktaskDao = worktaskDao;
	}
	/**
	 * @description 逻辑删除
	 * @param WorkTask  workTask 实体类
	 * @return Integer 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-04-30
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateDeleteFlagByIds(WorkTask workTask) throws PoException {
		Integer returnVal=0;
		WorkTask work=null;
		if (null ==workTask || null==workTask.getPrimaryKeys()) {
			return returnVal;
		}
		try{
			User userInfo = SystemSecurityUtils.getUser();
			String[] primaryKeys=workTask.getPrimaryKeys();
			if (null !=primaryKeys && primaryKeys.length>0) {
				for (int i = 0; i < primaryKeys.length; i++) {
					String primaryKey=primaryKeys[i];
					workTask=new WorkTask();
					workTask.setId(new Long(primaryKey));
					workTask.setDeleteFlag(0);
					work=worktaskDao.get(workTask);
					if (null!=work) {
						workTask.setTaskName(work.getTaskName()==null?"":work.getTaskName());
						workTask.setContent(work.getContent()==null?"":work.getContent());
						workTask.setModifyDate(new Date());
						workTask.setModifyUser(userInfo.getId());
						if (addWorkTaskHis(workTask,"2")==1) {//添加任务事件:删除
							if(worktaskDao.delete(workTask)==1){//逻辑删除任务：成功
								handleTaskRelevance(workTask,"4");
								returnVal=1;
								Anno anno = new Anno();
								propertyService.fillProperties(anno,false); 
								anno.setBusinessId(workTask.getId());
								anno.setAnnoType(Constants.ANNOTYPE_WORKTASK+"");
								annoService.deleteByBusinessId(anno);//删除批注
								cancelTaskRemind(work);//取消任务中汇报提醒
							}
						}
					}
				}
			}
		} catch (Exception e) {
			PoException po = new PoException(e);
			po.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
			throw po;
		}
		return returnVal;
	}
	/**
	 * @description 任务统计(分页查询)
	 * @param WorkTask  workTask 实体类，PageManager page
	 * @return PageManager 
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-05-04
	 */
	public PageManager queryTaskTatal(WorkTask workTask, PageManager page)throws PoException {
		if (null!=workTask && null!=workTask.getEndTime()) {
			workTask.setEndTime(DateUtils.fillTime(workTask.getEndTime()));
		}
		PageManager pageTemp=worktaskDao.queryByPage(workTask, page,
				Constants.QUERY_TASK_TOT_COUNT, Constants.QUERY_TASK_TOTAL);
		List newList=new ArrayList();
		List workTaskList=pageTemp.getData();
		WorkTaskHistory workTaskHistory=null;
		WorkTaskHistory taskHis=null;
		WorkTaskfinish workTaskfinish=null;
		WorkTaskfinish finishWorkTask=null;
		Integer workTaskHistToal=0;
		List<WorkTaskHistory>  taskHistory=null;
		List<WorkTaskfinish>   finishWork=null;
		String taskIds="";
		String finishTaskIds="";
		if(null !=workTaskList && workTaskList.size()>0){
			for (int i = 0; i < workTaskList.size(); i++) {
				WorkTask workT=(WorkTask)workTaskList.get(i);
				if(null !=workT){
					workTaskHistory=new WorkTaskHistory();
					workTaskfinish=new WorkTaskfinish();
					workTaskHistory.setDirectorId(workT.getDirectorId());
					workTaskHistory.setStartTime(workTask.getStartTime());
					workTaskHistory.setEndTime(workTask.getEndTime());
					workTaskHistToal=workTaskHistoryService.getWorkTaskHisTotal(workTaskHistory);
					if (null==workTaskHistToal) {
						workTaskHistToal=0;
					}
					workT.setRemindersTask(String.valueOf(workTaskHistToal));
					taskHistory=workTaskHistoryService.getWorkTaskHisTaskId(workTaskHistory);
					if (null !=taskHistory && taskHistory.size()>0) {
						for (int j = 0,k=taskHistory.size(); j <k; j++) {
							taskHis=taskHistory.get(j);
							if (j==taskHistory.size()-1) {
								taskIds+=taskHis.getTaskId();
							}else {
								taskIds+=taskHis.getTaskId()+",";
							}
						}
					}
					workT.setTaskIds(taskIds.split(","));//设置多个催办任务ID
					workTaskfinish.setDirectorId(workT.getDirectorId());
					workTaskfinish.setStartTime(workTask.getStartTime());
					workTaskfinish.setEndTime(workTask.getEndTime());
					finishWork=workTaskfinishService.getFinWorkTaskTaskId(workTaskfinish);
					finishTaskIds="";
					if (null !=finishWork && finishWork.size()>0) {
						for (int m = 0,n=finishWork.size(); m < n; m++) {
							finishWorkTask=finishWork.get(m);
							if (m==finishWork.size()-1) {
								finishTaskIds+=finishWorkTask.getId();
							}else {
								finishTaskIds+=finishWorkTask.getId()+",";
							}
						}
						workT.setFinTaskIds(finishTaskIds.split(","));//设置多个完成任务ID
					}
					newList.add(workT);
					pageTemp.setData(newList);
				}
			}
		}
		return pageTemp;
	}
	/**
	 * @description 添加任务事件
	 * @param WorkTask  workTask 实体类，PageManager page
	 * @return PageManager 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-07
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer addWorkTaskHis(WorkTask workTask,String eventType) throws PoException{
		Integer returnVal=0;
		WorkTaskHistory workTaskHistory=null;
		if(null !=workTask && null!=workTask.getId() && null !=eventType && !"".equals(eventType)){
			String[] primaryKeys=String.valueOf(workTask.getId()).split(",");
			workTask.setPrimaryKeys(primaryKeys);
			workTaskHistory=new WorkTaskHistory();
			workTaskHistory.setTaskId(workTask.getId());
			workTaskHistory.setTaskEventType(eventType);
			workTaskHistory.setTaskEventTitle(workTask.getTaskName()==null?"":workTask.getTaskName());
			workTaskHistory.setContent(workTask.getContent()==null?"":workTask.getContent());
			workTaskHistory.setDeleteFlag(0);
			try {
				returnVal=workTaskHistoryService.save(workTaskHistory);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
				throw po;
			}
		}
		return returnVal;
	}
	/**
	 * @description 查询人员名称
	 * @param HttpServletRequest request
	 * @return String 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-12
	 */ 
	public String getNames(String queryType,String uIds) throws PoException{
		String retuString="";
		if (null !=queryType && !"".equals(queryType) && null !=uIds && !"".equals(uIds)) {
			User user=new User();
			if ("single".equals(queryType)) {//查询单个
				user.setId(new Long(uIds));
				try {
					User userTemp=userService.get(user);
					if (null!=userTemp) {
						retuString+=userTemp.getDisplayName()==null?"":userTemp.getDisplayName();
					}		
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
					throw po;
				}
			}else {//查询多个 multiple
				List<User> userList=userService.queryUserByIds(uIds);
				if (null!=userList && userList.size()>0) {
					for (int i = 0,j=userList.size(); i < j; i++) {
						User userTemp=userList.get(i);
						if (null!=userTemp) {
							if (i== userList.size()-1) {
								retuString+=userTemp.getDisplayName()==null?"":userTemp.getDisplayName();
							}else {
								retuString+=userTemp.getDisplayName()==null?"":userTemp.getDisplayName()+",";
							}
						}	
					}
				}
			}
		}
		return retuString;
	}
	/**
	 * @description 查询所有任务(对外)
	 * @param WorkTask  workTask 实体类
	 * @return List<WorkTask> 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-12
	 */
	public List<WorkTask> queryWorkTaskList(WorkTask workTask) throws PoException{
		if (null==workTask || null==workTask.getDirectorId() 
				|| null==workTask.getStartDate() || "".equals(workTask.getStartDate())) {
			return null;
		}
		Long directorId=workTask.getDirectorId();
		String startDate=workTask.getStartDate();
		WorkTask workTaskTempTask=new WorkTask();
		workTaskTempTask.setDirectorId(directorId);
		if (null !=startDate && !"".equals(startDate)) {
			startDate+=" 00:00:00";
		}
		workTaskTempTask.setStartDate(startDate);
		List<WorkTask> list=worktaskDao.queryWorkTaskList(workTaskTempTask);
		String prticipants="";
		List taskList=null;
		if(null!=list && list.size()>0){
			taskList=new ArrayList();
			for (int i = 0,j=list.size(); i <j; i++) {
				WorkTask task=list.get(i); 
				try {
					prticipants=getNames("multiple",task.getPrticipantsId());
				} catch (PoException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
					throw po;
				}
				task.setPrticipants(prticipants);
				taskList.add(task);
			}
		}
		return taskList;
	}
	/**
	 * @description 将分页信息重新封装
	 * @param HttpServletRequest request
	 * @return String 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-12
	 */ 
	public PageManager converterWorkTask(PageManager pageManager) throws PoException{
		if(null!=pageManager){
			List list=pageManager.getData();
			List workTaskList=null;
			WorkTask  workTask=null;
			if(null !=list && list.size()>0){
				workTaskList=new ArrayList();
				for (int i = 0,j=list.size(); i <j; i++) {
					workTask=(WorkTask)list.get(i);
					String prticipants="";
					try {
						prticipants = getNames("multiple",workTask.getPrticipantsId());
					} catch (PoException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
						throw po;
					}
					workTask.setPrticipants(prticipants);
					workTaskList.add(workTask);
					pageManager.setData(workTaskList);
				}
			}
		}
		return pageManager;
	}
	/**
	 * @description 将传入的信息重新封装
	 * @param HttpServletRequest request
	 * @return String 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-12
	 */ 
	public WorkTask converterWorkTask(WorkTask workTask) throws PoException{
		if (null!=workTask) {
			//设置父任务名称
			workTask.setParentTaskName(workTask.getParentTaskName()==null?"无":workTask.getParentTaskName());
			//设置父任务重要程度名称
			String parentTaskImpName="无";
			if (null!= workTask.getParentTaskImpCode()) {
				
				if ("2".equals(workTask.getParentTaskImpCode())) {//2-不重要不紧急
					parentTaskImpName="不重要不紧急";
				}
				if ("4".equals(workTask.getParentTaskImpCode())) {//4-不重要紧急
					parentTaskImpName="不重要紧急";				
				}
				if ("6".equals(workTask.getParentTaskImpCode())) {//6-重要不紧急
					parentTaskImpName="重要不紧急";
				}
				if ("8".equals(workTask.getParentTaskImpCode())) {//8-重要紧急
					parentTaskImpName="重要紧急";
				}
			}
			workTask.setParentTaskImpName(parentTaskImpName);
			//设置任务重要程度名称
			String taskImpName="无";
			if (null!= workTask.getTaskImpCode()) {
				
				if ("2".equals(workTask.getTaskImpCode())) {//2-不重要不紧急
					taskImpName="不重要不紧急";
				}
				if ("4".equals(workTask.getTaskImpCode())) {//4-不重要紧急
					taskImpName="不重要紧急";				
				}
				if ("6".equals(workTask.getTaskImpCode())) {//6-重要不紧急
					taskImpName="重要不紧急";
				}
				if ("8".equals(workTask.getTaskImpCode())) {//8-重要紧急
					taskImpName="重要紧急";
				}
			}
			workTask.setTaskImpName(taskImpName);
			//查询参与人
			String prticipants="";
			try {
				prticipants = getNames("multiple", workTask.getPrticipantsId());
			} catch (PoException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
				throw po;
			}
			workTask.setPrticipants("".equals(prticipants)?null:prticipants);
			if (null==workTask.getPrticipantsId() || "".equals(workTask.getPrticipantsId())) {
				workTask.setPrticipantsId(null);
			}
			//将参与人组装成JSON
			String prtIdValue="";
			String prtObj="[";
			if (null!=workTask.getPrticipantsId() && null!=workTask.getPrticipants() 
					&& !"".equals(workTask.getPrticipantsId()) && !"".equals(workTask.getPrticipants() )) {
				String[] prtId=workTask.getPrticipantsId().split(",");
				String[] prtName=workTask.getPrticipants().split(",");
				if (null!=prtId && null!=prtName && prtId.length>0 && prtName.length>0 &&  prtId.length==prtName.length) {
					for (int i = 0; i < prtId.length; i++) {
						if(i== prtId.length-1){
							prtIdValue+="{id:"+prtId[i]+",text:\""+prtName[i]+"\"}";
						}else {
							prtIdValue+="{id:"+prtId[i]+",text:\""+prtName[i]+"\"},";
						}
					}
				}
				prtObj+=prtIdValue+"]";
				workTask.setPrtiForUpdate(prtObj);
			}
			//任务来源
			String taskOriName="无";
			if(null !=workTask.getTaskOrigin() && !"".equals(workTask.getTaskOrigin())){
				if ("1".equals(workTask.getTaskOrigin().trim())) {
					taskOriName="工作计划";
				}else if("2".equals(workTask.getTaskOrigin().trim())){
					taskOriName="会议纪要";
				}else {//0
					taskOriName="新建表单";
				}
			}
			workTask.setTaskOriName(taskOriName);
			if (null==workTask.getTaskWorkType() || "".equals(workTask.getTaskWorkType())) {
				workTask.setTaskTypeName("无");
			}
			//查询提醒设置
//			Remind remind=new Remind();
//			remind.setDataId(workTask.getId());
//			remind.setTableName(Constants.TABLE_NAME);
//			String remindtemp=remindService.getRemindJson(remind);
//			workTask.setRemind("".equals(remindtemp)?null:remindtemp);
		}
		return workTask;
	}
	/**
	 * @description 查询所有任务(对外，分页)
	 * @param WorkTask  workTask 实体类, PageManager page
	 * @return PageManager 
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-05-13
	 */
	public PageManager queryWorkTaskPage(WorkTask  workTask, PageManager page) {
		return worktaskDao.queryByPage(workTask, page, 
				Constants.QUERY_COUNT_OUT, Constants.QUERY_WORK_TASK_LIST_OUT);
	}
	/**
	 * @description 保存
	 * @param WorkTask task
	 * @return Integer 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-15
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(WorkTask task) throws PoException{
		Integer reVal=0;
		Integer taskRele=1;//1:布置；3：下发;
		if (null==task) {
			return reVal;
		}
		//用于处理提取状态为暂存的任务 start
		if(null!=task.getTaskId() && null!=task.getStatusArr()
				&& !"".equals(task.getTaskId()) && "8".equals(task.getStatusArr())){
			//查询暂存任务
			WorkTask taskTemp=new WorkTask();
			taskTemp.setId(task.getTaskId());
			taskTemp.setDeleteFlag(0);
			taskTemp=getTask(taskTemp);
			if(null!=taskTemp && "8".equals(taskTemp.getStatus())){
				String[] primaryKeys={task.getTaskId()+""};
				WorkTask workTaskTemp=new WorkTask();
				workTaskTemp.setPrimaryKeys(primaryKeys);
				//删除原暂存任务
				reVal=updateDeleteFlagByIds(workTaskTemp);
				if (reVal==0) {
					return reVal;
				}
				if (null!=taskTemp.getTaskImpCode()) {
					task.setTaskImpCode(taskTemp.getTaskImpCode());
				}
				if(null!=taskTemp.getParentTaskid() && "-1".equals(taskTemp.getParentTaskid())){
					task.setParentTaskid(null);
				}else {
					task.setParentTaskid(taskTemp.getParentTaskid());
				}
			}else {
				return reVal;
			}
		}
		//用于处理提取状态为暂存的任务 end
		if ("5".equals(task.getStatus())) {//保存模板
			taskRele=-1;//保存模板不进行任务关联
		}
		//对任务进行唯一查询
		reVal=querySomeTask(task);
		if (null!=reVal && reVal.intValue()==1) {
			reVal=0;
			return reVal;
		}
		try {
			if (null==task.getParentTaskid()) {//不存在父任务
				task.setParentTaskid(new Long(-1));
			}else {//存在父任务
				if(!"8".equals(task.getStatus())){
					WorkTask wt=new WorkTask();
					wt.setId(task.getParentTaskid());
					WorkTask wTask=worktaskDao.get(wt);
					if (null!=wTask && wTask.getNormalToCount()==0) {
						propertyService.fillProperties(wTask,false);
						wTask.setTaskProc(0);//首次下发子任务时，原进度设置为0
						reVal=worktaskDao.update(wTask);
					}
				}
				taskRele=3;
			}
			if(null!=task.getEndTime()){
				task.setEndTime(DateUtils.fillTime(task.getEndTime()));
			}
			task.setTaskProc(0);
			task.setDeleteFlag(0);
			task.setIsTemplet(0);
			propertyService.fillProperties(task,false);
			//判断当前任务是否已超期(保存模板及暂存不判断)
			if (!"5".equals(task.getStatus()) && !"8".equals(task.getStatus()) 
					&& DateUtils.subtractionMinute(DateUtils.fillTime(task.getEndTime()), new Date()) >0) {
				task.setStatus("6");//超期
			}
			//汇报提醒 用于最新需求调整 李洪宇 2014-09-28 start
			if (null!=task.getReportDay() && !"5".equals(task.getStatus()) && !"8".equals(task.getStatus())) {
				if(task.getReportDay().intValue()>0 ){
					long sponsorId=task.getSponsorId();//发起人
					long directorId=task.getDirectorId();//负责人
					String sponsorName="";//发起人 名称
					String directorName="";//负责人 名称
					User sponUser=userService.getUser(sponsorId);
					sponsorName=sponUser.getDisplayName();
					User direUser=userService.getUser(directorId);
					directorName=direUser.getDisplayName();
					String remindTime=DateUtils.formatDate(DateUtils.addOrSubtractDaysReturnDate(
									task.getEndTime(), 0-(task.getReportDay().intValue())), "yyyy-MM-dd");
					remindTime+="  09:00:00";//早上9：00提醒
					StringBuffer remindInfo=new StringBuffer();
					remindInfo.append("{");
					//针对内容中可能出的换行情况，特此将换行符去掉
					remindInfo.append("\"content\":\"").append(task.getContent()==null?"":task.getContent().replaceAll("\r", "").replaceAll("\n", "")).append("\",");
					remindInfo.append("\"remindMode\":2,");//内部邮件
					if (task.getSponsorId().intValue()==task.getDirectorId().intValue()) {
						remindInfo.append("\"receiveId\":\"[{\\\"id\\\":\\\"")
						.append(sponsorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
						.append(sponsorName).append("\\\"}]\",");
					}else {
						remindInfo.append("\"receiveId\":\"[{\\\"id\\\":\\\"")
						.append(sponsorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
						.append(sponsorName).append("\\\"},");
						remindInfo.append("{\\\"id\\\":\\\"").append(directorId)
						.append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
						.append(directorName).append("\\\"}]\",");
					}
					remindInfo.append("\"remindType\":4,");
					remindInfo.append("\"cycle\":1,");
					remindInfo.append("\"isActive\":\"1\",");
					remindInfo.append("\"tableName\":\"1\",");
					remindInfo.append("\"viewCycle\":\"\",");
					remindInfo.append("\"viewStartTime\":\"").append(remindTime+"\",");
					remindInfo.append("\"viewEndTime\":\"\",");
					remindInfo.append("\"startForwardTime\":0,");
					remindInfo.append("\"endForwardTime\":0,");
					remindInfo.append("\"endForwardTimeValue\":0,");
					remindInfo.append("\"startForwardTimeValue\":0,");
					remindInfo.append("\"title\":\"").append(task.getTaskName()).append("\",");
					remindInfo.append("\"intervalHour\":0,");
					remindInfo.append("\"intervalMinute\":0,");
					remindInfo.append("\"remindNum\":0,");
					remindInfo.append("\"cronExpression\":\"\",");
					remindInfo.append("\"remindInterval\":0,");
					remindInfo.append("\"viewStartTimeStr\":\"").append(remindTime+"\",");
					remindInfo.append("\"viewEndTimeStr\":\"\"");
					remindInfo.append("}");
					task.setRemind(remindInfo.toString());
					task.setReportTime(DateUtils.parseDate(remindTime));
					if(null!=task.getReportType() && "1".equals(task.getReportType())){//短信提醒
						remindInfo=new StringBuffer();
						remindInfo.append("{");
						//针对内容中可能出的换行情况，特此将换行符去掉
						remindInfo.append("\"content\":\"").append(task.getContent()==null?"":task.getContent().replaceAll("\r", "").replaceAll("\n", "")).append("\",");
						remindInfo.append("\"remindMode\":1,");//短信
						if (task.getSponsorId().intValue()==task.getDirectorId().intValue()) {
							remindInfo.append("\"receiveId\":\"[{\\\"id\\\":\\\"")
							.append(sponsorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
							.append(sponsorName).append("\\\"}]\",");
						}else {
							remindInfo.append("\"receiveId\":\"[{\\\"id\\\":\\\"")
							.append(sponsorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
							.append(sponsorName).append("\\\"},");
							remindInfo.append("{\\\"id\\\":\\\"")
							.append(directorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
							.append(directorName).append("\\\"}]\",");
						}
						remindInfo.append("\"remindType\":4,");
						remindInfo.append("\"cycle\":1,");
						remindInfo.append("\"isActive\":\"1\",");
						remindInfo.append("\"tableName\":\"1\",");
						remindInfo.append("\"viewCycle\":\"\",");
						remindInfo.append("\"viewStartTime\":\"").append(remindTime+"\",");
						remindInfo.append("\"viewEndTime\":\"\",");
						remindInfo.append("\"startForwardTime\":0,");
						remindInfo.append("\"endForwardTime\":0,");
						remindInfo.append("\"endForwardTimeValue\":0,");
						remindInfo.append("\"startForwardTimeValue\":0,");
						remindInfo.append("\"title\":\"").append(task.getTaskName()).append("\",");
						remindInfo.append("\"intervalHour\":0,");
						remindInfo.append("\"intervalMinute\":0,");
						remindInfo.append("\"remindNum\":0,");
						remindInfo.append("\"cronExpression\":\"\",");
						remindInfo.append("\"remindInterval\":0,");
						remindInfo.append("\"viewStartTimeStr\":\"").append(remindTime+"\",");
						remindInfo.append("\"viewEndTimeStr\":\"\"");
						remindInfo.append("}");
						task.setRemindTemp(remindInfo.toString());
					}
				}
			}
			if(null==task.getReportDay() || task.getReportDay().intValue()==0){
				task.setReportType("0");
			}
			//汇报提醒 用于最新需求调整 李洪宇 2014-09-28 end
			
			reVal=worktaskDao.save(task);//保存方法
			
			//添加消息提醒   add by xuweiping start
			if ("0".equals(task.getStatus())) {//保存任务时添加消息提醒
				NoticeMsg noticeMsg = new NoticeMsg();
				noticeMsg.setSendUser(task.getSponsorId());//消息发送人
				noticeMsg.setReceiveUser(task.getDirectorId());//消息接收人
				noticeMsg.setNoticeType(Constants.PO_WORKTASK_NOTICETYPE);//消息提醒类型
				noticeMsg.setTitle("您有一个新的任务待接收，请查看");//消息标题
				noticeMsg.setContent("您有一个新的任务待接收，请查看");//消息内容
				
				//modify by lihongyu at 2015-3-17 start 用于右下方消息提醒跳转
//				noticeMsg.setUrl("/po/workTask/queryTask.action?fromPortal=yes");
				noticeMsg.setUrl("/po/workTask/queryTask.action?firstQuery=yes&fromPortal=yes");
				noticeMsg.setExtStr1("/po/workTask/queryTask.action?firstQuery=yes&fromPortal=yes");
				//modify by lihongyu at 2015-3-17 end 用于右下方消息提醒跳转
				
				noticeMsg.setShowFlag(0);//弹出标识
				noticeMsg.setReadFlag(0);//已读标识
				noticeMsg.setBusinessId(task.getId());//业务ID
				noticeMsg.setBusinessFlag("tty_po_task");//业务标识
				NoticeMsgUtil.notice(noticeMsg);
			}
			//添加消息提醒   add by xuweiping end
			
			//任务关联关系处理
			reVal=handleTaskRelevance(task,taskRele+"");
			if(!"5".equals(task.getStatus())){//保存模板或暂存不添加事件
				if ("8".equals(task.getStatus())) {
					if (addWorkTaskHis(task, "15")==1) {//向任务事件表中添加信息:暂存
						reVal=1;
					}
				}else {
					if (addWorkTaskHis(task, "0")==1) {//向任务事件表中添加信息:布置
						reVal=1;
					}
				}
			}else {
				reVal=1;
			}
			//删除页面中上传中删除的附件
			if(null!=task.getDelattachIds() && !"null".equals(task.getDelattachIds()) 
					&& !StringUtil.isEmpty(task.getDelattachIds())){
				uploadService.deleteFileByIds(task.getDelattachIds());
			}
			//附件转换
			StringBuffer fileids=new StringBuffer();
			if (null!=task.getFileids() && task.getFileids().size()>0) {
				Iterator iterator=task.getFileids().iterator();
				while (iterator.hasNext()) {
					fileids.append(iterator.next()).append(",");
				}
				task.setFileid(fileids.toString().split(","));
			}
			//保存附件
			AttachBusiness attachBusiness =null;
			String fileIds[] = task.getFileid();
			if(fileIds != null && fileIds.length >0 && !"".equals(fileIds[0])){
				for (int i = 0; i < fileIds.length; i++) {
					attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(new Long(fileIds[i]));
					attachBusiness.setBusinessId(task.getId());
					attachBusiness.setBusinessTable(Constants.TABLE_NAME);
					attachBusiness.setBusinessSource("0");
					try {
						reVal=attachBusinessService.save(attachBusiness);
					} catch (CustomException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_046"));
						throw po;
					}
				}
			}
			//汇报提醒设置(邮件提醒：默认)
			if (null!=task.getRemind() && !"".equals(task.getRemind())
					&& !"5".equals(task.getStatus()) && !"8".equals(task.getStatus())) {
				try {
					reVal=remindService.save(task.getRemind(), task.getId(),Constants.TABLE_NAME);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
			//汇报提醒设置(短信提醒)
			if(null!=task.getRemindTemp() && null !=task.getReportType() 
					&& !"".equals(task.getRemindTemp()) 
					&& !"5".equals(task.getStatus()) && !"8".equals(task.getStatus()) && "1".equals(task.getReportType())){
				try {
					reVal=remindService.save(task.getRemindTemp(), task.getId(),Constants.TABLE_NAME_TEMP);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
			//日程
			if (null !=task.getIsImportDiary() && null!=task.getStatus() 
					&& "1".equals(task.getIsImportDiary()) &&  !"5".equals(task.getStatus()) && !"8".equals(task.getStatus())) {
				Diary diary=new Diary();
				diary.setPossessorId(task.getDirectorId());/*日程所有人ID*/
				diary.setTitle(task.getTaskName());/*日程标题*/
				diary.setContent(task.getContent());/*主要完成事项*/
				diary.setStarttime(task.getStartTime());/*日程开始时间*/
				diary.setEndtime(task.getEndTime());/*日程结束时间*/
				diary.setPeriodType(Constants.PO_PERIODWAY_NONE);/*周期类型(0-无定期)*/
				diary.setModuleFlag(Constants.PO_DIARY_MODULEFLAG_TASK);/*模块来源标记[3-待办任务导入]*/
				diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);/*日程类型[0-工作日程]*/
				diary.setIsShare(Constants.PO_DIARY_ISSHARE);/*是否被共享(0-否)*/
				diary.setPeriodWay("0");/*周期时间节点，辅助记录用*/
				diary.setBusinessId(task.getId());/*设置主键ID*/
				diary.setMillisecond(new Date().getTime());//创建时间毫秒数
				try {
					reVal=diaryService.save(diary);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
					throw po;
				}
			}
		} catch (DBException e) {
			PoException po=new PoException(e);
			po.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
			throw po;
		}
		return reVal;
	}
	/**
	 * @description 将传入的信息重新封装
	 * @param WorkTask workTask
	 * @return List<WorkTask> 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-16
	 */ 
	public List<WorkTask> converterTaskList(WorkTask workTask) throws PoException{
		List<WorkTask> list=null;
		List<WorkTask> listTemp=null;
		String prticipants="";
		if (null !=workTask) {
				list =worktaskDao.queryAllForUnion(workTask);
			if (null !=list && list.size()>0) {
				 listTemp=new ArrayList();
				for (int i = 0,j=list.size(); i <j; i++) {
					WorkTask work=list.get(i);
					try {
						prticipants = getNames("multiple",work.getPrticipantsId()==null?"":work.getPrticipantsId());
					} catch (PoException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
						throw po;
					}
					work.setPrticipants(prticipants);
					listTemp.add(work);
				}
			}
		}
		return listTemp;
	}
	/**
	 * @description 任务查询(分页--数据权限)
	 * @param WorkTask  workTask 实体类
	 * @param PageManager page
	 * @return PageManager
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-05-20
	 */
	public PageManager queryByPage(WorkTask task, PageManager page,String countSQL,String querySQL)throws PoException {
		String taskNameTemp="";
		if(null==countSQL || "".equals(countSQL)){
			countSQL=Constants.QUERY_COUNT_TASK;
		}
		if(null==querySQL || "".equals(querySQL)){
			querySQL=Constants.QUERY_TASK;
		}
		if (null!=task.getEndTime()) {//截止日期手动添加时分秒
			task.setEndTime(DateUtils.fillTime(task.getEndTime()));
		}
		task.setDeleteFlag(0);
		PageManager pageManager=worktaskDao.queryByPage(task, page,countSQL, querySQL);
		List workTaskList=pageManager.getData();
		List workList=null;
		if (null!=workTaskList && workTaskList.size()>0) {
			workList=new ArrayList();
			//对任务进度进行算法计算 start
			for (int i = 0,k=workTaskList.size(); i < k; i++) {
				WorkTask workTask=(WorkTask)workTaskList.get(i);
				try {
					Long parentTaskid=workTask.getParentTaskid();
					workTask.setTaskProc(queryTaskProc(workTask));
					workTask.setParentTaskid(parentTaskid);
					workList.add(workTask);
				} catch (PoException e) {
					e.printStackTrace();
				}
			}
			//对任务进度进行算法计算 end
		pageManager.setData(workList);
	  }
	 return pageManager;
	}
	/**
	 * @description 获取单条记录方法(点击任务名称查询)
	 * @param WorkTask task 实体类
	 * @return Task 查询结果
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-21
	 */
	public WorkTask getTask(WorkTask task) throws PoException {
		WorkTask workTask=null;
		WorkTaskfinish workTaskfinish=null;
		workTask=dao.get(task);
		if (null==workTask) {
			workTask=new WorkTask();
			workTaskfinish=new WorkTaskfinish();
			workTaskfinish.setTaskId(task.getId());
			try {
				WorkTaskfinish taskfinish=workTaskfinishService.get(workTaskfinish);
				if (null!=taskfinish) {
					BeanUtils.copyProperties(taskfinish, workTask);
					workTask.setId(taskfinish.getTaskId());
				}
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
				throw po;
			}
		}
		return workTask;
	}
	/**
	 * @description 修改
	 * @param WorkTask task 实体类,Integer addHistory，默认为0：添加事件；1：不添加事件
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-27
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer update(WorkTask workTask,Integer addHistory) throws PoException{
		Integer returnVal=-1;
		if (null==workTask) {
			return returnVal;
		}
		if (null==addHistory) {
			addHistory=0;//0：默认为添加事件（修改），1：历史操作
		}
		try {
			propertyService.fillProperties(workTask,true);
			if (null!=workTask.getEndTime()) {
				workTask.setEndTime(DateUtils.fillTime(workTask.getEndTime()));
			}
			if (null !=workTask.getIsImportDiary() && !"".equals(workTask.getIsImportDiary()) 
						&& null!=workTask.getStatus() &&  !"5".equals(workTask.getStatus()) 
						&&  !"8".equals(workTask.getStatus()) && addHistory==0) {//addHistory==0 历史操作不对工程接口调用
					if ("1".equals(workTask.getIsImportDiary())) {//导入日程
						//先查询，如存在，则修改;不存在，则新增
						List<Diary> list=null;
						Diary diaryTemp=new Diary();
						diaryTemp.setModuleFlag("3");/*模块来源标记[3-待办任务导入]*/
						diaryTemp.setBusinessId(workTask.getId());/*设置主键ID*/
						try {
							list=diaryService.queryInterface(diaryTemp);
							if (null!=list){
								if(list.size()==0){//新增
									Diary diary=new Diary();
									diary.setPossessorId(workTask.getDirectorId());/*日程所有人ID*/
									diary.setTitle(workTask.getTaskName());/*日程标题*/
									diary.setContent(workTask.getContent());/*主要完成事项*/
									diary.setStarttime(workTask.getStartTime());/*日程开始时间*/
									diary.setEndtime(workTask.getEndTime());/*日程结束时间*/
									diary.setPeriodType(Constants.PO_PERIODWAY_NONE);/*周期类型(0-无定期)*/
									diary.setModuleFlag(Constants.PO_DIARY_MODULEFLAG_TASK);/*模块来源标记[3-待办任务导入]*/
									diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);/*日程类型[0-工作日程]*/
									diary.setIsShare(Constants.PO_DIARY_ISSHARE);/*是否被共享(0-否)*/
									diary.setPeriodWay("0");/*周期时间节点，辅助记录用*/
									diary.setBusinessId(workTask.getId());/*设置主键ID*/
									diary.setMillisecond(new Date().getTime());//创建时间毫秒数
									diary.setModifyFlag("");
									diaryService.save(diary);
								}else{//修改
									Diary diary=list.get(0);
									diary.setPossessorId(workTask.getDirectorId());/*日程所有人ID*/
									diary.setTitle(workTask.getTaskName());/*日程标题*/
									diary.setContent(workTask.getContent());/*主要完成事项*/
									diary.setStarttime(workTask.getStartTime());/*日程开始时间*/
									diary.setEndtime(workTask.getEndTime());/*日程结束时间*/
									diary.setPeriodType(Constants.PO_PERIODWAY_NONE);/*周期类型(0-无定期)*/
									diary.setModuleFlag(Constants.PO_DIARY_MODULEFLAG_TASK);/*模块来源标记[3-待办任务导入]*/
									diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);/*日程类型[0-工作日程]*/
									diary.setIsShare(Constants.PO_DIARY_ISSHARE);/*是否被共享(0-否)*/
									diary.setPeriodWay("0");/*周期时间节点，辅助记录用*/
									diary.setBusinessId(workTask.getId());/*设置主键ID*/
									diary.setMillisecond(new Date().getTime());//创建时间毫秒数
									diary.setModifyFlag("");
									diaryService.updateDairyAndSaveControlSide(diary);
								}
							}
						} catch (Exception e1) {
							PoException po=new PoException(e1);
							po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
							throw po;
						}
					}else {//取消日程
						List<Diary> list=null;
						Diary diaryTemp=new Diary();
						diaryTemp.setModuleFlag("3");/*模块来源标记[3-待办任务导入]*/
						diaryTemp.setBusinessId(workTask.getId());/*设置主键ID*/
						try {
							list=diaryService.queryInterface(diaryTemp);
							if (null!=list && list.size()==1) {
								Diary diary=list.get(0);
								String primarykey=String.valueOf(diary.getId());
								String[] primaryKeys=primarykey.split(",");
								diary.setPrimaryKeys(primaryKeys);
								returnVal=diaryService.delete(diary, false);
							}
						} catch (Exception e1) {
							PoException po=new PoException(e1);
							po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
							throw po;
						}
					}
			}
			//修改任务
			if (workTask.getParentTaskid().intValue()==-1) {//如果当前任务为父任务则不存在任务紧急程度
				workTask.setTaskImpCode(null);
			}
			if(null!=workTask.getEndTime()){
				workTask.setEndTime(DateUtils.fillTime(workTask.getEndTime()));
			}
			//判断当前任务是否已超期(通过修改操作)
			if (!"8".equals(workTask.getStatus()) 
					&& DateUtils.subtractionMinute(DateUtils.fillTime(workTask.getEndTime()), new Date()) >0) {
				workTask.setStatus("6");//超期
			}
			//汇报提醒 用于最新需求调整 李洪宇 2014-09-28 start
			if (!"8".equals(workTask.getStatus()) && null!=workTask.getReportDay()
					&& workTask.getReportDay().intValue()>0 && addHistory==0) {
				long sponsorId=workTask.getSponsorId();//发起人
				long directorId=workTask.getDirectorId();//负责人
				String sponsorName="";//发起人 名称
				String directorName="";//负责人 名称
				User sponUser=userService.getUser(sponsorId);
				sponsorName=sponUser.getDisplayName();
				User direUser=userService.getUser(directorId);
				directorName=direUser.getDisplayName();
				String remindTime=DateUtils.formatDate(DateUtils.addOrSubtractDaysReturnDate(
						workTask.getEndTime(), 0-(workTask.getReportDay().intValue())), "yyyy-MM-dd");
				remindTime+="  09:00:00";//早上9：00提醒
				StringBuffer remindInfo=new StringBuffer();
				remindInfo.append("{");
				//针对内容中可能出的换行情况，特此将换行符去掉
				remindInfo.append("\"content\":\"").append(
						workTask.getContent()==null?"":workTask.getContent().replaceAll("\r", "").replaceAll("\n", "")).append("\",");
				remindInfo.append("\"remindMode\":2,");//内部邮件
				if (workTask.getSponsorId().intValue()==workTask.getDirectorId().intValue()) {
					remindInfo.append("\"receiveId\":\"[{\\\"id\\\":\\\"")
					.append(sponsorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
					.append(sponsorName).append("\\\"}]\",");
				}else {
					remindInfo.append("\"receiveId\":\"[{\\\"id\\\":\\\"")
					.append(sponsorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
					.append(sponsorName).append("\\\"},");
					remindInfo.append("{\\\"id\\\":\\\"")
					.append(directorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
					.append(directorName).append("\\\"}]\",");
				}
				remindInfo.append("\"remindType\":4,");
				remindInfo.append("\"cycle\":1,");
				remindInfo.append("\"isActive\":\"1\",");
				remindInfo.append("\"tableName\":\"1\",");
				remindInfo.append("\"viewCycle\":\"\",");
				remindInfo.append("\"viewStartTime\":\"").append(remindTime+"\",");
				remindInfo.append("\"viewEndTime\":\"\",");
				remindInfo.append("\"startForwardTime\":0,");
				remindInfo.append("\"endForwardTime\":0,");
				remindInfo.append("\"endForwardTimeValue\":0,");
				remindInfo.append("\"startForwardTimeValue\":0,");
				remindInfo.append("\"title\":\"").append(workTask.getTaskName()).append("\",");
				remindInfo.append("\"intervalHour\":0,");
				remindInfo.append("\"intervalMinute\":0,");
				remindInfo.append("\"remindNum\":0,");
				remindInfo.append("\"cronExpression\":\"\",");
				remindInfo.append("\"remindInterval\":0,");
				remindInfo.append("\"viewStartTimeStr\":\"").append(remindTime+"\",");
				remindInfo.append("\"viewEndTimeStr\":\"\"");
				remindInfo.append("}");
				workTask.setRemind(remindInfo.toString());
				workTask.setReportTime(DateUtils.parseDate(remindTime));
				if(null!=workTask.getReportType() && "1".equals(workTask.getReportType())){//短信提醒
					remindInfo=new StringBuffer();
					remindInfo.append("{");
					//针对内容中可能出的换行情况，特此将换行符去掉
					remindInfo.append("\"content\":\"").append(
							workTask.getContent()==null?"":workTask.getContent().replaceAll("\r", "").replaceAll("\n", "")).append("\",");
					remindInfo.append("\"remindMode\":1,");//短信
					if (workTask.getSponsorId().intValue()==workTask.getDirectorId().intValue()) {
						remindInfo.append("\"receiveId\":\"[{\\\"id\\\":\\\"")
						.append(sponsorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
						.append(sponsorName).append("\\\"}]\",");
					}else {
						remindInfo.append("\"receiveId\":\"[{\\\"id\\\":\\\"")
						.append(sponsorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
						.append(sponsorName).append("\\\"},");
						remindInfo.append("{\\\"id\\\":\\\"")
						.append(directorId).append("\\\",\\\"type\\\":\\\"1\\\",\\\"text\\\":\\\"")
						.append(directorName).append("\\\"}]\",");
					}
					remindInfo.append("\"remindType\":4,");
					remindInfo.append("\"cycle\":1,");
					remindInfo.append("\"isActive\":\"1\",");
					remindInfo.append("\"tableName\":\"1\",");
					remindInfo.append("\"viewCycle\":\"\",");
					remindInfo.append("\"viewStartTime\":\"").append(remindTime+"\",");
					remindInfo.append("\"viewEndTime\":\"\",");
					remindInfo.append("\"startForwardTime\":0,");
					remindInfo.append("\"endForwardTime\":0,");
					remindInfo.append("\"endForwardTimeValue\":0,");
					remindInfo.append("\"startForwardTimeValue\":0,");
					remindInfo.append("\"title\":\"").append(workTask.getTaskName()).append("\",");
					remindInfo.append("\"intervalHour\":0,");
					remindInfo.append("\"intervalMinute\":0,");
					remindInfo.append("\"remindNum\":0,");
					remindInfo.append("\"cronExpression\":\"\",");
					remindInfo.append("\"remindInterval\":0,");
					remindInfo.append("\"viewStartTimeStr\":\"").append(remindTime+"\",");
					remindInfo.append("\"viewEndTimeStr\":\"\"");
					remindInfo.append("}");
					workTask.setRemindTemp(remindInfo.toString());
				}
			}
			if (!"8".equals(workTask.getStatus()) && null!=workTask.getReportDay() 
					&& workTask.getReportDay().intValue()==0 && addHistory==0) {
				StringBuffer remindInfo=new StringBuffer();
				remindInfo.append("{");
				remindInfo.append("\"content\":\"\",");
				remindInfo.append("\"remindMode\":0,");//不提醒
				remindInfo.append("\"receiveId\":\"\",");
				remindInfo.append("\"remindType\":4,");
				remindInfo.append("\"cycle\":1,");
				remindInfo.append("\"isActive\":\"0\",");
				remindInfo.append("\"tableName\":\"1\",");
				remindInfo.append("\"viewCycle\":\"\",");
				remindInfo.append("\"viewStartTime\":\"\",");
				remindInfo.append("\"viewEndTime\":\"\",");
				remindInfo.append("\"startForwardTime\":0,");
				remindInfo.append("\"endForwardTime\":0,");
				remindInfo.append("\"endForwardTimeValue\":0,");
				remindInfo.append("\"startForwardTimeValue\":0,");
				remindInfo.append("\"title\":\"\",");
				remindInfo.append("\"intervalHour\":0,");
				remindInfo.append("\"intervalMinute\":0,");
				remindInfo.append("\"remindNum\":0,");
				remindInfo.append("\"cronExpression\":\"\",");
				remindInfo.append("\"remindInterval\":0,");
				remindInfo.append("\"viewStartTimeStr\":\"\",");
				remindInfo.append("\"viewEndTimeStr\":\"\"");
				remindInfo.append("}");
				workTask.setRemind(remindInfo.toString());
				remindInfo=new StringBuffer();
				remindInfo.append("{");
				remindInfo.append("\"content\":\"\",");
				remindInfo.append("\"remindMode\":0,");//不提醒
				remindInfo.append("\"receiveId\":\"\",");
				remindInfo.append("\"remindType\":4,");
				remindInfo.append("\"cycle\":1,");
				remindInfo.append("\"isActive\":\"0\",");
				remindInfo.append("\"tableName\":\"1\",");
				remindInfo.append("\"viewCycle\":\"\",");
				remindInfo.append("\"viewStartTime\":\"\",");
				remindInfo.append("\"viewEndTime\":\"\",");
				remindInfo.append("\"startForwardTime\":0,");
				remindInfo.append("\"endForwardTime\":0,");
				remindInfo.append("\"endForwardTimeValue\":0,");
				remindInfo.append("\"startForwardTimeValue\":0,");
				remindInfo.append("\"title\":\"\",");
				remindInfo.append("\"intervalHour\":0,");
				remindInfo.append("\"intervalMinute\":0,");
				remindInfo.append("\"remindNum\":0,");
				remindInfo.append("\"cronExpression\":\"\",");
				remindInfo.append("\"remindInterval\":0,");
				remindInfo.append("\"viewStartTimeStr\":\"\",");
				remindInfo.append("\"viewEndTimeStr\":\"\"");
				remindInfo.append("}");
				workTask.setRemindTemp(remindInfo.toString());
			}
			if(null==workTask.getReportDay() || workTask.getReportDay().intValue()==0){
				workTask.setReportType("0");
			}
			//汇报提醒 用于最新需求调整 李洪宇 2014-09-28 end
			
			//修改批注中名称 add by lihongyu at 2014-10-31 start
			if(!"8".equals(workTask.getStatus())){
				Anno anno = new Anno();
				anno.setBusinessId(workTask.getId());//传入businessId
				anno.setAnnoName(workTask.getTaskName());//传入更改后的标题
				anno.setAnnoType("3");//传入类型，0计划，1工作日程，2工作日志，3督办协办
				try {
					boolean flag = annoService.updateAnnoName(anno);//抛出异常时返回false，其他返回true
					if(!flag){
						PoException po=new PoException(new CustomException());
						po.setLogMsg(MessageUtils.getMessage("JC_OA_PO_046"));
						throw po;
					}
				} catch (Exception e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
			//修改批注中名称add by lihongyu at 2014-10-31 end
			
			//针对暂存任务保存时，查询其是否存父任务，如果存在则判断其父任务所包括的子任务 start
			if("0".equals(workTask.getStatus())){
				WorkTask wt=new WorkTask();
				wt.setId(workTask.getParentTaskid());
				WorkTask wTask=worktaskDao.get(wt);
				if (null!=wTask && wTask.getNormalToCount()==0) {
					propertyService.fillProperties(wTask,false);
					wTask.setTaskProc(0);//首次下发子任务时，原进度设置为0
					returnVal=worktaskDao.update(wTask);
				}
			}
			//针对暂存任务保存时，查询其是否存父任务，如果存在则判断其父任务所包括的子任务 end
			
			returnVal=dao.update(workTask);
			
			if (addHistory==0) {
				returnVal=handleTaskRelevance(workTask,"2");//任务关联处理
				returnVal=addWorkTaskHis(workTask, "1");//向任务事件表中添加信息:修改
			}
			
			//汇报时限设置(内部邮件：默认)
			if (!"8".equals(workTask.getStatus()) && null!=workTask.getRemind() 
					&& !"".equals(workTask.getRemind()) && addHistory==0) {
				try {
					returnVal=remindService.save(workTask.getRemind(), workTask.getId(),Constants.TABLE_NAME);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
			//汇报提醒设置(短信提醒)
			if(!"8".equals(workTask.getStatus()) && null!=workTask.getRemindTemp() 
					&& !"".equals(workTask.getRemindTemp())  && addHistory==0){
				try {
					returnVal=remindService.save(workTask.getRemindTemp(), workTask.getId(),Constants.TABLE_NAME_TEMP);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
			//删除页面中上传中删除的附件
			if(null !=workTask.getDelattachIds() && !"null".equals(workTask.getDelattachIds())
					&& !StringUtil.isEmpty(workTask.getDelattachIds())){
				uploadService.deleteFileByIds(workTask.getDelattachIds());
			}
			//附件转换
			StringBuffer fileids=new StringBuffer();
			if (null!=workTask.getFileids() && workTask.getFileids().size()>0) {
				Iterator iterator=workTask.getFileids().iterator();
				while (iterator.hasNext()) {
					fileids.append(iterator.next()).append(",");
				}
				workTask.setFileid(fileids.toString().split(","));
			}
			//查询附件
			AttachBusiness attachBusiness =null;
			String fileIds[] = workTask.getFileid();
			if (null!=fileIds && fileIds.length>0) {
				List<AttachBusiness> attachList=null;
				AttachBusiness attach=new AttachBusiness();
				attach.setBusinessId(workTask.getId());
				try {
					attachList=attachBusinessService.queryAll(attach);//查询当前任务是否存在附件
				} catch (CustomException e1) {
					PoException po=new PoException(e1);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
					throw po;
				}
				if (null!=attachList && attachList.size()>0) {//如存在，则删除附件
					String[] attachId=null;
					attachId=new String[attachList.size()];
					AttachBusiness att=null;
					for (int i = 0,j=attachList.size(); i < j; i++) {
						att=attachList.get(i);
						attachId[i]=String.valueOf(att.getId());
					}
					AttachBusiness attch=new AttachBusiness();
					attch.setPrimaryKeys(attachId);
					try {
						returnVal=attachBusinessService.delete(attch, false);
					} catch (CustomException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
						throw po;
					}
				}
				if(fileIds != null && fileIds.length >0 && !"".equals(fileIds[0])){//删除后，再重新保存附件
					for (int i = 0; i < fileIds.length; i++) {
						attachBusiness = new AttachBusiness();
						attachBusiness.setAttachId(new Long(fileIds[i]));
						attachBusiness.setBusinessId(workTask.getId());
						attachBusiness.setBusinessTable(Constants.TABLE_NAME);
						attachBusiness.setBusinessSource("0");
						try {
							returnVal=attachBusinessService.save(attachBusiness);
						} catch (CustomException e) {
							PoException po=new PoException(e);
							po.setLogMsg(MessageUtils.getMessage("JC_SYS_046"));
							throw po;
						}
					}
				}
			}
		} catch (DBException e) {
			PoException po=new PoException(e);
			po.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw po;
		}
		return returnVal;
	}
	/**
	 * @description：根据日志查询日志下的批注
	 * @param anno
	 * @return
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-05-27
	 */
	public List<Anno> queryAnnoByWorklog(Anno anno){
		anno.setAnnoType(Constants.ANNOTYPE_WORKTASK+"");
		return annoService.queryAnnoAndReply(anno);
	}
	/**
	 * @description：保存批注回复
	 * @param anno
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-27
	 */
	public Integer saveAnnoReply(Anno anno) throws PoException{
		try {
			anno.setAnnoType(anno.getAnnoType()==null?Constants.ANNOTYPE_WORKTASK+"":anno.getAnnoType());
			anno.setAnnoDate(DateUtils.getSysDate());
			anno.setAnnoUserId(SystemSecurityUtils.getUser().getId());
			anno.setAnnoUserDepid(SystemSecurityUtils.getUser().getDeptId());
			anno.setByAnnoUserDepid(UserUtils.getUser(anno.getByAnnoUserId()).getDeptId());
			return annoService.save(anno);
		} catch (CustomException e) {
			PoException po = new PoException(e);
			throw po;
		}
	}
	/**
	 * @description 批量修改
	 * @param List<WorkTask> workTask
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-28
	 */
	public Integer batchUpdate(List<WorkTask> workTask) throws PoException{
		Integer reVal=0;
		if (null!=workTask && workTask.size()>0) {
			for (int i = 0,j=workTask.size(); i <j; i++) {
				try {
					reVal=dao.update(workTask.get(i));
				} catch (DBException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
					throw po;
				}
			}
		}
		return reVal;
	}
	/**
	 * @description 邮件及短信发送
	 * @param WorkTask task
	 * @return boolean
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-06-06
	 */
	public boolean sendMailOrSms(WorkTask task) throws PoException{
		boolean remind=false;
		if (null==task || null==task.getRemindType() || null==task.getSponsorId() || null==task.getDirectorId()) {
			return remind;
		}
		String rePersonId=task.getDirectorId()+"";//收件人为任务负责人及参与人
		if (null!=task.getPrticipantsId()) {
			//对参与人是否存在负责人重复校验
			StringBuffer prticsBuffer=new StringBuffer();
			String[] prtic=task.getPrticipantsId().split(",");
			if (null!=prtic && prtic.length>0) {
				for (int i = 0; i < prtic.length; i++) {
					if (!task.getDirectorId().toString().equals(prtic[i].toString())) {
						prticsBuffer.append(prtic[i]).append(",");
					}
				}
			}
			rePersonId+=","+prticsBuffer.toString();
		}
	    User userInfo = null;
	    String tipInfo="";
	    String smsTypeString="0";//0:短信接口，当收件人手机号为空时，仍可以发送；1：短信接口，当收件人手机号为空时，不可以发送；
	    switch (task.getTaskEventType()) {
		case "6":
	    	if("2".equals(task.getRemindType())){//邮件
				tipInfo+=Constants.getEmailSubject("任务催办");
			}
	    	userInfo=SystemSecurityUtils.getUser();
	    	smsTypeString="0";
			break;
		case "8":
	    	if("2".equals(task.getRemindType())){//邮件
				tipInfo+=Constants.getEmailSubject("任务取消");
			}
	    	userInfo=SystemSecurityUtils.getUser();
	    	smsTypeString="0";
			break;
		case "11":
	    	if("2".equals(task.getRemindType())){//邮件
				tipInfo+=Constants.getEmailSubject("任务超期");
			}
	    	User user=new User();
	    	user.setId(task.getSponsorId());//任务发起人为邮件发件人
	    	user.setDeleteFlag(0);
	    	try {
	    		userInfo=userService.get(user);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
				throw po;
			}
	    	smsTypeString="1";
			break;
		default:
			userInfo=SystemSecurityUtils.getUser();
			break;
		}
		if (null!=task.getRemindType() && "2".equals(task.getRemindType())) {//发邮件
			Map<String,String> map=new java.util.HashMap<String,String>();
			map.put(IInteractFacadeService.MAIL_SENDER_ID,userInfo.getId()==null?"":userInfo.getId()+"");//发件人为当前登录人
			map.put(IInteractFacadeService.MAIL_RECEIVER_IDS,rePersonId);
			map.put(IInteractFacadeService.MAIL_SUBJECT,tipInfo);
			String content=Constants.getEmailContent(task.getTaskName(), task.getContent()==null?"":task.getContent());
			map.put(IInteractFacadeService.MAIL_CONTENT,content);
			try {
				remind=interactFacadeService.sendMail(map);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
				throw po;
			}
		}
		if (null!=task.getRemindType() && "1".equals(task.getRemindType())) {//发短信
			Map<String,String> map=new java.util.HashMap<String,String>();
			map.put("ids",rePersonId);//信息收件人
			tipInfo+=Constants.getSmsText(task.getTaskName(), task.getContent());
			map.put("text",tipInfo);
			map.put("createUser",userInfo.getId()+"");
			map.put("SMS_SENDTYPE","sendType_3"); // 短信类型  通知
			try {
				if ("0".equals(smsTypeString)) {
					remind=interactFacadeService.sendSms(map);
				}else {
					remind=interactFacadeService.jobSendSms(map);
				}
				
			} catch (Exception e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
				throw po;
			}
		}
		return remind;
	}
	/**
	 * @description 设置session
	 * @param WorkTask task,HttpServletRequest request
	 * @return WorkTask
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-10
	 */
	public WorkTask setSession(WorkTask task,HttpServletRequest request) throws PoException{
		User userInfo = SystemSecurityUtils.getUser();
		if (!"yes".equals(request.getParameter("isQueryAll"))) {//用于区分任务查询及任务监控
			task.setTaskPersonId(userInfo.getId());
			task.setSponsorId(null);
			task.setDirectorId(null);
//			if (null==task.getInfoStatus() && "1".equals(task.getTaskType())) {//负责人未设置任务状态
//				String[] infoStatus={"0","1","2","3","4","6","7"};
//				task.setInfoStatus(infoStatus);
//			}
		}else {
			task.setTaskPersonId(task.getSponsorId());
			task.setSponsorId(null);
			task.setDirectorId(null);
			task.setUserId(null);
			task.setCreateUserOrg(userInfo.getOrgId());//新增按组织查询 2014-07-29 by 李洪宇
//			if (null==task.getInfoStatus()) {//负责人未设置任务状态
//				String[] infoStatus={"0","1","2","3","4","6","7"};
//				task.setInfoStatus(infoStatus);
//			}
		}
		if (null==task.getInfoStatus()) {
			String[] infoStatus={"0","1","2","3","4","6","7"};
			task.setInfoStatus(infoStatus);
		}
		return task;
	}
	/**
	 * @description 查询任务进度
	 * @param WorkTask task
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-13
	 */
	public Integer queryTaskProc(WorkTask task)throws PoException{
		if (null==task) {
			return 0;
		}
		//判断当前任务是否为最底层任务
		WorkTask w=new WorkTask();
		w.setParentTaskid(task.getId());
		List<WorkTask> totalList=worktaskDao.queryTaskProc(w);
		if (null!=totalList && totalList.size()>0) {
			List<WorkTask> list=getSubTaskList(task.getId(),0);
			WorkTask workTask=new WorkTask();
			workTask.setId(task.getId());
			workTask.setParentTaskid(task.getParentTaskid());
			workTask.setTaskProc(0);
			workTask.setIsSubTask(1);
			workTask.setTaskPercent(1.0);
			list.add(workTask);
			WorkTask work=new WorkTask();
			work.setId(task.getParentTaskid());
			work.setDeleteFlag(0);
			work=worktaskDao.get(work);
			if (null!=work) {
				WorkTask pWork=new WorkTask();
				pWork.setId(work.getId());
				pWork.setParentTaskid(work.getParentTaskid());
				pWork.setTaskProc(0);
				pWork.setIsSubTask(1);
				pWork.setTaskPercent(1.0);
				list.add(pWork);
			}
			return (int)Math.floor(getTaskProc(task.getId().intValue(),list));
		}else {	
			return task.getTaskProc();
		}
		
	}
	/**
	 * @description 任务(子任务占父任务百分比)
	 * @param WorkTask task
	 * @return WorkTask
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-16
	 */
	public Double getPercent(Long parentId,Long subId){
		WorkTask works=new WorkTask();
		works.setParentTaskid(parentId);
		List<WorkTask> totalList=worktaskDao.queryTaskProc(works);
		Double taskImp=1.0;//百分比
		Integer totalImpCode=0;//总任务重要等级之和，作为之后分母使用
		WorkTask workTask=null;
		if (null!=totalList && totalList.size()>0) {
			Iterator it =totalList.iterator();
			while(it.hasNext() ){
				workTask=(WorkTask)it.next();
				totalImpCode+=Integer.valueOf(workTask.getTaskImpCode2())+Integer.valueOf(workTask.getTaskImpCode4())
				+Integer.valueOf(workTask.getTaskImpCode6())+Integer.valueOf(workTask.getTaskImpCode8());
			}
			if (totalImpCode!=0){//分母使用不为0时
				Iterator subIt =totalList.iterator();
				while(subIt.hasNext() ){
					WorkTask work=(WorkTask)subIt.next();
					Double impCode=0.0;
					if (subId.equals(work.getTaskId())) {
						 impCode=((Double.valueOf(work.getTaskImpCode2())/totalImpCode)+
									(Double.valueOf(work.getTaskImpCode4())/totalImpCode)+
									(Double.valueOf(work.getTaskImpCode6())/totalImpCode)+
									(Double.valueOf(work.getTaskImpCode8())/totalImpCode));
						 taskImp=impCode;
					}	
				}
			}
		}
		return taskImp;
	}
	/**
	 * @description 取得子任务(对外)
	 * @param HttpServletRequest request
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-25
	 */
	public List<WorkTask> getChildList(HttpServletRequest request) throws PoException{
		List<WorkTask> workTasks=null;
		WorkTask workTask=null;
		String parentTaskid=request.getParameter("id");
		if (null!=parentTaskid && !"".equals(parentTaskid)) {
			workTask=new WorkTask();
			workTask.setParentTaskid(new Long(parentTaskid));
			workTask.setDeleteFlag(0);
			try {
				workTasks=queryAll(workTask);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
				throw po;
			}
		}
		return workTasks;
	}
	/**
	 * @description 根据任务ID查询其对应的所有上级父任务ID
	 * @param Long taskId
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-27
	 */
	private List<WorkTask> parentList=new ArrayList<WorkTask>();
	public List<WorkTask> getParentTask(Long taskId,Integer isFrist){
		List<WorkTask> list=null;
		WorkTask workTask=null;
		if (isFrist.intValue()==0) {
			parentList=new ArrayList<WorkTask>();
		}
		list=worktaskDao.getParentTask(taskId);
		if (null!=list && list.size()>0) {
			Iterator it =list.iterator();
			while(it.hasNext()){
				workTask=(WorkTask)it.next();
				parentList.add(workTask);
				if (workTask.getParentTaskid().intValue()!=-1) {
					getParentTask(workTask.getParentTaskid(),1);
				}
			}
		}
		return parentList;
	}
	/**
	 * @description 查询子任务集合(根据指定的任务ID)
	 * @param Long taskId,Integer isFrist
	 * @return List<WorkTask>
	 * @author 李洪宇
	 * @version  2014-06-28
	 */
	private List<WorkTask> subWorkTaskList=new ArrayList<WorkTask>();
	private Long parentTaskId=0L;
	public List<WorkTask> getSubTaskList(Long taskId,Integer isFrist){
		//初始化子任务集合
		if (null !=isFrist && isFrist.intValue()==0) {
			parentTaskId=taskId;
			subWorkTaskList=new ArrayList<WorkTask>();
		}
		//查询子任务
		WorkTask workTask=new WorkTask();
		workTask.setParentTaskid(taskId);
		List<WorkTask> subList=worktaskDao.queryTaskProc(workTask);
		if (null!=subList && subList.size()>0) {
			if (taskId.intValue()!=parentTaskId.intValue()) {
				WorkTask work=new WorkTask();
				work.setId(taskId);
				work.setDeleteFlag(0);
				WorkTask wTask=worktaskDao.get(work);
				WorkTask newTask=new WorkTask();
				newTask.setId(wTask.getId());
				newTask.setParentTaskid(wTask.getParentTaskid());
				newTask.setTaskPercent(getPercent(wTask.getParentTaskid(),wTask.getId()));
				newTask.setTaskProc(wTask.getTaskProc());
				newTask.setIsSubTask(1);
				subWorkTaskList.add(newTask);
			}
			Iterator it =subList.iterator();
			while(it.hasNext()){
				WorkTask wTask=(WorkTask)it.next();
				WorkTask newTask=new WorkTask();
				newTask.setId(wTask.getTaskId());
				newTask.setParentTaskid(wTask.getParentTaskid());
				newTask.setTaskPercent(getPercent(wTask.getParentTaskid(),wTask.getTaskId()));
				newTask.setTaskProc(wTask.getTaskProc());
				wTask.setParentTaskid(wTask.getId());
				List<WorkTask> subTask=worktaskDao.queryTaskProc(wTask);
				if (null!=subTask && subTask.size()>0) {
					newTask.setIsSubTask(1);
				}else {
					newTask.setIsSubTask(0);
				}
				subWorkTaskList.add(newTask);
			}
			Iterator sub =subList.iterator();
			while(sub.hasNext()){
				WorkTask wTask=(WorkTask)sub.next();
				wTask.setParentTaskid(wTask.getTaskId());
				List<WorkTask> subTask=worktaskDao.queryTaskProc(wTask);
				if (null!=subTask && subTask.size()>0) {
					Iterator subIt=subTask.iterator();
					while(subIt.hasNext()){
						WorkTask wt=(WorkTask)subIt.next();
						getSubTaskList(wt.getTaskId(),1);
					}
				}
			}
		}else {
			WorkTask work=new WorkTask();
			work.setId(taskId);
			work.setDeleteFlag(0);
			WorkTask wTask=worktaskDao.get(work);
			if (null!=wTask) {
				WorkTask newTask=new WorkTask();
				newTask.setId(wTask.getId());
				newTask.setParentTaskid(wTask.getParentTaskid());
				newTask.setTaskPercent(getPercent(wTask.getParentTaskid(),wTask.getId()));
				newTask.setTaskProc(wTask.getTaskProc());
				newTask.setIsSubTask(0);
				subWorkTaskList.add(newTask);
			}
		}
		return subWorkTaskList;
	}
	/**
	 * @description 查询当前任务进度
	 * @param int taskId,List<WorkTask> ls
	 * @return Double
	 * @author 孙一石
	 * @version  2014-06-28
	 */
	public Double getTaskProc(int taskId,List<WorkTask> ls){
		// 1.先判断是否有子节点，有子节点
		// =（子结节进度 * 比重/重要程度和）*比重/重要程度和
		// 2.无子节点
		// =进度*比重/重要程度和
		 List low_nodes = new ArrayList();
		 for (int i = 0; i < ls.size(); i++) {
			 	WorkTask a=ls.get(i);
				// 找出最低层节点
				if (a.getIsSubTask().intValue() == 0) {
					// 计算最低层节点的进度
					a.setTaskProc(Integer.parseInt(new BigDecimal(a.getTaskPercent() * a.getTaskProc()).setScale(0, BigDecimal.ROUND_HALF_UP)+""));
					// 找出父节点，将最底层节点的进度累加到父节点中
					WorkTask parent_node = getNode(a.getParentTaskid().intValue(), ls);
					int proc=parent_node.getTaskProc().intValue();
					proc+=a.getTaskProc().intValue();
					parent_node.setTaskProc(proc);
					low_nodes.add(a.getId());
				}
			}
		// 从集合中摘除最低层
			int y=0;
			for (int i = 0; i < ls.size(); i=y) {
				WorkTask a = ls.get(i);
				y++;
				for (int j = 0; j < low_nodes.size(); j++) {
					if (a.getId().intValue() == Integer.parseInt(low_nodes.get(j).toString())) {
						WorkTask r = getNode(a.getId().intValue(), ls);
						ls.remove(r);
						y = 0;
					}
				}
			}
			if (ls.size() == 1) {
				return Double.valueOf(ls.get(0).getTaskProc().intValue());
			} else {
				// 重新构建树
				for (WorkTask a : ls) {
					int c = 0;
					for (WorkTask b : ls) {
						if (a.getId().intValue() == b.getParentTaskid()) {
							c++;
							break;
						}
					}
					if (c == 0) {
						a.setIsSubTask(0);
					}
				}
				return  getTaskProc(taskId, ls);
			}
	}
	/**
	 * @description 将集合中相等的任务提出
	 * @param int id, List<WorkTask> ls
	 * @return WorkTask
	 * @author 孙一石
	 * @version  2014-06-28
	 */
	public WorkTask getNode(int id, List<WorkTask> ls) {
		for (WorkTask a : ls) {
			if (a.getId().intValue() == id) {
				return a;
			}
		}
		return null;
	}
	/**
	 * @description 取消任务模板
	 * @param WorkTask task
	 * @return Integer
	 * @author 李洪宇
	 * @version  2014-07-02
	 */
	public Integer cancalTemplate(WorkTask task){
		return worktaskDao.cancalTemplate(task);
	}
	/**
	 * @description 任务查询(分页,非过滤)
	 * @param WorkTask  workTask 实体类
	 * @param PageManager page
	 * @return PageManager
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-05-20
	 */
	public PageManager queryByPageAll(WorkTask task, PageManager page,String countSQL,String querySQL) {
		String taskNameTemp="";
		if(null==countSQL || "".equals(countSQL)){
			countSQL=Constants.QUERY_COUNT_TASK;
		}
		if(null==querySQL || "".equals(querySQL)){
			querySQL=Constants.QUERY_TASK;
		}
		if (null!=task.getEndTime()) {//截止日期手动添加时分秒
			task.setEndTime(DateUtils.fillTime(task.getEndTime()));
		}
		task.setDeleteFlag(0);
		PageManager pageManager=dao.queryByPage(task, page,countSQL,querySQL);
		List workTaskList=pageManager.getData();
		List workList=null;
		if (null!=workTaskList && workTaskList.size()>0) {
			workList=new ArrayList();
			//对任务进度进行算法计算 start
			for (int i = 0,k=workTaskList.size(); i < k; i++) {
				WorkTask workTask=(WorkTask)workTaskList.get(i);
				try {
					Long parentTaskid=workTask.getParentTaskid();
					workTask.setTaskProc(queryTaskProc(workTask));
					workTask.setParentTaskid(parentTaskid);
					workList.add(workTask);
				} catch (PoException e) {
					e.printStackTrace();
				}
			}
			//对任务进度进行算法计算 end
		pageManager.setData(workList);
	  }
	 return pageManager;
	}
	/**
	 * @description 根据任务名称、发起人、负责人、开始、结束时间查询任务（用于任务布置、修改、保存模板时，是否存在已有任务）
	 * @param WorkTask  task 实体类
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-07-03
	 */
	public Integer querySomeTask(WorkTask task){
		Integer reVal=0;
		List<WorkTask> taskList=null;
		WorkTask workTask=null;
		if (null !=task && null!=task.getTaskName()) {
			taskList=new ArrayList<WorkTask>();
			workTask=new WorkTask();
			workTask.setTaskNameTemp(task.getTaskName());//用于任务名称查询  李洪宇 于2014-8-22
			workTask.setSponsorId(task.getSponsorId());
			workTask.setDirectorId(task.getDirectorId());
			if ("5".equals(task.getStatus())) {//保存模板时，对是否为模板进行校验。
				workTask.setIsTemplet(0);
				String[] infoStatus={"0","1","2","3","4","5","6","7","8"};
				workTask.setInfoStatus(infoStatus);
			}else {
				String[] infoStatus={"0","1","2","3","5","6","8"};//非保存模板操作时，不对状态为取消及拒接收的任务进行重复校验
				workTask.setInfoStatus(infoStatus);
			}
			workTask.setDeleteFlag(0);
			taskList=worktaskDao.queryAllForUnion(workTask);
			if (null!=taskList && taskList.size()>0) {
				//校验当前所要保存任务的开始、结束时间，是否包含在已存在任务的时间范围内
				if (null!=task.getStartTime() && null!=task.getEndTime()) {
					Iterator<WorkTask> it=taskList.iterator();
					while (it.hasNext()) {
						WorkTask woTask=(WorkTask)it.next();
						long sVal=DateUtils.subtractionMinute(woTask.getStartTime(),task.getStartTime());
						Date endDate=DateUtils.fillTime(task.getEndTime());
						long eVal=DateUtils.subtractionMinute(endDate, woTask.getEndTime());
						if (sVal >=0 && eVal>=0) {
							if ("5".equals(woTask.getStatus()) && "0".equals(task.getStatus())) {//模板->保存
								reVal=0;
							}else if ("5".equals(woTask.getStatus()) && "8".equals(task.getStatus())) {//模板->暂存  new add by lihongyu at 2015-3-18
								reVal=0;
							}
//							else if ("8".equals(woTask.getStatus()) && "0".equals(task.getStatus())) {//暂存->保存  new add by lihongyu at 2015-3-18
//								reVal=0;
//							}
							else if (!"5".equals(woTask.getStatus()) && "5".equals(task.getStatus())) {//非模板->保存模板
								reVal=0;
							}else {//其他
								reVal=1;
								break;
							}
						}
					}
				}
			}
		}
		return reVal;
	}
	/**
	 * @description 超期提醒调用
	 * @param 
	 * @return void
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-07-04
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void callRemind() throws PoException{
		Integer reVal=0;
		//一.查询当前任务是否存在设置超期提醒并且状态为未接收、 进行中、超期的任务
		String[] infoStatus={"0","1","2"};//未接收、进行中、延期的任务
		//1.查询短信提醒
		WorkTask workTask=new WorkTask();
		workTask.setRemindType("1");//1：短信提醒
		workTask.setDeleteFlag(0);
		workTask.setInfoStatus(infoStatus);
		List<WorkTask> messageList=worktaskDao.queryAll(workTask);
		Iterator<WorkTask> message=messageList.iterator();
		while (message.hasNext()) {
			WorkTask work=message.next();
			long val=DateUtils.subtractionMinute(work.getEndTime(),new Date());
			if (val>0) {//计划结束时间与当前时间对比
				//修改任务状态
				try {
					work.setStatus("6");//将任务状态设置为超期 
					reVal=worktaskDao.update(work);
//					//添加消息提醒   add by lihongyu start
//					NoticeMsg noticeMsg = new NoticeMsg();
//					noticeMsg.setSendUser(work.getSponsorId());//消息发送人
//					noticeMsg.setReceiveUser(work.getDirectorId());//消息接收人(负责人)
//					noticeMsg.setNoticeType(Constants.PO_WORKTASK_NOTICETYPE);//消息提醒类型
//					noticeMsg.setTitle("您有一个新的任务待接收，请查看");//消息标题
//					noticeMsg.setContent("您有一个新的任务待接收，请查看");//消息内容
//					noticeMsg.setUrl("/po/workTask/queryTask.action?fromPortal=yes");
//					noticeMsg.setShowFlag(0);//弹出标识
//					noticeMsg.setReadFlag(0);//已读标识
//					noticeMsg.setBusinessId(work.getId());//业务ID
//					noticeMsg.setBusinessFlag("tty_po_task");//业务标识
//					NoticeMsgUtil.notice(noticeMsg);
//					//添加消息提醒   add by lihongyu end
				} catch (DBException e1) {
					PoException po=new PoException(e1);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
				//添加事件
				try {
					WorkTaskHistory wHistory=new WorkTaskHistory();
					wHistory.setTaskId(work.getId());
					wHistory.setTaskEventType("11");
					wHistory.setTaskEventTitle(work.getTaskName());
					wHistory.setContent(work.getContent());
					wHistory.setDeleteFlag(0);
					wHistory.setCreateUser(work.getSponsorId());
					wHistory.setCreateDate(new Date());
					reVal=workTaskHistoryService.save(wHistory);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
				//调用发送邮件短信接口
				try {
					work.setTaskEventType("11");//超期提醒
					boolean isSend=sendMailOrSms(work);
				} catch (PoException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
		}
		//2.查询邮件提醒
		workTask=new WorkTask();
		workTask.setRemindType("2");//2：邮件提醒
		workTask.setDeleteFlag(0);
		workTask.setInfoStatus(infoStatus);
		List<WorkTask> mailList=worktaskDao.queryAll(workTask);
		Iterator<WorkTask> mail=mailList.iterator();
		while (mail.hasNext()) {
			WorkTask work=mail.next();
			long val=DateUtils.subtractionMinute(work.getEndTime(),new Date());
			if (val>0) {//计划结束时间与当前时间对比
				//修改任务状态
				try {
					work.setStatus("6");//将任务状态设置为超期 
					reVal=worktaskDao.update(work);
//					//添加消息提醒   add by lihongyu start
//					NoticeMsg noticeMsg = new NoticeMsg();
//					noticeMsg.setSendUser(work.getSponsorId());//消息发送人
//					noticeMsg.setReceiveUser(work.getDirectorId());//消息接收人(负责人)
//					noticeMsg.setNoticeType(Constants.PO_WORKTASK_NOTICETYPE);//消息提醒类型
//					noticeMsg.setTitle("您有一个新的任务待接收，请查看");//消息标题
//					noticeMsg.setContent("您有一个新的任务待接收，请查看");//消息内容
//					noticeMsg.setUrl("/po/workTask/queryTask.action?fromPortal=yes");
//					noticeMsg.setShowFlag(0);//弹出标识
//					noticeMsg.setReadFlag(0);//已读标识
//					noticeMsg.setBusinessId(work.getId());//业务ID
//					noticeMsg.setBusinessFlag("tty_po_task");//业务标识
//					NoticeMsgUtil.notice(noticeMsg);
//					//添加消息提醒   add by lihongyu end
				} catch (DBException e1) {
					PoException po=new PoException(e1);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
				//添加事件
				try {
					WorkTaskHistory wHistory=new WorkTaskHistory();
					wHistory.setTaskId(work.getId());
					wHistory.setTaskEventType("11");
					wHistory.setTaskEventTitle(work.getTaskName());
					wHistory.setContent(work.getContent());
					wHistory.setDeleteFlag(0);
					wHistory.setCreateUser(work.getSponsorId());
					wHistory.setCreateDate(new Date());
					reVal=workTaskHistoryService.save(wHistory);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
				//调用发送邮件短信接口
				try {
					work.setTaskEventType("11");//超期提醒
					boolean isSend=sendMailOrSms(work);
				} catch (PoException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
		}
		//3.查询未设置提醒
		workTask=new WorkTask();
		workTask.setRemindType("0");//0：不提醒
		workTask.setDeleteFlag(0);
		workTask.setInfoStatus(infoStatus);
		List<WorkTask> noRemindList=worktaskDao.queryAll(workTask);
		Iterator<WorkTask> noRemind=noRemindList.iterator();
		while (noRemind.hasNext()) {
			WorkTask work=noRemind.next();
			long val=DateUtils.subtractionMinute(work.getEndTime(),new Date());
			if (val>0) {//计划结束时间与当前时间对比
				//修改任务状态
				try {
					work.setStatus("6");//将任务状态设置为超期 
					reVal=worktaskDao.update(work);
//					//添加消息提醒   add by lihongyu start
//					NoticeMsg noticeMsg = new NoticeMsg();
//					noticeMsg.setSendUser(work.getSponsorId());//消息发送人
//					noticeMsg.setReceiveUser(work.getDirectorId());//消息接收人(负责人)
//					noticeMsg.setNoticeType(Constants.PO_WORKTASK_NOTICETYPE);//消息提醒类型
//					noticeMsg.setTitle("您有一个新的任务待接收，请查看");//消息标题
//					noticeMsg.setContent("您有一个新的任务待接收，请查看");//消息内容
//					noticeMsg.setUrl("/po/workTask/queryTask.action?fromPortal=yes");
//					noticeMsg.setShowFlag(0);//弹出标识
//					noticeMsg.setReadFlag(0);//已读标识
//					noticeMsg.setBusinessId(work.getId());//业务ID
//					noticeMsg.setBusinessFlag("tty_po_task");//业务标识
//					NoticeMsgUtil.notice(noticeMsg);
//					//添加消息提醒   add by lihongyu end
				} catch (DBException e1) {
					PoException po=new PoException(e1);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
				//添加事件
				try {
					WorkTaskHistory wHistory=new WorkTaskHistory();
					wHistory.setTaskId(work.getId());
					wHistory.setTaskEventType("11");
					wHistory.setTaskEventTitle(work.getTaskName());
					wHistory.setContent(work.getContent());
					wHistory.setDeleteFlag(0);
					wHistory.setCreateUser(work.getSponsorId());
					wHistory.setCreateDate(new Date());
					reVal=workTaskHistoryService.save(wHistory);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
		}
	}
	/**
	 * @description 查询所有任务
	 * @param WorkTask workTask
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-07-11
	 */
	public List<WorkTask> queryAllForUnion(WorkTask workTask){
		List<WorkTask> workTaskList=null;
		if (null!=workTask) {
			workTaskList=worktaskDao.queryAllForUnion(workTask);
		}
		return workTaskList;
	}
	/**
	 * @description 关联任务办理（当任务布置、修改、下发、删除时，处理与任务关联表业务）
	 * @param WorkTask workTask
	 * @param String handType 1:任务布置;2:任务修改;3:下发任务;4:删除
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-07-16
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer handleTaskRelevance(WorkTask workTask,String handType) throws PoException{
		Integer reVal=0;
		boolean isSpon=false;
		boolean isDire=false;
		if (null==workTask || "".equals(handType)) {
			return reVal;
		}
		switch (handType) {
		case "1"://任务布置(不存在父任务)
				List<WorkTaskRelevance> taskList=new ArrayList<WorkTaskRelevance>();
				WorkTaskRelevance taskRelevance=new WorkTaskRelevance();
				taskRelevance.setTaskId(workTask.getId());
				taskRelevance.setTaskPersonId(workTask.getSponsorId());
				taskRelevance.setTaskType("0");//发起人
				taskRelevance.setIsParent("1");//父任务
				taskList.add(taskRelevance);
				taskRelevance=new WorkTaskRelevance();
				taskRelevance.setTaskId(workTask.getId());
				taskRelevance.setTaskPersonId(workTask.getDirectorId());
				taskRelevance.setTaskType("1");//负责人
				taskRelevance.setIsParent("1");//父任务
				taskList.add(taskRelevance);
				try {
					workTaskRelevanceService.saveList(taskList);
					reVal=1;
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			break;
		case "2"://任务修改(不存在子任务，但可能存在父任务)
			//1.查询父任务
			if (workTask.getParentTaskid().intValue()!=-1){//存在父任务
				List<WorkTask> parentIdList=getParentTask(workTask.getId(),0);
				WorkTaskRelevance wtr=null;
				if (null!=parentIdList && parentIdList.size()>0) {
					//2.查询任务关联表--发起人
					Iterator<WorkTask> itSpon=parentIdList.iterator();
					while (itSpon.hasNext()) {
						WorkTask task=itSpon.next();
						wtr=new WorkTaskRelevance();
						wtr.setTaskId(task.getParentTaskid());
						wtr.setTaskType("0");//发起人
						wtr.setTaskPersonId(workTask.getSponsorId());
						wtr.setDeleteFlag(0);
						try {
							List<WorkTaskRelevance> workTaskRele=workTaskRelevanceService.queryAll(wtr);
							Iterator<WorkTaskRelevance> rele=workTaskRele.iterator();
							while (rele.hasNext()) {
								WorkTaskRelevance taskRele=rele.next();
								if ("1".equals(taskRele.getIsParent())) {//存在父任务
									isSpon=true;
									break;
								}
							}
						} catch (CustomException e) {
							PoException po=new PoException(e);
							po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
							throw po;
						}
					}
					//查询当前任务关联信息
					wtr=new WorkTaskRelevance();
					wtr.setTaskId(workTask.getId());
					wtr.setTaskType("0");//发起人
					wtr.setDeleteFlag(0);
					try {
						List<WorkTaskRelevance> workTaskReleSpon = workTaskRelevanceService.queryAll(wtr);
						Iterator<WorkTaskRelevance> releSpon=workTaskReleSpon.iterator();
						//删除当前关联信息
						while (releSpon.hasNext()) {
							WorkTaskRelevance taskRele=releSpon.next();
							String[] primaryKeys={taskRele.getId()+""};
							taskRele.setPrimaryKeys(primaryKeys);
							workTaskRelevanceService.delete(taskRele, true);
						}
						//重新添加任务关联信息
						String isParent="1";
						if (isSpon==true) {
							isParent="0";
						}
						WorkTaskRelevance taskRele=new WorkTaskRelevance();
						taskRele.setTaskId(workTask.getId());
						taskRele.setTaskPersonId(workTask.getSponsorId());
						taskRele.setTaskType("0");//发起人
						taskRele.setIsParent(isParent);
						taskRele.setDeleteFlag(0);
						workTaskRelevanceService.save(taskRele);
						reVal=1;
					} catch (CustomException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
						throw po;
					}
					//3.查询任务关联表--负责人
					Iterator<WorkTask> itDire=parentIdList.iterator();
					while (itDire.hasNext()) {
						WorkTask task=itDire.next();
						wtr=new WorkTaskRelevance();
						wtr.setTaskId(task.getParentTaskid());
						wtr.setTaskType("1");//负责人
						wtr.setTaskPersonId(workTask.getDirectorId());
						wtr.setDeleteFlag(0);
						try {
							List<WorkTaskRelevance> worktr=workTaskRelevanceService.queryAll(wtr);
							Iterator<WorkTaskRelevance> rele=worktr.iterator();
							while (rele.hasNext()) {
								WorkTaskRelevance taskRelev=rele.next();
								if ("1".equals(taskRelev.getIsParent())) {//存在父任务
									isDire=true;
									break;
								}
							}
						} catch (CustomException e) {
							PoException po=new PoException(e);
							po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
							throw po;
						}
					}
					//查询当前任务关联信息
					wtr=new WorkTaskRelevance();
					wtr.setTaskId(workTask.getId());
					wtr.setTaskType("1");//负责人
					wtr.setDeleteFlag(0);
					try {
						List<WorkTaskRelevance> workTaskRele=workTaskRelevanceService.queryAll(wtr);
						Iterator<WorkTaskRelevance> releSpons=workTaskRele.iterator();
						//删除当前关联信息
						while (releSpons.hasNext()) {
							WorkTaskRelevance taskReles=releSpons.next();
							String[] primaryKeys={taskReles.getId()+""};
							taskReles.setPrimaryKeys(primaryKeys);
							workTaskRelevanceService.delete(taskReles, true);
						}
						//重新添加任务关联信息
						String isParents="1";
						if (isDire==true) {
							isParents="0";
						}
						WorkTaskRelevance taskRel=new WorkTaskRelevance();
						taskRel.setTaskId(workTask.getId());
						taskRel.setTaskPersonId(workTask.getDirectorId());
						taskRel.setTaskType("1");//负责人
						taskRel.setIsParent(isParents);
						taskRel.setDeleteFlag(0);
						workTaskRelevanceService.save(taskRel);
						reVal=1;
					} catch (CustomException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
						throw po;
					}
				}
			}else {//不存在父任务
				List<WorkTaskRelevance> workTaskRele=null;
				WorkTaskRelevance wtr=new WorkTaskRelevance();
				wtr.setTaskId(workTask.getId());
				wtr.setDeleteFlag(0);
				try {
					workTaskRele=workTaskRelevanceService.queryAll(wtr);
					Iterator<WorkTaskRelevance> it=workTaskRele.iterator();
					while (it.hasNext()) {
						WorkTaskRelevance taskRe=it.next();
						if ("0".equals(taskRe.getTaskType())) {//发起人
							taskRe.setTaskPersonId(workTask.getSponsorId());
						}
						if ("1".equals(taskRe.getTaskType())) {//负责人
							taskRe.setTaskPersonId(workTask.getDirectorId());
						}
						workTaskRelevanceService.update(taskRe);
						reVal=1;
					}
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
			break;
		case "3"://下发任务(一定存在父任务，可能存在子任务，但只考虑父任务)
			//1.查询父任务
			List<WorkTask> parentIdList=getParentTask(workTask.getId(),0);
			WorkTaskRelevance wtr=null;
			if (null!=parentIdList && parentIdList.size()>0) {
				//2.查询任务关联表--发起人
				Iterator<WorkTask> itSpon=parentIdList.iterator();
				while (itSpon.hasNext()) {
					WorkTask task=itSpon.next();
					wtr=new WorkTaskRelevance();
					wtr.setTaskId(task.getParentTaskid());
					wtr.setTaskType("0");//发起人
					wtr.setTaskPersonId(workTask.getSponsorId());
					wtr.setDeleteFlag(0);
					try {
						List<WorkTaskRelevance> workTaskRele=workTaskRelevanceService.queryAll(wtr);
						Iterator<WorkTaskRelevance> rele=workTaskRele.iterator();
						while (rele.hasNext()) {
							WorkTaskRelevance taskRele=rele.next();
							if ("1".equals(taskRele.getIsParent())) {//存在父任务
								isSpon=true;
								break;
							}
						}
					} catch (CustomException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
						throw po;
					}
				}
				//3.查询任务关联表--负责人
				Iterator<WorkTask> itDire=parentIdList.iterator();
				while (itDire.hasNext()) {
					WorkTask task=itDire.next();
					wtr=new WorkTaskRelevance();
					wtr.setTaskId(task.getParentTaskid());
					wtr.setTaskType("1");//负责人
					wtr.setTaskPersonId(workTask.getDirectorId());
					wtr.setDeleteFlag(0);
					try {
						List<WorkTaskRelevance> workTaskRele=workTaskRelevanceService.queryAll(wtr);
						Iterator<WorkTaskRelevance> rele=workTaskRele.iterator();
						while (rele.hasNext()) {
							WorkTaskRelevance taskRelev=rele.next();
							if ("1".equals(taskRelev.getIsParent())) {//存在父任务
								isDire=true;
								break;
							}
						}
					} catch (CustomException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
						throw po;
					}
				}
				//4.对存在父任务进行处理
				List<WorkTaskRelevance> taskReleList=new ArrayList<WorkTaskRelevance>();
				WorkTaskRelevance taskRe=new WorkTaskRelevance();
				String isSponParent="1";
				String isDireParent="1";
				if (isSpon==true) {
					isSponParent="0";
				}
				taskRe.setTaskId(workTask.getId());
				taskRe.setTaskPersonId(workTask.getSponsorId());
				taskRe.setTaskType("0");//发起人
				taskRe.setIsParent(isSponParent);//是否为父任务
				taskReleList.add(taskRe);
				if (isDire==true) {
					isDireParent="0";
				}
				taskRe=new WorkTaskRelevance();
				taskRe.setTaskId(workTask.getId());
				taskRe.setTaskPersonId(workTask.getDirectorId());
				taskRe.setTaskType("1");//负责人
				taskRe.setIsParent(isDireParent);//非父任务
				taskReleList.add(taskRe);
				try {
					workTaskRelevanceService.saveList(taskReleList);
					reVal=1;
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
					throw po;
				}
			}
			break;
		case "4"://删除(不存在子任务)
			WorkTaskRelevance wtRelevance=new WorkTaskRelevance();
			wtRelevance.setTaskId(workTask.getId());
			wtRelevance.setDeleteFlag(0);
			try {
				List<WorkTaskRelevance> taskReleList=workTaskRelevanceService.queryAll(wtRelevance);
				Iterator<WorkTaskRelevance> it=taskReleList.iterator();
				StringBuffer priKey=new StringBuffer();
				while (it.hasNext()) {
					WorkTaskRelevance taskR=it.next();
					priKey.append(taskR.getId()+",");
				}
				if (null!=priKey && priKey.length()>0){
					WorkTaskRelevance wtre=new WorkTaskRelevance();
					String priKeys=priKey.toString();
					String[] primaryKey=priKeys.split(",");
					wtre.setPrimaryKeys(primaryKey);
					workTaskRelevanceService.delete(wtre, true);
					reVal=1;
				}
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
				throw po;
			}
			break;
		default://预留
			reVal=1;
			break;
		}
		return reVal;
	}
	/**
	 * @description 取消任务中汇报提醒
	 * @param WorkTask workTask
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-07-21
	 */
	public Integer cancelTaskRemind(WorkTask workTask) throws PoException{
		Integer reVal=0;
		if (null==workTask || null==workTask.getId()) {
			return reVal;
		}
		StringBuffer remindInfo=new StringBuffer();
		//汇报时限设置(内部邮件：默认)
		if (null!=workTask.getReportDay() && workTask.getReportDay().intValue() >0) {
			remindInfo.append("{");
			remindInfo.append("\"content\":\"\",");
			remindInfo.append("\"remindMode\":0,");//不提醒
			remindInfo.append("\"receiveId\":\"\",");
			remindInfo.append("\"remindType\":4,");
			remindInfo.append("\"cycle\":1,");
			remindInfo.append("\"isActive\":\"0\",");
			remindInfo.append("\"tableName\":\"1\",");
			remindInfo.append("\"viewCycle\":\"\",");
			remindInfo.append("\"viewStartTime\":\"\",");
			remindInfo.append("\"viewEndTime\":\"\",");
			remindInfo.append("\"startForwardTime\":0,");
			remindInfo.append("\"endForwardTime\":0,");
			remindInfo.append("\"endForwardTimeValue\":0,");
			remindInfo.append("\"startForwardTimeValue\":0,");
			remindInfo.append("\"title\":\"\",");
			remindInfo.append("\"intervalHour\":0,");
			remindInfo.append("\"intervalMinute\":0,");
			remindInfo.append("\"remindNum\":0,");
			remindInfo.append("\"cronExpression\":\"\",");
			remindInfo.append("\"remindInterval\":0,");
			remindInfo.append("\"viewStartTimeStr\":\"\",");
			remindInfo.append("\"viewEndTimeStr\":\"\"");
			remindInfo.append("}");
			workTask.setRemind(remindInfo.toString());
			try {
				reVal=remindService.save(workTask.getRemind(), workTask.getId(),Constants.TABLE_NAME);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
				throw po;
			}
		}
		//汇报提醒设置(短信提醒)
		if (null!=workTask.getReportType() && "1".equals(workTask.getReportType())) {
			remindInfo=new StringBuffer();
			remindInfo.append("{");
			remindInfo.append("\"content\":\"\",");
			remindInfo.append("\"remindMode\":0,");//不提醒
			remindInfo.append("\"receiveId\":\"\",");
			remindInfo.append("\"remindType\":4,");
			remindInfo.append("\"cycle\":1,");
			remindInfo.append("\"isActive\":\"0\",");
			remindInfo.append("\"tableName\":\"1\",");
			remindInfo.append("\"viewCycle\":\"\",");
			remindInfo.append("\"viewStartTime\":\"\",");
			remindInfo.append("\"viewEndTime\":\"\",");
			remindInfo.append("\"startForwardTime\":0,");
			remindInfo.append("\"endForwardTime\":0,");
			remindInfo.append("\"endForwardTimeValue\":0,");
			remindInfo.append("\"startForwardTimeValue\":0,");
			remindInfo.append("\"title\":\"\",");
			remindInfo.append("\"intervalHour\":0,");
			remindInfo.append("\"intervalMinute\":0,");
			remindInfo.append("\"remindNum\":0,");
			remindInfo.append("\"cronExpression\":\"\",");
			remindInfo.append("\"remindInterval\":0,");
			remindInfo.append("\"viewStartTimeStr\":\"\",");
			remindInfo.append("\"viewEndTimeStr\":\"\"");
			remindInfo.append("}");
			workTask.setRemindTemp(remindInfo.toString());
			try {
				reVal=remindService.save(workTask.getRemindTemp(), workTask.getId(),Constants.TABLE_NAME_TEMP);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
				throw po;
			}
		}
		return reVal;
	}
	/**
	 * @description 操作校验
	 * @param Long taskId,String operateType
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-07-28
	 */
	public Integer checkOperation(Long taskId,String operateType){
		Integer reVal=0;
		if (taskId.longValue()==0 || null==operateType || "".equals(operateType)) {
			return reVal;
		}
		//1.根据任务ID查询当前任务
		WorkTask taskTemp=null;
		WorkTask  workTask=new WorkTask();
		workTask.setId(taskId);
		workTask.setDeleteFlag(0);
		String[] infoStatus={"0","1","2","3","4","6","7","8"};
		workTask.setInfoStatus(infoStatus);
		List<WorkTask> taskList=worktaskDao.queryAllForUnion(workTask);
		if (null!=taskList && taskList.size()==1) {
			taskTemp=taskList.get(0);
		}
		//2.判断操作类型是否与当前任务状态一致
		if(null!=taskTemp){
			switch (operateType) {
			case "0"://查看
				reVal=1;
				break;
			case "1"://修改
				Integer tu=compareOperator(taskTemp.getSponsorId());//当前登录人为发布人
				if (tu.intValue()==1 && ("0".equals(taskTemp.getStatus()) || "8".equals(taskTemp.getStatus()))) {//当前登录人为发布人并且任务状态为未接收或暂存
					reVal=1;
				}
				break;
			case "2"://删除
				Integer td=compareOperator(taskTemp.getSponsorId());//当前登录人为发布人
				if (td.intValue()==1 && ("0".equals(taskTemp.getStatus()) || "8".equals(taskTemp.getStatus()))) {//当前登录人为发布人并且任务状态为未接收或暂存
					reVal=1;
				}
				break;
			case "3"://接收
				Integer tr=compareOperator(taskTemp.getDirectorId());//当前登录人为负责人
				if (tr.intValue()==1 && "0".equals(taskTemp.getStatus())) {//当前登录人为负责人并且任务状态为未接收
					reVal=1;
				}
				break;
			case "4"://不接收
				Integer trv=compareOperator(taskTemp.getDirectorId());//当前登录人为负责人
				if (trv.intValue()==1 && "0".equals(taskTemp.getStatus())) {//当前登录人为负责人并且任务状态为未接收
					reVal=1;
				}
				break;
			case "5"://汇报
				Integer th=compareOperator(taskTemp.getDirectorId());//当前登录人为负责人
				if (th.intValue()==1 && ("1".equals(taskTemp.getStatus()) 
						|| "2".equals(taskTemp.getStatus()) || "6".equals(taskTemp.getStatus()))) {//当前登录人为负责人并且任务状态为进行中
					reVal=1;
				}
				break;
			case "6"://催办
				Integer tc=compareOperator(taskTemp.getSponsorId());//当前登录人为发布人
				if (tc.intValue()==1 && ("1".equals(taskTemp.getStatus()) 
						|| "2".equals(taskTemp.getStatus()) || "6".equals(taskTemp.getStatus()))) {//当前登录人为发布人并且任务状态为进行中
					reVal=1;
				}
				break;
			case "7"://延期审批通过
				Integer typ=compareOperator(taskTemp.getSponsorId());//当前登录人为发布人
				if (typ.intValue()==1 && "0".equals(taskTemp.getDelayStatus()) && ("1".equals(taskTemp.getStatus()) 
						|| "2".equals(taskTemp.getStatus()) || "6".equals(taskTemp.getStatus()))) {//当前登录人为负责人并且任务状态为进行中、延期、超期
					reVal=1;
				}
				break;
			case "8"://取消
				Integer tq=compareOperator(taskTemp.getSponsorId());//当前登录人为发布人
				if (tq.intValue()==1 && ("1".equals(taskTemp.getStatus()) 
						|| "2".equals(taskTemp.getStatus()) || "6".equals(taskTemp.getStatus()))) {//当前登录人为发布人并且任务状态为进行中
					reVal=1;
				}
				break;
			case "12"://延期申请
				Integer ty=compareOperator(taskTemp.getDirectorId());//当前登录人为负责人
				if (ty.intValue()==1 && ("1".equals(taskTemp.getStatus())
						|| "2".equals(taskTemp.getStatus()) || "6".equals(taskTemp.getStatus()))) {//当前登录人为负责人并且任务状态为进行中、延期、超期
					reVal=1;
				}
				break;
			case "13"://延期审批未通过
				Integer tyu=compareOperator(taskTemp.getSponsorId());//当前登录人为发布人
				if (tyu.intValue()==1 && "0".equals(taskTemp.getDelayStatus()) && ("1".equals(taskTemp.getStatus()) 
						|| "2".equals(taskTemp.getStatus()) || "6".equals(taskTemp.getStatus()))) {//当前登录人为负责人并且任务状态为进行中、延期、超期
					reVal=1;
				}
				break;
			case "14"://延期取消
				Integer tyc=compareOperator(taskTemp.getDirectorId());//当前登录人为负责人
				if (tyc.intValue()==1 && "0".equals(taskTemp.getDelayStatus()) && ("1".equals(taskTemp.getStatus()) 
						|| "2".equals(taskTemp.getStatus()) || "6".equals(taskTemp.getStatus()))) {//当前登录人为负责人并且任务状态为进行中、延期、超期
					reVal=1;
				}
				break;
			case "15"://下发任务
				Integer txf=compareOperator(taskTemp.getDirectorId());//当前登录人为负责人
				if (txf.intValue()==1 && ("1".equals(taskTemp.getStatus()) 
						|| "2".equals(taskTemp.getStatus()) || "6".equals(taskTemp.getStatus()))) {//当前登录人为负责人并且任务状态为进行中
					reVal=1;
				}
				break;
			default://其他
				reVal=0;
				break;
			}
		}
		return reVal;
	}
	/**
	 * @description 操作人员校验
	 * @param Long operatorId
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-07-28
	 */
	private Integer compareOperator(Long operatorId){
		Integer reVal=0;
		if(operatorId.longValue()==0){
			return reVal;
		}
		User userInfo = SystemSecurityUtils.getUser();//取得当前登录用户信息
		if (userInfo.getId().longValue()==operatorId.longValue()) {
			reVal=1;
		}
		return reVal;
	}
	/**
	 * @description 查询待办任务(对外)
	 * @param WorkTask  workTask 实体类
	 * @return List<WorkTask> 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-10-20
	 */
	public List<WorkTask> queryAbeyanceTaskList(WorkTask workTask) throws PoException{
		if (null==workTask || null==workTask.getDirectorId() 
				|| null==workTask.getStartDate() || "".equals(workTask.getStartDate())) {
			return null;
		}
		Long directorId=workTask.getDirectorId();
		String startDate=workTask.getStartDate();
		WorkTask workTaskTempTask=new WorkTask();
		workTaskTempTask.setDirectorId(directorId);
		if (null !=startDate && !"".equals(startDate)) {
			startDate+=" 00:00:00";
		}
		workTaskTempTask.setStartDate(startDate);
		List<WorkTask> list=worktaskDao.queryAbeyanceTaskList(workTaskTempTask);
		String prticipants="";
		List taskList=null;
		if(null!=list && list.size()>0){
			taskList=new ArrayList();
			for (int i = 0,j=list.size(); i <j; i++) {
				WorkTask task=list.get(i); 
				try {
					prticipants=getNames("multiple",task.getPrticipantsId());
				} catch (PoException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
					throw po;
				}
				task.setPrticipants(prticipants);
				taskList.add(task);
			}
		}
		return taskList;
	}
}