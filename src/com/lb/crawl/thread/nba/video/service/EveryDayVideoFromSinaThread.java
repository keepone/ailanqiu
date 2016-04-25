package com.lb.crawl.thread.nba.video.service;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.lb.dao.BaseDao;
import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetInfoBy163;
import com.lucene.crawl.GetPlayerVideoBySina;
import com.lucene.crawl.GetPlayerVideoByYouKu;

@Component
public class EveryDayVideoFromSinaThread extends Thread {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public void run() {
		System.out.println("run������������¼��ÿ�ջ�����Ƶ��............................��ǰ�̣߳�"+Thread.currentThread().getName());
		//String startURL = "http://roll.sports.sina.com.cn/s_nbavideo_video_big/index_";
		
		for(int i=1;i<=30;i++){
			GetPlayerVideoBySina.getEveryDayMatchVideo("http://roll.sports.sina.com.cn/s_nbavideo_video_big/index_");
			try {
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼��ÿ����Ƶ����ֹͣ10����,��ǰiΪ������"+i+",i�ܹ�Ϊ30��ÿ10��������һ�Σ�����5Сʱ");
				this.sleep(600000);
				System.out.println(Thread.currentThread().getName()+"�߳�¼��ÿ����Ƶ����ֹͣ��");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		 
	}

	 
	 
	
	 
}
