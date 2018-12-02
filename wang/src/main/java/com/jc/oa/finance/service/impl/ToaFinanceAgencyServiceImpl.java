package com.jc.oa.finance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.finance.domain.ToaFinanceAgency;
import com.jc.oa.finance.dao.IToaFinanceAgencyDao;
import com.jc.oa.finance.service.IToaFinanceAgencyService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 代理费
*/
@Service
public class  ToaFinanceAgencyServiceImpl  extends BaseServiceImpl<ToaFinanceAgency> implements IToaFinanceAgencyService {

	public ToaFinanceAgencyServiceImpl(){}	

    private IToaFinanceAgencyDao toaFinanceAgencyDao;

	@Autowired
	public ToaFinanceAgencyServiceImpl(IToaFinanceAgencyDao toaFinanceAgencyDao){
		super(toaFinanceAgencyDao);
		this.toaFinanceAgencyDao = toaFinanceAgencyDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaFinanceAgency toaFinanceAgency) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaFinanceAgency,true);
			result = toaFinanceAgencyDao.delete(toaFinanceAgency);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinanceAgency);
			throw ce;
		}
		return result;
	}

	

}
