package com.jc.shjfgl.machine.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.android.oa.common.JpushClientUtil;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.dao.IToaMachineMessageDao;
import com.jc.oa.machine.domain.ToaMachineMessage;
import com.jc.shjfgl.machine.dao.IToaShjfglEquipmentMoveDao;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentMove;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentMoveService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.dic.dao.IDicDao;
import com.jc.system.dic.domain.Dic;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.dao.IDepartmentDao;
import com.jc.system.security.domain.Department;
import com.jc.system.security.domain.User;
/**
 * @author mrb
 * @version 设备搬迁表
*/
@Service
public class  ToaShjfglEquipmentMoveServiceImpl  extends BaseServiceImpl<ToaShjfglEquipmentMove> implements IToaShjfglEquipmentMoveService {

	public ToaShjfglEquipmentMoveServiceImpl(){}	

    private IToaShjfglEquipmentMoveDao toaShjfglEquipmentMoveDao;
    
    @Autowired
    private IDicDao dicDao;
    
    @Autowired
    private IDepartmentDao  departmentDao;
    
    @Autowired
    private IToaMachineMessageDao toaMachineMessageDao;

	@Autowired
	public ToaShjfglEquipmentMoveServiceImpl(IToaShjfglEquipmentMoveDao toaShjfglEquipmentMoveDao){
		super(toaShjfglEquipmentMoveDao);
		this.toaShjfglEquipmentMoveDao = toaShjfglEquipmentMoveDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaShjfglEquipmentMove,true);
			result = toaShjfglEquipmentMoveDao.delete(toaShjfglEquipmentMove);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShjfglEquipmentMove);
			throw ce;
		}
		return result;
	}

	/**
	* @description APP保存方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer saveEquipmentMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		 Integer msgResult=-1;
		try{
			User user=SystemSecurityUtils.getUser();
			toaShjfglEquipmentMove.setCompanyName(user.getDisplayName());
			toaShjfglEquipmentMove.setCompanyId(Integer.valueOf(user.getCode()));
			String machina=toaShjfglEquipmentMove.getMachina();
			//得到机房code
			Dic dic = new Dic();
			dic.setValue(machina);
			dic = dicDao.get(dic);
			if(dic!=null){//机房
				toaShjfglEquipmentMove.setMachina(dic.getCode());
			}else{
				toaShjfglEquipmentMove.setMachina(toaShjfglEquipmentMove.getMachina());
			}
			
			//得到机房所在部门
			Department department = new Department();
			department.setName(machina);
			department = departmentDao.get(department);
			if(department!=null){//机房所在部门
				//部门id
				toaShjfglEquipmentMove.setCreateUserDept(department.getId());
				//机构id
				toaShjfglEquipmentMove.setExtStr1(department.getParentId().toString());
			}else{
				toaShjfglEquipmentMove.setCreateUserDept((long) 1028);
			}
			toaShjfglEquipmentMove.setStatus(0);
			propertyService.fillProperties(toaShjfglEquipmentMove, false);
			result=toaShjfglEquipmentMoveDao.save(toaShjfglEquipmentMove);
			
			//工单消息
			ToaMachineMessage toaMachineMessage = new ToaMachineMessage();
			if(result == 1){
				
				//编号
				INumber number = new Number();
				String applyNum = number.getNumber("MessageNumber", 1,2, null);
				applyNum = applyNum.substring(1, applyNum.length());

				toaMachineMessage.setMessageNumber(applyNum);
				if("0".equals(toaShjfglEquipmentMove.getOperationType())){
					toaMachineMessage.setMessageTitle("上架");//操作类型 
					toaMachineMessage.setMessageType("3");
				}else if("1".equals(toaShjfglEquipmentMove.getOperationType())){
					toaMachineMessage.setMessageTitle("下架");//操作类型
					toaMachineMessage.setMessageType("3");
				}else if("2".equals(toaShjfglEquipmentMove.getOperationType())){
					toaMachineMessage.setMessageTitle("迁移");//操作类型
					toaMachineMessage.setMessageType("4");
				}
				toaMachineMessage.setMessageContent(toaShjfglEquipmentMove.getCompanyName());

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
			ce.setBean(toaShjfglEquipmentMove);
			throw ce;
		}
		return result;
	}

	/**
	* @description APP查询列表方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public List<ToaShjfglEquipmentMove> quertMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException {
		// TODO Auto-generated method stub
		return toaShjfglEquipmentMoveDao.queryMove(toaShjfglEquipmentMove);
	}
	
	/**
	* @description APP接单方法(0-1)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer updateMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		String userName=user.getDisplayName();
		Long createUserDept=user.getDeptId();
		Long id=toaShjfglEquipmentMove.getId();
		ToaShjfglEquipmentMove toaEquipmentMove=new ToaShjfglEquipmentMove();
		toaEquipmentMove.setId(id);
		ToaShjfglEquipmentMove toaMachineMove=toaShjfglEquipmentMoveDao.get(toaEquipmentMove);
		if(createUserDept.equals(toaMachineMove.getCreateUserDept())){
			try{
				//进行填写技术处理人才改变状态
				if(StringUtil.isEmpty(toaMachineMove.getCaozgcs())){
					toaMachineMove.setCaozgcs(userId);
					toaMachineMove.setCaozgcsName(userName);
					toaMachineMove.setStatus(1);
					propertyService.fillProperties(toaMachineMove,true);
					result=toaShjfglEquipmentMoveDao.update(toaMachineMove);
				}else{
					result=0;
				}
			}catch(Exception e){
				CustomException ce=new CustomException(e);
				ce.setBean(toaShjfglEquipmentMove);
				throw ce;
			}
		}else{
			result=0;
		}
		
		return result;
	}
	
	/**
	* @description APP到达设备现场方法(1-2)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer updateMoveScan(ToaShjfglEquipmentMove toaShjfglEquipmentMove)throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		Long createUserDept=user.getDeptId();
		Long id=toaShjfglEquipmentMove.getId();
		
		ToaShjfglEquipmentMove toaEquipmentMove=new ToaShjfglEquipmentMove();
		toaEquipmentMove.setId(id);
		ToaShjfglEquipmentMove toaMachineMove=toaShjfglEquipmentMoveDao.get(toaEquipmentMove);
		try{
			if(createUserDept.equals(toaMachineMove.getCreateUserDept())&&toaMachineMove.getCaozgcs().equals(userId)){
				toaMachineMove.setWarnDate(new Date());
				toaMachineMove.setStatus(2);
				propertyService.fillProperties(toaMachineMove, true);
				result=toaShjfglEquipmentMoveDao.update(toaMachineMove);
			}else{
				result=0;
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaShjfglEquipmentMove);
			throw ce;
		}
		return result;
	}

	/**
	* @description APP开始操作方法(2-3)
	* @param EatOutreg eatOutreg 实体类
	* @param id 搬迁表id
	* @param sn 搬迁sn
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer updateMoveHandle(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		Long createUserDept=user.getDeptId();
		Long id=toaShjfglEquipmentMove.getId();
		//String scanCode=toaShjfglEquipmentMove.getSn();
		ToaShjfglEquipmentMove toaEquipmentMove=new ToaShjfglEquipmentMove();
		toaEquipmentMove.setId(id);
		ToaShjfglEquipmentMove toaMachineMove=toaShjfglEquipmentMoveDao.get(toaEquipmentMove);
		try{
			if(createUserDept.equals(toaMachineMove.getCreateUserDept())&&toaMachineMove.getCaozgcs().equals(userId)){
				//判断扫描码是否与SN一致
				//if(scanCode.equals(toaMachineMove.getSn())){
					toaMachineMove.setStartDate(new Date());
					toaMachineMove.setStatus(3);
					propertyService.fillProperties(toaMachineMove, true);
					result=toaShjfglEquipmentMoveDao.update(toaMachineMove);
//				}else{
//					result=0;
//				}
			}else{
				result=0;
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaShjfglEquipmentMove);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description APP操作方法(3-4)
	* @param EatOutreg eatOutreg 实体类
	* @param id 搬迁表id
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer updateMoveShow(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		Long createUserDept=user.getDeptId();
		Long id=toaShjfglEquipmentMove.getId();
		ToaShjfglEquipmentMove toaEquipmentMove=new ToaShjfglEquipmentMove();
		toaEquipmentMove.setId(id);
		ToaShjfglEquipmentMove toaMachineMove=toaShjfglEquipmentMoveDao.get(toaEquipmentMove);
		if(createUserDept.equals(toaMachineMove.getCreateUserDept())&&toaMachineMove.getCaozgcs().equals(userId)){
			toaMachineMove.setStatus(4);
			if(toaMachineMove.getOperationType().equals("2")){
				toaMachineMove.setShelfDate(new Date());
			}else{
				toaMachineMove.setEndDate(new Date());
			}
		}else{
			toaMachineMove.setStatus(3);
			result=0;
			return result;
		}
		
		toaMachineMove.setCabinetSpace(toaShjfglEquipmentMove.getCabinetSpace());
		toaMachineMove.setCabinetPower(toaShjfglEquipmentMove.getCabinetPower());
		toaMachineMove.setRemark(toaShjfglEquipmentMove.getRemark());
		toaMachineMove.setCheckAgainst(toaShjfglEquipmentMove.getCheckAgainst());
		toaMachineMove.setPowerOff(toaShjfglEquipmentMove.getPowerOff());
		toaMachineMove.setEquipmentConstruction(toaShjfglEquipmentMove.getEquipmentConstruction());
		toaMachineMove.setHaveAfterOperate(toaShjfglEquipmentMove.getHaveAfterOperate());
		toaMachineMove.setMormalEquipment(toaShjfglEquipmentMove.getMormalEquipment());
		toaMachineMove.setExtStr2(toaShjfglEquipmentMove.getExtStr2());
		try{
			propertyService.fillProperties(toaMachineMove, true);
			result=toaShjfglEquipmentMoveDao.update(toaMachineMove);
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaShjfglEquipmentMove);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description APP到达上架设备现场方法(4-5)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer headblockMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove)throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		Long createUserDept=user.getDeptId();
		Long id=toaShjfglEquipmentMove.getId();
		
		ToaShjfglEquipmentMove toaEquipmentMove=new ToaShjfglEquipmentMove();
		toaEquipmentMove.setId(id);
		ToaShjfglEquipmentMove toaMachineMove=toaShjfglEquipmentMoveDao.get(toaEquipmentMove);
		try{
			if(createUserDept.equals(toaMachineMove.getCreateUserDept())&&toaMachineMove.getCaozgcs().equals(userId)){
				toaMachineMove.setArrivalNewDate(new Date());
				toaMachineMove.setStatus(5);
				propertyService.fillProperties(toaMachineMove, true);
				result=toaShjfglEquipmentMoveDao.update(toaMachineMove);
			}else{
				result=0;
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaShjfglEquipmentMove);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description APP上架开始操作方法(5-6)
	* @param EatOutreg eatOutreg 实体类
	* @param id 搬迁表id
	* @param sn 搬迁sn
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer StoreOperatingMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		Long createUserDept=user.getDeptId();
		Long id=toaShjfglEquipmentMove.getId();
		//String scanCode=toaShjfglEquipmentMove.getSn();
		ToaShjfglEquipmentMove toaEquipmentMove=new ToaShjfglEquipmentMove();
		toaEquipmentMove.setId(id);
		ToaShjfglEquipmentMove toaMachineMove=toaShjfglEquipmentMoveDao.get(toaEquipmentMove);
		try{
			if(createUserDept.equals(toaMachineMove.getCreateUserDept())&&toaMachineMove.getCaozgcs().equals(userId)){
				//判断扫描码是否与SN一致
				//if(scanCode.equals(toaMachineMove.getSn())){
					toaMachineMove.setBuildDate(new Date());
					toaMachineMove.setStatus(6);             
					propertyService.fillProperties(toaMachineMove, true);
					result=toaShjfglEquipmentMoveDao.update(toaMachineMove);
//				}else{
//					result=0;
//				}
			}else{
				result=0;
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaShjfglEquipmentMove);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description APP搬迁完成方法(6-7)
	* @param EatOutreg eatOutreg 实体类
	* @param id 搬迁表id
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Override
	public Integer completeMove(ToaShjfglEquipmentMove toaShjfglEquipmentMove) throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;		
		//判断是不是机房处理
		User user =SystemSecurityUtils.getUser();
		String userId=user.getId().toString();
		Long createUserDept=user.getDeptId();
		Long id=toaShjfglEquipmentMove.getId();
		ToaShjfglEquipmentMove toaEquipmentMove=new ToaShjfglEquipmentMove();
		toaEquipmentMove.setId(id);
		ToaShjfglEquipmentMove toaMachineMove=toaShjfglEquipmentMoveDao.get(toaEquipmentMove);
		if(createUserDept.equals(toaMachineMove.getCreateUserDept())&&toaMachineMove.getCaozgcs().equals(userId)){
			toaMachineMove.setStatus(7);
			toaMachineMove.setEndDate(new Date());
		}else{
			toaMachineMove.setStatus(6);
			result=0;
			return result;
		}
		
		toaMachineMove.setCabinetSpace(toaShjfglEquipmentMove.getCabinetSpace());
		toaMachineMove.setCabinetPower(toaShjfglEquipmentMove.getCabinetPower());
		toaMachineMove.setRemark(toaShjfglEquipmentMove.getRemark());
		toaMachineMove.setCheckAgainst(toaShjfglEquipmentMove.getCheckAgainst());
		toaMachineMove.setPowerOff(toaShjfglEquipmentMove.getPowerOff());
		toaMachineMove.setEquipmentConstruction(toaShjfglEquipmentMove.getEquipmentConstruction());
		toaMachineMove.setHaveAfterOperate(toaShjfglEquipmentMove.getHaveAfterOperate());
		toaMachineMove.setMormalEquipment(toaShjfglEquipmentMove.getMormalEquipment());
		toaMachineMove.setExtStr2(toaShjfglEquipmentMove.getExtStr2());
		try{
			propertyService.fillProperties(toaMachineMove, true);
			result=toaShjfglEquipmentMoveDao.update(toaMachineMove);
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaShjfglEquipmentMove);
			throw ce;
		}
		return result;
	}

}
