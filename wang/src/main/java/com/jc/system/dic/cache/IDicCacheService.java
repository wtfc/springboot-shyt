package com.jc.system.dic.cache;

import java.util.List;

import com.jc.system.dic.domain.BaseDic;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;

/**
 * @description:字典缓存服务接口
 */
public interface IDicCacheService {

	/**
	 * @description 初始化函数
	 */
	void init();

	/**
	 * @description 根据字典类型获得标志可以使用的字典
	 * @param typeCode
	 *            字典类型码
	 */
	List<Dic> getDicsByTypeCode(String typeCode);

	/**
	 * @description 根据字典类型获得自定义的字典
	 * @param typeCode
	 *            字典类型码
	 */
	List<BaseDic> getDicsByTypeCode(String flag, String typeCode);

	/**
	 * @description 根据字典类型获得（包括标志不可用）字典
	 * @param typeCode
	 *            字典类型码
	 */
	List<Dic> getDicsByTypeCodeAll(String typeCode);

	/**
	 * @description 获得字典
	 * @param typeCode
	 *            字典类型code
	 * @param dicCode
	 *            字典code
	 */
	Dic getDic(String typeCode, String dicCode);

	/**
	 * @description 获得自定义的字典
	 * @param flag
	 *            标示
	 * @param typeCode
	 *            类型code
	 * @param dicCode
	 *            字典code
	 */
	BaseDic getDic(String flag, String typeCode, String dicCode);

	/**
	 * @description 获得所有类型
	 * @return 树形结构（子节点放入到children中）
	 */
	List<DicType> getTypes();

	/**
	 * @description 获得自定义的所有类型（树形结构）
	 */
	List<BaseDic> getTypes(String flag);

	/**
	 * @description 获得字典类型
	 */
	DicType getType(String code);

	/**
	 * @description 获得字典类型
	 */
	BaseDic getType(String flag, String code);

	/**
	 * @description 刷新缓存（项目和类型）
	 */
	void refreshDicItem(Dic dic, Dic oldDic);

	/**
	 * @description 刷新缓存（自定义id）
	 */
	void refreshOtherDic(String flag, BaseDic dic, BaseDic oldDic);
}
