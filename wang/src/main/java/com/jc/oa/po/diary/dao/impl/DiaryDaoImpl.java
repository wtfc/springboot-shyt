package com.jc.oa.po.diary.dao.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.util.ReflectHelper;
import com.jc.oa.po.diary.dao.IDiaryDao;
import com.jc.oa.po.diary.domain.Diary;

/**
 * @title 个人办公
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 金峰
 * @version  2014-04-28
 */
@Repository
public class DiaryDaoImpl extends BaseDaoImpl<Diary> implements IDiaryDao{
	
	/**
	 * 条件查询SQL ID
	 */
//	public static final String SQL_UPDATE_BY_IDS= "updatePeriod";
	public static final String SQL_QUERY_DIARY_ANNO= "queryDiaryAndAnno";
	public static final String SQL_QUERY_DIARY_INTERFACE= "queryInterface";
	public static final String SQL_QUERY_DIARY_LIST_VIEW= "queryForListView";
	public static final String SQL_QUERY_DIARY_SHARE= "queryDiaryShare";
	
	public DiaryDaoImpl(){}

	/** 方法描述：关联查询日程表和批注表
	 * @param diary
	 * @return List<Diary>
	 * @author 金峰
	 * @version  2014年5月14日下午4:32:49
	 * @see
	 */
	@Override
	public List<Diary> queryDiaryAnno(Diary diary) {
		List<Diary> list = template.selectList(getNameSpace(diary) + "."+SQL_QUERY_DIARY_ANNO, diary);
		return list;
	}

	/** 方法描述：查询日程接口供日志管理中使用
	 * @param diary
	 * @return
	 * @author 金峰
	 * @version  2014年5月15日上午11:32:06
	 * @see
	 */
	@Override
	public List<Diary> queryInterface(Diary diary) {
		List<Diary> list = template.selectList(getNameSpace(diary) + "."+SQL_QUERY_DIARY_INTERFACE, diary);
		return list;
	}

	/** 方法描述：查询列表视图信息
	 * @param diary
	 * @return List<Diary>
	 * @author 金峰
	 * @version  2014年5月15日上午11:32:06
	 * @see
	 */
	@Override
	public List<Diary> queryForListView(Diary diary) {
		List<Diary> list = template.selectList(getNameSpace(diary) + "."+SQL_QUERY_DIARY_LIST_VIEW, diary);
		return list;
	}

	/** 方法描述：查询共享日程
	 * @param diary
	 * @return List<Diary>
	 * @author 金峰
	 * @version  2014年5月26日下午1:27:55
	 * @see
	 */
	@Override
	public List<Diary> queryDiaryShare(Diary diary) {
		List<Diary> list = template.selectList(getNameSpace(diary) + "."+SQL_QUERY_DIARY_SHARE, diary);
		return list;
	}
	
	@Override
	public PageManager queryByPage(Diary o,final PageManager page,String countSQL,String querySQL){
		return super.queryByPage(o, page, countSQL, querySQL);
	}
}