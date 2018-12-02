package com.jc.system.userPhone.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.userPhone.dao.IUserPhoneDao;
import com.jc.system.userPhone.domain.UserPhone;
import com.jc.system.userPhone.service.IUserPhoneService;

/**
 * @title GOA2.0使用的个人组别和公共组别应用
 * @description  业务服务类
 * @author 
 * @version  2014-11-21
 */
@Service
public class UserPhoneServiceImpl extends BaseServiceImpl<UserPhone> implements IUserPhoneService{

	private IUserPhoneDao userPhoneDao;
	
	public UserPhoneServiceImpl(){}
	
	@Autowired
	public UserPhoneServiceImpl(IUserPhoneDao userPhoneDao){
		super(userPhoneDao);
		this.userPhoneDao = userPhoneDao;
	}
	
	public Map<String, Object> valiAndResetUserPhone(UserPhone userPhone,Map<String, Object> resultMap,User user) throws CustomException{
		String imei = userPhone.getImeiNo();
		String deviceModel = userPhone.getDeviceModel();
		userPhone.setImeiNo(null);
		userPhone.setDeviceModel(null);
		List<UserPhone> selectUserPhoneList = userPhoneDao.queryAll(userPhone);
		
		//查询链表一条记录为初始状态
		if(selectUserPhoneList.size() == 1){
			//第一次无记录
			if(selectUserPhoneList.get(0).getId() == null){
				UserPhone newUserPhone = new UserPhone();
				newUserPhone.setUserId(userPhone.getUserId());
				newUserPhone.setDeviceModel(deviceModel);
				newUserPhone.setImeiNo(imei);
				newUserPhone.setStatus("1");
				this.save(newUserPhone);
				return resultMap;
			}
			//有记录 首先查看型号是否匹配，如匹配进行串号验证，无匹配，则新增记录并设置状态为审核
			else{
				if(selectUserPhoneList.get(0).getDeviceModel().equals(deviceModel)){
					if(selectUserPhoneList.get(0).getStatus().equals("1")){
						if(!selectUserPhoneList.get(0).getImeiNo().equals(imei)){
							resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_129"));
							return resultMap;
						}else{
							return resultMap;
						}
					}else if(selectUserPhoneList.get(0).getStatus().equals("0")){
						resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_130"));
						return resultMap;
					}
				}else{
					UserPhone newUserPhone = new UserPhone();
					newUserPhone.setUserId(userPhone.getUserId());
					newUserPhone.setDeviceModel(deviceModel);
					newUserPhone.setImeiNo(imei);
					newUserPhone.setStatus("2");
					this.save(newUserPhone);
					resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_131"));
					return resultMap;
				}
			}
		}else if(selectUserPhoneList.size() > 1){
			for(UserPhone selectUserPhone : selectUserPhoneList){
				if(selectUserPhone.getDeviceModel().equals(deviceModel)){
					if(selectUserPhone.getStatus().equals("2")){
						resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_131"));
						return resultMap;
					}else if(selectUserPhone.getStatus().equals("1")){
						if(!selectUserPhone.getImeiNo().equals(imei)){
							resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_129"));
							return resultMap;
						}else{
							return resultMap;
						}
					}else if(selectUserPhone.getStatus().equals("0")){
						resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_130"));
						return resultMap;
					}
				}
			}
			UserPhone newUserPhone = new UserPhone();
			newUserPhone.setUserId(userPhone.getUserId());
			newUserPhone.setDeviceModel(deviceModel);
			newUserPhone.setImeiNo(imei);
			newUserPhone.setStatus("2");
			this.save(newUserPhone);
			resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_131"));
			return resultMap;
		}
		
		
		//数据库中无对应项
//		if(selectUserPhone == null){
//			//通过验证
//		}else if(!selectUserPhone.getStatus().equals("1")){
//			resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_130"));
//		}else if(selectUserPhone.getImeiNo() != null && selectUserPhone.getImeiNo().length() > 0){
//			if(!imei.equals(selectUserPhone.getImeiNo())){
//				resultMap.put("errormsg", MessageUtils.getMessage("JC_SYS_129"));
//			}
//		}else if(selectUserPhone.getImeiNo() == null || selectUserPhone.getImeiNo().length() == 0){
//			selectUserPhone.setImeiNo(imei);
//			selectUserPhone.setModifyDateNew(DateUtils.getSysDate());
//			selectUserPhone.setModifyUser(user.getId());
//			userPhoneDao.update(selectUserPhone);
//		}
		return resultMap;
	}


	/* (non-Javadoc)
	 * @see com.jc.system.userPhone.service.IUserPhoneService#saveUserPhone(com.jc.system.userPhone.domain.UserPhone)
	 */
	@Override
	public void saveUserPhone(UserPhone userPhone) throws CustomException {
		List<User> userList = userPhone.getUserList();
		
		String [] ids = new String[userList.size()];
		for(int i = 0 ; i < userList.size(); i++){
			ids[i] = userList.get(i).getId().toString();
		}
		userPhone.setPrimaryKeys(ids);
		super.delete(userPhone,false);
		
		List<UserPhone> hasUserPhoneList = new ArrayList<UserPhone>();
		hasUserPhoneList = super.queryAll(new UserPhone());
		
		for(User u : userList){
			boolean flag = true;
			for(UserPhone userP : hasUserPhoneList){
				if(u.getId().equals(userP.getUserId())){
					flag = false;
					break;
				}
			}
			if(flag){
				UserPhone uPhone = new UserPhone();
				uPhone.setUserId(u.getId());
				uPhone.setStatus("1");
				super.save(uPhone);
			}
		}
	}

}