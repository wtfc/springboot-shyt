package com.jc.system.content.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.system.content.domain.Attach;
import com.jc.system.content.dao.IAttachDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;

/**
 * @title GOA2.0
 * @description  dao实现类
 * @author 
 * @version  2014-04-17
 */
@Repository
public class AttachDaoImpl extends BaseDaoImpl<Attach> implements IAttachDao{
	/**
	 * 使用业务id拼接的字符串查询所有的附件
	 * 
	 * @param attach
	 * @throws Exception
	 */
	@Override
	public List<Attach> queryAttachByBusinessIds(Attach attach) throws Exception {
		return  template.selectList(getNameSpace(attach) + ".queryAttachByBusinessIds", attach);
	}
}