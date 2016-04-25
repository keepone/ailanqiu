package com.lb.time.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lucene.crawl.GetGameResult;

@Service("NBAMatchResutlTime")
@Component
public class NBAMatchResutlTime {
	@Scheduled(cron="0 25 22 * * ?")
	public void go(){
		//System.out.println("������¼����������............................��ǰ�̣߳�"+Thread.currentThread().getName());	
		//this.sleep(2000);
		for(int i=1;i<=10;i++){
			GetGameResult.getGamResult("http://nba.sports.sina.com.cn/match_result.php");
			try {
				if(i>12){
					throw new IllegalArgumentException("make a fault");
					//System.out.println("ff");
				}
				//System.out.println(Thread.currentThread().getName()+"��ʱ����ʼֹͣ¼����������ֹͣʱ��60��,��ǰiΪ��"+i+", i�ܹ�Ϊ270��ÿ1��������һ�Σ�����260����");
				Thread.currentThread().sleep(60000);
				//System.out.println(Thread.currentThread().getName()+"¼������������ֹͣ��");
			} catch (InterruptedException e) {
				System.out.println("¼��nba������Ƶ����");
			}
			
		}
	}
}
