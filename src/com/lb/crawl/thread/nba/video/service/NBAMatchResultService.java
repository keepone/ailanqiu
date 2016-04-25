package com.lb.crawl.thread.nba.video.service;

import com.lucene.crawl.GetGameResult;

public class NBAMatchResultService extends Thread {

	
	public void run() {
		System.out.println("从新浪录入比赛结果：............................当前线程："+Thread.currentThread().getName());	
		//this.sleep(2000);
		for(int i=1;i<=240;i++){
			boolean flag=GetGameResult.getGamResult("http://nba.sports.sina.com.cn/match_result.php");
			if(!flag){
				i=1000;
			}
			try {
				System.out.println(Thread.currentThread().getName()+"开始停止录入比赛结果：停止时间60秒,当前i为："+i+", i总共为240，每1分钟运行一次，运行240分钟");
				this.sleep(60000);
				System.out.println(Thread.currentThread().getName()+"录入比赛结果结束停止：");
			} catch (InterruptedException e) {
				System.out.println("录入nba新浪视频出错");
			}
			
		}
	}

}
