package com.jc.oa.ic.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.ISetDao;
import com.jc.oa.ic.domain.SetSms;
import com.jc.oa.ic.domain.SmsStatistic;
import com.jc.oa.ic.service.ISetService;
import com.jc.oa.ic.service.ISmsStatisticService;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA V2.0
 * @description 
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年5月15日上午10:49:07
 */
@Service
public class SetServiceImpl extends BaseServiceImpl<SetSms> implements ISetService{

	private ISetDao setDao;
	
	@Autowired
	private ISmsStatisticService smsStatisticService;
	
	public SetServiceImpl(){}
	
	@Autowired
	public SetServiceImpl(ISetDao setDao){
		super(setDao);
		this.setDao = setDao;
	}
	/**
	 * 如下代码以后有用
	 */
	
	/**
	 * 方法描述：保存短信设置时，如果是个人（setType为1时）查询出当月短信统计对应的记录，如果记录不为空，判断设置数量是否大于统计的发送数量
	 * 		     如果大于设置当月剩余数量为0，否则剩余数量=短信设置数-当月发送数量。如果添加的是级别查询当月统计所有记录，根据记录的id得到用户
	 * 		     的级别，如果级别和短信设置的级别相同，再用这些用户id查询短信设置中是否存在该记录如果不存在，判断插入数量是否大于短信统计统计的
	 * 		     发送数量如果大于修改统计剩余数量=短信设置数-当月发送数量，否则为0，保存短信设置
	 * @param setSms
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月10日上午11:16:45
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(SetSms setSms)throws IcException {
		try{
			SmsStatistic smsStatistic = new SmsStatistic();
			smsStatistic.setStatisticsMonthBegin(DateUtils.parseDate(DateFormatUtils.format(new Date(), "yyyy-MM")));
			if(setSms!=null){
				//如果是个人
				if("1".equals(setSms.getSetType())){
					smsStatistic.setUserId(Long.parseLong(setSms.getRankId()));
					//smsStatistic = smsStatisticService.get(smsStatistic);
					List<SmsStatistic> list = smsStatisticService.queryAll(smsStatistic);
					if(list.size()>0){
						for (SmsStatistic smsStatistic2 : list) {
							if(setSms.getMaxnum()<smsStatistic2.getSendNum()){
								smsStatistic2.setResidueNum(0);
							}else{
								smsStatistic2.setResidueNum(setSms.getMaxnum()-smsStatistic2.getSendNum());
							}
							smsStatisticService.update(smsStatistic2);
						}
					}
				//否则为级别
				}else{
					List<SmsStatistic> list = smsStatisticService.queryAll(smsStatistic);
					//循环统计记录如果统计记录用户级别等于设置级别，根据统计记录用户id查询短信设置记录，如果不存在
					//如果不存在设置短信统计记录剩余数量为0,否则短信统计记录剩余条数=短信设置修改的短信条数-相应统计记录中发送条数
					for (SmsStatistic smsStatistic2 : list) {
						User userInfo = UserUtils.getUser(smsStatistic2.getUserId());
						if(setSms.getRankId().equals(userInfo.getLevel())){
							SetSms setSms3 = new SetSms();
							setSms3.setRankId(smsStatistic2.getUserId().toString());
							setSms3 = setDao.get(setSms3);
							if(setSms3==null){
								if(setSms.getMaxnum()<smsStatistic2.getSendNum()){
									smsStatistic2.setResidueNum(0);
								}else{
									smsStatistic2.setResidueNum(setSms.getMaxnum()-smsStatistic2.getSendNum());
								}
								smsStatisticService.update(smsStatistic2);
							}
						}
					}
				}
			}
			//保存到用设置表
			propertyService.fillProperties(setSms,false);
			setDao.save(setSms);
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
			throw se;
		}
		
		return 1;
	}
	
	/**
	 * 方法描述：删除短信设置，根据删除记录id的到短信设置对象，如果setType为1代表个人存储的是userId，根据userId的到用户级别查询短信设置
	 * 		     对象，如果不存在修改该用户当月短信统计中可发送短信为0，如果存在判断其短信设置中设置的可发送短信条数是否大于已发送条数，如果大于
	 * 		     修改短信统计剩余数量=短信设置中设置的可发送短信条数-本月已发送条数，否则修改统计剩余短信条数为0；如果删除是级别查询当月所有统计
	 * 		     记录，得到用户id，根据用户id得到用户级别，如果用户级别和删除级别相等，使用该用户的id统计查询短信设置中是否存在该记录，如果存在
	 * 		     则不作任何处理，如果不存在修改短信统计剩余数量为0。删除短信设置
	 * @param setSms
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月10日上午8:26:35
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delete(SetSms setSms) throws IcException{
		int num = 0;
		try{
			String[] ids = setSms.getPrimaryKeys();
			SetSms setSms1 = new SetSms();
			for (int i = 0; i < ids.length; i++) {
				setSms.setId(Long.parseLong(ids[0]));
				//根据删除记录id的到短信设置对象
				setSms1 = setDao.get(setSms);
			}
			SmsStatistic smsStatistic = new SmsStatistic();
			smsStatistic.setStatisticsMonthBegin(DateUtils.parseDate(DateFormatUtils.format(new Date(), "yyyy-MM")));
			if(setSms1!=null){
				//删除的是个人，要查看短信设置中该级别记录是否为空，如果不为空，判断得到短信设置中短信可发送数量与该用户统计记录中发送数量差
				//如果大于0：短信统计记录剩余条数=短信设置修改的短信条数-相应统计记录中发送条数，否则为0
				if("1".equals(setSms1.getSetType())){
					User userInfo = UserUtils.getUser(Long.parseLong(setSms1.getRankId()));
					SetSms setSms2 = new SetSms();
					if(userInfo.getLevel()!=null){
						setSms2.setRankId(userInfo.getLevel());
						setSms2 = setDao.get(setSms2);
					}else{
						setSms2 = null;
					}
					smsStatistic.setUserId(Long.parseLong(setSms1.getRankId()));
					List<SmsStatistic> list = smsStatisticService.queryAll(smsStatistic);
					if(list.size()>0){
						for (SmsStatistic smsStatistic2 : list) {
							//判断删除用户的级别是否为空
							if(setSms2!=null){
								if(setSms2.getMaxnum()<smsStatistic2.getSendNum()){
									smsStatistic2.setResidueNum(0);
								}else{
									smsStatistic2.setResidueNum(setSms2.getMaxnum()-smsStatistic2.getSendNum());
								}
							}else{
								smsStatistic2.setResidueNum(0);
							}
							smsStatisticService.update(smsStatistic2);
						}
					}
				//删除级别
				}else{
					List<SmsStatistic> list = smsStatisticService.queryAll(smsStatistic);
					//遍历统计记录，如果统计记录中存在删除记录相应的记录，设置其剩余数量为0
					for (SmsStatistic smsStatistic2 : list) {
						User userInfo = UserUtils.getUser(smsStatistic2.getUserId());
						if(setSms1.getRankId().equals(userInfo.getLevel())){
							SetSms setSms3 = new SetSms();
							setSms3.setRankId(smsStatistic2.getUserId().toString());
							setSms3 = setDao.get(setSms3);
							if(setSms3==null){
								smsStatistic2.setResidueNum(0);
								smsStatisticService.update(smsStatistic2);
							}
						}
					}
				}
			}
			num = setDao.delete(setSms, true);
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
			throw se;
		}
		return num;
	}
	
	/**
	 * 方法描述：短信设置修改分为：级别修改为个人、个人修改为个人、个人修改为级别和级别修改为级别
	 * @param setSms
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月10日下午7:31:39
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer update(SetSms setSms) throws IcException{
		int num = 0;
		try{
			//短信统计实体类
			SmsStatistic smsStatistic = new SmsStatistic();
			//设置统计时间为当前月
			smsStatistic.setStatisticsMonthBegin(DateUtils.parseDate(DateFormatUtils.format(new Date(), "yyyy-MM")));
			//查询当月统计集合
			List<SmsStatistic> list = smsStatisticService.queryAll(smsStatistic);
			if(setSms!=null){
				//当前为个人，1代表个人
				if("1".equals(setSms.getSetType())){
					//级别改成个人，0代表级别
					if("0".equals(setSms.getStatisticsType())){
						updateLevtoPer(setSms,list);
					//个人改成个人
					}else{
						updatePertoPer(setSms,smsStatistic);
					}
				}else{
					//个人转换级别
					if("1".equals(setSms.getStatisticsType())){
						updatePertoLev(setSms,list);
					//级别转换级别
					}else{
						updateLevtoLev(setSms,list);
					}
				}
			}
			//保存到用设置表
			propertyService.fillProperties(setSms,false);
			setSms.setModifyDateNew(DateUtils.getSysDate());
			num = setDao.update(setSms);
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw se;
		}
		return num;
	}
	
	/**
	 * 方法描述：个人修改为个人
	 * @param setSms
	 * @param list
	 * @param smsStatistic
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月10日下午8:33:45
	 * @see
	 */
	private void updatePertoPer(SetSms setSms,SmsStatistic smsStatistic)throws IcException{
		try{
			//将上一人员短信统计剩余条数设置为0
			if(setSms.getStatisticsRank()!=null&&!"".equals(setSms.getStatisticsRank())){
				smsStatistic.setUserId(Long.parseLong(setSms.getStatisticsRank()));
				List<SmsStatistic> list = smsStatisticService.queryAll(smsStatistic);
				if(list.size()>0){
					for (SmsStatistic smsStatistic2 : list) {
						smsStatistic2.setResidueNum(0);
						smsStatisticService.update(smsStatistic2);
					}
				}
			}
			//修改统计表记录
			SmsStatistic smsStatistic1 = new SmsStatistic();
			smsStatistic1.setStatisticsMonthBegin(DateUtils.parseDate(DateFormatUtils.format(new Date(), "yyyy-MM")));
			smsStatistic1.setUserId(Long.parseLong(setSms.getRankId()));
			List<SmsStatistic> list1 = smsStatisticService.queryAll(smsStatistic1);
			if(list1.size()>0){
				for (SmsStatistic smsStatistic3 : list1) {
					
					if(setSms.getMaxnum()<smsStatistic3.getSendNum()){
						smsStatistic3.setResidueNum(0);
					}else{
						smsStatistic3.setResidueNum(setSms.getMaxnum()-smsStatistic3.getSendNum());
					}
					smsStatisticService.update(smsStatistic3);
				}
			}
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw se;
		}	
		
	}
	
