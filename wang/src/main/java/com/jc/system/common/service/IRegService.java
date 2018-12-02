package com.jc.system.common.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @title GOA V2.0
 * @description 生成注册文件（进行IE设置）
 * @author 孙圣然
 * @version  2014年7月8日
 */
public interface IRegService {
	/**
	  * 返回注册表文件内容方法
	  * @author 孙圣然
	  * @version 1.0 2014年7月8日 上午9:04:29
	*/
	String getRegStr(HttpServletRequest request);
	
	/**
	  * 压缩辅助安装程序
	  * @author 孙圣然
	  * @version 1.0 2014年7月9日 上午10:56:40
	*/
	void zipSetupFile();
}
