package com.proxy;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ShareQueue {
	public static final BlockingQueue<String> brandQueue = new ArrayBlockingQueue<String>(100000);
	public static final BlockingQueue<String> ipWrite = new ArrayBlockingQueue<String>(100000);

}
