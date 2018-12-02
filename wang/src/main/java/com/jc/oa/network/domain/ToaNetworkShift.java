package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 交接班记录表
*/
public class ToaNetworkShift extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyName;//   客户名称	private Integer companyId;//   客户ID	private String phone;//   联系方式	private String executor;//   执行人	private String connectPeople;//   交接人	private Date shiftDate;//   交接日期	private Date finishDate;//   预计完成时间	private String remark;//   描述
	private List<Long> fileids;
	private String delattachIds;//删除附件Id	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getPhone() {	    return phone;	}	public void setPhone(String phone) {	    this.phone=phone;	}	public String getExecutor() {	    return executor;	}	public void setExecutor(String executor) {	    this.executor=executor;	}	public String getConnectPeople() {	    return connectPeople;	}	public void setConnectPeople(String connectPeople) {	    this.connectPeople=connectPeople;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getShiftDate() {	    return shiftDate;	}	public void setShiftDate(Date shiftDate) {	    this.shiftDate=shiftDate;	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)	public Date getFinishDate() {	    return finishDate;	}	public void setFinishDate(Date finishDate) {	    this.finishDate=finishDate;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}
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
	}}

