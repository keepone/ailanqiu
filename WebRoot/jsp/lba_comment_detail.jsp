<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jiutong.com/tag/pager" prefix="pager" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'lba_comment_list.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<link href="/videojs/video-js.css" rel="stylesheet" type="text/css">
	<script src="/videojs/video.js"></script>
	<script>
    videojs.options.flash.swf = "/videojs/video-js.swf";
	</script>
  </head>
  
  <body> 
    ${json.name}<br/>
    ${json.author}--<pager:date DATE="${json.addTime}"/><br/>
    ${json.content}
   
</body>
</html>
