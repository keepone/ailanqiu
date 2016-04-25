<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
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
    <form action="news/AddZhuanJi" method="post" name="myFrm">
    	 优酷专辑链接：<input type="text" name="url"> 
    	<input type="submit" value="录入">
    </form>
    
      <form action="news/AddZhuanJiByTencent" method="post" name="myFrm2">
     <a href="http://v.qq.com/vplus/f2aeafa04b377ef3f9bb462829b154a5/videos" target="_blank">腾讯个人主页链接：</a>	 <input type="text" name="source_url"> 
    	<input type="submit" value="录入">
    </form>
    
      <form action="news/AddLETVZhunJi" method="post" name="myFrm2">
     <a href="http://sports.le.com/video/topic/s/11375_25212906.html" target="_blank">乐视相关视频：</a>	 <input type="text" name="source_url"> 
    	<input type="submit" value="录入">
    </form>
    
  </body>
</html>
