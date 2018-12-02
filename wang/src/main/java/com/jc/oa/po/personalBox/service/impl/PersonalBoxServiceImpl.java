package com.jc.oa.po.personalBox.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.personalBox.dao.IPersonalBoxDao;
import com.jc.oa.po.personalBox.domain.Note;
import com.jc.oa.po.personalBox.service.IPersonalBoxService;

/**
 * @title 个人办公-工具箱
 * @description  便签
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-11-21
 */
@Service
public class PersonalBoxServiceImpl extends BaseServiceImpl<Note> implements IPersonalBoxService{

	private IPersonalBoxDao personalBoxDao;
	@Autowired
	public PersonalBoxServiceImpl(IPersonalBoxDao personalBoxDao){
		super(personalBoxDao);
		this.personalBoxDao = personalBoxDao;
	}
	public PersonalBoxServiceImpl(){}

}