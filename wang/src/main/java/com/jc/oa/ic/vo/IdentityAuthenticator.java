package com.jc.oa.ic.vo;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class IdentityAuthenticator extends Authenticator {
	private String userName = null;
	private String password = null;

	public IdentityAuthenticator() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public IdentityAuthenticator(String userName, String password) {
		super();
		setUserName(userName);
		setPassword(password);
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(userName, password);
	}

}
