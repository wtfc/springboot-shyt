package com.jc.system.log.receiver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import com.jc.system.log.domain.LogBean;

/**
 * @description: 普通本地日志记录
 * @created: 2013-10-28 上午9:30:10
 * @version：$Id: CommonLogReciver.java 40809 2014-04-03 18:12:35Z sunjf $
 */
public class CommonLogReciver extends ActiveMqLogReceiver {
	private static final String DESTINATION_STR = "commonLog";

	public CommonLogReciver() {
		super(DESTINATION_STR);
	}

	public LogBean reciveObject() {
		LogBean bean = null;
		try {
			Message message = (Message) consumer.receive();
			if (message == null) {
				logger.info("获得的消息为空");
				return null;
			}
			ObjectMessage objMessgae = (ObjectMessage) message;
			bean = (LogBean) objMessgae.getObject();
			session.commit();
			session.close();
			connection.stop();
			connection.close();
		} catch (JMSException e) {
			logger.error(e);
		}
		return bean;
	}
}
