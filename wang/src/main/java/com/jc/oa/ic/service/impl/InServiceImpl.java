package com.jc.oa.ic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.IInDao;
import com.jc.oa.ic.domain.In;
import com.jc.oa.ic.service.IInService;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.UserUtils;

/**
 * 
 * @title GOA V2.0
 * @description 业务服务类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年5月13日下午9:03:22
 */
@Service
public class InServiceImpl extends BaseServiceImpl<In> implements IInService{
	// TODO
	public static final String MOBILE_IS_NULL="[暂无手机号]";
	
	private IInDao inDao;
	@Autowired
	private IUserService userService;
	public InServiceImpl(){}
	
	@Autowired
	public InServiceImpl(IInDao inDao){
		super(inDao);
		this.inDao = inDao;
	}
	
	/**
	 * 方法描述：查询发信箱
	 * @param in
	 * @param page
	 * @return
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:00:10
	 * @see
	 */
	public PageManager query(In in,PageManager page){
		String outMobile = in.getOriginatorOut();
		if((in.getUserId()!=null&&!"".equals(in.getUserId()))
				&&(outMobile!=null&&!"".equals(outMobile))){
			in.setOriginator(MOBILE_IS_NULL);
		}else if((in.getUserId()!=null&&!"".equals(in.getUserId()))&&(outMobile==null||"".equals(outMobile))){
			//通过用户id从内存读取用户手机号
			String mobile = UserUtils.getUser(Long.valueOf(in.getUserId()).longValue()).getMobile();
			//如果通过用户的到的手机号为空
			if(mobile==null||"".equals(mobile)){
				mobile=MOBILE_IS_NULL;
			//如果通过用户的到的手机号不为空
			}else{
				//判断手输电话号是否为空
				if(in.getOriginator()!=null&&!"".equals(in.getOriginator())){
					//如果通过用户的到的手机号不包含手输的手机号
					if(!mobile.contains(in.getOriginator())){
						mobile=MOBILE_IS_NULL;
					}
				}
			}
			in.setOriginatorOut(mobile);
		}else if((in.getUserId()==null||"".equals(in.getUserId()))&&(outMobile!=null&&!"".equals(outMobile))){
			if(in.getOriginator()!=null&&!"".equals(in.getOriginator())){
				if(!outMobile.contains(in.getOriginator())){
					outMobile=MOBILE_IS_NULL;
				}
			}
			in.setOriginator(outMobile);
		}
//		//判断收信人是否为空
//		if(in.getUserId()!=null&&!"".equals(in.getUserId())){
//			//通过用户id从内存读取用户手机号
//			String mobile = UserUtils.getUser(Long.parseLong(in.getUserId())).getMobile();
//			//如果通过用户的到的手机号为空
//			if(mobile==null||"".equals(mobile)){
//				mobile=MOBILE_IS_NULL;
//			//如果通过用户的到的手机号不为空
//			}else{
//				//判断手输电话号是否为空
//				if(in.getOriginator()!=null&&!"".equals(in.getOriginator())){
//					//如果通过用户的到的手机号不包含手输的手机号
//					if(!mobile.contains(in.getOriginator())){
//						mobile=MOBILE_IS_NULL;
//					}
//				}
//			}
//			in.setOriginator(mobile);
//		}
		//查询条件中有“_”或则“%”字符的，不做转义处理，sql语句为当做通配符处理。转义处理方式
		if(in.getText()!=null&&!"".equals(in.getText())){
			in.setText(StringUtil.escapeSQLWildcard(in.getText()));
		}
		if(in.getOriginator()!=null&&!"".equals(in.getOriginator())){
			in.setOriginator(StringUtil.escapeSQLWildcard(in.getOriginator()));
		}
		in.setCurrentUserId(SystemSecurityUtils.getUser().getId().toString());
		PageManager pm = inDao.queryByPage(in, page);
//		List<In> list = (List<In>) pm.getData();
//		if(list != null && list.size() > 0){
//			for (In receiveIn : list) {
//				//根据电话号码查询收件人姓名
//				String userName ="";
//				try {
//					userName = getUserName(receiveIn.getOriginator());
//				} catch (IcException e) {
//					e.printStackTrace();
//				}
//				receiveIn.setOriginator(userName);
//			}
//		}
//		pm.setData(list);
		return pm;
	}
	
	/**
	 * 方法描述：根据手机号查询出对应人员名称
	 * @param mobile
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年5月13日下午8:06:02
	 * @see
	 */
	private String getUserName(String mobile)throws IcException{
		User user = new User();
		user.setMobile(mobile);
		try {
			user =  userService.getUser(user);
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_013"));
			throw se;
		}
		if(user != null){
			return user.getDisplayName();
		}
		return mobile;
	}
	
	
	/**
	 * 方法描述： 获取单条记录方法
	 * @param in
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年5月27日下午8:08:07
	 * @see
	 */
	public In get(In in) throws IcException{
		in.setDeleteFlag(0);
		In getIn = inDao.get(in);
		String userName="";
		try {
			 userName = getUserName(getIn.getOriginator());
		} catch (IcException e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_014"));
			throw se;
		}
		getIn.setOriginator(userName);
		return getIn;
	}

}