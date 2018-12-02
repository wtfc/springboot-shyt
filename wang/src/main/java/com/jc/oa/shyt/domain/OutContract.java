package com.jc.oa.shyt.domain;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;
/**
 * @author mrb
 * @version 2016-2-1
 * 付款方合同信息实体类
 * */
public class OutContract extends BaseBean{
	
	private static final long serialVersionUID = 1L;
	private String companyName;//甲方公司名称
	private String customer;//乙方公司名称
	private String contractNumber;//合同编码
	private String leard;//发起人
	private Date leardDate;//发起日期
	private String agreement;//合同类型
	private String contractStatus;//合同类别
	private String contractMoney;//合同金额
	private String seal;//盖章
	private Date place;//归档时间
	private Date startDate;//合同起始日期
	private Date endDate;//合同终止日期
	private String resource;//合同资源
	private String contractFile;//合同扫描件
	private String remark;//备注
	private List<Long> fileids;
	private String delattachIds;//删除附件Id
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getLeard() {
		return leard;
	}
	public void setLeard(String leard) {
		this.leard = leard;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getLeardDate() {
		return leardDate;
	}
	public void setLeardDate(Date leardDate) {
		this.leardDate = leardDate;
	}
	public String getAgreement() {
		return agreement;
	}
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}
	public String getContractStatus() {
		return contractStatus;
	}
	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}
	public String getContractMoney() {
		return contractMoney;
	}
	public void setContractMoney(String contractMoney) {
		this.contractMoney = contractMoney;
	}
	public String getSeal() {
		return seal;
	}
	public void setSeal(String seal) {
		this.seal = seal;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getPlace() {
		return place;
	}
	public void setPlace(Date place) {
		this.place = place;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getResource() {
		return resource;
	}
	public void setResource(String resource) {
		this.resource = resource;
	}
	public String getContractFile() {
		return contractFile;
	}
	public void setContractFile(String contractFile) {
		this.contractFile = contractFile;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getContractNumber() {
		return contractNumber;
	}
	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}
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
	
}
