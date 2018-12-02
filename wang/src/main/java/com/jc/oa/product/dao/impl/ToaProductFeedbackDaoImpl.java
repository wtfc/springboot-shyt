package com.jc.oa.product.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.product.domain.ToaProductFeedback;
import com.jc.oa.product.dao.IToaProductFeedbackDao;
/**
 * @author mrb
 * @version 云主机测试反馈
 */
@Repository
public class ToaProductFeedbackDaoImpl extends BaseDaoImpl<ToaProductFeedback> implements IToaProductFeedbackDao{
	
	public ToaProductFeedbackDaoImpl(){};
}
