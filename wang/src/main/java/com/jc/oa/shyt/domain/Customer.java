package com.jc.oa.shyt.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 合同资源表
*/
public class Customer extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyName;//   客户名称
	//新增字段
	private String clientName;//客户名称
	
	//新增字段
	private String customerAccess;//   客户接入商
	private String companyPlaced;//   放置在公司业务
	private String customerWebsite;//   客户官网
	private String commonNumber;//   公共号
	private String companyPurchasing;//   客户公司确定采购资源部门
	
	public String getCustomerAccess() {
		return customerAccess;
	}
	public void setCustomerAccess(String customerAccess) {
		this.customerAccess = customerAccess;
	}
	public String getCompanyPlaced() {
		return companyPlaced;
	}
	public void setCompanyPlaced(String companyPlaced) {
		this.companyPlaced = companyPlaced;
	}
	public String getCustomerWebsite() {
		return customerWebsite;
	}
	public void setCustomerWebsite(String customerWebsite) {
		this.customerWebsite = customerWebsite;
	}
	public String getCommonNumber() {
		return commonNumber;
	}
	public void setCommonNumber(String commonNumber) {
		this.commonNumber = commonNumber;
	}
	public String getCompanyPurchasing() {
		return companyPurchasing;
	}
	public void setCompanyPurchasing(String companyPurchasing) {
		this.companyPurchasing = companyPurchasing;
	}
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public String getCompanyName() {
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
	
	
	public String getServiceTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("customerType", this.getServiceType()) == null ? "" : dicManager.getDic("customerType", this.getServiceType()).getValue();
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	@JsonSerialize(using = CustomDatetimeSerializer.class)
