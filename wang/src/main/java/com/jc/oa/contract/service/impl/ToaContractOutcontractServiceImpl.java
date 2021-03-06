package com.jc.oa.contract.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.contract.domain.ToaContractOutcontract;
import com.jc.oa.contract.dao.IToaContractOutcontractDao;
import com.jc.oa.contract.service.IToaContractOutcontractService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 付款方合同
*/
@Service
public class  ToaContractOutcontractServiceImpl  extends BaseServiceImpl<ToaContractOutcontract> implements IToaContractOutcontractService {

	public ToaContractOutcontractServiceImpl(){}	

    private IToaContractOutcontractDao toaContractOutcontractDao;

	@Autowired
	public ToaContractOutcontractServiceImpl(IToaContractOutcontractDao toaContractOutcontractDao){
		super(toaContractOutcontractDao);
		this.toaContractOutcontractDao = toaContractOutcontractDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaContractOutcontract toaContractOutcontract) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaContractOutcontract,true);
			result = toaContractOutcontractDao.delete(toaContractOutcontract);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaContractOutcontract);
			throw ce;
		}
		return result;
	}

	

}
