package com.jc.system.specialData.domain;

import org.apache.log4j.Logger;

import com.jc.foundation.domain.BaseBean;


/**
 * @title 172.16.3.68
 * @description  实体类
 * @author 
 * @version  2014-12-02
 */

public class SpecialdataShare extends BaseBean{
	
	protected transient final Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
	private Long specialdataId;   /*特殊日期Id*/
	private Long shareDept;   /*共享部门*/
	private Long shareUsers;   /*共享人员*/
	private Long shareOrg;   /*共享机构*/

	public Long getSpecialdataId(){
		return specialdataId;
	}
	
	public void setSpecialdataId(Long specialdataId){
		this.specialdataId = specialdataId;
	}
	
	public Long getShareDept(){
		return shareDept;
	}
	
	public void setShareDept(Long shareDept){
		this.shareDept = shareDept;
	}
	
	public Long getShareUsers(){
		return shareUsers;
	}
	
	public void setShareUsers(Long shareUsers){
		this.shareUsers = shareUsers;
	}
	
	public Long getShareOrg(){
		return shareOrg;
	}
	
	public void setShareOrg(Long shareOrg){
		this.shareOrg = shareOrg;
	}
	
}