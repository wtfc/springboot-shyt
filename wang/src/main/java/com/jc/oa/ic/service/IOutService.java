package com.jc.oa.ic.service;

import java.util.Map;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.Out;
/**
 * 
 * @title GOA V2.0
 * @description 业务接口类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年5月15日上午11:51:36
 */

public interface IOutService extends IBaseService<Out>{
	/**
	 * 方法描述： 短信保存
	 * @param out
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日下午2:14:53
	 * @see
	 */
	public Integer sendAndSave(Out out) throws Exception;
	
	/**
	 * 方法描述：查询发信箱
	 * @param out
	 * @param page
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日下午2:15:19
	 * @see
	 */
	public PageManager queryOut(Out out,PageManager page);
	
	/**
	 * 方法描述： 根据用户id获得用户电话号
	 * @param id
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日下午2:15:50
	 * @see
	 */
	public String getMobile(String id);
		
	/**
	 * 方法描述：判断是否有可发短信
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日下午3:36:32
	 * @see
	 */
	public Map<String, Object> isHaveSendMes(Out out) throws Exception;
	
	/**
	 * 方法描述：查看发送用户用是否存在没有电话号的情况
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日下午3:36:32
	 * @see
	 */
	public Map<String, Object> isHaveMobileFacade(Out out);
	
	/**
	 * 方法描述：外部查看发送用户用是否存在没有电话号的情况
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日下午3:36:32
	 * @see
	 */
	public Map<String, Object> isHaveMobile(Out out);
	
	/**
	 * 方法描述：查看短信功能是否开启
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月6日下午4:17:30
	 * @see
	 */
	public Map<String, Object> mesFunctionIsOpen();
	
	/**
	 * 方法描述：查询短信前缀
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月6日下午4:17:52
	 * @see
	 */
	public Map<String,Object> addmSgPrefix();
	
	/**
	 * 方法描述：添加签名
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月12日下午2:34:17
	 * @see
	 */
	public Integer addName();
	
	/**
	 * 方法描述：外部调用判断是否存在可发送短信
	 * @param out
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月12日下午2:34:36
	 * @see
	 */
	public Map<String, Object> isHaveSendMessage(Out out)throws Exception;
	
	/**
	 * 方法描述：外部调用判断是否存在可发送短信
	 * @return
	 * @author 宋海涛
	 * @version  2014年6月13日上午10:34:03
	 * @see
	 */
	Map<String, Object> messageFunctionIsOpen();
	
	/**
	 * 方法描述：校验短信发送
	 * @param userId
	 * @return
	 * @throws IcException
	 * @author 宋海涛
	 * @version  2014年10月9日上午10:46:44
	 * @see
	 */
	public Map<String, Object> sendValidate(Out out)throws IcException ;
	
	
}