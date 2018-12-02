package com.jc.android.oa.shyt.network.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;
/**
 * @author mrb
 * @version 网络故障处理表
*/
public class ToaNetworkWrong4M extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String equipmentNumber;//   工单编号
	private Integer companyId;//   客户ID
	private String companyName;//   客户名称
	private String contact;//   客户联系人
	private String tel;//   客户联系方式
	private Date workTime;//   操作开始时间
	private Date startDate;//   操作开始时间
	private Date endDate;//   操作完成时间
	private String wrongType;//   故障类型
	private String srcAddress;//   源IP地址
	private Integer isSrcForbidPing;//   源IP地址是否禁Ping（是 、否）
	private String destAddress;//   目的IP地址
	private Integer isDestForbidPing;//   目的IP地址是否禁Ping（是 、否）
	private String compareSrcAddress;//   对照组源IP地址
	private String compareDestAddress;//   对照组目的IP地址
	private String belongCompanyName;//   对端所属运营商
	private String lostPercent;//   丢包率
	private String lastJumpOut;//   路由跟踪图可看到最后一跳地址-出森华方向
	private String lastJumpIn;//   路由跟踪图可看到最后一跳地址-入森华方向
	private String excepIp;//   异常接口所在IP段或网关地址
	private String excepDirection;//   异常流量方向：（IDC方向（服务器到公网）、ISP方向（公网到服务器））
	private Integer isRankAnalyse;//   是否需要进行IP地址排名分析
	private Date analyseStartDate;//   IP地址排名分析起始时间
	private Date analyseEndDate;//   IP地址排名分析结束时间
	private String ipRange;//   流量突降IP地址范围
	private String interfaceProblem;//   接口问题：（上联接口有闪断记录、上联接口没查到问题）
	private String badIp;//   问题IP地址
	private Integer isBackup;//   是否备案
	private Integer isAgree;//   是否备案
	private Date problemStartTime;//   问题发生时间
	private String realExcepType;//   实际故障类型
	private String solveResult;//   故障排查结果汇报
	private String operationType;//   工单类型（17.设备故障 18、网络故障）
	private String operationTypeImg;//   工单类型图标名称（17.设备故障 18、网络故障）
	private String machina;//   机房
	private String caozgcs;//   操作人员ID
	private String caozgcsName;//   操作人员姓名
	private Integer status;//   处理状态
	private String score;//   评分
	private String remark;//   备注
	public String getEquipmentNumber() {	    return equipmentNumber;	}	public void setEquipmentNumber(String equipmentNumber) {	    this.equipmentNumber=equipmentNumber;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getContact() {	    return contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getTel() {	    return tel;	}	public void setTel(String tel) {	    this.tel=tel;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getStartDate() {	    return startDate;	}	public void setStartDate(Date startDate) {	    this.startDate=startDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getEndDate() {	    return endDate;	}	public void setEndDate(Date endDate) {	    this.endDate=endDate;	}	public String getWrongType() {	    return wrongType;	}	public void setWrongType(String wrongType) {	    this.wrongType=wrongType;	}	public String getSrcAddress() {	    return srcAddress;	}	public void setSrcAddress(String srcAddress) {	    this.srcAddress=srcAddress;	}	public String getDestAddress() {	    return destAddress;	}	public void setDestAddress(String destAddress) {	    this.destAddress=destAddress;	}	public Integer getIsSrcForbidPing() {
		return isSrcForbidPing;
	}
	public void setIsSrcForbidPing(Integer isSrcForbidPing) {
		this.isSrcForbidPing = isSrcForbidPing;
	}
	public Integer getIsDestForbidPing() {
		return isDestForbidPing;
	}
	public void setIsDestForbidPing(Integer isDestForbidPing) {
		this.isDestForbidPing = isDestForbidPing;
	}
	public String getCompareSrcAddress() {	    return compareSrcAddress;	}	public void setCompareSrcAddress(String compareSrcAddress) {	    this.compareSrcAddress=compareSrcAddress;	}	public String getCompareDestAddress() {	    return compareDestAddress;	}	public void setCompareDestAddress(String compareDestAddress) {	    this.compareDestAddress=compareDestAddress;	}	public String getBelongCompanyName() {	    return belongCompanyName;	}	public void setBelongCompanyName(String belongCompanyName) {	    this.belongCompanyName=belongCompanyName;	}	public String getLostPercent() {	    return lostPercent;	}	public void setLostPercent(String lostPercent) {	    this.lostPercent=lostPercent;	}	public String getLastJumpOut() {	    return lastJumpOut;	}	public void setLastJumpOut(String lastJumpOut) {	    this.lastJumpOut=lastJumpOut;	}	public String getLastJumpIn() {	    return lastJumpIn;	}	public void setLastJumpIn(String lastJumpIn) {	    this.lastJumpIn=lastJumpIn;	}	public String getExcepIp() {	    return excepIp;	}	public void setExcepIp(String excepIp) {	    this.excepIp=excepIp;	}	public String getExcepDirection() {	    return excepDirection;	}	public void setExcepDirection(String excepDirection) {	    this.excepDirection=excepDirection;	}	public Integer getIsRankAnalyse() {	    return isRankAnalyse;	}	public void setIsRankAnalyse(Integer isRankAnalyse) {	    this.isRankAnalyse=isRankAnalyse;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getAnalyseStartDate() {	    return analyseStartDate;	}	public void setAnalyseStartDate(Date analyseStartDate) {	    this.analyseStartDate=analyseStartDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getAnalyseEndDate() {	    return analyseEndDate;	}	public void setAnalyseEndDate(Date analyseEndDate) {	    this.analyseEndDate=analyseEndDate;	}	public String getIpRange() {	    return ipRange;	}	public void setIpRange(String ipRange) {	    this.ipRange=ipRange;	}	public String getInterfaceProblem() {
		return interfaceProblem;
	}
	public void setInterfaceProblem(String interfaceProblem) {
		this.interfaceProblem = interfaceProblem;
	}
	public String getBadIp() {	    return badIp;	}	public void setBadIp(String badIp) {	    this.badIp=badIp;	}	public Integer getIsBackup() {	    return isBackup;	}	public void setIsBackup(Integer isBackup) {	    this.isBackup=isBackup;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getProblemStartTime() {	    return problemStartTime;	}	public void setProblemStartTime(Date problemStartTime) {	    this.problemStartTime=problemStartTime;	}	public String getRealExcepType() {	    return realExcepType;	}	public void setRealExcepType(String realExcepType) {	    this.realExcepType=realExcepType;	}	public String getSolveResult() {	    return solveResult;	}	public void setSolveResult(String solveResult) {	    this.solveResult=solveResult;	}	public String getOperationType() {	    return operationType;	}	public void setOperationType(String operationType) {	    this.operationType=operationType;	}
	public String getOperationTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("operateType", this.getOperationType()+"") == null ? "" : dicManager.getDic("operateType", this.getOperationType()+"").getValue();
	}	public String getOperationTypeImg() {	    return operationTypeImg;	}	public void setOperationTypeImg(String operationTypeImg) {	    this.operationTypeImg=operationTypeImg;	}	public String getMachina() {	    return machina;	}	public void setMachina(String machina) {	    this.machina=machina;	}	public String getCaozgcs() {	    return caozgcs;	}	public void setCaozgcs(String caozgcs) {	    this.caozgcs=caozgcs;	}	public String getCaozgcsName() {	    return caozgcsName;	}	public void setCaozgcsName(String caozgcsName) {	    this.caozgcsName=caozgcsName;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}
	public Date getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
	public Integer getIsAgree() {
		return isAgree;
	}
	public void setIsAgree(Integer isAgree) {
		this.isAgree = isAgree;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}}

