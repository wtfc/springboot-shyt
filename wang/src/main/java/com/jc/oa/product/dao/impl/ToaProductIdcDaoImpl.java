package com.jc.oa.product.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.product.domain.ToaProductIdc;
import com.jc.oa.product.dao.IToaProductIdcDao;
/**
 * @author mrb
 * @version IDC业务表
 */
@Repository
public class ToaProductIdcDaoImpl extends BaseDaoImpl<ToaProductIdc> implements IToaProductIdcDao{
	
	public ToaProductIdcDaoImpl(){};
}
