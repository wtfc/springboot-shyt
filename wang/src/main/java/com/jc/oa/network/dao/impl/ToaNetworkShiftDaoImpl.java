package com.jc.oa.network.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkShift;
import com.jc.oa.network.dao.IToaNetworkShiftDao;
/**
 * @author mrb
 * @version 交接班记录表
 */
@Repository
public class ToaNetworkShiftDaoImpl extends BaseDaoImpl<ToaNetworkShift> implements IToaNetworkShiftDao{
	
	public ToaNetworkShiftDaoImpl(){}

	@Override
	public Integer state(ToaNetworkShift toaNetworkShift) {
		// TODO Auto-generated method stub
		return template.update(getNameSpace(toaNetworkShift)+"."+"state",toaNetworkShift);
	};
}
