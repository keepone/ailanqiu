package com.lb.crawl.thread.cba.video.service;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.TimeUtil;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetInfoBy163;
import com.lucene.crawl.GetPlayerVideoByYouKu;

@Component
public class CBAMatchResultService extends Thread {
	BaseDao baseDao = null;
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	
	
	private static ServiceUtil serviceUtil=null;
	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}

	@Override
	public void run() {
		for(int i=1;i<=240;i++){   
			boolean flag=GetGameResult.getCBAGameResult("http://cba.sports.sina.com.cn/cba/schedule/all/");
			if(!flag){
				i=1000;
			}
				if(i==1){
					if(flag&&Thread.currentThread().getName().equals("cbamatch_result_1")){
					String sql="select game_time from cba_direct where date="+TimeUtil.getTodayDecimal()+" order by game_time limit 0,1";
					String time=serviceUtil.getStringBySql(sql, "game_time"); //19:35
					String today=TimeUtil.toDay(""+TimeUtil.getTodayDecimal());
					long game_date=TimeUtil.dateTo_2(today+" "+time+":00");
					long cur_date=TimeUtil.dateTo_2(today+" "+"16:40:00");
					if(game_date>cur_date){
						i=1000;
					}
				  } 
				}
			 
			try {		
				System.out.println(Thread.currentThread().getName()+"线程开始停止录入cba比赛结果：将停止60秒,当前i为"+i+"i总共为240，每1分钟运行1次，运行4小时");
				this.sleep(60000);
				System.out.println(Thread.currentThread().getName()+"线程录入cba比赛结果结束停止：");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
 
}
