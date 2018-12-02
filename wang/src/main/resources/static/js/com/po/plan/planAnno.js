/**
 * 领导批注初始化方法
 */
plan.initAnno = function(dataid) {
	$.ajax({
		type:"GET",
		url:getRootPath() + "/po/plan/queryLeaderIdea.action",
		data:{"businessId":dataid},
			dataType : "json",
			success : function(data){
				if(data){
					var liStr = '';
					if (data.length == 0) {
						liStr += "<li class=\"clearfix m-b input-group\">" + 
								 "<p class=\"dialog-text input-group\" id=\"rootAnno\" name=\"rootAnno\">没有批注内容</p></li>";
					}
					var currentUser = $('#currentUser').val();//当前登录人
					for (var i = 0; i < data.length; i++) {
						 var annoParent = data[i];
						 liStr += "<li class=\"clearfix m-b input-group\">" + 
								  "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\"" + 
								  annoParent.id + "\">" + 
								  "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
								  annoParent.annoUserIdValue + "：</strong>" + annoParent.content + 
								  "<span>" + annoParent.annoDate + "</span></p>";
								 
						 liStr += "<div class=\"input-group-btn p-l\">" + 
						          "<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"anno"+i+"\" name=\"reply\">回 复</a></div>";
						 liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
		                          "<textarea name='replayAnno' rows=\"3\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+":"+"\"></textarea>"+
		                          "<div class=\"input-group-btn p-l\">"+
				                  "<input type=\"hidden\" name=\"byAnnoUserId\" value=\""+annoParent.annoUserId+"\">"+
				                  "<input type=\"hidden\" name=\"byAnnoUserDepid\" value=\""+annoParent.annoUserDepid+"\">"+
		                          " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
		                          "</div></div>";

						 // 批注回复
						 var replyList = annoParent.annoReplyList;
						 if(replyList){
							for(var j = 0; j < replyList.length; j++){
								var annoReply = replyList[j];
								liStr += "<div class=\"dis-row\">" + 
										 "<p class=\"dialog-text input-group\" data=\"" + 
										 annoReply.id + "\">" + "<input type=\"hidden\" name=\"\">" + 
										 "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
										 annoReply.annoUserIdValue + "回复" + annoReply.parentUserName + 
										 "：</strong>" + annoReply.content + 
										 "<span>" + annoReply.annoDate + 
										 "</span></p>";
								liStr += "<div class=\"input-group-btn p-l\">" + 
								         	"<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"reply"+j+"\" name=\"reply\">回 复</a></div>" +
								         "</div>" +
								         "<div class=\"hide hf-area\" for=\"reply"+j+"\">"+
								         "<textarea name='replayAnno' rows=\"3\" placeholder=\""+currentUser+"回复"+annoReply.annoUserIdValue+":"+"\"></textarea>"+
								         "<div class=\"input-group-btn p-l\">"+
						                 "<input type=\"hidden\" name=\"byAnnoUserId\" value=\""+annoParent.annoUserId+"\">"+
						                 "<input type=\"hidden\" name=\"byAnnoUserDepid\" value=\""+annoParent.annoUserDepid+"\">"+
								         " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
								         "</div></div>";
							}
						}
						liStr += "</li>";
					}
					$('#comment').append(liStr);
				}
			}
		});
};


/**
 * 领导批注初始化方法
 */
plan.initAnnoReadOnly = function(dataid) {
	$.ajax({
		type:"GET",
		url:getRootPath() + "/po/plan/queryLeaderIdea.action",
		data:{"businessId":dataid},
			dataType : "json",
			success : function(data){
				if(data){
					var liStr = '';
					if (data.length == 0) {
						liStr += "<li class=\"clearfix m-b input-group\">" + 
								 "<p class=\"dialog-text input-group\" id=\"rootAnno\" name=\"rootAnno\">没有批注内容</p></li>";
					}
					var currentUser = $('#currentUser').val();//当前登录人
					for (var i = 0; i < data.length; i++) {
						 var annoParent = data[i];
						 liStr += "<li class=\"clearfix m-b input-group\">" + 
								  "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\"" + 
								  annoParent.id + "\">" + 
								  "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
								  annoParent.annoUserIdValue + "：</strong>" + annoParent.content + 
								  "<span>" + annoParent.annoDate + "</span></p>";
						 
						 // 批注回复
						 var replyList = annoParent.annoReplyList;
						 if(replyList){
							for(var j = 0; j < replyList.length; j++){
								var annoReply = replyList[j];
								liStr += "<div class=\"dis-row\">" + 
										 "<p class=\"dialog-text input-group\" data=\"" + 
										 annoReply.id + "\">" + "<input type=\"hidden\" name=\"\">" + 
										 "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
										 annoReply.annoUserIdValue + "回复" + annoReply.parentUserName + 
										 "：</strong>" + annoReply.content + 
										 "<span>" + annoReply.annoDate + 
										 "</span></p>";
							}
						}
						liStr += "</li>";
					}
					$('#comment').append(liStr);
				}
			}
		});
};

