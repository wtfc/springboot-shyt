package com.jc.oa.po.worklog.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.common.domain.ControlSide;
import com.jc.oa.common.service.IControlSideService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.anno.service.IAnnoService;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.diary.service.IDiaryService;
import com.jc.oa.po.readingstatus.domain.ReadingStatus;
import com.jc.oa.po.readingstatus.service.IReadingStatusService;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;
import com.jc.oa.po.workTask.service.IWorkTaskHistoryService;
import com.jc.oa.po.workTask.service.IWorkTaskService;
import com.jc.oa.po.worklog.dao.IWorklogContentDao;
import com.jc.oa.po.worklog.dao.IWorklogDao;
import com.jc.oa.po.worklog.dao.impl.WorklogDaoImpl;
import com.jc.oa.po.worklog.domain.Worklog;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.oa.po.worklog.service.IWorklogService;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.UserUtils;

/**
 * @title 个人办公
 * @description  业务服务类
 * @version  2014-05-04
 */
@Service
public class WorklogServiceImpl extends BaseServiceImpl<Worklog> implements IWorklogService{

	private IWorklogDao worklogDao;
	@Autowired
	private IWorklogContentDao worklogContentDao;
	@Autowired
	private IDiaryService diaryService;
	@Autowired
	private IWorkTaskService workTaskService;
	@Autowired
	private IControlSideService controlSideService;
	@Autowired
	private IAnnoService annoService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IWorkTaskHistoryService workTaskHistoryService;
	@Autowired
	private IReadingStatusService ReadingStatusService;
	@Autowired
	private IInteractFacadeService interactFacadeService;
	@Autowired
	private IAttachBusinessService attachBusinessService;
	@Autowired
	private IUploadService uploadService;
	
	
	public WorklogServiceImpl(){}
	
	@Autowired
	public WorklogServiceImpl(IWorklogDao worklogDao){
		super(worklogDao);
		this.worklogDao = worklogDao;
	}
	
	/**
	 * 查询日志
	 */
	public static final String SQL_GET_WORKLOG_COUNT_4M= "query4M";
	/**
	 * 查询日志
	 */
	public static final String SQL_GET_WORKLOG_4M= "queryCount4M";
	
