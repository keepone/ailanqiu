<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'openAccount.jsp' starting page</title>
    
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
     <form action="company/DoAddOpenAccountAction" name="myFrm" method="post">
     <input type="text" value="${companyId}" name="companyId">
     	<table>
     		<tr>
     			<td>开户方:</td><td>${companyName}</td>
     		</tr>
     		<tr>
     			<td>赠送量房数量:</td><td><input type="text" name="freeCount"/></td>
     		</tr>
     		<tr>
     			<td>开户金额:</td><td><input type="text" name="openMoney"/></td>
     		</tr>
     		<tr><td colspan="2"><input type="submit" value="确定"/></td></tr>
     	</table>
     </form>
  </body>
</html>
