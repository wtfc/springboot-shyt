package com.jc.shjfgl.machine.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.shjfgl.machine.domain.Topiao;

public interface ITopiaoDao extends IBaseDao<Topiao>{
	
	//个人分数统计结果
	public List<Topiao> finish(Topiao topiao);

}
