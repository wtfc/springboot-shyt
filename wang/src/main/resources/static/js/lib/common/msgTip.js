 /**
 *	右下角弹出框相关js
 **/

var msgTip = {};

msgTip.messageTip_lh=$(".messageTip").height()+ 10;

/**
 * 初始化函数
 * 60000
 */
msgTip.init = function(){
	$(".messageTip").css("bottom",-msgTip.messageTip_lh+'px');
	
	$('.messageTip-close').click(function(){
        $(".messageTip").animate({bottom:-msgTip.messageTip_lh+"px"},500);
        setTimeout(function(){
        	navigator.userAgent.indexOf("MSIE") > 0 && removeInfoInOffice();
        	$(".messageTip .messageList").html("");
        },550)
    });
	msgTip.refresh();
	window.setInterval('msgTip.refresh()',60000);
}

/**
 * 刷新函数
 */
msgTip.refresh = function(){
	//不显示时重新加载数据并显示
	if(parseInt($(".messageTip").css("bottom"))==-msgTip.messageTip_lh){
		msgTip.loadData();
		if($(".messageTip").find("li").length>0){
			msgTip.show();
		}
	}else{
		//显示时重新加载数据
		if($(".messageTip").find("li").length<5){
			msgTip.loadData();
		}
	}
	
	//更新待办数量
	msgTip.reminders();
}

/**
 * 更新待办数量
 */
msgTip.reminders = function(){
	$.ajax({
		type : "POST",
		dataType:'json',
		url:getRootPath()+"/sys/reminders/getRemindCount.action",
		success:function(data){
			$.each(data, function(i, element){
				var entitydata =data[i];
				if(entitydata.num!="0"){
					$('#'+entitydata.divid+'b').show();
				    $('#'+entitydata.divid).html(entitydata.num);
				}else{
					$('#'+entitydata.divid+'b').hide();
				}
			});		
		}
	});
}

/**
 * 弹出显示页
 */
msgTip.show = function(){
	 if(navigator.userAgent.indexOf("MSIE")>0){
		 setInfoInOffice();
	 }
	 $(".messageTip").stop().animate({bottom:'0'},500);
	 $('#__PostscriptIframeBodyCovere_info').css('height',(msgTip.messageTip_lh-8)+'px');
	 playVoice("voiceDiv");
}

/**
 * 加载数据
 */
msgTip.loadData = function(){
	$.ajax({
		url:getRootPath()+"/sys/noticeMsg/queryMsgTip.action",
		method:"GET",
		async:false,
		data:{time:new Date()},
		success:function(data){
			if(data.length>0){
				$(".messageTip .messageList").html("");
				for(var i=0;i<data.length;i++){
					var item = data[i];
					var htmlStr = msgTip.getTemplate(item.id,item.title,item.noticeType,item.url,item.extStr1,item.createDate);
					$(".messageTip .messageList").append(htmlStr);
				}
				msgTip.messageTip_lh=$(".messageTip").height()+ 10;
			}
		}
	});
}

/**
 * 获得模板
 */
msgTip.getTemplate = function(id,title,type,url,typeUrl,createDate){
	var titleStr = "["+type+"]"+title;
	if(titleStr.length>14){
		titleStr = titleStr.substring(0,14)+"……";
	}
	var result = '<li id="'+id+'">'+
        			'<div class="fl listWrap">'+
    					'<a href="javascript:void(0)" onclick="msgTip.clickFun(\''+id+'\',\''+url+'\',\''+typeUrl+'\')" title="'+title+'">'+titleStr+'</a>'+
    				'</div>'+
    				'<span>'+createDate+'<a class="accordion-toggle m-t-xs" onclick="msgTip.clickIgnore(\''+id+'\')">忽略</a></span>'+
    				'<div class="clearfix"></div>'+
    			'</li>';
	return result;
}

/**
 * 获得模板
 */
msgTip.clickFun = function(id,url,typeUrl){
	$.ajax({
		url:getRootPath()+url+"&readOnly=true",
		data:{time:new Date()},
		type:"get",
		success:function(data, textStatus){
			if(textStatus!="success"){
				msgBox.tip({
					content:"原始数据出错",
					type:"fail"
				});
			}
			//设置返回流程返回页面为空
			historyUrl.length=0;
			//加载页面
			loadrightmenu(url,'copyforurl',typeUrl);
			//设置已读
			var getData = {
					ids:id,
					time:new Date()
			}
			$.get(getRootPath()+"/sys/noticeMsg/readNoticeByIds.action",getData,function(){
				if($(".messageTip").find("li").length==1){
					$('.messageTip-close').trigger("click");
				}
				$(".messageTip").find("li[id='"+id+"']").remove();
				$('#__PostscriptIframeBodyCovere_info').css('height',$(".messageTip").height()+'px');
			});
		},error:function(){
			msgBox.tip({
				content:"原始数据出错",
				type:"fail"
			});
		}
	});
}

msgTip.clickIgnore = function(id){
			//设置已读
			var getData = {
					ids:id,
					time:new Date()
			}
			$.get(getRootPath()+"/sys/noticeMsg/readNoticeByIds.action",getData,function(){
				if($(".messageTip").find("li").length==1){
					$('.messageTip-close').trigger("click");
				}
				$(".messageTip").find("li[id='"+id+"']").remove();
			});
			
}

function messageToggle(obj){
    $(obj).parent().siblings().slideToggle(300);
    $(obj).find("i").toggleClass("fa-angle-up").toggleClass("fa-angle-down");
}

$(document).ready(function(){
	msgTip.init();
	$(".messageTip").show();
})

//信息提醒追加iframe
function setInfoInOffice(){
    var iframeBodyCover = document.createElement("iframe");
    iframeBodyCover.id = "__PostscriptIframeBodyCovere_info";
    iframeBodyCover.style.backgroundColor= "transparent";
    iframeBodyCover.style.cssText = "position: absolute; bottom: 0px; right: 0px; width: 380px; height: 328px;filter:alpha(opacity=0);";
    iframeBodyCover.setAttribute('frameborder', '0', 0);
    iframeBodyCover.src = "javascript:'';";
    document.body.appendChild(iframeBodyCover);
}
//清除信息提醒追加的iframe
function removeInfoInOffice(){
    $('#__PostscriptIframeBodyCovere_info').remove();
}