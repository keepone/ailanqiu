package com.lb.admin.action;

import java.io.File;
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
import com.lb.admin.service.DynamicService;
import com.lb.admin.service.ParkService;
import com.lb.admin.service.QuestionnaireService;
import com.lb.admin.service.UserService;
import com.lb.utils.CreateDefinedPwdUtil;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.JpushUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.lb.utils.UploadPhotoToQiNiu;
import com.opensymphony.xwork2.ActionContext;

@Namespace("/admin/park")
@Controller
@Scope("prototype")
public class ParkAction extends PageAction{
	private JSONArray jsonArray=null;
	private JSONObject json=null;
	private String resourceFileName; // 保存文件名称
	private File resource;
	private String img;
	private String imgURL;
	private String parkName;
	private Integer userId=1;
	private String parkImg;
	private String areaDetail;
	
	 
	private JSONArray userArray;
	
	PageBean pageBean = null;
	private String searchDynamicContent;
	@Autowired
	ParkService parkService=null;
	
	@Autowired
	UserService userService=null;
	
	@Autowired
	QuestionnaireService questionnaireService=null;
	 

	
	
	@Action(value = "AddPark", results = {
			@Result(name = "success", location = "/jsp/admin/add_park.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String addQuestion() {
		String res=INPUT;
		long start=System.currentTimeMillis();
		Integer status=0;
		 if(imgURL!=null&&!imgURL.equals("")){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgURL);
		 }else{
			 img=UploadPhotoToQiNiu.uploadToQiNiuByWEB(resource, resourceFileName);
			
		 }
		 //如果图片上传成功
		 if(!img.equals("")){
			 long end=System.currentTimeMillis();
			 int t=(int) ((end-start)/1000);
			 if(t>5){
				 
			 }else{
				status=parkService.addPark(userId, parkName, areaDetail, img);
			 }
			
		 }
		 
		 if(status>0){
			 res=SUCCESS;
		 }
		 return res;
	}
	
	
	/**
	 * 搜索公园  121.51431918973-----31.308624023569
	 * /review/lbs/SearchBasketballCourt?lng=121.51431918973&lat=31.308624023569&city=上海市&area=杨浦区&pageNo=1&pageSize=10
	 * @return
	 */
	@Action(value = "SearchBasketballCourt", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String search() {
		 String lng=GetParameterUtil.getString("lng"); //经度
		 String lat=GetParameterUtil.getString("lat");  //纬度
		 String city=GetParameterUtil.getString("city");
		 String area=GetParameterUtil.getString("area");
		 Integer pageNo=GetParameterUtil.getInteger("pageNo"); 
		 Integer pageSize=GetParameterUtil.getInteger("pageSize"); 
		 jsonArray=parkService.getParkBy(pageNo, pageSize, lng, lat, city, area);
		 PrintWriter out=PrintUtil.printWord();
		 out.print(jsonArray);
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

	public String getResourceFileName() {
		return resourceFileName;
	}

	public void setResourceFileName(String resourceFileName) {
		this.resourceFileName = resourceFileName;
	}

	public File getResource() {
		return resource;
	}

	public void setResource(File resource) {
		this.resource = resource;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	

	public JSONArray getUserArray() {
		return userArray;
	}


	public void setUserArray(JSONArray userArray) {
		this.userArray = userArray;
	}


	public String getImgURL() {
		return imgURL;
	}


	public void setImgURL(String imgURL) {
		this.imgURL = imgURL;
	}


	

	 

	 


	public String getParkName() {
		return parkName;
	}


	public void setParkName(String parkName) {
		this.parkName = parkName;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public String getParkImg() {
		return parkImg;
	}


	public void setParkImg(String parkImg) {
		this.parkImg = parkImg;
	}


	public String getAreaDetail() {
		return areaDetail;
	}


	public void setAreaDetail(String areaDetail) {
		this.areaDetail = areaDetail;
	}


	public PageBean getPageBean() {
		return pageBean;
	}


	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}


	public String getSearchDynamicContent() {
		return searchDynamicContent;
	}


	public void setSearchDynamicContent(String searchDynamicContent) {
		this.searchDynamicContent = searchDynamicContent;
	}
	
	
}
