<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<title></title>
		<meta charset="UTF-8" />
		<meta name="Description" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0" />
		<link rel="stylesheet" href="/msite/styles/f3d0a03a.m.css" />
		<link rel="stylesheet" href="/msite/styles/a0fc97c4.index.css"
			type="text/css" />
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="" />
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="" />
		<link rel="apple-touch-icon-precomposed" href="" />
		<style type="text/css">
		img {
		  width: 100%;
		}
		p {
		  word-break: break-all;
		  margin-left: 10px;
		  margin-right: 10px;
		  margin-bottom: 8px;
		  color: #32393d;
		  font-size: 16px;
		  line-height: 24px;
		}
		</style>
	</head>
	<body>
		<header class="nav" id="nav">
		<div class="nav-top">
			<a href="#" class="nav-logo icon-logo"><h1>
					爱篮球，从未改变
				</h1> </a>
		</div>
		</header>
		<section class="content-block">
			<img alt="" src="${news.img}"/>
		</section>

		<section class="titles-list" style="margin-top: 20px;">
			${news.content}
		</section>

		<script type="text/javascript">
var ukey = null;
</script>
		<script src="/msite/scripts/7e1793a0.zepto.js">
</script>
		<script src="/msite/scripts/a9468bf8.m.js">
</script>
		<script src="/msite/scripts/4e06de78.index.js">
</script>
	</body>
</html>