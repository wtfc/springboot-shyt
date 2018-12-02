package com.jc.oa.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineInout;
import com.jc.oa.machine.dao.IToaMachineInoutDao;
import com.jc.oa.machine.service.IToaMachineInoutService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 机房进出
*/
@Service
public class  ToaMachineInoutServiceImpl  extends BaseServiceImpl<ToaMachineInout> implements IToaMachineInoutService {

	public ToaMachineInoutServiceImpl(){}	

    private IToaMachineInoutDao toaMachineInoutDao;

	@Autowired
	public ToaMachineInoutServiceImpl(IToaMachineInoutDao toaMachineInoutDao){
		super(toaMachineInoutDao);
		this.toaMachineInoutDao = toaMachineInoutDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineInout toaMachineInout) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineInout,true);
			result = toaMachineInoutDao.delete(toaMachineInout);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineInout);
			throw ce;
		}
		return result;
	}

	

}
