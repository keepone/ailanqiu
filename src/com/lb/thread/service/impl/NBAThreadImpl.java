package com.lb.thread.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.thread.nba.news.service.NBA98News;
import com.lb.crawl.thread.nba.news.service.SinaNewsThread;
import com.lb.crawl.thread.nba.news.service.SoHuNewsThread;
import com.lb.crawl.thread.nba.news.service.TBBANews;
import com.lb.crawl.thread.nba.news.service.ZhiBoBaThread;
import com.lb.crawl.thread.nba.video.service.EveryDayVideoFromSinaThread;
import com.lb.crawl.thread.nba.video.service.LuXiangFromZhiBoBa;
import com.lb.crawl.thread.nba.video.service.EveryDayVideoFromHuPuThread;
import com.lb.crawl.thread.nba.video.service.NBAMatchResultService;
@Component
public class NBAThreadImpl {

 /**
  * 线程执行规则：
  * nba比赛结果，分三个时间段执行（3个线程），每个线程设置循环时间4个小时
  * 新闻：直播吧，搜狐循环次数为1次
  * 
  */
	//录入nba比赛结果
	//录入nba比赛结果
	public static NBAMatchResultService nba3=new NBAMatchResultService();
	public static Thread nbamatch_result_afternoon=new Thread(nba3,"nbamatch_result_afternoon");
	@Scheduled(cron="0 0 3 * * ?")
	public static void begin(){
		  NBAMatchResultService nba=new NBAMatchResultService();
		  Thread nba_thread=new Thread(nba,"nbamatch_result_lincheng");
		  nba_thread.start(); 
	}
	
	//录入nba比赛结果（上午）
	@Scheduled(cron="0 45 10 * * ?")
	public static void begin_2(){
			//录入nba比赛结果
			NBAMatchResultService nba2=new NBAMatchResultService();
			Thread nba_thread2=new Thread(nba2,"nbamatch_result_morning");
			nba_thread2.start(); 
	}
	//录入nba比赛结果(下午)
	@Scheduled(cron="0 10 11 * * ?")
	public static void begin_3(){
			nbamatch_result_afternoon.start(); 
			
			
	}
	
	
	//录入新浪每日汇总视频(上午)
	@Scheduled(cron="0 0 7 * * ?")
	public static void addSinaVideo(){
			EveryDayVideoFromSinaThread every=new EveryDayVideoFromSinaThread();
			Thread thread=new Thread(every,"evervideofromsina_morning");
			thread.start();
	}
	
	//录入新浪每日汇总视频(下午)
	@Scheduled(cron="0 10 12 * * ?")
	public static void addSinaVideo_2(){
			EveryDayVideoFromSinaThread every=new EveryDayVideoFromSinaThread();
			Thread thread=new Thread(every,"evervideofromsina_afternoon");
			thread.start();
	}
	
	
	//录入新浪每日汇总视频(下午)
		@Scheduled(cron="0 32 11 * * ?")
		public static void addHuPuMatchVideo(){
			EveryDayVideoFromHuPuThread  hp=new EveryDayVideoFromHuPuThread();
				Thread thread=new Thread(hp,"hu_nba_match_video");
				thread.start();
		}
	
	
	
	
	@Scheduled(cron="0 0 7 * * ?")
	public static void beginAddNews(){			
			//录入直播吧新闻
			ZhiBoBaThread zbb_news=new ZhiBoBaThread();
			Thread zbb_news_thread=new Thread(zbb_news,"zhibobanews");
			zbb_news_thread.start();
			
			//录入nba98新闻
			NBA98News news_98=new NBA98News();
			Thread news_98_thread=new Thread(news_98,"nba98news");
			news_98_thread.start();
			
			
			//录入TBBA新闻
			TBBANews tbba_news=new TBBANews();
			Thread tbba_news_thread=new Thread(tbba_news,"tbbanews");
			tbba_news_thread.start();
			
			//录入新浪新闻
			SinaNewsThread sina=new SinaNewsThread();
			Thread sina_thread=new Thread(sina,"sina_nba_news");
			sina_thread.start();
			
			//录入搜狐新闻
			SoHuNewsThread sohu=new SoHuNewsThread();
			Thread sohu_news=new Thread(sohu,"sohu_nba_news");
			sohu.start();
			
	}
	
	
	
	
	
