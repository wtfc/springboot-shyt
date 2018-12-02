package com.jc.oa.po.worklog.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.po.worklog.dao.IWorklogDao;
import com.jc.oa.po.worklog.domain.Worklog;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.system.common.util.Constants;

/**
 * @title 个人办公
 * @description  dao实现类
 * @author 李新桐
 * @version  2014-05-04
 */
@Repository
public class WorklogDaoImpl extends BaseDaoImpl<Worklog> implements IWorklogDao{
	
	public static final String NAMESPACE= "com.jc.oa.po.worklog.domain.Worklog";
	
	/**
	 * 查询我的日志SQL ID
	 */
	public static final String SQL_QUERY_MYWORKLOG= "queryMyWorklogList";
	/**
	 * 某天的过去五天的日志数SQL ID
	 */
	public static final String SQL_QUERY_MYWORKLOG_PASTFIVEDAYS= "queryMyWorklogPastFiveDays";
	/**
	 * 查询共享给我的日志 SQL ID
	 */
	public static final String SQL_GET_SHARE_WORKLOG= "getShareWorklogList";
	/**
	 * 查询共享给我的日志数量 SQL ID
	 */
	public static final String SQL_GET_SHARE_WORKLOG_COUNT= "getShareWorklogCount";
	/**
	 * 查询昨天提醒
	 */
	public static final String SQL_GET_YESTERDAY_RAMIND= "getYesterdayRamind";

	public WorklogDaoImpl(){}
	
	/**
	 * 方法描述：我的日志-查询日志列表
	 * @param worklog
	 * @return 我的日志列表
	 * @author 李新桐
	 * @version  2014年5月6日下午7:09:34
	 * @see
	 */
	public List<Worklog> queryMyWorklogList(Worklog worklog){
		return template.selectList(getNameSpace(worklog) +"."+ SQL_QUERY_MYWORKLOG, worklog);
	}
	
	/**
	 * 方法描述：某天的过去五天的日志数
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月13日下午12:57:16
	 * @see
	 */
	public List<Worklog> queryMyWorklogPastFiveDays(Worklog worklog){
		return template.selectList(getNameSpace(worklog) +"."+ SQL_QUERY_MYWORKLOG_PASTFIVEDAYS, worklog);
	}

	/**
	 * 方法描述：查询共享给我的日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月16日上午10:43:56
	 * @see
	 */
	@Override
	public List<Worklog> getShareWorklogList(Worklog worklog) {
		return  template.selectList(getNameSpace(worklog) +"."+ SQL_GET_SHARE_WORKLOG, worklog);
	}
	
	/**
	 * 方法描述：获得昨日提醒
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日下午2:14:31
	 * @see
	 */
	@Override
	public List<WorklogContent> getYesterdayRemind(WorklogContent worklogContent) {
		return  template.selectList(NAMESPACE +"."+ SQL_GET_YESTERDAY_RAMIND, worklogContent);
	}

	/**
	 * @description 工作日程快捷方式日志批注数量统计
	 * @param worklog
	 * @return Integer
	 * @author 刘锡来
	 * @version  2014年7月26日上午10:45:45
	 */
	public Integer worklogAnnoCount(Worklog worklog){
		return template.selectOne(getNameSpace(worklog) + "."+ Constants.PO_SQL_WORKLOGANNO_COUNT, worklog);
	}
}