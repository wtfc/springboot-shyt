package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 机房操作
*/
public class ToaMachineOperator extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String equipmentNumber;//   工单编号	private Integer companyId;//   客户id	private String companyName;//   客户名称	private String machina;//   所属机房	private String contact;//   客户联系人	private String tel;//   客户联系方式	private String operateType;//   操作类型	private String operateText;//   操作内容	private Date intDate;//   下单时间	private String caozgcs;//   操作工程师	private Date operateDate;//   操作日期	private Date endDate;//   处理完成时间	private String isFinish;//   是否按要求完成	private Integer countt;//   设备数量	private String search;//   客户满意度	private String remark;//   备注	private Integer status;//   处理状态	public String getEquipmentNumber() {	    return equipmentNumber;	}	public void setEquipmentNumber(String equipmentNumber) {	    this.equipmentNumber=equipmentNumber;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getMachina() {	    return machina;	}	public void setMachina(String machina) {	    this.machina=machina;	}
	
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}
		public String getContact() {	    return contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getTel() {	    return tel;	}	public void setTel(String tel) {	    this.tel=tel;	}	public String getOperateType() {	    return operateType;	}	public void setOperateType(String operateType) {	    this.operateType=operateType;	}	public String getOperateText() {	    return operateText;	}	public void setOperateText(String operateText) {	    this.operateText=operateText;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getIntDate() {	    return intDate;	}	public void setIntDate(Date intDate) {	    this.intDate=intDate;	}	public String getCaozgcs() {	    return caozgcs;	}	public void setCaozgcs(String caozgcs) {	    this.caozgcs=caozgcs;	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getOperateDate() {	    return operateDate;	}	public void setOperateDate(Date operateDate) {	    this.operateDate=operateDate;	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getEndDate() {	    return endDate;	}	public void setEndDate(Date endDate) {	    this.endDate=endDate;	}	public String getIsFinish() {	    return isFinish;	}	public void setIsFinish(String isFinish) {	    this.isFinish=isFinish;	}	public Integer getCountt() {	    return countt;	}	public void setCountt(Integer countt) {	    this.countt=countt;	}	public String getSearch() {	    return search;	}	public void setSearch(String search) {	    this.search=search;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}}

