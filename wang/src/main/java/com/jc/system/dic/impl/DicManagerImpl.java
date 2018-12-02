package com.jc.system.dic.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.jc.system.CustomException;
import com.jc.system.common.util.DateUtils;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.SpringContextHolder;
import com.jc.system.common.util.SpringUtil;
import com.jc.system.dic.IDicManager;
import com.jc.system.dic.IOtherDicService;
import com.jc.system.dic.cache.IDicCacheService;
import com.jc.system.dic.cache.impl.DicCacheService;
import com.jc.system.dic.domain.BaseDic;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;
import com.jc.system.dic.domain.OtherDic;
import com.jc.system.dic.service.IDicService;
import com.jc.system.dic.service.IDicTypeService;

/**
 * @description: 字典管理实现类
 */
public class DicManagerImpl implements IDicManager {

	IDicCacheService cacheService = DicCacheService.getInstance();

	// IDicService dicService = SpringUtil.ctx.getBean(IDicService.class);
	IDicService dicService = SpringContextHolder.getBean(IDicService.class);
	// IDicTypeService typeService =
	// SpringUtil.ctx.getBean(IDicTypeService.class);
	IDicTypeService typeService = SpringContextHolder
			.getBean(IDicTypeService.class);

	private Logger log = Logger.getLogger(DicManagerImpl.class);

	@Override
	public void init() {
		cacheService.init();
	}

	/**
	 * @description 根据字典类型获得标志可以使用的字典
	 * @param typeCode
	 *            字典类型码
	 */
	public List<Dic> getDicsByTypeCode(String typeCode) {
		return cacheService.getDicsByTypeCode(typeCode);
	}

	/**
	 * @description 根据字典类型获得自定义的字典
	 * @param typeCode
	 *            字典类型码
	 */
	public List<BaseDic> getDicsByTypeCode(String flag, String typeCode) {
		return cacheService.getDicsByTypeCode(flag, typeCode);
	}

	/**
	 * @description 根据字典类型获得字典
	 * @param typeCode
	 *            字典类型吗
	 * @param useFlag
	 *            true:返回标志启用的字典 false:返回所有（包括标志不可用）的字典
	 */
	public List<Dic> getDicsByTypeCode(String typeCode, Boolean useFlag) {
		if (useFlag == false) {
			return cacheService.getDicsByTypeCodeAll(typeCode);
		} else {
			return getDicsByTypeCode(typeCode);
		}
	}

	/**
	 * @description 获得字典
	 * @param typeCode
	 *            字典类型code
	 * @param dicCode
	 *            字典code
	 */
	public Dic getDic(String typeCode, String dicCode) {
		return cacheService.getDic(typeCode, dicCode);
	}

	/**
	 * @description 获得自定义的字典
	 * @param flag
	 *            标示
	 * @param typeCode
	 *            类型code
	 * @param dicCode
	 *            字典code
	 */
	public BaseDic getDic(String flag, String typeCode, String dicCode) {
		return cacheService.getDic(flag, typeCode, dicCode);
	}

	/**
	 * @description 获得字典类型类型
	 */
	public DicType getDicType(String code) {
		return cacheService.getType(code);
	}

	/**
	 * @description 获得字典类型
	 */
	public BaseDic getType(String flag, String code) {
		return cacheService.getType(flag, code);
	}

	/**
	 * @description 获得所有类型
	 * @return 树形结构（子节点放入到children中）
	 */
	public List<DicType> getDicTypes() {
		return cacheService.getTypes();
	}

	/**
	 * @description 获得自定义的所有类型（树形结构）
	 */
	public List<BaseDic> getTypes(String flag) {
		return cacheService.getTypes(flag);
	}

	/**
	 * @description 新增字典类型
	 * @param dicType
	 *            字典类型
	 * @throws CustomException 
	 */
	public DicType addNewDicType(DicType dicType) throws CustomException {
		Dic dicTemp = new Dic();
		dicTemp.setCode(dicType.getCode());
		// dicTemp.setDicFlag(1);
		if (dicService.query(dicTemp).size() > 0) {
			log.info("存在相同code的字典类型,字典类型为：" + dicType.getCode());
			return null;
		}
		return typeService.save(dicType);
	}

	/**
	 * @description 新增字典
	 * @param dic
	 *            新字典bean
	 * @throws CustomException 
	 */
	public Dic addNewDic(Dic dic) throws CustomException {
		Dic dicTemp = new Dic();
		dicTemp.setCode(dic.getCode());
		// dicTemp.setParentId(dic.getParentId());
		// dicTemp.setDicFlag(1);
		if (dicService.query(dicTemp).size() > 0) {
			log.info("不存在传入code的字典,字典code为：" + dic.getCode());
			return null;
		}
		if (dic.getOrderFlag() == null) {
			dic.setOrderFlag(1);
		}
		dic.setDicFlag(1);
		dic.setTypeFlag(0);
		dic.setUseFlag(1);

		return dicService.saveDic(dic);
	}
	
