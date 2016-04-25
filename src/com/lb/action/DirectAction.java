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
import com.lb.service.DirectService;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.IoUtil;
import com.lb.utils.PageBean;
import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerVideoByNBA98;
 
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/direct")
@Controller
@Scope("prototype")
public class DirectAction extends PageAction {
	private PageBean pageBean=null;
	private String result=null;
	@Autowired
	@Qualifier("videoService")
	VideoService videoService=null;
	
	@Qualifier("directService")
	DirectService directService=null;
	 
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

	@Action(value = "DirectListAction", results = {
			@Result(name = "success", location = "/jsp/direct_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String direct() {
		pageBean=directService.getDirectSinceToday(getPage().getPageNo(), getPage().getPageSize());
		ActionContext actionContext = ActionContext.getContext();
		actionContext.getSession().put("page", pageBean);
		result = SUCCESS;
		return result;
	}
	 

	public void setVideoService(VideoService videoService) {
		this.videoService = videoService;
	}

	public void setDirectService(DirectService directService) {
		this.directService = directService;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
