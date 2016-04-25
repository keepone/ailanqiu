package com.lb.crawl.thread.cba.news.service;

import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetEuropeBasketball;
import com.lucene.crawl.GetWCBANews;


public class GetWcbaNewsFromQQThread  extends Thread {

	@Override
	public void run() {
		for(int i=1;i<=1;i++){
			System.out.println("从腾讯获取wcba新闻");
			GetWCBANews.getNewsList("http://sports.qq.com/l/cba/wcba/list20110816174202.htm");
			try {		
				//System.out.println(Thread.currentThread().getName()+"线程开始停止录入腾讯网wcba篮球新闻：将停止2小时,当前i为：－－"+i+",i总共为2，每2小时运行一次，运行4小时");
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
