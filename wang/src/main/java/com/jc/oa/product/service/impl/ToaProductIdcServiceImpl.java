package com.jc.oa.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.product.domain.ToaProductIdc;
import com.jc.oa.product.dao.IToaProductIdcDao;
import com.jc.oa.product.service.IToaProductIdcService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version IDC业务表
*/
@Service
public class  ToaProductIdcServiceImpl  extends BaseServiceImpl<ToaProductIdc> implements IToaProductIdcService {

	public ToaProductIdcServiceImpl(){}	

    private IToaProductIdcDao toaProductIdcDao;

	@Autowired
	public ToaProductIdcServiceImpl(IToaProductIdcDao toaProductIdcDao){
		super(toaProductIdcDao);
		this.toaProductIdcDao = toaProductIdcDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaProductIdc toaProductIdc) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaProductIdc,true);
			result = toaProductIdcDao.delete(toaProductIdc);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaProductIdc);
			throw ce;
		}
		return result;
	}

	

}
