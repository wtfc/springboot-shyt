package com.jc.shjfgl.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.shjfgl.machine.domain.Exchange;
import com.jc.system.CustomException;

public interface IExchangeService extends IBaseService<Exchange>{

	public Integer deleteByIds(Exchange exchange)throws CustomException;
}
