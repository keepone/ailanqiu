package com.lb.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

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
import com.lb.app.service.CommonForAppService;
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
public class CommonForAppAction extends ActionSupport {
	private List<JSONObject>  arr=null;
	private JSONObject json=null;
	private String result=null;
	@Autowired
	@Qualifier("commonForAppService")
	CommonForAppService commonForAppService=null;
	 
	//获取每一年的NBA选秀状元
	@Action(value = "BestPlayerListForApp", results = {
			@Result(name = "success", location = "/scholar_list.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getBestPlayer() {
		arr=commonForAppService.getAllBestPlayer();
		return SUCCESS;
	}

	//获取每一年的NBA冠军
	@Action(value = "ChampionListForApp", results = {
			@Result(name = "success", location = "/champion_list.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getChampion() {
		 arr=commonForAppService.getChampion(); 
		return SUCCESS;
	}
	public void setCommonForAppService(CommonForAppService commonForAppService) {
		this.commonForAppService = commonForAppService;
	}
 

	public List<JSONObject> getArr() {
		return arr;
	}

	public void setArr(List<JSONObject> arr) {
		this.arr = arr;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}
	
	 
 
	 

	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
