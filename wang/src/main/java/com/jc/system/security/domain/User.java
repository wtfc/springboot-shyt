package com.jc.system.security.domain;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;
import com.jc.system.security.util.DeptCacheUtil;
import com.jc.system.security.util.UserUtils;
import com.jc.foundation.util.CustomDateSerializer;

/**
 * @title GOA2.0
 * @version 2014-03-18
 * 
 */
public class User extends BaseBean{
	private static final long serialVersionUID = 1L;
	
	private String code; /**/
	private String displayName; /**/
	private String loginName; /**/
	private String password; /**/
	private String newPassword; /**/
	private String sex; /**/
	private String kind; /**/
	private String dutyId; /**/
	private String level; /**/
	private Long deptId; /**/
	private String status; /**/
	private Date entryDate; /**/
	private Long leaderId; /**/
	private Long deptLeader; /**/
	private Long chargeLeader; /**/
	private String political; /**/
	private String cardNo; /**/
	private Integer weight; /**/
	private Integer orderNo; /**/
	private String jobTitle; /**/
	private String photo;
	private String mobile; /**/
	private String officeTel; /**/
	private String email; /**/
	private Date entryPoliticalDate; /**/
	private Date birthday; /**/
	private String ethnic; /**/
	private String hometown; /**/
	private String degree; /**/
	private String cername; /**/
	private String maritalStatus; /**/
	private Integer isAdmin; /**/
	private String payCardNo; /**/
	private String cardBank; /**/
	private String cardName; /**/
	private Date lastLonginDate; /**/
	private Integer wrongCount; /**/
	private Date lockStartDate; /**/
	private String officeAddress; /**/
	private Integer isDriver; /**/
	private Integer isLeader; /**/
	private String answer; /**/
	private String question; /**/
	private String openCale;
	private String isCheck;
	private Integer lockType;
	private String groupTel;
	private int modifyPwdFlag;
	private String theme;
	
	//机构ID
	private Long orgId;
	//机构名称
	private String orgName;
	// 部门查询条件
	private String deptIds;
	private String deptName;
	
	//其他部门职位
	private List<SysOtherDepts> otherDepts;
	//角色
	private List<SysUserRole> sysUserRole;
	//管理员部门
	private List<AdminSide> adminSide;
	
	//用户树属性
	private String name;
	private Long parentId;
	
	private int rowNo;

	//判断是否是超级管理员
	public boolean getIsSystemAdmin(){
		if(this.loginName.equals(GlobalContext.ADMIN_NAME)){
//			User user = SystemSecurityUtils.getUser();
//			if(user.getLoginName().equals(GlobalContext.ADMIN_NAME)){
//				return false;
//			} else {
//				return true;
//			}
			return true;
		} else {
			return false;
		}
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}
	
