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
    <title>用户管理</title>
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
 function updateValue(userId,phone,selectIndex,paramName){
 	var selectName=paramName+selectIndex;
 var paramValue=document.getElementById(selectName).value;
 if(paramName=='budgetNum'){
 var reg = new RegExp("^[0-9\.]*$");
	 if (!reg.test(paramValue)){
		alert("请输入数字");
		return false;
	}
 }
 	
 	var url = "user/UpdateUserApplyAction?time=+"+new Date().getTime()+"?";
 	var mark="1";
    		$.get(url, {mark: mark,userId: userId,phone: phone,paramName: paramName,paramValue: paramValue},function(data){
    			if (data=="ok"){
    				 alert("更新成功^^"); 
    			} else {	
    				 alert("更新失败"); 
    			}
    		});
 }
</script>
<script type="text/javascript">
  //客服点击修改用户地址或添加对用户的备注时，获取相关值并显示默认隐藏的元素。
 function replaceSelect(userId,phone,paramName,paramValue){
 				document.getElementById("hiddenUserId").value=userId;
 				document.getElementById("hiddenPhone").value=phone;
 				//根据参数
 				if(paramName=='hiddenAreaSelect'){
 					document.getElementById("hiddenAreaSelect").style.display="block";
 					document.getElementById("hiddenUserRemark").style.display="none";
 				}else{
 					document.getElementById("hiddenAreaSelect").style.display="none";
 					document.getElementById("userRemark").innerHTML=paramValue;
 					
 					document.getElementById("hiddenUserRemark").style.display="block";
 					document.getElementById("userRemark").focus();
 				}
 				
 				window.scroll(0,0);
 };
 
 
 
 
 function changeSelectAreaMark(){
 	document.getElementById("SelectAreaMark2").style.display="none";
 	document.getElementById("SelectAreaMark3").style.display="block";
 }
</script>


 
 

<!-- 修改用户备注 -->
<script type="text/javascript">
 $(function(){
 		$("#submitValue_2").click(function(){
 		var userId=$("#hiddenUserId").val();
 		var phone=$("#hiddenPhone").val();
 			var url = "user/UpdateUserApplyAction?time=+"+new Date().getTime()+"?";
 			var mark="1";
 			var paramName="userRemark"
 			var paramValue=$("#userRemark").val();
    	    $.get(url, {mark: mark,userId: userId,phone: phone,paramName: paramName,paramValue: paramValue},function(data){
    		 	if (data=="ok"){
    			 	 alert("更新成功^^"); 
    			 	 document.getElementById("hiddenUserRemark").style.display="none";
    			 	 //window.location.href="/user/GetUserApplyByConditionsAction";
    			 	 document.myFrm.submit(); //回到发起请求的页面并刷新。
    			 } else {	
    			 	 alert("更新失败"); 
    			 }
    		 }); 
 		});
 		//点击【取消】按钮时，修改备注，都将被隐藏。
 		$("#cancelValue_2").click(function(){
 	 		document.getElementById("hiddenUserRemark").style.display="none";
 		});
 });
</script>


<!-- 根据多条件查询申请用户信息 -->
<script type="text/javascript">
  	 $(function(){
  	 	$("#toSearch").click(function(){
  	 			$("#clickType").val("查询");
  		 
  	 	});
      });
  	 
  </script>
  
  <!-- 对已经确认的申请用户，执行匹配家装公司 -->
    <script type="text/javascript">
    function matchCompany(userId,userName,phone,homeType,homeStructure,budgetType,budgetNum,province,city,area,community){	
    	$("#hiddenUid").val(userId);
    	$("#hiddenUserName").val(userName);
    	$("#hiddenPhone").val(phone);
    	$("#hiddenHomeType").val(homeType);
    	$("#hiddenHomeStructure").val(homeStructure);
    	$("#hiddenBudgetType").val(budgetType);
    	$("#hiddenBudgetNum").val(budgetNum);
    	$("#hiddenProvince").val(province);
    	$("#hiddenCity").val(city);
    	$("#hiddenArea").val(area);
    	$("#hiddenCommunity").val(community);
    	document.myFrm3.submit();
    }
  </script>
  
