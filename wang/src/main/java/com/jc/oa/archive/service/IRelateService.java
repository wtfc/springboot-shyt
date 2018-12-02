package com.jc.oa.archive.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.archive.domain.Relate;
import com.jc.oa.doc.DocException;

/**
 * @title  GOA2.0源代码
 * @description  业务接口类
 * @author 
 * @version  2014-06-05
 */

public interface IRelateService extends IBaseService<Relate>{
	/**
	 * 根据选择的文档批量保存关联关系方法
	 * @param Relate relate 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @throws DocException 
	 * @version  2014-07-01
	 */	
	public Integer saveByDocumentIds(Long documentId,String documentIds) throws DocException;
	
	public void deleteRelateDM(Relate relate);
}