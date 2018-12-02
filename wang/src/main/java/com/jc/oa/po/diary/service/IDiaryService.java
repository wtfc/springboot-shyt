package com.jc.oa.po.diary.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.po.PoException;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.diary.domain.Diary;
import com.jc.oa.po.readingstatus.domain.ReadingStatus;
import com.jc.system.CustomException;
import com.jc.system.security.domain.Department;

/**
 * @title 个人办公
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 金峰
 * @version  2014-04-28
 */

public interface IDiaryService extends IBaseService<Diary>{
	
	/** 方法描述：调用IBaseService save 并添加保存ControlSide
	 * @param diary
	 * @param request
	 * @return Integer -1 保存失败 1 保存成功
	 * @throws PoException
	 * @author 金峰
	 * @version  2014年5月15日下午1:33:31
	 * @see
	 */
	public Integer saveDairyAndControlSide(Diary diary) throws Exception;
	
	
	/** 方法描述：保存周期性日程
	 * @param diary 实体类 
	 * @return Integer -1 保存失败 >0 保存成功
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月9日上午11:05:01
	 * @see
	 */
	public Integer savePeriod(Diary diary,HttpServletRequest request) throws PoException;
	
	/** 方法描述：更新周期性日程
	 * @param diary 实体类
	 * @return Integer -1 保存失败 >0 保存成功
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月12日下午6:53:54
	 * @see
	 */
	public Integer updatePeriod(Diary diary,HttpServletRequest request) throws Exception;
	
	/** 方法描述：调用IBaseService update 并添加保存ControlSide
	 * @param diary
	 * @return Integer -1 保存失败 1 保存成功
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月21日下午1:58:53
	 * @see
	 */
	public Integer updateDairyAndSaveControlSide(Diary diary) throws Exception;
	
	/** 方法描述：
	 * @param diary
	 * @return Integer -1 逻辑删除失败 >0 逻辑删除成功
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月12日下午7:18:30
	 * @see
	 */
	public Integer deletePeriod(Diary diary,Boolean logicDelete) throws PoException;
	
	/** 方法描述：下属人员树
	 * @return List<User>
	 * @author 金峰
	 * @version  2014年5月14日下午1:34:28
	 * @see
	 */
//	public List<User> queryForUnderlingTree() throws PoException;
	public List<Department> queryForUnderlingTree() throws PoException;
	
	/** 方法描述：关联查询日程表和批注表
	 * @return List<Diary> 
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月14日下午4:45:40
	 * @see
	 */
	public List<Diary> queryDiaryAnno(Diary diary) throws Exception;
	
	/** 方法描述：日程汇总分页查询
	 * @return PageManager
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014年11月19日
	 * @see
	 */
	public PageManager queryDiarySummary(Diary diary,PageManager page) throws Exception;
	
	
	/** 方法描述：查询日程接口供日志管理中使用
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日上午11:34:35
	 * @see
	 */
	public List<Diary> queryInterface(Diary diary) throws Exception;
	
	/** 方法描述：共享人员树
	 * @return List<Department>
	 * @author 金峰
	 * @version  2014年5月14日下午1:34:28
	 * @see
	 */
//	public List<User> queryForShareTree() throws Exception;
	public List<Department> queryForShareTree() throws Exception;
	
	/** 方法描述：公开日程领导人员树
	 * @return List<Department>
	 * @author 金峰
	 * @version  2014年5月14日下午1:34:28
	 * @see
	 */
//	public List<User> queryForLeadTree() throws Exception;
	public List<Department> queryForLeadTree() throws Exception;
	
	/** 方法描述：查询列表视图信息
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日上午11:34:35
	 * @see
	 */
	public List<Diary> queryForListView(Diary diary) throws Exception;
	
	/** 方法描述：移动端查询列表视图信息
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日上午11:34:35
	 * @see
	 */
	public List<Diary> queryForListView4M(String date,String userid,String curPage) throws Exception;
	
	/** 方法描述：返回某周期日程 周期内全部日程ID 用逗号分隔
	 * @param diary
	 * @return String ID串
	 * @author 金峰
	 * @version  2014年5月21日下午1:47:28
	 * @see
	 */
	public String getPeriodIds(List<Diary> list) ;
	
	/** 方法描述：返回某周期日程 周期内全部日程
	 * @param diary
	 * @return String ID串
	 * @author 金峰
	 * @version  2014年5月21日下午1:47:28
	 * @see
	 */
	public List<Diary> getPeriod(Diary diary) throws PoException;
	
	/** 方法描述：查询共享日程
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月26日下午1:31:59
	 * @see
	 */
	public List<Diary> queryDiaryShare(Diary diary) throws Exception;
	
	/**
	 * 方法描述：根据日志查询日志下的批注
	 * @param anno
	 * @return List<Anno>
	 * @author 金峰
	 * @version  2014年5月13日下午8:23:13
	 * @see
	 */
	public List<Anno> queryAnnoByDiary(Anno anno);
	
	/**
	 * 方法描述：保存批注回复
	 * @param anno
	 * @return
	 * @author 金峰
	 * @version  2014年5月14日下午1:23:04
	 * @see
	 */
	public int saveAnnoReply(Anno anno) throws PoException;
	
	/**
	 * 方法描述：保存阅读情况
	 * @param readingStatus
	 * @return Integer
	 * @author 金峰
	 * @version  2014年5月22日上午9:49:21
	 * @throws PoException 
	 * @see
	 */
	public Integer savaReadingStatus(ReadingStatus readingStatus) throws Exception;
	
	/**
	 * 方法描述：校验选择短信提醒时被提醒人是否存在电话号
	 * @param worklog
	 * @return
	 * @author 金峰
	 * @version  2014年6月3日下午2:05:44
	 * @see
	 */
	public Map<String,Object> validRemind(String userIds) throws PoException;
	/** 方法描述：删除周期性日程中包含的批注信息
	 * @param ids
	 * @return Integer
	 * @throws CustomException
	 * @author 金峰
	 * @version  2014年5月27日上午11:56:50
	 * @see
	 */
	public Integer delPeriodAnnoIds(String ids) throws CustomException;
	/** 方法描述：根据id串及时间（年月）分页查询日程
	 * @param ids
	 * @param date
	 * @param curPage
	 * @return
	 * @throws Exception
	 * @author Administrator
	 * @version  2014年9月15日上午11:23:03
	 * @see
	 */
	public List<Diary> queryForListByIds4m(String ids,String date,String curPage) throws Exception;
	/** 方法描述：移动端根据领导id查询列表视图信息
	 * @param diary
	 * @return List<Diary>
	 * @throws Exception
	 * @author 金峰
	 * @version  2014年5月15日上午11:34:35
	 * @see
	 */
	public List<Diary> queryForListByLeaderId4M(String date,String leaderId,String curPage) throws Exception;
}