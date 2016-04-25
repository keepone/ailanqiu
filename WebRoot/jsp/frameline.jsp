<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <script type="text/javascript">
		function ShowHideLeft(objtd){
			var pageframe = parent.document.getElementById('pageframe');
		if (pageframe.cols=="187,6,*"){
		   pageframe.cols="0,6,*";
		   $("body").css('background-image', "url('images/show_2.gif')");
		}
		else{
		   parent.pageframe.cols="187,6,*";
		   $("body").css('background-image', "url('images/show.gif')");
		}
		}
</script>
    <title>My JSP 'frameline.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body style="margin: 0; padding: 0; cursor: pointer; background: #E8EED0 no-repeat center; background-image: url('images/show.gif');" onclick="ShowHideLeft(this);" title="点击收起/展开">
  </body>
</html>
