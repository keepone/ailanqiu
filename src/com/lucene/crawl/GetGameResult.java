package com.lucene.crawl;

import java.io.IOException;
 

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
import com.lb.thread.service.impl.NBAThreadImpl;
import com.lb.utils.TimeUtil;



@Component
public class GetGameResult {
	private static BaseDao baseDao = null;
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	private static ServiceUtil serviceUtil=null;
	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	
	private static int sum_retry_count=0;
	
	public static void main(String[] args) {
		getGamResult("http://nba.sports.sina.com.cn/match_result.php");
		String startURL = "http://cba.sports.sina.com.cn/cuba/women/schedule/show/13743/";
		getGameData("http://nba.sports.sina.com.cn/look_scores.php?id=2015110911", "0");
	}

	/**
	 * 获取每节比赛比分
	 * @param startURL
	 * @param directId
	 */
	public synchronized static void getScore(String startURL,String directId){
		Document doc = null;
		int g_one=0;  //客队第一节得分
		int g_two=0;	//主队第一节得分
		int g_three=0;
		int g_four=0;
		int h_one=0;
		int h_two=0;
		int h_three=0;
		int h_four=0;
			try {
				doc = Jsoup.connect(startURL).timeout(60000).get();
				Elements trs=doc.select("table.score").select("tr");
				int count_tr=1;
				int tr_index=1;
				String hostScore="";
				String guestScore="";
				for(Element tr:trs){
					if(count_tr>1){ //去除第一行
						Elements tds=tr.select("td");
						if(tr_index==1){
							int hCount=1;
							for(Element td:tds){
								if(hCount==1){
									 hostScore=td.ownText();
								}else{
									 hostScore=hostScore+" "+td.ownText();
								}
								hCount++;
								
							}
						}else{
							int gCount=1;
							for(Element td:tds){
								if(gCount==1){
									 guestScore=td.ownText();
								}else{
									 guestScore=guestScore+" "+td.ownText();
								}
								gCount++;
								
							}
						}
						tr_index++;
					}
					count_tr++;
				}
				 
				String sql="Update nba_direct set guest_every_score=?,host_every_score=? where id=?";
				Object [] params={hostScore,guestScore,directId};
				baseDao.updateBy(sql, params);
			} catch (IOException e) {
				 
			}
	}
	
