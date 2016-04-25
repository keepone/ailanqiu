package com.lb.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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
import com.lb.service.GameDataService;
import com.lb.service.RecommendService;
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.lb.utils.StrUtil;
import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerVideoByNBA98;
 
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/recommend")
@Controller
@Scope("prototype")
public class RecommendAction extends PageAction {
 
	@Autowired
	@Qualifier("recommendService")
	RecommendService recommendService=null;
	 
	/**
	 * 推荐新闻，被推荐将在首页头条栏目显示
	 * @return
	 */
	@Action(value = "IndexRecommendNewsAction", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String direct() {
		Integer resourceId=GetParameterUtil.getInteger("resourceId");
		Integer resourceType=GetParameterUtil.getInteger("resourceType");
		String resourceHref="/indexDetail/getNewsIndexDetail.do?resourceId="+resourceId;
		Integer count=recommendService.addRecommend(resourceId, resourceType,resourceHref);
		PrintWriter out=PrintUtil.printWord();
		if(count>0){
			out.print("200");
		}else{
			out.print("500");
		}
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 推荐视频，被推荐将在首页头条栏目显示
	 * @return
	 */
	@Action(value = "IndexRecommendVideoAction", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String recommendVideo() {
		Integer resourceId=GetParameterUtil.getInteger("resourceId");
		Integer resourceType=GetParameterUtil.getInteger("resourceType");
		String resourceHref="/indexDetail/getVideoIndexDetail.do?resourceId="+resourceId;
		Integer count=recommendService.addRecommend(resourceId, resourceType,resourceHref);
		
		PrintWriter out=PrintUtil.printWord();
		if(count>0){
			out.print("200");
		}else{
			out.print("500");
		}
		out.flush();
		out.close();
		return null;
	}
	
	/**
	 * 推荐，被推荐将在首页头条的滑动板块显示
	 * @return
	 */
	@Action(value = "IndexRecommendAnimationAction", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String index_animation() {
		Integer resourceId=GetParameterUtil.getInteger("resourceId");
		Integer resourceType=GetParameterUtil.getInteger("resourceType");
		String resourceHref=GetParameterUtil.getString("resourceHref");
		Integer count=recommendService.addIndexAnimation(resourceId, resourceType,resourceHref);
		PrintWriter out=PrintUtil.printWord();
		if(count>0){
			out.print("200");
		}else{
			out.print("500");
		}
		out.flush();
		out.close();
		return null;
	}
	public void setRecommendService(RecommendService recommendService) {
		this.recommendService = recommendService;
	}


  
}
