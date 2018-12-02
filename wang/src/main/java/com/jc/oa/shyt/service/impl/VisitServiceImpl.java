package com.jc.oa.shyt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.shyt.dao.IVisitDao;
import com.jc.oa.shyt.domain.Visit;
import com.jc.oa.shyt.service.IVisitService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;

@Service
public class VisitServiceImpl extends BaseServiceImpl<Visit> implements IVisitService{
	
	private IVisitDao visitDao;
	
	@Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IUploadService uploadService;
	
	public VisitServiceImpl(){}
	
	@Autowired
	public VisitServiceImpl(IVisitDao visitDao){
		super(visitDao);
		this.visitDao = visitDao;
	}
	
	/**
	* @description 根据主键删除多条记录方法
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Visit visit) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(visit,true);
			result = visitDao.delete(visit);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(visit);
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
	public Integer saveResource(Visit visit)throws CustomException {
		try {
			//保存通用字段
			propertyService.fillProperties(visit,false);
			// 保存意见
			visitDao.save(visit);
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(visit.getDelattachIds())){
				uploadService.deleteFileByIds(visit.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = visit.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(visit.getId());
					attachBusiness.setBusinessTable("toa_shyt_visit");
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
	public Integer updateResource(Visit visit)throws CustomException {
			//保存通用字段
			propertyService.fillProperties(visit,true);
			// 保存意见
			int result = visitDao.update(visit);
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(visit.getDelattachIds())){
				uploadService.deleteFileByIds(visit.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = visit.getFileids();
			if(fileIds != null && fileIds.size() >0){
				//查询所需修改的图片信息
				AttachBusiness attachBusines = new AttachBusiness();
				attachBusines.setBusinessId(visit.getId());
				attachBusines.setBusinessTable("toa_shyt_visit");
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
					attachBusiness.setBusinessId(visit.getId());
					attachBusiness.setBusinessTable("toa_shyt_visit");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		return result;
	}

	@Override
	public Integer updateCustomer(Visit visit) throws CustomException {
		
		int result = visitDao.updateCustomer(visit);
		
		return result;
	}
}
