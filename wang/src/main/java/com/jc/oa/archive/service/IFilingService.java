package com.jc.oa.archive.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jc.foundation.service.IBaseService;
import com.jc.oa.archive.ArchiveException;
import com.jc.oa.archive.domain.Filing;

/**
 * @title  GOA2.0源代码
 * @description  业务接口类
 * @author 
 * @version  2014-07-09
 */

public interface IFilingService extends IBaseService<Filing>{

	/**
	 * 下载附件
	 * @param Filing filing 实体类
	 * @author 闻瑜
	 * @version  2014-07-10
	 * @throws ArchiveException 
	 */
	public void downLoad(Long id,String dmName, HttpServletRequest request,
			HttpServletResponse response) throws ArchiveException;
	
	/**
	 * 下载附件check
	 * @param Filing filing 实体类
	 * @author 闻瑜
	 * @version  2014-07-10
	 * @throws ArchiveException 
	 */
	public boolean downLoadCheck(Long id, HttpServletRequest request) throws ArchiveException;
	

	/**
	 * 下载附件check
	 * @param Filing filing 实体类
	 * @author 闻瑜
	 * @version  2014-07-10
	 * @throws ArchiveException 
	 */
	public void getDeleteFiling(List<Filing> file) throws ArchiveException;
}