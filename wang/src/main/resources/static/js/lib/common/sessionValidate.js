//处理session超时
$.ajaxSetup({
    contentType:"application/x-www-form-urlencoded;charset=utf-8",
    complete:function(XMLHttpRequest,textStatus){
    	if(typeof(XMLHttpRequest.getResponseHeader)=="function"){
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            if(sessionstatus == "kickout"){
            	window.location = getRootPath()+"/login?kickout=true";
            }
            else if(sessionstatus == "timeout"){
                window.location = getRootPath()+"/login?timeout=true";
            }  
    	}
    }  
}); 