	/**
	 * 查询日志
	 */
	public static final String SQL_QUERY_MYWORKLOG_COLLECT= "queryMyWorklogList";
	
	
	/**
	 * 查询日志
	 */
	public static final String SQL_QUERY_MYWORKLOG_COLLECT_COUNT= "queryMyWorklogListCount";
	/**
	 * 方法描述：我的日志-查询日志汇总
	 * @param worklog
	 * @return 我的日志列表
	 * @author 李新桐
	 * @version  2014年5月6日下午7:09:34
	 * @see
	 */
	@Override
	public List<Worklog> getMyworklogCollect(Worklog worklog) {
		List<Worklog> worklogList = worklogDao.queryMyWorklogList(worklog);
		Anno anno=null;
		List<Anno> annoList=null;
		for (Worklog w : worklogList) {
			WorklogContent wc = new WorklogContent();
			wc.setWorklogId(w.getId());
			wc.setContentType("0");
			List<WorklogContent> todayLogList = worklogContentDao.queryAll(wc);
			int i = todayLogList.size();
			w.setTodayLogs(todayLogList);
			//批注查询 开始
			anno=new Anno();
			annoList=new ArrayList<Anno>();
			anno.setAnnoType("2");//日志
			anno.setBusinessId(w.getId());
			anno.setDeleteFlag(0);
			try {
				annoList=annoService.queryAll(anno);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			if (null!=annoList) {
				w.setAnnoCount(annoList.size());
			}else {
				w.setAnnoCount(0);
			}
			//批注查询 结束
		}
		return worklogList;
	}
	
	
	/**
	 * @description 保存方法
	 * @param Worklog worklog 实体类
	 * @return Integer 增加的记录数
	 * @author 李新桐
	 * @version 2014-05-08
	 * @throws PoException 
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(Worklog worklog) throws PoException {
		try{
			propertyService.fillProperties(worklog,false); 
			String ids = worklog.getShareUserIds();
			ControlSide  cs = new ControlSide();
			worklog.setIsShare("0");
			String remindType = worklog.getRemindType();
			if(!StringUtil.isEmpty(ids)){
				worklog.setIsShare("1");
			}
			worklogDao.save(worklog);
			List<WorklogContent> todayLogs = worklog.getTodayLogs();
			List<WorklogContent> tomorrowReminds = worklog.getTomorrowReminds();
			//保存今日日志
			if(todayLogs != null && todayLogs.size() > 0){
				for(WorklogContent worklogContent : todayLogs){
					worklogContent.setWorklogId(worklog.getId());
					propertyService.fillProperties(worklogContent,false); 
					worklogContentDao.save(worklogContent);
				}
			}
			//保存明日提醒
			if(tomorrowReminds != null && tomorrowReminds.size() > 0){
				for(WorklogContent worklogContent : tomorrowReminds){
					worklogContent.setWorklogId(worklog.getId());
					propertyService.fillProperties(worklogContent,false); 
					worklogContentDao.save(worklogContent);
				}
			}
			//李洪宇 于 2014-7-9 修改 开始
			//保存附件
//			List<Long> fileIds = worklog.getFileids();
//			if(fileIds != null && fileIds.size() >0){
//				for (int i = 0; i < fileIds.size(); i++) {
//					AttachBusiness attachBusiness = new AttachBusiness();
//					attachBusiness.setAttachId(fileIds.get(i));
//					attachBusiness.setBusinessId(worklog.getId());
//					attachBusiness.setBusinessTable(worklog.TABLE_NAME);
//					attachBusinessService.save(attachBusiness);
//				}
//			}
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(worklog.getDelattachIds())){
				uploadService.deleteFileByIds(worklog.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = worklog.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(worklog.getId());
					attachBusiness.setBusinessTable(worklog.TABLE_NAME);
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
			//李洪宇 于 2014-7-9 修改 结束
			//保存共享范围
			if(!StringUtil.isEmpty(ids)){
				cs.setDataId(worklog.getId());
				cs.setIds(ids);
				cs.setControlType("3");
				cs.setTableName(Worklog.TABLE_NAME);
				controlSideService.saveRangeByIds(cs);
				//保存提醒
				if(!StringUtil.isEmpty(remindType)){
					saveRemind(worklog);
				}
			}
		} catch (Exception e) {
			PoException se = new PoException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
			throw se;
		}
		return 1;
	}
	
	/**
	 * @description 查询单条记录方法
	 * @param Worklog worklog 实体类
	 * @return Integer 查询结果
	 * @author 李新桐
	 * @version 2014-05-08
	 * @throws CustomException 
	 */
	@Override
	public Worklog getWorklog(Worklog worklog) throws CustomException {
		worklog = worklogDao.get(worklog);
		if(worklog==null)return null;
		WorklogContent wc = new WorklogContent();
		wc.setWorklogId(worklog.getId());
		wc.setContentType("0");
		List<WorklogContent> todayLogList = worklogContentDao.queryAll(wc);
		wc.setContentType("1");
		List<WorklogContent> tomorrowRemindList = worklogContentDao.queryAll(wc);
		ControlSide controlSide=new ControlSide();
		controlSide.setDataId(worklog.getId());
		controlSide.setControlType("3");
		controlSide.setTableName(Worklog.TABLE_NAME);
		List<ControlSide> list=controlSideService.queryAll(controlSide);
		StringBuffer shareUserIds=new StringBuffer();
		StringBuffer shareUserNames=new StringBuffer();
		if(list.size()>0){
			shareUserIds.append("[");
			for(ControlSide bo:list){
				shareUserIds.append("{id:").append(bo.getUserId())
				.append(",");
				String userName = UserUtils.getUser(bo.getUserId()).getDisplayName();
				shareUserNames.append(userName).append(",");
				shareUserIds.append("text:\"")
				.append(userName).append("\"}");
				shareUserIds.append(",");
	
			}
			if (shareUserIds.length() > 0
					&& shareUserIds.charAt(shareUserIds.length() - 1) == ',') {
				shareUserIds.deleteCharAt(shareUserIds.length() - 1);
				shareUserIds.append("]");
			}
			shareUserNames.delete(shareUserNames.lastIndexOf(","), shareUserNames.length());
	
		}
		worklog.setShareUserIds(shareUserIds.toString());
		worklog.setShareUserNames(shareUserNames.toString());
		worklog.setTodayLogs(todayLogList);
		worklog.setTomorrowReminds(tomorrowRemindList);
		return worklog;
	}
	
	/**
	 * 方法描述：获得昨日提醒
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日下午2:14:31
	 * @see
	 */
	public List<WorklogContent> getYesterdayRemind(WorklogContent worklogContent){
		return worklogDao.getYesterdayRemind(worklogContent);
	}
	
	/**
	 * @description 修改方法(只修改日志的部分属性，不修改日志内容)
	 * @param Worklog worklog 实体类
	 * @return Integer 修改的记录数量
	 * @author 李新桐
	 * @version 2014-05-08
	 * @throws PoException 
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateWorklog(Worklog worklog) throws PoException {
		int result = -1;
		try {
		propertyService.fillProperties(worklog,false);
		if(StringUtil.isEmpty(worklog.getRemindMan())){
			worklog.setRemindMan(null);
		}
		String ids = worklog.getShareUserIds();
		ControlSide  cs = new ControlSide();
		worklog.setIsShare("0");
		if(!StringUtil.isEmpty(ids)){
			worklog.setIsShare("1");
		}
		result = worklogDao.update(worklog);
		}catch(Exception e){
			PoException ce = new PoException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));;
			throw ce;
		}
		return result;
	}
	
	/**
	 * @description 修改方法
	 * @param Worklog worklog 实体类
	 * @return Integer 修改的记录数量
	 * @author 李新桐
	 * @version 2014-05-08
	 * @throws PoException 
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer update(Worklog worklog) throws PoException {
		int result = -1;
		try {
			propertyService.fillProperties(worklog,false); 
			String ids = worklog.getShareUserIds();
			ControlSide  cs = new ControlSide();
			worklog.setIsShare("0");
			if(!StringUtil.isEmpty(ids)){
				worklog.setIsShare("1");
			}
			result = worklogDao.update(worklog);
			//修改日志时对批注名称修改  add by lihongyu at 2014-10-31 start
			Anno anno = new Anno();
			anno.setBusinessId(worklog.getId());//传入businessId
			anno.setAnnoName(worklog.getTitle());//传入更改后的标题
			anno.setAnnoType("2");//传入类型，0计划，1工作日程，2工作日志，3督办协办
			boolean flag = annoService.updateAnnoName(anno);//抛出异常时返回false，其他返回true
			if(!flag){
				PoException po=new PoException(new CustomException());
				po.setLogMsg(MessageUtils.getMessage("JC_OA_PO_046"));
				throw po;
			}
			//修改日志时对批注名称修改  add by lihongyu at 2014-10-31 end
			
			//认为为并发操作
			//if(result != 1){
			//	throw new ConcurrentException();
			//}
			//先删除日志下今日日志数据
			WorklogContent todayWC = new WorklogContent();
			todayWC.setWorklogId(worklog.getId());
			todayWC.setContentType(Constants.WORKLOG_CONTENT_TYPE_TODAY+"");
			worklogContentDao.deleteByworklogId(todayWC);
			//保存今日日志
			if(worklog.getTodayLogs().size() > 0){
				for(WorklogContent worklogContent : worklog.getTodayLogs()){
					worklogContent.setWorklogId(worklog.getId());
					worklogContent.setContentType(Constants.WORKLOG_CONTENT_TYPE_TODAY+"");
					propertyService.fillProperties(worklogContent,false); 
					worklogContentDao.save(worklogContent);
				}
			}
			//先删除日志下明日提醒数据
			WorklogContent tomorrowRemindsWC = new WorklogContent();
			tomorrowRemindsWC.setWorklogId(worklog.getId());
			tomorrowRemindsWC.setContentType(Constants.WORKLOG_CONTENT_TYPE_TOMORROW+"");
			worklogContentDao.deleteByworklogId(tomorrowRemindsWC);
			//保存明日提醒
			if(worklog.getTomorrowReminds().size() > 0){
				for(WorklogContent worklogContent : worklog.getTomorrowReminds()){
					worklogContent.setWorklogId(worklog.getId());
					worklogContent.setContentType(Constants.WORKLOG_CONTENT_TYPE_TOMORROW+"");
					propertyService.fillProperties(worklogContent,false); 
					worklogContentDao.save(worklogContent);
				}
			}
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(worklog.getDelattachIds())){
				uploadService.deleteFileByIds(worklog.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = worklog.getFileids();
			if(fileIds != null && fileIds.size() >0){
				//查询所需修改的图片信息
				AttachBusiness attachBusines = new AttachBusiness();
				attachBusines.setBusinessId(worklog.getId());
				attachBusines.setBusinessTable(Worklog.TABLE_NAME);
				List<AttachBusiness> list = attachBusinessService.queryAll(attachBusines);
				//删除所需修改的图片信息及图片文件
				AttachBusiness delAttachBusiness = new AttachBusiness();
				String[] keys = new String[list.size()];
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						keys[i] = list.get(i).getId().toString();
					}
					delAttachBusiness.setPrimaryKeys(keys);
					attachBusinessService.delete(delAttachBusiness, false);
				}
				//保存新的图片信息
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(worklog.getId());
					attachBusiness.setBusinessTable(Worklog.TABLE_NAME);
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
			
			//保存共享范围
			cs.setDataId(worklog.getId());
			cs.setIds(ids);
			cs.setControlType("3");
			cs.setTableName(Worklog.TABLE_NAME);
			controlSideService.updateByDataId(cs);
			//保存共享提醒
			if(!StringUtil.isEmpty(ids)){
				saveRemind(worklog);
			}
		}
		catch (Exception e) {
			PoException ce = new PoException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));;
			throw ce;
		}
		return 1;
	}

	/**
	 * 方法描述：保存提醒
	 * @param worklog
	 * @throws CustomException
	 * @throws IcException
	 * @author 李新桐
	 * @version  2014年6月17日下午6:56:45
	 * @see
	 */
	private void saveRemind(Worklog worklog) throws CustomException,
			IcException {
		String shareRemind = worklog.getRemindType();
		String Content = StringUtil.isEmpty(worklog.getRemindContent())?getShareRemindContent(worklog):worklog.getRemindContent();
		if(!StringUtil.isEmpty(shareRemind)){
			String remindMan = worklog.getRemindMan();
			if(!StringUtil.isEmpty(remindMan)){
				if("1".equals(shareRemind)){//邮件提醒
					String remindContent = Constants.getEmailContent("工作日志", Content);
					String remindTitle = StringUtil.trimIsEmpty(worklog.getRemindTitle())==true?Constants.getEmailSubject("工作日志"):worklog.getRemindTitle();
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put(interactFacadeService.MAIL_SENDER_ID, worklog.getCreateUser().toString());
					paramMap.put(interactFacadeService.MAIL_SUBJECT, remindTitle);
					paramMap.put(interactFacadeService.MAIL_CONTENT, remindContent);
					paramMap.put(interactFacadeService.MAIL_RECEIVER_IDS, remindMan);
					interactFacadeService.sendMail(paramMap);
				}else if("2".equals(shareRemind)){//短信提醒
					String remindContent = Constants.getSmsText("工作日志", Content);
					Map<String,String> paramMap = new HashMap<String,String>();
					paramMap.put(interactFacadeService.SMS_SEND_IDS, remindMan);
					paramMap.put(interactFacadeService.SMS_TEXT, remindContent);
					paramMap.put(interactFacadeService.SMS_CEATEUSER, worklog.getCreateUser().toString());
					paramMap.put(interactFacadeService.SMS_SENDTYPE, "sendType_3");// 短信类型  通知   李洪宇 2014-9-30
					interactFacadeService.sendSms(paramMap);
				}
			}
		}
	}
	
	/**
	 * 方法描述：根据工作日志id逻辑删除日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月9日上午9:33:18
	 * @throws PoException 
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteByworklogIdLogic(Worklog worklog) throws PoException {
		int result = -1;
		try{
			propertyService.fillProperties(worklog,false); 
			result = worklogDao.delete(worklog);
			Long worklogId = worklog.getId();
			//删除日志内容
			WorklogContent wc = new WorklogContent();
			wc.setWorklogId(worklogId);
			propertyService.fillProperties(wc,false); 
			worklogContentDao.deleteByworklogIdLogic(wc);
			//删除注解
			Anno anno = new Anno();
			propertyService.fillProperties(anno,false); 
			anno.setBusinessId(worklogId);
			anno.setAnnoType(Constants.ANNOTYPE_WORKLOG+"");
			annoService.deleteByBusinessId(anno);
			//删除附件
			
			ControlSide controlSide=new ControlSide();
			controlSide.setTableName(Worklog.TABLE_NAME);
			controlSide.setDataId(worklogId);
//			controlSide.setIds(idstr);
			controlSideService.deleteControlSideByDataID(controlSide);//删除范围表数据
		}catch(Exception e){
			PoException po = new PoException(e);
			po.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
			throw po;
		}
		return 1;
	}
	
	/**
	 * 方法描述：某天的过去五天的日志数
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月13日下午12:57:16
	 * @see
	 */
	public List<Worklog> queryMyWorklogPastFiveDays(Worklog worklog){
		propertyService.fillProperties(worklog,false);
		return worklogDao.queryMyWorklogPastFiveDays(worklog);
	}
	
	
	/**
	 * 方法描述：待办任务插入日程
	 * @param diary
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日上午10:08:38
	 * @throws CustomException 
	 * @see
	 */
	public int taskToDiary(Diary diary) throws PoException{
		int result = -1;
		try{
			diary.setPossessorId(SystemSecurityUtils.getUser().getId());
			diary.setPeriodType("0");
			diary.setDiaryType("0");
			diary.setIsShare("0");
			diary.setPeriodWay("0");
			result = diaryService.save(diary);
		}catch(CustomException e){
			PoException po = new PoException(e);
			po.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
			throw po;
		}
		return result;
	}
	
	/** 方法描述：共享查询tree
	 * @return List<Department>
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年5月16日下午9:32:15
	 * @see
	 */
	public List<Department> queryForShareTree() throws Exception{
		ControlSide controlSide=new ControlSide();
		controlSide.setUserId(SystemSecurityUtils.getUser().getId());
		controlSide.setTableName(Worklog.TABLE_NAME);
		/*List<ControlSide> csList=controlSideService.queryAll(controlSide);
		StringBuffer ids = new StringBuffer();
		for (ControlSide cs :csList) {
			ids.append(cs.getCreateUser()+",");
		}
		return userService.queryUserByIds(ids.toString());*/
		
		String ids=controlSideService.queryAllRangeCreateUserId(controlSide);
		List<Department> list =null;
		if (null!=ids &&  !"".equals(ids)) {
			list = userService.queryUserTreeByIds(ids); 
		}
//		List<Department> list = userService.queryUserTreeByIds(ids); 
		return list;
	}
	/** 方法描述：共享查询左右人员选择tree
	 * @return List<User>
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年5月16日下午9:32:15
	 * @see
	 */
	public List<User> queryForShareLRTree() throws Exception{
		ControlSide controlSide=new ControlSide();
		controlSide.setUserId(SystemSecurityUtils.getUser().getId());
		controlSide.setTableName(Worklog.TABLE_NAME);
		List<ControlSide> csList=controlSideService.queryAll(controlSide);
		StringBuffer ids = new StringBuffer();
		for (ControlSide cs :csList) {
			ids.append(cs.getCreateUser()+",");
		}
		return userService.queryUserByIds(ids.toString());
		
		
	}
	
	/**
	 * 方法描述：查询对应日期的日程
	 * @param diary
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日上午10:54:18
	 * @throws Exception 
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<Diary> queryDiary(Diary diary) throws PoException{
		try{
			return diaryService.queryInterface(diary);
		}catch(Exception e){
			PoException po = new PoException(e);
			throw po;
		}
		//return;
	}
	
	/**
	 * 方法描述：查询待办列表
	 * @param workTask
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日下午2:57:31
	 * @see
	 */
	public List<WorkTask> queryWorkTask(WorkTask workTask) throws PoException{
		try{
//			List<WorkTask> wtList =  workTaskService.queryWorkTaskList(workTask);
			List<WorkTask> wtList =  workTaskService.queryAbeyanceTaskList(workTask);//查询待办任务
			List<WorkTask> returnList = (wtList==null? new ArrayList<WorkTask>():wtList);
			return returnList;
		}catch(Exception e){
			PoException po = new PoException(e);
			throw po;
		}
	}
	
	/**
	 * 方法描述：根据日志查询日志下的批注
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月13日下午8:23:13
	 * @see
	 */
	public List<Anno> queryAnnoByWorklog(Anno anno){
		anno.setAnnoType(Constants.ANNOTYPE_WORKLOG+"");
		return annoService.queryAnnoAndReply(anno);
	}
		
	/**
	 * 方法描述：保存批注回复
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日下午1:23:04
	 * @see
	 */
	public int saveAnnoReply(Anno anno) throws PoException{
		try {
			Integer returnVal = -1;
			anno.setAnnoType(Constants.ANNOTYPE_WORKLOG+"");
			anno.setAnnoDate(DateUtils.getSysDate());
			anno.setAnnoUserId(SystemSecurityUtils.getUser().getId());
			anno.setAnnoUserDepid(SystemSecurityUtils.getUser().getDeptId());
			anno.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());//设置结构ID
			anno.setReadingState(Constants.PO_ANNO_IS_NOT_READ);//设置阅读状态为未阅读
			
			returnVal = annoService.save(anno);
			
			Worklog worklog = new Worklog();
			worklog.setId(anno.getBusinessId());
			worklog = this.get(worklog);
			if(worklog!=null && SystemSecurityUtils.getUser()!=null &&  worklog.getCreateUser().longValue()!=SystemSecurityUtils.getUser().getId().longValue()){//其他人的回复更新日志的阅读状态
				worklog.setStatus("0");
			}
			worklogDao.update(worklog);
			return returnVal;
		} catch (CustomException e) {
			PoException po = new PoException(e);
			throw po;
		}
	}
	
