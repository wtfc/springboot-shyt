package com.jc.system.dic.cache.init;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.SpringUtil;
import com.jc.system.dic.IOtherDicService;
import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.domain.BaseDic;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.OtherDic;

/**
 * @description: 初始化自定义的字典
 */
public class InitOtherDic extends InitBase {

	@Override
	public void init(List<Dic> list, CacheDate cacheData) {
		// 获得属性文件
		List<OtherDic> dicConfList = GlobalContext.logCinfigList;
		for (OtherDic dicConf : dicConfList) {
			IOtherDicService service = null;
			try {
				service = (IOtherDicService) SpringUtil.ctx.getBean(SpringUtil
						.getBeanName(dicConf.getServiceStr()));
			} catch (Exception e) {
				logger.error("实例化service出错,配置内容为:" + dicConf.toString(), e);
				continue;
			}
			List<BaseDic> dicList = service.queryAll();
			initData(dicConf.getFlag(), dicList, cacheData);
		}
	}

	/**
	 * @description 初始化数据
	 */
	private void initData(String flag, List<BaseDic> dicList,
			CacheDate cacheData) {
		Map<String, Map<String, BaseDic>> map = new HashMap<String, Map<String, BaseDic>>();
		Map<String, BaseDic> typeMap = new HashMap<String, BaseDic>();
		for (BaseDic dic : dicList) {
			if (dic.isRoot()) {
				if (map.get(dic.getCode()) == null) {
					map.put(dic.getCode(), new LinkedHashMap<String, BaseDic>());
					typeMap.put(dic.getCode(), dic);
				}
			} else {
				if (map.get(dic.getParentId()) == null) {
					map.put(dic.getParentId(),
							new LinkedHashMap<String, BaseDic>());
					for (BaseDic parentDic : dicList) {
						if (parentDic.getCode().equals(dic.getParentId())) {
							typeMap.put(parentDic.getCode(), parentDic);
							break;
						}
					}
				}
				map.get(dic.getParentId()).put(dic.getCode(), dic);
			}
		}
		cacheData.otherDicMap.put(flag, map);
		cacheData.otherDicTypeMap.put(flag, typeMap);
	}
}
