package com.jc.oa.network.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.math.BigDecimal;
/**
 * @author mrb
 * @version 骨干网链路带宽统计表
*/
public class ToaNetworkRing extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String ringType;//   环网类型
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
	
	
	public String getOppositeMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getOppositeMachine()) == null ? "" : dicManager.getDic("room", this.getOppositeMachine()).getValue();
	}
	
