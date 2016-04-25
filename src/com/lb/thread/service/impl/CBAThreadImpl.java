package com.lb.thread.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.lb.crawl.thread.cba.news.service.GetNewsFromSinaThread;
import com.lb.crawl.thread.cba.news.service.GetNewsFromSoHuThread;
import com.lb.crawl.thread.cba.news.service.GetNewsFromXinHuaThread;
import com.lb.crawl.thread.cba.video.service.CBAMatchResultService;
@Component
public class CBAThreadImpl {

 /**
  * �߳�ִ�й���Ϊ��ֹ����������崻���[ÿ���������������ִ��һ��]
  */
	
	@Scheduled(cron="0 40 15 * * ?")
	public static void begin(){
			//¼��cba�������
			CBAMatchResultService cba=new CBAMatchResultService();
			Thread nba_thread=new Thread(cba,"cbamatch_result_1");
			nba_thread.start(); 
	}
	
 
	
	@Scheduled(cron="0 40 19 * * ?")
	public static void begin3(){
			//¼��cba�������
			CBAMatchResultService cba=new CBAMatchResultService();
			Thread nba_thread=new Thread(cba,"cbamatch_result2");
			nba_thread.start(); 
	}
	
	
	@Scheduled(cron="0 51 16 * * ?")
	public static void addNews(){
			//�»�������
			GetNewsFromXinHuaThread xh=new GetNewsFromXinHuaThread();
			Thread xh_news=new Thread(xh,"xinhua_cba_news");
			xh_news.start();
			
			//����������
			GetNewsFromSinaThread sina=new GetNewsFromSinaThread();
			Thread sina_news=new Thread(sina,"sina_cba_news");
			sina_news.start();
			
			//�Ѻ�������
			GetNewsFromSoHuThread sohu=new GetNewsFromSoHuThread();
			Thread sohu_news=new Thread(sohu,"sohu_cba_news");
			sohu_news.start();
			
	}

	@Scheduled(cron="0 10 12 * * ?")
	public static void addNews2(){
			//�»�������
			GetNewsFromXinHuaThread xh=new GetNewsFromXinHuaThread();
			Thread xh_news=new Thread(xh,"xinhua_cba_news2");
			xh_news.start();
			
			//����������
			GetNewsFromSinaThread sina=new GetNewsFromSinaThread();
			Thread sina_news=new Thread(sina,"sina_cba_news2");
			sina_news.start();
			
			//�Ѻ�������
			GetNewsFromSoHuThread sohu=new GetNewsFromSoHuThread();
			Thread sohu_news=new Thread(sohu,"sohu_cba_news2");
			sohu_news.start();
			
	}
	

}
