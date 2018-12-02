package com.jc.oa.shyt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.shyt.dao.IOutContractDao;
import com.jc.oa.shyt.domain.OutContract;
import com.jc.oa.shyt.service.IOutContractService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;

@Service
public class OutContractServiceImpl extends BaseServiceImpl<OutContract> implements IOutContractService{

	private IOutContractDao outContractDao;
	
	 @Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IUploadService uploadService;
	
	public OutContractServiceImpl(){}
	
	@Autowired
	public OutContractServiceImpl(IOutContractDao outContractDao){
		super(outContractDao);
		this.outContractDao = outContractDao;
	}
	
	/**
	* @description 根据主键删除多条记录方法
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(OutContract outContract) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(outContract,true);
			result = outContractDao.delete(outContract);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(outContract);
			throw ce;
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer save(OutContract outContract)throws CustomException {
		try {
			//保存通用字段
			propertyService.fillProperties(outContract,false);
			// 保存意见
			outContractDao.save(outContract);
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(outContract.getDelattachIds())){
				uploadService.deleteFileByIds(outContract.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = outContract.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(outContract.getId());
					attachBusiness.setBusinessTable("toa_shyt_outContract");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer update(OutContract outContract)throws CustomException {
		//保存通用字段
		propertyService.fillProperties(outContract,true);
		// 保存意见
		int result = outContractDao.update(outContract);
		//删除页面中上传中删除的附件
		if(!StringUtil.isEmpty(outContract.getDelattachIds())){
			uploadService.deleteFileByIds(outContract.getDelattachIds());
		}
		//保存附件
		List<Long> fileIds = outContract.getFileids();
		if(fileIds != null && fileIds.size() >0){
			//查询所需修改的图片信息
			AttachBusiness attachBusines = new AttachBusiness();
			attachBusines.setBusinessId(outContract.getId());
			attachBusines.setBusinessTable("toa_shyt_outContract");
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
				attachBusiness.setBusinessId(outContract.getId());
				attachBusiness.setBusinessTable("toa_shyt_outContract");
				attachBusiness.setBusinessSource("0");
				attachBusinessService.save(attachBusiness);
			}
		}
	return result;
	}
}
