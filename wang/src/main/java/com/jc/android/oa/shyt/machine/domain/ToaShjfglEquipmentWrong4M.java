package com.jc.android.oa.shyt.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 故障处理表
*/
public class ToaShjfglEquipmentWrong4M extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String equipmentNumber;//   工单编号
	private String diviceNumber;//设备编号
	private Integer companyId;//   客户ID
	private String companyName;//   客户名称
	private String contact;//   客户联系人
	private String tel;//   客户联系方式
	private Date billDate;//   下单日期
	private Date workTime;//   接单时间
	private Date startDate;//   操作开始时间
	private Date endDate;//   操作完成时间
	private String equipmentId;//   设备ID
	private String cabinet;//机柜
	private String ip;//   IP地址
	private String sn;//SN
	private String brand;//品牌型号
	private String equipmentCheckResult;//   设备信息核对结果
	private String isReport;//   是否上报处理
	private String haveAfterOperate;//   是否存在后续操作
	private String operationType;//   工单类型（17.设备故障 18、网络故障）
	private String operationTypeImg;//   工单类型图标名称（17.设备故障 18、网络故障）
	private String machina;//   机房
	private String caozgcs;//   操作人员ID
	private String caozgcsName;//   操作人员姓名
	private Integer status;//   处理状态
	private Integer isAgree;//   处理状态
	private String remark;//   备注
	public String getDiviceNumber() {
		return diviceNumber;
	}
	public void setDiviceNumber(String diviceNumber) {
		this.diviceNumber = diviceNumber;
	}
	public String getEquipmentNumber() {	    return equipmentNumber;	}	public void setEquipmentNumber(String equipmentNumber) {	    this.equipmentNumber=equipmentNumber;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getContact() {	    return contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getTel() {	    return tel;	}	public void setTel(String tel) {	    this.tel=tel;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getBillDate() {	    return billDate;	}	public void setBillDate(Date billDate) {	    this.billDate=billDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getStartDate() {	    return startDate;	}	public void setStartDate(Date startDate) {	    this.startDate=startDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getEndDate() {	    return endDate;	}	public void setEndDate(Date endDate) {	    this.endDate=endDate;	}	public String getEquipmentId() {	    return equipmentId;	}	public void setEquipmentId(String equipmentId) {	    this.equipmentId=equipmentId;	}	public String getEquipmentCheckResult() {	    return equipmentCheckResult;	}	public void setEquipmentCheckResult(String equipmentCheckResult) {	    this.equipmentCheckResult=equipmentCheckResult;	}	public String getIsReport() {	    return isReport;	}	public void setIsReport(String isReport) {	    this.isReport=isReport;	}	public String getHaveAfterOperate() {	    return haveAfterOperate;	}	public void setHaveAfterOperate(String haveAfterOperate) {	    this.haveAfterOperate=haveAfterOperate;	}	public String getOperationType() {	    return operationType;	}	public void setOperationType(String operationType) {	    this.operationType=operationType;	}
	public String getOperationTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("operateType", this.getOperationType()+"") == null ? "" : dicManager.getDic("operateType", this.getOperationType()+"").getValue();
	}	public String getOperationTypeImg() {	    return operationTypeImg;	}	public void setOperationTypeImg(String operationTypeImg) {	    this.operationTypeImg=operationTypeImg;	}	public String getMachina() {	    return machina;	}	public void setMachina(String machina) {	    this.machina=machina;	}
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}	public String getCaozgcs() {	    return caozgcs;	}	public void setCaozgcs(String caozgcs) {	    this.caozgcs=caozgcs;	}	public String getCaozgcsName() {	    return caozgcsName;	}	public void setCaozgcsName(String caozgcsName) {	    this.caozgcsName=caozgcsName;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
	public String getCabinet() {
		return cabinet;
	}
	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Integer getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}}

