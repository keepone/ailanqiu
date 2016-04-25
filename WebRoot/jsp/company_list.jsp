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
    <title>My JSP 'right.jsp' starting page</title>
	  <link href="../css/style.css" rel="stylesheet" type="text/css" />
	 	
  <script type="text/javascript" src="../js/calendar.js"></script>
  <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
  <script type="text/javascript">
  	 $(function(){
  	 	$("#toSearch").click(function(){
  	 			$("#clickType").val("查询");
  		 
  	 	});
  	 });
  </script>
  <script type="text/javascript">
  	function goURL(){
  		window.location.href="/jsp/add_company.jsp";
  	}
  </script>
	 <style type="text/css">
				tr td{ text-align:center;}
	</style>
  </head>
  
  <body>
  
   <div class="place">
    <span>位置：</span>
    <ul class="placeul">
    <li><a href="#">首页</a></li>
    <li><a href="#">数据表</a></li>
    <li><a href="#">基本内容</a></li>
    </ul>
    </div>
    
    <div class="rightinfo">
    
    <div class="tools">
    
    	<ul class="toolbar">
        <li onclick="goURL()"><span><img src="images/t01.png" /></span>添加</li>
        <li class="click"><span><img src="images/t02.png" /></span>修改</li>
        <li><span><img src="images/t03.png" /></span>删除</li>
        <li><span><img src="images/t04.png" /></span>统计</li>
        </ul>
        
        
        <ul class="toolbar1">
        <li><span><img src="images/t05.png" /></span>设置</li>
        </ul>
    
    </div>
    
     <form name="myFrm" action="company/GetAllCompanyInfoAction" method="post">
     <!-- 判断用户是点击查询还是点击上一页下一页，默认为非查询，如果是查询则在Action中，应将当前页数设为1 -->
     <input type="hidden" id="clickType" name="clickType" value="非查询">  
     <div style="float: left;width:1000px;">
      
  	<div style="width:1000px;height:40px;margin-top: 20px;">
	  	 <div style="float: left;">
	  		 公司名称:<input type="text" style="border: 1px solid gray" type="text" value="${companyName}"  name="companyName"/> 
	  	</div>
	  	 <div style="float: left;margin-left: 30px;">签约状态：
	  	 	<select name="signedStatus" name="signedStatus">  
  	 	 	  <option  value="请选择"  >请选择</option>
			  <option  value="已签约" <c:if test="${signedStatus=='已签约'}"> selected="selected" </c:if>>已签约</option>  
			  <option  value="未签约"  <c:if test="${signedStatus=='未签约'}"> selected="selected" </c:if> >未签约</option>
			</select> 
	  	 </div>
	  	  <div style="float: left;margin-left: 70px;">服务区域:
    	   <select id="serviceArea" name="serviceArea">
			<option value="" >请选择</option> 
			<option value="北京市" <c:if test="${serviceArea=='北京市'}"> selected="selected" </c:if> >北京市</option>
			<option value="上海市" <c:if test="${serviceArea=='上海市'}"> selected="selected" </c:if>>上海市</option>
			<option value="天津市" <c:if test="${serviceArea=='天津市'}"> selected="selected" </c:if>>天津市</option>
			<option value="重庆市" <c:if test="${serviceArea=='重庆市'}"> selected="selected" </c:if>>重庆市</option>
			<option value="河北省" <c:if test="${serviceArea=='河北省'}"> selected="selected" </c:if>>河北省</option>
			<option value="河南省" <c:if test="${serviceArea=='河南省'}"> selected="selected" </c:if>>河南省</option>
			<option value="云南省" <c:if test="${serviceArea=='云南省'}"> selected="selected" </c:if>>云南省</option>
			<option value="辽宁省" <c:if test="${serviceArea=='辽宁省'}"> selected="selected" </c:if>>辽宁省</option>
			<option value="湖南省" <c:if test="${serviceArea=='湖南省'}"> selected="selected" </c:if>>湖南省</option>
			<option value="安徽省" <c:if test="${serviceArea=='安徽省'}"> selected="selected" </c:if>>安徽省</option>
			<option value="江苏省" <c:if test="${serviceArea=='江苏省'}"> selected="selected" </c:if>>江苏省</option>
			<option value="浙江省" <c:if test="${serviceArea=='浙江省'}"> selected="selected" </c:if>>浙江省</option>
			<option value="江西省" <c:if test="${serviceArea=='江西省'}"> selected="selected" </c:if>>江西省</option>
			<option value="湖北省" <c:if test="${serviceArea=='湖北省'}"> selected="selected" </c:if>>湖北省</option>
			<option value="甘肃省" <c:if test="${serviceArea=='甘肃省'}"> selected="selected" </c:if>>甘肃省</option>
			<option value="山西省" <c:if test="${serviceArea=='山西省'}"> selected="selected" </c:if> >山西省</option>
			<option value="内蒙古" <c:if test="${serviceArea=='内蒙古'}"> selected="selected" </c:if>>内蒙古</option>
			<option value="陕西省" <c:if test="${serviceArea=='陕西省'}"> selected="selected" </c:if>>陕西省</option>
			<option value="吉林省" <c:if test="${serviceArea=='吉林省'}"> selected="selected" </c:if>>吉林省</option>
			<option value="福建省" <c:if test="${serviceArea=='福建省'}"> selected="selected" </c:if>>福建省</option>
			<option value="贵州省" <c:if test="${serviceArea=='贵州省'}"> selected="selected" </c:if>>贵州省</option>
			<option value="广东省" <c:if test="${serviceArea=='广东省'}"> selected="selected" </c:if>>广东省</option>
			<option value="青海省" <c:if test="${serviceArea=='青海省'}"> selected="selected" </c:if>>青海省</option>
			<option value="四川省" <c:if test="${serviceArea=='四川省'}"> selected="selected" </c:if> >四川省</option>
			<option value="海南省" <c:if test="${serviceArea=='海南省'}"> selected="selected" </c:if>>海南省</option>
			<option value="广西区" <c:if test="${serviceArea=='广西区'}"> selected="selected" </c:if>>广西区</option>
			<option value="宁夏回族" <c:if test="${serviceArea=='宁夏回族'}"> selected="selected" </c:if>>宁夏回族</option>
			<option value="黑龙江省" <c:if test="${serviceArea=='黑龙江省'}"> selected="selected" </c:if>>黑龙江省</option>
			<option value="西藏" <c:if test="${serviceArea=='西藏'}"> selected="selected" </c:if>>西藏</option>
			<option value="台湾省" <c:if test="${serviceArea=='台湾省'}"> selected="selected" </c:if>>台湾省</option>
			<option value="香港特别行政区" <c:if test="${serviceArea=='香港特别行政区'}"> selected="selected" </c:if>>香港特别行政区</option>
			<option value="澳门特别行政区" <c:if test="${serviceArea=='澳门特别行政区'}"> selected="selected" </c:if>>澳门特别行政区</option>
		   </select>
    </div>
  	</div>
  	
  	<div style="width:1000px;height:40px;">
     <div style="float: left;">经营范围：
    	  <select id="runScope" name="runScope" >
	    <option value="" >请选择</option> 
			<option value="小户型"  <c:if test="${runScope=='小户型'}"> selected="selected" </c:if>>小户型</option>
			<option value="大户型" <c:if test="${runScope=='大户型'}"> selected="selected" </c:if>>大户型</option>
		</select>
    </div>
  	 
  	  <div style="float: left;margin-left:85px;">承包方式：
    	   <select name="runWay" name="runWay"> 
    	 <option value="" >请选择</option> 
			<option  value="清包"  <c:if test="${runWay=='清包'}"> selected="selected" </c:if>>清包</option>
			<option  value="半包" <c:if test="${runWay=='半包'}"> selected="selected" </c:if>>半包</option>
			<option  value="全包" <c:if test="${runWay=='全包'}"> selected="selected" </c:if>>全包</option>
			<option  value="软装" <c:if test="${runWay=='软装'}"> selected="selected" </c:if> >软装</option>
			<option  value="纯设计" <c:if test="${runWay=='纯设计'}"> selected="selected" </c:if>>纯设计</option> 
		</select> 
    </div>
     <div style="float: left;margin-left: 70px;">
     	 	承包价格:&nbsp;<input style="width:40px;border: 1px solid gray;"  name="price_begin" type="text"  value="${price_begin}"  size="10" maxlength="10"" />
        ---&nbsp;<input style=" width:40px;border: 1px solid gray;"  name="price_end" type="text" value="${price_end}" size="10" maxlength="10" />
         </div>
  	   
  	 </div>
   <div style="width:1000px;height:40px;">
   		
     <div style="float: left;">
     最后登录时间:&nbsp;&nbsp;<input style=" width:130px;border: 1px solid gray;"  name="lastLoginTimeBegin" type="text" value="${lastLoginTimeBegin}" id="range_date" onclick="new Calendar(1980, 2014).show(this);" size="10" maxlength="10" readonly="readonly" />
        ---&nbsp;<input style=" width:130px;border: 1px solid gray;"  name="lastLoginTimeEnd" type="text" value="${lastLoginTimeEnd}" id="range_date" onclick="new Calendar(1980, 2014).show(this);" size="10" maxlength="10" readonly="readonly" />
     </div>
   </div>
    
      <div style="position:relative;margin-left:60%;">
    <input id="toSearch" type="submit" class="scbtn" value="查询"/>
	</div>
     </div>
   
    <table class="tablelist" style="width: 100%;margin-top: 5px;">
    	<thead>
    	<tr>
        <th  style="width: 8%;">家装公司<i class="sort"><img src="images/px.gif" /></i></th>
        <th  style="width: 20%;">地址</th>
        <th  style="width: 14%;">联系方式</th>
        <th  style="width: 30%;" colspan="2">服务内容</th>
         <th>用户名</th>
 		 <th>最后登录时间</th>    	
        <th>操作</th>
        </tr>
    
        </thead>
        <tbody>
        <c:forEach  items="${pageBean.data}" var="company"  varStatus="status" >
  
   <tr>
 
    <td>${company.companyName}&nbsp;</td>
	 
	<td>${company.areaDetail} &nbsp;</td>
    <td>
    ${company.linkName}<br/>
    ${company.phone}<br/>
    ${company.telephone} 
      
	</td>
	<td>
	  
	 ${company.serviceArea}<br/>
	 ${company.runWay} 
		  
	</td>
	<td> 
		 ${company.runScope}<br/> 
		   承包价格${company.priceBegin}万元-${company.priceEnd}万元 
		  
	</td>
	<td>
		${company.loginName}
	</td>
	<td >
		 ${company.lastLoginTime}
   </td>
 
	<td>
		<a href="company/ToUpdateCompanyInfoAction?companyId=${company.companyId}">修改</a>&nbsp;
		<a href="company/UpdateCompanyAccountStatus?companyId=${company.companyId}&&columnName=userStatus">冻结</a>&nbsp;
		<a href="company/UpdateCompanyAccountStatus?companyId=${company.companyId}&&columnName=loginPwd">重置</a>&nbsp;
		<a href="company/toAddCompanyModel?companyId=${company.companyId}&&companyName=${company.companyName}">模板</a>&nbsp;
		<c:choose>
			<c:when test="${company.operater=='0'}">
				<a href="company/toAddOpenAccountAction?companyId=${company.companyId}&&companyName=${company.companyName}">开户</a>&nbsp;
			</c:when>
			<c:otherwise>
				<a href="company/toAddMoneyAction?companyId=${company.companyId}&&companyName=${company.companyName}">充值</a>&nbsp;
				<a href="company/TransLogAction?companyId=${company.companyId}&&companyName=${company.companyName}">账单</a>&nbsp;
			</c:otherwise>
		</c:choose>
		
	</td>
	 
  </tr>
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
