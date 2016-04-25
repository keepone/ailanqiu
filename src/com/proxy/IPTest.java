package com.proxy;

import java.util.concurrent.TimeUnit;

public class IPTest implements Runnable {
	public final static String USERAGENT = "Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; GTB5; .NET CLR 2.0.50727; CIBA)";

	public void run() {
		while (true) {
			String url = null;
			try {
				url = ShareQueue.brandQueue.poll(2, TimeUnit.SECONDS); // 读取队列
				if (url == null) {
					System.out.println("�?��:"
							+ Thread.currentThread().toString());
					break;
				}
				String[] hostAndPort = url.split(":");
				String host = hostAndPort[0];
				String port = hostAndPort[1];

				NetworkAccess.getHttpRequest("http://sports.sina.com.cn/nba/", host,Integer.parseInt(port), USERAGENT);
			} catch (Exception e) {
				System.out.println("IP blockingQueue size====="+ShareQueue.brandQueue.size());
				System.out.println("IP url====="+url);
				continue;
			}
			if (url != null){
				Util.write(url+":::");
			}
		}
	}

}
