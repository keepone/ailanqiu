package com.lb.crawl.thread.cba.news.service;
import com.lb.craw.cba.GetCBANewsFromSoHu;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromSoHu;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;

public class GetNewsFromSoHuThread extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=8;i++){
			GetCBANewsFromSoHu.getNewsList_2("http://sports.sohu.com/1/1102/39/subject204253982.shtml");//NBA动态新闻
			GetCBANewsFromSoHu.getNewsList_2("http://cbachina.sports.sohu.com/s2011/2671/s309755607/");//CBA媒体评论
			try {		
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入搜狐网nba新闻：将停止30分钟,当前i为：－－"+i+",i总共为8，每30分钟运行一次，运行4小时");
				this.sleep(1800000);
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入新浪网nba新闻结束停止：");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
