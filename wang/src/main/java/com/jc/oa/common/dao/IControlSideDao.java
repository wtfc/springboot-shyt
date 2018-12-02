package com.jc.oa.common.dao;


import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.common.domain.ControlSide;
import com.jc.system.CustomException;


/**
 * @title 172.16.3.68
 * @description  dao接口类
 * @author 
 * @version  2014-04-29
 */
 
public interface IControlSideDao extends IBaseDao<ControlSide>{

	/**
	 * 用业务ID删除记录（物理删除）
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public Integer deleteByDataId(ControlSide controlSide) throws CustomException;
	
	/**
	 * 用业务ID删除记录（物理删除）
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public Integer deleteControlSide(ControlSide controlSide) throws CustomException;
}
