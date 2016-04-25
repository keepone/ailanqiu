<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'left.jsp' starting page</title>
<link href="css/style.css" rel="stylesheet" type="text/css" />

<script language="JavaScript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/jquery-1.7.2.js"></script>
<script type="text/javascript">
	$(function() {
		//导航切换
		$(".menuson li").click(function() {
			$(".menuson li.active").removeClass("active")
			$(this).addClass("active");
		});

		$('.title').click(function() {
			var $ul = $(this).next('ul');
			$('dd').find('ul').slideUp();
			if ($ul.is(':visible')) {
				$(this).next('ul').slideUp();
			} else {
				$(this).next('ul').slideDown();
			}
		});
	})
</script>

</head>

<body style="background: #1abc9c;">
	<!-- <div class="lefttop"><span></span>通讯录</div> -->

	<dl class="leftmenu">

		<dd>
			<div class="title">
				<span><img src="images/leftico01.png" /> </span>信息管理
			</div>
			<ul class="menuson">

				  <li><cite></cite><a href="http://localhost:8080/houtaiJsp/admin.jsp" target="_blank">另外一个管理后台</a><i></i></li>
				<li><cite></cite><a href="jsp/admin/add_user.jsp"
					target="rightFrame">添加用户</a><i></i></li>
						 
				<li><cite></cite><a href="jsp/add_text.jsp" target="rightFrame">录入文章</a><i></i>
				</li>
				<li><cite></cite><a href="/ailanqiu/admin/user/UserList"
					target="rightFrame">用户管理</a><i></i></li>
					<li><cite></cite><a href="/ailanqiu/admin/user/BattleList"
					target="rightFrame">约战管理</a><i></i></li>
				<li><cite></cite><a href="/ailanqiu/admin/court/GetReviewList"
					target="rightFrame">审核管理</a><i></i></li>
				<li><cite></cite><a href="/ailanqiu/admin/court/GetSchoolList "
					target="rightFrame">学校管理</a><i></i></li>


				<li><cite></cite><a href="admin/dynamic/ToAddDynamic"
					target="rightFrame">发布动态</a><i></i></li>
				<li><cite></cite><a href="jsp/admin/add_park.jsp"
					target="rightFrame">添加公园</a><i></i></li>

				<li><cite></cite><a href="jsp/admin/uploadPhoto.jsp"
					target="rightFrame">上传图片</a><i></i></li>
				<li><cite></cite><a href="/ailanqiu/jsp/add_special.jsp"
					target="rightFrame">添加专题</a><i></i></li>
				<li><cite></cite><a href="/ailanqiu/jsp/add_zhuanji.jsp"
					target="rightFrame">添加专辑</a><i></i></li>


				<li><cite></cite><a href="jsp/craw.jsp" target="rightFrame">CRAW</a><i></i>
				</li>
				<li><cite></cite><a href="jsp/common_craw.jsp"
					target="rightFrame">COMMON_CRAW</a><i></i></li>


				<li><cite></cite><a href="jsp/downloadimg.jsp"
					target="rightFrame">下载公众号图片</a><i></i></li>

				<!-- <li><cite></cite><a href="jsp/add_video.jsp"
					target="rightFrame">下载视频</a><i></i></li> -->

				<li><cite></cite><a href="jsp/admin/addTeam.jsp"
					target="rightFrame">录入球队</a><i></i></li>
				<!-- <li><cite></cite><a href="lbacomment/ToAddLbaCommentAction"
					target="rightFrame">添加专栏</a><i></i></li> -->
				<!-- <li><cite></cite><a href="lbacomment/LbaCommentListAction"
					target="rightFrame">专栏列表</a><i></i></li>
				<li><cite></cite><a href="/news/NewsListAction"
					target="rightFrame">News</a><i></i></li> -->
				<!-- <li class="active"><cite></cite><a
					href="/direct/DirectListAction" target="rightFrame">直播</a><i></i></li>
				<li><cite></cite><a href="/news/SpecialListAction"
					target="rightFrame">专题</a><i></i></li>

				<li><cite></cite><a
					href="/videoapp/VideoListForApp?pageNo=1&pageSize=10"
					target="_blank">视频</a><i></i></li>
				<li><cite></cite><a
					href="/tacticsapp/TacticsListForApp?pageNo=1&pageSize=10"
					target="_blank">战术</a><i></i></li>
				<li><cite></cite><a href="" target="_blank">规则</a><i></i></li>
				<li><cite></cite><a
					href="/newsapp/NewsListForApp?pageNo=1&pageSize=10" target="_blank">新闻</a><i></i>
				</li>
				<li><cite></cite><a href="/newsapp/BestPlayerListForApp"
					target="_blank">历届状元</a><i></i></li>
				<li><cite></cite><a href="/newsapp/ChampionListForApp"
					target="_blank">历届冠军</a><i></i></li> -->
			</ul>
		</dd>


		<dd>
			<div class="title">
				<span><img src="images/leftico03.png" /> </span>资讯管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="jsp/add_video.jsp"
					target="rightFrame">录入视频</a><i></i>
				</li>
				<li><cite></cite><a href="jsp/add_text.jsp" target="rightFrame">录入文章</a><i></i>
				</li>
			</ul>
		</dd>


		<dd>
			<div class="title">
				<span><img src="images/leftico03.png" /> </span>社区管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="admin/dynamic/ToAddDynamic"
					target="rightFrame">发布动态</a><i></i></li>
				<li><cite></cite><a
					href="admin/dynamic/DynamicList?pageNo=1&pageSize=20"
					target="rightFrame">帖子管理</a><i></i></li>
			</ul>
		</dd>


		<dd>
			<div class="title">
				<span><img src="images/leftico03.png" /> </span>问卷管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="admin/dynamic/GetAllQuestionnaire"
					target="rightFrame">添加问卷</a><i></i>
				</li>
				<li><cite></cite><a
					href="http://www.lanqiure.com/review/lbquestionnaire/GetQuestionnaireList"
					target="rightFrame">问卷列表</a><i></i>
				</li>

			</ul>
		</dd>

		<dd>
			<div class="title">
				<span><img src="images/leftico03.png" /> </span>权限管理
			</div>
			<ul class="menuson">
				<li><cite></cite><a href="/sys/manage/authority/user_role.html"
					target="rightFrame">用户-角色</a><i></i></li>
				<li><cite></cite><a
					href="/sys/manage/authority/role_resource.html" target="rightFrame">角色-资源</a><i></i>
				</li>
			</ul>
		</dd>



		<%--  
    
    
    <dd><div class="title"><span><img src="../images/leftico04.png" /></span>数据分析</div>
    <ul class="menuson">
        <li><cite></cite><a href="/sys/manage/data/user_area.html"  target="rightFrame">用户-地区</a><i></i></li>  
    </ul>
    
    </dd>   
    
    --%>
	</dl>

</body>
</html>
