package com.lb.crawl.thread.nba.video.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.crawl.thread.nba.news.service.ZhiBoBaThread;
import com.lb.crawl.thread.nba.video.service.EveryDayVideoFromSinaThread;
import com.lb.crawl.thread.nba.video.service.LuXiangFromZhiBoBa;
import com.lb.crawl.thread.nba.video.service.NBAMatchResultService;
@Service("EveryDayVideoFromSinaThreadImpl")
@Component
public class EveryDayVideoFromSinaThreadImpl {

 
	
	@Scheduled(cron="0 12 12 * * ?")
	public static void add(){
			EveryDayVideoFromSinaThread every=new EveryDayVideoFromSinaThread();
			Thread thread=new Thread(every);
			thread.start();
			
			NBAMatchResultService nba=new NBAMatchResultService();
			Thread nba_thread=new Thread(nba);
			nba_thread.start();
			
			LuXiangFromZhiBoBa luxiang=new LuXiangFromZhiBoBa();
			Thread lx_thread=new Thread(luxiang);
			lx_thread.start();
	}
	
//	@Scheduled(cron="0 47 11 * * ?")
//	public static void add2(){
//		NBAMatchResultService nba=new NBAMatchResultService();
//			Thread thread=new Thread(nba);
//			//Thread thread2=new Thread(every);
//				//System.out.println("录入每日视频");
//				thread.start();
//				//thread2.start();
//	}
//	@Scheduled(cron="0 47 11 * * ?")
//	public static void add3(){
//		LuXiangFromZhiBoBa luxiang=new LuXiangFromZhiBoBa();
//			Thread thread=new Thread(luxiang);
//			//Thread thread2=new Thread(every);
//				//System.out.println("录入每日视频");
//				thread.start();
//				//thread2.start();
//	}
}