	/**
	 * 方法描述：判断某天时候已经存在日志
	 * @param Worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日下午3:14:28
	 * @see
	 */
	public Integer worklogAllowInsert(Worklog worklog){
		return worklogDao.getCount(worklog).intValue();
	}
	
	/**
	 * 方法描述：查询共享给我的日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月16日上午10:42:42
	 * @see
	 */
	@Override
	public List<Worklog> getShareWorklogList(Worklog worklog) {
		worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
		return worklogDao.getShareWorklogList(worklog);
	}
	
	/**
	 * 方法描述：分页查询共享给我的日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月16日上午10:42:42
	 * @see
	 */
	@Override
	public PageManager getShareWorklogPage(Worklog worklog,PageManager page) {
		worklog.setCreateUser(SystemSecurityUtils.getUser().getId());
		return worklogDao.queryByPage(worklog, page, WorklogDaoImpl.SQL_GET_SHARE_WORKLOG_COUNT , WorklogDaoImpl.SQL_GET_SHARE_WORKLOG);
	}
	
	/**
	 * 方法描述：待办任务汇报
	 * @param workTaskHistory
	 * @return
	 * @throws PoException
	 * @author 李新桐
	 * @version  2014年5月19日上午10:54:58
	 * @see
	 */
	public Integer taskReport(WorkTaskHistory workTaskHistory) throws PoException{
		return workTaskHistoryService.saveWorkTask(workTaskHistory);
	}
	
