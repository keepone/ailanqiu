package com.lb.time.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lucene.crawl.GetGameResult;

@Service("NBAMatchResutlTime")
@Component
public class NBAMatchResutlTime {
	@Scheduled(cron="0 25 22 * * ?")
	public void go(){
		//System.out.println("从新浪录入比赛结果：............................当前线程："+Thread.currentThread().getName());	
		//this.sleep(2000);
		for(int i=1;i<=10;i++){
			GetGameResult.getGamResult("http://nba.sports.sina.com.cn/match_result.php");
			try {
				if(i>12){
					throw new IllegalArgumentException("make a fault");
					//System.out.println("ff");
				}
				//System.out.println(Thread.currentThread().getName()+"定时器开始停止录入比赛结果：停止时间60秒,当前i为："+i+", i总共为270，每1分钟运行一次，运行260分钟");
				Thread.currentThread().sleep(60000);
				//System.out.println(Thread.currentThread().getName()+"录入比赛结果结束停止：");
			} catch (InterruptedException e) {
				System.out.println("录入nba新浪视频出错");
			}
			
		}
	}
}
