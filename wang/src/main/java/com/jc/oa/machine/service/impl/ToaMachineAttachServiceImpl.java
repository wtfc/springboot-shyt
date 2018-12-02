package com.jc.oa.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.dao.IToaMachineAttachDao;
import com.jc.oa.machine.service.IToaMachineAttachService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 工单附件表
*/
@Service
public class  ToaMachineAttachServiceImpl  extends BaseServiceImpl<ToaMachineAttach> implements IToaMachineAttachService {

	public ToaMachineAttachServiceImpl(){}	

    private IToaMachineAttachDao toaMachineAttachDao;

	@Autowired
	public ToaMachineAttachServiceImpl(IToaMachineAttachDao toaMachineAttachDao){
		super(toaMachineAttachDao);
		this.toaMachineAttachDao = toaMachineAttachDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineAttach toaMachineAttach) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineAttach,true);
			result = toaMachineAttachDao.delete(toaMachineAttach);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineAttach);
			throw ce;
		}
		return result;
	}

	

}
