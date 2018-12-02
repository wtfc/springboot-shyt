package com.jc.android.oa.common.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.jc.android.oa.ic.domain.Attach4M;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.system.content.service.IUploadService;

/**
 * 
 * @title GOA V2.0
 * @description 
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * @version  2014年9月25日
 */
@Controller("androidCommonController")
@RequestMapping(value="/android/common")
public class CommonController{
	
	@Autowired
	private IUploadService uploadService;

	/**
	 * 文件上传接口 工单使用
	 * 
	 * @param files
	 *            附件
	 * @param businessId
	 *            业务ID（工单ID）
	 * @param businessSource
	 *            业务来源（1、重启操作）
	 * @param request
	 *            请求对象
	 * @return Map<"code",状态值> 200 正常 500 异常 <"id",插入成功后的数据库编号> 成功返回编号 不成功返回空
	 */
	@RequestMapping(value = "fileupload.action")
	@ResponseBody
	public Map<String, Object> upload(@RequestParam(value = "files") CommonsMultipartFile  files,Integer businessId,String businessSource,HttpServletRequest request,HttpServletResponse response) throws Exception {
		String method = request.getMethod() ;
		Map<String, Object> resultMap = new HashMap<String, Object>();
	    response.setContentType("text/plain");
		if("POST".equals(method.toUpperCase())){
			ToaMachineAttach attach = uploadService.uploadFileForRestart(request, 2,"android",businessId,businessSource);
			Attach4M attach4m = new Attach4M();
			attach4m.setId(attach.getId());
			attach4m.setFileName(attach.getFileName());
			attach4m.setFileSize(attach.getFileSize());
			attach4m.setResourcesName(attach.getResourcesName());
			resultMap.put("data", attach4m);
			resultMap.put("state", "success");
		}
		return resultMap;
	}
}
