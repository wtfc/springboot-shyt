package com.jc.oa.ic.facade;

import java.util.Map;

import com.jc.oa.ic.IcException;
import com.jc.oa.ic.ValidateException;
import com.jc.system.CustomException;

/**
 * @title GOA V2.0
 * @description 互动交流门面服务类
 * Copyright (c) 2014 Jiacheng.com Inc. All Rights Reserved
 * Company 长春嘉诚网络工程有限公司
 * @author 宋海涛
 * @version  2014年5月22日上午20:21:33
 */
public interface IInteractFacadeService {
	public static final String MAIL_SENDER_ID="senderId";
	public static final String MAIL_RECEIVER_IDS="receiverIds";
	public static final String MAIL_SUBJECT="subject";
	public static final String MAIL_CONTENT="content";
	public static final String MAIL_FILEIDS ="fileids";
	
	
	public static final String SMS_SEND_IDS="ids";
	public static final String SMS_TEXT="text";
	public static final String SMS_SENDTYPE="sendType";
	public static final String SMS_CEATEUSER="createUser";
	public static final String SMS_TITLE="mesTitle";
	
	/**
	 * 方法描述： 发短信
	 * @param messages 参数Map结构： {key:IInteractFacadeService.SMS_SEND_IDS,Value:接收人id},
	 * 							 {key:IInteractFacadeService.SMS_TEXT,Value:短信内容},	
	 * 							 {key:IInteractFacadeService.SMS_SENDTYPE,Value:短信类型}
	 * 							    如：key：sendType_1，value： 流程，key：sendType_2，value： 会议,	
	 * 								key：sendType_3，value： 通知，key：sendType_4，value：其他
	 * 							 {key:IInteractFacadeService.SMS_CEATEUSER,Value:发送人}	
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日下午2:14:53
	 * @throws ValidateException 
	 * @see
	 */
	public boolean sendSms(Map<String, String> messages) throws IcException;
	
	/**
	 * 方法描述：发短信校验用户中是否有存在没有电话号的情况,并且校验该用户是否有发送短信条数，提示内容为小于70字
	 * @param SMS_SEND_IDS 用户id以“，”分割的字符串
	 * @return resultMap 
	 * @author 宋海涛
	 * @version  2014年6月3日下午2:43:25
	 * @throws IcException 
	 * @see
	 */
	public Map<String, Object> sendSmsValidate(String SMS_SEND_IDS) throws IcException;
	
	/**
	 * 方法描述：发短信校验用户中是否有存在没有电话号的情况,并且校验该用户是否有发送短信条数，提示内容为小于70字，用于传发送人
	 * @param SMS_SEND_IDS 用户id以“，”分割的字符串
	 * @return resultMap 
	 * @author 宋海涛
	 * @version  2014年6月3日下午2:43:25
	 * @throws IcException 
	 * @see
	 */
	public Map<String, Object> sendSmsValidateParam(String SMS_SEND_IDS,String SMS_CEATEUSER) throws IcException;
	
	/**
	 * 方法描述： 定时发送
	 * @param messages 参数Map结构： {key:IInteractFacadeService.SMS_SEND_IDS,Value:发送人用户id},
	 * 							 {key:IInteractFacadeService.SMS_TEXT,Value:短信内容},	
	 * 							 {key:IInteractFacadeService.SMS_TITLE,Value:短信标题},	
	 * 							 {key:IInteractFacadeService.SMS_SENDTYPE,Value:短信类型}
	 * 							    如：key：sendType_1，value： 流程，key：sendType_2，value： 会议,	
	 * 								key：sendType_3，value： 通知，key：sendType_4，value：其他
	 * 							 {key:IInteractFacadeService.SMS_CEATEUSER,Value:创建人}	
	 * @return
	 * @throws Exception
	 * @author 宋海涛
	 * @version  2014年5月15日下午2:14:53
	 * @throws ValidateException 
	 * @see
	 */
	public boolean jobSendSms(Map<String, String> messages) throws IcException;
	
	
	/**
	 * 方法描述：发邮件接口
	 * @param paramMap 参数Map结构：{key:IInteractFacadeService.MAIL_SENDER_ID,Value:发送人用户id},
	 * 														{key:IInteractFacadeService.MAIL_RECEIVER_IDS,Value:收件人用户id数组},	
	 * 														{key:IInteractFacadeService.MAIL_SUBJECT,Value:邮件主题},	
	 * 														{key:IInteractFacadeService.MAIL_CONTENT,Value:邮件正文},	
	 * 
	 * @return Boolean true:成功，false:失败		
	 * @throws CustomException
	 * @author zhanglg
	 * @version  2014年5月22日下午8:38:11
	 * @see
	 */
	public boolean sendMail(Map<String,String> paramMap) throws CustomException;
}
