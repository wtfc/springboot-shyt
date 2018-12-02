package com.jc.oa.finance.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.finance.domain.ToaFinanceSplit;
import com.jc.oa.finance.dao.IToaFinanceSplitDao;
import com.jc.oa.finance.service.IToaFinanceSplitService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 权责发生制
*/
@Service
public class  ToaFinanceSplitServiceImpl  extends BaseServiceImpl<ToaFinanceSplit> implements IToaFinanceSplitService {

	public ToaFinanceSplitServiceImpl(){}	

    private IToaFinanceSplitDao toaFinanceSplitDao;

	@Autowired
	public ToaFinanceSplitServiceImpl(IToaFinanceSplitDao toaFinanceSplitDao){
		super(toaFinanceSplitDao);
		this.toaFinanceSplitDao = toaFinanceSplitDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaFinanceSplit toaFinanceSplit) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaFinanceSplit,true);
			result = toaFinanceSplitDao.delete(toaFinanceSplit);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaFinanceSplit);
			throw ce;
		}
		return result;
	}

	

}
