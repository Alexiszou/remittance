function ymformatter(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    return y+'-'+(m<10?('0'+m):m);
}
function ymparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    if (!isNaN(y) && !isNaN(m)){
        return new Date(y,m-1);
    } else {
        return new Date();
    }
}  
function myformatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d);
}
function myparser(s){
    if (!s) return new Date();
    var ss = (s.split('-'));
    var y = parseInt(ss[0],10);
    var m = parseInt(ss[1],10);
    var d = parseInt(ss[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        return new Date(y,m-1,d);
    } else {
        return new Date();
    }
} 

function myfullformatter(date){
            var y = date.getFullYear();
            var m = date.getMonth()+1;
            var d = date.getDate();
            var h = date.getHours();
            var mi = date.getMinutes();
            var s = date.getSeconds();
            return y+'-'+(m<10?('0'+m):m)+'-'+(d<10?('0'+d):d)+' '+(h<10?('0'+h):h)+':'+(mi<10?('0'+mi):mi)+':'+(s<10?('0'+s):s);
}
function myfullparser(s){
    if (!s) return new Date();
    var ss_1 = (s.split(' '))[0].split('-');
    var ss_2 = (s.split(' '))[1].split(':');;
    
    var y = parseInt(ss_1[0],10);
    var m = parseInt(ss_1[1],10);
    var d = parseInt(ss_1[2],10);
    var h = parseInt(ss_2[0],10);
    var mi = parseInt(ss_2[1],10);
    var s = parseInt(ss_2[2],10);
    if (!isNaN(y) && !isNaN(m) && !isNaN(d)){
        var d = new Date(y,m-1,d);
        if(!isNaN(h))
        	d.setHours(h);
        if(!isNaN(mi))
        	d.setMinutes(mi);
        if(!isNaN(s))
        	d.setSeconds(s);
        return d;
    } else {
        return new Date();
    }
} 

Date.prototype.format = function(format){ 
	var o = { 
    	"M+" : this.getMonth()+1, //month 
    	"d+" : this.getDate(), //day 
    	"h+" : this.getHours(), //hour 
    	"m+" : this.getMinutes(), //minute 
    	"s+" : this.getSeconds(), //second 
    	"q+" : Math.floor((this.getMonth()+3)/3), //quarter 
    	"S" : this.getMilliseconds() //millisecond 
	} 
	if(/(y+)/.test(format)) { 
			format = format.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
		} 

		for(var k in o) { 
   		if(new RegExp("("+ k +")").test(format)) { 
   			format = format.replace(RegExp.$1, RegExp.$1.length==1 ? o[k] : ("00"+ o[k]).substr((""+ o[k]).length)); 
   		} 
		} 
		return format; 

}