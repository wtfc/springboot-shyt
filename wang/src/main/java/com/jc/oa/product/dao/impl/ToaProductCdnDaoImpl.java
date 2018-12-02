package com.jc.oa.product.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.product.domain.ToaProductCdn;
import com.jc.oa.product.dao.IToaProductCdnDao;
/**
 * @author mrb
 * @version CDN业务表
 */
@Repository
public class ToaProductCdnDaoImpl extends BaseDaoImpl<ToaProductCdn> implements IToaProductCdnDao{
	
	public ToaProductCdnDaoImpl(){};
}
