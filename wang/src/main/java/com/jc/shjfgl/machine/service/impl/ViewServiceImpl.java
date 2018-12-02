package com.jc.shjfgl.machine.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.shjfgl.machine.dao.IViewDao;
import com.jc.shjfgl.machine.domain.View;
import com.jc.shjfgl.machine.service.IViewService;
import com.jc.system.CustomException;

@Service
public class ViewServiceImpl extends BaseServiceImpl<View> implements IViewService{
	
	@Autowired
	public ViewServiceImpl(IViewDao viewDao){
		super(viewDao);
		this.viewDao=viewDao;
	}
	private IViewDao viewDao;
	
	public ViewServiceImpl(){};
	
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(View view) throws CustomException {
		Integer result = -1;
		try{
			propertyService.fillProperties(view,true);
			result = viewDao.delete(view);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(view);
			throw ce;
		}
		return result;
	}
	
	

}
