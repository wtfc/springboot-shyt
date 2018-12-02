package com.jc.system.content.web;

import javax.validation.Valid;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.WebDataBinder;

import com.jc.system.content.UploadConstants;
import com.jc.system.content.domain.Attach;
import com.jc.system.content.domain.validator.AttachValidator;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.web.BaseController;
import com.jc.system.content.service.IAttachService;
import com.jc.system.content.service.IUploadService;
import com.jc.system.portal.domain.Portlet;
import com.jc.system.portal.service.IPortletService;
import com.jc.system.security.SystemAuthorizingRealm.Principal;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IUserService;


/**
 * @title GOA2.0
 * @description  controller类
 * @author 
 * @version  2014-04-17
 */
 
@Controller
@RequestMapping(value=UploadConstants.REQUEST_URI)
public class AttachController extends BaseController{
	@Autowired
	private IUploadService uploadService;
	@Autowired
	private IAttachService attachService;
	@Autowired
	private IUserService userService;
	@Autowired
	private IPortletService portletService;
	
	@org.springframework.web.bind.annotation.InitBinder("attach")
    public void initBinder(WebDataBinder binder) {  
        	binder.setValidator(new AttachValidator()); 
    }
	
	public AttachController() {
	}

	/**
	 * @description 分页查询方法
	 * @param Attach attach 实体类
	 * @param PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17 
	 */
	@RequestMapping(value="manageList.action")
	@SuppressWarnings("unchecked")
	@ResponseBody
	public PageManager manageList(Attach attach,PageManager page){
		PageManager page_ = attachService.query(attach, page);
		return page_; 
	}

	/**
	* @description 跳转方法
	* @return String 跳转的路径
	* @throws Exception
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="manage.action")
	public String manage() throws Exception{
		return "/attach/attachManage"; 
	}

/**
	 * @description 删除方法
	 * @param Attach attach 实体类
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="delete/{ids}")
	@ResponseBody
	public Map<String,Boolean> deleteByIds(Attach attach,@PathVariable String ids) throws Exception{
		Map<String,Boolean> result = new HashMap<>();
		try {
			uploadService.deleteFileByIds(ids); 
			result.put(ids, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put(ids, false);
		}
		return result;
	}

	/**
	 * @description 删除方法
	 * @param String ids 删除的多个主键
	 * @return Integer 成功删除
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="delete.action")
	@ResponseBody
	public Map<String,Boolean> deleteByIds(String ids) throws Exception{
		Map<String,Boolean> result = new HashMap<>();
		try {
			uploadService.deleteFileByIds(ids); 
			result.put(ids, true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			result.put(ids, false);
		}
		return result;
	}
	
	/**
	 * @description 保存方法
	 * @param Attach attach 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value = "save.action")
	@ResponseBody
	public Map<String,Object> save(@Valid Attach attach,BindingResult result) throws Exception{
		
		Map<String,Object> resultMap = validateBean(result);
		if(!"false".equals(resultMap.get("success"))){
			attachService.save(attach);
			resultMap.put("success", "true");
		}
		return resultMap;
	}

	/**
	* @description 修改方法
	* @param Attach attach 实体类
	* @return Integer 更新的数目
	* @author
	* @version  2014-04-17 
	*/
	@RequestMapping(value="update.action")
	@ResponseBody
	public Integer update(Attach attach) throws Exception{
		Integer flag = attachService.update(attach);
		return flag;
	}

	/**
	 * @description 获取单条记录方法
	 * @param Attach attach 实体类
	 * @return Attach 查询结果
	 * @throws Exception
	 * @author
	 * @version  2014-04-17
	 */
	@RequestMapping(value="get.action")
	@ResponseBody
	public Attach get(Attach attach) throws Exception{
		return attachService.get(attach);
	}
	
