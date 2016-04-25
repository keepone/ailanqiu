package com.lucene.crawl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.TimeUtil;

@Component
public class GetTvDirectByBroadcast {
	private static BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private static ServiceUtil serviceUtil = null;
	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	public static void main(String[] args) throws IOException {
		String url = "http://www.zhibo8.cc/";
	 JSONArray jsonArray=getZhibo(url);

		 System.out.println(jsonArray.size());
		 for(int i=0;i<1;i++){
		 JSONObject j=(JSONObject) jsonArray.get(i);
		 String host_team=j.get("host_team").toString();
		 String guest_team=j.get("guest_team").toString();
		 String date=j.get("date").toString();
		 String week=j.get("week").toString();
		 String time_detail=j.get("time_detail").toString();
		 String direct=j.get("direct").toString();
		 System.out.println(direct);
		 }
	}

	/**
	 * 使用选择器语法来查找元素
	 * 
	 * @throws IOException
	 */
	public static JSONArray getZhibo(String url) throws IOException {
		String pageCount = null;
		Document doc = Jsoup.connect(url).get();
		Elements boxs = doc.select("div.box");
		JSONArray jsonArray = new JSONArray();
		List<String> list = new ArrayList<String>();
		list.add("CCTV5");
		list.add("QQ");
		list.add("广东");
		list.add("PPTV");
		list.add("新浪 ");
		list.add("重庆");
		for (Element box : boxs) {
			String label = "";
			if (box.html().indexOf("titlebar") != -1) {
				String time = box.select("div.titlebar").select("h2").first()
						.ownText();
				int ii = time.indexOf("日");
				String date = time.substring(0, ii + 1);
				String week = time.substring(ii + 1, time.length());
				if (date.indexOf("月") != -1) {
					date = date.replace("月", "-");
				}
				if (date.indexOf("日") != -1) {
					date = date.replace("日", "");
				}
				date = "2015-" + date;

				String today = TimeUtil.toDate("" + System.currentTimeMillis());
				today = today.substring(0, 10);
				long decimal_today = TimeUtil.dateTo(today) + 6 * 60 * 24 * 60
						* 1000;
				long jj = TimeUtil.dateTo(date);
				if (decimal_today >= jj) {

					Elements es = box.select("li");
					String nbaOrCba = "";
					for (Element e : es) {
						/**
						 * 去除包含以下关键字的信息
						 */
						if ((e.html().indexOf("篮球") != -1 || e.html().indexOf(
								"NBA") != -1)
								&& e.html().indexOf("NBA最前线") == -1&& e.html().indexOf("全明星") == -1
								&& e.html().indexOf("篮球公园") == -1&& e.html().indexOf("女篮") == -1
								&& e.html().indexOf("欧篮") == -1&& e.html().indexOf("女子") == -1) {
							String content = e.ownText().trim();
							String time_detail = content.substring(0, 5);
							label = e.attr("label"); 
							if (label.indexOf("NBA,") != -1) {

								nbaOrCba = "NBA";
							}
							if (label.indexOf("CBA,") != -1) {

								nbaOrCba = "CBA";
							}

							String team = null;
							if (content.length() == 5) {
								team = e.select("b").last().ownText().replace(
										"NBA常规赛", "");
							} else {
								team = content.substring(5, content.length());
							}
							int index = team.indexOf("-");
							if(index!=-1){
								String host_team = team.substring(0, index).trim();
								if(nbaOrCba.equals("CBA")&&(team.indexOf("决赛")!=-1||team.indexOf("季后赛")!=-1)){
									int ind=host_team.indexOf(" ");
									host_team=host_team.substring(ind+1,host_team.length());
								}
								String guest_team = team.substring(index + 1, team
										.length()).trim();
								
								Elements eee = e.select("a[href]");
								JSONObject direct = new JSONObject();
								int j = 0;
								String name ="";
								String directTV = "";
								for (Element tv : eee) {
									 if(j==0){
										 name = tv.ownText();
										 String a = null;
											String b = name.substring(0, 2);
											
											for (int i = 0; i < list.size(); i++) {
												a = list.get(i);
			
												if (a.indexOf(b) != -1) {
													directTV = name;
													directTV=directTV.replace("直播", "");
													directTV=directTV.replaceAll("\u00A0\u00A0\u00A0\u00A0", " ");

													break;
												}
											}
										 
									 }
									 j++;
	 

								}

//								JSONObject json = new JSONObject();
//								json.put("nbaOrCba", nbaOrCba);
//								json.put("host_team", host_team);
//								json.put("guest_team", guest_team);
//								json.put("date", date);
//								json.put("week", week);
//								json.put("time_detail", time_detail);
//								json.put("direct", directTV);
//								jsonArray.add(json);
								
								String host_team_logo="";
								String guest_team_logo="";
								String sql1 = null;
								String sql =null;
								 
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
								long time2 = TimeUtil.dateTo(date);
								long current_time = System.currentTimeMillis();
								Object[] params = { host_team, guest_team, time2, week, time_detail,
										directTV, nbaOrCba, current_time,host_team_logo,guest_team_logo};
								Object[] params2 = { host_team, time2 };
								Integer c = Integer.parseInt(((Map) baseDao.findBy(sql1, params2)
										.get(0)).get("count").toString());
								if (c == 0) {
									baseDao.insertBy(sql, params);
								}
								
								
								System.out.println(date+"------"+time_detail + "------" + host_team
										+ "---" + guest_team);
							}
							
						}
						

					}
				} else {
					return jsonArray;
				}

			} else {

			}

		}

		return jsonArray;

	}
}