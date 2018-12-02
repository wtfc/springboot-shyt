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
	private String companyName;//   客户名称	private String companyId;//   客户id	private String machine;//   所属机房	private String department;//   销售部门	private String sale;//   客户经理	private String tradeUser;//   所属客维	private String serviceType;//   业务类型	private String linkUser;//   关联客户名称	private String trade;//   所属行业	private String memberType;//   客户类型	private String address;//   通信地址	private String newAddress;//   注册地址	private String companyOld;//   信息变更过程	private String taxid;//   纳税识别号	private String bankName;//   开户银行	private String bankNo;//   银行帐号	private String ticketFlag;//   发票类型	private String overflowCategory;//   超流量取值方式	private String isDaili;//   是否有代理费	private String dailiName;//   代理费收款方	private Date startIntel;//   首次入网时间	private Date endIntel;//   终止时间	private String rating;//   客户评级	private BigDecimal roomPrice;//   机房单价	private BigDecimal machinePrice;//   机柜单价	private BigDecimal servicePrice;//   服务器单价	private BigDecimal ipPrice;//   IP单价	private BigDecimal portPrice;//   端口单价	private BigDecimal portBandwidthPrice;//   端口带宽单价	private BigDecimal minBandwidthPrice;//   保底带宽单价	private BigDecimal overflowBandwidthPrice;//   超流量单价	private BigDecimal switchPrice;//   交换机单价	private BigDecimal odfPrice;//   链路单价	private BigDecimal memoryPrice;//   内存单价	private BigDecimal cpuPrice;//   CPU单价	private BigDecimal diskPrice;//   硬盘单价	private BigDecimal routerPrice;//   路由器单价	private BigDecimal otherssPrice;//   其他单价	private String remark;//   备注
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
	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getCompanyId() {	    return companyId;	}	public void setCompanyId(String companyId) {	    this.companyId=companyId;	}	public String getMachine() {	    return machine;	}	public void setMachine(String machine) {	    this.machine=machine;	}
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
		public String getDepartment() {	    return department;	}	public void setDepartment(String department) {	    this.department=department;	}	public String getSale() {	    return sale;	}	public void setSale(String sale) {	    this.sale=sale;	}	public String getTradeUser() {	    return tradeUser;	}	public void setTradeUser(String tradeUser) {	    this.tradeUser=tradeUser;	}	public String getServiceType() {	    return serviceType;	}	public void setServiceType(String serviceType) {	    this.serviceType=serviceType;	}
	
	public String getServiceTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("customerType", this.getServiceType()) == null ? "" : dicManager.getDic("customerType", this.getServiceType()).getValue();
	}
		public String getLinkUser() {	    return linkUser;	}	public void setLinkUser(String linkUser) {	    this.linkUser=linkUser;	}	public String getTrade() {	    return trade;	}	public void setTrade(String trade) {	    this.trade=trade;	}	public String getMemberType() {	    return memberType;	}	public void setMemberType(String memberType) {	    this.memberType=memberType;	}	public String getAddress() {	    return address;	}	public void setAddress(String address) {	    this.address=address;	}	public String getNewAddress() {	    return newAddress;	}	public void setNewAddress(String newAddress) {	    this.newAddress=newAddress;	}	public String getCompanyOld() {	    return companyOld;	}	public void setCompanyOld(String companyOld) {	    this.companyOld=companyOld;	}	public String getTaxid() {	    return taxid;	}	public void setTaxid(String taxid) {	    this.taxid=taxid;	}	public String getBankName() {	    return bankName;	}	public void setBankName(String bankName) {	    this.bankName=bankName;	}	public String getBankNo() {	    return bankNo;	}	public void setBankNo(String bankNo) {	    this.bankNo=bankNo;	}	public String getTicketFlag() {	    return ticketFlag;	}	public void setTicketFlag(String ticketFlag) {	    this.ticketFlag=ticketFlag;	}	public String getOverflowCategory() {	    return overflowCategory;	}	public void setOverflowCategory(String overflowCategory) {	    this.overflowCategory=overflowCategory;	}	public String getIsDaili() {	    return isDaili;	}	public void setIsDaili(String isDaili) {	    this.isDaili=isDaili;	}	public String getDailiName() {	    return dailiName;	}	public void setDailiName(String dailiName) {	    this.dailiName=dailiName;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getStartIntel() {	    return startIntel;	}	public void setStartIntel(Date startIntel) {	    this.startIntel=startIntel;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getEndIntel() {	    return endIntel;	}	public void setEndIntel(Date endIntel) {	    this.endIntel=endIntel;	}	public String getRating() {	    return rating;	}	public void setRating(String rating) {	    this.rating=rating;	}	public BigDecimal getRoomPrice() {	    return roomPrice;	}	public void setRoomPrice(BigDecimal roomPrice) {	    this.roomPrice=roomPrice;	}	public BigDecimal getMachinePrice() {	    return machinePrice;	}	public void setMachinePrice(BigDecimal machinePrice) {	    this.machinePrice=machinePrice;	}	public BigDecimal getServicePrice() {	    return servicePrice;	}	public void setServicePrice(BigDecimal servicePrice) {	    this.servicePrice=servicePrice;	}	public BigDecimal getIpPrice() {	    return ipPrice;	}	public void setIpPrice(BigDecimal ipPrice) {	    this.ipPrice=ipPrice;	}	public BigDecimal getPortPrice() {	    return portPrice;	}	public void setPortPrice(BigDecimal portPrice) {	    this.portPrice=portPrice;	}	public BigDecimal getPortBandwidthPrice() {	    return portBandwidthPrice;	}	public void setPortBandwidthPrice(BigDecimal portBandwidthPrice) {	    this.portBandwidthPrice=portBandwidthPrice;	}	public BigDecimal getMinBandwidthPrice() {	    return minBandwidthPrice;	}	public void setMinBandwidthPrice(BigDecimal minBandwidthPrice) {	    this.minBandwidthPrice=minBandwidthPrice;	}	public BigDecimal getOverflowBandwidthPrice() {	    return overflowBandwidthPrice;	}	public void setOverflowBandwidthPrice(BigDecimal overflowBandwidthPrice) {	    this.overflowBandwidthPrice=overflowBandwidthPrice;	}	public BigDecimal getSwitchPrice() {	    return switchPrice;	}	public void setSwitchPrice(BigDecimal switchPrice) {	    this.switchPrice=switchPrice;	}	public BigDecimal getOdfPrice() {	    return odfPrice;	}	public void setOdfPrice(BigDecimal odfPrice) {	    this.odfPrice=odfPrice;	}	public BigDecimal getMemoryPrice() {	    return memoryPrice;	}	public void setMemoryPrice(BigDecimal memoryPrice) {	    this.memoryPrice=memoryPrice;	}	public BigDecimal getCpuPrice() {	    return cpuPrice;	}	public void setCpuPrice(BigDecimal cpuPrice) {	    this.cpuPrice=cpuPrice;	}	public BigDecimal getDiskPrice() {	    return diskPrice;	}	public void setDiskPrice(BigDecimal diskPrice) {	    this.diskPrice=diskPrice;	}	public BigDecimal getRouterPrice() {	    return routerPrice;	}	public void setRouterPrice(BigDecimal routerPrice) {	    this.routerPrice=routerPrice;	}	public BigDecimal getOtherssPrice() {	    return otherssPrice;	}	public void setOtherssPrice(BigDecimal otherssPrice) {	    this.otherssPrice=otherssPrice;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

