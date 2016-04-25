<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jiutong.com/tag/pager" prefix="jt" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
 	 <base href="<%=basePath%>">
    <title>用户管理-分配角色</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
  
  </head>
  
  <body>
   <form name="myFrm" action="/user/UserManageAction" method="post">
   <table>
   <tr>
   	<td style="width:170px; font-weight: bold;font-size:20px;">用户名称</td><td style="font-weight: bold;font-size:20px;">操作</td>
   </tr>
   <c:forEach items="${pageBean.data}" var="json">
   	<tr>
   		<td>${json.userName}</td><td><a href="/role/ToAssignRoleAction?userId=${json.userId}&&userName=${json.userName}">分配角色</a></td>
   	</tr>
   	</c:forEach>
   </table>
     <jt:pager2 FORM_NAME="myFrm"/>
 </form>
  </body>
</html>
