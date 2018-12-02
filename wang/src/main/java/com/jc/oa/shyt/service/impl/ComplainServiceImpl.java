package com.jc.oa.shyt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.shyt.dao.IComplainDao;
import com.jc.oa.shyt.domain.Complain;
import com.jc.oa.shyt.service.IComplainService;
import com.jc.system.CustomException;

@Service
public class ComplainServiceImpl extends BaseServiceImpl<Complain> implements IComplainService{
	
	private IComplainDao complainDao;
	
	public ComplainServiceImpl(){}
	@Autowired
	public ComplainServiceImpl(IComplainDao complainDao){
		super(complainDao);
		this.complainDao = complainDao;
	}
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Complain complain) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(complain,true);
			result = complainDao.delete(complain);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(complain);
			throw ce;
		}
		return result;
	}

}
