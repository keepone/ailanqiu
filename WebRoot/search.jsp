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
    
    <title>My JSP 'search.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	 <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	function go(itemId,video_url){
		 
		  var url="/lucene/AddUserItemAction";
		 	$.get(url, {url: video_url,itemId:itemId},function(data){
    			if (data=="ok"){
    				 
    				window.location.href=video_url;
    				 
    			} else {	
    				 window.location.href=video_url;
    			}
    		});
	}
</script>
  </head>
  <body>
   <form action="lucene/SearchAction" name="myFrm" method="post">
   <c:choose>
   	<c:when test="${searchKey!=null&&searchKey!=''}">
   		<input type="text" name="searchKey" value="${searchKey}"/>
   	</c:when>
   	<c:otherwise>
   		<input type="text" name="searchKey" value="艾弗森"/>
   	</c:otherwise>
   </c:choose>
   	<input type="submit" value="搜索"/>
   </form>
   <c:forEach items="${jsonArray}" var="video" varStatus="i">
   <c:if test="${i.index==1}">
   	 共搜索到<span style="color:red;font-weight: bold;font-family: serif">${video_video_count}</span>条记录
   </c:if>
   	 <table>
   	 	<tr><td><a href="javascript:void(0)" onclick="go('${video.itemId}','${video.video_href}')">${video.video_name }</a><br/><br/></td></tr>
   	 	<%--<tr><td><a href="${video.video_img}">${video.video_img }</a></td></tr>
   	 	<tr><td><img alt="" src="v2.hoopchina.com.cn/nba/2a/bf/690378_373.jpg"/> </td></tr>
   	 --%></table>
   </c:forEach>
  </body>
</html>
