package com.jc.system.dic;

import java.util.List;

import com.jc.system.CustomException;
import com.jc.system.dic.domain.BaseDic;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;

/**
 * @description: 数据字典管理类
 */
public interface IDicManager {
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
	 * @description 根据字典类型获得字典
	 * @param typeCode
	 *            字典类型吗
	 * @param useFlag
	 *            true:返回标志启用的字典 false:返回所有（包括标志不可用）的字典
	 */
	List<Dic> getDicsByTypeCode(String typeCode, Boolean useFlag);

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
	List<DicType> getDicTypes();

	/**
	 * @description 获得自定义的所有类型（树形结构）
	 */
	List<BaseDic> getTypes(String flag);

	/**
	 * @description 新增字典
	 * @param dic
	 *            新字典bean
	 */
	Dic addNewDic(Dic dic) throws CustomException;
	
	/**
	 * @description 批量新增字典
	 * @param dicList
	 *            新字典bean集合
	 */
	Integer addNewDicList(List<Dic> dicList) throws CustomException;

	/**
	 * @description 新建自定义节点
	 */
	BaseDic addOtherDic(String flag, BaseDic dic);

	/**
	 * @description 新增字典类型
	 * @param dicType
	 *            字典类型
	 */
	DicType addNewDicType(DicType dicType) throws CustomException;

	/**
	 * @description 删除字典类型
	 * @param dicType
	 *            字典类型
	 */
	DicType removeDicType(DicType dicType) throws CustomException;

	/**
	 * @description 删除字典
	 * @param dic
	 *            字典
	 */
	Dic removeDic(Dic dic) throws CustomException;

	/**
	 * @description 删除字典(自定义)
	 * @param dicType
	 *            字典(需要parentId,以及code)
	 */
	BaseDic removeDic(String flag, BaseDic dic);

	/**
	 * @description 更新字典
	 * @param dicType
	 *            字典
	 */
	Dic updateDic(Dic dic) throws CustomException;

	/**
	 * @description 更行dic方法(自定义)
	 * @param dic
	 *            （由code以及parent确定字典的更新实例）
	 */
	BaseDic updateDic(String flag, BaseDic dic);

	/**
	 * @description 更新字典类型
	 * @param dicType
	 *            字典类型
	 */
	DicType updateType(DicType dicType) throws CustomException;

	/**
	 * @description 将字典转换为字典类型，以增加字典
	 * @param dic
	 *            .parentId 类型id
	 * @param dic
	 *            .code 字典code
	 */
	Dic changeDicToType(Dic dic) throws CustomException;
	
	/**
	 * @description 获得字典
	 * @param dicId
	 *            字典id
	 */
	Dic getDic(Long dicId);
}
