package com.jc.oa.common.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.common.domain.ControlSide;
import com.jc.system.CustomException;

/**
 * @title 172.16.3.68
 * @author
 * @version 2014-04-29
 */

public interface IControlSideService extends IBaseService<ControlSide> {


	/**
	 * 更新数据功能 按查询条件检索出数据，比较检索出的数据与data_new两个集合中的数据，做以下处理 1、和原有数据一致的不动
	 * 2、新增数据插入数据库 3、多出数据从数据库中删除
	 * 
	 * @param controlSide
	 *            -查询条件
	 * @param data_new
	 *            -要插入的数据
	 * @return
	 * @throws CustomException
	 */
	public int updateControlSideServiceBatch(ControlSide controlSide,
			List<ControlSide> data_new) throws CustomException;
	
	/**
	 * 更新数据功能  根据DataId 先删除 然后插入数据
	 * 
	 * @param controlSide
	 * @throws CustomException
	 */
	public Integer updateByDataId(ControlSide controlSide) throws CustomException;

	/**
	 * 按人ID查询此人是否对制定业务有权限 部门权限和人员权限有其一，就算有权限
	 * 
	 * @param controlSide
	 *            -
	 * @return -true-有权限 false-无权限
	 * @throws CustomException
	 */
	public boolean checkControlSide(ControlSide controlSide)
			throws CustomException;

	/**
	 * 用业务ID删除记录（物理删除）
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public Integer deleteControlSideByDataID(ControlSide controlSide)
			throws CustomException;
	
	/**
	 * 存储范围方法
	 * @param  ControlSide controlSide
	 * @return int 保存记录
	 * @throws CustomException
	 */
	public int saveRangeByIds(ControlSide controlSide) throws CustomException;
	
	/** 方法描述：传入TableName 返回符合条件的所有user_id 不能有重复的 id拼接成字符串用逗号分隔
	 * @param controlSide
	 * @return
	 * @throws Exception
	 * @author 
	 * @version  2014年5月15日下午5:03:37
	 * @see
	 */
	public String queryAllRangeUserId(ControlSide controlSide) throws Exception;
	
	/** 方法描述：传入TableName userId 返回所有符合条件的createUser 滤重 id拼接成字符串 用逗号分隔
	 * @param controlSide
	 * @return
	 * @throws Exception
	 * @author 
	 * @version  2014年5月27日上午8:15:19
	 * @see
	 */
	public String queryAllRangeCreateUserId(ControlSide controlSide) throws Exception;
	
	/** 方法描述：传入TableName dataId 返回所有符合条件的userid 滤重 id拼接成字符串 用逗号分隔
	 * @param controlSide
	 * @return
	 * @throws Exception
	 * @author 
	 * @version  2014年5月27日上午8:15:19
	 * @see
	 */
	public String queryRangeUserId(ControlSide controlSide) throws Exception;
	
	/**
	 * 更新数据功能  根据DataId 先删除 然后插入数据
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public Integer updateControlSide(ControlSide controlSide,List<ControlSide> data_new) throws CustomException;
	
	/**
	 * 更新数据功能  根据人员id和表名 查询 与人员有关的所有范围
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public List<ControlSide> queryControlSide(ControlSide controlSide) throws CustomException;
	
	/**
	 * 根据部门Id 取部门所有父节点ID
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 */
	public String getparentDepatmentIDs(Long deptId) throws CustomException;
	/** 方法描述：获取dataid串
	 * @param controlSide
	 * @return 
	 * @throws Exception
	 * @author Administrator
	 * @version  2014年9月15日上午11:07:41
	 * @see
	 */
	public String queryAllRangeDataId(ControlSide controlSide) throws Exception;
	
	/**
	 * 更新数据功能  根据人员id和表名 查询 与人员有关的所有范围(本机构)
	 * 
	 * @param controlSide
	 * @return
	 * @throws CustomException
	 * @version  2014年11月3日上午11:07:41
	 */
	public List<ControlSide> queryControlSideOnlyOneOrg(ControlSide controlSide) throws CustomException;
}