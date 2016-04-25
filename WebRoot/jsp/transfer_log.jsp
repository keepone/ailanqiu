<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jiutong.com/tag/pager" prefix="jt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'openAccount.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	  <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
   <style type="text/css">
   	td{text-align: center};
   </style>
  </head>
  
  <body>
  
   <p>账户:${jsonObject.companyName}</p>
    <c:choose>
		<c:when test="${companyId=='2527'}">
		 
		</c:when>
		<c:otherwise>
			 <p>赠送量房数量:${jsonObject.count}</p>
		</c:otherwise>
	</c:choose>
   
     <p>当前余额:${balanceMoney}</p>
     	 <form action="company/TransLogAction?companyId=${companyId}" name="myFrm" method="post">
     	 	<table width="60%">
     	<tr><td>交易日期</td><td>交易类型</td><td>交易金额</td>
     	<%--<td>交易内容</td>
     	
     <td>交易时间</td>	--%>
     <td>支付方</td><td>操作员</td></tr>
     	 <c:forEach items="${pageBean.data}" var="json">
     		<tr>
     			<td>${json.transTime}</td>
     		 
     			<td>${json.transType }</td>
     		 
     			 <td>${json.transMoney }</td><%--
     			 
     			 <c:choose>
     				<c:when test="${json.transType=='存入'&&companyId=='2527'}">
     					<td>量房</td>
     					<td>${json.transTime}</td>
     				</c:when>
     				<c:when test="${json.transType=='存入'&&companyId!='2527'}">
     					<td>充值</td>
     					<td>${json.transTime}</td>
     				</c:when>
     				<c:when test="${json.transType=='开户'}">
     					<td> 开户</td>
     					<td>${json.transTime}</td>
     				</c:when>
     				<c:otherwise>
     					<td>${json.cooperateContent }</td>
     					<td>${json.cooperateTime}</td>
     				</c:otherwise>
     				</c:choose>
     				--%><td>${json.payCompany}</td>
     			 <td>${json.operater}</td>
     			  
     		 </tr>
     	  </c:forEach>
     	</table>
     	 <jt:pager2 FORM_NAME="myFrm"/>
     	 </form>
      
  </body>
</html>
