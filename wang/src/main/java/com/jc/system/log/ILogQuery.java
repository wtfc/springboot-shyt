package com.jc.system.log;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.jc.foundation.domain.IPageManager;
import com.jc.system.log.domain.LogBean;

/**
 * @description: 日志查询接口
 * @created: 2013-10-30 上午9:21:24
 */
public interface ILogQuery {
	/**
	 * @description 设置人员id条件查询
	 */
	public ILogQuery setUsesId(String userId);

	/**
	 * @description 设置开始时间条件查询
	 */
	public ILogQuery setStartDate(Date startDate);

	/**
	 * @description 设置结束时间条件查询
	 */
	public ILogQuery setEndDate(Date endDate);

	/**
	 * @description 设置操作类型条件查询
	 */
	public ILogQuery setOperType(String operType);

	/**
	 * @description 设置管理员标示类型条件查询
	 */
	public ILogQuery isManager(Boolean managerFlag);

	/**
	 * @description 返回符合条件查询的所有记录
	 */
	public List<LogBean> list();

	/**
	 * @description 返回符合条件查询的分页记录
	 * @param page
	 *            分页信息
	 */
	public Map<String, Object> listByPage(IPageManager page);
}
