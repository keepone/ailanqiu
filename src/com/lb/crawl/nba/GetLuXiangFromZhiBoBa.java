package com.lb.crawl.nba;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

import javax.annotation.Resource;
 

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.TimeUtil;
 

@Component
public class GetLuXiangFromZhiBoBa {
	private static BaseDao baseDao;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	private static ServiceUtil serviceUtil=null;
	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		getNBALuXiang("http://www.zhibo8.cc/nba/luxiang.htm");
		//getNewsDetail("http://sports.qq.com/a/20141028/058599.htm");

	}
	 
	/**
	 * 获取nba和cba录像
	 * @param startURL
	 */
	public static void getNBALuXiang(String startURL) {
		System.out.println(Thread.currentThread().getName()+":录入比赛录像线程正在执行");
		Document doc = null;
		String date="";
		
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Element div=doc.select("div.titlebar").first();
			date=div.select("h2").first().text().substring(0,6).trim();
			SimpleDateFormat format=new SimpleDateFormat("M月dd日");
			String month=format.format(System.currentTimeMillis());
			 
			if(!date.equals(month)){  //如果当天没有比赛则不抓取
				return;
			}else{
				Elements detailHrefs=doc.select("div.content").first().select("a");
				for(Element detail:detailHrefs){
					String detailHref=detail.attr("abs:href");
					doc = Jsoup.connect(detailHref).timeout(60000).get();
					Elements as=doc.select("div.content").first().select("a");
					//Elements ps=div.select("p");
					int count=1;
					String name="";
					String sourceHref="";
					String videoHref="";
					String content="";
					String img="";
					Integer c=0;
					
					//如果有新浪视频录像(不包含英文录像)，则抓取新浪视频录像
					if(doc.outerHtml().indexOf("新浪视频")!=-1&&doc.outerHtml().indexOf("英语原声")==-1){
						String gameName=doc.select("div.title").select("h1").first().text(); //获取标题：例如：12月31日NBA常规赛 掘金vs湖人 全场录像
						for(Element a:as){
							System.out.println(as);
							if(a.outerHtml().indexOf("sina.com")!=-1){  //只获取新浪视频
								if(count<=4){
									name=gameName.replace("全场录像", "");
									String aTXT=a.text();   //<a href="http://video.sina.com.cn/p/sports/k/v/2014-12-31/140864454595.html" target="_blank"><strong>[窗口/手机/PAD观看]</strong></a>
									if(aTXT.indexOf("窗口")!=-1){
										name=name+"第"+count+"节";
										
										String sql1="select count(*) AS count from lb_resource where NAME like '%[新浪]"+name+"%'";
										
										
										c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
										if(c==0){
													sourceHref=a.attr("abs:href");  //腾讯视频播放链接
													 
													 
														String one_cateGory="01";
														String two_cateGory="010212";
														String hostTeamName=name.substring(gameName.indexOf(" ")+1,name.length());
														int index=hostTeamName.indexOf("vs");
														hostTeamName=hostTeamName.substring(0,index);
														
														int directId=0;
														if(name.indexOf("NBA")==-1){
															one_cateGory="02";
															two_cateGory="020212";
															
															long decimal_today = TimeUtil.getTodayDecimal();
															String sql="select id from cba_direct where date="+decimal_today+" and(host_team like '%"+hostTeamName+"%' or guest_team like '%"+hostTeamName+"%') ";		
															
															String id=serviceUtil.getStringBySql(sql, "id");
															directId=Integer.valueOf(id);
															
														}else{

															long decimal_today = TimeUtil.getTodayDecimal();
															String sql="select id from nba_direct where date="+decimal_today+" and(host_team like '%"+hostTeamName+"%' or guest_team like '%"+hostTeamName+"%') ";		
															
															 String id=serviceUtil.getStringBySql(sql, "id");
															directId=Integer.valueOf(id);
														}
														String sql="INSERT INTO lb_resource(img,name,video_href,sourceVideoHref,addTime,updateTime,source_href,one_category,two_category,resourceType,source,directId) values(?,?,?,?,?,?,?,?,?,?,?,?)";							
														AnalyzeVideoUtil analyze=new AnalyzeVideoUtil();
														try {
															videoHref=analyze.analyze(sourceHref);
														} catch (Exception e) {
															// TODO Auto-generated catch block
															e.printStackTrace();
														}
														long current=System.currentTimeMillis();
														name="[新浪]"+name;
														String teamLogo="";
														if(name.indexOf("vs")!=-1){
															int index1=name.indexOf(" ");
															teamLogo=name.substring(index1+1,name.length());
															int index2=teamLogo.indexOf("vs");
															teamLogo=teamLogo.substring(0,index2);
															String host_sql="select sourceLogo from nba_team where name like '%"+teamLogo+"%'";
															teamLogo=serviceUtil.getStringBySql(host_sql, "sourceLogo");
														}
														
														
														Object[] params={teamLogo,name,videoHref,sourceHref,current,current,detailHref,one_cateGory,two_cateGory,2,"新浪",directId};  
														baseDao.insertBy(sql, params);
														System.out.println("NBA------"+name+"------"+videoHref);
												 
												 
									}else{
										System.out.println(gameName+"该区录像没有更新，－－来自直播吧");
										//break;
									}	
										count++;
									}
									
								}
							}
							
						}
					}else{
						for(Element a:as){
							if(count<=4){
								name=a.text().replace("录像", "").replace("[]", "");
								if(name.indexOf("窗口")==-1){
									String sql1="select count(*) AS count from lb_resource where NAME like '%"+name+"%'";
									c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
									if(c==0){
										String source="腾讯";
												sourceHref=a.attr("abs:href");  //腾讯视频播放链接
												if(sourceHref.indexOf("qq.com")!=-1||sourceHref.indexOf("cntv")!=-1){  //只抓取腾讯,CNTV视频录像	
													if(sourceHref.indexOf("cntv")==-1){
														int index=sourceHref.indexOf("vid=");
														videoHref=sourceHref.substring(index+4,sourceHref.length());  
														videoHref="http://v.qq.com/iframe/player.html?vid="+videoHref+"&tiny=0&auto=0";
														name="[腾讯]"+name;
													}else{
														source="CNTV";
														name="[CNTV]"+name;
														videoHref=sourceHref;
													} 
													String one_cateGory="01";
													String two_cateGory="010212";
													String hostTeamName=name.substring(name.indexOf("赛")+1,name.length());
													hostTeamName=hostTeamName.substring(0,hostTeamName.indexOf("vs")).replace(" ", "");
													int directId=0;
													if(name.indexOf("NBA")==-1){
														one_cateGory="02";
														two_cateGory="020212";
														
														long decimal_today = TimeUtil.getTodayDecimal();
														String sql="select id from cba_direct where date="+decimal_today+" and(host_team like '%"+hostTeamName+"%' or guest_team like '%"+hostTeamName+"%') ";		
														
														String id=serviceUtil.getStringBySql(sql, "id");
														if(id.equals("")){
															break;
														}
														directId=Integer.valueOf(id);
														
													}else{

														long decimal_today = TimeUtil.getTodayDecimal();
														String sql="select id from nba_direct where date="+decimal_today+" and(host_team like '%"+hostTeamName+"%' or guest_team like '%"+hostTeamName+"%') ";		
														
														 String id=serviceUtil.getStringBySql(sql, "id");
														directId=Integer.valueOf(id);
													}
													
													String teamLogo="";
													
													if(name.indexOf("NBA")!=-1){
														if(name.indexOf("vs")!=-1){
															int index1=name.indexOf(" ");
															teamLogo=name.substring(index1+1,name.length());
															int index2=teamLogo.indexOf("vs");
															teamLogo=teamLogo.substring(0,index2);
															String host_sql="select sourceLogo from nba_team where name like '%"+teamLogo+"%'";
															teamLogo=serviceUtil.getStringBySql(host_sql, "sourceLogo");
														}
													}else{
														if(name.indexOf("vs")!=-1){
															int index1=name.indexOf(" ");
															teamLogo=name.substring(index1+1,name.length());
															int index2=teamLogo.indexOf("vs");
															teamLogo=teamLogo.substring(0,index2);
															String host_sql="select source_logo from cba_team where name like '%"+teamLogo+"%'";
															teamLogo=serviceUtil.getStringBySql(host_sql, "source_logo");
														}
													}
													
													
													String sql="INSERT INTO lb_resource(img,name,video_href,sourceVideoHref,addTime,updateTime,source_href,one_category,two_category,resourceType,source,directId) values(?,?,?,?,?,?,?,?,?,?,?,?)";							
													Object[] params={teamLogo,name,videoHref,sourceHref,System.currentTimeMillis(),System.currentTimeMillis(),detailHref,one_cateGory,two_cateGory,2,source,directId};  
													baseDao.insertBy(sql, params);
													System.out.println("NBA------"+name+"------"+videoHref);
												}
											 
								}else{
									System.out.println(name+"该区录像没有更新，－－来自直播吧");
									//break;
								}	
									count++;
								}
								
							}
							
						}
					}
					
					
						 
				}
			}
			
		} catch (IOException e1) {
			System.out.println("IO异常：再此发起抓取：");
			getNBALuXiang(startURL);
			 
		}
		
		 
	}

	 
}
