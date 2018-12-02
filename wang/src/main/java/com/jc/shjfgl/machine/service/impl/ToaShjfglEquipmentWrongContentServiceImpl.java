package com.jc.shjfgl.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrongContent;
import com.jc.shjfgl.machine.dao.IToaShjfglEquipmentWrongContentDao;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentWrongContentService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 故障处理过程表
*/
@Service
public class  ToaShjfglEquipmentWrongContentServiceImpl  extends BaseServiceImpl<ToaShjfglEquipmentWrongContent> implements IToaShjfglEquipmentWrongContentService {

	public ToaShjfglEquipmentWrongContentServiceImpl(){}	

    private IToaShjfglEquipmentWrongContentDao toaShjfglEquipmentWrongContentDao;

	@Autowired
	public ToaShjfglEquipmentWrongContentServiceImpl(IToaShjfglEquipmentWrongContentDao toaShjfglEquipmentWrongContentDao){
		super(toaShjfglEquipmentWrongContentDao);
		this.toaShjfglEquipmentWrongContentDao = toaShjfglEquipmentWrongContentDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaShjfglEquipmentWrongContent toaShjfglEquipmentWrongContent) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaShjfglEquipmentWrongContent,true);
			result = toaShjfglEquipmentWrongContentDao.delete(toaShjfglEquipmentWrongContent);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShjfglEquipmentWrongContent);
			throw ce;
		}
		return result;
	}

	

}
