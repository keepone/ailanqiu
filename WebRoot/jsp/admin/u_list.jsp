<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.lanqiure.com/tag" prefix="lqr" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  	  			<script type="text/javascript"
	src="<%=request.getContextPath()%>/js/calendar.js"></script>
	
  <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
   <script type="text/javascript">
	function recommend(replyUserId,dynamicId){
		 var url = "admin/dynamic/AddDynamicAgree?time=+"+new Date().getTime()+"?";
		 var userId=$("#userId  option:selected").val();

		 
		  $.get(url, {userId: userId,replyUserId: replyUserId,dynamicId: dynamicId},function(data){
    		 	if (data=="ok"){
    			 	 alert("执行成功^^"); 
    			 	 
    			 } else {	
    			 	 alert("执行失败"); 
    			 }
    		 }); 
    		 //alert(userId+"--"+replyUserId+"--"+dynamicId);
		
	}
	
		function recommend_2(replyUserId,dynamicId){
		 var url = "admin/dynamic/AddDynamicAgree2?time=+"+new Date().getTime()+"?";
		 
		 
		  $.get(url, {replyUserId: replyUserId,dynamicId: dynamicId},function(data){
    		 	if (data=="ok"){
    			 	 alert("执行成功^^"); 
    			 	 
    			 } else {	
    			 	 alert("执行失败"); 
    			 }
    		 }); 
    		 //alert(userId+"--"+replyUserId+"--"+dynamicId);
		
	}
	
	function focusUser(focusUserId){
		 var url = "admin/user/AddFocusOfOne?time=+"+new Date().getTime()+"?";
		 

		 
		  $.get(url, {focusUserId: focusUserId},function(data){
    		 	if (data=="1"){
    			 	 alert("执行成功^^"); 
    			 	 
    			 } else {	
    			 	 alert("执行失败"); 
    			 }
    		 }); 
    		 //alert(userId+"--"+replyUserId+"--"+dynamicId);
		
	}
	
	function RecommendDynamic(dynamicId){
		 var url = "admin/dynamic/RecommendDynamic?time=+"+new Date().getTime()+"?";
		 

		 
		  $.get(url, {dynamicId: dynamicId},function(data){
    		 	if (data=="ok"){
    			 	 alert("推荐成功^^"); 
    			 	 
    			 } else {	
    			 	 alert("执行失败"); 
    			 }
    		 }); 
    		 //alert(userId+"--"+replyUserId+"--"+dynamicId);
		
	}
	
	
	function replyDynamic(replyUserId,dynamicId,replyLeaveId){
		 var url = "admin/dynamic/AddDynamicLeave?time=+"+new Date().getTime()+"?";
		 var replyContent=$("#replyContent").val();
	  var userId=$("#userId  option:selected").val();
		 
		  $.get(url, {replyContent: replyContent,userId: userId,replyUserId: replyUserId,dynamicId: dynamicId,replyLeaveId:replyLeaveId},function(data){
		 
    		 	if (data=="0"){
    			 	 alert("执行失败"); 
    			 	 
    			 } else {	
    			  alert("执行成功^^"); 
    			 	
    			 }
    		 }); 
    		 //alert(userId+"--"+replyUserId+"--"+dynamicId);
		
	}
	
	function replyDynamic_2(replyUserId,dynamicId,replyLeaveId,user_id){
		 var url = "admin/dynamic/AddDynamicLeave?time=+"+new Date().getTime()+"?";
		 var replyContent=$("#replyContent").val();
	  
		 
		  $.get(url, {replyContent: replyContent,userId: user_id,replyUserId: replyUserId,dynamicId: dynamicId,replyLeaveId:replyLeaveId},function(data){
		 
    		 	if (data=="0"){
    			 	 alert("执行失败"); 
    			 	 
    			 } else {	
    			  alert("执行成功^^"); 
    			 	
    			 }
    		 }); 
    		 //alert(userId+"--"+replyUserId+"--"+dynamicId);
		
	}
	
	function pushDynamic(dynamicId){
		 var url = "admin/dynamic/AddDynamicPush?time=+"+new Date().getTime()+"?";
		 var dynamicContent=$("#replyContent").val();
		 if (!confirm("确认要推送？")) {
             
        }else{
         $.get(url, {dynamicContent: dynamicContent,dynamicId: dynamicId},function(data){
		 
    		 	if (data=="0"){
    			 	 alert("执行失败"); 
    			 	 
    			 } else {	
    			  alert("执行成功^^"); 
    			 	
    			 }
    		 }); 
        }
	}
	
