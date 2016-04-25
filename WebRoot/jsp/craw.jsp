<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'craw.jsp' starting page</title>
    
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
    <a href="news/AddNBANewsAction">录入nba新闻［腾讯，新浪，tbba，NBA98］</a>&nbsp;&nbsp;&nbsp;<a href="http://sports.qq.com/l/basket/nba/newsmore.htm" target="_blank">address</a><br><br>
      <a href="news/AddZhiBoBaNews">录入nba新闻［直播吧］</a><br><br><br><br>
      
     <a href="news/AddCBANewsAction">录入cba新闻[搜狐，新浪，新华]</a>&nbsp;&nbsp;&nbsp;<a href="http://roll.sports.sina.com.cn/s_campusbasket_all/index.shtml" target="_blank">address</a><br><br>
     
      <a href="news/AddTencentCBANews">录入cba新闻[腾讯]</a>&nbsp;&nbsp;&nbsp;<a href="http://sports.qq.com/l/cba/CBAlist.htm" target="_blank">address</a><br><br> 

    <a href="news/AddNBAVideoAction">录入新浪nba视频</a><br><br>
    
  <a href="news/AddNBA98Video">录入NBA98 nba视频</a><br><br>    
  
    <a href="news/AddLETVVideoAction">录入LETV</a><br><br>
 
    <a href="news/AddHuPuVideoAction">录入虎扑视频</a><br><br>
    
  <!--     
     <a href="news/AddEurope">录入Europe新闻[搜狐］</a><br><br>
    <a href="news/AddWCBA">录入WCBA新闻［腾讯］</a><br><br><br><br>
         
     <a href="news/AddZhiBoBaSpecial">录入直播吧专题</a><br><br><br><br>
  
   
    <a href="news/AddNBASoHuAction">录入nba新闻[搜狐]</a><br><br>
   <a href="news/AddLuXiangAction">录入比赛录像</a><br><br>
    <a href="news/AddDirectAction">录入比赛直播</a><br><br>
     <a href="news/AddNBAMatchResultAction">录入NBA比赛结果［新浪］</a><br><br>
    <a href="news/AddCBAMatchResultAction">录入CBA比赛结果［新浪］</a><br><br>
  -->
     <a href="news/AddFalseUser">录入用户</a><br><br>
    

    
    
     
  </body>
</html>
