package com.lb.crawl.thread.cba.news.service;

import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetEuropeBasketball;


public class GetEuropeNewsFromSouHuThread  extends Thread {

	@Override
	public void run() {
		for(int i=1;i<=1;i++){
			System.out.println("从搜狐获取欧洲篮球新闻");
			GetEuropeBasketball.getNewsList("http://sports.sohu.com/s2014/1565/s395217589/");
			try {		
				//System.out.println(Thread.currentThread().getName()+"线程开始停止录入搜狐欧洲篮球新闻：将停止2小时,当前i为：－－"+i+",i总共为2，每2小时运行一次，运行4小时");
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
