package com.jc.oa.common.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.quartz.CalendarIntervalScheduleBuilder;
import org.quartz.CronScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.DateBuilder.IntervalUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.common.dao.IRemindDao;
import com.jc.oa.common.domain.ControlSide;
import com.jc.oa.common.domain.Remind;
import com.jc.oa.common.service.IRemindService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.system.CustomException;
import com.jc.system.common.service.ICommonService;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.JsonUtil;
import com.jc.system.common.util.ThreadPool;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.ActionLog;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-04-17
 */
@Service
public class RemindServiceImpl extends BaseServiceImpl<Remind> implements IRemindService{

	private IRemindDao remindDao;
	
	@Autowired
	private IInteractFacadeService interactFacadeService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICommonService iCommonService;
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public RemindServiceImpl() {
		
	}
	
	@Autowired
	public RemindServiceImpl(IRemindDao remindDao){
		super(remindDao);
		this.remindDao = remindDao;
	}

    
 
	/*
	 * (non-Javadoc)
	 * @see com.jc.oa.common.service.IRemindService#getNextReminds()
	 */
	@ActionLog(operateModelNm="公共提醒",operateFuncNm="getNextReminds",operateDescribe="获取需要下次提醒")
	public List<Remind> getNextReminds() throws CustomException {
		List<Remind> list = null;
		log.info("开始查询提醒任务:" + new Date().toLocaleString());
		try {
			list = remindDao.getNextRemind();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("查询提醒任务失败:"+ e.getMessage());
			throw new CustomException("获取提醒信息错误,请检测服务器或数据库是否可用。");
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * @see com.jc.oa.common.service.IRemindService#batchUpdate(java.util.List)
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@ActionLog(operateModelNm="公共提醒",operateFuncNm="batchUpdate",operateDescribe="更新提醒")
	public void batchUpdate(List<Remind> list) throws CustomException{
		try {
			log.info("更新下次提醒时间");
			remindDao.batchUpdate(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
			throw new CustomException("更新提醒信息错误,请检测服务器或数据库是否可用。");
		}
	}
	
	/**
	 * 根据业务id和类型获取设定的提示
	 */
	@ActionLog(operateModelNm="公共提醒",operateFuncNm="getRemindByDataIdAndType",operateDescribe="通过类型和业务id获取提醒")
	public Remind getRemindByDataIdAndType(Remind remind) throws CustomException {
		try {
			remind.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
			Remind r = remindDao.getRemindByDataIdAndType(remind);
			if(r != null && (r.getViewEndTime() == null || r.getViewEndTime().length() < 8)) {
				r.setViewEndTimeStr(null);
			}
			if(r != null && ( r.getViewStartTime() == null || r.getViewStartTime().length() < 8)) {
				r.setViewStartTimeStr(null);
			}  
			if(r != null && r.getStartForwardTime() != null) {
				r.setStartForwardTimeValue(setForwardTime(r.getStartForwardTime()));
			}
			if(r != null && r.getEndForwardTime() != null) {
				r.setEndForwardTimeValue(setForwardTime(r.getEndForwardTime()));
			}
			return r;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
		}
		return null;
	}
	
	private int setForwardTime(int forward) {
		int result = 0;
		switch(forward) {
			case 0:
				result = 0;
			break;
			case 1:
				result = 5;
				break;
			case 2:
				result = 10;
				break;
			case 3:
				result = 15;
				break;
			case 4:
				result = 30;
				break;
			case 5:
				result = 60;
				break;
			case 6:
				result = 90;
				break;
			case 7:
				result = 120;
				break;
			case 8:
				result = 150;
				break;
			case 9:
				result = 180;
				break;
			case 10:
				result = 240;
				break;
			case 11:
				result = 480;
				break;
			case 12:
				result = 720;
				break;
			case 13:
				result = 1440;
				break;
			case 14:
				result = 2160;
				break;
			case 15:
				result = 2880;
				break;
			case 16:
				result = 4320;
				break;
			case 17:
				result = 5760;
				break;
			case 18:
				result = 7200;
				break;
			case 19:
				result = 14400;
				break;
		}
		return result;
	}
	
	
	/**
	 * 
	 * @param remind
	 * @return
	 */
	@ActionLog(operateModelNm="公共提醒",operateFuncNm="assembleRemind",operateDescribe="组装提醒内容")
	private Remind assembleRemind(Remind remind) throws Exception{
		if(remind == null || remind.getViewStartTime() == null || "".equals(remind.getViewStartTime().trim())) {
			return  null;
		}
		Date startDate = DateUtils.parseDate(remind.getViewStartTime(), "yyyy-MM-dd HH:mm:ss");
		remind.setViewStartTime(remind.getViewStartTime());
		remind.setNextTime(startDate.getTime());
		Calendar c = Calendar.getInstance();
		c.set(9999, 11, 29, 0, 0, 0);
		if(remind.getViewEndTime() != null && !"".equals(remind.getViewEndTime()) && remind.getViewEndTime().length() > 8) {
			Date endDate = DateUtils.parseDate(remind.getViewEndTime(), "yyyy-MM-dd HH:mm:ss");
			remind.setViewEndTime(remind.getViewEndTime());
			remind.setEndTime(endDate.getTime());
		} else {
			remind.setViewEndTime(null);
			remind.setEndTime(c.getTimeInMillis());
		}
		//remind.setIsActive("1");
		//remind.setRemindedNum(0);
		if(remind.getIntervalHour() == null || remind.getIntervalHour() <= 0) {
			remind.setIntervalHour(0);
		}
		if(remind.getIntervalMinute() == null || remind.getIntervalMinute() <= 0) {
			remind.setIntervalMinute(0);
		}
		/*1 边界提醒 2固定间隔 3 周期日期 4 一次性*/
		//2014-4-25 7:08:00
		switch(remind.getRemindType()) {
			case  Constants.REMIND_SIDE :
				remind.setCronExpression("");
				int startForward = remind.getStartForwardTimeValue();
				int endForward = remind.getEndForwardTimeValue();
				 
				remind.setRemindNum(2);
				if(startForward != 0 && endForward != 0) {
					remind.setNextTime(remind.getNextTime() - startForward * Constants.MILLISECOND_ONE_MINUTE);
					//设定了结束时间
					Long n = remind.getEndTime() - endForward * Constants.MILLISECOND_ONE_MINUTE - remind.getNextTime();
					// 结束前比开始前早了，只开始前提醒一次
					if(n.intValue() <= 0) {
						remind.setRemindNum(1);
					} else {
						//remind.setRemindInterval((int)(n/Constants.MILLISECOND_ONE_MINUTE));
						remind.setRemindInterval(n.intValue());
					}
				}else {
					if(remind.getViewEndTime() == null) {
						remind.setRemindNum(1);
					}
					if(startForward != 0) {
						//没有设定开始前提醒
						remind.setNextTime(remind.getNextTime() - startForward * Constants.MILLISECOND_ONE_MINUTE);
//						remind.setRemindNum(1);
					} 
					if(endForward != 0){
						//没有设定结束前提醒
						remind.setEndTime(remind.getEndTime() - endForward * Constants.MILLISECOND_ONE_MINUTE);
	//					remind.setRemindNum(1);
					}
					if(remind.getEndTime() <= remind.getNextTime()) {
						remind.setEndTime(remind.getNextTime());
					}
					Long n = remind.getEndTime() - remind.getNextTime();
					remind.setRemindInterval(n.intValue());
				}
				break;
			case  Constants.REMIND_INTERVAL :
				remind.setCronExpression("");
				remind.setRemindInterval((remind.getIntervalHour() == null ? 0 : remind.getIntervalHour()) * 60 + (remind.getIntervalMinute() == null ? 0 : remind.getIntervalMinute()));
				break;
			case  Constants.REMIND_CYCLE :
				remind.setCronExpression(assembleCycle(remind));
				break;
			case  Constants.REMIND_ONCE :
				remind.setCronExpression("");
				remind.setRemindNum(1);
				//remind.setViewEndTime(endDate);
				break;
			default:
		}
		if(remind.getRemindedNum() == null) {
			remind.setRemindedNum(0);
		}
		if(remind.getRemindNum() == null) {
			remind.setRemindNum(0);
		}
		if(remind.getIntervalHour() == null) {
			remind.setIntervalHour(0);
		}
		if(remind.getIntervalMinute() == null) {
			remind.setIntervalMinute(0);
		}
		if(remind.getStartForwardTime() == null) {
			remind.setStartForwardTime(0);
		} 
		if(remind.getEndForwardTime() == null) {
			remind.setEndForwardTime(0);
		}
		remind.setCreateUserOrg(SystemSecurityUtils.getUser().getOrgId());
		return remind;
	}
	
	/**
	 * 需要传tablename和dataid
	 */
	public void updateNextTimeToMax(Remind bean) {
		Calendar c = Calendar.getInstance();
		c.set(9999, 11, 29, 0, 0, 0);
		bean.setNextTime(c.getTimeInMillis());
		try {
			remindDao.updateNextTimeToMax(bean);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("更新下次提醒时间到最大出错："+e.getMessage());
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param bean
	 * @throws Exception
	 */
	private void saveRemind(Remind bean) throws Exception {
		log.info("保存提醒信息 dataID=" + bean.getDataId());
		remindDao.deleteRemindByDataIdAndTable(bean);
		// 不存在提醒设置需要新建
		if(!Constants.NO_REMIND.equals(bean.getRemindMode())) {
			//如果页面设置了提醒保存到数据库
			//bean.setIsActive(Constants.BOOKREMARK_USE_START);
			bean.setRemindedNum(0);
			Remind r = assembleRemind(bean);
			if(r != null) {
				super.save(r);
			}
		} 
		/*Remind r = getRemindByDataIdAndType(bean);
		if(r == null) {
			// 不存在提醒设置需要新建
			if(!Constants.NO_REMIND.equals(bean.getRemindMode())) {
				//如果页面设置了提醒保存到数据库
				//bean.setIsActive(Constants.BOOKREMARK_USE_START);
				 super.save(assembleRemind(bean));
			}  
		} else {
			bean.setId(r.getId());
			if(Constants.NO_REMIND.equals(bean.getRemindMode())) {
				//如果页面设置了不提醒，删除以前存的提醒规则
				remindDao.delete(bean);
			} else {
				//更新提醒规则
				bean.setIsActive(Constants.BOOKREMARK_USE_START);
				remindDao.updateRemindByDataIdAndType(assembleRemind(bean));
			}
		}*/
	}
	/**
	 * 保存提醒的json格式数据
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@ActionLog(operateModelNm="公共提醒",operateFuncNm="save+3",operateDescribe="保存公共提醒提醒")
	public Integer save(String remind, long dataId, String tableName) throws CustomException {
		log.info("提醒信息:" + remind);
		try {
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(remind); 
			Remind bean = (Remind) net.sf.json.JSONObject.toBean(jsonObject, Remind.class );
			bean.setDataId(dataId);
			bean.setTableName(tableName);
			if(bean.getControls() != null) {
				bean.setReceiveId(JsonUtil.getJSON(bean.getControls()));
			}
			bean.setIsActive(Constants.BOOKREMARK_USE_START);
			saveRemind(bean);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("保存提醒信息错误:" + e.getMessage());
			CustomException c = new CustomException();
			throw c;
		}
	}
	/**
	 * 保存提醒的json格式数据
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@ActionLog(operateModelNm="公共提醒",operateFuncNm="save+3",operateDescribe="保存公共提醒提醒")
	public Integer saveByActive(String remind, long dataId, String tableName, boolean isActive) throws CustomException {
		log.info("提醒信息:" + remind);
		try {
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(remind); 
			Remind bean = (Remind) net.sf.json.JSONObject.toBean(jsonObject, Remind.class );
			bean.setDataId(dataId);
			bean.setIsActive(isActive ? "1" : "0");
			bean.setTableName(tableName);
			//remindDao.deleteRemindByDataIdAndTable(bean);
			if(bean.getControls() != null) {
				bean.setReceiveId(JsonUtil.getJSON(bean.getControls()));
			}
			saveRemind(bean);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("保存提醒信息错误:" + e.getMessage());
			return 0;
		}
	}
	/**
	 * 保存提醒的json格式数据
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	@ActionLog(operateModelNm="公共提醒",operateFuncNm="save+2",operateDescribe="保存公共提醒")
	public Integer save(String remind, long dataId) throws CustomException {
		
		log.info("提醒信息:" + remind);
		try {
			net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(remind); 
			Remind bean = (Remind) net.sf.json.JSONObject.toBean( jsonObject, Remind.class );
			bean.setDataId(dataId);
			bean.setIsActive(Constants.BOOKREMARK_USE_START);
			saveRemind(bean);
			return 1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error("保存提醒信息错误:" + e.getMessage());
			CustomException c = new CustomException();
			throw c;
		}
	}
	
	
	/*
     * 获取提醒的json格式数据
     * @see com.jc.foundation.service.impl.BaseServiceImpl#get(com.jc.foundation.domain.BaseBean)
     */
	public String getRemindJson(Remind remind){
		Remind result = null;
		try {
			if(remind.getIsActive() == null || "".equals(remind.getIsActive())) {
				remind.setIsActive(Constants.BOOKREMARK_USE_START);
			}  
			result = getRemindByDataIdAndType(remind);
			if(result != null) {
				String receive = result.getReceiveId();
				String info = JsonUtil.getJSON(result);
				log.info("获取的提醒信息:" + info);
				return info;
			} else {
				return "";
			}
		} catch (Exception e) {
			log.error(remind.getTableName() + ":获取提醒信息错误" + e.getMessage());
			return "";
		}
	}
	
	
	/**
	 * @description 组装提醒周期的cron表达式
	 *  cron例子说明
	 	 * 0 9 9 ? * 1-7 每天9点9分运行
		 * 0 9 9 ? * 1,2 每个周日周一9点9分运行
		 * 0 9 9 29 * ? 每月29号9点9分运行
		 * 0 9 9 L * ? 每月最后一天9点9分运行
		 * 0 9 9 ? * 2#3 每月第三个星期一9点9分运行
		 * 0 9 9 ? * 3L 每月最后一个星期二9点9分运行
		 * 0 9 9 29 12 ? *每年12月29号9点9分运行
		 * 
		 * 0 9 9 ? 11 3#2 * 每年11月第二个星期二9点9分执行
		 *  0 9 9 ? 11 4L *每年11月最后一个星期三9点9分执行
	 * @param Remind remind 实体类
	 * @return String cron表达式
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	private String assembleCycle(Remind remind) {
		String viewCycle = remind.getViewCycle();
		String cronExpression = "0 " + remind.getIntervalMinute() + " " + remind.getIntervalHour();
		//1100000
		switch(remind.getCycle()) {
			case  Constants.CYCLE_DAY :
				cronExpression += " ? * 1-7";
				break;
			case  Constants.CYCLE_WEEK :
				cronExpression += " ? * ";
				for(int i = 0; i < viewCycle.length(); i++) {
					char c = viewCycle.charAt(i);
					//根据选中的周组装 cron表单式
					if(Constants.SELECTED_INDEX_ONE.equals(String.valueOf(c))) {
						cronExpression += (i + 1) + ",";
					}
				}
				cronExpression = cronExpression.substring(0, cronExpression.length() - 1);
				break;
			case  Constants.CYCLE_MONTH :
				//获取提醒周期为月时选中的是第几个radio
				String monthCycle = String.valueOf(viewCycle.charAt(0));
				if(Constants.SELECTED_INDEX_ONE.equals(monthCycle)) {
					//每月几号执行
					//48～57号为0～9十个阿拉伯数字；65～90号为26个大写英文字母，97～122号为26个小写英文字母
					char day = viewCycle.charAt(1);
					cronExpression += " " + getIntFromChar(day) + " * ?";
				} else if(Constants.SELECTED_INDEX_TWO.equals(monthCycle)) {
					//每月最后一天执行
					cronExpression += " L * ?";
				} else if(Constants.SELECTED_INDEX_THREE.equals(monthCycle)) {
					String i = String.valueOf(viewCycle.charAt(1));
					//选择最后一个星期执行
					if(Constants.SELECTED_INDEX_FOUR.equals(i)) {
						//选中下拉框中的选项为“最后一个”
						cronExpression += " ? * " + viewCycle.charAt(2) + "L";
					} else {
						//选择第几个星期几执行
						cronExpression += " ? * " + viewCycle.charAt(2) + "#" + i;
					}
				}
				break;
			case  Constants.CYCLE_YEAR :
				String yearCycle = String.valueOf(viewCycle.charAt(0));
				if(Constants.SELECTED_INDEX_ONE.equals(yearCycle)) {
					//每年几月几日执行
					char month = viewCycle.charAt(1);
					char day = viewCycle.charAt(2);
					cronExpression += (" " + getIntFromChar(day) + " " + getIntFromChar(month) + " ? *");
				} else {
					//
					String n = String.valueOf(viewCycle.charAt(2));
					cronExpression += " ? " + getIntFromChar(viewCycle.charAt(1));
					if(Constants.SELECTED_INDEX_FOUR.equals(n)) {
						//选中下拉框中的选项为“最后一个”
						//表达式的意义为每年那个月的最后一个星期几
						cronExpression += (" " + viewCycle.charAt(3) +"L *");
					} else {
						//表达式的意义为每年那个月的第几（1,2,3）个星期几
						cronExpression += (" " + viewCycle.charAt(3) +"#" + viewCycle.charAt(2) + " *");
					}
				}
				break;
			default:
		}
		log.info("cronExpression : " + cronExpression);
		System.out.println(cronExpression);
		return cronExpression;
	}
	
	

	/**
	 * 将字母转化为数字
	 * @param c
	 * @return
	 */
	private int getIntFromChar(char c) {
		int ascii = c;
		if(ascii > 48 && ascii < 58) {
			return (ascii - 48);
		} else {
			return (ascii - 87);
		}
	}
	
	
	/**
	 * 根据crom表达式获取下次提醒时间
	 * @param remind
	 * @return
	 */
	private long getNextTimeBySchedule(Remind remind) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(remind.getNextTime());
		String ce = remind.getCronExpression();
		Trigger trigger = TriggerBuilder.newTrigger().withSchedule(CronScheduleBuilder.cronSchedule(ce)).build();
		long nextTime = trigger.getFireTimeAfter(c.getTime()).getTime();
		return nextTime;
	}
	private long getNextTimeByInterval(Remind remind) {
		Trigger trigger = TriggerBuilder.newTrigger()
				.withSchedule(CalendarIntervalScheduleBuilder.calendarIntervalSchedule().withInterval(remind.getRemindInterval(), IntervalUnit.MINUTE)).build();
		long nextTime = trigger.getFireTimeAfter(new Date(remind.getNextTime())).getTime();
		return nextTime;
	}
	
	private class SendRemindJob implements Runnable { 
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    private Map<String, String> map = new HashMap<String, String>();
	    private int type = 0;
	    public SendRemindJob(){
	    	map = new HashMap<String, String>();
	    }
	    public void run() { 
	    		try { 
	    			if(map.size() > 0) {
	    				log.info("开始提醒" + sdf.format(new Date()));
	    				if(type == 0) {
	    					interactFacadeService.sendMail(map);
	    				} else {
	    					interactFacadeService.jobSendSms(map);
	    				}
	    			}
	            } catch (Exception ex) { 
	                ex.printStackTrace(); 
	            } 
	            //System.out.println("do something  at:" + sdf.format(new Date())); 
	        } 
	}

	/**根据表名及dataid删除提醒信息
	 * @param remind
	 * @throws Exception
	 */ 
	public void deleteRemindByDataIdAndTable(Remind remind) throws Exception{
		 remindDao.deleteRemindByDataIdAndTable(remind);
	 }

	@Override
	public void remindByMode(List<Remind> list) throws CustomException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Map<String, Object> validRemind(String userIds, String createUser)
			throws IcException {
		// TODO Auto-generated method stub
		return null;
	}
}