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
<script   language="javascript"   type="text/javascript">
	
  //首先需要初始化
  var   xmlDoc;   
  var   nodeIndex; 
  function   getxmlDoc()   
  {   
      xmlDoc=new   ActiveXObject("Microsoft.XMLDOM");   
          var   currNode;   
          xmlDoc.async=false;   
          xmlDoc.load("../js/Area.xml");   
          if(xmlDoc.parseError.errorCode!=0)   
          {   
                  var   myErr=xmlDoc.parseError;   
                  alert("出错！"+myErr.reason);   
          }           
  }
  function Init()
  {
    //打开xlmdocm文档
    getxmlDoc();
    var   dropElement1=document.getElementById("Select1"); 
    var   dropElement2=document.getElementById("Select2"); 
    var   dropElement3=document.getElementById("Select3");   
    RemoveDropDownList(dropElement1);
    RemoveDropDownList(dropElement2);
    RemoveDropDownList(dropElement3);
    var  TopnodeList=xmlDoc.selectSingleNode("address").childNodes;
    if(TopnodeList.length>0)
    {
        //省份列表
        var country;
        var province;
        var city;
        for(var   i=0; i<TopnodeList.length;   i++)
        {
              //添加列表项目
              country=TopnodeList[i];       
              var   eOption=document.createElement("option");   
              eOption.value=country.getAttribute("name");
              eOption.text=country.getAttribute("name");
              dropElement1.add(eOption);
        }
        if(TopnodeList[0].childNodes.length>0)
        {
            //城市列表
            for(var i=0;i<TopnodeList[0].childNodes.length;i++)
            {
               var   id=dropElement1.options[0].value;
               //默认为第一个省份的城市
               province=TopnodeList[0]; 
               var   eOption=document.createElement("option");  
               eOption.value=province.childNodes[i].getAttribute("name");   
               eOption.text=province.childNodes[i].getAttribute("name");   
               dropElement2.add(eOption);
            }
            if(TopnodeList[0].childNodes[0].childNodes.length>0)
            {
               //县列表
               for(var i=0;i<TopnodeList[0].childNodes[0].childNodes.length;i++)
               {
                  var   id=dropElement2.options[0].value;
                  //默认为第一个城市的第一个县列表
                  city=TopnodeList[0].childNodes[0];  
                  var   eOption=document.createElement("option");  
                  eOption.value=city.childNodes[i].getAttribute("name");   
                  eOption.text=city.childNodes[i].getAttribute("name");   
                  this.document.getElementById("Select3").add(eOption);
               }
            }
        }
    }
  }   
  function   selectCity()   
  {       var   dropElement1=document.getElementById("Select1"); 
          var   name=dropElement1.options[dropElement1.selectedIndex].value;
          //alert(id);
          var   countryNodes=xmlDoc.selectSingleNode('//address/province [@name="'+name+'"]');   
          //alert(countryNodes.childNodes.length); 
          var   province=document.getElementById("Select2");       
          var   city=document.getElementById("Select3");       
          RemoveDropDownList(province);   
          RemoveDropDownList(city);
          if(countryNodes.childNodes.length>0)
          {
               //填充城市          
               for(var   i=0;   i<countryNodes.childNodes.length;   i++)   
               {   
                  var   provinceNode=countryNodes.childNodes[i];     
                  var   eOption=document.createElement("option");   
                  eOption.value=provinceNode.getAttribute("name");   
                  eOption.text=provinceNode.getAttribute("name");   
                  province.add(eOption);   
               }
               if(countryNodes.childNodes[0].childNodes.length>0)
               {
                  //填充选择省份的第一个城市的县列表
                  for(var i=0;i<countryNodes.childNodes[0].childNodes.length;i++)
                  {
                      //alert("i="+i+"\r\n"+"length="+countryNodes.childNodes[0].childNodes.length+"\r\n");
                      var   dropElement2=document.getElementById("Select2"); 
                      var   dropElement3=document.getElementById("Select3"); 
                      //取当天省份下第一个城市列表
                      var cityNode=countryNodes.childNodes[0];
                      //alert(cityNode.childNodes.length); 
                      var   eOption=document.createElement("option");  
                      eOption.value=cityNode.childNodes[i].getAttribute("name");   
                      eOption.text=cityNode.childNodes[i].getAttribute("name");   
                      dropElement3.add(eOption);
                  }
               }
          }
  }   
  function   selectCountry()   
  {   
          var   dropElement2=document.getElementById("Select2");   
          var   name=dropElement2.options[dropElement2.selectedIndex].value;   
          var   provinceNode=xmlDoc.selectSingleNode('//address/province/city[@name="'+name+'"]');   
          var   city=document.getElementById("Select3");       
          RemoveDropDownList(city);   
          for(var   i=0;   i<provinceNode.childNodes.length;   i++)   
          {   
                  var   cityNode=provinceNode.childNodes[i];     
                  var   eOption=document.createElement("option");   
                  eOption.value=cityNode.getAttribute("name");   
                  eOption.text=cityNode.getAttribute("name");   
                  city.add(eOption);   
          }   
  }   
  function   RemoveDropDownList(obj)   
  {   
      if(obj)
      {
          var   len=obj.options.length;   
          if(len>0)
          {
            //alert(len);   
            for(var   i=len;i>=0;i--)   
            {   
                  obj.remove(i);   
            }
          }
       }
            
  }   
  </script> 
  
  <script type="text/javascript">
	 function loadXML(xmlpath) {
            var xmlDoc = null;
            if (window.ActiveXObject) {
                xmlDoc = new ActiveXObject("Microsoft.XMLDOM");
            } else if (document.implementation && document.implementation.createDocument) {
                xmlDoc = document.implementation.createDocument("", "", null);
            } else {
                alert('Your browser cannot handle this script.');
            }
            xmlDoc.async = false;
            xmlDoc.load(xmlpath);
            return xmlDoc;
        }
        $(function () {
            var xmlDoc = null;
            xmlDoc = loadXML("js/Ctry/xml/Area.xml");
            var $s1 = $("#SelectProvince");
            var $s2 = $("#SelectCity");
            var $s3 = $("#SelectDistrict");
            var v1 = "省份";
            var v2 = "城市";
            var v3 = "区县";
            var root = $(xmlDoc).find("address")[0];
            $(root).children("province").each(function () {
                appendOptionTo($s1, $(this).attr("name"), $(this).attr("name"), v1);
            });
            $s1.change(function () {
                $s2.html("");
                var province_node = $(root).children("province")[this.selectedIndex];
                $(province_node).children("city").each(function () {
                    appendOptionTo($s2, $(this).attr("name"), $(this).attr("name"), v2);
                });
                $s2.change();
            }).change();
            $s2.change(function () {
                $s3.html("");
                var province_node = $(root).children("province")[$s1[0].selectedIndex];
                var city_node = $(province_node).children("city")[this.selectedIndex];
                $(city_node).children("country").each(function () {
                    appendOptionTo($s3, $(this).attr("name"), $(this).attr("name"), v3)
                });
            }).change();
            function appendOptionTo($o, k, v, d) {
                var $opt = $("<option>").text(k).val(v);
                if (v == d) { $opt.attr("selected", "selected") }
                $opt.appendTo($o);
            }
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


<!-- 修改申请用户地址信息 -->
<script type="text/javascript">
 $(function(){
 		$("#submitValue").click(function(){
 		var userId=$("#hiddenUserId").val();
 		var phone=$("#hiddenPhone").val();
 		var province=$("#SelectProvince").val();
 		if(province=='省份'){
 			alert("你还没有选择！");
 			return false;
 		}
 		var city=$("#SelectCity").val();
 		var area=$("#SelectDistrict").val();
 		var mark="2";
 		var url = "user/UpdateUserApplyAction?time=+"+new Date().getTime()+"?";
    		$.get(url, {mark: mark,userId: userId,phone: phone,province: province,city: city,area: area},function(data){
    			if (data=="ok"){
    				 alert("更新成功^^"); 
    				//window.location.href="/user/GetUserApplyByConditionsAction";
    			 	 document.myFrm.submit(); //回到发起请求的页面并刷新。
    			} else {	
    				 alert("更新失败"); 
    			}
    		}); 	
 		});
 		
 		//点击【取消】按钮时,修改地址将被隐藏。
 		$("#cancelValue").click(function(){
 	 		document.getElementById("hiddenAreaSelect").style.display="none";
 	 		
 		});
 });
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
        <li class="click"><span><img src="images/t01.png" /></span>添加</li>
        <li class="click"><span><img src="images/t02.png" /></span>修改</li>
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
	  			 <div style="float: left;">用户名:<input style="border: 1px solid gray" type="text" value="${userName}" name="userName"/> </div>
	  	 <div style="float: left;margin-left: 30px;">电话:<input style="border: 1px solid gray"  type="text" value="${phone}" name="phone"/> </div>
	  	 <div style="float: left;margin-left: 30px;">房屋类型：
	  	 	<select name="search_homeType">  
	  	 	 	<option  value="请选择"   >请选择</option>
				  <option  value="住宅公寓" <c:if test="${search_homeType=='住宅公寓'}"> selected="selected" </c:if> >住宅公寓</option>  
				  <option  value="别墅豪宅" <c:if test="${search_homeType=='别墅豪宅'}"> selected="selected" </c:if> >别墅豪宅</option>
				  <option  value="商业办公" <c:if test="${search_homeType=='商业办公'}"> selected="selected" </c:if> >商业办公</option>
				  <option  value="无信息" <c:if test="${search_homeType=='无信息'}"> selected="selected" </c:if> >无信息</option>
			</select> 
			<select id="search_homeStructure"  name="search_homeStructure">  
			<option  value="请选择"   >请选择</option>
				<option  value="一室户" <c:if test="${search_homeStructure=='一室户'}"> selected="selected" </c:if> >一室户</option>
				<option  value="两房" <c:if test="${search_homeStructure=='两房'}"> selected="selected" </c:if> >两房</option>  
				<option  value="三房"  <c:if test="${search_homeStructure=='三房'}"> selected="selected" </c:if> > 三房</option>  
				<option  value="四房"  <c:if test="${search_homeStructure=='四房'}"> selected="selected" </c:if> >四房</option>  
				<option  value="高档公寓" <c:if test="${search_homeStructure=='高档公寓'}"> selected="selected" </c:if> >高档公寓</option>  
				<option  value="超级豪宅"  <c:if test="${search_homeStructure=='超级豪宅'}"> selected="selected" </c:if> >超级豪宅</option>  
				<option  value="独栋别墅"   <c:if test="${search_homeStructure=='独栋别墅'}"> selected="selected" </c:if> >独栋别墅</option> 
				<option  value="双拼别墅" <c:if test="${search_homeStructure=='双拼别墅'}"> selected="selected" </c:if> >双拼别墅</option>   
				<option  value="联排别墅" <c:if test="${search_homeStructure=='联排别墅'}"> selected="selected" </c:if> >联排别墅</option>  
				<option  value="小户型"   <c:if test="${search_homeStructure=='小户型'}"> selected="selected" </c:if> >小户型</option>  
				<option  value="办公室" <c:if test="${search_homeStructure=='办公室'}"> selected="selected" </c:if> >办公室</option>  
				<option  value="复式楼" <c:if test="${search_homeStructure=='复式楼'}"> selected="selected" </c:if> >复式楼</option>  
				<option  value="茶楼餐厅"  <c:if test="${search_homeStructure=='茶楼餐厅'}"> selected="selected" </c:if> >茶楼餐厅</option> 
				<option  value="局部装修" <c:if test="${search_homeStructure=='局部装修'}"> selected="selected" </c:if> >局部装修</option>  
				<option  value="商铺店铺" <c:if test="${search_homeStructure=='商铺店铺'}"> selected="selected" </c:if> >商铺店铺</option>  
				<option  value="二手房" <c:if test="${search_homeStructure=='二手房'}"> selected="selected" </c:if> >二手房</option>  
			</select> 
	  	 </div>
  	</div>
  	
  	<div style="height:40px;">
  		 <div style="float: left;">方式：
    	 <select name="search_budgetType">  
    	 <option  value="请选择"   >请选择</option>
			<option  value="清包"  <c:if test="${search_budgetType=='半包'}"> selected="selected" </c:if> >清包</option>
			<option  value="半包" <c:if test="${search_budgetType=='半包'}"> selected="selected" </c:if> >半包</option>
			<option  value="全包" <c:if test="${search_budgetType=='全包'}"> selected="selected" </c:if> >全包</option>
			<option  value="软装"  <c:if test="${search_budgetType=='软装'}"> selected="selected" </c:if> >软装</option>
			<option  value="纯设计" <c:if test="${search_budgetType=='纯设计'}"> selected="selected" </c:if> >纯设计</option> 
		</select> 
    </div>
    
    
     <div style="float: left;margin-left:30px;">确认情况：
    	 <select name="search_certainStatus">  
    		<option  value="请选择"   >请选择</option>
    		<option  value="新用户" <c:if test="${search_certainStatus=='新用户'}"> selected="selected" </c:if> >新用户</option> 
			<option  value="待确认"  <c:if test="${search_certainStatus=='待确认'}"> selected="selected" </c:if> >待确认</option>
			<option  value="确认需求" <c:if test="${search_certainStatus=='确认需求'}"> selected="selected" </c:if> >确认需求</option>
			<option  value="无人接听" <c:if test="${search_certainStatus=='无人接听'}"> selected="selected" </c:if> >无人接听</option> 
		</select> 
    </div>
  	 
  	  <div style="float: left;margin-left:30px;">所在地:<input  style="border: 1px solid gray;width:80px;" type="text" name="search_area" value="${search_area}"/> </div>
  	 
  	    <div style="float: left;margin-left:30px;">用户来源：
    	 <select name="search_source">  
    		<option  value="请选择"   >请选择</option>
			<option  value="web"  <c:if test="${search_source=='web'}"> selected="selected" </c:if> >web</option>
			<option  value="iPhone" <c:if test="${search_source=='iPhone'}"> selected="selected" </c:if> >iPhone</option>
			<option  value="iPad" <c:if test="${search_source=='iPad'}"> selected="selected" </c:if> >iPad</option>
			 <option  value="android" <c:if test="${search_source=='android'}"> selected="selected" </c:if> >android</option>
		</select> 
    </div>
  	 </div>
  	 
  	
   <div style="height:40px;">
   		 <div style="float: left;">
     	申请日期:&nbsp;<input style=" width:120px;border: 1px solid gray;" value="${search_applyTimeBegin}" name="search_applyTimeBegin" type="text" id="range_date" onclick="new Calendar(1980, 2014).show(this);" size="10" maxlength="10" readonly="readonly" />
        ---&nbsp; <input style=" width:120px;border: 1px solid gray;"  value="${search_applyTimeEnd}" name="search_applyTimeEnd" type="text" id="range_date" onclick="new Calendar(1980, 2014).show(this);" size="10" maxlength="10" readonly="readonly" />
     </div>
     <div style="float: left;margin-left: 30px;">
     	确认日期:&nbsp;<input style=" width:120px;border: 1px solid gray;" value="${search_certainTimeBegin}"  name="search_certainTimeBegin" type="text" id="range_date" onclick="new Calendar(1980, 2014).show(this);" size="10" maxlength="10" readonly="readonly" />
        ---&nbsp;<input style=" width:120px;border: 1px solid gray;"  value="${search_certainTimeEnd}" name="search_certainTimeEnd" type="text" id="range_date" onclick="new Calendar(1980, 2014).show(this);" size="10" maxlength="10" readonly="readonly" />
     </div>
   </div>
    
      <div style="position:relative;margin-left:90%;">
    <input id="toSearch" type="submit" class="scbtn" value="查询"/>
	</div>
     </div>
     <div style="float: left;">
       	
       	<!-- 修改用户地址 -->
     	 <div id="hiddenAreaSelect" style="display: none;border:1px solid red;width:300px;">
     	 <span style="font-size: 15px;color:black;font-weight:bold; ">修改用户地址：</span><br/>
	    	  省：<select  style="width:60px;"  id="SelectProvince" name="SelectProvince">
		    </select>
		    &nbsp;市：<select  style="width:60px;"  id="SelectCity" name="SelectCity">
		    </select>
		    &nbsp;区：<select   style="width:60px;"  id="SelectDistrict" name="SelectArea">
		    </select>
		    <div style="cursor:pointer;font-size: 20px;color:red;margin-top: 10px;margin-left: 120px;"><span style="cursor:pointer;font-size: 20px;color:red;float: left;"  id="submitValue"  >确定</span><span style="width: 20px;float: left;"></span><span  style="cursor:pointer;font-size: 20px;color:red;" id="cancelValue"  >取消</span></div>
		    
        </div>
        
        <!-- 修改用户备注 -->
        <div id="hiddenUserRemark" style="border: 1px solid red;display: none;margin-left: 20px;">
        <span style="font-size: 15px;color:black;font-weight:bold; ">修改备注：</span><br/>
        	 <textarea style="width:300px;height:100px;" id="userRemark">
        	 </textarea>
        	  <div style="cursor:pointer;font-size: 20px;color:red;margin-top: 10px;margin-left: 120px;"><span style="cursor:pointer;font-size: 20px;color:red;float: left;"  id="submitValue_2"  >确定</span><span style="width: 20px;float: left;"></span><span  style="cursor:pointer;font-size: 20px;color:red;" id="cancelValue_2"  >取消</span></div>
        </div>
     </div>
    <table class="tablelist" style="width: 100%;margin-top: 5px;">
    	<thead>
    	<tr>
        <th  colspan="2" style="width: 7%;">个人信息<i class="sort"><img src="images/px.gif" /></i></th>
        <th colspan="2" style="width: 16%;">房型</th>
        <th colspan="2" style="width: 16%;">预算</th>
        <th colspan="2" style="width: 31%;">所在地</th>
       <c:choose>
        <c:when test="${search_certainStatus=='确认需求'||search_certainTimeBegin!=''||search_certainTimeEnd!=''}">
        	 <th style="width: 12%;">确认时间</th>
        	 
        	  <th style="width: 8%;" colspan="2">家装公司</th>
        </c:when>
        <c:otherwise>
        	 <th style="width: 8%;">状态</th>
        	<th style="width: 12%;">申请时间</th>
        </c:otherwise>
        </c:choose>
        <th  style="width:5%;">备注</th>
        <th  style="width:5%;">短信</th>
        </tr>
         <tr>
    <td style="width: 5%;">姓名</td>
    <td style="width: 2%;">手机号</td>
    <td style="width: 8%;">房屋类型</td>
    <td style="width: 8%;">房屋结构</td>
    <td style="width: 8%;">方式</td>
    <td style="width: 8%;">预算</td>
    <td style="width: 20%;">省/市&nbsp;&nbsp;市/地区&nbsp;&nbsp;县/市</td>
    
    <td style="width: 11%;">小区名称</td>
    <c:choose>
     <c:when test="${search_certainStatus=='确认需求'||search_certainTimeBegin!=''||search_certainTimeEnd!=''}">
     <td id="applyTime" style="cursor: pointer;">确认时间</td>
     <input type="hidden" name="sortColumnName" value="certainTime"/>
     	 <td>家装公司</td>
		 <td>操作</td>
     </c:when>
     <c:otherwise>
     	 <td  style="width: 8%;">情况</td>
		 <td id="applyTime" style="cursor: pointer;">申请时间</td>
		 <input type="hidden" name="sortColumnName" value="CreateTimeDetails"/>
     </c:otherwise>
   </c:choose>
   <td>备注</td>
   <td>来源</td>
  </tr>
        </thead>
        <tbody>
        <c:choose>
        	<c:when test="${pageBean.data!=null&&pageBean.data!=''}">
        		 <c:forEach items="${pageBean.data}" var="user" varStatus="status" >
  
   <tr>
 
    <td>${user.userName}-${user.sex}</td>
	 
	<td>${user.phone} </td>
    <td>
     
		 <select name="homeType" id="homeType${status.index}"  onchange="updateValue('${user.userId}','${user.phone}','${status.index}','homeType')">  
		 	 
			  <option  value="住宅公寓" <c:if test="${user.homeType=='住宅公寓'}"> selected="selected" </c:if> >住宅公寓</option>  
			  <option  value="别墅豪宅" <c:if test="${user.homeType=='别墅豪宅'}"> selected="selected" </c:if> >别墅豪宅</option>
			  <option  value="商业办公" <c:if test="${user.homeType=='商业办公'}"> selected="selected" </c:if> >商业办公</option>
			  <option  value="无信息" <c:if test="${user.homeType==''||user.homeType==null}"> selected="selected" </c:if> >无信息</option>
		</select> 
		
	</td>
	<td>
		<select id="homeStructure${status.index}" name="homeStructure" onchange="updateValue('${user.userId}','${user.phone}','${status.index}','homeStructure')">  
			<option  value="无信息" <c:if test="${user.homeStructure==''||user.homeStructure==null}"> selected="selected" </c:if> >无信息</option>	
			<option  value="一室户" <c:if test="${user.homeStructure=='一室户'}"> selected="selected" </c:if> >一室户</option>
			<option  value="两房" <c:if test="${user.homeStructure=='两房'}"> selected="selected" </c:if> >两房</option>  
			<option  value="三房" <c:if test="${user.homeStructure=='三房'}"> selected="selected" </c:if> >三房</option>  
			<option  value="四房" <c:if test="${user.homeStructure=='四房'}"> selected="selected" </c:if> >四房</option>  
			<option  value="高档公寓" <c:if test="${user.homeStructure=='高档公寓'}"> selected="selected" </c:if> >高档公寓</option>  
			<option  value="超级豪宅" <c:if test="${user.homeStructure=='超级豪宅'}"> selected="selected" </c:if> >超级豪宅</option>  
			<option  value="独栋别墅" <c:if test="${user.homeStructure=='独栋别墅'}"> selected="selected" </c:if> >独栋别墅</option> 
			<option  value="双拼别墅" <c:if test="${user.homeStructure=='双拼别墅'}"> selected="selected" </c:if> >双拼别墅</option>   
			<option  value="联排别墅" <c:if test="${user.homeStructure=='联排别墅'}"> selected="selected" </c:if> >联排别墅</option>  
			<option  value="小户型" <c:if test="${user.homeStructure=='小户型'}"> selected="selected" </c:if> >小户型</option>  
			<option  value="办公室" <c:if test="${user.homeStructure=='办公室'}"> selected="selected" </c:if> >办公室</option>  
			<option  value="复式楼" <c:if test="${user.homeStructure=='复式楼'}"> selected="selected" </c:if> >复式楼</option>  
			<option  value="茶楼餐厅" <c:if test="${user.homeStructure=='茶楼餐厅'}"> selected="selected" </c:if> >茶楼餐厅</option> 
			<option  value="局部装修" <c:if test="${user.homeStructure=='局部装修'}"> selected="selected" </c:if> >局部装修</option>  
			<option  value="商铺店铺" <c:if test="${user.homeStructure=='商铺店铺'}"> selected="selected" </c:if> >商铺店铺</option>  
			<option  value="二手房" <c:if test="${user.homeStructure=='二手房'}"> selected="selected" </c:if> >二手房</option>  
		</select> 
	</td>
	<td width="100px"> 
		<select name="budgetType" id="budgetType${status.index}"  onchange="updateValue('${user.userId}','${user.phone}','${status.index}','budgetType')">  
			<option  value="无信息" <c:if test="${user.budgetType==''||user.budgetType==null}"> selected="selected" </c:if> >无信息</option>	
			<option  value="清包" <c:if test="${user.budgetType=='清包'}"> selected="selected" </c:if> >清包</option>
			<option  value="半包" <c:if test="${user.budgetType=='半包'}"> selected="selected" </c:if> >半包</option>
			<option  value="全包" <c:if test="${user.budgetType=='全包'}"> selected="selected" </c:if> >全包</option>
			<option  value="软装" <c:if test="${user.budgetType=='软装'}"> selected="selected" </c:if> >软装</option>
			<option  value="纯设计" <c:if test="${user.budgetType=='纯设计'}"> selected="selected" </c:if> >纯设计</option> 
		</select> 
	</td>
	<td><input style="border:1px solid gray;width:30px;" type="text" id="budgetNum${status.index}" value="${user.budgetNum}" onblur="updateValue('${user.userId}','${user.phone}','${status.index}','budgetNum')"> &nbsp;</td>
	<td >
		<div id="userAreaDetail${status.index}" style="display: block">
			<select name="province" style="width:60px;" onclick="replaceSelect('${user.userId}','${user.phone}','hiddenAreaSelect','');">  
					<option  value="${user.province}" >${user.province}</option>
			</select> 
			 
			<select name="city" style="width:60px;" onclick="replaceSelect('${user.userId}','${user.phone}','hiddenAreaSelect','');">  
				<option  value="${user.city}" >${user.city}</option>
			</select> 
			 
			<select name="area" style="width:60px;" onclick="replaceSelect('${user.userId}','${user.phone}','hiddenAreaSelect','');">  
				<option  value="${user.area}" >${user.area}</option>
			</select> 
		</div>
		  
   </td>
 
	<td><input style="border:1px solid gray;width:80px;" type="text" id="community${status.index}" value="${user.community}" onblur="updateValue('${user.userId}','${user.phone}','${status.index}','community')"> &nbsp;</td>
	<c:choose>
	<c:when test="${search_certainStatus=='确认需求'||search_certainTimeBegin!=''||search_certainTimeEnd!=''}">
     	 <td>${user.certainTime}</td>
		 <td>
		 <div id="companys${status.index}">
		 	<c:forEach items="${user.companys}" var="company">
			${company}<br/>
			</c:forEach>
		</div>
		 </td>
		<td>
			<c:if test="${user.certainStatus=='确认需求'}"> 
				<div style="cursor:pointer" onclick="matchCompany('${user.userId}','${user.userName}','${user.phone}','${user.homeType}','${user.homeStructure}','${user.budgetType}','${user.budgetNum}','${user.province}','${user.city}','${user.area}','${user.community}')" style="color: red; text-decoration: underline">选择</div>
			</c:if>
		</td>
     </c:when>
	<c:otherwise>
	<td>
		<select name="certainStatus" id="certainStatus${status.index}"  onchange="updateValue('${user.userId}','${user.phone}','${status.index}','certainStatus')" >  
			  <option  value="新用户"  >新用户</option>  
			    <option  value="待确认"  <c:if test="${user.certainStatus=='待确认'}"> selected="selected" </c:if> >待确认</option>  
			  <option  value="确认需求"  <c:if test="${user.certainStatus=='确认需求'}"> selected="selected" </c:if> >确认需求</option>
			  <option  value="无人接听" <c:if test="${user.certainStatus=='无人接听'}"> selected="selected" </c:if> >无人接听</option>
			  <option  value="错号" <c:if test="${user.certainStatus=='错号'}"> selected="selected" </c:if> >错号</option>
			  <option  value="空号" <c:if test="${user.certainStatus=='空号'}"> selected="selected" </c:if> >空号</option>
			  <option  value="停机" <c:if test="${user.certainStatus=='停机'}"> selected="selected" </c:if> >停机</option>
			  <option  value="直接拒绝" <c:if test="${user.certainStatus=='直接拒绝'}"> selected="selected" </c:if> >直接拒绝</option>
			  <option  value="关闭"  <c:if test="${user.certainStatus=='关闭'}"> selected="selected" </c:if> >关闭</option>
		</select> 
	</td>
	<td>${user.CreateTime}&nbsp;</td>
	</c:otherwise>
	
	
	
	</c:choose>
	<td onclick="replaceSelect('${user.userId}','${user.phone}','hiddenUserRemark','${user.userRemark}');">
	 <!-- 考虑到界面显示问题，默认备注只显示2个字符(userRemark_2)，当点击修改时显示原始内容(userRemark) -->
		${user.userRemark_2}  
	</td>
	<c:choose>
	<c:when test="${user.source=='web'}">
	<td style="cursor:pointer;" id="sendMessage${status.index}" onclick="sendMessage('${user.phone}')">
		发送
	</td>
	</c:when>
	<c:otherwise>
		<td>${user.source}</td>
	</c:otherwise>
	</c:choose>
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
