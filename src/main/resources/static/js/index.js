
	/*密码显示*/
	$('.seePw').mousedown(function(){
		$(this).css({'background':'url(images/seePw.png) no-repeat center','background-size':'100% 90%'}).parent().find('input').attr('type','text');
	})
	$('.seePw').mouseup(function(){
		$(this).css({'background':'url(images/noSeePw.png) no-repeat center','background-size':'100% 90%'}).parent().find('input').attr('type','password');
	})