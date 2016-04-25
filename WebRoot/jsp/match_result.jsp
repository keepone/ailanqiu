<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://jiutong.com/tag/pager" prefix="jt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tranb" uri="/WEB-INF/tld/tranb-ocr.tld"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
  <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
 
 
    <script type="text/javascript">
 function updateValue(userId,companyId,selectIndex,paramName,cooperateContent){
 
 	var selectName=paramName+selectIndex;
 	
  var paramValue=document.getElementById(selectName).value;
 
 	var url = "company/AddCooperateAction?time="+new Date().getTime()+"?";
    		$.get(url, {userId: userId,companyId: companyId,cooperateContent: cooperateContent},function(data){
    			
    			if (data=="ok"){
    				 alert("更新成功^^"); 
    				document.myFrm.submit();
    			} else {	
    				alert(data);
    				 document.myFrm.submit();
    			}
    		});
 }
</script>
 <!-- 根据多条件查询申请用户信息 -->
<script type="text/javascript">
  	 $(function(){
  	 	$("#toSearch").click(function(){
  	 			$("#clickType").val("查询");
  		 
  	 	});
      });
  	 
  </script>
<style>
	td{text-align: center;}
</style>
</head>
  <body>
    <fieldset>
    	 
    
    <form name="myFrm" action="user/UserMatchResultAction" method="post">
    <!-- 判断用户是点击查询还是点击上一页下一页，默认为非查询，如果是查询则在Action中，应将当前页数设为1 -->
     <input type="hidden" id="clickType" name="clickType" value="非查询">   
    
    
     <div style="float: left;margin-left:30px;">合作状态：
    	 <select name="cooperateContent">  
    		<option  value="请选择"   >请选择</option>
    		<option  value="待量房" <c:if test="${cooperateContent=='待量房'}"> selected="selected" </c:if> >待量房</option> 
			<option  value="待签约" <c:if test="${cooperateContent=='待签约'}"> selected="selected" </c:if> >待签约</option>
			<option  value="已签约" <c:if test="${cooperateContent=='已签约'}"> selected="selected" </c:if> >已签约</option> 
		</select> 
    </div>
  	 
  	 
    
      <div style="position:relative;margin-left:90%;">
    <input id="toSearch" type="submit" class="scbtn" value="查询"/>
	</div>
      
   
				<br>
				<table border="1" style="border: 0" cellspacing="0" cellpadding="4" width="90%" align="center" >
					<thead>
						<tr align="center" height="30px" bgcolor="#F8F8FF">
							<td width="3%">姓名</td>
							<td width="3%">手机</td>
							<td width="3%">公司</td>
							<td width="3%">电话</td>
							<td width="3%">手机</td>
							<td width="3%">量房</td>
							 <td width="3%">签约</td>
						</tr>
					</thead>
					<tbody>
								   <c:forEach items="${pageBean.data}" var="m" varStatus="status">
								  
									   	<tr >
											<td rowspan="${fn:length(m.value)+1}">
											${m.key.userName}--${m.key.callName}<br/>
											${m.key.source}
											</td>
											<td rowspan="${fn:length(m.value)+1}">${m.key.userPhone}</td>
											 <c:forEach var="v" items="${m.value}" varStatus="aa">
											 	<c:choose>
											 		<c:when test="${aa.index==1}">
											 			
											 			<td>${v.companyName}</td>
											 			<td>${v.companyTelephone}</td>
														<td>${v.companyPhone}</td>
														<c:choose>
														 <c:when test="${v.lookHouse=='是'}">
											 			<td>
											 				 是
											 			</td>
											 			  </c:when>
											 			  <c:otherwise>
											 			     <c:choose>
											 			     	<c:when test="${cooperateContent=='已签约'}">
											 			     		<td>否</td>
											 			     	</c:when>
											 			     	<c:otherwise>
											 			     		<td>
														 				<select id="cooperate${status.index}${aa.index}" onchange="updateValue('${v.userId}','${v.companyId}','${status.index}${aa.index}','cooperate','量房')">
														 					<option value="否">否</option>
														 					<option value="是"><span style="color:red;">是</span></option>
														 				</select>
											 			    		</td>
											 			     	</c:otherwise>
											 			     </c:choose>
											 			  	 
											 			  </c:otherwise>
											 			</c:choose>
											 			
											 			
											 			
											 			 
											 			<c:choose>
											 		
												 		 <c:when test="${v.lookHouse=='是'&&cooperateContent=='待量房'}">
												 		 	<td>
												 				 等待签约中……
												 			</td>
												 		 </c:when>
												 		  <c:when test="${v.lookHouse=='是'&&cooperateContent=='待签约'}">
												 		 	<td>
												 				<select id="cooperate${status.index}${aa.index}" onchange="updateValue('${v.userId}','${v.companyId}','${status.index}${aa.index}','cooperate','签约')">
												 					<option value="否">否</option>
												 					<option value="是"><span style="color:red;">是</span></option>	
												 				</select>
												 			</td>
												 		 </c:when>
												 		  <c:when test="${cooperateContent=='已签约'}">
												 		 	 <c:choose>
												 		 	 	<c:when test="${v.sign=='是'}">
												 		 	 		<td>是</td>
												 		 	 	</c:when>
												 		 	 	<c:otherwise>
												 		 	 		<td>否</td>
												 		 	 	</c:otherwise>
												 		 	 </c:choose>
												 		 </c:when>
												 		 <c:otherwise>
												 		  
												 		<td> 等待量房中</td>
												 		 </c:otherwise>
											 			</c:choose>
											 			
											 			
											 			
											 			
											 			</tr>
											 		</c:when>
													<c:otherwise>
														<tr>
														<td>${v.companyName}</td>
														<td>${v.companyTelephone}</td>
														<td>${v.companyPhone}</td>
														
														<c:choose>
														 <c:when test="${v.lookHouse=='是'}">
											 			<td>
											 				 是
											 			</td>
											 			  </c:when>
											 			  <c:otherwise>
											 			     <c:choose>
											 			     	<c:when test="${cooperateContent=='已签约'}">
											 			     		<td>否</td>
											 			     	</c:when>
											 			     	<c:otherwise>
											 			     		<td>
														 				<select id="cooperate${status.index}${aa.index}" onchange="updateValue('${v.userId}','${v.companyId}','${status.index}${aa.index}','cooperate','量房')">
														 					<option value="否">否</option>
														 					<option value="是"><span style="color:red;">是</span></option>
														 				</select>
											 			    		</td>
											 			     	</c:otherwise>
											 			     </c:choose>
											 			  	 
											 			  </c:otherwise>
											 			</c:choose>
											 			
											 			
											 			
											 			 
											 			<c:choose>
											 		
												 		 <c:when test="${v.lookHouse=='是'&&cooperateContent=='待量房'}">
												 		 	<td>
												 				 等待签约中……
												 			</td>
												 		 </c:when>
												 		  <c:when test="${v.lookHouse=='是'&&cooperateContent=='待签约'}">
												 		 	<td>
												 				<select id="cooperate${status.index}${aa.index}" onchange="updateValue('${v.userId}','${v.companyId}','${status.index}${aa.index}','cooperate','签约')">
												 					<option value="否">否</option>
												 					<option value="是"><span style="color:red;">是</span></option>	
												 				</select>
												 			</td>
												 		 </c:when>
												 		  <c:when test="${cooperateContent=='已签约'}">
												 		 	 <c:choose>
												 		 	 	<c:when test="${v.sign=='是'}">
												 		 	 		<td>是</td>
												 		 	 	</c:when>
												 		 	 	<c:otherwise>
												 		 	 		<td>否</td>
												 		 	 	</c:otherwise>
												 		 	 </c:choose>
												 		 </c:when>
												 		 <c:otherwise>
												 		  
												 		<td> 等待量房中</td>
												 		 </c:otherwise>
											 			</c:choose>
														
														</tr>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</tr>
								   </c:forEach> 
								 
						</tbody>
					</table>
					<br>
					 <jt:pager2 FORM_NAME="myFrm"/>
 </form>
				</fieldset>
  </body>
 
</html>
