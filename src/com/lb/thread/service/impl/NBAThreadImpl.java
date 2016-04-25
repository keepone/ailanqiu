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
  * �߳�ִ�й���
  * nba���������������ʱ���ִ�У�3���̣߳���ÿ���߳�����ѭ��ʱ��4��Сʱ
  * ���ţ�ֱ���ɣ��Ѻ�ѭ������Ϊ1��
  * 
  */
	//¼��nba�������
	//¼��nba�������
	public static NBAMatchResultService nba3=new NBAMatchResultService();
	public static Thread nbamatch_result_afternoon=new Thread(nba3,"nbamatch_result_afternoon");
	@Scheduled(cron="0 0 3 * * ?")
	public static void begin(){
		  NBAMatchResultService nba=new NBAMatchResultService();
		  Thread nba_thread=new Thread(nba,"nbamatch_result_lincheng");
		  nba_thread.start(); 
	}
	
	//¼��nba������������磩
	@Scheduled(cron="0 45 10 * * ?")
	public static void begin_2(){
			//¼��nba�������
			NBAMatchResultService nba2=new NBAMatchResultService();
			Thread nba_thread2=new Thread(nba2,"nbamatch_result_morning");
			nba_thread2.start(); 
	}
	//¼��nba�������(����)
	@Scheduled(cron="0 10 11 * * ?")
	public static void begin_3(){
			nbamatch_result_afternoon.start(); 
			
			
	}
	
	
	//¼������ÿ�ջ�����Ƶ(����)
	@Scheduled(cron="0 0 7 * * ?")
	public static void addSinaVideo(){
			EveryDayVideoFromSinaThread every=new EveryDayVideoFromSinaThread();
			Thread thread=new Thread(every,"evervideofromsina_morning");
			thread.start();
	}
	
	//¼������ÿ�ջ�����Ƶ(����)
	@Scheduled(cron="0 10 12 * * ?")
	public static void addSinaVideo_2(){
			EveryDayVideoFromSinaThread every=new EveryDayVideoFromSinaThread();
			Thread thread=new Thread(every,"evervideofromsina_afternoon");
			thread.start();
	}
	
	
	//¼������ÿ�ջ�����Ƶ(����)
		@Scheduled(cron="0 32 11 * * ?")
		public static void addHuPuMatchVideo(){
			EveryDayVideoFromHuPuThread  hp=new EveryDayVideoFromHuPuThread();
				Thread thread=new Thread(hp,"hu_nba_match_video");
				thread.start();
		}
	
	
	
	
	@Scheduled(cron="0 0 7 * * ?")
	public static void beginAddNews(){			
			//¼��ֱ��������
			ZhiBoBaThread zbb_news=new ZhiBoBaThread();
			Thread zbb_news_thread=new Thread(zbb_news,"zhibobanews");
			zbb_news_thread.start();
			
			//¼��nba98����
			NBA98News news_98=new NBA98News();
			Thread news_98_thread=new Thread(news_98,"nba98news");
			news_98_thread.start();
			
			
			//¼��TBBA����
			TBBANews tbba_news=new TBBANews();
			Thread tbba_news_thread=new Thread(tbba_news,"tbbanews");
			tbba_news_thread.start();
			
			//¼����������
			SinaNewsThread sina=new SinaNewsThread();
			Thread sina_thread=new Thread(sina,"sina_nba_news");
			sina_thread.start();
			
			//¼���Ѻ�����
			SoHuNewsThread sohu=new SoHuNewsThread();
			Thread sohu_news=new Thread(sohu,"sohu_nba_news");
			sohu.start();
			
	}
	
	
	
	
	
	@Scheduled(cron="0 10 12 * * ?")
	public static void beginAddNews_2(){			
			//¼��ֱ��������
			ZhiBoBaThread zbb_news=new ZhiBoBaThread();
			Thread zbb_news_thread=new Thread(zbb_news,"zhibobanews2");
			zbb_news_thread.start();
			
			//¼��nba98����
			NBA98News news_98=new NBA98News();
			Thread news_98_thread=new Thread(news_98,"nba98news2");
			news_98_thread.start();
			
			
			//¼��TBBA����
			TBBANews tbba_news=new TBBANews();
			Thread tbba_news_thread=new Thread(tbba_news,"tbbanews2");
			tbba_news_thread.start();
			
			//¼����������
			SinaNewsThread sina=new SinaNewsThread();
			Thread sina_thread=new Thread(sina,"sina_nba_news2");
			sina_thread.start();
			
			//¼���Ѻ�����
			SoHuNewsThread sohu=new SoHuNewsThread();
			Thread sohu_news=new Thread(sohu,"sohu_nba_news2");
			sohu.start();
			
	}
	
	
	
	
	@Scheduled(cron="0 33 18 * * ?")
	public static void beginAddNews_3(){			
			//¼��ֱ��������
			ZhiBoBaThread zbb_news=new ZhiBoBaThread();
			Thread zbb_news_thread=new Thread(zbb_news,"zhibobanews3");
			zbb_news_thread.start();
			
			//¼��nba98����
			NBA98News news_98=new NBA98News();
			Thread news_98_thread=new Thread(news_98,"nba98news3");
			news_98_thread.start();
			
			
			//¼��TBBA����
			TBBANews tbba_news=new TBBANews();
			Thread tbba_news_thread=new Thread(tbba_news,"tbbanews3");
			tbba_news_thread.start();
			
			//¼����������
			SinaNewsThread sina=new SinaNewsThread();
			Thread sina_thread=new Thread(sina,"sina_nba_news3");
			sina_thread.start();
			
			
			//¼���Ѻ�����
			SoHuNewsThread sohu=new SoHuNewsThread();
			Thread sohu_news=new Thread(sohu,"sohu_nba_news3");
			sohu.start();
	}
	
	
	
	/**
	 * ��������
	 */
	@Scheduled(cron="0 33 3 * * ?")
	public static void addSinaNews(){
		GetNBANewsFromSina.getNewsBySina("http://sports.sina.com.cn/nba/");
	}
	
	/**
	 * ¼��
	 */
	@Scheduled(cron="0 45 23 * * ?")
	public static void lx(){
		GetLuXiangFromZhiBoBa.getNBALuXiang("http://www.zhibo8.cc/nba/luxiang.htm");
	}
	
 
	
	@Scheduled(cron="0 7 7 * * ?")
	public static void beginAddLuXiang(){
			//¼�����¼�񣨰���nba��cba��
			LuXiangFromZhiBoBa luxiang=new LuXiangFromZhiBoBa();
			Thread lx_thread=new Thread(luxiang,"luxiang1");
			lx_thread.start();

	}
	
	@Scheduled(cron="0 57 11 * * ?")
	public static void beginAddLuXiang_2(){
			//¼�����¼�񣨰���nba��cba��
			LuXiangFromZhiBoBa luxiang=new LuXiangFromZhiBoBa();
			Thread lx_thread=new Thread(luxiang,"luxiang2");
			lx_thread.start();

	}
	
	
	
	@Scheduled(cron="0 10 18 * * ?")
	public static void beginAddLuXiang3(){
			//¼�����¼�񣨰���nba��cba��
			LuXiangFromZhiBoBa luxiang=new LuXiangFromZhiBoBa();
			Thread lx_thread=new Thread(luxiang,"cba_luxiang3");
			lx_thread.start();

	}
	
}
