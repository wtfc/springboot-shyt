package com.jc.system.dic.cache.refresh;

import com.jc.system.dic.cache.CacheDate;
import com.jc.system.dic.domain.Dic;

/**
 * @description: 缓存刷新基础类
 */
public abstract class RefreshBase {
	public abstract void refresh(Dic dic, Dic oldDic, CacheDate cacheData);
}
