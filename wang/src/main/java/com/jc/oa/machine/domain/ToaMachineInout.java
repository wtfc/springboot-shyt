package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 机房进出
*/
public class ToaMachineInout extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String equipmentNumber;//   工单编号	private Integer companyId;//   客户id	private String companyName;//   客户名称	private String machina;//   所属名称	private String contact;//   客户联系人	private String tel;//   客户联系方式	private Date intDate;//   入室日期	private String type;//   类型（维护、搬入、搬出、迁移）	private Integer countt;//   工单设备数量	private String deviceInfo;//   设备信息	private String intPeople;//   入室人员	private String isInput;//   是否进场	private String caozgcs;//   操作工程师	private Integer origin;//   实际到场设备数量	private String remark;//   备注	private Integer status;//   处理状态	public String getEquipmentNumber() {	    return equipmentNumber;	}	public void setEquipmentNumber(String equipmentNumber) {	    this.equipmentNumber=equipmentNumber;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getMachina() {	    return machina;	}	public void setMachina(String machina) {	    this.machina=machina;	}
	
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}
		public String getContact() {	    return contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getTel() {	    return tel;	}	public void setTel(String tel) {	    this.tel=tel;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getIntDate() {	    return intDate;	}	public void setIntDate(Date intDate) {	    this.intDate=intDate;	}	public String getType() {	    return type;	}	public void setType(String type) {	    this.type=type;	}	public Integer getCountt() {	    return countt;	}	public void setCountt(Integer countt) {	    this.countt=countt;	}	public String getDeviceInfo() {	    return deviceInfo;	}	public void setDeviceInfo(String deviceInfo) {	    this.deviceInfo=deviceInfo;	}	public String getIntPeople() {	    return intPeople;	}	public void setIntPeople(String intPeople) {	    this.intPeople=intPeople;	}	public String getIsInput() {	    return isInput;	}	public void setIsInput(String isInput) {	    this.isInput=isInput;	}	public String getCaozgcs() {	    return caozgcs;	}	public void setCaozgcs(String caozgcs) {	    this.caozgcs=caozgcs;	}	public Integer getOrigin() {	    return origin;	}	public void setOrigin(Integer origin) {	    this.origin=origin;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}}

