package com.jc.oa.finance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.finance.domain.ToaFinancePercentage;
import com.jc.oa.finance.dao.IToaFinancePercentageDao;
import com.jc.oa.finance.service.IToaFinancePercentageService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 绩效提成表
*/
@Service
public class  ToaFinancePercentageServiceImpl  extends BaseServiceImpl<ToaFinancePercentage> implements IToaFinancePercentageService {

	public ToaFinancePercentageServiceImpl(){}	

    private IToaFinancePercentageDao toaFinancePercentageDao;

	@Autowired
	public ToaFinancePercentageServiceImpl(IToaFinancePercentageDao toaFinancePercentageDao){
		super(toaFinancePercentageDao);
		this.toaFinancePercentageDao = toaFinancePercentageDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaFinancePercentage toaFinancePercentage) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaFinancePercentage,true);
			result = toaFinancePercentageDao.delete(toaFinancePercentage);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinancePercentage);
			throw ce;
		}
		return result;
	}

	

}
