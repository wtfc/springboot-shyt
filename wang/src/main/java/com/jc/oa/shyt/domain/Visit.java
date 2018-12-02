package com.jc.oa.shyt.domain;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;
import com.jc.foundation.util.CustomDatetimeSerializer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
/**
 * @author mrb
 * @version 工单设备关联表
*/
public class Visit extends BaseBean {

	private static final long serialVersionUID = 1L;
	private String companyName;//   公司名称
	private String companyAddress;//   公司地址
	private Integer customerId;//   公司ID
	private Integer complainNumber;//   回访次数
	private BigDecimal visitPay;//   消费费用金额(费用)
	private BigDecimal visitPrice;//   购买礼品金额
	private Date visitDate;//   回访日期(回访时间)
	private String visitStatus;//   回访类型
	private String visitMode;//   回访方式
	private String status;//   回访涉及部门(协作部门)
	private String visit;//   回访内容
	private String returnName;//   回访人
	private String returnDept;//   回访人部门
	private String personName;//   受访人
	private String position;//   职位
	private String contactWay;//   联系方式
	private String visitIsReturn;//   回访是否完成[0:未完成，1：完成]
	private String contentMain;//   主要访问/沟通内容1
	private String contentAccess;//   主要访问/沟通内容2
	private String contentComment;//   主要访问/沟通内容3
	private String visitFind;//   回访发现问题1
	private String visitProblem;//   回访发现问题2
	private String visitTheProblem;//   回访发现问题3
	private String solutionReplation;//   解决方案1
	private String solutionDispose;//   解决方案2
	private String solutionSlove;//   解决方案3
	private String customerJudge;//   客户评价1
	private String customerEvaluation;//   客户评价2
	private String customerReviews;//   客户评价3
	private String substanceOne;//   内容1
	private String substanceTwo;//   内容2
	private String substanceThree;//   内容3
	private String involvoedBranch;//   涉及部门1
	private String involvoedDivision;//   涉及部门2
	private String involvoedSection;//   涉及部门3
	private String schemePlan;//   解决建议方案1
	private String schemeBlue;//   解决建议方案2
	private String schemeProject;//   解决建议方案3
	private String replyAnswer;//   是否回复客户1
	private String replyRestore;//   是否回复客户2
	private String replyReflext;//   是否回复客户3
	private String customTickling;//   客户反馈1
	private String customFeed;//   客户反馈2
	private String customCouple;//   客户反馈3
	private String visitPeople;//   陪同人员(协作人)
	private String serviceStatus;//   业务状态
	private String remark;//   回访反馈
	
