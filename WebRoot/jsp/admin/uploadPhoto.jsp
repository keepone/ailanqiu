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
     <form action="admin/dynamic/UploadPhoto" name="myFrm" method="post" enctype="multipart/form-data">
    <select name="uploadType">
      <option name="uploadType" value="1">上传到本地服务器</option>
     
     	<option name="uploadType" value="2" selected="selected">上传到七牛服务器</option>
     	 
     </select><br><br>
     
图片：  <input type="file" name="resource"><br>
 图片路径：  <input type="text" name="imgURL" style="width: 300px;"><br>
 
<input type="submit" value="开始上传" />
   
  </form>
  </body>
</html>
