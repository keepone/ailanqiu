package com.lb.crawl.thread.nba.news.service;

import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;

public class ZhiBoBaThread extends Thread {

	@Override
	public void run() {
		//for(int i=1;i<=8;i++){   //8Сʱ
			GetNBANewsFromZhiBoBa.getNewsList("http://news.zhibo8.cc/nba");
			try {
				//System.out.println(Thread.currentThread().getName()+"��ʼֹͣ¼��ֱ�������ţ�ֹͣʱ��30����,��ǰiΪ������"+i+",i�ܹ�Ϊ8��ÿ30��������һ�Σ�����4Сʱ");
				this.sleep(10);
				System.out.println(Thread.currentThread().getName()+"¼��ֱ�������Ž���ֹͣ��");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		//}
	}

}
