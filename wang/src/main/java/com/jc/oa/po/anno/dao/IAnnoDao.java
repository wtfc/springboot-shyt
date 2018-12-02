package com.jc.oa.po.anno.dao;

import java.util.List;

import com.jc.foundation.dao.IBaseDao;
import com.jc.oa.po.anno.domain.Anno;


/**
 * @title 个人办公
 * @description  dao接口类
 * Copyright (c) 2014 Jiachengnet.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 
 * @version  2014-04-29
 */
 
public interface IAnnoDao extends IBaseDao<Anno>{
	
	/**
	 * 方法描述：查询批注回复
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日上午9:12:07
	 * @see
	 */
	public List<Anno> queryAnnoRaply(Anno anno);
	
	/**
	 * 方法描述：查询根批注
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月14日上午11:16:14
	 * @see
	 */
	public List<Anno> queryRootAnno(Anno anno);
	
	/**
	 * 方法描述：
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年5月28日上午8:53:27
	 * @see
	 */
	public Integer annoReading(Anno anno);
	
	/**
	 * 方法描述：被批注的日志，当修改日志标题时同步修改批注文件名称
	 * @param anno   传入businessId和修改后的标题以及类型（0计划、1日程、2日志、3督办）
	 * @return
	 * @author 徐伟平
	 * @version  2014年10月30
	 * @throws Exception 
	 * @see
	 */
	public boolean updateAnnoName(Anno anno) throws Exception;
	
	/**
	 * 方法描述：根据业务id逻辑删除批注
	 * @param anno
	 * @return
	 * @author 李新桐
	 * @version  2014年6月12日上午11:13:25
	 * @see
	 */
	public Integer deleteByBusinessId(Anno anno);
}
