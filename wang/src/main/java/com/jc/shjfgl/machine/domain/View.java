package com.jc.shjfgl.machine.domain;

import org.apache.log4j.Logger;

import com.jc.foundation.domain.BaseBean;

public class View extends BaseBean{

	protected transient final Logger log = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	private String machina;//机房区域
	private String machinaId;//机房id
	private String machinaNumber;//机柜编号
	private String contact;//机房名称
	private String address;//机柜位置
	private String isOpen;//是否开通
	private String type;//机柜类型
	private Integer valume;//机柜容量
	private String num;//现有机器数量
	private String remark;//备注
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
	public String getIsOpen() {
		return isOpen;
	}
	public void setIsOpen(String isOpen) {
		this.isOpen = isOpen;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getValume() {
		return valume;
	}
	public void setValume(Integer valume) {
		this.valume = valume;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
