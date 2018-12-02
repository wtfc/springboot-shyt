package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
/**
 * @author mrb
 * @version 网络设备统计表
*/
public class ToaNetworkMachine extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String machine;//   设备品牌	private String machineName;//   设备名称	private String machineType;//   设备类型	private String loginIp;//   登录IP	private String machineNumber;//   设备型号	private String describes;//   设备软件版本	private String serialNumber;//   序列号SN	private String address;//   所在位置	private String remark;//   备注	public String getMachine() {	    return machine;	}	public void setMachine(String machine) {	    this.machine=machine;	}	public String getMachineName() {	    return machineName;	}	public void setMachineName(String machineName) {	    this.machineName=machineName;	}	public String getMachineType() {	    return machineType;	}	public void setMachineType(String machineType) {	    this.machineType=machineType;	}	public String getLoginIp() {	    return loginIp;	}	public void setLoginIp(String loginIp) {	    this.loginIp=loginIp;	}	public String getMachineNumber() {	    return machineNumber;	}	public void setMachineNumber(String machineNumber) {	    this.machineNumber=machineNumber;	}	public String getDescribes() {	    return describes;	}	public void setDescribes(String describes) {	    this.describes=describes;	}	public String getSerialNumber() {	    return serialNumber;	}	public void setSerialNumber(String serialNumber) {	    this.serialNumber=serialNumber;	}	public String getAddress() {	    return address;	}	public void setAddress(String address) {	    this.address=address;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

