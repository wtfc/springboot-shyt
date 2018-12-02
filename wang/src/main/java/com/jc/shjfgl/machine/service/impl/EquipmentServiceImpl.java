package com.jc.shjfgl.machine.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.dao.IToaMachineRestartDao;
import com.jc.oa.machine.domain.ToaMachineRestart;
import com.jc.shjfgl.machine.dao.IEquipmentDao;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.service.IEquipmentService;
import com.jc.system.CustomException;
import com.jc.system.dic.dao.IDicDao;
import com.jc.system.dic.domain.Dic;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.dao.IDepartmentDao;
import com.jc.system.security.domain.Department;
import com.jc.system.security.util.ActionLog;

@Service
public class EquipmentServiceImpl extends BaseServiceImpl<Equipment> implements IEquipmentService{

	@Autowired
	public EquipmentServiceImpl(IEquipmentDao equipmentDao){
		super(equipmentDao);
		this.equipmentDao = equipmentDao;
	}
	

	@Autowired
	private IToaMachineRestartDao toaMachineRestartDao;
	@Autowired
	private IEquipmentDao equipmentDao;
	@Autowired
	private IDicDao dicDao;
	@Autowired
	private IDepartmentDao departmentDao;
	
	public EquipmentServiceImpl(){};
	
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(Equipment equipment) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(equipment,true);
			result = equipmentDao.delete(equipment);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(equipment);
			throw ce;
		}
		return result;
	}
	
	@Override
	public Integer operate(Equipment equipment,String operate) throws CustomException {
		Integer result=-1;

		Equipment equipmentTemp=this.get(equipment);
		if(equipmentTemp!=null){
			result=this.restart(equipmentTemp,operate);
		}
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
	public Integer restart(Equipment equipmentTemp,String operate) throws CustomException{

		ToaMachineRestart toaMachineRestart=new ToaMachineRestart();
		
		//编号
		INumber number = new Number();
		String applyNum = number.getNumber("MachineRestart", 1,2, null);
		applyNum = applyNum.substring(1, applyNum.length());
		
		String equipmentNumber=applyNum;

		toaMachineRestart.setEquipmentId(equipmentTemp.getId().toString());//设备ID
		
		toaMachineRestart.setEquipmentNumber(equipmentNumber);//工单编号
		if(equipmentTemp.getCompanyId()!=null){
			toaMachineRestart.setCompanyId(equipmentTemp.getCompanyId().intValue());//客户id
		}
		toaMachineRestart.setCompanyName(equipmentTemp.getClientName());//客户名称
		toaMachineRestart.setApplicant(SystemSecurityUtils.getUser().getDisplayName());//申请人
		toaMachineRestart.setBillDate(new Date());//申请时间
		//是否前置操作
		//前置操作内容
		toaMachineRestart.setMachina(equipmentTemp.getContact());//机房
		toaMachineRestart.setCabinet(equipmentTemp.getAddress());//机柜
		toaMachineRestart.setIp(equipmentTemp.getIp());//IP
		toaMachineRestart.setSn(equipmentTemp.getSn());//SN
		toaMachineRestart.setBrand(equipmentTemp.getType());//品牌型号
		toaMachineRestart.setDeleteFlag(0);
		toaMachineRestart.setFirstRestart("0");
		toaMachineRestart.setErrorRepair("0");
		//呼叫中心发起
		toaMachineRestart.setStatus(0);
		
		//操作类型  1、重启操作  6、系统配置 7、系统安装...
		toaMachineRestart.setOperationType(operate);
		//操作类型图标  1、重启操作  6、系统配置 7、系统安装...
		toaMachineRestart.setExtStr5(operate);
		
		Integer flag=this.saveRestart(toaMachineRestart);
			
		return flag;
	}

	/**
	* @description 保存方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	 * @throws CustomException 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer saveRestart(ToaMachineRestart toaMachineRestart) throws CustomException{
		propertyService.fillProperties(toaMachineRestart, false);
		Integer result=-1;
		if(toaMachineRestart.getId()!=null){
			toaMachineRestart.setId(null);
		}
		String machina=toaMachineRestart.getMachina();
		
//		if((toaMachineRestart.getSn()==null||"".equals(toaMachineRestart.getSn()))
//				&&(toaMachineRestart.getIp()==null||"".equals(toaMachineRestart.getIp()))){
//			return result;
//		}

		//得到机房code
		Dic dic = new Dic();
		dic.setValue(machina);
		dic = dicDao.get(dic);
		if(dic!=null){//机房
			toaMachineRestart.setMachina(dic.getCode());
		}else{
			toaMachineRestart.setMachina(machina);
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
		
		
//		if("鲁谷机房".equals(machina)){
//			toaMachineRestart.setMachina("room_0");
//			toaMachineRestart.setCreateUserDept((long) 1056);
//			toaMachineRestart.setExtStr1("1054");
//		}else if("清华园机房".equals(machina)){
//			toaMachineRestart.setMachina("room_1");
//			toaMachineRestart.setCreateUserDept((long) 1057);
//			toaMachineRestart.setExtStr1("1054");
//		}else if("兆维机房".equals(machina)){
//			toaMachineRestart.setMachina("room_2");
//			toaMachineRestart.setCreateUserDept((long) 1050);
//			toaMachineRestart.setExtStr1("1049");
//		}else if("沙河机房".equals(machina)){
//			toaMachineRestart.setMachina("room_3");
//			toaMachineRestart.setCreateUserDept((long) 1051);
//			toaMachineRestart.setExtStr1("1049");
//		}else if("看丹桥机房".equals(machina)){
//			toaMachineRestart.setMachina("room_4");
//			toaMachineRestart.setCreateUserDept((long) 1055);
//			toaMachineRestart.setExtStr1("1054");
//		}else if("廊坊机房".equals(machina)){
//			toaMachineRestart.setMachina("room_5");
//			toaMachineRestart.setCreateUserDept((long) 1053);
//			toaMachineRestart.setExtStr1("1049");
//		}else if("富丰桥机房".equals(machina)){
//			toaMachineRestart.setMachina("room_6");
//			toaMachineRestart.setCreateUserDept((long) 1058);
//			toaMachineRestart.setExtStr1("1050");
//		}else if("亦庄大族机房".equals(machina)){
//			toaMachineRestart.setMachina("room_7");
//			toaMachineRestart.setCreateUserDept((long) 1052);
//			toaMachineRestart.setExtStr1("1049");
//		}else if("洋桥机房".equals(machina)){
//			toaMachineRestart.setMachina("room_8");
//			toaMachineRestart.setCreateUserDept((long) 1059);
//			toaMachineRestart.setExtStr1("1054");
//		}else{
//			toaMachineRestart.setCreateUserDept((long) 1028);
//		}

		//List<Equipment> equipmentNew=this.queryAll(equipment);
		//if(equipmentNew.size()>0){
			
			//保存
			Integer flag = toaMachineRestartDao.save(toaMachineRestart);
			if(flag==1){
				//保存成功
				result=1;
			}else{
				//保存失败
				result=2;
			}
		//}else{
			//设备不存在
			//result=3;
		//}
		return result;
	}

	@Override
	public void updateEquipmentNumber() throws CustomException {
		// TODO Auto-generated method stub
		Equipment equipment = new Equipment();
		//equipment.setId((long)121896);
		List<Equipment> equipmentList = equipmentDao.queryAll(equipment);
		
		int equipmentCount = equipmentList.size();
		for(int index = 0;index<equipmentCount;index++){
			Equipment equipmentTemp= equipmentList.get(index);
			//编号
			INumber number = new Number();
			String applyNum = number.getNumber("EquipmentNumber", 1,2, null);
			applyNum = applyNum.substring(1, applyNum.length());
			
			String equipmentNumber=applyNum;
			equipmentTemp.setEquipmentNumber(equipmentNumber);
			propertyService.fillProperties(equipmentTemp, true);
			equipmentDao.update(equipmentTemp);
		}
		
	}
	
}