	/**
	 * 方法描述：保存阅读情况
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月22日上午9:49:21
	 * @throws CustomException 
	 * @see
	 */
	public Integer savaReadingStatus(ReadingStatus readingStatus) throws PoException{
		try {
			return ReadingStatusService.save(readingStatus);
		} catch (CustomException e) {
			PoException po = new PoException(e);
			throw po;
		}
		
	}
	
	/**
	 * 方法描述：更新共享范围
	 * @return
	 * @author 李新桐
	 * @version  2014年5月22日下午4:22:30
	 * @throws CustomException 
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateShareUser(Worklog worklog) throws PoException{
		Integer result;
		try{
			this.updateWorklog(worklog);
			ControlSide controlSide = new ControlSide();
			controlSide.setDataId(worklog.getId());
			controlSide.setIds(worklog.getShareUserIds());
			controlSide.setControlType("3");
			if(worklog.getCreateUser()!=null){
				controlSide.setCreateUser(worklog.getCreateUser());
			}
			controlSide.setTableName(worklog.TABLE_NAME);
			
			result = controlSideService.updateByDataId(controlSide);
			//保存共享提醒
			String ids = worklog.getShareUserIds();
			if(!StringUtil.isEmpty(ids)){
				this.saveRemind(worklog);
			}
			//如果进行提醒设置，对被提醒人进行更新，以便以后提醒时比对  lihongyu  at 2014-10-17 start
			if (null!=worklog.getRemindType() && !"".equals(worklog.getRemindType()) && !"0".equals(worklog.getRemindType())) {
				worklog.setRemindMan(worklog.getBeforeRemindMan());
//				propertyService.fillProperties(worklog,false);
				result = worklogDao.update(worklog);
			}
			//如果进行提醒设置，对被提醒人进行更新，以便以后提醒时比对  lihongyu  at 2014-10-17 end
			return result;
		} catch (CustomException e) {
			PoException po = new PoException(e);
			throw po;
		}
	}
	
	
	/**
	 * 方法描述：组装共享提醒内容
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月26日上午10:43:04
	 * @see
	 */
	private String getShareRemindContent(Worklog worklog){
		StringBuffer content = new StringBuffer();
		content.append(UserUtils.getUser(worklog.getCreateUser()).getDisplayName())
		.append("于")
		.append(DateUtils.getDate())
		.append("共享日志给您，请关注。");
		return content.toString();
		
	}

