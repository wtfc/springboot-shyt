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
	private String companyName;//   公司名称	private Integer customerId;//   客户表id	private String contractNumber;//   合同编码
	private String customerType;//业务类型	private String leard;//   发起人	private Date leardDate;//   发起日期	private String agreement;//   协议类型	private String contractStatus;//   合同类别	private String contractMoney;//   合同金额	private String seal;//   盖章	private Date place;//   归档时间	private Date startDate;//   合同起始日期	private Date endDate;//   合同终止日期
	private Date endDateBegin;   /*合同终止日期开始*/
	private Date endDateEnd;   /*合同终止日期结束*/	private String derive;//   是否衍生其他合同	private String deriveNo;//   衍生合同编号	private String resource;//   合同资源	private String contractFile;//   合同扫描件	private String remark;//   备注
	private List<Long> fileids;
	private String delattachIds;//删除附件Id		public String getCompanyName() {	    return companyName;	}	public void setCompanyName(String companyName) {	    this.companyName=companyName;	}	public Integer getCustomerId() {	    return customerId;	}	public void setCustomerId(Integer customerId) {	    this.customerId=customerId;	}	public String getContractNumber() {	    return contractNumber;	}	public void setContractNumber(String contractNumber) {	    this.contractNumber=contractNumber;	}	public String getLeard() {	    return leard;	}	public void setLeard(String leard) {	    this.leard=leard;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getLeardDate() {	    return leardDate;	}	public void setLeardDate(Date leardDate) {	    this.leardDate=leardDate;	}	public String getAgreement() {	    return agreement;	}	public void setAgreement(String agreement) {	    this.agreement=agreement;	}	public String getContractStatus() {	    return contractStatus;	}	public void setContractStatus(String contractStatus) {	    this.contractStatus=contractStatus;	}	public String getContractMoney() {	    return contractMoney;	}	public void setContractMoney(String contractMoney) {	    this.contractMoney=contractMoney;	}	public String getSeal() {	    return seal;	}	public void setSeal(String seal) {	    this.seal=seal;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getPlace() {	    return place;	}	public void setPlace(Date place) {	    this.place=place;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getStartDate() {	    return startDate;	}	public void setStartDate(Date startDate) {	    this.startDate=startDate;	}
	@JsonSerialize(using = CustomDateSerializer.class)	public Date getEndDate() {	    return endDate;	}	public void setEndDate(Date endDate) {	    this.endDate=endDate;	}	public String getDerive() {	    return derive;	}	public void setDerive(String derive) {	    this.derive=derive;	}	public String getDeriveNo() {	    return deriveNo;	}	public void setDeriveNo(String deriveNo) {	    this.deriveNo=deriveNo;	}	public String getResource() {	    return resource;	}	public void setResource(String resource) {	    this.resource=resource;	}	public String getContractFile() {	    return contractFile;	}	public void setContractFile(String contractFile) {	    this.contractFile=contractFile;	}	public String getRemark() {	    return remark;	}	public void setRemark(String remark) {	    this.remark=remark;	}
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

