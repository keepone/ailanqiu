package com.lb.crawl.thread.nba.video.service;

import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBAVideoFromHuPu;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lucene.crawl.GetGameResult;

public class EveryDayVideoFromHuPuThread extends Thread {

	@Override
	public void run() {
		for(int i=1;i<=24;i++){   //8小时
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/game");//比赛
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/Cavaliers"); //骑士
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/lakers"); //湖人
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/heat"); //热火
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/rockets"); //火箭
			GetNBAVideoFromHuPu.addTouTiao("http://v.opahnet.com/nba"); //首页推荐
			
			try {
				System.out.println(Thread.currentThread().getName()+"开始停止录入虎扑比赛视频：停止时间30分钟,当前i为：－－"+i+",i总共为8，每10分钟运行一次，运行4小时");
				this.sleep(600000);
			} catch (InterruptedException e) {
				 
			}
			
		}
	}

}
