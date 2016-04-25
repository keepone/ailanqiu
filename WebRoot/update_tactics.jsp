<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'test.jsp' starting page</title>
    
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
	<script>
	        KindEditor.ready(function(K) {
	                K.create('#editor_id', {
                uploadJson : '/img/UploadImgAction',
                fileManagerJson : '../jsp/file_manager_json.jsp',
                allowFileManager : true
        });
	        });
	</script>
  </head>
  
  <body>
  <form action="/" name="myFrm" method="post">
  <input name="specialId" type="hidden" value="${json.id}"/>
  Title:<input type="text" name="specialName" value="${json.name}"/>
   <textarea id="editor_id" name="content" style="width:100px;height:300px;">
		 ${json.content}
	</textarea>
	<input type="submit" value="submit">
	</form>
  </body>
</html>
