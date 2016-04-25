<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add_special.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
        <form action="admin/dynamic/UpdateDynamic" name="myFrm" method="post" enctype="multipart/form-data">
  
  <input type="hidden" name="dynamicId" value="<%=request.getParameter("dynamicId") %>">
   <input type="text" name="img" value="<%=request.getParameter("dynamicImg") %>">
图片：  <input type="file" name="resource"><br>
 图片路径：  <input type="text" name="imgURL" style="width: 300px;"><br>
 
文字：
<textarea id="content" name="content" style="width:800px;height:300px;"></textarea>
<input type="submit" value="开始上传" />
   
  </form>
  </body>
</html>
