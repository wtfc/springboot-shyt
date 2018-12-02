package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version IP封停记录表
*/
public class ToaNetworkIpstop extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private Integer companyId;//   客户id	private String companyName;//   客户名称	private String machine;//   机房	private String stopEquipment;//   封停IP所在设备	private String ip;//   IP地址/位数	private String stopType;//   封停方式	private String stopReason;//   封停原因（攻击、网络安全组）	private String operatorHelp;//   是否请求运营商协助	private String operator;//   运营商	private Date stopDate;//   封停日期	private Date stopDatetime;//   封停时间	private String stopEnginer;//   封停工程师	private Date firstDate;//   最早可解封时间	private Date deblockingDate;//   解封日期	private Date deblockingDatetime;//   解封时间	private String deblockingEnginer;//   解封工程师	private String remark;//   备注	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getMachine() {	    return machine;	}	public void setMachine(String machine) {	    this.machine=machine;	}
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
		public String getStopEquipment() {	    return stopEquipment;	}	public void setStopEquipment(String stopEquipment) {	    this.stopEquipment=stopEquipment;	}	public String getIp() {	    return ip;	}	public void setIp(String ip) {	    this.ip=ip;	}	public String getStopType() {	    return stopType;	}	public void setStopType(String stopType) {	    this.stopType=stopType;	}	public String getStopReason() {	    return stopReason;	}	public void setStopReason(String stopReason) {	    this.stopReason=stopReason;	}	public String getOperatorHelp() {	    return operatorHelp;	}	public void setOperatorHelp(String operatorHelp) {	    this.operatorHelp=operatorHelp;	}	public String getOperator() {	    return operator;	}	public void setOperator(String operator) {	    this.operator=operator;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getStopDate() {	    return stopDate;	}	public void setStopDate(Date stopDate) {	    this.stopDate=stopDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getStopDatetime() {	    return stopDatetime;	}	public void setStopDatetime(Date stopDatetime) {	    this.stopDatetime=stopDatetime;	}	public String getStopEnginer() {	    return stopEnginer;	}	public void setStopEnginer(String stopEnginer) {	    this.stopEnginer=stopEnginer;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getFirstDate() {	    return firstDate;	}	public void setFirstDate(Date firstDate) {	    this.firstDate=firstDate;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getDeblockingDate() {	    return deblockingDate;	}	public void setDeblockingDate(Date deblockingDate) {	    this.deblockingDate=deblockingDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getDeblockingDatetime() {	    return deblockingDatetime;	}	public void setDeblockingDatetime(Date deblockingDatetime) {	    this.deblockingDatetime=deblockingDatetime;	}	public String getDeblockingEnginer() {	    return deblockingEnginer;	}	public void setDeblockingEnginer(String deblockingEnginer) {	    this.deblockingEnginer=deblockingEnginer;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}
