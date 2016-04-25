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
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<link href="../css/style.css" rel="stylesheet" type="text/css" />
	
	<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="../js/jquery.js"></script>

  </head>
 
  <body>

	<div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    </ul>
    </div>
    
    <div class="mainindex">
    
    
    <div class="welinfo">
    <span><img src="../images/sun.png" alt="天气" /></span>
    <b>${jsonObject.loginName}，欢迎使用【梦想家客服系统V1.0】</b>
    <%--<a href="#">帐号设置</a>
    --%></div>
    
    <div class="welinfo">
    <span><img src="../images/time.png" alt="时间" /></span>
    
    <c:choose>
    <c:when test="${jsonObject.lastLoginTime==0}">
    	 <span style="color:red;font-size:25px;font-weight: bold">你是第一次登陆本系统，请修改密码。</span> <a href="/company/update/pwd.html">修改密码</a>
    </c:when>
    <c:otherwise>
    <i>您上次登录的时间：${jsonObject.lastLoginTime}</i> （不是您登录的？<a href="/company/update/pwd.html">修改密码</a>）
   </c:otherwise>
   </c:choose>
    </div>
    
    <%--<div class="welinfo">
    	<i><a href="../file/manual.zip">【梦想家客服系统V1.0】使用教程</a></i>
    </div>
    <div class="xline"></div>
    
    <ul class="iconlist">
    
    <li><img src="../images/ico01.png" /><p><a href="#">管理设置</a></p></li>
    <li><img src="../images/ico02.png" /><p><a href="#">发布文章</a></p></li>
    <li><img src="../images/ico03.png" /><p><a href="#">数据统计</a></p></li>
    <li><img src="../images/ico04.png" /><p><a href="#">文件上传</a></p></li>
    <li><img src="../images/ico05.png" /><p><a href="#">目录管理</a></p></li>
    <li><img src="../images/ico06.png" /><p><a href="#">查询</a></p></li> 
            
    </ul>
    
    <div class="ibox"><a class="ibtn"><img src="../images/iadd.png" />添加新的快捷功能</a></div>
    
    <div class="xline"></div>
    <div class="box"></div>
    
    <div class="welinfo">
    <span><img src="../images/dp.png" alt="提醒" /></span>
    <b>【梦想家】信息管理系统使用指南</b>
    </div>
    
    <ul class="infolist">
    <li><span>您可以快速进行文章发布管理操作</span><a class="ibtn">发布或管理文章</a></li>
    <li><span>您可以快速发布产品</span><a class="ibtn">发布或管理产品</a></li>
    <li><span>您可以进行密码修改、账户设置等操作</span><a class="ibtn">账户管理</a></li>
    </ul>
    
    <div class="xline"></div>
    
    <div class="uimakerinfo"><b>查看【梦想家】系统使用指南</b>(<a href="http://www.imengxiangjia.com" target="_blank">www.imengxiangjia.com</a>)</div>
    <ul class="umlist">
    <li><a href="#">如何发布文章</a></li>
    <li><a href="#">如何访问系统</a></li>
    <li><a href="#">如何管理广告</a></li>
    <li><a href="#">后台用户设置(权限)</a></li>
    <li><a href="#">系统设置</a></li>
    </ul>
    </div>
    
    

--%></body>
</html>
