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
    <title>Product</title>
     <script type="text/javascript"  src="../js/Ctry/select.js"></script>
	<link href="../css/style.css" rel="stylesheet" type="text/css" />
	 <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="../js/calendar.js"></script>
	<script type="text/javascript" src="../js/jquery.js"></script>
<script type="text/javascript">
$(document).ready(function(){
  $(".click").click(function(){
  $(".tip").fadeIn(200);
  });
  
  $(".tiptop a").click(function(){
  $(".tip").fadeOut(200);
});

  $(".sure").click(function(){
  $(".tip").fadeOut(100);
});

  $(".cancel").click(function(){
  $(".tip").fadeOut(100);
});

});
</script>
 
     

 

<script type="text/javascript">
	function recommend(productId,directory1){
		 var url = "product/RecommendAction?time=+"+new Date().getTime()+"?";
		  $.get(url, {productId: productId,directory1: directory1},function(data){
    		 	if (data=="ok"){
    			 	 alert("更新成功^^"); 
    			 	 
    			 } else {	
    			 	 alert("更新失败"); 
    			 }
    		 }); 
		
	}
</script>
 
 
 
 
    <script type="text/javascript">
 	function refresh(){
 		 	document.myFrm.submit();
 	}
 
  </script>
  <style type="text/css">
tr{ text-align:center;}
td select{width:100%;}
.select_wrap{
width:1500px;
}
</style>
  </head>
  
  <body onload="Init();">
   <form name="myFrm3" action="user/MatchCompanyAction" method="post">    
   
 
 </form>
   <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">用户管理</a></li>
    <li><a href="#">用户信息</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li  onclick="javascript:window.history.back(-1);"><span><img src="images/t01.png" /></span>返回</li>
        <li  onclick="refresh()"><span><img src="images/t02.png" /></span>刷新</li>
        <li><span><img src="images/t03.png" /></span>删除</li>
        <li><span><img src="images/t04.png" /></span>统计</li>
        </ul>
        
        
        <ul class="toolbar1">
        <li><span><img src="images/t05.png" /></span>设置</li>
        </ul>
    
    </div>
    
    <form name="myFrm" action="/product/ProductList2Action" method="post">
     <!-- 判断用户是点击查询还是点击上一页下一页，默认为非查询，如果是查询则在Action中，应将当前页数设为1 -->
     
     <div style="float: left;width:700px;">
      
  	<div style="height:40px;margin-top: 20px;">
	  			 
	  	 <div style="float: left;margin-left: 30px;">Category：
	  	 	<select name="search_category">  
	  	 	 	<option  value="">请选择</option>
	  	 	 	 <c:forEach items="${jsonArray}" var="category">
				  <option  value="${category.unionCode}"  <c:if test="${search_category==category.unionCode}"> selected="selected" </c:if> >${category.name}</option>  
				  </c:forEach>
			</select> 
	  	 </div>
	  	  <div style="float: left;margin-left: 30px;">Brand：
	  	 	<select name="search_brand">  
	  	 	 	<option  value="">请选择</option>
	  	 	 	 <c:forEach items="${json.brand}" var="brand">
				  <option  value="${brand.brand}"  <c:if test="${search_brand==brand.brand}"> selected="selected" </c:if> >${brand.brand}</option>  
				  </c:forEach>
			</select> 
	  	 </div>
	  	  <div style="float: left;margin-left: 30px;"> 产品名称：
	  	 	 <input type="text" value="${search_product_name}" name="search_product_name">
	  	 </div>
  	</div>
      <div style="position:relative;margin-left:90%;">
    <input id="toSearch" type="submit" class="scbtn" value="查询"/>
     
	</div>
     </div>
    
     
     
     
    <table class="tablelist" style="width: 100%;margin-top: 5px;">
    	<thead>
    	<tr>
        <th  style="width: 5%;" colspan="4">Product Info<i class="sort"><img src="images/px.gif" /></i></th>
        </tr>
         <tr>
		    <td style="width: 5%;">name</td>
		    <td style="width: 8%;">detail</td> 
		    <td style="width: 8%;">recommend</td> 
		     <td style="width: 8%;">update</td> 
		 </tr>
        </thead>
        <tbody>
         
<c:forEach items="${pageBean.data}" var="product" varStatus="status" >
  
   <tr>
 
    <td>
   		 ${product.productName} 
    </td>
    <td>
    	<img alt="" src="${product.productImage}"/>
    <%--
    <table>
      <c:forEach items="${product.detailImgs}" var="detail" varStatus="d">
    	<tr>
    		<td><img width="120px" height="100px;" src="${detail.pic}" /></td>
    		<td>${detail.pic}</td>
    	 
    	
    	</tr>
    	 </c:forEach>
    </table>
     
       	
      
	--%></td>
	<%--<td>
	<table>
		<c:forEach items="${product.thumbnailImgs}" var="thumbnail" varStatus="t">
    	<tr>
    		<td><img  width="120px" height="100px;" src="${thumbnail.pic}"/></td>
    		<td>${thumbnail.pic}</td>
    		 
    	</tr>
    	  </c:forEach>
    </table>
		  
  </td>
		--%>
		<td>
		<c:if test="${product.ifRecommend=='true'}">
			recommend
		</c:if>
		<c:if test="${product.ifRecommend=='false'}">
				<input type="radio" name="identity" value="0" onclick="recommend('${product.productId}','${product.directory1}')"/>recommend
		</c:if>
	
		
		</td>
		<td><a href="/product/ProductDetail2Action?productId=${product.productId}">update</a></td>
	 </tr>
	 <div style="height:10px;"></div>
	 </c:forEach>
       
        
        
             
        </tbody>
    </table>
    <jt:pager2 FORM_NAME="myFrm"/>
 </form>
    
    
    
    <div class="tip">
    	<div class="tiptop"><span>提示信息</span><a></a></div>
        
      <div class="tipinfo">
        <span><img src="images/ticon.png" /></span>
        <div class="tipright">
        <p>是否确认对信息的修改 ？</p>
        <cite>如果是请点击确定按钮 ，否则请点取消。</cite>
        </div>
        </div>
        
        <div class="tipbtn">
        <input name="" type="button"  class="sure" value="确定" />&nbsp;
        <input name="" type="button"  class="cancel" value="取消" />
        </div>
    
    </div>
    
    
    
    
    </div>
    
    <script type="text/javascript">
	$('.tablelist tbody tr:odd').addClass('odd');
	</script>

</body>
</html>
