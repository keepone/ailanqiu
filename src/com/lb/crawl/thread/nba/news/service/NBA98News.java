package com.lb.crawl.thread.nba.news.service;

import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;

public class NBA98News extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=2;i++){
			GetNewsFromNBA98.getNewsList("http://www.nba98.com/news/");
			try {		
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼��NBA98���ţ���ֹͣ2Сʱ,��ǰiΪ������"+i+",i�ܹ�Ϊ2��ÿ2Сʱ����һ�Σ�����4Сʱ");
				this.sleep(7200000);
				System.out.println(Thread.currentThread().getName()+"�߳�¼��NBA98���Ž���ֹͣ��");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