	/**
	 * @description 批量新增字典
	 * @param dicList
	 *            新字典bean集合
	 * @throws CustomException 
	 */
	public Integer addNewDicList(List<Dic> dicList) throws CustomException {
		if(dicList != null && dicList.size() > 0){
			Dic dic = new Dic();
			String parentId = "";
			String ids = "";
			for(int i=0;i<dicList.size();i++){
				Dic dicTemp = dicList.get(i);
				parentId = dicTemp.getParentId();
				if(dicTemp.getId() != null){
					if(ids.equals(""))
						ids = String.valueOf(dicTemp.getId());
					else
						ids = ids + "," + String.valueOf(dicTemp.getId());
					dicTemp.setModifyDate(DateUtils.getSysDate());
					dicService.update(dicTemp);
				} else{
					dicTemp.setCreateDate(DateUtils.getSysDate());
					dicTemp.setModifyDate(DateUtils.getSysDate());
					dicService.saveDic(dicTemp);
					if(ids.equals(""))
						ids = String.valueOf(dicTemp.getId());
					else
						ids = ids + "," + dicTemp.getId();
				}
			}
			dic.setPrimaryKeys(ids.split(","));
			dic.setParentId(parentId);
			dicService.delForDicList(dic);
			
			Dic redic = new Dic();
			redic.setParentId(parentId);
			redic.setDeleteFlag(1);
			List<Dic> rediclist = dicService.query(redic);
			if(rediclist != null && rediclist.size() > 0){
				for(int i=0;i<rediclist.size();i++){
					Dic redicTemp = rediclist.get(i);
					cacheService.refreshDicItem(redicTemp,redicTemp);
				}
			}
			
			return 1;
		}else {
			return 0;
		}
	}

	/**
	 * @description 新建自定义节点
	 */
	public BaseDic addOtherDic(String flag, BaseDic dic) {
		IOtherDicService service = null;
		List<OtherDic> dicConfigList = GlobalContext.logCinfigList;
		String serviceStr = null;
		for (OtherDic dicConfig : dicConfigList) {
			if (dicConfig.getFlag().equals(flag)) {
				serviceStr = dicConfig.getServiceStr();
				break;
			}
		}
		if (serviceStr == null) {
			log.error("没有找到标志为:" + flag + "的配置文件");
			return null;
		}
		service = (IOtherDicService) SpringUtil.ctx.getBean(SpringUtil
				.getBeanName(serviceStr));
		if (service == null) {
			log.error("实例化service出错,service为:" + serviceStr);
		}
		dic = service.save(dic);
		cacheService.refreshOtherDic(flag, dic, null);
		return dic;
	}

	/**
	 * @description 删除字典
	 * @param dicType
	 *            字典
	 * @throws CustomException 
	 */
	public Dic removeDic(Dic dic) throws CustomException {
		Dic dicTemp = new Dic();
		dicTemp.setCode(dic.getCode());
		dicTemp.setParentId(dic.getParentId());
		dicTemp = dicService.get(dicTemp);
		if (dicTemp == null) {
			log.info("不存在传入code的字典,typeId为:" + dic.getParentId() + ",字典code为："
					+ dic.getCode());
			return null;
		}
		dicService.delete(dicTemp);
		return dicTemp;
	}

	/**
	 * @description 删除字典(自定义)
	 * @param dicType
	 *            字典
	 */
	public BaseDic removeDic(String flag, BaseDic dic) {
		BaseDic oldDic = (BaseDic) dic.clone();
		IOtherDicService service = null;
		List<OtherDic> dicConfigList = GlobalContext.logCinfigList;
		String serviceStr = null;
		for (OtherDic dicConfig : dicConfigList) {
			if (dicConfig.getFlag().equals(flag)) {
				serviceStr = dicConfig.getServiceStr();
				break;
			}
		}
		if (serviceStr == null) {
			log.error("没有找到标志为:" + flag + "的配置文件");
			return null;
		}
		service = (IOtherDicService) SpringUtil.ctx.getBean(SpringUtil
				.getBeanName(serviceStr));
		if (service == null) {
			log.error("实例化service出错,service为:" + serviceStr);
		}
		removeDicChild(flag, oldDic, service);
		return oldDic;
	}

