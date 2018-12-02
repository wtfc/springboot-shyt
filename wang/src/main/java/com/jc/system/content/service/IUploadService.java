package com.jc.system.content.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.system.CustomException;
import com.jc.system.content.domain.Attach;

/**
 * @description:上传资源业务接口
 */
public interface IUploadService {
	/**
	 * 文件上传接口
	 * 
	 * @param request
	 *            请求对象
	 * @param userId
	 *            上传人编号
	 * @return Map<"code",状态值> 200 正常 500 异常 <"id",插入成功后的数据库编号> 成功返回编号 不成功返回空
	 */
	public Attach uploadFile(HttpServletRequest request,
			Integer userId,String category);
	
	/**
	 * 文件上传接口 工单使用
	 * 
	 * @param request
	 *            请求对象
	 * @param userId
	 *            上传人编号
	 * @param businessId
	 *            业务ID
	 * @param businessSource
	 *            业务来源
	 * @return Map<"code",状态值> 200 正常 500 异常 <"id",插入成功后的数据库编号> 成功返回编号 不成功返回空
	 */
	public ToaMachineAttach uploadFileForRestart(HttpServletRequest request,
			Integer userId,String category,Integer businessId,String businessSource);
	
	public Attach userPhotoUploadFile(HttpServletRequest request,
			Long userId);
	
	public Attach portletPhotoUploadFile(HttpServletRequest request,
			Long userId);
	public Attach bookPhotoUploadFile(HttpServletRequest request,
			Long bookId);
	/**
	 * 文件下载接口
	 * 
	 * @param uploadtype
	 *            下载目标 是 ftp 还是相对项目路径 uploadtype="" 相对项目路径 uploadtype="ftp"
	 *            为上传到ftp 请注意上传ftp的配置文件是否存在
	 * @param filename 
	 *            文件名
	 * @param resourcesname
	 * 			      相对文件路径和文件名           
	 * @param response
	 *            请求响应
	 * @param request
	 *            请求对象
	 */
	public void downloadFile(String uploadtype, String filename,String resourcesname,
			HttpServletResponse response, HttpServletRequest request);

	/**
	 * 删除已上传的文件以及数据库实体记录
	 * 
	 * @param request
	 * @param uploadtype
	 * @param paths
	 * @param ids
	 * @return
	 */
	public Boolean deleteFileByIds(String ids);
	
	public Boolean deleteUserPhoto(HttpServletRequest request, String url);
	
	public void deleteAttach(Long id) throws CustomException;
	
	public Boolean deleteBookPhoto(HttpServletRequest request, String url);
	
	public Boolean deleteAllPhoto(HttpServletRequest request, String url);
	
	public void getThumbnail(Long id,HttpServletRequest request, HttpServletResponse response) throws CustomException;
	
	public void getOriginalImg(Long id,HttpServletResponse response) throws CustomException;

	public Attach portalPicUploadFile(HttpServletRequest request, Long id);
	
	public List<Attach> copyAttachList(HttpServletRequest request, List<Attach> list);
	
	public List<Attach> copyAttachAndTextList(HttpServletRequest request, List<Attach> list, String businessId);
}
