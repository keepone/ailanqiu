package com.lb.app.action;

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
import com.lb.app.service.NewsForAppService;
import com.lb.service.DirectService;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerVideoByNBA98;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/newsapp")
@Controller
@Scope("prototype")
public class NewsForAppAction extends ActionSupport {
	private JSONArray news_array=null;
	private JSONObject news=null;
	private String result=null;
	@Autowired
	@Qualifier("newsForAppService")
	NewsForAppService newsForAppService=null;
	 
	//新闻列表
	@Action(value = "NewsListForApp", results = {
			@Result(name = "success", location = "/index.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getNewsList() {
		Integer pageNo=GetParameterUtil.getInteger("pageNo");
		Integer pageSize=GetParameterUtil.getInteger("pageSize");
		news_array=newsForAppService.getAllNews(pageNo, pageSize);
		return SUCCESS;
	}
	
	//新闻列表(返回JSON)
	@Action(value = "NewsListJsonForApp", results = {
			@Result(name = "success", location = "/index.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getNewsList_json() {
		Integer pageNo=GetParameterUtil.getInteger("pageNo");
		Integer pageSize=GetParameterUtil.getInteger("pageSize");
		news_array=newsForAppService.getAllNews(pageNo, pageSize);
		PrintWriter out=PrintUtil.printWord();
		out.print(news_array);
		out.flush();
		out.close();
		return null;
	}
	
	//新闻详情
	@Action(value = "NewsDetailForApp", results = {
			@Result(name = "success", location = "/news_detail.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getNewsDetail() {
		Integer newsId=GetParameterUtil.getInteger("newsId");
		news=newsForAppService.getNewsByNewsId(newsId);
		return SUCCESS;
	}
//	@Action(value = "NewsDetailForApp", results = {
//			@Result(name = "success", location = "/jsp/admin.jsp"),
//			@Result(name = "input", location = "/jsp/admin.jsp") })
//	public String getNewsDetail() {
//		Integer newsId=GetParameterUtil.getInteger("newsId");
//		news=newsForAppService.getNewsByNewsId(newsId);
//		PrintWriter out=PrintUtil.printWord();
//		out.print(news);
//		out.flush();
//		out.close();
//		return null;
//	}

	public void setNewsForAppService(NewsForAppService newsForAppService) {
		this.newsForAppService = newsForAppService;
	}

	public JSONArray getNews_array() {
		return news_array;
	}

	public void setNews_array(JSONArray newsArray) {
		news_array = newsArray;
	}

	public JSONObject getNews() {
		return news;
	}

	public void setNews(JSONObject news) {
		this.news = news;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
 
	 

	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
