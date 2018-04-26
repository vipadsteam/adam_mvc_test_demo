/**
 * 
 */
package org.adam.asyn.web.common;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author USER
 *
 */
public class TestThreadPool {

	private AtomicInteger dbExecutorCounter = new AtomicInteger();

	private static TestThreadPool tp;

	private ThreadPoolExecutor threadPoolExecutor;

	public TestThreadPool() {
		super();
	}

	public static TestThreadPool instance() {
		if (null == tp) {
			tp = new TestThreadPool();
			tp.init();
		}
		return tp;
	}
	

	private synchronized void init() {
		threadPoolExecutor = new ThreadPoolExecutor(
				// 50条核心线程
				50,
				// max 1000
				200,
				// 30秒空闲回收
				30, TimeUnit.SECONDS,
				// 1000的队列
				new LinkedBlockingDeque<>(1000), r -> new Thread(r, "DBExecutor-" + dbExecutorCounter.getAndIncrement()));
	}

	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

}
