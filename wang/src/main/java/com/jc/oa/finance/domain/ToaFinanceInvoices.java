package com.jc.oa.finance.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.math.BigDecimal;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 月收入
*/
public class ToaFinanceInvoices extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private Integer mainId;//   收入主体id	private Integer companyId;//   客户id	private String companyName;//   客户名称
	private String companyType;//   客户名称	private String invoicesMonth;//   应收月份
	private String department;//   销售部门	private BigDecimal monthAmount;//   应收金额	private Date invoicesStartdate;//   应收起始日期	private Date invoicesEnddate;//   应收终止日期	private String invoicesState;//   是否开票	private Date vdateDate;//   开票日期	private String invoicesNo;//   发票号码	private BigDecimal invoicesAccount;//   发票金额	private BigDecimal noinvoicesAccount;//   未开票金额	private String receivedState;//   是否回款	private Date receivedDate;//   回款日期	private BigDecimal receivedAccount;//   回款金额	private BigDecimal arrearage;//   未回款金额	private BigDecimal commision;//   代理费	private Integer otherDeductions;//   其它扣除项
	private String orderNo;//   编码
	private Date orderDate;//   变动日期
	private Date months;//   月份
	private Date startIntel;
	private String resourceType;//   资源变动类型
	private String oldDepartment;
	private String sale;//   客户经理
	private String maintenanSale;//   维护经理
	private String roomName;//   机房
	private String payType;//   付费方式
	private Date cycleStart;//   计费起始时间
	private Date cycleEnd;//   计费终止时间
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public Date getMonths() {
		return months;
	}
	public void setMonths(Date months) {
		this.months = months;
	}
	public Date getStartIntel() {
		return startIntel;
	}
	public void setStartIntel(Date startIntel) {
		this.startIntel = startIntel;
	}
	public String getOldDepartment() {
		return oldDepartment;
	}
	public void setOldDepartment(String oldDepartment) {
		this.oldDepartment = oldDepartment;
	}
	public String getSale() {
		return sale;
	}
	public void setSale(String sale) {
		this.sale = sale;
	}
	public String getMaintenanSale() {
		return maintenanSale;
	}
	public void setMaintenanSale(String maintenanSale) {
		this.maintenanSale = maintenanSale;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCycleStart() {
		return cycleStart;
	}
	public void setCycleStart(Date cycleStart) {
		this.cycleStart = cycleStart;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getCycleEnd() {
		return cycleEnd;
	}
	public void setCycleEnd(Date cycleEnd) {
		this.cycleEnd = cycleEnd;
	}
	public String getLineCategory() {
		return lineCategory;
	}
	public void setLineCategory(String lineCategory) {
		this.lineCategory = lineCategory;
	}
	public String getSingleCharg() {
		return singleCharg;
	}
	public void setSingleCharg(String singleCharg) {
		this.singleCharg = singleCharg;
	}
	public String getOverflowCategory() {
		return overflowCategory;
	}
	public void setOverflowCategory(String overflowCategory) {
		this.overflowCategory = overflowCategory;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public BigDecimal getCardAmount() {
		return cardAmount;
	}
	public void setCardAmount(BigDecimal cardAmount) {
		this.cardAmount = cardAmount;
	}
	public String getPerformanceAmount() {
		return performanceAmount;
	}
	public void setPerformanceAmount(String performanceAmount) {
		this.performanceAmount = performanceAmount;
	}
	public BigDecimal getCardStockAmount() {
		return cardStockAmount;
	}
	public void setCardStockAmount(BigDecimal cardStockAmount) {
		this.cardStockAmount = cardStockAmount;
	}
	public BigDecimal getPrestoreAmount() {
		return prestoreAmount;
	}
	public void setPrestoreAmount(BigDecimal prestoreAmount) {
		this.prestoreAmount = prestoreAmount;
	}
	public BigDecimal getDiscount() {
		return discount;
	}
	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}
	public String getOrderRemark() {
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark) {
		this.orderRemark = orderRemark;
	}
	private String lineCategory;//   专线类型
	private String singleCharg;//   单天计算方式
	private String overflowCategory;//   超流量取值方式
	private String cardNo;//   合同编号
	private BigDecimal cardAmount;//   合同金额
	private String performanceAmount;//   带库端口类型
	private BigDecimal cardStockAmount;//   存量合同额
	private BigDecimal prestoreAmount;//   预存金额
	private BigDecimal discount;//   折扣
	private String orderRemark;//   备注
	private Integer minBandwidth;//   保底带宽
	private BigDecimal minBandwidthPrice;//   单价
	private Integer portBandwidth;//   端口带宽
	private BigDecimal portBandwidthPrice;//   单价
	private Integer overflowBandwidth;//   超流量带宽
	private BigDecimal overflowBandwidthPrice;//   单价
	private Integer cabinetNum;//   机柜
	private BigDecimal cabinetPrice;//   单价
	private Integer serviceNum;//   服务器
	private BigDecimal servicePrice;//   单价
	private Integer ipNum;//   IP
	private BigDecimal ipPrice;//   单价
	private Integer switchNum;//   交换机
	private BigDecimal switchPrice;//   单价
	private Integer odfNum;//   链路
	private BigDecimal odfPrice;//   单价
	private Integer portNum;//   端口
	private BigDecimal portPrice;//   单价
	private Integer memoryNum;//   内存
	private BigDecimal memoryPrice;//   单价
	private Integer cpuNum;//   CPU
	private BigDecimal cpuPrice;//   单价
	private Integer diskNum;//   硬盘
	private BigDecimal diskPrice;//   单价
	private String jixiaoticheng;
	private BigDecimal allmoney;
	private BigDecimal addmoney=BigDecimal.valueOf(0.00);//新增到账
	private BigDecimal stackMoney=BigDecimal.valueOf(0.00);//存量到账
	private BigDecimal scaleMoney=BigDecimal.valueOf(0.00);//扩容到账
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}
	public Integer getMinBandwidth() {
		return minBandwidth;
	}
	public void setMinBandwidth(Integer minBandwidth) {
		this.minBandwidth = minBandwidth;
	}
	public BigDecimal getMinBandwidthPrice() {
		return minBandwidthPrice;
	}
	public void setMinBandwidthPrice(BigDecimal minBandwidthPrice) {
		this.minBandwidthPrice = minBandwidthPrice;
	}
	public Integer getPortBandwidth() {
		return portBandwidth;
	}
	public void setPortBandwidth(Integer portBandwidth) {
		this.portBandwidth = portBandwidth;
	}
	public BigDecimal getPortBandwidthPrice() {
		return portBandwidthPrice;
	}
	public void setPortBandwidthPrice(BigDecimal portBandwidthPrice) {
		this.portBandwidthPrice = portBandwidthPrice;
	}
	public Integer getOverflowBandwidth() {
		return overflowBandwidth;
	}
	public void setOverflowBandwidth(Integer overflowBandwidth) {
		this.overflowBandwidth = overflowBandwidth;
	}
	public BigDecimal getOverflowBandwidthPrice() {
		return overflowBandwidthPrice;
	}
	public void setOverflowBandwidthPrice(BigDecimal overflowBandwidthPrice) {
		this.overflowBandwidthPrice = overflowBandwidthPrice;
	}
	public Integer getCabinetNum() {
		return cabinetNum;
	}
	public void setCabinetNum(Integer cabinetNum) {
		this.cabinetNum = cabinetNum;
	}
	public BigDecimal getCabinetPrice() {
		return cabinetPrice;
	}
	public void setCabinetPrice(BigDecimal cabinetPrice) {
		this.cabinetPrice = cabinetPrice;
	}
	public Integer getServiceNum() {
		return serviceNum;
	}
	public void setServiceNum(Integer serviceNum) {
		this.serviceNum = serviceNum;
	}
	public BigDecimal getServicePrice() {
		return servicePrice;
	}
	public void setServicePrice(BigDecimal servicePrice) {
		this.servicePrice = servicePrice;
	}
	public Integer getIpNum() {
		return ipNum;
	}
	public void setIpNum(Integer ipNum) {
		this.ipNum = ipNum;
	}
	public BigDecimal getIpPrice() {
		return ipPrice;
	}
	public void setIpPrice(BigDecimal ipPrice) {
		this.ipPrice = ipPrice;
	}
	public Integer getSwitchNum() {
		return switchNum;
	}
	public void setSwitchNum(Integer switchNum) {
		this.switchNum = switchNum;
	}
	public BigDecimal getSwitchPrice() {
		return switchPrice;
	}
	public void setSwitchPrice(BigDecimal switchPrice) {
		this.switchPrice = switchPrice;
	}
	public Integer getOdfNum() {
		return odfNum;
	}
	public void setOdfNum(Integer odfNum) {
		this.odfNum = odfNum;
	}
	public BigDecimal getOdfPrice() {
		return odfPrice;
	}
	public void setOdfPrice(BigDecimal odfPrice) {
		this.odfPrice = odfPrice;
	}
	public Integer getPortNum() {
		return portNum;
	}
	public void setPortNum(Integer portNum) {
		this.portNum = portNum;
	}
	public BigDecimal getPortPrice() {
		return portPrice;
	}
	public void setPortPrice(BigDecimal portPrice) {
		this.portPrice = portPrice;
	}
	public Integer getMainId() {	    return mainId;	}	public void setMainId(Integer mainId) {	    this.mainId=mainId;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}
	//@JsonSerialize(using = CustomDateSerializer.class)	public String getInvoicesMonth() {	    return invoicesMonth;	}	public void setInvoicesMonth(String invoicesMonth) {	    this.invoicesMonth=invoicesMonth;	}	public BigDecimal getMonthAmount() {	    return monthAmount;	}	public void setMonthAmount(BigDecimal monthAmount) {	    this.monthAmount=monthAmount;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getInvoicesStartdate() {	    return invoicesStartdate;	}	public void setInvoicesStartdate(Date invoicesStartdate) {	    this.invoicesStartdate=invoicesStartdate;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getInvoicesEnddate() {	    return invoicesEnddate;	}	public void setInvoicesEnddate(Date invoicesEnddate) {	    this.invoicesEnddate=invoicesEnddate;	}	public String getInvoicesState() {	    return invoicesState;	}	public void setInvoicesState(String invoicesState) {	    this.invoicesState=invoicesState;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getVdateDate() {	    return vdateDate;	}	public void setVdateDate(Date vdateDate) {	    this.vdateDate=vdateDate;	}	public String getInvoicesNo() {	    return invoicesNo;	}	public void setInvoicesNo(String invoicesNo) {	    this.invoicesNo=invoicesNo;	}	public BigDecimal getInvoicesAccount() {	    return invoicesAccount;	}	public void setInvoicesAccount(BigDecimal invoicesAccount) {	    this.invoicesAccount=invoicesAccount;	}	public BigDecimal getNoinvoicesAccount() {	    return noinvoicesAccount;	}	public void setNoinvoicesAccount(BigDecimal noinvoicesAccount) {	    this.noinvoicesAccount=noinvoicesAccount;	}	public String getReceivedState() {	    return receivedState;	}	public void setReceivedState(String receivedState) {	    this.receivedState=receivedState;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getReceivedDate() {	    return receivedDate;	}	public void setReceivedDate(Date receivedDate) {	    this.receivedDate=receivedDate;	}	public BigDecimal getReceivedAccount() {	    return receivedAccount;	}	public void setReceivedAccount(BigDecimal receivedAccount) {	    this.receivedAccount=receivedAccount;	}	public BigDecimal getArrearage() {	    return arrearage;	}	public void setArrearage(BigDecimal arrearage) {	    this.arrearage=arrearage;	}	public BigDecimal getCommision() {	    return commision;	}	public void setCommision(BigDecimal commision) {	    this.commision=commision;	}
	public Integer getOtherDeductions() {
		return otherDeductions;
	}
	public void setOtherDeductions(Integer otherDeductions) {
		this.otherDeductions = otherDeductions;
	}
	public String getCompanyType() {
		return companyType;
	}
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}
	public String getJixiaoticheng() {
		return jixiaoticheng;
	}
	public void setJixiaoticheng(String jixiaoticheng) {
		this.jixiaoticheng = jixiaoticheng;
	}
	public BigDecimal getAllmoney() {
		return allmoney;
	}
	public void setAllmoney(BigDecimal allmoney) {
		this.allmoney = allmoney;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Integer getMemoryNum() {
		return memoryNum;
	}
	public void setMemoryNum(Integer memoryNum) {
		this.memoryNum = memoryNum;
	}
	public BigDecimal getMemoryPrice() {
		return memoryPrice;
	}
	public void setMemoryPrice(BigDecimal memoryPrice) {
		this.memoryPrice = memoryPrice;
	}
	public Integer getCpuNum() {
		return cpuNum;
	}
	public void setCpuNum(Integer cpuNum) {
		this.cpuNum = cpuNum;
	}
	public BigDecimal getCpuPrice() {
		return cpuPrice;
	}
	public void setCpuPrice(BigDecimal cpuPrice) {
		this.cpuPrice = cpuPrice;
	}
	public Integer getDiskNum() {
		return diskNum;
	}
	public void setDiskNum(Integer diskNum) {
		this.diskNum = diskNum;
	}
	public BigDecimal getDiskPrice() {
		return diskPrice;
	}
	public void setDiskPrice(BigDecimal diskPrice) {
		this.diskPrice = diskPrice;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public BigDecimal getAddmoney() {
		return addmoney;
	}
	public void setAddmoney(BigDecimal addmoney) {
		this.addmoney = addmoney;
	}
	public BigDecimal getStackMoney() {
		return stackMoney;
	}
	public void setStackMoney(BigDecimal stackMoney) {
		this.stackMoney = stackMoney;
	}
	public BigDecimal getScaleMoney() {
		return scaleMoney;
	}
	public void setScaleMoney(BigDecimal scaleMoney) {
		this.scaleMoney = scaleMoney;
	}
	
	public String getCompanyTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("customerType", this.getCompanyType()) == null ? "" : dicManager.getDic("customerType", this.getCompanyType()).getValue();
	}
	
	public String getRoomNameValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getRoomName()) == null ? "" : dicManager.getDic("room", this.getRoomName()).getValue();
	}}

