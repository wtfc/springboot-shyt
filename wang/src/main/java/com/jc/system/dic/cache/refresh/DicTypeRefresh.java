package com.jc.system.dic.cache.refresh;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;
import com.jc.system.dic.service.IDicService;

/**
 * @description: 刷新字典类型
 */
public class DicTypeRefresh extends RefreshBase {

	IDicService dicService = SpringContextHolder.getBean(IDicService.class);

	@Override
	public void refresh(Dic dic, Dic oldDic, CacheDate cacheData) {
		if (oldDic != null && oldDic.getTypeFlag() == Dic.TYPE_FLAG_TRUE) {
			cacheData.dicMap.remove(dic.getCode());
			cacheData.dicTypeMap.remove(dic.getCode());
		}

		Dic dicType = dicService.get(dic);
		if (dicType != null && dicType.getTypeFlag() == Dic.TYPE_FLAG_TRUE) {
			Dic itemDic = new Dic();
			itemDic.setParentId(dicType.getCode());
			List<Dic> list = dicService.query(itemDic);
			Map<String, Dic> itemMap = new LinkedHashMap<String, Dic>();
			for (Dic item : list) {
				itemMap.put(item.getCode(), item);
			}
			cacheData.dicMap.put(dicType.getCode(), itemMap);
			cacheData.dicTypeMap.put(dicType.getCode(), new DicType(dicType));
		}
	}
}
