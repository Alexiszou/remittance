/*占位符*/
 (function(){
 	var windowH=parseInt($('.move-content').css('height'))+63;
 	$('body').css('height',windowH);
 })()

	var documentW=parseInt($(window).width());
	var documentH=parseInt($(window).height());
	var newTime='';
/*二级页面登录*/
	$('.header-btnR').click(function(){
		if (localStorage.id||sessionStorage.id) {

		}else{
			window.location.href='../dl.html';
		}
	});
	/*账户登录跳转*/
	$('.header-user li.grzl').click(function(){
		window.location.href='../personal/grzl.html';
	})
	$('.header-user li.wddd').click(function(){
		window.location.href='../personal/wddd.html';
	})
	/*添加学校后从新载入开关*/
	if (!sessionStorage.openShools) {
		sessionStorage.openShools=0;/*学校开关存储*/
	}
	/*添加我的订单开关*/
	if (!sessionStorage.openWddd) {
		sessionStorage.openWddd=0;
	}

	/*留学汇款未登录*/
		/*账户切换*/
		var openhkwdl=0;
		$('.hkwdl-zhanghu-top .radius-btn').click(function(){
			$('.hkwdl-zhanghu-top .radius-btn').css('background','#fff');
			$(this).css('background','url(../images/radius-btn.jpg) no-repeat');
			$('.hkwdl-zhanghu .input .false,.hkwdl-zhanghu .input .true').hide();
			$('.hkwdl-zhanghu .input input').val('');
			if ($(this).parent().index()==0) {
				$('.hkwdl-zhanghu .dl').hide();
				$('.hkwdl-zhanghu .wdl').hide();
				$('.hkwdl-zhanghu .wdl').show();
				openhkwdl=0;
			}else{
				$('.hkwdl-zhanghu .wdl').hide();
				$('.hkwdl-zhanghu .dl').hide();
				$('.hkwdl-zhanghu .dl').show();
				openhkwdl=1;
			}
		})
		$('.hkwdl-zhanghu .box-btn').click(function(){
			$('.hkwdl-zhanghu .box-btn,.hkwdl-zhanghu .Submit input').toggleClass('cur');
		})
		/*账户登录、注册*/
		$('.hkwdl-zhanghu .wdl input.email').blur(function(){
			$('.hkwdl-zhanghu .input:eq(0) .false').text('邮箱格式输入错误');
		isEmail($('.hkwdl-zhanghu input.email'))
		})
		$('.hkwdl-zhanghu .wdl input.name').blur(function(){
			isName($('.hkwdl-zhanghu input.name'))
		})
		$('.hkwdl-zhanghu .wdl input.phone').blur(function(){
			$('.hkwdl-zhanghu .input:eq(2) .false').text('电话格式输入有误');
			isPhone($('.hkwdl-zhanghu input.phone'))
		})
		$('.hkwdl-zhanghu .wdl input.password').blur(function(){
			isPassword($('.hkwdl-zhanghu input.password'))
		})
		$('.hkwdl-zhanghu .wdl input.passwordAg').blur(function(){
			if ($('.hkwdl-zhanghu input.passwordAg').val()==$('.hkwdl-zhanghu input.password').val()) {
				isStyle($('.hkwdl-zhanghu input.passwordAg'))
			}else{
				isStyle1($('.hkwdl-zhanghu input.passwordAg'))
			}
		})
		function SubmitI(){
			if (openhkwdl==0) {
				if (!$(".hkwdl-zhanghu .wdl .true:not(:last)").is(":hidden")&&$('.hkwdl-zhanghu .box-btn').hasClass('cur')){
					var dataS={
	                	email:$('.hkwdl-zhanghu .wdl input.email').val(),
	                	name:$('.hkwdl-zhanghu .wdl input.name').val(),
	                	telphone:$('.hkwdl-zhanghu .wdl input.phone').val(),
	                	password:$('.hkwdl-zhanghu .wdl input.password').val(),
	                	captcha:$('.hkwdl-zhanghu .wdl input.codeNum').val(),
	                	ctoken:newTime
	                } 
				$.ajax({
	                type: "POST",
	                url:ip+'/users/register',
	                data:JSON.stringify(dataS),
	                contentType: "application/json",
	                async: false,
	                dataType: "json",
	                error: function(request) {
	                    alert("Connection error");
	                },
	                success: function(data) {
	                	console.log(data)
	                    if (data[0].result=='T') {
	                    	sessionStorage.id=data[0].data;
	                    	isStyle($('.hkwdl-zhanghu .wdl input.codeNum'));
	                    	$('.header-userId').text($('.hkwdl-zhanghu .wdl input.name').val()+'，你好')
	                    	sessionStorage.name=$('.hkwdl-zhanghu .wdl input.name').val();
	                    	window.location.href='hkdl.html';
	                    }else{
	                    	if (data[0].data=='手机号已经注册过') {
	                    		$('.hkwdl-zhanghu .input:eq(2) .false').text(data[0].data);
	                    		isStyle1($('.hkwdl-zhanghu .input:eq(2) input'));
	                    	}else if(data[0].data=='邮箱已经注册过'){
	                    		$('.hkwdl-zhanghu .input:eq(0) .false').text(data[0].data);
	                    		isStyle1($('.hkwdl-zhanghu .input:eq(0) input'));
	                    	}else{
		                    	isStyle1($('.hkwdl-zhanghu .wdl input.codeNum'));
		                    	$('.codefalse').text(data[0].data);
		                    }
	                    }
	                }
	            })
			}
			}else{
				$.ajax({
	                type: "GET",
	                url:ip+'/users/login?name='+$('.hkwdl-zhanghu .dl input.name').val()+'&password='+$('.hkwdl-zhanghu .dl input.password').val(),
	                /*data:{
	                	name:$('.shadow-login .RegisteredIn input.name').val(),
	                	password:$('.shadow-login .RegisteredIn input.password').val(),
	                },*/
	                async: false,
	                dataType: "json",
	                error: function(request) {
	                    alert("Connection error");
	                },
	                success: function(data) {
	                	//console.log(data)
	                    if (data==0) {
	                    	isStyle1($('.hkwdl-zhanghu .dl input.name'));
	                    	isStyle1($('.hkwdl-zhanghu .dl input.password'));
	                    }else{
							sessionStorage.id=data;
							var dataS=data;
							$.ajax({
				                type: "GET",
				                url:ip+'/users/getUserInfo?userId='+dataS,
				                async: false,
				                dataType: "json",
				                error: function(request) {
				                    alert("Connection error");
				                },
				                success: function(data) {
				                	//console.log(data)
				                    localStorage.name=data[0].Name;
				                    sessionStorage.name=data[0].Name;
				                    window.location.href='hkdl.html';
				                }
				            })
	                    }
	                }
	            })
			}
		}
	/*留学汇款登录*/
		/*提交*/
		$('.hkdl-box .box-btn').click(function(){
			$(this).toggleClass('cur');
			$('.hkdl-Submit').toggleClass('cur');
		})
	/*订单付款-付款*/
	/*$('.shadow-fk1 li').click(function(){
		$('.shadow-fk1 li img').removeClass('cur');
		$(this).find('img').addClass('cur');
	})*/
	/*$('.shadow-fk1 .close,.shadow-fk2 .close').click(function(){
		$('.shadow-fk').hide();
	})*/
	/*$('.shadow-fk1 .btnL').click(function(){
		$('.shadow-fk1').hide();
		$('.shadow-fk2').show();
	})*/
	/*$('.shadow-fk1 .btnR,.shadow-fk2 .btnL').click(function(){
		$('.shadow-fk').hide();
	})*/
	/*修改密码*/
	$('.grzlC .btn').click(function(){
		$('.shadow-xiugai,.xgpassW').show();
	})
	$('.shadow-xiugai .close').click(function(){
		$('.shadow-xiugai input').val('');
		$('.shadow-xiugai,.shadow-xiugai .true,.shadow-xiugai .false').hide();
	})
	/*帮助中心*/
	$('.bzzxR-title .btn').click(function(){
		if ($(this).hasClass('cur')) {
			$(this).parent().next().show();
		}else{
			$(this).parent().next().hide();
		}
		$(this).toggleClass('cur');
	})
