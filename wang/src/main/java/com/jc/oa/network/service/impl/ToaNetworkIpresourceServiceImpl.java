package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkIpresource;
import com.jc.oa.network.dao.IToaNetworkIpresourceDao;
import com.jc.oa.network.service.IToaNetworkIpresourceService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version IP总资源表
*/
@Service
public class  ToaNetworkIpresourceServiceImpl  extends BaseServiceImpl<ToaNetworkIpresource> implements IToaNetworkIpresourceService {

	public ToaNetworkIpresourceServiceImpl(){}	

    private IToaNetworkIpresourceDao toaNetworkIpresourceDao;

	@Autowired
	public ToaNetworkIpresourceServiceImpl(IToaNetworkIpresourceDao toaNetworkIpresourceDao){
		super(toaNetworkIpresourceDao);
		this.toaNetworkIpresourceDao = toaNetworkIpresourceDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkIpresource toaNetworkIpresource) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkIpresource,true);
			result = toaNetworkIpresourceDao.delete(toaNetworkIpresource);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkIpresource);
			throw ce;
		}
		return result;
	}

	

}