	@Override
	public Map<String, Object> validRemind(String userIds) throws PoException {
		try{
			return interactFacadeService.sendSmsValidate(userIds);
		}catch(IcException e){
			PoException poException = new PoException(e);
			throw poException;
		}
	}
	
	/**
	 * 方法描述：保存领导批注同时更新日志的标志位
	 * @param anno
	 * @return
	 * @throws CustomException
	 * @author 李新桐
	 * @version  2014年6月4日上午11:11:00
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer saveAnno(Anno anno) throws PoException{
		try{
			Integer returnVal = -1;
			anno.setAnnoUserId(SystemSecurityUtils.getUser().getId());
			anno.setAnnoUserDepid(SystemSecurityUtils.getUser().getDeptId());
			anno.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());//设置结构ID
			anno.setAnnoDate(DateUtils.getSysDate());
			anno.setReadingState("0");
			returnVal = annoService.save(anno);
			Worklog worklog = new Worklog();
			worklog.setId(anno.getBusinessId());
			worklog.setStatus("0");
			worklogDao.update(worklog);
			return returnVal;
		} catch (CustomException e) {
			PoException po = new PoException(e);
			throw po;
		}
	}
	
	/**
	 * 方法描述：更新批注的阅读状态同时更新日志的标志位
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日上午8:53:27
	 * @throws CustomException 
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer annoReading(Anno anno,String status) throws CustomException{
		try{
			Integer returnVal = -1;
			returnVal = annoService.annoReading(anno);
			Worklog worklog = new Worklog();
			worklog.setId(anno.getBusinessId());
			if(!StringUtil.isEmpty(status)){
				worklog.setId(anno.getBusinessId());
				worklog.setStatus("1");
				worklogDao.update(worklog);
			}
			return returnVal;
		} catch (CustomException e) {
			PoException po = new PoException(e);
			throw po;
		}
	}
	
	/**
	 * @description 工作日程快捷方式日志批注数量统计
	 * @param worklog
	 * @return Integer
	 * @author 刘锡来
	 * @version  2014年7月26日上午10:45:45
	 */
	public Integer worklogAnnoCount(Worklog worklog){
		Integer result = worklogDao.worklogAnnoCount(worklog);
		return result;
	}
	/**
	 * @description 工作日志，日志管理中提醒设置短信校验
	 * @param String userIds,String smsCeateuser
	 * @return Map
	 * @author 李洪宇
	 * @version  2014年9月3日上午8:58:50
	 */
	public Map<String, Object> validRemind(String userIds,String smsCeateuser) throws PoException {
		try{
			return interactFacadeService.sendSmsValidateParam(userIds, smsCeateuser);
		}catch(IcException e){
			PoException po = new PoException(e);
			po.setLogMsg("");
			throw po;
		}
	}
	
