package com.jc.oa.po.worklog.dao;

import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title 个人办公
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-05-04
 */
 
public interface IWorklogContentDao extends IBaseDao<WorklogContent>{

	/**
	 * 方法描述：根据工作日志id和内容类型删除内容
	 * @param worklogContent
	 * @return
	 * @author 李新桐
	 * @version  2014年5月8日下午2:50:32
	 * @see
	 */
	public int deleteByworklogId(WorklogContent worklogContent);
	/**
	 * 方法描述：根据工作日志id逻辑删除日志内容
	 * @param worklogContent
	 * @return
	 * @author 李新桐
	 * @version  2014年5月9日上午9:33:18
	 * @see
	 */
	public int deleteByworklogIdLogic(WorklogContent worklogContent);
}
