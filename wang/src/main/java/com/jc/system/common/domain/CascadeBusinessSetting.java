package com.jc.system.common.domain;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @title GOA V2.0 级联表配置类
 * @description  级联表配置类，用于描述级联关系
 * @author zhangligang
 * @version  2014年7月22日 下午4:06:40
 * @see CascadeSetting
 */
@XmlRootElement (name="business")
public class CascadeBusinessSetting {

	public CascadeBusinessSetting() {
	}
	/**
	 * 配置项目
	 */
	private CascadeSetting[] function;
	@XmlElement (name="function")
	public CascadeSetting[] getFunction() {
		return function;
	}

	public void setFunction(CascadeSetting[] function) {
		this.function = function;
	}

	
	
}
