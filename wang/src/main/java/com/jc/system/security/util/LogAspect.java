package com.jc.system.security.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.beans.factory.annotation.Autowired;

import com.jc.system.common.util.DateUtils;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.Operlog;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IOperlogService;
public class LogAspect implements MethodInterceptor {

	@Autowired
	private IOperlogService operlogService;

	@Override
	public Object invoke(MethodInvocation mi) throws Throwable {
		Object[] ars = mi.getArguments();
		String userIp = "";
		for(Object o :ars){
            if(o instanceof HttpServletRequest){
            	userIp = getIpAddr((HttpServletRequest)o);
            }
        }
		
		// 判断该方法是否加了@ActionLog 注解
        if(mi.getMethod().isAnnotationPresent(ActionLog.class)){
        	ActionLog logAnnotation = mi.getMethod().getAnnotation(ActionLog.class);  
            String operateModelNm = logAnnotation.operateModelNm();  //业务功能
            String operateFuncNm = logAnnotation.operateFuncNm();  	 //业务方法
            String operateDescribe = logAnnotation.operateDescribe();  //操作描述
            
            String ids = ""; //被删除的数据id集合
            String opertype = "";	//操作类型
            if(operateFuncNm.indexOf("delete") != -1){
            	ids = ":被删除数据id集合是"+ars[1].toString();//要求调用的删除方法，被输出数据参数ids必须在第二位
            	opertype = "删除";
            	//System.out.println(mi.proceed()+"----------this method is added @LoginRequired-------------------------");
            } else if(operateFuncNm.indexOf("update") != -1){//要求调用的修改方法，被输出数据参数对象必须在第一位
            	if(ars[0] instanceof Map) 
            		ids = ":被修改数据id是"+((Map)ars[0]).get("id");//当参数类型为map情况下 处理方式
            		//System.out.println(ids+((Map)ars[0]).get("id")+"----------this method is added @LoginRequired-------------------------");
            	else
            		ids = ":被修改数据id是"+String.valueOf(ars[0].getClass().getMethod("getId").invoke(ars[0]));//当参数类型为bean情况下 处理方式
            	opertype = "修改";
            } else if(operateFuncNm.indexOf("save") != -1){
            	opertype = "添加";
            } else{
            	opertype = "查询";
            }
            
            //获取用户登录信息
    		User user = SystemSecurityUtils.getUser();
            //存储操作日志  ------差人员信息等
            Operlog operLog=new Operlog();  
			operLog.setCreateDate(DateUtils.getSysDate());  
			operLog.setUserId(user.getId());  
			operLog.setLoginName(user.getLoginName());
			operLog.setDisplayName(user.getDisplayName());
			operLog.setIp(userIp);
			operLog.setOperTime(DateUtils.getSysDate());
			operLog.setFunName(operateFuncNm+":"+operateModelNm+ids);
			operLog.setOperDesc(operateDescribe);
			operLog.setOperType(opertype);
			operLog.setModifyDate(DateUtils.getSysDate());
			operLog.setIsAdmin(String.valueOf(user.getIsAdmin()));
			operlogService.save(operLog); 
            //System.out.println(mi.proceed()+"----------this method is added @LoginRequired-------------------------");
        }
        //执行被拦截的方法，切记，如果此方法不调用，则被拦截的方法不会被执行。
        return mi.proceed();
	} 
	
	public String getIpAddr(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }

}