	/**
	 * 方法描述：移动端分页查询我的日志
	 * @param worklog
	 * @return
	 * @author 金峰
	 * @version  2014年5月16日上午10:42:42
	 * @see
	 */
	@Override
	public List<Worklog> getWorklogPage4M(Worklog worklog,PageManager page) throws Exception{
		PageManager page_=worklogDao.queryByPage(worklog, page, SQL_GET_WORKLOG_COUNT_4M , SQL_GET_WORKLOG_4M);
		return (List<Worklog>)page_.getData();
	}
	
	
	/**
	 * 方法描述：日志汇总分页查询
	 * @param worklog
	 * @return
	 * @author 徐伟平
	 * @version  2014年11月19日
	 * @see
	 */
	@Override
	public PageManager queryMyworklogCollect(Worklog worklog,PageManager page) throws Exception{
			PageManager pageManager = worklogDao.queryByPage(worklog, page, SQL_QUERY_MYWORKLOG_COLLECT_COUNT , SQL_QUERY_MYWORKLOG_COLLECT);
			List<Worklog> worklogList = (List<Worklog>)pageManager.getAaData();
			Anno anno=null;
			List<Anno> annoList=null;
			for (Worklog w : worklogList) {
				WorklogContent wc = new WorklogContent();
				wc.setWorklogId(w.getId());
				wc.setContentType("0");
				List<WorklogContent> todayLogList = worklogContentDao.queryAll(wc);
				int i = todayLogList.size();
				w.setTodayLogs(todayLogList);
				//批注查询 开始
				anno=new Anno();
				annoList=new ArrayList<Anno>();
				anno.setAnnoType("2");//日志
				anno.setBusinessId(w.getId());
				anno.setDeleteFlag(0);
				try {
					annoList=annoService.queryAll(anno);
				} catch (CustomException e) {
					e.printStackTrace();
				}
				if (null!=annoList) {
					w.setAnnoCount(annoList.size());
				}else {
					w.setAnnoCount(0);
				}
				//批注查询 结束
			}
			pageManager.setData(worklogList);
		return pageManager;
	}
}