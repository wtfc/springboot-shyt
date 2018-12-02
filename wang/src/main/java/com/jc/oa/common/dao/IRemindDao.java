package com.jc.oa.common.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.common.domain.Remind;
import com.jc.system.CustomException;


/**
 * @title  GOA2.0源代码
 * @description  dao接口类
 * @author 
 * @version  2014-04-17
 */
 
public interface IRemindDao extends IBaseDao<Remind>{

	public List<Remind> getNextRemind() throws Exception;
	void batchUpdate(List<Remind> list) throws Exception;
	
	 /**
     * 根据业务id和类型获取设定的提示
     * @param remind
     * @return
     * @throws CustomException
     */
    Remind getRemindByDataIdAndType(Remind remind) throws Exception;
	
    
    void deleteRemindByDataIdAndTable(Remind remind) throws Exception;
    /**
     * 根据类型及业务id更新提醒
     * @param remind
     * @return
     * @throws Exception
     */
    int updateRemindByDataIdAndType(Remind remind) throws Exception;
    
    /**
     * 根据业务id和类型获取设定的提示数量
     * @param remind
     * @return
     * @throws Exception
     */
    int getRemindCountByDataIdAndType(Remind remind) throws Exception;
    void updateNextTimeToMax(Remind bean);
}
