package com.jc.oa.po.diary.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.oa.common.domain.ControlSide;
import com.jc.oa.common.domain.Remind;
import com.jc.oa.common.service.IControlSideService;
import com.jc.oa.common.service.IRemindService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.anno.service.IAnnoService;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.diary.domain.validator.DiaryValidator;
import com.jc.oa.po.diary.service.IDiaryService;
import com.jc.oa.po.readingstatus.domain.ReadingStatus;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.ExcuteExcelUtil;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.JsonUtil;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;
import com.jc.system.security.util.UserUtils;


/**
 * @title 个人办公
 * @description  controller类
 * @author 金峰
 * @version  2014-04-28
 */
 
@Controller
@RequestMapping(value="/po/diary")
public class DiaryController extends BaseController{
	
	@Autowired
	private IDiaryService diaryService;
	
	@Autowired
	private IControlSideService controlSideService;
	
	@Autowired
	private IRemindService remindService;
	
	@Autowired
	private IAnnoService annoService;
	
	@Autowired
	private IUserService userService;
	
	
	@org.springframework.web.bind.annotation.InitBinder("diary")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new DiaryValidator()); 
    }
	
	public DiaryController() {
	}

	/** 方法描述：分页查询方法
	 * @param diary
	 * @param page 分页实体类
	 * @return PageManager 查询结果
	 * @author 
	 * @version  2014年5月14日上午9:37:50
	 * @see
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(Diary diary,PageManager page){
		PageManager page_ = diaryService.query(diary, page);
		return page_; 
	}

	/** 方法描述：跳转方法
	 * @return
	 * @throws Exception
	 * @author 
	 * @version  2014年5月14日上午9:36:57
	 * @see
	 */
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "po/diary/manage"; 
	}
	
	/** 方法描述：删除方法
	 * @param diary 实体类
	 * @param ids 删除的多个主键
	 * @return Integer 删除数量
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:36:19
	 * @see
	 */
	@RequestMapping(value="deleteByIds.action")
	@ResponseBody
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="deleteByIds",operateDescribe="日程删除操作")
	public Map<String, Object> deleteByIds(Diary diary,String ids,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		diary.setId(Long.parseLong(ids));
		diary=diaryService.get(diary);
//		if(diary!=null){
			if(diary.getPeriodWay().equals(Constants.PO_PERIODWAY_NONE)){
				diary.setPrimaryKeys(ids.split(","));
				Long dataid=diary.getId();
				if(diaryService.delete(diary,false) == 1){
					Anno anno=new Anno();
					anno.setBusinessId(Long.parseLong(ids));
					anno.setAnnoType(Constants.ANNOTYPE_DIARY+"");
					annoService.deleteByBusinessId(anno);//删除相应的批注信息
					ControlSide controlSide=new ControlSide();
					controlSide.setTableName("TTY_PO_DIARY");
					controlSide.setDataId(diary.getId());
//					controlSide.setIds(idstr);
					controlSideService.deleteControlSideByDataID(controlSide);//删除范围表数据
					resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
					Remind remind=new Remind();
					remind.setDataId(dataid);
					remind.setTableName("TTY_PO_DIARY");
					remindService.deleteRemindByDataIdAndTable(remind);//删除提醒信息
				}
			}else{
				if(diaryService.deletePeriod(diary,false) >0){
					resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
					resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_005"));
				}
			}
//		}else{
//			resultMap.put(GlobalContext.RESULT_SUCCESS, "false");
//			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_PO_039"));
//		}
		return resultMap;
	}

	/** 方法描述： 保存方法
	 * @param diary 实体类
	 * @param result 校验结果
	 * @param request
	 * @return Map<String,Object>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:35:13
	 * @see
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="save",operateDescribe="日程保存方法")
	public Map<String,Object> save(@Valid Diary diary,Model model,BindingResult result,
			HttpServletRequest request) throws Exception{
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		// 验证token
		resultMap = validateToken(request);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		if(diary.getPossessorId()==null){
			diary.setPossessorId(SystemSecurityUtils.getUser().getId());
		}
		
		// 保存日程
		String periodWay = diary.getPeriodWay();
		String token=super.getToken(request);
		model.addAttribute(GlobalContext.SESSION_TOKEN, token);
		if(periodWay.equals(Constants.PO_PERIODWAY_NONE)){//无周期
			if(diaryService.saveDairyAndControlSide(diary)==1){
				diary.setToken(token);
				resultMap.put("diaryVo", diary);
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			}
		}else{//周期性日程
			if (diaryService.savePeriod(diary,request) > 0) {
				Diary bo=(Diary)request.getAttribute("bo");
				bo.setToken(token);
				resultMap.put("diaryVo", bo);
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_001"));
			}
		}
		return resultMap;
	}

	/** 方法描述：修改方法
	 * @param diary 实体类
	 * @param result 校验结果
	 * @param request
	 * @return Map<String,Object> 
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:33:50
	 * @see
	 */
	@RequestMapping(value="update.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="update",operateDescribe="日程修改方法")
	public Map<String,Object> update(@Valid Diary diary,BindingResult result,
			HttpServletRequest request) throws Exception{
		// 验证bean
		Map<String, Object> resultMap = validateBean(result);
		if (resultMap.size() > 0) {
			return resultMap;
		}
		String modifyFlag=request.getParameter("modifyFlag");//0周期性编辑当前事件 1周期性编辑所有事件 2非周期性日程 3非周期性日程编辑成周期性日程 4周期性日程改成非周期性
		String editable=request.getParameter("editable");//拖拽拉伸用
		if(editable==null&&diary.getPossessorId()==null){
			diary.setPossessorId(SystemSecurityUtils.getUser().getId());
		}
//		if(diary.getPossessorId()==null){
//			diary.setPossessorId(SystemSecurityUtils.getUser().getId());
//		}
		String token=super.getToken(request);
		if(modifyFlag.equals(Constants.PO_MODIFY_ONE)||modifyFlag.equals(Constants.PO_ONE_TO_PERIOD_MODIFY_NO)||modifyFlag.equals(Constants.PO_ONE_TO_MOVE_RESIZE_MODIFY)){
			Integer flag = diaryService.updateDairyAndSaveControlSide(diary);
			if (flag == 1) {
				diary.setToken(token);
				resultMap.put("diaryVo", diary);
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
			}
		}
		if(modifyFlag.equals(Constants.PO_MODIFY_ALL)||modifyFlag.equals(Constants.PO_ONE_TO_PERIOD_MODIFY_YES)||modifyFlag.equals(Constants.PO_PERIOD_TO_ONE_MODIFY)){
			Integer flag = diaryService.updatePeriod(diary,request);
			if (flag > 0) {
				Diary bo=(Diary)request.getAttribute("bo");
				bo.setToken(token);
				resultMap.put("diaryVo", bo);
				resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_SYS_003"));
			}
		}
//		resultMap.put(GlobalContext.RESULT_SUCCESS, "false");//测试拖拽拉伸失败效果用
//		resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, "修改失败");//测试拖拽拉伸失败效果用
		return resultMap;
	}

	/** 方法描述：获取单条记录方法
	 * @param diary 实体类
	 * @return Diary 查询结果
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:33:14
	 * @see
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="get",operateDescribe="获取单条记录方法")
	public Diary get(Diary diary,HttpServletRequest request) throws Exception{
		diary=getDiary(diary.getId());
		String token=super.getToken(request);//批注管理用
		diary.setAnnoTokenAnno(token);//批注管理用
		return diary;
	}
	
	/** 方法描述：公共列表页某日某用户全部日程 进入日程安排列表页
	 * @param diary 实体类
	 * @param request
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:32:48
	 * @see
	 */
	@RequestMapping(value="queryDiaryListPublic.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryDiaryListPublic",operateDescribe="公共列表页某日某用户全部日程 ")
	public List<Diary> queryDiaryListPublic(Diary diary,HttpServletRequest request) throws Exception{
		String menuFlag=request.getParameter("menuFlag");//工作日程菜单标识-1日程安排 2下属日程 3共享查询 4领导日程
		List<Diary> list=new ArrayList<Diary>();
		if(menuFlag.equals(Constants.PO_MENU_FLAG_MY_DIARY)){//1日程安排
			diary.setPossessorId(SystemSecurityUtils.getUser().getId());
			diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);//类型是日程的
			diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);//未删除的
			list= diaryService.queryForListView(diary);
			request.setAttribute("diaryList", list);
		}else if(menuFlag.equals(Constants.PO_MENU_FLAG_UNDERLING_DIARY)){//2下属日程
			diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);//类型是日程的
			diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);//未删除的
			list= diaryService.queryForListView(diary);
			request.setAttribute("diaryList", list);
		}else if(menuFlag.equals(Constants.PO_MENU_FLAG_SHARE_DIARY)){//3共享查询
//			String startDate=request.getParameter("startDate");
//			String endDate=request.getParameter("endDate");
			String userId=request.getParameter("possessorId");
			diary.setCreateUser(Long.parseLong(userId));//点选人员树返回的ID
			diary.setPossessorId(SystemSecurityUtils.getUser().getId());//当前用户ID
//			diary.setStarttime(DateUtils.parseDate(startDate));
//			diary.setEndtime(DateUtils.parseDate(endDate));
			list= diaryService.queryDiaryShare(diary);
		}else if(menuFlag.equals(Constants.PO_MENU_FLAG_LEAD_DIARY)){//4领导日程
			diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);//类型是日程的
			diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);//未删除的
			list= diaryService.queryForListView(diary);
			request.setAttribute("diaryList", list);
		}
		return list; 
	}

	/** 方法描述：跳转方法 进入日程安排列表页 new 
	 * @param diary 实体类
	 * @param request
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:32:48
	 * @see
	 */
	@RequestMapping(value="diaryMyList.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryMyList",operateDescribe="进入日程安排列表页 ")
	public String diaryMyList(Diary diary,HttpServletRequest request) throws Exception{
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		if(starttime!=null&&!starttime.equals("")&&endtime!=null&&!endtime.equals("")){
			diary.setStarttime(DateUtils.parseDate(starttime));
			diary.setEndtime(DateUtils.parseDate(endtime));
		}else{
			String date=DateUtils.getDate();
			String beginDate=date+" 00:00:00";
			String endDate=date+" 23:59:59";
			diary.setStarttime(DateUtils.parseDate(beginDate));
			diary.setEndtime(DateUtils.parseDate(endDate));
		}
		diary.setPossessorId(SystemSecurityUtils.getUser().getId());
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
		List<Diary> list= diaryService.queryForListView(diary);
		request.setAttribute("diaryList", list);
		request.setAttribute("userid", SystemSecurityUtils.getUser().getId());
		request.setAttribute("loginUserId", SystemSecurityUtils.getUser().getId());
		return "po/diary/diaryMyList"; 
	}
	
	/** 方法描述：跳转方法 进入日程安排日历页 new
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:32:27
	 * @see
	 */
	@RequestMapping(value="diaryMyCalendar.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryMyCalendar",operateDescribe="入日程安排日历页 ")
	public String diaryMyCalendar(HttpServletRequest request) throws Exception{
		User user=UserUtils.getUser(SystemSecurityUtils.getUser().getId());
		Integer isLeader=user.getIsLeader();
//		String openCale=user.getOpenCale();
//		if(isLeader.intValue()==Constants.PO_DIARY_IS_LEADER&&Constants.PO_DIARY_OPEN_CALE.equals(openCale)){
		if(isLeader.intValue()==Constants.PO_DIARY_IS_LEADER){
			request.setAttribute("agentBtn", Constants.PO_DIARY_AGENT_BTN_SHOW);
		}else{
			request.setAttribute("agentBtn", Constants.PO_DIARY_AGENT_BTN_NONE);
		}
		request.setAttribute("userid", SystemSecurityUtils.getUser().getId());
		request.setAttribute("loginUserId", SystemSecurityUtils.getUser().getId());
		return "po/diary/diaryMyCalendar"; 
	}
	
	/** 方法描述：查询方法 查询日程数据 供fullcalendar使用
	 * @param diary 实体类
	 * @param request
	 * @return List<Diary> 查询结果
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:53
	 * @see
	 */
	@RequestMapping(value="queryDiaryList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryDiaryList",operateDescribe="查询日程数据 供fullcalendar使用 ")
	public List<Diary> queryDiaryList(Diary diary,HttpServletRequest request) throws Exception{
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String userId=request.getParameter("possessorId");
		Long possessorId=userId.equals("0")?SystemSecurityUtils.getUser().getId():Long.parseLong(userId);
		diary.setStarttime(DateUtils.parseDate(startDate));
		diary.setEndtime(DateUtils.parseDate(endDate));
		diary.setPossessorId(possessorId);
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
		List<Diary> list= diaryService.queryForListView(diary);
		if(list!=null&&!list.isEmpty()){
			for(Diary td:list){
				td.setLoginUserId(SystemSecurityUtils.getUser().getId().toString());
			}
		}
		return list;
	}
	
	/** 方法描述：跳转方法 进入下属日程日历页 new
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:19
	 * @see
	 */
	@RequestMapping(value="diaryUnderlingCalendar.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryUnderlingCalendar",operateDescribe="进入下属日程日历页")
	public String diaryUnderlingCalendar(HttpServletRequest request) throws Exception{
		String userid=request.getParameter("userid");
		request.setAttribute("userid", userid);
		request.setAttribute("loginUserId", SystemSecurityUtils.getUser().getId());
		return "po/diary/diaryUnderlingCalendar"; 
	}
	
	/** 方法描述：跳转方法 进入下属日程列表页 new
	 * @param diary 实体类
	 * @param request
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:32:48
	 * @see
	 */
	@RequestMapping(value="diaryUnderlingList.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryUnderlingList",operateDescribe="进入下属日程列表页 ")
	public String diaryUnderlingList(Diary diary,HttpServletRequest request) throws Exception{
		String userId=request.getParameter("possessorId");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		if(starttime!=null&&!starttime.equals("")&&endtime!=null&&!endtime.equals("")){
			diary.setStarttime(DateUtils.parseDate(starttime));
			diary.setEndtime(DateUtils.parseDate(endtime));
		}else{
			String date=DateUtils.getDate();
			String beginDate=date+" 00:00:00";
			String endDate=date+" 23:59:59";
			diary.setStarttime(DateUtils.parseDate(beginDate));
			diary.setEndtime(DateUtils.parseDate(endDate));
		}
		diary.setPossessorId(Long.parseLong(userId));//传入选中人的ID userId
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
		List<Diary> list= diaryService.queryForListView(diary);
		request.setAttribute("diaryList", list);
		request.setAttribute("userid", userId);
		request.setAttribute("loginUserId", SystemSecurityUtils.getUser().getId());
		return "po/diary/diaryUnderlingList"; 
	}
	
	/** 方法描述：跳转方法 进入共享查询日历页 new
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:19
	 * @see
	 */
	@RequestMapping(value="diaryShareCalendar.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryShareCalendar",operateDescribe="进入共享查询日历页  ")
	public String diaryShareCalendar(HttpServletRequest request) throws Exception{
		String userid=request.getParameter("userid");
		request.setAttribute("userid", userid);
		request.setAttribute("loginUserId", SystemSecurityUtils.getUser().getId());
		return "po/diary/diaryShareCalendar"; 
	}
	
	/** 方法描述：查询方法 查询共享日程数据 供fullcalendar使用
	 * @param diary 实体类
	 * @param request
	 * @return List<Diary> 查询结果
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:53
	 * @see
	 */
	@RequestMapping(value="queryShareDiaryList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryShareDiaryList",operateDescribe="查询共享日程数据 供fullcalendar使用  ")
	public List<Diary> queryShareDiaryList(Diary diary,HttpServletRequest request) throws Exception{
//		long startTime=System.currentTimeMillis();
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String userId=request.getParameter("possessorId");
		diary.setCreateUser(Long.parseLong(userId));//点选人员树返回的ID
		diary.setPossessorId(SystemSecurityUtils.getUser().getId());//当前用户ID
		diary.setStarttimeBegin(DateUtils.parseDate(startDate));
		diary.setStarttimeEnd(DateUtils.parseDate(endDate));
		List<Diary> list= diaryService.queryDiaryShare(diary);
//		long endTime=System.currentTimeMillis(); 
//		System.out.println("--------------------------------------------------------------------");
//		System.out.println("查询共耗时："+(endTime-startTime)+"ms");
//		System.out.println("--------------------------------------------------------------------");
		return list;
	}
	
	/** 方法描述：跳转方法 进入共享日程列表页 new
	 * @param diary 实体类
	 * @param request
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:32:48
	 * @see
	 */
	@RequestMapping(value="diaryShareList.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryShareList",operateDescribe="进入共享日程列表页")
	public String diaryShareList(Diary diary,HttpServletRequest request) throws Exception{
		String userId=request.getParameter("possessorId");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		if(starttime!=null&&!starttime.equals("")&&endtime!=null&&!endtime.equals("")){
			diary.setStarttime(DateUtils.parseDate(starttime));
			diary.setEndtime(DateUtils.parseDate(endtime));
		}else{
			String date=DateUtils.getDate();
			String beginDate=date+" 00:00:00";
			String endDate=date+" 23:59:59";
			diary.setStarttime(DateUtils.parseDate(beginDate));
			diary.setEndtime(DateUtils.parseDate(endDate));
		}
		diary.setCreateUser(Long.parseLong(userId));//点选人员树返回的ID
		diary.setPossessorId(SystemSecurityUtils.getUser().getId());//当前用户ID
//		diary.setPossessorId(Long.parseLong(userId));//传入选中人的ID userId
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
		List<Diary> list= diaryService.queryDiaryShare(diary);
		if(list!=null&&!list.isEmpty()){
			for(Diary td:list){
				td.setLoginUserId(SystemSecurityUtils.getUser().getId().toString());
			}
		}
		request.setAttribute("diaryList", list);
		request.setAttribute("userid", userId);
		request.setAttribute("loginUserId", SystemSecurityUtils.getUser().getId());
		return "po/diary/diaryShareList"; 
	}
	
	/** 方法描述：跳转方法 进入领导日程日历页 new
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:19
	 * @see
	 */
	@RequestMapping(value="diaryLeadCalendar.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryLeadCalendar",operateDescribe="进入领导日程日历页")
	public String diaryLeadCalendar(HttpServletRequest request) throws Exception{
		String userid=request.getParameter("userid");
		request.setAttribute("userid", userid);
		request.setAttribute("loginUserId", SystemSecurityUtils.getUser().getId());
		return "po/diary/diaryLeadCalendar"; 
	}
	
	/** 方法描述：跳转方法 进入领导日程列表页 new
	 * @param diary 实体类
	 * @param request
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:32:48
	 * @see
	 */
	@RequestMapping(value="diaryLeadList.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryLeadList",operateDescribe=" 进入领导日程列表页")
	public String diaryLeadList(Diary diary,HttpServletRequest request) throws Exception{
		String userId=request.getParameter("possessorId");
		String starttime=request.getParameter("starttime");
		String endtime=request.getParameter("endtime");
		if(starttime!=null&&!starttime.equals("")&&endtime!=null&&!endtime.equals("")){
			diary.setStarttime(DateUtils.parseDate(starttime));
			diary.setEndtime(DateUtils.parseDate(endtime));
		}else{
			String date=DateUtils.getDate();
			String beginDate=date+" 00:00:00";
			String endDate=date+" 23:59:59";
			diary.setStarttime(DateUtils.parseDate(beginDate));
			diary.setEndtime(DateUtils.parseDate(endDate));
		}
		diary.setPossessorId(Long.parseLong(userId));//传入选中人的ID userId
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
		List<Diary> list= diaryService.queryForListView(diary);
		request.setAttribute("diaryList", list);
		request.setAttribute("userid", userId);
		request.setAttribute("loginUserId", SystemSecurityUtils.getUser().getId());
		return "po/diary/diaryLeadList"; 
	}
	
	
	/** 方法描述：下属日程tree
	 * @return List<Department>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日下午1:32:15
	 * @see
	 */
	@RequestMapping(value="queryForUnderlingTree.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryForUnderlingTree",operateDescribe="下属日程tree")
