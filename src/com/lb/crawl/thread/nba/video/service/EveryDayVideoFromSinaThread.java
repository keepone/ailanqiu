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
		System.out.println("run方法，从新浪录入每日汇总视频：............................当前线程："+Thread.currentThread().getName());
		//String startURL = "http://roll.sports.sina.com.cn/s_nbavideo_video_big/index_";
		
		for(int i=1;i<=30;i++){
			GetPlayerVideoBySina.getEveryDayMatchVideo("http://roll.sports.sina.com.cn/s_nbavideo_video_big/index_");
			try {
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入每日视频：将停止10分钟,当前i为：－－"+i+",i总共为30，每10分钟运行一次，运行5小时");
				this.sleep(600000);
				System.out.println(Thread.currentThread().getName()+"线程录入每日视频结束停止：");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		 
	}

	 
	 
	
	 
}
