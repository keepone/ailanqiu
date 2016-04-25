package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import org.jsoup.Connection;
import org.jsoup.Jsoup;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;

import com.opensymphony.xwork2.ActionSupport;
 
@Component
public class GetDataRankingBySina extends ActionSupport {
	private static BaseDao baseDao;
	private static ServiceUtil serviceUtil = null;
	private static String no="";
    private static String name="";
    private static String team="";
    private static String average_score="";
    private static String score_count="";
    private static String shoot_rate="";
    private static String shoot_count="";
    private static String shoot_into_count="";
    private static String three_rate="";
    private static String three_into_count="";
    private static String three_count="";
    private static String penalty_rate="";
    private static String penalty_into_count="";
    private static String penalty_count="";
    private static String average_play_time="";
    private static String avereage_swat="";
    private static String swat_count="";
    private static String play_count="";
    private static String average_rebound="";
    private static String rebound_count="";
    private static String average_holding="";
    private static String holding_count="";
    private static String average_fault="";
    private static String fault_count="";
    private static String average_steal="";
    private static String steal_count="";
	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		GetDataRankingBySina.serviceUtil = serviceUtil;
	}

	private static List<JSONObject> list = new ArrayList<JSONObject>();

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		// http://search.sina.com.cn/?q=&range=all&c=news&sort=time&SL2=nbavideo&PID=&col=&source=&from=&country=&size=&time=&a=&page=
		// String startURL =
		// "http://search.sina.com.cn/?q=&range=all&c=news&sort=time&SL2=nbavideo&PID=&col=&source=&from=&country=&size=&time=&a=&page=1&pf=2132801744&ps=2132735600&dpc=1";
		// get("http://search.sina.com.cn/?q=&range=title&c=news&sort=time&SL2=nbavideo&PID=");
		// getSinaVideo(startURL);
		// System.out.println(2%101);
		 getDataRanking("http://nba.sports.sina.com.cn/league_order1.php?dpc=1");
		//getPlayerScoreRanking("http://nba.sports.sina.com.cn/playerstats.php?s=0&e=49&key=1&t=1");

	}

	/**
	 * 字符串转换unicode
	 */
	public static String string2Unicode(String string) {

		StringBuffer unicode = new StringBuffer();

		for (int i = 0; i < string.length(); i++) {

			// 取出每一个字符
			char c = string.charAt(i);

			// 转换为unicode
			unicode.append("\\u" + Integer.toHexString(c));
		}

		return unicode.toString();
	}

	// 获取东西部排名
	public static void getDataRanking(String startURL) {
		int count = 1;
		int f = 1;
		// 起始时间
		long begin_time = System.currentTimeMillis();

		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(30000).get();

			// 因为数据量只有30条，每次执行删除再添加
			String delete_sql = "delete from nba_team_ranking";
			baseDao.updateBy(delete_sql, null);
		} catch (IOException e) {
			Logger log = Logger.getLogger("fail");  
	    	log.info("获取东西部排名\r\n--------------------------------------------------------------------------------------------\r\n");  
		}
		Integer department = 1;
		Elements trs = doc.select("tr[bgcolor=#FFEFB6]");
		for (Element tr : trs) {
			Elements tds = tr.select("td");
			String value = "";
			int z = 0;
			if (count > 15) {
				department = 2;
			}
			for (Element td : tds) {
				if (z == 0) {
					value = value + "'" + td.text() + "'";
				} else {
					value = value + "," + "'" + td.text() + "'";
				}
				z++;
			}
			value = value + ",'" + department + "'";

			String sql = "INSERT INTO nba_team_ranking(NO,team,win,lose,win_rate,win_difference,get_score,lose_score,point_spread,host_grade,guest_grade,department_grade,area_grade,score_less_three,score_more_ten,score_less_hundred, score_more_hundread,recently_ten, win_lose,department) VALUES("
					+ value + ")";
			Object[] params = { value };
			baseDao.updateBy(sql, null);
			count++;
		}

		 

	}

	/*
	 * //获取NBA球员排名前10(得分榜 | 篮板榜 | 助攻榜 | 抢断榜 | 盖帽榜 | 失误榜 | 神投榜 | 三分榜 | 罚球榜
	 * )http://nba.sports.sina.com.cn/playerstats.php?s=0&e=49&key=1&t=1 public
	 * static void getPlayerRanking(String startURL){ Map<String, String>
	 * map=new HashMap<String, String>(); int count=1; int f = 1; //起始时间 long
	 * begin_time=System.currentTimeMillis(); for(int key=1;key<=9;key++){
	 * boolean mark=true; while(mark){ for(int i=1;i<2;i++){ Document doc =
	 * null; try { String url=new String();
	 * url=startURL+(i-1)*50+"&e="+(50*i-1)+"&key="+key+"&t=1"; doc =
	 * Jsoup.connect(url).timeout(30000).get(); //System.out.println(doc); }
	 * catch (IOException e) { System.out.println("发生异常,再次请求.................");
	 * try { Thread.sleep(5000); getPlayerRanking(startURL);
	 * System.out.println("重写请求的地址是："+startURL); } catch (InterruptedException
	 * e1) { System.out.println("线程出现问题");
	 * 
	 * }
	 * 
	 * 
	 * }
	 * 
	 * Elements trs = doc.select("table.text").select("tr");
	 * 
	 * int l=0; for(Element tr:trs){ if(l>0){ if(l<=10){ String sql=""; Elements
	 * tds = tr.select("td"); if(key==1){ int z=0; String name=""; String
	 * value=""; for(Element td:tds){ if(z==1){ name=td.text();
	 * value=value+"'"+td.text()+"'"; }else if(z>1){
	 * value=value+","+"'"+td.text()+"'"; } z++; } map.put(name, value);
	 * System.out.println(value); }else{ mark=false; break; }
	 * 
	 * //value=value+",'"+department+"'";
	 * 
	 * //因为数据量只有30条，每次执行删除再添加 String delete_sql="delete from nba_team_ranking";
	 * //baseDao.updateBy(delete_sql, null);//sql=
	 * "INSERT INTO nba_team_ranking(NO,team,win,lose,win_rate,win_difference,get_score,lose_score,point_spread,host_grade,guest_grade,department_grade,area_grade,score_less_three,score_more_ten,score_less_hundred, score_more_hundread,recently_ten, win_lose,department) VALUES("
	 * +value+")";
	 * 
	 * //baseDao.updateBy(sql, null); count++; }else{ mark=false; break; } }
	 * l++; }
	 * 
	 * if(l==1){ mark=false; break; } }
	 * 
	 * 
	 * 
	 * } }
	 * 
	 * Iterator<String> iter = map.keySet().iterator();
	 * 
	 * while (iter.hasNext()) {
	 * 
	 * String key = iter.next();
	 * 
	 * String value = map.get(key); System.out.println(key+"---------"+value);
	 * 
	 * } System.out.println(map.size());
	 * 
	 * 
	 * 
	 * System.out.println("球队排名统计:"+count+"支球队");
	 * 
	 * 
	 * 
	 * 
	 * 
	 * }
	 */

	// 获取NBA球员排名前10(得分榜 | 篮板榜 | 助攻榜 | 抢断榜 | 盖帽榜 | 失误榜 | 神投榜 | 三分榜 | 罚球榜
	// )http://nba.sports.sina.com.cn/playerstats.php?s=0&e=49&key=1&t=1
	public static void getPlayerScoreRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='得分'";
		baseDao.updateBy(delete_sql, null);

		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				Logger log = Logger.getLogger("fail");  
		    	log.info("获取NBA球员排名－－得分\r\n--------------------------------------------------------------------------------------------\r\n");
			}
			Elements trs = doc.select("table.text").select("tr");

			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								average_score = td.text();
							} else if (z == 4) {
								 
								score_count = td.text();
							} else if (z == 5) {
								 
								shoot_rate = td.text();
							}  else if (z ==6) {
								three_rate = td.text();
							}
							 else if (z == 7) {
								 
									penalty_rate = td.text();
								} else if (z == 8) {
									 
									average_play_time = td.text();
								}  else if (z ==9) {
									play_count = td.text();
								}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(no,name,team,average_score,score_count,shoot_rate,three_rate,penalty_rate,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,average_score,score_count,shoot_rate,three_rate,penalty_rate,average_play_time,play_count,"得分"};
						baseDao.updateBy(sql, params);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 
						// value=value+",'"+department+"'";
						// 因为数据量只有30条，每次执行删除再添加

						count++;
					}else {
						mark = false;
						break;
					}
				}
				l++;
			}

			if (l == 1) {
				mark = false;
				break;
			}

		}
		 
