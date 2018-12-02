package com.jc.foundation.domain;

import java.util.List;

public interface IPageManager extends Cloneable {

	/**
	 * 获取当前页
	 * 
	 * @return
	 */
	int getPage();

	/**
	 * 设置当前页码
	 * 
	 * @param page
	 */
	void setPage(int page);

	/**
	 * 获取每页显示行数
	 * 
	 * @return
	 */
	int getPageRows();

	/**
	 * 设置每页显示行数
	 * 
	 * @param rows
	 */
	void setPageRows(int rows);

	/**
	 * 获取总记录数
	 * 
	 * @return
	 */
	int getTotalCount();

	/**
	 * 设置总记录数
	 * 
	 * @param rowsCount
	 */
	void setTotalCount(int rowsCount);

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	int getTotalPages();

	/**
	 * 
	 * @param pageCount
	 */
	void setTotalPages(int pageCount);

	/**
	 * 获取数据
	 * @return
	 */
	List<? extends BaseBean> getData();

	/**
	 * 设置分页数据
	 * @param list
	 */
	void setData(List<? extends BaseBean> list);

	/**
	 * 获取每页显示行数，jQuery datatable分页用
	 * @return 每页显示行数
	 * @see #getPageRows()
	 * @see #getiDisplayLength()
	 */
	int getiDisplayLength();

	/**
	 * 获取总记录数，jQuery datatable分页用
	 * @return 总记录数
	 * @see #getTotalCount()
	 * @see #getiTotalDisplayRecords()	 
	 */
	int getiTotalRecords();

	/**
	 * 获取回显次数，jQuery datatable分页用
	 * @return 回显次数
	 */
	int getsEcho();

	/**
	  * 设置每页显示多少行，jquery分页使用 
	  * @param iDisplayLength 每页显示行数
	  * @version 1.0 2014年5月23日 下午2:07:04
	  * @see #setPageRows(int)
	 */
	void setiDisplayLength(int iDisplayLength);
	
	
	/**
	  * 设置总记录数jquery使用
	  * @param iTotalRecords 总记录数
	  * @version 1.0 2014年5月23日 下午2:09:37
	  * @see #setTotalCount(int)
	*/
	void setiTotalRecords(int iTotalRecords);
	
	/**
	  * 设置显示行数jquery使用
	  * @param sEcho 设置回显次数
	  * @version 1.0 2014年5月23日 下午2:10:45
	*/
	void setsEcho(int sEcho);
	
	/**
	  * 设置数据，jquery使用
	  * @param list 数据列表
	  * @version 1.0 2014年5月23日 下午2:12:36
	  * @see #setData(List)
	 */
	void setAaData(List<? extends BaseBean> list);

	/**
	  * 获取查询结果，jquery使用
	  * @return 查询数据结果集
	  * @version 1.0 2014年5月23日 下午2:14:24
	  * @see #getData()
	*/
	List<? extends BaseBean> getAaData();

	
	/**
	  * 设置总记录数 jquery使用
	  * @param iTotalDisplayRecords 总记录数
	  * @version 1.0 2014年5月23日 下午2:15:57
	  * @see #setiTotalRecords(int)
	  * @see #setTotalCount(int)
	*/
	void setiTotalDisplayRecords(int iTotalDisplayRecords);

	/**
	  * 获取总记录数
	  * @return  总记录数
	  * @version 1.0 2014年5月23日 下午2:16:00
	  * @see #getTotalCount()
	*/
	int getiTotalDisplayRecords();
}
