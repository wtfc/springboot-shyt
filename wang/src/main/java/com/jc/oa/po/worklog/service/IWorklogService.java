package com.jc.oa.po.worklog.service;

import java.util.List;
import java.util.Map;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.readingstatus.domain.ReadingStatus;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.oa.po.workTask.domain.WorkTaskHistory;
import com.jc.oa.po.worklog.domain.Worklog;
import com.jc.oa.po.worklog.domain.WorklogContent;
import com.jc.system.CustomException;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;

/**
 * @title 个人办公
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 李新桐
 * @version  2014-05-04
 */
public interface IWorklogService extends IBaseService<Worklog>{
	
	/**
	 * 方法描述：我的日志-查询日志汇总(不分页)
	 * @param worklog
	 * @return 日志列表
	 * @author 李新桐
	 * @version  2014年5月6日下午7:09:34
	 * @see
	 */
	public List<Worklog> getMyworklogCollect(Worklog worklog);
	
	/**
	 * 方法描述：我的日志-查询日志汇总（分页）
	 * @param worklog,page
	 * @return 日志列表
	 * @author 徐伟平
	 * @version  2014年11月19日
	 * @see
	 */
	public PageManager queryMyworklogCollect(Worklog worklog,PageManager page) throws Exception;
	
	/**
	 * @description 查询单条记录方法
	 * @param Worklog worklog 实体类
	 * @return Integer 查询结果
	 * @throws PoEception
	 * @author 李新桐
	 * @version 2014-05-08
	 * @throws CustomException 
	 */
	public Worklog getWorklog(Worklog worklog) throws PoException, CustomException;
	
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
	 * @description 修改方法(只修改日志的部分属性，不修改日志内容)
	 * @param Worklog worklog 实体类
	 * @return Integer 修改的记录数量
	 * @author 李新桐
	 * @version 2014-05-08
	 * @throws PoException 
	 */
	public Integer updateWorklog(Worklog worklog) throws PoException;
	
	/**
	 * 方法描述：根据工作日志id逻辑删除日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月9日上午9:33:18
	 * @throws PoException 
	 * @see
	 */
	public int deleteByworklogIdLogic(Worklog worklog) throws PoException;
	
	/**
	 * 方法描述：待办任务插入日程
	 * @param diary
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日上午10:08:38
	 * @throws CustomException 
	 * @see
	 */
	public int taskToDiary(Diary diary) throws PoException;
	
	/**
	 * 方法描述：查询对应日期的日程
	 * @param diary
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日上午10:54:18
	 * @throws PoException 
	 * @see
	 */
	public List<Diary> queryDiary(Diary diary) throws PoException;
	
	/**
	 * 方法描述：查询待办列表
	 * @param workTask
	 * @return
	 * @author 李新桐
	 * @version  2014年5月12日下午2:57:31
	 * @see
	 */
	public List<WorkTask> queryWorkTask(WorkTask workTask) throws PoException;
	
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
	 * 方法描述：根据日志查询改日志下的批注
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月13日下午8:23:13
	 * @see
	 */
	public List<Anno> queryAnnoByWorklog(Anno anno);
	
	/**
	 * 方法描述：保存批注回复
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日下午1:23:04
	 * @see
	 */
	public int saveAnnoReply(Anno anno) throws PoException;
	
	/**
	 * 方法描述：判断某天时候已经存在日志
	 * @param Worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日下午3:14:28
	 * @see
	 */
	public Integer worklogAllowInsert(Worklog worklog);
	
	/** 方法描述：共享查询tree
	 * @return List<Department>
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年5月16日下午9:32:15
	 * @see
	 */
	public List<Department> queryForShareTree() throws Exception;
	
	/** 方法描述：共享查询左右人员选择tree
	 * @return List<User>
	 * @throws Exception
	 * @author 李新桐
	 * @version  2014年5月16日下午9:32:15
	 * @see
	 */
	public List<User> queryForShareLRTree() throws Exception;
	
	/**
	 * 方法描述：查询共享给我的日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月16日上午10:42:42
	 * @see
	 */
	public List<Worklog> getShareWorklogList(Worklog worklog);
	
	/**
	 * 方法描述：待办任务汇报
	 * @param workTaskHistory
	 * @return
	 * @throws PoException
	 * @author 李新桐
	 * @version  2014年5月19日上午10:54:58
	 * @see
	 */
	public Integer taskReport(WorkTaskHistory workTaskHistory) throws PoException;

	/**
	 * 方法描述：保存阅读情况
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月22日上午9:49:21
	 * @throws CustomException 
	 * @see
	 */
	public Integer savaReadingStatus(ReadingStatus readingStatus) throws CustomException;
	
	/**
	 * 方法描述：更新共享范围
	 * @return
	 * @author 李新桐
	 * @version  2014年5月22日下午4:22:30
	 * @throws CustomException 
	 * @see
	 */
	public Integer updateShareUser(Worklog worklog) throws CustomException;
	
	/**
	 * 方法描述：校验将日志共享短信提醒时被提醒人是否存在电话号
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年6月3日下午2:05:44
	 * @see
	 */
	public Map<String,Object> validRemind(String userIds) throws PoException ;
	
	/**
	 * 方法描述：保存领导批注同时更新日志的标志位
	 * @param anno
	 * @return
	 * @throws CustomException
	 * @author 李新桐
	 * @version  2014年6月4日上午11:11:00
	 * @see
	 */
	public Integer saveAnno(Anno anno) throws CustomException;
	
	/**
	 * 方法描述：
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日上午8:53:27
	 * @see
	 */
	public Integer annoReading(Anno anno,String status)throws CustomException;

	/**
	 * 方法描述：分页查询共享给我的日志
	 * @param worklog
	 * @return
	 * @author 李新桐
	 * @version  2014年5月16日上午10:42:42
	 * @see
	 */
	public PageManager getShareWorklogPage(Worklog worklog, PageManager page);
	
	/**
	 * @description 工作日程快捷方式日志批注数量统计
	 * @param worklog
	 * @return Integer
	 * @author 刘锡来
	 * @version  2014年7月26日上午10:45:45
	 */
	public Integer worklogAnnoCount(Worklog worklog);
	/**
	 * @description 工作日志，日志管理中提醒设置短信校验
	 * @param String userIds,String smsCeateuser
	 * @return Map
	 * @author 李洪宇
	 * @version  2014年9月3日上午8:58:50
	 */
	public Map<String, Object> validRemind(String userIds,String smsCeateuser) throws PoException;
		
	/** 方法描述：移动端分页查询我的日志
	 * @param worklog
	 * @param page
	 * @return
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年9月16日上午10:17:53
	 * @see
	 */
	public List<Worklog> getWorklogPage4M(Worklog worklog,PageManager page) throws Exception;
}