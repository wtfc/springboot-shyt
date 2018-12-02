package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;
/**
 * @author mrb
 * @version 退租客户记录表
*/
public class ToaNetworkTenancies extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyId;//   客户id	private String companyName;//   客户名称	private String room;//   机房	private Date tenanciesDate;//   退租时间	private String bandwidth;//   带宽	private String ipAddress;//   IP地址	private String machine;//   设备	private String port;//   端口	private String remark;//   备注	public String getCompanyId() {	    return companyId;	}	public void setCompanyId(String companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getRoom() {	    return room;	}	public void setRoom(String room) {	    this.room=room;	}
	
	public String getRoomValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getRoom()) == null ? "" : dicManager.getDic("room", this.getRoom()).getValue();
	}
		public Date getTenanciesDate() {	    return tenanciesDate;	}	public void setTenanciesDate(Date tenanciesDate) {	    this.tenanciesDate=tenanciesDate;	}	public String getBandwidth() {	    return bandwidth;	}	public void setBandwidth(String bandwidth) {	    this.bandwidth=bandwidth;	}	public String getIpAddress() {	    return ipAddress;	}	public void setIpAddress(String ipAddress) {	    this.ipAddress=ipAddress;	}	public String getMachine() {	    return machine;	}	public void setMachine(String machine) {	    this.machine=machine;	}	public String getPort() {	    return port;	}	public void setPort(String port) {	    this.port=port;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

