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
	private String ringType;//   环网类型	private String machine;//   机房名称	private String oppositeMachine;//   对端机房名称	private Integer lineGbps;//   链路带宽	private Integer lineNumber;//   聚合链路数量	private BigDecimal gbps;//   用户带宽总和	private String withFuyongbi;//   链路带宽复用比	private String remark;//   备注	public String getRingType() {	    return ringType;	}	public void setRingType(String ringType) {	    this.ringType=ringType;	}	public String getMachine() {	    return machine;	}	public void setMachine(String machine) {	    this.machine=machine;	}
	
	public String getMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getMachine()) == null ? "" : dicManager.getDic("room", this.getMachine()).getValue();
	}
		public String getOppositeMachine() {	    return oppositeMachine;	}	public void setOppositeMachine(String oppositeMachine) {	    this.oppositeMachine=oppositeMachine;	}
	
	public String getOppositeMachineValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("room", this.getOppositeMachine()) == null ? "" : dicManager.getDic("room", this.getOppositeMachine()).getValue();
	}
		public Integer getLineGbps() {	    return lineGbps;	}	public void setLineGbps(Integer lineGbps) {	    this.lineGbps=lineGbps;	}	public Integer getLineNumber() {	    return lineNumber;	}	public void setLineNumber(Integer lineNumber) {	    this.lineNumber=lineNumber;	}	public BigDecimal getGbps() {	    return gbps;	}	public void setGbps(BigDecimal gbps) {	    this.gbps=gbps;	}	public String getWithFuyongbi() {	    return withFuyongbi;	}	public void setWithFuyongbi(String withFuyongbi) {	    this.withFuyongbi=withFuyongbi;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

