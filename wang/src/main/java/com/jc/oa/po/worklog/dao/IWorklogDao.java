package com.jc.oa.po.worklog.dao;

import java.util.List;

import com.jc.oa.po.worklog.domain.Worklog;
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
 
public interface IWorklogDao extends IBaseDao<Worklog>{

	/**
	 * 方法描述：我的日志-查询日志列表
	 * @param worklog
	 * @return 我的日志列表
	 * @author 李新桐
	 * @version  2014年5月6日下午7:09:34
	 * @see
	 */
	public List<Worklog> queryMyWorklogList(Worklog worklog);
	
	/**
	 * 方法描述：某天的过去五天的日志数
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月13日下午12:57:16
	 * @see
	 */
	public List<Worklog> queryMyWorklogPastFiveDays(Worklog worklog);

	/**
	 * 方法描述：查询共享给我的日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月16日上午10:43:56
	 * @see
	 */
	public List<Worklog> getShareWorklogList(Worklog worklog);

	/**
	 * 方法描述：获得昨日提醒
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日下午2:14:31
	 * @see
	 */
	public List<WorklogContent> getYesterdayRemind(WorklogContent worklogContent);
	
	/**
	 * @description 工作日程快捷方式日志批注数量统计
	 * @param worklog
	 * @return Integer
	 * @author 刘锡来
	 * @version  2014年7月26日上午10:45:45
	 */
	public Integer worklogAnnoCount(Worklog worklog);
}
