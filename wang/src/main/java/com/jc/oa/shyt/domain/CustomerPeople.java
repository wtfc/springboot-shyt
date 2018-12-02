package com.jc.oa.shyt.domain;

import com.jc.foundation.domain.BaseBean;
/**
 * @author mrb
 * @version 2016-2-1
 * 客户联系信息实体类
 * */
public class CustomerPeople extends BaseBean{
	private static final long serialVersionUID = 1L;
	private String companyName;//公司名称
	private Integer customerId;//客户表id
	private String job;//职务
	private String name;//联系人
	private String email;//邮箱
	private String weixin;//微信/qq
	private String tel;//联系方式
	private String idCard;//身份证
	private String likee;//个人喜好
	private String leaveJob;//离职去向
	private String remark;//备注
	
	//新增字段
	private String placeOrigin;//   籍贯
	private String marriagePeople;//   婚否
	private String childrenSituation;//   子女情况
	private String departmentPeople;//   部门
	private String customerYear;//   客户入职年份
	private String decisionMaking;//   是否有决策采购权
	
	public String getPlaceOrigin() {
		return placeOrigin;
	}
	public void setPlaceOrigin(String placeOrigin) {
		this.placeOrigin = placeOrigin;
	}
	public String getMarriagePeople() {
		return marriagePeople;
	}
	public void setMarriagePeople(String marriagePeople) {
		this.marriagePeople = marriagePeople;
	}
	public String getChildrenSituation() {
		return childrenSituation;
	}
	public void setChildrenSituation(String childrenSituation) {
		this.childrenSituation = childrenSituation;
	}
	public String getDepartmentPeople() {
		return departmentPeople;
	}
	public void setDepartmentPeople(String departmentPeople) {
		this.departmentPeople = departmentPeople;
	}
	public String getCustomerYear() {
		return customerYear;
	}
	public void setCustomerYear(String customerYear) {
		this.customerYear = customerYear;
	}
	public String getDecisionMaking() {
		return decisionMaking;
	}
	public void setDecisionMaking(String decisionMaking) {
		this.decisionMaking = decisionMaking;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public Integer getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getLeaveJob() {
		return leaveJob;
	}
	public void setLeaveJob(String leaveJob) {
		this.leaveJob = leaveJob;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getLikee() {
		return likee;
	}
	public void setLikee(String likee) {
		this.likee = likee;
	}
	
}
