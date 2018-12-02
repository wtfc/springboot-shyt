package com.jc.system.portal.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import net.sf.json.JSONObject;

public class PortalUtils {
	
	/**
	 * 获取门户功能组件参数
	 * 返回参数为Map
	 */
	public static Map<String, String> getPortalParameter(HttpServletRequest request) {
		String jsonStr = request.getParameter("mydata");
		JSONObject jsonObject = JSONObject.fromObject(jsonStr); 
		Map<String, String> data = new HashMap<String, String>();
		Iterator it = jsonObject.keys();  
	    // 遍历jsonObject数据，添加到Map对象  
	    while (it.hasNext())  
	    {  
	       String key = String.valueOf(it.next());  
	       String value = (String) jsonObject.get(key);  
	       data.put(key, value);  
	    }
	    
	    return data;
	}
	
	/**
	 * 获取门户功能组件参数
	 * 返回参数为Model
	 */
	public static Model getPortalParameter(Model model,HttpServletRequest request) {
		String jsonStr = request.getParameter("mydata");
		JSONObject jsonObject = JSONObject.fromObject(jsonStr); 
		Map<String, String> data = new HashMap<String, String>();
		Iterator it = jsonObject.keys();  
	    // 遍历jsonObject数据，添加到Map对象  
	    while (it.hasNext())  
	    {  
	       String key = String.valueOf(it.next());  
	       String value = (String) jsonObject.get(key);  
	       data.put(key, value);  
	    }
	    String parameter = request.getParameter("parameter");//携带业务参数
	    model.addAttribute("funViewType",data.get("funViewType"));//门户视图类型
	    model.addAttribute("dataRows",data.get("dataRows"));//门户数据显示行数
	    model.addAttribute("portletId",data.get("portletId"));//门户业务组件id
	    model.addAttribute("functionId",data.get("functionId"));//门户功能组件id
	    if(parameter != null && !parameter.equals(""))
	    	model.addAttribute("funUrlmore",data.get("funUrlmore")+"?parameter="+parameter);//门户功能更多url
	    else
	    	model.addAttribute("funUrlmore",data.get("funUrlmore"));//门户功能更多url
	    return model;
	}
}
