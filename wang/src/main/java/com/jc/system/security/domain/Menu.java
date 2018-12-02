package com.jc.system.security.domain;

import java.util.List;

import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description  实体类
 * @author 
 * @version  2014-04-17
 */

public class Menu extends BaseBean{
	private static final long serialVersionUID = 1L;
   /*主键id*/
	private String name;   /*菜单名称*/
	private Long parentId;   /*上级菜单ID*/
	private String icon;   /*图标类型名称*/
	private Integer menuType;   /*0功能菜单  1权限控制菜单*/
	private String actionName;   /*对应action名称*/
	private Integer queue;   /*排序*/

	//2014.4.17 chz 生成新代码覆盖原型，由于SystemAuthorizingRealm.java内方法doGetAuthorizationInfo出错引入---start--
	private String permission; //2014.4.22 chz 确认为新增字段 /*按钮认证字段*/
	private Integer isShow;	//2014.4.22 chz 确认为新增字段 /*是否显示 */ null全部数据 0显示 1不显示 
	//2014.4.17 chz ---end---
	//2014.4.22 chz 增加临时显示字段 parentname 解决资源添加显示上级菜单名称问题 ---start
	private String parentname; /*父菜单名称字段*/
	private String isNextNode; /*是否存在下级节点*/ //isNextNode,rootNode 解决资源管理 树菜单数据加载刷新问题
	private String rootNode; /*父节点的上级节点*/
	private Long userId; /*登录用户id*/
	//2014.4.22 chz 增加临时显示字段 parentname 解决资源添加显示上级菜单名称问题 ---end
	//2014.7.17 chz	增加临时存储下级菜单列表字段----start---
	private List<Menu> childmenus;
	private Integer childmenussize;
	//2014.7.17 chz	增加临时存储下级菜单列表字段----end---

	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	public Long getParentId(){
		return parentId;
	}
	
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}
	public String getIcon(){
		return icon;
	}
	
	public void setIcon(String icon){
		this.icon = icon;
	}
	public Integer getMenuType(){
		return menuType;
	}
	
	public void setMenuType(Integer menuType){
		this.menuType = menuType;
	}
	public String getActionName(){
		return actionName;
	}
	
	public void setActionName(String actionName){
		this.actionName = actionName;
	}
	public Integer getQueue(){
		return queue;
	}
	
	public void setQueue(Integer queue){
		this.queue = queue;
	}
	
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public Integer getIsShow() {
		return isShow;
	}

	public void setIsShow(Integer isShow) {
		this.isShow = isShow;
	}
	
	public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}
	
	public String getIsNextNode() {
		return isNextNode;
	}

	public void setIsNextNode(String isNextNode) {
		this.isNextNode = isNextNode;
	}
	
	public String getRootNode() {
		return rootNode;
	}

	public void setRootNode(String rootNode) {
		this.rootNode = rootNode;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<Menu> getChildmenus() {
		return childmenus;
	}

	public void setChildmenus(List<Menu> childmenus) {
		this.childmenus = childmenus;
	}

	public Integer getChildmenussize() {
		return childmenussize;
	}

	public void setChildmenussize(Integer childmenussize) {
		this.childmenussize = childmenussize;
	}

	
}