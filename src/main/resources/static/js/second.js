
	var documentW=parseInt($(window).width());
	var documentH=parseInt($(window).height());
	var newTime='';
/*二级账户退出*/
	$('.header-user li.out').click(function(){
		sessionStorage.removeItem("id");
		localStorage.removeItem("id");
		$('.header-login').show();
		$('.header-user').hide();
		window.location.href='../index.html';
	})
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

	/*密码显示*/
	$('.seePw').mousedown(function(){
		$(this).css({'background':'url(../images/seePw.png) no-repeat center','background-size':'100% 90%'}).parent().find('input').attr('type','text');
	})
	$('.seePw').mouseup(function(){
		$(this).css({'background':'url(../images/noSeePw.png) no-repeat center','background-size':'100% 90%'}).parent().find('input').attr('type','password');
	})
/*汇款首页搜索*/
var hksyXx='';
$('.hksy .adC').click(function(){
	$(".hksy .adC ul").hide();
})
$('.hksy .adC input').click(function(event){
	event.stopPropagation();
})
//$('.hksy .adC input').blur(function(){/*搜索框失去焦点*/
//	$('.hksy .adC ul').hide();
//})
$('.hksy .adC input').keyup(function(event){
	if ($('.hksy .adC input').val()!=''&&$('.hksy .adC input').val().length>1) {
			if (event.keyCode!=13&&event.keyCode!=38&&event.keyCode!=40) {
				$.ajax({
	                type: "GET",
	                url:ip+'/schools/getInfo?key='+$('.hksy .adC input').val(),
	                //data:,
	                async: false,
	                dataType: "json",
	                error: function(request) {
	                    alert("Connection error");
	                },
	                success: function(data) {
	                	//console.log(data.length)
	                    console.log(data)
	                    var content=''; 
	                    for (var i = 0; i < data.length; i++) {
	                    	if (i==0) {
	                    		content+='<li class="clear cur" onclick="hksyS(this)"><div class="optionL fl">'+data[i].Country+'</div><div class="optionC fl">'+data[i].EnName+'</div><div class="optionR fr">'+data[i].AccountNo+'</div><span style="display:none" class='+data[i].ID+'>'+data[i].EnName+',,'+data[i].Location+',,'+data[i].AccountName+',,'+data[i].AccountNo+',,'+data[i].BankName+',,'+data[i].BankAddress+',,'+data[i].BIC+',,'+data[i].Rate+',,'+data[i].UnitText+'</span></li>'
	                    	}else{
	                    		content+='<li class="clear" onclick="hksyS(this)"><div class="optionL fl">'+data[i].Country+'</div><div class="optionC fl">'+data[i].EnName+'</div><div class="optionR fr">'+data[i].AccountNo+'</div><span style="display:none" class='+data[i].ID+'>'+data[i].EnName+',,'+data[i].Location+',,'+data[i].AccountName+',,'+data[i].AccountNo+',,'+data[i].BankName+',,'+data[i].BankAddress+',,'+data[i].BIC+',,'+data[i].Rate+',,'+data[i].UnitText+'</span></li>'
	                    	}
	                    }
	                    $('.hksy .adC ul').html(content+'<div onclick="searchaddF()" class="search-add">找不到院校？ 手动添加>></div>');
	                }
	            })
			}
            if(event.keyCode==13&&$('.hksy .adC li').length!=0){/*回车操作*/
		    	$('.hksy .adC li.cur').click();
            }else if(event.keyCode==13){
            	if (sessionStorage.id||localStorage.id) {
					if (sessionStorage.schoolsID) {
			 			window.location.href='hkdl.html'
			 		}else{
			 			window.location.href='hksyAdd.html'
			 		}
			 	}else{
			 		if (sessionStorage.schoolsID) {
			 			window.location.href='hkwdl.html'
			 		}else{
			 			window.location.href='hksyAdd.html'
			 		}
			 	}
            }
            if (event.keyCode==38&&$('.hksy .adC li').length!=0) {/*上键操作*/
            	var keyCodeUp=$('.hksy .adC li.cur').index();
            	if (keyCodeUp!=0) {
            		$('.hksy .adC li').removeClass('cur');
            		$('.hksy .adC li').eq(keyCodeUp-1).addClass('cur');
            		$('.hksy .adC ul').scrollTop($('.hksy .adC ul').scrollTop()-43)
            	}
            }
            if (event.keyCode==40&&$('.hksy .adC li').length!=0) {/*下键操作*/
            	var keyCodeUp=$('.hksy .adC li.cur').index();
            	console.log($('.hksy .adC li').length)
            	if (keyCodeUp!=$('.hksy .adC li').length-1) {
            		$('.hksy .adC li').removeClass('cur');
            		$('.hksy .adC li').eq(keyCodeUp+1).addClass('cur');
            		$('.hksy .adC ul').scrollTop($('.hksy .adC ul').scrollTop()+43)
            	}
            }
	}else if ($('.hksy .adC input').val()=='') {
		sessionStorage.removeItem("schoolsID");
		sessionStorage.removeItem("schoolsData");
		sessionStorage.removeItem("schoolsCt");
	}
	if(event.keyCode==13&&$('.hksy .adC input').val()==''){
		alert('请输入院校信息！')
	}
})
 function hksyS(name){
 	//console.log(name)
 	hksyXx=$(name).find('.optionL').text()+','+$(name).find('.optionC').text()+','+$(name).find('.optionR').text();
 	sessionStorage.schoolsID=$(name).find('span').attr('class');/*存贮学校ID*/
 	$('.hksy .adC input').val(hksyXx);
 	var schoolsArr=$(name).find('span').text();
 	schoolsArr=schoolsArr.split(',,');
 	console.log(schoolsArr)
 	var schoolsData={
 		EnName:schoolsArr[0],
 		Location:schoolsArr[1],
 		AccountName:schoolsArr[2],
 		AccountNo:schoolsArr[3],
 		BankName:schoolsArr[4],
 		BankAddress:schoolsArr[5],
 		BIC:schoolsArr[6],
 		Rate:schoolsArr[7],
 		UnitText:schoolsArr[8]
 	};
 	sessionStorage.schoolsData=JSON.stringify(schoolsData);/*存贮学校信息*/
 	$(".hksy .adC ul").hide().html('');
 	sessionStorage.openShools=0;
 	sessionStorage.removeItem("schoolsCt");
 	//console.log(sessionStorage.schoolsID+','+schoolsArr)
 	//console.log(schoolsData)
 }
 /*汇款首页跳转判断*/
 	/*添加学校判断*/
 	function searchaddF(){
 		sessionStorage.removeItem("schoolsID");
		sessionStorage.removeItem("schoolsData");
		sessionStorage.removeItem("schoolsCt");
		if (sessionStorage.id||localStorage.id) {
			if (sessionStorage.schoolsID) {
	 			window.location.href='hkdl.html'
	 		}else{
	 			window.location.href='hksyAdd.html'
	 		}
	 	}else{
	 		if (sessionStorage.schoolsID) {
	 			window.location.href='hkwdl.html'
	 		}else{
	 			window.location.href='hksyAdd.html'
	 		}
	 	}
 	}
 	/*开始汇款判断*/
 $('.hksy .search-btn').click(function(){
 	if ($('.hksy .adC input').val()=='') {
 		alert('请输入院校信息！')
 	}else{
 		if (sessionStorage.id||localStorage.id) {
			if (sessionStorage.schoolsID) {
	 			window.location.href='hkdl.html'
	 		}else{
	 			window.location.href='hksyAdd.html'
	 		}
	 	}else{
	 		if (sessionStorage.schoolsID) {
	 			window.location.href='hkwdl.html'
	 		}else{
	 			window.location.href='hksyAdd.html'
	 		}
	 	}
 	}
 })
	/*留学汇款*/
		$(".hksy .adC input").focus(function(){
		   $('.hksy .adC ul').html('<div onclick="searchaddF()" class="search-add">找不到院校？ 手动添加>></div>').show();
		});
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
	                	ctoken:newTime,
	                	source:0
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
