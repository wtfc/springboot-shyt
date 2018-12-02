package com.jc.system.common.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPool {
/*
 * 1、固定大小线程池

import java.util.concurrent.Executors;  
import java.util.concurrent.ExecutorService;

ExecutorService pool = Executors.newFixedThreadPool(2);

pool.execute(t1);

pool.shutdown();

2、单任务线程池

ExecutorService pool = Executors.newSingleThreadExecutor();

3、可变尺寸线程池

ExecutorService pool = Executors.newCachedThreadPool();

4、延迟连接池

import java.util.concurrent.Executors;  
import java.util.concurrent.ScheduledExecutorService;  
import java.util.concurrent.TimeUnit;

ScheduledExecutorService pool = Executors.newScheduledThreadPool(2);

pool.schedule(t4, 10, TimeUnit.MILLISECONDS);

5、单任务延迟连接池

ScheduledExecutorService pool = Executors.newSingleThreadScheduledExecutor();
 * 
 * */
	public static ExecutorService pool = null;
	public static ExecutorService getThreadPool() {
		if(ThreadPool.pool == null) {
			pool = Executors.newCachedThreadPool();
		}
		return pool;
	}
}
