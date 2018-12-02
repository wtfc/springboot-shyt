package com.jc.oa.po.workTask.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.workTask.domain.WorkTask;
import com.jc.system.CustomException;

/**
 * @title 个人办公
 * @description  业务接口类
 * @version  2014-04-23
 */

public interface IWorkTaskService extends IBaseService<WorkTask>{
	/**
	 * @description 逻辑删除
	 * @param WorkTask  workTask 实体类
	 * @return Integer 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-04-30
	 */
	public Integer updateDeleteFlagByIds(WorkTask workTask) throws PoException;
	/**
	 * @description 任务统计(分页查询)
	 * @param WorkTask  workTask 实体类，PageManager page
	 * @return PageManager 
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-05-04
	 */
	public PageManager queryTaskTatal(WorkTask workTask, PageManager page)throws PoException;
	/**
	 * @description 添加任务事件
	 * @param WorkTask  workTask 实体类，PageManager page
	 * @return PageManager 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-07
	 */
	public Integer addWorkTaskHis(WorkTask workTask,String eventType) throws PoException;
	/**
	 * @description 查询人员名称
	 * @param HttpServletRequest request
	 * @return String 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-12
	 */ 
	public String getNames(String queryType,String uIds) throws PoException;
	/**
	 * @description 将分页信息重新封装
	 * @param HttpServletRequest request
	 * @return String 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-12
	 */ 
	public PageManager converterWorkTask(PageManager pageManager) throws PoException;
	/**
	 * @description 查询所有任务(对外)
	 * @param WorkTask  workTask 实体类
	 * @return List<WorkTask> 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-12
	 */
	public List<WorkTask> queryWorkTaskList(WorkTask workTask) throws PoException;
	/**
	 * @description 将传入的信息重新封装
	 * @param HttpServletRequest request
	 * @return String 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-12
	 */ 
	public WorkTask converterWorkTask(WorkTask workTask) throws PoException;
	/**
	 * @description 查询所有任务(对外,分页)
	 * @param WorkTask  workTask 实体类, PageManager page
	 * @return PageManager 
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-05-13
	 */
	public PageManager queryWorkTaskPage(WorkTask  workTask, PageManager page);
	/**
	 * @description 查询工作计划(分页)
	 * @param Plan plan, PageManager page
	 * @return PageManager 
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-05-15
	 */
	/**
	 * @description 保存
	 * @param WorkTask task
	 * @return Integer 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-15
	 */
	public Integer save(WorkTask task) throws PoException;
	/**
	 * @description 将传入的信息重新封装
	 * @param WorkTask workTask
	 * @return List<WorkTask> 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-05-16
	 */ 
	public List<WorkTask> converterTaskList(WorkTask workTask) throws PoException;
	/**
	 * @description 任务查询(分页,过滤)
	 * @param WorkTask  workTask 实体类
	 * @param PageManager page,String countSQL,String querySQL
	 * @return PageManager
	 * @throws Exception
	 * @author 李洪宇
	 * @version 2014-05-20
	 */
	public PageManager queryByPage(WorkTask workTask, PageManager page,String countSQL,String querySQL)throws PoException;
	/**
	 * @description 任务查询(分页,非过滤)
	 * @param WorkTask  workTask 实体类
	 * @param PageManager page,String countSQL,String querySQL
	 * @return PageManager
	 * @throws Exception
	 * @author 李洪宇
	 * @version 2014-05-20
	 */
	public PageManager queryByPageAll(WorkTask workTask, PageManager page,String countSQL,String querySQL);
	/**
	 * @description 获取单条记录方法(点击任务名称查询)
	 * @param WorkTask task 实体类
	 * @return Task 查询结果
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-21
	 */
	public WorkTask getTask(WorkTask task) throws PoException;
	/**
	 * @description 修改
	 * @param WorkTask task 实体类,Integer addHistory，默认为0：添加事件；1：不添加事件
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-27
	 */
	public Integer update(WorkTask workTask,Integer addHistory) throws PoException;
	/**
	 * @description：根据日志查询日志下的批注
	 * @param anno
	 * @return List<Anno>
	 * @author 李洪宇
	 * @version  2014-05-27
	 */
	public List<Anno> queryAnnoByWorklog(Anno anno);
	/**
	 * @description：保存批注回复
	 * @param anno
	 * @return
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-27
	 */
	public Integer saveAnnoReply(Anno anno) throws PoException;
	/**
	 * @description 批量修改
	 * @param List<WorkTask> workTask
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-05-28
	 */
	public Integer batchUpdate(List<WorkTask> workTask) throws PoException;
	/**
	 * @description 设置session
	 * @param WorkTask task,HttpServletRequest request
	 * @return WorkTask
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-10
	 */
	public WorkTask setSession(WorkTask task,HttpServletRequest request)throws PoException;
	/**
	 * @description 查询任务进度
	 * @param String taskId
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-13
	 */
	public Integer queryTaskProc(WorkTask task)throws PoException;
	/**
	 * @description 取得子任务(对外)
	 * @param HttpServletRequest request
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-06-25
	 */
	public List<WorkTask> getChildList(HttpServletRequest request) throws PoException;
	/**
	 * @description 邮件及短信发送
	 * @param WorkTask task
	 * @return boolean
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-06-06
	 */
	public boolean sendMailOrSms(WorkTask task) throws PoException;
	/**
	 * @description 取消任务模板
	 * @param WorkTask task
	 * @return Integer
	 * @author 李洪宇
	 * @version  2014-07-02
	 */
	public Integer cancalTemplate(WorkTask task);
	/**
	 * @description 超期提醒调用
	 * @param 
	 * @return void
	 * @throws 
	 * @author 李洪宇
	 * @version 2014-07-04
	 */
	public void callRemind()throws PoException;
	/**
	 * @description 查询所有任务
	 * @param WorkTask workTask
	 * @return List<WorkTask>
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-07-11
	 */
	public List<WorkTask> queryAllForUnion(WorkTask workTask);
	/**
	 * @description 关联任务办理（当任务布置、修改、下发、删除时，处理与任务关联表业务）
	 * @param WorkTask workTask
	 * @param String handType 1:任务布置;2:任务修改;3:下发任务;4:删除
	 * @return Integer
	 * @throws PoException
	 * @author 李洪宇
	 * @version  2014-07-16
	 */
	public Integer handleTaskRelevance(WorkTask workTask,String handType) throws PoException;
	/**
	 * @description 取消任务中汇报提醒
	 * @param WorkTask workTask
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-07-21
	 */
	public Integer cancelTaskRemind(WorkTask workTask) throws PoException;
	/**
	 * @description 查询子任务集合(根据指定的任务ID)
	 * @param Long taskId,Integer isFrist
	 * @return List<WorkTask>
	 * @author 李洪宇
	 * @version  2014-06-28
	 */
	public List<WorkTask> getSubTaskList(Long taskId,Integer isFrist);
	/**
	 * @description 操作校验
	 * @param Long taskId,String operateType
	 * @return Integer
	 * @throws 
	 * @author 李洪宇
	 * @version  2014-07-28
	 */
	public Integer checkOperation(Long taskId,String operateType);
	/**
	 * @description 查询待办任务(对外)
	 * @param WorkTask  workTask 实体类
	 * @return List<WorkTask> 
	 * @throws PoException
	 * @author 李洪宇
	 * @version 2014-10-20
	 */
	public List<WorkTask> queryAbeyanceTaskList(WorkTask workTask) throws PoException;
}