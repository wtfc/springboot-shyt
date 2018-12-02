package com.jc.shjfgl.machine.domain;

import com.jc.foundation.domain.BaseBean;
import java.util.Date;
/**
 * @author mrb
 * @version 工单设备关联表
*/
public class ToaMachineDevice extends BaseBean {
	
		private long machineId;//   工单id	private long equipmentId;//   设备id	private String machineType;//   工单类型	private String machineName;//   工单表名	public long getMachineId() {	    return machineId;	}	public void setMachineId(long machineId) {	    this.machineId=machineId;	}	public long getEquipmentId() {	    return equipmentId;	}	public void setEquipmentId(long equipmentId) {	    this.equipmentId=equipmentId;	}	public String getMachineType() {	    return machineType;	}	public void setMachineType(String machineType) {	    this.machineType=machineType;	}	public String getMachineName() {	    return machineName;	}	public void setMachineName(String machineName) {	    this.machineName=machineName;	}}

