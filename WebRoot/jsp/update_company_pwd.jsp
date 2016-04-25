<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<% request.setCharacterEncoding("UTF-8"); %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>密码修改</title>
	<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
	<script src="../js/jquery.validate.js" type="text/javascript"></script>
	 <script type="text/javascript">
 function  check(){
 	return true;
 }
	$().ready(function() {
	 
 $("#myForm").validate({
	
        rules: {
   updatePwd: {

    required: true,

    minlength: 5

   },

   certainUpdatePwd: {

    required: true,

    minlength: 5,

    equalTo: "#password"

   }

  },

        messages: {

    
   updatePwd: {

    required: "请输入密码",

    minlength: jQuery.format("密码不能小于{0}个字符")

   },

   certainUpdatePwd: {

    required: "请输入确认密码",

    minlength: "确认密码不能小于5个字符",

    equalTo: "两次输入密码不一致"

   }

  }

    });

});
</script>
<style type="text/css">
div{
	height:35px;
}
	input.error { border: 1px solid red; }
	label.error {
  background:url("images/del.gif") no-repeat 0px 0px;

  padding-left: 16px;

  padding-bottom: 2px;

  font-weight: bold;

  color: red;
  
  #div_wrap{widht:200px; border:1px solid red;}
  
  
}

label.checked {
  background:url("images/del.gif") no-repeat 0px 0px;
}
</style>
</head>
 
<body >
 <h1>密码修改</h1>
  <form  name="myForm" id="myForm"  action="company/UpdateCompanyLoginPwdAction" method="post">
	<table>
	   	<tr><td>修改前密码：</td><td><input type="text" name="loginPwd"/></td></tr>
	   	<tr><td>修改后密码：</td><td><input id="password"  type="password" name="updatePwd"/></td></tr>
	   	<tr><td>确认密码：</td><td><input type="password" name="certainUpdatePwd"/></td></tr>
   </table>
   		<input  type="submit" value="修改" style="position:relative; margin-left:190px"  /> 
  </form>
</body>
   
</html>
