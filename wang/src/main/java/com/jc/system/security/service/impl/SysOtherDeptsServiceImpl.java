package com.jc.system.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.system.CustomException;
import com.jc.system.security.dao.ISysOtherDeptsDao;
import com.jc.system.security.service.ISysOtherDeptsService;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.security.domain.SysOtherDepts;

/**
 * @title GOA2.0
 * @description 用户其他部门任职表 业务实现类
 * @author 高呀
 * @version  2014-04-14
 *
 */
@Service
public class SysOtherDeptsServiceImpl extends BaseServiceImpl<SysOtherDepts> implements ISysOtherDeptsService{
	
	@Autowired
	public SysOtherDeptsServiceImpl(ISysOtherDeptsDao dao) {
		super(dao);
		this.iSysOtherDeptsDao = dao;
	}
	public SysOtherDeptsServiceImpl(){
		
	}
	private ISysOtherDeptsDao iSysOtherDeptsDao;

	/**
	 * 删除方法
	 * @param Long userId
	 * @return Integer 删除的记录数
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int deleteOtherDept(Long userId) {
		return iSysOtherDeptsDao.deleteOtherDept(userId);
	}

	/**
	 * 修改删除状态
	 * @param SysOtherDepts
	 * @return Integer
	 * @author
	 * @version 2014-03-18
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateDeleteFlagByIds(SysOtherDepts sysOtherDepts) {
		return iSysOtherDeptsDao.updateDeleteFlagByIds(sysOtherDepts);
	}
}