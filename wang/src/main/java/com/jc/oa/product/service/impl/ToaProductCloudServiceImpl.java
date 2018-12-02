package com.jc.oa.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.product.domain.ToaProductCloud;
import com.jc.oa.product.dao.IToaProductCloudDao;
import com.jc.oa.product.service.IToaProductCloudService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 云计算业务表
*/
@Service
public class  ToaProductCloudServiceImpl  extends BaseServiceImpl<ToaProductCloud> implements IToaProductCloudService {

	public ToaProductCloudServiceImpl(){}	

    private IToaProductCloudDao toaProductCloudDao;

	@Autowired
	public ToaProductCloudServiceImpl(IToaProductCloudDao toaProductCloudDao){
		super(toaProductCloudDao);
		this.toaProductCloudDao = toaProductCloudDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaProductCloud toaProductCloud) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaProductCloud,true);
			result = toaProductCloudDao.delete(toaProductCloud);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaProductCloud);
			throw ce;
		}
		return result;
	}

	

}
