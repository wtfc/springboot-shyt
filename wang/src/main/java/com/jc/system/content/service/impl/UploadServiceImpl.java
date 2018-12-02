package com.jc.system.content.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jc.foundation.domain.BaseBean;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.domain.ToaMachineAttach;
import com.jc.oa.machine.service.IToaMachineAttachService;
import com.jc.system.CustomException;
import com.jc.system.common.util.CharConventUtils;
import com.jc.system.common.util.FileUtil;
import com.jc.system.common.util.FtpUploader;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.Identities;
import com.jc.system.common.util.ZipFile;
import com.jc.system.content.UploadConstants;
import com.jc.system.content.UploadUtils;
import com.jc.system.content.domain.Attach;
import com.jc.system.content.service.IAttachService;
import com.jc.system.content.service.IUploadService;
import com.jc.system.portal.service.IPortletService;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.util.SettingUtils;
/**
 * @description:上传资源业务实现类
 */
@Service
public class UploadServiceImpl extends BaseServiceImpl<BaseBean> implements IUploadService {
	
	private static List<String> imgExt = new ArrayList<>();
	static {
		imgExt.add("png");
		imgExt.add("jpg");
		imgExt.add("bmp");
		imgExt.add("jpeg");
		imgExt.add("jpe");
		imgExt.add("gif");
	}
	private String uploadType = "";
	
	private String uploadBaseDir = null;
	
	public String getUploadBaseDir() {
		return String.valueOf(SettingUtils.getSetting(SettingUtils.FILE_PATH));
	}

	public void setUploadBaseDir(String uploadBaseDir) {
		this.uploadBaseDir = uploadBaseDir;
	}

	public UploadServiceImpl(){

	}


	@Autowired
	private IAttachService attachService ;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPortletService portletService;
	@Autowired
	private IToaMachineAttachService toaMachineAttachService;
	
