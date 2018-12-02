package com.jc.oa.archive.service;

import javax.servlet.http.HttpServletRequest;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.domain.Audithis;
import com.jc.system.CustomException;

/**
 * @title  GOA2.0源代码
 * @description  业务接口类
 * @author 
 * @version  2014-06-30
 */

public interface IAudithisService extends IBaseService<Audithis>{
	/**
	 * 方法描述：
	 * @param request 
	 * @param dataId 操作对象ID
	 * @param dataType 操作对象类型：0 文件夹，1 文档
	 * @param auditType 审计类型
	 * @param desc 描述
	 * @throws ArchiveException
	 * @author zhangligang
	 * @version  2014年7月1日上午10:50:29
	 * @see
	 */
	public void audithis(HttpServletRequest request, Long dataId,String dataName, Integer dataType,
			String auditType, String desc) throws ArchiveException;
	
	
	PageManager queryByPermission(Audithis audithis, PageManager page);
}