<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'left.jsp' starting page</title>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    
	<script language="JavaScript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function(){	
	//导航切换
	$(".menuson li").click(function(){
		$(".menuson li.active").removeClass("active")
		$(this).addClass("active");
	});
	
	$('.title').click(function(){
		var $ul = $(this).next('ul');
		$('dd').find('ul').slideUp();
		if($ul.is(':visible')){
			$(this).next('ul').slideUp();
		}else{
			$(this).next('ul').slideDown();
		}
	});
})	
</script>

  </head>
  
  <body style="background: #1abc9c;">
	<!-- <div class="lefttop"><span></span>通讯录</div> -->
    
    <dl class="leftmenu">
        
    <dd>
    <div class="title">
    <span><img src="images/leftico01.png" /></span>信息管理
    </div>
    	<ul class="menuson">
        <li><cite></cite><a href="/company/BackGroundAction" target="rightFrame">首页</a><i></i></li>
        
          <li><cite></cite><a href="jsp/admin/add_user.jsp" target="rightFrame">添加用户</a><i></i></li>
            <li><cite></cite><a href="admin/dynamic/DynamicList?pageNo=1&pageSize=20" target="rightFrame">帖子管理</a><i></i></li>
            
        </ul>    
    </dd>
        
    
    
    
   
    
   </dl>
    
</body>
</html>
