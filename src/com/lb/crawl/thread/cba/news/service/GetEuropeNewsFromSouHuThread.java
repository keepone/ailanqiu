package com.lb.crawl.thread.cba.news.service;

import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetEuropeBasketball;


public class GetEuropeNewsFromSouHuThread  extends Thread {

	@Override
	public void run() {
		for(int i=1;i<=1;i++){
			System.out.println("���Ѻ���ȡŷ����������");
			GetEuropeBasketball.getNewsList("http://sports.sohu.com/s2014/1565/s395217589/");
			try {		
				//System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼���Ѻ�ŷ���������ţ���ֹͣ2Сʱ,��ǰiΪ������"+i+",i�ܹ�Ϊ2��ÿ2Сʱ����һ�Σ�����4Сʱ");
				this.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
