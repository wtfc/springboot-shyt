package com.jc.oa.ic.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.dao.ISuggestDao;
import com.jc.oa.ic.domain.SugRecipient;
import com.jc.oa.ic.domain.SugRep;
import com.jc.oa.ic.domain.Suggest;
import com.jc.oa.ic.domain.SuggestType;
import com.jc.oa.ic.service.ISugRecipientService;
import com.jc.oa.ic.service.ISugRepService;
import com.jc.oa.ic.service.ISuggestService;
import com.jc.oa.ic.service.ISuggestTypeService;
import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IAttachService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;

/**
 * @title 互动交流
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-17
 */
@Service
public class SuggestServiceImpl extends BaseServiceImpl<Suggest> implements ISuggestService{
	private final static String TABLE_NAME = "TOA_IC_SUGGEST";
	private ISuggestDao suggestDao;
	
	@Autowired
	private ISugRecipientService sugRecipientService;
	
	@Autowired
	private ISuggestTypeService suggestTypeService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IAttachService attachService;
	
	@Autowired
	private ISugRepService sugRepService;
	
	@Autowired
	public SuggestServiceImpl(ISuggestDao suggestDao){
		super(suggestDao);
		this.suggestDao = suggestDao;
	}
	public SuggestServiceImpl(){
		
	}
	/**
	 * @description 保存方法
	 * @param SuggestBean suggest 实体类
	 * @return Integer 增加的记录数
	 * @author 徐伟平
	 * @version 2014-05-12
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(Suggest suggest) throws IcException {
		try {
			/*// 判断意见建议标题是否存在
			Suggest  s = new Suggest();
			s.setSuggestTitle(suggest.getSuggestTitle());
			s.setDeleteFlag(0);
			if (suggestDao.get(s) != null) {
				IcException ce = new IcException();
				ce.setLogMsg(MessageUtils.getMessage("JC_SYS_036",new String[]{"建议标题"}));
				throw ce;
			}*/
			//保存通用字段
			propertyService.fillProperties(suggest,false);
			// 保存意见
			suggestDao.save(suggest);
			//成功后保存接收人表
			SugRecipient sugRecipient = new SugRecipient();
			if(!"".equals(suggest.getRecipientIds()) && suggest.getRecipientIds() != null){
				String[] ids =suggest.getRecipientIds().split(",");
				for (int i = 0; i < ids.length; i++) {
					//存入接收人ID
					sugRecipient.setRecipientId(new Long(ids[i]));
					//存入建议ID
					sugRecipient.setSuggestId(suggest.getId());
					propertyService.fillProperties(sugRecipient,false);
					sugRecipientService.save(sugRecipient);
				}
			}
			List<Long> fileIds = suggest.getFileids();
			//保存附件
			if(fileIds != null && fileIds.size() >0 ){
				AttachBusiness attachBusiness = new AttachBusiness();
				for (int i = 0; i < fileIds.size(); i++) {
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(suggest.getId());
					attachBusiness.setBusinessTable(TABLE_NAME);
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
			
		} catch (IcException e) {
			IcException se = new IcException(e);
			se.setLogMsg(e.getLogMsg());
			throw se;
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return 1;
	}
	
	/**
	 * @description 获取单条记录方法
	 * @param SuggestBean suggest 实体类
	 * @return Suggest 查询结果
	 * @author 徐伟平
	 * @version 2014-05-07
	 */
	public Suggest get(Suggest suggest) throws IcException{
		//获取建议对象
		Suggest newsuggest = suggestDao.get(suggest);
		if(newsuggest == null){
			return null;
		}
		try {
			//获取接收人名称并放入到建议对象中带入前台
			SugRecipient sugRecipient = new SugRecipient();
			sugRecipient.setSuggestId(suggest.getId());
			User userInfo = SystemSecurityUtils.getUser();
			String recipientNames = "";
			List<SugRecipient> sugRecipientList = sugRecipientService.queryAll(sugRecipient);;
			if(sugRecipientList != null && sugRecipientList.size() > 0){
				for(SugRecipient s:sugRecipientList){
					recipientNames += s.getRecipientNames()+",";
				}
				newsuggest.setRecipientNames(recipientNames.substring(0, recipientNames.length()-1));
			}
			newsuggest.setUserId(userInfo.getId());//放入当前登录人ID，用于判断群发单显功能
			newsuggest.setUserName(userInfo.getDisplayName());//放入当前登录人名称，用于群发单显功能
			
			//查取回复信息list放入到建议对象中带入前台
			SugRep sugRep = new SugRep();
			sugRep.setSuggestId(suggest.getId());
			List<SugRep> sugRepList = sugRepService.queryAll(sugRep);
			newsuggest.setSugRepList(sugRepList);
			
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return newsuggest;
	}
	
	/**
	* @description 设置建议类别的下拉数据
	* @return List<SuggestType>
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	
	public List<SuggestType> getSuggestTypeList(SuggestType suggestType) throws IcException{
		List<SuggestType> list = null;
		try {
			list = suggestTypeService.queryAll(suggestType);
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_060"));
			throw se;
		}
		return list;
	}
	
	/**
	* @description 带入默认的人员
	* @return String
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	public Map<String,String> getUserNames(String ids) throws IcException{
		Map<String, String> resultMap = new HashMap<String, String>();
		try {
			//根据用户id拼接的字符串去查找对应的用户名称，然后拼成字符串返回到实体类中带入页面
			StringBuffer recipientNames = new StringBuffer("[");
			String returnNames = "";
			String returnIds = "";
			List<User> userList = userService.queryUserByIds(ids);
			if(userList != null){
				for (User user : userList) {
					recipientNames.append("{id:").append(user.getId()).append(",");
					recipientNames.append("text:\"").append(user.getDisplayName()).append("\"}");
					recipientNames.append(",");
					returnNames += user.getDisplayName()+",";
					returnIds += user.getId()+",";
				} 
				if (recipientNames.length() > 0 && recipientNames.charAt(recipientNames.length() - 1) == ',') {
					recipientNames.deleteCharAt(recipientNames.length() - 1);
					recipientNames.append("]");
				}
			}
			resultMap.put("recipientNames", recipientNames.toString());
			resultMap.put("returnNames", returnNames.substring(0, returnNames.length()-1));
			resultMap.put("returnIds", returnIds.substring(0, returnIds.length()-1));
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_013"));
			throw se;
		}
		return resultMap;
	}
	
	/**
	 * @description 保存方法
	 * @param SugRep sugRep 实体类
	 * @return 1
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer saveRep(SugRep sugRep) throws IcException{
		try {
			// 保存回复信息
			sugRepService.save(sugRep);
			//同时将意见的回复标志置为1"已回复"
			Suggest suggest = new Suggest();
			suggest.setId(sugRep.getSuggestId());
			suggest = this.get(suggest);
			suggest.setRepStatus("1");
			suggest.setModifyDateNew(DateUtils.getSysDate());
			suggestDao.update(suggest);
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_002"));
			throw se;
		}
		return 1;
	}
	
	/**
	 * @description 删除建议
	 * @param SuggestBean suggest 实体类
	 * @return Integer 更改结果
	 * @throws Exception
	 * @author 徐伟平
	 * @version 2014-05-12
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delete(Suggest suggest) throws IcException {
		try{
			//更改意见建议deleteFlag为1
			suggest.setModifyDate(DateUtils.getSysDate());
			suggest.setModifyUser(SystemSecurityUtils.getUser().getId());
			suggestDao.delete(suggest);
			
			//更改接收人表deleteFlag为1
			SugRecipient sugRecipient = new SugRecipient();
			sugRecipient.setPrimaryKeys(suggest.getPrimaryKeys());
			sugRecipientService.updateDeleteFlagByIds(sugRecipient);
			
			//更改回复信息表deleteflag为1
			SugRep sugRep = new SugRep();
			sugRep.setPrimaryKeys(suggest.getPrimaryKeys());
			sugRepService.updateDeleteFlagByIds(sugRep);
			
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
			throw se;
		}
		return 1;
	}
}