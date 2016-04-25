package com.lb.service.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.jiutong.test.Log;
import com.lb.dao.BaseDao;
import com.lb.service.ServiceUtil;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.IoUtil;
import com.lb.utils.TimeUtil;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetNBAInfoUtil;
import com.lucene.crawl.GetPlayerVideoBySina;
import com.lucene.crawl.GetPlayerVideoByYouKu;
import com.lucene.crawl.GetTvDirectByBroadcast;
 

/**
 * 
 * @author Allen
 * @date 2014-11-9
 * @fuction
 * 
 */
@Service("videoService")
@Component
public class VideoServiceImpl implements VideoService {

	BaseDao baseDao = null;

	@Resource(name = "baseDAO")
	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	ServiceUtil serviceUtil=null;
	@Resource(name = "serviceUtil")
	public void setServiceUtil(ServiceUtil serviceUtil) {
		this.serviceUtil = serviceUtil;
	}
	@Override
	public Integer addVideo(String name, String oneCategory,
			String twoCategory, String href, String img, long currentTime) {
		String sql = "insert into nba_video(name,one_category,two_category,href,img,addTime) values(?,?,?,?,?,?)";
		Object[] params = { name, oneCategory, twoCategory, href, img,
				currentTime };
		return baseDao.insertBy(sql, params);
	}

	

	
	
	
	//@Scheduled(cron="0 0/1 18,23 * * ?")  
	public void addBestPlayer() {
		System.out.println("begin add best player^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		String sql="INSERT  INTO  nba_best_player(year,team,name,position,school) values(?,?,?,?,?)";
		String startURL = "http://sports.163.com/13/0626/22/92B1N94S00051CA1.html";
		JSONArray jsonArray=null;
	 	jsonArray =GetNBAInfoUtil.getBestPlayer(startURL);
		
		for(int i=0;i<jsonArray.size();i++){
			JSONObject player=(JSONObject) jsonArray.get(i);
			String year=player.get("td0").toString(); 
			String team=player.get("td1").toString();
			String name=player.get("td2").toString();
			String position=player.get("td3").toString();
			String school=player.get("td4").toString();
			Object[] params={year,team,name,position,school};
			Integer count=baseDao.insertBy(sql, params);	 
		}
		
	}
	
	
	// @Scheduled(cron="0 0/1 17,23 * * ?")  
	public void addAllChampion() {
		System.out.println("begin add best player^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		String sql="INSERT  INTO  nba_champion(year,date,name,coach,second_name,second_coach,score,mvp) values(?,?,?,?,?,?,?,?)";
		String startURL = "http://sports.sina.com.cn/nba/finals.shtml";
		JSONArray jsonArray=null;
	 	jsonArray =GetNBAInfoUtil.getAllChampion(startURL);
		for(int i=0;i<jsonArray.size();i++){
			JSONObject player=(JSONObject) jsonArray.get(i);
			String year=player.get("td0").toString(); 
			String date=player.get("td1").toString();
			String name=player.get("td2").toString();
			String coach=player.get("td3").toString();
			String second_name=player.get("td4").toString();
			String second_coach=player.get("td5").toString();
			String score=player.get("td6").toString();
			String mvp=player.get("td7").toString();
			Object[] params={year,date,name,coach,second_name,second_coach,score,mvp};
			Integer count=baseDao.insertBy(sql, params);	 
		}
	}
	
	
	/**
	 * 添加NBA所有运动员
	 */
	// @Scheduled(cron="0 47 11,* * * ?")  
	public  void addAllPlayer() {
		System.out.println("begin add best player^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		String sql="INSERT  INTO  nba_player(name, english_name, height, weight, school, into_nba, nba_age, team, into_reason, position, career_max, season_max, birthday, cloth_num, img, birth_place, age) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String startURL = "http://nba.sports.sina.com.cn/players.php?dpc=1";
		JSONArray jsonArray=null;
	 	jsonArray =GetNBAInfoUtil.getAllPlayer(startURL); 
		for(int i=0;i<jsonArray.size();i++){
			JSONObject json=(JSONObject) jsonArray.get(i);
			long current_time=0;String name="";String english_name="";String height="";String weight="";String school="";String into_nba="";String nba_age="";String team="";String into_reason="";String position="";String career_max="";String season_max="";String birthday="";String cloth_num="";String img="";String birth_place="";String age="";
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
						current_time=System.currentTimeMillis();
						IoUtil.downloadImage(img, "", "D:/tomcat6/Tomcat 6.0/webapps/ROOT/player_img/"+current_time+".jpg");
						img=current_time+".jpg";
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
			Object[] params={ name, english_name, height, weight, school, into_nba, nba_age, team, into_reason, position, career_max, season_max, birthday, cloth_num, img, birth_place, age};
			baseDao.insertBy(sql, params);
		}
	}
	

	//增加新浪视频(球星视频)（http://search.sina.com.cn/?q=&range=all&c=news&sort=time&SL2=nbavideo&PID=&col=&source=&from=&country=&size=&time=&a=&page=1&pf=2121726312&ps=2132736388&dpc=1）
	//@Scheduled(cron="0 2 1 * * ?")  
	public void addSinaVideo() throws Exception {
		long begin_time=System.currentTimeMillis();
		AnalyzeVideoUtil getSinaMp4Util=new AnalyzeVideoUtil();
		System.out.println("机器人007正在赶来：............................");
		Thread.sleep(1000);
		System.out.println("机器人007已经就位：............................");
		System.out.println("主人，我是机器人007，按照你的指示，007马上向新浪视频库发起第一波攻击............................");
		Thread.sleep(1000);
		System.out.println("开始攻击............................");
		String startURL = "http://search.sina.com.cn/?q=%B0%AC%B8%A5%C9%AD&range=all&c=news&sort=time&SL2=nbavideo&PID=";
		GetPlayerVideoBySina.getSinaVideo(startURL);
	}



	
	
	//增加优酷视频(NIKE系列教学)
	//@Scheduled(cron="0 1 3 * * ?")  
	public void addYouKuVideoFromNike() throws Exception {
		long begin_time=System.currentTimeMillis();
		System.out.println("机器人007正在赶来：............................");
		Thread.sleep(1000);
		System.out.println("机器人007已经就位：............................");
		System.out.println("主人，我是机器人007，按照你的指示，007马上向新浪视频库发起第一波攻击............................");
		Thread.sleep(1000);
		System.out.println("开始攻击............................");
		//加农贝克系列教学
		String startURL ="http://www.soku.com/search_video/q_NIKE%E7%B2%BE%E5%93%81%E7%AF%AE%E7%90%83%E6%95%99%E5%AD%A6_orderby_1_page_";
		GetPlayerVideoByYouKu.getYouKuVideoFromNike(startURL);
	}
}
