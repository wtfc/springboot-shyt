package com.jc.system.dic.cache.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.cache.IDicCacheService;
import com.jc.system.dic.cache.init.InitDicType;
import com.jc.system.dic.cache.refresh.DicRefreshManage;
import com.jc.system.dic.domain.BaseDic;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;
import com.jc.system.dic.service.IDicService;
/**
 * @description: 字典缓存实现类
 */
public class DicCacheService implements IDicCacheService {
	private static final Logger logger = Logger
			.getLogger(DicCacheService.class);

	// IDicService service=SpringUtil.ctx.getBean(DicServiceImpl.class);
	IDicService service = SpringContextHolder.getBean(IDicService.class);

	static CacheDate cacheData = new CacheDate();
	private static DicCacheService cacheService = null;

	private DicCacheService() {
	}

	public static DicCacheService getInstance() {
		if (cacheService == null) {
			cacheService = new DicCacheService();
		}
		return cacheService;
	}

	@Override
	public void init() {
		logger.info("开始初始化数据字典");
		List<Dic> list = service.query(new Dic());
		InitDicType initType = new InitDicType();
		initType.init(list, cacheData);
		logger.info("初始化数据字典完成");
	}

	/**
	 * @description 根据字典类型获得标志可以使用的字典
	 * @param typeCode
	 *            字典类型码
	 */
	public List<Dic> getDicsByTypeCode(String typeCode) {
		List<Dic> result = new ArrayList<Dic>();
		Map<String, Dic> map = cacheData.dicMap.get(typeCode);
		if (map == null) {
			logger.debug("类型值为" + typeCode + "的字典类型不存在");
			return null;
		}
		Collection<Dic> collection = map.values();
		Iterator<Dic> itor = collection.iterator();
		while (itor.hasNext()) {
			Dic item = itor.next();
			if (Dic.USE_FLAG_TRUE == item.getUseFlag()) {
				result.add(item);
			}
		}
		return result;
	}

	/**
	 * @description 根据字典类型获得自定义的字典
	 * @param typeCode
	 *            字典类型码
	 */
	public List<BaseDic> getDicsByTypeCode(String flag, String typeCode) {
		List<BaseDic> result = new ArrayList<BaseDic>();
		Map<String, Map<String, BaseDic>> map = cacheData.otherDicMap.get(flag);
		if (map == null) {
			logger.debug("flag为" + typeCode + "的字典不存在");
			return null;
		}
		Map<String, BaseDic> dicMap = map.get(typeCode);
		if (dicMap == null) {
			logger.debug("flag为:" + flag + ",类型值值为" + typeCode + "的字典不存在");
			return null;
		}
		Collection<BaseDic> collection = dicMap.values();
		Iterator<BaseDic> itor = collection.iterator();
		while (itor.hasNext()) {
			BaseDic item = itor.next();
			result.add(item);
		}
		return result;
	}

	/**
	 * @description 根据字典类型获得（包括标志不可用）字典
	 * @param typeCode
	 *            字典类型码
	 */
	public List<Dic> getDicsByTypeCodeAll(String typeCode) {
		List<Dic> result = new ArrayList<Dic>();
		Map<String, Dic> map = cacheData.dicMap.get(typeCode);
		if (map == null) {
			logger.debug("类型值为" + typeCode + "的字典类型不存在");
			return null;
		}
		result.addAll(map.values());
		Collections.sort(result, new Comparator<Dic>(){  
	          public int compare(Dic a, Dic b) {  
	        	 //第一次比较行标
	        	 int i = a.getCode().compareTo(b.getCode());
	        	 return i;
	          }
		});
		return result;
	}

	/**
	 * @description 获得字典
	 * @param typeCode
	 *            字典类型code
	 * @param dicCode
	 *            字典code
	 */
	public Dic getDic(String typeCode, String dicCode) {
		Map<String, Dic> map = cacheData.dicMap.get(typeCode);
		if (map == null) {
			logger.debug("类型值为" + typeCode + "的字典类型不存在");
			return null;
		}
		Dic dic = map.get(dicCode);
		if (dic == null) {
			logger.debug("类型值为" + typeCode + ",字典值为" + dicCode + "的字典不存在");
			return null;
		}
		return dic.clone();
	}