	public synchronized static  boolean getGamResult(String startURL) {
		//System.out.println(Thread.currentThread().getName()+":线程正在执行录入比赛结果任务");
		NBAThreadImpl nba=new NBAThreadImpl();
		if(nba.nbamatch_result_afternoon.isAlive()&&Thread.currentThread().getName().equals("nbamatch_result_morning")){
			System.out.println("［下午线程已经启动，上午线程将被终止］");
			return false;
			
		}
		int count = 1;
		boolean flag=true;
		int f = 1;
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements trs = doc.select("tr[bgcolor$=#FFEFB6]");
			System.out.println(trs);
			if(trs.outerHtml().indexOf("赛中")==-1&&trs.outerHtml().indexOf("完场")==-1){
				System.out.println(trs);
				System.out.println("hava game");
				String threadName=Thread.currentThread().getName();
				if(threadName.equals("nbamatch_result_lincheng")){
					return false;   
				}else{
					return true;
				}
			 
			}
			for (Element tr : trs) {
				Elements tds = tr.select("td");
				int m = 1;
				String guestTeam="";
				String dataHref="";//数据统计超链接
				JSONObject json = new JSONObject();
				String mark = "";
				int status=0;
				String guestScore="";
				String hostScore="";
				for (Element element : tds) {
					if (m == 1) {
						mark = element.ownText();
						if (mark.indexOf("未赛") != -1) {
							break;
						} else if (mark.indexOf("赛中") != -1) {
							status=1;
						} else {
							 status=2;
						}
					}else if(m==3){  //获取客队名称
						guestTeam=element.text();
					}else if(m==4){  //获取客队名称
						String content=element.text();
						int index = content.indexOf("-");
						
						guestScore= content.substring(0, index);
						hostScore = content.substring(index + 1, content.length());
						
					}else if(m==9){
						dataHref=element.select("a[href]").first().attr("abs:href");
					}	
					m++;
			}
				
			//只有赛中或完场状态才更新数据库；
			if(status>0){
				 
				long decimal_today = TimeUtil.getTodayDecimal();
				
				String sql="select id from nba_direct where date="+decimal_today+" and guest_team='"+guestTeam+"'";
					//long decimal_today =1416931200000L;
					//String sql="select id from nba_direct where date="+decimal_today+" and guest_team='公牛'";
					
				String directId=serviceUtil.getStringBySql(sql, "id");
				String sql1 = "update  nba_direct set status=?,guest_score=?,host_score=? where id=?";
				Object[] params1 = {status,guestScore,hostScore,directId};
				baseDao.updateBy(sql1, params1);
				getScore(dataHref,directId);
				//录入球员得分情况
				getGameData(dataHref,directId);
//				if(trs.outerHtml().indexOf("赛中")==-1){   //如果所有比赛已经结束，则停止线程
//					String gSql="select guest_team from nba_direct where date="+TimeUtil.getTodayDecimal()+" ORDER BY game_time desc limit 0,1 ";
//					String last_guest_team=serviceUtil.getStringBySql(gSql,"guest_team");
//					if(last_guest_team.equals(guestTeam)){
//						return false;
//					}
//					 
//				}
				 
					System.out.println(guestTeam);
				 
		}
				

		} 
			}catch (IOException e) {
			  
		}
		
		return flag;

		 

	}

	 
	
