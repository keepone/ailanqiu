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
import com.lb.admin.service.CommonService;
import com.lb.admin.service.DynamicService;
import com.lb.admin.service.QuestionnaireService;
import com.lb.admin.service.UserService;
import com.lb.utils.CreateDefinedPwdUtil;
import com.lb.utils.GetParameterUtil;
import com.lb.utils.JpushUtil;
import com.lb.utils.PageBean;
import com.lb.utils.PrintUtil;
import com.lb.utils.UploadPhotoToQiNiu;
import com.opensymphony.xwork2.ActionContext;

@Namespace("/admin/wqj/product")
@Controller
@Scope("prototype")
public class ProductAction extends PageAction{
	private JSONArray jsonArray=null;
	private JSONObject json=new JSONObject();
	private String resourceFileName; // 保存文件名称
	private File resource;
	private File resource1;
	private String img;
	private String imgURL;
	private String imgURL1;
	private String imgURL2;
	private String imgURL3;
	private String imgURL4;
	private String content;
	private Integer userId;
	private Integer dynamicId;
	
	private String tag_name;
	private double x_position;
	private double y_position;
	
	private String tag_name2;
	private double x_position2;
	private double y_position2;
	
	private String tag_name3;
	private double x_position3;
	private double y_position3;
	
	private String userName;
	private String questionName;
	private String questionA;
	private String questionB;
	private String questionC;
	private String questionD;
	private Integer questionnaireId;
	private String rightAnswer;
	private String cardName;
	private String name;
	
	private JSONArray userArray;
	private Integer adId;
	private Integer circleId=1;
	PageBean pageBean = null;
	private String searchDynamicContent;
	@Autowired
	DynamicService dynamicService=null;
	
	@Autowired
	UserService userService=null;
	
	@Autowired
	QuestionnaireService questionnaireService=null;
	
	@Autowired
	CommonService commonService=null;
	 
	/**
	 * 获取所有问卷调查
	 * @return
	 */
	@Action(value = "GetAllQuestionnaire", results = {
			@Result(name = "success", location = "/jsp/admin/add_question.jsp"),
			@Result(name = "input", location = "/jsp/admin/add_dynamic.jsp") })
	public String getAllQuestionnaire() {
		String res=SUCCESS;
		jsonArray=questionnaireService.getAllQuestionnaire();
		 return res;
	}
	
	
	/**
	 * 执行sql语句
	 * @return
	 */
	@Action(value = "RunSQL", results = {
			@Result(name = "success", location = "/jsp/admin/add_question.jsp"),
			@Result(name = "input", location = "/jsp/admin/add_dynamic.jsp") })
	public String RunSQL() {
		String sql="UPDATE lb_dynamic set ad_id=1  where dynamic_id in (SELECT d.dynamic_id from lb_dynamic  d inner join lb_img_tag t  on d.dynamic_id=t.dynamic_id  where tag_name like '%抓球%')";
		String res=SUCCESS;
		Integer count=questionnaireService.runSQL(sql);
		 return res;
	}
	
	@Action(value = "AddProduct", results = {
			@Result(name = "success", location = "/ok.jsp"),
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
				 status=questionnaireService.addQuestion(questionnaireId, questionName, questionA, questionB, questionC, questionD, img, rightAnswer);
			 }
			
		 }
		 
