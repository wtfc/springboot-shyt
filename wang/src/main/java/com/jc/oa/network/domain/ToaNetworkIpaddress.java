package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 客户IP地址统计表
*/
public class ToaNetworkIpaddress extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyName;//   客户名称
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
	
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	@JsonSerialize(using = CustomDatetimeSerializer.class)
