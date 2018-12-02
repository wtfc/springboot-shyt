package com.jc.foundation.web;

import java.beans.PropertyEditorSupport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.jc.foundation.domain.ErrorMessage;
import com.jc.system.CustomException;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.content.domain.Attach;
import com.jc.system.exception.DataNotFoundException;
import com.jc.system.security.exception.ConcurrentException;

/**
 * @title GOA2.0
 * @version 2014-03-24
 */
public class BaseController {

	protected transient final Logger log = Logger.getLogger(this.getClass());
	
	@Autowired
	private HttpServletRequest request;

	/**
	 * @description 初始化后台公共校验方法
	 * @param request
	 *            服务器请求类
	 * @param binder
	 *            绑定类
	 * @author
	 * @version 2014-03-24
	 */
	@InitBinder
	public void InitBinder(HttpServletRequest request,
			ServletRequestDataBinder binder) {
		binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
			public void setAsText(String text) throws IllegalArgumentException {
				setValue(text==null?text:text.trim());
			}
		});
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {

			@Override
			public void setAsText(String text) throws IllegalArgumentException {
				try {
					SimpleDateFormat dateFormat = new SimpleDateFormat(
							GlobalContext.DATE_FORMAT);
					SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
							GlobalContext.DATE_TIME_FORMAT);

					String patterTimeStr = "\\d{4}-\\d{1,2}-\\d{1,2}\\s\\d{1,2}\\:\\d{1,2}\\:\\d{1,2}";
					String patterDateStr = "\\d{4}-\\d{1,2}-\\d{1,2}";

					Pattern patternTime = Pattern.compile(patterTimeStr);
					Pattern patternDate = Pattern.compile(patterDateStr);

					Matcher matcher = patternTime.matcher(text);
					Matcher dateMatcher = patternDate.matcher(text);

					if (matcher.find()) {
						setValue(dateTimeFormat.parse(text));
					} else if (dateMatcher.find()) {
						setValue(dateFormat.parse(text));
					}
				} catch (ParseException e) {
					log.error(e);
				}
			}
		});
	}

	/**
	 * @description 公共处理Exception方法
	 * @param e
	 *            捕获的Exception类
	 * @return String 无实际意义
	 * @author
	 * @version 2014-03-03
	 */
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public Object baseException(Exception e, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		//并发异常
		if(e.getCause() != null && e.getCause().toString().startsWith(ConcurrentException.class.getName())){
			CustomException ce = (CustomException) e;
			log.error(ce.getLogMsg(), ce);
			if (isAjaxRequest(request)) {
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "数据已被修改，请刷新后重新操作");
			} 
		}//数据不存在
		else if(e instanceof DataNotFoundException){
			DataNotFoundException ce = (DataNotFoundException) e;
			log.error(ce.getLogMsg(), ce);
			if (isAjaxRequest(request)) {
				resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "数据已被删除");
			} 
		}
		//附件异常
		else if(e instanceof MaxUploadSizeExceededException){
			try {
				List<Attach> attachList = new java.util.ArrayList<>();
				Attach attach = new Attach();
				attach.setError("附件大小不能超过500M");
				attachList.add(attach);
				resultMap.put("files", attachList);
				ObjectMapper mapper = new ObjectMapper();
				response.setContentType("text/plain");
				response.setCharacterEncoding("UTF-8");
				response.getWriter().write(mapper.writeValueAsString(resultMap));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			return null;
		}
		//shiro权限异常
		else if(e.getCause() != null && e.getCause().toString().startsWith(AuthorizationException.class.getName())){
			throw new UnauthorizedException();
		} 
		//业务异常
		else if (e instanceof CustomException) {
			CustomException ce = (CustomException) e;
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, ((CustomException) e).getMessageStr());
			//业务出异常刷新token
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			Throwable t = ce;
			while(t.getCause()!=null){
				t = t.getCause();
				if(t instanceof ConcurrentException){
					resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, ((CustomException) t).getMessageStr());
				}
			}
			log.error(ce.getLogMsg(), ce);
		} else {
			log.error(e.getMessage(), e);
		}

		if (isAjaxRequest(request)) {
			resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			return resultMap;
		} else {
			try {
				response.sendRedirect(request.getContextPath()
						+ "/system/500.action");
			} catch (IOException e1) {
				log.error("出现异常，跳转页面错误", e1);
			}
		}
		return "";
	}

	/**
	 * @description 下载文件
	 * @param String
	 *            path 路径和文件名
	 * @author
	 * @version 2014-03-24
	 */
	public void sendFile(String path, HttpServletResponse response) {
		sendFile(path,response,"application/octet-stream");
	}
	
	/**
	  * 下载文件
	  * @param path 文件路径
	  * @param response 
	  * @param contentType response类型
	  * @author 孙圣然
	  * @version 1.0 2014年5月20日 下午4:49:03
	*/
	public void sendFile(String path, HttpServletResponse response,String contentType) {
		try {
			File file = new File(path);
			String filename = file.getName();
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			if("application/octet-stream".equals(contentType)){
				response.addHeader("Content-Disposition", "attachment;filename="
						+ new String(filename.getBytes()));
			}
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType(contentType);
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	  * 下载文件（字符串）
	  * @param path 文件路径
	  * @param response 
	  * @param contentType response类型
	  * @author 孙圣然
	  * @version 1.0 2014年5月20日 下午4:49:03
	*/
	public void sendFileByStr(String content,String fileName, HttpServletResponse response) {
		try {
			byte[] buffer = content.getBytes();
			// 清空response
			response.reset();
			// 设置response的Header
			response.addHeader("Content-Disposition", "attachment;filename="
					+ new String(fileName.getBytes()));
			OutputStream toClient = new BufferedOutputStream(
					response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * @description 判断是否为ajax请求
	 * @param request
	 *            请求request
	 * @return 判断结果
	 * @author 孙圣然
	 * @version 1.0 上午11:29:07
	 */
	protected boolean isAjaxRequest(HttpServletRequest request) {
		String requestType = request.getHeader("X-Requested-With");
		if ("XMLHttpRequest".equals(requestType)) {
			return true;
		}
		return false;
	}

	/**
	 * @description 校验bean的统一方法
	 * @param result
	 *            校验结果
	 * @return 成功状态：success true/false 错误信息：labelErrorMessage
	 * @version 下午3:06:39
	 */
	protected Map<String, Object> validateBean(BindingResult result) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (result.hasErrors()) {
			List<ErrorMessage> errorMessageList = new ArrayList<ErrorMessage>();
			List<ObjectError> errorList = result.getAllErrors();
			for (ObjectError error : errorList) {
				ErrorMessage em = new ErrorMessage();
				em.setCode(error.getCode());
				em.setMessage(error.getDefaultMessage());
				errorMessageList.add(em);
			}
			resultMap.put(GlobalContext.RESULT_LABELERRORMESSAGE, errorMessageList);
			resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			//业务出异常刷新token
			resultMap.put(GlobalContext.SESSION_TOKEN, getToken(request));
			return resultMap;
		}
		return resultMap;
	}

	/**
	 * @description 校验session中token
	 * @param request
	 * @return false代表重复提交 true正常提交
	 * @version
	 */
	public boolean isDupSubmit(HttpServletRequest request, String clientToken) {
		String serverToken = (String) request.getSession(false).getAttribute(GlobalContext.SESSION_TOKEN);
		if (serverToken == null) {
			return true;
		}
		if(clientToken == null){
			clientToken = request.getParameter(GlobalContext.SESSION_TOKEN);
		}
		if (clientToken == null) {
			return true;
		}
		if (!serverToken.equals(clientToken)) {
			return true;
		}
		return false;
	}

	/**
	 * @description 校验是否是重复提交
	 * @param request
	 * @return success为false代表重复提交
	 * @version
	 */
	protected Map<String, Object> validateToken(HttpServletRequest request, String clientToken) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (isDupSubmit(request, clientToken)) {
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "请不要重复提交");
			resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			return resultMap;
		} else {
			request.getSession().removeAttribute(GlobalContext.SESSION_TOKEN);
		}
		return resultMap;
	}
	

	/**
	 * @description 校验是否是重复提交
	 * @param request
	 * @return success为false代表重复提交
	 * @version
	 */
	protected Map<String, Object> validateToken(HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		if (isDupSubmit(request, null)) {
			resultMap.put(GlobalContext.RESULT_ERRORMESSAGE, "请不要重复提交");
			resultMap.put(GlobalContext.RESULT_SUCCESS, false);
			return resultMap;
		} else {
			request.getSession().removeAttribute("token");
		}
		return resultMap;
	}
	
	/**
	 * @description 生成token
	 * @param request
	 * @return String
	 * @version
	 */
	public String getToken(HttpServletRequest request){
		String token = UUID.randomUUID().toString().replaceAll("-", "");
		request.getSession().setAttribute(GlobalContext.SESSION_TOKEN, token);
		return token;
	}
	
	
	/**
	 * @description 从Map中通过key获取value
	 * @param Map key
	 * @return Object
	 * @version
	 */
	protected Object getMapValue(Map<String, Object> map, String key){
		for(Map.Entry<String, Object> entry : map.entrySet()){
			if(entry.getKey().equals(key)){
				return entry.getValue();
			}
		} 
		return null;
	}
}
