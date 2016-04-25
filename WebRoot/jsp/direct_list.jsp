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
				 
					比赛： 
					 
			
				<br>
				<table border="1" style="border: 0" cellspacing="0" cellpadding="4" width="90%" align="center" >
					<thead>
						<tr align="center" height="30px" bgcolor="#F8F8FF">
							 
							<td width="20%">标题</td>
							<td width="10%">时间</td>
							<td width="10%">直播</td>
							<td width="20%">状态</td> 
						   <td width="5%">统计</td> 
							<td width="15%">操作</td>
						</tr>
					</thead>
					<tbody>
				 
					<c:forEach items="${pageBean.data}" var="direct">
								   
									<tr  align="center" id="">
										 
										<td>
											<img alt="" src="/images/team_logo/${direct.guest_team_logo}">
											${direct.guest_team}<span style="color:red;font-weight: bold">${direct.guest_score==0?"":direct.guest_score }</span>VS
											<img alt="" src="/images/team_logo/${direct.host_team_logo}">
											${direct.host_team}<span style="color:red;font-weight: bold">${direct.host_score==0?"":direct.host_score }</span></td>
											<td><qilang:date DATE="${direct.date}"/>,${direct.week},${direct.game_time}
										</td>
										<td>
											<c:forEach items="${direct.direct_broadcast}" var="bro">
												${bro.value}
											</c:forEach>
										</td>
										
										 <td>
										 	<c:choose>
											<c:when test="${direct.status==0}">
												还未开赛
											</c:when>
											<c:when test="${direct.status==2}">
												已结束
											</c:when>
											<c:otherwise>
												正在比赛
											</c:otherwise>
										</c:choose>
										 </td>
										 <td>
										 	<c:choose>
											<c:when test="${direct.status==0}">
												 
											</c:when>
											<c:when test="${direct.status==2||direct.status==1}">
												<a href="/gamedata/GameDataAction?directId=${direct.id}&guest_team=${direct.guest_team}&host_team=${direct.host_team}" target="_blank">统计</a>
											</c:when>
											<c:otherwise>
												 
											</c:otherwise>
										</c:choose>
										 </td>
										<td>  <a href="/news/GetSpecialAction?specialId=${direct.id}">编辑&nbsp;删除&nbsp;置顶</a></td>
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
