package com.lb.admin.action;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lb.action.PageAction;
import com.lb.admin.service.BasketballCourtService;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Namespace("/admin/court")
@Controller
@Scope("prototype")
public class BasketballCourtAction extends PageAction {
	private JSONArray jsonArray=null;
	private JSONObject json=null;
	private Integer nationalId;
	private Integer userType;
	private String tableName="lb_basketball_court";
	private String content;
	private String schoolName;
	private String startTime=null;
	private String endTime=null;
	private Integer ifOKSchool=0;
	PageBean pageBean = null;
	
	
	@Autowired
	BasketballCourtService basketballCourtService=null;
	
	@Action(value = "GetReviewList", results = {
			@Result(name = "success", location = "/court_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String  getReviewList() { 
		 jsonArray=basketballCourtService.getReview(tableName);
		 JSONObject json=new JSONObject();
		 json.put("tableName", tableName);
		 jsonArray.add(json);
		 return SUCCESS;
	}
	
	@Action(value = "CourtList", results = {
			@Result(name = "success", location = "/court_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String  courtList() { 
		 jsonArray=basketballCourtService.getReviewCourt();
		 return SUCCESS;
	}

	
	@Action(value = "GetSchoolList", results = {
			@Result(name = "success", location = "/jsp/admin/school_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String  getSchoolList() { 
		
		 pageBean=basketballCourtService.getSchoolList(ifOKSchool,startTime,endTime,schoolName,getPage().getPageNo(), getPage().getPageSize(),userType);
		 	 
		 ActionContext actionContext = ActionContext.getContext();
		 actionContext.getSession().put("page", pageBean);
		 return SUCCESS;
	}
	
	@Action(value = "UpdateCourt", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String  updateCourt() { 
		Integer courtId=GetParameterUtil.getInteger("courtId");
		String tableName=GetParameterUtil.getString("tableName");
		Integer userId=GetParameterUtil.getInteger("userId");
		 Integer count=basketballCourtService.updateCourtStatus(courtId,tableName);
		 String result=INPUT;
		 if(count>0){
			 result=SUCCESS;
		 }
		 return result;
	}

	@Action(value = "UpdateNational", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String  updateNational() { 
		 Integer count=basketballCourtService.updateNational(nationalId, content);
		 String result=INPUT;
		 if(count>0){
			 result=SUCCESS;
		 }
		 return result;
	}
	
	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public Integer getNationalId() {
		return nationalId;
	}

	public void setNationalId(Integer nationalId) {
		this.nationalId = nationalId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getIfOKSchool() {
		return ifOKSchool;
	}

	public void setIfOKSchool(Integer ifOKSchool) {
		this.ifOKSchool = ifOKSchool;
	}

 

}
