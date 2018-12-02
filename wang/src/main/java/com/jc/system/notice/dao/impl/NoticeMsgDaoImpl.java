package com.jc.system.notice.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.system.CommonException;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.system.notice.dao.INoticeMsgDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title GOA系统管理
 * @description  dao实现类
 * @author 
 * @version  2014-06-05
 */
@Repository
public class NoticeMsgDaoImpl extends BaseDaoImpl<NoticeMsg> implements INoticeMsgDao{

	public NoticeMsgDaoImpl(){}

	/**
	  * 批量将通知消息设为只读
	  * @param notice 实体类
	  * @return 更改的条数
	  * @version 1.0 2014年6月9日 上午10:55:15
	 * @throws CommonException 
	*/
	@Override
	public Integer readNotice(NoticeMsg noticeMsg) throws CommonException {
		Integer result = 0;
		try {
			String[] ids = noticeMsg.getPrimaryKeys();
			
			
			for(String id : ids){
				NoticeMsg notice = new NoticeMsg();
				notice.setReadFlag(1);
				notice.setId(Long.parseLong(id));
				template.update("com.jc.system.notice.domain.NoticeMsg.update",notice);
				result++;
			}
		} catch (Exception e) {
			CommonException commonException = new CommonException(e);
			commonException.setLogMsg("批量已读发生错误");
			throw commonException;
		}
		
		return result;
	}

}