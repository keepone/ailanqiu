package com.lb.crawl.thread.nba.video.service;

import com.lucene.crawl.GetGameResult;

public class NBAMatchResultService extends Thread {

	
	public void run() {
		System.out.println("������¼����������............................��ǰ�̣߳�"+Thread.currentThread().getName());	
		//this.sleep(2000);
		for(int i=1;i<=240;i++){
			boolean flag=GetGameResult.getGamResult("http://nba.sports.sina.com.cn/match_result.php");
			if(!flag){
				i=1000;
			}
			try {
				System.out.println(Thread.currentThread().getName()+"��ʼֹͣ¼����������ֹͣʱ��60��,��ǰiΪ��"+i+", i�ܹ�Ϊ240��ÿ1��������һ�Σ�����240����");
				this.sleep(60000);
				System.out.println(Thread.currentThread().getName()+"¼������������ֹͣ��");
			} catch (InterruptedException e) {
				System.out.println("¼��nba������Ƶ����");
			}
			
		}
	}

}
