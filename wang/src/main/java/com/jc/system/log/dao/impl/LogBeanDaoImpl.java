package com.jc.system.log.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.IPageManager;
import com.jc.system.log.dao.ILogBeanDao;
import com.jc.system.log.domain.LogBean;

/**
 * @description: 日志Dao类
 */
@Repository
public class LogBeanDaoImpl extends BaseDaoImpl<LogBean> implements ILogBeanDao {
	public Integer save(LogBean logBean) {
		String insertSql = "";
		if (logBean.getManagerFlag().equals(LogBean.MANAGE_FLAG_TRUE)) {
			insertSql = getNameSpace(logBean) + ".insertManager";
		} else {
			insertSql = getNameSpace(logBean) + ".insert";
		}
		Integer result = template.insert(insertSql, logBean);
		return result;
	}

	public Integer save(List<LogBean> list) {
		Integer result = template.insert(getNameSpace(new LogBean())
				+ ".insertCommonBatch", list);
		return result;
	}

	@Override
	public List<LogBean> queryAll(LogBean logBean) {
		String querySql = "";
		if (logBean.getManagerFlag().equals(LogBean.MANAGE_FLAG_TRUE)) {
			querySql = getNameSpace(logBean) + ".queryManager";
		} else {
			querySql = getNameSpace(logBean) + ".query";
		}
		List<LogBean> list = template.selectList(querySql, logBean);
		return list;
	}

	@Override
	public Map<String, Object> queryByPage(LogBean logBean, IPageManager page) {
		String querySql = "";
		if (logBean.getManagerFlag().equals(LogBean.MANAGE_FLAG_TRUE)) {
			querySql = getNameSpace(logBean) + ".queryManager";
		} else {
			querySql = getNameSpace(logBean) + ".query";
		}
		// 查询行数
		int rowsCount = template.selectOne(querySql + "Count", logBean);
		page.setTotalCount(rowsCount);

		// 计算页数 page.getRows()获得每页显示条数，系统中固定值
		int pageCount = rowsCount / page.getPageRows();
		if (rowsCount % page.getPageRows() > 0) {
			pageCount++;
		}
		// 如果传过来的当前页码大于总页码 则把当前页码设置为最大页码
		if (page.getPage() > pageCount && pageCount != 0) {
			page.setPage(pageCount);
		}

		// 将页面传过来的当前页传回到前台
		page.setPage(page.getPage());

		List<LogBean> list = template.selectList(
				querySql,
				logBean,
				new RowBounds((page.getPage() - 1) * page.getPageRows(), page
						.getPageRows()));

		page.setTotalPages(pageCount);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("page", page);
		return map;
	}
}
