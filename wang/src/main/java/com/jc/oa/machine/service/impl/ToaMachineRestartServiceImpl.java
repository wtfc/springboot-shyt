package com.jc.oa.machine.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.android.oa.common.JpushClientUtil;
import com.jc.foundation.domain.PageManager;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.dao.IToaMachineMessageDao;
import com.jc.oa.machine.dao.IToaMachineRestartDao;
import com.jc.oa.machine.domain.ToaMachineMessage;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.oa.machine.service.IToaMachineRestartService;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.service.IEquipmentService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.content.domain.AttachBusiness;
import com.jc.system.content.service.IAttachBusinessService;
import com.jc.system.content.service.IUploadService;
import com.jc.system.dic.dao.IDicDao;
import com.jc.system.dic.domain.Dic;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.dao.IDepartmentDao;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;
import com.jc.system.security.util.ActionLog;
/**
 * @author mrb
 * @version 重启操作
*/
@Service
public class  ToaMachineRestartServiceImpl  extends BaseServiceImpl<ToaMachineRestart> implements IToaMachineRestartService {

	public ToaMachineRestartServiceImpl(){}	

    private IToaMachineRestartDao toaMachineRestartDao;
    @Autowired
   	private IAttachBusinessService attachBusinessService;

    @Autowired
   	private IUploadService uploadService;
    
    @Autowired
    private IEquipmentService equipmentService;
    
    @Autowired
    private IDepartmentDao departmentDao;
    
    @Autowired
    private IDicDao dicDao;
    
    @Autowired
    private IToaMachineMessageDao toaMachineMessageDao;