//		 
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		getPlayerBoardRanking("http://nba.sports.sina.com.cn/playerstats.php?key=2&t=1");
		System.out.println("篮板排行录入完成");
		getPlayerHoldingRanking("http://nba.sports.sina.com.cn/playerstats.php?key=3&t=1");
		System.out.println("助攻排行录入完成");
		getPlayerStealRanking("http://nba.sports.sina.com.cn/playerstats.php?key=4&t=1");
		System.out.println("抢断排行录入完成");
		getPlayerSwatRanking("http://nba.sports.sina.com.cn/playerstats.php?key=5&t=1");
		System.out.println("盖帽排行录入完成");
		getPlayerFaultRanking("http://nba.sports.sina.com.cn/playerstats.php?key=6&t=1");
		System.out.println("失误排行录入完成");
		getPlayerShootRanking("http://nba.sports.sina.com.cn/playerstats.php?key=7&t=1");
		System.out.println("投篮排行录入完成");
		getPlayerThreeRanking("http://nba.sports.sina.com.cn/playerstats.php?key=8&t=1");
		System.out.println("三分排行录入完成");
		getPlayerPenaltyRanking("http://nba.sports.sina.com.cn/playerstats.php?key=9&t=1");
		System.out.println("排行榜录入完成");
		Logger log = Logger.getLogger("success");  
    	log.info("获取NBA球员排名\r\n--------------------------------------------------------------------------------------------\r\n");  

	}
	//篮板排行榜
	public static void getPlayerBoardRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='篮板'";
		baseDao.updateBy(delete_sql, null);
		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				 
			}
			Elements trs = doc.select("table.text").select("tr");
			  String no="";
	          String name="";
	          String team="";
		      String average_rebound="";
		      String rebound_count="";
		      String average_play_time="";
		      String play_count="";
			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								average_rebound = td.text();
							} else if (z == 4) {
								 
								rebound_count = td.text();
							} else if (z == 5) {
								 
								average_play_time = td.text();
							}  else if (z ==6) {
								play_count = td.text();
							}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(NO,name,team,average_rebound,rebound_count,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,average_rebound,rebound_count,average_play_time,play_count,"篮板"};
						baseDao.updateBy(sql, params);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						map.put(name, value);
						System.out.println(value);
						// value=value+",'"+department+"'";
						// 因为数据量只有30条，每次执行删除再添加

						count++;
					} else {
						mark = false;
						break;
					}
				}
				l++;
			}

			if (l == 1) {
				mark = false;
				break;
			}

		}
	}
	
	//助攻排行榜
	public static void getPlayerHoldingRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='助攻'";
		baseDao.updateBy(delete_sql, null);
		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				Logger log = Logger.getLogger("fail");  
		    	log.info("获取NBA球员排名－－助攻\r\n--------------------------------------------------------------------------------------------\r\n");  
			}
			Elements trs = doc.select("table.text").select("tr");
			  
			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								average_holding = td.text();
							} else if (z == 4) {
								 
								 holding_count= td.text();
							} else if (z == 5) {
								 
								average_play_time = td.text();
							}  else if (z ==6) {
								play_count = td.text();
							}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(NO,name,team,average_holding,holding_count,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,average_holding,holding_count,average_play_time,play_count,"助攻"};
						baseDao.updateBy(sql, params);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						count++;
					} else {
						mark = false;
						break;
					}
				}
				l++;
			}

			if (l == 1) {
				mark = false;
				break;
			}

		}
	}
	
	//抢断排行榜
	public static void getPlayerStealRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='抢断'";
		baseDao.updateBy(delete_sql, null);
		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				 
			}
			Elements trs = doc.select("table.text").select("tr");
			  
			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								average_steal = td.text();
							} else if (z == 4) {
								 
								 steal_count= td.text();
							} else if (z == 5) {
								 
								average_play_time = td.text();
							}  else if (z ==6) {
								play_count = td.text();
							}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(NO,name,team,average_steal,steal_count,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,average_steal,steal_count,average_play_time,play_count,"抢断"};
						baseDao.updateBy(sql, params);
					 
						 

						count++;
					} else {
						mark = false;
						break;
					}
				}
				l++;
			}
			
			if (l == 1) {
				mark = false;
				break;
			}

		}
	}
	//盖帽排行榜
	public static void getPlayerSwatRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='盖帽'";
		baseDao.updateBy(delete_sql, null);
		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				 
			}
			Elements trs = doc.select("table.text").select("tr");
			  
			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								avereage_swat = td.text();
							} else if (z == 4) {
								 
								 swat_count= td.text();
							} else if (z == 5) {
								 
								average_play_time = td.text();
							}  else if (z ==6) {
								play_count = td.text();
							}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(NO,name,team,average_swat,swat_count,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,avereage_swat,swat_count,average_play_time,play_count,"盖帽"};
						baseDao.updateBy(sql, params);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 
						count++;
					} else {
						mark = false;
						break;
					}
				}
				l++;
			}

			if (l == 1) {
				mark = false;
				break;
			}

		}
	}
	//失误排行榜
	public static void getPlayerFaultRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='失误'";
		baseDao.updateBy(delete_sql, null);
		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				 
			}
			Elements trs = doc.select("table.text").select("tr");
			  
			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								average_fault = td.text();
							} else if (z == 4) {
								 
								 fault_count= td.text();
							} else if (z == 5) {
								 
								average_play_time = td.text();
							}  else if (z ==6) {
								play_count = td.text();
							}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(NO,name,team,average_fault,fault_count,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,average_fault,fault_count,average_play_time,play_count,"失误"};
						baseDao.updateBy(sql, params);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						count++;
					} else {
						mark = false;
						break;
					}
				}
				l++;
			}

			if (l == 1) {
				mark = false;
				break;
			}

		}
	}
	
	
	
	

	

	
	
	// 神投榜 排行榜
	public static void getPlayerShootRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='投篮'";
		baseDao.updateBy(delete_sql, null);
		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				 
			}
			Elements trs = doc.select("table.text").select("tr");
			  
			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								shoot_rate = td.text();
							} else if (z == 4) {
								 
								 shoot_into_count= td.text();
							} else if (z == 5) {
								 
								shoot_count = td.text();
							}  else if (z ==6) {
									average_play_time = td.text();
							}
							 else if (z ==7) {
									play_count = td.text();
								}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(NO,name,team,shoot_rate,shoot_into_count,shoot_count,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,shoot_rate,shoot_into_count,shoot_count,average_play_time,play_count,"投篮"};
						baseDao.updateBy(sql, params);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						count++;
					} else {
						mark = false;
						break;
					}
				}
				l++;
			}

			if (l == 1) {
				mark = false;
				break;
			}

		}
	}
	
	
	
	// 三分 排行榜
	public static void getPlayerThreeRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='三分'";
		baseDao.updateBy(delete_sql, null);
		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				 
			}
			Elements trs = doc.select("table.text").select("tr");
			  
			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								three_rate = td.text();
							} else if (z == 4) {
								 
								 three_into_count= td.text();
							} else if (z == 5) {
								 
								three_count = td.text();
							}  else if (z ==6) {
									average_play_time = td.text();
							}
							 else if (z ==7) {
									play_count = td.text();
								}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(NO,name,team,three_rate,three_into_count,three_count,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,three_rate,three_into_count,three_count,average_play_time,play_count,"三分"};
						baseDao.updateBy(sql, params);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						count++;
					} else {
						mark = false;
						break;
					}
				}
				l++;
			}

			if (l == 1) {
				mark = false;
				break;
			}

		}
	}
	
	
	
	// 罚球 排行榜
	public static void getPlayerPenaltyRanking(String startURL) {
		Map<String, String> map = new HashMap<String, String>();
		int count = 1;
		int f = 1;
		
		// 起始时间
		long begin_time = System.currentTimeMillis();
		String delete_sql = "delete from nba_player_ranking where rank='罚球'";
		baseDao.updateBy(delete_sql, null);
		boolean mark = true;
		while (mark) {

			Document doc = null;
			try {
				String url = new String();

				doc = Jsoup.connect(startURL).timeout(30000).get();
				// System.out.println(doc);
			} catch (IOException e) {
				 
			}
			Elements trs = doc.select("table.text").select("tr");
			  
			int l = 0;
			for (Element tr : trs) {
				if (l > 0) {
					if (l <= 10) {
						String sql = "";
						Elements tds = tr.select("td");
						int z = 0;
					
						String value = "";
						for (Element td : tds) {
							if (z == 0) {
								no=td.text();

							} else if (z == 1) {
								 
								name = td.text();
							}else if (z == 2) {
								 
								team = td.text();
							} else if (z == 3) {
								 
								penalty_rate = td.text();
							} else if (z == 4) {
								 
								 penalty_into_count= td.text();
							} else if (z == 5) {
								 
								penalty_count = td.text();
							}  else if (z ==6) {
									average_play_time = td.text();
							}
							 else if (z ==7) {
									play_count = td.text();
								}
							z++;
						}
						 

						sql = "INSERT INTO nba_player_ranking(NO,name,team,penalty_rate,penalty_into_count,penalty_count,average_play_time,play_count,rank) VALUES(?,?,?,?,?,?,?,?,?)";
						Object[] params={no,name,team,penalty_rate,penalty_into_count,penalty_count,average_play_time,play_count,"罚球"};
						baseDao.updateBy(sql, params);
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						 
						count++;
					} else {
						mark = false;
						break;
					}
				}
				l++;
			}

			if (l == 1) {
				mark = false;
				break;
			}

		}
	}
	
}
