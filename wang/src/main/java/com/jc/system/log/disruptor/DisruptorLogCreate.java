package com.jc.system.log.disruptor;

import java.util.List;

import com.jc.system.log.domain.LogBean;
import com.lmax.disruptor.RingBuffer;

/**
 * @description: 日志生产者类
 */
public class DisruptorLogCreate {
	private RingBuffer<LogEvent> ringbuffer = null;

	public DisruptorLogCreate(RingBuffer<LogEvent> rb) {
		ringbuffer = rb;
	}

	public void log(List<LogBean> list) {
		long sequence = ringbuffer.next();
		ringbuffer.get(sequence).setLogList(list);
		ringbuffer.publish(sequence);
	}
}
