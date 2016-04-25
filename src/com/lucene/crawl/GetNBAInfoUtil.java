package com.lucene.crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.jiutong.test.Log;
import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.IoUtil;
import com.lb.utils.StrUtil;
 
@Component
public class GetNBAInfoUtil {
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

	public static void main(String[] args) {
		
		String startURL = "http://sports.163.com/13/0626/22/92B1N94S00051CA1.html";
		//getSpecial("http://china.nba.com/");
		//getAllChampion("http://sports.sina.com.cn/nba/finals.shtml");
		getAllPlayer("http://nba.sports.sina.com.cn/players.php?dpc=1");
		
	}

	public static JSONArray getBestPlayer(String startURL) {
		JSONArray jsonArray = new JSONArray();
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
					int m = 0;
					for (Element td : tds) {
						String content = null;
						Element a = td.select("a[href]").first();
						if (a != null) {
							content = a.ownText();
							jsonObject.put("td" + m, content);
							m += 1;
						} else {
							content = td.ownText();
							jsonObject.put("td" + m, content);
							m = m + 1;
						}

					}
					jsonArray.add(jsonObject);
				}

				count = count + 1;

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return jsonArray;

	}
	
	/**
	 * 获取每年的NBA总冠军
	 * @param startURL
	 * @return
	 */
	public static JSONArray getAllChampion(String startURL) {
		JSONArray jsonArray = new JSONArray();
		int count = 1;
		String name = "";
		String champion="";
		String second_place;
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
						}else if(m==7){
							mvp=td.ownText();
						}
				 
						m = m + 1;
					}
					
					jsonArray.add(jsonObject);
				} else {

				}

				count = count + 1;

			}
		} catch (IOException e) {

			e.printStackTrace();
		}

		return jsonArray;

	}
	
	/**
	 * 获取联盟所有现役球员
	 * @param startURL
	 * @return
	 */
	public static JSONArray getAllPlayer(String startURL) {
		JSONArray jsonArray = new JSONArray();
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
			Connection conn  = Jsoup.connect(startURL);
			doc=conn.timeout(60000).get();
			conn.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");
			Elements trs = doc.select("tr#playerslist");
			 
			for (Element tr : trs) {
				 Elements tds=tr.select("td");
					for(Element td:tds){
						if(td.ownText()!=null&&!td.ownText().equals("")){
							JSONObject json = new JSONObject();
							Element a=td.select("a").first();
							String detail_href=a.attr("abs:href");
							String name=a.ownText();
							String clothNum=td.ownText();
							json=getPlayerById(detail_href);
							json.put("td0", name);
							json.put("td1", clothNum);
							
							
							long current_time=0;  name="";String english_name="";String height="";String weight="";String school="";String into_nba="";String nba_age="";String team="";String into_reason="";String position="";String career_max="";String season_max="";String birthday="";String cloth_num="";String img="";String birth_place="";String age="";
							for(int j=0;j<json.size();j++){
								if(j==0){
									String content=json.get("td"+j).toString();
									int index=content.indexOf(",");
									name=content.substring(0,index);
									english_name=content.substring(index+1,content.length());
								}else if(j==1){
									cloth_num=json.get("td"+j).toString().trim();
								}else if(j==2){
									position=json.get("td"+j).toString();
								}else if(j==3){
									team=json.get("td"+j).toString();
								}else if(j==4){
									img=json.get("td"+j).toString();
									if(img!=null&&!img.equals("")){
//										current_time=System.currentTimeMillis();
//										IoUtil.downloadImage(img, "", "D:/tomcat6/Tomcat 6.0/webapps/ROOT/player_img/"+current_time+".jpg");
//										img=current_time+".jpg";
									}
									
								}else if(j==5){
									birthday=json.get("td"+j).toString();
								}else if(j==6){
									age=json.get("td"+j).toString();
								}else if(j==7){
									birth_place=json.get("td"+j).toString();
								}else if(j==8){
									school=json.get("td"+j).toString();
								}else if(j==9){
									height=json.get("td"+j).toString();
								}else if(j==10){
									weight=json.get("td"+j).toString();
								}else if(j==11){
									into_nba=json.get("td"+j).toString();
								}else if(j==12){
									nba_age=json.get("td"+j).toString();
								}else if(j==13){
									into_reason=json.get("td"+j).toString();
								}else if(j==14){
									season_max=json.get("td"+j).toString();
								}else if(j==15){
									career_max=json.get("td"+j).toString();
								} 
								
							}
							//String sql="SELECT id as count from nba_team where name= "+team;
							//"position":"前锋","weight":"103公斤(228磅)","height":"1.98米(6英尺6英寸)
							//	public String[] pgArr = { "PG", "SG", "C", "SF",
						//"PF" };
						
						   int position_2=1;
						   int height_2=0;
						   int weight_2=0;
						   int draft=2005;
						   if(position!=null&&!position.equals("")){
							   if(position.indexOf("小前锋")!=-1){
									position_2=4;
								}else if(position.indexOf("大前锋")!=-1){
									position_2=5;
								}else if(position.indexOf("中锋")!=-1){
									position_2=3;
								}else{
									position_2=1;
								}
						   }
						   if(height!=null&&!height.equals("")){
							   height_2=Integer.parseInt(StrUtil.getBeforeStrBySubString(height, "米").replace(".", ""));
						   }
						   if(weight!=null&&!weight.equals("")){
							   weight_2=Integer.parseInt(StrUtil.getBeforeStrBySubString(weight, "公".replace(".", "")));
						   }
						   if(into_nba!=null&&!into_nba.equals("")&&into_nba.trim().length()>1){
							   draft=Integer.parseInt(into_nba.replace("年", ""));
						   }
							
							Integer teamId=getNBATeamIdBy(team.replace("队", ""));
							
//							String sql="INSERT  INTO bh_player(name, english_name, height, weight, school, draft, nba_age, team_id, draft_grade, position, career_max, season_max, birthday, cloth_num, img, birth_place, age) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							String sql="UPDATE bh_player set team_id=? where name=?";
														
//							Object[] params={ name, english_name, height_2, weight_2, school, draft, nba_age, teamId, into_reason, position_2, career_max, season_max, birthday, cloth_num, img, birth_place, age};
							Object[] params={ teamId,name};
													
//							baseDao.insertBy(sql, params);
							baseDao.updateBy(sql, params);
							
							
							//jsonArray.add(json); 
							System.out.println(name);
						}
					}
				 } 

				count = count + 1;

		 
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return jsonArray;

	}
	/**
	 * Get every player
	 * @param startURL
	 * @return
	 */
	public static JSONObject getPlayerById(String startURL) {
		String ifFault=startURL;
		JSONObject json=new JSONObject();
		int count = 1;
		int m = 2;
		int f = 1;
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			String position=doc.select("div#table730top").select("p").first().ownText();
			if(position.indexOf("中锋")!=-1){
				position="中锋";
				json.put("td"+m, position);
				m+=1;
			}else if(position.indexOf("前锋")!=-1){
				position="前锋";
				json.put("td"+m, position);
				m+=1;
			}else{
				position="后卫";
				json.put("td"+m, position);
				m+=1;
			}
			String teamName=doc.select("div#table730top").first().select("a").first().ownText();
			json.put("td"+m, teamName);
			m+=1;
			Elements trs = doc.select("tr[bgcolor=#fcac08]");
				for (Element tr : trs) {
					JSONObject jsonObject = new JSONObject();
					Elements tds = tr.select("td");
					if(count==1){
						int tdindex=1;
						for (Element td : tds) {
							if(tdindex==1){
								String img = td.select("img").first().attr("src");
								json.put("td"+m, img);
								tdindex+=1;
								m+=1;
							}else if(tdindex==2||tdindex==4){
								tdindex+=1;
								 
							}else if(tdindex==3||tdindex==5){
								String content = td.ownText();
								json.put("td"+m, content);
								tdindex+=1;
								m+=1;
							} 
						}
					}else if(2<=count&&count<=4||count==8){
						int tdindex=1;
						for (Element td : tds) {
							if(tdindex==1||tdindex==3){
								tdindex+=1;	 
							}else if(tdindex==2||tdindex==4){
								String content = td.ownText();
								json.put("td"+m, content);
								tdindex+=1;
								m+=1;
								
							} 
						}
					}else if(5<=count&&count<=6){
					 
					}else if(count==7){
						String content = tr.select("td").last().ownText();
						json.put("td"+m, content);
						m+=1;
					}
					count = count + 1;
				} 
			 
				

		 
		} catch (IOException e) {
			 
				 
				 System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------");
			 
		}

		return json;

	}
	
	
	/**
	 * Get every player
	 * @param startURL
	 * @return
	 *//*
	public static JSONArray getAllPlayer(String startURL) {
		JSONArray jsonArray = new JSONArray();
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
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements hrefs = doc.select("div.players_left").select("li").select("span").select("a[href]");
			for (Element href : hrefs) {
					JSONObject jsonObject = new JSONObject();
					String detail_href=href.attr("href");
					String teamName=href.ownText();
					jsonObject.put("teamName", teamName);
					jsonArray.add(jsonObject);
				} 

				count = count + 1;

		 
		} catch (IOException e) {

			e.printStackTrace();
		}

		return jsonArray;

	}
	*//**
	 * Get every player
	 * @param startURL
	 * @return
	 *//*
	public static JSONArray getPlayerById(String startURL) {
		JSONArray jsonArray = new JSONArray();
		int count = 1;
		int f = 1;
		Document doc = null;
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Elements trs = doc.select("table.players_table").select("tr");
			if(count>1){
				for (Element tr : trs) {
					JSONObject jsonObject = new JSONObject();
					Elements tds = tr.select("td");
					int m = 0;
					for (Element td : tds) {
						String content = null;
						Element e=td.select("img").first();
						if(e!=null){
							content=e.attr("src");
							jsonObject.put("td" + m, content);
							m = m + 1;
						}else if(td.select("a").first()!=null){
							content=e.attr("href");
							jsonObject.put("td" + m, content);
							m = m + 1;
						}else{
							content=td.ownText();
							jsonObject.put("td" + m, content);
							m = m + 1;
						}
						 
					}
					jsonArray.add(jsonObject);
				} 
			}
				count = count + 1;

		 
		} catch (IOException e) {

			e.printStackTrace();
		}

		return jsonArray;

	}*/
	
	/**
	 * 获取来自NBA中文网的专栏作家模块信息
	 * @param startURL
	 * @return
	 */
	public static void getSpecial(String startURL) {
		String content=null;
		Document doc = null;
		try {
			Connection conn  = Jsoup.connect(startURL);
			doc=conn.timeout(60000).get();
			conn.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");
			Element element=doc.select("div.column-writer-i").first();
			String href=element.select("div.img").select("a[href]").first().attr("abs:href");
			String sourceCover=element.select("div.img").select("img").first().attr("src");
			String title=element.select("div.txt").select("strong").first().ownText();
			content = getSpecialContent(href);
			String sql1="select count(*) AS count from nba_special where NAME='"+title+"'";
			Integer c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
			//只有数据库不存在该条记录时才爬取数据
			if(c==0){
				String sql="INSERT  INTO  nba_special(sourceCover,name,content,addTime) values(?,?,?,?)";
				Object[] params={sourceCover,title,content,System.currentTimeMillis()};
			 	baseDao.insertBy(sql, params);
			}
			 
			 
		 
		} catch (IOException e) {
			 
		}

		 

	}
	
	 
	/**
	 * 获取专栏内容
	 * @param startURL
	 * @return
	 */
	public static String getSpecialContent(String startURL) {
		String content=null;
		System.out.println("爬虫正在启动…………");
		Document doc = null;
		try {
			Connection conn  = Jsoup.connect(startURL);
			doc=conn.timeout(60000).get();
			conn.header(
					"User-Agent",
					"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2 Googlebot/2.1");
			 content = doc.select("div.passage1-cont").first().outerHtml();
		} catch (IOException e) {
		}

		return content;

	}
	
	public  static Integer getNBATeamIdBy(String teamName){
		String sql="select id as count from nba_team where name like ?";
		Object [] params={"%"+teamName+"%"};
		return serviceUtil.getCountBySqlAndParams(sql, params);
	}
}
