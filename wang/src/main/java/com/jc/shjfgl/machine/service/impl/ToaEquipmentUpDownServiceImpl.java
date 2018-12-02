package com.jc.shjfgl.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.shjfgl.machine.domain.ToaEquipmentUpDown;
import com.jc.shjfgl.machine.dao.IToaEquipmentUpDownDao;
import com.jc.shjfgl.machine.service.IToaEquipmentUpDownService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 设备上下架和迁移
*/
@Service
public class  ToaEquipmentUpDownServiceImpl  extends BaseServiceImpl<ToaEquipmentUpDown> implements IToaEquipmentUpDownService {

	public ToaEquipmentUpDownServiceImpl(){}	

    private IToaEquipmentUpDownDao toaEquipmentUpDownDao;

	@Autowired
	public ToaEquipmentUpDownServiceImpl(IToaEquipmentUpDownDao toaEquipmentUpDownDao){
		super(toaEquipmentUpDownDao);
		this.toaEquipmentUpDownDao = toaEquipmentUpDownDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaEquipmentUpDown toaEquipmentUpDown) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaEquipmentUpDown,true);
			result = toaEquipmentUpDownDao.delete(toaEquipmentUpDown);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaEquipmentUpDown);
			throw ce;
		}
		return result;
	}

	

}
