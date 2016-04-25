<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
	<head>
		<title>爱篮球 - 首页</title>
		<meta charset="UTF-8" />
		<meta name="Description" content="" />
		<meta name="viewport" content="width=device-width,initial-scale=1.0" />
		<link rel="stylesheet" href="/msite/styles/f3d0a03a.m.css" />
		<link rel="stylesheet" href="/msite/styles/a0fc97c4.index.css"
			type="text/css" />
		<link rel="apple-touch-icon-precomposed" sizes="114x114" href="" />
		<link rel="apple-touch-icon-precomposed" sizes="72x72" href="" />
		<link rel="apple-touch-icon-precomposed" href="" />
		
<script type="text/javascript" src="/msite/scripts/jquery.min.js"></script>
		<script type="text/javascript" src="/msite/scripts/jquery.lazyload.js">
</script><%--
		<script type="text/javascript">
		
		var page = 2;
		jQuery(document).ready(
			function($){
				$("img").lazyload({
			     	//placeholder : "grey.gif", //加载图片前的占位图片
     				effect      : "fadeIn" //加载图片使用的效果(淡入)
				});
			var stop = true; 
			var show = "";
			$(window).scroll(function(){ 
    			totalheight = parseFloat($(window).height()) + parseFloat($(window).scrollTop()); 
    			if($(document).height() <= totalheight){ 
     			   if(stop==true){ 
     			       stop=false; 
            		   $.getJSON("/newsapp/NewsListJsonForApp", {pageNo:page, pageSize:20},function(data) {
            			   show = "";
            			   $.each(data, function(id,item) {
            				   show +="<li><a href=\"/newsapp/NewsDetailForApp?newsId="+item.newsId+"\" data-gaevent=\"home_post_list:home\"><span class=\"title-detail\"><img src=\""+item.img+"\" width=\"200\"/>";
							   show +="<h4>"+item.name+"</h4><h4 class=\"news_description\">"+item.content+"</h4></span></a></li>";
            			   }
            			 ); 
                $(".gotop-btn").before(show); 
                stop=true;
                page++;
            },"text"); 
        } 
    } 
});
				
		});
		</script>
	--%></head>
	<body>
		<header class="nav" id="nav">
		<div class="nav-top">
			<a href="#" class="nav-logo icon-logo"><h1>
					NBA历届冠军
				</h1> </a>
		</div>
		</header><%--
		<section class="content-block">
		<ul class="focus" id="focus">
			<c:forEach items="${arr}" var="json" varStatus="i">
				<c:if test="${i.index<3}">
					<li>
						<a href="/videoapp/VideoDetailForApp?videoId=${json.id}"><img width="320" height="228" src="${json.source_img}">
							<h4 class="gellipsis">
								${json.name}
							</h4> </a>
					</li>
				</c:if>
			</c:forEach>
		</ul>
		</section>

		--%>
		
		<section class="titles-list">

		<ul class="titles">
		 <table>
			<c:forEach items="${arr}" var="json" varStatus="i">
				<tr>
				 
					<td style="width:100px">&nbsp;&nbsp;${json.year}</td>
							 		<td style="width:150px">${json.name}</td>
							 		<td style="width:70px">${json.score}</td>
				 
					</tr>
			</c:forEach>
			 </table>
	 
		</ul>

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