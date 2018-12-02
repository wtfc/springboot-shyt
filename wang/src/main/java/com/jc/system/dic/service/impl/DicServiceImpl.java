package com.jc.system.dic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.system.CustomException;
import com.jc.system.dic.cache.IDicCacheService;
import com.jc.system.dic.cache.impl.DicCacheService;
import com.jc.system.dic.dao.IDicDao;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.service.IDicService;

@Service
public class DicServiceImpl extends BaseServiceImpl<Dic> implements IDicService {
	@Autowired
	public DicServiceImpl(IDicDao dicDao) {
		super(dicDao);
		this.dicDao = dicDao;
		// TODO Auto-generated constructor stub
	}
	public DicServiceImpl(){
		
	}
	private IDicDao dicDao;

	@Transactional(rollbackFor = Exception.class)
	public Dic saveDic(Dic dic) throws CustomException {
		dic.setDefaultValue(0);
		dicDao.save(dic);
		IDicCacheService cacheService = DicCacheService.getInstance();
		cacheService.refreshDicItem(dic, null);
		return dic;
	}

	@Transactional(rollbackFor = Exception.class)
	public Integer update(Dic dic) throws CustomException {
		IDicCacheService cacheService = DicCacheService.getInstance();
		Dic oldDic = cacheService.getDic(dic.getParentId(), dic.getCode());
		// 如果为某认选中的话需要现移出同类型的其他类型
		if (dic.getDefaultValue() != null && dic.getDefaultValue() == 1) {
			Dic dicTemp = new Dic();
			dicTemp.setParentId(dic.getParentId());
			dicTemp.setDicFlag(1);
			dicTemp.setDefaultValue(1);
			dicTemp = dicDao.get(dicTemp);
			if (dicTemp != null && !dic.getCode().equals(dicTemp.getCode())) {
				Dic oldDicTemp = dicTemp.clone();
				dicTemp.setDefaultValue(0);
				dicDao.update(dicTemp);
				cacheService.refreshDicItem(dicTemp, oldDicTemp);
			}
		}
		Integer ret = dicDao.update(dic);

		cacheService.refreshDicItem(dic, oldDic);
		return ret;
	}

	/*
	 * @see com.dic.service.IDicService#get(com.dic.bean.Dic)
	 */
	public Dic get(Dic dic) {
		return dicDao.get(dic);
	}

	/**
	 * @description 分页查询方法
	 * @param Dic
	 *            dic 实体类,PageManager page 分页实体类
	 * @return PagingBean 查询结果
	 * @author
	 */
	public PageManager query(Dic dic, PageManager page) {
		return dicDao.queryByPage(dic, page);
	}

	public List<Dic> query(Dic dic){
		return dicDao.queryAll(dic);
	}

	/*
	 * @see com.dic.service.IDicService#delete(com.dic.bean.Dic)
	 */
	@Transactional(rollbackFor = Exception.class)
	public Integer delete(Dic dic) throws CustomException {
		IDicCacheService cacheService = DicCacheService.getInstance();
		Dic oldDic = cacheService.getDic(dic.getParentId(), dic.getCode());
		Integer result = dicDao.delete(dic,false);
		cacheService.refreshDicItem(dic, oldDic);
		return result;
	}
	@Override
	public List<Dic> getDicByDuty(Dic dic) {
		return dicDao.getDicByDuty(dic);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Integer delForDicList(Dic dic) throws CustomException {
		
		return dicDao.delForDicList(dic);
	}

}