	public String getSexValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("sex", this.getSex()) == null ? "" : dicManager.getDic("sex", this.getSex()).getValue();
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getKind() {
		return kind;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public String getDutyId() {
		return dutyId;
	}
	
	public String getDutyIdValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("dutyId", this.getDutyId()) == null ? "" : dicManager.getDic("dutyId", this.getDutyId()).getValue();
	}

	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getLevelValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("level", level) == null ? "" : dicManager.getDic("level", level).getValue();
	}
	
	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getStatus() {
		return status;
	}
	
	public String getStatusValue() {
		return this.getStatus() == null ? "" : this.status.equals("status_0") ? "启用" : this.status.equals("status_1") ? "禁用" : this.status.equals("status_2") ? "锁定" : this.status.equals("status_3") ? "删除" : "";
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEntryDate() {
		return entryDate;
	}

	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}

	public Long getLeaderId() {
		return leaderId;
	}
	
	public String getLeaderIdValue() {
		if(leaderId != null && leaderId > 0){
			return UserUtils.getUser(leaderId).getDisplayName();
		} else {
			return "";
		}
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public Long getDeptLeader() {
		return deptLeader;
	}
	
	public String getDeptLeaderValue() {
		if(deptLeader != null && deptLeader > 0){
			return UserUtils.getUser(deptLeader).getDisplayName();
		} else {
			return "";
		}
	}

	public void setDeptLeader(Long deptLeader) {
		this.deptLeader = deptLeader;
	}

	public Long getChargeLeader() {
		return chargeLeader;
	}
	
	public String getChargeLeaderValue() {
		if(chargeLeader!= null && chargeLeader > 0){
			return UserUtils.getUser(chargeLeader).getDisplayName();
		} else {
			return "";
		}
	}

	public void setChargeLeader(Long chargeLeader) {
		this.chargeLeader = chargeLeader;
	}

	public String getPolitical() {
		return political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getJobTitle() {
		return jobTitle;
	}
	
	public String getJobTitleValue(){
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("jobTitle", jobTitle) == null ? "" : dicManager.getDic("jobTitle", jobTitle).getValue();
	}

	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOfficeTel() {
		return officeTel;
	}

	public void setOfficeTel(String officeTel) {
		this.officeTel = officeTel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEntryPoliticalDate() {
		return entryPoliticalDate;
	}

	public void setEntryPoliticalDate(Date entryPoliticalDate) {
		this.entryPoliticalDate = entryPoliticalDate;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getEthnic() {
		return ethnic;
	}

	public void setEthnic(String ethnic) {
		this.ethnic = ethnic;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getDegree() {
		return degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	public String getCername() {
		return cername;
	}

	public void setCername(String cername) {
		this.cername = cername;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public Integer getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Integer isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPayCardNo() {
		return payCardNo;
	}

	public void setPayCardNo(String payCardNo) {
		this.payCardNo = payCardNo;
	}

	public String getCardBank() {
		return cardBank;
	}

	public void setCardBank(String cardBank) {
		this.cardBank = cardBank;
	}

	public String getCardName() {
		return cardName;
	}

	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLastLonginDate() {
		return lastLonginDate;
	}

	public void setLastLonginDate(Date lastLonginDate) {
		this.lastLonginDate = lastLonginDate;
	}

	public Integer getWrongCount() {
		return wrongCount;
	}

	public void setWrongCount(Integer wrongCount) {
		this.wrongCount = wrongCount;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLockStartDate() {
		return lockStartDate;
	}

	public void setLockStartDate(Date lockStartDate) {
		this.lockStartDate = lockStartDate;
	}

	public String getOfficeAddress() {
		return officeAddress;
	}

	public void setOfficeAddress(String officeAddress) {
		this.officeAddress = officeAddress;
	}

	public Integer getIsDriver() {
		return isDriver;
	}

	public void setIsDriver(Integer isDriver) {
		this.isDriver = isDriver;
	}

	public Integer getIsLeader() {
		return isLeader;
	}

	public void setIsLeader(Integer isLeader) {
		this.isLeader = isLeader;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public String getDeptName() {
		if(getDeptId() == null){
			return deptName;
		}
		try {
			Department department = DeptCacheUtil.getDeptById(getDeptId());
			if(department != null){
				return department.getName();
			} else {
				return "";
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getOpenCale() {
		return openCale;
	}

	public void setOpenCale(String openCale) {
		this.openCale = openCale;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public List<SysUserRole> getSysUserRole() {
		return sysUserRole;
	}

	public void setSysUserRole(List<SysUserRole> sysUserRole) {
		this.sysUserRole = sysUserRole;
	}

	public List<AdminSide> getAdminSide() {
		return adminSide;
	}

	public void setAdminSide(List<AdminSide> adminSide) {
		this.adminSide = adminSide;
	}

	public List<SysOtherDepts> getOtherDepts() {
		return otherDepts;
	}

	public void setOtherDepts(List<SysOtherDepts> otherDepts) {
		this.otherDepts = otherDepts;
	}

	public Long getOrgId() {
		if(getDeptId() == null){
			return orgId;
		}
		try {
			Department department = DeptCacheUtil.getDeptById(getDeptId());
			if(department != null){
				return department.getOrgId();
			} else {
				return -1L;
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		if(getDeptId() == null){
			return orgName;
		}
		try {
			Department department = DeptCacheUtil.getDeptById(getDeptId());
			if(department != null){
				return department.getOrgName();
			} else {
				return "";
			}
		} catch (CustomException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getLockType() {
		return lockType;
	}

	public void setLockType(Integer lockType) {
		this.lockType = lockType;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGroupTel() {
		return groupTel;
	}

	public void setGroupTel(String groupTel) {
		this.groupTel = groupTel;
	}

	public int getModifyPwdFlag() {
		return modifyPwdFlag;
	}

	public void setModifyPwdFlag(int modifyPwdFlag) {
		this.modifyPwdFlag = modifyPwdFlag;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public int getRowNo() {
		return rowNo;
	}

	public void setRowNo(int rowNo) {
		this.rowNo = rowNo;
	}
	
}