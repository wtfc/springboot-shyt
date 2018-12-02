package com.jc.shjfgl.machine.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.shjfgl.machine.domain.Topiao;
import com.jc.system.CustomException;

public interface ITopiaoService extends IBaseService<Topiao>{

	public Integer deleteByIds(Topiao topiao)throws CustomException;
	
	public List<Topiao> finish(Topiao topiao)throws CustomException;
}
