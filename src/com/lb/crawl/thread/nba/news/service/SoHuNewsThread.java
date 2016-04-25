package com.lb.crawl.thread.nba.news.service;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromSoHu;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;

public class SoHuNewsThread extends Thread {

	@Override
	public void run() {
		
		//for(int i=1;i<=8;i++){
			GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/1/1102/39/subject204253982.shtml");//NBA动态新闻
			GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/1/1102/34/subject204253472.shtml");//火箭新闻
			GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/s2014/lebron-james/index.shtml");//骑士新闻
			GetNBANewsFromSoHu.getNewsList_2("http://sports.sohu.com/nba_a_932.shtml");//NBA新闻
			try {		
				//System.out.println(Thread.currentThread().getName()+"线程开始停止录入搜狐网nba新闻：将停止30分钟,当前i为：－－"+i+",i总共为8，每30分钟运行一次，运行4小时");
				this.sleep(10);
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入新浪网nba新闻结束停止：");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		//}
	}

}
