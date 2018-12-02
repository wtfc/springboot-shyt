package com.jc.system.common.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.domain.ISecurityBean;

/**
 * 
 * @title GOA V2.0
 * @description 安全系数管理bean
 * @author 王世元
 * @version  2014年5月7日上午8:52:03
 */
public class SecurityBean extends BaseBean implements ISecurityBean {
	private static final long serialVersionUID = 8370618015406833649L;
	private Integer weight;     /** 安全系数 */
	
	@Override
	public Integer getWeight() {
		return weight;
	}
	
	@Override
	public void setWeight(Integer weight) {
		this.weight = weight;
	}
}
