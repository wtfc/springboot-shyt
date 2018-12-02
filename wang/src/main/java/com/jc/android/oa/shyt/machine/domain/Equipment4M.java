package com.jc.android.oa.shyt.machine.domain;

import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

public class Equipment4M {
	
	private Long id;
	private Long companyId;//客户id
	private String equipmentNumber;//设备编号
	private String clientName;//客户名称
	private String machina;//机房区域
	private String machinaId;//机房id
	private String machinaNumber;//机柜编号
	private String contact;//联系人
	private String address;//机柜位置
	private String type;//品牌类型
	private String netmaskOne;//eth1 ip/netmask
	private Date onlineDate;//上架时间
	private String interchangerTwo;//交换机对应端口
	private String netmaskTwo;//eth2 ip/netmask
	private String interchangerOne;//交换机对应端口
	private String ip;//管理网ip
	private String interchangerThree;//交换机对应端口
	private String power;//电源（单/双电）
	private String plantPower;//设备功率
	private Integer uCount;//u数
	private String functionn;//设备功能
	private String port;//上联端口
	private String aCurrent;//a路电流
	private String bCurrent;//b路电流
	private String system;//操作系统
	private String device;//设备配置
	private String serialNumber;//资产编号
	private String sn;//sn
	private String remark;//备注
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getMachina() {
		return machina;
	}
	public void setMachina(String machina) {
		this.machina = machina;
	}
	public String getMachinaId() {
		return machinaId;
	}
	public void setMachinaId(String machinaId) {
		this.machinaId = machinaId;
	}
	public String getMachinaNumber() {
		return machinaNumber;
	}
	public void setMachinaNumber(String machinaNumber) {
		this.machinaNumber = machinaNumber;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNetmaskOne() {
		return netmaskOne;
	}
	public void setNetmaskOne(String netmaskOne) {
		this.netmaskOne = netmaskOne;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}
	public String getInterchangerTwo() {
		return interchangerTwo;
	}
	public void setInterchangerTwo(String interchangerTwo) {
		this.interchangerTwo = interchangerTwo;
	}
	public String getNetmaskTwo() {
		return netmaskTwo;
	}
	public void setNetmaskTwo(String netmaskTwo) {
		this.netmaskTwo = netmaskTwo;
	}
	public String getInterchangerOne() {
		return interchangerOne;
	}
	public void setInterchangerOne(String interchangerOne) {
		this.interchangerOne = interchangerOne;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getInterchangerThree() {
		return interchangerThree;
	}
	public void setInterchangerThree(String interchangerThree) {
		this.interchangerThree = interchangerThree;
	}
	public String getPower() {
		return power;
	}
	public void setPower(String power) {
		this.power = power;
	}
	public String getPlantPower() {
		return plantPower;
	}
	public void setPlantPower(String plantPower) {
		this.plantPower = plantPower;
	}
	public Integer getuCount() {
		return uCount;
	}
	public void setuCount(Integer uCount) {
		this.uCount = uCount;
	}
	public String getFunctionn() {
		return functionn;
	}
	public void setFunctionn(String functionn) {
		this.functionn = functionn;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getaCurrent() {
		return aCurrent;
	}
	public void setaCurrent(String aCurrent) {
		this.aCurrent = aCurrent;
	}
	public String getbCurrent() {
		return bCurrent;
	}
	public void setbCurrent(String bCurrent) {
		this.bCurrent = bCurrent;
	}
	public String getSystem() {
		return system;
	}
	public void setSystem(String system) {
		this.system = system;
	}
	public String getDevice() {
		return device;
	}
	public void setDevice(String device) {
		this.device = device;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public String getEquipmentNumber() {
		return equipmentNumber;
	}
	public void setEquipmentNumber(String equipmentNumber) {
		this.equipmentNumber = equipmentNumber;
	}
	
}