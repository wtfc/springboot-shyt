package com.jc.system.content.domain;

import java.util.Date;
import java.math.BigDecimal;
import com.jc.system.common.util.DateUtils;
import com.jc.foundation.domain.BaseBean;


/**
 * @title GOA2.0
 * @description 附件业务关系表 实体类
 * @author 
 * @version  2014-05-21
 */

public class AttachBusiness extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long attachId;   /*附件ID*/
	private Long businessId;   /*业务ID*/
	private String businessTable;   /*业务表名*/
	private String businessSource;   /*业务来源  0-OA  1-CRM*/

	public Long getAttachId(){
		return attachId;
	}
	
	public void setAttachId(Long attachId){
		this.attachId = attachId;
	}
	
	public Long getBusinessId(){
		return businessId;
	}
	
	public void setBusinessId(Long businessId){
		this.businessId = businessId;
	}
	
	public String getBusinessTable(){
		return businessTable;
	}
	
	public void setBusinessTable(String businessTable){
		this.businessTable = businessTable;
	}
	
	public String getBusinessSource(){
		return businessSource;
	}
	
	public void setBusinessSource(String businessSource){
		this.businessSource = businessSource;
	}
	
}