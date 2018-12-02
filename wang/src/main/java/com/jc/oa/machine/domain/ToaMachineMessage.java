package com.jc.oa.machine.domain;

import com.jc.foundation.domain.BaseBean;
import java.util.Date;
/**
 * @author mrb
 * @version 工单消息表
*/
public class ToaMachineMessage extends BaseBean {
	

	private static final long serialVersionUID = 1L;
	private String messageNumber;//   消息编号	private String messageTitle;//   消息标题	private String messageContent;//   消息内容	private String receivedDeptId;//   接收部门ID	private String messageType;//   消息所属工单类型	private String remark;//   备注
		public String getMessageNumber() {	    return messageNumber;	}	public void setMessageNumber(String messageNumber) {	    this.messageNumber=messageNumber;	}	public String getMessageTitle() {	    return messageTitle;	}	public void setMessageTitle(String messageTitle) {	    this.messageTitle=messageTitle;	}	public String getMessageContent() {	    return messageContent;	}	public void setMessageContent(String messageContent) {	    this.messageContent=messageContent;	}	public String getReceivedDeptId() {	    return receivedDeptId;	}	public void setReceivedDeptId(String receivedDeptId) {	    this.receivedDeptId=receivedDeptId;	}	public String getMessageType() {	    return messageType;	}	public void setMessageType(String messageType) {	    this.messageType=messageType;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}}

