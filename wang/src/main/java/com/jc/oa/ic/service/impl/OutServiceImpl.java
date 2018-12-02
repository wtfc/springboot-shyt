package com.jc.oa.ic.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.IOutDao;
import com.jc.oa.ic.domain.Out;
import com.jc.oa.ic.domain.SetSms;
import com.jc.oa.ic.domain.SmsStatistic;
import com.jc.oa.ic.service.IOutService;
import com.jc.oa.ic.service.ISetService;
import com.jc.oa.ic.service.ISmsStatisticService;
import com.jc.system.DBException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Setting;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.ISettingService;
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA V2.0
 * @description 业务服务类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年5月6日下午7:11:46
 */
@Service
public class OutServiceImpl extends BaseServiceImpl<Out> implements IOutService{

	private IOutDao outDao;
	@Autowired
	private ISetService setService;
	
	@Autowired
	private ISmsStatisticService smsStatisticService;
	
	@Autowired
	private ISettingService settingService;
	
	public OutServiceImpl(){}
	
	@Autowired
	public OutServiceImpl(IOutDao outDao){
		super(outDao);
		this.outDao = outDao;
	}
	//发送信息统计列表查询语句
	public static final String SQL_QUERYALLOUTMESCOUNT= "queryAllOutMesCount";
	//发送信息统计条数查询语句
	public static final String SQL_QUERYALLOUTMES= "queryAllOutMes";
	//发送信息列表查询语句
	public static final String SQL_QUERYOUTMES= "queryOutMes";
	//发送信息条数查询语句
	public static final String SQL_QUERYOUTMESCOUNT= "queryOutMesCount";
	// TODO
	public static final String MOBILE_IS_NULL="[暂无手机号]";
	
