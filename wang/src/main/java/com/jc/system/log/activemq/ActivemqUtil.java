package com.jc.system.log.activemq;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

import com.jc.system.common.util.PropertiesUtil;
import com.jc.system.log.domain.LogBean;

/**
 * @description: activeMq工具类
 * @created: 2013-10-25 下午2:08:47
 */
public class ActivemqUtil {
	private static Logger logger = Logger.getLogger(ActivemqUtil.class);

	private static ConnectionFactory connectionFactory = null;
	private static Map<String, String> paraMap;
	static {
		paraMap = PropertiesUtil.getProperties("log_activemq.properties");
		String username = paraMap.get("username");
		String password = paraMap.get("password");
		String ip = paraMap.get("ip");
		connectionFactory = new ActiveMQConnectionFactory(username, password,
				ip);
	}

	/**
	 * @description 获得activemq连接
	 */
	public static Connection createConnection() {
		try {
			Connection connection = connectionFactory.createConnection();
			return connection;
		} catch (JMSException e) {
			logger.error("获取Connection失败", e);
		}
		return null;
	}

	/**
	 * @description 发布消息
	 * @param object
	 *            发布消息的bean
	 * @param destinationStr
	 *            目的地的str
	 */
	public static boolean sendMessage(LogBean object, String destinationStr) {
		Connection connection = createConnection();
		try {
			connection.start();
			Session session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			Destination destination = session.createQueue(destinationStr);
			MessageProducer producer = session.createProducer(destination);
			if ("1".equals(paraMap.get("persistant"))) {
				producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			} else {
				producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			}
			ObjectMessage objectMessage = session.createObjectMessage();
			objectMessage.setObject(object);
			producer.send(objectMessage);
			session.commit();
			session.close();
			connection.stop();
			connection.close();
		} catch (Exception e) {
			logger.error(e);
			return false;
		}
		return true;
	}
}
