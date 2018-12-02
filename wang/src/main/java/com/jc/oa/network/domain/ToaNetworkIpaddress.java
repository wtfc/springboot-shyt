package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 客户IP地址统计表
*/
public class ToaNetworkIpaddress extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyName;//   客户名称	private Integer companyId;//   客户id	private String addressType;//   地址类型（单线、多线）	private String machine;//   机房	private String equipment;//   设备	private String ipOne;//   IP地址段开始	private String ipTwo;//   IP地址段结束	private String ipNumber;//   IP数量	private String bandwidthNumber;//   设备端口	private String bandwidth;//   带宽	private String operationEnginner;//   操作工程师	private Date operationDate;//   操作时间	private Date dividerDate;//   分配时间	private String workNumber;//   工单编号	private Integer addressNumber;//   地址总数	private Integer usingNumber;//   使用地址总数	private Integer idlenessNumber;//   空闲地址总数	private String usingRate;//   使用率	private String idlenessRate;//   空闲率	private String remark;//   备注	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getAddressType() {	    return addressType;	}	public void setAddressType(String addressType) {	    this.addressType=addressType;	}	public String getMachine() {	    return machine;	}	public void setMachine(String machine) {	    this.machine=machine;	}
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
		public String getEquipment() {	    return equipment;	}	public void setEquipment(String equipment) {	    this.equipment=equipment;	}	public String getIpOne() {	    return ipOne;	}	public void setIpOne(String ipOne) {	    this.ipOne=ipOne;	}	public String getIpTwo() {	    return ipTwo;	}	public void setIpTwo(String ipTwo) {	    this.ipTwo=ipTwo;	}	public String getIpNumber() {	    return ipNumber;	}	public void setIpNumber(String ipNumber) {	    this.ipNumber=ipNumber;	}	public String getBandwidthNumber() {	    return bandwidthNumber;	}	public void setBandwidthNumber(String bandwidthNumber) {	    this.bandwidthNumber=bandwidthNumber;	}	public String getBandwidth() {	    return bandwidth;	}	public void setBandwidth(String bandwidth) {	    this.bandwidth=bandwidth;	}	public String getOperationEnginner() {	    return operationEnginner;	}	public void setOperationEnginner(String operationEnginner) {	    this.operationEnginner=operationEnginner;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getOperationDate() {	    return operationDate;	}	public void setOperationDate(Date operationDate) {	    this.operationDate=operationDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getDividerDate() {	    return dividerDate;	}	public void setDividerDate(Date dividerDate) {	    this.dividerDate=dividerDate;	}	public String getWorkNumber() {	    return workNumber;	}	public void setWorkNumber(String workNumber) {	    this.workNumber=workNumber;	}	public Integer getAddressNumber() {	    return addressNumber;	}	public void setAddressNumber(Integer addressNumber) {	    this.addressNumber=addressNumber;	}	public Integer getUsingNumber() {	    return usingNumber;	}	public void setUsingNumber(Integer usingNumber) {	    this.usingNumber=usingNumber;	}	public Integer getIdlenessNumber() {	    return idlenessNumber;	}	public void setIdlenessNumber(Integer idlenessNumber) {	    this.idlenessNumber=idlenessNumber;	}	public String getUsingRate() {	    return usingRate;	}	public void setUsingRate(String usingRate) {	    this.usingRate=usingRate;	}	public String getIdlenessRate() {	    return idlenessRate;	}	public void setIdlenessRate(String idlenessRate) {	    this.idlenessRate=idlenessRate;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

