package com.jc.system.dic.domain;

import com.jc.foundation.domain.BaseBean;

public class Dic extends BaseBean implements Cloneable {
	public static final Integer TYPE_FLAG_TRUE = 1;
	public static final Integer TYPE_FLAG_FALSE = 0;
	public static final Integer DIC_FLAG_TRUE = 1;
	public static final Integer DIC_FLAG_FALSE = 0;
	public static final Integer USE_FLAG_TRUE = 1;
	public static final Integer USER_FLAG_FALSE = 0;

	private static final long serialVersionUID = 1L;
	private String code; /* 字典码 */
	private String value; /* 字典值 */
	private String parentId; /* 父项目id */
	private Integer useFlag; /* 启用标志 */
	private Integer typeFlag; /* 字典类型标志 */
	private Integer dicFlag; /* 字典标志 */
	private Integer defaultValue; /* 字典类型默认值 */
	private Integer orderFlag; /* 字典排序标示 */
	private String dicType;   /*字典类型*/ 
	private String parentType;   /*父字典类型*/

	public Dic() {
	}

	public Dic(DicType dicType) {
		setId(dicType.getId());
		this.code = dicType.getCode();
		this.value = dicType.getValue();
		this.parentId = dicType.getParentId();
		this.defaultValue = dicType.getDefaultValue();
		setTypeFlag(Dic.TYPE_FLAG_TRUE);
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

	public Integer getUseFlag() {
		return useFlag;
	}

	public void setUseFlag(Integer useFlag) {
		this.useFlag = useFlag;
	}

	public Integer getTypeFlag() {
		return typeFlag;
	}

	public void setTypeFlag(Integer typeFlag) {
		this.typeFlag = typeFlag;
	}

	public Integer getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(Integer defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getDicFlag() {
		return dicFlag;
	}

	public void setDicFlag(Integer dicFlag) {
		this.dicFlag = dicFlag;
	}

	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}

	public Integer getOrderFlag() {
		return orderFlag;
	}
	
	public String getDicType(){
		return dicType;
	}
	
	public void setDicType(String dicType){
		this.dicType = dicType;
	}
	
	public String getParentType(){
		return parentType;
	}
	
	public void setParentType(String parentType){
		this.parentType = parentType;
	}

	@Override
	public Dic clone() {
		Dic result = null;
		try {
			result = (Dic) super.clone();
		} catch (Exception e) {
			log.error("实例复制出错；" + e);
			result = new Dic();
		}
		return result;
	}

	@Override
	public String toString() {
		return "字典:类型id:" + getParentId() + ",code:" + getCode();
	}

}