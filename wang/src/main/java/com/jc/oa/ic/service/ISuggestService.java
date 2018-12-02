package com.jc.oa.ic.service;

import java.util.List;
import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.SugRep;
import com.jc.oa.ic.domain.Suggest;
import com.jc.oa.ic.domain.SuggestType;

/**
 * @title GOA V2.0 互动交流
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public interface ISuggestService extends IBaseService<Suggest>{
	
	/**
	* @description 设置建议类别的下拉数据
	* @return List<SuggestType>
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	public List<SuggestType> getSuggestTypeList(SuggestType suggestType) throws IcException;
	
	/**
	* @description 带入默认的人员
	* @return String
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	public  Map<String,String>  getUserNames(String ids) throws  IcException;
	
	/**
	 * @description 保存回复方法
	 * @param SugRep sugRep 实体类
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	public Integer saveRep(SugRep sugRep) throws  IcException;
	
}