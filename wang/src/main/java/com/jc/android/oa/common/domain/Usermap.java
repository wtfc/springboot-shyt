package com.jc.android.oa.common.domain;

import java.util.Date;
import java.math.BigDecimal;
import com.jc.system.common.util.DateUtils;
import com.jc.foundation.domain.BaseBean;


/**
 * @title 172.16.3.68
 * @description  实体类
 * @author 
 * @version  2014-09-23
 */

public class Usermap extends BaseBean{
	private static final long serialVersionUID = 1L;
	private Long userId;   /*用户ID*/
	private String userName;   /*用户登录名*/
	private String clientCode;   /*客户端手机标识号*/

	public Long  getUserId(){
		return userId;
	}
	
	public void setUserId(Long userId){
		this.userId = userId;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public void setUserName(String userName){
		this.userName = userName;
	}
	
	public String getClientCode(){
		return clientCode;
	}
	
	public void setClientCode(String clientCode){
		this.clientCode = clientCode;
	}
	
}