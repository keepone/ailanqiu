package com.lb.crawl.thread.nba.news.service;

import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lb.crawl.nba.GetNewsFromTBBA;

public class TBBANews extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=2;i++){
			GetNewsFromTBBA.getNewsList("http://www.tbba.com.cn/");
			try {		
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼��TBBA���ţ���ֹͣ2Сʱ,��ǰiΪ������"+i+",i�ܹ�Ϊ2��ÿ2Сʱ����һ�Σ�����4Сʱ");
				this.sleep(7200000);
				System.out.println(Thread.currentThread().getName()+"�߳�¼��TBBA���Ž���ֹͣ��");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
