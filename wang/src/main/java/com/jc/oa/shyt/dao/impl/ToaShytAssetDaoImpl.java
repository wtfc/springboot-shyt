package com.jc.oa.shyt.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.shyt.domain.ToaShytAsset;
import com.jc.oa.shyt.dao.IToaShytAssetDao;
/**
 * @author mrb
 * @version 资产信息表
 */
@Repository
public class ToaShytAssetDaoImpl extends BaseDaoImpl<ToaShytAsset> implements IToaShytAssetDao{
	
	public ToaShytAssetDaoImpl(){};
}
