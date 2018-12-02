package com.jc.oa.po.anno.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.po.anno.dao.IAnnoDao;
import com.jc.oa.po.anno.domain.Anno;
import com.jc.oa.po.anno.service.IAnnoService;

/**
 * @title 个人办公
 * @description  业务服务类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */
@Service
public class AnnoServiceImpl extends BaseServiceImpl<Anno> implements IAnnoService{

	private IAnnoDao annoDao;
	
	public AnnoServiceImpl(){}
	
	@Autowired
	public AnnoServiceImpl(IAnnoDao annoDao){
		super(annoDao);
		this.annoDao = annoDao;
	}
	
	/**
	 * 方法描述：查询包括回复的批注
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月13日下午8:28:01
	 * @see
	 */
	@Override
	public List<Anno> queryAnnoAndReply(Anno anno){
		List<Anno> annoList= annoDao.queryRootAnno(anno);
		for (Anno annoTemp : annoList) {
			Anno a = new Anno();
			a.setRootParentId(annoTemp.getId());
			annoTemp.setAnnoReplyList(queryAnnoReply(a));
		}
		return annoList;
	}
	
	/**
	 * 方法描述：查询批注的回复
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月13日下午8:28:01
	 * @see
	 */
	@Override
	public List<Anno> queryAnnoReply(Anno anno){
		return annoDao.queryAnnoRaply(anno);
	}
	
	/**
	 * 方法描述：查询根批注
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日上午11:16:14
	 * @see
	 */
	@Override
	public List<Anno> queryRootAnno(Anno anno) {
		return annoDao.queryRootAnno(anno);
	}

	/**
	 * 方法描述：
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日上午8:53:27
	 * @see
	 */
	public Integer annoReading(Anno anno){
		return annoDao.annoReading(anno);
	}
	/**
	 * 方法描述：被批注的日志，当修改日志标题时同步修改批注文件名称
	 * @param anno   传入businessId和修改后的标题以及类型（0计划、1日程、2日志、3督办）
	 * @return
	 * @author 徐伟平
	 * @version  2014年10月30
	 * @throws Exception 
	 * @see
	 */
	public boolean updateAnnoName(Anno anno) throws Exception{
		return annoDao.updateAnnoName(anno);
	}
	/**
	 * 方法描述：根据业务id逻辑删除批注
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年6月12日上午11:13:25
	 * @see
	 */
	public Integer deleteByBusinessId(Anno anno){
		return annoDao.deleteByBusinessId(anno);
	}
}