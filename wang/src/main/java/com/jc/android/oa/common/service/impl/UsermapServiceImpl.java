package com.jc.android.oa.common.service.impl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.android.oa.common.ClientMessageUtil;
import com.jc.android.oa.common.dao.IUsermapDao;
import com.jc.android.oa.common.domain.Usermap;
import com.jc.android.oa.common.service.IUsermapService;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.ic.domain.MailRecord;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;

/**
 * @title 172.16.3.68
 * @description 业务服务类 Copyright (c) 2014 Jiachengnet.com Inc. All Rights
 * @author
 * @version 2014-09-23
 */
@Service
public class UsermapServiceImpl extends BaseServiceImpl<Usermap> implements
		IUsermapService {

	private IUsermapDao usermapDao;

	ClientMessageUtil clientMessageUtil = new ClientMessageUtil();

	public UsermapServiceImpl() {
	}

	@Autowired
	public UsermapServiceImpl(IUsermapDao usermapDao) {
		super(usermapDao);
		this.usermapDao = usermapDao;
	}

	public void pushMessage(String toUserIds, String title, String message,
			String uri) throws Exception {
		String users[] = toUserIds.split(",");
		for (String u : users) {
			Long id = Long.parseLong(u);
			try {
				clientMessageUtil.pushMessage(id, title, message, uri);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void pushMessage(List<MailRecord> listr, String title,
			String message, String uri) {

		for (MailRecord mailRecord : listr) {
			if (!mailRecord.getReceiveType().equals(
					Constants.IC_MAIL_RECEIVETYPE_INNERSENDER)) {
				try {
					clientMessageUtil.pushMessage(mailRecord.getReceiveUserId(),
							title, message, uri);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (CustomException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch(Exception e){
					
				}
				
			}

		}
	}

	@Override
	public int saveUsermapClient(Long userId, String username, String client)
			throws Exception {
		Usermap usermap = new Usermap();
		usermap.setUserId(userId);
		usermap.setUserName(username);
		usermap.setClientCode(client);
		List list = usermapDao.queryAll(usermap);
		int result =0;
		if (list == null || list.size() == 0) {
			result  =super.save(usermap);
		}
		return result;
	}

}