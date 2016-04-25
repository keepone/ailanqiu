package com.lb.app.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Controller;

import com.lb.app.service.VideoForAppService;

import com.lb.utils.GetParameterUtil;

import com.lb.utils.PrintUtil;
import com.opensymphony.xwork2.ActionSupport;

@Namespace("/videoapp")
@Controller
@Scope("prototype")
public class VideoForAppAction extends ActionSupport {
	private JSONArray arr=null;
	private JSONObject json=null;
	private String result=null;
	@Autowired
	@Qualifier("videoForAppService")
	VideoForAppService videoForAppService=null;
	 
	//��Ƶ�б�
	@Action(value = "VideoListForApp", results = {
			@Result(name = "success", location = "/video_list.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getNewsList() {
		Integer pageNo=GetParameterUtil.getInteger("pageNo");
		Integer pageSize=GetParameterUtil.getInteger("pageSize");
		arr=videoForAppService.getAllVideo(pageNo, pageSize);
		return SUCCESS;
	}
	
	//��Ƶ�б�(����JSON)
	@Action(value = "VideoListJsonForApp", results = {
			@Result(name = "success", location = "/video_list.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getNewsList_json() {
		Integer pageNo=GetParameterUtil.getInteger("pageNo");
		Integer pageSize=GetParameterUtil.getInteger("pageSize");
		arr=videoForAppService.getAllVideo(pageNo, pageSize);
		PrintWriter out=PrintUtil.printWord();
		out.print(arr);
		out.flush();
		out.close();
		return null;
	}
	
	//��Ƶ����
	@Action(value = "VideoDetailForApp", results = {
			@Result(name = "success", location = "/video_detail.jsp"),
			@Result(name = "input", location = "/jsp/admin.jsp") })
	public String getNewsDetail() {
		Integer videoId=GetParameterUtil.getInteger("videoId");
		json=videoForAppService.getVideoById(videoId);
		return SUCCESS;
	}
 
	
	//�ṩ��APP���õ���Ƕ��Ƶ����ҳ��
	@Action(value = "VideoDetailForAppQuote", results = {
			@Result(name = "success", location = "/jsp/video_play/youku.jsp"),
			@Result(name = "input", location = "/jsp/404.jsp") })
	public String videoDetailForApp() {
		JSONObject json=new JSONObject();
		Integer videoId=GetParameterUtil.getInteger("videoId");
		String source=GetParameterUtil.getString("source");
		if(source.equals("�ſ�")){
			json.put("quote_html_page", "http://tzjiahao.xicp.net:14102/videoapp/YouKuDetailForApp?videoId="+videoId);
		}else if(source.equals("���� ")){
			json.put("quote_html_page",  "http://tzjiahao.xicp.net:14102/videoapp/SinaDetailForApp?videoId="+videoId);
		}else if(source.equals("LeTV ")){
			json.put("quote_html_page",  "http://tzjiahao.xicp.net:14102/videoapp/LeTVDetailForApp?videoId="+videoId);
		}
		PrintWriter out=PrintUtil.printWord();
		out.print(json);
		out.flush();
		out.close();
		return SUCCESS;
	}
	
	//�ſ���Ƶ����ҳ��
	@Action(value = "YouKuDetailForApp", results = {
			@Result(name = "success", location = "/jsp/video_play/youku.jsp"),
			@Result(name = "input", location = "/jsp/404.jsp") })
	public String getYouKuDetail() {
		Integer videoId=GetParameterUtil.getInteger("videoId");
		json=videoForAppService.getVideoById(videoId);
		return SUCCESS;
	}
	
	//Sina��Ƶ����ҳ��
	@Action(value = "SinaDetailForApp", results = {
			@Result(name = "success", location = "/jsp/video_play/sina.jsp"),
			@Result(name = "input", location = "/jsp/404.jsp") })
	public String getSinaDetail() {
		Integer videoId=GetParameterUtil.getInteger("videoId");
		json=videoForAppService.getVideoById(videoId);
		return SUCCESS;
	}

	//LETV��Ƶ����ҳ��
	@Action(value = "LeTVDetailForApp", results = {
			@Result(name = "success", location = "/jsp/video_play/leshi.jsp"),
			@Result(name = "input", location = "/jsp/404.jsp") })
	public String getLeTVDetail() {
		Integer videoId=GetParameterUtil.getInteger("videoId");
		json=videoForAppService.getVideoById(videoId);
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

	public void setVideoForAppService(VideoForAppService videoForAppService) {
		this.videoForAppService = videoForAppService;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
 
	 

	 
	 
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	 

}
