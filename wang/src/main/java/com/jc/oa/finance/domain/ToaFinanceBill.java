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
	private Date billDate;//   账单日期
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
	
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
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
	}
