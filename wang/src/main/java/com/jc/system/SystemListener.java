package com.jc.system;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.Logger;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.jc.system.common.service.IRegService;
import com.jc.system.common.service.impl.RegServiceImpl;
import com.jc.system.common.util.SpringUtil;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.impl.DicManagerImpl;
import com.jc.system.log.OperateLogUtil;
import com.jc.system.security.service.IDepartmentService;
import com.jc.system.security.service.IUserService;
import com.jc.system.security.service.impl.DepartmentServiceImpl;
import com.jc.system.security.service.impl.UserServiceImpl;
import com.jc.system.security.util.License;
import com.jc.system.security.util.SettingUtils;

/**
 * @author
 * @version 2014-03-24
 * 
 */
@WebListener
public class SystemListener extends ContextLoaderListener implements
		ServletContextListener {

	private Logger log = Logger.getLogger(this.getClass());
	
	/**
	 * @description 系统初始化方法
	 * @param event
	 *            spring的初始化事件
	 * @author
	 * @version 2014-03-24
	 */
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		// 可以在其中取得spring初始化好的bean
		WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(context);
		
		// 初始化操作日志系统
		OperateLogUtil.init();
		SpringUtil.ctx = applicationContext;
		IDicManager manager = new DicManagerImpl();
		manager.init();
		
		//初始化系统参数
		SettingUtils.init();
		
		//缓存所有组织
		DepartmentServiceImpl deptService = (DepartmentServiceImpl) SpringUtil.ctx.getBean(IDepartmentService.class);
		deptService.getAllDepts();		
		
		//缓存所有用户
		UserServiceImpl userService = (UserServiceImpl) SpringUtil.ctx.getBean(IUserService.class);
		userService.getAllUsers();
		
		//生成辅助安装程序
//		IRegService regService = new RegServiceImpl();
//		regService.zipSetupFile();
	}

	/**
	 * @description 系统关闭时调用的方法
	 * @param event
	 *            spring的初始化事件
	 * @author
	 * @version 2014-03-24
	 */
	public void contextDestroyed(ServletContextEvent event) {
		super.contextDestroyed(event);
	}
}
