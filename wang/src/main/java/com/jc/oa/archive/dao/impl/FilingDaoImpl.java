package com.jc.oa.archive.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.archive.dao.IFilingDao;
import com.jc.oa.archive.domain.Filing;

/**
 * @title  GOA2.0源代码
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-07-09
 */
@Repository
public class FilingDaoImpl extends BaseDaoImpl<Filing> implements IFilingDao{

	public FilingDaoImpl(){}

	/**
	 * @description 下载路径取得
	 * @param Filing filing 实体类
	 * @return List<Filing>
	 * @throws Exception 
	 * @author weny
	 * @version  2014-07-09
	 */
	public List<Filing> getDownLoad(Filing filing) {
		return template.selectList(getNameSpace(filing) + ".queryDownLoad", filing);
	}
	
	/**
	 * @description 销毁归档数据
	 * @param Filing filing 实体类
	 * @return List<Filing>
	 * @throws Exception 
	 * @author weny
	 * @version  2014-07-15
	 */
	public void getDeleteFiling(List<Filing> filingList) {
		for(int i=0;i<filingList.size();i++){
			Filing filing = filingList.get(i);
			template.delete(getNameSpace(filing) + ".deleteList", filing);
		}
	}
}