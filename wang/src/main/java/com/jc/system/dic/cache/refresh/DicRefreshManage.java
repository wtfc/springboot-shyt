package com.jc.system.dic.cache.refresh;

import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.domain.BaseDic;
import com.jc.system.dic.domain.Dic;

/**
 * @description: 刷新管理类
 */
public class DicRefreshManage {
	public void refresh(Dic dic, Dic oldDic, CacheDate cacheData) {
		Dic dictemp = new Dic();
		dictemp.setId(dic.getId());
		dictemp.setCode(dic.getCode());
		dictemp.setParentId(dic.getParentId());
		new DicTypeRefresh().refresh(dictemp, oldDic, cacheData);
		new DicItemRefresh().refresh(dictemp, oldDic, cacheData);
	}

	public void refresh(String flag, BaseDic dic, BaseDic oldDic,
			CacheDate cacheData) {
		OtherDicRefresh refresh = new OtherDicRefresh();
		refresh.setFlag(flag);
		refresh.refresh(dic, oldDic, cacheData);
	}
}
