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
import com.lb.app.service.TacticsForAppService;
import com.lb.app.service.VideoForAppService;
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

@Namespace("/tacticsapp")
@Controller
@Scope("prototype")
public class TacticsForAppAction extends ActionSupport {
	private JSONArray arr=null;
	private JSONObject json=null;
	private String result=null;
	@Autowired
	@Qualifier("tacticsForAppService")
	TacticsForAppService tacticsForAppService=null;
	 
	//【篮球战术】列表
	@Action(value = "TacticsListForApp", results = {
			@Result(name = "success", location = "/tactics_list.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getTacticsList() {
		Integer pageNo=GetParameterUtil.getInteger("pageNo");
		Integer pageSize=GetParameterUtil.getInteger("pageSize");
		arr=tacticsForAppService.getAllTactics(pageNo, pageSize);
		return SUCCESS;
	}
	
	//【篮球战术】列表(返回JSON)
	@Action(value = "TacticsListJsonForApp", results = {
			@Result(name = "success", location = "/tactics_list.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getTacticsList_json() {
		Integer pageNo=GetParameterUtil.getInteger("pageNo");
		Integer pageSize=GetParameterUtil.getInteger("pageSize");
		arr=tacticsForAppService.getAllTactics(pageNo, pageSize);
		PrintWriter out=PrintUtil.printWord();
		out.print(arr);
		out.flush();
		out.close();
		return null;
	}
	
	//【篮球战术】详情
	@Action(value = "TacticsDetailForApp", results = {
			@Result(name = "success", location = "/tactics_detail.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getTacticsDetail() {
		Integer tacticsId=GetParameterUtil.getInteger("tacticsId");
		json=tacticsForAppService.getTacticsById(tacticsId);
		return SUCCESS;
	}
	
	//编辑【篮球战术】
	@Action(value = "UpdateTacticsAction", results = {
			@Result(name = "success", location = "/update_tactics.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String updateTactics() {
		Integer tacticsId=GetParameterUtil.getInteger("tacticsId");
		json=tacticsForAppService.getTacticsById(tacticsId);
		return SUCCESS;
	}
 

	public JSONArray getArr() {
		return arr;
	}

	public void setArr(JSONArray arr) {
		this.arr = arr;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	 

	public void setTacticsForAppService(TacticsForAppService tacticsForAppService) {
		this.tacticsForAppService = tacticsForAppService;
	}

 
 
	 

	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
