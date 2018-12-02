package com.jc.system.dic.domain;

import java.util.ArrayList;
import java.util.List;

import com.jc.foundation.domain.BaseBean;

/**
 * @description: 字典类型类
 */
public class DicType extends BaseBean implements Cloneable {
	public static final Integer TYPE_FLAG_TRUE = 1;
	public static final Integer TYPE_FLAG_FALSE = 0;
	public static final Integer USE_FLAG_TRUE = 1;
	public static final Integer USER_FLAG_FALSE = 0;

	private static final long serialVersionUID = 1L;
	private String code; /* 字典码 */
	private String value; /* 字典值 */
	private String parentId; /* 父项目id */
	private Integer defaultValue; /* 字典类型默认值 */
	private String dicFlag; /* 字典标识 */
	private List<DicType> children = null; /* 子类型 */

	private String str1; /* 预留字段1 */
	private String str2; /* 预留字段2 */
	private String str3; /* 预留字段3 */
	private String str4; /* 预留字段4 */
	private String str5; /* 预留字段5 */

	public DicType() {
	}

	public DicType(Dic dic) {
		setId(dic.getId());
		this.code = dic.getCode();
		this.value = dic.getValue();
		this.parentId = dic.getParentId();
		this.defaultValue = dic.getDefaultValue();
	}

	public List<DicType> getChildren() {
		return children;
	}

	public void addChildren(DicType type) {
		if (children == null) {
			children = new ArrayList<DicType>();
		}
		children.add(type);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Integer defaultValue) {
		this.defaultValue = defaultValue;
	}

	public DicType clone() {
		try {
			return (DicType) super.clone();
		} catch (Exception e) {
			log.error("对象复制出错");
		}
		return new DicType();
	}

	public void setDicFlag(String dicFlag) {
		this.dicFlag = dicFlag;
	}

	public String getDicFlag() {
		return dicFlag;
	}

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public String getStr3() {
		return str3;
	}

	public void setStr3(String str3) {
		this.str3 = str3;
	}

	public String getStr4() {
		return str4;
	}

	public void setStr4(String str4) {
		this.str4 = str4;
	}

	public String getStr5() {
		return str5;
	}

	public void setStr5(String str5) {
		this.str5 = str5;
	}
}