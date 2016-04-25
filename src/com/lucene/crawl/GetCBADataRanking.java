package com.lucene.crawl;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
 
@Component
public class GetCBADataRanking {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public static void main(String[] args) {
		//String url = "http://roll.sports.sina.com.cn/s_cba_all/index.shtml";
		//getTeamRanking("http://cba.sports.sina.com.cn/cba/stats/teamrank/?dpc=1");
		getWCBAScoreRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=pts&qleagueid=160&qround=");
	}

	/**
	 * 获取cba球队排行榜
	 * @param startURL
	 */
	public static void getTeamRanking(String startURL) {
		 String no="";
		 String team=null;
		 String win=null;
		 String lose=null;
		 String win_rate=null;
		 String win_lose=null;
		Document doc = null;
		try {
			doc = Jsoup.connect("http://cba.sports.sina.com.cn/cba/stats/teamrank/?dpc=1").timeout(60000).get();
			Elements trs = doc.select("div.blk_wrap").first().select("tbody").first().select("tr");
			baseDao.updateBy("DELETE FROM cba_team_ranking", null);
			for (Element tr : trs) {
				Elements tds=tr.select("td");
				int count=1;
				String str="";
				for(Element td:tds){
					if(count==1){
						no=td.text();
					}else if(count==2){
						team=td.text();
					}else if(count==3){
						win=td.text();
					}else if(count==4){
						lose=td.text();
					}else if(count==5){
						win_rate=td.text();
					}else if(count==10){
						win_lose=td.text();
					}
					count++;
				}
				String sql="INSERT INTO cba_team_ranking (no,team,win,lose,win_rate,win_lose) VALUES(?,?,?,?,?,?)";
				Object[] params={no,team,win,lose,win_rate,win_lose};
				baseDao.insertBy(sql, params);
				System.out.println(str);
			}
			

		} catch (IOException e) {
			 
		}

	}
	
