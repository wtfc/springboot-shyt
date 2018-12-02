package com.jc.oa.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.project.dao.IMonitorsDao;
import com.jc.oa.project.domain.Monitors;
import com.jc.oa.project.service.IMonitorsService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;

@Service
public class MonitorsServiceImpl extends BaseServiceImpl<Monitors> implements IMonitorsService{

	@Autowired
	public MonitorsServiceImpl(IMonitorsDao monitorDao){
		super(monitorDao);
		this.monitorDao = monitorDao;
	}
	
	private IMonitorsDao monitorDao;
	
	public MonitorsServiceImpl(){};
	
	@Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IUploadService uploadService;
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Monitors monitor) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(monitor,true);
			result = monitorDao.delete(monitor);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(monitor);
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
	public Integer save(Monitors monitor)throws CustomException {
		try {
			//保存通用字段
			propertyService.fillProperties(monitor,false);
			// 保存意见
			monitorDao.save(monitor);
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(monitor.getDelattachIds())){
				uploadService.deleteFileByIds(monitor.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = monitor.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(monitor.getId());
					attachBusiness.setBusinessTable("toa_project_monitor");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}
	/*
	 * update 方法
	 * **/
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer update(Monitors monitor)throws CustomException {
			//保存通用字段
			propertyService.fillProperties(monitor,true);
			// 保存意见
			if(monitor.getLeared()!=null){
				monitor.setStatus(1);
			}
			int result = monitorDao.update(monitor);
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(monitor.getDelattachIds())){
				uploadService.deleteFileByIds(monitor.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = monitor.getFileids();
			if(fileIds != null && fileIds.size() >0){
				//查询所需修改的图片信息
				AttachBusiness attachBusines = new AttachBusiness();
				attachBusines.setBusinessId(monitor.getId());
				attachBusines.setBusinessTable("toa_project_monitor");
				List<AttachBusiness> list = attachBusinessService.queryAll(attachBusines);
				//删除所需修改的图片信息及图片文件
				AttachBusiness delAttachBusiness = new AttachBusiness();
				String[] keys = new String[list.size()];
				if(list != null && list.size() > 0){
					for(int i = 0; i < list.size(); i++){
						keys[i] = list.get(i).getId().toString();
					}
					delAttachBusiness.setPrimaryKeys(keys);
					attachBusinessService.delete(delAttachBusiness, false);
				}
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(monitor.getId());
					attachBusiness.setBusinessTable("toa_project_monitor");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		return result;
	}
	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public Integer updateStatus(Monitors monitor) throws CustomException {
		int result = -1;
		try {
			propertyService.fillProperties(monitor,true);
			result = monitorDao.updateStatus(monitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
