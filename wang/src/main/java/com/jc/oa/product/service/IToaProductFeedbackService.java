package com.jc.oa.product.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.product.domain.ToaProductFeedback;
/**
 * @author mrb
 * @version 云主机测试反馈
*/
public interface IToaProductFeedbackService extends IBaseService<ToaProductFeedback>{

	public Integer deleteByIds(ToaProductFeedback toaProductFeedback) throws CustomException;
}
