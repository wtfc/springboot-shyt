package com.jc.oa.project.domain;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

public class Monitors extends BaseBean{
	
	//定义对象缓存log
	protected transient final Logger log = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;
	private String name;//项目名称
	private Date startDate;//开始时间
	private Date endDate;//完成时间
	private String leared;//开发负责人
	private String people;//所需开发人员
	private String operate;//需求变更描述
	private Integer status;//项目阶段 0立项 1设计 2开发 3完
	/*附件Id*/
	private List<Long> fileids;
	private String delattachIds;//删除附件Id
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLeared() {
		return leared;
	}
	public void setLeared(String leared) {
		this.leared = leared;
	}
	public String getPeople() {
		return people;
	}
	public void setPeople(String people) {
		this.people = people;
	}
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public List<Long> getFileids() {
		return fileids;
	}
	public void setFileids(List<Long> fileids) {
		this.fileids = fileids;
	}
	public String getDelattachIds() {
		return delattachIds;
	}
	public void setDelattachIds(String delattachIds) {
		this.delattachIds = delattachIds;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}
