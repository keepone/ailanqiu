package com.lb.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.lb.dao.BaseDao;
import com.lb.service.DirectService;
import com.lb.service.ServiceUtil;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.PageBean;
import com.lb.utils.TimeUtil;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetNBAInfoUtil;
import com.lucene.crawl.GetTvDirectByBroadcast;
 

/**
 * 
 * @author Allen
 * @date 2014-11-9
 * @fuction
 * 
 */
@Service("directService")
@Component
public class DirectServiceImpl implements DirectService {

	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	ServiceUtil serviceUtil = null;
	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	/**
	 * 增加NBA或CBA直播信息
	 */
	@Override
	@Scheduled(cron = "0 53 1 * * ?")
	public void addDirectBroadcast() {
		String host_team_logo="";
		String guest_team_logo="";
		String sql1 = null;
		System.out.println("开始录入直播信息......................");
		String sql =null;
		String url = "http://www.zhibo8.cc/";
		JSONArray jsonArray = null;
		try {
			jsonArray = GetTvDirectByBroadcast.getZhibo(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	
		

	}
	
	
	
	public void addDirectBroadcast2() {
		String host_team_logo="";
		String guest_team_logo="";
		String sql1 = null;
		System.out.println("开始录入直播信息......................");
		String sql =null;
		String url = "http://www.zhibo8.cc/";
		JSONArray jsonArray = null;
		try {
			jsonArray = GetTvDirectByBroadcast.getZhibo(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(jsonArray.size());
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject j = (JSONObject) jsonArray.get(i);
			String host_team = j.get("host_team").toString().trim();
			String guest_team = j.get("guest_team").toString().trim();
			StringBuffer buffer=new StringBuffer();
			buffer.append("select logo from nba_team where logo like '%");
//			 if(host_team.equals("黄蜂")){
//				 host_team="鹈鹕";
//			 }
//			 if(guest_team.equals("黄蜂")){
//				 guest_team="鹈鹕";
//			 }
			
			String date = j.get("date").toString();
			long time = TimeUtil.dateTo(date);
			String week = j.get("week").toString().trim();
			String time_detail = j.get("time_detail").toString();
			String direct = j.get("direct").toString();
			String nbaOrCba = j.get("nbaOrCba").toString();
			if(nbaOrCba.equals("NBA")){
				String host_sql="select sourceLogo from nba_team where name like '%"+host_team+"%'";
				String guest_sql="select sourceLogo from nba_team where name like '%"+guest_team+"%'";
				
				host_team_logo=serviceUtil.getStringBySql(host_sql, "sourceLogo");
				guest_team_logo=serviceUtil.getStringBySql(guest_sql, "sourceLogo");
				
				sql= "INSERT  INTO  nba_direct(host_team,guest_team,DATE,WEEK,game_time,direct_broadcast,NBAorCBA,addTime,host_team_logo,guest_team_logo) values(?,?,?,?,?,?,?,?,?,?)";
				sql1="select count(*) AS count from nba_direct where host_team=? and date=?";
			}else{
				String host_sql="select source_logo from cba_team where name like '%"+host_team+"%'";
				String guest_sql="select source_logo from cba_team where name like '%"+guest_team+"%'";
				host_team_logo=serviceUtil.getStringBySql(host_sql, "source_logo");
				guest_team_logo=serviceUtil.getStringBySql(guest_sql, "source_logo");
				
				sql= "INSERT  INTO  cba_direct(host_team,guest_team,DATE,WEEK,game_time,direct_broadcast,NBAorCBA,addTime,host_team_logo,guest_team_logo) values(?,?,?,?,?,?,?,?,?,?)";
				sql1="select count(*) AS count from cba_direct where host_team=? and date=?";
			}
			long current_time = System.currentTimeMillis();
			Object[] params = { host_team, guest_team, time, week, time_detail,
					direct, nbaOrCba, current_time,host_team_logo,guest_team_logo};
			Object[] params2 = { host_team, time };
			Integer c = Integer.parseInt(((Map) baseDao.findBy(sql1, params2)
					.get(0)).get("count").toString());
			if (c == 0) {
				baseDao.insertBy(sql, params);
			}

		}

	}
	@Override
	public PageBean getDirectSinceToday(Integer pageNo, Integer pageSize) {
		PageBean page = new PageBean();
		page.setPageNo(pageNo);
		long today_decimal = TimeUtil.getTodayDecimal();
		String sql = "SELECT * FROM nba_direct where date>=" + today_decimal
				+ " ORDER BY date";

		JSONArray arr = baseDao.find(sql, null);
		String sql_count = "select count(*) as count from nba_direct where date>="
				+ today_decimal;
		Integer count = serviceUtil.getCountBySql(sql_count);
		page.setData(arr);
		page.setRowCount(count);
		return page;
	}

	 
	
	
	
	
	 
	
	
	
	@Override
	public Integer getDirectId(long date, String guestTeam) {
		String sql="select id from nba_direct where date=? and guest_team=?";
		Object [] params={date,guestTeam};
		JSONArray arr=baseDao.find(sql, params);
		JSONObject json=(JSONObject) arr.get(0);
		Integer count=json.getInt("id");
		return count;
	}

	

}
