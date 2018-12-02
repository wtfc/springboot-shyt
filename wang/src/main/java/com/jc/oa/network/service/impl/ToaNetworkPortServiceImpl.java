package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkPort;
import com.jc.oa.network.dao.IToaNetworkPortDao;
import com.jc.oa.network.service.IToaNetworkPortService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 端口使用情况统计表
*/
@Service
public class  ToaNetworkPortServiceImpl  extends BaseServiceImpl<ToaNetworkPort> implements IToaNetworkPortService {

	public ToaNetworkPortServiceImpl(){}	

    private IToaNetworkPortDao toaNetworkPortDao;

	@Autowired
	public ToaNetworkPortServiceImpl(IToaNetworkPortDao toaNetworkPortDao){
		super(toaNetworkPortDao);
		this.toaNetworkPortDao = toaNetworkPortDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkPort toaNetworkPort) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkPort,true);
			result = toaNetworkPortDao.delete(toaNetworkPort);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkPort);
			throw ce;
		}
		return result;
	}

	

}
