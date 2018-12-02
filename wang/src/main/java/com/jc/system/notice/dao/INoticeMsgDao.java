package com.jc.system.notice.dao;

import com.jc.system.CommonException;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.foundation.dao.IBaseDao;


/**
 * @title GOA系统管理
 * @description  dao接口类
 * @author 
 * @version  2014-06-05
 */
 
public interface INoticeMsgDao extends IBaseDao<NoticeMsg>{

	/**
	  * 批量将通知消息设为只读
	  * @param notice 实体类
	  * @return 更改的条数
	  * @version 1.0 2014年6月9日 上午10:55:15
	*/
	Integer readNotice(NoticeMsg notice) throws CommonException;
}
