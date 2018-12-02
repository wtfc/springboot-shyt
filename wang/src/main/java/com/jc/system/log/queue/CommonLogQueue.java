package com.jc.system.log.queue;

import java.util.LinkedList;
import java.util.Queue;

import com.jc.system.log.domain.LogBean;

/**
 * @description: 普通用户日志队列
 * @created: 2013-10-29 下午2:26:38
 * @version：$Id: CommonLogQueue.java 40809 2014-04-03 18:12:35Z sunjf $
 */
public class CommonLogQueue {
	private static Queue<LogBean> queue = new LinkedList<LogBean>();

	/**
	 * @description 获得的激烈的长度
	 */
	public static Integer getQueueSize() {
		return queue.size();
	}

	/**
	 * @description 从队列头部取出内容
	 */
	public static LogBean poll() {
		return queue.poll();
	}

	/**
	 * @description 在队列尾部增加内容
	 */
	public static void add(LogBean logBean) {
		queue.add(logBean);
	}
}
