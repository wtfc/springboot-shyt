package com.jc.shjfgl.machine.domain;

import org.apache.log4j.Logger;
import com.jc.foundation.domain.BaseBean;

public class Exchange extends BaseBean{

	protected transient final Logger log = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	
	private String equipmentId;//工单ID
	private String name;//姓名
	private String content;//内容
	private String startDate;
	public String getEquipmentId() {
		return equipmentId;
	}
	public void setEquipmentId(String equipmentId) {
		this.equipmentId = equipmentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
}
