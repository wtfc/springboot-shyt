package com.jc.system.security.exception;

import org.apache.shiro.authc.AuthenticationException;

public class UserIpException extends AuthenticationException{

private static final long serialVersionUID = 1L;
	
	public UserIpException(){
		super();
	}
	
	public UserIpException(String message){
		super(message);
	}
}
