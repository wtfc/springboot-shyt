package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkIpstop;
import com.jc.oa.network.dao.IToaNetworkIpstopDao;
import com.jc.oa.network.service.IToaNetworkIpstopService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version IP封停记录表
*/
@Service
public class  ToaNetworkIpstopServiceImpl  extends BaseServiceImpl<ToaNetworkIpstop> implements IToaNetworkIpstopService {

	public ToaNetworkIpstopServiceImpl(){}	

    private IToaNetworkIpstopDao toaNetworkIpstopDao;

	@Autowired
	public ToaNetworkIpstopServiceImpl(IToaNetworkIpstopDao toaNetworkIpstopDao){
		super(toaNetworkIpstopDao);
		this.toaNetworkIpstopDao = toaNetworkIpstopDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkIpstop toaNetworkIpstop) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkIpstop,true);
			result = toaNetworkIpstopDao.delete(toaNetworkIpstop);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkIpstop);
			throw ce;
		}
		return result;
	}

	

}
