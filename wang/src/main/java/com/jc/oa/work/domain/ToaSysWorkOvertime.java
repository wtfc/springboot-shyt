package com.jc.oa.work.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 人员加班信息表
*/
public class ToaSysWorkOvertime extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String name;//   姓名	private Date startTime;//   加班开始时间	private Date endTime;//   加班结束时间	private String workHour;//   加班小时数	private String workContent;//   加班内容	private String remark;//   备注
	public String getName() {	    return name;	}	public void setName(String name) {	    this.name=name;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getStartTime() {	    return startTime;	}	public void setStartTime(Date startTime) {	    this.startTime=startTime;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getEndTime() {	    return endTime;	}	public void setEndTime(Date endTime) {	    this.endTime=endTime;	}	public String getWorkHour() {	    return workHour;	}	public void setWorkHour(String workHour) {	    this.workHour=workHour;	}	public String getWorkContent() {	    return workContent;	}	public void setWorkContent(String workContent) {	    this.workContent=workContent;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

