package com.lb.crawl.thread.nba.news.service;

import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;

public class ZhiBoBaThread extends Thread {

	@Override
	public void run() {
		//for(int i=1;i<=8;i++){   //8小时
			GetNBANewsFromZhiBoBa.getNewsList("http://news.zhibo8.cc/nba");
			try {
				//System.out.println(Thread.currentThread().getName()+"开始停止录入直播吧新闻：停止时间30分钟,当前i为：－－"+i+",i总共为8，每30分钟运行一次，运行4小时");
				this.sleep(10);
				System.out.println(Thread.currentThread().getName()+"录入直播吧新闻结束停止：");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//}
	}

}
