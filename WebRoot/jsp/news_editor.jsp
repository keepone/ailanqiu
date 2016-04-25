<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>新闻编辑</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script charset="utf-8" src="${pageContext.request.contextPath}/editor/kindeditor.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/editor/lang/zh_CN.js"></script>
	 <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
	<script type="text/javascript">
		 function updateValue(resourceId,resourceType){
		 	var url = "recommend/IndexRecommendNewsAction?time=+"+new Date().getTime()+"?";
		 	
		    		$.get(url, {resourceId: resourceId,resourceType: resourceType},function(data){
		    				 
		    			if (data=="200"){
		    				 alert("推荐成功!"); 
		    			} else {	
		    				 alert("推荐失败!"); 
		    			}
		    		});
		 }
	</script>
	
	
	<script type="text/javascript">
		 function updateValue2(resourceId,resourceType){
		 	var url = "recommend/IndexRecommendAnimationAction?time=+"+new Date().getTime()+"?";
		 	var resourceHref="/indexDetail/getNewsIndexDetail.do?resourceId="+resourceId;
		    		$.get(url, {resourceId: resourceId,resourceType: resourceType,resourceHref:resourceHref},function(data){
		    			
		    			if (data=="200"){
		    				 alert("推荐成功!"); 
		    			} else {	
		    				 alert("推荐失败!"); 
		    			}
		    		});
		 }
	</script>
	
	<script>
	        KindEditor.ready(function(K) {
	                K.create('#editor_id', {
	                	height : "800px",
                uploadJson : '/img/UploadImgAction',
                fileManagerJson : '../jsp/file_manager_json.jsp',
                allowFileManager : true
        });
	        });
	</script>
  </head>
  
  <body>
  <form action="/news/UpdateNewsDetailAction" name="myFrm" method="post">
  <input name="newsId" type="hidden" value="${json.id}" />
  标题:<input type="text" name="newsTitle" value="${json.name}" style="width: 300px;height:30px;"/><br/><br/>
  <a href="javascript:;" onclick="updateValue('${json.id}','1')">推荐至头条栏目</a><br/><br/>
   <a href="javascript:;" onclick="updateValue2('${json.id}','1')">推荐至首页滑动</a><br/><br/>
   <textarea id="editor_id" name="content" style="width:100px;height:300px;">
		 ${json.content}
	</textarea>
	<input type="submit" value="更新">
	</form>
  </body>
</html>
