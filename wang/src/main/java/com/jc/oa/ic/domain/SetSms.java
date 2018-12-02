package com.jc.oa.ic.domain;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.util.CustomDatetimeSerializer;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;
import com.jc.system.security.util.UserUtils;

/**
 * @title HR
 * @description 短信设置表 实体类 Copyright (c) 2014 Jiachengnet.com Inc. All Rights
 *              Reserved Company 长春嘉诚网络工程有限公司
 * @author
 * @version 2014-04-29
 */

public class SetSms extends BaseBean {
	private static final long serialVersionUID = 1L;
	private String type; /* 短信类型. "O"普通短信, "W" wappush 收件人手机号码，前面不要加"+" 或者0 */
	private String rankId; /* 当类别选择了级别时，该字段存储的是选择的职位ID，如果是个人那么存储的是个人的ID */
	private Date startDate; /* 更改设置的日期,以该时间为起点，按月统计数量 */
	private Integer maxnum; /* 个人或职务发送最大短信数量 */
	private String setType;/*设置类别 1 级别 ，0个人*/
	
	//字典转换
	private IDicManager dicManager = new DicManagerImpl();
	
	private String controlUserName;/*操作人名称*/
	private Date setDateStart;/*开始时间*/
	private Date setDateEnd;/*结束时间*/
	private String statisticsType;/*统计取得修改前设置类别*/
	private String statisticsRank;/*统计取得修改前设置rankId*/
	private String rankIdSelect;/*级别字典值*/
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRankId() {
		return rankId;
	}
	
	public void setRankId(String rankId) {
		this.rankId = rankId;
	}
	@JsonSerialize(using = CustomDatetimeSerializer.class)
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Integer getMaxnum() {
		return maxnum;
	}

	public void setMaxnum(Integer maxnum) {
		this.maxnum = maxnum;
	}

	public String getSetType() {
		return setType;
	}

	public void setSetType(String setType) {
		this.setType = setType;
	}

	public String getControlUserName() {
		String userName = "";
		if(super.getCreateUser()>0&&UserUtils.getUser(super.getCreateUser())!=null){
			userName = UserUtils.getUser(super.getCreateUser()).getDisplayName();
		}
		return userName;
	}
	public String getRankName(){
		String  rankName="";
		if(this.getRankId()!=null&&!"".equals(this.getRankId())){
			//0，代表级别
			if("0".equals(this.getSetType())){
				if(dicManager.getDic("level", this.getRankId())!=null){
					rankName =  dicManager.getDic("level", this.getRankId()).getValue();
				}
			//1，代表个人
			}else  if("1".equals(this.getSetType())){
				if(UserUtils.getUser(Long.valueOf(this.getRankId()).longValue())!=null){
					rankName = UserUtils.getUser(Long.valueOf(this.getRankId()).longValue()).getDisplayName();
				}
			}
		}
		return rankName;
		
	}

	public Date getSetDateStart() {
		return setDateStart;
	}

	public void setSetDateStart(Date setDateStart) {
		this.setDateStart = setDateStart;
	}

	public Date getSetDateEnd() {
		return setDateEnd;
	}

	public void setSetDateEnd(Date setDateEnd) {
		this.setDateEnd = setDateEnd;
	}

	public String getStatisticsType() {
		return statisticsType;
	}

	public void setStatisticsType(String statisticsType) {
		this.statisticsType = statisticsType;
	}

	public String getStatisticsRank() {
		return statisticsRank;
	}

	public void setStatisticsRank(String statisticsRank) {
		this.statisticsRank = statisticsRank;
	}

	public String getRankIdSelect() {
		return rankIdSelect;
	}

	public void setRankIdSelect(String rankIdSelect) {
		this.rankIdSelect = rankIdSelect;
	}
	
	
}