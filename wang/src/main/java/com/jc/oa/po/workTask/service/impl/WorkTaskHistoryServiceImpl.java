package com.jc.oa.po.workTask.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.oa.ic.IcException;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.workTask.dao.IWorkTaskHistoryDao;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;
import com.jc.oa.po.workTask.domain.WorkTaskfinish;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.workTask.service.IWorkTaskHistoryService;
import com.jc.oa.po.workTask.service.IWorkTaskService;
import com.jc.oa.po.workTask.service.IWorkTaskfinishService;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.service.impl.UserServiceImpl;

/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李洪宇
 * @version  2014-04-23
 */
@Service
public class WorkTaskHistoryServiceImpl extends BaseServiceImpl<WorkTaskHistory> implements IWorkTaskHistoryService{

	private IWorkTaskHistoryDao workTaskHistoryDao;
	@Autowired
	private IWorkTaskService workTaskService;
	@Autowired
	private IWorkTaskfinishService workTaskfinishService;
	@Autowired
	private IInteractFacadeService interactFacadeService;
	
	@Autowired
	private IUserService userService;
	
	public WorkTaskHistoryServiceImpl(){}
	
	@Autowired
	public WorkTaskHistoryServiceImpl(IWorkTaskHistoryDao workTaskHistoryDao){
		super(workTaskHistoryDao);
		this.workTaskHistoryDao = workTaskHistoryDao;
	}
	/**
	 * @description 对于任务事件操作而对应保存任务信息
	 * @param WorkTaskHistory taskHistory 实体类
	 * @return Integer
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-07
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer saveWorkTask(WorkTaskHistory taskHistory) throws PoException{
		Integer returnVal=0;
		if(null !=taskHistory && null !=taskHistory.getTaskId()){
			try {
				if (null!=taskHistory.getReportProc() && taskHistory.getReportProc()==100) {
					//汇报任务进度为100%时，向事件中添加一条汇报进度为100%的记录 start
					WorkTaskHistory wth=new WorkTaskHistory();
					BeanUtils.copyProperties(taskHistory, wth);
					returnVal=save(wth);
					//汇报任务进度为100%时，向事件中添加一条汇报进度为100%的记录  end
					taskHistory.setTaskEventType("9");//进度为100%时状态设为完成
				}
				returnVal=save(taskHistory);
			} catch (CustomException e1) {
				PoException po=new PoException(e1);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
				throw po;
			}
			if (returnVal==1) {
				//取得任务基本信息
				String status="";//任务状态
				Integer deleteSta=0;//删除状态
				WorkTask workTask=new WorkTask();
				workTask.setId(taskHistory.getTaskId());
				WorkTask workTaskTemp=null;
				try {
					workTaskTemp = workTaskService.get(workTask);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
					throw po;
				}
				if (null !=workTaskTemp) {
					String statusTemp=workTaskTemp.getStatus();
					status=statusTemp;
					if ("0".equals(statusTemp)) {//未接收
						switch (taskHistory.getTaskEventType()) {
						case "2"://删除
							deleteSta=1;
							workTaskService.cancelTaskRemind(workTaskTemp);//任务删除时，关闭关联的汇报提醒设置
							break;
						case "3"://接受
							status="1";//进行中
							if (null!=taskHistory.getActStartTime()) {
								workTaskTemp.setActStartTime(taskHistory.getActStartTime());
							}else {
								workTaskTemp.setActStartTime(new Date());
							}
							break;
						case "4"://不接受
							status="7";//拒接收
							workTaskService.cancelTaskRemind(workTaskTemp);//任务拒接收时，关闭关联的汇报提醒设置
							break;
						}
					}
					if ("1".equals(statusTemp)) {//进行中
						switch (taskHistory.getTaskEventType()) {
						case "5"://汇报
							workTaskTemp.setTaskProc(taskHistory.getReportProc());
							if (null!=taskHistory.getReportProc() && taskHistory.getReportProc()==100) {
								status="3";//进度为100%时状态设为完成
							}
							break;
						case "7"://延期审批通过
							status="2";//延期
							delayWorkTask(workTaskTemp.getId(),taskHistory.getDelayEnddate());
							try {
								returnVal=addDelayInfo(taskHistory,"审批通过");
							} catch (CustomException e) {
								PoException po=new PoException(e);
								po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
								throw po;
							}
							break;
						case "8"://取消
							status="4";//取消
							break;
						case "9"://完成
							workTaskTemp.setTaskProc(taskHistory.getReportProc());
							if (null!=taskHistory.getReportProc() && taskHistory.getReportProc()==100) {
								status="3";//进度为100%时状态设为完成
							}
							break;
						case "12"://延期申请
							workTaskTemp.setDelayStatus("0");//延期申请
							break;
						case "13"://延期审批未通过
							workTaskTemp.setDelayStatus("2");//审批未通过
							try {
								returnVal=addDelayInfo(taskHistory,"审批未通过");
							} catch (CustomException e) {
								PoException po=new PoException(e);
								po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
								throw po;
							}
							break;
						case "14"://延期取消
							workTaskTemp.setDelayStatus("3");//取消申请
							try {
								returnVal=addDelayInfo(taskHistory,"取消");
							} catch (CustomException e) {
								PoException po=new PoException(e);
								po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
								throw po;
							}
							break;
						}
					}
					if ("2".equals(statusTemp)) {//延期
						switch (taskHistory.getTaskEventType()) {
							case "5"://汇报
								workTaskTemp.setTaskProc(taskHistory.getReportProc());
								if (null!=taskHistory.getReportProc() && taskHistory.getReportProc()==100) {
									status="3";//进度为100%时状态设为完成
								}
								break;
							case "7"://延期审批通过
								delayWorkTask(workTaskTemp.getId(),taskHistory.getDelayEnddate());
								try {
									returnVal=addDelayInfo(taskHistory,"审批通过");
								} catch (CustomException e) {
									PoException po=new PoException(e);
									po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
									throw po;
								}
								break;
							case "8"://取消
								status="4";//取消
								break;
							case "9"://完成
								workTaskTemp.setTaskProc(taskHistory.getReportProc());
								if (null!=taskHistory.getReportProc() && taskHistory.getReportProc()==100) {
									status="3";//进度为100%时状态设为完成
								}
								break;
							case "12"://延期申请
								workTaskTemp.setDelayStatus("0");//延期申请
								break;
							case "13"://延期审批未通过
								workTaskTemp.setDelayStatus("2");//审批未通过
								try {
									returnVal=addDelayInfo(taskHistory,"审批未通过");
								} catch (CustomException e) {
									PoException po=new PoException(e);
									po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
									throw po;
								}
								break;
							case "14"://延期取消
								workTaskTemp.setDelayStatus("3");//取消申请
								try {
									returnVal=addDelayInfo(taskHistory,"取消");
								} catch (CustomException e) {
									PoException po=new PoException(e);
									po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
									throw po;
								}
								break;
						}
					}
					if ("6".equals(statusTemp)) {//超期
						switch (taskHistory.getTaskEventType()) {
							case "5"://汇报
								workTaskTemp.setTaskProc(taskHistory.getReportProc());
								if (null!=taskHistory.getReportProc() && taskHistory.getReportProc()==100) {
									status="3";//进度为100%时状态设为完成
								}
								break;
							case "7"://延期审批通过
								delayWorkTask(workTaskTemp.getId(),taskHistory.getDelayEnddate());
								try {
									returnVal=addDelayInfo(taskHistory,"审批通过");
									status="2";//延期
								} catch (CustomException e) {
									PoException po=new PoException(e);
									po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
									throw po;
								}
								break;
							case "8"://取消
								status="4";//取消
								break;
							case "9"://完成
								workTaskTemp.setTaskProc(taskHistory.getReportProc());
								if (null!=taskHistory.getReportProc() && taskHistory.getReportProc()==100) {
									status="3";//进度为100%时状态设为完成
								}
								break;
							case "12"://延期申请
								workTaskTemp.setDelayStatus("0");//延期申请
								break;
							case "13"://延期审批未通过
								workTaskTemp.setDelayStatus("2");//审批未通过
								try {
									returnVal=addDelayInfo(taskHistory,"审批未通过");
								} catch (CustomException e) {
									PoException po=new PoException(e);
									po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
									throw po;
								}
								break;
							case "14"://延期取消
								workTaskTemp.setDelayStatus("3");//取消申请
								try {
									returnVal=addDelayInfo(taskHistory,"取消");
								} catch (CustomException e) {
									PoException po=new PoException(e);
									po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
									throw po;
								}
								break;
						}
					}
					workTaskTemp.setStatus(status);
					workTaskTemp.setDeleteFlag(deleteSta);
					try {
						returnVal=workTaskService.update(workTaskTemp,1);//修改(保存模板)，不添加事件
					} catch (PoException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
						throw po;
					}
					if ("3".equals(status)) {//完成任务，将任务复制到任务完成表
						returnVal=workTaskFinish(workTaskTemp);
					}
					if ("4".equals(status)) {//取消
						//1、对本条任务进行取消操作，及关闭关联汇报提醒
						WorkTask task=new WorkTask();
						task.setId(taskHistory.getTaskId());
						task.setDeleteFlag(0);
						try {
							task=workTaskService.get(task);
							if (null!=task) {
								task.setStatus(status);
								returnVal=workTaskService.update(task);
							}
						} catch (CustomException e1) {
							PoException po=new PoException(e1);
							po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
							throw po;
						}
						workTaskService.cancelTaskRemind(task);//任务取消时，关闭关联的汇报提醒设置
						//2、当父任务被取消后，如存在子任务，也同时被取消。
						List<WorkTask> taskListTemp=new ArrayList<WorkTask>();
						List<WorkTask> taskList=workTaskService.getSubTaskList(taskHistory.getTaskId(), 0);
						WorkTask workTemp=null;
						Iterator<WorkTask> iterator=taskList.iterator();
						while (iterator.hasNext()) {
							workTemp=iterator.next();
							try {
								workTemp=workTaskService.get(workTemp);
								workTemp.setStatus(status);
								taskListTemp.add(workTemp);
								workTaskService.cancelTaskRemind(workTemp);//任务取消时，关闭关联的汇报提醒设置
							} catch (CustomException e) {
								PoException po=new PoException(e);
								po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
								throw po;
							}
						}
						if (null!=taskListTemp && taskListTemp.size()>0) {
							returnVal=workTaskService.batchUpdate(taskListTemp);
						}
					}
					boolean isSend=false;
					if ("6".equals(taskHistory.getTaskEventType())) {//催办-发送邮件或短信
						workTaskTemp.setContent(taskHistory.getDelayReason()==null?"":taskHistory.getDelayReason());
						workTaskTemp.setRemindType(taskHistory.getRemindType()==null?"":taskHistory.getRemindType());
						workTaskTemp.setTaskEventType("6");
						isSend=workTaskService.sendMailOrSms(workTaskTemp);
						if (isSend) {
							returnVal=1;
						}else {
							returnVal=0;
						}
				    }
					if ("8".equals(taskHistory.getTaskEventType())) {//取消-发送邮件或短信
						workTaskTemp.setContent(taskHistory.getDelayReason()==null?"":taskHistory.getDelayReason());
						workTaskTemp.setRemindType(taskHistory.getRemindType()==null?"":taskHistory.getRemindType());
						workTaskTemp.setTaskEventType("8");
						isSend=workTaskService.sendMailOrSms(workTaskTemp);
						if (isSend) {
							returnVal=1;
						}else {
							returnVal=0;
						}
				    }
				}else {
					returnVal=0;
					try {
						delete(taskHistory);
					} catch (CustomException e) {
						PoException po=new PoException(e);
						po.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
						throw po;
					}
				}
			}
		}
		return returnVal;
	}
	/**
	 * @description 查询任务事件总数
	 * @param WorkTaskHistory taskHistory 实体类
	 * @return Integer
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-09
	 */
	public Integer getWorkTaskHisTotal(WorkTaskHistory taskHistory){
		return workTaskHistoryDao.getWorkTaskHisTotal(taskHistory);
	}
	/**
	 * @description 查询任务事件ID
	 * @param WorkTaskHistory taskHistory 实体类
	 * @return List<WorkTaskHistory>
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-20
	 */
	public List<WorkTaskHistory> getWorkTaskHisTaskId(WorkTaskHistory taskHistory){
		return workTaskHistoryDao.getWorkTaskHisTaskId(taskHistory);
	}
	/**
	 * @description 根据任务ID及任务事件类型查询任务事件
	 * @param WorkTaskHistory taskHistory 实体类
	 * @return PageManager
	 * @throws Exception
	 * @author	李洪宇
	 * @version  2014-05-13
	 */
	public PageManager getTaskHistListByTaskId(WorkTaskHistory taskHistory,final PageManager page){
		PageManager pageManager=null;
		if (null!=taskHistory && null!=taskHistory.getTaskId()) {
//			taskHistory.setDeleteFlag(0);
			taskHistory.setDeleteFlag(null);
			pageManager=workTaskHistoryDao.queryByPage(taskHistory, page, 
					Constants.GET_TASK_HIS_BY_ID_COUNT, Constants.GET_TASK_HIS_LIST_BY_ID);
		}
		return pageManager;
	}
	/**
	 * @description 用于处理工作任务在完成时的业务操作
	 * @param WorkTask workTaskTemp
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-06-18
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer workTaskFinish(WorkTask workTaskTemp) throws PoException{
		Integer returnVal=0;
		if (null!=workTaskTemp) {
			WorkTaskfinish workTaskfinish=new WorkTaskfinish();
			BeanUtils.copyProperties(workTaskTemp, workTaskfinish);//任务复制
			workTaskfinish.setId(null);
			workTaskfinish.setTaskId(workTaskTemp.getId());
			workTaskfinish.setDeleteFlag(0);
			workTaskfinish.setTaskProc(100);//为已完成的任务进度设置为100%
			workTaskfinish.setStatus("3");//置为完成
			workTaskfinish.setActEndTime(new Date());//将当前时间设为实际完成时间
			//考虑创建与修改相关字段
			workTaskfinish.setCreateDate(new Date());
			workTaskfinish.setCreateUser(workTaskTemp.getCreateUser());
			workTaskfinish.setCreateUserDept(workTaskTemp.getCreateUserDept());
			workTaskfinish.setCreateUserOrg(workTaskTemp.getCreateUserOrg());
			try {
				returnVal=workTaskfinishService.save(workTaskfinish);
				//复制完成后，将任务表中已完成的任务删除 start  注：待以后存在子任务时，要考虑父任务与子任务一同删除
				String primarykey=String.valueOf(workTaskTemp.getId());
				String[] primaryKeys=primarykey.split(",");
				workTaskTemp.setPrimaryKeys(primaryKeys);
				returnVal=workTaskService.delete(workTaskTemp,false);//物理删除
				Integer calVal=workTaskService.cancelTaskRemind(workTaskTemp);//任务完成时，关闭关联的汇报提醒设置
				//复制完成后，将任务表中已完成的任务删除 end
			} catch (CustomException e){
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
				throw po;
			}
			if (returnVal==1 && workTaskTemp.getParentTaskid().intValue()!=-1) {//判断是否存在父任务
				List<WorkTask> workList=null;
				WorkTask wTask=new WorkTask();
				wTask.setParentTaskid(workTaskTemp.getParentTaskid());
				String[] infoStatus={"0","1","2","6"};
				wTask.setInfoStatus(infoStatus);
				wTask.setDeleteFlag(0);
				try {
					workList=workTaskService.queryAll(wTask);
					if (null!=workList) {
						if(workList.size()==0){//如不存在“兄弟”任务，将父任务置为完成
							WorkTask  wt=new WorkTask();
							wt.setId(workTaskTemp.getParentTaskid());
							wt.setDeleteFlag(0);
							workTaskFinish(workTaskService.get(wt));
						}
					}
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
					throw po;
				}
			}
		}	
		return returnVal;
	}
	/**
	 * @description 对延期事件处理
	 * @param WorkTask workTaskTemp
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-06-26
	 */
	public Integer addDelayInfo(WorkTaskHistory taskHistory,String tipInfo) throws PoException{
		Integer returnVal=0;
		if (null==taskHistory || null==taskHistory.getTaskId()) {
			return returnVal;
		}
		if (null==tipInfo) {
			tipInfo="";
		}
		try {
			//查询申请的延期信息
			WorkTaskHistory workTaskHistory=new WorkTaskHistory();
			workTaskHistory.setTaskId(taskHistory.getTaskId());
			workTaskHistory.setTaskEventType("12");
			workTaskHistory.setDeleteFlag(0);
			workTaskHistory=get(workTaskHistory);
			if (null!=workTaskHistory) {
				WorkTaskHistory wh=new WorkTaskHistory();
				wh.setId(taskHistory.getId());
				wh.setDeleteFlag(0);
				wh=get(taskHistory);
				User user=userService.getUser(workTaskHistory.getCreateUser());
				wh.setDelayReason("对操作人： "+user.getDisplayName()+" ,操作时间：“"+DateUtils.formatDate(workTaskHistory.getCreateDate(),"yyyy-MM-dd HH:mm:ss")+"” ,任务名称：  “"+workTaskHistory.getTaskEventTitle()+"” 的延期申请进行"+tipInfo+"操作。");
				returnVal=update(wh);
				//将延期申请逻缉删除
				workTaskHistory.setDeleteFlag(1);
				returnVal=update(workTaskHistory);
			}			
		} catch (CustomException e) {
			PoException po=new PoException();
			po.setLogMsg("");
			throw po;
		}
		return returnVal;
	}
	/**
	 * @description 任务延期
	 * @param Long taskId,Date dalayDate
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-06-26
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delayWorkTask(Long taskId,Date dalayDate) throws PoException{
		Integer returnVal=0;
		WorkTask subTask=null;
		WorkTask parTask=null;
		Date dalayTime=DateUtils.fillTime(dalayDate);
		//1 查询当前任务
		try {
			WorkTask workTask=new WorkTask();
			workTask.setId(taskId);
			workTask.setDeleteFlag(0);
			subTask=workTaskService.get(workTask);
		} catch (CustomException e) {
			PoException po=new PoException(e);
			po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
			throw po;
		}
		if (null!=subTask) {//2对当前任务进行延期操作
			subTask.setEndTime(dalayTime);
			subTask.setDelayStatus("1");//审批通过
			if ("1".equals(subTask.getStatus())) {
				subTask.setStatus("2");//延期
			}
			try {
				returnVal=workTaskService.update(subTask);
			} catch (CustomException e) {
				PoException po=new PoException(e);
				po.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
				throw po;
			}
			if (subTask.getParentTaskid().intValue() !=-1) {//3判断当前任务是否存在父任务
				WorkTask work=new WorkTask();
				work.setId(subTask.getParentTaskid());
				work.setDeleteFlag(0);
				try {
					parTask=workTaskService.get(work);
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg(MessageUtils.getMessage("JC_SYS_007"));
					throw po;
				}
				if (null!=parTask) {//4 对比当前任务延期时间与父任务的结束时间
					Date parEndTime=parTask.getEndTime();
					long reVal=DateUtils.subtractionDays(dalayTime,parEndTime);
					if (reVal >0) {//当前任务延期时间大于父任务的结束时间,则同时对父任务进行延期操作
						parTask.setEndTime(dalayTime);
						parTask.setDelayStatus("1");//审批通过
						if ("1".equals(parTask.getStatus())) {
							parTask.setStatus("2");//延期
						}
						try {
							returnVal=workTaskService.update(parTask);
						} catch (CustomException e) {
							PoException po=new PoException(e);
							po.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
							throw po;
						}
						delayWorkTask(parTask.getId(),dalayTime);
					}
				}
			}
		}
		return returnVal;
	}
}