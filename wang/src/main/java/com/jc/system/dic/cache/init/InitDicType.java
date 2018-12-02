package com.jc.system.dic.cache.init;

import java.util.LinkedHashMap;
import java.util.List;

import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;

/**
 * @description: 初始化数据类型
 */
public class InitDicType extends InitBase {

	@Override
	public void init(List<Dic> list, CacheDate cacheData) {
		for (Dic dic : list) {
			if (cacheData.dicMap.get(dic.getCode()) != null) {
				logger.error("已经存在相同code的类型,code值为:" + dic.getCode());
			}
			if (Dic.TYPE_FLAG_TRUE == dic.getTypeFlag()) {
				cacheData.dicMap.put(dic.getCode(),
						new LinkedHashMap<String, Dic>());
				cacheData.dicTypeMap.put(dic.getCode(), new DicType(dic));
			}
		}

		InitBase initItem = new InitDicItem();
		initItem.init(list, cacheData);
	}

}
