package com.jc.system.security.exception;

import org.apache.shiro.authc.AuthenticationException;
/**
 * 
 * @title GOA V2.0
 * @description 
 * @version  2014年5月13日
 */
public class UserLockedException extends AuthenticationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserLockedException(){
		super();
	}
	
	public UserLockedException(String message){
		super(message);
	}

}
