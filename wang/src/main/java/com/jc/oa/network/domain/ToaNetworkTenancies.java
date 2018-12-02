package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;
/**
 * @author mrb
 * @version 退租客户记录表
*/
public class ToaNetworkTenancies extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyId;//   客户id
	
	public String getRoomValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getRoom()) == null ? "" : dicManager.getDic("room", this.getRoom()).getValue();
	}
	
