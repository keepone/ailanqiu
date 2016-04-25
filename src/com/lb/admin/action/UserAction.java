package com.lb.admin.action;

import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lb.action.PageAction;
import com.lb.admin.service.UserService;
import com.lb.utils.CreateDefinedPwdUtil;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
@Namespace("/admin/user")
@Controller
@Scope("prototype")
public class UserAction extends PageAction {
	private JSONArray jsonArray=null;
	private JSONObject json=null;
	private String phone=null;
	private String pwd=null;
	private String startTime=null;
	private String endTime=null;
	private Integer userType;
	
	
	PageBean pageBean = null;
	


	@Autowired
	UserService userService=null;
	
	//约赛列表
		@Action(value = "BattleList", results = {
				@Result(name = "success", location = "/jsp/admin/battle_list.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String  BattleList() { 
			 jsonArray=userService.getBattleList();
			 
			 return SUCCESS;
		}
		
		
		
	//删除用户
	@Action(value = "DeleteUser", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String  delete() { 
		 Integer userId=GetParameterUtil.getInteger("userId");
		 Integer count=userService.deleteUserBy(userId);
		 String re=SUCCESS;
		 PrintWriter out=PrintUtil.printWord();
		 if(count==0){
			 re=INPUT;
		 }
		
	 
		 return re;
	}
 
	
//用户列表
	@Action(value = "UserList", results = {
			@Result(name = "success", location = "/jsp/admin/u_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String  userlist() { 
		 pageBean=userService.getUserListBy(startTime,endTime,userType,getPage().getPageNo(), getPage().getPageSize());
		 ActionContext actionContext = ActionContext.getContext();
		 actionContext.getSession().put("page", pageBean);
		 return SUCCESS;
	}


		//添加关注
		@Action(value = "AddFocusOfOne", results = {
				@Result(name = "success", location = "/jsp/news_editor.jsp"),
				@Result(name = "input", location = "/jsp/add_company.jsp") })
		public String addFocus() { 
			 JSONObject json=userService.getRandomUser();
			 Integer userId=json.getInt("id");
			 PrintWriter out=PrintUtil.printWord();
			  
			 Integer r=1;
			
			 String result="1";
			 
			if(json!=null&&json.size()>0){   
				Integer focusUserId=GetParameterUtil.getInteger("focusUserId");
				 
				 Integer count=userService.addFocusBy(userId, focusUserId);
				 
				
				 if(count==0){
					 out.print("0");
				 }else{
					 out.print("1");
				 }
			}else{
				out.print("0");
			}
			 
			
			 
			out.flush();
			out.close();
			 return null;
		}
	
	//用户注册
	@Action(value = "UserRegister", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String register() { 
		 
		// Integer pho=Integer.getInteger(phone);
		 Integer count=userService.userRegister(phone,pwd);
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		 if(count==0){
			 json.put("result", "0");
		 }else{
			 //String encrp_pwd=CreateDefinedPwdUtil.encryp(count);
			 json.put("result", count);
		 }
		out.print(json);
		out.flush();
		out.close();
		return null;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
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
	
}