	public static String getPlayerRanking(String href){
		try {
			String no="";
			 String team=null;
			 String name=null;
			 String data=null;
			 String rank=null;
			for(int i=1;i<=10;i++){
				
				//删除对应排行规则
				String delete="DELETE FROM cba_player_rank where rankNo="+i;
				baseDao.updateBy(delete, null);
				
				String url=new String();
				url=href+i;
				Document doc = Jsoup.connect(url).timeout(60000).get();
				Elements trs=doc.select("table.dataTable").first().select("tr");
				int index=1;
				for(Element tr:trs){
					if(index>2){
						Elements tds=tr.select("td");
						int count=1;
						for(Element td:tds){
							if(count==1){
								no=td.text();
							}else if(count==2){
								name=td.text();
							}else if(count==3){
								team=td.text();
							}
							
							if(i==1&&count==6){
								data=td.text();
								rank="得分";
							}else if(i==2&count==6){
								data=td.text();
								rank="篮板";
							}else if(i==3&&count==6){
								data=td.text();
								rank="助攻";
							}else if(i==4&&count==8){
								data=td.text();
								rank="抢断";
							}else if(i==5&&count==10){
								data=td.text();
								rank="盖帽";
							}else if(i==6&&count==6){
								data=td.text();
								rank="失误";
							}else if(i==7&&count==8){
								data=td.text();
								rank="犯规";
							}else if(i==8&&count==8){
								data=td.text();
								rank="投篮命中率";
							}else if(i==9&&count==10){
								data=td.text();
								rank="罚球命中率";
							}else if(i==10&&count==9){
								data=td.text();
								rank="三分球命中率";
							}
							count++;
						}
						
						String sql="INSERT INTO cba_player_rank(no,name,team,data,rankNo,rank) VALUES(?,?,?,?,?,?)";
						Object[] params={no,name,team,data,i,rank};
						baseDao.insertBy(sql, params);
					}
					index++;
				}
				
			}
		
		} catch (IOException e) {
			Logger log = Logger.getLogger("fail");  
	    	log.info("获取CBA球员排名\r\n--------------------------------------------------------------------------------------------\r\n");  
		}
		return "";
	}
	
	
	public static String getWCBAScoreRanking(String href){
		try {
			String no="";
			 String team=null;
			 String name=null;
			 String data=null;
			 String rank=null;

				 
				String url=new String();
				Document doc = Jsoup.connect(href).timeout(60000).get();
				Elements trs=doc.select("div.blk_wrap").first().select("tbody").select("tr");
				int index=1;
				for(Element tr:trs){
					if(index<=20){
						Elements tds=tr.select("td");
						int count=1;
						for(Element td:tds){
							if(count==1){
								no=td.text();
							}else if(count==2){
								name=td.text();
							}else if(count==3){
								team=td.text();
							}else if(count==5){
								data=td.text();
								rank="得分";
							} 
							count++;
						}					
						String sql="INSERT INTO wcba_player_rank(no,name,team,data,rankNo,rank) VALUES(?,?,?,?,?,?)";
						Object[] params={no,name,team,data,1,rank};
						baseDao.insertBy(sql, params);
						System.out.println(name+"--"+team+"---"+data+"---"+rank);
					}
					index++;
				}
				
				getWCBAReboundRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=lb&qleagueid=160&qround=");
				getWCBAHoldingRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=ast&qleagueid=160&qround=");
				getWCBAStealRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=st&qleagueid=160&qround=");
				getWCBAGaiMaoRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=bs&qleagueid=160&qround=");
				getWCBAShiWuRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=to&qleagueid=160&qround=");
				getWCBAFanGuiRanking("http://cba.sports.sina.com.cn/wcba/stats/playerstats/?qorder=pf&qleagueid=160&qround=");
				
				
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 篮板排行
	 * @param href
	 * @return
	 */
	public static String getWCBAReboundRanking(String href){
		try {
			String no="";
			 String team=null;
			 String name=null;
			 String data=null;
			 String rank=null;


				String url=new String();
				Document doc = Jsoup.connect(href).timeout(60000).get();
				Elements trs=doc.select("div.blk_wrap").first().select("tbody").select("tr");
				int index=1;
				for(Element tr:trs){
					if(index<=20){
						Elements tds=tr.select("td");
						int count=1;
						for(Element td:tds){
							if(count==1){
								no=td.text();
							}else if(count==2){
								name=td.text();
							}else if(count==3){
								team=td.text();
							}else if(count==9){
								data=td.text();
								rank="篮板";
							} 
							count++;
						}					
						String sql="INSERT INTO wcba_player_rank(no,name,team,data,rankNo,rank) VALUES(?,?,?,?,?,?)";
						Object[] params={no,name,team,data,2,rank};
						baseDao.insertBy(sql, params);
						System.out.println(name+"--"+team+"---"+data+"---"+rank);
					}
					index++;
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	

	/**
	 * 助攻排行
	 * @param href
	 * @return
	 */
	public static String getWCBAHoldingRanking(String href){
		try {
			String no="";
			 String team=null;
			 String name=null;
			 String data=null;
			 String rank=null;
				String url=new String();
				Document doc = Jsoup.connect(href).timeout(60000).get();
				Elements trs=doc.select("div.blk_wrap").first().select("tbody").select("tr");
				int index=1;
				for(Element tr:trs){
					if(index<=20){
						Elements tds=tr.select("td");
						int count=1;
						for(Element td:tds){
							if(count==1){
								no=td.text();
							}else if(count==2){
								name=td.text();
							}else if(count==3){
								team=td.text();
							}else if(count==12){
								data=td.text();
								rank="助攻";
							} 
							count++;
						}					
						String sql="INSERT INTO wcba_player_rank(no,name,team,data,rankNo,rank) VALUES(?,?,?,?,?,?)";
						Object[] params={no,name,team,data,3,rank};
						baseDao.insertBy(sql, params);
						System.out.println(name+"--"+team+"---"+data+"---"+rank);
					}
					index++;
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 抢断排行
	 * @param href
	 * @return
	 */
	public static String getWCBAStealRanking(String href){
		try {
			String no="";
			 String team=null;
			 String name=null;
			 String data=null;
			 String rank=null;
				String url=new String();
				Document doc = Jsoup.connect(href).timeout(60000).get();
				Elements trs=doc.select("div.blk_wrap").first().select("tbody").select("tr");
				int index=1;
				for(Element tr:trs){
					if(index<=20){
						Elements tds=tr.select("td");
						int count=1;
						for(Element td:tds){
							if(count==1){
								no=td.text();
							}else if(count==2){
								name=td.text();
							}else if(count==3){
								team=td.text();
							}else if(count==13){
								data=td.text();
								rank="抢断";
							} 
							count++;
						}					
						String sql="INSERT INTO wcba_player_rank(no,name,team,data,rankNo,rank) VALUES(?,?,?,?,?,?)";
						Object[] params={no,name,team,data,4,rank};
						baseDao.insertBy(sql, params);
						System.out.println(name+"--"+team+"---"+data+"---"+rank);
					}
					index++;
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 盖帽排行
	 * @param href
	 * @return
	 */
	public static String getWCBAGaiMaoRanking(String href){
		try {
			String no="";
			 String team=null;
			 String name=null;
			 String data=null;
			 String rank=null;
				String url=new String();
				Document doc = Jsoup.connect(href).timeout(60000).get();
				Elements trs=doc.select("div.blk_wrap").first().select("tbody").select("tr");
				int index=1;
				for(Element tr:trs){
					if(index<=20){
						Elements tds=tr.select("td");
						int count=1;
						for(Element td:tds){
							if(count==1){
								no=td.text();
							}else if(count==2){
								name=td.text();
							}else if(count==3){
								team=td.text();
							}else if(count==15){
								data=td.text();
								rank="盖帽";
							} 
							count++;
						}					
						String sql="INSERT INTO wcba_player_rank(no,name,team,data,rankNo,rank) VALUES(?,?,?,?,?,?)";
						Object[] params={no,name,team,data,5,rank};
						baseDao.insertBy(sql, params);
						System.out.println(name+"--"+team+"---"+data+"---"+rank);
					}
					index++;
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 失误排行
	 * @param href
	 * @return
	 */
	public static String getWCBAShiWuRanking(String href){
		try {
			String no="";
			 String team=null;
			 String name=null;
			 String data=null;
			 String rank=null;
				String url=new String();
				Document doc = Jsoup.connect(href).timeout(60000).get();
				Elements trs=doc.select("div.blk_wrap").first().select("tbody").select("tr");
				int index=1;
				for(Element tr:trs){
					if(index<=20){
						Elements tds=tr.select("td");
						int count=1;
						for(Element td:tds){
							if(count==1){
								no=td.text();
							}else if(count==2){
								name=td.text();
							}else if(count==3){
								team=td.text();
							}else if(count==16){
								data=td.text();
								rank="失误";
							} 
							count++;
						}					
						String sql="INSERT INTO wcba_player_rank(no,name,team,data,rankNo,rank) VALUES(?,?,?,?,?,?)";
						Object[] params={no,name,team,data,6,rank};
						baseDao.insertBy(sql, params);
						System.out.println(name+"--"+team+"---"+data+"---"+rank);
					}
					index++;
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	/**
	 * 犯规排行
	 * @param href
	 * @return
	 */
	public static String getWCBAFanGuiRanking(String href){
		try {
			String no="";
			 String team=null;
			 String name=null;
			 String data=null;
			 String rank=null;
				String url=new String();
				Document doc = Jsoup.connect(href).timeout(60000).get();
				Elements trs=doc.select("div.blk_wrap").first().select("tbody").select("tr");
				int index=1;
				for(Element tr:trs){
					if(index<=20){
						Elements tds=tr.select("td");
						int count=1;
						for(Element td:tds){
							if(count==1){
								no=td.text();
							}else if(count==2){
								name=td.text();
							}else if(count==3){
								team=td.text();
							}else if(count==17){
								data=td.text();
								rank="犯规";
							} 
							count++;
						}					
						String sql="INSERT INTO wcba_player_rank(no,name,team,data,rankNo,rank) VALUES(?,?,?,?,?,?)";
						Object[] params={no,name,team,data,7,rank};
						baseDao.insertBy(sql, params);
						System.out.println(name+"--"+team+"---"+data+"---"+rank);
					}
					index++;
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