	/**
	 * @description 递归删除子节点
	 */
	private void removeDicChild(String flag, BaseDic parentDic,
			IOtherDicService service) {
		List<BaseDic> list = service.queryChildren(parentDic.getCode());
		for (BaseDic dic : list) {
			removeDicChild(flag, dic, service);
		}
		service.delete(parentDic);
		cacheService.refreshOtherDic(flag, null, parentDic);
	}

	/**
	 * @description 删除字典类型
	 * @param dicType
	 *            字典类型
	 * @throws CustomException 
	 */
	public DicType removeDicType(DicType dicType) throws CustomException {
		DicType dicTypeTemp = new DicType();
		dicTypeTemp.setCode(dicType.getCode());
		dicTypeTemp = typeService.get(dicTypeTemp);
		if (dicTypeTemp == null) {
			log.info("不存在传入code的字典类型,字典类型为：" + dicType.getCode());
			return null;
		}
		typeService.delete(dicTypeTemp);
		return dicTypeTemp;
	}

	/**
	 * @description 更新字典
	 * @param dicType
	 *            字典
	 * @throws CustomException 
	 */
	public Dic updateDic(Dic dic) throws CustomException {
		Dic dicTemp = new Dic();
		dicTemp.setId(dic.getId());
		dicTemp.setCode(dic.getCode());
		dicTemp.setDicFlag(1);
		dicTemp = dicService.get(dicTemp);
		if (dicTemp == null) {
			log.info("不存在传入code的字典,typeId为:" + dic.getParentId() + ",字典code为："
					+ dic.getCode());
			return null;
		}
		dicService.update(dic);
		return dicTemp;
	}

	/**
	 * @description 更行dic方法(自定义)
	 * @param dic
	 *            （由code以及parent确定字典的更新实例）
	 */
	public BaseDic updateDic(String flag, BaseDic dic) {
		BaseDic oldDic = (BaseDic) dic.clone();
		IOtherDicService service = null;
		List<OtherDic> dicConfigList = GlobalContext.logCinfigList;
		String serviceStr = null;
		for (OtherDic dicConfig : dicConfigList) {
			if (dicConfig.getFlag().equals(flag)) {
				serviceStr = dicConfig.getServiceStr();
				break;
			}
		}
		if (serviceStr == null) {
			log.error("没有找到标志为:" + flag + "的配置文件");
			return null;
		}
		service = (IOtherDicService) SpringUtil.ctx.getBean(SpringUtil
				.getBeanName(serviceStr));
		if (service == null) {
			log.error("实例化service出错,service为:" + serviceStr);
		}
		BaseDic newDic = service.update(oldDic);
		cacheService.refreshOtherDic(flag, newDic, oldDic);
		return newDic;
	}

	/**
	 * @description 更新字典类型
	 * @param dicType
	 *            字典类型
	 * @throws CustomException 
	 */
	public DicType updateType(DicType dicType) throws CustomException {
		DicType dicTypeTemp = new DicType();
		dicTypeTemp.setId(dicType.getId());
		dicTypeTemp.setCode(dicType.getCode());
		dicTypeTemp = typeService.get(dicTypeTemp);
		if (dicTypeTemp == null) {
			log.info("不存在传入code的字典类型,字典类型为：" + dicType.getCode());
			return null;
		}
		typeService.updateDicType(dicType);
		return dicTypeTemp;
	}

	/**
	 * @description 将字典转换为字典类型，以增加字典
	 * @param dic
	 *            .parentId 类型id
	 * @param dic
	 *            .code 字典code
	 * @throws CustomException 
	 */
	public Dic changeDicToType(Dic dic) throws CustomException {
		Dic cacheDic = getDic(dic.getParentId(), dic.getCode());
		// 如果字典更新前不是类型需要更新类型
		if (cacheDic.getTypeFlag() == Dic.TYPE_FLAG_FALSE) {
			if (getDicType(dic.getCode()) != null) {
				log.info("存在字典code相同的字典类型:" + cacheDic.toString() + ",转换失败");
				return null;
			} else {
				log.info("开始将字典转换为字典类型:" + cacheDic.toString());
				dic.setTypeFlag(Dic.TYPE_FLAG_TRUE);
				dicService.update(dic);
				log.info("字典转换为字典类型结束:" + cacheDic.toString());
			}
		} else {
			log.info("不需要转换字典类型:" + cacheDic.toString());
		}
		return getDic(dic.getParentId(), dic.getCode());
	}

	public Dic getDic(Long dicId) {
		Dic dic = new Dic();
		dic.setId(dicId);
		return dicService.get(dic);
	}

}
