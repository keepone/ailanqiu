package com.lb.crawl.thread.nba.news.service;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;

public class SinaNewsThread extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=30;i++){
			GetNBANewsFromSina.getNewsBySina("http://sports.sina.com.cn/nba/");
			try {		
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入新浪网nba新闻：将停止30分钟,当前i为：－－"+i+",i总共为30，每10分钟运行一次，运行5小时");
				this.sleep(600000);
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入新浪网nba新闻结束停止：");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
