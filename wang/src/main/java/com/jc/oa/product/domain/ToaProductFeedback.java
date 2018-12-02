package com.jc.oa.product.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 云主机测试反馈
*/
public class ToaProductFeedback extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private Integer companyId;//   客户编码	private String companyName;//   客户名称	private String salePeople;//   销售	private String cooperateType;//   测试平台	private Date finishDate;//   收回时间	private String appraisal;//   客户评价	private String remark;//   备注	public Integer getCompanyId() {	    return companyId;	}	public void setCompanyId(Integer companyId) {	    this.companyId=companyId;	}	public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public String getSalePeople() {	    return salePeople;	}	public void setSalePeople(String salePeople) {	    this.salePeople=salePeople;	}	public String getCooperateType() {	    return cooperateType;	}	public void setCooperateType(String cooperateType) {	    this.cooperateType=cooperateType;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getFinishDate() {	    return finishDate;	}	public void setFinishDate(Date finishDate) {	    this.finishDate=finishDate;	}	public String getAppraisal() {	    return appraisal;	}	public void setAppraisal(String appraisal) {	    this.appraisal=appraisal;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

