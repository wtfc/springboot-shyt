package com.jc.system.log.receiver;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.apache.log4j.Logger;

import com.jc.system.log.activemq.ActivemqUtil;

/**
 * @description: 日志接收基类
 * @created: 2013-10-25 下午3:52:36
 * @version：$Id: ActiveMqLogReceiver.java 40809 2014-04-03 18:12:35Z sunjf $
 *            Rights Reserved.
 */
public class ActiveMqLogReceiver {
	protected Logger logger = Logger.getLogger(this.getClass());
	protected MessageConsumer consumer = null;
	protected Connection connection;
	protected Session session;

	public ActiveMqLogReceiver(String receiveQueue) {
		connection = ActivemqUtil.createConnection();
		try {
			connection.start();
			session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(receiveQueue);
			consumer = session.createConsumer(destination);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
