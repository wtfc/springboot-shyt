package com.jc.oa.shyt.service;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.shyt.domain.OutContract;
import com.jc.system.CustomException;

public interface IOutContractService extends IBaseService<OutContract>{
	
	public Integer deleteByIds(OutContract outContract) throws CustomException;
	
	public Integer save(OutContract outContract)throws CustomException ;
	public Integer update(OutContract outContract)throws CustomException;

}
