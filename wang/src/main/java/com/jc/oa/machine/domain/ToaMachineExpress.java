package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 代收发快递
*/
public class ToaMachineExpress extends BaseBean {
	//
	private static final long serialVersionUID = 1L;
	private String expressNumber;//   工单编号
	private String companyName;//   客户名称
	private Integer companyId;//   客户ID
	private String machina;//   所属机房
	private String contact;//   客户联系人
	private String tel;//   客户联系方式-》联系方式
	private String type;//   工单类型（代发快递、代收快递）
	private String deviceDs;//   代收设备信息->代收发设备信息
	private String payType;//   付款方式
	private String deviceDf;//   代发设备信息
	private String moneyType;//   是否保价
	private String expressPeople;//   收件人
	private String expressAddress;//   收件地址-》地址
	private String daifuPay;//   代付费用-》付款金额
	private Date expressDate;//   代收发时间->预计到货时间
	private String caozgcs;//   操作工程师-》工程师id
	private String remark;//   备注
	private Integer status;//   处理状态
	//新增
	private String prepose;//   前置操作
	private String preposeLink;//   前置链接
	private String expressCompany;//   快递公司
	private String expressCentral;//   快递单号
	private String sender;//   发件人
	private String drawee;//   付款人
	private String insurance;//   保险
	private String situation;//   (保险)情况
	private String packingModel;//   包装规格
	private Date orderDate;//   接单时间
	private Date completionDate;//   完成时间
	private String checkAccept;//   验收方式
	private String checkOther;//   验收方式情况
	private String checkAccepttance;//   验收
	private String signReceiver;//   签收
	//private String extStr1;//工程师名称
		public String getExpressNumber() {	    return expressNumber;	}	public String getCheckAccepttance() {
		return checkAccepttance;
	}
	public void setCheckAccepttance(String checkAccepttance) {
		this.checkAccepttance = checkAccepttance;
	}
	public String getSignReceiver() {
		return signReceiver;
	}
	public void setSignReceiver(String signReceiver) {
		this.signReceiver = signReceiver;
	}
	public void setExpressNumber(String expressNumber) {	    this.expressNumber=expressNumber;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getMachina() {	    return machina;	}	public void setMachina(String machina) {	    this.machina=machina;	}
	
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}
		public String getContact() {	    return contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getTel() {	    return tel;	}	public void setTel(String tel) {	    this.tel=tel;	}	public String getType() {	    return type;	}	public void setType(String type) {	    this.type=type;	}	public String getDeviceDs() {	    return deviceDs;	}	public void setDeviceDs(String deviceDs) {	    this.deviceDs=deviceDs;	}	public String getPayType() {	    return payType;	}	public void setPayType(String payType) {	    this.payType=payType;	}	public String getDeviceDf() {	    return deviceDf;	}	public void setDeviceDf(String deviceDf) {	    this.deviceDf=deviceDf;	}	public String getMoneyType() {	    return moneyType;	}	public void setMoneyType(String moneyType) {	    this.moneyType=moneyType;	}	public String getExpressPeople() {	    return expressPeople;	}	public void setExpressPeople(String expressPeople) {	    this.expressPeople=expressPeople;	}	public String getExpressAddress() {	    return expressAddress;	}	public void setExpressAddress(String expressAddress) {	    this.expressAddress=expressAddress;	}	public String getDaifuPay() {	    return daifuPay;	}	public void setDaifuPay(String daifuPay) {	    this.daifuPay=daifuPay;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getExpressDate() {	    return expressDate;	}	public void setExpressDate(Date expressDate) {	    this.expressDate=expressDate;	}	public String getCaozgcs() {	    return caozgcs;	}	public void setCaozgcs(String caozgcs) {	    this.caozgcs=caozgcs;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}
	public String getPrepose() {
		return prepose;
	}
	public void setPrepose(String prepose) {
		this.prepose = prepose;
	}
	public String getPreposeLink() {
		return preposeLink;
	}
	public void setPreposeLink(String preposeLink) {
		this.preposeLink = preposeLink;
	}
	public String getExpressCompany() {
		return expressCompany;
	}
	public void setExpressCompany(String expressCompany) {
		this.expressCompany = expressCompany;
	}
	public String getExpressCentral() {
		return expressCentral;
	}
	public void setExpressCentral(String expressCentral) {
		this.expressCentral = expressCentral;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getDrawee() {
		return drawee;
	}
	public void setDrawee(String drawee) {
		this.drawee = drawee;
	}
	public String getInsurance() {
		return insurance;
	}
	public void setInsurance(String insurance) {
		this.insurance = insurance;
	}
	public String getSituation() {
		return situation;
	}
	public void setSituation(String situation) {
		this.situation = situation;
	}
	public String getPackingModel() {
		return packingModel;
	}
	public void setPackingModel(String packingModel) {
		this.packingModel = packingModel;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	public String getCheckAccept() {
		return checkAccept;
	}
	public void setCheckAccept(String checkAccept) {
		this.checkAccept = checkAccept;
	}
	public String getCheckOther() {
		return checkOther;
	}
	public void setCheckOther(String checkOther) {
		this.checkOther = checkOther;
	}
}

