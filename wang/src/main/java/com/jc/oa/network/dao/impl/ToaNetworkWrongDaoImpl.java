package com.jc.oa.network.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.network.domain.ToaNetworkWrong;
import com.jc.oa.network.dao.IToaNetworkWrongDao;
/**
 * @author mrb
 * @version 网络故障处理表
 */
@Repository
public class ToaNetworkWrongDaoImpl extends BaseDaoImpl<ToaNetworkWrong> implements IToaNetworkWrongDao{
	
	public ToaNetworkWrongDaoImpl(){}

	@Override
	public List<ToaNetworkWrong> queryApp(ToaNetworkWrong toaNetworkWrong) {
		
		return template.selectList(getNameSpace(toaNetworkWrong)+"."+"queryApp", toaNetworkWrong);
	};
}
