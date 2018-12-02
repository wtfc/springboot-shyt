package com.jc.oa.network.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.jc.android.oa.common.JpushClientUtil;
import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.machine.dao.IToaMachineMessageDao;
import com.jc.oa.machine.domain.ToaMachineMessage;
import com.jc.oa.network.dao.IToaNetworkWrongDao;
import com.jc.oa.network.domain.ToaNetworkWrong;
import com.jc.oa.network.service.IToaNetworkWrongService;
import com.jc.system.CustomException;
import com.jc.system.number.service.Number;
import com.jc.system.number.service.interfaces.INumber;
import com.jc.system.security.SystemSecurityUtils;
import com.jc.system.security.domain.User;
/**
 * @author mrb
 * @version 网络故障处理表
*/
@Service
public class  ToaNetworkWrongServiceImpl  extends BaseServiceImpl<ToaNetworkWrong> implements IToaNetworkWrongService {

	public ToaNetworkWrongServiceImpl(){}	

    private IToaNetworkWrongDao toaNetworkWrongDao;
    
    @Autowired
    private IToaMachineMessageDao toaMachineMessageDao;

	@Autowired
	public ToaNetworkWrongServiceImpl(IToaNetworkWrongDao toaNetworkWrongDao){
		super(toaNetworkWrongDao);
		this.toaNetworkWrongDao = toaNetworkWrongDao;
	}	
	
	/**
	* @description 根据主键删除多条记录方法
	* @param EatOutreg eatOutreg 实体类
	* @return Integer 处理结果
	* @author
	* @version  2015-03-31 
	*/
	@Transactional(rollbackFor=Exception.class,propagation=Propagation.REQUIRED)
	public Integer deleteByIds(ToaNetworkWrong toaNetworkWrong) throws CustomException{
		Integer result = -1;
		try{
			propertyService.fillProperties(toaNetworkWrong,true);
			result = toaNetworkWrongDao.delete(toaNetworkWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkWrong);
			throw ce;
		}
		return result;
	}

	/** 接单  status(0-1)  申请状态 =》已接单状态
	 * 	@param Long id 工单ID
	 * @throws CustomException 
	 * 
	 * */
	@Override
	public Integer jieDan(ToaNetworkWrong toaNetworkWrong) throws CustomException{
		Integer result = -1;

		//修改工单状态
		toaNetworkWrong.setStatus(1);
		//机房参观.陪同人	入室维护.机房接待人员
		User user =SystemSecurityUtils.getUser();
		String userCode=user.getId().toString();
		String userName=user.getDisplayName();
		toaNetworkWrong.setCaozgcs(userCode);
		toaNetworkWrong.setCaozgcsName(userName);
		
		//接单时间
		toaNetworkWrong.setWorkTime(new Date());
		try{
			propertyService.fillProperties(toaNetworkWrong,true);
			
			result = toaNetworkWrongDao.update(toaNetworkWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkWrong);
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
	public Integer daoChang(ToaNetworkWrong toaNetworkWrong) throws CustomException{
		Integer result = -1;

		//修改工单状态
		toaNetworkWrong.setStatus(2);

		//到长时间=》操作开始时间
		toaNetworkWrong.setStartDate(new Date());
		try{
			propertyService.fillProperties(toaNetworkWrong,true);
			
			result = toaNetworkWrongDao.update(toaNetworkWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkWrong);
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
	public Integer operate(ToaNetworkWrong toaNetworkWrong) throws CustomException{
		Integer result = -1;

		//修改工单状态
		toaNetworkWrong.setStatus(3);
		
		//结束时间
		//toaShjfglEquipmentWrong.setEndDate(new Date());
		try{
			propertyService.fillProperties(toaNetworkWrong,true);
			
			result = toaNetworkWrongDao.update(toaNetworkWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkWrong);
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
	public Integer renke(ToaNetworkWrong toaNetworkWrong) throws CustomException{
		Integer result = -1;

		if(toaNetworkWrong.getIsAgree()==1){
			//修改工单状态
			toaNetworkWrong.setStatus(4);
		}else{
			//修改工单状态
			toaNetworkWrong.setStatus(2);
		}
		
		//结束时间
		toaNetworkWrong.setEndDate(new Date());
		try{
			propertyService.fillProperties(toaNetworkWrong,true);
			
			result = toaNetworkWrongDao.update(toaNetworkWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkWrong);
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
	public List<ToaNetworkWrong> queryApp(ToaNetworkWrong toaNetworkWrong) throws CustomException {
		// TODO Auto-generated method stub
		return toaNetworkWrongDao.queryApp(toaNetworkWrong);
	}

	@Override
	public Integer pingJia(ToaNetworkWrong toaNetworkWrong)
			throws CustomException {
		Integer result = -1;
		
		toaNetworkWrong.setStatus(5);
		try{
			propertyService.fillProperties(toaNetworkWrong,true);
			
			result = toaNetworkWrongDao.update(toaNetworkWrong);
		}catch(Exception e){
			CustomException ce = new CustomException(e);
			ce.setBean(toaNetworkWrong);
			throw ce;
		}
		
		return result;
	}

	@Override
	public Integer save4M(ToaNetworkWrong toaNetworkWrong)
			throws CustomException {
		Integer result = -1;
		Integer msgResult=-1;
		propertyService.fillProperties(toaNetworkWrong, false);
		User user = SystemSecurityUtils.getUser();
		if(!StringUtils.isEmpty(user.getCode())){
			toaNetworkWrong.setCompanyId(Integer.parseInt(user.getCode()));//客户id
			toaNetworkWrong.setCompanyName(user.getDisplayName());//客户名称
			toaNetworkWrong.setStatus(0);
			result = toaNetworkWrongDao.save(toaNetworkWrong);
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
			toaMachineMessage.setMessageTitle(toaNetworkWrong.getOperationTypeValue());
			toaMachineMessage.setMessageContent(toaNetworkWrong.getCompanyName());
			toaMachineMessage.setMessageType(toaNetworkWrong.getOperationType());

			String receivedDeptId = null;
			receivedDeptId = "1066";//网络维护组
			
			propertyService.fillProperties(toaMachineMessage, false);
			
			toaMachineMessage.setReceivedDeptId(receivedDeptId);
			msgResult = toaMachineMessageDao.save(toaMachineMessage);
			
		}
		
		//发出消息
		if(msgResult == 1){
			JpushClientUtil.sendToAllAndroid(toaMachineMessage.getMessageTitle(), null, toaMachineMessage.getMessageContent(), toaMachineMessage.getMessageType(), toaMachineMessage.getReceivedDeptId());
		}
		return result;
	}
	

}
