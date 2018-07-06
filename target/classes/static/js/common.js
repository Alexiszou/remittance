
	var ip='http://www.erbaopay.com/remittance';
	//var ip='http://localhost:8088';
	//var ip="";
	var newTime='';//时间戳
	//sessionStorage.id="gaga";
	//sessionStorage.setItem("id", "value");
	//alert(sessionStorage.id)
	//sessionStorage.removeItem("id");
	//localStorage.removeItem("id");
	//alert(sessionStorage.getItem("id"))
	
	if (localStorage.id) {
		$('.header-login').hide();
		$('.header-user').show();
		$('.header-userId').text(localStorage.name+'，你好');
		$('.headerC nav div.active').show();
		sessionStorage.id=localStorage.id;
	}else{
		if (sessionStorage.id) {
			$('.header-login').hide();
			$('.header-user').show();
			$('.header-userId').text(sessionStorage.name+'，你好');
			$('.headerC nav div.active').show();
		}else{
			$('.header-login').show();
			$('.header-user').hide();
			$('.headerC nav div.active').hide();
		}
	}

	var documentW=parseInt($(window).width());
	var documentH=parseInt($(window).height());
	/*账户退出*/
	$('.header-user li.out').click(function(){
		sessionStorage.removeItem("id");
		localStorage.removeItem("id");
		sessionStorage.removeItem("name");
		localStorage.removeItem("name");
		sessionStorage.removeItem("schoolsID");
		sessionStorage.removeItem("schoolsData");
		$('.header-login').show();
		$('.header-user').hide();
		$('.headerC nav div.active').hide();
	})
	/*账户登录跳转*/
	$('.header-user li.grzl').click(function(){
		window.location.href='personal/grzl.html';
	})
	$('.header-user li.wddd').click(function(){
		window.location.href='personal/wddd.html';
	})
	/*登录遮罩*/
	$('.shadow-login,.shadow-xiugai').css('height',documentH);
	$('.shadow-login .close').click(function(){
		$('.shadow-login').hide();
		$('.shadow-login input').val('');
	})
	$('.header-login .login-left').click(function(){
		$('.shadow-login .login,.shadow-login .RegisteredIn,.shadow-login .fgpassW').hide();
		$('.shadow-login .login,.shadow-login').show();
	})
	$('.header-login .login-right').click(function(){
		$('.shadow-login .login,.shadow-login .RegisteredIn,.shadow-login .fgpassW').hide();
		$('.shadow-login .RegisteredIn,.shadow-login').show();
		$('.shadow-login .true,.shadow-login .false').hide();
	})
	$('.shadow-login .box-btn').click(function(){
		$(this).toggleClass('cur');
		$('.header-RegisteredIn-btn').toggleClass('cur');
	})
	$('.RegisteredIn-btn').click(function(){
		$('.shadow-login .login,.shadow-login .RegisteredIn,.shadow-login .fgpassW').hide();
		$('.shadow-login .login,.shadow-login').show();
	})
	$('.header-login-btnR').click(function(){
		$('.shadow-login .login,.shadow-login .RegisteredIn,.shadow-login .fgpassW').hide();
		$('.shadow-login .RegisteredIn,.shadow-login').show();
	})
	$('.shadow-login .box a,.hkwdl-zhanghu .Submit span').click(function(){
		$('.shadow-login .login,.shadow-login .RegisteredIn,.shadow-login .fgpassW').hide();
		$('.shadow-login .fgpassW,.shadow-login').show();
	})
	/*  生成二维码 */
	$('.header-login .login-right,.header-login-btnR,img.codeNum').click(function(){
		 newTime=new Date().getTime();
		 $('img.codeNum').attr('src',ip+'/captcha/image.html?ctoken='+newTime);
        //$('img.codeNum').attr('src',"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522329664845&di=6522503c84d8dde69be2f49282799d36&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0149ce57052b106ac7257948400b76.jpg");

	})
	/*注册验证弹出框*/
	$('.shadow-login .RegisteredIn input.email').blur(function(){
		isEmail($('.shadow-login .RegisteredIn input.email'))
	})
	$('.shadow-login .RegisteredIn input.name').blur(function(){
		isName($('.shadow-login .RegisteredIn input.name'))
	})
	$('.shadow-login .RegisteredIn input.phone').blur(function(){
		isPhone($('.shadow-login .RegisteredIn input.phone'))
	})
	$('.shadow-login .RegisteredIn input.password').blur(function(){
		isPassword($('.shadow-login .RegisteredIn input.password'))
	})
	$('.shadow-login .RegisteredIn input.passwordAg').blur(function(){
		if ($('.shadow-login .RegisteredIn input.passwordAg').val()==$('.shadow-login .RegisteredIn input.password').val()) {
			isStyle($('.shadow-login .RegisteredIn input.passwordAg'))
		}else{
			isStyle1($('.shadow-login .RegisteredIn input.passwordAg'))
		}
	})
	$('.header-RegisteredIn-btn').click(function(){
		if (!$(".shadow-login .RegisteredIn .true:not(:last)").is(":hidden")&&$('.shadow-login .RegisteredIn .box-btn').hasClass('cur')){
			var dataS={
                	email:$('.shadow-login .RegisteredIn input.email').val(),
                	name:$('.shadow-login .RegisteredIn input.name').val(),
                	telphone:$('.shadow-login .RegisteredIn input.phone').val(),
                	password:$('.shadow-login .RegisteredIn input.password').val(),
                	captcha:$('.shadow-login .RegisteredIn input.codeNum').val(),
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
                	//console.log(data)
                    if (data[0].result=='T') {
                    	sessionStorage.id=data[0].data;
                    	isStyle($('.shadow-login .RegisteredIn input.codeNum'));
                    	$('.header-userId').text($('.shadow-login .RegisteredIn input.name').val()+'，你好')
                    	sessionStorage.name=$('.shadow-login .RegisteredIn input.name').val();
						$('.shadow-login').hide();
                    	$('.header-login').hide();
						$('.header-user').show();
                    }else{
                    	isStyle1($('.shadow-login .RegisteredIn input.codeNum'));
                    	$('.shadow-login .RegisteredIn input.codeNum').parent().find('.false').text(data[0].data);
                    }
                }
            })
		}
	})

	/*登录验证弹出框*/
	function loginF(){
		$.ajax({
                type: "GET",
                url:ip+'/users/login?name='+$('.shadow-login .login input.name').val()+'&password='+$('.shadow-login .login input.password').val(),
                /*data:{
                	name:$('.shadow-login .RegisteredIn input.name').val(),
                	password:$('.shadow-login .RegisteredIn input.password').val(),
                },*/
                async: false,
                dataType: "json",
                error: function(request) {
                    alert("2");
                },
                success: function(data) {
                	//console.log(data)
                    if (data==0) {
                    	isStyle1($('.shadow-login .login input.name'));
                    	isStyle1($('.shadow-login .login input.password'));
                    }else{
                    	if ($('.shadow-login .login .box-btn').hasClass('cur')) {
							localStorage.id=data;
							sessionStorage.id=data;
						}else{
							sessionStorage.id=data;
						}
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
			                    $('.header-userId').text(data[0].Name+'，你好')
			                    localStorage.name=data[0].Name;
			                    sessionStorage.name=data[0].Name;
								$('.shadow-login').hide();
		                    	$('.header-login').hide();
								$('.header-user').show();
			                }
			            })
                    }
                }
            })
	}
	$(document).keydown(function(event){
		if(event.keyCode==13&&$('.shadow-login .login input.name').val()!=''&&$('.shadow-login .login input.password').val()!=='') {
			loginF();
		}
	})
	$('.header-login-btnL').click(function(){
		loginF();
	})

	/*忘记密码验证弹出框*/
	$('.shadow-login .fgpassW input.name').blur(function(){
		if ($(this).val()!='') {
			$.ajax({
	            type: "GET",
	            url:ip+'/users/ckUser?userName='+$('.shadow-login .fgpassW input.name').val(),
	            //data:JSON.stringify(dataS),
	            async: false,
	            dataType: "json",
	            error: function(request) {
	                alert("Connection error");
	            },
	            success: function(data) {
	            	console.log(data)
	                if(data==0){
	                	isStyle1($('.shadow-login .fgpassW input.name'));
	                }else{
	                	isStyle($('.shadow-login .fgpassW input.name'));
	                }
	            }
	        })
		}else{
			isStyle1($(this));
		}
	})
	$('.shadow-login .fgpassW input.password').blur(function(){
		isPassword($('.shadow-login .fgpassW input.password'))
	})
		/*获取验证码*/
		$('.shadow-login .fgpassW .codeBtn').click(function(){
			if (!$('.shadow-login .fgpassW input.name').parent().find('.true').is(":hidden")&&!$('.shadow-login .fgpassW input.password').parent().find('.true').is(":hidden")&&!$(this).hasClass('cur')) {
				$('.shadow-login .fgpassW .codeBtn').addClass('cur');/*添加认证码倒数*/
				var codeBtnNum=31;
				var codeTimer=setInterval(function(){
					codeBtnNum--;
					if (codeBtnNum==0) {
						clearInterval(codeTimer);
						$('.shadow-login .fgpassW .codeBtn').text('获取').removeClass('cur');
						return
					}
					$('.shadow-login .fgpassW .codeBtn').text(codeBtnNum+'s');
				},1000)
				$.ajax({
	                type: "GET",
	                url:ip+'/users/sendCode?userName='+$('.shadow-login .fgpassW input.name').val(),
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
	                	//alert(data)
	                }
	            })
	        }
		})
		/*点击修改密码*/
	$('.header-fgpassW-btn').click(function(){
		if (!$(".shadow-login .fgpassW .true:not(:last)").is(":hidden")){
			var dataS={
				userName:$('.shadow-login .fgpassW input.name').val(),
                password:$('.shadow-login .fgpassW input.password').val(),
                checkCode:$('.shadow-login .fgpassW input.checkCode').val()
			}
			console.log(JSON.stringify(dataS))
			$.ajax({
                type: "GET",
                url:ip+'/users/resetPassword',
                data:dataS,
                async: false,
                dataType: "json",
                error: function(request) {
                    alert("Connection error");
                },
                success: function(data) {
                    console.log(data);
                    if (data==1) {
                    	isStyle($('.shadow-login .fgpassW input.checkCode'));
                    	$('.shadow-login .fgpassW input.checkCode').parent().find('.true').text('修改成功');
                    	alert("密码修改成功");
                        $('.shadow-login').hide();
                        $('.shadow-login input').val('');
                    }else{
                    	isStyle1($('.shadow-login .fgpassW input.checkCode'));
                    	$('.shadow-login .fgpassW input.checkCode').parent().find('.false').text('验证码错误');
                    }
                }
            })
		}
	})

	/* 验证判断 */
		/*判断邮箱*/
		function isEmail(obj) {
			if(/[_a-zA-Z\d\-\.]+@[_a-zA-Z\d\-]+(\.[_a-zA-Z\d\-]+)+$/.test(obj.val())){
				isStyle(obj);
			}else{
				isStyle1(obj);
			}
		}
		/*判断姓名*/
		function isName(obj) {
			if(/^[\u4E00-\u9FA5]{1,6}$/.test(obj.val())){
				isStyle(obj);
			}else{
				isStyle1(obj);
			}
		}
		/*判断电话*/
		function isPhone(obj) {
			if(/^13[0-9]{1}[0-9]{8}$|15[0-9]{1}[0-9]{8}$|16[0-9]{1}[0-9]{8}$|18[0-9]{1}[0-9]{8}$|17[0-9]{1}[0-9]{8}$/.test(obj.val())){
				isStyle(obj);
			}else{
				isStyle1(obj);
			}
		}
		/*判断密码*/
		function isPassword(obj) {
			if(/^([a-z0-9\.\@\!\#\$\%\^\&\*\(\)]){6,20}$/i.test(obj.val())){
				isStyle(obj);
			}else{
				isStyle1(obj);
			}
		}
		/*判断英文*/
		function isEn(obj) {
			if(/^\w+$/.test(obj.val())){
				isStyle(obj);
			}else{
				isStyle1(obj);
			}
		}
		/*判断不为空*/
		function isNull(obj) {
			if(obj.val()!=''){
				isStyle(obj);
			}else{
				isStyle1(obj);
			}
		}
		/*判断身份证*/
		function isIdcard(obj){
			if ( /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/.test(obj.val())) {
				isStyle(obj)
			}else{
				isStyle1(obj)
			}
		}
	/*验证确定样式*/
	function isStyle(obj){
		obj.parent().find('.true,.false').hide();
		obj.parent().find('.true').show();
	}
	function isStyle1(obj){
		obj.parent().find('.true,.false').hide();
		obj.parent().find('.false').show();
	}
function add0(m){
	return m<10?'0'+m:m 
}
function format(time)
{
	//ijianchuo是整数，否则要parseInt转换
	var time = new Date(time);
	var y = time.getFullYear();
	var m = time.getMonth()+1;
	var d = time.getDate();
	var h = time.getHours();
	var mm = time.getMinutes();
	var s = time.getSeconds();
	//+' '+add0(h)+':'+add0(mm)+':'+add0(s)
	return y+'-'+add0(m)+'-'+add0(d)+' '+add0(h)+':'+add0(mm)+':'+add0(s)
}


    function getUrlParam(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)","i");
        var r = window.location.search.substr(1).match(reg);
        if (r!=null) return (r[2]); return null;
    }