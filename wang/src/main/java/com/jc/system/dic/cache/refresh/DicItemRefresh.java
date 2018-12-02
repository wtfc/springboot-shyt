package com.jc.system.dic.cache.refresh;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.service.IDicService;

/**
 * @description: 字典项目更新类
 */
public class DicItemRefresh extends RefreshBase {

	IDicService dicService = SpringContextHolder.getBean(IDicService.class);

	@Override
	public void refresh(Dic dic, Dic oldDic, CacheDate cacheData) {

		// 去掉缓存里面的该项目
		Iterator<Map<String, Dic>> itor = cacheData.dicMap.values().iterator();
		out: while (itor.hasNext()) {
			Map<String, Dic> map = itor.next();
			Iterator<Dic> itemIt = map.values().iterator();
			while (itemIt.hasNext()) {
				Dic item = itemIt.next();
				if (item.getId().equals(dic.getId())) {
					map.remove(item.getCode());
					break out;
				}
			}
		}

		Dic newDic = dicService.get(dic);
		if (newDic != null && newDic.getDicFlag() == Dic.DIC_FLAG_TRUE) {
			// 不更新排序
			if (oldDic != null
					&& (newDic.getOrderFlag() == null || newDic.getOrderFlag()
							.equals(oldDic.getOrderFlag()))) {
				if (newDic != null) {
					cacheData.dicMap.get(newDic.getParentId()).put(
							newDic.getCode(), newDic);
				}
			} else {
				// 更新排序
				LinkedHashMap<String, Dic> newOrderMap = new LinkedHashMap<String, Dic>();
				Dic dicTemp = new Dic();
				dicTemp.setParentId(newDic.getParentId());
				List<Dic> list = dicService.query(dicTemp);
				for (Dic item : list) {
					newOrderMap.put(item.getCode(), item);
				}
				cacheData.dicMap.put(dic.getParentId(), newOrderMap);
			}
		}

	}

}
