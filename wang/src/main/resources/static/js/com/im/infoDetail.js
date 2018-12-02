infoDetail = {};

/**对信息进行评论*/
infoDetail.reviewSend = function(){
	if($("#reviewForm").valid()){
		var formData = $("#reviewForm").serializeArray();
		formData.push({
			"name" : "token",
			"value" : $("#detailToken").val()
		});
		jQuery.ajax({
			url : getRootPath() + "/im/review/save.action",
			async : false,
			type : 'POST',
			data : formData,
			success : function(data) {
//				getToken();
				$("#detailToken").val(data.detailToken)
				if (data.success == "true") {
					msgBox.tip({
						type:"success",
						content:data.successMessage,
						callback:infoDetail.initReview()
					});
				} else {
					msgBox.info({
						type:"fail",
						content: $.i18n.prop("JC_OA_IM_004")
					});
				}
				$('#reviewForm')[0].reset();
			},
			error : function() {
				msgBox.info({
					type:"fail",
					//---xuweiping 修改bug4051提示错误信息乱码--begin
					//content:jQuery.validator.format($.i18n.prop("JC_OA_IM_004"))
					content:$.i18n.prop("JC_OA_IM_004")
					//----end---
				});
			}
		});
	}
};

/**详细页跳转方法*/
infoDetail.infoDetail = function(infoId){
	var type = $('#type').val();
	var menuFlag=$('#menuFlag').val();
//	if(type==1){//我的信息
	window.location.href=getRootPath() +"/im/info/infoDetail.action?id="+infoId+"&type="+type+"&menuFlag="+menuFlag+"&time=" + new Date();
//	}
	
};

/**初始化评论列表*/
infoDetail.initReview = function(){
	var infoId = $('#infoId').val();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/im/review/queryAll.action?time="+new Date(),
		data : {"infoId":infoId},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data) {
				var liStr='';
				if(data.length==0){
					liStr +="<li class=\"clearfix m-b input-group\">"+
		                    "<p class=\"dialog-text input-group\" name=\"rootAnno\">没有匹配的内容</p></li>";
				}
				for(var i=0;i<data.length;i++){
					var review = data[i];
					liStr += "<li class=\"clearfix m-b input-group\">"+
				                    "<p class=\"dialog-text input-group\">"+
				                    "<i class=\"fa fa-comment input-group-btn p-r\"></i><strong>"+review.reviewName+"：</strong>"+review.reviewContent+
				                    "<span>"+review.reviewTime+"</span>"+
					                "</p>"+
					          "</li>";
				}
//				$('#review').empty();
//				$('#review').append(liStr);
				$('#review').html(liStr);
			}
		}
	});
};

/**更新信息阅读状态*/
infoDetail.reading = function(id){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/im/infoRead/save.action?time=" + new Date(),
		data : {'infoId':id},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				
			}
		}
	});
};

/**
 * 初始化方法
 */
jQuery(function($){
//	getToken();
	//绑定提交评论
	$('#reviewSend').click(function(){
		infoDetail.reviewSend();
	});
	infoDetail.initReview();
	
	infoDetail.reading($('#infoId').val());
	//返回顶部
	$("#backtotop").click(function(){
		$("#scrollable").animate({ scrollTop: 0 }, 120);
		return false;
	});
	var url = getRootPath()+"/im/info/infoContentDetail.action?id="+$('#infoId').val()+"&time=" + new Date();
//	var curWwwPath=window.document.location.href;
//    var pathName=window.document.location.pathname;
//    var pos=curWwwPath.indexOf(pathName);
//    var localhostPaht=curWwwPath.substring(0,pos);
	$('#infoContent').attr('src',url);
	//设置附件数据
	$('#businessId').val($('#infoId').val());
	$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
	showAttachList();//附件弹出层数据回显方法
	echoAttachList();//附件列表数据回显方法
	$("[data-toggle=tooltip]").tooltip();
});
//@sourceURL=infoDetail.js