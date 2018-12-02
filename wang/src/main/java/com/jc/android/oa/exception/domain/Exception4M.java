package com.jc.android.oa.exception.domain;

import java.util.Date;

import org.apache.log4j.Logger;

import com.jc.foundation.domain.BaseBean;

/**
 * @title goa2.0 
 * @author 
 * @version  2014-12-10
 */

public class Exception4M extends BaseBean{
	
	protected transient final Logger log = Logger.getLogger(this.getClass());
	
	private static final long serialVersionUID = 1L;
	private Date exceTime;   /**/
	private Date exceTimeBegin;   /*开始*/
	private Date exceTimeEnd;   /*结束*/
	private String exceDetail;   /**/
	private Long userId;   /**/

	public Date getExceTime(){
		return exceTime;
	}
	
	public void setExceTime(Date exceTime){
		this.exceTime = exceTime;
	}
	
	public Date getExceTimeBegin(){
		return exceTimeBegin;
	}
	
	public void setExceTimeBegin(Date exceTimeBegin){
		this.exceTimeBegin = exceTimeBegin;
	}
	
	public Date getExceTimeEnd(){
		return exceTimeEnd;
	}
	
	public void setExceTimeEnd(Date exceTimeEnd){
	   
		this.exceTimeEnd = exceTimeEnd;
	}
	
	
	public String getExceDetail(){
		return exceDetail;
	}
	
	public void setExceDetail(String exceDetail){
		this.exceDetail = exceDetail;
	}
	
	
	
	public Long getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
}