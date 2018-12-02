package com.jc.oa.network.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkRing;
import com.jc.oa.network.dao.IToaNetworkRingDao;
import com.jc.oa.network.service.IToaNetworkRingService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 骨干网链路带宽统计表
*/
@Service
public class  ToaNetworkRingServiceImpl  extends BaseServiceImpl<ToaNetworkRing> implements IToaNetworkRingService {

	public ToaNetworkRingServiceImpl(){}	

    private IToaNetworkRingDao toaNetworkRingDao;

	@Autowired
	public ToaNetworkRingServiceImpl(IToaNetworkRingDao toaNetworkRingDao){
		super(toaNetworkRingDao);
		this.toaNetworkRingDao = toaNetworkRingDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkRing toaNetworkRing) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkRing,true);
			result = toaNetworkRingDao.delete(toaNetworkRing);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkRing);
			throw ce;
		}
		return result;
	}

	

}
