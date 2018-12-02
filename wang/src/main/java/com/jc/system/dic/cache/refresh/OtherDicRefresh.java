package com.jc.system.dic.cache.refresh;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.SpringUtil;
import com.jc.system.dic.IOtherDicService;
import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.domain.BaseDic;
import com.jc.system.dic.domain.OtherDic;

public class OtherDicRefresh {

	private Logger logger = Logger.getLogger(OtherDicRefresh.class);

	private String flag;
	List<BaseDic> dicList = null;

	public void setFlag(String flag) {
		this.flag = flag;
		List<OtherDic> dicConfList = GlobalContext.logCinfigList;
		for (OtherDic dicConf : dicConfList) {
			if (dicConf.getFlag().equals(flag)) {
				IOtherDicService service = null;
				try {
					service = (IOtherDicService) SpringUtil.ctx
							.getBean(SpringUtil.getBeanName(dicConf
									.getServiceStr()));
				} catch (Exception e) {
					logger.error("实例化service出错,配置内容为:" + dicConf.toString(), e);
					continue;
				}
				dicList = service.queryAll();
				break;
			}
		}
	}

	public void refresh(BaseDic dic, BaseDic oldDic, CacheDate cacheData) {
		Map<String, Map<String, BaseDic>> dicMap = cacheData.otherDicMap
				.get(flag);
		Map<String, BaseDic> typeMap = cacheData.otherDicTypeMap.get(flag);
		if (oldDic != null) {
			if (oldDic.getParentId() != null && oldDic.isRoot() == false) {
				dicMap.get(oldDic.getParentId()).remove(oldDic.getCode());
			}
			dicMap.remove(oldDic.getCode());
			typeMap.remove(oldDic.getCode());
		}
		if (dic != null) {
			if (dic.isRoot()) {
				dicMap.put(dic.getCode(), new LinkedHashMap<String, BaseDic>());
				typeMap.put(dic.getCode(), dic);
			} else {
				String parentId = dic.getParentId();
				if (dicMap.get(parentId) == null) {
					dicMap.put(parentId, new LinkedHashMap<String, BaseDic>());
					for (BaseDic parentDic : dicList) {
						if (parentDic.getCode().equals(parentId)) {
							typeMap.put(parentDic.getCode(), parentDic);
							break;
						}
					}
				}
				dicMap.get(parentId).put(dic.getCode(), dic);
			}
			// 更新本节点的子节点
			for (BaseDic childDic : dicList) {
				if (childDic.getParentId().equals(dic.getCode())) {
					if (dicMap.get(dic.getCode()) == null) {
						dicMap.put(dic.getCode(),
								new LinkedHashMap<String, BaseDic>());
						typeMap.put(dic.getCode(), dic);
					}
					dicMap.get(dic.getCode()).put(childDic.getCode(), childDic);
				}
			}
		}
	}

}