	@Scheduled(cron="0 10 12 * * ?")
	public static void beginAddNews_2(){			
			//录入直播吧新闻
			ZhiBoBaThread zbb_news=new ZhiBoBaThread();
			Thread zbb_news_thread=new Thread(zbb_news,"zhibobanews2");
			zbb_news_thread.start();
			
			//录入nba98新闻
			NBA98News news_98=new NBA98News();
			Thread news_98_thread=new Thread(news_98,"nba98news2");
			news_98_thread.start();
			
			
			//录入TBBA新闻
			TBBANews tbba_news=new TBBANews();
			Thread tbba_news_thread=new Thread(tbba_news,"tbbanews2");
			tbba_news_thread.start();
			
			//录入新浪新闻
			SinaNewsThread sina=new SinaNewsThread();
			Thread sina_thread=new Thread(sina,"sina_nba_news2");
			sina_thread.start();
			
			//录入搜狐新闻
			SoHuNewsThread sohu=new SoHuNewsThread();
			Thread sohu_news=new Thread(sohu,"sohu_nba_news2");
			sohu.start();
			
	}
	
	
	
	
	@Scheduled(cron="0 33 18 * * ?")
	public static void beginAddNews_3(){			
			//录入直播吧新闻
			ZhiBoBaThread zbb_news=new ZhiBoBaThread();
			Thread zbb_news_thread=new Thread(zbb_news,"zhibobanews3");
			zbb_news_thread.start();
			
			//录入nba98新闻
			NBA98News news_98=new NBA98News();
			Thread news_98_thread=new Thread(news_98,"nba98news3");
			news_98_thread.start();
			
			
			//录入TBBA新闻
			TBBANews tbba_news=new TBBANews();
			Thread tbba_news_thread=new Thread(tbba_news,"tbbanews3");
			tbba_news_thread.start();
			
			//录入新浪新闻
			SinaNewsThread sina=new SinaNewsThread();
			Thread sina_thread=new Thread(sina,"sina_nba_news3");
			sina_thread.start();
			
			
			//录入搜狐新闻
			SoHuNewsThread sohu=new SoHuNewsThread();
			Thread sohu_news=new Thread(sohu,"sohu_nba_news3");
			sohu.start();
	}
	
	
	
	/**
	 * 新浪新闻
	 */
	@Scheduled(cron="0 33 3 * * ?")
	public static void addSinaNews(){
		GetNBANewsFromSina.getNewsBySina("http://sports.sina.com.cn/nba/");
	}
	
	/**
	 * 录像
	 */
	@Scheduled(cron="0 45 23 * * ?")
	public static void lx(){
		GetLuXiangFromZhiBoBa.getNBALuXiang("http://www.zhibo8.cc/nba/luxiang.htm");
	}
	
 
	
	@Scheduled(cron="0 7 7 * * ?")
	public static void beginAddLuXiang(){
			//录入比赛录像（包括nba，cba）
			LuXiangFromZhiBoBa luxiang=new LuXiangFromZhiBoBa();
			Thread lx_thread=new Thread(luxiang,"luxiang1");
			lx_thread.start();

	}
	
	@Scheduled(cron="0 57 11 * * ?")
	public static void beginAddLuXiang_2(){
			//录入比赛录像（包括nba，cba）
			LuXiangFromZhiBoBa luxiang=new LuXiangFromZhiBoBa();
			Thread lx_thread=new Thread(luxiang,"luxiang2");
			lx_thread.start();

	}
	
	
	
	@Scheduled(cron="0 10 18 * * ?")
	public static void beginAddLuXiang3(){
			//录入比赛录像（包括nba，cba）
			LuXiangFromZhiBoBa luxiang=new LuXiangFromZhiBoBa();
			Thread lx_thread=new Thread(luxiang,"cba_luxiang3");
			lx_thread.start();

	}
	
}
