package com.lb.crawl.thread.nba.news.service;

import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lb.crawl.nba.GetNewsFromTBBA;

public class TBBANews extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=2;i++){
			GetNewsFromTBBA.getNewsList("http://www.tbba.com.cn/");
			try {		
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入TBBA新闻：将停止2小时,当前i为：－－"+i+",i总共为2，每2小时运行一次，运行4小时");
				this.sleep(7200000);
				System.out.println(Thread.currentThread().getName()+"线程录入TBBA新闻结束停止：");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
