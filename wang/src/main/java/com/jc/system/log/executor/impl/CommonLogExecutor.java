package com.jc.system.log.executor.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;

import com.jc.system.common.util.GlobalContext;
import com.jc.system.log.domain.LogBean;
import com.jc.system.log.executor.LogExecutor;
import com.jc.system.log.receiver.CommonLogReciver;

/**
 * @description: 普通本地日志线程执行类
 * @author：孙圣然
 * @created: 2013-10-25 下午4:06:54
 * @version：$Id: CommonLogExecutor.java 40809 2014-04-03 18:12:35Z sunjf $
 */
public class CommonLogExecutor extends LogExecutor {
	Queue<LogBean> logQueue = new LinkedList<LogBean>();
	private long scheduleTime = GlobalContext.COMMON_LOG_TIME;
	private int logNum = GlobalContext.COMMON_LOG_NUM;
	private Timer timer = new Timer(); // 定时器
	private CommonLogTask task = new CommonLogTask();
	boolean mainOperFlag = false; // 主线程执行标志
	boolean timerFlag = false; // 定时器执行标志

	public void run() {
		log.info("开始activemq普通日志监听");
		List<LogBean> logListTemp = null; // 临时数组
		// 设置定时器
		timer.schedule(task, scheduleTime, scheduleTime);
		while (true) {
			logListTemp = new ArrayList<LogBean>();
			while (logQueue.size() < logNum) {
				CommonLogReciver receive = new CommonLogReciver();
				LogBean bean = receive.reciveObject();
				logQueue.add(bean);
			}
			if (timerFlag != true && logQueue.size() > 0) {
				mainOperFlag = true;
				timer.cancel();
				while (logQueue.size() > 0) {
					logListTemp.add(logQueue.poll());
				}
				InsertLogThread insertThread = new InsertLogThread();
				insertThread.setList(logListTemp);
				insertThread.run();
				timer = new Timer();
				task = new CommonLogTask();
				timer.schedule(task, scheduleTime, scheduleTime);
				mainOperFlag = false;
			}
		}
	}

	/**
	 * @description: 操作日志定时任务
	 * @author：孙圣然
	 * @created: 2013-10-28 上午10:29:54
	 * @version：$Id: CommonLogExecutor.java 40809 2014-04-03 18:12:35Z sunjf $
	 * @copyright ©1995-2013 Changchun Jiacheng Project of the Network
	 *            Co.,ltd.All Rights Reserved.
	 */
	public class CommonLogTask extends TimerTask {
		List<LogBean> logListTemp = null;

		@Override
		public void run() {
			if (mainOperFlag == false) {
				timerFlag = true;
				if (logQueue.size() > 0) {
					logListTemp = new ArrayList<LogBean>();
					while (logQueue.size() > 0) {
						logListTemp.add(logQueue.poll());
					}
					InsertLogThread insertThread = new InsertLogThread();
					insertThread.setList(logListTemp);
					insertThread.run();
				}
				timerFlag = false;
			}
		}
	}

}
