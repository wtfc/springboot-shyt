package com.jc.oa.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.common.dao.IControlSideDao;
import com.jc.oa.common.domain.ControlSide;
import com.jc.oa.common.service.IControlSideService;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.common.util.StringUtil;
import com.jc.system.security.domain.Department;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.util.UserUtils;

import flex.messaging.io.ArrayList;

/**
 * @title 172.16.3.68
 * @author
 * @version 2014-04-29
 */
@Service
public class ControlSideServiceImpl extends BaseServiceImpl<ControlSide>
		implements IControlSideService {

	private IControlSideDao controlSideDao;
	
	@Autowired
	private IDepartmentService departmentService;

	public ControlSideServiceImpl() {
	}

	@Autowired
	public ControlSideServiceImpl(IControlSideDao controlSideDao) {
		super(controlSideDao);
		this.controlSideDao = controlSideDao;
	}

	/**
	 * 更新数据功能 按查询条件检索出数据，比较检索出的数据与data_new两个集合中的数据，做以下处理 1、和原有数据一致的不动
	 * 2、新增数据插入数据库 3、多出数据从数据库中删除
	 * 
	 * @param controlSide
	 *            -查询条件
	 * @param data_new
	 *            -要插入的数据
	 * @return 1-成功 其他-失败
	 * @throws CustomException
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int updateControlSideServiceBatch(ControlSide controlSide,
			List<ControlSide> data_new) throws CustomException {
		// 检索出数据库中数据
		List<ControlSide> data_old = queryAll(controlSide);
		
		//更新用list
		List<ControlSide> delete_data_old = data_old;
		List<ControlSide> delete_data_new = data_new;
		
		
		ControlSide cs = new ControlSide();
		// 对比数据
		for (ControlSide controlside_new : data_new) {
			for (ControlSide controlside_old : data_old) {
				boolean type_flag = false;
				// 判断业务数据id是否相同
				if (controlside_new.getDataId().equals(
						controlside_old.getDataId())) {
					// 判断控制类型是否相同
					if (controlside_new.getControlType().equals(
							controlside_old.getControlType())) {
						// 控制类型为部门时
						if (controlside_new.getControlType().equals(
								Constants.DEPT_TYPE)) {
							// 判断部门Id是否相同
							if (controlside_new.getDeptId().equals(
									controlside_old.getDeptId()))
								type_flag = true;
						}
						// 控制类型为人员时
						else if (controlside_new.getControlType().equals(
								Constants.USER_TYPE)) {
							// 判断人员ID是否相同
							if (controlside_new.getUserId().equals(
									controlside_old.getUserId()))
								type_flag = true;
						}
						// 判断授权类型是否相等
						if (type_flag
								&& controlside_new.getPermissionType().equals(
										controlside_old.getPermissionType())) {
							// 判断表名是否相同
							if (controlside_new.getTableName().equals(
									controlside_old.getTableName())) {
								// 删除相同项
								delete_data_new.remove(controlside_new);
								delete_data_old.remove(controlside_old);
							}
						}
					}
				}
			}
		}
		// 设置要删除的数据ID
		String primaryKey[] = new String[delete_data_old.size()];
		for (int i=0;i<0;i++) {
			ControlSide controlside = delete_data_old.get(i);
			primaryKey[i] = controlside.getId().toString();
		}

		int result = -1;
		try {
			if (delete_data_new.size() > 0) {
				// 批量插入
				super.saveList(delete_data_new);
			}
			if (primaryKey.length > 0) {
				cs.setPrimaryKeys(primaryKey);
				// 批量删除
				super.delete(cs);
			}

			result = 1;

		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw ce;
		}

		return result;
	}

	/**
	 * 按人ID查询此人是否对制定业务有权限 部门权限和人员权限有其一，就算有权限
	 * 
	 * @param controlSide
	 *            -
	 * @return -true-有权限 false-无权限
	 * @throws CustomException
	 */
	public boolean checkControlSide(ControlSide controlSide)
			throws CustomException {
		List<ControlSide> cs = queryAll(controlSide);
		if (cs.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 更新数据功能  根据人员id和表名 查询 与人员有关的所有范围(本机构)
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public List<ControlSide> queryControlSideOnlyOneOrg(ControlSide controlSide) throws CustomException{
		//查询用户所有范围
		List<ControlSide> cs1 = queryAll(controlSide);
		ControlSide controlSide2 = new ControlSide();
		List<ControlSide> cs2 = null;
		String[] dept;
		//查询用户所在部门及所有府部门的范围信息
		if(controlSide.getUserId() != null){
			Long deptId = UserUtils.getUser(controlSide.getUserId()).getDeptId();
			String deptIds = deptId+",";
			deptIds += getparentDepatmentIDsOnlyOneOrg(deptId);
			dept = deptIds.split(",");
			if(dept != null && dept.length > 0){
				for(String deptid : dept){
					if(!StringUtil.isEmpty(deptid)){
						controlSide2.setDeptId(Long.valueOf(deptid));
						controlSide2.setTableName(controlSide.getTableName());
						cs2 = queryAll(controlSide2);
						//过滤重复范围信息
						if(cs2!=null){
							
							Map<String, Object> map = new HashMap<String, Object>();
							for(ControlSide controlSide3 : cs2){
								map.put(controlSide3.getDataId().toString(), controlSide3);
							}
							for(ControlSide controlSide3 : cs1) {
								if(controlSide3.getDataId() != null) {
									if(map.get(controlSide3.getDataId().toString()) != null){
										cs2.remove(map.get(controlSide3.getDataId().toString()));
									}
								}
							}
							cs1.addAll(cs2);
						}
					}
				}
			}
		}
		
		return cs1;
	}
	
	/**
	 * 根据部门Id 取部门所有父节点ID
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public String getparentDepatmentIDsOnlyOneOrg(Long deptId) throws CustomException{
		String deptids = "";
		if(deptId != null && deptId > 0){
			try {
				Department department = new Department();
				department.setId(deptId);
				while(true){
					department = departmentService.queryOne(department);
					if(!(department.getId().intValue() == deptId.intValue())){
						deptids+=department.getId()+",";
					}
					if(department.getDeptType().intValue() == 1){
						break;
					}
					long pId = department.getParentId();
					if(pId > 0){
						department = new Department();
						department.setId(pId);
					} else{
						break;
					}
				}
				
			} catch (CustomException e) {
				CustomException ae = new CustomException(e);
				ae.setLogMsg(MessageUtils.getMessage("JC_SYS_060"));
				throw ae;
			}
		}
		return deptids;
	}
	
	/**
	 * 更新数据功能  根据人员id和表名 查询 与人员有关的所有范围
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public List<ControlSide> queryControlSide(ControlSide controlSide) throws CustomException{
		//查询用户所有范围
		List<ControlSide> cs1 = queryAll(controlSide);
		ControlSide controlSide2 = new ControlSide();
		List<ControlSide> cs2 = null;
		String[] dept;
		//查询用户所在部门及所有府部门的范围信息
		if(controlSide.getUserId() != null){
			String deptIds = UserUtils.getUser(controlSide.getUserId()).getDeptId()+",";
			deptIds += getparentDepatmentIDs(UserUtils.getUser(controlSide.getUserId()).getDeptId());
			dept = deptIds.split(",");
			if(dept != null && dept.length > 0){
				for(String deptid : dept){
					if(!StringUtil.isEmpty(deptid)){
						controlSide2.setDeptId(Long.valueOf(deptid));
						controlSide2.setTableName(controlSide.getTableName());
						cs2 = queryAll(controlSide2);
						//过滤重复范围信息
						if(cs2!=null){
							
							Map<String, Object> map = new HashMap<String, Object>();
							for(ControlSide controlSide3 : cs2){
								map.put(controlSide3.getDataId().toString(), controlSide3);
							}
							for(ControlSide controlSide3 : cs1) {
								if(map.get(controlSide3.getDataId().toString()) != null){
									cs2.remove(map.get(controlSide3.getDataId().toString()));
								}
							}
							cs1.addAll(cs2);
						}
					}
				}
			}
		}
		
		return cs1;
	}
	
	/**
	 * 根据部门Id 取部门所有父节点ID
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public String getparentDepatmentIDs(Long deptId) throws CustomException{
		String deptids = "";
		if(deptId != null && deptId > 0){
			try {
				Department department = new Department();
				department.setId(deptId);
				while(true){
					department = departmentService.queryOne(department);
					deptids+=department.getParentId()+",";
					long pId = department.getParentId();
					if(pId > 0){
						department = new Department();
						department.setId(pId);
					} else{
						break;
					}
				}
				
			} catch (CustomException e) {
				CustomException ae = new CustomException(e);
				ae.setLogMsg(MessageUtils.getMessage("JC_SYS_060"));
				throw ae;
			}
		}
		return deptids;
	}
	

	/**
	 * 用业务ID删除记录（物理删除）
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer deleteControlSideByDataID(ControlSide controlSide)
			throws CustomException {
		Integer result = -1;
		if(controlSide.getIds()!= null){
			controlSide.setUserIds(controlSide.getIds().split(","));
		}
		try {
			// 删除记录
			result = controlSideDao.deleteByDataId(controlSide);

		} catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_006"));
			throw ce;
		}

		return result;
	}

	/**
	 * 存储范围方法
	 * 
	 * @param ControlSide
	 *            controlSide
	 * @return int 保存记录
	 * @throws CustomException
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int saveRangeByIds(ControlSide controlSide) throws CustomException {
		int result = -1;
		if(!StringUtil.isEmpty(controlSide.getIds())){
			String[] s = controlSide.getIds().split(",");
			for (String id : s) {
				ControlSide rangeSide = new ControlSide();
				controlSide.setUserId(Long.parseLong(id));
				// 复制bean属性
				BeanUtils.copyProperties(controlSide, rangeSide);
				result = super.save(rangeSide);
			}
		}
		return result;
	}

	@Override
	public String queryAllRangeUserId(ControlSide controlSide) throws Exception {
		List<ControlSide> list = controlSideDao.queryAll(controlSide);
		// List list = new ArrayList();
		// for (Long i = 1L; i <= 5; i++) {
		// ControlSide controlSide2 = new ControlSide();
		// controlSide2.setUserId(i);
		// list.add(controlSide2);
		// }
		String str = "";
		if (list != null && list.size() > 0) {
			ControlSide contr = new ControlSide();
			list.add(contr);

			List<Long> arr = new ArrayList();
			if (list != null && list.size() > 0 && arr.size() == 0) {
				arr.add(((ControlSide) list.get(0)).getUserId());
			}

			for (int j = 0; j < list.size(); j++) {
				ControlSide side = (ControlSide) list.get(j);
				int k = 0;
				for (int i = 0; i < arr.size(); i++) {
					if (side.getUserId().equals(arr.get(i))) {
						k++;
					}
				}
				if (k == 0) {
					arr.add(side.getUserId());
				}
			}

			for (int i = 0; i < arr.size(); i++) {
				str += arr.get(i) + ",";
			}
			if (str.lastIndexOf(",") == str.length() - 1) {
				str = str.substring(0, str.length() - 1);
			}
		}

		return str;
	}
	
	/** 方法描述：传入TableName userId 返回所有符合条件的createUser 滤重 id拼接成字符串 用逗号分隔
	 * @param controlSide
	 * @return
	 * @throws Exception
	 * @author 
	 * @version  2014年5月27日上午8:15:19
	 * @see
	 */
	@Override
	public String queryAllRangeCreateUserId(ControlSide controlSide) throws Exception {
		List<ControlSide> list = controlSideDao.queryAll(controlSide);
		String str="";
		if(list!=null&&list.size()>0){
			int len=list.size();
			for(int i=0;i<len-1;i++){
				for(int j=i+1; j<len; j++){
					if(list.get(j).getCreateUser().longValue()==list.get(i).getCreateUser().longValue()){
						list.remove(j);
						j--;
	                    len--;
					}
				}
			}
			for(ControlSide bo:list){
				str+=bo.getCreateUser()+",";
			}
			str=str.substring(0,str.length()-1);
		}
		return str;
	}
	
	/** 方法描述：获取dataid串
	 * @param controlSide
	 * @return 
	 * @throws Exception
	 * @author Administrator
	 * @version  2014年9月15日上午11:07:41
	 * @see
	 */
	@Override
	public String queryAllRangeDataId(ControlSide controlSide) throws Exception {
		List<ControlSide> list = controlSideDao.queryAll(controlSide);
		String str="";
		if(list!=null&&list.size()>0){
			for(ControlSide cs:list){
				str+=cs.getDataId()+",";
			}
			str=str.substring(0,str.length()-1);
		}
		return str;
	}

	/**
	 * 更新数据功能  根据DataId 先删除 然后插入数据
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateByDataId(ControlSide controlSide) throws CustomException {
		Integer result = -1;
		try{
			controlSideDao.deleteByDataId(controlSide);
			result = saveRangeByIds(controlSide);
		}
		catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw ce;
		}
		return result;
	}

	
	/**
	 * 更新数据功能  根据DataId 先删除 然后插入数据
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateControlSide(ControlSide controlSide,List<ControlSide> data_new) throws CustomException {
		Integer result = -1;
		try{
			if(controlSide != null){
				List<ControlSide> list = super.queryAll(controlSide);
				if(list != null && list.size() > 0){
					String[] pks = new String[list.size()];
					for(int i = 0; i<list.size(); i++){
						pks[i] = list.get(i).getId().toString();
					}
					controlSide.setPrimaryKeys(pks);
					controlSideDao.delete(controlSide, false);
				}
				
			}
			if(data_new != null && data_new.size() > 0){
				result = super.saveList(data_new);
			}
			
		}
		catch (Exception e) {
			CustomException ce = new CustomException(e);
			ce.setLogMsg(MessageUtils.getMessage("JC_SYS_004"));
			throw ce;
		}
		return result;
	}

	/** 方法描述：传入TableName dataId 返回所有符合条件的userid 滤重 id拼接成字符串 用逗号分隔
	 * @param controlSide
	 * @return
	 * @throws Exception
	 * @author 
	 * @version  2014年5月27日上午8:15:19
	 * @see
	 */
	@Override
	public String queryRangeUserId(ControlSide controlSide) throws Exception {
		List<ControlSide> list = controlSideDao.queryAll(controlSide);
		String str="";
		if(list!=null&&list.size()>0){
			for(int i=0;i<list.size()-1;i++){
				for(int j=list.size()-1;j>i;j--){
					if(list.get(j).getUserId().longValue()==list.get(i).getUserId().longValue()){
						list.remove(j);
					}
				}
			}
			for(ControlSide bo:list){
				str+=bo.getUserId()+",";
			}
			str=str.substring(0,str.length()-1);
		}
		return str;
	}
	
	
}