package com.jc.oa.network.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkShift;
/**
 * @author mrb
 * @version 交接班记录表
*/
public interface IToaNetworkShiftService extends IBaseService<ToaNetworkShift>{

	public Integer deleteByIds(ToaNetworkShift toaNetworkShift) throws CustomException;
	
	public Integer save(ToaNetworkShift toaNetworkShift)throws CustomException ;
	public Integer update(ToaNetworkShift toaNetworkShift)throws CustomException;
	
	public Integer state(ToaNetworkShift toaNetworkShift)throws CustomException;
}
