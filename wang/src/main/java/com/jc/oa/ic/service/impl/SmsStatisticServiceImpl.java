package com.jc.oa.ic.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.ISmsStatisticDao;
import com.jc.oa.ic.domain.SetSms;
import com.jc.oa.ic.domain.SmsStatistic;
import com.jc.oa.ic.service.ISetService;
import com.jc.oa.ic.service.ISmsStatisticService;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.UserUtils;

/**
 * @title 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-06-05
 */
@Service
public class SmsStatisticServiceImpl extends BaseServiceImpl<SmsStatistic> implements ISmsStatisticService{

	@Autowired
	private ISetService setService;
	
	private ISmsStatisticDao smsStatisticDao;
	
	public SmsStatisticServiceImpl(){}
	
	@Autowired
	public SmsStatisticServiceImpl(ISmsStatisticDao smsStatisticDao){
		super(smsStatisticDao);
		this.smsStatisticDao = smsStatisticDao;
	}
	
	/**
	 * 方法描述： 短信统计保存方法
	 * @param smsStatistic
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月5日下午6:32:41
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(SmsStatistic smsStatistic ) throws IcException{
		SetSms setSms = new SetSms();
		User userInfo =  SystemSecurityUtils.getUser();
		if(userInfo==null){
			 userInfo = UserUtils.getUser(smsStatistic.getUserId());
		}
		userInfo = UserUtils.getUser(userInfo.getId());
		smsStatistic.setUserId(userInfo.getId());
		smsStatistic.setUserLevel(userInfo.getLevel());
		try{
			setSms.setRankId(userInfo.getId().toString());
			//根据用户id获得用户发送短信最大数
			SetSms isHaveSms =  setService.get(setSms);
			//如果不存在
			if(isHaveSms==null){
				userInfo = UserUtils.getUser(userInfo.getId());
				//再通过用户级别查询
				if(userInfo.getLevel()!=null&&!"".equals(userInfo.getLevel())){
					setSms.setRankId(userInfo.getLevel());
					isHaveSms =  setService.get(setSms);
				}
			}
//			if(isHaveSms!=null){
//				smsStatistic.setResidueNum(isHaveSms.getMaxnum()-smsStatistic.getSendNum());
//			}
			if(isHaveSms!=null){
				if(isHaveSms.getMaxnum()-smsStatistic.getSendNum()>0){
					smsStatistic.setResidueNum(isHaveSms.getMaxnum()-smsStatistic.getSendNum());
				}else{
					smsStatistic.setResidueNum(0);
				}
			}else{
				smsStatistic.setResidueNum(0);
			}
			smsStatisticDao.save(smsStatistic);
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg("JC_OA_IC_006");
			throw se;
		}
		return 1;
	}
	
	/**
	 * 方法描述：短信统计分页查询
	 * @param smsStatistic
	 * @param page
	 * @return 
	 * @author 宋海涛
	 * @version  2014年6月5日下午6:31:51
	 * @see
	 */
	public PageManager query(SmsStatistic smsStatistic, PageManager page) {
		//获得当前用户
		String sentDate = "";
		//如果查询条件为空默认查询当月
		if(smsStatistic.getStatisticsMonth()!=null){
			sentDate = DateFormatUtils.format(smsStatistic.getStatisticsMonth(), "yyyy-MM");
		}else{
			sentDate = DateFormatUtils.format(new Date(), "yyyy-MM");
		}
		smsStatistic.setStatisticsMonth(DateUtils.parseDate(sentDate));
		
		PageManager page_ =  dao.queryByPage(smsStatistic, page);
		try{
			List<SmsStatistic>  list =  (List<SmsStatistic>)page_.getData();
			if(list.size()>0){
				for (SmsStatistic smsStatistic2 : list) {
					User userInfo = UserUtils.getUser(smsStatistic2.getUserId());
					if(smsStatistic2.getUserLevel()!=null&&!userInfo.getLevel().equals(smsStatistic2.getUserLevel())
							&&DateFormatUtils.format(smsStatistic2.getStatisticsMonth(), "yyyy-MM").equals(DateFormatUtils.format(new Date(), "yyyy-MM"))){
						SetSms setSms = new SetSms();
						setSms.setDeleteFlag(0);
						setSms.setCreateUserOrg(userInfo.getOrgId());
						setSms.setRankId(smsStatistic2.getUserId().toString());
						//根据用户id获得用户发送短信最大数
						SetSms isHaveSms =  setService.get(setSms);
						//如果不存在
						if(isHaveSms==null){
							//再通过用户级别查询
							if(userInfo.getLevel()!=null&&!"".equals(userInfo.getLevel())){
								setSms.setRankId(userInfo.getLevel());
								setSms.setCreateUserOrg(userInfo.getOrgId());
								setSms.setDeleteFlag(0);
								isHaveSms =  setService.get(setSms);
							}
						}
						if(isHaveSms!=null){
							if(isHaveSms.getMaxnum()-smsStatistic2.getSendNum()>0){
								smsStatistic2.setResidueNum(isHaveSms.getMaxnum()-smsStatistic2.getSendNum());
							}else{
								smsStatistic2.setResidueNum(0);
							}
							
						}else{
							smsStatistic2.setResidueNum(0);
						}
						smsStatistic2.setUserLevel(userInfo.getLevel());
						smsStatisticDao.update(smsStatistic2);
					}else{
						if(smsStatistic2.getResidueNum()==null||smsStatistic2.getResidueNum()<0){
							smsStatistic2.setResidueNum(0);
							smsStatisticDao.update(smsStatistic2);
						}
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return page_;
	}

	/**
	 * 方法描述：当用户级别改变时，发送短信先更新统计表
	 * @param smsStatistic
	 * @param userInfo
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年10月10日下午3:02:46
	 * @see
	 */
	@Override
	public void updateSmsStatistic(SmsStatistic smsStatistic, User userInfo)
			throws IcException {
		try{
			SetSms setSms = new SetSms();
			setSms.setDeleteFlag(0);
			setSms.setCreateUserOrg(userInfo.getOrgId());
			setSms.setRankId(smsStatistic.getUserId().toString());
			//根据用户id获得用户发送短信最大数
			SetSms isHaveSms =  setService.get(setSms);
			//如果不存在
			if(isHaveSms==null){
				//再通过用户级别查询
				if(userInfo.getLevel()!=null&&!"".equals(userInfo.getLevel())){
					setSms.setRankId(userInfo.getLevel());
					setSms.setCreateUserOrg(userInfo.getOrgId());
					setSms.setDeleteFlag(0);
					isHaveSms =  setService.get(setSms);
				}
			}
			if(isHaveSms!=null){
				if(isHaveSms.getMaxnum()-smsStatistic.getSendNum()>0){
					smsStatistic.setResidueNum(isHaveSms.getMaxnum()-smsStatistic.getSendNum());
				}else{
					smsStatistic.setResidueNum(0);
				}
				
			}else{
				smsStatistic.setResidueNum(0);
			}
			smsStatistic.setUserLevel(userInfo.getLevel());
			smsStatisticDao.update(smsStatistic);
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_006"));
			throw se;
		}
	}
	
}