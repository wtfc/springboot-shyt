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
	private String orderNo;//   编码	private Date orderDate;//   变动日期	private String months;//   月份
	private Date startIntel;	private String companyId;//   客户id	private String companyName;//   客户名称	private String companyType;//   业务类型	private String resourceType;//   资源变动类型
	private String oldDepartment;	private String department;//   销售部门	private String sale;//   客户经理	private String maintenanSale;//   维护经理	private String roomName;//   机房	private String payType;//   付费方式	private Date cycleStart;//   计费起始时间	private Date cycleEnd;//   计费终止时间	private String lineCategory;//   专线类型	private String singleCharg;//   单天计算方式	private String overflowCategory;//   超流量取值方式	private String cardNo;//   合同编号	private BigDecimal cardAmount;//   合同金额	private String performanceAmount;//   带库端口类型	private BigDecimal cardStockAmount;//   存量合同额	private BigDecimal prestoreAmount;//   预存金额	private BigDecimal discount;//   折扣	private String orderRemark;//   备注	private Integer minBandwidth;//   保底带宽	private BigDecimal minBandwidthPrice;//   单价	private Integer portBandwidth;//   端口带宽	private BigDecimal portBandwidthPrice;//   单价	private Integer overflowBandwidth;//   超流量带宽	private BigDecimal overflowBandwidthPrice;//   单价	private Integer cabinetNum;//   机柜	private BigDecimal cabinetPrice;//   单价	private Integer serviceNum;//   服务器	private BigDecimal servicePrice;//   单价	private Integer ipNum;//   IP	private BigDecimal ipPrice;//   单价	private Integer switchNum;//   交换机	private BigDecimal switchPrice;//   单价	private Integer odfNum;//   链路	private BigDecimal odfPrice;//   单价	private Integer portNum;//   端口	private BigDecimal portPrice;//   单价	private Integer memoryNum;//   内存	private BigDecimal memoryPrice;//   单价	private Integer cpuNum;//   CPU	private BigDecimal cpuPrice;//   单价	private Integer diskNum;//   硬盘	private BigDecimal diskPrice;//   单价
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
	public String getOrderNo() {	    return orderNo;	}	public void setOrderNo(String orderNo) {	    this.orderNo=orderNo;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getOrderDate() {	    return orderDate;	}	public void setOrderDate(Date orderDate) {	    this.orderDate=orderDate;	}
	//@JsonSerialize(using = CustomDateSerializer.class)	public String getMonths() {	    return months;	}	public void setMonths(String months) {	    this.months=months;	}	public String getCompanyId() {	    return companyId;	}	public void setCompanyId(String companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getCompanyType() {	    return companyType;	}	public void setCompanyType(String companyType) {	    this.companyType=companyType;	}
	
	public String getCompanyTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("customerType", this.getCompanyType()) == null ? "" : dicManager.getDic("customerType", this.getCompanyType()).getValue();
	}
		public String getResourceType() {	    return resourceType;	}	public void setResourceType(String resourceType) {	    this.resourceType=resourceType;	}	public String getDepartment() {	    return department;	}	public void setDepartment(String department) {	    this.department=department;	}	public String getSale() {	    return sale;	}	public void setSale(String sale) {	    this.sale=sale;	}	public String getMaintenanSale() {	    return maintenanSale;	}	public void setMaintenanSale(String maintenanSale) {	    this.maintenanSale=maintenanSale;	}	public String getRoomName() {	    return roomName;	}	public void setRoomName(String roomName) {	    this.roomName=roomName;	}
	
	public String getRoomNameValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getRoomName()) == null ? "" : dicManager.getDic("room", this.getRoomName()).getValue();
	}
		public String getPayType() {	    return payType;	}	public void setPayType(String payType) {	    this.payType=payType;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getCycleStart() {	    return cycleStart;	}	public void setCycleStart(Date cycleStart) {	    this.cycleStart=cycleStart;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getCycleEnd() {	    return cycleEnd;	}	public void setCycleEnd(Date cycleEnd) {	    this.cycleEnd=cycleEnd;	}	public String getLineCategory() {	    return lineCategory;	}	public void setLineCategory(String lineCategory) {	    this.lineCategory=lineCategory;	}	public String getSingleCharg() {	    return singleCharg;	}	public void setSingleCharg(String singleCharg) {	    this.singleCharg=singleCharg;	}	public String getOverflowCategory() {	    return overflowCategory;	}	public void setOverflowCategory(String overflowCategory) {	    this.overflowCategory=overflowCategory;	}	public String getCardNo() {	    return cardNo;	}	public void setCardNo(String cardNo) {	    this.cardNo=cardNo;	}	public BigDecimal getCardAmount() {	    return cardAmount;	}	public void setCardAmount(BigDecimal cardAmount) {	    this.cardAmount=cardAmount;	}	public String getPerformanceAmount() {	    return performanceAmount;	}	public void setPerformanceAmount(String performanceAmount) {	    this.performanceAmount=performanceAmount;	}	public BigDecimal getCardStockAmount() {	    return cardStockAmount;	}	public void setCardStockAmount(BigDecimal cardStockAmount) {	    this.cardStockAmount=cardStockAmount;	}	public BigDecimal getPrestoreAmount() {	    return prestoreAmount;	}	public void setPrestoreAmount(BigDecimal prestoreAmount) {	    this.prestoreAmount=prestoreAmount;	}	public BigDecimal getDiscount() {	    return discount;	}	public void setDiscount(BigDecimal discount) {	    this.discount=discount;	}	public String getOrderRemark() {	    return orderRemark;	}	public void setOrderRemark(String orderRemark) {	    this.orderRemark=orderRemark;	}	public Integer getMinBandwidth() {	    return minBandwidth;	}	public void setMinBandwidth(Integer minBandwidth) {	    this.minBandwidth=minBandwidth;	}	public BigDecimal getMinBandwidthPrice() {	    return minBandwidthPrice;	}	public void setMinBandwidthPrice(BigDecimal minBandwidthPrice) {	    this.minBandwidthPrice=minBandwidthPrice;	}	public Integer getPortBandwidth() {	    return portBandwidth;	}	public void setPortBandwidth(Integer portBandwidth) {	    this.portBandwidth=portBandwidth;	}	public BigDecimal getPortBandwidthPrice() {	    return portBandwidthPrice;	}	public void setPortBandwidthPrice(BigDecimal portBandwidthPrice) {	    this.portBandwidthPrice=portBandwidthPrice;	}	public Integer getOverflowBandwidth() {	    return overflowBandwidth;	}	public void setOverflowBandwidth(Integer overflowBandwidth) {	    this.overflowBandwidth=overflowBandwidth;	}	public BigDecimal getOverflowBandwidthPrice() {	    return overflowBandwidthPrice;	}	public void setOverflowBandwidthPrice(BigDecimal overflowBandwidthPrice) {	    this.overflowBandwidthPrice=overflowBandwidthPrice;	}	public Integer getCabinetNum() {	    return cabinetNum;	}	public void setCabinetNum(Integer cabinetNum) {	    this.cabinetNum=cabinetNum;	}	public BigDecimal getCabinetPrice() {	    return cabinetPrice;	}	public void setCabinetPrice(BigDecimal cabinetPrice) {	    this.cabinetPrice=cabinetPrice;	}	public Integer getServiceNum() {	    return serviceNum;	}	public void setServiceNum(Integer serviceNum) {	    this.serviceNum=serviceNum;	}	public BigDecimal getServicePrice() {	    return servicePrice;	}	public void setServicePrice(BigDecimal servicePrice) {	    this.servicePrice=servicePrice;	}	public Integer getIpNum() {	    return ipNum;	}	public void setIpNum(Integer ipNum) {	    this.ipNum=ipNum;	}	public BigDecimal getIpPrice() {	    return ipPrice;	}	public void setIpPrice(BigDecimal ipPrice) {	    this.ipPrice=ipPrice;	}	public Integer getSwitchNum() {	    return switchNum;	}	public void setSwitchNum(Integer switchNum) {	    this.switchNum=switchNum;	}	public BigDecimal getSwitchPrice() {	    return switchPrice;	}	public void setSwitchPrice(BigDecimal switchPrice) {	    this.switchPrice=switchPrice;	}	public Integer getOdfNum() {	    return odfNum;	}	public void setOdfNum(Integer odfNum) {	    this.odfNum=odfNum;	}	public BigDecimal getOdfPrice() {	    return odfPrice;	}	public void setOdfPrice(BigDecimal odfPrice) {	    this.odfPrice=odfPrice;	}	public Integer getPortNum() {	    return portNum;	}	public void setPortNum(Integer portNum) {	    this.portNum=portNum;	}	public BigDecimal getPortPrice() {	    return portPrice;	}	public void setPortPrice(BigDecimal portPrice) {	    this.portPrice=portPrice;	}	public Integer getMemoryNum() {	    return memoryNum;	}	public void setMemoryNum(Integer memoryNum) {	    this.memoryNum=memoryNum;	}	public BigDecimal getMemoryPrice() {	    return memoryPrice;	}	public void setMemoryPrice(BigDecimal memoryPrice) {	    this.memoryPrice=memoryPrice;	}	public Integer getCpuNum() {	    return cpuNum;	}	public void setCpuNum(Integer cpuNum) {	    this.cpuNum=cpuNum;	}	public BigDecimal getCpuPrice() {	    return cpuPrice;	}	public void setCpuPrice(BigDecimal cpuPrice) {	    this.cpuPrice=cpuPrice;	}	public Integer getDiskNum() {	    return diskNum;	}	public void setDiskNum(Integer diskNum) {	    this.diskNum=diskNum;	}	public BigDecimal getDiskPrice() {	    return diskPrice;	}	public void setDiskPrice(BigDecimal diskPrice) {	    this.diskPrice=diskPrice;	}
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
	}}