//	public List<User> queryForUnderlingTree() throws Exception{
	public List<Department> queryForUnderlingTree(HttpServletRequest request) throws Exception{
		return diaryService.queryForUnderlingTree(); 
	}
	
	/** 方法描述：领导日程tree
	 * @return List<Department>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日下午1:32:15
	 * @see
	 */
	@RequestMapping(value="queryForLeadTree.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryForLeadTree",operateDescribe="领导日程tree")
//	public List<User> queryForLeadTree() throws Exception{
	public List<Department> queryForLeadTree(HttpServletRequest request) throws Exception{
		return diaryService.queryForLeadTree(); 
	}
	
	/** 方法描述：共享查询tree
	 * @return List<Department>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日下午1:32:15
	 * @see
	 */
	@RequestMapping(value="queryForShareTree.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryForShareTree",operateDescribe="共享查询tree")
//	public List<User> queryForShareTree() throws Exception{
	public List<Department> queryForShareTree(HttpServletRequest request) throws Exception{
		return diaryService.queryForShareTree(); 
	}
	
	/** 方法描述：查询方法 查询日程汇总数据
	 * @param diary 实体类
	 * @param request
	 * @return List<Diary> 查询结果
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:53
	 * @see
	 */
//	@RequestMapping(value="queryDiarySummary.action")
//	@SuppressWarnings("unchecked")
//	@ResponseBody
//	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryDiarySummary",operateDescribe="查询日程汇总数据")
//	public List<Diary> queryDiarySummary(Diary diary,HttpServletRequest request) throws Exception{
//		String startDate=request.getParameter("startDate");
//		String endDate=request.getParameter("endDate");
//		String userId=request.getParameter("possessorId");
//		Long possessorId=userId.equals("0")?SystemSecurityUtils.getUser().getId():Long.parseLong(userId);
//		diary.setStarttime(DateUtils.parseDate(startDate));
//		diary.setEndtime(DateUtils.parseDate(endDate));
//		diary.setPossessorId(possessorId);
//		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
//		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
//		List<Diary> list= diaryService.queryDiaryAnno(diary);//需关联批注表查询
//		return list;
//	}
	@RequestMapping(value="queryDiarySummary.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryDiarySummary",operateDescribe="查询日程汇总数据")
	public PageManager queryDiarySummary(PageManager page,Diary diary,HttpServletRequest request) throws Exception{
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String userId=request.getParameter("possessorId");
		Long possessorId=userId.equals("0")?SystemSecurityUtils.getUser().getId():Long.parseLong(userId);
		diary.setStarttime(DateUtils.parseDate(startDate));
		diary.setEndtime(DateUtils.parseDate(endDate));
		diary.setPossessorId(possessorId);
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
//		List<Diary> list= diaryService.queryDiaryAnno(diary);//需关联批注表查询 (此处为以前带批注的)
		//xuweiping 日程汇总添加分页
		PageManager pageManager = diaryService.queryDiarySummary(diary,page);
		List<Diary> list= (List<Diary>)pageManager.getAaData();
		//批注查询 开始
		Anno anno=null;
		List<Anno> annoList=null;
		for(Diary d:list){
			anno=new Anno();
			annoList=new ArrayList<Anno>();
			anno.setAnnoType("1");//日程
			anno.setBusinessId(d.getId());
			anno.setDeleteFlag(0);
			try {
				annoList=annoService.queryAll(anno);
			} catch (CustomException e) {
				e.printStackTrace();
			}
			if (null!=annoList) {
				d.setAnnoCount(annoList.size());
			}else {
				d.setAnnoCount(0);
			}
			//批注查询 结束
		}
		
		pageManager.setData(list);
		return pageManager;
	}
	
	/**
	 * 方法描述：查询批注
	 * @param anno
	 * @return List<Anno>
	 * @throws PoException
	 * @author 金峰
	 * @version  2014年5月14日上午9:24:54
	 * @see
	 */
	@RequestMapping(value="queryAnno.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="queryAnno",operateDescribe="查询批注")
	public List<Anno> queryAnno(Anno anno,HttpServletRequest request) throws PoException{
		return diaryService.queryAnnoByDiary(anno);
	}
	
	/**
	 * 方法描述：保存批注回复
	 * @param anno
	 * @return Map<String,Object>
	 * @author 金峰
	 * @version  2014年5月14日下午1:21:04
	 * @see
	 */
	@RequestMapping(value="annoReply.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="annoReply",operateDescribe="保存批注回复")
	public Map<String,Object> annoReply(Anno anno,HttpServletRequest request)throws PoException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//权限判断 add by lihongyu at 2014-11-05 start
		boolean isLeader=false;
		//将当前登录用户ID传入页面
		User userInfo = SystemSecurityUtils.getUser();
		if (null!=anno) {
			User user=new User();
			user.setId(anno.getByAnnoUserId());
			isLeader=userService.isLeader(user,userInfo);
			if(userInfo.getId().intValue()==anno.getByAnnoUserId().intValue()){
				isLeader=true;
			}
		}
		if (isLeader==false) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_PO_047"));
			return resultMap;
		}
		//权限判断 add by lihongyu at 2014-11-05 end
		if(diaryService.saveAnnoReply(anno)==1){
			resultMap.put(GlobalContext.RESULT_SUCCESS, "true");
			resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_PO_010"));
			return resultMap;
		}
		return null;
	}
	
	/**
	 * @description 保存方法
	 * @param Anno anno 实体类
	 * @param BindingResult result 校验结果
	 * @param HttpServletRequest request
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author 金峰
	 * @version  2014-04-29
	 */
