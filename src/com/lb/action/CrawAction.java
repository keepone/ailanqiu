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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import com.jiutong.test.Log;
import com.lb.craw.cba.GetCBANewsByXinHua;
import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNBAVideoFromHuPu;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lb.crawl.nba.GetNewsFromTBBA;
import com.lb.service.NewsService;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.lb.utils.SourceUtil;

import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerVideoByNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;
 
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/crawer")
@Controller
@Scope("prototype")
public class CrawAction extends PageAction {
	@Autowired
	@Qualifier("newsService")
	NewsService newsService = null;
	 

	@Action(value = "AddNBANewsAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addNBANews() {
		GetNBANewsFromZhiBoBa.getNewsList("http://news.zhibo8.cc/nba");
		GetNewsFromNBA98.getNewsList("http://www.nba98.com/news/");
		GetNewsFromTBBA.getNewsList("http://www.tbba.com.cn/");
		GetNBANewsFromSina.getNewsBySina("http://sports.sina.com.cn/nba/");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	@Action(value = "AddNBAVideoAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addNBAVideo() {
		GetPlayerVideoBySina.getEveryDayMatchVideo("http://roll.sports.sina.com.cn/s_nbavideo_video_big/index_");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
		
	}
	
	
	@Action(value = "AddCBANewsAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addCBANews() {
		GetCBANewsByXinHua.getNewsList("http://www.news.cn/sports/cba.htm");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
		
	}
	
	@Action(value = "AddLuXiangAction", results = {
			@Result(name = "success", location = "/editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addLuXiang() {
		GetLuXiangFromZhiBoBa.getNBALuXiang("http://www.zhibo8.cc/nba/luxiang.htm");
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}
	
	
	
	@Action(value = "AddHuPuVideoAction", results = {
			@Result(name = "success", type = "redirectAction", location = "/jsp/add_company.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String add() {
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/game");//比赛
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/Cavaliers"); //骑士
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/lakers"); //湖人
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/heat"); //热火
		GetNBAVideoFromHuPu.addVideo("http://v.opahnet.com/nba/new/rockets"); //火箭
		GetNBAVideoFromHuPu.addTouTiao("http://v.opahnet.com/nba"); //首页推荐
		GetNBAVideoFromHuPu.addTouTiao("http://v.opahnet.com/nba/new/cba"); //CBA
		GetNBAVideoFromHuPu.addTouTiao("http://v.opahnet.com/nba/hot"); // 热门
		
		PrintWriter out=PrintUtil.printWord();
		out.print("录入完毕");
		out.flush();
		out.close();
		return null;
	}

 

	public void setNewsService(NewsService newsService) {
		this.newsService = newsService;
	}

	 
	
	
	

}
