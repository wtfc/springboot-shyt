package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 网络设备采购表
*/
public class ToaNetworkProcure extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String machineNumber;//   设备型号	private String describes;//   描述	private String serialNumber;//   序列号	private Date applicationDate;//   采购申请时间	private Date arrivalDate;//   采购到货时间	private String installs;//   是否安装	private String address;//   设备位置	private String remark;//   备注	public String getMachineNumber() {	    return machineNumber;	}	public void setMachineNumber(String machineNumber) {	    this.machineNumber=machineNumber;	}	public String getSerialNumber() {	    return serialNumber;	}	public void setSerialNumber(String serialNumber) {	    this.serialNumber=serialNumber;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getApplicationDate() {	    return applicationDate;	}	public void setApplicationDate(Date applicationDate) {	    this.applicationDate=applicationDate;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getArrivalDate() {	    return arrivalDate;	}	public void setArrivalDate(Date arrivalDate) {	    this.arrivalDate=arrivalDate;	}	public String getAddress() {	    return address;	}	public void setAddress(String address) {	    this.address=address;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getInstalls() {
		return installs;
	}
	public void setInstalls(String installs) {
		this.installs = installs;
	}}

