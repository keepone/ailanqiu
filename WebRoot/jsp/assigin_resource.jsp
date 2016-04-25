<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户管理-分配资源</title>
    <s:head/>
	<link rel="stylesheet" href="${pageContext.request.contextPath }/ztree/css/demo.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath }/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/ztree/js/jquery-1.4.4.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/ztree/js/jquery.ztree.core-3.5.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/ztree/js/jquery.ztree.excheck-3.5.js"></script>
    <script type="text/javascript">
    	var setting = {
    		check:{
    			enable:true
    		},
    		async:{
    			enable:true,
    			url:"GetResourceTreeAction.action?roleId=${roleId}&time="+new Date().getTime(),
				autoParam:["id"]//异步加载时需要提交的参数，多个用逗号分隔
    			
    		},

    		data:{
    			simpleData:{
    				enable:true,
    				idKey:"id",
    				pIdKey:"pId",
    				rootPId:0
    			}
    		}
    	}
    	$(document).ready(function(){
			$.fn.zTree.init($("#mytree"), setting);//实例化后直接返回树对象
			
			$("#btnSubmit").click(function(){
				var zTreeObj = $.fn.zTree.getZTreeObj("mytree");//实例化后直接返回树对象
				var values = zTreeObj.getCheckedNodes(true);
				var url = "DoAssignResourceAction.action?";
				url = url + "roleId="+$(":hidden[name='roleId']").val();
				
				for (var i = 0 ; i < values.length; i++){
					url = url + "&resIds="+values[i].id;
				}
				location.href=url;
			});
    	});
    </script>
    
  </head>
  
  <body>
    <s:form namespace="/resource" action="DoAssignResourceAction" method="get">
    <table width="100%">
    	<tr>
    		<td>
    		<input type="hidden" name="roleId" value="${roleId}">
    		<input type="hidden" name="roleName" value="${roleName}">
    		角色编号:${roleId}</td>
    		<td>角色名称:${roleName}</td>
    		<td>
    			<input type="button" id="btnSubmit" value="提交">
    		</td>
    	</tr>
    	<tr>
    		<td>
    			<div>
    				<ul id="mytree" class="ztree">
    				
    				</ul>
    			
    			</div>
    			
    		</td>
    	</tr>
    </table>
    </s:form>
  </body>
</html>
