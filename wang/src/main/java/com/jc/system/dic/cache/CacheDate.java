package com.jc.system.dic.cache;

import java.util.HashMap;
import java.util.Map;

import com.jc.system.dic.domain.BaseDic;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;

/**
 * @description: 缓存的数据
 */
public class CacheDate {
	public Map<String, DicType> dicTypeMap = null;
	public Map<String, Map<String, Dic>> dicMap = null; // 数据缓存 格式：
														// <typyCode,<code,dic>>

	public Map<String, Map<String, Map<String, BaseDic>>> otherDicMap = null; // 自定义字典<flag,parent,<code,dic>>
	public Map<String, Map<String, BaseDic>> otherDicTypeMap = null; // 自定义字典<flag,parent,<code,dic>>

	public CacheDate() {
		dicTypeMap = new HashMap<String, DicType>();
		dicMap = new HashMap<String, Map<String, Dic>>();
		otherDicMap = new HashMap<String, Map<String, Map<String, BaseDic>>>();
		otherDicTypeMap = new HashMap<String, Map<String, BaseDic>>();
	}
}
