package com.lb.crawl.thread.nba.video.service;

import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBAVideoFromHuPu;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lucene.crawl.GetGameResult;

public class EveryDayVideoFromHuPuThread extends Thread {

	@Override
	public void run() {
		for(int i=1;i<=24;i++){   //8Сʱ
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/game");//����
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/Cavaliers"); //��ʿ
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/lakers"); //����
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/heat"); //�Ȼ�
			GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/rockets"); //���
			GetNBAVideoFromHuPu.addTouTiao("http://v.opahnet.com/nba"); //��ҳ�Ƽ�
			
			try {
				System.out.println(Thread.currentThread().getName()+"��ʼֹͣ¼�뻢�˱�����Ƶ��ֹͣʱ��30����,��ǰiΪ������"+i+",i�ܹ�Ϊ8��ÿ10��������һ�Σ�����4Сʱ");
				this.sleep(600000);
			} catch (InterruptedException e) {
				 
			}
			
		}
	}

}
