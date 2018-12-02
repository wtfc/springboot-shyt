package com.jc.oa.common.dao.impl;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.common.dao.IRemindDao;
import com.jc.oa.common.domain.Remind;
import com.jc.system.CustomException;

/**
 * @title  GOA2.0源代码
 * @description  dao实现类
 * @author 
 * @version  2014-04-17
 */
@Repository
public class RemindDaoImpl extends BaseDaoImpl<Remind> implements IRemindDao{


	public List<Remind> getNextRemind() throws Exception {
		long currentTime = Calendar.getInstance().getTimeInMillis();
		return template.selectList("com.jc.oa.common.domain.Remind.getNextReminds", currentTime);
	}

	@Override
	public void batchUpdate(List<Remind> list) throws Exception {
		template.update("com.jc.oa.common.domain.Remind.batchUpdate", list);
	}
	
	 /**
     * 根据业务id和类型获取设定的提示
     * @param remind
     * @return
     * @throws CustomException
     */
    public Remind getRemindByDataIdAndType(Remind remind) throws Exception {
    	return template.selectOne("com.jc.oa.common.domain.Remind.getRemindByDataIdAndType", remind);
    }
    /**
     * 删除提醒
     * @param remind
     * @return
     * @throws CustomException
     */
    public void deleteRemindByDataIdAndTable(Remind remind) throws Exception {
    	//return template.selectOne("com.jc.oa.common.domain.Remind.deleteByTableAndIds", remind);
    	template.delete("com.jc.oa.common.domain.Remind.deleteByTableAndIds", remind);
    }
    /**
     * 根据业务id和类型更新设定的提示
     * @param remind
     * @return
     * @throws CustomException
     */
    public int updateRemindByDataIdAndType(Remind remind) throws Exception {
    	return template.update("com.jc.oa.common.domain.Remind.updateRemindByDataIdAndType", remind);
    }
    /**
     * 根据业务id和类型获取设定的提示数量
     * @param remind
     * @return
     * @throws CustomException
     */
    public int getRemindCountByDataIdAndType(Remind remind) throws Exception {
    	return template.selectOne("com.jc.oa.common.domain.Remind.getRemindCountByDataIdAndType", remind);
    }
    
    public void updateNextTimeToMax(Remind bean) {
    	template.update("com.jc.oa.common.domain.Remind.updateNextTimeToMax", bean);
    }
}