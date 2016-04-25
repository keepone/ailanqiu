<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
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
  
   <!-- poster 指定视频封面 -->
  ${json.href}<br/>
 

  <video id="example_video_1" class="video-js vjs-default-skin"    
  controls preload="auto" width="640" height="264"    
  poster="${json.source_img}"   
  data-setup='{"example_option":false}'>    
     
 <source src="${json.href}"/>
</video> 
 
  </body>
</html>
