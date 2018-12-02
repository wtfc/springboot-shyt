/**
 * 
 */
package com.jc.foundation.domain;

/**
 * @title GOA V2.0
 * @description 权重管理接口
 * @version  2014年5月7日上午8:31:04
 */
public interface ISecurityBean extends IBaseBean{
	
	/**
	  * 获取权重
	  * @return
	  * @version 1.0 2014年5月23日 下午2:20:44
	*/
	public Integer getWeight();
	
	/**
	  * 设置权重
	  * @param weight 权重值
	  * @version 1.0 2014年5月23日 下午2:20:59
	*/
	public void setWeight(Integer weight);
}
