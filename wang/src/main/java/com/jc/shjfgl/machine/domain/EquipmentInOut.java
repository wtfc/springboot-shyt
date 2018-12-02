package com.jc.shjfgl.machine.domain;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDateSerializer;


public class EquipmentInOut extends BaseBean{

		protected transient final Logger log = Logger.getLogger(this.getClass());
		private static final long serialVersionUID = 1L;
		private String companyName;//客户名称
		private String machina;//机房名称
		private String machinaId;//机房id
		private String machinaNumber;//机柜编号
		private String contact;//联系人
		private String tel;//联系方式
		private String type;//操作类型
		private String origin;//来源 0客户 1销售 2技术
		private Date intDate;//入室时间
		private String noter;//记录人
		private String intPeople;//入室人员
		private String deviceInfo;//设备信息
		private String operate;//操作需求
		private String remarkOne; //备注说明
		private String process;//处理过程
		private String isInput;//是否进场
		private Integer countt;//设备数量
		private String eazyName;//客户简称
		private String implemtation;//实施情况
		private String implemtationName;//实施人
		private String workLeader;//施工负责人
		private String caozgcs;//操作工程师
		private String remark;//备注
		private String remarkTwo;//备注shuoming3
		private String feedback;//客户反馈
		private String rebackName;//回访人
		private String search;//满意度评价
		private Integer status;//处理状态 0待处理 1已接单 2操作完成 3作废 4工单完结
		private String fileName;//文件名
		private String businessId;//附件id
		private String businessAdress;//文件路径
		private Date startDate;
		private Date endDate;
		private Integer equipmentId;
		private Integer isFinish;
		private Date orderTime;
		/*附件Id*/
		private List<Long> fileids;
		private String delattachIds;//删除附件Id
		
		
		
		public String getCompanyName() {
			return companyName;
		}
		public void setCompanyName(String companyName) {
			this.companyName = companyName;
		}
		public String getMachina() {
			return machina;
		}
		public void setMachina(String machina) {
			this.machina = machina;
		}
		public String getMachinaId() {
			return machinaId;
		}
		public void setMachinaId(String machinaId) {
			this.machinaId = machinaId;
		}
		public String getMachinaNumber() {
			return machinaNumber;
		}
		public void setMachinaNumber(String machinaNumber) {
			this.machinaNumber = machinaNumber;
		}
		public String getContact() {
			return contact;
		}
		public void setContact(String contact) {
			this.contact = contact;
		}
		public String getTel() {
			return tel;
		}
		public void setTel(String tel) {
			this.tel = tel;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getOrigin() {
			return origin;
		}
		public void setOrigin(String origin) {
			this.origin = origin;
		}
		@JsonSerialize(using = CustomDateSerializer.class)
		public Date getIntDate() {
			return intDate;
		}
		public void setIntDate(Date intDate) {
			this.intDate = intDate;
		}
		public String getNoter() {
			return noter;
		}
		public void setNoter(String noter) {
			this.noter = noter;
		}
		public String getIntPeople() {
			return intPeople;
		}
		public void setIntPeople(String intPeople) {
			this.intPeople = intPeople;
		}
		public String getDeviceInfo() {
			return deviceInfo;
		}
		public void setDeviceInfo(String deviceInfo) {
			this.deviceInfo = deviceInfo;
		}
		public String getOperate() {
			return operate;
		}
		public void setOperate(String operate) {
			this.operate = operate;
		}
		public String getProcess() {
			return process;
		}
		public void setProcess(String process) {
			this.process = process;
		}
		public String getIsInput() {
			return isInput;
		}
		public void setIsInput(String isInput) {
			this.isInput = isInput;
		}
		public Integer getCountt() {
			return countt;
		}
		public void setCountt(Integer countt) {
			this.countt = countt;
		}
		public String getEazyName() {
			return eazyName;
		}
		public void setEazyName(String eazyName) {
			this.eazyName = eazyName;
		}
		public String getImplemtation() {
			return implemtation;
		}
		public void setImplemtation(String implemtation) {
			this.implemtation = implemtation;
		}
		public String getImplemtationName() {
			return implemtationName;
		}
		public void setImplemtationName(String implemtationName) {
			this.implemtationName = implemtationName;
		}
		public String getWorkLeader() {
			return workLeader;
		}
		public void setWorkLeader(String workLeader) {
			this.workLeader = workLeader;
		}
		public String getCaozgcs() {
			return caozgcs;
		}
		public void setCaozgcs(String caozgcs) {
			this.caozgcs = caozgcs;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
		public String getFeedback() {
			return feedback;
		}
		public void setFeedback(String feedback) {
			this.feedback = feedback;
		}
		public String getRebackName() {
			return rebackName;
		}
		public void setRebackName(String rebackName) {
			this.rebackName = rebackName;
		}
		public String getSearch() {
			return search;
		}
		public void setSearch(String search) {
			this.search = search;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public String getFileName() {
			return fileName;
		}
		public void setFileName(String fileName) {
			this.fileName = fileName;
		}
		public String getBusinessId() {
			return businessId;
		}
		public void setBusinessId(String businessId) {
			this.businessId = businessId;
		}
		public String getBusinessAdress() {
			return businessAdress;
		}
		public void setBusinessAdress(String businessAdress) {
			this.businessAdress = businessAdress;
		}
		public String getRemarkOne() {
			return remarkOne;
		}
		public void setRemarkOne(String remarkOne) {
			this.remarkOne = remarkOne;
		}
		public String getRemarkTwo() {
			return remarkTwo;
		}
		public void setRemarkTwo(String remarkTwo) {
			this.remarkTwo = remarkTwo;
		}
		public Integer getEquipmentId() {
			return equipmentId;
		}
		public void setEquipmentId(Integer equipmentId) {
			this.equipmentId = equipmentId;
		}
		public Date getEndDate() {
			return endDate;
		}
		public void setEndDate(Date endDate) {
			this.endDate = endDate;
		}
		public Date getStartDate() {
			return startDate;
		}
		public void setStartDate(Date startDate) {
			this.startDate = startDate;
		}
		public Integer getIsFinish() {
			return isFinish;
		}
		public void setIsFinish(Integer isFinish) {
			this.isFinish = isFinish;
		}
		public Date getOrderTime() {
			return orderTime;
		}
		public void setOrderTime(Date orderTime) {
			this.orderTime = orderTime;
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