		 if(status>0){
			 res=SUCCESS;
		 }
		 return res;
	}
	
	
	
	/**
	 *  
	 * @return
	 */
	@Action(value = "ToAddDynamic", results = {
			@Result(name = "success", location = "/jsp/admin/add_dynamic.jsp"),
			@Result(name = "input", location = "/jsp/admin/add_dynamic.jsp") })
	public String ToaddLBTeam() {
		String res=SUCCESS;
		jsonArray=userService.getUserList();
		JSONArray arr=userService.getIndexAd();
		JSONArray circleList=userService.getCircleList();
		json.put("userList", jsonArray);
		json.put("indexAds",  arr);
		json.put("circleList",  circleList);
		 return res;
	}
	
	
	@Action(value = "AddDynamic", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String addLBTeam() {
		String res=INPUT;
		Integer adId=GetParameterUtil.getInteger("adId");
		Integer dynamicType=0;
		 if(!cardName.trim().equals("")&&cardName.length()>1){
			 dynamicType=3;
		 }
		JSONArray imgArr=new JSONArray();
		long start=System.currentTimeMillis();
		Integer status=0;
		 if(imgURL!=null&&!imgURL.equals("")){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgURL);
			 imgArr.add(img);
		 } if(imgURL1!=null&&!imgURL1.equals("")){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgURL1);
			 imgArr.add(img);
		 } if(imgURL2!=null&&!imgURL2.equals("")){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgURL2);
			 imgArr.add(img);
		 } if(imgURL3!=null&&!imgURL3.equals("")){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgURL3);
			 imgArr.add(img);
		 } if(imgURL4!=null&&!imgURL4.equals("")){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgURL4);
			 imgArr.add(img);
		 }if(resourceFileName!=null){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByWEB(resource, resourceFileName);
			 imgArr.add(img);
		 }
		 if(resource1!=null){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByWEB(resource1, resourceFileName);
			 imgArr.add(img);
		 }
		 //如果图片上传成功
		 if(!img.equals("")){
			 long end=System.currentTimeMillis();
			 int t=(int) ((end-start)/1000);
			 if(t>10&&imgArr.toString().indexOf(".gif")==-1){
				 
			 }else{
				 status=dynamicService.addDynamic(circleId,cardName,dynamicType,adId,userId, content, imgArr, tag_name,x_position,y_position,tag_name2,x_position2,y_position2,tag_name3,x_position3,y_position3);
			 }
			
		 }
		 
		 if(status>0){
			 res=SUCCESS;
		 }
		 return res;
	}
	
	@Action(value = "UploadPhoto", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String uploadPhoto() {
		String res=INPUT;
		Integer status=0;
		 if(imgURL!=null&&!imgURL.equals("")){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgURL);
		 }else{
			 img=UploadPhotoToQiNiu.uploadToQiNiuByWEB(resource, resourceFileName);
			
		 }
		 PrintWriter out=PrintUtil.printWord();
		 out.print(img);
		 out.flush();
		 out.close();
		 return res;
	}
	
	
	@Action(value = "DynamicList", results = {
			@Result(name = "success", location = "/jsp/admin/dynamic_list.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String  courtList() { 
		Integer pageNo=GetParameterUtil.getInteger("pageNo");
		Integer pageSize=GetParameterUtil.getInteger("pageSize");
		
		 pageBean=dynamicService.getDynamicListBy(userName,adId,searchDynamicContent,getPage().getPageNo(), getPage().getPageSize());
		 	jsonArray=userService.getUserList();
			JSONArray arr=userService.getIndexAd();
			json.put("userList", jsonArray);
			json.put("indexAds",  arr);
		 ActionContext actionContext = ActionContext.getContext();
		 actionContext.getSession().put("page", pageBean);
		 return SUCCESS;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Action(value = "DeleteDynamic", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String  updateCourt() { 
		Integer dynamicId=GetParameterUtil.getInteger("dynamicId");
		 Integer count=dynamicService.deteteDynamicBy(dynamicId);
		 
		 String result=INPUT;
		 if(count>0){
			 result=SUCCESS;
		 }
		 return result;
	}
	
	@Action(value = "UpdateDynamic", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String  updateDanymic() { 
		long start=System.currentTimeMillis();
		Integer count=0;
		 if(imgURL!=null&&!imgURL.equals("")){
			 img=UploadPhotoToQiNiu.uploadToQiNiuByImgURL(imgURL);
		 }else{
			 if(resource!=null&&!resource.equals("")){
				 img=UploadPhotoToQiNiu.uploadToQiNiuByWEB(resource, resourceFileName);
			 }else{
				  
			 }
			 
			
		 }
		 //如果图片上传成功
		 if(!img.equals("")){
			 long end=System.currentTimeMillis();
			 int t=(int) ((end-start)/1000);
			 if(t>5){
				 
			 }else{
				 count=dynamicService.updateDynamic(dynamicId, content,img);
			 }
			
		 }
		 
		 
		   
		 
		 String result=INPUT;
		 if(count>0){
			 result=SUCCESS;
		 }
		 return result;
	}
	
	
	
	@Action(value = "AddDynamicAgree", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String  updatAddDynamicAgreeeDanymic() { 
		 String userId=GetParameterUtil.getString("userId");
		 
		 String replyUserId=GetParameterUtil.getString("replyUserId");
		 PrintWriter out=PrintUtil.printWord();
		 String dynamicId=GetParameterUtil.getString("dynamicId");
		 
		 Integer count=dynamicService.addDynamicAgree(Integer.parseInt(userId), Integer.parseInt(replyUserId), Integer.parseInt(dynamicId));
		 
		 String result=INPUT;
		 if(count>0){
			 out.print("ok");
			 
		 }
		 out.flush();
		 out.close();
		 return result;
	}
	
	//推荐帖子
	@Action(value = "RecommendDynamic", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String  recommendDynamic() { 
		 String userId=GetParameterUtil.getString("userId");
		 
		 String replyUserId=GetParameterUtil.getString("replyUserId");
		 PrintWriter out=PrintUtil.printWord();
		 String dynamicId=GetParameterUtil.getString("dynamicId");
		 Integer d=Integer.parseInt(dynamicId);
		 Integer count=dynamicService.recommendDynamic(d);
		 
		 String result=INPUT;
		 if(count>0){
			 out.print("ok");
			 
		 }
		 out.flush();
		 out.close();
		 return result;
	}
	
	@Action(value = "AddDynamicAgree2", results = {
			@Result(name = "success", location = "/ok.jsp"),
			@Result(name = "input", location = "/false.jsp") })
	public String  updatAddDynamicAgreeeDanymic2() { 
		 
		 Integer uid=userService.getRandomUser().getInt("id");
		 String replyUserId=GetParameterUtil.getString("replyUserId");
		 PrintWriter out=PrintUtil.printWord();
		 String dynamicId=GetParameterUtil.getString("dynamicId");
		 
		 Integer count=dynamicService.addDynamicAgree(uid, Integer.parseInt(replyUserId), Integer.parseInt(dynamicId));
		 
		 String result=INPUT;
		 if(count>0){
			 out.print("ok");
			 
		 }
		 out.flush();
		 out.close();
		 return result;
	}
	
	@Action(value = "AddDynamicLeave", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addDynamicLeave() {
		Integer  userId=Integer.parseInt(GetParameterUtil.getString("userId"));
		 Integer replyUserId=Integer.parseInt(GetParameterUtil.getString("replyUserId"));
		 
		 
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		 int result=1;
		 Integer count=0;
		  
			 
//			 Integer replyLeaveId=GetParameterUtil.getInteger("replyLeaveId");
		 Integer replyLeaveId=GetParameterUtil.getInteger("replyLeaveId");;
			Integer dynamicId=GetParameterUtil.getInteger("dynamicId");
			String leaveContent=GetParameterUtil.getString("replyContent");
			if(leaveContent!=null&&!leaveContent.equals("")){
				 count=dynamicService.addDynamicLeave(userId, replyLeaveId, dynamicId,leaveContent,replyUserId);
			}else{
//				leaveContent="nice";
			}
			
			  
			
			
			if(count==0){
				result=0;
			}
		 
		 
		 
		 
		
		json.put("result", result);
		out.print(json);
		out.flush();
		out.close();
		return null;
	}
	
	
	@Action(value = "AddDynamicPush", results = {
			@Result(name = "success", location = "/jsp/news_editor.jsp"),
			@Result(name = "input", location = "/jsp/add_company.jsp") })
	public String addDynamicPush() {
		 PrintWriter out=PrintUtil.printWord();
		 JSONObject json=new JSONObject();
		 int result=1;
		 Integer count=0;
			Integer dynamicId=GetParameterUtil.getInteger("dynamicId");
			String dynamicContent=GetParameterUtil.getString("dynamicContent");
		 
			if(dynamicContent!=null&&!dynamicContent.equals("")){
				
				count=JpushUtil.pushDynamicMessage(dynamicId, 5, dynamicContent,0,"");
				count=commonService.addSysMessage(1, dynamicContent, dynamicId);
			}else{
				 
			}
			 
			  
			
			
			if(count==0){
				result=0;
			}
		 
		 
		 
		 
		
		json.put("result", result);
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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDynamicId() {
		return dynamicId;
	}

	public void setDynamicId(Integer dynamicId) {
		this.dynamicId = dynamicId;
	}

	public String getTag_name() {
		return tag_name;
	}

	public void setTag_name(String tag_name) {
		this.tag_name = tag_name;
	}

	public double getX_position() {
		return x_position;
	}

	public void setX_position(double x_position) {
		this.x_position = x_position;
	}

	public double getY_position() {
		return y_position;
	}

	public void setY_position(double y_position) {
		this.y_position = y_position;
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


	public String getQuestionName() {
		return questionName;
	}


	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}


	public String getQuestionA() {
		return questionA;
	}


	public void setQuestionA(String questionA) {
		this.questionA = questionA;
	}


	public String getQuestionB() {
		return questionB;
	}


	public void setQuestionB(String questionB) {
		this.questionB = questionB;
	}


	public String getQuestionC() {
		return questionC;
	}


	public void setQuestionC(String questionC) {
		this.questionC = questionC;
	}


	public String getQuestionD() {
		return questionD;
	}


	public void setQuestionD(String questionD) {
		this.questionD = questionD;
	}


	public Integer getQuestionnaireId() {
		return questionnaireId;
	}


	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}


	public String getRightAnswer() {
		return rightAnswer;
	}


	public void setRightAnswer(String rightAnswer) {
		this.rightAnswer = rightAnswer;
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


	public Integer getAdId() {
		return adId;
	}


	public void setAdId(Integer adId) {
		this.adId = adId;
	}


	public String getCardName() {
		return cardName;
	}


	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	public String getTag_name2() {
		return tag_name2;
	}


	public void setTag_name2(String tag_name2) {
		this.tag_name2 = tag_name2;
	}


	public double getX_position2() {
		return x_position2;
	}


	public void setX_position2(double x_position2) {
		this.x_position2 = x_position2;
	}


	public double getY_position2() {
		return y_position2;
	}


	public void setY_position2(double y_position2) {
		this.y_position2 = y_position2;
	}


	public String getTag_name3() {
		return tag_name3;
	}


	public void setTag_name3(String tag_name3) {
		this.tag_name3 = tag_name3;
	}


	public double getX_position3() {
		return x_position3;
	}


	public void setX_position3(double x_position3) {
		this.x_position3 = x_position3;
	}


	public double getY_position3() {
		return y_position3;
	}


	public void setY_position3(double y_position3) {
		this.y_position3 = y_position3;
	}


	public Integer getCircleId() {
		return circleId;
	}


	public void setCircleId(Integer circleId) {
		this.circleId = circleId;
	}


	public String getImgURL1() {
		return imgURL1;
	}


	public void setImgURL1(String imgURL1) {
		this.imgURL1 = imgURL1;
	}


	public String getImgURL2() {
		return imgURL2;
	}


	public void setImgURL2(String imgURL2) {
		this.imgURL2 = imgURL2;
	}


	public String getImgURL3() {
		return imgURL3;
	}


	public void setImgURL3(String imgURL3) {
		this.imgURL3 = imgURL3;
	}


	public String getImgURL4() {
		return imgURL4;
	}


	public void setImgURL4(String imgURL4) {
		this.imgURL4 = imgURL4;
	}


	public File getResource1() {
		return resource1;
	}


	public void setResource1(File resource1) {
		this.resource1 = resource1;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
}
