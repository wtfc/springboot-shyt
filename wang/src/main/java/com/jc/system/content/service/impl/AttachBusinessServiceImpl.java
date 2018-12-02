package com.jc.system.content.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;






import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.content.dao.IAttachBusinessDao;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;



/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-05-21
 */
@Service
public class AttachBusinessServiceImpl extends BaseServiceImpl<AttachBusiness> implements IAttachBusinessService{

	private IAttachBusinessDao attachBusinessDao;
	
	public AttachBusinessServiceImpl(){}
	
	@Autowired
	public AttachBusinessServiceImpl(IAttachBusinessDao attachBusinessDao){
		super(attachBusinessDao);
		this.attachBusinessDao = attachBusinessDao;
	}

}