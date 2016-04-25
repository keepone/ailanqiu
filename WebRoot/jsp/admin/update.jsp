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
	<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor4/kindeditor.js"></script>
	<script charset="utf-8" src="${pageContext.request.contextPath}/kindeditor4/zh_CN.js"></script>
	 <script type="text/javascript" src="js/jquery-1.7.2.js"></script>
	 <script type="text/javascript">
	 
	 </script>
	<script type="text/javascript">
		 function updateValue(resourceId,resourceType){
		 	var url = "recommend/RecommendAction?time=+"+new Date().getTime()+"?";
		 	
		    		$.get(url, {resourceId: resourceId,resourceType: resourceType},function(data){
		    				 
		    			if (data=="200"){
		    				 alert("推荐成功!"); 
		    			} else {	
		    				 alert("推荐失败!"); 
		    			}
		    		});
		 }
	</script>
	<script>
	  var editor1=null;
	        KindEditor.ready(function(K) {
	       
	           editor1= K.create('#content', {
	           uploadJson : 'img/UploadToQiNiuAction',
	                	 allowFileManager : true
	                	 });
	           //同步 只有添加此句，才能取到textarea的值
               editor1.sync();

                 
             
         	//editor1.html("<p>hello kindeditor</p>");
         	
	        });
	        
	        function analyze(){
	        	 
	        	var source_url=$("#source_url").val();
				  //同步 只有添加此句，才能取到textarea的值
               editor1.sync();
				  var f=$("#content").val();
		    			 /* alert(f); */
				var url = "video/AnalyzeVideoAction?time=+"+new Date().getTime()+"?";
		 			
		    		$.get(url, {videoUrl: source_url},function(data){
		    				 
		    			if (data==""){
		    				 alert("获取视频失败"); 
		    			} else {	
		    			
		    			 f=f+"<iframe src=\""+data+"\" allowfullscreen=\"true\" scrolling=\"no\" border=\"0\" frameborder=\"0\" style=\"min－height:200px;\"></iframe>";
		    			 
		    			 
		    			 editor1.html(f);
		    			  
		    			//document.getElementsByTagName("content")[0].contentWindow.document.body.innerHTML = f;
		    			}
		    		});
	        	
	        }
	</script>
	<script type="text/javascript">
		$(function(){
			$("#videoType").change(function(){
			var source_url=$("#source_url").val();
			var recesourceType=$("#videoType").val();
				 
				var url = "video/AnalyzeVideoAction?time=+"+new Date().getTime()+"?";
		 			
		    		$.get(url, {videoUrl: source_url,recesourceType: recesourceType},function(data){
		    				 
		    			if (data==""){
		    				 alert("获取视频失败"); 
		    			} else {	
		    			var f=$("#content").val();
		    			 
		    			 $("#content").val("ff");
		    			 
		    			  
		    			//document.getElementsByTagName("content")[0].contentWindow.document.body.innerHTML = f;
		    			}
		    		});
		    		
				
			});
		});
	</script>
  </head>
  
  <body>
  <form action="admin/court/UpdateNational" name="myFrm" method="post">
  如果需要插入视频，请在此输入视频地址(暂时只支持新浪视频和优酷视频):<input type="text" id="source_url" name="source_url" style="width: 300px;height:30px;"/><br/><br/>
  <input type="button" value="插入视频" onclick="analyze()"/>
  <div id="videoUrl">
  	
  </div>
   
  <input type="text" name="nationalId" value=""><br/><br/>
	<textarea id="content" name="content" style="width:100px;height:300px;">
		 
	</textarea>
	 
	<input type="submit" value="添加">
	</form>
  </body>
</html>