<!-- 根据时间在升序和降序之间切换查询数据。（数据量大时，不建议使用该种方式） -->
  <script type="text/javascript">
$(function(){
	  	$("#applyTime").click( 
			function () { 
				var rule=$("#timeSortRule").val();
				if(rule=="desc"){
					$("#timeSortRule").val("asc");
				}else{
					$("#timeSortRule").val("desc");
				}
				document.myFrm.submit();
			} 
	);
	
});
  </script>
 
<!-- 对从PC端提交信息的用户，在客服确认信息后，将用户账户及密码通过短信告之用户 -->
   <script type="text/javascript">
 	function sendMessage(phone){
 		 	 //在发送ajax请求的时候，为了保证每次都与服务器交互，就要传递一个参数每次都不一样，这里就用了时间戳
    		var url = "user/SendMessageAction?time=+"+new Date().getTime()+"?";
    		 
    				$.get(url, {phone: phone},function(data){
    					var obj = eval("("+data+")");
    					alert("obj="+obj);
    					 var num=obj.code;
    					if(num=='0'){
    						alert("短信发送成功！");	
    					}else{
    						alert("短信发送失败！请联系管理员！")
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
  <input id="hiddenUid" type="hidden" name="userId"> 
  <input id="hiddenUserName" type="hidden" name="userName">
  <input id="hiddenPhone" type="hidden" name="phone">
  <input id="hiddenHomeType" type="hidden" name="homeType">
  <input id="hiddenHomeStructure" type="hidden" name="homeStructure">
  <input id="hiddenBudgetType" type="hidden" name="budgetType">
  <input id="hiddenBudgetNum" type="hidden" name="budgetNum">
  <input id="hiddenProvince" type="hidden" name="province">
  <input id="hiddenCity" type="hidden" name="city">
  <input id="hiddenArea" type="hidden" name="area">
  <input id="hiddenCommunity" type="hidden" name="community">
 
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
    
    <form name="myFrm" action="user/GetUserApplyByConditionsAction" method="post">
     <!-- 判断用户是点击查询还是点击上一页下一页，默认为非查询，如果是查询则在Action中，应将当前页数设为1 -->
     <input type="hidden" id="clickType" name="clickType" value="非查询"> 
 	 <input type="hidden" id="hiddenUserId"/>
 	  <input type="hidden"  id="timeSortRule"  name="timeSortRule" value=${timeSortRule}> 
     <div style="float: left;width:700px;">
      
  	<div style="height:40px;margin-top: 20px;">
	  			 <div style="float: left;">no/name:<input style="border: 1px solid gray" type="text" value="${userName}" name="userName"/> </div>
	  	  
    
      <div style="position:relative;margin-left:90%;">
    <input id="toSearch" type="submit" class="scbtn" value="search"/>
	</div>
     </div>
  </div>
    <table class="tablelist" style="width: 100%;margin-top: 5px;">
    	 
 
        <tbody>
        <c:choose>
        	<c:when test="${pageBean.data!=null&&pageBean.data!=''}">
        		 <c:forEach items="${pageBean.data}" var="order" varStatus="status" >
  
   <tr>
   	<td>${order.createTime}&nbsp;&nbsp;&nbsp;${order.orderNo}</td>
   </tr>
   <tr>
 
    <td><img width="50px" height="40px" src="${order.image}"/></td>
    <td>
      ${order.productName}	
	</td>
	<td>
	  
	</td>
	<td width="100px"> 
	  
	</td>
	<td>
		 
	</td>
 
 
		<td><a href="/user/UserDetailAction?phone=${user.phone}"><span  style="cursor:pointer;color:red;">修改</span></a></td>
	 
  </tr>
  </c:forEach>
        	</c:when>
        	<c:otherwise>
        		<tr>
        			<td colspan="10" style="text-align: center;color:red;font-weight: bold;font-size: 23px;">----【没有发现任何猎物噢，换个搜索条件试试^^】----</td>
        		</tr>
        	</c:otherwise>
        </c:choose>
       
        
        
             
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
