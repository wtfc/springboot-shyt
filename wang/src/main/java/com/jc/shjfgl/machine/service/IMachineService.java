package com.jc.shjfgl.machine.service;

import com.jc.foundation.service.IBaseService;
import com.jc.shjfgl.machine.domain.Machine;
import com.jc.system.CustomException;

public interface IMachineService  extends IBaseService<Machine>{

	public Integer deleteByIds(Machine machine)throws CustomException;

}
