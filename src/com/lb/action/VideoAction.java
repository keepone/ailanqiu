package com.lb.action;

import java.io.IOException;
import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.jiutong.test.Log;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.AnalyzeVideoUtil;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.IoUtil;
import com.lb.utils.PrintUtil;
import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerVideoByNBA98;
 
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/video")
@Controller
@Scope("prototype")
public class VideoAction extends ActionSupport {
	@Autowired
	@Qualifier("videoService")
	VideoService videoService=null;

	 
	@Action(value = "AddVideoAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String add() {
		String startURL = "http://www.nba98.com/streetball/list_184_";
		String img_savePath="D:/tomcat6/Tomcat 6.0/webapps/ROOT/video_img/";
		String one_category="03";
		String two_category="0301";
		
		 
		long start=System.currentTimeMillis(); //
		JSONArray jsonArray=GetPlayerVideoByNBA98.getVideoByNBA98(startURL);
		long end=System.currentTimeMillis(); //
		Logger logger = Logger.getLogger(Log.class);
		Logger log = Logger.getLogger("myTest1");  
    	log.info("Get Video Use Time:--------"+(end-start)/1000);  
    	log.info("Get Video Count:--------"+jsonArray.size());  
    	
    	
    	long insert_start=System.currentTimeMillis(); //
		for(int i=0;i<jsonArray.size();i++){
			long currentTime=System.currentTimeMillis();
			JSONObject json=(JSONObject) jsonArray.get(i);
			String href=json.get("href").toString();
			String img=json.get("img").toString();
			IoUtil.downloadImage(img, "",  img_savePath+currentTime+".jpg");
			String name=json.get("name").toString();
			videoService.addVideo(name, one_category, two_category, href, currentTime+".jpg", currentTime);
			
		}
		long insert_end=System.currentTimeMillis();  //
		log.info("Insertt Video Use Time:--------"+(insert_end-insert_start)/1000);  
		System.out.println("all is finished");
		
		return null;
	}

	/**
	 * 将视频转换为可以在移动端播放的视频格式
	 * @return
	 */
	@Action(value = "AnalyzeVideoAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String analyzeVideo() {
		AnalyzeVideoUtil analyzeVideoUtil=new AnalyzeVideoUtil();
		 String url=GetParameterUtil.getString("videoUrl");
		 
		 Integer recesourceType=1;
		 try {
		 if(url.indexOf("sina")!=-1){
			 //解析新浪视频
				url=analyzeVideoUtil.analyze(url);
		 }else if(url.indexOf("youku")!=-1){
			 url=analyzeVideoUtil.analyzeFromYouKu(url).replace("\"\"", "'");
		 }else if(url.indexOf("tudou")!=-1){
			 url=analyzeVideoUtil.analyzeFromTuDou(url);
		 }else if(url.indexOf("qq")!=-1){
			 url=analyzeVideoUtil.analyzeFromTencent(url);
		 }
		 }catch(Exception err1){
			 url="false";
		 }
		 PrintWriter out=PrintUtil.printWord();
		 out.print(url);
		 out.flush();
		 out.close();
		return null;
	}
	 

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
