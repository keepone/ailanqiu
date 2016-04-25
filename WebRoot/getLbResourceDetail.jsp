<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<!DOCTYPE HTML>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport"
          content="user-scalable=no, initial-scale=1.0, maximum-scale=1.0" />
    <meta name="apple-mobile-web-app-capable" content="yes" />
    <meta name="apple-mobile-web-app-status-bar-style" content="black">

    <title></title>

    <meta name="keywords" content="">
    <meta name="description" content="">
    <link rel="stylesheet" type="text/css" href="../styles/style.css">

    <script type="text/javascript" src="../scripts/jquery-v1.10.2.min.js"></script>

    <script>
        $(document).ready(function() {
            $("iframe").width($(window).width() - 30);
        });

        $(window).resize(function() {
            $("iframe").width($(window).width() - 30);
        });
    </script>
<script type="text/javascript">
		function loadURL(url){
		var iFrame;
		//alert(url);
		window.location.href=url;
	}
	</script>
</head>

<body>
<div class="news_article_title">${title}</div>
<div class="news_article_info">
    <div class="news_article_author left">爱篮球</div>
    <div class="news_article_release_time right">${addTime}</div>
</div>
<div class="news_article_content">
	<c:if test="${!empty img}">
		<img src="${img}"/>
	</c:if>
    <c:if test="${!empty videoSource}">
        <iframe src="${videoSource}" allowfullscreen="true" scrolling="no" border="0"
                frameborder="0" style="min-height:163px;"></iframe>
    </c:if>
    <c:if test="${!empty content}">
        ${content}
    </c:if>
    <div class="news_article_source">来源：${source}</div>
</div>
<a href="gap://aboutus.net">Click</a>
<div class="news_article_content">
    <div class="news_article_comment_title">最新评论</div>
    <div>
      
     <div class="news_article_comment" style="border:1px solid red;"  onclick="loadURL('gap://argument.net')">
            <div class="icon-wrap left">
                <img alt="" src="../img/user.jpg">
            </div>
            <div class="news_article_comment_bar left">
                <div class="news_article_author">Lucy_X</div>
                <div class="news_article_comment_time">2014年12月13日06:42</div>
                <div class="news_article_comment_content">我觉得他。。。。。。我觉得他。。。。。。我觉得他。。。。。。我觉得他。。。。。。我觉得他。。。。。。我觉得他。。。。。。</div>
            </div>
        </div>

        <div class="news_article_comment"  onclick="loadURL('gap://argument.net')">
            <div class="icon-wrap left">
                <img alt="" src="../img/user.jpg">
            </div>
            <div class="news_article_comment_bar left">
                <div class="news_article_author">Lucy_X</div>
                <div class="news_article_comment_time">2014年12月13日06:42</div>
                <div class="news_article_comment_content">我觉得他。。。。。。我觉得他。。。。。。我觉得他。。。。。。我觉得他。。。。。。我觉得他。。。。。。我觉得他。。。。。。</div>
            </div>
        </div> 

        <div class="news_article_more_comment">更多评论</div>
    </div>
</div>
</body>
</html>
