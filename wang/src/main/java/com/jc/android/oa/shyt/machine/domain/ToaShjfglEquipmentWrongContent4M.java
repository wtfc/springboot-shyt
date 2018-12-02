package com.jc.android.oa.shyt.machine.domain;

import com.jc.foundation.domain.BaseBean;

import java.util.Date;
/**
 * @author mrb
 * @version 故障处理过程表
*/
public class ToaShjfglEquipmentWrongContent4M extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private Integer wrongId;//   故障ID
	private String operationType;//   故障类型（17、设备故障 18、网络故障）
	private String operateAndResult;//   处理过程及结果
	private Integer contentType;//   内容类型（0、机房提供、1、用户提供）
	private String caozgcs;//   提交人ID
	private String caozgcsName;//   提交人姓名
	private String remark;//   备注
	public Integer getWrongId() {	    return wrongId;	}	public void setWrongId(Integer wrongId) {	    this.wrongId=wrongId;	}	public String getOperateAndResult() {	    return operateAndResult;	}	public void setOperateAndResult(String operateAndResult) {	    this.operateAndResult=operateAndResult;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}
	public Integer getContentType() {
		return contentType;
	}
	public void setContentType(Integer contentType) {
		this.contentType = contentType;
	}
	public String getCaozgcs() {
		return caozgcs;
	}
	public void setCaozgcs(String caozgcs) {
		this.caozgcs = caozgcs;
	}
	public String getCaozgcsName() {
		return caozgcsName;
	}
	public void setCaozgcsName(String caozgcsName) {
		this.caozgcsName = caozgcsName;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}}

