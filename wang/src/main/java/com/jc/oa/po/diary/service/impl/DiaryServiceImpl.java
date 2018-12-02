package com.jc.oa.po.diary.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.common.domain.ControlSide;
import com.jc.oa.common.domain.Remind;
import com.jc.oa.common.service.IControlSideService;
import com.jc.oa.common.service.IRemindService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.anno.service.IAnnoService;
import com.jc.oa.po.diary.dao.IDiaryDao;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.diary.domain.DiaryDelegation;
import com.jc.oa.po.diary.service.IDiaryDelegationService;
import com.jc.oa.po.diary.service.IDiaryService;
import com.jc.oa.po.readingstatus.domain.ReadingStatus;
import com.jc.oa.po.readingstatus.service.IReadingStatusService;
import com.jc.system.CustomException;
import com.jc.system.common.service.ICommonService;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.UserUtils;

/**
 * @title 个人办公
 * @description 业务服务类 Copyright (c) 2014 Jiachengnet.com Inc. All Rights
 *              Reserved Company 长春嘉诚网络工程有限公司
 * @author 金峰
 * @version 2014-04-28
 */
@Service
public class DiaryServiceImpl extends BaseServiceImpl<Diary> implements
		IDiaryService {

	private IDiaryDao diaryDao;
	
	@Autowired
	private IUserService serService;
	
	@Autowired
	private IControlSideService controlSideService;
	
	@Autowired
	private IAnnoService annoService;
	
	@Autowired
	private IReadingStatusService ReadingStatusService;
	
	@Autowired
	private IRemindService remindService;
	
	@Autowired
	private IInteractFacadeService interactFacadeService;
	
	@Autowired
	private ICommonService commonService;
	
	@Autowired
	private IDiaryDelegationService diaryDelegationService;
	
	
	public DiaryServiceImpl() {
	}

	@Autowired
	public DiaryServiceImpl(IDiaryDao diaryDao) {
		super(diaryDao);
		this.diaryDao = diaryDao;
	}
	
	/**
	 * 查询日程
	 */
	public static final String SQL_QUERY_DIARY_4M= "queryForListView4m";
	/**
	 * 查询日程
	 */
	public static final String SQL_QUERY_DIARY_COUNT_4M= "queryForListView4mCount";
	
	/**
	 * 日程汇总分页查询  徐伟平 add 20141119
	 */
	public static final String SQL_QUERY_DIARY_SUMMARY= "queryDiaryAndAnno";
	
	/**
	 * 日程汇总分页查询  徐伟平 add 20141119
	 */
	public static final String SQL_QUERY_DIARY_COUNT_SUMMARY= "queryDiaryAndAnnoCount";
	
	/**
	 * 方法描述：根据日志查询日志下的批注
	 * @param anno
	 * @return List<Anno>
	 * @author 金峰
	 * @version  2014年5月13日下午8:23:13
	 * @see
	 */
	public List<Anno> queryAnnoByDiary(Anno anno){
		anno.setAnnoType(Constants.ANNOTYPE_DIARY+"");
		return annoService.queryAnnoAndReply(anno);
	}
	
	/** 方法描述：删除周期性日程
	 * @param diary
	 * @return Integer -1 删除失败 >0 删除成功
	 * @throws PoException
	 * @author 金峰
	 * @version  2014年5月12日下午7:18:30
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer deletePeriod(Diary diary,Boolean logicDelete) throws PoException {
		List<Diary> al=this.getPeriod(diary);
		String idstr=this.getPeriodIds(al);
		diary.setPrimaryKeys(idstr.split(","));
		int result=-1;
		try{
			result=diaryDao.delete(diary,logicDelete);
//			this.delPeriodAnnoIds(idstr);//删除相应的批注信息
			String[]arrIds=idstr.split(",");
			for(int i=0;i<arrIds.length;i++){
				Anno anno=new Anno();
				anno.setBusinessId(Long.parseLong(arrIds[i]));
				anno.setAnnoType(Constants.ANNOTYPE_DIARY+"");
				annoService.deleteByBusinessId(anno);
			}
			ControlSide controlSide=new ControlSide();
			controlSide.setTableName("TTY_PO_DIARY");
//			controlSide.setDataId(diary.getId());
			controlSide.setIds(idstr);
			controlSideService.deleteControlSideByDataID(controlSide);//删除范围表数据
			
			
			for(int i=0;i<arrIds.length;i++){
				Remind remind=new Remind();
				remind.setDataId(Long.parseLong(arrIds[i]));
				remind.setTableName("TTY_PO_DIARY");
				remindService.deleteRemindByDataIdAndTable(remind);//删除提醒信息
			}
		
		}catch(Exception e){
			PoException pe = new PoException(e);
			pe.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
			throw pe;
		}
		return result;
	}
	
	/** 方法描述：更新非周期性日程
	 * @param diary
	 * @return Integer -1 保存失败 1 保存成功
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月21日下午1:58:53
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateDairyAndSaveControlSide(Diary diary) throws Exception {
		if(diary.getModifyFlag().equals(Constants.PO_ONE_TO_MOVE_RESIZE_MODIFY)){//拖拽拉伸事件处理
			//-----处理拖拽拉伸时的并发-----begin  //因为拖拽拉伸时无法从前台传回modifyDate 因此查询赋值 可能导致 并发修改时后者覆盖前者的内容
//			Diary d=new Diary();
//			d.setId(diary.getId());
//			d=diaryDao.get(d);
//			if(d==null){
//				diary.setModifyDate(new Date());
//			}else{
//				diary.setModifyDate(d.getModifyDate());
//			}
			//-----处理拖拽拉伸时的并发-----end
			ControlSide controlSide=new ControlSide();
			controlSide.setDataId(diary.getId());
			controlSide.setTableName("TTY_PO_DIARY");
			String ids=controlSideService.queryRangeUserId(controlSide);
			if(ids!=null&&!ids.equals("")){
				diary.setIsShare(Constants.PO_DIARY_ISSHARE_NOT);//是共享日程
				diary.setShareScopeId(ids);
			}
		}
		if(SystemSecurityUtils.getUser().getId().toString().equals(diary.getPossessorId().toString())){
			ControlSide controlSide=new ControlSide();
			controlSide.setTableName("TTY_PO_DIARY");
			controlSide.setDataId(diary.getId());
//			controlSide.setIds(ids);
			controlSideService.deleteControlSideByDataID(controlSide);//更新前删除范围表数据
			
			if(diary.getShareScopeId()!=null&&!diary.getShareScopeId().equals("")){
				diary.setIsShare(Constants.PO_DIARY_ISSHARE_NOT);//是共享日程
			}else{
				diary.setIsShare(Constants.PO_DIARY_ISSHARE);//不是共享日程
			}
			
			if(diary.getShareScopeId()!=null&&!diary.getShareScopeId().equals("")){
				controlSideService.saveRangeByIds(getControlSide(diary));
			}
		}
		
		propertyService.fillProperties(diary,true);
		Integer result =diaryDao.update(diary);
		if (result==1) {	
			if(!diary.getModifyFlag().equals(Constants.PO_ONE_TO_MOVE_RESIZE_MODIFY)){//拖拽拉伸事件处理
				//如果该日程已经存在批注，那么需要更新批注文件名称与日程标题同步
				Anno anno = new Anno();
				anno.setBusinessId(diary.getId());
				anno.setAnnoName(diary.getTitle());
				anno.setAnnoType("1");//工作日程
				boolean flag = annoService.updateAnnoName(anno);
				if(!flag){
					PoException po=new PoException(new CustomException());
					po.setLogMsg(MessageUtils.getMessage("JC_OA_PO_046"));
					throw po;
				}
			}
			//提醒设置
			if (null!=diary.getRemind() && !"".equals(diary.getRemind())) {
				try {
					remindService.save(diary.getRemind(), diary.getId(),"TTY_PO_DIARY");
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg("提醒设置失败！");
					throw po;
				}
			}
		}
		return result;
	}
	
	/** 方法描述：更新周期性日程
	 * @param diary 实体类
	 * @return Integer -1 保存失败 >0 保存成功
	 * @throws PoException
	 * @author 金峰
	 * @version  2014年5月12日下午6:53:54
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updatePeriod(Diary diary,HttpServletRequest request) throws Exception {
		Diary vo=new Diary();
		vo.setId(diary.getId());
		Diary bo=this.get(vo);
		List<Diary> al=this.getPeriod(bo);
		Diary master=this.getPeriodMaster(al);//取得主日程
		String ids=this.getPeriodIds(al);
		ControlSide controlSide=new ControlSide();
		controlSide.setTableName("TTY_PO_DIARY");
		controlSide.setDataId(diary.getId());
		controlSide.setIds(ids);
		controlSideService.deleteControlSideByDataID(controlSide);//更新前删除范围表数据
		if(master==null&&al.size()==1){//非周期性日程改成周期性日程
			this.deletePeriod(bo,false);//物理删除周期性日程
			diary.setStarttime(bo.getStarttime());
			diary.setEndtime(bo.getEndtime());
//			diary.setPeriodStartdate(bo.getPeriodStartdate());
//			diary.setPeriodEnddate(bo.getPeriodEnddate());
			diary.setId(null);
		}else if(diary.getModifyFlag().equals(Constants.PO_PERIOD_TO_ONE_MODIFY)){
			this.deletePeriod(bo,false);//物理删除周期性日程
			diary.setStarttime(bo.getStarttime());
			diary.setEndtime(bo.getEndtime());
//			diary.setPeriodStartdate(bo.getPeriodStartdate());
//			diary.setPeriodEnddate(bo.getPeriodEnddate());
			diary.setId(null);
		}else{
			this.deletePeriod(master,false);//物理删除周期性日程
			diary.setStarttime(master.getStarttime());
			diary.setEndtime(master.getEndtime());
//			diary.setPeriodStartdate(bo.getPeriodStartdate());
//			diary.setPeriodEnddate(bo.getPeriodEnddate());
			diary.setId(null);
		}
		
//		this.delPeriodAnnoIds(ids);
		
		String[]arrIds=ids.split(",");
		for(int i=0;i<arrIds.length;i++){
			Anno anno=new Anno();
			anno.setBusinessId(Long.parseLong(arrIds[i]));
			anno.setAnnoType(Constants.ANNOTYPE_DIARY+"");
			annoService.deleteByBusinessId(anno);
		}//删除相应的批注信息
		
		if(diary.getModifyFlag().equals(Constants.PO_MODIFY_ALL)||diary.getModifyFlag().equals(Constants.PO_ONE_TO_PERIOD_MODIFY_YES)||diary.getModifyFlag().equals(Constants.PO_PERIOD_TO_ONE_MODIFY)){
			request.setAttribute("bo", diary);
		}
		
		if(diary.getModifyFlag().equals(Constants.PO_PERIOD_TO_ONE_MODIFY)){
			request.setAttribute("bo", diary);
			return this.saveDairyAndControlSide(diary);
		}else{
			return this.savePeriod(diary,request);
		}
	}
	
	/** 方法描述：删除周期性日程中包含的批注信息
	 * @param ids
	 * @return Integer
	 * @throws CustomException
	 * @author 金峰
	 * @version  2014年5月27日上午11:56:50
	 * @see
	 */
	public Integer delPeriodAnnoIds(String ids) throws CustomException{
		Integer result=-1;
		String[]id=ids.split(",");
		for(int i=0;i>id.length;i++){
			Anno anno =new Anno();
			anno.setBusinessId(Long.parseLong(id[i]));
			anno.setAnnoType(Constants.ANNOTYPE_DIARY+"");
			List<Anno> list=annoService.queryAnnoAndReply(anno);
			if(list.size()>0){
				for(Anno bo:list){
					result+=annoService.delete(bo, false);
				}
			}
		}
		return result;
	}

	/** 方法描述：根据传入数据获取同一周期所有日程id串
	 * @param Diary
	 * @return String
	 * @throws CustomException
	 * @author 金峰
	 * @version  2014年5月27日上午11:56:50
	 * @see
	 */
	public String getPeriodIds(List<Diary> list) {
		String idstr="";
		for(Diary bo:list){
			idstr+=bo.getId()+",";
		}
		idstr=idstr.substring(0, idstr.length()-1);
		return idstr;
	}
	
	/** 方法描述：根据传入数据获取同一周期所有日程
	 * @param Diary
	 * @return String
	 * @throws CustomException
	 * @author 金峰
	 * @version  2014年5月27日上午11:56:50
	 * @see
	 */
	public List<Diary> getPeriod(Diary diary) throws PoException{
		Diary d=new Diary();
		d.setPeriodType(diary.getPeriodType());
		d.setPeriodWay(diary.getPeriodWay());
		d.setPeriodStartdate(diary.getPeriodStartdate());
		d.setPeriodEnddate(diary.getPeriodEnddate());
		d.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);//类型 工作日程
		d.setCreateDate(diary.getCreateDate());
		
		d.setMillisecond(diary.getMillisecond());
		d.setBusinessId(diary.getBusinessId());
		d.setPossessorId(diary.getPossessorId());
		d.setCreateUser(diary.getCreateUser());
		return diaryDao.queryAll(d);
	}
	
	/** 方法描述：根据传入数据获取同一周期所有日程中的master日程
	 * @param Diary
	 * @return String
	 * @throws CustomException
	 * @author 金峰
	 * @version  2014年5月27日上午11:56:50
	 * @see
	 */
	public Diary getPeriodMaster(List<Diary> list) {
		for(Diary d:list){
			if(d.getMaster()!=null&&d.getMaster().equals(Constants.PO_DIARY_MASTER)){
				return d;
			}
		}
		return null;
	}
	
	/** 方法描述：组装ControlSide
	 * @param diary
	 * @return ControlSide
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日下午1:25:56
	 * @see
	 */
	public ControlSide getControlSide(Diary diary) throws Exception{ 
		//todo
		ControlSide controlSide=new ControlSide();
		controlSide.setDataId(diary.getId());
//		controlSide.setUserId(SystemSecurityUtils.getUser().getId());
//		controlSide.setDeptId(SystemSecurityUtils.getUser().getDeptId());
		controlSide.setControlType(Constants.PO_DIARY_CONTROLSIDE_CONTROLTYPE);
		controlSide.setPermissionType(Constants.PO_DIARY_CONTROLSIDE_PERMISSIONTYPE);
		controlSide.setTableName("TTY_PO_DIARY");
		controlSide.setIds(diary.getShareScopeId());
		return controlSide;
	}
	
	/** 方法描述：调用IBaseService save 并添加保存ControlSide
	 * @param diary
	 * @param request
	 * @return Integer -1 保存失败 1 保存成功
	 * @author 金峰
	 * @version  2014年5月15日下午1:33:31
	 * @throws  
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer saveDairyAndControlSide(Diary diary) throws Exception{
		if(diary.getShareScopeId()!=null&&!diary.getShareScopeId().equals("")){
			diary.setIsShare(Constants.PO_DIARY_ISSHARE_NOT);//是共享日程
		}else{
			diary.setIsShare(Constants.PO_DIARY_ISSHARE);//不是共享日程
		}
		propertyService.fillProperties(diary,false);
		diary.setMillisecond(diary.getCreateDate().getTime());
		Integer result =diaryDao.save(diary);
		if (result==1) {//提醒设置			
			if (null!=diary.getRemind() && !"".equals(diary.getRemind())) {
				try {
					remindService.save(diary.getRemind(), diary.getId(),"TTY_PO_DIARY");
				} catch (CustomException e) {
					PoException po=new PoException(e);
					po.setLogMsg("提醒设置失败！");
					throw po;
				}
			}
		}
		if(diary.getShareScopeId()!=null&&!diary.getShareScopeId().equals("")){
			controlSideService.saveRangeByIds(getControlSide(diary));
		}
		return result;
	}


	/**
	 * 方法描述：保存周期性日程
	 * 
	 * @param diary 实体类
	 * @return Integer -1 保存失败 >0 保存成功
	 * @throws PoException
	 * @author 金峰
	 * @version 2014年5月9日上午11:05:01
	 * @see
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer savePeriod(Diary diary,HttpServletRequest request) throws PoException {
		Integer result = -1;
		try{
//			Long id=diary.getId();
			List<Diary> list = diaryPeriodAssign(diary);
			int i=0;
			Date date=new Date();
			for(Diary bo:list){
				propertyService.fillProperties(bo,false);
				bo.setCreateDate(date);
				bo.setMillisecond(date.getTime());
				if(diary.getShareScopeId()!=null&&!diary.getShareScopeId().equals("")){
					bo.setIsShare(Constants.PO_DIARY_ISSHARE_NOT);//是共享日程
				}else{
					bo.setIsShare(Constants.PO_DIARY_ISSHARE);//不是共享日程
				}
				int n=diaryDao.save(bo);
				i+=n;
				if (n==1) {//提醒设置			
					if (null!=diary.getRemind() && !"".equals(diary.getRemind())) {
						try {
							remindService.save(diary.getRemind(), bo.getId(),"TTY_PO_DIARY");
						} catch (CustomException e) {
							PoException po=new PoException(e);
							po.setLogMsg("提醒设置失败！");
							throw po;
						}
					}
				}
				if(bo.getShareScopeId()!=null&&!diary.getShareScopeId().equals("")){
					controlSideService.saveRangeByIds(getControlSide(bo));//todo
				}
			}
			result=i;
			//以下供列表页回显用
			diary.setId(null);
			List<Diary> al=this.queryAll(diary);
			for(Diary d:al){
				if(d.getStarttime().equals(diary.getStarttime())&&d.getEndtime().equals(diary.getEndtime())){
					request.setAttribute("bo", d);
				}
			}
			
		}catch(Exception e){
			PoException pe = new PoException(e);
			pe.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
			throw pe;
		}
		return result;
	}

	/**
	 * 方法描述：处理周期性日程生成集合
	 * 
	 * @param diary
	 *            实体类
	 * @return List<Diary> 实体集合
	 * @throws PoException
	 * @author 金峰
	 * @version 2014年5月9日上午11:05:55
	 * @see
	 */
	public List<Diary> diaryPeriodAssign(Diary diary) throws Exception {
		List<Diary> list = new ArrayList<Diary>();
		String periodWay = diary.getPeriodWay();
		String[]pw=periodWay.split(":");

		String startTime = DateUtils.formatDate(diary.getStarttime(),
				"yyyy-MM-dd HH:mm:ss");
		String endTime = DateUtils.formatDate(diary.getEndtime(),
				"yyyy-MM-dd HH:mm:ss");
		String startT = startTime.substring(11, 19);// HH:mm:ss
		String endT = endTime.substring(11, 19);// HH:mm:ss
		startTime = startTime.substring(0, 10);// yyyy-MM-dd
		endTime = endTime.substring(0, 10);// yyyy-MM-dd

		String periodStartDate = DateUtils.formatDate(diary.getPeriodStartdate());// yyyy-MM-dd
		String periodEndDate = DateUtils.formatDate(diary.getPeriodEnddate());// yyyy-MM-dd

		Date startDate = DateUtils.parseDate(startTime);//选中开始日期  yyyy-MM-dd 00:00:00
		Date endDate = DateUtils.parseDate(endTime);//选中结束日期  yyyy-MM-dd 00:00:00
		long subtractionDay=DateUtils.subtractionDays(endDate,startDate);//结束时间和开始时间的差值
		int sday=Integer.parseInt(subtractionDay+"");//结束时间和开始时间的差值
		
		Date pStartDate = DateUtils.parseDate(periodStartDate);//周期开始时间 yyyy-MM-dd 00:00:00
		Date pEndDate = DateUtils.parseDate(periodEndDate);//周期结束时间 yyyy-MM-dd 00:00:00

		long n=DateUtils.subtractionDays(startDate, pStartDate);//选中的日期和周期开始日期比较 +
		long m=DateUtils.subtractionDays(startDate, pEndDate);//选中的日期和周期结束日期比较 -
		
		if (pw[0].equals(Constants.PO_PERIODWAY_EVERYDAY)) {// 每天
			list=this.getPeriodDateListEveryDay(diary,pStartDate,pEndDate,startT,endT,sday,1);
			for(Diary d:list){
				if(d.getStarttime().equals(diary.getStarttime())){
					d.setMaster(Constants.PO_DIARY_MASTER);
				}
			}
		} else if (pw[0].equals(Constants.PO_PERIODWAY_EVERYWEEK)) {// 每周
			String[]weeks=pw[1].split(",");
			int wd=DateUtils.getWeekday(startDate);
			boolean is=false;
			if(n>=0&&m<=0){//满足该条件说明选中日期包含在周期范围内
				for(int i=0;i<weeks.length;i++){//遍历所选周期，判断是选中日期是否刚好在其中
					if(wd==Integer.parseInt(weeks[i])){
						is=true;//选中日期刚好在其中，则下面循环中操作不进行，反之进行一次
					}
				}
			}
			for(int i=0;i<weeks.length;i++){
				List<Diary> alist=this.getPeriodDateListWeek(diary,pStartDate,pEndDate,startT,endT,Integer.parseInt(weeks[i]),sday,1);
				for(int j=0;j<alist.size();j++){
					Diary data=alist.get(j);
					if(is&&data.getStarttime().equals(diary.getStarttime())){
						alist.get(j).setMaster(Constants.PO_DIARY_MASTER);
						list.add(alist.get(j));
					}else{
						list.add(alist.get(j));
					}
				}
			}
			if(n>=0&&m<=0){//满足该条件说明选中日期包含在周期范围内
				for(int i=0;i<weeks.length;i++){//遍历所选周期，判断是选中日期是否刚好在其中
					if(wd!=Integer.parseInt(weeks[i])){
						if(!is){
							diary.setMaster(Constants.PO_DIARY_MASTER);
							list.add(diary);
							is=true;
						}
					}
				}
			}
		} else if (pw[0].equals(Constants.PO_PERIODWAY_EVERYMONTH)) {//每月
			list=this.getPeriodDateListMonth(diary,pStartDate,pEndDate,startT,endT,Integer.parseInt(pw[1]+""),sday,1);
			if(n>=0&&m<=0){//满足该条件说明选中日期包含在周期范围内
				int monthday=Integer.parseInt(pw[1]);
				int day=DateUtils.getDayByDate(startDate);
				if(monthday!=day){//选中日期不是周期日期时
					diary.setMaster(Constants.PO_DIARY_MASTER);
					list.add(diary);
				}
			}
		} else if (pw[0].equals(Constants.PO_PERIODWAY_EVERYYEAR)) {//每年-----------------------------
			list=this.getPeriodDateListYear(diary,pStartDate,pEndDate,startT,endT,Integer.parseInt(pw[1]+""),Integer.parseInt(pw[2]+""),sday,1);
			if(n>=0&&m<=0){//满足该条件说明选中日期包含在周期范围内
				int yearmonth=Integer.parseInt(pw[1]);
				int monthday=Integer.parseInt(pw[2]);
				int month=DateUtils.getMonthByDate(startDate);
				int day=DateUtils.getDayByDate(startDate);
				if(yearmonth!=month){//选中日期不是周期日期时
					diary.setMaster(Constants.PO_DIARY_MASTER);
					list.add(diary);
				}else{
					if(monthday!=day){//选中日期不是周期日期时
						diary.setMaster(Constants.PO_DIARY_MASTER);
						list.add(diary);
					}
				}
			}
		}
		if(pStartDate.getTime()>startDate.getTime()||startDate.getTime()>pEndDate.getTime()){//判断选中日期没包含在周期时间段内
			diary.setMaster(Constants.PO_DIARY_MASTER);
			list.add(diary);
		}
		return list;
	}

	/** 方法描述：处理周期性日程（每天）
	 * @param diary 待复制的实体
	 * @param beginDate 周期开始时间
	 * @param endDate 周期结束时间
	 * @param startTime 开始时间 时分秒部分
	 * @param endTime 结束时间时分秒部分
	 * @param subtractionDay 结束时间和开始时间差（天）
	 * @param timeStep 步长，每次增加几天(正整数)
	 * @return List<Diary>
	 * @throws PoException
	 * @author 金峰
	 * @version  2014年5月12日上午10:02:41
	 * @see
	 */
	public List<Diary> getPeriodDateListEveryDay(Diary diary,Date beginDate,
			Date endDate, String startTime,String endTime,int subtractionDay, int timeStep) throws Exception {
		List<Diary> list = new ArrayList<Diary>();
		
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		c_begin.setTime(beginDate);
		c_end.setTime(endDate);
		
		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天
		while (c_begin.before(c_end)) {
			Diary diaryPeriod = new Diary();
			BeanUtils.copyProperties(diary, diaryPeriod);// (源，目标)
			String start=DateUtils.formatDate(c_begin.getTime())+" "+startTime;
			Date end=DateUtils.addOrSubtractDaysReturnDate(c_begin.getTime(),subtractionDay);
			String endDateTmp=DateUtils.formatDate(end)+" "+endTime;
			diaryPeriod.setStarttime(DateUtils.parseDate(start,"yyyy-MM-dd HH:mm:ss"));
			diaryPeriod.setEndtime(DateUtils.parseDate(endDateTmp,"yyyy-MM-dd HH:mm:ss"));
			list.add(diaryPeriod);
			c_begin.add(Calendar.DAY_OF_YEAR, timeStep);
		}
		return list;
	}
	
	/** 方法描述：处理周期性日程（每周）
	 * @param diary 待复制的实体
	 * @param beginDate 周期开始时间
	 * @param endDate 周期结束时间
	 * @param startTime 开始时间 时分秒部分
	 * @param endTime 结束时间时分秒部分
	 * @param weekDay 周几
	 * @param subtractionDay 结束时间和开始时间差（天）
	 * @param timeStep 步长，每次增加几天(正整数)
	 * @return List<Diary>
	 * @throws PoException
	 * @author 金峰
	 * @version  2014年5月12日上午10:11:24
	 * @see
	 */
	public List<Diary> getPeriodDateListWeek(Diary diary,Date beginDate,
			Date endDate, String startTime,String endTime, int weekDay,int subtractionDay ,int timeStep) throws Exception {
		List<Diary> list = new ArrayList<Diary>();
		
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		c_begin.setTime(beginDate);
		c_end.setTime(endDate);
		
		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天
		while (c_begin.before(c_end)) {
			if (c_begin.get(Calendar.DAY_OF_WEEK) == weekDay) {
				Diary diaryPeriod = new Diary();
				BeanUtils.copyProperties(diary, diaryPeriod);// (源，目标)
				String start=DateUtils.formatDate(c_begin.getTime())+" "+startTime;
				Date end=DateUtils.addOrSubtractDaysReturnDate(c_begin.getTime(),subtractionDay);
				String endDateTmp=DateUtils.formatDate(end)+" "+endTime;
				diaryPeriod.setStarttime(DateUtils.parseDate(start,"yyyy-MM-dd HH:mm:ss"));
				diaryPeriod.setEndtime(DateUtils.parseDate(endDateTmp,"yyyy-MM-dd HH:mm:ss"));
				list.add(diaryPeriod);
			}
			c_begin.add(Calendar.DAY_OF_YEAR, timeStep);
		}
		return list;
	}
	
	/** 方法描述：方法描述：处理周期性日程（每月）
	 * @param diary 待复制的实体
	 * @param beginDate 周期开始时间
	 * @param endDate 周期结束时间
	 * @param startTime 开始时间 时分秒部分
	 * @param endTime 结束时间时分秒部分
	 * @param monthDay 每月第几天
	 * @param subtractionDay 结束时间和开始时间差（天）
	 * @param timeStep 步长，每次增加几天(正整数)
	 * @return List<Diary>
	 * @throws PoException
	 * @author 金峰
	 * @version  2014年5月12日上午11:07:46
	 * @see
	 */
	public List<Diary> getPeriodDateListMonth(Diary diary,Date beginDate,
			Date endDate, String startTime,String endTime,int monthDay ,int subtractionDay, int timeStep) throws Exception {
		List<Diary> list = new ArrayList<Diary>();
		
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		c_begin.setTime(beginDate);
		c_end.setTime(endDate);
		
//		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天
		while (c_begin.getTime().compareTo(c_end.getTime()) <= 0) {// 直到两个时间相同
			if (c_begin.get(Calendar.DAY_OF_MONTH) == monthDay) {
				Diary diaryPeriod = new Diary();
				BeanUtils.copyProperties(diary, diaryPeriod);// (源，目标)
				String start=DateUtils.formatDate(c_begin.getTime())+" "+startTime;
				Date end=DateUtils.addOrSubtractDaysReturnDate(c_begin.getTime(),subtractionDay);
				String endDateTmp=DateUtils.formatDate(end)+" "+endTime;
				diaryPeriod.setStarttime(DateUtils.parseDate(start,"yyyy-MM-dd HH:mm:ss"));
				diaryPeriod.setEndtime(DateUtils.parseDate(endDateTmp,"yyyy-MM-dd HH:mm:ss"));
				list.add(diaryPeriod);
			}
			c_begin.add(Calendar.DAY_OF_YEAR, timeStep);
		}
		return list;
	}
	
	/** 方法描述：方法描述：处理周期性日程（每年）
	 * @param diary 待复制的实体
	 * @param beginDate 周期开始时间
	 * @param endDate 周期结束时间
	 * @param startTime 开始时间 时分秒部分
	 * @param endTime 结束时间时分秒部分
	 * @param month 月份
	 * @param monthDay 每月第几天
	 * @param subtractionDay 结束时间和开始时间差（天）
	 * @param timeStep 步长，每次增加几天(正整数)
	 * @return List<Diary>
	 * @throws PoException
	 * @author 金峰
	 * @version  2014年5月12日上午11:07:38
	 * @see
	 */
	public List<Diary> getPeriodDateListYear(Diary diary,Date beginDate,
			Date endDate, String startTime,String endTime, int month, int monthDay,int subtractionDay, int timeStep)
			throws Exception {
		List<Diary> list = new ArrayList<Diary>();
		
		Calendar c_begin = new GregorianCalendar();
		Calendar c_end = new GregorianCalendar();
		c_begin.setTime(beginDate);
		c_end.setTime(endDate);
		
		c_end.add(Calendar.DAY_OF_YEAR, 1); // 结束日期下滚一天是为了包含最后一天
		int MONTH=month-1;//迁就Calendar.MONTH取值从零开始
		while (c_begin.getTime().compareTo(c_end.getTime()) <= 0) {// 直到两个时间相同
			if (c_begin.get(Calendar.MONTH) == MONTH
					&& c_begin.get(Calendar.DAY_OF_MONTH) == monthDay) {
				Diary diaryPeriod = new Diary();
				BeanUtils.copyProperties(diary, diaryPeriod);// (源，目标)
				String start=DateUtils.formatDate(c_begin.getTime())+" "+startTime;
				Date end=DateUtils.addOrSubtractDaysReturnDate(c_begin.getTime(),subtractionDay);
				String endDateTmp=DateUtils.formatDate(end)+" "+endTime;
				diaryPeriod.setStarttime(DateUtils.parseDate(start,"yyyy-MM-dd HH:mm:ss"));
				diaryPeriod.setEndtime(DateUtils.parseDate(endDateTmp,"yyyy-MM-dd HH:mm:ss"));
				list.add(diaryPeriod);
			}
			c_begin.add(Calendar.DAY_OF_YEAR, timeStep);
		}
		return list;
	}

	/** 方法描述：下属人员树
	 * @return List<Department>
	 * @author 金峰
	 * @version  2014年5月14日下午1:34:28
	 * @see
	 */
	@Override
