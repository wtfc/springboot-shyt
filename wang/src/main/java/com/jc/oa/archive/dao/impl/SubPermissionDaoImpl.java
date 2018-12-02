package com.jc.oa.archive.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.archive.dao.ISubPermissionDao;
import com.jc.oa.archive.domain.SubPermission;

/**
 * @title  GOA2.0源代码
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 闻瑜
 * @version  2014-06-19
 */
@Repository
public class SubPermissionDaoImpl extends BaseDaoImpl<SubPermission> implements ISubPermissionDao{

	public SubPermissionDaoImpl(){}

	/**
	 * 物理删除权限
	 * @param SubPermission subPermission 实体类
	 * @author weny
	 * @version  2014-06-19
	 */
	public Integer deleteSubPermission(SubPermission subPermission) {
		// 查询行数
		Integer result = template.delete(getNameSpace(subPermission) + "."+SQL_DELETE_PHYSICAL, subPermission);
		return result;
	}
	

	/**
	 * 修改权限
	 * @param SubPermission subPermission 实体类
	 * @author weny
	 * @version  2014-06-19
	 */
	public List<SubPermission> updateSubPermission(SubPermission subPermission) {
		// 查询行数
		List<SubPermission> sub = template.selectList(getNameSpace(subPermission) + "."+"query", subPermission);
		return sub;
	}
}