package com.jc.oa.ic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.IConGroupRDao;
import com.jc.oa.ic.dao.IContactsDao;
import com.jc.oa.ic.domain.ConGroupR;
import com.jc.oa.ic.domain.Contacts;
import com.jc.oa.ic.service.IContactsGroupService;
import com.jc.oa.ic.service.IContactsService;
import com.jc.system.CustomException;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;

/**
 * @title GOAIC
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-05-04
 */
@Service
public class ContactsServiceImpl extends BaseServiceImpl<Contacts> implements IContactsService{

	private IContactsDao contactsDao;
	@Autowired
	private IContactsGroupService contactsGroupService;
	@Autowired
	private IConGroupRDao conGroupRDao;
	public ContactsServiceImpl(){}
	
	@Autowired
	public ContactsServiceImpl(IContactsDao contactsDao){
		super(contactsDao);
		this.contactsDao = contactsDao;
	}
	/**
     * 联合联系人分组表分页查询
     * @param contacts
     * @param page
     * @return
     * @throws CustomException
     */
	@Override
	public PageManager queryContacts(Contacts contacts, PageManager pageManager){
			return contactsDao.queryContacts(contacts, pageManager);

	}
	/**
	 * 联系人保存
	 * @param contacts
     * @return
     * @throws CustomException
     */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(Contacts contacts) throws IcException{
		try {
			// 判断联系人是否存在
			Contacts contact = new Contacts();
			contact.setUserName(contacts.getUserName());
			contact.setCreateUser(SystemSecurityUtils.getUser().getId());
			contact = contactsDao.get(contact) ;
			if (contact != null) {
				IcException ic = new IcException();
				ic.setLogMsg(MessageUtils.getMessage("JC_OA_IC_019"));
				throw ic;
			}
			// 判断邮件地址是否存在
			Contacts con = new Contacts();
			con.setMail(contacts.getMail());
			con.setCreateUser(SystemSecurityUtils.getUser().getId());
			con = contactsDao.get(con) ;
			if (con != null) {
				IcException ice = new IcException();
				ice.setLogMsg(MessageUtils.getMessage("JC_OA_IC_074"));
				throw ice;
			}
			propertyService.fillProperties(contacts,false);
			//保存联系人
			contactsDao.save(contacts);
			if(contacts.getGroupId()!=null){
			ConGroupR cong  = new ConGroupR();
			//保存联系人分组关系表
			if(contacts.getId() != null && contacts.getGroupId() != null){
				cong.setContactsId(contacts.getId());
				cong.setContactsGroupId(contacts.getGroupId());
				propertyService.fillProperties(cong,false);
				conGroupRDao.save(cong);
			    }
		     }
		} 
		catch (CustomException e) {
			IcException se = new IcException(e);
			se.setLogMsg(e.getLogMsg());
			throw se;
		} 
		catch (Exception e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_SYS_002"), e);
		}
		return Integer.parseInt(contacts.getId().toString());		
	}
	/**
	 * 方法描述：获取邮件联系人与分组信息
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 曹杨
	 * @version  2014年5月12日下午1:05:37
	 */
	@Override
	public Contacts getContacts(Contacts contacts) throws IcException {
		
		try {
			return contactsDao.getContacts(contacts);
		} catch (CustomException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_SYS_007"), e);
		}
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
	public Integer updateContacts(Contacts contacts) throws IcException{
		try {
			if (!contacts.getUserNameOld().equals(contacts.getUserName())) {
				// 判断联系人是否存在
					Contacts contact = new Contacts();
					contact.setUserName(contacts.getUserName());
					contact.setCreateUser(SystemSecurityUtils.getUser().getId());
					contact = contactsDao.get(contact);
					if (contact != null) {
						IcException ic = new IcException();
						ic.setLogMsg(MessageUtils.getMessage("JC_OA_IC_019"));
						throw ic;
					}			
			}
			
			if(!contacts.getMailOld().equals(contacts.getMail())){
				// 判断邮件地址是否存在
					Contacts con = new Contacts();
					con.setMail(contacts.getMail());
					con.setCreateUser(SystemSecurityUtils.getUser().getId());
					con = contactsDao.get(con);
					if (con != null) {
						IcException ice = new IcException();
						ice.setLogMsg(MessageUtils.getMessage("JC_OA_IC_074"));
						throw ice;
					}
			}
			propertyService.fillProperties(contacts, true);
			if(contactsDao.update(contacts) > 0){
				//设置邮件联系与组关系
				ConGroupR r = new ConGroupR();
				r.setContactsId(contacts.getId());
				r.setContactsGroupId(contacts.getGroupId());
				if(contacts.getGroupId() != null){
				//如果存在关系修改关系，不存在新增关系
					if (contacts.getGroupRId() != null) {
						r.setId(contacts.getGroupRId());
						propertyService.fillProperties(r, true);
						conGroupRDao.update(r);
					} else {
						conGroupRDao.save(r);
						propertyService.fillProperties(r, true);
					}
				}
               else {//删除对应关系
					ConGroupR conr = conGroupRDao.get(r);
					if (conr != null) {
						conr.setPrimaryKeys(new String[] { conr.getId().toString() });
						conGroupRDao.delete(conr, false);
					}
				}
		     }
		}
		 catch (CustomException e) {
					IcException se = new IcException(e);
					se.setLogMsg(e.getLogMsg());
					throw se;
		} catch (Exception e) {
					e.printStackTrace();
					throw new IcException(MessageUtils.getMessage("JC_SYS_002"), e);
				}
		return 1;
	}
	/**
	 * 方法描述：级联删除联系人及联系人关系
	 * @param contacts
	 * @return
	 * @throws IcException
	 * @author 曹杨
	 * @version 2014年5月20日下午4:58:30
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer deleteContacts(Contacts contacts) throws IcException {
		Integer	result = -1;
		try{
		String[] primaryKeys = contacts.getPrimaryKeys();
		//删除对应关系
		ConGroupR conR = null;
		String str = null;
		for (int i = 0; i < primaryKeys.length; i++) {
			String primaryKey = primaryKeys[i];
			conR = new ConGroupR();
			conR.setContactsId(new Long(primaryKey));
			List<ConGroupR> rList = conGroupRDao.queryAll(conR);
			if (rList != null) {
				for (ConGroupR conGroupR : rList) {
					str = Long.toString(conGroupR.getId());
					String key[] = str.split(",");
					conGroupR.setPrimaryKeys(key);
					conGroupRDao.delete(conGroupR, false);
				}
			}
		}
		//删除联系人记录
		result = contactsDao.delete(contacts);
		} catch (Exception e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_SYS_006"), e);
		}
		return result;	
	}

	/**
	 * 方法描述：获取邮件联系人与分组信息（外部联系人使用）
	 * @param contacts
	 * @return
	 * @throws CustomException
	 * @author 孙鹏
	 * @version  2014年5月12日下午1:05:37
	 */
	public Contacts queryOutSideUser(Contacts contacts) throws IcException {
		try {
			return contactsDao.queryOutSideUser(contacts);
		} catch (CustomException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_SYS_007"), e);
		}
	}

	public List<Contacts> getOutSideUserTree(Contacts contacts)
			throws IcException {
		try {
			List<Contacts> contactsList = contactsDao.getOutSideUserTree(contacts);
			boolean flag = true;
			for(Contacts con : contactsList){
				if(con.getGroupId() == -1){
					flag = false;
				}
			}
			if(flag){
				for(int i = 0 ; i < contactsList.size() ; i++){
					if(contactsList.get(i).getId() == -1L){
						contactsList.remove(i);
					}
				}
			}
			return contactsList;
		} catch (CustomException e) {
			e.printStackTrace();
			throw new IcException(MessageUtils.getMessage("JC_SYS_007"), e);
		}
	}

	
	/**
	 * 方法描述：验证联系人手机号是否存在
	 * @param contacts
	 * @param request
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年8月6日上午10:05:16
	 * @see
	 */
	@Override
	public Contacts getMobile(Contacts contacts) {
		return  contactsDao.getMobile(contacts);
	}	
}