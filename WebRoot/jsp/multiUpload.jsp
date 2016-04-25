<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib uri="http://jiutong.com/tag/pager" prefix="jt" %>
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
	 <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
	<link rel="stylesheet" type="text/css" href="../css/uploadify.css" />  
	<script type="text/javascript" src="../js/jquery.uploadify.min.js"></script>
	<script type="text/javascript">
		$(function(){
			alert("dd");
		});
	</script>
	<SCRIPT type="text/javascript">
		$(function() {  
   $("#actpic").uploadify({ 
    //是组件自带的flash，用于打开选取本地文件的按钮 
    'swf': '../js/uploadify.swf',
    //服务器端 脚本文件路径
     'uploader'  : '/photo/PhotoUploadAction', 
     
     'multi':true,
    //和input的name属性值保持一致就好，Struts2就能处理了
    'fileObjName'   : 'resource',
      //按钮上的文字
       'buttonText'     : '选择图片',
       //是否选取文件后自动上传
       'auto': false,
       'removeCompleted':true,
        'method':'post',
       //上传文件大小限制
       'fileSizeLimit':'3024KB',
       
       //同时上传的文件最大数
       
       'simUploadLimit':'999',
       //调用后台action时传递的参数
       'formData':{ 'actid':'2'},
       //有speed和percentage两种，一个显示速度，一个显示完成百分比 
     'displayData'    : 'percentage',
     //如果配置了以下的'fileExt'属性，那么这个属性是必须的 
     'fileTypeDesc'       : '支持格式:jpg/gif/jpeg/png/bmp.', 
     //允许的格式
          'fileExt'        : '*.jpg;*.gif;*.jpeg;*.png;*.bmp',
    //显示待上传文件列表的div区域
    'queueID':'file_queue',
    //全部文件上传完触发（上传完成后，跳转的页面）
     'onAllComplete'  :function(event,data)   
        {  
           alert("f");
        }  
   
       
          
   });  
  });
	</SCRIPT>
	</head>
  <body>
    <form action="/photo/PhotoUploadAction" name="myFrm" method="post" enctype="multipart/form-data">
 <div id="alertinfo"> 

       <h4><span>上传图片</span><span id="close" onclick="closeAlert();">关闭</span></h4>
  <p><input type="file" id="actpic" class="sub" name="resource"/>
<input type="button" value="开始上传" class="sub" onclick="$('#actpic').uploadify('upload','*');"/>
  </p> 
  <div id="file_queue"></div>
  </div>
  </form>
  </body>
</html>
