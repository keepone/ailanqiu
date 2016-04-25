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
$(document).ready(function() {  
    $("#uploadify").uploadify({  
        'swf': '../js/uploadify.swf',
    'script': '<%=basePath%>/photoupload.jsp', 
        'queueID'        : 'fileQueue',  
        'auto'           : false,  
        'multi'          : true,  
        'wmode'          : 'transparent',  
        'simUploadLimit' : 999,  
        'fileExt'        : '*.png;*.gif;*.jpg;*.bmp;*.jpeg',  
        'fileDesc'       : '图片文件(*.png;*.gif;*.jpg;*.bmp;*.jpeg)',  
        'onAllComplete'  :function(event,data)   
        {  
            $('#result').html(data.filesUploaded +'个图片上传成功');  
        }  
    });  
});  
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
    <table style="width: 90%;">  
        <tr>  
            <td style="width: 100px;">  
                <input type="file" name="uploadify" id="uploadify" />  
            </td>  
            <td align="left">  
                <a href="javascript:$('#uploadify').uploadifyUpload()">开始上传</a>|  
            <a href="javascript:jQuery('#uploadify').uploadifyClearQueue()">取消上传</a>  
            <span id="result" style="font-size: 13px;color: red"></span>  
            </td>  
        </tr>  
    </table>  
        <div id="fileQueue" style="width: 400px;height: 300px; border: 2px solid green;"></div>  
</body>  
</html> 