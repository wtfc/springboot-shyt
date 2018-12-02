package com.jc.shjfgl.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.shjfgl.machine.domain.View;
import com.jc.system.CustomException;

public interface IViewService extends IBaseService<View>{
	
	public Integer deleteByIds(View view)throws CustomException;

}
