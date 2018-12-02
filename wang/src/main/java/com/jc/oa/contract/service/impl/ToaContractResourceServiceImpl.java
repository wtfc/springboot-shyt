package com.jc.oa.contract.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.contract.domain.ToaContractResource;
import com.jc.oa.contract.dao.IToaContractResourceDao;
import com.jc.oa.contract.service.IToaContractResourceService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 合同资源表
*/
@Service
public class  ToaContractResourceServiceImpl  extends BaseServiceImpl<ToaContractResource> implements IToaContractResourceService {

	public ToaContractResourceServiceImpl(){}	

    private IToaContractResourceDao toaContractResourceDao;

	@Autowired
	public ToaContractResourceServiceImpl(IToaContractResourceDao toaContractResourceDao){
		super(toaContractResourceDao);
		this.toaContractResourceDao = toaContractResourceDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaContractResource toaContractResource) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaContractResource,true);
			result = toaContractResourceDao.delete(toaContractResource);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaContractResource);
			throw ce;
		}
		return result;
	}

	

}
