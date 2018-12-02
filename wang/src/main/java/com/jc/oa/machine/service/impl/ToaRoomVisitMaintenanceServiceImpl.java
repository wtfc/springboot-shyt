package com.jc.oa.machine.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jc.android.oa.common.JpushClientUtil;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.dao.IToaMachineMessageDao;
import com.jc.oa.machine.dao.IToaRoomVisitMaintenanceDao;
import com.jc.oa.machine.domain.ToaMachineMessage;
import com.jc.oa.machine.domain.ToaRoomVisitMaintenance;
import com.jc.oa.machine.service.IToaRoomVisitMaintenanceService;
import com.jc.oa.shyt.domain.Customer;
import com.jc.oa.shyt.service.ICustomerService;
import com.jc.system.CustomException;
import com.jc.system.common.util.StringUtil;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
/**
 * @author mrb
 * @version 机房参观和入室维护表
*/
@Service
public class  ToaRoomVisitMaintenanceServiceImpl  extends BaseServiceImpl<ToaRoomVisitMaintenance> implements IToaRoomVisitMaintenanceService {

	public ToaRoomVisitMaintenanceServiceImpl(){}	

    private IToaRoomVisitMaintenanceDao toaRoomVisitMaintenanceDao;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IToaMachineMessageDao toaMachineMessageDao;

	@Autowired
	public ToaRoomVisitMaintenanceServiceImpl(IToaRoomVisitMaintenanceDao toaRoomVisitMaintenanceDao){
		super(toaRoomVisitMaintenanceDao);
		this.toaRoomVisitMaintenanceDao = toaRoomVisitMaintenanceDao;
	}	
	
