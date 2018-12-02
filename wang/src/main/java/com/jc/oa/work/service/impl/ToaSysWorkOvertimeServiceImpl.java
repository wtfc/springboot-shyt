package com.jc.oa.work.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.work.domain.ToaSysWorkOvertime;
import com.jc.oa.work.dao.IToaSysWorkOvertimeDao;
import com.jc.oa.work.service.IToaSysWorkOvertimeService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 人员加班信息表
*/
@Service
public class  ToaSysWorkOvertimeServiceImpl  extends BaseServiceImpl<ToaSysWorkOvertime> implements IToaSysWorkOvertimeService {

	public ToaSysWorkOvertimeServiceImpl(){}	

    private IToaSysWorkOvertimeDao toaSysWorkOvertimeDao;

	@Autowired
	public ToaSysWorkOvertimeServiceImpl(IToaSysWorkOvertimeDao toaSysWorkOvertimeDao){
		super(toaSysWorkOvertimeDao);
		this.toaSysWorkOvertimeDao = toaSysWorkOvertimeDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaSysWorkOvertime toaSysWorkOvertime) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaSysWorkOvertime,true);
			result = toaSysWorkOvertimeDao.delete(toaSysWorkOvertime);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaSysWorkOvertime);
			throw ce;
		}
		return result;
	}

	

}
