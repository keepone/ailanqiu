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
   
	<script type="text/javascript"> 
	function href(url) {
    		jQuery('#report').load(url);
   		 } 
   		 
   	function href_function(url) {
    		jQuery('#report').load(url);
   		 } 
    function changeQuery(obj){
				var state = obj.value;
				var url="/sp/admin/showCircle.do?state="+state;
				href(url);
	}
			
			
	function message(btn){
		var offset = $(btn).offset();		
		$("#updateDiv").css({ top: offset.top, left:offset.left });
		document.getElementById("updateDiv").style.display="block";
		document.getElementById("name").value="";
		document.getElementById("introduce").value="";
		
	}
	
	function closeShow(){
		document.getElementById("updateDiv").style.display="none";
	}		
	function updates(page,state){
		var url="/sp/admin/showCircle.do?state="+state+"&page="+page;
		href(url);
	}		
			
 function addCircle(state) {
	var or = $("#circleimg");
	var type = or.val().substring(or.val().lastIndexOf("."),or.val().length);
	var types = new Array(".jpg",".png");
	var	flag = false;
	
	for(var i=0; i<types.length;i++){
		if (types[i] == type)
			flag = true;
	}	
	var name=$("#name").val();
	var introduce=$("#introduce").val();
	if(!name.length>0){
			alert("请输入圈子名称！");
			$("#name").focus();
			return false;
	}
	if(!introduce.length>0){
		alert("请输入圈子介绍！");
		$("#introduce").focus();
		return false;
	}
	if (or.val() == ''){
		alert("请选择图片");		
		return false;
	}
	if (flag){ 		
		$("#uform").attr("action","/sp/admin/addCircle.do?name="+name+"&introduce="+introduce+"&state="+state);
		document.getElementById("updateDiv").style.display="none";		
		return true;		
	}else{
		alert("请选择正确格式的图片！");
		return false;	
	}
}

	function circleState(id,display,page,state){
		$.post("/sp/admin/circleState.do?id="+id+"&display="+display,function(data){
			if(data){
				alert("操作成功");
				href("/sp/admin/showCircle.do?page="+page+"&state="+state);
			}else{
				alert("操作失败");
			}
		});
	}


  </script>
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
							<td width="4%">性别</td>
							<td width="13%">电话</td>
							<td width="8%">注册时间</td>
							<td width="8%">房屋类型</td>
							<td width="8%">房屋结构</td>
							<td width="6%">预算类型</td>
							<td width="6%">预算数量</td>
							<td width="6%">省份</td>
							<td width="6%">城市</td>
							<td width="6%">区域</td>
							<td width="6%">社区</td>
							<td width="6%">资源</td>
							 
							 
							 
						</tr>
					</thead>
					<tbody>
					<c:choose>
						<c:when test="${pageBean.data!=null&&pageBean.data!=''}">
					
								<c:forEach items="${pageBean.data}" var="user" varStatus="status">
									<tr  align="center" id="p${v_user.id}" <c:if test="${status.index%2==0}">bgcolor="#F6F8FA"</c:if>>
										<td>${user.userName}&nbsp;</td>
										<td>${user.callName} &nbsp;</td>
										<td>${user.phone} &nbsp;</td>
										<td>${user.CreateTime}&nbsp;</td>
										<td>${user.homeType} &nbsp;</td>
										<td>${user.homeStructure} &nbsp;</td>
										<td>${user.budgetType}&nbsp;</td>
										<td>${user.budgetNum} &nbsp;</td>
										<td>${user.province} &nbsp;</td>
										<td>${user.city}&nbsp;</td>
										<td>${user.area} &nbsp;</td>
										<td>${user.community} &nbsp;</td>
										<td>${user.resource}&nbsp;</td>
										 
										 
										 
									</tr>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr  align="center" >
										<td colspan="13" style="font-weight: bold;color:red;">你好，暂时还没有符合你公司家装条件的客户！</td>
							</c:otherwise>
							</c:choose>
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
