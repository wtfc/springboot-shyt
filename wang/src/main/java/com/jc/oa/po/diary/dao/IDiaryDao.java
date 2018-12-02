package com.jc.oa.po.diary.dao;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

import com.jc.foundation.dao.IBaseDao;
import com.jc.foundation.domain.PageManager;
import com.jc.oa.po.diary.domain.Diary;


/**
 * @title 个人办公
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 金峰
 * @version  2014-04-28
 */
 
public interface IDiaryDao extends IBaseDao<Diary>{
	
	/** 方法描述：关联查询日程表和批注表
	 * @param diary
	 * @return List<Diary>
	 * @author 金峰
	 * @version  2014年5月14日下午4:32:49
	 * @see
	 */
	public List<Diary> queryDiaryAnno(Diary diary);
	
	/** 方法描述：查询日程接口供日志管理中使用
	 * @param diary
	 * @return
	 * @author 金峰
	 * @version  2014年5月15日上午11:32:06
	 * @see
	 */
	public List<Diary> queryInterface(Diary diary);
	
	/** 方法描述：查询列表视图信息
	 * @param diary
	 * @return List<Diary>
	 * @author 金峰
	 * @version  2014年5月15日上午11:32:06
	 * @see
	 */
	public List<Diary> queryForListView(Diary diary);
	
	/** 方法描述：查询共享日程
	 * @param diary
	 * @return List<Diary>
	 * @author 金峰
	 * @version  2014年5月26日下午1:27:55
	 * @see
	 */
	public List<Diary> queryDiaryShare(Diary diary);

	PageManager queryByPage(Diary o, PageManager page, String countSQL,
			String querySQL);
}
