package com.jc.android.oa.common.service;

import java.util.List;

import com.jc.android.oa.common.domain.Usermap;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.ic.domain.MailRecord;

/**
 * @title 172.16.3.68
 * @description 业务接口类 Copyright (c) 2014 Jiachengnet.com Inc. All Rights
 * @author
 * @version 2014-09-23
 */

public interface IUsermapService extends IBaseService<Usermap> {

	public void pushMessage(List<MailRecord> toUserIds, String title,
			String message, String uri) throws Exception;

	public void pushMessage(String toUserIds, String title, String message,
			String uri) throws Exception;

	public int saveUsermapClient(Long userId, String username, String client) throws Exception;
}