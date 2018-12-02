package com.jc.system.security.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.dao.IPhraseDao;
import com.jc.system.security.domain.Phrase;
import com.jc.system.security.domain.User;
import com.jc.system.security.service.IPhraseService;

/**
 * @title GOA2.0
 * @description  业务服务类
 * @author 
 * @version  2014-04-28
 */
@Service
public class PhraseServiceImpl extends BaseServiceImpl<Phrase> implements IPhraseService{

	private IPhraseDao phraseDao;
	
	public PhraseServiceImpl(){}
	
	@Autowired
	public PhraseServiceImpl(IPhraseDao phraseDao){
		super(phraseDao);
		this.phraseDao = phraseDao;
	}
	
	/**
	  * 获取当前人的常用词(包括个人与公共)
	  * @param userId 查询人员id
	  * @return Map<String,List<Phrase>>查询结果  key:person为个人，key:common为公共
	  * @author 孙圣然
	  * @version 1.0 2014年5月14日 上午10:07:47
	*/
	public Map<String,List<Phrase>> queryPhrase(Long userId){
		Map<String,List<Phrase>> result = new HashMap<String, List<Phrase>>();
		result.put("person", queryPersonPhrase(userId));
		result.put("common", queryCommonPhrase());
		return result;
	}
	
	/**
	  * 查询个人常用词
	  * @param userId 人员id
	  * @return	List<Phrase> 个人常用词列表
	  * @author 孙圣然
	  * @version 1.0 2014年5月14日 上午10:11:09
	*/
	private List<Phrase> queryPersonPhrase(Long userId){
		Phrase phrase = new Phrase();
		phrase.setPhraseType("1");
		phrase.setCreateUser(userId);
		phrase.addOrderByFieldDesc("t.CREATE_DATE");
		return phraseDao.queryAll(phrase);
	}
	
	/**
	  * 查询公共常用词
	  * @param userId 人员id
	  * @return	List<Phrase> 公共常用词列表
	  * @author 孙圣然
	  * @version 1.0 2014年5月14日 上午10:11:09
	*/
	private List<Phrase> queryCommonPhrase(){
		User user = SystemSecurityUtils.getUser();
		Phrase phrase = new Phrase();
		phrase.setPhraseType("0");
		phrase.setCreateUserOrg(user.getOrgId());
		phrase.addOrderByFieldDesc("t.CREATE_DATE");
		return phraseDao.queryAll(phrase);
	}

	public PageManager queryPhraseForUser(Phrase phrase,PageManager page) {
		return phraseDao.queryPhraseForUser(phrase,page);
	}
}