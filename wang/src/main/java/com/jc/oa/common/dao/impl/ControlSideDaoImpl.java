package com.jc.oa.common.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.common.dao.IControlSideDao;
import com.jc.oa.common.domain.ControlSide;
import com.jc.system.CustomException;

/**
 * @title 172.16.3.68
 * @description  dao实现类
 * @author 
 * @version  2014-04-29
 */
@Repository
public class ControlSideDaoImpl extends BaseDaoImpl<ControlSide> implements IControlSideDao{

	public ControlSideDaoImpl(){}
	
	public static final String SQL_DELETE_BY_DATAID = "deleteByDataId";
	
	public static final String SQL_DELETE_CONTROLSIDE = "deleteControlSide";

	/**
	 * 用业务ID删除记录（物理删除）
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public Integer deleteByDataId(ControlSide controlSide) throws CustomException {
		return template.delete(getNameSpace(controlSide) + "."+SQL_DELETE_BY_DATAID, controlSide);
	}
	
	/**
	 * 用业务ID删除记录（物理删除）
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public Integer deleteControlSide(ControlSide controlSide) throws CustomException {
		return template.delete(getNameSpace(controlSide) + "."+SQL_DELETE_CONTROLSIDE, controlSide);
	}
	
	
	

}