package com.jc.oa.contract.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 收款方合同
*/
public class ToaContractIncontract extends BaseBean {
	
	private static final long serialVersionUID = 1L;
	private String companyName;//   公司名称
	private String customerType;//业务类型
	private Date endDateBegin;   /*合同终止日期开始*/
	private Date endDateEnd;   /*合同终止日期结束*/
	private List<Long> fileids;
	private String delattachIds;//删除附件Id
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	@JsonSerialize(using = CustomDateSerializer.class)
	public List<Long> getFileids() {
		return fileids;
	}
	public void setFileids(List<Long> fileids) {
		this.fileids = fileids;
	}
	public String getDelattachIds() {
		return delattachIds;
	}
	public void setDelattachIds(String delattachIds) {
		this.delattachIds = delattachIds;
	}
	public Date getEndDateBegin() {
		return endDateBegin;
	}
	public void setEndDateBegin(Date endDateBegin) {
		this.endDateBegin = endDateBegin;
	}
	public Date getEndDateEnd() {
		return endDateEnd;
	}
	public void setEndDateEnd(Date endDateEnd) {
		this.endDateEnd = endDateEnd;
	}
	public String getCustomerType() {
		return customerType;
	}
	
	public String getCustomerTypeValue() {
		IDicManager dicManager = new DicManagerImpl();
		return dicManager.getDic("customerType", this.getCustomerType()) == null ? "" : dicManager.getDic("customerType", this.getCustomerType()).getValue();
	}
	
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
}
