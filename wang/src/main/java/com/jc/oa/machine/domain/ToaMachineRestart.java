package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 重启操作
*/
public class ToaMachineRestart extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String equipmentNumber;//   工单编号
	private String diviceNumber;//设备编号	private Integer companyId;//   客户id	private String companyName;//   客户名称	private String machina;//   所属机房	private String contact;//   客户联系人	private String tel;//   客户联系方式	private String ip;//   IP地址（机柜）--》客户需求	private String caozgcs;//   操作工程师--》技术处理人	private Date operateDate;//   操作时间--》开始操作	private Date endDate;//   完成时间--》操作完成	private String remark;//   备注	private Integer status;//   处理状态
	private String  engineRoom;//机房区域
	private String cabinetNumber;//机柜区域
	
	//新增字段
	private Date billDate; //发单时间
	private Date warnDate;//报障时间--》到达设备现场时间
	private String isMonitor;//是否连显示器
	private String deviceStatus;//设备状态
	private String isWrong;//是否有报错误
	private String errorMessage;//错误信息
	private String description;//情况说明
	private String recoedPeople;//记录人
	private String isOvertime;//是否超时(10分钟)
	private String errorRepair;//错误是否修复
	private String chargeExamine;//机房主管审批 -->前置操作
	private String chargeInformation;//机房主管问题说明-->前置操作内容
	private String regionExamine;//区域主管审批-->后置操作
    private String regionInformation;//区域主管问题说明-->后置操作内容
    private String contactWay; //客户联系方式
	private String sn;//SN
	private String firstRestart;//第一次重启
	private String assist; //协助处理
	private String scanCode;//扫描码
	
	/*附件Id*/
	private List<Long> fileids;
	private String delattachIds;//删除附件Id
	
	//新增字段
	private String applicant;//申请人
	private String cabinet;//机柜
	private String brand;//品牌-》品牌型号
	private String modelNumber;//型号
	private String operationType;//操作类型
	private String equipmentId;//设备id
	
	private Date workTime;//   接单时间/完成时间
	private String executiveOutcome;//   执行结果/设备核对结果/核对设备结果
	private String contentViewed;//   查看内容/统计内容/调试内容
	private String diewctOperation;//   是否直接操作
	private String outsideInformation;//   外接设备信息
	private String outsideSpecification;//   外接设备规格/网线规格
	private String connectionInformation;//   连接设备信息
	private String externalEquipment;//   外接设备是否满足
	private String networkInformation;//   网络接口信息
	private String portResult;//   核对端口结果/ip配置结果
	private String estimatedTime;//   预计使用时长
	private String placeStorage;//   存放位置
	private String connectWires;//   是否连接网线
	private String ipConfiguration;//   是否配置IP/是否提供配置命令
	private String ipEmly;//   IP配置
	private String configurationType;//   配置类型
	private String systemType;//   系统类型
	private String partitionRequirements;//   分区要求
	private String remoteDesktop;//   远程桌面是否开启远程
	private String longDistance;//   ssh是否开启远程
	private String currentSite;//   kvm地址
	private String currentUserName;//   用户名
	private String currentPassword;//   密码
	private String testOutcome;//   测试结果/网络连通性测试结果/调试结果
	private String statisticalListing;//   统计清单
	private String salesExpers;//   具体要求
	
	public String getDiviceNumber() {
		return diviceNumber;
	}
	public void setDiviceNumber(String diviceNumber) {
		this.diviceNumber = diviceNumber;
	}
	public String getEngineRoom() {
		return engineRoom;
	}
	public void setEngineRoom(String engineRoom) {
		this.engineRoom = engineRoom;
	}
	public String getCabinetNumber() {
		return cabinetNumber;
	}
	public void setCabinetNumber(String cabinetNumber) {
		this.cabinetNumber = cabinetNumber;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Date workTime) {
		this.workTime = workTime;
	}
	public String getExecutiveOutcome() {
		return executiveOutcome;
	}
	public void setExecutiveOutcome(String executiveOutcome) {
		this.executiveOutcome = executiveOutcome;
	}
	public String getContentViewed() {
		return contentViewed;
	}
	public void setContentViewed(String contentViewed) {
		this.contentViewed = contentViewed;
	}
	public String getDiewctOperation() {
		return diewctOperation;
	}
	public void setDiewctOperation(String diewctOperation) {
		this.diewctOperation = diewctOperation;
	}
	public String getOutsideInformation() {
		return outsideInformation;
	}
	public void setOutsideInformation(String outsideInformation) {
		this.outsideInformation = outsideInformation;
	}
	public String getOutsideSpecification() {
		return outsideSpecification;
	}
	public void setOutsideSpecification(String outsideSpecification) {
		this.outsideSpecification = outsideSpecification;
	}
	public String getConnectionInformation() {
		return connectionInformation;
	}
	public void setConnectionInformation(String connectionInformation) {
		this.connectionInformation = connectionInformation;
	}
	public String getExternalEquipment() {
		return externalEquipment;
	}
	public void setExternalEquipment(String externalEquipment) {
		this.externalEquipment = externalEquipment;
	}
	public String getNetworkInformation() {
		return networkInformation;
	}
	public void setNetworkInformation(String networkInformation) {
		this.networkInformation = networkInformation;
	}
	public String getPortResult() {
		return portResult;
	}
	public void setPortResult(String portResult) {
		this.portResult = portResult;
	}
	public String getEstimatedTime() {
		return estimatedTime;
	}
	public void setEstimatedTime(String estimatedTime) {
		this.estimatedTime = estimatedTime;
	}
	public String getPlaceStorage() {
		return placeStorage;
	}
	public void setPlaceStorage(String placeStorage) {
		this.placeStorage = placeStorage;
	}
	public String getConnectWires() {
		return connectWires;
	}
	public void setConnectWires(String connectWires) {
		this.connectWires = connectWires;
	}
	public String getIpConfiguration() {
		return ipConfiguration;
	}
	public void setIpConfiguration(String ipConfiguration) {
		this.ipConfiguration = ipConfiguration;
	}
	public String getIpEmly() {
		return ipEmly;
	}
	public void setIpEmly(String ipEmly) {
		this.ipEmly = ipEmly;
	}
	public String getConfigurationType() {
		return configurationType;
	}
	public void setConfigurationType(String configurationType) {
		this.configurationType = configurationType;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getPartitionRequirements() {
		return partitionRequirements;
	}
	public void setPartitionRequirements(String partitionRequirements) {
		this.partitionRequirements = partitionRequirements;
	}
	public String getRemoteDesktop() {
		return remoteDesktop;
	}
	public void setRemoteDesktop(String remoteDesktop) {
		this.remoteDesktop = remoteDesktop;
	}
	public String getLongDistance() {
		return longDistance;
	}
	public void setLongDistance(String longDistance) {
		this.longDistance = longDistance;
	}
	public String getCurrentSite() {
		return currentSite;
	}
	public void setCurrentSite(String currentSite) {
		this.currentSite = currentSite;
	}
	public String getCurrentUserName() {
		return currentUserName;
	}
	public void setCurrentUserName(String currentUserName) {
		this.currentUserName = currentUserName;
	}
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getTestOutcome() {
		return testOutcome;
	}
	public void setTestOutcome(String testOutcome) {
		this.testOutcome = testOutcome;
	}
	public String getStatisticalListing() {
		return statisticalListing;
	}
	public void setStatisticalListing(String statisticalListing) {
		this.statisticalListing = statisticalListing;
	}
	public String getSalesExpers() {
		return salesExpers;
	}
	public void setSalesExpers(String salesExpers) {
		this.salesExpers = salesExpers;
	}
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("operateType", this.getOperationType()) == null ? "" : dicManager.getDic("operateType", this.getOperationType()).getValue();
	}
	public String getApplicant() {
		return applicant;
	}
	public void setApplicant(String applicant) {
		this.applicant = applicant;
	}
	public String getCabinet() {
		return cabinet;
	}
	public void setCabinet(String cabinet) {
		this.cabinet = cabinet;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModelNumber() {
		return modelNumber;
	}
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}
	public List<Long> getFileids() {
		return fileids;
	}
	public void setFileids(List<Long> fileids) {
		this.fileids = fileids;
	}
	public String getDelattachIds() {
		return delattachIds;
	}
	public void setDelattachIds(String delattachIds) {
		this.delattachIds = delattachIds;
	}
	public String getContactWay() {
		return contactWay;
	}
	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getFirstRestart() {
		return firstRestart;
	}
	public void setFirstRestart(String firstRestart) {
		this.firstRestart = firstRestart;
	}
	public String getAssist() {
		return assist;
	}
	public void setAssist(String assist) {
		this.assist = assist;
	}
	public String getScanCode() {
		return scanCode;
	}
	public void setScanCode(String scanCode) {
		this.scanCode = scanCode;
	}
	public String getChargeInformation() {
		return chargeInformation;
	}
	public void setChargeInformation(String chargeInformation) {
		this.chargeInformation = chargeInformation;
	}
	public String getRegionExamine() {
		return regionExamine;
	}
	public void setRegionExamine(String regionExamine) {
		this.regionExamine = regionExamine;
	}
	public String getRegionInformation() {
		return regionInformation;
	}
	public void setRegionInformation(String regionInformation) {
		this.regionInformation = regionInformation;
	}
	public String getErrorRepair() {
		return errorRepair;
	}
	public void setErrorRepair(String errorRepair) {
		this.errorRepair = errorRepair;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getBillDate() {
		return billDate;
	}
	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getWarnDate() {
		return warnDate;
	}
	public void setWarnDate(Date warnDate) {
		this.warnDate = warnDate;
	}
	public String getIsMonitor() {
		return isMonitor;
	}
	public void setIsMonitor(String isMonitor) {
		this.isMonitor = isMonitor;
	}
	public String getDeviceStatus() {
		return deviceStatus;
	}
	public void setDeviceStatus(String deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
	public String getIsWrong() {
		return isWrong;
	}
	public void setIsWrong(String isWrong) {
		this.isWrong = isWrong;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRecoedPeople() {
		return recoedPeople;
	}
	public void setRecoedPeople(String recoedPeople) {
		this.recoedPeople = recoedPeople;
	}
	public String getChargeExamine() {
		return chargeExamine;
	}
	public void setChargeExamine(String chargeExamine) {
		this.chargeExamine = chargeExamine;
	}
	public String getIsOvertime() {
		return isOvertime;
	}
	public void setIsOvertime(String isOvertime) {
		this.isOvertime = isOvertime;
	}
	public String getEquipmentNumber() {	    return equipmentNumber;	}	public void setEquipmentNumber(String equipmentNumber) {	    this.equipmentNumber=equipmentNumber;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getMachina() {	    return machina;	}	public void setMachina(String machina) {	    this.machina=machina;	}
	
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}
		public String getContact() {	    return contact;	}	public void setContact(String contact) {	    this.contact=contact;	}	public String getTel() {	    return tel;	}	public void setTel(String tel) {	    this.tel=tel;	}	public String getIp() {	    return ip;	}	public void setIp(String ip) {	    this.ip=ip;	}	public String getCaozgcs() {	    return caozgcs;	}	public void setCaozgcs(String caozgcs) {	    this.caozgcs=caozgcs;	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getOperateDate() {	    return operateDate;	}	public void setOperateDate(Date operateDate) {	    this.operateDate=operateDate;	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getEndDate() {	    return endDate;	}	public void setEndDate(Date endDate) {	    this.endDate=endDate;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}}

