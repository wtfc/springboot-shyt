package com.jc.system.portal.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.portal.dao.IPortalFunctionDao;
import com.jc.system.portal.domain.PortalFunction;
import com.jc.system.portal.service.IPortalFunctionService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-06-11
 */
@Service
public class PortalFunctionServiceImpl extends BaseServiceImpl<PortalFunction> implements IPortalFunctionService{

	private IPortalFunctionDao portalFunctionDao;
	
	public PortalFunctionServiceImpl(){}
	
	@Autowired
	public PortalFunctionServiceImpl(IPortalFunctionDao portalFunctionDao){
		super(portalFunctionDao);
		this.portalFunctionDao = portalFunctionDao;
	}

	public Map<String, Object> valNameEcho(PortalFunction portalFunction) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Long portalid = portalFunction.getId();
		int result = portalFunctionDao.valNameEcho(portalFunction);
		if(portalid == null){
			if(result == 0){
				resultMap.put("success", "true");
			} else {
				resultMap.put("success", "false");
			}
		} else {
			PortalFunction tempVo = new PortalFunction();
			tempVo.setId(portalid);
			tempVo = portalFunctionDao.get(tempVo);
			if(tempVo.getFunName().equals(portalFunction.getFunName())){
				if(result <= 1){
					resultMap.put("success", "true");
				} else {
					resultMap.put("success", "false");
				}
			} else {
				if(result == 0){
					resultMap.put("success", "true");
				} else {
					resultMap.put("success", "false");
				}
			}
		}
		
		return resultMap;
	}

}