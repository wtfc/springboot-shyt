package com.jc.oa.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.product.domain.ToaProductFeedback;
import com.jc.oa.product.dao.IToaProductFeedbackDao;
import com.jc.oa.product.service.IToaProductFeedbackService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 云主机测试反馈
*/
@Service
public class  ToaProductFeedbackServiceImpl  extends BaseServiceImpl<ToaProductFeedback> implements IToaProductFeedbackService {

	public ToaProductFeedbackServiceImpl(){}	

    private IToaProductFeedbackDao toaProductFeedbackDao;

	@Autowired
	public ToaProductFeedbackServiceImpl(IToaProductFeedbackDao toaProductFeedbackDao){
		super(toaProductFeedbackDao);
		this.toaProductFeedbackDao = toaProductFeedbackDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaProductFeedback toaProductFeedback) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaProductFeedback,true);
			result = toaProductFeedbackDao.delete(toaProductFeedback);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaProductFeedback);
			throw ce;
		}
		return result;
	}

	

}
