package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 机房故障
*/
public class ToaMachineFault extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String faultNumber;//   工单编号	private String companyName;//   报障客户	private Integer companyId;//   客户ID	private Date intDate;//   日期	private String type;//   故障类型（光缆故障、网内攻击、设备故障、运营商）	private Date operateDate;//   故障时间	private Date restoreDate;//   恢复时间	private String faultReason;//   故障原因	private String faultReport;//   故障报告	private String eazyName;//   报障客户	private String processing;//   处理过程	private String department;//   涉及部门	private String network;//   网络工程师	private String yunwei;//   运维工程师	private String jiankong;//   监控工程师	private String remarkOne;//   备注说明	private Integer status;//   处理状态	public String getFaultNumber() {	    return faultNumber;	}	public void setFaultNumber(String faultNumber) {	    this.faultNumber=faultNumber;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getIntDate() {	    return intDate;	}	public void setIntDate(Date intDate) {	    this.intDate=intDate;	}	public String getType() {	    return type;	}	public void setType(String type) {	    this.type=type;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getOperateDate() {	    return operateDate;	}	public void setOperateDate(Date operateDate) {	    this.operateDate=operateDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getRestoreDate() {	    return restoreDate;	}	public void setRestoreDate(Date restoreDate) {	    this.restoreDate=restoreDate;	}	public String getFaultReason() {	    return faultReason;	}	public void setFaultReason(String faultReason) {	    this.faultReason=faultReason;	}	public String getFaultReport() {	    return faultReport;	}	public void setFaultReport(String faultReport) {	    this.faultReport=faultReport;	}	public String getEazyName() {	    return eazyName;	}	public void setEazyName(String eazyName) {	    this.eazyName=eazyName;	}	public String getProcessing() {	    return processing;	}	public void setProcessing(String processing) {	    this.processing=processing;	}	public String getDepartment() {	    return department;	}	public void setDepartment(String department) {	    this.department=department;	}	public String getNetwork() {	    return network;	}	public void setNetwork(String network) {	    this.network=network;	}	public String getYunwei() {	    return yunwei;	}	public void setYunwei(String yunwei) {	    this.yunwei=yunwei;	}	public String getJiankong() {	    return jiankong;	}	public void setJiankong(String jiankong) {	    this.jiankong=jiankong;	}	public String getRemarkOne() {	    return remarkOne;	}	public void setRemarkOne(String remarkOne) {	    this.remarkOne=remarkOne;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}}