	/**
	 * @description 获得自定义的字典
	 * @param flag
	 *            标示
	 * @param typeCode
	 *            类型code
	 * @param dicCode
	 *            字典code
	 */
	public BaseDic getDic(String flag, String typeCode, String dicCode) {
		Map<String, Map<String, BaseDic>> map = cacheData.otherDicMap.get(flag);
		if (map == null) {
			logger.debug("类型值为" + typeCode + "的字典类型不存在");
			return null;
		}
		Map<String, BaseDic> typeMap = map.get(typeCode);
		if (typeMap == null) {
			logger.debug("类型值为" + typeCode + ",字典值为" + dicCode + "的类型不存在");
			return null;
		}
		BaseDic dic = typeMap.get(dicCode);
		if (dic == null) {
			logger.debug("类型值为" + typeCode + ",类型值值为" + dicCode + "的类型,字典值为"
					+ dicCode + "的字典不存在");
			return null;
		}
		return (BaseDic) dic.clone();
	}

	/**
	 * @description 获得所有类型
	 * @return 树形结构（子节点放入到children中）
	 */
	public List<DicType> getTypes() {
		List<DicType> list = new ArrayList<DicType>();
		Collection<DicType> types = cacheData.dicTypeMap.values();
		Iterator<DicType> itor = types.iterator();
		while (itor.hasNext()) {
			DicType type = itor.next().clone();
			if ("-1".equals(type.getParentId())) {
				addTypeChildren(type);
				list.add(type);
			}
		}
		return list;
	}

	/**
	 * @description 获得自定义的所有类型（树形结构）
	 */
	public List<BaseDic> getTypes(String flag) {
		Map<String, BaseDic> typeMap = cacheData.otherDicTypeMap.get(flag);
		if (typeMap == null) {
			logger.debug("标示为" + flag + "的字典不存在");
			return null;
		}
		List<BaseDic> list = new ArrayList<BaseDic>();
		Collection<BaseDic> types = typeMap.values();
		Iterator<BaseDic> itor = types.iterator();
		while (itor.hasNext()) {
			BaseDic type = (BaseDic) itor.next().clone();
			if ("-1".equals(type.getParentId())) {
				addOhterTypeChildren(type, typeMap);
				list.add(type);
			}
		}
		return list;
	}

	/**
	 * @description 获得字典类型
	 */
	public BaseDic getType(String flag, String code) {
		Map<String, BaseDic> typeMap = cacheData.otherDicTypeMap.get(flag);
		if (typeMap == null) {
			logger.debug("标示为" + flag + "的字典不存在");
			return null;
		}
		return typeMap.get(code);
	}

	/**
	 * @description 获得自定义的字典类型
	 */
	public DicType getType(String code) {
		return cacheData.dicTypeMap.get(code);
	}

	/**
	 * @description 把type的子节点增加到children中
	 */
	private void addTypeChildren(DicType type) {
		String parentCode = type.getCode();
		Collection<DicType> types = cacheData.dicTypeMap.values();
		Iterator<DicType> itor = types.iterator();
		while (itor.hasNext()) {
			DicType item = itor.next().clone();
			if (item.getParentId().equals(parentCode)) {
				type.addChildren(item);
				addTypeChildren(item);
			}
		}
	}

	/**
	 * @description 把type的子节点增加到children中(自定义字典)
	 */
	private void addOhterTypeChildren(BaseDic type, Map<String, BaseDic> typeMap) {
		String parentCode = type.getCode();
		Collection<BaseDic> types = typeMap.values();
		Iterator<BaseDic> itor = types.iterator();
		while (itor.hasNext()) {
			BaseDic item = (BaseDic) itor.next().clone();
			if (item.getParentId().equals(parentCode)) {
				type.addChildren(item);
				addOhterTypeChildren(item, typeMap);
			}
		}
	}

	/**
	 * @description 刷新缓存（项目和类型）
	 */
	public void refreshDicItem(Dic dic, Dic oldDic) {
		new DicRefreshManage().refresh(dic, oldDic, cacheData);
	}

	/**
	 * @description 刷新缓存（自定义id）
	 */
	public void refreshOtherDic(String flag, BaseDic dic, BaseDic oldDic) {
		new DicRefreshManage().refresh(flag, dic, oldDic, cacheData);
	}
}