//	public List<User> queryForUnderlingTree() throws PoException {
	public List<Department> queryForUnderlingTree() throws PoException {
		User user = new User();
		user.setId(SystemSecurityUtils.getUser().getId());
//		return serService.queryUserByLeader(user);
		List<User> ul=serService.queryUserByLeader(user);
		String ids="";
		if(!ul.isEmpty()){
			for(User u:ul){
				ids+=u.getId()+",";
			}
			ids=ids.substring(0,ids.length()-1);
			List<Department> list = serService.queryUserTreeByIds(ids); 
			return list;
		}else{
			List<Department> list = new ArrayList<Department>();  
			return list;
		}
	}

	/** 方法描述：关联查询日程表和批注表
	 * @return List<Diary> 
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日下午4:45:40
	 * @see
	 */
	@Override
	public List<Diary> queryDiaryAnno(Diary diary) throws Exception {
		return diaryDao.queryDiaryAnno(diary);
	}
	
	/** 方法描述：日程汇总分页查询
	 * @return PageManager
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014年11月19日
	 * @see
	 */
	@Override
	public PageManager queryDiarySummary(Diary diary,PageManager page) throws Exception {
		return diaryDao.queryByPage(diary, page,SQL_QUERY_DIARY_COUNT_SUMMARY,SQL_QUERY_DIARY_SUMMARY);
	}

	/** 方法描述：查询日程接口供日志管理中使用
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日上午11:34:35
	 * @see
	 */
	@Override
	public List<Diary> queryInterface(Diary diary) throws Exception {
		return diaryDao.queryInterface(diary);
	}

	/** 方法描述：共享人员树
	 * @return List<Department>
	 * @author 金峰
	 * @version  2014年5月14日下午1:34:28
	 * @see
	 */
	@Override
