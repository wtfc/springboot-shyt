package com.jc.system.log.disruptor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.lmax.disruptor.BatchEventProcessor;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.SequenceBarrier;
import com.lmax.disruptor.YieldingWaitStrategy;

public class DisruptorContext {
	private static final int BUFFER_SIZE = 1024 * 8;
	private static final RingBuffer<LogEvent> ringBuffer = RingBuffer
			.createSingleProducer(LogEvent.EVENT_FACTORY, BUFFER_SIZE,
					new YieldingWaitStrategy());
	private static final SequenceBarrier sequenceBarrier = ringBuffer
			.newBarrier();
	private static final LogEventHandler handler = new LogEventHandler();
	private static final ExecutorService EXECUTOR = Executors
			.newSingleThreadExecutor();
	private static final BatchEventProcessor<LogEvent> batchEventProcessor = new BatchEventProcessor<LogEvent>(
			ringBuffer, sequenceBarrier, handler);

	public static RingBuffer<LogEvent> getRingBuffer() {
		return ringBuffer;
	}

	public static void startLogExecutor() {
		EXECUTOR.submit(batchEventProcessor);
	}
}
