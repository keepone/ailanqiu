<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@  taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tranb" uri="/WEB-INF/tld/tranb-ocr.tld"%>
<%@ taglib uri="http://jiutong.com/tag/pager" prefix="qilang" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  	<script src="assets/scripts/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
    function href(url) {
    	//jQuery('#report').load(url);
    	window.location.href=url;
    }
    
    </script>
  <body>
    <fieldset>
    		<form style="margin-left: 55px;" action="/news/NewsListAction" name="myFrm" id="searchform" method="post">
				 
					专栏作家： 
					 
			
				<br>
				<table border="1" style="border: 0" cellspacing="0" cellpadding="4" width="90%" align="center" >
					<thead>
						<tr align="center" height="30px" bgcolor="#F8F8FF">
							 
							<td width="10%">标题</td>
							<td width="20%">时间</td>
							 
						 
							<td width="15%">操作</td>
						</tr>
					</thead>
					<tbody>
				 
					<c:forEach items="${pageBean.data}" var="news">
								   
									<tr  align="center" id="">
										 
										<td>${news.name}</td>
										<td><qilang:date DATE="${news.addTime}"/></td>
										 
										 
										<td>  <a href="/news/GetSpecialAction?specialId=${news.id}">编辑&nbsp;删除&nbsp;置顶</a></td>
									</tr>
					</c:forEach>				 
								 
						</tbody>
					</table>
					<br>
					  <qilang:pager2 FORM_NAME="myFrm"/>
			</form>
				</fieldset>
  </body>
 
</html>
