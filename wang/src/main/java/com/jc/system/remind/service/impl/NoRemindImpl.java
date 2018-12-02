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
public class NoRemindImpl implements IRemindService{
	public String getRemindCount() {	
		return "0";
	}
	
}
