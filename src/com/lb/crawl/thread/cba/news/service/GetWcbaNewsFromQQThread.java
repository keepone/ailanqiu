package com.lb.crawl.thread.cba.news.service;

import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetEuropeBasketball;
import com.lucene.crawl.GetWCBANews;


public class GetWcbaNewsFromQQThread  extends Thread {

	@Override
	public void run() {
		for(int i=1;i<=1;i++){
			System.out.println("����Ѷ��ȡwcba����");
			GetWCBANews.getNewsList("http://sports.qq.com/l/cba/wcba/list20110816174202.htm");
			try {		
				//System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼����Ѷ��wcba�������ţ���ֹͣ2Сʱ,��ǰiΪ������"+i+",i�ܹ�Ϊ2��ÿ2Сʱ����һ�Σ�����4Сʱ");
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