	/*附件Id*/
	private List<Long> fileids;
	private String delattachIds;//删除附件Id
	
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
	public String getCompanyName() {
	    return companyName;
	}
	public void setCompanyName(String companyName) {
	    this.companyName=companyName;
	}
	public String getCompanyAddress() {
	    return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
	    this.companyAddress=companyAddress;
	}
	public Integer getCustomerId() {
	    return customerId;
	}
	public void setCustomerId(Integer customerId) {
	    this.customerId=customerId;
	}
	public Integer getComplainNumber() {
	    return complainNumber;
	}
	public void setComplainNumber(Integer complainNumber) {
	    this.complainNumber=complainNumber;
	}
	public BigDecimal getVisitPay() {
	    return visitPay;
	}
	public void setVisitPay(BigDecimal visitPay) {
	    this.visitPay=visitPay;
	}
	public BigDecimal getVisitPrice() {
	    return visitPrice;
	}
	public void setVisitPrice(BigDecimal visitPrice) {
	    this.visitPrice=visitPrice;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getVisitDate() {
	    return visitDate;
	}
	public void setVisitDate(Date visitDate) {
	    this.visitDate=visitDate;
	}
	public String getVisitStatus() {
	    return visitStatus;
	}
	public void setVisitStatus(String visitStatus) {
	    this.visitStatus=visitStatus;
	}
	public String getVisitMode() {
	    return visitMode;
	}
	public void setVisitMode(String visitMode) {
	    this.visitMode=visitMode;
	}
	public String getStatus() {
	    return status;
	}
	public void setStatus(String status) {
	    this.status=status;
	}
	public String getVisit() {
	    return visit;
	}
	public void setVisit(String visit) {
	    this.visit=visit;
	}
	public String getReturnName() {
	    return returnName;
	}
	public void setReturnName(String returnName) {
	    this.returnName=returnName;
	}
	public String getReturnDept() {
	    return returnDept;
	}
	public void setReturnDept(String returnDept) {
	    this.returnDept=returnDept;
	}
	public String getPersonName() {
	    return personName;
	}
	public void setPersonName(String personName) {
	    this.personName=personName;
	}
	public String getPosition() {
	    return position;
	}
	public void setPosition(String position) {
	    this.position=position;
	}
	public String getContactWay() {
	    return contactWay;
	}
	public void setContactWay(String contactWay) {
	    this.contactWay=contactWay;
	}
	public String getVisitIsReturn() {
	    return visitIsReturn;
	}
	public void setVisitIsReturn(String visitIsReturn) {
	    this.visitIsReturn=visitIsReturn;
	}
	public String getContentMain() {
	    return contentMain;
	}
	public void setContentMain(String contentMain) {
	    this.contentMain=contentMain;
	}
	public String getContentAccess() {
	    return contentAccess;
	}
	public void setContentAccess(String contentAccess) {
	    this.contentAccess=contentAccess;
	}
	public String getContentComment() {
	    return contentComment;
	}
	public void setContentComment(String contentComment) {
	    this.contentComment=contentComment;
	}
	public String getVisitFind() {
	    return visitFind;
	}
	public void setVisitFind(String visitFind) {
	    this.visitFind=visitFind;
	}
	public String getVisitProblem() {
	    return visitProblem;
	}
	public void setVisitProblem(String visitProblem) {
	    this.visitProblem=visitProblem;
	}
	public String getVisitTheProblem() {
	    return visitTheProblem;
	}
	public void setVisitTheProblem(String visitTheProblem) {
	    this.visitTheProblem=visitTheProblem;
	}
	public String getSolutionReplation() {
	    return solutionReplation;
	}
	public void setSolutionReplation(String solutionReplation) {
	    this.solutionReplation=solutionReplation;
	}
	public String getSolutionDispose() {
	    return solutionDispose;
	}
	public void setSolutionDispose(String solutionDispose) {
	    this.solutionDispose=solutionDispose;
	}
	public String getSolutionSlove() {
	    return solutionSlove;
	}
	public void setSolutionSlove(String solutionSlove) {
	    this.solutionSlove=solutionSlove;
	}
	public String getCustomerJudge() {
	    return customerJudge;
	}
	public void setCustomerJudge(String customerJudge) {
	    this.customerJudge=customerJudge;
	}
	public String getCustomerEvaluation() {
	    return customerEvaluation;
	}
	public void setCustomerEvaluation(String customerEvaluation) {
	    this.customerEvaluation=customerEvaluation;
	}
	public String getCustomerReviews() {
	    return customerReviews;
	}
	public void setCustomerReviews(String customerReviews) {
	    this.customerReviews=customerReviews;
	}
	public String getSubstanceOne() {
	    return substanceOne;
	}
	public void setSubstanceOne(String substanceOne) {
	    this.substanceOne=substanceOne;
	}
	public String getSubstanceTwo() {
	    return substanceTwo;
	}
	public void setSubstanceTwo(String substanceTwo) {
	    this.substanceTwo=substanceTwo;
	}
	public String getSubstanceThree() {
	    return substanceThree;
	}
	public void setSubstanceThree(String substanceThree) {
	    this.substanceThree=substanceThree;
	}
	public String getInvolvoedBranch() {
	    return involvoedBranch;
	}
	public void setInvolvoedBranch(String involvoedBranch) {
	    this.involvoedBranch=involvoedBranch;
	}
	public String getInvolvoedDivision() {
	    return involvoedDivision;
	}
	public void setInvolvoedDivision(String involvoedDivision) {
	    this.involvoedDivision=involvoedDivision;
	}
	public String getInvolvoedSection() {
	    return involvoedSection;
	}
	public void setInvolvoedSection(String involvoedSection) {
	    this.involvoedSection=involvoedSection;
	}
	public String getSchemePlan() {
	    return schemePlan;
	}
	public void setSchemePlan(String schemePlan) {
	    this.schemePlan=schemePlan;
	}
	public String getSchemeBlue() {
	    return schemeBlue;
	}
	public void setSchemeBlue(String schemeBlue) {
	    this.schemeBlue=schemeBlue;
	}
	public String getSchemeProject() {
	    return schemeProject;
	}
	public void setSchemeProject(String schemeProject) {
	    this.schemeProject=schemeProject;
	}
	public String getReplyAnswer() {
	    return replyAnswer;
	}
	public void setReplyAnswer(String replyAnswer) {
	    this.replyAnswer=replyAnswer;
	}
	public String getReplyRestore() {
	    return replyRestore;
	}
	public void setReplyRestore(String replyRestore) {
	    this.replyRestore=replyRestore;
	}
	public String getReplyReflext() {
	    return replyReflext;
	}
	public void setReplyReflext(String replyReflext) {
	    this.replyReflext=replyReflext;
	}
	public String getCustomTickling() {
	    return customTickling;
	}
	public void setCustomTickling(String customTickling) {
	    this.customTickling=customTickling;
	}
	public String getCustomFeed() {
	    return customFeed;
	}
	public void setCustomFeed(String customFeed) {
	    this.customFeed=customFeed;
	}
	public String getCustomCouple() {
	    return customCouple;
	}
	public void setCustomCouple(String customCouple) {
	    this.customCouple=customCouple;
	}
	public String getVisitPeople() {
	    return visitPeople;
	}
	public void setVisitPeople(String visitPeople) {
	    this.visitPeople=visitPeople;
	}
	public String getServiceStatus() {
	    return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
	    this.serviceStatus=serviceStatus;
	}
	public String getRemark() {
	    return remark;
	}
	public void setRemark(String remark) {
	    this.remark=remark;
	}
}

