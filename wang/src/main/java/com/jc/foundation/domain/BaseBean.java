package com.jc.foundation.domain;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.constants.OrderType;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.common.util.StringUtil;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
public class BaseBean implements IBaseBean {

	private static final long serialVersionUID = -1771748797466926767L;

	protected Date createDate; // 创建时间

	protected Date createDateBegin;// 创建时间

	protected Date createDateEnd;// 创建时间

	protected Long createUser; // 创建人员

	protected Long createUserDept; // 创建用户部门

	protected Long createUserOrg; // 创建用户组织

	protected Integer deleteFlag = new Integer(0); // 删除标志位

	protected Date extDate1;// 预留时间字段1

	protected Date extDate1Begin;// 预留时间字段1

	protected Date extDate1End;// 预留时间字段1

	protected Date extDate2;// 预留时间字段2

	protected Date extDate2Begin;

	protected Date extDate2End;

	protected BigDecimal extNum1;// 预留数字字段1

	protected BigDecimal extNum2;// 预留数字字段2

	protected BigDecimal extNum3;// 预留数字字段3
	
	protected String dataAccessDynamicSQL;

	public Date getExtDate1End() {
		return extDate1End;
	}

	public void setExtDate1End(Date extDate1End) {
		this.extDate1End = extDate1End;
	}

	public Date getExtDate2Begin() {
		return extDate2Begin;
	}

	public void setExtDate2End(Date extDate2End) {
		this.extDate2End = extDate2End;
	}

	protected String extStr1;// 预留字符字段1

	protected String extStr2;// 预留字符字段2

	protected String extStr3;// 预留字符字段3

	protected String extStr4;// 预留字符字段4

	protected String extStr5;// 预留字符字段5

	protected Long id;

	// private String orderBy ;

	private Map<String, OrderType> orderByMap = new LinkedHashMap<>();
	protected transient final Logger log = Logger.getLogger(this.getClass());

	protected Date modifyDate; // 修改时间

	protected Date modifyDateBegin; // 修改时间

	protected Date modifyDateEnd; // 修改时间

	protected Date modifyDateNew;

	protected Long modifyUser; // 修改人员

	private Integer weight;     /** 安全系数 */
	
	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
	
	public Long getModifyUser() {
		return modifyUser;
	}

	/**
	 * 用于批量删除时传入主健的ids用逗号分隔
	 */
	public String[] primaryKeys;

	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getCreateDate() {
		return createDate;
	}

	public Date getCreateDateBegin() {
		return createDateBegin;
	}

	public Date getCreateDateEnd() {
		return createDateEnd;
	}

	public Long getCreateUser() {
		return createUser;
	}

