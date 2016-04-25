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
				 
					比赛： ${guest_team}VS${host_team}
					 
			
				<br>
				 
					 
								<table border="1" style="border: 0" cellspacing="0" cellpadding="4" width="90%" align="center" >
					<thead>
						<tr align="center" height="30px" bgcolor="#F8F8FF">
						<td height="10%">球员</td>
						<td width="5%">时间</td><td width="40">投篮</td>
						<td width="5%">三分</td><td width="40">罚球</td>
						<td width="5%">前篮板</td><td width="40">后篮板</td>
						<td width="5%">总篮板</td><td width="35">助攻</td>
						<td width="5%">抢断</td><td width="35">盖帽</td>
						<td width="5%">失误</td><td width="35">犯规</td>
						<td width="5%">得分</td>
						</tr>
						
					</thead>
					<tbody>
					<tr>
						<td colspan="14">${guest_team}技术统计</td>
					</tr>
					<c:forEach items="${guest_team_data}" var="p" varStatus="i">	
					<c:choose>
						<c:when test="${i.index<5}">
							<tr  align="center" id="">
						<td height="20">${p.name}(首发)</td>
						<td>${p.play_time}</td>
						<td>${p.shoot}-${p.shoot_count}</td>
						<td>${p.three_score}-${p.three_score_count}</td>
						<td>${p.penalty_shot}-${p.penalty_shot_count}</td>
						<td>${p.before_rebound}</td>	
						<td>${p.after_rebound}</td>
						<td>${p.rebound}</td>
						<td>${p.holding_attack}</td>	
						<td>${p.steal}</td>
						<td>${p.swat}</td>
						<td>${p.fault}</td>
						<td>${p.foul}</td>
						<td>${p.score}</td>
					</tr>
						</c:when>	 
						<c:otherwise>
							<tr  align="center" id="">
						<td height="20">${p.name}</td>
						<td>${p.play_time}</td>
						<td>${p.shoot}-${p.shoot_count}</td>
						<td>${p.three_score}-${p.three_score_count}</td>
						<td>${p.penalty_shot}-${p.penalty_shot_count}</td>
						<td>${p.before_rebound}</td>	
						<td>${p.after_rebound}</td>
						<td>${p.rebound}</td>
						<td>${p.holding_attack}</td>	
						<td>${p.steal}</td>
						<td>${p.swat}</td>
						<td>${p.fault}</td>
						<td>${p.foul}</td>
						<td>${p.score}</td>
					</tr>
						</c:otherwise>
					</c:choose>
					  
					
					</c:forEach>		
					
					
					
					<tr>
						<td colspan="14">${host_team}技术统计</td>
					</tr>
					<c:forEach items="${host_team_data}" var="p" varStatus="i">	
					<c:choose>
						<c:when test="${i.index<5}">
							<tr  align="center" id="">
						<td height="20">${p.name}(首发)</td>
						<td>${p.play_time}</td>
						<td>${p.shoot}-${p.shoot_count}</td>
						<td>${p.three_score}-${p.three_score_count}</td>
						<td>${p.penalty_shot}-${p.penalty_shot_count}</td>
						<td>${p.before_rebound}</td>	
						<td>${p.after_rebound}</td>
						<td>${p.rebound}</td>
						<td>${p.holding_attack}</td>	
						<td>${p.steal}</td>
						<td>${p.swat}</td>
						<td>${p.fault}</td>
						<td>${p.foul}</td>
						<td>${p.score}</td>
					</tr>
						</c:when>	 
						<c:otherwise>
							<tr  align="center" id="">
						<td height="20">${p.name}</td>
						<td>${p.play_time}</td>
						<td>${p.shoot}-${p.shoot_count}</td>
						<td>${p.three_score}-${p.three_score_count}</td>
						<td>${p.penalty_shot}-${p.penalty_shot_count}</td>
						<td>${p.before_rebound}</td>	
						<td>${p.after_rebound}</td>
						<td>${p.rebound}</td>
						<td>${p.holding_attack}</td>	
						<td>${p.steal}</td>
						<td>${p.swat}</td>
						<td>${p.fault}</td>
						<td>${p.foul}</td>
						<td>${p.score}</td>
					</tr>
						</c:otherwise>
					</c:choose>
					  
					
					</c:forEach>			 			 
						</tbody>
					</table>
					<br>
						 
				 
				
					  
			</form>
				</fieldset>
  </body>
 
</html>
