package com.jc.oa.common.service;

import java.util.List;
import java.util.Map;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.common.domain.Remind;
import com.jc.oa.ic.IcException;
import com.jc.system.CustomException;

/**
 * @title  GOA2.0源代码
 * @description  业务接口类
 * @author 
 * @version  2014-04-17
 */

public interface IRemindService extends IBaseService<Remind>{
	
	List<Remind> getNextReminds() throws CustomException;
    /*void batchUpdate(List<Remind> list) throws CustomException;*/
    void remindByMode(List<Remind> list) throws CustomException;
    /**
     * 根据业务id和类型获取设定的提示
     * @param remind
     * @return
     * @throws CustomException
     */
    Remind getRemindByDataIdAndType(Remind remind) throws CustomException;
    
    /**
     * 保存提醒规则
     * 如在前台提醒规则整设置了tableName调用这个方法
     * @param remind
     * @param dataId
     * @return
     * @throws CustomException
     */
    Integer save(String remind, long dataId) throws CustomException;
    
    /**
     * 保存提醒规则
     * 如在前台提醒规则没有设置tableName调用这个方法
     * @param remind
     * @param dataId
     * @return
     * @throws CustomException
     */
    Integer save(String remind, long dataId, String tableName) throws CustomException;
    
    /**
     * 提供给其他模块的方法
     * 参数remind
     * 其中俩个属性必须有值
     * tableName
     * dataId
     * 获取提醒规则json格式数据
     * @param remind
     * @return
     */
    String getRemindJson(Remind remind);
    
    Integer saveByActive(String remind, long dataId, String tableName, boolean isActive) throws CustomException;
    
    
    void updateNextTimeToMax(Remind bean);
    
    /**
     * 方法描述：校验公共短信提醒
     * @param userIds
     * @param createUser
     * @return
     * @author 宋海涛
     * @version  2014年9月16日上午9:14:36
     * @see
     */
	Map<String, Object> validRemind(String userIds, String createUser)throws IcException;
	
	/**根据表名及dataid删除提醒信息
	 * @param remind
	 * @throws Exception
	 */
	public void deleteRemindByDataIdAndTable(Remind remind) throws Exception;
}