	/**
	 * 方法描述：个人修改为级别
	 * @param setSms
	 * @param list
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月10日下午8:33:22
	 * @see
	 */
	private void updatePertoLev(SetSms setSms,List<SmsStatistic> list)throws IcException{
		try{
			if(setSms.getStatisticsRank()!=null&&!"".equals(setSms.getStatisticsRank())){
				User user = UserUtils.getUser(Long.parseLong(setSms.getStatisticsRank()));;
				//循环遍历统计记录，如果设置的级别和用户统计中记录用户级别相等，根据统计记录用户id查询短信设置表
				//如果记录为空设置短信统计记录剩余条数=短信设置修改的短信条数-相应统计记录中发送条数
				for (SmsStatistic smsStatistic2 : list) {
					User userInfo = UserUtils.getUser(smsStatistic2.getUserId());
					if(setSms.getRankId().equals(userInfo.getLevel())){
						SetSms setSms1 = new SetSms();
						setSms1.setRankId(smsStatistic2.getUserId().toString());
						setSms1 = setDao.get(setSms1);
						if(setSms1==null){
							if(setSms.getMaxnum()-smsStatistic2.getSendNum()>0){
								smsStatistic2.setResidueNum(setSms.getMaxnum()-smsStatistic2.getSendNum());
							}else{
								smsStatistic2.setResidueNum(0);
							}
							smsStatisticService.update(smsStatistic2);
						}
					}
					
				}
				//如果该用户的级别和修改级别相等，查询统计相应记录，如果不为空判断短信设置可发送数量和短信统计发送数量的差
				//如果小于0，设置短信剩余条数为0，更改统计表
				if(setSms.getRankId().equals(user.getLevel())){
					SmsStatistic smsStatistic3 = new SmsStatistic();
					smsStatistic3.setUserId(Long.parseLong(setSms.getStatisticsRank()));
					List<SmsStatistic> list1 = smsStatisticService.queryAll(smsStatistic3);
					if(list.size()>0){
						for (SmsStatistic smsStatistic : list1) {
							if(setSms.getMaxnum()<smsStatistic.getSendNum()){
								smsStatistic.setResidueNum(0);
							}else{
								smsStatistic.setResidueNum(setSms.getMaxnum()-smsStatistic.getSendNum());
							}
							smsStatisticService.update(smsStatistic);
						}
					}
				}
			}
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw se;
		}	
		
	}
	
