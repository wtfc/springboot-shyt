package com.jc.oa.network.service;

import java.util.List;

import com.jc.foundation.service.IBaseService;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
import com.jc.system.CustomException;
import com.jc.oa.network.domain.ToaNetworkWrong;
/**
 * @author mrb
 * @version 网络故障处理表
*/
public interface IToaNetworkWrongService extends IBaseService<ToaNetworkWrong>{

	public Integer deleteByIds(ToaNetworkWrong toaNetworkWrong) throws CustomException;

	public Integer jieDan(ToaNetworkWrong toaNetworkWrong) throws CustomException;

	public Integer daoChang(ToaNetworkWrong toaNetworkWrong) throws CustomException;

	public Integer operate(ToaNetworkWrong toaNetworkWrong) throws CustomException;

	public Integer renke(ToaNetworkWrong toaNetworkWrong) throws CustomException;

	public List<ToaNetworkWrong> queryApp(ToaNetworkWrong toaNetworkWrong) throws CustomException;

	public Integer pingJia(ToaNetworkWrong toaNetworkWrong) throws CustomException;

	public Integer save4M(ToaNetworkWrong toaNetworkWrong) throws CustomException;
}
