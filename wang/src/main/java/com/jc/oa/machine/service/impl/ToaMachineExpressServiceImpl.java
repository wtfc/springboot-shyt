package com.jc.oa.machine.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.jc.android.oa.common.JpushClientUtil;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.dao.IToaMachineExpressDao;
import com.jc.oa.machine.dao.IToaMachineMessageDao;
import com.jc.oa.machine.domain.ToaMachineExpress;
import com.jc.oa.machine.domain.ToaMachineMessage;
import com.jc.oa.machine.service.IToaMachineExpressService;
import com.jc.system.CustomException;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
/**
 * @author mrb
 * @version 代收发快递
*/
@Service
public class  ToaMachineExpressServiceImpl  extends BaseServiceImpl<ToaMachineExpress> implements IToaMachineExpressService {

	public ToaMachineExpressServiceImpl(){}	

    private IToaMachineExpressDao toaMachineExpressDao;
    
    @Autowired
    private IToaMachineMessageDao toaMachineMessageDao;

	@Autowired
	public ToaMachineExpressServiceImpl(IToaMachineExpressDao toaMachineExpressDao){
		super(toaMachineExpressDao);
		this.toaMachineExpressDao = toaMachineExpressDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaMachineExpress toaMachineExpress) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaMachineExpress,true);
			result = toaMachineExpressDao.delete(toaMachineExpress);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaMachineExpress);
			throw ce;
		}
		return result;
	}

	/**
	* @description 保存方法(状态0)
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author 
	*/
	/* (non-Javadoc)
	 * @see com.jc.oa.machine.service.IToaMachineExpressService#saveExpress(com.jc.oa.machine.domain.ToaMachineExpress)
	 */
	@Override
	public Integer saveExpress(ToaMachineExpress toaMachineExpress)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		Integer msgResult=-1;
		try{
			//获取客户id
			User user = SystemSecurityUtils.getUser();
			toaMachineExpress.setCompanyName(user.getDisplayName());
			toaMachineExpress.setCompanyId(Integer.valueOf(user.getCode()));
			toaMachineExpress.setCompanyName(user.getDisplayName());
			String machina=toaMachineExpress.getMachina();
			if(machina.equals("room_0")){
				toaMachineExpress.setCreateUserDept((long) 1056);
			}else if(machina.equals("room_1")){
				toaMachineExpress.setCreateUserDept((long) 1057);
			}else if(machina.equals("room_2")){
				toaMachineExpress.setCreateUserDept((long) 1050);
			}else if(machina.equals("room_3")){
				toaMachineExpress.setCreateUserDept((long) 1051);
			}else if(machina.equals("room_4")){
				toaMachineExpress.setCreateUserDept((long) 1055);
			}else if(machina.equals("room_5")){
				toaMachineExpress.setCreateUserDept((long) 1053);
			}else if(machina.equals("room_6")){
				toaMachineExpress.setCreateUserDept((long) 1058);
			}else if(machina.equals("room_7")){
				toaMachineExpress.setCreateUserDept((long) 1052);
			}else if(machina.equals("room_8")){
				toaMachineExpress.setCreateUserDept((long) 1059);
			}else{
				toaMachineExpress.setCreateUserDept((long) 1028);
			}
			
			toaMachineExpress.setStatus(0);
			propertyService.fillProperties(toaMachineExpress,false);
			result=toaMachineExpressDao.save(toaMachineExpress);
			//工单消息
			ToaMachineMessage toaMachineMessage = new ToaMachineMessage();
			if(result == 1){
				
				//编号
				INumber number = new Number();
				String applyNum = number.getNumber("MessageNumber", 1,2, null);
				applyNum = applyNum.substring(1, applyNum.length());
				//消息设置
				toaMachineMessage.setMessageNumber(applyNum);
				if("0".equals(toaMachineExpress.getType())){
					toaMachineMessage.setMessageTitle("代发快递");//操作类型  需修改
				}else if("1".equals(toaMachineExpress.getType())){
					toaMachineMessage.setMessageTitle("代收快递");//操作类型  需修改
				}
				toaMachineMessage.setMessageContent(toaMachineExpress.getCompanyName());
				toaMachineMessage.setMessageType("2");//操作类型  需修改

				String receivedDeptId = null;
				receivedDeptId = toaMachineExpress.getCreateUserDept().toString();
				
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
			ce.setBean(toaMachineExpress);
			throw ce;
		}
		return result;
	}

	/**
	* @description 接单方法(状态0-1)
	* @param Long id 代收发快递id
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author 
	*/
	@Override
	public Integer updateExpress(ToaMachineExpress toaMachineExpress)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		try{
			User user=SystemSecurityUtils.getUser();
			String userName=user.getDisplayName();
			String userId=user.getId().toString();
			ToaMachineExpress toaExpress=new ToaMachineExpress();
			toaExpress.setId(toaMachineExpress.getId());
			toaMachineExpress=toaMachineExpressDao.get(toaExpress);
			//操作工程师id
			toaMachineExpress.setCaozgcs(userId);
			//操作工程师名称
			toaMachineExpress.setExtStr1(userName);
			toaMachineExpress.setStatus(1);
			toaMachineExpress.setOrderDate(new Date());
			propertyService.fillProperties(toaMachineExpress,true);
			result=toaMachineExpressDao.update(toaMachineExpress);
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineExpress);
			throw ce;
		}
		return result;
	}

	/**
	* @description 完成方法(状态1-2)
	* @param Long id 代收发快递id
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author 
	*/
	@Override
	public Integer updateComplete(ToaMachineExpress toaMachineExpress)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		try{
			ToaMachineExpress toaExpress=new ToaMachineExpress();
			toaExpress.setId(toaMachineExpress.getId());
			ToaMachineExpress toaExpressMachine=toaMachineExpressDao.get(toaExpress);
			toaExpressMachine.setPackingModel(toaMachineExpress.getPackingModel());
			toaExpressMachine.setPayType(toaMachineExpress.getPayType());
			toaExpressMachine.setDaifuPay(toaMachineExpress.getDaifuPay());
    		toaExpressMachine.setDrawee(toaMachineExpress.getDrawee());
    		toaExpressMachine.setCheckAccept(toaMachineExpress.getCheckAccept());
    		toaExpressMachine.setCheckOther(toaMachineExpress.getCheckOther());
    		toaExpressMachine.setCheckAccepttance(toaMachineExpress.getCheckAccepttance());
    		toaExpressMachine.setSignReceiver(toaMachineExpress.getSignReceiver());
			if(toaExpressMachine != null){
				toaExpressMachine.setStatus(2);
				toaExpressMachine.setCompletionDate(new Date());
				propertyService.fillProperties(toaExpressMachine, true);
				result=toaMachineExpressDao.update(toaExpressMachine);
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineExpress);
			throw ce;
		}
		return result;
	}
	
	/**
	* @description 拒收方法(状态1-3)
	* @param Long id 代收发快递id
	* @param String checkAccepttance 验收
	* @param String  signReceiver 签收
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author 
	*/
	@Override
	public Integer updateRejection(ToaMachineExpress toaMachineExpress)
			throws CustomException {
		// TODO Auto-generated method stub
		Integer result=-1;
		try{
			ToaMachineExpress toaExpress=new ToaMachineExpress();
			toaExpress.setId(toaMachineExpress.getId());
			String checkAccepttance=toaMachineExpress.getCheckAccepttance();
			String signReceiver=toaMachineExpress.getSignReceiver();
			ToaMachineExpress toaExpressMachine=toaMachineExpressDao.get(toaExpress);
			if(toaExpressMachine!=null){
				if(checkAccepttance.equals("否")&&signReceiver.equals("否")){
					toaExpressMachine.setCheckAccepttance(checkAccepttance);
					toaExpressMachine.setSignReceiver(signReceiver);
					toaExpressMachine.setStatus(3);
					toaExpressMachine.setCompletionDate(new Date());
					propertyService.fillProperties(toaExpressMachine,true);
					result=toaMachineExpressDao.update(toaExpressMachine);
				}
			}
		}catch(Exception e){
			CustomException ce=new CustomException(e);
			ce.setBean(toaMachineExpress);
			throw ce;
		}
		return result;
	}

	/**
	* @description 查询列表
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author 
	*/
	@Override
	public List<ToaMachineExpress> quertExpress(ToaMachineExpress toaMachineExpress) throws CustomException {
		// TODO Auto-generated method stub
		return toaMachineExpressDao.queryExpress(toaMachineExpress);
	}

	

}
