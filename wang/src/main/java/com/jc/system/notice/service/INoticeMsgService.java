package com.jc.system.notice.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CommonException;
import com.jc.system.CustomException;
import com.jc.system.notice.domain.NoticeMsg;

/**
 * @title GOA系统管理
 * @description  业务接口类
 * @author 
 * @version  2014-06-05
 */

public interface INoticeMsgService extends IBaseService<NoticeMsg>{
	
	/**
	  * 根据人员id查询消息提醒信息
	  * @param userId 人员id
	  * @return 消息提醒数组
	  * @version 1.0 2014年6月6日 上午9:25:52
	*/
	List<NoticeMsg> queryMsg(Long userId) throws CustomException;
	
	/**
	  * 根据人员id查询消息提醒信息
	  * @param userId 人员id
	  * @return 消息提醒数组
	  * @version 1.0 2014年6月6日 上午9:25:52
	*/
	Integer readNotice(NoticeMsg noticeMsg) throws CommonException;
}