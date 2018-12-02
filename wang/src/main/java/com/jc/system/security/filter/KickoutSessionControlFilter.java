package com.jc.system.security.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;

/**
 * 
 * @title GOA V2.0
 * @description 
 * @version  2014年6月18日
 */
public class KickoutSessionControlFilter extends AccessControlFilter {
	
		public static Properties p = null;	
		public static  InputStream in;
		//sessionId,上一次有效操作时间
		public static ConcurrentHashMap<String,Double> sessionEffectiveMap = new ConcurrentHashMap<String,Double>();

	
		public KickoutSessionControlFilter(){
			if(p == null) {
				 in = this.getClass().getClassLoader().getResourceAsStream("InvalidAction.properties");  
					p = new Properties();
					try {
						p.load(in);
						in.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
		}
		
		public static String getValue(String itemName) {
			return p.getProperty(itemName);
		}
		
		@SuppressWarnings("rawtypes")
		public static Iterator getKeyIterator(){
			return p.keySet().iterator();
		}
			
	    @Override
	    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
	        return false;
	    }

	    @SuppressWarnings("static-access")
		@Override
	    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
	    	
	    	HttpServletRequest req = (HttpServletRequest) request;
    		//HttpServletResponse resp = (HttpServletResponse) response;
    		String reuqestUrl = req.getRequestURI();
    		Subject subject = getSubject(request, response);
			Session session = subject.getSession();
    		
    		Integer sessionFailureTime = req.getSession().getMaxInactiveInterval()/60;
    		String sessionId = req.getSession().getId();
    		if(this.getEffectiveWithSessionId(sessionId) != null){
    			Double effectiveTime = this.getEffectiveWithSessionId(sessionId);
    			Double nowTime = (double) new Date().getTime();
    			//判断是否为无效链接
    			Iterator iter = this.getKeyIterator();
    			boolean flag = true;
    			while(iter.hasNext()){
    				String invaild = (String) iter.next();
    				if(reuqestUrl.indexOf(invaild) != -1){
    					flag = false;
    				}
    			}
    			if(flag){
					this.putSeesionMap(sessionId, nowTime);
				}
    			if((nowTime - effectiveTime)/(1000*60)>sessionFailureTime){
    				this.removeSessionMap(sessionId);
    				subject.logout();
	                session.stop();
    			}
    		}else{
    			this.putSeesionMap(sessionId, (double)new Date().getTime());
    		}
    		
	    	
			//判断是不是被踢出
	        if (session.getAttribute("kickout") != null) {
	            try {
	                subject.logout();
	                session.stop();
	            } catch (Exception e) {
	            }
	            
	            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
	            httpServletResponse.sendRedirect(req.getContextPath() + "/login?kickout=true");
	            return false;
	        }
	        return true;
	    }
	    
	    public void putSeesionMap(String sessionId,Double lastTime){
	    	sessionEffectiveMap.put(sessionId, lastTime);
	    }
	    public void removeSessionMap(String sessionId){
	    	sessionEffectiveMap.remove(sessionId);
	    }
	    public Double getEffectiveWithSessionId(String sessionId){
	    	return sessionEffectiveMap.get(sessionId);
	    }
}