	public Long getCreateUserDept() {
		return createUserDept;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jc.foundation.domain.IBaseBean#getCreateUserOrg()
	 */
	public Long getCreateUserOrg() {
		return createUserOrg;
	}

	/**
	 * @description 获取指定属性的get方法返回值，如果为空的话返回空串
	 * @param name 属性名字
	 * @return String 获得的值，如果为null的话 返回""
	 * @author
	 * @version 2014-03-24
	 */
	public String getDefaultValue(String name) {
		return getDefaultValue(name, "");
	}

	/**
	 * @description 获取指定属性的get方法返回值
	 * @param name 属性名字
	 * @param def 属性如果为空的话，返回的值
	 * @return String get返回值
	 * @author
	 * @version 2014-03-24
	 */
	public String getDefaultValue(String name, String def) {
		try {
			if (name != null && name.length() > 0) {
				Method method = this.getClass().getMethod(
						"get" + name.substring(0, 1).toUpperCase()
								+ name.substring(1));
				Object obj = method.invoke(this);
				if (obj == null) {
					return def;
				}
				return String.valueOf(obj);
			}
		} catch (SecurityException e) {
			log.error("获取属性错误", e);
		} catch (Exception e) {
			log.error(e);
		}
		return name;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getExtDate1() {
		return extDate1;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getExtDate2() {
		return extDate2;
	}

	public BigDecimal getExtNum1() {
		return extNum1;
	}

	public BigDecimal getExtNum2() {
		return extNum2;
	}

	public BigDecimal getExtNum3() {
		return extNum3;
	}

	public String getExtStr1() {
		return extStr1;
	}

	public String getExtStr2() {
		return extStr2;
	}

	public String getExtStr3() {
		return extStr3;
	}

	public String getExtStr4() {
		return extStr4;
	}

	public String getExtStr5() {
		return extStr5;
	}

	public Long getId() {
		return id;
	}

	/**
	 * @description 打印到log的字符串，默认为bean的toString方法
	 * @return 结果字符串
	 * @author 孙圣然
	 * @version 1.0 上午11:31:34
	 */
	public String toLogMsg() {
		return this.toString();
	}

	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getModifyDate() {
		return modifyDate;
	}

	public Date getModifyDateBegin() {
		return modifyDateBegin;
	}

	public Date getModifyDateEnd() {
		return modifyDateEnd;
	}

	public Date getModifyDateNew() {
		return modifyDateNew;
	}

	public String[] getPrimaryKeys() {
		return primaryKeys;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateDateBegin(Date createDateBegin) {
		this.createDateBegin = createDateBegin;
	}

	public void setCreateDateEnd(Date createDateEnd) {
		this.createDateEnd = createDateEnd;
	}

	public void setCreateUser(Long createUser) {
		this.createUser = createUser;
	}

	public void setCreateUserDept(Long createUserDept) {
		this.createUserDept = createUserDept;
	}

	public void setCreateUserOrg(Long createUserOrg) {
		this.createUserOrg = createUserOrg;
	}

	/**
	 * @description 设置指定属性的值
	 * @param name 属性名字
	 * @param def 设置属性的值
	 * @author
	 * @version 2014-03-24
	 */
	@SuppressWarnings("rawtypes")
	public void setDefaultValue(String name, String def) {
		try {
			if (name != null && name.length() > 0) {
				Class[] argsClass = new Class[1];
				argsClass[0] = String.class;
				Method method = this.getClass().getMethod(
						"set" + name.substring(0, 1).toUpperCase()
								+ name.substring(1), argsClass);
				method.invoke(this, def);
			}
		} catch (SecurityException e) {
			log.error("获取属性错误", e);
		} catch (Exception e) {
			log.error(e);
		}
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setExtDate1(Date extDate1) {
		this.extDate1 = extDate1;
	}

	public void setExtDate1Begin(Date extDate1Begin) {
		this.extDate1Begin = extDate1Begin;
	}

	public Date getExtDate1Begin() {
		return extDate1Begin;
	}

	public void setExtDate2Begin(Date extDate2Begin) {
		this.extDate2Begin = extDate2Begin;
	}

	public Date getExtDate2End() {
		return extDate2End;
	}

	public void setExtDate2(Date extDate2) {
		this.extDate2 = extDate2;
	}

	public void setExtNum1(BigDecimal extNum1) {
		this.extNum1 = extNum1;
	}

	public void setExtNum2(BigDecimal extNum2) {
		this.extNum2 = extNum2;
	}

	public void setExtNum3(BigDecimal extNum3) {
		this.extNum3 = extNum3;
	}

	public void setExtStr1(String extStr1) {
		this.extStr1 = extStr1;
	}

	public void setExtStr2(String extStr2) {
		this.extStr2 = extStr2;
	}

	public void setExtStr3(String extStr3) {
		this.extStr3 = extStr3;
	}

	public void setExtStr4(String extStr4) {
		this.extStr4 = extStr4;
	}

	public void setExtStr5(String extStr5) {
		this.extStr5 = extStr5;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public void setModifyDateBegin(Date modifyDateBegin) {
		this.modifyDateBegin = modifyDateBegin;
	}

	public void setModifyDateEnd(Date modifyDateEnd) {
		this.modifyDateEnd = modifyDateEnd;
	}

	public void setModifyDateNew(Date modifyDateNew) {
		this.modifyDateNew = modifyDateNew;
	}

	public void setModifyUser(Long modifyUser) {
		this.modifyUser = modifyUser;
	}

	public void setPrimaryKeys(String[] primaryKeys) {
		this.primaryKeys = primaryKeys;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.jc.foundation.domain.IBaseBean#addOrderByField(java.lang.String)
	 */
	@Override
	public void addOrderByField(String fieldName) {
		addOrderByField(fieldName, OrderType.ASC);
	}

	@Override
	public void addOrderByFieldDesc(String fieldName) {
		addOrderByField(fieldName, OrderType.DESC);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addOrderByField(String fieldName, OrderType orderType) {
		orderByMap.put(fieldName, orderType);
	}

	@Override
	public String getOrderBy() {
		int size = orderByMap.size();
		if (size == 0) {
			return null;
		} else {
			StringBuffer buffer = new StringBuffer();
			Iterator<Map.Entry<String, OrderType>> iterator = orderByMap
					.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry<String, OrderType> entry = iterator.next();
				buffer.append(entry.getKey()).append(" ")
						.append(entry.getValue()).append(",");
			}
			return buffer.substring(0, buffer.length() - 1);
		}
	}

	public void setOrderBy(String orderBy) {
		orderByMap.clear();
		if (StringUtil.isEmpty(orderBy)) {
			return;
		}
		String[] orders = orderBy.split(",");
		for (int i = 0; i < orders.length; i++) {
			String order = orders[i];
			String[] fieldAndOrderType = order.split("\\s+");
			String field = fieldAndOrderType[0];
			OrderType orderType = OrderType.ASC;
			if (fieldAndOrderType.length != 1) {
				orderType = OrderType.valueOf(fieldAndOrderType[1]
						.toUpperCase());
			}
			orderByMap.put(field, orderType);
		}
	}

	@Override
	public String getDataAccessDynamicSQL() {
		return this.dataAccessDynamicSQL;
	}

	@Override
	public void setDataAccessDynamicSQL(String dataAccessDynamicSQL) {
		this.dataAccessDynamicSQL = dataAccessDynamicSQL;
	}
}
