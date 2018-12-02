package com.jc.system.log.dao;

import java.util.List;
import java.util.Map;

import com.jc.foundation.domain.IPageManager;
import com.jc.system.log.domain.LogBean;

/**
 * @description: 日志Dao接口
 */
public interface ILogBeanDao {
	public Integer save(LogBean logBean);

	public Integer save(List<LogBean> list);

	public List<LogBean> queryAll(LogBean logBean);

	public Map<String, Object> queryByPage(LogBean o, IPageManager page);
}
