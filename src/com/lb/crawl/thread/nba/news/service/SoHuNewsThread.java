package com.lb.crawl.thread.nba.news.service;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromSoHu;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;

public class SoHuNewsThread extends Thread {

	@Override
	public void run() {
		
		//for(int i=1;i<=8;i++){
			GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/1/1102/39/subject204253982.shtml");//NBA��̬����
			GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/1/1102/34/subject204253472.shtml");//�������
			GetNBANewsFromSoHu.getNewsList("http://sports.sohu.com/s2014/lebron-james/index.shtml");//��ʿ����
			GetNBANewsFromSoHu.getNewsList_2("http://sports.sohu.com/nba_a_932.shtml");//NBA����
			try {		
				//System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼���Ѻ���nba���ţ���ֹͣ30����,��ǰiΪ������"+i+",i�ܹ�Ϊ8��ÿ30��������һ�Σ�����4Сʱ");
				this.sleep(10);
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼��������nba���Ž���ֹͣ��");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		//}
	}

}
