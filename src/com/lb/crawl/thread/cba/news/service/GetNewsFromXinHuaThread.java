package com.lb.crawl.thread.cba.news.service;

import com.lb.craw.cba.GetCBANewsByXinHua;
 

public class GetNewsFromXinHuaThread  extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=2;i++){
			 
			GetCBANewsByXinHua.getNewsList("http://www.news.cn/sports/cba.htm");
			try {		
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入新华网cba新闻：将停止2小时,当前i为：－－"+i+",i总共为2，每2小时运行一次，运行4小时");
				this.sleep(7200000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
