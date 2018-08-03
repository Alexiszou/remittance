/*我的订单*/
	var content='';
	/*我的订单链接解析*/
	var thisHref=window.location.href;

	/*普通当日到账文案*/
	var careT0='注意：8:00-21:00订单，订单有效期至当日22:00；21:00-次日8:00订单，订单有效期至次日8:00，请及时支付。<br>如支付遇到问题，请直接咨询在线客服。预计T+0到账，我们将以短信或邮件形式通知您!';
	var careT2='注意：8:00-21:00订单，订单有效期至当日22:00；21:00-次日8:00订单，订单有效期至次日8:00，请及时支付。<br>如支付遇到问题，请直接咨询在线客服。预计T+2到账，我们将以短信或邮件形式通知您!';


	$('.wddd-nav li').click(function(){
		var _this=$(this);
		$('.wddd-nav li').removeClass('cur');
		$(this).addClass('cur');
		ddUpdate(_this);
	})
	/*订单载入*/	
	function ddOnlond(name){
		if (name.index()==0) {
			content='';
			$('.wdddR11').show();
			$('.wdddR22').hide();
				$.ajax({/*未提交订单*/
			        type: "GET",
			        url:ip+'/orders/getList/'+sessionStorage.id+'/0',
			        //data:JSON.stringify(dataS),
			        async: false,
			        dataType: "json",
			        //contentType:"application/json",
			        error: function(request) {
			            alert("Connection error");
			        },
			        success: function(data) {
			        	console.log(data)
			        	var careStyle='';/*到账方式*/
			        	var fkpzIf='';/*是否付款成功*/
			        	for (var i=0; i < data.length; i++) {
			        		data[i].PayStyle==0?careStyle=careT0:careStyle=careT2/*到账方式判断*/
			        		if (data[i].FinishFlg==0) {
			        			content+='<div class="ddfk-middle-ie wddd-middle wdddR1">						<div class="ddfk-middleT">							<span>'+data[i].ChName+'</span> 向 <span>'+data[i].EnName+'</span> 汇款 <span>'+data[i].PriceEN+'</span>'+data[i].UnitText+'					</div>						<div class="ddfk-middleB wddd-middleB">							<ul class="flex">								<li class="flex1">订单编号<br>									<span>'+data[i].OrderNo+'</span>								</li>								<li class="flex1">付款金额<br>									<span class="red">'+data[i].PriceCN+'人民币</span>								</li>								<li class="flex1">创建时间<br>									<span>'+format(data[i].CreatedTime.time)+'</span>								</li>								<li class="flex1"><div class="btn" id='+data[i].OrderNo+' onclick="ddxqF(this)">查看订单详情</div></li>							</ul>						</div>						<div class="ddfk-top wddd-top">							<div class="ddfk-line">								<div class="ddfk-lineC"></div>							</div>							<ul class="flex">								<li class="flex1">提交订单</li>								<li class="flex1">等待付款</li>								<li class="flex1 cur">付款成功<br><span class="red">订单失效</span></li>								<li class="flex1 cur">已成功到账<br><span class="red">订单失效</span></li>							</ul>						</div>						<div class="ddfk-bot wddd-bot">							<div class="text">'+careStyle+'</div>							<div class="clear btnBox1"><div onclick="jxfkF(this)" class="btnL fr" id='+data[i].OrderNo+'>继续付款</div></div>						</div>					</div>'
			        		}else if(data[i].FinishFlg==1){
			        			content+='<div class="ddfk-middle-ie wddd-middle wdddR1">						<div class="ddfk-middleT">							<span>'+data[i].ChName+'</span> 向 <span>'+data[i].EnName+'</span> 汇款 <span>'+data[i].PriceEN+'</span> '+data[i].UnitText+'					</div>						<div class="ddfk-middleB wddd-middleB">							<ul class="flex">								<li class="flex1">订单编号<br>									<span>'+data[i].OrderNo+'</span>								</li>								<li class="flex1">付款金额<br>									<span class="red">'+data[i].PriceCN+'人民币</span>								</li>								<li class="flex1">创建时间<br>									<span>'+format(data[i].CreatedTime.time)+'</span>								</li>								<li class="flex1"><div class="btn" id='+data[i].OrderNo+' onclick="ddxqF(this)">查看订单详情</div></li>							</ul>						</div>						<div class="ddfk-top wddd-top">							<div class="ddfk-line">								<div class="ddfk-lineC" style="width:66.6%"></div>							</div>							<ul class="flex">								<li class="flex1">提交订单</li>								<li class="flex1">等待付款</li>								<li class="flex1">付款成功<br><span class="red">订单失效</span></li>								<li class="flex1 cur">已成功到账<br><span class="red">订单失效</span></li>							</ul>						</div>						<div class="ddfk-bot wddd-bot">							<div class="text">'+careStyle+'</div>							<div class="clear btnBox1"><div class="btnR fr" onclick="fkpzF(this)" id='+data[i].OrderNo+'>查看付款凭证</div></div></div>					</div>'
			        		}else if(data[i].FinishFlg==2){
			        			content+='<div class="ddfk-middle-ie wddd-middle wdddR1">						<div class="ddfk-middleT">							<span>'+data[i].ChName+'</span> 向 <span>'+data[i].EnName+'</span> 汇款 <span>'+data[i].PriceEN+'</span> '+data[i].UnitText+'					</div>						<div class="ddfk-middleB wddd-middleB">							<ul class="flex">								<li class="flex1">订单编号<br>									<span>'+data[i].OrderNo+'</span>								</li>								<li class="flex1">付款金额<br>									<span class="red">'+data[i].PriceCN+'人民币</span>								</li>								<li class="flex1">创建时间<br>									<span>'+format(data[i].CreatedTime.time)+'</span>								</li>								<li class="flex1"><div class="btn" id='+data[i].OrderNo+' onclick="ddxqF(this)">查看订单详情</div></li>							</ul>						</div>						<div class="ddfk-top wddd-top">							<div class="ddfk-line">								<div class="ddfk-lineC"></div>							</div>							<ul class="flex">								<li class="flex1">提交订单</li>								<li class="flex1">等待付款<br><span class="red" style="display:block">订单失效</span></li>								<li class="flex1 cur">付款成功</li>								<li class="flex1 cur">已成功到账<br><span class="red">已失效</span></li>							</ul>						</div>						<div class="ddfk-bot wddd-bot">							<div class="text">'+careStyle+'</div>							<div class="clear btnBox1"></div>						</div>					</div>'
			        		}else if(data[i].FinishFlg==3){
			        			content+='<div class="ddfk-middle-ie wddd-middle wdddR1">						<div class="ddfk-middleT">							<span>'+data[i].ChName+'</span> 向 <span>'+data[i].EnName+'</span> 汇款 <span>'+data[i].PriceEN+'</span>'+data[i].UnitText+'						</div>						<div class="ddfk-middleB wddd-middleB">							<ul class="flex">								<li class="flex1">订单编号<br>									<span>'+data[i].OrderNo+'</span>								</li>								<li class="flex1">付款金额<br>									<span class="red">'+data[i].PriceCN+'人民币</span>								</li>								<li class="flex1">创建时间<br>									<span>'+format(data[i].CreatedTime.time)+'</span>								</li>								<li class="flex1"><div class="btn" id='+data[i].OrderNo+' onclick="ddxqF(this)">查看订单详情</div></li>							</ul>						</div>						<div class="ddfk-top wddd-top">							<div class="ddfk-line">								<div class="ddfk-lineC" style="width:66.6%"></div>							</div>							<ul class="flex">								<li class="flex1">提交订单</li>								<li class="flex1">等待付款</li>								<li class="flex1">付款成功<br><span class="red">订单失效</span></li>								<li class="flex1 cur">已成功到账<br><span class="red" style="display:block">到账失败</span></li>							</ul>						</div>						<div class="ddfk-bot wddd-bot">							<div class="text">'+careStyle+'</div>							<div class="clear btnBox1"><div class="btnR fr" onclick="fkpzF(this)" id='+data[i].OrderNo+'>查看付款凭证</div></div>						</div>					</div>'
			        		}else if(data[i].FinishFlg==5){
			        			content+='<div class="ddfk-middle-ie wddd-middle wdddR1">						<div class="ddfk-middleT">							<span>'+data[i].ChName+'</span> 向 <span>'+data[i].EnName+'</span> 汇款 <span>'+data[i].PriceEN+'</span> '+data[i].UnitText+'					</div>						<div class="ddfk-middleB wddd-middleB">							<ul class="flex">								<li class="flex1">订单编号<br>									<span>'+data[i].OrderNo+'</span>								</li>								<li class="flex1">付款金额<br>									<span class="red">'+data[i].PriceCN+'人民币</span>								</li>								<li class="flex1">创建时间<br>									<span>'+format(data[i].CreatedTime.time)+'</span>								</li>								<li class="flex1"><div class="btn" id='+data[i].OrderNo+' onclick="ddxqF(this)">查看订单详情</div></li>							</ul>						</div>						<div class="ddfk-top wddd-top">							<div class="ddfk-line">								<div class="ddfk-lineC" style="width:66.6%"></div>							</div>							<ul class="flex">								<li class="flex1">提交订单</li>								<li class="flex1">等待付款</li>								<li class="flex1 cur">付款成功<br><span class="red"style="display:block">支付失败</span></li>								<li class="flex1 cur">已成功到账<br><span class="red">到账失败</span></li>							</ul>						</div>						<div class="ddfk-bot wddd-bot">							<div class="text">'+careStyle+'</div>							<div class="clear btnBox1"></div>						</div>					</div>'
			        		}

			        	}
			        	$('.wdddR11').html(content);
			        }
			    })
		}else{
			content='';
			$('.wdddR11').hide();
			$('.wdddR22').show();
			$.ajax({/*已完成订单*/
			        type: "GET",
			        url:ip+'/orders/getList/'+sessionStorage.id+'/4',
			        //data:JSON.stringify(dataS),
			        async: false,
			        dataType: "json",
			        //contentType:"application/json",
			        error: function(request) {
			            alert("Connection error");
			        },
			        success: function(data) {
			        	console.log(data)
			        	var pinying;/*拼音拆分*/
			        	for (var i=0; i < data.length; i++) {
			        		pinYin=data[i].PyName.split(',');
			        		pinYin=pinYin[0].substring(0,1).toUpperCase()+''+pinYin[0].substring(1)+' '+pinYin[1].substring(0,1).toUpperCase()+''+pinYin[1].substring(1);
			        		content+='<div class="wdddR2">						<div class="wdddR2C">							<div class="wdddR2C-top clear">									<div class="fl">订单编号：'+data[i].OrderNo+'</div>									<div class="fr">订单完成时间：'+format(data[i].CreatedTime.time)+'</div>							</div>							<ul class="clear">								<li class="fl">学生姓名：<span>'+pinYin+'</span></li>								<li class="fl">创建日期：<span>'+format(data[i].CreatedTime.time)+'</span></li>								<li class="fl">收款院校：<span><span>'+data[i].EnName+'</span></li>								<li class="fl">汇款金额：<span>'+data[i].PriceEN+' '+data[i].UnitText+'</span></li>								<li class="fl">支付金额：<span>'+data[i].PriceCN+' 元</span></li>								<a href="../remit/qrfkxx.html"><li onclick="ddxqF(this)" id='+data[i].OrderNo+' class="fl btn">查看订单详情</li></a>							</ul>							<div class="clear btnBox2">								<a href="../remit/hksy.html"><div class="btnL fl">再次付款</div></a><div class="btnR fr" onclick="fkpzF(this)" id='+data[i].OrderNo+'>查看付款凭证</div>								</div>						</div>					</div>'
			        	}
			        	$('.wdddR22').html(content);
			        }
			    })
		}
	}
	/*继续付款跳转*/
	function jxfkF(name){
		sessionStorage.ordersId=$(name).attr('id');
		sessionStorage.openWddd=1;
		window.location.href='../remit/ddfk.html';
	}
	/*查看订单详情*/
	function ddxqF(name){
		sessionStorage.openWddd=1;
		sessionStorage.ordersId=$(name).attr('id');
		window.location.href='../remit/qrfkxx.html';
	}
	/*查看付款凭证*/
	function fkpzF(name){
		sessionStorage.ordersId=$(name).attr('id');
		window.open("../personal/hkpz.html");  
	}
	/*判断是否有跟新订单*/
	function ddUpdate(name){
		if(thisHref.indexOf('?updateOrder')!=-1){/*我已支付完成跳转*/
			$.ajax({/*付款完后压缩并上传该订单的附件*/
			        type: "POST",
			        url:ip+'/orders/zipAndUpFile/'+sessionStorage.ordersId,
			        //data:JSON.stringify(dataS),
			        dataType: "json",
			        //contentType:"application/json",
			        error: function(request) {
			            //alert('没成功添加');
			        },
			        success: function(data) {
			        	//alert('成功添加');
			        }
			    })
			    $.ajax({/* 付款完后更新数据订单*/
				        type: "POST",
				        url:ip+'/orders/updatePayResult/'+sessionStorage.ordersId,
				        //data:JSON.stringify(dataS),
				        dataType: "json",
				        //contentType:"application/json",
				        error: function(request) {
				            //alert('没成功添加');
				            ddOnlond(name)
				        },
				        success: function(data) {
				        	//alert('成功添加');
				        	ddOnlond(name)
				        }
				    })
		}else if(thisHref.indexOf('order_no')!=-1&&thisHref.indexOf('pay_result')!=-1) {
			var thisNum=thisHref.indexOf('pay_result');
			thisNum=thisHref.substring(thisNum+11,thisNum+12)
			if (thisNum==1) {
				thisHref=thisHref.split('order_no=');
				thisHref=thisHref[1].split('&pay_no');
				console.log(thisHref)
				$.ajax({/*付款完后压缩并上传该订单的附件*/
			        type: "POST",
			        url:ip+'/orders/zipAndUpFile/'+thisHref[0],
			        //data:JSON.stringify(dataS),
			        dataType: "json",
			        //contentType:"application/json",
			        error: function(request) {
			            //alert('没成功添加');
			        },
			        success: function(data) {
			        	//alert('成功添加');
			        }
			    })
			    $.ajax({/* 付款完后更新数据订单*/
				        type: "POST",
				        url:ip+'/orders/updatePayResult/'+thisHref[0],
				        //data:JSON.stringify(dataS),
				        dataType: "json",
				        //contentType:"application/json",
				        error: function(request) {
				            //alert('没成功添加');
				            ddOnlond(name)
				        },
				        success: function(data) {
				        	//alert('成功添加');
				        	ddOnlond(name)
				        }
				    })
			}else{ddOnlond(name)}
		}else{
			ddOnlond(name)
		}
	}
	/*默认选中*/
	ddUpdate($('.wddd-nav li:eq(0)'));