//	/**
//	 * 获取NBA比赛统计数据（球员得分情况）
//	 * @param startURL
//	 */
//	public synchronized static JSONArray getGameData(String startURL,String directId) {
//		JSONObject jsonObject=new JSONObject();
//		JSONArray arr=new JSONArray();
//			String url = new String();
//			Document doc = null;
//			String teamName=null;
//			String str="";
//			try {
//				doc = Jsoup.connect(startURL).timeout(60000).get();
//				Elements tables = doc.select("table[width=730]");
//				
//				//先删除数据
//				String delete="DELETE FROM nba_game_data where directId="+directId;
//				baseDao.updateBy(delete, null);
//				//System.out.println(tables);
//				int i=1;
//				for(Element table:tables){
//					if(i<3){
//						 
//						Elements trs = table.select("tr[align=center]");
//						 
//						 
//							 teamName=table.select("tr[align=center]").first().select("td").first().ownText();
//							 if(teamName.indexOf("队技术统计")>0){
//								 teamName=teamName.replace("队技术统计", "");
//							 }
//							 if(!teamName.equals("总计")){
//									Elements trs_2 = table.select("tr[bgcolor=#FFEFB6]");
//									 
//									for (Element tr : trs_2) {
//										Elements tds = tr.select("td");
//										int count=1;                    
//										JSONObject json=new JSONObject();
//										for(Element td : tds){
//											if(count==1){
//												str="'"+td.text()+"'";
//											}else{
//												String content=td.ownText();
//												if(content.indexOf("-")!=-1){
//													int index = content.indexOf("-");
//													
//														String a = content.substring(0, index);
//														String b = content.substring(index + 1, content.length());
//														str=str+",'"+a+"'";
//														str=str+",'"+b+"'";
//												}else{
//													str=str+",'"+td.ownText()+"'";
//												}
//												
//											}
//											
//											 count++;
//										}
//										str=str+",'"+teamName+"','"+directId+"'";
//										
//										//不录入没有上场或没被激活的球员数据
//										if(tr.outerHtml().indexOf("上场")!=-1||tr.outerHtml().indexOf("激活")!=-1){
//											 
//										}else{
//											String sql="INSERT  INTO  nba_game_data(name, play_time, shoot, shoot_count,three_score, three_score_count, penalty_shot,penalty_shot_count,before_rebound, after_rebound,rebound, holding_attack,steal, swat, fault,foul, score,team,directId) values(";
//											sql=sql+str+")";
//											
//											
//											baseDao.updateBy(sql, null);
//										}
//										
////										System.out.println(str);							
//										
//									}
//									
//							 }
//							 
//						 
//					}
//					i++;
//					 
//				}
//				
//			} catch (IOException e) {
//				System.out.println("录入比赛数据出错");
//				e.printStackTrace();
//			}
//		return arr;
//
//	}
	
	
	/**
	 * 获取NBA比赛统计数据（球员得分情况）
	 * @param startURL
	 */
	public synchronized static JSONArray getGameData(String startURL,String directId) {
		JSONObject jsonObject=new JSONObject();
		JSONArray arr=new JSONArray();
			String url = new String();
			Document doc = null;
			String teamName=null;
			String str="";
			try {
				doc = Jsoup.connect(startURL).timeout(60000).get();
				Elements tables = doc.select("table[width=730]");
				
				//先删除数据
				String delete="DELETE FROM nba_game_data where directId="+directId;
				//baseDao.updateBy(delete, null);
				//System.out.println(tables);
				int i=1;
				for(Element table:tables){
					if(i<3){
						 
						Elements trs = table.select("tr[align=center]");
						 
						 
							 teamName=table.select("tr[align=center]").first().select("td").first().ownText();
							 if(teamName.indexOf("队技术统计")>0){
								 teamName=teamName.replace("队技术统计", "");
							 }
							 if(!teamName.equals("总计")){
									Elements trs_2 = table.select("tr[bgcolor=#FFEFB6]");
									 
									for (Element tr : trs_2) {
										Elements tds = tr.select("td");
										int count=1;                    
										JSONObject json=new JSONObject();
										for(Element td : tds){
											if(count==1){
												
												str="'"+td.text()+"'";
												Integer userId=getPlayerIdBy(td.text());
												str=str+",'"+userId+"'";
											}else{
												String content=td.ownText();
												if(content.indexOf("-")!=-1){
													int index = content.indexOf("-");
													
														String a = content.substring(0, index);
														String b = content.substring(index + 1, content.length());
														str=str+",'"+a+"'";
														str=str+",'"+b+"'";
												}else{
													str=str+",'"+td.ownText()+"'";
												}
												
											}
											
											 count++;
										}
										str=str+",'"+teamName+"','"+directId+"'";
										
										//不录入没有上场或没被激活的球员数据
										if(tr.outerHtml().indexOf("上场")!=-1||tr.outerHtml().indexOf("激活")!=-1){
											 
										}else{
//											String sql="INSERT  INTO  nba_game_data(name, play_time, shoot, shoot_count,three_score, three_score_count, penalty_shot,penalty_shot_count,before_rebound, after_rebound,rebound, holding_attack,steal, swat, fault,foul, score,team,directId) values(";
//											sql=sql+str+")";

											String sql="INSERT  INTO  bh_player_data(name,player_id, play_time, shoot, shoot_count,three_score, three_score_count, penalty_shot,penalty_shot_count,before_rebound, after_rebound,rebound, holding_attack,steal, swat, fault,foul, score,team,directId) values(";
											sql=sql+str+")";
											//boolean flag=ifAdd(name);
											baseDao.updateBy(sql, null);
										}
										
//										System.out.println(str);							
										
									}
									
							 }
							 
						 
					}
					i++;
					 
				}
				
			} catch (IOException e) {
				System.out.println("录入比赛数据出错");
				e.printStackTrace();
			}
		return arr;

	}
	
	/**
	 * 统计cba比赛数据
	 * @param startURL
	 */
	public static boolean getCBAGameResult(String startURL){
		Document doc=null;
		boolean flag=true;
		try {
			doc=Jsoup.connect(startURL).timeout(60000).get();
			Elements trs=doc.select("div.content").select("tbody").first().select("tr");
			for(Element tr:trs){
				int count=1;
				int status=0;
				String date="";
				String detailHref="";
				String result="";
				String score="";
				String data="";
				Elements tds=tr.select("td");
				boolean ifMatch=true;
				for(Element td:tds){  
					if(count==1){
						date=td.text().substring(0,10);
						if(!date.equals("")){
							long d=TimeUtil.dateTo(date);
							long today=TimeUtil.getTodayDecimal();
							if(today!=d){  //如果当天没有比赛则不抓取
								//ifMatch=false;
								return false;
							}
							
						}else{
							ifMatch=false;
							break;
						}
					}if(count==3){
						score=td.text();
					}
					else if(count==5){
						result=td.text();
					}else if(count==6){
						data=td.text();
						if(!data.equals("--")){
							detailHref=td.select("a[href]").first().attr("abs:href");
						}
					}	
					count++;
				}
				
				//比赛还没开始
//				if(result.equals("--")&&data.equals("--")){
//					return;
//				}
				if(score.indexOf("VS")!=-1){
					break;
				}else if(result.equals("--")){  //赛中
					status=1;
				}else{	//完场
					status=2;   
				}
				
				if(ifMatch){
					getCBAEveryScore(detailHref, status);
				}
				
			}
			
			System.out.println("OVER");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}
	
	
	public static void getCBAEveryScore(String startURL,int status){
		Document doc=null;
		try {
			doc=Jsoup.connect(startURL).timeout(60000).get();
			sum_retry_count=0;
			Elements teams=doc.select("span.team_name");
			int z=1;
			String hostTeam="";
			String guestTeam="";
			for(Element team:teams){
				if(z==1){
					hostTeam=team.text();
				}else{
					guestTeam=team.text();
				}
				z++;
				
			}
			Elements spans=doc.select("div.score_tab").select("span");
			String hostScore="";
			String guestScore="";
			String hostSum="";
			String guestSum="";
			int i=1;
			for(Element span:spans){
				if(span.text().indexOf("\u00A0")==-1){
					if(i%2==0){
						hostScore=hostScore+" "+span.text().trim();
					}else{
						guestScore=guestScore+" "+span.text().trim();
					}
					i++;
				}
				
			}
			guestScore=guestScore.substring(guestScore.indexOf(" ")+1,guestScore.length());
			hostSum=guestScore.substring(0,guestScore.indexOf(" "));
			guestSum=hostScore.substring(hostScore.lastIndexOf(" ")+1,hostScore.length());
			
			hostScore=hostScore.replace(guestSum, "");
			guestScore=guestScore.replace(hostSum, "");
//			hostScore=hostScore.substring(1,hostScore.length());
//			guestScore=guestScore.substring(1,guestScore.length());
			System.out.println(guestScore);
			System.out.println(hostScore);
			
			long decimal_today = TimeUtil.getTodayDecimal();
			String sql="select id from cba_direct where date="+decimal_today+" and guest_team='"+guestTeam+"'";		
			String directId=serviceUtil.getStringBySql(sql, "id");
			String sql1 = "update  cba_direct set status=?,guest_score=?,host_score=?,guest_every_score=?,host_every_score=? where id=?";
			Object[] params1 = {status,guestSum,hostSum,guestScore,hostScore,directId};
			baseDao.updateBy(sql1, params1);
			getCBAGameData(startURL, directId, hostTeam, guestTeam);
			
			
			
			
		} catch (IOException e) {
			if(sum_retry_count<=3){
				sum_retry_count++;
				System.out.println("CBA录入每节比分时发生错误,重新请求");
				 getCBAEveryScore(startURL,status);
				
			}else{
				sum_retry_count=0;
				return;
			}
		}
	}
	
	
	public static void getCBAGameData(String startURL,String directId,String hostTeam,String guestTeam){
		Document doc=null;
		try {
			doc=Jsoup.connect(startURL).timeout(60000).get();
			sum_retry_count=0;
			Elements divs=doc.select("div.blk_wrap");
			int divCount=1;
			//先删除数据
//			String delete="DELETE FROM cba_game_data where directId="+directId;
//			baseDao.updateBy(delete, null);
			for(Element div:divs){
				if(divCount<=2){
					Elements trs=div.select("tr");
					int trCount=1;
					for(Element tr:trs){
						
						//获取当前球队名称
						if(trCount==1){
							
						}
						
						if(trCount>1){
							Elements tds=tr.select("td");
							String str="";
							int columnIndex=1;
							for(Element td:tds){
								if(columnIndex==1){
									str="'"+td.text()+"'";
								}else{
									String content=td.ownText();
									if(content.indexOf("-")!=-1&&content.indexOf("--")==-1){
										int index = content.indexOf("-");
										int index2 = content.indexOf("(");
										content=content.substring(0,index2);
											String a = content.substring(0, index);
											String b = content.substring(index + 1, content.length());
											str=str+",'"+a+"'";
											str=str+",'"+b+"'";
									}else if(td.outerHtml().indexOf("document")!=-1){  //主队快攻和得分显示规则不一样
										String c=td.outerHtml();
										int index=c.indexOf("write");
										String z=c.substring(index+7,c.length());
										int last=z.indexOf("\"");
										String j=z.substring(0,last);
										
										index=j.indexOf("/");
										if(index!=-1){
											String a = j.substring(0, index);
											String b = j.substring(index + 1, j.length());
											str=str+",'"+a+"'";
											str=str+",'"+b+"'";
										}else{
											str=str+",'"+j+"'";
										}
										
									}else if(td.text().indexOf("/")!=-1){
										String j=td.text();
										int index=j.indexOf("/");
										String a = j.substring(0, index);
										String b = j.substring(index + 1, j.length());
										str=str+",'"+a+"'";
										str=str+",'"+b+"'";
									}else{
										str=str+",'"+td.ownText()+"'";
									}
								}
								columnIndex++;
							}
							
							if(tr.outerHtml().indexOf("--")==-1){
								System.out.println(divCount);
								String sql="INSERT  INTO  cba_game_data(name,clothNum, play_time,isFirst, shoot, shoot_count,three_score, three_score_count, penalty_shot,penalty_shot_count,before_rebound, after_rebound, holding_attack,foul,steal,fault, swat,dunk,be_invaded,quick_attack,quick_attack_count,score,team,directId) values(";
								
								if(divCount==1){
									str=str+",'"+hostTeam+"',"+directId;
								}else{
									str=str+",'"+guestTeam+"',"+directId;
								}
								sql=sql+str+")";
								baseDao.updateBy(sql, null);
								System.out.println(str);
							}
						}
						trCount++;
					}
				}
				divCount++;
			}
		} catch (IOException e) {
			if(sum_retry_count<=3){
				sum_retry_count++;
				System.out.println("发生错误,重新请求");
				getCBAGameData( startURL, directId, hostTeam, guestTeam);
				
			}else{
				sum_retry_count=0;
				return;
			}
			
		}
	}
	
	public static boolean ifAdd(String userName){
		String sql="SELECT COUNT(*) AS count FROM bh_player_data where name=? AND add_time>?";
		Object [] params={userName,TimeUtil.getToday()};
		Integer count=serviceUtil.getCountBy(sql, params);
		boolean flag=true;
		if(count==0){
			flag=false;
		}
		return flag;
	}
	
	public static Integer getPlayerIdBy(String userName){
		String sql="SELECT id AS count FROM bh_player where name=?";
		Object [] params={userName};
		Integer count=serviceUtil.getCountBySqlAndParams(sql, params);
	 
		return count;
	}
}
