package com.jc.system.dic;

import java.util.List;

import com.jc.system.dic.domain.BaseDic;

/**
 * @description: 自定义字典的service类
 */
public interface IOtherDicService {
	List<BaseDic> queryAll();

	List<BaseDic> queryChildren(String parentId);

	BaseDic save(BaseDic dic);

	BaseDic delete(BaseDic dic);

	BaseDic update(BaseDic dic);
}
