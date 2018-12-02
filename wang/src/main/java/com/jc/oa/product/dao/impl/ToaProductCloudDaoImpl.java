package com.jc.oa.product.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.product.domain.ToaProductCloud;
import com.jc.oa.product.dao.IToaProductCloudDao;
/**
 * @author mrb
 * @version 云计算业务表
 */
@Repository
public class ToaProductCloudDaoImpl extends BaseDaoImpl<ToaProductCloud> implements IToaProductCloudDao{
	
	public ToaProductCloudDaoImpl(){};
}
