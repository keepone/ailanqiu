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
import com.lb.app.service.LBSForAppService;
import com.lb.craw.cba.GetCBANewsByXinHua;
import com.lb.craw.cba.GetCBANewsFromSoHu;
import com.lb.craw.cba.GetCBANewsFromTencent;
import com.lb.craw.common.GetBasketballRule;
import com.lb.craw.common.GetTactics;
import com.lb.crawl.nba.GetHistoryRecords;
import com.lb.crawl.nba.GetLuXiangFromZhiBoBa;
import com.lb.crawl.nba.GetNBACommentFromNBAChina;
import com.lb.crawl.nba.GetNBANewsFromSina;
import com.lb.crawl.nba.GetNBANewsFromSoHu;
import com.lb.crawl.nba.GetNBANewsFromTencent;
import com.lb.crawl.nba.GetNBANewsFromZhiBoBa;
import com.lb.crawl.nba.GetNBAVideoFromHuPu;
import com.lb.crawl.nba.GetNewsFromNBA98;
import com.lb.crawl.nba.GetNewsFromTBBA;
import com.lb.crawl.nba.GetSpecialFrom163;
import com.lb.crawl.utils.GetBasketballPlace;
import com.lb.service.NewsService;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.service.impl.DirectServiceImpl;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.lb.utils.SourceUtil;
import com.lucene.crawl.GetCBADataRanking;
import com.lucene.crawl.GetCBANewsBySina;
import com.lucene.crawl.GetDataRankingBySina;
import com.lucene.crawl.GetEuropeBasketball;
import com.lucene.crawl.GetGameResult;
import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerVideoByNBA98;
import com.lucene.crawl.GetPlayerVideoBySina;
import com.lucene.crawl.GetPlayerVideoByYouKu;
import com.lucene.crawl.GetTvDirectByBroadcast;
import com.lucene.crawl.GetWCBANews;
 
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/lbs")
@Controller
@Scope("prototype")
public class LBSAction extends PageAction {
	@Autowired
	@Qualifier("lbsForAppService")
	LBSForAppService lbsForAppService=null;
	JSONArray jsonArray = null;
	private String content = null;
	private JSONObject json = null;
	private String specialId = null;
	private String specialName = null;
	private String result = null;
	private PageBean pageBean = null;
	private String newsId=null;
	private String newsTitle=null;
	private String clickType = "非查询";

	private String name;
	private String img;
	private String telephone;
	private String workTime;
	private String province;
	private String city;
	private String area;
	private String areaDetail;
	
	
	/**
	 * 爬虫录入球场
	 * @return
	 */
	@Action(value = "AddBasketballCourt", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addCourt() {
		
		 
		 GetBasketballPlace getBasketballPlace=new GetBasketballPlace();
		 getBasketballPlace.addCourtByCraw();
		return null;
	}

	/**
	 * 【用户】录入球场
	 * @return
	 */
	@Action(value = "AddCourtByUser", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addCourtByUser() {
		 GetBasketballPlace getBasketballPlace=new GetBasketballPlace();
		 name=GetParameterUtil.getString_2("name");
		 img=GetParameterUtil.getString_2("img");
		 telephone=GetParameterUtil.getString_2("telephone");
		 workTime=GetParameterUtil.getString_2("workTime");
		 province=GetParameterUtil.getString_2("province");
		 city=GetParameterUtil.getString_2("city");
		 area=GetParameterUtil.getString_2("area");
		 areaDetail=GetParameterUtil.getString_2("areaDetail");
		 Integer status=getBasketballPlace.addCourtByUser(name, img, telephone, workTime, province, city, area, areaDetail);
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		 json.put("result", status);
		out.print(json);
		out.flush();
		out.close();
		 
		 return null;
	}
	
	
	/**
	 * 搜索球场  121.51431918973-----31.308624023569
	 * @return
	 */
	@Action(value = "SearchBasketballCourt", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String search() {
		 String lng=GetParameterUtil.getString("lng");
		 String lat=GetParameterUtil.getString("lat");
		 Integer pageNo=GetParameterUtil.getInteger("pageNo"); 
		 jsonArray=lbsForAppService.getCourt(pageNo, lng, lat);		
		 PrintWriter out=PrintUtil.printWord();
		 out.print(jsonArray);
		 out.flush();
		 out.close();
		return null;
	}

	/**
	 * 搜索球场  121.51431918973-----31.308624023569
	 * @return
	 */
	@Action(value = "GetBasketballCourtDetail", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String detail() {
		 Integer courtId=GetParameterUtil.getInteger("courtId");
		 JSONObject json=lbsForAppService.getCourtDetail(courtId);
		 PrintWriter out=PrintUtil.printWord();
		 out.print(json);
		 out.flush();
		 out.close();
		return null;
	}

	public void setLbsForAppService(LBSForAppService lbsForAppService) {
		this.lbsForAppService = lbsForAppService;
	}
	
	

}