/**
 * 领导批注保存方法
 */
plan.saveLeaderIdeaForm = function(planId) {
	//校验表单信息
	if ($("#leaderIdeaForm").valid()) {
		var url = getRootPath() + "/po/anno/save.action?time=" + new Date();
		// 将表单序列化成json格式
		var formData = $("#leaderIdeaForm").serializeArray();
		formData.push({"name":"businessId","value":planId});
		formData.push({"name":"annoType","value":0});
		formData.push({"name":"annoName","value":$("[itemname='planTitle']").html()});
		formData.push({"name":"byAnnoUserId","value":$('#applyId').val()});
		formData.push({"name":"byAnnoUserDepid","value":$('#applyDeptid').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data){
//				getToken();//更新token
				if (data.success == "true") {
					msgBox.tip({
						type : "success",
						content : data.successMessage,
						callback : function() {
							$('#leaderIdeaContent').val('');
							$('#comment').empty();
							plan.initAnno(planId);
						}
					});
				} else {
					if (data.labelErrorMessage) {
						showErrors("leaderIdeaForm", data.labelErrorMessage);
					} else {
						msgBox.info({
							type : "fail",
							content : data.errorMessage
						});
					}
				}
			},
			error : function() {
				plan.subState = false;
				msgBox.tip({
					type : "fail",
					content : $.i18n.prop("JC_SYS_002")
				});
			}
		});
	}
};


/**
 * 领导批注回复方法
 */
plan.commentReply = function(e) {
	var parentId = $(e).parent().prev().attr('data');
	var id = $(e).attr('id');
	$(e).toggle();//点击的回复按钮隐藏
	var $showArea = $(e).parents('li').find('div[class*="hf-area"][for="'+id+'"]');
	$showArea.find('a[name="send"]').attr('data',parentId);
	$showArea.find('textarea').val('');
	$showArea.toggle();//显示当前批注的回复文本域
	$('a[name="reply"]').not($(e)).show();//显示其他回复按钮
	$showArea.css("display","table-row");//添加样式
	$(e).parents('ul').find('div[class*="hf-area"]').not($showArea).hide();
};

/**
 * 领导批注发送方法
 */
plan.commentSend = function(e) {
	var businessId = $('#id').val();
	var annoParentId = $(e).attr('data');
	var byAnnoUserId = $(e).prevAll('input[name="byAnnoUserId"]').val();
	var byAnnoUserDepid = $(e).prevAll('input[name="byAnnoUserDepid"]').val();
	var annoName = $("[itemname=planTitle]").text();
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
	var content = $(e).parent().parent().find("textarea").val();
	
	if($("#leaderIdeaReplayForm").valid()){
		$.ajax({
			type : "POST",
			url : getRootPath() + "/po/plan/annoReply.action?time=" + new Date(),
			data : {
				"businessId" : businessId,
				"annoName" : annoName,
				"annoParentId" : annoParentId,
				"rootParentId" : rootParentId,
				"byAnnoUserId" : $('#applyId').val(),
				"byAnnoUserDepid" : $('#applyDeptid').val(),
				"content" : content
			},
			dataType : "json",
			success : function(data) {
				if (data.success == "true") {
					msgBox.tip({
						type : "success",
						content : data.successMessage,
						callback : function() {
							$('#comment').empty();
							plan.initAnno(businessId);
						}
					});
				} else {
					msgBox.info({
						type : "fail",
						content : data.errorMessage
					});
				}
			}
		});
	}
};