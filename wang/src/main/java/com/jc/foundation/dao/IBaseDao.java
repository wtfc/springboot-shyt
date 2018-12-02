package com.jc.foundation.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.domain.PageManager;
import com.jc.system.DBException;
import com.jc.system.exception.DataNotFoundException;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
@Mapper
public interface IBaseDao<T extends BaseBean> {

	/**
	 * 保存方法
	 * @param o
	 *            实体类
	 * @return 新增的数目数
	 * @author
	 * @version 2014-03-24
	 */
	Integer save(T o) throws DBException;
	
	/**
	 * 批量保存方法
	 * @param list 实体类集合
	 * @return Integer 添加的记录数
	 * @author
	 * @version 2014-03-24
	 */
	Integer save(List<T> list) throws DBException;
	
	/**
	 * 修改方法
	 * @param o
	 *            实体类
	 * @return Integer 修改的记录数
	 * @author
	 * @version 2014-03-24
	 */
	Integer update(T o) throws DBException;

	/**
	 * 删除方法
	 * @param o
	 *            实体类
	 * @return Integer 删除的记录数
	 * @author
	 * @version 2014-03-24
	 */
	Integer delete(T o) throws DBException;
	

	/**
	  * 根据标志位进行物理删除或逻辑删除
	  * @param o  实体类
	  * @param logicDelete  是否逻辑删除
	  * @return  数据库影响的记录数
	  * @version 1.0 2014年4月28日 下午2:43:17
	*/
	Integer delete(T o,Boolean logicDelete) throws DBException;
	
	/**
	 * 查询所有记录方法
	 * @param o
	 *            实体类
	 * @return List 查询的所有记录
	 * @author
	 * @version 2014-03-24
	 */
	List<T> queryAll(T o);



	/**
	 * 查询单条记录方法
	 * @param o
	 *            实体类
	 * @return T 查询的记录
	 * @author
	 * @version 2014-03-24
	 */
	T get(T o);

	/**
	 * 获得记录的数量
	 * @param o
	 *            实体类
	 * @return Long 查询的结果数量
	 * @author
	 * @version 2014-03-24
	 */
	Long getCount(T o);
	
	/**
	  * 获取是否是逻辑删除
	  * @return  逻辑删除返回true 否则返回false
	  * @version 1.0 2014年4月28日 下午2:20:44
	 */
	Boolean isLogicDelete();
	
	/**
	 * 分页查询方法
	 * @param o  实体类,存放查询条件
	 * @param page 分页配置信息
	 * @return 查询结果，包含总页数，当前页码，每页多少条，及查询到的数据
	 * @see #queryByPage(BaseBean, PageManager, String, String)
	 * @author 
	 * @version 2014-03-24
	 */
	PageManager queryByPage(T o, PageManager page);
			
	/**
	 * 数据权限分页查询方法
	 * @param o  实体类,存放查询条件
	 * @param page 分页配置信息
	 * @return 查询结果，包含总页数，当前页码，每页多少条，及查询到的数据
	 * @see #queryByPage(BaseBean, PageManager, String, String)
	 * @author 
	 * @version 2014-03-24
	 */
	PageManager queryForPermissionByPage(T o, PageManager page);
			
	/**
	  * 指定SQL ID分页查询方法
	  * @param o 实体类,存放查询条件
	  * @param page   分页配置信息
	  * @param countSQL 统计总数语句
	  * @param querySQL 实际查询语句
	  * @return  查询结果，包含总页数，当前页码，每页多少条，及查询到的数据
	  * @version 1.0 2014年5月7日 下午3:15:15
	  * @see #queryByPage(BaseBean, PageManager)
	*/
	PageManager queryByPage(T o,final PageManager page,String countSQL,String querySQL);
	
	/**
	 * 指定SQL ID分页查询方法--权限数据过滤
	 * @param o
	 * @param page
	 * @param countSQL
	 * @param querySQL
	 * @return
	 * @version 1.0 2014年7月8日 下午4:26:45
	 */
	PageManager queryForPermissionByPage(T o, final PageManager page, String countSQL, String querySQL);
	
	/**
	  * 锁定行数据
	  * @param table表名
	  * @param id	锁定的数据id
	  * @return
	  * @version 1.0 2014年11月4日 下午1:17:52
	*/
	Integer selectForUpdate(String table,Long id) throws DataNotFoundException;
/*	*//**
	  * @description 设置逻辑删除标志位
	  * @version 1.0 2014年4月28日 下午2:20:44
	 *//* 不允许设置全局标志位
	void setLogicDelete(Boolean logicDelete);*/
	
}
