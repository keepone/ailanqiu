<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>youku视频</title>
<link href="http://static.youku.com/v1.0.1003/v/css/playV5.css"
	type="text/css" rel="stylesheet" />
<script type="text/javascript"
	src="http://static.youku.com/v1.0.1003/js/prototype.js"></script>
<script type="text/javascript"
	src="http://static.youku.com/v1.0.1003/index/js/common.js"></script>
<script type="text/javascript"
	src="http://static.youku.com/v1.0.1003/index/js/qcard.js"></script>
<script type="text/javascript"
	src="http://static.youku.com/v1.0.1003/v/js/v5/v.js"></script>
${json.href}
 

    

    
    
 

</head>

<body class="page_ugc">
${json.name}[爱篮球,从未改变]
	<div class="window">
		<div class="screen">
			<div class="s_body">
				<div class="s_main layout_121">
					<div class="play_ugc">
						<div class="play_area" id="playBox">
							<div id="div_ad_crazy_v5" style="display: none"></div>
							<div class="playBox" id="playerBox">
								<div class="playArea">
									<div class="abs">
										<div id="preAd1" style="zoom: 1;">
											<div
												style="position: absolute; top: 0px; left: 610px; width: 320px; height: 460px; text-align: right; display: none"
												id="preAdContent"></div>
										</div>
										<div id="ad_crazy" style="zoom: 1;">
											<div id="ab_558"></div>
										</div>
									</div>

									<div class="player" id="player">
										<script type="text/javascript">
											var playerUrl = 'http://static.youku.com/v1.0.0484/v/swf/loader.swf';
											var preId = "";
											var nextId = "";
											var plid = "";
											var type = "";
										</script>
										<div id="player_title"></div>
										<div id="player_html5" class="player_html5">
											<div class="picture" id="youku-html5-player-info">
												<video id="youku-html5-player-video" width="100%"
													height="458" x-webkit-airplay="allow" controls autoplay
													preload>
												</video>
											</div>
											<div class="controls">
												<div class="panel">
													<div id="youku-html5-player-progressbar" class="processbar">
														<div id="youku-html5-player-progress-tp" class="timepoint"
															style="left: 0%; display: none">00:00</div>
														<div id="youku-html5-player-progress-trk" class="track"
															style="width: 0%;"></div>
														<div id="youku-html5-player-progress-hd" class="handle"
															style="left: 0%"></div>
													</div>
													<div id="youku-html5-player-play" class="start_disabled"></div>
													<div class="time">
														<span id="youku-html5-player-currentTime" class="current">00:00</span>/<span
															id="youku-html5-player-totalTime" class="total">00:00</span>
													</div>
													<div class="controls-fullscreen-button"></div>
													<div class="base-button controls-widescreen-button">
														标屏</div>
													<div class="controls-playmode"></div>
													<div class="controls-language"></div>
													<div class="volume" id="youku-html5-player-volume">
														<div class="speaker" id="youku-html5-player-speaker">
															<div class="mask">
																<div class="lose" id="youku-html5-player-speaker-lose"
																	style="width: 100%"></div>
															</div>
														</div>
														<div class="volumebar">
															<div class="track" id="youku-html5-player-volumebar-trk"></div>
															<div class="handle" id="youku-html5-player-volumebar-hd"
																style="left: 0%"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
										<iframe id="universalPlayer" src="" width="100%" height="498"
											border="0" frameborder="0" style="display: none"></iframe>
										<script>
											play();
										</script>
									</div>
								</div>
							</div>

							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
 

	<!--toolbar-->
	<link rel="stylesheet" type="text/css"
		href="http://static.youku.com/v1.0.1003/index/css/sideTool.css" />
	<script src="http://static.youku.com/v1.0.1003/index/js/sideTool.js"></script>

	<link rel="stylesheet" type="text/css"
		href="http://static.youku.com/v1.0.1003/index/css/qfooter.css" />

	<script type="text/javascript">
		var transjs = "http://static.youku.com/v1.0.1003/index/js/sttrans.js";

		window.nova_init_hook_sttrans = function() {
			var cstt = Nova.Cookie.get('stt'), handler = $('sttrans');
			var _trans = function() {
				var t = $A(arguments).shift();
				if (typeof (trans) == 'undefined') {
					var jselm = document.createElement('script');
					jselm = $$('head')[0].appendChild(jselm);
					if (typeof (jselm) != 'undefined') {
						jselm.onreadystatechange = function() {
							if (this.readyState == 'loaded'
									|| this.readyState == 'complete') {
								try {
									trans(t)
								} catch (e) {
								}
							}
						}
						jselm.onload = function() {
							trans(t);
						}
					}
					jselm.src = transjs;
				} else {
					trans(t);
				}
			}
			if (cstt) {
				_trans();
				if (handler)
					handler.innerHTML = '简体版';
			}
			if (!handler)
				return;
			var _evth = function() {
				if (Nova.Cookie.get('stt')) {
					_trans(true);
					handler.innerHTML = '繁體版';
					Nova.Cookie.set('stt', '', -1);
				} else {
					_trans();
					handler.innerHTML = '简体版';
					Nova.Cookie.set('stt', 1, 10);
				}
			}.bind(handler);
			Event.observe(handler, 'click', _evth);
		}
	</script>
	<div>
		 
	</div>
	${json.description}
</body>
</html>
