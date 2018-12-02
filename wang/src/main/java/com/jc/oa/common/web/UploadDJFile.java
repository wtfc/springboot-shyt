package com.jc.oa.common.web;

import java.io.File;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.jc.foundation.web.BaseController;
import com.jc.oa.common.domain.FileEntity;
import com.jc.system.common.util.Constants;
import com.jc.system.security.util.SettingUtils;

@Controller
@RequestMapping(value="/common/dj")
public class UploadDJFile extends BaseController{

	
	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-23 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "doc/basesetting/testdj"; 
	}
	
	/**
	* @description 保存AIP文件到服务器方法
	* @return String 是否成功
	* @throws Exception
	* @author lixw
	* @version  2014-11-27 
	*/
	@RequestMapping(value="saveAip.action")
	public void saveAip(HttpServletRequest request, HttpServletResponse response) throws Exception{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		String workId = multipartRequest.getParameter("workId");
		String root = request.getSession().getServletContext().getRealPath("/")+File.separator+SettingUtils.getSetting(SettingUtils.FILE_PATH)+File.separator+"office"+File.separator+workId;
		File file = new File(root);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		String filePath = root+File.separator+"1.aip";

		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile mf = entity.getValue();

			File uploadFile = new File(filePath);
			mf.transferTo(uploadFile);
		}
		
		File AIPfile = new File(filePath);
		if(AIPfile.exists()){
			OutputStream out = response.getOutputStream();
			out.write("ok".getBytes());
			out.flush();
			out.close();
		}
		
		
	}

	/**
	 * 上传文件到服务器upload/office文件夹下
	 * @param fileEntity 预留
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadFile.action")
	@ResponseBody
	public void uploadFile(FileEntity fileEntity,HttpServletRequest request, HttpServletResponse response) throws Exception {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		Calendar calendar = Calendar.getInstance();
		String fileName = String.valueOf(calendar.getTimeInMillis()) + "-"
							+ String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + "-" + String.valueOf(calendar.get(Calendar.MINUTE)) + "-" + String.valueOf(calendar.get(Calendar.SECOND));
		String template = "";
		if(request.getParameter("template") != null && request.getParameter("template").length() > 0) {
			template = "template" + File.separator;
		}
		String root = request.getRealPath(Constants.DJ_UPLOAD_DIR + File.separator + template);
		
		File file = new File(root);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		JSONObject result = new JSONObject();
		//一次只上传一个文件
		try {
			for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
				MultipartFile mf = entity.getValue();
				String name = mf.getOriginalFilename();
				String suffix = name.substring(name.indexOf("."));
				long fileSize = mf.getSize();
				BigDecimal size = new BigDecimal(fileSize);
				/*
				 * BigDecimal size = new BigDecimal(attach.getFileSize());
						doc.setDmSize();
						size.divide(new BigDecimal(1024)).setScale(2, BigDecimal.ROUND_HALF_UP).toString() + "K"
							*/
				if(fileSize / 1024 < 1024) {
					result.put("fileSize", (size.divide(new BigDecimal(1024)).setScale(2, BigDecimal.ROUND_HALF_UP)) + "K");
				} else {
					result.put("fileSize", (size.divide(new BigDecimal(1024 * 1024)).setScale(2, BigDecimal.ROUND_HALF_UP)) + "M");
				}
				result.put("fileName", fileName + suffix);
				result.put("dmSuffix", suffix.substring(1));
				result.put("relativePath", Constants.DJ_UPLOAD_DIR + File.separator + template + fileName + suffix);
				//文件的服务器路径
				result.put("filePath", "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath()+"/" + Constants.DJ_UPLOAD_DIR + File.separator + template + fileName + suffix);
				File uploadFile = new File(root + File.separator + fileName + suffix);
				mf.transferTo(uploadFile);
			}
			response.getWriter().write(result.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("上传文件失败：" + e.getMessage());
			response.getWriter().write("");
		}
		//String data = fileEntity.getBase64();
		/*String fileType = fileEntity.getFileType();
		int fileSize = fileEntity.getFileSize();
		String fileName = fileEntity.getFileName();
		System.out.println(data);
		
		byte[]aa = null;
		aa = Base64.decode(data);
		File file = new File("c:\\aa.doc");
		FileOutputStream outStream = new FileOutputStream(file);
		
		outStream.write(aa, 0, aa.length);
		outStream.close();*/
		/*
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String id = request.getParameter("id");
		if (file != null) {
			System.out.println(file.getAbsolutePath());
				if (this.getFileFileName1() != null) {
					try {
						
						
						String mailTime = DateUtil.mailDate(new java.util.Date());
						
						//notice.setNotice_path("upload/"+ mailTime+ "_"+ "."+ StringUtil.getExtension(this.getFileFileName()));
						//notice.setNotice_pathName(fileFileName);
						
						InputStream is = new FileInputStream(file);
						
						String root = ServletActionContext.getRequest().getRealPath(file.separatorChar+"upload"+file.separator+"office"+file.separator+"temp");
						if(id != null && id.length() > 0){
							root = ServletActionContext.getRequest().getRealPath(file.separatorChar+"upload"+file.separator+"office"+file.separator+id);
						}
						if("endseal".equals(id)){
							 root = ServletActionContext.getRequest().getRealPath(file.separatorChar+"upload"+file.separator+"office"+file.separator+"seal");
						}
						//创建 root 路径文件夹
						MyUtils.mkDirectory(root);
						//MOD BY FUSH 2013-05-9 START
						//String fileName = request.getParameter("oldFileName");
						String fileName = URLDecoder.decode(request.getParameter("oldFileName"), "utf-8");
						//MOD BY FUSH 2013-05-9 END
						String docname = "";
						if(fileName != null && fileName.length() > 0){
							docname = fileName+ "."+ StringUtil.getExtension(this.getFileFileName1());
						}else{
							docname = mailTime+ "_"+ "."+ StringUtil.getExtension(this.getFileFileName1());
						}
						
						
						File diskFile = new File(root, docname);
						
						OutputStream os = new FileOutputStream(diskFile);
						
						byte[] buffer = new byte[8192];
						int length = 0;
						while ((length = is.read(buffer)) > 0) {
							os.write(buffer, 0, length);
						}
						is.close();
						os.close();
						
						//MOD BY FUSH 2013-05-9 START
						//response.getWriter().write("success|"+docname);
						response.getWriter().write("success|"+URLEncoder.encode(docname, "utf-8"));
						//MOD BY FUSH 2013-05-9 END
						//如果是排版，传递一份到审批盖章文件夹
						if("composing".equals(id)){
							String[] Soursepath = {root+file.separator+docname} ;
							MyUtils.copyFile(Soursepath, ServletActionContext.getRequest().getRealPath(file.separatorChar+"upload"+file.separator+"office"+file.separator+"seal")+File.separator);
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}else
					response.getWriter().write("failed");
		}else
			response.getWriter().write("failed");
	*/}
	
}