	/**
	 * 方法描述：短信保存
	 * @param out
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月7日上午8:42:00
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer sendAndSave(Out out) throws IcException{
		int usersCount = 0;
		Set<String> mobileSet = new HashSet<String>();
		try{
			User userInfo = null;
			if(out.getCreateUser()==null){
				userInfo =  SystemSecurityUtils.getUser();
			}else{
				userInfo = UserUtils.getUser(out.getCreateUser());
			}
		    //短信内容
		    StringBuilder text = new StringBuilder();
		 	text.append(out.getText());
		 	//表示添加签名
		 	if("true".equals(out.getAddName())){
		    	text.append("["+userInfo.getDisplayName()+"]");
		    }
		   
		    //收件人以‘，’分割
			if(out.getUserId() != null && !"".equals(out.getUserId())){
				String[] userIdArray = out.getUserId().split(",");
				//usersCount += userIdArray.length;
				for(int i=0; i<userIdArray.length; i++) {
					//根据用户id获得用户电话号码
					String mobile = getMobile(userIdArray[i]);
					if(mobile!=null&&!"".equals(mobile)){
						mobileSet.add(mobile);
					}
				}
			}
			//外部联系人不为空
			if(out.getOutLinkman()!=null&&!"".equals(out.getOutLinkman())){
				String[] mobiles = out.getOutLinkman().split(",");
				//usersCount += mobiles.length;
				for(int i=0; i<mobiles.length; i++){
					mobileSet.add(mobiles[i]);
				}
			}
			usersCount = mobileSet.size();
			//循环发送短信
			for(String mobile : mobileSet){
		    	this.sendMessage(mobile,out,text.toString());
		    }
			out.setText(text.toString());
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_004"));
			throw se;
		}
		if(usersCount>0){
			statisticSave(out,usersCount);
		}
		
		return 1;
	}
	
	/**
	 * 方法描述：保存或修改统计表中发送和剩余短信数量
	 * @param out
	 * @param usersCount
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月5日下午7:43:33
	 * @see
	 */
	private void statisticSave(Out out,int usersCount) throws IcException{
		
		SmsStatistic smsStatistic = new SmsStatistic();
		String sentDate = "";
		try {
			//是否是定时发送
			if("true".equals(out.getSmmscheduler())&&out.getSentDate()!=null){
		    	sentDate = DateFormatUtils.format(out.getSentDate(), "yyyy-MM");
		    }else{
		    	sentDate = DateFormatUtils.format(new Date(), "yyyy-MM");
		    	
		   	}
			User userInfo = new User();
			//定时任务时可能没有登录用户，得不到用户信息，传一个用户id
			if(out.getCreateUser()==null){
				userInfo = SystemSecurityUtils.getUser();
				smsStatistic.setUserId(userInfo.getId());
			}else{
				userInfo = UserUtils.getUser(out.getCreateUser());
				smsStatistic.setUserId(out.getCreateUser());
			}
			smsStatistic.setCreateUserOrg(userInfo.getOrgId());
			smsStatistic.setStatisticsMonth(DateUtils.parseDate(sentDate));
			//取出统计数据
			smsStatistic = smsStatisticService.get(smsStatistic);
			//如果统计数据存在则修改，不存在新增
			if(smsStatistic!=null){
				userInfo = UserUtils.getUser(userInfo.getId());
				if(smsStatistic.getUserLevel()!=null&&!userInfo.getLevel().equals(smsStatistic.getUserLevel())
						&&DateFormatUtils.format(smsStatistic.getStatisticsMonth(), "yyyy-MM").equals(DateFormatUtils.format(new Date(), "yyyy-MM"))){
					smsStatisticService.updateSmsStatistic(smsStatistic, userInfo);
				}
				smsStatistic.setUserLevel(userInfo.getLevel());
				//(out.getText().length()/70+1)*usersCount发送用户数量与短信数量的乘积，短信默认70字为一条
				if( out.getText().length()%70==0){
					smsStatistic.setSendNum(smsStatistic.getSendNum()+(out.getText().length()/70)*usersCount);
					if(smsStatistic.getResidueNum()-(out.getText().length()/70)*usersCount>0){
						smsStatistic.setResidueNum(smsStatistic.getResidueNum()-(out.getText().length()/70)*usersCount);
					}else{
						smsStatistic.setResidueNum(0);
					}
				}else{
					smsStatistic.setSendNum(smsStatistic.getSendNum()+(out.getText().length()/70+1)*usersCount);
					if(smsStatistic.getResidueNum()-(out.getText().length()/70+1)*usersCount>0){
						smsStatistic.setResidueNum(smsStatistic.getResidueNum()-(out.getText().length()/70+1)*usersCount);
					}else{
						smsStatistic.setResidueNum(0);
					}
				}
				smsStatisticService.update(smsStatistic);
			}else{
				smsStatistic = new SmsStatistic();
				smsStatistic.setStatisticsMonth(DateUtils.parseDate(sentDate));
				smsStatistic.setCreateUserOrg(userInfo.getOrgId());
				smsStatistic.setUserId(userInfo.getId());
				if(out.getText().length()%70==0){
					smsStatistic.setSendNum((out.getText().length()/70)*usersCount);
				}else{
					smsStatistic.setSendNum((out.getText().length()/70+1)*usersCount);
				}
				smsStatisticService.save(smsStatistic);
			}
			
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_006"));
			throw se;
		}
	}
	
	/**
	 * 方法描述： 将发送数据存储到数据库
	 * @param mobile
	 * @param model
	 * @param text
	 * @author 宋海涛
	 * @version  2014年5月7日上午8:43:41
	 * @see
	 */
	private void sendMessage(String mobile,Out model, String text)throws IcException{
		//如果电话号码存在并且符合号码规范
		if(!"".equals(mobile) && mobile.matches("^[1][3-8]+\\d{9}")) {
			Out out = new Out();
			out.setSendType(model.getSendType());
			out.setRecipient(mobile);
			out.setText(text);
			User userInfo = new User();
			if(model.getCreateUser()==null){
				userInfo = SystemSecurityUtils.getUser();
				out.setCreateUserOrg(userInfo.getOrgId());
				out.setCreateUser(userInfo.getId());
			}else{
				userInfo = UserUtils.getUser(model.getCreateUser());
				out.setCreateUserOrg(userInfo.getOrgId());
				out.setCreateUser(model.getCreateUser());
			}
			
			//TODO 此处为短信定时发送使用暂时去掉 需要时将代码注释去掉，删除注释代码下ut.setSentDate(null);  start 
			//SMSScheduler如果等于true设定了定时发送
//		    if("true".equals(model.getSmmscheduler())){
//		    	out.setSentDate(model.getSentDate());
//		    }else{
//		    	out.setSentDate(null);
//		    	
//		   	}
			//TODO 此处为短信定时发送使用暂时去掉 需要时将代码注释去掉，删除注释代码下ut.setSentDate(null);  end
			out.setSentDate(null);
		    out.setType("O");
			out.setWapUrl("");
			out.setWapExpiryDate(DateUtils.parseDate("2050-09-09 00:00:00"));
			out.setWapSignal("H");
			out.setCreateDate(new Date());
			out.setOriginator("");
			out.setEncoding("U");
			out.setStatusReport(0);
			out.setFlashSms(0);
			out.setDstPort(-1);
			out.setSrcPort(-1);
			out.setRefNo(null);
			out.setPriority(0);
			out.setStatus("U");
			out.setErrors(0);
			out.setGatewayId("*");
		    try {
				outDao.save(out);
			} catch (DBException e) {
				IcException se = new IcException(e);
				se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_004"));
				throw se;
			}
			
		}
	}

