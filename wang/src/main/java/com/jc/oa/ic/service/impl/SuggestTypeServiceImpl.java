package com.jc.oa.ic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.ISuggestTypeDao;
import com.jc.oa.ic.domain.Suggest;
import com.jc.oa.ic.domain.SuggestType;
import com.jc.oa.ic.service.ISuggestService;
import com.jc.oa.ic.service.ISuggestTypeService;
import com.jc.system.CustomException;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;

/**
 * @title GOA V2.0 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */
@Service
public class SuggestTypeServiceImpl extends BaseServiceImpl<SuggestType> implements ISuggestTypeService{

	private ISuggestTypeDao suggestTypeDao;
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ISuggestService suggestService;
	private String[] ids;
	
	@Autowired
	public SuggestTypeServiceImpl(ISuggestTypeDao suggestTypeDao){
		super(suggestTypeDao);
		this.suggestTypeDao = suggestTypeDao;
	}
	public SuggestTypeServiceImpl(){
	}
	/**
	 * @description 删除方法
	 * @param SuggestType suggestType 实体类
	 * @param String id 被删除数据主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delete(SuggestType suggestType) throws IcException{
		try {
			//判断该类型是否已经有邮箱使用
			Suggest suggest = new Suggest();
			ids = suggestType.getPrimaryKeys();
			if(ids.length > 0){
				for(int i=0;i< ids.length; i++){
					suggest.setSuggestTypeId(Long.parseLong(ids[i]));
					suggest.setDeleteFlag(0);
					if(suggestService.queryAll(suggest).size() != 0){
						IcException ce = new IcException();
						ce.setLogMsg(MessageUtils.getMessage("JC_OA_IC_075"));
						throw ce;
					}
				}
			}
			//保存通用字段
			propertyService.fillProperties(suggestType,false);
			suggestTypeDao.delete(suggestType,true);
		} catch (CustomException e) {
			IcException se = new IcException(e);
			se.setLogMsg(e.getLogMsg());
			throw se;
		}
		return 1;
	}
	
	/**
	 * @description 获取单条记录方法
	 * @param SuggestType suggestType 实体类
	 * @return SuggestType 查询结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	public SuggestType get(SuggestType suggestType)throws IcException{
		try {
			SuggestType newSuggestType = suggestTypeDao.get(suggestType);
			if(newSuggestType != null){
			//根据用户id拼接的字符串去查找对应的用户名称，然后拼成字符串返回到实体类中带入页面
			StringBuffer recipientNames = new StringBuffer("[");
			List<User> userList = userService.queryUserByIds(newSuggestType.getRecipientIds());
				if (userList != null) {
					for (User user : userList) {
						recipientNames.append("{id:").append(user.getId()).append(",");
						recipientNames.append("text:\"").append(user.getDisplayName()).append("\"}");
						recipientNames.append(",");
					}
					if (recipientNames.length() > 0 && recipientNames.charAt(recipientNames.length() - 1) == ',') {
						recipientNames.deleteCharAt(recipientNames.length() - 1);
						recipientNames.append("]");
					}
					newSuggestType.setUserName(recipientNames.toString());
				}
			}
			return newSuggestType;
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_055"));
			throw se;
		}
	}
}