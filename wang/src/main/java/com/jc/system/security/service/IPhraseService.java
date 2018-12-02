package com.jc.system.security.service;

import java.util.List;
import java.util.Map;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.IBaseService;
import com.jc.system.security.domain.Phrase;

/**
 * @title GOA2.0
 * @description  业务接口类
 * @author 
 * @version  2014-04-28
 */

public interface IPhraseService extends IBaseService<Phrase>{
	/**
	  * 获取当前人的常用词(包括个人与公共)
	  * @param userId 查询人员id
	  * @return Map<String,List<Phrase>>查询结果  key:person为个人，key:common为公共
	  * @author 孙圣然
	  * @version 1.0 2014年5月14日 上午10:07:47
	*/
	Map<String,List<Phrase>> queryPhrase(Long userId);
	
	/**
	 * 查询所有常用词
	 * @param Phrase phrase
	 * @return PageManager
	 */
	public PageManager queryPhraseForUser(Phrase phrase,PageManager page);
	
}