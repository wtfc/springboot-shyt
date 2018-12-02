package com.jc.shjfgl.machine.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.shjfgl.machine.dao.IEquipmentInOutDao;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.domain.EquipmentInOut;
import com.jc.shjfgl.machine.service.IEquipmentInOutService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;

@Service
public class EquipmentInOutServiceImpl extends BaseServiceImpl<EquipmentInOut> implements IEquipmentInOutService{

	@Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IUploadService uploadService;
	
	private IEquipmentInOutDao equipmentInOutDao;
	 
	@Autowired
	public EquipmentInOutServiceImpl(IEquipmentInOutDao equipmentInOutDao){
		super(equipmentInOutDao);
		this.equipmentInOutDao = equipmentInOutDao;
	}
	
	public EquipmentInOutServiceImpl(){};
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(EquipmentInOut equipmentInOut) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(equipmentInOut,true);
			result = equipmentInOutDao.delete(equipmentInOut);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(equipmentInOut);
			throw ce;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteStatus(EquipmentInOut equipmentInOut)
			throws CustomException {
		Integer result = -1;
		try{
			propertyService.fillProperties(equipmentInOut,true);
			result = equipmentInOutDao.updateStatus(equipmentInOut);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(equipmentInOut);
			throw ce;
		}
		return result;
	}
	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer operate(EquipmentInOut equipmentInOut)
			throws CustomException {
		Integer result = -1;
		try{
			propertyService.fillProperties(equipmentInOut,true);
			result = equipmentInOutDao.operate(equipmentInOut);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(equipmentInOut);
			throw ce;
		}
		return result;
	}
	/**
	 * @description 保存方法
	 * @param MeetingSpirit meetingSpirit 实体类
	 * @return Integer 增加的记录数
	 * @author 
	 * @version 
	 */
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer save(EquipmentInOut equipment) {
		try {
			//保存通用字段
			propertyService.fillProperties(equipment,false);
			// 保存意见
			equipmentInOutDao.save(equipment);
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(equipment.getDelattachIds())){
				uploadService.deleteFileByIds(equipment.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = equipment.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(equipment.getId());
					attachBusiness.setBusinessTable("toa_shjfgl_equipment_inout");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
}
