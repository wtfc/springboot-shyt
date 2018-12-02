package com.jc.system.number.service.factory;

import com.jc.system.number.domain.NumberRule;


public abstract class SerialNumberFactory {

	public static SerialNumberFactory getInstance(int type) {
		SerialNumberFactory serialNumberFactory = null;
		if(type == 0) {
			serialNumberFactory = new NumberSerial();
		} else if(type == 1) {
			serialNumberFactory = new AlphabetSerial();
		}
		return serialNumberFactory;
	}

	public abstract String getSerialNumber(NumberRule numberRule) throws Exception;
}
