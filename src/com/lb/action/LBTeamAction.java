package com.lb.action;

import java.io.File;
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

import com.lb.app.service.LBTeamService;
import com.lb.crawl.utils.GetBasketballPlace;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PhotoUploadUtil;
import com.lb.utils.PrintUtil;
import com.lb.utils.UploadPhotoToQiNiu;

@Namespace("/lbteam")
@Controller
@Scope("prototype")
public class LBTeamAction extends PageAction {
	@Autowired
	@Qualifier("lbTeamService")
	LBTeamService lbTeamService=null;
	private String resourceFileName; // 保存文件名称
	private File resource;
	private String name;
	private String img;
	private String content;
	private String createTime;
	private String phone;
	private String workTime;
	private String province;
	private String city;
	private String area;
	private String areaDetail;
	private Integer teamId;
	private static JSONArray arr=null;

	/**
	 * 录入球队
	 * @return
	 */
	@Action(value = "AddLBTeam", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addLBTeam() {
		 name=GetParameterUtil.getString("name");
		 img=UploadPhotoToQiNiu.uploadToQiNiuByWEB(resource, resourceFileName);
		
		 Integer status=lbTeamService.addLBTeam(name, img, "上海市", "上海市", "虹口区", phone);
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		 
		json.put("result", status);
		out.print(json);
		out.flush();
		out.close();
		 
		 return null;
	}
	
	/**
	 * 球队列表
	 * @return
	 */
	@Action(value = "GetLBTeamList", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String getLBTeamList() {
		 
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		 Integer pageNo=GetParameterUtil.getInteger("pageNo");
		 Integer pageSize=GetParameterUtil.getInteger("pageSize");
		 JSONArray arr=lbTeamService.getLBTeamBy(pageNo, pageSize);
		 
		out.print(arr);
		out.flush();
		out.close();
		 
		 return null;
	}
	
	
	
	/**
	 * 球队详情
	 * @return
	 */
	@Action(value = "GetLBTeamDetail", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String getLBTeamDetail() {
		 
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		teamId=GetParameterUtil.getInteger("teamId");
		json=lbTeamService.getLBTeamDetailBy(teamId);
		out.print(json);
		out.flush();
		out.close();
		 
		 return null;
	}

	/**
	 * 球队所有球员
	 * @return
	 */
	@Action(value = "GetPlayerListByTeamId", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String getPlayerListByTeamId() {
		 
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		teamId=GetParameterUtil.getInteger("teamId");
		arr=lbTeamService.getPlayersByTeamId(teamId);
		out.print(arr);
		out.flush();
		out.close();
		 
		 return null;
	}

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getAreaDetail() {
		return areaDetail;
	}

	public void setAreaDetail(String areaDetail) {
		this.areaDetail = areaDetail;
	}

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}
	
	
}
