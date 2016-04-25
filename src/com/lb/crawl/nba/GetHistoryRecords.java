package com.lb.crawl.nba;

import java.io.IOException;
import java.util.Map;

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
 
@Component
public class GetHistoryRecords {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getLianSheng("http://sports.sohu.com/20070303/n248486360.shtml");
		// getNewsDetail("http://sports.qq.com/a/20141028/058599.htm");

	}

	/**
	 * nba球队连胜
	 * @param startURL
	 */
	public static void getLianSheng(String startURL) {
		// System.out.println(Thread.currentThread().getName()+":线程正在执行");
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements trs = doc.select("div#sohu_content").first()
					.select("TBODY").first().select("tr");
			// Elements ps=div.select("p");
			int count = 1;
			String name = "";
			String year = "";
			String score = "";
			
			String sourceHref = "";

			Integer c = 0;

			for (Element tr : trs) {
				String str = "";
				if (count > 1) {
					Elements tds = tr.select("td");
					int i = 1;
					for (Element td : tds) {
						if (i == 1) {
							name=td.text();
						} else if(i==2){
							year=td.text();
							if(year.length()>4){
								year=year.substring(0,4);
							}
						}else if(i==3){
							score=td.text();
						} 
						i++;
					}
					System.out.println(str);
					String sql = "INSERT INTO lb_history_records(name,year,score,source_href,rank,rank_name) VALUES(?,?,?,?,?,?)";
					Object [] params={name,year,score,startURL,1,"NBA球队连胜"};
					baseDao.updateBy(sql, params);
				}
				count++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * nba个人单场得分排行
	 * @param startURL
	 */
	public static void getPersonalScore(String startURL) {
		// System.out.println(Thread.currentThread().getName()+":线程正在执行");
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements trs = doc.select("div#sohu_content").first()
					.select("TBODY").first().select("tr");
			// Elements ps=div.select("p");
			int count = 1;
			String name = "";
			String team="";
			String year = "";
			String score = "";
			
			String sourceHref = "";

			Integer c = 0;

			for (Element tr : trs) {
				String str = "";
				if (count > 1) {
					Elements tds = tr.select("td");
					int i = 1;
					for (Element td : tds) {
						if (i == 1) {
							name=td.text();
						} else if(i==2){
							team=td.text();
						}else if(i==3){
							year=td.text();
							year=year.substring(0,4);
						}else if(i==4){
							score=td.text();
						} 
						i++;
					}
					System.out.println(str);
					String sql = "INSERT INTO lb_history_records(name,team,year,score,source_href,rank,rank_name) VALUES(?,?,?,?,?,?,?)";
					Object [] params={name,team,year,score,startURL,2,"NBA个人单场得分"};
					baseDao.insertBy(sql, params);
				}
				count++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * nba历届得分王
	 * @param startURL
	 */
	public static void getEveryMaxScore(String startURL) {
		// System.out.println(Thread.currentThread().getName()+":线程正在执行");
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements trs = doc.select("div#sohu_content").first()
					.select("TBODY").first().select("tr");
			// Elements ps=div.select("p");
			int count = 1;
			String name = "";
			String team="";
			String year = "";
			String score = "";
			
			String sourceHref = "";

			Integer c = 0;

			for (Element tr : trs) {
				String str = "";
				if (count > 1) {
					Elements tds = tr.select("td");
					int i = 1;
					for (Element td : tds) {
						if (i == 1) {
							year=td.text();
						} else if(i==2){
							team=td.text();
						}else if(i==3){
							name=td.text();
						}else if(i==4){
							score=td.text();
						} 
						i++;
					}
					System.out.println(str);
					String sql = "INSERT INTO lb_history_records(name,year,score,source_href,rank,rank_name) VALUES(?,?,?,?,?,?)";
					Object [] params={name,year,score,startURL,3,"NBA历届得分王"};
					if(team.indexOf("NBA")!=-1){
						baseDao.insertBy(sql, params);
					}
				}
				count++;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
		
		/**
		 * nba个人总得分
		 * @param startURL
		 */
		public static void getPersonalSumScore(String startURL) {
			// System.out.println(Thread.currentThread().getName()+":线程正在执行");
			Document doc = null;
			try {
				doc = Jsoup.connect(startURL).timeout(60000).get();
				Elements trs = doc.select("div#sohu_content").first()
						.select("TBODY").first().select("tr");
				// Elements ps=div.select("p");
				int count = 1;
				String name = "";
				String team="";
				String year = "";
				String score = "";
				
				String sourceHref = "";

				Integer c = 0;

				for (Element tr : trs) {
					String str = "";
					if (count > 2) {
						Elements tds = tr.select("td");
						int i = 1;
						for (Element td : tds) {
							if(i==2){
								name=td.text();
							}else if(i==3){
								score=td.text();
							}
							i++;
						}
						System.out.println(str);
						String sql = "INSERT INTO lb_history_records(name,score,source_href,rank,rank_name) VALUES(?,?,?,?,?)";
						Object [] params={name,score,startURL,4,"NBA个人总得分"};
						 
							baseDao.insertBy(sql, params);
					 
					}
					count++;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
	}
		
		/**
		 * 获取每年的NBA总冠军
		 * @param startURL
		 * @return
		 */
		public static void getAllChampion(String startURL) {
			 
			int count = 1;
			String name = "";
			String champion="";
			String second_place="";
			String mvp="";
			String year = "";
			String score = "";
			 
			int f = 1;
			System.out.println("爬虫正在启动…………");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("爬虫开始抓取…………");

			Document doc = null;
			try {
				doc = Jsoup.connect(startURL).timeout(60000).get();
				Elements trs = doc.select("table[bordercolor=#000000]")
						.select("tr");
				for (Element tr : trs) {
					if (count > 1) {
						JSONObject jsonObject = new JSONObject();
						Elements tds = tr.select("td");
						int m = 1;
						/**
						 * <td height="20" bgcolor="#ffff99">1947</td>
							<td height="20" bgcolor="#ffff99">4.16-4.22</td>
							<td height="20" bgcolor="#ffff99">费城勇士</td>
							<td height="20" bgcolor="#ffff99">哥特莱布</td>
							<td height="20" bgcolor="#ffff99">芝加哥牡鹿</td>
							<td height="20" bgcolor="#ffff99">奥尔森</td>
							<td height="20" bgcolor="#ffff99">4-1</td>
							<td height="20" bgcolor="#ffff99">&nbsp;</td>
							
							年份
	日期
	冠军
	冠军教练
	亚军
	亚军教练
	比分
	MVP
						 */
						for (Element td : tds) {
							if(m==1){
								year=td.ownText();
							}else if(m==2){
								//日期
							}else if(m==3){
								champion=td.ownText();
							}else if(m==4){
								//冠军教练
							}else if(m==5){
								second_place=td.ownText();
							}else if(m==6){
								//亚军教练
							}else if(m==7){
								score=td.ownText();
							}else if(m==8){
								mvp=td.ownText();
								if(mvp.length()<2){
									mvp="";
								}
							}
					 
							m = m + 1;
						}
						System.out.println(mvp);
						String sql = "INSERT INTO lb_history_records(year,champion,second_place,score,mvp,source_href,rank,rank_name) VALUES(?,?,?,?,?,?,?,?)";
						Object [] params={year,champion,second_place,score,mvp,startURL,5,"NBA历届冠军"};
						baseDao.insertBy(sql, params);
						 
					} else {

					}

					count = count + 1;

				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		 
		}
		
		
		/**
		 * 历届状元
		 * @param startURL
		 */
		public static void  getBestPlayer(String startURL) {
			String name = "";
			String team="";
			String position="";
			String mvp="";
			String year = "";
			String school = "";
			
			int count = 1;
			int f = 1;
			System.out.println("爬虫正在启动…………");
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("爬虫开始抓取…………");

			Document doc = null;
			try {
				doc = Jsoup.connect(startURL).timeout(90000).get();
				Elements trs = doc.select("table.f_table").select("tr");
				for (Element tr : trs) {
					if (count <= 2) {

					} else {
						JSONObject jsonObject = new JSONObject();
						Elements tds = tr.select("td");
						int m = 1;
						for (Element td : tds) {
							String content = null;
							Element a = td.select("a[href]").first();

							if(m==1){
								year=td.ownText();
							}else if(m==2){
								if (a != null) {
									team = a.ownText(); //状元
									 
								} else {
									team = td.ownText();
								 
								}
							}else if(m==3){
								if (a != null) {
									mvp = a.ownText(); //状元
									 
								} else {
									mvp = td.ownText();
								 
								}
							}else if(m==4){
								position=td.ownText();
							}else if(m==5){
								school=td.ownText();
							}
							m=m+1;
						}
						
						System.out.println(mvp);
						String sql = "INSERT INTO lb_history_records(year,team,best_player,position,school,source_href,rank,rank_name) VALUES(?,?,?,?,?,?,?,?)";
						Object [] params={year,team,mvp,position,school,startURL,6,"NBA历届状元"};
						 
							baseDao.insertBy(sql, params);
						 
					}

					count = count + 1;

				}
			} catch (IOException e) {

				e.printStackTrace();
			}

		 

		}
	
}
