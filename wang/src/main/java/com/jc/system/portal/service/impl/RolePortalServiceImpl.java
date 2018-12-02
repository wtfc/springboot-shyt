package com.jc.system.portal.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.portal.dao.IRolePortalDao;
import com.jc.system.portal.domain.RolePortal;
import com.jc.system.portal.service.IRolePortalService;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.LeftRight;
import com.jc.system.security.domain.Role;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.IRoleService;
import com.jc.system.security.service.IUserService;

/**
 * @title GOA2.0
 * @author
 * @version 2014-06-16
 */
@Service
public class RolePortalServiceImpl extends BaseServiceImpl<RolePortal>
		implements IRolePortalService {

	private IRolePortalDao rolePortalDao; 

	@Autowired
	private IDepartmentService departmentService; // 部门服务

	@Autowired
	private IUserService userService; // 用户服务

	@Autowired
	private IRoleService roleService;

	public RolePortalServiceImpl() {
	}

	@Autowired
	public RolePortalServiceImpl(IRolePortalDao rolePortalDao) {
		super(rolePortalDao);
		this.rolePortalDao = rolePortalDao;
	}

	@Override
	public List<LeftRight> getData(String type) throws Exception{
		List<LeftRight> result = new ArrayList<>();
		if("role".equals(type)){
			Role role = new Role();
			List<Role> list = roleService.queryAll(role);
			Iterator<Role> iterator = list.iterator();
			while(iterator.hasNext()){
				Role roleItem = iterator.next();
				LeftRight item = new LeftRight();
				item.setCode(String.valueOf(roleItem.getId()));
				item.setText(roleItem.getName());
				result.add(item);
			}
		}
		if("dept".equals(type)){
			Department department = new Department();
			department.setDeptType(0);		
			List<Department> list = departmentService.queryAll(department);
			Iterator<Department> iterator = list.iterator();
			while(iterator.hasNext()){
				Department deptItem = iterator.next();
				LeftRight item = new LeftRight();
				item.setCode(String.valueOf(deptItem.getId()));
				item.setText(deptItem.getName());
				result.add(item);
			}
		}
		if("user".equals(type)){
			User user = new User();			
			List<User> list = userService.queryAll(user);
			Iterator<User> iterator = list.iterator();
			while(iterator.hasNext()){
				User userItem = iterator.next();
				LeftRight item = new LeftRight();
				item.setCode(String.valueOf(userItem.getId()));
				item.setText(userItem.getDisplayName());
				result.add(item);
			}
		}
		if("organ".equals(type)){
			Department department = new Department();
			department.setDeptType(1);
			List<Department> list =departmentService.queryAll(department);
			Iterator<Department> iterator = list.iterator();
			while(iterator.hasNext()){
				Department deptItem = iterator.next();
				LeftRight item = new LeftRight();
				item.setCode(String.valueOf(deptItem.getId()));
				item.setText(deptItem.getName());
				result.add(item);
			}
		}
		return result;
	}

	@Override
	public List<RolePortal> parseToList(RolePortal rolePortal,String userids, String roleids,
			String deptids, String organids) throws Exception {
		// TODO Auto-generated method stub
		List<RolePortal> list = new ArrayList<RolePortal>();
		String[] user = userids.split(",");
		Long portalId = rolePortal.getPortalId();
		Long portaletId = rolePortal.getPortaletId();
		for(int i=0;i<user.length;i++){
			RolePortal item = new RolePortal();
			if(user[i]==null||"".equals(user[i]))
				continue;
			item.setPortalId(portalId);
			item.setPortaletId(portaletId);
			item.setUserId(Long.parseLong(user[i]));
			list.add(item);
		}
		String[] role = roleids.split(",");
		for(int i=0;i<role.length;i++){
			RolePortal item = new RolePortal();
			if(role[i]==null||"".equals(role[i]))
				continue;
			item.setPortaletId(portaletId);
			item.setPortalId(portalId);
			item.setRoleId(Long.parseLong(role[i]));
			list.add(item);
		}
		String[] dept = deptids.split(",");
		for(int i=0;i<dept.length;i++){
			RolePortal item = new RolePortal();
			if(dept[i]==null||"".equals(dept[i]))
				continue;
			item.setPortalId(portalId);
			item.setPortaletId(portaletId);
			item.setDeptId(Long.parseLong(dept[i]));
			list.add(item);
		}
		String[] organ = organids.split(",");
		for(int i=0;i<organ.length;i++){
			RolePortal item = new RolePortal();
			if(organ[i]==null||"".equals(organ[i]))
				continue;
			item.setPortalId(portalId);
			item.setPortaletId(portaletId);
			item.setOrganId(Long.parseLong(organ[i]));
			list.add(item);
		}
		return list;
	}

	@Override
	public Map<String, String> getIds(RolePortal rolePortal) throws Exception {
		List<RolePortal> list = rolePortalDao.queryAll(rolePortal);
		Map<String, String> result = new HashMap<String, String>();
		String userids="";
		String roleids="";
		String deptids="";
		String organids="";
		Iterator<RolePortal> iterator = list.iterator();
		while(iterator.hasNext()){
			RolePortal rPortal = iterator.next();
			String userid = String.valueOf(rPortal.getUserId());
			if(!(userid==null||"".equals(String.valueOf(userid))||"null".equals(String.valueOf(userid)))){
				if("".equals(userids))
					userids = userid;
				else
					userids+=","+userid;
			}
			String roleid = String.valueOf(rPortal.getRoleId());
			if(!(roleid==null||"".equals(String.valueOf(roleid))||"null".equals(String.valueOf(roleid)))){
				if("".equals(roleids))
					roleids = roleid;
				else
					roleids+=","+roleid;
			}
			String deptid = String.valueOf(rPortal.getDeptId());
			if(!(deptid==null||"".equals(String.valueOf(deptid))||"null".equals(String.valueOf(deptid)))){
				if("".equals(deptids))
					deptids = deptid;
				else
					deptids+=","+deptid;
			}
			String organid = String.valueOf(rPortal.getOrganId());
			if(!(organid==null||"".equals(String.valueOf(organid))||"null".equals(String.valueOf(organid)))){
				if("".equals(organids))
					organids = organid;
				else
					organids+=","+organid;
			}
		}
		result.put("userids",userids);
		result.put("roleids",roleids);
		result.put("deptids",deptids);
		result.put("organids",organids);
		return result;
	}
	
	public Long getCount(RolePortal rolePortal){
		return rolePortalDao.getCount(rolePortal);
	}

	@Override
	public Integer deleteRolePortalItem(RolePortal rolePortal) throws Exception {
		// TODO Auto-generated method stub
		return rolePortalDao.deleteRolePortalItem(rolePortal);
	}

	@Override
	public List<RolePortal> queryPortaletPower(RolePortal rolePortal) throws Exception  {
		// TODO 自动生成的方法存根
		return rolePortalDao.queryPortaletPower(rolePortal);
	}

	@Override
	public Integer deleteForPortalsOrPortalets(RolePortal rolePortal)
			throws Exception {
		// TODO 自动生成的方法存根
		return rolePortalDao.deleteForPortalsOrPortalets(rolePortal);
	}
	
	
}