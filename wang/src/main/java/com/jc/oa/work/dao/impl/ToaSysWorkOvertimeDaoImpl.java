package com.jc.oa.work.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.work.domain.ToaSysWorkOvertime;
import com.jc.oa.work.dao.IToaSysWorkOvertimeDao;
/**
 * @author mrb
 * @version 人员加班信息表
 */
@Repository
public class ToaSysWorkOvertimeDaoImpl extends BaseDaoImpl<ToaSysWorkOvertime> implements IToaSysWorkOvertimeDao{
	
	public ToaSysWorkOvertimeDaoImpl(){};
}
