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
	<script data-vid="IzDGnRawYRP-d52VpSelmg.." src="//player.ku6.com/out/v.js" data-width="480" data-height="400"></script>
  </head>
  
  <body> 
  
<%--<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="610" height="460">
<param name="movie" value="http://you.video.sina.com.cn/api/sinawebApi/outplayrefer.php/vid=121789502_0/s.swf">
<param name="quality" value="high">
<embed src="http://you.video.sina.com.cn/api/sinawebApi/outplayrefer.php/vid=136532697/s.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="610" height="460"></embed>
</object>
 
  --%>
  
<!--  <iframe height=498 width=510 src="http://player.56.com/v_MTM2MTI0NDg0.swf" frameborder=0 allowfullscreen></iframe>
  -->
<!--   <iframe src="http://minisite.letv.com/tuiguang/index.shtml?vid=21638356&autoPlay=none&typeFrom=letv_gjzq_dqd&cid=4&ark=100&isAutoPlay=0" allowfullscreen="true" scrolling="no" border="0"
                frameborder="0" style="min-height:163px;"></iframe>
  -->
 <iframe src="http://minisite.letv.com/tuiguang/index.shtml?vid=21638356&autoPlay=none&typeFrom=letv_gjzq_dqd&cid=4&ark=100&isAutoPlay=0" width="320" height="270" frameborder="0" allowfullscreen="true"></iframe>
 
 <!--  <video controls="controls" preload="yes" width="640" height="320" src="http://v.iask.com/v_play_ipad.php?vid=136821177&as=0"/> -->

   <br/><br/>
<!--  <iframe src="http://img1.c0.letv.com/ptv/player/id=21516975" allowfullscreen="true" scrolling="no" border="0" frameborder="0" style="width:480px;height:400px;"></iframe><br/><br/>
 
 <iframe frameborder=0 src="http://v.qq.com/iframe/player.html?vid=e0015w8d8fu"></iframe>
 <iframe src="http://m.tv.sohu.com/hots/128?vid=2154034" allowfullscreen="true" scrolling="no" border="0" frameborder="0" "></iframe>
 <br/><br/> -->

 
 <!--
 
   <iframe src="http://www.56.com/iframe/MTIwMjQ4Mjg0"></iframe>
  优酷：<br/><br/>
 <iframe allowfullscreen="" frameborder="0" width="640" height="320" src="http://player.youku.com/embed/XODQ2MTQ4NjI4"></iframe><br/><br/>
  
 土豆：<br/><br/>
  <iframe src="http://www.tudou.com/programs/view/html5embed.action?type=1&code=7h6_NKbMuZ0&lcode=zhUCJHcs6SI&resourceId=0_06_05_99" allowtransparency="true" allowfullscreen="true" scrolling="no" border="0" frameborder="0" style="width:480px;height:400px;"></iframe><br/><br/>
 酷六：<br/><br/>
 <iframe src="http://m.ku6.com/show/TiS7WsR6e3ameG0stV7xVw...html" allowtransparency="true" allowfullscreen="true" scrolling="no" border="0" frameborder="0" style="width:480px;height:400px;"></iframe><br/><br/>
  -->
  </body>
</html>
