package com.jc.oa.ic.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.domain.MailSign;
import com.jc.oa.ic.domain.Mailbox;
import com.jc.system.CustomException;

/**
 * @title GOA V2.0 互动交流
 * @description  业务接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 徐伟平
 * @version  2014-04-17
 */

public interface IMailSignService extends IBaseService<MailSign>{
	/**
	 * @description 删除方法
	 * @param MailSign mailSign 实体类
	 * @param String id 被删除数据主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author 徐伟平
	 * @version  2014-04-17
	 */
	public Integer delete(MailSign mailSign, String id) throws IcException;
	/**
	* @description 设置邮箱默认签名时的下拉数据
	* @return List<Mailbox>
	* @throws Exception
	* @author 徐伟平
	* @version  2014-04-21 
	*/
	public List<Mailbox> mailboxList(Mailbox mailbox)throws IcException;
	/**
	 * @description 保存邮箱默认签名方法
	 * @param Mailbox mailbox 实体类
	 * @return Integer 更新的数目
	 * @author 徐伟平
	 * @version 2014-03-18
	 */
	public Integer updateMailbox(Mailbox mailbox)throws IcException;
	
	/**
	 * 方法描述：保存内部邮箱默认签名方法
	 * @param mailbox
	 * @param result
	 * @param request
	 * @return
	 * @throws CustomException
	 * @author 宋海涛
	 * @version  2014年9月24日上午8:29:05
	 * @see
	 */
	public Integer saveMailbox(Mailbox mailbox)throws IcException;
	
}