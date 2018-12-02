package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkMachinewidth;
import com.jc.oa.network.dao.IToaNetworkMachinewidthDao;
import com.jc.oa.network.service.IToaNetworkMachinewidthService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 在网客户机房带宽统计表
*/
@Service
public class  ToaNetworkMachinewidthServiceImpl  extends BaseServiceImpl<ToaNetworkMachinewidth> implements IToaNetworkMachinewidthService {

	public ToaNetworkMachinewidthServiceImpl(){}	

    private IToaNetworkMachinewidthDao toaNetworkMachinewidthDao;

	@Autowired
	public ToaNetworkMachinewidthServiceImpl(IToaNetworkMachinewidthDao toaNetworkMachinewidthDao){
		super(toaNetworkMachinewidthDao);
		this.toaNetworkMachinewidthDao = toaNetworkMachinewidthDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkMachinewidth toaNetworkMachinewidth) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkMachinewidth,true);
			result = toaNetworkMachinewidthDao.delete(toaNetworkMachinewidth);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkMachinewidth);
			throw ce;
		}
		return result;
	}

	

}