	/**
	 * 方法描述：级别修改为个人
	 * @param setSms
	 * @param list
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月10日下午8:14:06
	 * @see
	 */
	private void updateLevtoPer(SetSms setSms,List<SmsStatistic> list) throws IcException{
		try{	
			for (SmsStatistic smsStatistic2 : list) {
				//判断当前个人（即用户id）是否等于统计记录的id
				//1：如果等于修改相应统计记录中的剩余条数=短信设置修改的短信条数-相应统计记录中发送条数
				//2:如果不相等通过统计记录用户id查询短信设置表中是否设置可发送条数，如果不存在设置
				//  设置相应统计记录中的剩余条数为0，更新统计表
				//修改统计数记录用户id和被修改用户的rankId相等，修改对应的统计记录的剩余数量为修改时输入数量
				if(setSms.getRankId().equals(smsStatistic2.getUserId().toString())){
					if(setSms.getMaxnum()-smsStatistic2.getSendNum()>0){
						smsStatistic2.setResidueNum(setSms.getMaxnum()-smsStatistic2.getSendNum());
					}else{
						smsStatistic2.setResidueNum(0);
					}
				//否则根据用户id查询短信设置中是否存在相应用户，不存在则只设置级别，将短信统计中剩余数量设置为0，其他不变
				}else{
					SetSms setSms3 = new SetSms();
					setSms3.setRankId(smsStatistic2.getUserId().toString());
					setSms3 = setDao.get(setSms3);
					if(setSms3==null){
						smsStatistic2.setResidueNum(0);
					}
				}
				smsStatisticService.update(smsStatistic2);
			}
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw se;
		}
	}
	