	/**
	* @description 根据工单类型保存方法
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Integer operateType 工单类型（19、机房参观；20、入室维护）
	* @return Integer 处理结果
	* @author wtf
	* @version  2017-04-25
	*/
	@Override
	public Integer saveByOperateType(ToaRoomVisitMaintenance toaRoomVisitMaintenance) throws CustomException {
		Integer result = -1;
		Integer msgResult = -1;
		Integer operateType = toaRoomVisitMaintenance.getOperateType();
				
		//机房参观.陪同人	入室维护.机房接待人员
		User user =SystemSecurityUtils.getUser();
		String userCode=user.getCode().toString();
		String userName=user.getDisplayName();
		if(!StringUtil.isEmpty(userCode)){
			toaRoomVisitMaintenance.setCompanyId(Integer.parseInt(userCode));
			toaRoomVisitMaintenance.setCreateUser(Long.parseLong(userCode));
		}
		toaRoomVisitMaintenance.setCompanyName(userName);
		String machina = toaRoomVisitMaintenance.getMachina();
		if("room_0".equals(machina)){//鲁谷机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1056);
		}else if("room_1".equals(machina)){//清华园机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1057);
		}else if("room_2".equals(machina)){//兆维机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1050);
		}else if("room_3".equals(machina)){//沙河机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1051);
		}else if("room_4".equals(machina)){//看丹桥机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1055);
		}else if("room_5".equals(machina)){//廊坊机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1053);
		}else if("room_6".equals(machina)){//富丰桥机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1058);
		}else if("room_7".equals(machina)){//亦庄大族机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1052);
		}else if("room_8".equals(machina)){//洋桥机房
			toaRoomVisitMaintenance.setCreateUserDept((long) 1059);
		}else{
			toaRoomVisitMaintenance.setCreateUserDept((long) 1028);
		}

		toaRoomVisitMaintenance.setCreateDate(new Date());
		
		try{
			propertyService.fillProperties(toaRoomVisitMaintenance,true);
			if(operateType==19){//19、机房参观
				//操作类型图标  1、重启操作  6、系统配置 7、系统安装...19、机房参观
				toaRoomVisitMaintenance.setExtStr1(operateType.toString());
				result = this.jiFangCanGuan(toaRoomVisitMaintenance);
			}else if(operateType==20){//20、入室维护
				//操作类型图标  1、重启操作  6、系统配置 7、系统安装...20、入室维护
				toaRoomVisitMaintenance.setExtStr1(operateType.toString());
				result = this.ruShiWeiHu(toaRoomVisitMaintenance);
			}
			
			//工单消息
			ToaMachineMessage toaMachineMessage = new ToaMachineMessage();
			if(result == 1){
				
				//编号
				INumber number = new Number();
				String applyNum = number.getNumber("MessageNumber", 1,2, null);
				applyNum = applyNum.substring(1, applyNum.length());
				//消息设置
				toaMachineMessage.setMessageNumber(applyNum);
				toaMachineMessage.setMessageTitle(toaRoomVisitMaintenance.getOperationTypeValue());
				toaMachineMessage.setMessageContent(toaRoomVisitMaintenance.getCompanyName());
				toaMachineMessage.setMessageType(toaRoomVisitMaintenance.getOperateType().toString());
				toaMachineMessage.setReceivedDeptId(toaRoomVisitMaintenance.getCreateUserDept().toString());

				propertyService.fillProperties(toaMachineMessage, false);
				msgResult = toaMachineMessageDao.save(toaMachineMessage);
				
			}
			
			//发出消息
			if(msgResult == 1){
				JpushClientUtil.sendToAllAndroid(toaMachineMessage.getMessageTitle(), null, toaMachineMessage.getMessageContent(), toaMachineMessage.getMessageType(), toaMachineMessage.getReceivedDeptId());
			}
			
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaRoomVisitMaintenance);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaRoomVisitMaintenance toaRoomVisitMaintenance) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaRoomVisitMaintenance,true);
			result = toaRoomVisitMaintenanceDao.delete(toaRoomVisitMaintenance);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaRoomVisitMaintenance);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description 根据工单类型保存方法-19、机房参观
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Integer operateType 工单类型（19、机房参观；20、入室维护）
	* @return Integer 处理结果
	* @author wtf
	* @version  2017-04-25
	*/
	public Integer jiFangCanGuan(ToaRoomVisitMaintenance toaRoomVisitMaintenance) throws CustomException {
		Integer result = -1;
		String customerId=SystemSecurityUtils.getUser().getCode();
		//行业类型
		if(!StringUtils.isEmpty(customerId)){
			Customer customer = new Customer();
			customer.setId(Long.parseLong(customerId));
			customer = customerService.get(customer);
			if(customer!=null){
				toaRoomVisitMaintenance.setIndustryType(customer.getTrade());
			}
		}
		try{
			propertyService.fillProperties(toaRoomVisitMaintenance,true);
			result = toaRoomVisitMaintenanceDao.save(toaRoomVisitMaintenance);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaRoomVisitMaintenance);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description 根据工单类型保存方法-20、入室维护
	* @param ToaRoomVisitMaintenance4M toaRoomVisitMaintenance 实体类
	* @param Integer operateType 工单类型（19、机房参观；20、入室维护）
	* @return Integer 处理结果
	* @author wtf
	* @version  2017-04-25
	*/
	public Integer ruShiWeiHu(ToaRoomVisitMaintenance toaRoomVisitMaintenance) throws CustomException {
		Integer result = -1;
		toaRoomVisitMaintenance.setCreateDate(new Date());
		try{
			propertyService.fillProperties(toaRoomVisitMaintenance,true);
			result = toaRoomVisitMaintenanceDao.save(toaRoomVisitMaintenance);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaRoomVisitMaintenance);
			throw ce;
		}
		return result;
	}
	
	/** 接单  status(0-1)  申请状态 =》已接单状态
	 * 	@param Long id 工单ID
	 * 
	 * */
	@Override
	public Integer jieDan(ToaRoomVisitMaintenance toaRoomVisitMaintenance)
			throws CustomException {
		Integer result = -1;

		ToaRoomVisitMaintenance toaRoomVisitMaintenanceTemp = new ToaRoomVisitMaintenance();
		toaRoomVisitMaintenanceTemp.setId(toaRoomVisitMaintenance.getId());
		
		toaRoomVisitMaintenance = toaRoomVisitMaintenanceDao.get(toaRoomVisitMaintenanceTemp);
		//修改工单状态
		toaRoomVisitMaintenance.setStatus(1);
		//机房参观.陪同人	入室维护.机房接待人员
		User user =SystemSecurityUtils.getUser();
		String userCode=user.getId().toString();
		String userName=user.getDisplayName();
		toaRoomVisitMaintenance.setCaozgcs(userCode);
		toaRoomVisitMaintenance.setCaozgcsName(userName);
		
		//机房参观.参观开始时间    入室维护.实际入室日期
		toaRoomVisitMaintenance.setStartDate(new Date());
		try{
			propertyService.fillProperties(toaRoomVisitMaintenance,true);
			
			result = toaRoomVisitMaintenanceDao.update(toaRoomVisitMaintenance);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaRoomVisitMaintenance);
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
	public Integer shenFenShenHe(ToaRoomVisitMaintenance toaRoomVisitMaintenance)
			throws CustomException {
		Integer result = -1;
		
		ToaRoomVisitMaintenance toaRoomVisitMaintenanceTemp = new ToaRoomVisitMaintenance();
		toaRoomVisitMaintenanceTemp.setId(toaRoomVisitMaintenance.getId());
		toaRoomVisitMaintenanceTemp=toaRoomVisitMaintenanceDao.get(toaRoomVisitMaintenanceTemp);

		//修改工单状态
		toaRoomVisitMaintenanceTemp.setStatus(2);
		try{
			propertyService.fillProperties(toaRoomVisitMaintenanceTemp,true);
			
			result = toaRoomVisitMaintenanceDao.update(toaRoomVisitMaintenanceTemp);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaRoomVisitMaintenance);
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
	public Integer operate(ToaRoomVisitMaintenance toaRoomVisitMaintenance)
			throws CustomException {
		Integer result = -1;

		ToaRoomVisitMaintenance toaRoomVisitMaintenanceTemp = new ToaRoomVisitMaintenance();
		toaRoomVisitMaintenanceTemp.setId(toaRoomVisitMaintenance.getId());
		toaRoomVisitMaintenanceTemp=toaRoomVisitMaintenanceDao.get(toaRoomVisitMaintenanceTemp);
		//修改工单状态
		toaRoomVisitMaintenanceTemp.setStatus(3);
		
		//机房参观.参观结束时间 	入室维护.实际出室日期
		toaRoomVisitMaintenanceTemp.setEndDate(new Date());
		try{
			propertyService.fillProperties(toaRoomVisitMaintenanceTemp,true);
			
			result = toaRoomVisitMaintenanceDao.update(toaRoomVisitMaintenanceTemp);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaRoomVisitMaintenance);
			throw ce;
		}
		return result;
	}
}
