package com.lb.crawl.thread.cba.news.service;
import com.lb.craw.cba.GetCBANewsFromSoHu;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromSoHu;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;

public class GetNewsFromSoHuThread extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=8;i++){
			GetCBANewsFromSoHu.getNewsList_2("http://sports.sohu.com/1/1102/39/subject204253982.shtml");//NBA��̬����
			GetCBANewsFromSoHu.getNewsList_2("http://cbachina.sports.sohu.com/s2011/2671/s309755607/");//CBAý������
			try {		
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼���Ѻ���nba���ţ���ֹͣ30����,��ǰiΪ������"+i+",i�ܹ�Ϊ8��ÿ30��������һ�Σ�����4Сʱ");
				this.sleep(1800000);
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼��������nba���Ž���ֹͣ��");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
