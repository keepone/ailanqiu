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
    
    <title>My JSP 'product_list2.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
    <form name="myFrm" action="/product/ProductList2Action" method="post">
     <!-- 判断用户是点击查询还是点击上一页下一页，默认为非查询，如果是查询则在Action中，应将当前页数设为1 -->
     
     <div style="float: left;width:700px;">
      
  	<div style="height:40px;margin-top: 20px;">
	  			 
	  	 <div style="float: left;margin-left: 30px;">Category：
	  	 	<select name="search_category">  
	  	 	 	<option  value="请选择">请选择</option>
	  	 	 	 <c:forEach items="${jsonArray}" var="category">
				  <option  value="${category.unionCodeId}">${category.unionCode}</option>  
				  </c:forEach>
			</select> 
	  	 </div>
  	</div>
      <div style="position:relative;margin-left:90%;">
    <input id="toSearch" type="submit" class="scbtn" value="查询"/>
	</div>
     </div>
     </form>
   <table>
   	<c:forEach items="${pageBean.data}" var="product" varStatus="status" >
  
   <tr>
 
    <td>
   		 ${product.name} 
    </td>
    <td>
       <c:forEach items="${detailImgs}" var="detail">
       		${detail.pic}
       </c:forEach>
	</td>
	<td>
		  <c:forEach items="${thumbnailImgs}" var="thumbnail">
       		${thumbnail.pic}
       </c:forEach>
	</td>
	 </tr>
	 </c:forEach>
   </table>
  </body>
</html>
