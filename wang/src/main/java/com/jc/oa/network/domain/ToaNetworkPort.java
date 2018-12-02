package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;
/**
 * @author mrb
 * @version 端口使用情况统计表
*/
public class ToaNetworkPort extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyName;//   客户名称	private Integer companyId;//   客户id	private String companyType;//   类型	private String machine;//   机房	private String equipment;//   设备	private String board;//   板卡	private String ports;//   端口	private String vlan;//   VLAN	private String bandwidth;//   带宽	private String usings;//   使用状态	private Integer bandwidthNumber;//   端口总数	private Integer myselfNumber;//   自用使用个数	private Integer companyNumber;//   客户使用个数	private String myselfRate;//   自用使用率	private String companyRate;//   客户使用率	private String myselfIdleness;//   自用空闲率	private String companyIdleness;//   客户空闲率	private String remark;//   备注	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyType() {	    return companyType;	}	public void setCompanyType(String companyType) {	    this.companyType=companyType;	}	public String getMachine() {	    return machine;	}	public void setMachine(String machine) {	    this.machine=machine;	}
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
		public String getEquipment() {	    return equipment;	}	public void setEquipment(String equipment) {	    this.equipment=equipment;	}	public String getBoard() {	    return board;	}	public void setBoard(String board) {	    this.board=board;	}	public String getVlan() {	    return vlan;	}	public void setVlan(String vlan) {	    this.vlan=vlan;	}	public String getBandwidth() {	    return bandwidth;	}	public void setBandwidth(String bandwidth) {	    this.bandwidth=bandwidth;	}	public Integer getBandwidthNumber() {	    return bandwidthNumber;	}	public void setBandwidthNumber(Integer bandwidthNumber) {	    this.bandwidthNumber=bandwidthNumber;	}	public Integer getMyselfNumber() {	    return myselfNumber;	}	public void setMyselfNumber(Integer myselfNumber) {	    this.myselfNumber=myselfNumber;	}	public Integer getCompanyNumber() {	    return companyNumber;	}	public void setCompanyNumber(Integer companyNumber) {	    this.companyNumber=companyNumber;	}	public String getMyselfRate() {	    return myselfRate;	}	public void setMyselfRate(String myselfRate) {	    this.myselfRate=myselfRate;	}	public String getCompanyRate() {	    return companyRate;	}	public void setCompanyRate(String companyRate) {	    this.companyRate=companyRate;	}	public String getMyselfIdleness() {	    return myselfIdleness;	}	public void setMyselfIdleness(String myselfIdleness) {	    this.myselfIdleness=myselfIdleness;	}	public String getCompanyIdleness() {	    return companyIdleness;	}	public void setCompanyIdleness(String companyIdleness) {	    this.companyIdleness=companyIdleness;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}
	public String getPorts() {
		return ports;
	}
	public void setPorts(String ports) {
		this.ports = ports;
	}
	public String getUsings() {
		return usings;
	}
	public void setUsings(String usings) {
		this.usings = usings;
	}}

