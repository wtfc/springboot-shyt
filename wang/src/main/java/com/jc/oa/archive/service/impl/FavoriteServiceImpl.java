package com.jc.oa.archive.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.archive.dao.IFavoriteDao;
import com.jc.oa.archive.domain.Favorite;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.service.IFavoriteService;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-06-05
 */
@Service
public class FavoriteServiceImpl extends BaseServiceImpl<Favorite> implements IFavoriteService{

	private IFavoriteDao favoriteDao;
	
	public FavoriteServiceImpl(){}
	
	@Autowired
	public FavoriteServiceImpl(IFavoriteDao favoriteDao){
		super(favoriteDao);
		this.favoriteDao = favoriteDao;
	}

}