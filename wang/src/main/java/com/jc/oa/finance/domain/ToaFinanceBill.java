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
 * @version 账单信息
*/
public class ToaFinanceBill extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private Date billDate;//   账单日期	private String companyName;//   客户名称	private String companyId;//   客户id	private String custmersTaxid;//   纳税人识号	private String address;//   地址	private String phone;//   电话	private String bankName;//   银行	private String bankNo;//   银行帐号	private String ticket;//   发票类型	private Integer state;//   审批状态	private BigDecimal billMoney;//   合计金额=》应收金额	private BigDecimal billMoneym;//   合计金额大写=》应收金额大写	private Date payDate;//   应支付日期	private String obankname;//   开户银行	private String obankno;//   帐 号	private String ocompany;//   公司名称	private String sale;//   客户经理	private String salePhone;//   电话	private String remarkId;//   所对应收入id
	private String serviceType;
	private Date startIntel;
	private BigDecimal oweMoney;//欠费金额
	
	//增加字段 
	private BigDecimal comeMoney;//   实际回款金额
	private String numbers;//   发票号
	private BigDecimal comeMoneyByMonth;//   每月实际回款金额
	private BigDecimal billMoneyByMonth;//   每月应收金额
	private BigDecimal oweMoneyByMonth;//   每月欠费金额
	
	//统计各个机房应收金额所占比例饼图使用
	private String roomFlg;
	private String roomName;

	public String getServiceTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("customerType", this.getServiceType()) == null ? "" : dicManager.getDic("customerType", this.getServiceType()).getValue();
	}
	
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getBillDate() {	    return billDate;	}	public void setBillDate(Date billDate) {	    this.billDate=billDate;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getCompanyId() {	    return companyId;	}	public void setCompanyId(String companyId) {	    this.companyId=companyId;	}	public String getCustmersTaxid() {	    return custmersTaxid;	}	public void setCustmersTaxid(String custmersTaxid) {	    this.custmersTaxid=custmersTaxid;	}	public String getAddress() {	    return address;	}	public void setAddress(String address) {	    this.address=address;	}	public String getPhone() {	    return phone;	}	public void setPhone(String phone) {	    this.phone=phone;	}	public String getBankName() {	    return bankName;	}	public void setBankName(String bankName) {	    this.bankName=bankName;	}	public String getBankNo() {	    return bankNo;	}	public void setBankNo(String bankNo) {	    this.bankNo=bankNo;	}	public String getTicket() {	    return ticket;	}	public void setTicket(String ticket) {	    this.ticket=ticket;	}	public BigDecimal getBillMoney() {	    return billMoney;	}	public void setBillMoney(BigDecimal billMoney) {	    this.billMoney=billMoney;	}	public BigDecimal getBillMoneym() {	    return billMoneym;	}	public void setBillMoneym(BigDecimal billMoneym) {	    this.billMoneym=billMoneym;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getPayDate() {	    return payDate;	}	public void setPayDate(Date payDate) {	    this.payDate=payDate;	}	public String getObankname() {	    return obankname;	}	public void setObankname(String obankname) {	    this.obankname=obankname;	}	public String getObankno() {	    return obankno;	}	public void setObankno(String obankno) {	    this.obankno=obankno;	}	public String getOcompany() {	    return ocompany;	}	public void setOcompany(String ocompany) {	    this.ocompany=ocompany;	}	public String getSale() {	    return sale;	}	public void setSale(String sale) {	    this.sale=sale;	}	public String getSalePhone() {	    return salePhone;	}	public void setSalePhone(String salePhone) {	    this.salePhone=salePhone;	}	public String getRemarkId() {	    return remarkId;	}	public void setRemarkId(String remarkId) {	    this.remarkId=remarkId;	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getStartIntel() {
		return startIntel;
	}
	public void setStartIntel(Date startIntel) {
		this.startIntel = startIntel;
	}
	public BigDecimal getOweMoney() {
		return oweMoney;
	}
	public void setOweMoney(BigDecimal oweMoney) {
		this.oweMoney = oweMoney;
	}
	public BigDecimal getComeMoney() {
		return comeMoney;
	}
	public void setComeMoney(BigDecimal comeMoney) {
		this.comeMoney = comeMoney;
	}
	public String getNumbers() {
		return numbers;
	}
	public void setNumbers(String numbers) {
		this.numbers = numbers;
	}
	public BigDecimal getComeMoneyByMonth() {
		return comeMoneyByMonth;
	}
	public void setComeMoneyByMonth(BigDecimal comeMoneyByMonth) {
		this.comeMoneyByMonth = comeMoneyByMonth;
	}
	public BigDecimal getBillMoneyByMonth() {
		return billMoneyByMonth;
	}
	public void setBillMoneyByMonth(BigDecimal billMoneyByMonth) {
		this.billMoneyByMonth = billMoneyByMonth;
	}
	public BigDecimal getOweMoneyByMonth() {
		return oweMoneyByMonth;
	}
	public void setOweMoneyByMonth(BigDecimal oweMoneyByMonth) {
		this.oweMoneyByMonth = oweMoneyByMonth;
	}
	public String getRoomFlg() {
		return roomFlg;
	}
	public void setRoomFlg(String roomFlg) {
		this.roomFlg = roomFlg;
	}
	public String getRoomName() {
		return roomName;
	}
	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}}

