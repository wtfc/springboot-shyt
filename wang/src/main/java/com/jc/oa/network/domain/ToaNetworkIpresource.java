package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
/**
 * @author mrb
 * @version IP总资源表
*/
public class ToaNetworkIpresource extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String ipOne;//   IP地址	private String routeNumber;//   路由条目数	private String airingStatus;//   是否广播	private String airingName;//   广播运营商名称	private String remark;//   备注	public String getIpOne() {	    return ipOne;	}	public void setIpOne(String ipOne) {	    this.ipOne=ipOne;	}	public String getRouteNumber() {	    return routeNumber;	}	public void setRouteNumber(String routeNumber) {	    this.routeNumber=routeNumber;	}	public String getAiringStatus() {	    return airingStatus;	}	public void setAiringStatus(String airingStatus) {	    this.airingStatus=airingStatus;	}	public String getAiringName() {	    return airingName;	}	public void setAiringName(String airingName) {	    this.airingName=airingName;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

