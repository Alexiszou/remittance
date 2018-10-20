	var ip='https://www.erbaopay.com/remittance';
    //var ip='http://localhost:8080';

	var newTime='';//时间戳
	//sessionStorage.id="gaga";
	//sessionStorage.setItem("id", "value");
	//alert(sessionStorage.id)
	//sessionStorage.removeItem("id");
	//localStorage.removeItem("id");
	//alert(sessionStorage.getItem("id"))
	

	$('.header-btnR').click(function(){
		if (localStorage.id) {
			sessionStorage.id=localStorage.id;
			sessionStorage.name=localStorage.name;
			$('header,.move-content').toggleClass('curR');
			$('.header-contentR').toggleClass('cur');
			$('.header-contentR h1').text(sessionStorage.name+'，您好');
		}else if (sessionStorage.id) {
			$('header,.move-content').toggleClass('curR');
			$('.header-contentR').toggleClass('cur');
			$('.header-contentR h1').text(sessionStorage.name+'，您好');
		}else{
			window.location.href='dl.html';
		}
	});
/*账户退出*/
$('.header-contentR li.out').click(function(){
		sessionStorage.removeItem("id");
		localStorage.removeItem("id");
		sessionStorage.removeItem("name");
		localStorage.removeItem("name");
		sessionStorage.removeItem("schoolsID");
		sessionStorage.removeItem("schoolsData");
		$('header,.move-content').toggleClass('curR');
		$('.header-contentR').toggleClass('cur');
	})
/*计算REM*/
	var docEl = document.documentElement;
	var resize = 'orientationchange' in window ? 'orientationchange' : 'resize';
	var setRem = function() {
		var screenWidth = docEl.clientWidth || window.screen.width || 360;
		docEl.style.fontSize = (100 * screenWidth / 1080 / 10) + 'px';
	};
	window.addEventListener('resize', setRem, false);
	setRem();
/*导航动画*/
	$('.header-btnL').click(function(){
		$('header,.move-content').toggleClass('curL');
		$('.header-contentL').toggleClass('cur');
	});
/*使用条款和隐私政策*/
$('.shadow-login .box-btn').click(function(){
		$(this).toggleClass('cur');
		$('.header-RegisteredIn-btn').toggleClass('cur');
	})
/*点击中间回到自然*/
$('.move-content').click(function(){
	$('header,.move-content').removeClass('curL curR');
	$('.header-contentL,.header-contentR').removeClass('cur');
})
/* 验证判断 */
/*密码显示*/
	for (var i =0; i < $('.seePw').length; i++) {
		$('.seePw').eq(i).get(0).addEventListener('touchstart',function(event){
		var event = event || window.event;  
			$(this).css({'background':'url(images/seePw.png) no-repeat center','background-size':'100% 90%'}).parent().find('input').attr('type','text');
		})
		$('.seePw').eq(i).get(0).addEventListener('touchend',function(event){
			var event = event || window.event;  
			$(this).css({'background':'url(images/noSeePw.png) no-repeat center','background-size':'100% 90%'}).parent().find('input').attr('type','password');
		})
	}
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