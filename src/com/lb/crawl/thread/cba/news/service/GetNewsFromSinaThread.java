package com.lb.crawl.thread.cba.news.service;

import com.lucene.crawl.GetCBANewsBySina;


public class GetNewsFromSinaThread  extends Thread {

	@Override
	public void run() {
		for(int i=1;i<=2;i++){
			String startURL = "http://roll.sports.sina.com.cn/s_cba_all/index.shtml";
			GetCBANewsBySina.getNewsList("http://roll.sports.sina.com.cn/s_cba_all/index.shtml");
			try {		
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼��������cba���ţ���ֹͣ2Сʱ,��ǰiΪ������"+i+",i�ܹ�Ϊ2��ÿ2Сʱ����һ�Σ�����4Сʱ");
				this.sleep(7200000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
