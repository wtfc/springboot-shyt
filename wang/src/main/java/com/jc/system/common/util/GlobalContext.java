package com.jc.system.common.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.jc.system.dic.domain.OtherDic;

/**
 * @title GOA2.0
 * @author
 * @version 2014-03-24
 * 
 */
@SuppressWarnings("unchecked")
public final class GlobalContext {
	private static final Logger logger = Logger.getLogger(GlobalContext.class);

	/**
	 * 时间格式
	 */
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	public static final String TIME_FORMAT = "HH:mm:ss";
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	public static final String TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * 页面分页信息
	 */
	public static final Integer ROWS_DEFAULT = 10; // 默认系那是页面数
	// session中存放menu的常量
	public static final String SESSION_MENU_LIST = "menuList";
	// session中存放token的常量
	public static final String SESSION_TOKEN = "token";
	//密码初始值
	public static final String PASSWORD_DEFAULT_VALUE = "12345678";
	//用户来源
	public static final String USER_SOURCE_OA = "1";
	//返回成功
	public static final String RESULT_SUCCESS = "success";
	//返回错误信息
	public static final String RESULT_ERRORMESSAGE = "errorMessage";
	//返回成功信息
	public static final String RESULT_SUCCESSMESSAGE = "successMessage";
	//label类错误
	public static final String RESULT_LABELERRORMESSAGE = "labelErrorMessage";
	//超级管理员登录名称
	public static final String ADMIN_NAME = "admin";
	//用户启用状态
	public static final String USER_STATUS_0 = "status_0";
	//用户禁用状态
	public static final String USER_STATUS_1 = "status_1";
	//用户锁定状态
	public static final String USER_STATUS_2 = "status_2";
	//用户删除状态
	public static final String USER_STATUS_3 = "status_3";
	//系统根文件目录
	public static String basePath = GlobalContext.class.getClassLoader().getResource("").toString().substring(6);
	
	/**
	 * properties文件中的系统变量
	 */
	private static Map<String, String> properties = null;
	static {
		// 需要循环加上根路径  
        System.out.println("basePath1:"+basePath);
        
        if (System.getProperty("os.name").toLowerCase().indexOf("window") > -1) {  
        	System.out.println("basePath1:"+basePath);
        } else {   
        	basePath = File.separator+basePath; 
        	System.out.println("basePath2:"+basePath);   
        } 
		properties = PropertiesUtil.getProperties("goa.properties");
	}

	/**
	 * @description 获取系统变量
	 * @param property
	 *            系统变量名
	 */
	public static String getProperty(String property) {
		return properties.get(property);
	}

	/**
	 * @description 日志相关
	 */
	private static Map<String, String> logMap = null;
	static {
		logMap = PropertiesUtil.getProperties("log.properties");
	}

	public static final Long COMMON_LOG_TIME = Long.parseLong(logMap
			.get("commonLogTime"));
	public static final Integer COMMON_LOG_NUM = Integer.parseInt(logMap
			.get("cmmmonLogNum"));
	public static final Integer USER_ACTIVE = Integer.parseInt(logMap
			.get("useActivemq"));
	public static final Integer USER_DSIRUPTOR = Integer.parseInt(logMap
			.get("useDisruptor"));
	/**
	 * @description 字典相关
	 */
	private static final String CONFIG_FILE_NAME = "dataDic.xml";
	public static List<OtherDic> logCinfigList = null;
	static {
		logCinfigList = new ArrayList<OtherDic>();
		Document confDoc = readDicConf();
		if (confDoc != null) {
			Element rootEle = confDoc.getRootElement();
			List<Element> dicEles = rootEle.elements("dic");
			for (Element dicEle : dicEles) {
				String flag = dicEle.elementText("flag");
				String beanStr = dicEle.elementText("bean");
				String serviceStr = dicEle.elementText("service");
				OtherDic otherDic = new OtherDic(flag, beanStr, serviceStr);
				logCinfigList.add(otherDic);
			}
		}
	}

	/**
	 * @description 读取配置文件
	 */
	private static Document readDicConf() {
		File file = new File(basePath + CONFIG_FILE_NAME);
		SAXReader reader = new SAXReader();
		System.out.println("file.exists():"+file.exists());
		System.out.println("file:"+file);
		if (file.exists()) {
			try {
				return reader.read(file);
			} catch (DocumentException e) {
				logger.error("读取配置文件错误:", e);
			}
		}
		logger.error("读取配置文件失败");
		return null;
	}
}
