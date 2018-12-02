package com.jc.oa.network.dao;


import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.network.domain.ToaNetworkShift;
/**
 * @author mrb
 * @version 交接班记录表
*/
public interface IToaNetworkShiftDao extends IBaseDao<ToaNetworkShift> {
	
	public Integer state(ToaNetworkShift toaNetworkShift);
}
