package com.jc.oa.product.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 云主机测试表
*/
public class ToaProductCloudtest extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private Integer companyId;//   客户编码	private String companyName;//   客户名称	private Date startDate;//   测试开始时间	private Date endDate;//   测试结束时间	private String salePeople;//   销售	private String cooperateType;//   测试状态	private String cpu;//   CPU	private String ram;//   存储器	private String performance;//   性能型磁盘	private String stick;//   容量型磁盘	private String bandwidth;//   带宽	private String systemMachine;//   系统	private String email;//   邮箱	private String publicIp;//   测试IP	private Date chargeTime;//   延长时间	private Date finishDate;//   收回时间	private String remark;//   其他	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getStartDate() {	    return startDate;	}	public void setStartDate(Date startDate) {	    this.startDate=startDate;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getEndDate() {	    return endDate;	}	public void setEndDate(Date endDate) {	    this.endDate=endDate;	}	public String getSalePeople() {	    return salePeople;	}	public void setSalePeople(String salePeople) {	    this.salePeople=salePeople;	}	public String getCooperateType() {	    return cooperateType;	}	public void setCooperateType(String cooperateType) {	    this.cooperateType=cooperateType;	}	public String getCpu() {	    return cpu;	}	public void setCpu(String cpu) {	    this.cpu=cpu;	}	public String getRam() {	    return ram;	}	public void setRam(String ram) {	    this.ram=ram;	}	public String getPerformance() {	    return performance;	}	public void setPerformance(String performance) {	    this.performance=performance;	}	public String getStick() {	    return stick;	}	public void setStick(String stick) {	    this.stick=stick;	}	public String getBandwidth() {	    return bandwidth;	}	public void setBandwidth(String bandwidth) {	    this.bandwidth=bandwidth;	}	public String getSystemMachine() {	    return systemMachine;	}	public void setSystemMachine(String systemMachine) {	    this.systemMachine=systemMachine;	}	public String getEmail() {	    return email;	}	public void setEmail(String email) {	    this.email=email;	}	public String getPublicIp() {	    return publicIp;	}	public void setPublicIp(String publicIp) {	    this.publicIp=publicIp;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getChargeTime() {	    return chargeTime;	}	public void setChargeTime(Date chargeTime) {	    this.chargeTime=chargeTime;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getFinishDate() {	    return finishDate;	}	public void setFinishDate(Date finishDate) {	    this.finishDate=finishDate;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

