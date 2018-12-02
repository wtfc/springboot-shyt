package com.jc.system.security.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.security.dao.ISettingDao;
import com.jc.system.security.domain.Setting;
import com.jc.system.security.service.ISettingService;
import com.jc.system.security.util.SettingUtils;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-04-28
 */
@Service
public class SettingServiceImpl extends BaseServiceImpl<Setting> implements ISettingService{

	private ISettingDao settingDao;
	
	public SettingServiceImpl(){}
	
	@Autowired
	public SettingServiceImpl(ISettingDao settingDao){
		super(settingDao);
		this.settingDao = settingDao;
	}

	/**
	 * @description 获取单条记录方法(系统参数配置为统一的一个)
	 * @param  Setting setting 实体类
	 * @return Setting 查询结果
	 * @author chz
	 * @version 2014-04-28
	 */
	public Setting getOne(Setting setting) {
		Setting settingvo = new Setting();
		List<Setting> templist = settingDao.queryAll(setting);
		if(templist != null && templist.size() > 0){
			settingvo = templist.get(0);
		}
		return settingvo;
	}
	
	/**
	 * @更新系统参数（刷新内存）
	 * @param  Setting setting 实体类
	 * @return Setting 查询结果
	 * @author chz
	 * @version 2014-04-28
	 */
	@Override
	public Integer update(Setting t) throws CustomException {
		Integer resutl = super.update(t);
		SettingUtils.refresh();
		return resutl;
	}

}