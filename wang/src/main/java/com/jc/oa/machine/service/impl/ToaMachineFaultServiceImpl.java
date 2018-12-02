package com.jc.oa.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineFault;
import com.jc.oa.machine.dao.IToaMachineFaultDao;
import com.jc.oa.machine.service.IToaMachineFaultService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 机房故障
*/
@Service
public class  ToaMachineFaultServiceImpl  extends BaseServiceImpl<ToaMachineFault> implements IToaMachineFaultService {

	public ToaMachineFaultServiceImpl(){}	

    private IToaMachineFaultDao toaMachineFaultDao;

	@Autowired
	public ToaMachineFaultServiceImpl(IToaMachineFaultDao toaMachineFaultDao){
		super(toaMachineFaultDao);
		this.toaMachineFaultDao = toaMachineFaultDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineFault toaMachineFault) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineFault,true);
			result = toaMachineFaultDao.delete(toaMachineFault);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineFault);
			throw ce;
		}
		return result;
	}

	

}
