package com.lb.crawl.thread.nba.news.service;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;

public class SinaNewsThread extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=30;i++){
			GetNBANewsFromSina.getNewsBySina("http://sports.sina.com.cn/nba/");
			try {		
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼��������nba���ţ���ֹͣ30����,��ǰiΪ������"+i+",i�ܹ�Ϊ30��ÿ10��������һ�Σ�����5Сʱ");
				this.sleep(600000);
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼��������nba���Ž���ֹͣ��");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
