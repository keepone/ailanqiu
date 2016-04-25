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
	function goFrm(FormName){
	 
		 document.FormName.submit();
		
	}
</script>
 
 
 
 
    <script type="text/javascript">
 	 $(function(){
 		 $("#updateProductStatus").change(function(){
 			 var status=$("#updateProductStatus").val();
 			 var productId=$("#hidden_productId").val();
 			 var url = "product/UpdateProductStatusAction?time=+"+new Date().getTime()+"?";
 			 
    	    $.get(url, {productId: productId,status: status},function(data){
    		 	if (data=="ok"){
    			 	 alert("更新成功^^"); 
    			 	 window.location.href="/product/ProductDetail2Action?productId="+productId;
    			 	 //window.location.href="/user/GetUserApplyByConditionsAction";
    			 	 document.myFrm.submit(); //回到发起请求的页面并刷新。
    			 } else {	
    			 	 alert("更新失败"); 
    			 }
    		 }); 
 			 
 		 });
 	 });
  </script>
  
   <script type="text/javascript">
 	 function updateImagesStatus(imageId,imageStatus,updateType){
 		  
 			 var productId=$("#hidden_productId").val();
 			 var url;
 			 if(updateType=='detail'){
 				 url = "product/UpdateDetailImageStatusAction?time=+"+new Date().getTime()+"?";
 			 }else{
 				 url = "product/UpdateThumbnailImageStatusAction?time=+"+new Date().getTime()+"?";
 			 }
 			  
 			 
    	    $.get(url, {productId: productId,imageId:imageId,status: imageStatus},function(data){
    		 	if (data=="ok"){
    			 	 alert("更新成功^^"); 
    			 	 window.location.href="/product/ProductDetail2Action?productId="+productId;
    			 	 //window.location.href="/user/GetUserApplyByConditionsAction";
    			 	 document.myFrm.submit(); //回到发起请求的页面并刷新。
    			 } else {	
    			 	 alert("更新失败"); 
    			 }
    		 }); 
 			 alert(x);
 	 
 	 }
 	 
 	  	 function updateThumbnailStatus(imageId,imageStatus,updateType){
 		  
 			 var productId=$("#hidden_productId").val();
 			 var url;
 			 if(updateType=='detail'){
 				 url = "product/UpdateThumbnailImageStatusAction?time=+"+new Date().getTime()+"?";
 			 }else{
 				 url = "product/UpdateThumbnailImageStatusAction?time=+"+new Date().getTime()+"?";
 			 }
 			  
 			 
    	    $.get(url, {productId: productId,imageId:imageId,status: imageStatus},function(data){
    		 	if (data=="ok"){
    			 	 alert("更新成功^^"); 
    			 	 window.location.href="/product/ProductDetail2Action?productId="+productId;
    			 	 //window.location.href="/user/GetUserApplyByConditionsAction";
    			 	 document.myFrm.submit(); //回到发起请求的页面并刷新。
    			 } else {	
    			 	 alert("更新失败"); 
    			 }
    		 }); 
 			 alert(x);
 	 
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
    <input type="text" id="hidden_productId" value="${json.id}"> 
 
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
    
    
     
     
     
    <table class="tablelist" style="width: 100%;margin-top: 5px;">
    	<thead>
    	<tr>
        <th  style="width: 80%;" colspan="2">Product Info<i class="sort"><img src="images/px.gif" /></i></th>
        <th  style="width: 20%;" >
        		 
		<select name="updateProductStatus" id="updateProductStatus">
			<option value="1"  <c:if test="${json.status==1}"> selected="selected" </c:if>>yes</option>
			<option value="0"  <c:if test="${json.status==0}"> selected="selected" </c:if>>no</option>
		</select>
	 
        </th>
        </tr>
         <tr>
		    <td style="width: 10%;">name</td>
		    <td style="width: 70%;">detail</td> 
		    <td style="width: 20%;">href</td> 
		     
		 </tr>
        </thead>
        <tbody>
         
 
  
   <tr>
 
    <td>
   		 ${json.productName} 
    </td>
    <td  style="width:900px;" >
    <table><%--
      <c:forEach items="${json.detailImgs}" var="detail" varStatus="d">
    	<tr>
    		<td  style="width: 80%;" ><img width="120px" height="100px;" src="${detail.pic}" /></td>
    		<td  style="width: 5%;" >${detail.pic}</td>
    		<td  style="width: 10%;"  >
    				 <form  name="updatedetailFrm${d.index}" method="post" action="/product/UpdatePhotoAction" enctype="multipart/form-data">
				    	
				    	 
				    	  <input type="hidden" name="productId" id="productId" value="${json.productId}"/>
				    	 <input type="hidden" name="detailOrThumbnailId" id="detailOrThumbnailId" value="${detail.id}"/>
				    	 <input type="hidden" name="updateType" id="updateType" value="1"/>
			 				<input type="file" id="userImg" name="resource"/>
			  				<input type="submit" value="Submit" onclick="goFrm('updatedetailFrm${d.index}')"/>
						</form>
    		</td>
    		<td  style="width: 5%;" >
    		<c:if test="${detail.status==1}">
    		 
    			 
				<input type="radio" name="identity" value="0" onclick="updateImagesStatus('${detail.id}','0','detail')"/>no
    		</c:if>
    		<c:if test="${detail.status==0}">
    			<input type="radio" name="identity" value="1" onclick="updateImagesStatus('${detail.id}','1','detail')"/>yes
				 
    		</c:if>	
    		</td>
    	</tr>
    	 </c:forEach>
    --%>
    
    <tr>
    		<td  style="width: 80%;" ><img alt="" src="${json.logo}"></td>
    		
    		<td  style="width: 10%;"  >
    				 <form  name="updatedetailFrm" method="post" action="/product/UpdatePhotoAction" enctype="multipart/form-data">
				    	
				    	 
				    	  <input type="hidden" name="productId" id="productId" value="${json.id}"/>
				    	 <input type="hidden" name="detailOrThumbnailId" id="detailOrThumbnailId" value="${detail.id}"/>
				    	 <input type="hidden" name="updateType" id="updateType" value="1"/>
			 				<input type="file" id="userImg" name="resource"/>
			  				<input type="submit" value="Submit" onclick="goFrm('updatedetailFrm${d.index}')"/>
						</form>
    		</td>
    	 
    	</tr>
    
    </table>
     
       	
      
	</td>
	
	<td  style="width:300px;" >
	  
		  
     ${json.logo} 
     
	</td>
	

	 </tr>
	  
	 
       
        
        
             
        </tbody>
    </table>
     

    
    
    
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
