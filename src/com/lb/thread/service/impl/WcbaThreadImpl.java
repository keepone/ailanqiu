package com.lb.thread.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.crawl.thread.cba.news.service.GetWcbaNewsFromQQThread;
import com.lb.crawl.thread.cba.video.service.CBAMatchResultService;
import com.lb.crawl.thread.nba.news.service.NBA98News;
import com.lb.crawl.thread.nba.news.service.NBACommentFromNBAChinaThread;
import com.lb.crawl.thread.nba.news.service.TBBANews;
import com.lb.crawl.thread.nba.news.service.ZhiBoBaThread;
import com.lb.crawl.thread.nba.video.service.EveryDayVideoFromSinaThread;
import com.lb.crawl.thread.nba.video.service.LuXiangFromZhiBoBa;
import com.lb.crawl.thread.nba.video.service.NBAMatchResultService;
@Service("WcbaThreadImpl")
@Component
public class WcbaThreadImpl {

 /**
  * 线程执行规则：为防止服务器出现宕机。[每个任务将在上下午各执行一次]
  */
	
	@Scheduled(cron="0 48 15 * * ?")
	public static void begin(){
			 GetWcbaNewsFromQQThread wcba=new GetWcbaNewsFromQQThread();
			 Thread wcba_news=new Thread(wcba,"wcba_news");
			 wcba_news.start();
	}
	

	 
}
