package com.jc.shjfgl.machine.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.shjfgl.machine.dao.ITopiaoDao;
import com.jc.shjfgl.machine.domain.Topiao;

@Repository
public class TopiaoDaoImpl extends BaseDaoImpl<Topiao> implements ITopiaoDao{

	public TopiaoDaoImpl(){}

	@Override
	public List<Topiao> finish(Topiao topiao) {
		return template.selectList(getNameSpace(topiao)+"."+"pingjunfen", topiao);
	}
}
