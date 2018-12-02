package com.jc.system.content.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.system.content.dao.IAttachDao;
import com.jc.system.content.domain.Attach;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.content.service.IAttachService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-04-17
 */
@Service
public class AttachServiceImpl extends BaseServiceImpl<Attach> implements IAttachService{

	private IAttachDao attachDao;
	
	@Autowired
	public AttachServiceImpl(IAttachDao attachDao){
		super(attachDao);
		this.attachDao = attachDao;
	}
	
	public AttachServiceImpl(){
		
	}
	/**
	 * 使用业务id拼接的字符串查询所有的附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	@Override
	public List<Attach> queryAttachByBusinessIds(Attach attach)
			throws Exception {
		return attachDao.queryAttachByBusinessIds(attach);
	}
}