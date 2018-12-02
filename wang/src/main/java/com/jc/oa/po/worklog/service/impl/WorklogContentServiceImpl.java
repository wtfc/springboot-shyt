package com.jc.oa.po.worklog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.worklog.dao.IWorklogContentDao;
import com.jc.oa.po.worklog.domain.Worklog;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.oa.po.worklog.service.IWorklogContentService;

/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-05-04
 */
@Service
public class WorklogContentServiceImpl extends BaseServiceImpl<WorklogContent> implements IWorklogContentService{

	private IWorklogContentDao worklogContentDao;
	
	public WorklogContentServiceImpl(){}
	
	@Autowired
	public WorklogContentServiceImpl(IWorklogContentDao worklogContentDao){
		super(worklogContentDao);
		this.worklogContentDao = worklogContentDao;
	}
	
	/**
	 * 方法描述：根据工作日志id和内容类型删除内容
	 * @param worklogId
	 * @param contentType
	 * @author 李新桐
	 * @version  2014年5月8日下午2:39:28
	 * @see
	 */
	@Override
	public int deleteByworklogId(Long worklogId, String contentType) {
		WorklogContent worklogContent = new WorklogContent();
		worklogContent.setWorklogId(worklogId);
		worklogContent.setContentType(contentType);
		return worklogContentDao.deleteByworklogId(worklogContent);
	}
	
	/**
	 * 方法描述：根据工作日志id逻辑删除日志内容
	 * @param worklogContent
	 * @return
	 * @author 李新桐
	 * @version  2014年5月9日上午9:37:17
	 * @see
	 */
	@Override
	public int deleteByworklogIdLogic(WorklogContent worklogContent){
		return worklogContentDao.deleteByworklogIdLogic(worklogContent);
	}

}