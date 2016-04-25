<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>
<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>用户登录</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	
	<link href="${pageContext.request.contextPath }/assets/css/reset.css" rel="stylesheet" type="text/css" />
	<link href="${pageContext.request.contextPath }/css/style.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.7.2.js"></script>
	<script src="${pageContext.request.contextPath}/js/jquery.validate.js" type="text/javascript" ></script>


<script language="javascript">
	$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    })  
});  
</script> 
<%--<script type="text/javascript">

	$().ready(function() {
	 
 $("#myFrm").validate({
        rules: {
    j_username:{
    	required:true 	 
    },
    j_password:{
    	required:true 
    }
  },
       messages: {
    j_username:{
    	required:"请输入登录用户名" 
   		 
    },
    j_password:{
    	required:"请输入密码" 
    }
  }

    });

});
</script>
--%><script type="text/javascript"><%--
	function submitForm(){
		$('#myFrm').submit();
	}
--%></script>
  </head>
  
 <!-- <body style="background-color:#1c77ac; background-image:url(images/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;"> -->
 <body style="background: #91A83E; ">


<!-- 
    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  


<div class="logintop">    
    <span>欢迎登录后台管理界面平台</span>    
    <ul>
    <li><a href="#">回首页</a></li>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    </ul>    
    </div>
 -->    
    <!-- 
    <div class="loginbody" style="background: transparent;">
    
    <span class="systemlogo"></span> 
      <form action="j_spring_security_check" id="login" name="login"
				method="post"> 
    <div class="loginbox">
   
    <ul>
    <li>
    <input name="j_username" id="userName" type="text"  class="loginuser" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}"  onclick="JavaScript:this.value=''" />
   
    </li>
    <li>
     <input name="j_password" id="userPwd" type="password" class="loginpwd"  onclick="JavaScript:this.value=''"/>
       
    </li>
    <div style="color: red; line-height: 35px;text-align:center;">
    	<c:if test="${param.error=='true'}">
    	  用户名或密码错误
    	</c:if>
    </div>
    <li>
    <input name="" type="button" class="loginbtn" value="登录"  onclick="submitForm()"  />
     <label><input id="_spring_security_remember_me" name="_spring_security_remember_me" type="checkbox" value="true"/>2周之内免登陆</label><%--
    <label><a href="#">忘记密码？</a></label>
    --%></li>
    
    </ul>
     
    
    </div>
    </form>
    </div>
     -->
     
     <style type="text/css">
     	.loginuser, loginpwd { font-size: 30px; font-weight: normal; }
     	.loginuser { position: absolute; top: 348px; left: 105px; border: 0; background: transparent; padding: 10px; margin: 0; }
     	.loginpwd { position: absolute; top: 432px; left: 105px; border: 0; background: transparent; padding: 10px; margin: 0; }
     	.loginbtn{ position: absolute; top: 535px; left: 47px; border: 0; background: transparent; width: 385px; height: 74px; color: transparent; font-size: 0; }
     </style>
     <script type="text/javascript">
     	$(function() {
     		var _fun = function() {
     			var $this = $(this);
     			if ($.trim($this.val()) == '') {
     				$this.css({background: 'transparent'});
     			} else {
     				$this.css({background: '#E5E7E9'});
     			}
     		};
     		$(".loginuser, .loginpwd").each(_fun).bind({'focus, blur, keydown, keyup': _fun});
     		$('.loginpwd').keydown(function(e) {
     			if (e.which == 13) {
     				submitForm();
     			}
     		});
     	});
     	
     	<c:if test="${param.error=='true'}">
    		alert('用户名或密码错误');
    	</c:if>
     </script>
     
     <div style="background: url('images/login_from.png') no-repeat; width: 479px; height: 639px; margin: 100px auto auto auto; position: relative;">
     	<form action="j_spring_security_check" id="myFrm" name="login" method="post">
     		 <input name="j_username" id="userName" type="text"  class="loginuser" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" />
     		 <input name="j_password" id="userPwd" type="password" class="loginpwd"/>
     		 <input   type="submit" class="loginbtn" value="登录"  />
		</form>
     </div>
    
    <!-- 
    <div class="loginbm">版权所有  【梦想家】2014  <a href="http://www.uimaker.com">www.imengxiangjia.com</a></div>
     -->
	
    
</body>
</html>
