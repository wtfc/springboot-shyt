package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkIpresource;
import com.jc.oa.network.dao.IToaNetworkIpresourceDao;
/**
 * @author mrb
 * @version IP总资源表
 */
@Repository
public class ToaNetworkIpresourceDaoImpl extends BaseDaoImpl<ToaNetworkIpresource> implements IToaNetworkIpresourceDao{
	
	public ToaNetworkIpresourceDaoImpl(){};
}
