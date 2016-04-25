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
    <a href="news/AddRule"> 录入规则［http://www.ilanqiu.com/rules］</a><br><br>
    <a href="news/AddTactics">录入战术[http://www.172lanqiu.com/tuwenjiaoxue]</a><br><br>
    
     <a href="news/AddHistorySpecial"> 考古系［网易］</a><br><br>
    <a href="news/AddPersonlSpecial">人物志[网易]</a><br><br>
     
     
     
       <a href="news/AddHistoryRecords">录入历史纪录[搜狐]</a><br><br>
     <a href="news/AddSpecialFromNBA">录入专栏文章[nba中国]</a><br><br>
    <a href="news/AddNBATeamRank">NBA球队排名［新浪］</a><br><br>
    <a href="news/AddNBAPlayerRank">NBA球员数据统计［新浪］</a><br><br>
    
     <a href="news/AddCBATeamRank">CBA球队统计［腾讯］</a><br><br>
    <a href="news/AddCBAPlayerRank">CBA球员数据统计［腾讯］</a><br><br>
     <a href="news/AddWCBAPlayerRank">WCBA球员数据统计［新浪］</a><br><br><br><br>
    
    
     <a href="news/Add56">教学视频［56］</a><br><br>
      <a href="news/AddStarVideo">球星视频［新浪］</a><br><br>
    <a href="news/AddJiaNongTeach">加农贝克［优酷］</a><br><br>
     <a href="news/AddWuHu">五虎［优酷］</a><br><br>
       <a href="news/AddNike">Nike［优酷］</a><br><br>
     <a href="news/AddShuQi">暑期［优酷］</a><br><br>
     
   
    
  </body>
</html>
