package com.jc.foundation.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.util.ReflectHelper;
import com.jc.system.DBException;
import com.jc.system.exception.DataNotFoundException;
import com.jc.system.security.exception.ConcurrentException;

/**
 * @title GOA2.0
 * @description 基础Dao实体类
 * @author
 * @version 2014-03-24
 * 
 */
public class BaseDaoImpl<T extends BaseBean> implements IBaseDao<T> {
	/**
	 * 插入记录SQL ID
	 */
	public static final String SQL_INSERT= "insert";
	
	/**
	 * 插入记录SQL ID
	 */
	public static final String SQL_INSERT_LIST= "insertList";
	
	/**
	 * 更新记录SQL ID
	 */
	public static final String SQL_UPDATE= "update";
	/**
	 * 删除记录SQL ID
	 */
	public static final String SQL_DELETE= "delete";
	/**
	 * 条件查询SQL ID
	 */
	public static final String SQL_QUERY= "query";
	/**
	 * 统计记录条数SQL ID
	 */
	public static final String SQL_QUERY_COUNT= "queryCount";
	
	/**
	 * 批量删除多个ID对应记录 SQL ID
	 */
	public static final String SQL_DELETE_LOGIC= "deleteLogic";
	
	/**
	 * 批量删除多个ID对应记录 SQL ID
	 */
	public static final String SQL_DELETE_PHYSICAL = "deletePhysical";
	
	/**
	 * 锁定行数据
	 */
	public static final String SQL_SELECT_FOR_UPDATE = "com.jc.foundation.selectForUpdate";
	
	@Autowired
	protected SqlSessionTemplate template;

	public Integer save(T o) throws DBException {
		Integer result = -1;
		try {
			result = template.insert(getNameSpace(o) + "."+SQL_INSERT, o);
		} catch (Exception e) {
			DBException exception = new DBException(e);
			exception.setLogMsg("数据库添加数据发生错误");
			throw exception;
		}
		return result;
	}
	
	public Integer save(List<T> list) throws DBException{
		if(list==null||list.size()==0){
			return 0;
		}
		Integer result = -1;
		try {
			result =template.insert(getNameSpace(list.get(0)) + "."+SQL_INSERT_LIST, list);
		} catch (Exception e) {
			DBException exception = new DBException(e);
			exception.setLogMsg("数据库批量添加数据发生错误");
			throw exception;
		}
		return result;
	}

	public Integer update(T o) throws DBException {
		Integer result = -1;
		try {
			result = template.update(getNameSpace(o)   + "."+SQL_UPDATE, o);
		} catch (Exception e) {
			DBException exception = new DBException(e);
			exception.setLogMsg("数据库更新数据发生错误");
			throw exception;
		}
		if(result == 0){
			throw new ConcurrentException("数据已被修改，请刷新后重新操作");
		}
		return result;
	}

	public List<T> queryAll(T o){
		List<T> list = template.selectList(getNameSpace(o) + "."+SQL_QUERY, o);
		return list;
	}

	public PageManager queryByPage(T o,final PageManager page,String countSQL,String querySQL){
		ReflectHelper.escapeSQLSpecialChar(o);
		PageManager page_ = new PageManager();
		// 查询行数 mapping xml中返回类型有的是long有的是integer，临时处理办法
		Object rowsCountObject = template.selectOne(getNameSpace(o) + "."+countSQL,
				o);
		int rowsCount = 0;
		if(rowsCountObject instanceof Long){
			rowsCount = ((Long) rowsCountObject).intValue();
		}
		else {
			rowsCount = ((Integer) rowsCountObject).intValue();
		}
		page_.setTotalCount(rowsCount);
		if(page.getPageRows()==-1){
			page.setPageRows(Integer.MAX_VALUE);
		}
		// 计算页数 page.getRows()获得每页显示条数，系统中固定值
		int pageCount = rowsCount / page.getPageRows();
		if (rowsCount % page.getPageRows() > 0) {
			pageCount++;
		}
		// 如果传过来的当前页码大于总页码 则把当前页码设置为最大页码
		if (page.getPage() + 1 > pageCount && pageCount != 0) {
			page.setPage(pageCount);
		}

		// 将页面传过来的当前页传回到前台
		page_.setPage(page.getPage() + 1);

		List<T> list = template.selectList(
				getNameSpace(o) + "."+querySQL,
				o,
				new RowBounds((page.getPage()) * page.getPageRows(), page
						.getPageRows()));

		page_.setTotalPages(pageCount);
		page_.setData(list);
		page_.setsEcho(page.getsEcho());
		return page_;
	}
	
	public PageManager queryByPage(T o,final PageManager page){
		return queryByPage(o,page,SQL_QUERY_COUNT,SQL_QUERY);
	}
	
	public PageManager queryForPermissionByPage(T o, PageManager page) {
		return queryForPermissionByPage(o, page, SQL_QUERY_COUNT, SQL_QUERY);
	}
	
	public PageManager queryForPermissionByPage(T o, PageManager page, String countSQL,String querySQL) {
		return queryByPage(o, page, countSQL, querySQL);
	}
	
	@SuppressWarnings("unchecked")
	public T get(T o){
		return (T) template.selectOne(getNameSpace(o) + "."+SQL_QUERY, o);
	}

	public Long getCount(T o) {
		Object rowsCountObject = template.selectOne(getNameSpace(o) + "."+SQL_QUERY_COUNT, o);
		if(rowsCountObject instanceof Long){
			//rowsCountObject = ((Long) rowsCountObject).intValue();
			return (Long) rowsCountObject;
		}
		else {
			return ((Integer) rowsCountObject).longValue();
		}
	}

	/**
	 * 获得实体类的路径字符串作为map的namespace
	 * @param o 实体类
	 * @return String 实体类的路径字符串
	 * @author
	 * @version 2014-03-24
	 */
	protected String getNameSpace(T o) {
		return getClassName(o);
	}

	/**
	 * 获得类名
	 * @param o 实体类
	 * @return String 实体类的类名
	 * @author
	 * @version 2014-03-24
	 */
	@SuppressWarnings("unchecked")
	private String getClassName(T o) {
		Object obj = (Object) o;
		Class<T> cls = (Class<T>) obj.getClass();
		return cls.getName();
	}

	@Override
	public Boolean isLogicDelete() {
		return logicDelete;
	}

	private boolean logicDelete = true;

	@Override
	public Integer delete(T o, Boolean logicDelete) throws DBException {
		Integer result = null;
		try {
			if(logicDelete){
				 result = template.update(getNameSpace(o) +"."+SQL_DELETE_LOGIC, o);
			}
			else{
				 result = template.delete(getNameSpace(o) + "."+SQL_DELETE_PHYSICAL, o);
			}
		} catch (Exception e) {
			DBException exception = new DBException(e);
			exception.setLogMsg("数据库删除数据数量发生错误");
			throw exception;
		}
		return result;
	}
	
	/**
	  * 锁定行数据
	  * 在锁的方法名中不能以get开头
	  * @param table表名
	  * @param id	锁定的数据id
	  * @return
	  * @version 1.0 2014年11月4日 下午1:17:52
	 * @throws DataNotFoundException 
	*/
	public Integer selectForUpdate(String table,Long id) throws DataNotFoundException{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("table", table);
		map.put("id", id);
		int result = template.selectOne(SQL_SELECT_FOR_UPDATE, map);
		if(result == 0){
			throw new DataNotFoundException("数据没有找到,table"+table+",id:"+id);
		}
		return result;
	}
	
	
	public Integer delete(T o) throws DBException {
		return this.delete(o, this.isLogicDelete());
	}
	
}
