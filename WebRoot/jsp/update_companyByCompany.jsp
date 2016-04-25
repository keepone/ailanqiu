<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<% request.setCharacterEncoding("UTF-8"); %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
 
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>公司信息修改</title>
	<script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
	 <SCRIPT type="text/javascript">
	 	$(function(){
	 	
	 		 
	 	})
	 </SCRIPT>
<style type="text/css">
div{
	height:35px;
}
	input.error { border: 1px solid red; }
	label.error {
  background:url("images/del.gif") no-repeat 0px 0px;

  padding-left: 16px;

  padding-bottom: 2px;

  font-weight: bold;

  color: red;
  
  #div_wrap{widht:200px; border:1px solid red;}
  
  
}

label.checked {
  background:url("images/del.gif") no-repeat 0px 0px;
}
</style>
</head>
 
<body >
 <h1>公司信息修改</h1>
  <form  name="myForm" id="myForm"  action="company/DoUpdateCompanyInfoAction" method="post"  enctype="multipart/form-data">
		 <input type="hidden"  name="default_img" value="${jsonObject.logo}"/> 
		 <input type="hidden" name="companyId" value="${jsonObject.companyId}"/> 
	  <div>图片:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="${jsonObject.logo}"><input type="file" id="userImg" name="resource"/></div>	<br/><br/><br/> 
     <div id="div_wrap">
     	联系电话:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="phone" value="${jsonObject.phone}" type="text">
     </div>
     <div>
     	 地区:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="area" value="${jsonObject.area}" type="text">
     </div>
     <div>
     	 详细地区:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="areaDetail" value="${jsonObject.areaDetail}" type="text"> 
     </div>    
     <div>
     	商品样式:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input name="goodStyle" value="${jsonObject.goodStyle}" type="text"> 
     </div>
     <div>
     	商品房样式:&nbsp;&nbsp;<input name="goodHouseStyle" value="${jsonObject.goodHouseStyle}" type="text"> 
     </div>
     <DIV>公司简介:&nbsp;&nbsp;&nbsp;&nbsp;
	 <textarea cols="80" name="companyIntroduction"  rows="10">${jsonObject.companyIntroduction}</textarea> 		
	</DIV>		
   		 <input  type="submit" value="修改" style="position:relative; margin-left:190px"  />
		 <input  type="reset" value="重置" style="position:relative;  margin-left:10px"  />
  </form>
  </body>
   
</html>
