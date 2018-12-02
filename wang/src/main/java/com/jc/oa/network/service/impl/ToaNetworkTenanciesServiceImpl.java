package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkTenancies;
import com.jc.oa.network.dao.IToaNetworkTenanciesDao;
import com.jc.oa.network.service.IToaNetworkTenanciesService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 退租客户记录表
*/
@Service
public class  ToaNetworkTenanciesServiceImpl  extends BaseServiceImpl<ToaNetworkTenancies> implements IToaNetworkTenanciesService {

	public ToaNetworkTenanciesServiceImpl(){}	

    private IToaNetworkTenanciesDao toaNetworkTenanciesDao;

	@Autowired
	public ToaNetworkTenanciesServiceImpl(IToaNetworkTenanciesDao toaNetworkTenanciesDao){
		super(toaNetworkTenanciesDao);
		this.toaNetworkTenanciesDao = toaNetworkTenanciesDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkTenancies toaNetworkTenancies) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkTenancies,true);
			result = toaNetworkTenanciesDao.delete(toaNetworkTenancies);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkTenancies);
			throw ce;
		}
		return result;
	}

	

}
