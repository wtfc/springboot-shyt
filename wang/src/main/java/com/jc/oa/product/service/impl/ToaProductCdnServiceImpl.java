package com.jc.oa.product.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.product.domain.ToaProductCdn;
import com.jc.oa.product.dao.IToaProductCdnDao;
import com.jc.oa.product.service.IToaProductCdnService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version CDN业务表
*/
@Service
public class  ToaProductCdnServiceImpl  extends BaseServiceImpl<ToaProductCdn> implements IToaProductCdnService {

	public ToaProductCdnServiceImpl(){}	

    private IToaProductCdnDao toaProductCdnDao;

	@Autowired
	public ToaProductCdnServiceImpl(IToaProductCdnDao toaProductCdnDao){
		super(toaProductCdnDao);
		this.toaProductCdnDao = toaProductCdnDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaProductCdn toaProductCdn) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaProductCdn,true);
			result = toaProductCdnDao.delete(toaProductCdn);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaProductCdn);
			throw ce;
		}
		return result;
	}

	

}