	/**
	 * 方法描述：根据手机号查询出对应人员名称
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月7日上午8:47:40
	 * @see
	 */
	public String getMobile(String id){
		return UserUtils.getUser(Long.valueOf(id).longValue()).getMobile();
	}

	/**
	 * 方法描述：查询发信箱
	 * @param out
	 * @param page
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月7日上午8:47:58
	 * @see
	 */
	public PageManager queryOut(Out out,PageManager page){
		String outMobile = out.getRecipientOut();
		if((out.getUserId()!=null&&!"".equals(out.getUserId()))
				&&(outMobile!=null&&!"".equals(outMobile))){
			out.setRecipient(MOBILE_IS_NULL);
		}else if((out.getUserId()!=null&&!"".equals(out.getUserId()))&&(outMobile==null||"".equals(outMobile))){
			//通过用户id从内存读取用户手机号
			String mobile = UserUtils.getUser(Long.valueOf(out.getUserId()).longValue()).getMobile();
			//如果通过用户的到的手机号为空
			if(mobile==null||"".equals(mobile)){
				mobile=MOBILE_IS_NULL;
			//如果通过用户的到的手机号不为空
			}else{
				//判断手输电话号是否为空
				if(out.getRecipient()!=null&&!"".equals(out.getRecipient())){
					//如果通过用户的到的手机号不包含手输的手机号
					if(!mobile.contains(out.getRecipient())){
						mobile=MOBILE_IS_NULL;
					}
				}
			}
			out.setRecipient(mobile);
		}else if((out.getUserId()==null||"".equals(out.getUserId()))&&(outMobile!=null&&!"".equals(outMobile))){
			if(out.getRecipient()!=null&&!"".equals(out.getRecipient())){
				if(!outMobile.contains(out.getRecipient())){
					outMobile=MOBILE_IS_NULL;
				}
			}
			out.setRecipient(outMobile);
		}
		//将查询结束时间时分秒设置为23:59:59
		if(out.getOutDateEnd()!=null){
	        out.setOutDateEnd(DateUtils.fillTime(out.getOutDateEnd()));
		}
		//查询条件中有“_”或则“%”字符的，不做转义处理，sql语句为当做通配符处理。转义处理方式
		if(out.getText()!=null&&!"".equals(out.getText())){
			out.setText(StringUtil.escapeSQLWildcard(out.getText()));
		}
		if(out.getRecipient()!=null&&!"".equals(out.getRecipient())){
			out.setRecipient(StringUtil.escapeSQLWildcard(out.getRecipient()));
		}
		User userInfo  = SystemSecurityUtils.getUser();
		out.setCurrentUserId(userInfo.getId().toString());
		out.setCreateUserOrg(userInfo.getOrgId());
		PageManager pm = outDao.queryByPage(out, page, SQL_QUERYOUTMESCOUNT, SQL_QUERYOUTMES);
//		List<Out> list = (List<Out>) pm.getData();
//		if(list != null && list.size() > 0){
//			for (Out sendOut : list) {
//				//根据电话号码查询收件人姓名
//				if(sendOut.getReceiveUser()!=null&&!"".equals(sendOut.getReceiveUser())){
//					sendOut.setRecipient(sendOut.getReceiveUser());
//				}
//			}
//		}
//	    pm.setData(list);
		return pm;
	}
	
