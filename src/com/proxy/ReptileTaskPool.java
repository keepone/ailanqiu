package com.proxy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/*
 * 操作任务Task时，用此线程池
 */
public class ReptileTaskPool {

	/**
	 * 生成单例类
	 * 
	 * @return
	 */
	private static ReptileTaskPool instance = new ReptileTaskPool();

	private static final int corePoolSize = 20;
	private static final int maximumPoolSize = 100;
	private static BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<Runnable>(maximumPoolSize);
	private static ThreadFactory factory = new ThreadFactory() {
		public Thread newThread(Runnable r) {
			Thread t = new Thread(r);
			return t;
		}
	};

	private static ThreadPoolExecutor executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, 24,
			TimeUnit.HOURS, workQueue, factory);

	// private ThreadPoolExecutor executor = new
	// ThreadPoolExecutor(corePoolSize, maximumPoolSize, 60, TimeUnit.SECONDS,
	// workQueue, handler);

	public static synchronized ReptileTaskPool sharedInstance() {
		synchronized (ReptileTaskPool.class) {
			if (instance == null) {
				instance = new ReptileTaskPool();
			}
			return instance;
		}
	}

	public void doRun(Runnable task) {
		executor.execute(task);
	}

	public ThreadPoolExecutor getExecutor() {
		return executor;
	}
}