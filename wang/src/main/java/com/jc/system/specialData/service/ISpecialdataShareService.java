package com.jc.system.specialData.service;

import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.system.specialData.domain.SpecialdataShare;



/**
 * @title 172.16.3.68
 * @description  业务接口类
 * @author 
 * @version  2014-12-02
 */

public interface ISpecialdataShareService extends IBaseService<SpecialdataShare>{
	/**
	* @description 根据主键删除多条记录方法
	* @param SpecialdataShare specialdataShare 实体类
	* @return Integer 处理结果
	* @author
	* @version  2014-12-02 
	*/
	public Integer deleteByIds(SpecialdataShare specialdataShare) throws CustomException;

	public Map<String, String> getIds(SpecialdataShare specialdataShare) throws Exception;
	
	public Integer delAndsave(SpecialdataShare specialdataShare,String userids,String deptids,String organids) throws Exception;

	public Integer checkUserPower(Long specialDataId,Long shareUserId,Long shareDeptId,Long shareOrganId ) throws Exception;
}