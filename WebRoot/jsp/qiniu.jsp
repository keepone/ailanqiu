<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >  
<html>  
<head>  
<title>uploadify-实例</title> 
 <script type="text/javascript" src="../js/jquery-1.7.2.js"></script>
 <link rel="stylesheet" href="../css/uploadify.css" type="text/css"></link>  
 <script type="text/javascript" src="../js/jquery.uploadify.min.js"></script>

<script type="text/javascript">  
 
</script> 
<style type="text/css">  
.inputcss  
{  
    color:#333333;  
    font-family: "Tahoma";   
    font-size: 12px;   
    border:solid 1px #CCCCCC;  
}  
.buttoncss  
{  
    color:#333333;  
    font-family: "Tahoma";   
    font-size: 12px;   
    background-color:#FFFFFF;  
    border:solid 1px #CCCCCC;  
}  
</style>  
</head>  
<body>  
   <%-- <form method="post" action="http://upload.qiniu.com/"
 	enctype="multipart/form-data">
  <input name="key" type="text" value="${time}.jpg">
  <input name="x:name" type="hidden" value="td">
  <input name="token" type="text" value="${uptoken}">
  <input name="file" type="file" />
  <input type="submit" value="Submit"/>
	</form> --%>
	
	
	<form method="post" action="http://up.qiniu.com" enctype="multipart/form-data">
    <ul>
        <li>
            <label for="token">token:</label>
            <input id="token" name="token" class="ipt" value="scz23LP0R3h56PZJ5KswGT-8XNjHfr913OxhEKkl:aE3GIs2BKA9TzEbjwTGIBId2rqI=:eyJzY29wZSI6ImxhbnFpdSIsImRlYWRsaW5lIjoxNDIwMjg1ODM0fQ=="><a target="blank" href="http://jsfiddle.net/gh/get/extjs/4.2/icattlecoder/jsfiddle/tree/master/uptoken">在线生成token</a>

        </li>
        <li>
            <label for="key">key:</label>
            <input name="key" class="ipt" value="scz23LP0R3h56PZJ5KswGT-8XNjHfr913OxhEKkl">
        </li>
        <li>
            <label for="x:username">姓名(自定义变量):</label>
            <input name="x:username" class="ipt" value="test">
        </li>
        <li>
            <label for="bucket">照片:</label>
            <input name="file" class="ipt" type="file" />
        </li>
        <li>
            <input type="submit" value="提交">
        </li>
    </ul>
</form>


</body>  
</html> 