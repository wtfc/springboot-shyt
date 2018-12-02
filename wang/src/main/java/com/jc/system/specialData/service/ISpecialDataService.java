package com.jc.system.specialData.service;

import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.system.specialData.domain.SpecialData;



/**
 * @title 172.16.3.68
 * @description  业务接口类
 * @author 
 * @version  2014-12-02
 */

public interface ISpecialDataService extends IBaseService<SpecialData>{
	/**
	* @description 根据主键删除多条记录方法
	* @param SpecialData specialData 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-12-02 
	*/
	public Integer deleteByIds(SpecialData specialData) throws CustomException;
	
	/**
	* @description 门户数据组装方法
	* @param 
	* @return Map 处理结果
	* @author
	* @version  2014-12-22 
	*/
	public Map<String, Object> querySpecialForPortal(Long columnId,Long shareUserId, Long shareDeptId, Long shareOrganId) throws CustomException;
	
	/**
	* @description 生日祝福方法
	* @param 
	* @return SpecialData 处理结果
	* @author
	* @version  2014-12-22 
	*/
	public SpecialData getBirthdayBlessing(Long UserId) throws CustomException;
}