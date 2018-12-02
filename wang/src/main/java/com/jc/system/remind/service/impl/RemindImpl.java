package com.jc.system.remind.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.jc.system.CustomException;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.notice.domain.NoticeMsg;
import com.jc.system.notice.service.INoticeMsgService;
import com.jc.system.remind.service.IRemindService;

/**
 * @title GOA系统管理
 * @description  业务接口类
 * @author 
 * @version  2014-07-24
 */
public class RemindImpl implements IRemindService{
	INoticeMsgService noticeService = SpringContextHolder.getBean(INoticeMsgService.class);
	public String getRemindCount() {
		NoticeMsg dic = new NoticeMsg();
		List<NoticeMsg> diclist = new ArrayList();
		try {
			diclist = noticeService.queryMsg(Long.valueOf(1));
		} catch (CustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return String.valueOf(diclist.size());
	}
	
}
