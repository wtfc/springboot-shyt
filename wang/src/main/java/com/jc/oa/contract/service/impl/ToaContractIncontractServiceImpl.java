package com.jc.oa.contract.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.contract.domain.ToaContractIncontract;
import com.jc.oa.contract.dao.IToaContractIncontractDao;
import com.jc.oa.contract.dao.IToaContractResourceDao;
import com.jc.oa.contract.service.IToaContractIncontractService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;
/**
 * @author mrb
 * @version 收款方合同
*/
@Service
public class  ToaContractIncontractServiceImpl  extends BaseServiceImpl<ToaContractIncontract> implements IToaContractIncontractService {

	public ToaContractIncontractServiceImpl(){}	

    private IToaContractIncontractDao toaContractIncontractDao;
    
    @Autowired
    private IToaContractResourceDao toaContractResourceDao;
    
    @Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IUploadService uploadService;

	@Autowired
	public ToaContractIncontractServiceImpl(IToaContractIncontractDao toaContractIncontractDao){
		super(toaContractIncontractDao);
		this.toaContractIncontractDao = toaContractIncontractDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaContractIncontract toaContractIncontract) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaContractIncontract,true);
			result = toaContractIncontractDao.delete(toaContractIncontract);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaContractIncontract);
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
	public Integer saveResource(ToaContractIncontract toaContractIncontract)throws CustomException {
		try {
			//保存通用字段
			propertyService.fillProperties(toaContractIncontract,false);
			// 保存意见
			toaContractIncontractDao.save(toaContractIncontract);
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(toaContractIncontract.getDelattachIds())){
				uploadService.deleteFileByIds(toaContractIncontract.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = toaContractIncontract.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(toaContractIncontract.getId());
					attachBusiness.setBusinessTable("toa_contract_inContract");
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
	public Integer updateResource(ToaContractIncontract toaContractIncontract)throws CustomException {
			//保存通用字段
			propertyService.fillProperties(toaContractIncontract,true);
			// 保存意见
			int result = toaContractIncontractDao.update(toaContractIncontract);
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(toaContractIncontract.getDelattachIds())){
				uploadService.deleteFileByIds(toaContractIncontract.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = toaContractIncontract.getFileids();
			if(fileIds != null && fileIds.size() >0){
				//查询所需修改的图片信息
				AttachBusiness attachBusines = new AttachBusiness();
				attachBusines.setBusinessId(toaContractIncontract.getId());
				attachBusines.setBusinessTable("toa_contract_inContract");
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
					attachBusiness.setBusinessId(toaContractIncontract.getId());
					attachBusiness.setBusinessTable("toa_contract_inContract");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		return result;
	}

}
