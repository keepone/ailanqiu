<%@ page language="java" import="java.util.*" pageEncoding="GB18030"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'add_special.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
       <a href="http://tu.zhibo8.cc/home/">¼��ֱ����ר�⣺</a><form action="news/AddZhiBoBaSpecial" method="post" name="myFrm">
    	ר�����ӣ�<input type="text" name="url"> 
    	<input type="submit" value="¼��">
    </form>
    
    <br>
    <a href="http://sports.163.com/15/0914/15/B3G0J7V500052UUC.html#p=ANGIT4HB0ACR0005"  target="_blank">¼������ר�⣺</a><form action="news/GetSpecialFrom163" method="post" name="myFrm2">
    	ר�����ӣ�<input type="text" name="source_url"> 
    	<input type="submit" value="¼��">
    </form>
    
      <br>
    <a href="http://slide.sports.sina.com.cn/k/" target="_blank">¼������ר�⣺</a>
     <form action="news/GetSpecialBySina" method="post" name="myFrm3">
    	ר�����ӣ�<input type="text" name="source_url"> 
    	<input type="submit" value="¼��">
    </form>
    
    
      <a href="http://sports.qq.com/a/20151120/015518.htm#p=1" target="_blank">¼����Ѷר�⣺</a>
     <form action="news/GetSpecialByTencent" method="post" name="myFrm3">
    	ר�����ӣ�<input type="text" name="source_url"> 
    	<input type="submit" value="¼��">
    </form>
  </body>
</html>
