/**
 * 
 */
package com.jc.foundation.service;

import java.util.List;

import com.jc.foundation.domain.BaseBean;

/**
 * @title GOA V2.0
 * @description 用于给对象添加默认值
 * @version  2014年5月9日上午8:49:05
 */
public interface IBeanPropertyService {
	//public void fillProperties(BaseBean bean);
	
	/**
	  * 添加默认属性
	  * @param bean 需要添加默认值对象
	  * @param modify 是否是修改
	  * @version 1.0 2014年5月9日 上午10:53:05
	*/
	public void fillProperties(BaseBean bean,boolean modify);
	
	
	/**
	  * 批量设置bean属性
	  * @param list  批量设置的列表
	  * @param modify 是否是修改
	  * @return 返回复制后的列表
	  * @version 1.0 2014年7月9日 上午8:56:02
	*/
	public <T extends BaseBean>  List<T> fillProperties(List<T> list, boolean modify) ;
}
