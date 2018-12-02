/**
 * 提示框相关js
 */
var msgBox = {};

/**
 * 提示框(长信息)
 * opt:content:警告内容
 * 	   type:类型success/fail
 */
msgBox.info = function(opt){
	if(opt.type==null){
		opt.type="fail";
	}
	if(opt.callback!=null){
		jBox.setDefaults({ defaults: {closed:opt.callback} });
	}else{
		jBox.setDefaults({ defaults: {closed:function(){}} });
	}
	if(opt.type=="success"){
		jBox.info('',opt.content);
	}else{
		jBox.error('', opt.content,'S');
	}
	ie8InRounded();
}


/**
 * 提示框(短信息)
 * opt:type:类型success/fail
 * 	   content:内容
 */
msgBox.tip = function(opt){
	if(opt.type==null){
		opt.type="fail";
	}
	if(opt.callback!=null){
		jBox.setDefaults({ defaults: {closed:opt.callback} });
	}else{
		jBox.setDefaults({ defaults: {closed:function(){}} });
	}
	if(opt.type=="success"){
		jBox.tip(opt.content,'',{
			closed:function(){
				if(opt.callback!=null){
					opt.callback();
				}
			}	
		});
	}else{
		jBox.error('', opt.content,'S');
	}
	ie8InRounded();
}


/**
 * 确认对话框
 * opt:content:提示内容
 * 	   success:确认时回调函数
 * 	   cancel:取消时回调函数
 * 	   fontSize:B/S
 * 	   buttons:  按钮true/false 样例：{'是': true, '否': false}
 */
msgBox.confirm = function(opt){
	jBox.setDefaults({ defaults: {closed:function(){}} });
	var submit = function(v,h,f){
		if (v == 'yes') {
			if(opt.success!=null){
				 opt.success();
			}
        }else{
        	if(opt.cancel!=null){
        		opt.cancel();
			}
        }
	}
	var fontSize = "";
	if(opt.fontSize=="S"||opt.fontSize=="s"){
		 fontSize = "S";
	}

	jBox.warning('', opt.content, submit,fontSize);
	ie8InRounded();
}