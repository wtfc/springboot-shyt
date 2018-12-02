package com.jc.shjfgl.machine.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;
/**
 * @author mrb
 * @version 设备上下架和迁移
*/
public class ToaEquipmentUpDown extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String equipmentNumber;//   工单编号
	public String getEquipmentNumber() {
	public String getOperationTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("operateType", this.getOperationType()) == null ? "" : dicManager.getDic("operateType", this.getOperationType()).getValue();
	}
	public String getMachinaValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachina()) == null ? "" : dicManager.getDic("room", this.getMachina()).getValue();
	}
