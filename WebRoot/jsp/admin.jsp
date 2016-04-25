<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>篮球热，为每一个校园篮球迷服务</title>
	    <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
		<script type="text/javascript"><%--
		$(function(){
				var explorer = window.navigator.userAgent ;
				//ie 
				if (explorer.indexOf("MSIE") ==-1) {
				 alert("建议使用IE浏览器使用本系统！");
				//window.location.href="/jsp/error.jsp";
				};
			
		});
		--%></script>
		<link href="../img/logo.ico" rel="shortcut icon">
</head>
 <form action="">
 <input type="hidden" value="${jsonObject.loginName}" name="ff"> 
 </form>
  <frameset rows="88,*" cols="*" frameborder="no" border="0" framespacing="0">
  <frame src="jsp/top.jsp" name="topFrame" scrolling="No" noresize="noresize" id="topFrame" title="topFrame" />
  <frameset cols="187,6,*" frameborder="no" border="0" framespacing="0" name="pageframe" id="pageframe">
    <frame src="jsp/left.jsp"  name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame" title="leftFrame" />
    <frame src="jsp/frameline.jsp"   scrolling="No" noresize="noresize" name="pageline" title="middelFrame"/>
    <frame src="company/BackGroundAction" name="rightFrame" id="rightFrame" title="rightFrame" />
  </frameset>
</frameset>
  <noframes>
<body>
</body></noframes>
</html>
