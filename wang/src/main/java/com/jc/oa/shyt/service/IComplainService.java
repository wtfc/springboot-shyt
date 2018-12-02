package com.jc.oa.shyt.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.shyt.domain.Complain;
import com.jc.system.CustomException;

public interface IComplainService extends IBaseService<Complain>{
	
	public Integer deleteByIds(Complain complain) throws CustomException;

}
