package com.lb.crawl.thread.cba.news.service;

import com.lb.craw.cba.GetCBANewsByXinHua;
 

public class GetNewsFromXinHuaThread  extends Thread {

	@Override
	public void run() {
		
		for(int i=1;i<=2;i++){
			 
			GetCBANewsByXinHua.getNewsList("http://www.news.cn/sports/cba.htm");
			try {		
				System.out.println(Thread.currentThread().getName()+"�߳̿�ʼֹͣ¼���»���cba���ţ���ֹͣ2Сʱ,��ǰiΪ������"+i+",i�ܹ�Ϊ2��ÿ2Сʱ����һ�Σ�����4Сʱ");
				this.sleep(7200000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}

}
