package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 网络故障
*/
public class ToaMachineNetworkfault extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String faultNumber;//   工单编号	private String companyName;//   客户名称	private Integer companyId;//   客户ID	private Date operateDate;//   报障时间	private String contact;//   客户联系人	private String tel;//   客户联系方式	private String typeOne;//   故障类型（1）	private String typeTwo;//   故障类型（2）	private Date processDate;//   处理时间	private Date finishDate;//   完成时间	private String faultReport;//   故障现象描述	private String processing;//   处理过程和结果	private String faultReason;//   故障原因	private String caozgcs;//   操作工程师	private String feedbacks;//   客户反馈	private String isFinish;//   是否按要求完成	private String search;//   客户满意度	private String remark;//   备注	private Integer status;//   处理状态	public String getFaultNumber() {	    return faultNumber;	}	public void setFaultNumber(String faultNumber) {	    this.faultNumber=faultNumber;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getOperateDate() {	    return operateDate;	}	public void setOperateDate(Date operateDate) {	    this.operateDate=operateDate;	}	public String getContact() {	    return contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getTel() {	    return tel;	}	public void setTel(String tel) {	    this.tel=tel;	}	public String getTypeOne() {	    return typeOne;	}	public void setTypeOne(String typeOne) {	    this.typeOne=typeOne;	}	public String getTypeTwo() {	    return typeTwo;	}	public void setTypeTwo(String typeTwo) {	    this.typeTwo=typeTwo;	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getProcessDate() {	    return processDate;	}	public void setProcessDate(Date processDate) {	    this.processDate=processDate;	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getFinishDate() {	    return finishDate;	}	public void setFinishDate(Date finishDate) {	    this.finishDate=finishDate;	}	public String getFaultReport() {	    return faultReport;	}	public void setFaultReport(String faultReport) {	    this.faultReport=faultReport;	}	public String getProcessing() {	    return processing;	}	public void setProcessing(String processing) {	    this.processing=processing;	}	public String getFaultReason() {	    return faultReason;	}	public void setFaultReason(String faultReason) {	    this.faultReason=faultReason;	}	public String getCaozgcs() {	    return caozgcs;	}	public void setCaozgcs(String caozgcs) {	    this.caozgcs=caozgcs;	}	public String getFeedbacks() {	    return feedbacks;	}	public void setFeedbacks(String feedbacks) {	    this.feedbacks=feedbacks;	}	public String getIsFinish() {	    return isFinish;	}	public void setIsFinish(String isFinish) {	    this.isFinish=isFinish;	}	public String getSearch() {	    return search;	}	public void setSearch(String search) {	    this.search=search;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}}

