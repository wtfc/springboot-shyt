/**
 * 
 */
package com.jc.system.security.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.security.domain.Setting;
import com.jc.system.security.service.ISettingService;
import com.jc.system.security.service.impl.SettingServiceImpl;

/**
 * @title GOA V2.0
 * @description 系统参数工具类
 * @version  2014年5月14日
 */
public class SettingUtils {
	
	private transient static final Logger log = Logger.getLogger(SettingUtils.class);

	public static final String IS_MSG_SERVICE =  "isMsgService";						//短信服务是否开启
	public static final String MSG_PREFIX =  "msgPrefix";								//短信前缀
	public static final String MSG_DOC_SUGGESTION_HISTORY =  "msgDocSuggestionHistory";	//意见域显示规则
	public static final String SUGGESTION_TYPE =  "suggestionType";						//会签类型
	public static final String SHOW_IDENTIFYING_CODE =  "showIdentifyingCode";			//显示验证码
	public static final String USER_IP_BANDING =  "useIpBanding";						//IP绑定
	public static final String MAX_ERROR_COUNT =  "maxErrorCount";						//最多错误登录次数
	public static final String LOGIN_TYPE =  "loginType";								//登录规则
	public static final String NET_KEY =  "netKey";										//网络签章
	public static final String FILE_PATH =  "filePath";									//上传文件路径
	public static final String PHOTO_PATH =  "photoPath";								//图片路径
	public static final String EMAIL_SAVETIME =  "emailSaveTime";						//email自动保存时间
	public static final String LOCK_TIME =  "lockTime";									//锁定时间
	public static final String SIGN_TYPE =  "sign_type";								//插件类型0点聚  1 金格
	public static final String CONTROL_PRINT =  "control_print";						//是否根据打印份数控制正文中章的打印颜色   0 不控制  1 控制
	public static final String WORKLOG_DAY = "workLogDay";								//日志编辑天数控制
	public static final String TASK_PARENT_TO_SUB = "taskParentToSub";					//督办协办拆分任务时，新建下级任务，上级信息是否回显 0 不回显 1回显
	
	private static ISettingService settingService = null;
	private static Map<String,Object> settingMap = null;
	
	/**
	  * 初始化函数
	  * @author 孙圣然
	  * @version 1.0 2014年5月14日 下午2:58:10
	*/
	public static void init(){
		log.debug("初始化系统参数开始");
		settingService = SpringContextHolder.getBean(SettingServiceImpl.class);
		Setting setting = settingService.getOne(new Setting());
		SetMap(setting);
		log.debug("初始化系统参数结束");
	}
	
	/**
	  * 获得系统参数
	  * @param key 系统参数的标志
	  * @return 系统参数的值
	  * @author 孙圣然
	  * @version 1.0 2014年5月14日 下午3:14:20
	*/
	public static Object getSetting(String key){
		return settingMap.get(key);
	}

	/**
	  * 刷新系统参数内存
	  * @author 孙圣然
	  * @version 1.0 2014年5月14日 下午3:28:40
	*/
	public static void refresh(){
		Setting setting = null;
		setting = settingService.getOne(new Setting());
		SetMap(setting);
	}
	
	/**
	  * 将系统参数放到Map中
	  * @param setting
	  * @param settingMap
	  * @author 孙圣然
	  * @version 1.0 2014年5月14日 下午3:05:33
	*/
	private static void SetMap(Setting setting){
		settingMap = new HashMap<String, Object>();
		settingMap.put(IS_MSG_SERVICE, setting.getIsMsgService());
		settingMap.put(MSG_PREFIX, setting.getMsgPrefix());
		settingMap.put(MSG_DOC_SUGGESTION_HISTORY, setting.getDocSuggestionHistory());
		settingMap.put(SUGGESTION_TYPE, setting.getSuggestionType());
		settingMap.put(SHOW_IDENTIFYING_CODE, setting.getShowIdentifyingCode());
		settingMap.put(USER_IP_BANDING, setting.getUseIpBanding());
		settingMap.put(MAX_ERROR_COUNT, setting.getMaxErrorCount());
		settingMap.put(LOGIN_TYPE, setting.getLoginType());
		settingMap.put(NET_KEY, setting.getNetKey());
		settingMap.put(FILE_PATH, GlobalContext.getProperty("FILE_PATH"));
		settingMap.put(PHOTO_PATH, GlobalContext.getProperty("PHOTO_PATH"));
		settingMap.put(EMAIL_SAVETIME, setting.getEmailSaveTime());
		settingMap.put(LOCK_TIME, setting.getLockTime());
		settingMap.put(WORKLOG_DAY, setting.getWorklogDay());
		settingMap.put(SIGN_TYPE, "0");//0 点聚   1 金格
		settingMap.put(CONTROL_PRINT, setting.getControlPrint());// 0 不控制  1 控制
		settingMap.put(TASK_PARENT_TO_SUB, setting.getTaskParentToSub());// 0 不回显 1回显
	}
	
}
