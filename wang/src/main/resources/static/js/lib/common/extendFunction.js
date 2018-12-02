/**
 * 转义字符串的html标签
 */
$.extend(
		{ 
			escapeHtml : function(text) {
				  return text?$('<p/>').text(text).html():text;
			}
		}
);

$.extend({
	isAlpha:function(s){
		return /[a-z|A-Z]/.test(s);
	}
});


$.extend({
	hasSpecialChar:function(s,charset){
		//var charset = "@#$%&*~";
		
		var specialCharsetRegExp =  new RegExp(/[\@\#\$\%\&\*\~]+/);
		if(typeof charset ==="string"){
			var str = "";
			for(i=0;i<charset.length;i++){
				if(!$.isAlpha(charset.charAt(i))){
					str+="\\"+charset.charAt(i);
				}
			}
			specialCharsetRegExp = new RegExp("["+str+"]+");
		}
		if(!s){
			return false;
		}
		else{
			return specialCharsetRegExp.test(s);
		} 
	}
	
});

$.extend({
	null2Blank:function(s){
		//var charset = "@#$%&*~";
		if(s==null || typeof(s)=== "undefined"){
			return "";
		}
		else{
			return s;
		}
	}
	
});
$.extend({
	blank2HtmlBlank:function(s){
		//var charset = "@#$%&*~";
		if(s==null || typeof(s)=== "undefined"){
			return "";
		}
		else{
			return s.replace(/\s/g,"&nbsp;");
		}
	}
	
});