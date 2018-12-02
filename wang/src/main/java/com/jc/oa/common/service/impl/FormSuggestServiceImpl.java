package com.jc.oa.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.oa.common.dao.IFormSuggestDao;
import com.jc.oa.common.domain.FormSuggest;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.common.service.IFormSuggestService;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 
 * @version  2014-04-18
 */
@Service
public class FormSuggestServiceImpl extends BaseServiceImpl<FormSuggest> implements IFormSuggestService{

	private IFormSuggestDao suggestDao;
	
	public FormSuggestServiceImpl(){}
	
	@Autowired
	public FormSuggestServiceImpl(IFormSuggestDao suggestDao){
		super(suggestDao);
		this.suggestDao = suggestDao;
	}

}