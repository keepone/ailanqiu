<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="tranb" uri="/WEB-INF/tld/tranb-ocr.tld"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
  </head>
  	<script src="assets/scripts/jquery-1.8.3.min.js"></script>
	<script type="text/javascript">
    function href(url) {
    	//jQuery('#report').load(url);
    	window.location.href=url;
    }
    
    </script>
  <body>
    <fieldset>
    		<form style="margin-left: 55px;" action="/sp/admin/managePost.do" name="searchform" id="searchform" method="post">
				 
					公司信息： 
					 
			</form>
				<br>
				<table border="1" style="border: 0" cellspacing="0" cellpadding="4" width="90%" align="center" >
					<thead>
						<tr align="center" height="30px" bgcolor="#F8F8FF">
							<td width="10%">图片</td>
							<td width="10%">地区</td>
							<td width="20%">详细地区</td>
							<td width="10%">电话</td>
							<td width="20%">商品样式</td>
							<td width="10%">商品房样式</td>
							<td width="15%">公司信息</td>
							<td width="15%">操作</td>
						</tr>
					</thead>
					<tbody>
								   
									<tr  align="center" id="p${jsonObject.companyId}">
										<td><img width="100px" height="80px" src="${jsonObject.logo}">&nbsp;</td>
										<td>${jsonObject.area} &nbsp;</td>
										<td>${jsonObject.areaDetail} &nbsp;</td>
										<td>${jsonObject.phone} &nbsp;</td>
										<td>${jsonObject.goodStyle} &nbsp;</td>
										<td>${jsonObject.goodHouseStyle} &nbsp;</td>
										<td>${jsonObject.jsonObjectIntroduction} &nbsp;</td>
										<td>  <a href="javascript:href('/company/ToUpdateCompanyInfoAction?companyId=${jsonObject.companyId}')">修改</a></td>
									</tr>
									 
								 
						</tbody>
					</table>
					<br>
				</fieldset>
  </body>
 
</html>
