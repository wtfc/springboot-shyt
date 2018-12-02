package com.jc.system.number.service.interfaces;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * 
 * @description: service接口 
 * @created: 2013-4-1 下午3:27:43 
 */
@WebService
public interface INumber {

	@WebMethod public String getNumber(String ruleName, int count, int type, String [] parameter);
	
	@WebMethod public int setNumberState(String ruleName, int state);
}
