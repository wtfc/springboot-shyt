package com.jc.oa.finance.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 收入主体表
*/
public class ToaFinanceMain extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String orderNo;//   编码
	private Date startIntel;
	private String oldDepartment;
	//应收月份多条
	private List<ToaFinanceInvoices> toaFinanceInvoices;
	
	//新增合同字段
	private String contractStatus;//合同类别
	private String deriveNo;//   衍生合同编号
	private Date startDate;//   合同起始日期
	private Date endDate;//   合同终止日期
	
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getDeriveNo() {
		return deriveNo;
	}
	public void setDeriveNo(String deriveNo) {
		this.deriveNo = deriveNo;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getOrderNo() {
	@JsonSerialize(using = CustomDateSerializer.class)
	//@JsonSerialize(using = CustomDateSerializer.class)
	
	public String getCompanyTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("customerType", this.getCompanyType()) == null ? "" : dicManager.getDic("customerType", this.getCompanyType()).getValue();
	}
	
	
	public String getRoomNameValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getRoomName()) == null ? "" : dicManager.getDic("room", this.getRoomName()).getValue();
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	public List<ToaFinanceInvoices> getToaFinanceInvoices() {
		return toaFinanceInvoices;
	}
	public void setToaFinanceInvoices(List<ToaFinanceInvoices> toaFinanceInvoices) {
		this.toaFinanceInvoices = toaFinanceInvoices;
	}
	public String getOldDepartment() {
		return oldDepartment;
	}
	public void setOldDepartment(String oldDepartment) {
		this.oldDepartment = oldDepartment;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getStartIntel() {
		return startIntel;
	}
	public void setStartIntel(Date startIntel) {
		this.startIntel = startIntel;
	}
