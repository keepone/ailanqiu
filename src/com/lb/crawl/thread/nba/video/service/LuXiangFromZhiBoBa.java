package com.lb.crawl.thread.nba.video.service;

import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lucene.crawl.GetGameResult;

public class LuXiangFromZhiBoBa extends Thread {

	@Override
	public void run() {
		
		
	System.out.println("��ֱ����¼�����¼��............................��ǰ�̣߳�"+Thread.currentThread().getName());
		for(int i=1;i<=8;i++){   //8Сʱ
			GetLuXiangFromZhiBoBa.getNBALuXiang("http://www.zhibo8.cc/nba/luxiang.htm");
			try {
				System.out.println(Thread.currentThread().getName()+"��ʼֹͣ¼�����¼��ֹͣʱ��30����,��ǰiΪ������"+i+",i�ܹ�Ϊ8��ÿ30��������һ�Σ�����4Сʱ");
				this.sleep(1800000);
				System.out.println(Thread.currentThread().getName()+"¼�����¼�����ֹͣ��");
			} catch (InterruptedException e) {
				 
			}
			
		}
	}

}
