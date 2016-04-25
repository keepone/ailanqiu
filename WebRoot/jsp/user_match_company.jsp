<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://jiutong.com/tag/pager" prefix="jt" %>
<%@ taglib prefix ="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <base href="<%=basePath%>">
    
   <title>用户匹配</title>
   	<link href="../css/style.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript"  src="../js/Ctry/select.js"></script>
    <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
    <script type="text/javascript" src="../js/calendar.js"></script>
	<script type="text/javascript" src="../js/jquery.js"></script>
<style type="text/css">
tr{ text-align:center;}
td select{width:100%;}
.select_wrap{
 border:1px solid red; width:1500px;
}
</style>
<script type="text/javascript">
	function test(){
		 
		
	}
</script>
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
   	function matchCompany(companyId,userId){
   			//在发送ajax请求的时候，为了保证每次都与服务器交互，就要传递一个参数每次都不一样，这里就用了时间戳
   			var url = "user/AddCompanyMatchUserAction?time=+"+new Date().getTime()+"?";
    		$.get(url, {companyId: companyId,userId: userId}, function(data){
    			if (data=="ok"){
    				 alert("推送成功^^");
    				// window.location.href="/user/GetUserApplyByConditionsAction?search_certainStatus=certain";
    			}else if(data=="have_match") {	
    				 alert("推送失败！该用户已被推送给该家装公司！"); 
    			}else if(data=="have_match_enough") {	
    				 alert("推送失败！该用户已被推送至3家家装公司！"); 
    			}else{	
    				 alert("推送失败！"); 
    			}
    		});
   	}
 
  </script>
</head>
  
  <body onload="test()">
    
    
   
    
 <h1   style="cursor:pointer;width:68px; font-size: 30px;margin-left: 15px;margin-top: 20px;"  onclick="javascript :history.back(-1);">返回</h1>   
 <table class="tablelist">
	   
    	 
    	<thead>
    	
    	<tr>
        <th colspan="2">个人信息<i class="sort"><img src="images/px.gif" /></i></th>
        <th colspan="2">房型</th>
        <th colspan="2">预算</th>
        <th colspan="4" >所在地</th>
        
        </tr>
         <tr>
    <td>姓名</td>
    <td>手机号</td>
    <td>房屋类型</td>
    <td>房屋结构</td>
    <td>方式</td>
    <td>预算额（万元）</td>
     <td>省市</td>
    <td>市/地区</td>
    <td>县/市</td>
    <td>小区名称</td>
    
  </tr>
        </thead>
        <tbody>
        
  
   <tr>
   <td>${userName}</td>
	<td>${phone}</td>
    <td>${homeType}</td>
	<td>${homeStructure}</td>
	<td >${budgetType}</td>
	<td>${budgetNum}</td>
	<td>${province}</td>
	<td>${city}</td>
	<td>${area}</td>
	<td>${community}</td>
	 
     
  </tr>
  
        
        
             
        </tbody>
    </table>
    
     <c:if test="${jsonArr==null||jsonArr=='[]'}">
     <div style="font-size: 15px;font-weight: bold">当前系统无法匹配到符合客户【${userName}】家装信息的家装公司，是否立即将客户信息推送至所有家装公司？<a href="javascript:;">是</a></div>
     </c:if>
   <table class="tablelist" style="width:100%;" >
   	<tr><td style="text-align: left;" colspan="2"><h1 style=" font-size: 30px;">匹配列表</h1></td></tr>
   	 <c:forEach items="${jsonArr}" var="company">
	  <tr>
	   <td style="width:5%">
	   <input  onclick=" matchCompany('${company.companyId}','${userId}')" type="checkbox"> 
	   </td>
	    <td style="width:15%">${company.companyName}</td>
	     <td style="width:5%">${company.linkName}</td>
	      <td style="width:5%">${company.phone}</td>
	       <td style="width:7%">${company.telephone}</td>
	    <td style="width:15%">${company.areaDetail}</td>
	    <td style="width:10%">${company.runScope}</td>
	    <td style="width:5%">${company.runWay}</td>
	    <td style="width:5%">${company.priceBegin}万-${company.priceEnd}万</td>
	  </tr><br/>
	  </c:forEach>
   </table>
   
      <table class="tablelist" style="width:60%;" >
   	 
   	<tr>
   	<c:choose>
   	<c:when test="${mark=='无'}">
   		<td style="font-size: 15px;font-weight: bold">用户【${userName}】暂未被推送至任何公司。</td>
   	</c:when>
   	 
	<c:otherwise>
			<td style=" text-align: left;">用户【${userName}】已被推送至下列公司：</td>
	</c:otherwise>
   	</c:choose>
   
   	</tr>
	  <tr>
	   <td  style="width:5%;text-align: left;">
	 
	<c:if test="${company1!=null&&company1!=''}">
		【${company1}】
	</c:if>
	<c:if test="${company2!=null&&company2!=''}">
		【${company2}】
	</c:if>
	<c:if test="${company3!=null&&company3!=''}">
		【${company3}】
	</c:if>
	
	 </td>
	  </tr> 
	 
   </table>
  </body>
</html>
