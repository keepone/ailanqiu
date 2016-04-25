package com.lb.crawl.thread.cba.news.service;

import com.lucene.crawl.GetCBANewsBySina;


public class GetNewsFromSinaThread  extends Thread {

	@Override
	public void run() {
		for(int i=1;i<=2;i++){
			String startURL = "http://roll.sports.sina.com.cn/s_cba_all/index.shtml";
			GetCBANewsBySina.getNewsList("http://roll.sports.sina.com.cn/s_cba_all/index.shtml");
			try {		
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入新浪网cba新闻：将停止2小时,当前i为：－－"+i+",i总共为2，每2小时运行一次，运行4小时");
				this.sleep(7200000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
