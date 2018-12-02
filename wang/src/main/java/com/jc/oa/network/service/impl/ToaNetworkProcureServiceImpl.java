package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkProcure;
import com.jc.oa.network.dao.IToaNetworkProcureDao;
import com.jc.oa.network.service.IToaNetworkProcureService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 网络设备采购表
*/
@Service
public class  ToaNetworkProcureServiceImpl  extends BaseServiceImpl<ToaNetworkProcure> implements IToaNetworkProcureService {

	public ToaNetworkProcureServiceImpl(){}	

    private IToaNetworkProcureDao toaNetworkProcureDao;

	@Autowired
	public ToaNetworkProcureServiceImpl(IToaNetworkProcureDao toaNetworkProcureDao){
		super(toaNetworkProcureDao);
		this.toaNetworkProcureDao = toaNetworkProcureDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkProcure toaNetworkProcure) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkProcure,true);
			result = toaNetworkProcureDao.delete(toaNetworkProcure);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkProcure);
			throw ce;
		}
		return result;
	}

	

}
