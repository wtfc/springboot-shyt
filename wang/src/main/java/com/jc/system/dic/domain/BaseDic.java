package com.jc.system.dic.domain;

/**
 * @description: 基础字典类，用于自定义字典继承bean
 */
public interface BaseDic extends Cloneable {
	/**
	 * @description 获得字典码
	 */
	String getCode();

	/**
	 * @description 获得对应显示值
	 */
	String getValue();

	/**
	 * @description 获得父Id
	 */
	String getParentId();

	/**
	 * @description 是否是根节点
	 */
	boolean isRoot();

	Object clone();

	void addChildren(BaseDic dic);
}
