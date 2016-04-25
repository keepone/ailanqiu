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
    <title>角色管理-分配资源</title>
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
  </head>
  
  <body>
   <form name="myFrm" action="user/UserManageAction" method="post">
   <table>
   <tr>
   	<td style="width:170px; font-weight: bold;font-size:20px;">角色名称</td><td style="font-weight: bold;font-size:20px;">操作</td>
   </tr>
    
   <c:forEach items="${roleList}" var="role">
   
   	<tr>
   		<td>${role.roleName}</td>
   		<td><a href="/resource/ToAssignResourceAction?roleId=${role.roleId}&&roleName=${role.roleName}">分配资源</a></td>
   	</tr>
   	</c:forEach>
   </table>
     
 </form>
  </body>
</html>
