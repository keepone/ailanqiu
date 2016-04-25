package com.lb.action;

import java.io.IOException;
import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lb.app.service.LBPlayerService;
import com.lb.app.service.LBTeamService;
import com.lb.crawl.utils.GetBasketballPlace;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PrintUtil;

@Namespace("/lbplayer")
@Controller
@Scope("prototype")
public class LBPlayerAction extends PageAction {
	@Autowired
	@Qualifier("lbPlayerService")
	LBPlayerService lbPlayerService=null;
	private String name;
	private String img;
	private Integer weight;
	private Integer height;
	private String birthday;
	private Integer position;
	private Integer clothNum;
	private Integer teamId;
	
	

	/**
	 * 录入球员
	 * @return
	 */
	@Action(value = "AddLBPlayer", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addLBTeam() {
		 GetBasketballPlace getBasketballPlace=new GetBasketballPlace();
		 
		 name=GetParameterUtil.getString("name");
		 img=GetParameterUtil.getString("img");
		 weight=GetParameterUtil.getInteger("weight");
		 height=GetParameterUtil.getInteger("height");
		 birthday=GetParameterUtil.getString("birthday");
		 position=GetParameterUtil.getInteger("position");
		 clothNum=GetParameterUtil.getInteger("clothNum");
		 teamId=GetParameterUtil.getInteger("teamId");
		 Integer status=lbPlayerService.addPlayer(name, img, weight, height, birthday, position, clothNum, teamId);
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		 
		json.put("result", status);
		out.print(json);
		out.flush();
		out.close();
		 
		 return null;
	}
	
	/**
	 * 球员详细信息
	 * @return
	 */
	@Action(value = "GetPlayerDetail", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String getPlayerDetail() {
		 GetBasketballPlace getBasketballPlace=new GetBasketballPlace();
		 
		 Integer playerId=GetParameterUtil.getInteger("playerId");
		 
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		 json=lbPlayerService.getPlayerDetail(playerId);
		 
		out.print(json);
		out.flush();
		out.close();
		 
		 return null;
	}
	 
	
	
	
	 

}
