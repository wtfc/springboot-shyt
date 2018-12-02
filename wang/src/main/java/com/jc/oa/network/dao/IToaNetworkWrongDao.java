package com.jc.oa.network.dao;


import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.network.domain.ToaNetworkWrong;
/**
 * @author mrb
 * @version 网络故障处理表
*/
public interface IToaNetworkWrongDao extends IBaseDao<ToaNetworkWrong> {

	List<ToaNetworkWrong> queryApp(ToaNetworkWrong toaNetworkWrong);
	
	
}