//	@RequestMapping(value = "saveAnno.action")
//	@ResponseBody
//	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="saveAnno",operateDescribe="批注保存")
//	public Map<String,Object> saveAnno(@Valid Anno anno,BindingResult result,HttpServletRequest request) throws Exception{
//		Map<String,Object> resultMap = validateBean(result);
//		if(!"false".equals(resultMap.get("success"))){
//			Diary diary=new Diary();
//			diary.setId(anno.getBusinessId());
//			Diary d=diaryService.get(diary);
//			if(d!=null){
//				anno.setAnnoUserId(SystemSecurityUtils.getUser().getId());
//				anno.setAnnoUserDepid(SystemSecurityUtils.getUser().getDeptId());
//				anno.setAnnoDate(DateUtils.getSysDate());
//				anno.setByAnnoUserDepid(UserUtils.getUser(anno.getByAnnoUserId()).getDeptId());
//				anno.setReadingState(Constants.PO_ANNO_IS_NOT_READ);
//				annoService.save(anno);
//				resultMap.put("success", "true");
//				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_PO_008"));
//			}else{
//				resultMap.put("success", "false");
//				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_PO_040"));
//			}
//		}
//		return resultMap;
//	}
	
	/**
	 * 方法描述：保存阅读情况
	 * @param Diary
	 * @return Map<String,Object>
	 * @author 金峰
	 * @version  2014年5月22日上午9:49:21
	 * @throws Exception 
	 * @see
	 */
	@RequestMapping(value="savaReadingStatus.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="savaReadingStatus",operateDescribe="保存阅读情况")
	public Map<String,Object> savaReadingStatus(Diary diary,HttpServletRequest request) throws Exception{
		ReadingStatus readingStatus = new ReadingStatus();
		readingStatus.setWorklogId(diary.getId());
		readingStatus.setBusinessType(Constants.PO_READING_STATUS_DIARY);
		readingStatus.setSubordinateId(diary.getCreateUser());
		readingStatus.setReaderId(SystemSecurityUtils.getUser().getId());;
		diaryService.savaReadingStatus(readingStatus);
		return null;
	}
	
	/**
	 * 方法描述：校验将日志共享短信提醒时被提醒人是否存在电话号
	 * @param worklog
	 * @return
	 * @author 金峰
	 * @version  2014年6月3日下午2:05:44
	 * @see
	 */
	@RequestMapping(value="validRemind.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="validRemind",operateDescribe="校验将日志共享短信提醒时被提醒人是否存在电话号")
	public Map<String,Object> validRemind(Diary diary,HttpServletRequest request) throws PoException{
		String userIds = diary.getAgentId();
		return diaryService.validRemind(userIds);
	} 
	
	/** 方法描述：跳转方法 进入下属日程无信息页
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:19
	 * @see
	 */
	@RequestMapping(value="diaryNoUnderling.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryNoUnderling",operateDescribe="进入下属日程无信息页")
	public String diaryNoUnderling(HttpServletRequest request) throws Exception{
		return "po/diary/diaryNoUnderling"; 
	}
	
	/** 方法描述：跳转方法 进入共享查询无信息页
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:19
	 * @see
	 */
	@RequestMapping(value="diaryNoShare.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryNoShare",operateDescribe="进入共享查询无信息页 ")
	public String diaryNoShare(HttpServletRequest request) throws Exception{
		return "po/diary/diaryNoShare"; 
	}
	
	/** 方法描述：跳转方法 进入领导日程无信息页
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日上午9:31:19
	 * @see
	 */
	@RequestMapping(value="diaryNoLead.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="diaryNoLead",operateDescribe="进入领导日程无信息页 ")
	public String diaryNoLead(HttpServletRequest request) throws Exception{
		return "po/diary/diaryNoLead"; 
	}
	
	/** 方法描述：查询方法 日程汇总导出数据
	 * @param diary 实体类
	 * @param request
	 * @return List<Diary> 查询结果
	 * @throws Exception
	 * @version  2014年5月14日上午9:31:53
	 * @see
	 */
	@RequestMapping(value="exportExcel.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="exportExcel",operateDescribe="日程汇总导出数据")
	public void exportExcel(PageManager pageManager,Diary diary,HttpServletResponse response,HttpServletRequest request) throws Exception{
		String startDate=request.getParameter("startDate");
		String endDate=request.getParameter("endDate");
		String userId=request.getParameter("possessorId");
		Long possessorId=userId.equals("0")?SystemSecurityUtils.getUser().getId():Long.parseLong(userId);
		diary.setStarttime(DateUtils.parseDate(startDate));
		diary.setEndtime(DateUtils.parseDate(endDate));
		diary.setPossessorId(possessorId);
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
//		List<Diary> list= diaryService.queryDiaryAnno(diary);//需关联批注表查询
		List<Diary> list= diaryService.queryForListView(diary);
		for(Diary d:list){
			d.setStarttimeEndtime(DateUtils.formatDate(d.getStarttime(),"yyyy-MM-dd HH:mm:ss")+"-"+DateUtils.formatDate(d.getEndtime(),"yyyy-MM-dd HH:mm:ss"));
			String c=d.getContent().replace("<br/>", "\r\n");
			c=c.replace("<BR/>", "\r\n");
			c=c.replace("<BR>", "\r\n");
			c=c.replace("<br>", "\r\n");
			d.setContent(c);
		}
		List<String> head = new ArrayList<String>();
		head.add("所属人");
		head.add("日程时间");
		head.add("日程内容");
//		head.add("批注人");
//		head.add("批注内容");
		List<String> column = new ArrayList<String>();
		column.add("possessorIdValue");
		column.add("starttimeEndtime");
		column.add("content");
//		column.add("annoUserIdValue");
//		column.add("annoContent");
		ExcuteExcelUtil.exportExcel(head,column,list,response);
	}
	
	/**获取日程数据
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public Diary getDiary(Long id) throws Exception{
		ControlSide controlSide=new ControlSide();
		controlSide.setDataId(id);
		controlSide.setControlType(Constants.PO_DIARY_CONTROLSIDE_CONTROLTYPE);
		controlSide.setPermissionType(Constants.PO_DIARY_CONTROLSIDE_PERMISSIONTYPE);
		controlSide.setTableName("TTY_PO_DIARY");
		List<ControlSide> list=controlSideService.queryAll(controlSide);
		String ids="";
		for(ControlSide bo:list){
			ids+=bo.getUserId()+",";
		}
		if(!ids.equals("")){
			ids=ids.substring(0,ids.length()-1);
		}
		Remind remind=new Remind();
		remind.setTableName("TTY_PO_DIARY");
		remind.setDataId(id);
		String remindJson = remindService.getRemindJson(remind);
		Diary vo=new Diary();
		vo.setId(id);
		vo=diaryService.get(vo);
		if(vo==null){
			return vo;
		}else{
			vo.setShareScopeId(ids);
			vo.setRemind(remindJson);
			return vo;
		}
	}
	
	/** 方法描述：跳转方法 打开日历页新建日程层
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年10月8日上午13:14:19
	 * @see
	 */
	@RequestMapping(value="showAddDiaryDiv.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="showAddDiaryDiv",operateDescribe="打开日历页新建日程层 ")
	public String showAddDiaryDiv(Model model,HttpServletRequest request) throws Exception{
		String token=super.getToken(request);
		model.addAttribute(GlobalContext.SESSION_TOKEN, token);
		return "po/diary/saveOrModify"; 
	}
	
	/** 方法描述：跳转方法 打开日历页日程详细层
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年10月8日上午13:14:19
	 * @see
	 */
	@RequestMapping(value="showDiaryDetailDiv.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="showDiaryDetailDiv",operateDescribe="打开日历页日程详细层 ")
	public String showDiaryDetailDiv(Long id,Model model,HttpServletRequest request) throws Exception{
		Diary diary=null;
		if(id!=null){
			diary=getDiary(id);
		}
		String diaryJsonDetail=JsonUtil.getJSON(diary);
		diaryJsonDetail=diaryJsonDetail.replaceAll("'", "&acute;");
		model.addAttribute("diaryJsonDetail",diaryJsonDetail);
		model.addAttribute("annoToken", super.getToken(request));
		return "po/diary/diaryDetail"; 
	}
	
	/** 方法描述：跳转方法 打开日历页日程修改层
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年10月8日上午13:14:19
	 * @see
	 */
	@RequestMapping(value="showDiaryModifyDiv.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="showDiaryModifyDiv",operateDescribe="打开日历页日程修改层 ")
	public String showDiaryModifyDiv(Long id,Model model,HttpServletRequest request) throws Exception{
		String token=(String)request.getSession(false).getAttribute(GlobalContext.SESSION_TOKEN);
		
		Diary diary=null;
		if(id!=null){
			diary=getDiary(id);
		}
		String diaryJsonModify=JsonUtil.getJSON(diary);
		diaryJsonModify=diaryJsonModify.replaceAll("'", "&acute;");
		model.addAttribute("diaryJsonModify",diaryJsonModify);
		model.addAttribute(GlobalContext.SESSION_TOKEN, token);//super.getToken(request)
		
		return "po/diary/saveOrModify"; 
	}
	/** 方法描述：跳转方法 打开日历页周期性日程修改提示信息层
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年10月8日上午13:14:19
	 * @see
	 */
	@RequestMapping(value="periodDiaryModifyInfo.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="periodDiaryModifyInfo",operateDescribe="打开日历页周期性日程修改提示信息层 ")
	public String periodDiaryModifyInfo(Model model,HttpServletRequest request) throws Exception{
//		model.addAttribute("annoToken", super.getToken(request));
		return "po/diary/periodDiaryModifyInfo"; 
	}
	
	/** 方法描述：跳转方法 打开日历页日程修改层
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年10月8日上午13:14:19
	 * @see
	 */
	@RequestMapping(value="showSummaryAnnoDiv.action")
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="showSummaryAnnoDiv",operateDescribe="打开日历页日程修改层 ")
	public String showSummaryAnnoDiv(Long id,Model model,HttpServletRequest request) throws Exception{
		Anno anno=new Anno();
		List<Anno> list=new ArrayList<Anno>();
		if(id!=null){
			anno.setBusinessId(id);
			list=diaryService.queryAnnoByDiary(anno);
		}
		String annoListJson=JsonUtil.getJSON(list);
		annoListJson=annoListJson.replaceAll("'", "&acute;");
		model.addAttribute("annoListJson",annoListJson);
//		model.addAttribute(GlobalContext.SESSION_TOKEN, super.getToken(request));
		return "po/diary/diarySummaryAnno"; 
	}
	
	/** 方法描述：跳转方法 纯页面操作获取token专用
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年10月8日上午13:14:19
	 * @see
	 */
	@RequestMapping(value="getToken.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="getToken",operateDescribe="纯页面操作获取token专用 ")
	public Map<String, Object> getToken(Model model,HttpServletRequest request) throws Exception{
		String token=super.getToken(request);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put(GlobalContext.SESSION_TOKEN, token);
		return resultMap;
	}
	
	/** 方法描述：验证上下级关系
	 * @return String 跳转的路径
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年10月8日上午13:14:19
	 * @see
	 */
	@RequestMapping(value="checkLeaderMember.action")
	@ResponseBody
	@ActionLog(operateModelNm="公共_个人办公_工作日程",operateFuncNm="checkLeaderMember",operateDescribe="验证上下级关系")
	public Map<String, Object> checkLeaderMember(Long leaderId,Model model,HttpServletRequest request) throws Exception{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean flag=userService.isLeader(SystemSecurityUtils.getUser().getId(),leaderId);
		resultMap.put("isLeader", flag);
		return resultMap;
	}
}