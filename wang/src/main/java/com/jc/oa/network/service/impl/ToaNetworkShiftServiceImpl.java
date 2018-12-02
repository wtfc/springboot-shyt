package com.jc.oa.network.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.network.domain.ToaNetworkShift;
import com.jc.oa.network.dao.IToaNetworkShiftDao;
import com.jc.oa.network.service.IToaNetworkShiftService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;
/**
 * @author mrb
 * @version 交接班记录表
*/
@Service
public class  ToaNetworkShiftServiceImpl  extends BaseServiceImpl<ToaNetworkShift> implements IToaNetworkShiftService {

	public ToaNetworkShiftServiceImpl(){}	

    private IToaNetworkShiftDao toaNetworkShiftDao;
    
    @Autowired
	private IAttachBusinessService attachBusinessService;
	
	@Autowired
	private IUploadService uploadService;

	@Autowired
	public ToaNetworkShiftServiceImpl(IToaNetworkShiftDao toaNetworkShiftDao){
		super(toaNetworkShiftDao);
		this.toaNetworkShiftDao = toaNetworkShiftDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkShift toaNetworkShift) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkShift,true);
			result = toaNetworkShiftDao.delete(toaNetworkShift);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkShift);
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
	public Integer save(ToaNetworkShift toaNetworkShift)throws CustomException {
		try {
			//保存通用字段
			propertyService.fillProperties(toaNetworkShift,false);
			// 保存意见
			toaNetworkShiftDao.save(toaNetworkShift);
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(toaNetworkShift.getDelattachIds())){
				uploadService.deleteFileByIds(toaNetworkShift.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = toaNetworkShift.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(toaNetworkShift.getId());
					attachBusiness.setBusinessTable("toa_network_shift");
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
	public Integer update(ToaNetworkShift toaNetworkShift)throws CustomException {
			//保存通用字段
			propertyService.fillProperties(toaNetworkShift,true);
			// 保存意见
			int result = toaNetworkShiftDao.update(toaNetworkShift);
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(toaNetworkShift.getDelattachIds())){
				uploadService.deleteFileByIds(toaNetworkShift.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = toaNetworkShift.getFileids();
			if(fileIds != null && fileIds.size() >0){
				//查询所需修改的图片信息
				AttachBusiness attachBusines = new AttachBusiness();
				attachBusines.setBusinessId(toaNetworkShift.getId());
				attachBusines.setBusinessTable("toa_network_shift");
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
					attachBusiness.setBusinessId(toaNetworkShift.getId());
					attachBusiness.setBusinessTable("toa_network_shift");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		return result;
	}

	/**
	* @description 根据交接班方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer state(ToaNetworkShift toaNetworkShift) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkShift,true);
			result = toaNetworkShiftDao.state(toaNetworkShift);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkShift);
			throw ce;
		}
		return result;
	}

	

}
