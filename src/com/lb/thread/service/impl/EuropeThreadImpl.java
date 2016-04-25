package com.lb.thread.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.crawl.thread.cba.news.service.GetEuropeNewsFromSouHuThread;
import com.lb.crawl.thread.cba.video.service.CBAMatchResultService;
import com.lb.crawl.thread.nba.news.service.NBA98News;
import com.lb.crawl.thread.nba.news.service.NBACommentFromNBAChinaThread;
import com.lb.crawl.thread.nba.news.service.TBBANews;
import com.lb.crawl.thread.nba.news.service.ZhiBoBaThread;
import com.lb.crawl.thread.nba.video.service.EveryDayVideoFromSinaThread;
import com.lb.crawl.thread.nba.video.service.LuXiangFromZhiBoBa;
import com.lb.crawl.thread.nba.video.service.NBAMatchResultService;
@Service("EuropeThreadImpl")
@Component
public class EuropeThreadImpl {

 /**
  * �߳�ִ�й���Ϊ��ֹ����������崻���[ÿ���������������ִ��һ��]
  */
	
	@Scheduled(cron="0 57 15 * * ?")
	public static void begin(){
			 GetEuropeNewsFromSouHuThread europe=new GetEuropeNewsFromSouHuThread();
			 Thread europe_news=new Thread(europe,"europe_news");
			 europe_news.start();
	}
	

	@Scheduled(cron="0 57 3 * * ?")
	public static void begin_2(){
			 GetEuropeNewsFromSouHuThread europe=new GetEuropeNewsFromSouHuThread();
			 Thread europe_news=new Thread(europe,"europe_news");
			 europe_news.start();
	}
	 
}
