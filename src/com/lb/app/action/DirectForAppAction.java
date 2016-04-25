package com.lb.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

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
import com.lb.app.service.DirectForAppService;
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

@Namespace("/directapp")
@Controller
@Scope("prototype")
public class DirectForAppAction extends ActionSupport {
	private Map<String, JSONArray> map=new HashMap<String, JSONArray>();
	private String result=null;
	@Autowired
	@Qualifier("directForAppService")
	DirectForAppService directForAppService=null;
	 
	//Ö±²¥ÁÐ±í
	@Action(value = "DirectListForApp", results = {
			@Result(name = "success", location = "/jsp/admin.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getNewsList() {
		Integer pageNo=GetParameterUtil.getInteger("pageNo");
		Integer pageSize=GetParameterUtil.getInteger("pageSize");
		map=directForAppService.getDirectSinceToday(pageNo, pageSize);
		PrintWriter out=PrintUtil.printWord();
		out.print(map);
		out.flush();
		out.close();
		return null;
	}

	public void setDirectForAppService(DirectForAppService directForAppService) {
		this.directForAppService = directForAppService;
	}

	public Map<String, JSONArray> getMap() {
		return map;
	}

	public void setMap(Map<String, JSONArray> map) {
		this.map = map;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	 
	 

	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