	/**
	 * 方法描述：级别修改为级别
	 * @param setSms
	 * @param list
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年6月10日下午8:32:59
	 * @see
	 */
	private void updateLevtoLev(SetSms setSms,List<SmsStatistic> list) throws IcException{
		
		try{
			for (SmsStatistic smsStatistic2 : list) {
				User userInfo = UserUtils.getUser(smsStatistic2.getUserId());
				//获得修改之前的级别，根据统计表中的用户id查询短信设置对象，如果不存在设置短信统计记录剩余数量为0
				if(userInfo.getLevel()!=null&&userInfo.getLevel().equals(setSms.getStatisticsRank())){
					SetSms setSms1 = new SetSms();
					setSms1.setRankId(smsStatistic2.getUserId().toString());
					setSms1 = setDao.get(setSms1);
					if(setSms1==null){
						smsStatistic2.setResidueNum(0);
						smsStatisticService.update(smsStatistic2);
					}
				}
				//如果修改级别和统计记录的级别相等，根据统计表中的用户id查询短信设置对象，
				//如果不存在设置短信统计记录剩余数量为0,否则
				//短信统计记录剩余条数=短信设置修改的短信条数-相应统计记录中发送条数
				if(setSms.getRankId().equals(userInfo.getLevel())){
					SetSms setSms3 = new SetSms();
					setSms3.setRankId(smsStatistic2.getUserId().toString());
					setSms3 = setDao.get(setSms3);
					if(setSms3==null){
						if(setSms.getMaxnum()<smsStatistic2.getSendNum()){
							smsStatistic2.setResidueNum(0);
						}else{
							smsStatistic2.setResidueNum(setSms.getMaxnum()-smsStatistic2.getSendNum());
						}
					}
					
				}
				smsStatisticService.update(smsStatistic2);
			}
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw se;
		}
	
	
	}
}