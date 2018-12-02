package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 网络设备采购表
*/
public class ToaNetworkProcure extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String machineNumber;//   设备型号
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	public String getDescribes() {
		return describes;
	}
	public void setDescribes(String describes) {
		this.describes = describes;
	}
	public String getInstalls() {
		return installs;
	}
	public void setInstalls(String installs) {
		this.installs = installs;
	}
