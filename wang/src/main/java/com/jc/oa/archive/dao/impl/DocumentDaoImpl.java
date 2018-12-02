package com.jc.oa.archive.dao.impl;

import org.springframework.stereotype.Repository;

import com.jc.oa.archive.domain.Document;
import com.jc.oa.archive.dao.IDocumentDao;
import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.foundation.domain.PageManager;

/**
 * @title  GOA2.0源代码
 * @description  dao实现类
 * @author 
 * @version  2014-06-05
 */
@Repository
public class DocumentDaoImpl extends BaseDaoImpl<Document> implements IDocumentDao{

	public DocumentDaoImpl(){}

	@Override
	public int deleteDocToRecycle(Document doc) {
		return template.update(getNameSpace(doc)+".deleteDocToRecycle", doc);
	}

	@Override
	public String getSeq(Document d) {
		if(1 == d.getModel()) {
			//my document
			return template.selectOne(getNameSpace(new Document())+".getMySeq",d);
		} else {
			return template.selectOne(getNameSpace(new Document())+".getSeq",d);
		}
	}
	
	/**
	 * @description 根据文档id获取未关联文档的集合
	 * @param Document 实体类
	 * @param page 分页信息 
	 * @return map：list 查询的记录 ，page分页信息
	 * @author 
	 * @version 2014-07-02
	 */
	@Override
	public PageManager queryByRelate(Document document,final PageManager page) {
		return queryByPage(document,page,"queryByRelateCount","queryByRelate");
	}
	
	/**
	 * @description 销毁归档数据
	 * @param Document doc 实体类
	 * @throws Exception 
	 * @author weny
	 * @version  2014-07-15
	 */
	public int deleteList(Document doc) {
		return template.delete(getNameSpace(doc)+".deleteList", doc);
	}

}