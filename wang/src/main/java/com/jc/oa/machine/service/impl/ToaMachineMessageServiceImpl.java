package com.jc.oa.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineMessage;
import com.jc.oa.machine.dao.IToaMachineMessageDao;
import com.jc.oa.machine.service.IToaMachineMessageService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 工单消息表
*/
@Service
public class  ToaMachineMessageServiceImpl  extends BaseServiceImpl<ToaMachineMessage> implements IToaMachineMessageService {

	public ToaMachineMessageServiceImpl(){}	

    private IToaMachineMessageDao toaMachineMessageDao;

	@Autowired
	public ToaMachineMessageServiceImpl(IToaMachineMessageDao toaMachineMessageDao){
		super(toaMachineMessageDao);
		this.toaMachineMessageDao = toaMachineMessageDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineMessage toaMachineMessage) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineMessage,true);
			result = toaMachineMessageDao.delete(toaMachineMessage);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineMessage);
			throw ce;
		}
		return result;
	}

	

}
