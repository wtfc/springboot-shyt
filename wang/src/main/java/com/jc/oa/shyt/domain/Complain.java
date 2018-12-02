package com.jc.oa.shyt.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
/**
 * @author mrb
 * @version 投诉信息实体类
 * */
public class Complain extends BaseBean{
	
	private static final long serialVersionUID = 1L;
	
	private String companyName;//公司名称
	private Integer customerId;//公司id
	private Integer complainNumber;//投诉次数
	private String partment;//被投诉部门
	private Date complainDate;//投诉日期
	private String complainStatus;//投诉类型
	private String status;//是否解决
	private String program;//解决方案
	private String remark;//备注
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public Integer getComplainNumber() {
		return complainNumber;
	}
	public void setComplainNumber(Integer complainNumber) {
		this.complainNumber = complainNumber;
	}
	public String getPartment() {
		return partment;
	}
	public void setPartment(String partment) {
		this.partment = partment;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getComplainDate() {
		return complainDate;
	}
	public void setComplainDate(Date complainDate) {
		this.complainDate = complainDate;
	}
	public String getComplainStatus() {
		return complainStatus;
	}
	public void setComplainStatus(String complainStatus) {
		this.complainStatus = complainStatus;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
}
