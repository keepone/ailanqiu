<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8" />
		<title></title>
		<meta name="viewport" content="width=device-width,initial-scale=1.0" />
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="">
		<meta http-equiv="description" content="">
		<link rel="stylesheet" href="/msite/styles/f3d0a03a.m.css" />
		<link rel="stylesheet" href="/msite/styles/a0fc97c4.index.css"
			type="text/css" />
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="" />
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="" />
		<link rel="apple-touch-icon-precomposed" href="" />
		<link href="/videojs/video-js.css" rel="stylesheet" type="text/css" />
		
		<style type="text/css">
		body {
		  background-color: #262626;
		}
		</style>
		<script src="/videojs/video.js">
</script>
		<script type="text/javascript" src="/js/jquery-1.7.2.js">
</script>
		<script>
videojs.options.flash.swf = "/videojs/video-js.swf";
</script>

<script type="text/javascript">
  jQuery(document).ready(function(){
	  var width = $(window).width();
	  $("#example_video_1").width(width);
	  $("#example_video_1").height(width/4*3);
  });
</script>

	</head>
	<body>
		<header class="nav" id="nav">
		<div class="nav-top" style="height: 65px;">
			<a href="#" class="nav-logo icon-logo" style="width: 90%;height: 65px;">
				<h1 style="height: 65px;">
					${json.name}
				</h1> </a>
		</div>
		</header>
		<section class="content-block">
		  <video id="example_video_1" class="video-js vjs-default-skin" controls
			preload="auto" width="640" height="264" poster="${json.source_img}">
		    <source src="${json.href}" />
		  </video>
		</section>
	</body>
</html>
