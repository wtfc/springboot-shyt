package com.jc.system.dic.cache.init;

import java.util.LinkedHashMap;
import java.util.List;

import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.domain.Dic;

/**
 * @description: 初始化字典项目
 */
public class InitDicItem extends InitBase {

	@Override
	public void init(List<Dic> list, CacheDate cacheData) {
		for (Dic dic : list) {
			if (Dic.DIC_FLAG_TRUE == dic.getDicFlag()) {
				LinkedHashMap<String, Dic> map = (LinkedHashMap<String, Dic>) cacheData.dicMap
						.get(dic.getParentId());
				if (map == null) {
					logger.error("没有code为" + dic.getParentId() + "的字典类型");
					continue;
				}
				map.put(dic.getCode(), dic);
			}
		}

		InitBase otherDic = new InitOtherDic();
		otherDic.init(null, cacheData);
	}

}
