package com.lb.crawl.common.service.impl;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lucene.crawl.GetCBADataRanking;
import com.lucene.crawl.GetDataRankingBySina;

 
@Component
public class DataRankingService {
	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	//��ȡnba�������
	@Scheduled(cron="0 8 1 * * ?")   
	public void addNBATeamDataRanking(){
		System.out.println("�������ͳ��.........");
		GetDataRankingBySina.getDataRanking("http://nba.sports.sina.com.cn/league_order1.php?dpc=1");
	}
	
	//��ȡnba�������
	@Scheduled(cron="0 8 2 * * ?")    
	public void addNBATeamDataRanking2(){
		System.out.println("�������ͳ��.........");
		GetDataRankingBySina.getDataRanking("http://nba.sports.sina.com.cn/league_order1.php?dpc=1");
	}
	
	
	
	//�����˻�ȡnba��Ա����ͳ��	
	@Scheduled(cron="0 10 2 * * ?")  
	public void addTeamPlayerRanking(){
		System.out.println("�÷ְ�ͳ��.........");
		GetDataRankingBySina.getPlayerScoreRanking("http://nba.sports.sina.com.cn/playerstats.php?s=0&e=49&key=1&t=1");
	}
	
	//�����˻�ȡnba��Ա����ͳ��	
	@Scheduled(cron="0 10 3 * * ?")  
	public void addTeamPlayerRanking2(){
		System.out.println("�÷ְ�ͳ��.........");
		GetDataRankingBySina.getPlayerScoreRanking("http://nba.sports.sina.com.cn/playerstats.php?s=0&e=49&key=1&t=1");
	}
	
	
	
	//��ȡcba�������
	@Scheduled(cron="0 2 2 * * ?")   
	public void addCBATeamDataRanking(){
		System.out.println("cba�������ͳ��.........");
		GetCBADataRanking.getTeamRanking("http://cba.sports.sina.com.cn/cba/stats/teamrank/?dpc=1");
	}
	
	//��ȡcba�������
	@Scheduled(cron="0 2 3 * * ?")   
	public void addCBATeamDataRanking2(){
		System.out.println("cba�������ͳ��.........");
		GetCBADataRanking.getTeamRanking("http://cba.sports.sina.com.cn/cba/stats/teamrank/?dpc=1");
	}
	
	//����Ѷ��ȡcba��Ա����
	@Scheduled(cron="0 1 3 * * ?")   
	public void addCBAPlayerRanking(){
		System.out.println("cba��Ա����ͳ��.........");
		GetCBADataRanking.getPlayerRanking("http://cbadata.sports.qq.com/playerrank.php?id=");
	}
	
	//����Ѷ��ȡcba��Ա����
	@Scheduled(cron="0 1 5 * * ?")   
	public void addCBAPlayerRanking2(){
		System.out.println("cba��Ա����ͳ��.........");
		GetCBADataRanking.getPlayerRanking("http://cbadata.sports.qq.com/playerrank.php?id=");
	}
	
	//�����˻�ȡwcba��Ա����
	//@Scheduled(cron="0 39 23 * * ?")   
	public void addWCBAPlayerRanking(){
		System.out.println("wcba��Ա����ͳ��.........");
		GetCBADataRanking.getWCBAScoreRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=pts&qleagueid=160&qround=");
	}

}
