package com.jc.oa.po.anno.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.jc.foundation.dao.impl.BaseDaoImpl;
import com.jc.oa.po.anno.dao.IAnnoDao;
import com.jc.oa.po.anno.domain.Anno;

/**
 * @title 个人办公
 * @description  dao实现类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */
@Repository
public class AnnoDaoImpl extends BaseDaoImpl<Anno> implements IAnnoDao{
	/**
	 * 查询批注回复 SQL ID
	 */
	public static final String SQL_QUERYANNO_RAPLY= "queryAnnoRaply";
	/**
	 * 查询根批注 SQL ID
	 */
	public static final String SQL_QUERYROOTANNO= "queryRootAnno";
	/**
	 * 更新批注的阅读状态
	 */
	public static final String SQL_UPDATE_ANNOREADING= "updateAnnoReading";
	/**
	 * 根据业务id逻辑删除批注
	 */
	public static final String SQL_DELETE_BY_BUSINESSID= "deleteByBusinessId";
	
	/**
	 * 根据业务id更新批注文件名称
	 */
	public static final String SQL_UPDATE_ANNONAME= "updateAnnoName";
	
	public AnnoDaoImpl(){}
	
	/**
	 * 方法描述：查询批注回复
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日上午9:12:07
	 * @see
	 */
	@Override
	public List<Anno> queryAnnoRaply(Anno anno){
		return template.selectList(getNameSpace(anno) + "."+SQL_QUERYANNO_RAPLY,anno);
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
	public List<Anno> queryRootAnno(Anno anno){
		return template.selectList(getNameSpace(anno) + "."+SQL_QUERYROOTANNO,anno);
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
		return template.update(getNameSpace(anno) + "."+SQL_UPDATE_ANNOREADING, anno);
	}
	
	/**
	 * 方法描述：被批注的日志，当修改日志标题时同步修改批注文件名称
	 * @param anno   传入businessId和修改后的标题以及类型（0计划、1日程、2日志、3督办）
	 * @return 更新出现错误时，返回false，其他返回true
	 * @author 徐伟平
	 * @version  2014年10月30
	 * @see
	 */
	public boolean updateAnnoName(Anno anno) throws Exception{
		try {
			template.update(getNameSpace(anno) + "."+SQL_UPDATE_ANNONAME, anno);
		} catch (Exception e) {
			return false;
		}
		return true;
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
		return template.update(getNameSpace(anno) + "."+SQL_DELETE_BY_BUSINESSID, anno);
	}
}