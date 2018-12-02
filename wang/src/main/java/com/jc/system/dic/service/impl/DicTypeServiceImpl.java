package com.jc.system.dic.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.jc.system.CustomException;
import com.jc.system.DBException;
import com.jc.system.dic.cache.IDicCacheService;
import com.jc.system.dic.cache.impl.DicCacheService;
import com.jc.system.dic.dao.IDicDao;
import com.jc.system.dic.dao.IDicTypeDao;
import com.jc.system.dic.domain.Dic;
import com.jc.system.dic.domain.DicType;
import com.jc.system.dic.service.IDicTypeService;

/**
 * @description: 流程类型服务实现类
 */
@Service
public class DicTypeServiceImpl implements IDicTypeService {

	public DicTypeServiceImpl(){
		
	}
	
	@Resource
	private IDicTypeDao dicTypeDao;

	@Resource
	private IDicDao dicDao;

	public DicType save(DicType dicType) throws CustomException{
		IDicCacheService cacheService = DicCacheService.getInstance();
		dicTypeDao.save(dicType);
		cacheService.refreshDicItem(new Dic(dicType), null);
		return dicType;
	}

	public List<DicType> query(DicType dicType) throws CustomException {
		return dicTypeDao.queryAll(dicType);
	}

	public DicType get(DicType dicType) {
		return dicTypeDao.get(dicType);
	}

	/**
	 * @description 删除流程实例(递归删除子类型)
	 * @param dicType
	 * @throws CustomException 
	 */
	public void delete(DicType dicType) throws CustomException {
		// 递归删除子类型
		deleteChild(dicType, 0);
	}

	/**
	 * @description 递归删除子类型,如果对于删除的节点为父类型的字典，只需要将类型标志置为0
	 * @param dicType
	 *            子类型
	 * @throws DBException 
	 */
	private void deleteChild(DicType dicType, int level) throws CustomException {
		IDicCacheService cacheService = DicCacheService.getInstance();

		if (level == 0 && !"-1".equals(dicType.getParentId())) {
			Dic dic = cacheService.getDic(dicType.getParentId(),
					dicType.getCode());
			if (dic != null) {
				dic.setTypeFlag(Dic.TYPE_FLAG_FALSE);
				dicDao.update(dic);
			}
		}

		DicType child = new DicType();
		child.setParentId(dicType.getCode());
		List<DicType> childrenList = query(child);
		for (DicType item : childrenList) {
			deleteChild(item, level + 1);
		}
		// 删除对应字典项目
		Dic childDic = new Dic();
		childDic.setParentId(dicType.getCode());
		childDic.setDicFlag(Dic.DIC_FLAG_TRUE);
		dicDao.delete(childDic, false);
		dicTypeDao.delete(dicType);

		cacheService.refreshDicItem(new Dic(dicType), new Dic(dicType));
	}

	/**
	 * @description 更新字典类型
	 * @param dicType
	 *            字典类型
	 * @throws CustomException 
	 */
	public void updateDicType(DicType dicType) throws CustomException {
		dicTypeDao.update(dicType);
		IDicCacheService cacheService = DicCacheService.getInstance();
		cacheService.refreshDicItem(new Dic(dicType), new Dic(dicType));
	}
}
