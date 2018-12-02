/**
 * 
 */
package com.jc.foundation.service;

import java.util.List;

import com.jc.foundation.domain.PageManager;
import com.jc.system.CustomException;

/**
 * @description: 
 * @created: 2014年4月3日 下午4:53:30 
 * @version：$Id$ 
 */

public interface IBaseService<T> {
	
	/**
	  * @description 保存实体
	  * @param t  实体对象
	  * @return  返回数据库影响的记录数
	  * @throws CustomException
	  * @version 1.0 2014年4月28日 下午4:58:01
	 */
	Integer save(T t) throws CustomException;

	/**
	  * @description  批量保存方法
	  * @param list 实体类集合
	  * @return 返回数据库影响的记录数
	  * @throws CustomException
	  * @version 1.0 2014年4月28日 下午4:56:21
	*/
	Integer saveList(List<T> list) throws CustomException;
	
	/**
	 * @description 修改方法
	  * @param t  实体对象
	  * @return  返回数据库影响的记录数
	 * @version 1.0 2014年4月28日 下午4:56:21
	 */
	Integer update(T t) throws CustomException;

	/**
	 * @description 查询单条记录方法
	 * @param t 实体对象
	 * @return 符合条件实体对象 
	 * @throws CustomException
	 * @version  1.0  2014-03-20
	 */
	T get(T t) throws CustomException ;

	/**
	 * @description 分页查询方法
	 * @param t 实体对象条件
	 * @param page 分页实体条件
	 * @return 分页结果
	 * @author
	 * @version 2014-03-20
	 */
	PageManager query(T t, PageManager page);
	/**
	 * @description 数据权限分页查询方法
	 * @param t 实体对象条件
	 * @param page 分页实体条件
	 * @return 分页结果
	 * @author
	 * @version 2014-03-20
	 */
	PageManager queryForPermission(T t,PageManager page) throws CustomException ;
	
	/**
	 * @description 数据权限分页查询方法 android使用
	 * @param t 实体对象条件
	 * @param page 分页实体条件
	 * @return 分页结果
	 * @author
	 * @version 2014-03-20
	 */
	PageManager queryForPermission4M(T t, String userId,PageManager page) throws CustomException ;
	
	/**
	 * 数据权限分页查询方法--自定义语句
	 * @param t
	 * @param page
	 * @return
	 * @throws CustomException
	 * @version 1.0 2014年7月8日 下午4:28:18
	 */
	PageManager queryForPermission(T t, PageManager page, String countSQL, String querySQL) throws CustomException ;
	
	/**
	 * @description 根据主键删除记录方法（可以批量删除）
	 * @param t 实体对象
	 * @return  返回数据库影响的记录数
	 * @author
	 * @version 2014-03-20
	 */
	Integer delete(T t) throws CustomException;
	
	/**
	 * @description 根据主键删除记录方法（可以批量删除）
	 * @param t 实体对象
	 * @return  返回数据库影响的记录数
	 * @author
	 * @version 2014-03-20
	 */
	Integer delete(T t,Boolean logicDelete) throws CustomException;
	
	/**
	  * @description 查询符合条件的所有记录
	  * @param t 实体对象
	  * @return  实体对象集合
	  * @throws CustomException
	  * @version 1.0 2014年4月28日 下午4:55:03
	*/
	List<T> queryAll(T t)  throws CustomException;
}
