package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;
/**
 * @author mrb
 * @version 端口使用情况统计表
*/
public class ToaNetworkPort extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyName;//   客户名称
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
	
	public String getPorts() {
		return ports;
	}
	public void setPorts(String ports) {
		this.ports = ports;
	}
	public String getUsings() {
		return usings;
	}
	public void setUsings(String usings) {
		this.usings = usings;
	}