	private ObjectMapper objectMapper = new ObjectMapper();

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
			Integer userId,String category) {
		Attach attach = new Attach();
		User user = SystemSecurityUtils.getUser();
		Map<String, String> message = new HashMap<String, String>();
		message.put("code", "200");// 初始化一个成功的代码
		message.put("id", "");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			log.error("文件上传编码异常" + e2, e2);
		}
		
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 设置存放路径

		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 上传文件名
			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			if(mf.getSize() > 524288000){
				attach.setError("附件大小不能超过500M");
				attach.setFileName(fileName);
				return attach;
			}
			FileNamePolicy policy = new FileNamePolicy(fileName);
			String newFileName = policy.getNewFileName();
			try {
				if ("ftp".equals(uploadType)) {
					boolean b = FtpUploader.uploadFile(this.getContextPath(), newFileName,
							mf.getInputStream());
					if (!b) {
						message.put("code", "500");
						log.error("上传失败! 文件名称:" + mf.getOriginalFilename()
								+ "文件大小:" + mf.getSize());
						//return message;
					}
				} else {
					// 创建文件夹
					String absPath = request.getRealPath("/") + this.getAbsolutePath();
					
					File file = new File(absPath);
					if (!file.exists()||!file.isDirectory()) {
						file.mkdirs();
					}
					File uploadFile = new File(absPath + "/" + newFileName);
					mf.transferTo(uploadFile);
					//如果是图片格式生成缩略图
					if(UploadUtils.isImageExt(policy.getFileExt())){
						BufferedImage thumbnail = Scalr.resize(ImageIO.read(uploadFile), 80);
		                String thumbnailFilename = policy.getThumbnailFilename();
		                File thumbnailFile = new File(absPath + "/" + thumbnailFilename);
		                ImageIO.write(thumbnail, "png", thumbnailFile);
					}											
				}
			} catch (IOException e1) {
				message.put("code", "500");
				log.error("上传失败! 文件名称:" + mf.getOriginalFilename() + "文件大小:"
						+ mf.getSize() + e1);
			}
			/*
			 * 这里是数据库入库操作.
			 */
			attach.setFileSize(mf.getSize());
			attach.setFileName(fileName);
			attach.setUploadTime(new Date());
			attach.setUrl(UploadConstants.REQUEST_URI+"/download.action");
			attach.setResourcesName(this.getContextPath()+"/"+newFileName);
			attach.setContent_type(request.getContentType().split(";")[0]);
			attach.setCategory(category);
			attach.setExt(policy.getFileExt());
			attach.setUserid(String.valueOf(user.getId()));
			attach.setUsername(user.getDisplayName());
			try {
				attachService.save(attach);
				message.put("id", attach.getId().toString());
			} catch (Exception e) {
				log.error("附件插入数据库异常" + e);
				log.debug("开始删除" + getContextPath() + "文件");
				Boolean delbool = deleteFile(uploadType, getContextPath() + "/"
						+ newFileName, request);
				if (delbool) {
					log.debug(getContextPath() + "删除成功");
				} else {
					log.warn(getContextPath() + "删除失败");
				}
				message.put("code", "500");
			}
			
		}
		return attach;
	}
	
	
	/**
	 * 文件上传接口 工单使用
	 * 
	 * @param request
	 *            请求对象
	 * @param userId
	 *            上传人编号
	 * @param category
	 *            文件分类
	 * @param businessId
	 *            业务ID（工单ID）
	 * @param businessSource
	 *            业务来源（1、重启操作）
	 * @return Map<"code",状态值> 200 正常 500 异常 <"id",插入成功后的数据库编号> 成功返回编号 不成功返回空
	 */
	public ToaMachineAttach uploadFileForRestart(HttpServletRequest request,
			Integer userId,String category,Integer businessId,String businessSource) {
		ToaMachineAttach attach = new ToaMachineAttach();
		User user = SystemSecurityUtils.getUser();
		Map<String, String> message = new HashMap<String, String>();
		message.put("code", "200");// 初始化一个成功的代码
		message.put("id", "");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			log.error("文件上传编码异常" + e2, e2);
		}
		
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		// 设置存放路径

		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 上传文件名
			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			if(mf.getSize() > 524288000){
				attach.setError("附件大小不能超过500M");
				attach.setFileName(fileName);
				return attach;
			}
			FileNamePolicy policy = new FileNamePolicy(fileName);
			String newFileName = policy.getNewFileName();
			try {
				if ("ftp".equals(uploadType)) {
					boolean b = FtpUploader.uploadFile(this.getContextPath(), newFileName,
							mf.getInputStream());
					if (!b) {
						message.put("code", "500");
						log.error("上传失败! 文件名称:" + mf.getOriginalFilename()
								+ "文件大小:" + mf.getSize());
						//return message;
					}
				} else {
					// 创建文件夹
					String absPath = request.getRealPath("/") + this.getAbsolutePath();
					
					File file = new File(absPath);
					if (!file.exists()||!file.isDirectory()) {
						file.mkdirs();
					}
					File uploadFile = new File(absPath + "/" + newFileName);
					mf.transferTo(uploadFile);
					//如果是图片格式生成缩略图
					if(UploadUtils.isImageExt(policy.getFileExt())){
						BufferedImage thumbnail = Scalr.resize(ImageIO.read(uploadFile), 80);
		                String thumbnailFilename = policy.getThumbnailFilename();
		                File thumbnailFile = new File(absPath + "/" + thumbnailFilename);
		                ImageIO.write(thumbnail, "png", thumbnailFile);
					}											
				}
			} catch (IOException e1) {
				message.put("code", "500");
				log.error("上传失败! 文件名称:" + mf.getOriginalFilename() + "文件大小:"
						+ mf.getSize() + e1);
			}
			/*
			 * 这里是数据库入库操作.
			 */
			attach.setFileSize(mf.getSize());
			attach.setFileName(fileName);
			attach.setUploadTime(new Date());
			attach.setUrl(UploadConstants.REQUEST_URI+"/download.action");
			attach.setResourcesName(this.getContextPath()+"/"+newFileName);
			attach.setContentType(request.getContentType().split(";")[0]);
			attach.setCategory(category);
			attach.setExt(policy.getFileExt());
			attach.setUserId(String.valueOf(user.getId()));
			attach.setUserName(user.getDisplayName());
			attach.setBusinessId(businessId);//业务id
			attach.setBusinessSource(businessSource);
			try {
				toaMachineAttachService.save(attach);
				message.put("id", attach.getId().toString());
			} catch (Exception e) {
				log.error("附件插入数据库异常" + e);
				log.debug("开始删除" + getContextPath() + "文件");
				Boolean delbool = deleteFile(uploadType, getContextPath() + "/"
						+ newFileName, request);
				if (delbool) {
					log.debug(getContextPath() + "删除成功");
				} else {
					log.warn(getContextPath() + "删除失败");
				}
				message.put("code", "500");
			}
			
		}
		return attach;
	}
	
	public Attach userPhotoUploadFile(HttpServletRequest request, Long userId) {
		Attach attach = new Attach();
		Map<String, String> message = new HashMap<String, String>();
		message.put("code", "200");// 初始化一个成功的代码
		message.put("id", "");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			log.error("文件上传编码异常" + e2, e2);
		}
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 上传文件名
			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			FileNamePolicy policy = new FileNamePolicy(fileName);
			//String newFileName = "userPhoto.png";
			String mediumFileName = "small_80.png";
			String smallFileName = "small_30.png";
			//头像路径
			String uuid = Identities.uuid2();
			String url = "upload/userPhoto/" + uuid;
			String photoUrl = request.getRealPath("/") + url;
			
			try {
				if ("ftp".equals(uploadType)) {
					boolean b = FtpUploader.uploadFile(this.getContextPath(), fileName ,mf.getInputStream());
					if (!b) {
						message.put("code", "500");
						log.error("上传失败! 文件名称:" + mf.getOriginalFilename() + "文件大小:" + mf.getSize());
					}
				} else {
					// 创建文件夹
					File file = new File(photoUrl);
					if (!file.exists()||!file.isDirectory()) {
						file.mkdirs();
					}
					File uploadFile = new File(photoUrl + "/"+fileName);
					mf.transferTo(uploadFile);
					//如果是图片格式生成缩略图
					if(UploadUtils.isImageExt(policy.getFileExt())){
						BufferedImage thumbnail = Scalr.resize(ImageIO.read(uploadFile), 80);
		                File thumbnailFile = new File(photoUrl + "/" + mediumFileName);
		                ImageIO.write(thumbnail, "png", thumbnailFile);
		                
		                BufferedImage thumbnail1 = Scalr.resize(ImageIO.read(uploadFile), 30);
		                File thumbnailFile1 = new File(photoUrl + "/" + smallFileName);
		                ImageIO.write(thumbnail1, "png", thumbnailFile1);
					}
					//更新用户头像路径
//					if(userId != null){
//						User user = new User();
//						user.setId(userId);
//						user.setPhoto(url+"/"+fileName);
//						userService.update2(user);
//					}
					//组织返回值
					attach.setFileSize(mf.getSize());
					attach.setFileName(fileName);
					attach.setUploadTime(new Date());
					attach.setUrl(UploadConstants.REQUEST_URI+"/download.action");
					attach.setResourcesName(url+"/"+fileName);
					attach.setId(1L);
				}
			} catch (IOException e1) {
				message.put("code", "500");
				deleteFile(uploadType, getContextPath() + "/" + fileName, request);
				attach.setId(null);
			}
		}
		return attach;
	}
	
	public Attach portletPhotoUploadFile(HttpServletRequest request, Long portletid) {
		Attach attach = new Attach();
		Map<String, String> message = new HashMap<String, String>();
		message.put("code", "200");// 初始化一个成功的代码
		message.put("id", "");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			log.error("文件上传编码异常" + e2, e2);
		}
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 上传文件名
			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			FileNamePolicy policy = new FileNamePolicy(fileName);
			String newFileName = "userPhoto.png";
			String mediumFileName = "medium.png";
			String smallFileName = "small.png";
			//头像路径
			String uuid = Identities.uuid2();
			String url = "upload/portletPhoto/" + uuid;
			String photoUrl = request.getRealPath("/") + url;
			
			try {
				if ("ftp".equals(uploadType)) {
					boolean b = FtpUploader.uploadFile(this.getContextPath(), newFileName ,mf.getInputStream());
					if (!b) {
						message.put("code", "500");
						log.error("上传失败! 文件名称:" + mf.getOriginalFilename() + "文件大小:" + mf.getSize());
					}
				} else {
					// 创建文件夹
					File file = new File(photoUrl);
					if (!file.exists()||!file.isDirectory()) {
						file.mkdirs();
					}
					File uploadFile = new File(photoUrl + "/"+newFileName);
					mf.transferTo(uploadFile);
					//如果是图片格式生成缩略图
					if(UploadUtils.isImageExt(policy.getFileExt())){
						BufferedImage thumbnail = Scalr.resize(ImageIO.read(uploadFile), 74);
		                File thumbnailFile = new File(photoUrl + "/" + mediumFileName);
		                ImageIO.write(thumbnail, "png", thumbnailFile);
		                
		                BufferedImage thumbnail1 = Scalr.resize(ImageIO.read(uploadFile), 50);
		                File thumbnailFile1 = new File(photoUrl + "/" + smallFileName);
		                ImageIO.write(thumbnail1, "png", thumbnailFile1);
					}
					//更新用户头像路径
					if(portletid != null){
				    	portletService.updateForRelation(portletid,url);
					}
					//组织返回值
					attach.setFileSize(mf.getSize());
					attach.setFileName(newFileName);
					attach.setUploadTime(new Date());
					attach.setUrl(UploadConstants.REQUEST_URI+"/download.action");
					attach.setResourcesName(url);
					attach.setId(1L);
				}
			} catch (IOException e1) {
				message.put("code", "500");
				deleteFile(uploadType, getContextPath() + "/" + newFileName, request);
				attach.setId(null);
			}
		}
		return attach;
	}
	
	

	/**
	 * 文件下载接口
	 * 
	 * @param uploadType
	 *            下载目标 是 ftp 还是相对项目路径 uploadType="" 相对项目路径 uploadType="ftp"
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
	@SuppressWarnings("unchecked")
	public void downloadFile(String uploadType, String fileName,String resourcesName,
			HttpServletResponse response, HttpServletRequest request) {
		String filename = "";
		String filepath = "";// 单文件下载路径
		/*Map<String, String> maps = new HashMap<String, String>();
		try {
			maps = objectMapper.readValue(filepathandname, Map.class);
		} catch (JsonParseException e) {
			log.error("文件下载json转map异常 json=" + filepathandname);
			return;
		} catch (JsonMappingException e) {
			log.error("文件下载json转map异常 json=" + filepathandname);
			return;
		} catch (IOException e) {
			log.error("文件下载json转map异常 json=" + filepathandname);
			return;
		}
		if (maps.size() > 1) {
			filename = "下载集合.zip";
		} else {
			Set<String> key = maps.keySet();
			Iterator<String> iter = key.iterator();
			while (iter.hasNext()) {
				filepath = iter.next();
				filename = maps.get(filepath);
			}
		}*/
		filename = CharConventUtils.encodingFileName(fileName);
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=\""
				+ filename + "\"");
		/*if ("ftp".equals(uploadType)) {
			if (maps.size() > 1) {
				FtpUploader.downFile(maps, response);
			} else {
				FtpUploader.downFile(filepath, response);
			}

		} else {
			if (maps.size() > 1) {
				downFile(maps, response, request);
			} else {
				downFile(filepath, response, request);
			}

		}*/
		if ("ftp".equals(uploadType)) {
			FtpUploader.downFile(filepath, response);
		} else {
			downFile(resourcesName, response, request);
		}
	}

	/**
	 * 删除已上传的文件以及数据库实体记录
	 * 
	 * @param ids
	 * @return
	 */ 
	@Transactional
	public Boolean deleteFileByIds(String ids) {
		//Attach attach = new Attach();
		//attach.setPrimaryKeys(ids.split(","));
		if("".equals(ids)||ids==null){
			return false;
		}
		try {
			String[] attach_ids=ids.split(",");
			for(int i=0;i<attach_ids.length;i++){
				deleteAttach(Long.parseLong(attach_ids[i]));
			}
			//attachService.delete(attach);
		} catch (Exception e) { 
			log.error("数据删除异常编号为:" + ids, e);
			return false;
		}
		/*String[] path = paths.split(",");
		for (int i = 0; i < path.length; i++) {
			deleteFile(uploadType, path[i], request);
		}*/
		return true;
	}

	public void deleteAttach(Long id) throws CustomException{
		try {
			Attach attach = new Attach();
			attach.setId(id);
			Attach result = attachService.get(attach);
			String resourceName = result.getResourcesName(); 
			attach.setPrimaryKeys(new String[]{id.toString()});
			attachService.delete(attach,false);
			deleteFile(resourceName);
		} catch (Exception e) {
			throw new CustomException("删除附件异常",e);
		}	
	} 
	/**
	 * 初始化文件存储路径
	 * 
	 * @return
	 */
	private String generateDir(HttpServletRequest request) {
		String pathString = request.getSession().getServletContext()
				.getRealPath(File.separator + "upload");
		File dirPath = new File(pathString);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		return pathString;
	}

	/**
	 * 删除文件
	 * 
	 * @param uploadType
	 *            删除文件类型 uploadType = 'ftp' 则在ftp上删除文件 uploadType = ''
	 *            则删除项目相对路径下的文件
	 * @param path
	 *            文件相对路径带扩展名
	 * @return 成功标识
	 */
	private boolean deleteFile(String uploadType, String path,
			HttpServletRequest request) {
		boolean success = false;
		if ("ftp".equals(uploadType)) {
			success = FtpUploader.deleteFile(path);
		} else {
			success = FileUtil.delFile(generateDir(request) + File.separator
					+ path);
		}
		return success;
	}
	
	private boolean deleteFile(String path){
		boolean success = false;
		if ("ftp".equals(uploadType)) {
			success = FtpUploader.deleteFile(path);
		} else {
			success = FileUtil.delFile(getUploadBaseDir()+"/"+ File.separator + path);
		}
		return success;
	}

	/**
	 * 相对服务器路径单文件下载
	 * 
	 * @param remotePath
	 *            文件路径 带扩展名
	 * @param response
	 * @return
	 */
	public Boolean downFile(String resourcesName, HttpServletResponse response,
			HttpServletRequest request) {
		boolean success = true;
		try {
			//File file = new File(generateDir(request) + File.separatorChar
			//		+ remotePath);
			File file = new File(request.getRealPath("/") + getUploadBaseDir()+File.separatorChar+resourcesName);
			InputStream inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[Integer.parseInt(GlobalContext
					.getProperty("STREAM_SLICE"))];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.close();
			inputStream.close();
		} catch (FileNotFoundException e) {
			log.error("下载文件不存在 path:" + request.getRealPath("/") + getUploadBaseDir()+File.separatorChar+resourcesName);
			success = false;
		} catch (IOException e) {
			log.error("下载异常" + e);
			success = false;
		}
		return success;
	}

	/**
	 * 相对服务器路径单文件下载
	 * 
	 * @param remotePath
	 *            文件路径 带扩展名
	 * @param response
	 * @return
	 */
	public Boolean downFile(Map<String, String> files,
			HttpServletResponse response, HttpServletRequest request) {
		boolean success = true;
		try {
			ZipFile.zip(files, response.getOutputStream(), generateDir(request)
					+ "/");
		} catch (FileNotFoundException e) {
			log.error("下载文件不存在 path:" + e, e);
			success = false;
		} catch (IOException e) {
			log.error("下载异常" + e);
			success = false;
		}
		return success;
	}
	
	private static class FileNamePolicy{
		
	   // private	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		//private String dateStr = sdf.format(new Date());
		private String fileExt;
		private String newNameBase;
		//private String baseDir ;
		public FileNamePolicy(String baseDir,String oriName){
			//this.baseDir = baseDir;
			this.fileExt = UploadUtils.getFileExt(oriName);
		    newNameBase = UUID.randomUUID().toString();
		}
		
		
		public FileNamePolicy(String oriName){
			this("", oriName);
		}
		
		public String getNewFileName(){
			return newNameBase+ "." + fileExt;
		}
		
		public String getThumbnailFilename(){
			return newNameBase + "-thumbnail.png";
		}
		
		public String getFileExt(){
			return this.fileExt;
		}
		
	}
    
	
	public String getContextPath(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		String dateStr = sdf.format(new Date());
		return dateStr;
	}
	
	public String getAbsolutePath(){
		return (getUploadBaseDir()+"/"+getContextPath()).replace('\\','/');
	}

	/**
	 * @param id 缩略图编号
	 * 
	 */
	@Override
	public void getThumbnail(Long id, HttpServletRequest request, HttpServletResponse response) throws CustomException{
		Attach attach = new Attach();
		attach.setId(id);
		try {
			Attach result = attachService.get(attach);
			File imageFile = null;
			if(UploadUtils.isImage(result.getResourcesName())){
				imageFile = new File(request.getRealPath("/") + getUploadBaseDir()+"/"+FileUtil.getFileName(result.getResourcesName())+"-thumbnail.png");
			}
			else{
				imageFile = new File(request.getRealPath("/") + getUploadBaseDir() + "/images/no.png");
			}
	        
	        InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());

		} catch (Exception e) {
			throw new CustomException("获取缩略图异常",e);
		}
	}
	@Override
	public List<Attach> copyAttachList(HttpServletRequest request, List<Attach> list){
		Map<String, String> message = new HashMap<String, String>();
		List<Attach> result = new ArrayList<Attach>();
		// 创建文件夹
		String absPath = request.getRealPath("/") + this.getAbsolutePath();
		File file = new File(absPath);
		if (!file.exists()||!file.isDirectory()) {
			file.mkdirs();
		}
		Iterator<Attach> iterator = list.iterator();
		while(iterator.hasNext()){
			Attach attach_old = iterator.next();
			Attach attach_new = new Attach();
			FileNamePolicy policy = new FileNamePolicy(attach_old.getFileName());
			String newFileName = policy.getNewFileName();
			attach_new.setResourcesName(this.getContextPath()+"/"+newFileName);
			attach_new.setFileSize(attach_old.getSize());
			attach_new.setFileName(attach_old.getFileName());
			attach_new.setUploadTime(attach_old.getUploadTime());
			attach_new.setUrl(UploadConstants.REQUEST_URI+"/download.action");
			attach_new.setContent_type(attach_old.getContent_type());
			attach_new.setCategory(attach_old.getCategory());
			attach_new.setExt(policy.getFileExt());
			attach_new.setUserid(attach_old.getUserid());
			attach_new.setUsername(attach_old.getUsername());
			try {
				String oldPathFile = request.getRealPath("/") + getUploadBaseDir()+"/"+attach_old.getResourcesName();
				String newPathFile = request.getRealPath("/") + this.getAbsolutePath()+"/"+newFileName;
				copyFile(oldPathFile, newPathFile);
				oldPathFile = request.getRealPath("/") + getUploadBaseDir()+"/"+FileUtil.getFileName(attach_old.getResourcesName())+"-thumbnail.png";
				newPathFile = request.getRealPath("/") + getUploadBaseDir()+"/"+FileUtil.getFileName(attach_new.getResourcesName())+"-thumbnail.png";
				copyFile(oldPathFile, newPathFile);
				attachService.save(attach_new);
				result.add(attach_new);
				message.put("id", attach_new.getId().toString());
			} catch (Exception e) {
				log.error("附件插入数据库异常" + e);
				log.debug("开始删除" + getContextPath() + "文件");
				Boolean delbool = deleteFile(attach_new.getResourcesName());
				if (delbool) {
					log.debug(getContextPath() + "删除成功");
				} else {
					log.warn(getContextPath() + "删除失败");
				}
				message.put("code", "500");
			}
		}
		return result; 
	}

	/**
	 * param id 附件编号	 
	 * 
	 * */
	@Override
	public void getOriginalImg(Long id, HttpServletResponse response)
			throws CustomException {
		// TODO Auto-generated method stub
		Attach attach = new Attach();
		attach.setId(id);
		try {
			Attach result = attachService.get(attach);
			File imageFile = null;
			if(UploadUtils.isImage(result.getResourcesName())){
				imageFile = new File(getUploadBaseDir()+"/"+result.getResourcesName());
			}
			else{
				
			}
	        
	        InputStream is = new FileInputStream(imageFile);
            IOUtils.copy(is, response.getOutputStream());

		} catch (Exception e) {
			throw new CustomException("获取原图异常",e);
		}
	}

	@Override
	public Boolean deleteUserPhoto(HttpServletRequest request, String url) {
		String photoUrl = request.getRealPath("/") + url;
		boolean success = false;
		if ("ftp".equals(uploadType)) {
			//success = FtpUploader.deleteFile(path);
		} else {
			success = FileUtil.delFolder(photoUrl);
		}
		return success;
	}
	
	public void copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			File newfile = new File(newPathFile);
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newfile);
				byte[] buffer = new byte[Integer.parseInt(GlobalContext
						.getProperty("STREAM_SLICE"))];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			log.error("附件插入数据库异常" + e);
		} 
	}
	
	/**
	 * 删除图书图片 
	 * 
	 * */
	@Override
	public Boolean deleteBookPhoto(HttpServletRequest request, String url) {
		String photoUrl = request.getRealPath("/") + url;
		boolean success = false;
		if ("ftp".equals(uploadType)) {
			//success = FtpUploader.deleteFile(path);
		} else {
			success = FileUtil.delFolder(photoUrl);
		}
		return success;
	}
	
	/**
	 * 删除图片通用 
	 * 
	 * */
	@Override
	public Boolean deleteAllPhoto(HttpServletRequest request, String url) {
		String photoUrl = request.getRealPath("/") + url;
		boolean success = false;
		if ("ftp".equals(uploadType)) {
			//success = FtpUploader.deleteFile(path);
		} else {
			success = FileUtil.delFolder(photoUrl);
		}
		return success;
	}
	/**
	 * 上传图书图片 
	 * 
	 * */
	@Override
	public Attach bookPhotoUploadFile(HttpServletRequest request, Long bookId) {
		Attach attach = new Attach();
		Map<String, String> message = new HashMap<String, String>();
		message.put("code", "200");// 初始化一个成功的代码
		message.put("id", "");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e2) {
			log.error("文件上传编码异常" + e2, e2);
		}
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String fileName = null;
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			// 上传文件名
			MultipartFile mf = entity.getValue();
			fileName = mf.getOriginalFilename();
			FileNamePolicy policy = new FileNamePolicy(fileName);
			//String newFileName = "userPhoto.png";
			String mediumFileName = "small_80.png";
			String smallFileName = "small_30.png";
			//头像路径
			String uuid = Identities.uuid2();
			String url = "upload/bookPhoto/" + uuid;
			String photoUrl = request.getRealPath("/") + url;
			
			try {
				if ("ftp".equals(uploadType)) {
					boolean b = FtpUploader.uploadFile(this.getContextPath(), fileName ,mf.getInputStream());
					if (!b) {
						message.put("code", "500");
						log.error("上传失败! 文件名称:" + mf.getOriginalFilename() + "文件大小:" + mf.getSize());
					}
				} else {
					// 创建文件夹
					File file = new File(photoUrl);
					if (!file.exists()||!file.isDirectory()) {
						file.mkdirs();
					}
					File uploadFile = new File(photoUrl + "/"+fileName);
					mf.transferTo(uploadFile);
					//如果是图片格式生成缩略图
					if(UploadUtils.isImageExt(policy.getFileExt())){
						BufferedImage thumbnail = Scalr.resize(ImageIO.read(uploadFile), 80);
		                File thumbnailFile = new File(photoUrl + "/" + mediumFileName);
		                ImageIO.write(thumbnail, "png", thumbnailFile);
		                
		                BufferedImage thumbnail1 = Scalr.resize(ImageIO.read(uploadFile), 30);
		                File thumbnailFile1 = new File(photoUrl + "/" + smallFileName);
		                ImageIO.write(thumbnail1, "png", thumbnailFile1);
					}
					//更新用户头像路径
					if(bookId != null){
//						Manage book = new Manage();
//						book.setId(bookId);
//						book.setBookUrl(url+"/"+fileName);
//						manageService.updatePhoto(book);
					}
					//组织返回值
					attach.setFileSize(mf.getSize());
					attach.setFileName(fileName);
					attach.setUploadTime(new Date());
					attach.setUrl(UploadConstants.REQUEST_URI+"/download.action");
					attach.setResourcesName(url+"/"+fileName);
					attach.setId(1L);
				}
			} catch (IOException e1) {
				message.put("code", "500");
				deleteFile(uploadType, getContextPath() + "/" + fileName, request);
				attach.setId(null);
			}
		}
		return attach;
	}

	@Override
	public Attach portalPicUploadFile(HttpServletRequest request, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Attach> copyAttachAndTextList(HttpServletRequest request,
			List<Attach> list, String businessId) {
		// TODO Auto-generated method stub
		return null;
	}
}
