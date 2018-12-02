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
import com.jc.shjfgl.machine.dao.IEquipmentDao;
import com.jc.shjfgl.machine.dao.IToaShjfglEquipmentWrongDao;
import com.jc.shjfgl.machine.domain.Equipment;
import com.jc.shjfgl.machine.domain.ToaShjfglEquipmentWrong;
import com.jc.shjfgl.machine.service.IToaShjfglEquipmentWrongService;
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
 * @version 故障处理表
*/
@Service
public class  ToaShjfglEquipmentWrongServiceImpl  extends BaseServiceImpl<ToaShjfglEquipmentWrong> implements IToaShjfglEquipmentWrongService {

	public ToaShjfglEquipmentWrongServiceImpl(){}	
	@Autowired
    private IToaShjfglEquipmentWrongDao toaShjfglEquipmentWrongDao;
    @Autowired
    private IEquipmentDao equipmentDao;
    @Autowired
	private IDicDao dicDao;
	@Autowired
	private IDepartmentDao departmentDao;
    @Autowired
    private IToaMachineMessageDao toaMachineMessageDao;

	@Autowired
	public ToaShjfglEquipmentWrongServiceImpl(IToaShjfglEquipmentWrongDao toaShjfglEquipmentWrongDao){
		super(toaShjfglEquipmentWrongDao);
		this.toaShjfglEquipmentWrongDao = toaShjfglEquipmentWrongDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaShjfglEquipmentWrong,true);
			result = toaShjfglEquipmentWrongDao.delete(toaShjfglEquipmentWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShjfglEquipmentWrong);
			throw ce;
		}
		return result;
	}

	@Override
	public Integer saveByEquipment(Equipment equipment,ToaShjfglEquipmentWrong toaShjfglEquipmentWrong)throws CustomException {
		Integer result = -1;
		Integer msgResult=-1;
		String machina = null;
		String operateType = toaShjfglEquipmentWrong.getOperationType();

		//客户信息
		User user =SystemSecurityUtils.getUser();
		String userCode=user.getCode().toString();
		String userName=user.getDisplayName();
		if(!StringUtil.isEmpty(userCode)){
			toaShjfglEquipmentWrong.setCompanyId(Integer.parseInt(userCode));
			toaShjfglEquipmentWrong.setCreateUser(Long.parseLong(userCode));
			//toaShjfglEquipmentWrong.setCreateUserDept(user.getDeptId());
		}
		toaShjfglEquipmentWrong.setCompanyName(userName);
		equipment=equipmentDao.get(equipment);
		if(equipment!=null){
			machina = equipment.getContact();
		//}
		
			//得到机房code
			Dic dic = new Dic();
			dic.setValue(machina);
			dic = dicDao.get(dic);
			if(dic!=null){//机房
				toaShjfglEquipmentWrong.setMachina(dic.getCode());
			}else{
				toaShjfglEquipmentWrong.setMachina(machina);
			}
			
			Department department = new Department();
			department.setName(machina);
			department = departmentDao.get(department);
			if(department!=null){//机房所在部门
				toaShjfglEquipmentWrong.setCreateUserDept(department.getId());
			}else{
				toaShjfglEquipmentWrong.setCreateUserDept((long) 1028);
			}
			
			toaShjfglEquipmentWrong.setDiviceNumber(equipment.getEquipmentNumber());//设备编号
			toaShjfglEquipmentWrong.setCabinet(equipment.getAddress());//机柜
			toaShjfglEquipmentWrong.setIp(equipment.getIp());//IP
			toaShjfglEquipmentWrong.setSn(equipment.getSn());//SN
			toaShjfglEquipmentWrong.setBrand(equipment.getType());//品牌型号
			toaShjfglEquipmentWrong.setStatus(0);//品牌型号
			toaShjfglEquipmentWrong.setExtStr1(equipment.getMachina());//机房区域
			toaShjfglEquipmentWrong.setExtStr2(equipment.getMachinaNumber());//机柜编号
			toaShjfglEquipmentWrong.setDiviceNumber(equipment.getEquipmentNumber());//设备编号
			
			toaShjfglEquipmentWrong.setCreateDate(new Date());
			toaShjfglEquipmentWrong.setBillDate(new Date());
		
			//List<Equipment> equipmentNew=equipmentDao.queryAll(equipment);
			//if(equipmentNew.size()>0){

			propertyService.fillProperties(toaShjfglEquipmentWrong,true);
			//操作类型图标  1、重启操作  6、系统配置 7、系统安装...19、机房参观
			toaShjfglEquipmentWrong.setOperationTypeImg(operateType.toString());
			//保存
			
			Integer flag =toaShjfglEquipmentWrongDao.save(toaShjfglEquipmentWrong);
			
			//工单消息
			ToaMachineMessage toaMachineMessage = new ToaMachineMessage();
			if(flag == 1){
				
				//编号
				INumber number = new Number();
				String applyNum = number.getNumber("MessageNumber", 1,2, null);
				applyNum = applyNum.substring(1, applyNum.length());

				toaMachineMessage.setMessageNumber(applyNum);
				toaMachineMessage.setMessageTitle(toaShjfglEquipmentWrong.getOperationTypeValue());
				toaMachineMessage.setMessageContent(toaShjfglEquipmentWrong.getCompanyName());
				toaMachineMessage.setMessageType(toaShjfglEquipmentWrong.getOperationType());

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

	/** 接单  status(0-1)  申请状态 =》已接单状态
	 * 	@param Long id 工单ID
	 * @throws CustomException 
	 * 
	 * */
	@Override
	public Integer jieDan(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException{
		Integer result = -1;

		//修改工单状态
		toaShjfglEquipmentWrong.setStatus(1);
		//机房参观.陪同人	入室维护.机房接待人员
		User user =SystemSecurityUtils.getUser();
		String userCode=user.getId().toString();
		String userName=user.getDisplayName();
		toaShjfglEquipmentWrong.setCaozgcs(userCode);
		toaShjfglEquipmentWrong.setCaozgcsName(userName);
		
		//接单时间
		toaShjfglEquipmentWrong.setWorkTime(new Date());
		try{
			propertyService.fillProperties(toaShjfglEquipmentWrong,true);
			
			result = toaShjfglEquipmentWrongDao.update(toaShjfglEquipmentWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShjfglEquipmentWrong);
			throw ce;
		}
		return result;
	}
	
	/** 身份审核 status(1-2) 已接单状态=》审核通过状态
	 * 	@param Long id 工单ID
	 * 	@param String intPeopleCard 入室人员身份证号
	 * 	
	 * */
	@Override
	public Integer daoChang(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException{
		Integer result = -1;

		//修改工单状态
		toaShjfglEquipmentWrong.setStatus(2);

		//到长时间=》操作开始时间
		toaShjfglEquipmentWrong.setStartDate(new Date());
		try{
			propertyService.fillProperties(toaShjfglEquipmentWrong,true);
			
			result = toaShjfglEquipmentWrongDao.update(toaShjfglEquipmentWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShjfglEquipmentWrong);
			throw ce;
		}
		return result;
	}
	
	/** 进行操作(机房参观、入室维护) status(2-3) 审核通过状态=》操作完成状态
	 * 	@param Long id 工单ID
	 * 	@param String realityType 实际操作内容
	 * 
	 * */
	@Override
	public Integer operate(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException{
		Integer result = -1;

		//修改工单状态
		toaShjfglEquipmentWrong.setStatus(3);
		
		//结束时间
		//toaShjfglEquipmentWrong.setEndDate(new Date());
		try{
			propertyService.fillProperties(toaShjfglEquipmentWrong,true);
			
			result = toaShjfglEquipmentWrongDao.update(toaShjfglEquipmentWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShjfglEquipmentWrong);
			throw ce;
		}
		return result;
	}
	
	/** 进行操作(机房参观、入室维护) status(2-3) 审核通过状态=》操作完成状态
	 * 	@param Long id 工单ID
	 * 	@param String realityType 实际操作内容
	 * 
	 * */
	@Override
	public Integer renke(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException{
		Integer result = -1;

		if(toaShjfglEquipmentWrong.getIsAgree()==1){
			//修改工单状态
			toaShjfglEquipmentWrong.setStatus(4);
		}else{
			//修改工单状态
			toaShjfglEquipmentWrong.setStatus(2);
		}
		
		//结束时间
		toaShjfglEquipmentWrong.setEndDate(new Date());
		try{
			propertyService.fillProperties(toaShjfglEquipmentWrong,true);
			
			result = toaShjfglEquipmentWrongDao.update(toaShjfglEquipmentWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaShjfglEquipmentWrong);
			throw ce;
		}
		return result;
	}

	/**
	 * App报障工单列表方法
	 * @param TimeSet timeSet 实体类
	 * @param BindingResult result 校验结果
	 * @return success 是否成功， errorMessage错误信息
	 * @throws Exception
	 * @author
	 * @throws CustomException 
	 */
	@Override
	public List<ToaShjfglEquipmentWrong> queryApp(ToaShjfglEquipmentWrong toaShjfglEquipmentWrong) throws CustomException {
		// TODO Auto-generated method stub
		return toaShjfglEquipmentWrongDao.queryApp(toaShjfglEquipmentWrong);
	}

}
