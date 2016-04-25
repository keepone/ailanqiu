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
	 * ��ȡnba��cba¼��
	 * @param startURL
	 */
	public static void getNBALuXiang(String startURL) {
		System.out.println(Thread.currentThread().getName()+":¼�����¼���߳�����ִ��");
		Document doc = null;
		String date="";
		
		try {
			doc = Jsoup.connect(startURL).timeout(60000).get();
			Element div=doc.select("div.titlebar").first();
			date=div.select("h2").first().text().substring(0,6).trim();
			SimpleDateFormat format=new SimpleDateFormat("M��dd��");
			String month=format.format(System.currentTimeMillis());
			 
			if(!date.equals(month)){  //�������û�б�����ץȡ
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
					
					//�����������Ƶ¼��(������Ӣ��¼��)����ץȡ������Ƶ¼��
					if(doc.outerHtml().indexOf("������Ƶ")!=-1&&doc.outerHtml().indexOf("Ӣ��ԭ��")==-1){
						String gameName=doc.select("div.title").select("h1").first().text(); //��ȡ���⣺���磺12��31��NBA������ ���vs���� ȫ��¼��
						for(Element a:as){
							System.out.println(as);
							if(a.outerHtml().indexOf("sina.com")!=-1){  //ֻ��ȡ������Ƶ
								if(count<=4){
									name=gameName.replace("ȫ��¼��", "");
									String aTXT=a.text();   //<a href="http://video.sina.com.cn/p/sports/k/v/2014-12-31/140864454595.html" target="_blank"><strong>[����/�ֻ�/PAD�ۿ�]</strong></a>
									if(aTXT.indexOf("����")!=-1){
										name=name+"��"+count+"��";
										
										String sql1="select count(*) AS count from lb_resource where NAME like '%[����]"+name+"%'";
										
										
										c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
										if(c==0){
													sourceHref=a.attr("abs:href");  //��Ѷ��Ƶ��������
													 
													 
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
														name="[����]"+name;
														String teamLogo="";
														if(name.indexOf("vs")!=-1){
															int index1=name.indexOf(" ");
															teamLogo=name.substring(index1+1,name.length());
															int index2=teamLogo.indexOf("vs");
															teamLogo=teamLogo.substring(0,index2);
															String host_sql="select sourceLogo from nba_team where name like '%"+teamLogo+"%'";
															teamLogo=serviceUtil.getStringBySql(host_sql, "sourceLogo");
														}
														
														
														Object[] params={teamLogo,name,videoHref,sourceHref,current,current,detailHref,one_cateGory,two_cateGory,2,"����",directId};  
														baseDao.insertBy(sql, params);
														System.out.println("NBA------"+name+"------"+videoHref);
												 
												 
									}else{
										System.out.println(gameName+"����¼��û�и��£���������ֱ����");
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
								name=a.text().replace("¼��", "").replace("[]", "");
								if(name.indexOf("����")==-1){
									String sql1="select count(*) AS count from lb_resource where NAME like '%"+name+"%'";
									c=Integer.parseInt(((Map)baseDao.findBy(sql1, null).get(0)).get("count").toString());
									if(c==0){
										String source="��Ѷ";
												sourceHref=a.attr("abs:href");  //��Ѷ��Ƶ��������
												if(sourceHref.indexOf("qq.com")!=-1||sourceHref.indexOf("cntv")!=-1){  //ֻץȡ��Ѷ,CNTV��Ƶ¼��	
													if(sourceHref.indexOf("cntv")==-1){
														int index=sourceHref.indexOf("vid=");
														videoHref=sourceHref.substring(index+4,sourceHref.length());  
														videoHref="http://v.qq.com/iframe/player.html?vid="+videoHref+"&tiny=0&auto=0";
														name="[��Ѷ]"+name;
													}else{
														source="CNTV";
														name="[CNTV]"+name;
														videoHref=sourceHref;
													} 
													String one_cateGory="01";
													String two_cateGory="010212";
													String hostTeamName=name.substring(name.indexOf("��")+1,name.length());
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
									System.out.println(name+"����¼��û�и��£���������ֱ����");
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
			System.out.println("IO�쳣���ٴ˷���ץȡ��");
			getNBALuXiang(startURL);
			 
		}
		
		 
	}

	 
}
