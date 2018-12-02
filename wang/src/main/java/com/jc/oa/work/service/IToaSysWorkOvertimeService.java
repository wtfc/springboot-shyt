package com.jc.oa.work.service;

import com.jc.foundation.service.IBaseService;
import com.jc.system.CustomException;
import com.jc.oa.work.domain.ToaSysWorkOvertime;
/**
 * @author mrb
 * @version 人员加班信息表
*/
public interface IToaSysWorkOvertimeService extends IBaseService<ToaSysWorkOvertime>{

	public Integer deleteByIds(ToaSysWorkOvertime toaSysWorkOvertime) throws CustomException;
}