</script>
	  <script language="javascript">
    function delcfm() {
        if (!confirm("确认要删除？")) {
            window.event.returnValue = false;
        }
    }
</script>
  <body>
    <fieldset>
 
     
     <form name="myFrm" action="/ailanqiu/admin/user/UserList" method="post">
     <!-- 判断用户是点击查询还是点击上一页下一页，默认为非查询，如果是查询则在Action中，应将当前页数设为1 -->
     
     <div style="float: left;width:700px;">
      
  	<div style="height:40px;margin-top: 20px;">
	  			
	  			

			
			 <div style="float: left;margin-left: 30px;">用户类型：
			 	开始日期:<input
			id="startTime" name="startTime" style="width: 180px;"
			onclick="new Calendar().show(this);" value="${startTime}" />- 结束日期:<input
			id="endTime" name="endTime" style="width: 180px;"
			onclick="new Calendar().show(this);" value="${endTime}" />
			
			 </div>
	  	 
	  	 
			 
	  	 <div style="float: left;margin-left: 30px;">用户类型：
	  	 	 
				
				 <select id="userType" name="userType">
    
     	<option name="lbhayyp"   <c:if test="${userType==0 }">selected="selected" </c:if>     value="0">手机</option>
     	<option name="lbhayyp"  <c:if test="${userType==1}">selected="selected" </c:if> value="1">QQ</option>
     	<option name="lbhayyp"  <c:if test="${userType==2 }">selected="selected" </c:if> value="2">微博</option>
     	<option name="lbhayyp"  <c:if test="${userType==null }">selected="selected" </c:if> value="">全部</option>
     
     	 
     </select>
     
   
	  	 </div>
	  	 
	  	 
	  	   	 
	  	 
	  	 
	  	
     
     
	  	  
	  	<%--   <div style="float: left;margin-left: 30px;"> 产品名称：
	  	 	 <input type="text" value="${searchDynamicContent}" name="searchDynamicContent">
	  	 </div>
	  	   <div style="float: left;margin-left: 30px;">用户名：
	  	 	 <input type="text" value="${userName}" name="userName">
	  	 </div> --%>
  	</div>
      <div style="position:relative;margin-left:90%;height: 50px;">
    <input id="toSearch" type="submit" class="scbtn" value="查询"/>
     
	</div>
     </div>
     
    	<%-- 	 <select id="userId" name="userId">
     <c:forEach items="${userArray}" var="user">
     	<option name="lbhayyp" value="${user.id }">${user.user_name}</option>
     	 </c:forEach>
     </select><br> --%>
     
				<legend>
				 <br>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp;<a href="/create_excel.html">导出客户信息报表</a>
				 </legend>
				<br>
				<table border="1" style="border: 0" cellspacing="0" cellpadding="4" width="90%" align="center" >
					<thead>
						<tr align="center" height="30px" bgcolor="#F8F8FF">
							<td width="7%">姓名</td>
							  <td width="13%">用户电话</td>
					 
							 <td width="13%">时间</td>
							 
							 
						</tr>
					</thead>
					<tbody>
					 
						 
					
								<c:forEach items="${pageBean.data}" var="json" varStatus="status">
									<tr  align="center" id="p${v_user.id}" <c:if test="${status.index%2==0}">bgcolor="#F6F8FA"</c:if>>
										<td>${json.user_name}&nbsp;</td>
										<td>${json.phone}&nbsp;</td>
										 	 
										 
										  <td><lqr:date DATE="${json.create_time}"></lqr:date></td>
								
									 
										 
										 	
									</tr>
								</c:forEach>
						 
						</tbody>
					</table>
					<br> 	
					<lqr:pager2 FORM_NAME="myFrm"/>
					</form>		
	</fieldset>
	 
  </body>
  <c:url var="paginationUrlPattern" value="javascript:href('/sp/admin/showReportByDay.do?')">
			<c:param name="page" value="_page_"/>
</c:url>

</html>
