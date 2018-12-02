package com.jc.android.oa.shyt.customer.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;
/**
 * @author mrb
 * @version 机房参观和入室维护表
*/
public class ToaRoomVisitMaintenance4M extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String equipmentNumber;//   工单编号	private Integer companyId;//   客户id	private String companyName;//   客户名称	private String industryType;//   行业类型       						for 机房参观	private String contact;//   客户联系人								for 机房参观.销售经理    入室维护.联系人	private String tel;//   客户联系方式	private Date intDate;//   入室日期								for 机房参观.预计参观时间   	入室维护.预计入室日期
	private String machina;//   机房	private String type;//   入室操作类型（巡检、硬件测试、软件测试、盘点）			for						入室维护.入室操作类型	private String realityType;//   实际操作内容（巡检、硬件测试、软件测试、盘点）	for						入室维护.实际操作内容	private String preOperate;//   有无前置操作						for						入室维护.有无前置操作	private String intPeople;//   入室人员							for 机房参观.参观人员		入室维护.入室人员	private String intPeopleCard;//   入室人员身份证号	private String visitZone;//   参观区域（机房、配电、制冷、办公区、发电机）	for 机房参观.参观区域	private String isInput;//   是否进场	private String caozgcs;//   陪同人员								for 机房参观.陪同人			入室维护.机房接待人员
	private String caozgcsName;//   陪同人员姓名						for 机房参观.陪同人姓名		入室维护.机房接待人员姓名	private Integer judge;//   评价1-10分	private Date startDate;//   实际入室时间							for 机房参观.参观开始时间	入室维护.实际入室日期	private Date endDate;//   实际出室时间								for 机房参观.参观结束时间	入室维护.实际出室日期	private Integer operateType;//   工单类型（19、机房参观；20、入室维护）
	private Integer status;//   处理状态	private String remark;//   备注	private String preOperateUrl;//   前置操作链接						for						入室维护.前置操作链接	private String fileUrl;//   附件		public String getEquipmentNumber() {	    return equipmentNumber;	}	public void setEquipmentNumber(String equipmentNumber) {	    this.equipmentNumber=equipmentNumber;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getIndustryType() {	    return industryType;	}	public void setIndustryType(String industryType) {	    this.industryType=industryType;	}	public String getContact() {	    return contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getTel() {	    return tel;	}	public void setTel(String tel) {	    this.tel=tel;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getIntDate() {	    return intDate;	}	public void setIntDate(Date intDate) {	    this.intDate=intDate;	}	public String getType() {	    return type;	}	public void setType(String type) {	    this.type=type;	}	public String getRealityType() {	    return realityType;	}	public void setRealityType(String realityType) {	    this.realityType=realityType;	}	public String getPreOperate() {	    return preOperate;	}	public void setPreOperate(String preOperate) {	    this.preOperate=preOperate;	}	public String getIntPeople() {	    return intPeople;	}	public void setIntPeople(String intPeople) {	    this.intPeople=intPeople;	}	public String getIntPeopleCard() {	    return intPeopleCard;	}	public void setIntPeopleCard(String intPeopleCard) {	    this.intPeopleCard=intPeopleCard;	}	public String getVisitZone() {	    return visitZone;	}	public void setVisitZone(String visitZone) {	    this.visitZone=visitZone;	}	public String getIsInput() {	    return isInput;	}	public void setIsInput(String isInput) {	    this.isInput=isInput;	}	public String getCaozgcs() {	    return caozgcs;	}	public void setCaozgcs(String caozgcs) {	    this.caozgcs=caozgcs;	}	public Integer getJudge() {	    return judge;	}	public void setJudge(Integer judge) {	    this.judge=judge;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getStartDate() {	    return startDate;	}	public void setStartDate(Date startDate) {	    this.startDate=startDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getEndDate() {	    return endDate;	}	public void setEndDate(Date endDate) {	    this.endDate=endDate;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public String getPreOperateUrl() {	    return preOperateUrl;	}	public void setPreOperateUrl(String preOperateUrl) {	    this.preOperateUrl=preOperateUrl;	}	public String getFileUrl() {	    return fileUrl;	}	public void setFileUrl(String fileUrl) {	    this.fileUrl=fileUrl;	}
	public String getMachina() {
		return machina;
	}
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}
	public void setMachina(String machina) {
		this.machina = machina;
	}
	public String getCaozgcsName() {
		return caozgcsName;
	}
	public void setCaozgcsName(String caozgcsName) {
		this.caozgcsName = caozgcsName;
	}
	public Integer getOperateType() {
		return operateType;
	}
	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}
	public String getOperationTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("operateType", this.getOperateType()+"") == null ? "" : dicManager.getDic("operateType", this.getOperateType()+"").getValue();
	}}