	/**
	 * 方法描述：查询用户是否设置发送短信条数，
	 * 		     如果存在：设置定时发送时间根据时间取出该月发送的数量加上本次发送用户数量和（短信长度）/70的乘积（短信70字符为一条）。和短信设置的数量作比较
	 * 		     如果不存在：该用户没有设置可发送短信数量
	 * @return 
	 * @author 宋海涛
	 * @version  2014年5月7日上午8:48:50
	 * @throws Exception 
	 * @see
	 */
	public Map<String, Object> isHaveSendMes(Out out) throws IcException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获得当前用户
		User userInfo = SystemSecurityUtils.getUser();
		try{
			SetSms setSms = new SetSms();
			setSms.setDeleteFlag(0);
			setSms.setCreateUserOrg(userInfo.getOrgId());
			setSms.setRankId(userInfo.getId().toString());
			//根据用户id获得用户发送短信最大数
			SetSms isHaveSms =  setService.get(setSms);
			//如果不存在
			if(isHaveSms==null){
				//再通过用户级别查询
				userInfo = UserUtils.getUser(userInfo.getId());
				if(userInfo.getLevel()!=null&&!"".equals(userInfo.getLevel())){
					setSms.setRankId(userInfo.getLevel());
					setSms.setDeleteFlag(0);
					setSms.setCreateUserOrg(userInfo.getOrgId());
					isHaveSms =  setService.get(setSms);
				}
			}
			if(isHaveSms!=null&&isHaveSms.getMaxnum()>=0){
				//本次发送短信数量
				int userCount = 0;
				 //收件人以‘，’分割
				if(out.getUserId() != null && !"".equals(out.getUserId())){
					String[] userIdArray = out.getUserId().split(",");
					//除去没有电话的用户
					for(int i=0; i<userIdArray.length; i++) {
						//根据用户id获得用户电话号码
						String mobile = getMobile(userIdArray[i]);
						if(mobile!=null&&!"".equals(mobile)){
							userCount+=1;
						}
					}
				}
				if(out.getOutLinkman()!=null&&!"".equals(out.getOutLinkman())){
					userCount += out.getOutLinkman().split(",").length;
				}
				//发送用户数量与短信数量的乘积，短信默认70字为一条
				if(out.getText().length()%70==0){
					userCount = (out.getText().length()/70)*userCount;
				}else{
					userCount = (out.getText().length()/70+1)*userCount;
				}
				SmsStatistic smsStatistic = new SmsStatistic();
				String sentDate = "";
				if(out.getSentDate()!=null){
					sentDate = DateFormatUtils.format(out.getSentDate(), "yyyy-MM");
				}else{
					sentDate = DateFormatUtils.format(new Date(), "yyyy-MM");
				}
				smsStatistic.setUserId(userInfo.getId());
				smsStatistic.setCreateUserOrg(userInfo.getOrgId());
				smsStatistic.setStatisticsMonth(DateUtils.parseDate(sentDate));
				//得到统计发送的数量
				smsStatistic = smsStatisticService.get(smsStatistic);
				//得到当前用户所有发送短信的集合
		        Integer count  = 0;
		        if(smsStatistic!=null){
		        	 count = smsStatistic.getSendNum();
		        }
				//如果当前用户已发送的短信数已超过最大数量
				if(count>=isHaveSms.getMaxnum()){
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_IC_007"));
					resultMap.put(GlobalContext.RESULT_SUCCESS, false);
				//如果本次发送数量大于剩余数量
				}else if((count+userCount)>isHaveSms.getMaxnum()){
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_IC_008"));
					resultMap.put(GlobalContext.RESULT_SUCCESS, false);
				}
			}else{
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_IC_009"));
				resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			}
			return resultMap;
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_010"));
			throw se;
		}
		
	}
	/**
	 * 方法描述：查看发送用户用是否存在没有电话号的情况（包括都没有电话的情况）
	 * @param out
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日上午10:47:59
	 * @see
	 */
	@Override
	public Map<String, Object> isHaveMobile(Out out){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userNames = "";
		String userIds = out.getUserId();
		if(userIds != null && !"".equals(userIds)){
			String[] userIdArray = userIds.split(",");
			
			for(int i=0; i<userIdArray.length; i++) {
				//根据用户id获得用户电话号码
				String mobile =getMobile(userIdArray[i]);
				//当用户中存没有电话信息是设置flag=1调出循环
				if(mobile==null||"".equals(mobile)){
					userNames += UserUtils.getUser(Long.parseLong(userIdArray[i])).getDisplayName()+",";
				}
			}
			if(userNames.length()>0){
				userNames = userNames.substring(0, userNames.length()-1);
			}
			
		}
		
		if(userNames!=null&&!"".equals(userNames)){
			if(userIds.split(",").length==userNames.split(",").length&&out.getOutLinkman().length()>0){
				resultMap.put(GlobalContext.RESULT_SUCCESS,true);
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,MessageUtils.getMessage("JC_OA_IC_011", new Object[]{userNames}));
			}else if(userIds.split(",").length==userNames.split(",").length&&out.getOutLinkman().length()<=0){
				resultMap.put(GlobalContext.RESULT_SUCCESS,false);
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_IC_070"));
			}else{
				resultMap.put(GlobalContext.RESULT_SUCCESS, true);
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,MessageUtils.getMessage("JC_OA_IC_011", new Object[]{userNames}));
			}
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：外部调用查看发送用户用是否存在没有电话号的情况（包括都没有电话的情况）
	 * @param out
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日上午10:47:59
	 * @see
	 */
	@Override
	public Map<String, Object> isHaveMobileFacade(Out out){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		String userNames = "";
		String userIds = out.getUserId();
		if(userIds != null && !"".equals(userIds)){
			String[] userIdArray = userIds.split(",");
			
			for(int i=0; i<userIdArray.length; i++) {
				//根据用户id获得用户电话号码
				String mobile =getMobile(userIdArray[i]);
				//当用户中存没有电话信息是设置flag=1调出循环
				if(mobile==null||"".equals(mobile)){
					userNames += UserUtils.getUser(Long.parseLong(userIdArray[i])).getDisplayName()+",";
				}
			}
			if(userNames.length()>0){
				userNames = userNames.substring(0, userNames.length()-1);
			}
			
		}
		
		if(userNames!=null&&!"".equals(userNames)){
			if(userIds.split(",").length==userNames.split(",").length){
				resultMap.put(GlobalContext.RESULT_SUCCESS,true);
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE, MessageUtils.getMessage("JC_OA_IC_083"));
			}else{
				resultMap.put(GlobalContext.RESULT_SUCCESS, true);
				resultMap.put(GlobalContext.RESULT_SUCCESSMESSAGE,MessageUtils.getMessage("JC_OA_IC_011", new Object[]{userNames}));
			}
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：查看短信功能是否开启
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月6日下午4:20:06
	 * @see
	 */
	@Override
	public Map<String, Object> mesFunctionIsOpen() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Setting settingvo = new Setting();
		settingvo = settingService.getOne(settingvo);
		if(settingvo!=null){
			if(("0").equals(settingvo.getIsMsgService())){
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,MessageUtils.getMessage("JC_OA_IC_012") );
				resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			}else{
				resultMap.put(GlobalContext.RESULT_SUCCESS, true);
			}
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：查询短信前缀
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月6日下午4:17:52
	 * @see
	 */
	@Override
	public Map<String,Object> addmSgPrefix() {
		Map<String,Object> map = new HashMap<String, Object>();;
		Setting settingvo = new Setting();
		settingvo = settingService.getOne(settingvo);
		String mSgPrefixName = "";
		if(settingvo!=null){
			mSgPrefixName = settingvo.getMsgPrefix();
		}
		map.put("mSgPrefixName", mSgPrefixName);
		return map;
	}
	
	/**
	 * 方法描述：添加签名
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月12日下午2:31:25
	 * @see
	 */
	@Override
	public Integer addName() {
		User userInfo = SystemSecurityUtils.getUser();
		int nameLength = userInfo.getDisplayName().length();
		nameLength = nameLength+2;
		return nameLength;
	}
	
	/**
	 * 方法描述：外部调用判断是否存在可发送短信
	 * @param out
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月12日下午2:32:37
	 * @see
	 */
	public Map<String, Object> isHaveSendMessage(Out out) throws IcException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//获得当前用户
		User userInfo = null;
		if(out.getCreateUser()==null){
			userInfo =  SystemSecurityUtils.getUser();
		}else{
			userInfo = UserUtils.getUser(out.getCreateUser());
		}
		try{
			SetSms setSms = new SetSms();
			setSms.setDeleteFlag(0);
			setSms.setCreateUserOrg(userInfo.getOrgId());
			setSms.setRankId(userInfo.getId().toString());
			//根据用户id获得用户发送短信最大数
			SetSms isHaveSms =  setService.get(setSms);
			//如果不存在
			if(isHaveSms==null){
				userInfo = UserUtils.getUser(userInfo.getId());
				//再通过用户级别查询
				if(userInfo.getLevel()!=null&&!"".equals(userInfo.getLevel())){
					setSms.setRankId(userInfo.getLevel());
					setSms.setDeleteFlag(0);
					setSms.setCreateUserOrg(userInfo.getOrgId());
					isHaveSms =  setService.get(setSms);
				}
			}
			if(isHaveSms!=null&&isHaveSms.getMaxnum()>=0){
				//本次发送短信数量
				int userCount = 0;
				 //收件人以‘，’分割
				if(out.getUserId() != null && !"".equals(out.getUserId())){
					String[] userIdArray = out.getUserId().split(",");
					//除去没有电话的用户
					for(int i=0; i<userIdArray.length; i++) {
						//根据用户id获得用户电话号码
						String mobile = getMobile(userIdArray[i]);
						if(mobile!=null&&!"".equals(mobile)){
							userCount+=1;
						}
					}
				}
				if(out.getOutLinkman()!=null&&!"".equals(out.getOutLinkman())){
					userCount += out.getOutLinkman().split(",").length;
				}
				//发送用户数量与短信数量的乘积，短信默认70字为一条
				//userCount = (out.getText().length()/70+1)*userCount;
				SmsStatistic smsStatistic = new SmsStatistic();
				String sentDate = "";
				if(out.getSentDate()!=null){
					sentDate = DateFormatUtils.format(out.getSentDate(), "yyyy-MM");
				}else{
					sentDate = DateFormatUtils.format(new Date(), "yyyy-MM");
				}
				smsStatistic.setUserId(userInfo.getId());
				smsStatistic.setCreateUserOrg(userInfo.getOrgId());
				smsStatistic.setStatisticsMonth(DateUtils.parseDate(sentDate));
				//得到统计发送的数量
				smsStatistic = smsStatisticService.get(smsStatistic);
				//得到当前用户所有发送短信的集合
		        Integer count  = 0;
		        if(smsStatistic!=null){
		        	 count = smsStatistic.getSendNum();
		        }
				//如果当前用户已发送的短信数已超过最大数量
				if(count>=isHaveSms.getMaxnum()){
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_IC_007"));
					resultMap.put(GlobalContext.RESULT_SUCCESS, false);
				//如果本次发送数量大于剩余数量
				}else if((count+userCount)>isHaveSms.getMaxnum()){
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_IC_008"));
					resultMap.put(GlobalContext.RESULT_SUCCESS, false);
				}
			}else{
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, MessageUtils.getMessage("JC_OA_IC_009"));
				resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			}
			return resultMap;
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_010"));
			throw se;
		}
		
	}
	
	/**
	 * 方法描述：外部调用判断是否开启短信功能
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月6日下午4:20:06
	 * @see
	 */
	@Override
	public Map<String, Object> messageFunctionIsOpen() {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Setting settingvo = new Setting();
		settingvo = settingService.getOne(settingvo);
		if(settingvo!=null){
			if(("0").equals(settingvo.getIsMsgService())){
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE,MessageUtils.getMessage("JC_OA_IC_012") );
				resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			}
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> sendValidate(Out out)throws IcException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//短信功能是否开启
			resultMap = messageFunctionIsOpen();
			if(resultMap.size() <= 0){
				//是否存在可发送短信
				resultMap = isHaveSendMes(out);
				if (resultMap.size() <= 0) {
					//是否存在无电话号用户
					resultMap = isHaveMobile(out);
					if(resultMap.size()<=0){
						//成功
						resultMap.put(GlobalContext.RESULT_SUCCESS, "success");
					}
				}
			}
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_010"));
			throw se;
		}
		return resultMap;
	}
}