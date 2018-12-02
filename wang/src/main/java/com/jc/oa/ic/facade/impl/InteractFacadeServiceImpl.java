package com.jc.oa.ic.facade.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jc.foundation.service.impl.BaseServiceImpl;
import com.jc.oa.common.domain.Remind;
import com.jc.oa.ic.IcException;
import com.jc.oa.ic.ValidateException;
import com.jc.oa.ic.domain.Mail;
import com.jc.oa.ic.domain.Out;
import com.jc.oa.ic.facade.IInteractFacadeService;
import com.jc.oa.ic.service.IMailService;
import com.jc.oa.ic.service.IOutService;
import com.jc.system.CustomException;
import com.jc.system.common.util.Constants;
import com.jc.system.common.util.GlobalContext;
import com.jc.system.common.util.MessageUtils;
import com.jc.system.security.util.UserUtils;

/**
 * @title GOA V2.0
 * @description 互动交流门面服务实现类 Copyright (c) 2014 Jiacheng.com Inc. All Rights
 *              Reserved Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version 2014年5月22日上午20:21:33
 */
@Service
public class InteractFacadeServiceImpl implements IInteractFacadeService {

	@Autowired
	private IOutService outService;

	@Autowired
	private IMailService mailService;
	
	protected transient final Logger log = Logger.getLogger(InteractFacadeServiceImpl.class);
	/**
	 * 方法描述： 发送短信
	 * 
	 * @param messages
	 *            参数Map结构： {key:SMS_SEND_IDS,Value:接收人id},
	 *            {key:SMS_TEXT,Value:短信内容}, {key:SMS_SENDTYPE,Value:短信类型},
	 *            {key:SMS_CEATEUSER,Value:发送人}
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年5月22日上午20:21:33
	 * @throws ValidateException 
	 * @see
	 */
	@Override
	public boolean sendSms(Map<String, String> messages) throws IcException{

		Out out = new Out();
		// 用户id用“，”分割
		out.setUserId(messages.get(SMS_SEND_IDS));
		// 短信内容
		out.setText(messages.get(SMS_TEXT));
		// 短信类型
		String sendType = messages.get(SMS_SENDTYPE);
		if (sendType == null || "".equals(sendType)) {
			out.setSendType("sendType_1");
		} else {
			out.setSendType(sendType);
		}
		
		// 发送人
		out.setCreateUser(Long.valueOf(messages.get(SMS_CEATEUSER)).longValue());
		try {
			if (outService.sendAndSave(out) == 1) {
				return true;
			}
		} catch (Exception e) {
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_004"));
			throw se;
		}

		return false;
	}
	
	
	/**
	 * 方法描述：发短信校验用户中是否有存在没有电话号的情况，并且校验该用户是否有发送短信条数，提示内容为小于70字
	 * @param SMS_SEND_IDS 用户id以“，”分割的字符串
	 * @return resultMap 判断短信功能是否开启，如果没开启GlobalContext.RESULT_SUCCESS, "close"
	 * 					   否则判断是否存在可发送短息如果不存在可发送短信数量GlobalContext.RESULT_SUCCESS, false,
	 * 					   否则判断是否存在没有电话号人员 如果有GlobalContext.RESULT_SUCCESS, false，
	 * 					   否则GlobalContext.RESULT_SUCCESS,"success"
	 * @author 宋海涛
	 * @version  2014年6月3日下午2:43:25
	 * @throws Exception 
	 * @see
	 */
	public Map<String, Object> sendSmsValidate(String SMS_SEND_IDS) throws IcException{
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//短信功能是否开启
			resultMap = outService.messageFunctionIsOpen();
			if(resultMap.size() <= 0){
				Out out = new Out();
				out.setUserId(SMS_SEND_IDS);
				//是否存在可发送短信
				resultMap = outService.isHaveSendMessage(out);
				if (resultMap.size() <= 0) {
					//是否存在无电话号用户
					resultMap = outService.isHaveMobileFacade(out);
					if(resultMap.size()<=0){
						//成功
						resultMap.put(GlobalContext.RESULT_SUCCESS, "success");
					}
				}
			}
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_010"));
			throw se;
		}
		return resultMap;
	}
	
	/**
	 * 方法描述： 发送短信
	 * 
	* @param messages
	 *            参数Map结构： {key:SMS_SEND_IDS,Value:接收人id},
	 *            {key:SMS_TEXT,Value:短信内容}, {key:SMS_SENDTYPE,Value:短信类型},
	 *            {key:SMS_CEATEUSER,Value:发送人}
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version 2014年5月22日上午20:21:33
	 * @throws ValidateException 
	 * @see
	 */
	@Override
	public boolean jobSendSms(Map<String, String> messages) throws IcException{
		Out out = new Out();
		// 用户id用“，”分割
		out.setUserId(messages.get(SMS_SEND_IDS));
		// 短信内容
		out.setText(messages.get(SMS_TEXT));
		// 短信类型
		String sendType = messages.get(SMS_SENDTYPE);
		if (sendType == null || "".equals(sendType)) {
			out.setSendType("sendType_1");
		} else {
			out.setSendType(sendType);
		}
		Map<String, String> mailMap = new HashMap<String, String>();
		mailMap.put(MAIL_SENDER_ID, "1");
		mailMap.put(MAIL_RECEIVER_IDS,messages.get(SMS_CEATEUSER));
		// 发送人
		out.setCreateUser(Long.valueOf(messages.get(SMS_CEATEUSER)).longValue());
		Map<String, Object> resultMap = new HashMap<String, Object>();
		log.info("短信内容:"+messages.get(SMS_TEXT) + new Date().toLocaleString());
		log.info("创建人:"+UserUtils.getUser(out.getCreateUser()));
		try{
			log.info("开始短信效验:" + new Date().toLocaleString());
			//短信功能是否开启
			resultMap = outService.messageFunctionIsOpen();
			if(resultMap.size() <= 0){
				log.info("短信功能开启校验通过:");
				//是否存在可发送短信
				resultMap = outService.isHaveSendMessage(out);
				if (resultMap.size() <= 0) {
					log.info("是否含有可发送短信校验通过:");
					//是否存在无电话号用户
					resultMap = outService.isHaveMobileFacade(out);
					if(resultMap.size()<=0){
						log.info("用户均有电话号:");
						if (outService.sendAndSave(out) == 1) {
							log.info("短信发送成功:");
							return true;
						}
					}else{
						String message = resultMap.get("successMessage").toString() ;
						String[] smessages = message.split("\\?");
						if(smessages.length<=1){
							mailMap.put(MAIL_CONTENT,MessageUtils.getMessage("JC_OA_IC_090")+messages.get(SMS_TEXT));
							mailMap.put(MAIL_SUBJECT,MessageUtils.getMessage("JC_OA_IC_088", new Object[]{messages.get(SMS_TITLE)}));
							log.info(MessageUtils.getMessage("JC_OA_IC_090"));
						}else{
							mailMap.put(MAIL_CONTENT,MessageUtils.getMessage("JC_OA_IC_091")+smessages[1].replaceAll("<br>", "").replaceAll("：", "")+MessageUtils.getMessage("JC_OA_IC_093")+messages.get(SMS_TEXT));
							mailMap.put(MAIL_SUBJECT,MessageUtils.getMessage("JC_OA_IC_089", new Object[]{messages.get(SMS_TITLE)}));
							log.info("部分用户没有手机号:"+smessages[1].replaceAll("<br>", "").replaceAll("：", "") );
							if (outService.sendAndSave(out) == 1) {
								log.info("短信发送成功:");
								this.sendMail(mailMap);
								return true;
							}
						}
					}
				}else{
					mailMap.put(MAIL_CONTENT,MessageUtils.getMessage("JC_OA_IC_091")+resultMap.get("errorMessage").toString().replaceAll("<br>", "")+MessageUtils.getMessage("JC_OA_IC_092")+messages.get(SMS_TEXT));
					mailMap.put(MAIL_SUBJECT,MessageUtils.getMessage("JC_OA_IC_088", new Object[]{messages.get(SMS_TITLE)}));
					log.info("短信发送失败:"+resultMap.get("errorMessage").toString().replaceAll("<br>", ""));
				}
			}else{
				mailMap.put(MAIL_CONTENT,MessageUtils.getMessage("JC_OA_IC_091")+resultMap.get("errorMessage").toString().replaceAll("<br>", "")+MessageUtils.getMessage("JC_OA_IC_092")+messages.get(SMS_TEXT));
				mailMap.put(MAIL_SUBJECT,MessageUtils.getMessage("JC_OA_IC_088", new Object[]{messages.get(SMS_TITLE)}));
				log.info("短信发送失败:"+resultMap.get("errorMessage").toString().replaceAll("<br>", ""));
			}
			this.sendMail(mailMap);
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_010"));
			throw se;
		}
		return false;
	}
	
	
	/**
	 * 方法描述：发短信校验用户中是否有存在没有电话号的情况,并且校验该用户是否有发送短信条数，提示内容为小于70字，用于传发送人,即发送人不是当前登录用户
	 * @param SMS_SEND_IDS 用户id以“，”分割的字符串
	 * @return resultMap 
	 * @author 宋海涛
	 * @version  2014年6月3日下午2:43:25
	 * @throws IcException 
	 * @see
	 */
	@Override
	public Map<String, Object> sendSmsValidateParam(String SMS_SEND_IDS,
			String SMS_CEATEUSER) throws IcException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try{
			//短信功能是否开启
			resultMap = outService.messageFunctionIsOpen();
			if(resultMap.size() <= 0){
				Out out = new Out();
				out.setUserId(SMS_SEND_IDS);
				out.setCreateUser(Long.parseLong(SMS_CEATEUSER));
				//是否存在可发送短信
				resultMap = outService.isHaveSendMessage(out);
				if (resultMap.size() <= 0) {
					//是否存在无电话号用户
					resultMap = outService.isHaveMobileFacade(out);
					if(resultMap.size()<=0){
						//成功
						resultMap.put(GlobalContext.RESULT_SUCCESS, "success");
					}
				}
			}
		}catch(Exception e){
			IcException se = new IcException(e);
			se.setLogMsg(MessageUtils.getMessage("JC_OA_IC_010"));
			throw se;
		}
		return resultMap;
	}
	
	/**
	 * 方法描述：发邮件接口
	 * 
	 * @param paramMap
	 *            参数Map结构：
	 *            {key:MAIL_SENDER_ID,  Value:发送人用户id},
	 *            {key:MAIL_RECEIVER_IDS ,  Value:收件人用户id数组的字符串表现形式，如：｛1，2，3，4｝},
	 *            {key:MAIL_SUBJECT,  Value:邮件主题}, 
	 *            {key:MAIL_CONTENT,  Value:邮件正文},
	 *            {key:MAIL_FILEIDS,  Value:附件Id列表}
	 * @return Boolean true:成功，false:失败
	 * @throws CustomException
	 * @author zhanglg
	 * @version 2014年5月22日下午8:38:11
	 * @see
	 */
	@Override
	public boolean sendMail(Map<String, String> paramMap)
			throws CustomException {
		Long senderId = new Long(paramMap.get(MAIL_SENDER_ID));
		String receiverStr = paramMap.get(MAIL_RECEIVER_IDS);

		String subject = (String) paramMap.get(MAIL_SUBJECT);
		String content = (String) paramMap.get(MAIL_CONTENT);

		Mail mail = new Mail();

		mail.setMailboxId(Constants.IC_MAIL_MAILBOX_INNER);
		mail.setSenderUserId(senderId);
		mail.setMailTitle(subject);
		mail.setMailContent(content);
		mail.setTo(receiverStr);

	 	log.info("发件人ID ："+senderId);
        log.info("接收人ID ： "+receiverStr);
        log.info("主题  ："+subject);
        log.info("内容  ："+content);
        log.info("开始发送邮件");
		Integer result = mailService.sendMail(mail);
		if (result == 1) {
			log.info("邮件发送成功");
			return true;
		} else {
			log.info("邮件发送失败");
			return false;
		}

	}


	
}
