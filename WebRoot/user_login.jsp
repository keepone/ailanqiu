<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    
    <title>用户登录</title>
 	<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	 <script src="js/jquery.js" type="text/javascript"></script>
	<script src="js/jquery.validate.js" type="text/javascript"></script>
	<link type="text/css" rel="stylesheet" href="css/screen.css" />
 	 
 	 <SCRIPT type="text/javascript">
 	 	   
     function checkUser(){
     	
    	// var uName=$("#uname").val();
    	var uName=document.getElementById("uname").value;
    	
    	 if(uName=="false"){
    	 	 
    	 	return false;
    	 }else{
    	 	return true;
    	 }
     }
 	 </SCRIPT>
 	 

  <script type="text/javascript">
	$().ready(function() {
	 
 $("#myForm").validate({
	
        rules: {
    userName:{
    	required:true,
    	rangelength:[2,8]
    },

   userPwd: {
    required: true,
    minlength: 5

   }
  },

        messages: {

   //userName: "<span style='color='red''>请输入姓名</span>",
    userName:{
    	required:"请输入用户名",
   	rangelength:"请输入2-8位用户名"
    },
   
   userPwd: {
    required: "请输入密码",
    minlength: jQuery.format("密码不能小于{0}个字符")

   }
  }

    });

});
</script>
<style type="text/css">
	input.error { border: 1px solid red; }
	label.error {
  background:url("images/del.gif") no-repeat 0px 0px;

  padding-left: 16px;

  padding-bottom: 2px;

  font-weight: bold;

  color: red;
}
label.checked {
  background:url("images/del.gif") no-repeat 0px 0px;
}
</style>
  
 
  </head>
  
  <body>
  <input  type="hidden"  id="uname"/>
  
     <s:form  method="post"  id="myForm" name="myForm" onsubmit="checkUser()" action="/dologin.html"   enctype="multipart/form-data">
     <div>
     <label for="userName"> 用户名:&nbsp;&nbsp;</label>
     	<input name="userName" id="user_name" type="text">
     </div>
      <div>
      <label for="password"> 密码:&nbsp;&nbsp;&nbsp;&nbsp;</label>
     	<input id="password" name="userPwd"  type="password">
     </div>
     <div><span style="color:red">${error}</span></div>

     <s:submit value="登录"></s:submit>
  </s:form> 

  </body>
 
</html>
