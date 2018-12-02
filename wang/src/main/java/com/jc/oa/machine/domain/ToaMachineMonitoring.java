package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 监控报告通告
*/
public class ToaMachineMonitoring extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String faultNumber;//   工单编号	private String companyName;//   客户名称	private Integer companyId;//   客户ID	private String machina;//   所属机房	private Date startDate;//   开始时间	private Date finishDate;//   恢复时间	private String type;//   故障类型	private String typeTwo;//   具体类型（2）	private String faultReason;//   故障原因	private String overflow;//   超流量	private String inflow;//   流入流量	private String outflow;//   流出流量	private String fazhi;//   阀值	private String overfazhi;//   超出阀值	private String remark;//   备注	private Integer status;//   处理状态	public String getFaultNumber() {	    return faultNumber;	}	public void setFaultNumber(String faultNumber) {	    this.faultNumber=faultNumber;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getMachina() {	    return machina;	}	public void setMachina(String machina) {	    this.machina=machina;	}
	
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getStartDate() {	    return startDate;	}	public void setStartDate(Date startDate) {	    this.startDate=startDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getFinishDate() {	    return finishDate;	}	public void setFinishDate(Date finishDate) {	    this.finishDate=finishDate;	}	public String getType() {	    return type;	}	public void setType(String type) {	    this.type=type;	}	public String getTypeTwo() {	    return typeTwo;	}	public void setTypeTwo(String typeTwo) {	    this.typeTwo=typeTwo;	}	public String getFaultReason() {	    return faultReason;	}	public void setFaultReason(String faultReason) {	    this.faultReason=faultReason;	}	public String getOverflow() {	    return overflow;	}	public void setOverflow(String overflow) {	    this.overflow=overflow;	}	public String getInflow() {	    return inflow;	}	public void setInflow(String inflow) {	    this.inflow=inflow;	}	public String getOutflow() {	    return outflow;	}	public void setOutflow(String outflow) {	    this.outflow=outflow;	}	public String getFazhi() {	    return fazhi;	}	public void setFazhi(String fazhi) {	    this.fazhi=fazhi;	}	public String getOverfazhi() {	    return overfazhi;	}	public void setOverfazhi(String overfazhi) {	    this.overfazhi=overfazhi;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}	public Integer getStatus() {	    return status;	}	public void setStatus(Integer status) {	    this.status=status;	}}

