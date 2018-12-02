package com.jc.shjfgl.machine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.shjfgl.machine.dao.ITopiaoDao;
import com.jc.shjfgl.machine.domain.Topiao;
import com.jc.shjfgl.machine.service.ITopiaoService;
import com.jc.system.CustomException;

@Service
public class TopiaoServiceImpl extends BaseServiceImpl<Topiao> implements ITopiaoService{

	@Autowired
	public TopiaoServiceImpl(ITopiaoDao topiaoDao){
		super(topiaoDao);
		this.topiaoDao=topiaoDao;
	}
	private ITopiaoDao topiaoDao;
	
	public TopiaoServiceImpl(){};
	
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Topiao view) throws CustomException {
		Integer result = -1;
		try{
			propertyService.fillProperties(view,true);
			result = topiaoDao.delete(view);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(view);
			throw ce;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public List<Topiao> finish(Topiao topiao) throws CustomException {
		
		return topiaoDao.finish(topiao);
	}
}
