package com.jc.oa.ic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.IConGroupRDao;
import com.jc.oa.ic.dao.IContactsGroupDao;
import com.jc.oa.ic.domain.ConGroupR;
import com.jc.oa.ic.domain.Contacts;
import com.jc.oa.ic.domain.ContactsGroup;
import com.jc.oa.ic.service.IContactsGroupService;
import com.jc.system.CustomException;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;

/**
 * @title 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-08
 */
@Service
public class ContactsGroupServiceImpl extends BaseServiceImpl<ContactsGroup> implements IContactsGroupService{

	private IContactsGroupDao contactsGroupDao;
	@Autowired
	private IConGroupRDao groupRDao;
	public ContactsGroupServiceImpl(){}
	
	@Autowired
	public ContactsGroupServiceImpl(IContactsGroupDao contactsGroupDao){
		super(contactsGroupDao);
		this.contactsGroupDao = contactsGroupDao;
	}
	/**
	 * 联系组保存
	 * @param contactsGroup
     * @return
     * @throws CustomException
     */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(ContactsGroup contactsGroup) throws IcException{
		try {
			// 判断联系人组是否存在
			ContactsGroup contactG = new ContactsGroup();
			contactG.setGroupName(contactsGroup.getGroupName());
			contactG.setCreateUser(SystemSecurityUtils.getUser().getId());
			contactG = contactsGroupDao.get(contactG) ;
			if (contactG != null) {
				IcException ic = new IcException();
				ic.setLogMsg(MessageUtils.getMessage("JC_OA_IC_018"));
				throw ic;
			}
			
			propertyService.fillProperties(contactsGroup,false);
			//保存联系人组
			contactsGroupDao.save(contactsGroup);
		} 
		catch (IcException e){
			e.setLogMsg(MessageUtils.getMessage("JC_OA_IC_018"));
			throw e;
	    }
		catch (Exception e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_SYS_002"), e);
		}
		return 1;		
	}
	/**
	 * 方法描述：修改联系人与联系人分组
	 * @param contacts
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version  2014年5月20日下午1:05:30
	 * @see
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateContactsGroup(ContactsGroup contactsGroup) throws IcException{
		try {
			if(!contactsGroup.getGroupNameOld().equals(contactsGroup.getGroupName())){
				// 判断联系人组是否存在
				ContactsGroup contactG = new ContactsGroup();
				contactG.setGroupName(contactsGroup.getGroupName());
				contactG.setCreateUser(SystemSecurityUtils.getUser().getId());
				contactG = contactsGroupDao.get(contactG);
				if (contactG != null) {
					IcException ic = new IcException();
					ic.setLogMsg(MessageUtils.getMessage("JC_OA_IC_018"));
					throw ic;
				}
			}
			propertyService.fillProperties(contactsGroup, true);
			contactsGroupDao.update(contactsGroup); 
		}
		catch (IcException e){
			e.setLogMsg(MessageUtils.getMessage("JC_OA_IC_018"));
			throw e;
	    }
		catch (Exception e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_SYS_002"), e);
		}
		return 1;
	}
   /**
     * 方法描述：级联删除联系人组及联系人关系
     * @param contactsGroup
     * @return
     * @throws IcException
     * @author 曹杨
     * @version  2014年5月20日下午4:56:27
     */	
    @Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer deleteGroup(ContactsGroup contactsGroup) throws IcException {
    	Integer	result = -1;
		try{
		String[] primaryKeys = contactsGroup.getPrimaryKeys();
		//删除对应关系
			for (int i = 0; i < primaryKeys.length; i++) {
				String primaryKey = primaryKeys[i];
				ConGroupR conR = new ConGroupR();
				conR.setContactsGroupId(new Long(primaryKey));
				List<ConGroupR> rList = groupRDao.queryAll(conR);
				String str = null;
				if (rList != null) {
					for (ConGroupR conGroupR : rList) {
						str = Long.toString(conGroupR.getId());
						String key[] = str.split(",");
						conGroupR.setPrimaryKeys(key);
						groupRDao.delete(conGroupR, false);
					}
				}
			}
		//删除联系组记录
		result = contactsGroupDao.delete(contactsGroup);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_SYS_006"), e);
		}
		return result;	
	}
	
}