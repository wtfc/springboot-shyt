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
import com.jc.system.log.queue.CommonLogQueue;

/**
 * @description: 普通本地日志线程执行类
 * @created: 2013-10-25 下午4:06:54
 */
public class CommonLocalLogExecutor extends LogExecutor {

	Queue<LogBean> logQueue = new LinkedList<LogBean>();
	private long scheduleTime = GlobalContext.COMMON_LOG_TIME;
	private int logNum = GlobalContext.COMMON_LOG_NUM;
	private Timer timer = new Timer(); // 定时器
	private CommonLogTask task = new CommonLogTask();
	boolean mainOperFlag = false; // 主线程执行标志
	boolean timerFlag = false; // 定时器执行标志

	public void run() {
		log.info("开始本地普通日志监听器");
		// 判断是否启动disruptor
		if (GlobalContext.USER_DSIRUPTOR == 1) {
			LogExecutor executor = new CommonLocalDisruptorLogExecutor();
			executor.run();
		}
		// 设置定时器
		timer.schedule(task, scheduleTime, scheduleTime);
	}

	/**
	 * @description: 操作日志定时任务
	 * @author：孙圣然
	 * @created: 2013-10-28 上午10:29:54
	 * @version：$Id: CommonLocalLogExecutor.java 40770 2014-04-03 13:38:29Z
	 *               sunjf $
	 * @copyright ©1995-2013 Changchun Jiacheng Project of the Network
	 *            Co.,ltd.All Rights Reserved.
	 */
	public class CommonLogTask extends TimerTask {
		@Override
		public void run() {
			if (CommonLogQueue.getQueueSize() > 0) {
				List<LogBean> logList = new ArrayList<LogBean>();
				while (CommonLogQueue.getQueueSize() > 0) {
					logList.add(CommonLogQueue.poll());
				}
				List<LogBean> logListTemp = new ArrayList<LogBean>();
				for (int i = 0; i < logList.size(); i++) {
					if (logListTemp.size() >= logNum) {
						InsertLogThread insertThread = new InsertLogThread();
						insertThread.setList(logListTemp);
						insertThread.run();
						logListTemp = new ArrayList<LogBean>();
					}
					logListTemp.add(logList.get(i));
				}
				if (logListTemp.size() > 0) {
					InsertLogThread insertThread = new InsertLogThread();
					insertThread.setList(logListTemp);
					insertThread.run();
				}
			}
		}
	}

}