	@Autowired
	public ToaMachineRestartServiceImpl(IToaMachineRestartDao toaMachineRestartDao){
		super(toaMachineRestartDao);
		this.toaMachineRestartDao = toaMachineRestartDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineRestart toaMachineRestart) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineRestart,true);
			result = toaMachineRestartDao.delete(toaMachineRestart);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}

	/**
	* @description 接单（0-1）
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoom(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		String userName=user.getDisplayName();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		if(createUserDept.equals(toaMachine.getCreateUserDept())){
			try{
				//进行填写技术处理人才改变状态
				if(StringUtil.isEmpty(toaMachine.getCaozgcs())){
					//toaMachine.setCaozgcs(toaMachineRestart.getCaozgcs());
					toaMachine.setCaozgcs(userId);
					toaMachine.setExtStr4(userName);
					toaMachine.setStatus(1);
					propertyService.fillProperties(toaMachine,true);
					result=toaMachineRestartDao.update(toaMachine);
				}else{
					result=0;
				}
			}catch(Exception e){
				CustomException ce=new CustomException(e);
				ce.setBean(toaMachineRestart);
				throw ce;
			}
		}else{
			result=0;
		}
		
		return result;
	}

	/**
	* @description 扫描（1-2）到达设备现场
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoomScan(ToaMachineRestart toaMachineRestart) throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		//String userName=user.getDisplayName();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		//机房+机房区域+机柜编号
		String machinaLast=toaMachineRestart.getMachina();
		String engineRoomLast=toaMachineRestart.getEngineRoom();
		String cabinetNumberLast=toaMachineRestart.getCabinetNumber();
		//整合
		String numberLast=machinaLast+engineRoomLast+cabinetNumberLast;
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		//机房+机房区域+机柜编号
		String machinaNew=toaMachine.getMachinaValue();
		String engineRoomNew=toaMachine.getEngineRoom();
		String cabinetNumberNew=toaMachine.getCabinetNumber();
		//整合
		String numberNew=machinaNew+engineRoomNew+cabinetNumberNew;
		try{
			if(createUserDept.equals(toaMachine.getCreateUserDept())&&toaMachine.getCaozgcs().equals(userId)){
				if(numberLast.equals(numberNew)){
					toaMachine.setWarnDate(new Date());
					toaMachine.setStatus(2);
					propertyService.fillProperties(toaMachine, true);
					result=toaMachineRestartDao.update(toaMachine);
				}else{
					result=0;
				}
			}else{
				result=0;
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}

	/**
	* @description (2-3)开始操作
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoomHandle(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		//String userName=user.getDisplayName();
		String userId=user.getId().toString();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		String scanCode=toaMachineRestart.getDiviceNumber();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		try{
			if(createUserDept.equals(toaMachine.getCreateUserDept())&&toaMachine.getCaozgcs().equals(userId)){
				//判断扫描码是否与设备编号一致
				if(scanCode.equals(toaMachine.getDiviceNumber())){
					toaMachine.setOperateDate(new Date());
					toaMachine.setStatus(3);
					propertyService.fillProperties(toaMachine, true);
					result=toaMachineRestartDao.update(toaMachine);
				}else{
					result=0;
				}
			}else{
				result=0;
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}

	/**
	* @description (3-4) 操作完成
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoomShow(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		//String userName=user.getDisplayName();
		String userId=user.getId().toString();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		if(createUserDept.equals(toaMachine.getCreateUserDept())&&toaMachine.getCaozgcs().equals(userId)){
			toaMachine.setStatus(4);
		}else{
			toaMachine.setStatus(3);
			result=0;
			return result;
		}

		toaMachine.setEndDate(new Date());
		//在重复重启情况下，默认接显示器
		toaMachine.setFirstRestart("1");
		toaMachine.setIsMonitor("是");

		toaMachine.setExtStr3(toaMachineRestart.getExtStr3());			//附件url
		toaMachine.setIsWrong(toaMachineRestart.getIsWrong());		//是否报错
		toaMachine.setRegionExamine(toaMachineRestart.getRegionExamine());//有无后续操作
		toaMachine.setAssist(toaMachineRestart.getAssist());		//是否协助处理
		toaMachine.setExecutiveOutcome(toaMachineRestart.getExecutiveOutcome());//执行结果
		toaMachine.setExternalEquipment(toaMachineRestart.getExternalEquipment());//外接设备是否满足
		toaMachine.setStatisticalListing(toaMachineRestart.getStatisticalListing());//统计清单
		toaMachine.setPortResult(toaMachineRestart.getPortResult());//  核对端口结果
		toaMachine.setTestOutcome(toaMachineRestart.getTestOutcome());// 测试结果/网络连通性测试结果/调试结果
		toaMachine.setPlaceStorage(toaMachineRestart.getPlaceStorage());//存放位置
		try{
			propertyService.fillProperties(toaMachine, true);
			result=toaMachineRestartDao.update(toaMachine);
			
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(toaMachineRestart.getDelattachIds())){
				uploadService.deleteFileByIds(toaMachineRestart.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = toaMachineRestart.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(toaMachineRestart.getId());
					attachBusiness.setBusinessTable("toa_machine_restart");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description 上传附件
	* @param Id 重启工单id
	* @param delattachIds 删除附件Id
	* @param Fileids 附件Id List<Long>
	* @return Integer 处理结果
	* @author 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer uploadFile(ToaMachineRestart toaMachineRestart)throws CustomException {
		Integer result=-1;
		try{
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(toaMachineRestart.getDelattachIds())){
				uploadService.deleteFileByIds(toaMachineRestart.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = toaMachineRestart.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(toaMachineRestart.getId());
					attachBusiness.setBusinessTable("toa_machine_restart");
					attachBusiness.setBusinessSource("0");
					result=attachBusinessService.save(attachBusiness);
				}
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}
	
	
	
	/**
	* @description (3-4)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoomDirect(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		//String userName=user.getDisplayName();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		try{
			if(createUserDept.equals(toaMachine.getCreateUserDept())&&toaMachine.getCaozgcs().equals(userId)){
				toaMachine.setIsMonitor("否");
				toaMachine.setEndDate(new Date());
				toaMachine.setFirstRestart("1");
				toaMachine.setStatus(4);
				propertyService.fillProperties(toaMachine, true);
				result=toaMachineRestartDao.update(toaMachine);
			}else{
				result=0;
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description (4-7)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoomReveal(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userName=user.getDisplayName();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		if(createUserDept.equals(toaMachine.getCreateUserDept())&&toaMachine.getCaozgcs().equals(userName)){
			toaMachineRestart.setFirstRestart("1");
			toaMachineRestart.setStatus(7);
		}else{
			toaMachineRestart.setStatus(4);
		}
		
		try{
			propertyService.fillProperties(toaMachineRestart, true);
			result=toaMachineRestartDao.update(toaMachineRestart);
			
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(toaMachineRestart.getDelattachIds())){
				uploadService.deleteFileByIds(toaMachineRestart.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = toaMachineRestart.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(toaMachineRestart.getId());
					attachBusiness.setBusinessTable("toa_machine_restart");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description (4-5)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoomWrong(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userName=user.getDisplayName();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		if(createUserDept.equals(toaMachine.getCreateUserDept())&&toaMachine.getCaozgcs().equals(userName)){
			toaMachineRestart.setStatus(5);
		}else{
			toaMachineRestart.setStatus(4);
		}
		
		try{
			propertyService.fillProperties(toaMachineRestart, true);
			result=toaMachineRestartDao.update(toaMachineRestart);
			
			//保存附件
			//删除页面中上传中删除的附件
			if(!StringUtil.isEmpty(toaMachineRestart.getDelattachIds())){
				uploadService.deleteFileByIds(toaMachineRestart.getDelattachIds());
			}
			//保存附件
			List<Long> fileIds = toaMachineRestart.getFileids();
			if(fileIds != null && fileIds.size() >0){
				for (int i = 0; i < fileIds.size(); i++) {
					AttachBusiness attachBusiness = new AttachBusiness();
					attachBusiness.setAttachId(fileIds.get(i));
					attachBusiness.setBusinessId(toaMachineRestart.getId());
					attachBusiness.setBusinessTable("toa_machine_restart");
					attachBusiness.setBusinessSource("0");
					attachBusinessService.save(attachBusiness);
				}
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description (5-6)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoomSeed(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userName=user.getDisplayName();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		if(createUserDept.equals(toaMachine.getCreateUserDept())&&toaMachine.getCaozgcs().equals(userName)){
			toaMachineRestart.setStatus(6);
		}else{
			toaMachineRestart.setStatus(5);
		}
		
		try{
			propertyService.fillProperties(toaMachineRestart, true);
			result=toaMachineRestartDao.update(toaMachineRestart);
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description (6-7)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer updateRoomAssist(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userName=user.getDisplayName();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		if(createUserDept.equals(toaMachine.getCreateUserDept())&&toaMachine.getCaozgcs().equals(userName)&&toaMachine.getAssist().equals("否")){
			toaMachineRestart.setFirstRestart("1");
			toaMachineRestart.setStatus(7);
		}else{
			toaMachineRestart.setStatus(6);
		}
		
		try{
			propertyService.fillProperties(toaMachineRestart, true);
			result=toaMachineRestartDao.update(toaMachineRestart);
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}

	/**
	* @description 保存方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer saveRestart(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		propertyService.fillProperties(toaMachineRestart, false);
		Integer result=-1;
		if(toaMachineRestart.getId()!=null){
			toaMachineRestart.setId(null);
		}
		//呼叫中心发起
		toaMachineRestart.setStatus(0);
		//申请时间 add-wangtf
		toaMachineRestart.setBillDate(new Date());
		
		String machina=toaMachineRestart.getMachina();
		//设备
		Equipment equipment=new Equipment();
		//客户名称
		equipment.setClientName(toaMachineRestart.getCompanyName());
		//SN
		//equipment.setSn(toaMachineRestart.getSn());
		if(machina.equals("room_0")){
			equipment.setContact("鲁谷机房");
			toaMachineRestart.setCreateUserDept((long) 1056);
			toaMachineRestart.setExtStr1("1054");
		}else if(machina.equals("room_1")){
			equipment.setContact("清华园机房");
			toaMachineRestart.setCreateUserDept((long) 1057);
			toaMachineRestart.setExtStr1("1054");
		}else if(machina.equals("room_2")){
			equipment.setContact("兆维机房");
			toaMachineRestart.setCreateUserDept((long) 1050);
			toaMachineRestart.setExtStr1("1049");
		}else if(machina.equals("room_3")){
			equipment.setContact("沙河机房");
			toaMachineRestart.setCreateUserDept((long) 1051);
			toaMachineRestart.setExtStr1("1049");
		}else if(machina.equals("room_4")){
			equipment.setContact("看丹桥机房");
			toaMachineRestart.setCreateUserDept((long) 1055);
			toaMachineRestart.setExtStr1("1054");
		}else if(machina.equals("room_5")){
			equipment.setContact("廊坊机房");
			toaMachineRestart.setCreateUserDept((long) 1053);
			toaMachineRestart.setExtStr1("1049");
		}else if(machina.equals("room_6")){
			equipment.setContact("富丰桥机房");
			toaMachineRestart.setCreateUserDept((long) 1058);
			toaMachineRestart.setExtStr1("1050");
		}else if(machina.equals("room_7")){
			equipment.setContact("亦庄大族机房");
			toaMachineRestart.setCreateUserDept((long) 1052);
			toaMachineRestart.setExtStr1("1049");
		}else if(machina.equals("room_8")){
			equipment.setContact("洋桥机房");
			toaMachineRestart.setCreateUserDept((long) 1059);
			toaMachineRestart.setExtStr1("1054");
		}else{
			toaMachineRestart.setCreateUserDept((long) 1028);
		}
		
		toaMachineRestart.setErrorRepair("0");
		toaMachineRestart.setFirstRestart("0");
		
		List<Equipment> equipmentNew=equipmentService.queryAll(equipment);
		if(equipmentNew.size()>0){
			
			//保存
			Integer flag = toaMachineRestartDao.save(toaMachineRestart);
			if(flag==1){
				//保存成功
				result=1;
			}else{
				//保存失败
				result=2;
			}
		}else{
			//设备不存在
			result=3;
		}
		return result;
	}

	/**
	* @description 主管评分
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer updateEngine(ToaMachineRestart toaMachineRestart)throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		Long createUserDept=user.getDeptId();
		Long id=toaMachineRestart.getId();
		ToaMachineRestart toaRestart=new ToaMachineRestart();
		toaRestart.setId(id);
		ToaMachineRestart toaMachine=toaMachineRestartDao.get(toaRestart);
		if(createUserDept.equals(toaMachine.getCreateUserDept())){
			//进行填写技术处理人才改变状态
			if(!StringUtil.isEmpty(toaMachineRestart.getExtStr2())){
				toaMachineRestart.setStatus(5);
			}else{
				toaMachineRestart.setStatus(4);
			}
		}
		try{
			propertyService.fillProperties(toaMachineRestart, true);
			result=toaMachineRestartDao.update(toaMachineRestart);
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineRestart);
			throw ce;
		}
		return result;
	}

	@Override
	public PageManager queryAll(ToaMachineRestart toaMachineRestart,
			PageManager page) throws CustomException {
		
		return toaMachineRestartDao.queryAll(toaMachineRestart, page);
	}

	@Override
	public Integer operate(ToaMachineRestart toaMachineRestart,HttpServletRequest request) throws CustomException {
		Integer result=-1;
		result=this.restart(toaMachineRestart, request);
		return result;
	};
	
	/**
	 * 重启操作方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@ResponseBody
	@ActionLog(operateModelNm="重启操作",operateFuncNm="save",operateDescribe="对重启操作进行发起操作")
	public Integer restart(ToaMachineRestart toaMachineRestart,HttpServletRequest request) throws CustomException{

		ToaMachineRestart toaMachineRestartTemp=new ToaMachineRestart();
		//工单编号
		INumber number = new Number();
		String applyNum = number.getNumber("MachineRestart", 1,2, null);
		applyNum = applyNum.substring(1, applyNum.length());
		String equipmentNumber=applyNum;

		toaMachineRestartTemp.setEquipmentNumber(equipmentNumber);//工单编号
		if(toaMachineRestart.getCompanyId()!=null){
			toaMachineRestartTemp.setCompanyId(toaMachineRestart.getCompanyId());//客户id
			toaMachineRestartTemp.setCompanyName(toaMachineRestart.getCompanyName());//客户名称
		}
		toaMachineRestartTemp.setApplicant(SystemSecurityUtils.getUser().getDisplayName());//申请人
		toaMachineRestartTemp.setBillDate(new Date());//申请时间
		//是否前置操作
		toaMachineRestartTemp.setChargeInformation(toaMachineRestart.getChargeInformation());//前置操作内容
		toaMachineRestartTemp.setMachina(toaMachineRestart.getMachina());//机房
		toaMachineRestartTemp.setCabinet(toaMachineRestart.getCabinet());//机柜
		toaMachineRestartTemp.setIp(toaMachineRestart.getIp());//IP
		toaMachineRestartTemp.setSn(toaMachineRestart.getSn());//SN
		toaMachineRestartTemp.setBrand(toaMachineRestart.getBrand());//品牌型号
		toaMachineRestartTemp.setDeleteFlag(0);
		
		toaMachineRestartTemp.setFirstRestart("1");
		toaMachineRestartTemp.setErrorRepair("0");
		
		//操作类型  1、重启操作  6、系统配置 7、系统安装...
		toaMachineRestartTemp.setOperationType("1");
		//操作类型图标  1、重启操作  6、系统配置 7、系统安装...
		toaMachineRestartTemp.setExtStr5("1");
		
		Integer flag=this.saveRestart(toaMachineRestartTemp);
			
		return flag;
	}
	
	/**
	 * App重启工单列表方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@Override
	public List<ToaMachineRestart> queryApp(ToaMachineRestart toaMachineRestart) throws CustomException {
		// TODO Auto-generated method stub
		return toaMachineRestartDao.queryApp(toaMachineRestart);
	}

	/**
	 * App保存方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@Override
	public Integer saveMachine(ToaMachineRestart toaMachineRestart)
			throws CustomException {
		// TODO Auto-generated method stub
		 Integer result=-1;
		 Integer msgResult=-1;
		 try{
			 	//编号
				INumber number = new Number();
				String applyNum = number.getNumber("MachineRestart", 1,2, null);
				applyNum = applyNum.substring(1, applyNum.length());
				String equipmentNumber=applyNum;
				toaMachineRestart.setEquipmentNumber(equipmentNumber);
				toaMachineRestart.setApplicant(SystemSecurityUtils.getUser().getDisplayName());//申请人
				toaMachineRestart.setBillDate(new Date());//申请时间
				toaMachineRestart.setDeleteFlag(0);
				toaMachineRestart.setFirstRestart("0");
				toaMachineRestart.setErrorRepair("0");
				//呼叫中心发起
				toaMachineRestart.setStatus(0);
				//操作类型图标  1、重启操作  6、系统配置 7、系统安装...
				toaMachineRestart.setExtStr5(toaMachineRestart.getOperationType());
				
				//机房
				String machina=toaMachineRestart.getMachina();
				
				//得到机房code
				Dic dic = new Dic();
				dic.setValue(machina);
				dic = dicDao.get(dic);
				if(dic!=null){//机房
					toaMachineRestart.setMachina(dic.getCode());
				}else{
					toaMachineRestart.setMachina(toaMachineRestart.getMachina());
				}
				
				//得到机房所在部门
				Department department = new Department();
				department.setName(machina);
				department = departmentDao.get(department);
				if(department!=null){//机房所在部门
					toaMachineRestart.setCreateUserDept(department.getId());
					toaMachineRestart.setExtStr1(department.getParentId().toString());
				}else{
					toaMachineRestart.setCreateUserDept((long) 1028);
				}
				propertyService.fillProperties(toaMachineRestart, false);
				result=toaMachineRestartDao.save(toaMachineRestart);

				//工单消息
				ToaMachineMessage toaMachineMessage = new ToaMachineMessage();
				if(result == 1){
					
					//编号
					String applyNum2 = number.getNumber("MessageNumber", 1,2, null);
					applyNum2 = applyNum2.substring(1, applyNum2.length());
					//消息设置
					toaMachineMessage.setMessageNumber(applyNum2);
					toaMachineMessage.setMessageTitle(toaMachineRestart.getOperationTypeValue());
					toaMachineMessage.setMessageContent(toaMachineRestart.getCompanyName());
					toaMachineMessage.setMessageType(toaMachineRestart.getOperationType());

					String receivedDeptId = null;
					if(department!=null){//机房所在部门
						receivedDeptId = department.getId().toString();
					}else{
						receivedDeptId = "1028";
					}
					
					propertyService.fillProperties(toaMachineMessage, false);
					
					toaMachineMessage.setReceivedDeptId(receivedDeptId);
					msgResult = toaMachineMessageDao.save(toaMachineMessage);
					
				}
				
				//发出消息
				if(msgResult == 1){
					JpushClientUtil.sendToAllAndroid(toaMachineMessage.getMessageTitle(), null, toaMachineMessage.getMessageContent(), toaMachineMessage.getMessageType(), toaMachineMessage.getReceivedDeptId());
				}
				
		 }catch(Exception e){
			 CustomException ce=new CustomException(e);
			 ce.setBean(toaMachineRestart);
			 throw ce;
		 }
		return result;
	}

}
