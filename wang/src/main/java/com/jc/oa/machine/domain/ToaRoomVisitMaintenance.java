package com.jc.oa.machine.domain;

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
public class ToaRoomVisitMaintenance extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String equipmentNumber;//   工单编号
	private String machina;//   机房
	private String caozgcsName;//   陪同人员姓名						for 机房参观.陪同人姓名		入室维护.机房接待人员姓名
	private Integer status;//   处理状态
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	@JsonSerialize(using = CustomDatetimeSerializer.class)
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
	}
