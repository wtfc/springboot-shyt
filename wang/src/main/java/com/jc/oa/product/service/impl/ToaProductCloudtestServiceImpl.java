package com.jc.oa.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.product.domain.ToaProductCloudtest;
import com.jc.oa.product.dao.IToaProductCloudtestDao;
import com.jc.oa.product.service.IToaProductCloudtestService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 云主机测试表
*/
@Service
public class  ToaProductCloudtestServiceImpl  extends BaseServiceImpl<ToaProductCloudtest> implements IToaProductCloudtestService {

	public ToaProductCloudtestServiceImpl(){}	

    private IToaProductCloudtestDao toaProductCloudtestDao;

	@Autowired
	public ToaProductCloudtestServiceImpl(IToaProductCloudtestDao toaProductCloudtestDao){
		super(toaProductCloudtestDao);
		this.toaProductCloudtestDao = toaProductCloudtestDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaProductCloudtest toaProductCloudtest) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaProductCloudtest,true);
			result = toaProductCloudtestDao.delete(toaProductCloudtest);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaProductCloudtest);
			throw ce;
		}
		return result;
	}

	

}
