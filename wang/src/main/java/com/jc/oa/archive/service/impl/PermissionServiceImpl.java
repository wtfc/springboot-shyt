package com.jc.oa.archive.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.dao.IPermissionDao;
import com.jc.oa.archive.domain.Permission;
import com.jc.oa.archive.domain.SubPermission;
import com.jc.oa.archive.service.IPermissionService;
import com.jc.oa.archive.service.ISubPermissionService;
import com.jc.system.CustomException;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.util.UserUtils;

/**
 * @title  GOA2.0源代码
 * @description  业务服务类
 * @author 闻瑜
 * @version  2014-06-19
 */
@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission> implements IPermissionService{

	private IPermissionDao permissionDao;
	
	@Autowired
	private ISubPermissionService subPermissionService;
	
	public PermissionServiceImpl(){}
	
	@Autowired
	public PermissionServiceImpl(IPermissionDao permissionDao){
		super(permissionDao);
		this.permissionDao = permissionDao;
	}

	/**
	 * 查询授权权限初始化
	 * @param Permission permission 实体类
	 * @param PageManager page 分页实体类
	 * @return PageManager 查询结果
	 * @throws ArchiveException
	 * @author
	 * @version  2014-06-19
	 */
	@Override
	public PageManager manageListPermission(Permission permission,PageManager page) throws ArchiveException {
		PageManager manager = this.query(permission, page);
		if(manager ==null || manager.getAaData() == null || manager.getAaData().size()<1){
			return manager;
		}
		for(int i=0;i<manager.getAaData().size();i++){
			Permission ager = (Permission) manager.getAaData().get(i);
			SubPermission ssion = new SubPermission();
			ssion.setPermissionId(ager.getId());
			List<SubPermission> list;
			ager.setPermView(ager.getPermissionValue().substring(0,1));
			ager.setPermNewUpDown(ager.getPermissionValue().substring(1,2));
			ager.setPermEdit(ager.getPermissionValue().substring(2,3));
			ager.setPermDelete(ager.getPermissionValue().substring(3,4));
			ager.setPermCopyPaste(ager.getPermissionValue().substring(4,5));
			ager.setPermRename(ager.getPermissionValue().substring(5,6));
			ager.setPermCollect(ager.getPermissionValue().substring(6,7));
			ager.setPermVersion(ager.getPermissionValue().substring(7,8));
			ager.setPermHistory(ager.getPermissionValue().substring(8,9));
			ager.setPermRelate(ager.getPermissionValue().substring(9,10));
			try {
				list = subPermissionService.queryAll(ssion);
				String userName="";
				for(int j=0;j<list.size();j++){
					SubPermission subPermission = list.get(j);
					userName +=subPermission.getControlName()+",";
				}
				if(userName.length() > 1) {
					ager.setUserName(userName.substring(0,userName.length()-1));
				} else {
					ager.setUserName("");
				}
			} catch (CustomException e) {
				e.printStackTrace();
				ArchiveException ae = new ArchiveException();
				ae.setLogMsg(MessageUtils.getMessage("JC_SYS_060"));
				throw ae;
			}
		}
		return manager;
	}
	
	/**
	 * 物理删除权限
	 * @param Permission permission 实体类
	 * @author weny
	 * @version  2014-06-19
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer delectPermission(Permission permission) {
		SubPermission subpermission = new SubPermission();
		subpermission.setPermissionId(permission.getId());
		subPermissionService.deleteSubPermission(subpermission);
		Integer count = permissionDao.deletePermission(permission);
		return count;
	}
	
	/**
	 * 添加组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 * @throws ArchiveException 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer batchSave(Long folderId, String id, String text, String type) throws ArchiveException {
		Integer count=0;
		try {
			Permission permission = new Permission();
			permission.setFolderId(folderId);
			permission.setPermissionValue("1111000000");
			permission.setDeleteFlag(0);
			permission.setWeight(0);
			permission.setCreateDate(new Date());
			permission.setCreateUser(SystemSecurityUtils.getUser().getId());
			permission.setCreateUserDept(UserUtils.getUser(SystemSecurityUtils.getUser().getId()).getDeptId());
			permission.setCreateUserOrg(UserUtils.getUser(SystemSecurityUtils.getUser().getId()).getOrgId());
			count = this.save(permission);
			String[] controlId=id.substring(0, id.length()-1).split(",");
			String[] controlName=text.substring(0, text.length()-1).split(",");
			String[] dataType=type.substring(0, type.length()-1).split(",");
			for(int i=0;i<controlId.length;i++){
				SubPermission subPermission = new SubPermission();
				subPermission.setPermissionId(permission.getId());
				subPermission.setControlId(Long.valueOf(controlId[i]));
				subPermission.setControlName(controlName[i]);
				subPermission.setDataType(Integer.valueOf(dataType[i]));
				subPermissionService.save(subPermission);
			}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_060"));
			throw ae;
		}
		return count;
	}
	
	/**
	 * 修改组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 * @throws ArchiveException 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<SubPermission> updatePermission(Long permissionId) throws ArchiveException {
		SubPermission subPermission = new SubPermission();
		subPermission.setPermissionId(permissionId);
		return subPermissionService.updatePermission(subPermission);
	}
	
	/**
	 * 修改组织人员权限方法
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-20
	 * @throws ArchiveException 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateBatchPermission(Long permissionId, String id, String text, String type) throws ArchiveException {
		Integer count=0;
		SubPermission sub = new SubPermission();
		sub.setPermissionId(permissionId);
		count = subPermissionService.deleteSubPermission(sub);
		String[] controlId=id.substring(0, id.length()-1).split(",");
		String[] controlName=text.substring(0, text.length()-1).split(",");
		String[] dataType=type.substring(0, type.length()-1).split(",");
		for(int i=0;i<controlId.length;i++){
			SubPermission subPermission = new SubPermission();
			subPermission.setPermissionId(permissionId);
			subPermission.setControlId(Long.valueOf(controlId[i]));
			subPermission.setControlName(controlName[i]);
			subPermission.setDataType(Integer.valueOf(dataType[i]));
			try {
				subPermissionService.save(subPermission);
			} catch (CustomException e) {
				e.printStackTrace();
				ArchiveException ae = new ArchiveException();
				ae.setLogMsg(MessageUtils.getMessage("JC_SYS_060"));
				throw ae;
			}
		}
		return count;
	}
	
	/**
	 * 权限查询
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-06-23
	 * @throws ArchiveException 
	 */
	@Override
	public List<Permission> queryPermission(Permission permission) throws ArchiveException {
		return permissionDao.queryPermission(permission);
	}
	
	/**
	 * 复制权限给下级文件夹
	 * @param Permission permission 实体类
	 * @throws Exception
	 * @author 闻瑜
	 * @version  2014-07-03
	 * @throws ArchiveException 
	 */
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public void copyPermission(Long id, Long folderId) throws ArchiveException {
		SubPermission sub = null;
		Permission permission = new Permission();
		try {
			permission.setFolderId(id);
			List<Permission> listPermission = permissionDao.queryAll(permission);
			if(listPermission != null && listPermission.size()>0){
				for(int i=0;i<listPermission.size();i++){
					sub = new SubPermission();
					Permission per = listPermission.get(i);
					sub.setPermissionId(per.getId());
					List<SubPermission> listSubPermission = subPermissionService.queryAll(sub);
					per.setFolderId(folderId);
					per.setId(null);
					permissionDao.save(per);
					for(int j=0;j<listSubPermission.size();j++){
						SubPermission subPermission = listSubPermission.get(j);
						subPermission.setPermissionId(per.getId());
						subPermission.setId(null);
					}
					subPermissionService.saveList(listSubPermission);
				}
			}
		} catch (CustomException e) {
			e.printStackTrace();
			ArchiveException ae = new ArchiveException();
			ae.setLogMsg(MessageUtils.getMessage("JC_SYS_060"));
			throw ae;
		}
	}

	@Override
	public Long getFolderPermissionCount(Permission permission)
			throws ArchiveException {
		return permissionDao.getFolderPermissionCount(permission);
	}
}