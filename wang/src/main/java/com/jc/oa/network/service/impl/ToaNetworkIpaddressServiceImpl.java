package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkIpaddress;
import com.jc.oa.network.dao.IToaNetworkIpaddressDao;
import com.jc.oa.network.service.IToaNetworkIpaddressService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 客户IP地址统计表
*/
@Service
public class  ToaNetworkIpaddressServiceImpl  extends BaseServiceImpl<ToaNetworkIpaddress> implements IToaNetworkIpaddressService {

	public ToaNetworkIpaddressServiceImpl(){}	

    private IToaNetworkIpaddressDao toaNetworkIpaddressDao;

	@Autowired
	public ToaNetworkIpaddressServiceImpl(IToaNetworkIpaddressDao toaNetworkIpaddressDao){
		super(toaNetworkIpaddressDao);
		this.toaNetworkIpaddressDao = toaNetworkIpaddressDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkIpaddress toaNetworkIpaddress) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkIpaddress,true);
			result = toaNetworkIpaddressDao.delete(toaNetworkIpaddress);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkIpaddress);
			throw ce;
		}
		return result;
	}

	

}
