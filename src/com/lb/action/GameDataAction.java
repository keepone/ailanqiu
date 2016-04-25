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
import com.lb.service.TeamService;
import com.lb.service.VideoService;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PageBean;
import com.lb.utils.StrUtil;
import com.lucene.crawl.GetNBATeamInfoBy163;
import com.lucene.crawl.GetPlayerVideoByNBA98;
 
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/gamedata")
@Controller
@Scope("prototype")
public class GameDataAction extends PageAction {
	private PageBean pageBean=null;
	private String result=null;
	@Autowired
	@Qualifier("gameDataService")
	GameDataService gameDataService=null;
	private JSONArray jsonArray=null;
	private String host_team=null;
	private String guest_team=null;
	private JSONArray host_team_data=null;
	private JSONArray guest_team_data=null;

	@Action(value = "GameDataAction", results = {
			@Result(name = "success", location = "/jsp/game_data.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String direct() {
		Integer directId=GetParameterUtil.getInteger("directId");
		host_team=GetParameterUtil.getString("host_team");
		 
		guest_team=GetParameterUtil.getString("guest_team");
	 
		host_team_data=gameDataService.getGameDataByDirectId(directId,host_team);
		guest_team_data=gameDataService.getGameDataByDirectId(directId, guest_team);
		result = SUCCESS;
		return result;
	}



	public void setGameDataService(GameDataService gameDataService) {
		this.gameDataService = gameDataService;
	}



	public JSONArray getJsonArray() {
		return jsonArray;
	}



	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}



	public String getHost_team() {
		return host_team;
	}



	public void setHost_team(String hostTeam) {
		host_team = hostTeam;
	}



	public String getGuest_team() {
		return guest_team;
	}



	public void setGuest_team(String guestTeam) {
		guest_team = guestTeam;
	}



	public JSONArray getHost_team_data() {
		return host_team_data;
	}



	public void setHost_team_data(JSONArray hostTeamData) {
		host_team_data = hostTeamData;
	}



	public JSONArray getGuest_team_data() {
		return guest_team_data;
	}



	public void setGuest_team_data(JSONArray guestTeamData) {
		guest_team_data = guestTeamData;
	}
	 

	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
