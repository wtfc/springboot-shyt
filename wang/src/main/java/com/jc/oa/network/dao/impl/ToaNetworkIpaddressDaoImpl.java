package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkIpaddress;
import com.jc.oa.network.dao.IToaNetworkIpaddressDao;
/**
 * @author mrb
 * @version 客户IP地址统计表
 */
@Repository
public class ToaNetworkIpaddressDaoImpl extends BaseDaoImpl<ToaNetworkIpaddress> implements IToaNetworkIpaddressDao{
	
	public ToaNetworkIpaddressDaoImpl(){};
}
