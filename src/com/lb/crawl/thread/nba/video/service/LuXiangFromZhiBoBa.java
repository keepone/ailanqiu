package com.lb.crawl.thread.nba.video.service;

import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lucene.crawl.GetGameResult;

public class LuXiangFromZhiBoBa extends Thread {

	@Override
	public void run() {
		
		
	System.out.println("从直播吧录入比赛录像：............................当前线程："+Thread.currentThread().getName());
		for(int i=1;i<=8;i++){   //8小时
			GetLuXiangFromZhiBoBa.getNBALuXiang("http://www.zhibo8.cc/nba/luxiang.htm");
			try {
				System.out.println(Thread.currentThread().getName()+"开始停止录入比赛录像：停止时间30分钟,当前i为：－－"+i+",i总共为8，每30分钟运行一次，运行4小时");
				this.sleep(1800000);
				System.out.println(Thread.currentThread().getName()+"录入比赛录像结束停止：");
			} catch (InterruptedException e) {
				 
			}
			
		}
	}

}
