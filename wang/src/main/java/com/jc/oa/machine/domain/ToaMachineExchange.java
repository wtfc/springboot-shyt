package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import java.util.Date;
/**
 * @author mrb
 * @version 工单附言表
*/
public class ToaMachineExchange extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String restartId;//   工单ID	private String name;//   姓名	private String startDate;//   留言时间	private String substance;//   内容	private Integer serviceName;//   业务表名	public String getRestartId() {	    return restartId;	}	public void setRestartId(String restartId) {	    this.restartId=restartId;	}	public String getName() {	    return name;	}	public void setName(String name) {	    this.name=name;	}	public String getStartDate() {	    return startDate;	}	public void setStartDate(String startDate) {	    this.startDate=startDate;	}	public String getSubstance() {	    return substance;	}	public void setSubstance(String substance) {	    this.substance=substance;	}
	public Integer getServiceName() {
		return serviceName;
	}
	public void setServiceName(Integer serviceName) {
		this.serviceName = serviceName;
	}
	}

