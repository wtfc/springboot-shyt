package com.jc.oa.shyt.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.shyt.domain.ToaShytAsset;
import com.jc.oa.shyt.dao.IToaShytAssetDao;
import com.jc.oa.shyt.service.IToaShytAssetService;
import com.jc.system.CustomException;
/**
 * @author mrb
 * @version 资产信息表
*/
@Service
public class  ToaShytAssetServiceImpl  extends BaseServiceImpl<ToaShytAsset> implements IToaShytAssetService {

	public ToaShytAssetServiceImpl(){}	

    private IToaShytAssetDao toaShytAssetDao;

	@Autowired
	public ToaShytAssetServiceImpl(IToaShytAssetDao toaShytAssetDao){
		super(toaShytAssetDao);
		this.toaShytAssetDao = toaShytAssetDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaShytAsset toaShytAsset) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaShytAsset,true);
			result = toaShytAssetDao.delete(toaShytAsset);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShytAsset);
			throw ce;
		}
		return result;
	}

	

}
