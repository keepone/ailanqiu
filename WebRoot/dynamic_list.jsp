<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://jiutong.com/tag/pager" prefix="jt" %>
 
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
   
	
  <body>
    <fieldset>
    		
				<legend>
				 <br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="/create_excel.html">导出客户信息报表</a>
				 </legend>
				<br>
				<table border="1" style="border: 0" cellspacing="0" cellpadding="4" width="90%" align="center" >
					<thead>
						<tr align="center" height="30px" bgcolor="#F8F8FF">
							<td width="7%">用户</td>
							 
							<td width="13%">图片</td>
							 
							 <td width="13%">内容</td>
							  <td width="13%">编辑</td>
							 
							 
						</tr>
					</thead>
					<tbody>
					 
						 
					
								<c:forEach items="${jsonArray}" var="json" varStatus="status">
									<tr  align="center" id="p${v_user.id}" <c:if test="${status.index%2==0}">bgcolor="#F6F8FA"</c:if>>
										<td>${json.user_name}&nbsp;</td>
										 
										<td><img src="${json.img} " width="220px" height="200px"/></td>
										 <td>${json.text_content }</td>
										 <td><a href="/ailanqiu/admin/dynamic/DeleteDynamic?dynamicId=${json.dynamic_id }">删除</a></td>
										 
										 
									</tr>
								</c:forEach>
						 
						</tbody>
					</table>
					<br> 			
	</fieldset>
	 
	 <form name="myFrm" action="/user/user_list.html" method="post">
    	<jt:pager TARGET_URL="/user/GetAllCustomerInfoByCompanyIdAction"/>
    </form>
    
  </body>
  <c:url var="paginationUrlPattern" value="javascript:href('/sp/admin/showReportByDay.do?')">
			<c:param name="page" value="_page_"/>
</c:url>

</html>
