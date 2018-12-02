package com.jc.system.security.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.group.domain.GroupUser;
import com.jc.system.security.beans.UserBean;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-18
 * 
 */
public class Department extends BaseBean {

	private static final long serialVersionUID = 1L;

	private String code; 			/*部门编号*/
	private String name; 			/*部门名称*/
	private String deptDesc; 		/*部门描述*/
	private Long leaderId; 			/*部门主管领导ID*/
	private Integer chargeLeaderId; /*部门分管领导ID*/
	private Long parentId; 			/*上级节点Id*/
	private Integer managerDept; 	/*上级主管部门id*/
	private Integer deptType; 		/*标记是部门or机构节点  ’0‘--部门  ’1‘--机构*/
	private Long organizationId; 	/*所属机构ID*/
	private Integer queue; 			/*部门排序*/
	
	private String fullName;		/*组织全称*/
	private String shortName;		/*租户简称*/
	private String userName;		/*租户用户名*/
	private String password;		/*密码*/
	private String type;			/*0-试用 1-正式*/
	private String status;			/*组织状态  ’0‘--启用 ’1‘--禁用 ’2’--锁定 ‘3’--删除*/
	private Date openDay;			/*启用时间*/
	private Date endDay;			/*到期时间*/
	private BigDecimal fileSpace;	/*文件空间(m)*/
	private BigDecimal usedSpace;	/*已用空间*/
	private BigDecimal balance;		/*账户余额*/
	private BigDecimal smsBalance;	/*短信费用*/
	private String logo;			/*租户LOGO*/
	private String cont;			/*租户联系人*/
	private String telp;			/*联系人电话*/
	private String email;			/*用户邮箱*/
	private String memo;			/*租户备注*/
	
	private String displayName; 	/*负责人	自定义属性*/
	private String parentName; 		/*上级部门	自定义属性*/

	private List<UserBean> userBeanList = new ArrayList<UserBean>();/*部门下的人员	自定义属性*/
	private List<User> users = new ArrayList<User>();/*部门下的人员	自定义属性*/
	private List<Role> roles = new ArrayList<Role>();/*部门下的角色	自定义属性*/
	private List<GroupUser> groupUsers = new ArrayList<GroupUser>();/*组别下的人员	自定义属性*/
	
	private Long orgId;				/*机构ID	自定义属性*/
	private String orgName;			/*机构名称	自定义属性*/
	private String isChecked = "1";		/*是否有权限操作	自定义属性 1:有权操作,0:无权操作*/
	private int userType = 1; // 1超管 2机构管理员 3普通用户 4无意义
	
	private String deptIds;	/*根据部门ID集合行查询*/
	
	
	private String deptToken;	/*组织客户端token*/
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeptDesc() {
		return deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

	public Long getLeaderId() {
		return leaderId;
	}

	public void setLeaderId(Long leaderId) {
		this.leaderId = leaderId;
	}

	public Integer getChargeLeaderId() {
		return chargeLeaderId;
	}

	public void setChargeLeaderId(Integer chargeLeaderId) {
		this.chargeLeaderId = chargeLeaderId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getManagerDept() {
		return managerDept;
	}

	public void setManagerDept(Integer managerDept) {
		this.managerDept = managerDept;
	}

	public Integer getDeptType() {
		return deptType;
	}

	public void setDeptType(Integer deptType) {
		this.deptType = deptType;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Integer getQueue() {
		return queue;
	}

	public void setQueue(Integer queue) {
		this.queue = queue;
	}

	public List<User> getUsers() {
		return users;
	}

	public void addUser(User user) {
		users.add(user);
	}
	
	public void addUserBean(UserBean uBean) {
		userBeanList.add(uBean);
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void addRole(Role role) {
		roles.add(role);
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getOpenDay() {
		return openDay;
	}

	public void setOpenDay(Date openDay) {
		this.openDay = openDay;
	}

	public Date getEndDay() {
		return endDay;
	}

	public void setEndDay(Date endDay) {
		this.endDay = endDay;
	}

	public BigDecimal getFileSpace() {
		return fileSpace;
	}

	public void setFileSpace(BigDecimal fileSpace) {
		this.fileSpace = fileSpace;
	}

	public BigDecimal getUsedSpace() {
		return usedSpace;
	}

	public void setUsedSpace(BigDecimal usedSpace) {
		this.usedSpace = usedSpace;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getSmsBalance() {
		return smsBalance;
	}

	public void setSmsBalance(BigDecimal smsBalance) {
		this.smsBalance = smsBalance;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public String getTelp() {
		return telp;
	}

	public void setTelp(String telp) {
		this.telp = telp;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

	public String getDeptIds() {
		return deptIds;
	}

	public void setDeptIds(String deptIds) {
		this.deptIds = deptIds;
	}

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public List<GroupUser> getGroupUsers() {
		return groupUsers;
	}

	public void setGroupUsers(List<GroupUser> groupUsers) {
		this.groupUsers = groupUsers;
	}

	public String getDeptToken() {
		return deptToken;
	}

	public void setDeptToken(String deptToken) {
		this.deptToken = deptToken;
	}

	public List<UserBean> getUserBeanList() {
		return userBeanList;
	}

	public void setUserBeanList(List<UserBean> userBeanList) {
		this.userBeanList = userBeanList;
	}
	
}