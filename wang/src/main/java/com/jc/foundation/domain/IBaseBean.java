package com.jc.foundation.domain;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.annotations.Mapper;

import com.jc.foundation.constants.OrderType;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public interface IBaseBean extends java.io.Serializable {

	/**
	  * 获得主键
	  * @return 主键
	  * @version 1.0 2014年6月9日 下午4:03:28
	*/
	Long getId();
	
	/**
	 *  获取创建时间
	 * @return 创建时间
	 * @version 1.0 2014年4月28日 上午9:18:58
	 */
	Date getCreateDate();

	/**
	 *  获取创建人员ID
	 * @return
	 * @version 1.0 2014年4月28日 上午9:19:20
	 */
	Long getCreateUser();

	/**
	 *  获取创建人员所在部门ID
	 * @return
	 * @version 1.0 2014年4月28日 上午9:20:22
	 */
	Long getCreateUserDept();

	/**
	 *  获取创建人员所在组织ID
	 * @return
	 * @version 1.0 2014年4月28日 上午9:23:24
	 */
	Long getCreateUserOrg();

	/**
	 *  获取指定属性的get方法返回值，如果为空的话返回空串
	 * @param name
	 *            属性名字
	 * @return String 获得的值，如果为null的话 返回""
	 * @author
	 * @version 2014-03-24
	 */
	String getDefaultValue(String name);

	/**
	 *  获取指定属性的get方法返回值
	 * @param name
	 *            属性名字
	 * @param def
	 *            属性如果为空的话，返回的值
	 * @return String get返回值
	 * @author
	 * @version 2014-03-24
	 */
	String getDefaultValue(String name, String def);

	/**
	 * 
	 *  获取删除标志
	 * @return
	 * @version 1.0 2014年4月28日 上午9:23:47
	 */
	Integer getDeleteFlag();


	Date getExtDate1();

	Date getExtDate2();

	BigDecimal getExtNum1();

	BigDecimal getExtNum2();

	BigDecimal getExtNum3();

	String getExtStr1();

	String getExtStr2();

	String getExtStr3();

	String getExtStr4();

	String getExtStr5();

	/**
	 *  打印到log的字符串，默认为bean的toString方法
	 * @return 结果字符串
	 * @version 1.0 上午11:31:34
	 */
	String toLogMsg();

	/**
	  *  获取修改时间
	  * @return
	  * @version 1.0 2014年4月28日 上午10:10:23
	*/
	Date getModifyDate();

	/**
	 * 
	  *  获取新的修改时间
	  * @return
	  * @version 1.0 2014年4月28日 上午10:11:10
	 */
	Date getModifyDateNew();

	/**
	 * 
	  *  获取修改人员Id
	  * @return
	  * @version 1.0 2014年4月28日 上午10:11:25
	 */
	Long getModifyUser();

	/**
	 * 
	  *  获取要删除的主键数组
	  * @return
	  * @version 1.0 2014年4月28日 上午10:09:44
	 */
	String[] getPrimaryKeys();

	/**
	  * 设置主键
	  * @version 1.0 2014年6月9日 下午4:03:57
	*/
	void setId(Long id);
	
	/**
	 * 
	  *  设置创建日期
	  * @param createDate
	  * @version 1.0 2014年4月28日 上午10:11:44
	 */
	void setCreateDate(Date createDate);

	/**
	 * 
	  *  设置创建人员ID
	  * @param createUser
	  * @version 1.0 2014年4月28日 上午10:12:00
	 */
	void setCreateUser(Long createUser);

	/**
	 * 
	  *  设置创建人员所在部门ID
	  * @param createUserDept
	  * @version 1.0 2014年4月28日 上午10:12:13
	 */
	void setCreateUserDept(Long createUserDept);

	/**
	 * 
	  *  设置创建人员所在组织ID
	  * @param createUserOrg
	  * @version 1.0 2014年4月28日 上午10:12:38
	 */
	void setCreateUserOrg(Long createUserOrg);

	/**
	 *  设置指定属性的值
	 * @param name
	 *            属性名字
	 * @param def
	 *            设置属性的值
	 * @author
	 * @version 2014-03-24
	 */
	void setDefaultValue(String name, String def);

	/**
	 * 
	  *  设置删除标志
	  * @param deleteFlag
	  * @version 1.0 2014年4月28日 上午10:13:06
	 */
	void setDeleteFlag(Integer deleteFlag);

	void setExtDate1(Date extDate1);

	void setExtDate2(Date extDate2);

	void setExtNum1(BigDecimal extNum1);

	void setExtNum2(BigDecimal extNum2);

	void setExtNum3(BigDecimal extNum3);

	void setExtStr1(String extStr1);

	void setExtStr2(String extStr2);

	void setExtStr3(String extStr3);

	void setExtStr4(String extStr4);

	void setExtStr5(String extStr5);

	/**
	 *  设置修改时间
	 * @param modifyDate
	 * @version 1.0 2014年4月28日 上午9:54:51
	 */
	void setModifyDate(Date modifyDate);

	/**
	 * 
	 *  更新时设置新的修改时间
	 * @param modifyDateNew
	 * @version 1.0 2014年4月28日 上午9:55:22
	 */
	void setModifyDateNew(Date modifyDateNew);

	/**
	 *  设置修改人员ID
	 * @param modifyUser
	 * @version 1.0 2014年4月28日 上午9:56:08
	 */
	void setModifyUser(Long modifyUser);

	/**
	 * 
	 * @param primaryKeys
	 * @version 1.0 2014年4月28日 上午9:56:14
	 */
	void setPrimaryKeys(String[] primaryKeys);

	/**
	 *  增加升序字段
	 * @param fieldName   升序字段
	 * @version 1.0 2014年4月28日 上午9:56:17
	 */
	void addOrderByField(String fieldName);

	/**
	 *  增加降序字段
	 * @param fieldName
	 * @version 1.0 2014年4月28日 上午9:56:17
	 */
	void addOrderByFieldDesc(String fieldName);
	
	/**
	 *  增加排序字段
	 * @param sort   
	 * @version 1.0 2014年4月28日 上午9:56:17
	 */
	void addOrderByField(String fieldName,OrderType orderType);
	

	
	/**
	  *  获取排序字符串
	  * @return 排序字符串
	  * @version 1.0 2014年4月28日 上午10:15:00
	*/
	public String getOrderBy();
	

	
	/**
	  *  获取排序字符串
	  * @return 排序字符串
	  * @version 1.0 2014年4月28日 上午10:15:00
	*/
	public void setOrderBy(String orderBy);
	
	/**
	 *  获取数据访问动态SQL，用于数据层权限 
	 *  @return 根据用户角色、权限生成的动态SQL
	  * @version 1.0 2014年6月24日 上午9:29:33
	 */
	public String getDataAccessDynamicSQL();
	
	/**
	 *  设置数据访问动态SQL，用于数据层权限 
	 *  @param dataAccessDynamicSQL 动态SQL
	  * @version 1.0 2014年6月24日 上午9:29:33
	 */
	public void setDataAccessDynamicSQL(String dataAccessDynamicSQL);
	
	/**
	  * 获取权重
	  * @return
	  * @version 1.0 2014年5月23日 下午2:20:44
	*/
	Integer getWeight();
	
	/**
	  * 设置权重
	  * @param weight 权重值
	  * @version 1.0 2014年5月23日 下午2:20:59
	*/
	void setWeight(Integer weight);
}
