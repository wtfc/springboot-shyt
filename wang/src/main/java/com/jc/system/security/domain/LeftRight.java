package com.jc.system.security.domain;

import com.jc.foundation.domain.BaseBean;

/**
 * @title GOA V2.0
 * @description 左右选择树 实体类
 * @version  2014年6月11日
 */
public class LeftRight extends BaseBean {
	
	private static final long serialVersionUID = 1396039427853446822L;
	
	private String code;		//选择项标志
	private String text;		//选择项显示内容
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
}
