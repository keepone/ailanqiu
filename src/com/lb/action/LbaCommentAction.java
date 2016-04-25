package com.lb.action;

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

import com.lb.service.LbaCommentService;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.PrintUtil;
@Namespace("/lbacomment")
@Controller
@Scope("prototype")
public class LbaCommentAction extends PageAction{
	
	private JSONArray jsonArray=null;
	private String title=null;
	private String content=null;
	private JSONObject json=null;
	private Integer id=0;
	@Autowired
	@Qualifier("lbaCommentService")
	LbaCommentService lbaCommentService=null;
	
	@Action(value = "ToAddLbaCommentAction", results = {
			@Result(name = "success", location = "/jsp/add_lba_comment.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String toAdd() {
		 
		return SUCCESS;
	}

	@Action(value = "LbaCommentListAction", results = {
			@Result(name = "success", location = "/jsp/lba_comment_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String list() {
		 jsonArray=lbaCommentService.getAll();
		return SUCCESS;
	}
	
	@Action(value = "CommentDetailAction", results = {
			@Result(name = "success", location = "/jsp/lba_comment_detail.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String detail() {
		Integer id=GetParameterUtil.getInteger("id");
		 json=lbaCommentService.getCommentDetail(id);
		return SUCCESS;
	}
	
	
	@Action(value = "AddLbaCommentAction", results = {
			@Result(name = "success",type="redirectAction" , location = "/LbaCommentListAction"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String add() {
		System.out.println(content);
		//Integer count=lbaCommentService.addComment(title, content);
		return SUCCESS;
	}
	
	
	public void setLbaCommentService(LbaCommentService lbaCommentService) {
		this.lbaCommentService = lbaCommentService;
	}

	public JSONArray getJsonArray() {
		return jsonArray;
	}

	public void setJsonArray(JSONArray jsonArray) {
		this.jsonArray = jsonArray;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