	@RequestMapping(value = "upload.action")
	public void upload(HttpServletRequest request,HttpServletResponse response,String category) throws Exception {
		String method = request.getMethod() ;
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = new java.util.ArrayList<>();
	    response.setContentType("text/plain");
		if("POST".equals(method.toUpperCase())){ 
			Attach attach = uploadService.uploadFile(request, 2,category);
			attachList.add(attach);
		}
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
	
	/**
	* @description 用户上传头像
	* @param request, response
	* @return 
	* @author
	* @version  2014-06-09
	*/
	@RequestMapping(value = "userPhotoUpload.action")
	public void userPhotoUpload(Long id, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String method = request.getMethod() ;
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = new java.util.ArrayList<>();
	    response.setContentType("text/plain");
		if("POST".equals(method.toUpperCase())){
			Attach attach = uploadService.userPhotoUploadFile(request, id);
			attachList.add(attach);
		}
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
	
	/**
	* @description 删除用户头像
	* @param request
	* @return 
	* @author
	* @version  2014-06-09
	*/
	@RequestMapping(value="deleteUserPhoto.action")
	@ResponseBody
	public Map<String,Boolean> deleteUserPhoto(Long id, String fileFolderName, HttpServletRequest request) throws Exception{
		Map<String,Boolean> result = new HashMap<>();
		if(id != null && id > 0){
			User user = new User();
			user.setId(id);
			user = userService.get(user);
			boolean state = uploadService.deleteUserPhoto(request, user.getPhoto());
			if(state){
				User u = new User();
				u.setId(id);
				u.setPhoto("");
				userService.update2(u);
			}
			result.put("state", state);
		} else {
			if(StringUtils.isNotEmpty(fileFolderName)){
				boolean state = uploadService.deleteUserPhoto(request, fileFolderName);
				result.put("state", state);
			}
		}
		Principal principal = (Principal) SecurityUtils.getSubject().getPrincipal();
		if(principal != null){
			principal.setPhoto(null);
		}
		return result;
	}
	
	@RequestMapping("userPhotoList.action")
	public void userPhotoList(Long id, String fileFolderName, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = new java.util.ArrayList<>();
	    response.setContentType("text/plain");
	    Attach attach = new Attach();
	    
	    String fileUrl = null;
	    if(id != null && id > 0){
	    	User user = new User();
	    	user.setId(id);
	    	user = userService.get(user);
	    	fileUrl = user.getPhoto();
	    } else {
	    	fileUrl = fileFolderName;
	    }
	    if(StringUtils.isNotEmpty(fileUrl)){
    		//String photoUrl = request.getRealPath("/") + fileUrl;
    		String fileName = fileUrl.split("/")[3];
    		File file = new File(request.getRealPath("/")+fileUrl);
    		if(file.exists()){
    			attach.setFileSize(file.length());
				attach.setFileName(fileName);
				attach.setUploadTime(new Date());
				attach.setResourcesName(fileUrl);
				attach.setId(1L);
				attachList.add(attach);
    		} else {
    			attachList.add(null);
    		}
    	}
		
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
	
	/**
	* @description 门户上传图片
	* @param request, response
	* @return 
	* @author
	* @version  2014-07-29
	*/
	@RequestMapping(value = "portletPhotoUpload.action")
	public void portletPhotoUpload(Long id, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String method = request.getMethod() ;
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = new java.util.ArrayList<>();
	    response.setContentType("text/plain");
		if("POST".equals(method.toUpperCase())){
			Attach attach = uploadService.portletPhotoUploadFile(request, id);
			attachList.add(attach);
		}
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
	
	/**
	* @description 删除用户头像
	* @param request
	* @return 
	* @author
	* @version  2014-06-09
	*/
	@RequestMapping(value="deletePortletPhoto.action")
	@ResponseBody
	public Map<String,Boolean> deletePortletPhoto(Long id, String fileFolderName, HttpServletRequest request) throws Exception{
		Map<String,Boolean> result = new HashMap<>();
		if(id != null && id > 0){
			Portlet portlet = new Portlet();
	    	portlet.setId(id);
	    	portlet = portletService.get(portlet);
			boolean state = uploadService.deleteUserPhoto(request, portlet.getLetFile());
			if(state){
				Portlet tempvo = new Portlet();
				tempvo.setId(id);
				tempvo.setLetFile("");
				portletService.update(tempvo);
			}
			result.put("state", state);
		} else {
			if(StringUtils.isNotEmpty(fileFolderName)){
				boolean state = uploadService.deleteUserPhoto(request, fileFolderName);
				result.put("state", state);
			}
		}
		return result;
	}
	
	@RequestMapping("portletPhoto.action")
	public void portletPhoto(Long id, String fileFolderName, HttpServletRequest request, HttpServletResponse response)  throws Exception {
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = new java.util.ArrayList<>();
	    response.setContentType("text/plain");
	    Attach attach = new Attach();
	    
	    String fileUrl = null;
	    if(id != null && id > 0){
	    	Portlet portlet = new Portlet();
	    	portlet.setId(id);
	    	portlet = portletService.get(portlet);
	    	fileUrl = portlet.getLetFile();
	    } else {
	    	fileUrl = fileFolderName;
	    }
	    if(StringUtils.isNotEmpty(fileUrl)){
    		String photoUrl = request.getRealPath("/") + fileUrl;
    		String fileName = photoUrl + "/userPhoto.png";
    		File file = new File(fileName);
    		if(file.exists()){
    			attach.setFileSize(file.length());
				attach.setFileName("userPhoto.png");
				attach.setUploadTime(new Date());
				attach.setResourcesName(fileUrl);
				attach.setId(1L);
				attachList.add(attach);
    		} else {
    			attachList.add(null);
    		}
    	}
		
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
	
	@RequestMapping(value = "/thumbnail/{id}")
    public void thumbnail(HttpServletRequest request,HttpServletResponse response, @PathVariable Long id) throws Exception{

			uploadService.getThumbnail(id, request, response);

    }
	
	@RequestMapping(value = "/original/{id}")
    public void original(HttpServletRequest request,HttpServletResponse response, @PathVariable Long id) throws Exception{
			uploadService.getOriginalImg(id, response);
    }
	
	/**
	 * 文件下载方法
	 * 
	 * @param filename 
	 *            文件名
	 * @param resourcesname
	 * 			      相对文件路径和文件名    
	 * 		  
	 * @param request
	 *            文件路径 带扩展名
	 * @param response
	 */
	@RequestMapping("download.action")
	public void downloadFile(HttpServletRequest request, String uploadtype,
			String fileName,String resourcesName, HttpServletResponse response) {
		try {
			fileName = URLDecoder.decode(fileName,"utf-8");
			uploadService.downloadFile(uploadtype, fileName, resourcesName, response, request);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("test.action")
	public String showupload(HttpServletRequest request, String uploadtype,
			String fileName,String resourcesName, HttpServletResponse response) {
		return "/attach/MyTest";
	}
	
	@RequestMapping("attachlist.action")
	public void showAttachlist(HttpServletRequest request, HttpServletResponse response,String businessId,String businessTable,String isPaged,String category)  throws Exception {
		Attach attach = new Attach();
		if(businessId==null||"".equals(businessId)){
			businessId = "0";
		}
		attach.setBusinessId(Long.valueOf(businessId));
		attach.setBusinessTable(businessTable);
		attach.setIsPaged(isPaged);
		attach.setCategory(category);
		//PageManager page = new PageManager();
		//PageManager page_ = attachService.query(attach, page);
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = attachService.queryAll(attach);
	    response.setContentType("text/plain");
	    //attachList = (List<Attach>) page_.getData();
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
	
	@RequestMapping("attachAll.action")
	@ResponseBody
	public List<Attach> attachAll(Attach attach)throws Exception {
		return attachService.queryAll(attach);
	}

	
	@RequestMapping("copyAttachlist.action")
	public void copyAttachlist(HttpServletRequest request, HttpServletResponse response,String businessId,String businessTable,String isPaged,String category)  throws Exception {
		Attach attach = new Attach();
		if(businessId==null||"".equals(businessId)){
			businessId = "0";
		}
		attach.setBusinessId(Long.valueOf(businessId));
		attach.setBusinessTable(businessTable);
		attach.setIsPaged(isPaged);
		attach.setCategory(category);
		//PageManager page = new PageManager();
		//PageManager page_ = attachService.query(attach, page);
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = new java.util.ArrayList<>();
	    response.setContentType("text/plain");
	    //attachList = (List<Attach>) page_.getData();
	    attachList = attachService.queryAll(attach);
	    attachList = uploadService.copyAttachList(request, attachList);
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
	
	/**
	* @description 用于公文转发复制附件和正文
	* @param request, response
	* @return void
	* @author zouzhichao
	* @version  2014-07-22
	*/
	@RequestMapping("copyAttachAndTextList.action")
	public void copyAttachAndTextList(HttpServletRequest request, HttpServletResponse response,String businessId,String businessTable,String isPaged,String category)  throws Exception {
		Attach attach = new Attach();
		if(businessId==null||"".equals(businessId)){
			businessId = "0";
		}
		attach.setBusinessId(Long.valueOf(businessId));
		attach.setBusinessTable(businessTable);
		attach.setIsPaged(isPaged);
		attach.setCategory(category);
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = new java.util.ArrayList<>();
	    response.setContentType("text/plain");
	    attachList = attachService.queryAll(attach);
	    attachList = uploadService.copyAttachAndTextList(request, attachList, businessId);
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
	/**
	* @description 上传图书图片
	* @param request, response
	* @return 
	* @author  马如彪
	* @version  2015-03-31
	*/
	@RequestMapping(value = "bookPhotoUpload.action")
	public void bookPhotoUpload(Long id, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String method = request.getMethod() ;
		Map<String,List<Attach>>  result = new HashMap<>();
		List<Attach> attachList = new java.util.ArrayList<>();
	    response.setContentType("text/plain");
		if("POST".equals(method.toUpperCase())){
			Attach attach = uploadService.bookPhotoUploadFile(request, id);
			attachList.add(attach);
		}
		result.put("files",attachList);
		ObjectMapper mapper = new ObjectMapper();
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(mapper.writeValueAsString(result));
	}
}