//	public List<User> queryForShareTree() throws Exception {
	public List<Department> queryForShareTree() throws Exception {
		ControlSide controlSide=new ControlSide();
		controlSide.setTableName("TTY_PO_DIARY");
		controlSide.setUserId(SystemSecurityUtils.getUser().getId());
		String ids=controlSideService.queryAllRangeCreateUserId(controlSide);
//		return serService.queryUserByIds(ids);
		if(ids.equals("")){
			return new ArrayList<Department>();
		}else{
			List<Department> list = serService.queryUserTreeByIds(ids); 
			return list;
		}
	}
	
	/** 方法描述：公开日程领导人员树
	 * @return List<User>
	 * @author 金峰
	 * @version  2014年5月14日下午1:34:28
	 * @see
	 */
	@Override
//	public List<User> queryForLeadTree() throws Exception {
	public List<Department> queryForLeadTree() throws Exception {
		User user=new User();
		user.setIsLeader(Constants.PO_DIARY_IS_LEADER);//是领导
		user.setOpenCale(Constants.PO_DIARY_OPEN_CALE);//日程公开
		user.setOrgId(SystemSecurityUtils.getUser().getOrgId());
//		return serService.queryAll(user);
//		List<User> ul=serService.queryAll(user);
		List<User> ul=commonService.getLeaderUserByDeptId(user);
		
		String ids="";
		DiaryDelegation diaryDelegation=new DiaryDelegation();
		diaryDelegation.setMandataryId(SystemSecurityUtils.getUser().getId());
		List<DiaryDelegation> ddList=diaryDelegationService.queryAll(diaryDelegation);
		String ddIds="";
		String delIds="";//删除委托关系用
		if(ddList!=null&&!ddList.isEmpty()){
			for(DiaryDelegation t:ddList){
				//判断委托人现在状态还是不是领导 
				User u=serService.getUser(t.getMandatorId());
				if(u.getIsLeader().equals(Constants.PO_DIARY_IS_LEADER)){
					if(ddIds.equals("")){
						ddIds=t.getMandatorId().toString();
					}else{
						ddIds+=","+t.getMandatorId();
					}
				}else{//不是领导删除委托关系 
					if(delIds.equals("")){
						delIds=t.getId().toString();
					}else{
						delIds+=","+t.getId();
					}
				}
			}
			DiaryDelegation dd=new DiaryDelegation();
			dd.setPrimaryKeys(delIds.split(","));
			diaryDelegationService.delete(dd,false);//不是领导删除委托关系 
		}
		if(ul!=null&&!ul.isEmpty()){
			for(User u:ul){
				ids+=u.getId()+",";
			}
			if(!ddIds.equals("")){
				ids=ids+ddIds;//加入当前用户的委托人 即便该委托人不公开日程 被委托人在人员树也可见
				//id滤重-------------begin
				String[]tmp=ids.split(",");
				int len=tmp.length;
				ArrayList<String> tmplist = new ArrayList<String>();
				for(int i=0;i<len;i++){
					tmplist.add(tmp[i]);
				}
				for(int i=0;i<len-1;i++){
					for(int j=i+1; j<len; j++){
						if(tmplist.get(j).equals(tmplist.get(i))){
							tmplist.remove(j);
							j--;
		                    len--;
						}
					}
				}
				ids="";
				for(String id:tmplist){
					ids+=id+",";
				}
				ids=ids.substring(0,ids.length()-1);
				//id滤重-------------end
			}else{
				ids=ids.substring(0,ids.length()-1);
			}
			
			List<Department> list = serService.queryUserTreeByIds(ids); 
			return list;
		}else if(!ddList.isEmpty()){
			List<Department> list = serService.queryUserTreeByIds(ddIds); 
			return list;
		}else{
			List<Department> list = new ArrayList<Department>();  
			return list;
		}
	}

	/** 方法描述：移动端查询列表视图信息
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日上午11:34:35
	 * @see
	 */
	@Override
	public List<Diary> queryForListView4M(String date,String userid,String curPage) throws Exception {
		Diary diary=new Diary();
		
		Long possessorId=Long.parseLong(userid);
		String startDate=date+" 00:00:00";
		String endDate=date+" 23:59:59";
		diary.setStarttime(DateUtils.parseDate(startDate));
		diary.setEndtime(DateUtils.parseDate(endDate));
		diary.setPossessorId(possessorId);
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
		
		PageManager page=new PageManager();
		//page.setPage(Integer.parseInt(curPage));
		page.setPageRows(999999999);
		PageManager page_ = diaryDao.queryByPage(diary, page,SQL_QUERY_DIARY_COUNT_4M,SQL_QUERY_DIARY_4M);
		return (List<Diary>)page_.getData();
	}
	
	/** 方法描述：查询列表视图信息
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日上午11:34:35
	 * @see
	 */
	@Override
	public List<Diary> queryForListView(Diary diary) throws Exception {
		return diaryDao.queryForListView(diary);
	}

	/** 方法描述：查询共享日程
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月26日下午1:31:59
	 * @see
	 */
	@Override
	public List<Diary> queryDiaryShare(Diary diary) throws Exception {
		return diaryDao.queryDiaryShare(diary);
	}

	
	/**
	 * 方法描述：保存批注回复
	 * @param anno
	 * @return
	 * @author 金峰
	 * @version  2014年5月14日下午1:23:04
	 * @see
	 */
	@Override
	public int saveAnnoReply(Anno anno) throws PoException{
		try {
			anno.setAnnoType(Constants.ANNOTYPE_DIARY+"");
			anno.setAnnoDate(DateUtils.getSysDate());
			anno.setAnnoUserId(SystemSecurityUtils.getUser().getId());
			anno.setAnnoUserDepid(SystemSecurityUtils.getUser().getDeptId());
			anno.setByAnnoUserDepid(UserUtils.getUser(anno.getByAnnoUserId()).getDeptId());
			anno.setReadingState(Constants.PO_ANNO_IS_NOT_READ);
			return annoService.save(anno);
		} catch (CustomException e) {
			PoException pe = new PoException(e);
			pe.setLogMsg(MessageUtils.getMessage("JC_OA_PO_011"));
			throw pe;
		}
	}
	
	/**
	 * 方法描述：保存阅读情况
	 * @param readingStatus
	 * @return Integer
	 * @author 金峰
	 * @version  2014年5月22日上午9:49:21
	 * @throws PoException 
	 * @see
	 */
	public Integer savaReadingStatus(ReadingStatus readingStatus) throws Exception{
		return ReadingStatusService.save(readingStatus);
	}

	@Override
	public Map<String, Object> validRemind(String userIds) throws PoException{
		try{
			return interactFacadeService.sendSmsValidate(userIds);
		}catch(IcException ice){
			PoException pe = new PoException(ice);
			pe.setLogMsg(ice.getLogMsg());
			throw pe;
		}
	}
	
	/** 方法描述：根据id串及时间（年月）分页查询日程
	 * @param ids
	 * @param date
	 * @param curPage
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @version  2014年9月15日上午11:23:03
	 * @see
	 */
	@Override
	public List<Diary> queryForListByIds4m(String ids,String date,String curPage) throws Exception{
		Diary diary=new Diary();
		String year=date.substring(0,4);
		String month=date.substring(5,date.length());
		int day=DateUtils.getMonthDays(Integer.parseInt(year),Integer.parseInt(month));
		String startDate=date+"-01 00:00:00";
		String endDate=date+"-"+day+" 23:59:59";
		diary.setStarttime(DateUtils.parseDate(startDate));
		diary.setEndtime(DateUtils.parseDate(endDate));
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
		if(StringUtils.isNotEmpty(ids)){
			diary.setPrimaryKeys(ids.split(","));
		}
		PageManager page=new PageManager();
		page.setPage(Integer.parseInt(curPage));
		
		PageManager page_ = diaryDao.queryByPage(diary, page,SQL_QUERY_DIARY_COUNT_4M,SQL_QUERY_DIARY_4M);
		return (List<Diary>)page_.getData();
	}
	
	/** 方法描述：移动端根据领导id查询列表视图信息
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日上午11:34:35
	 * @see
	 */
	@Override
	public List<Diary> queryForListByLeaderId4M(String date,String leaderId,String curPage) throws Exception {
		Diary diary=new Diary();
		Long possessorId=Long.parseLong(leaderId);
		String year=date.substring(0,4);
		String month=date.substring(5,date.length());
		int day=DateUtils.getMonthDays(Integer.parseInt(year),Integer.parseInt(month));
		String startDate=date+"-01 00:00:00";
		String endDate=date+"-"+day+" 23:59:59";
		diary.setStarttime(DateUtils.parseDate(startDate));
		diary.setEndtime(DateUtils.parseDate(endDate));
		diary.setPossessorId(possessorId);
		diary.setDiaryType(Constants.PO_DIARY_TYPE_SCHEDULE);
		diary.setDeleteFlag(Constants.PO_DIARY_DELETE_FLAG);
		
		PageManager page=new PageManager();
		//page.setPage(Integer.parseInt(curPage));
		page.setPageRows(999999999);

		PageManager page_ = diaryDao.queryByPage(diary, page,SQL_QUERY_DIARY_COUNT_4M,SQL_QUERY_DIARY_4M);
		return (List<Diary>)page_.getData();
	}
}