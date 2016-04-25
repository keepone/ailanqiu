// JavaScript Document

jquery_inputDefault=function(){this.init=function(){var a=$("input.inputDef");a.each(function(){$(this).attr('value',$(this).attr('default')).addClass('jqueryInputDefault')});a.focus(function(){if($(this).attr('value')==$(this).attr('default')){$(this).attr('value','').removeClass('jqueryInputDefault')}});a.blur(function(){if($(this).attr('value')==''){$(this).attr('value',$(this).attr('default')).addClass('jqueryInputDefault')}})}};$(document).ready(function(){var a=new jquery_inputDefault();a.init()});

$(function() {
$("a.mainMenu").bind({
		'click': function() {
			var $this = $(this);
			var $subMenus = $this.next(".subMenus");
			$subMenus.slideToggle(function() {
				if ($subMenus.css("display") == 'block') {
					$this.addClass("up");
				} else {
					$this.removeClass("up");
				}
			});
		}
	});
	$(".subMenus a").bind({
		'click': function() {
			var $this = $(this);
			if (!$this.hasClass("curr")) {
				$(".subMenus a").removeClass("curr");
				$this.addClass("curr");
			}
		}
	});
});

$(function(){	
	var _H = $(window).height() - 63 ;
	$('.content,.contentWrapper').css('height',_H + 'px');
});

$(function(e){
	
	$(window).scroll(function(){
		ScrollMenu();	 
	});

	$(window).resize(function(){
		ScrollMenu();
	});

	function ScrollMenu(){
		var _H = $(window).height() - 63 ;
	$('.content,.contentWrapper').css('height',_H + 'px');
	};	
});
