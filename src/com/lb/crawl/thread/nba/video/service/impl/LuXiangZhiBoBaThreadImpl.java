package com.lb.crawl.thread.nba.video.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.crawl.thread.nba.news.service.ZhiBoBaThread;
import com.lb.crawl.thread.nba.video.service.LuXiangFromZhiBoBa;
@Service("NBALuXiangThread")
@Component
public class LuXiangZhiBoBaThreadImpl {

	private static LuXiangFromZhiBoBa lx=new LuXiangFromZhiBoBa();
	
	//@Scheduled(cron="0 45 13 * * ?")
	public static void add(){
		for(int i=1;i<=1700;i++){
			Thread thread=new Thread(lx,"nbalx"+i);
			try {
				
				thread.sleep(1800000);  //半小时跑一次
				thread.start();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
}
