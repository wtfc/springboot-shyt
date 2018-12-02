package com.jc.system.log.receiver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;

import com.jc.system.log.domain.LogBean;

public class ManagerLogReciver extends ActiveMqLogReceiver {

	private static final String DESTINATION_STR = "managerLog";

	public ManagerLogReciver() {
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
			connection.close();
		} catch (JMSException e) {
			logger.error(e);
		}
		return bean;
	}
}
