package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkBandwidth;
import com.jc.oa.network.dao.IToaNetworkBandwidthDao;
import com.jc.oa.network.service.IToaNetworkBandwidthService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 在网客户带宽统计表表
*/
@Service
public class  ToaNetworkBandwidthServiceImpl  extends BaseServiceImpl<ToaNetworkBandwidth> implements IToaNetworkBandwidthService {

	public ToaNetworkBandwidthServiceImpl(){}	

    private IToaNetworkBandwidthDao toaNetworkBandwidthDao;

	@Autowired
	public ToaNetworkBandwidthServiceImpl(IToaNetworkBandwidthDao toaNetworkBandwidthDao){
		super(toaNetworkBandwidthDao);
		this.toaNetworkBandwidthDao = toaNetworkBandwidthDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkBandwidth toaNetworkBandwidth) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkBandwidth,true);
			result = toaNetworkBandwidthDao.delete(toaNetworkBandwidth);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkBandwidth);
			throw ce;
		}
		return result;
	}

	

}
