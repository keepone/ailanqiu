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
   <form method="post" action="/photo/PhotoUploadAction"
 	enctype="multipart/form-data">
 		<input type="file" id="userImg" name="resource"/>
  <input type="submit" value="Submit"/>
	</form>
</body>  
</html> 