<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户管理-分配角色</title>
    <s:head/>
  </head>
  
  <body>
    <s:form namespace="/role" action="DoAssignRoleAction">
    <table width="100%">
    	<tr>
    		<td>
    		<input type="hidden" name="userId" value="${userId}">
    		<input type="hidden" name="userName" value="${userName}">
    		用户编号:${userId}</td>
    		<td>用户名称:${userName}</td>
    		<td><s:submit value="保存" /></td>
    	</tr>
    	<tr>
    		<td>
    			<s:optiontransferselect 
    			leftTitle="已分配角色" rightTitle="未分配角色" 
    			name="roleIds" 
    			
    			doubleListKey="roleId" 
    			doubleListValue="roleName"
    		    doubleList="notList"
    		    
    		     listKey="roleId"
    		     listValue="roleName" 
    		     list="haveList"
    		     
    		      doubleName="notRoleIds"></s:optiontransferselect>
    		</td>
    	</tr>
    </table>
    </s:form>
  </body>
</html>
