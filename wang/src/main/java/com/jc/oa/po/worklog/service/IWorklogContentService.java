package com.jc.oa.po.worklog.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.po.worklog.domain.WorklogContent;

/**
 * @title 个人办公
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-05-04
 */

public interface IWorklogContentService extends IBaseService<WorklogContent>{
	
	/**
	 * 方法描述：根据工作日志id和内容类型删除内容
	 * @param worklogId
	 * @param contentType
	 * @author 李新桐
	 * @version  2014年5月8日下午2:39:28
	 * @see
	 */
	public int deleteByworklogId(Long worklogId,String contentType);
	
	/**
	 * 方法描述：根据工作日志id逻辑删除日志内容
	 * @param worklogContent
	 * @return
	 * @author 李新桐
	 * @version  2014年5月9日上午9:37:17
	 * @see
	 */
	public int deleteByworklogIdLogic(WorklogContent worklogContent);
}