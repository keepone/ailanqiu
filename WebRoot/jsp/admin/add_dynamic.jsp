<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add_team.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor4/kindeditor.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor4/zh_CN.js"></script>
	 <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
 <!-- <script>
	  var editor1=null;
	        KindEditor.ready(function(K) {
	       
	           editor1= K.create('#content', {
	           uploadJson : 'img/UploadToQiNiuAction',
	                	 allowFileManager : true
	                	 });
	           //同步 只有添加此句，才能取到textarea的值
               editor1.sync();

                 
             
         	//editor1.html("<p>hello kindeditor</p>");
         	
	        });
	        
	  
	</script>   -->
  </head>
  
  <body>
     <form action="admin/dynamic/AddDynamic" name="myFrm" method="post" enctype="multipart/form-data">
      请选择用户：
     <select name="userId">
     <c:forEach items="${json.userList}" var="json">
     	<option name="lbhayyp" value="${json.id }">${json.user_name}</option>
     	 </c:forEach>
     </select><br>
     <br><br>是否参加活动： 
      <select name="adId">
      <option name="indexAd" value="0">不参加活动</option>
     <c:forEach items="${json.indexAds}" var="json">
     	<option name="indexAd" value="${json.ad_id}">${json.ad_name}</option>
     	 </c:forEach>
     </select><br><br>
    圈子ID： 
      <select name="circleId">
      <option name="circle" value="1">不参加活动</option>
     <c:forEach items="${json.circleList}" var="json">
     	<option name="circle" value="${json.id}">${json.name}</option>
     	 </c:forEach>
     </select><br><br>
     打卡名称：<input type="text" name="cardName"><br><br>
     
     
     标签名称1：<input type="text" name="tag_name"><br><br>
     X:<input type="text" name="x_position"><br><br>
     Y:<input type="text" name="y_position"><br><br>
     
       标签名称2：<input type="text" name="tag_name2"><br><br>
     X:<input type="text" name="x_position2"><br><br>
     Y:<input type="text" name="y_position2"><br><br>
     
      标签名称3：<input type="text" name="tag_name3"><br><br>
     X:<input type="text" name="x_position3"><br><br>
     Y:<input type="text" name="y_position3"><br><br>
     
上传图片方式（2选1）：<br>
打开图片：<br>  
<input type="file" name="resource"><br>
<input type="file" name="resource1"><br>
 图片网址：  
 <input type="text" name="imgURL" style="width: 300px;"><br>
 <input type="text" name="imgURL1" style="width: 300px;"><br>
 <input type="text" name="imgURL2" style="width: 300px;"><br>
 <input type="text" name="imgURL3" style="width: 300px;"><br>
 <input type="text" name="imgURL4" style="width: 300px;"><br>
文字：<br>
<textarea id="content" name="content" style="width:500px;height:100px;"></textarea>
<input type="submit" value="开始上传" />
   
  </form>
  </body>
</html>
