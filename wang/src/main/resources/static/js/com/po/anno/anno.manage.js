var annoManage={};
/**重复提交标识*/
annoManage.subState = false;
/**
 * 领导批注初始化方法
 */
annoManage.initAnno = function(dataid) {
	$.ajax({
		type:"GET",
		url : getRootPath()+"/po/workTask/queryAnno.action",
		data:{"businessId":dataid},
			dataType : "json",
			success : function(data){
				if(data){
					var liStr = '';
					if (data.length == 0) {
						liStr += "<li class=\"clearfix m-b input-group\">" + 
								 "<p class=\"dialog-text input-group\" id=\"rootAnno\" name=\"rootAnno\">没有批注内容</p></li>";
					}
					var currentUser = $('#displayName').val();//当前登录人
					for (var i = 0; i < data.length; i++) {
						 var annoParent = data[i];
						 liStr += "<li class=\"clearfix m-b input-group\">" + 
								  "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\"" + 
								  annoParent.id + "\">" + 
								  "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>" + 
								  annoParent.annoUserIdValue + "：</strong>" + annoParent.content + 
								  "<span>" + annoParent.annoDate + "</span></p>";
								 
						 liStr += "<div class=\"input-group-btn  p-l\">" + 
						          "<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"anno"+i+"\" name=\"reply\">回 复</a></div>";
						 liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
		                          "<textarea name=\"replayAnno\" rows=\"3\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+":\"></textarea>"+
		                          "<div class=\"input-group-btn  p-l\">"+
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
										 "<i class=\"fa fa-comment input-group-btn  p-l\"></i><strong>" + 
										 annoReply.annoUserIdValue + "回复" + annoReply.parentUserName + 
										 "：</strong>" + annoReply.content + 
										 "<span>" + annoReply.annoDate + 
										 "</span></p>";
								liStr += "<div class=\"input-group-btn  p-l\">" + 
								         	"<a href=\"#\" class=\"a-icon i-new btn-hf\" id=\"reply"+j+"\" name=\"reply\">回 复</a></div>" +
								         "</div>" +
								         "<div class=\"hide hf-area\" for=\"reply"+j+"\">"+
								         "<textarea name=\"replayAnno\" rows=\"3\" placeholder=\""+currentUser+"回复"+annoReply.annoUserIdValue+":\"></textarea>"+
								         "<div class=\"input-group-btn p-l\">"+
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
 * 领导批注回复方法
 */
annoManage.commentReply = function(e) {
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
	iePlaceholderPatch();
};

/**
 * 领导批注发送方法(回复)
 */
annoManage.commentSend = function(e,sta) {
	//校验是否重复提交
	if (annoManage.subState)return;
	annoManage.subState = true;
	var businessId = taskId;
	var annoParentId = $(e).attr('data');
	var byAnnoUserId = $('#taskDirectorId').val();
	var byAnnoUserDepid = $('#annoCreateUserDept').val(); 
	var annoName = tTaskName;
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
	var content = $(e).parent().parent().find("textarea").val();
	if(sta==3){
		if ($("#leaderIdeaReplayForm").valid()) {
			$.ajax({
				type : "POST",
				url : getRootPath()+"/po/workTask/annoReply.action?time=" + new Date(),
				data : {
					"businessId" : businessId,
					"annoName" : annoName,
					"annoParentId" : annoParentId,
					"rootParentId" : rootParentId,
					"byAnnoUserId" : byAnnoUserId,
					"byAnnoUserDepid" : byAnnoUserDepid,
					"content" : content
				},
				dataType : "json",
				success : function(data) {
					annoManage.subState = false;//更新重复提交状态标识
					if (data.success == "true") {
						msgBox.tip({
							type : "success",
							content : data.successMessage,
							callback : function() {
								$('#comment').empty();
								annoManage.initAnno(businessId);
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
		}else {
			annoManage.subState = false;//更新重复提交状态标识
			msgBox.info({
				content:$.i18n.prop("JC_SYS_067"),
				success:function(){
					fnCall();
				}
			});
		}
	}else{
		if(!DeleteCascade.syncCheckExist('workTaskAnno',businessId)){
			msgBox.info({
				content: $.i18n.prop("JC_OA_COMMON_015")
			});
			annoManage.subState = false;//更新重复提交状态标识
			return;
		}else{
			if ($("#leaderIdeaReplayForm").valid()) {
				$.ajax({
					type : "POST",
					url : getRootPath()+"/po/workTask/annoReply.action?time=" + new Date(),
					data : {
						"businessId" : businessId,
						"annoName" : annoName,
						"annoParentId" : annoParentId,
						"rootParentId" : rootParentId,
						"byAnnoUserId" : byAnnoUserId,
						"byAnnoUserDepid" : byAnnoUserDepid,
						"content" : content
					},
					dataType : "json",
					success : function(data) {
						annoManage.subState = false;//更新重复提交状态标识
						if (data.success == "true") {
							msgBox.tip({
								type : "success",
								content : data.successMessage,
								callback : function() {
									$('#comment').empty();
									annoManage.initAnno(businessId);
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
			}else {
				annoManage.subState = false;//更新重复提交状态标识
				msgBox.info({
					content:$.i18n.prop("JC_SYS_067"),
					success:function(){
						fnCall();
					}
				});
			}
		}
	}
};
/**保存领导批注*/
annoManage.saveLeaderIdeaForm = function(sta){
	var worklogId = taskId;
	if(worklogId=='')return;
	//校验是否重复提交
	if (annoManage.subState)return;
	annoManage.subState = true;
	if(sta==3){
		if ($("#leaderIdeaForm").valid()) {
			var url = getRootPath()+"/po/anno/save.action?time=" + new Date();
			//将表单序列化成json格式
			var formData = $("#leaderIdeaForm").serializeArray();
			formData.push({"name": "businessId", "value": worklogId});
			formData.push({"name": "annoType", "value": 3});
			formData.push({"name": "annoName", "value": tTaskName});
			formData.push({"name":"byAnnoUserId","value":$('#taskDirectorId').val()});
			formData.push({"name":"byAnnoUserDepid","value":$('#annoCreateUserDept').val()});
			formData.push({"name": "token", "value": $('#token').val()});//添加 token 校验
			jQuery.ajax({
				url : url,
				type : 'POST',
				data : formData,
				success : function(data) {
					annoManage.subState = false;//更新重复提交状态标识
//					getToken();//更新token
					if(data.success == "true"){
						msgBox.tip({
							type:"success",
							content: data.successMessage
						});
						$('#leaderIdeaContent').val('');
						$('#comment').empty();
						annoManage.initAnno(worklogId);
						$("#token").val(data.token);
					} else {
						if(data.labelErrorMessage){
							showErrors("leaderIdeaForm", data.labelErrorMessage);
						} else{
							msgBox.info({
								content: data.errorMessage
							});
						} 
						$("#token").val(data.token);
					}
				},
				error : function() {
					annoManage.subState = false;//更新重复提交状态标识
					msgBox.tip({
						content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
					});
				}
			});
		} else {
			annoManage.subState = false;//更新重复提交状态标识
			msgBox.info({
				content:$.i18n.prop("JC_SYS_067"),
				success:function(){
					fnCall();
				}
			});
		}
	}else{
		//校验表单信息
		if(!DeleteCascade.syncCheckExist('workTaskAnno',worklogId)){
			msgBox.info({
				content: $.i18n.prop("JC_OA_COMMON_015")
			});
			annoManage.subState = false;//更新重复提交状态标识
			return;
		}else{
			if ($("#leaderIdeaForm").valid()) {
				var url = getRootPath()+"/po/anno/save.action?time=" + new Date();
				//将表单序列化成json格式
				var formData = $("#leaderIdeaForm").serializeArray();
				formData.push({"name": "businessId", "value": worklogId});
				formData.push({"name": "annoType", "value": 3});
				formData.push({"name": "annoName", "value": tTaskName});
				formData.push({"name":"byAnnoUserId","value":$('#taskDirectorId').val()});
				formData.push({"name":"byAnnoUserDepid","value":$('#annoCreateUserDept').val()});
				formData.push({"name": "token", "value": $('#token').val()});//添加 token 校验
				jQuery.ajax({
					url : url,
					type : 'POST',
					data : formData,
					success : function(data) {
						annoManage.subState = false;//更新重复提交状态标识
//						getToken();//更新token
						if(data.success == "true"){
							msgBox.tip({
								type:"success",
								content: data.successMessage
							});
							$('#leaderIdeaContent').val('');
							$('#comment').empty();
							annoManage.initAnno(worklogId);
							$("#token").val(data.token);
						} else {
							if(data.labelErrorMessage){
								showErrors("leaderIdeaForm", data.labelErrorMessage);
							} else{
								msgBox.info({
									content: data.errorMessage
								});
							} 
							$("#token").val(data.token);
						}
					},
					error : function() {
						annoManage.subState = false;//更新重复提交状态标识
						msgBox.tip({
							content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
						});
					}
				});
			} else {
				annoManage.subState = false;//更新重复提交状态标识
				msgBox.info({
					content:$.i18n.prop("JC_SYS_067"),
					success:function(){
						fnCall();
					}
				});
			}
		}
	}
};
//领导批注框清空
annoManage.clearleaderIdea=function(){
	$("#leaderIdeaContent").val("");
};
/**
 * 领导批注初始化方法(只读)
 */
annoManage.initAnnoReadOnly = function(dataid) {
	$.ajax({
		type:"GET",
		url : getRootPath()+"/po/workTask/queryAnno.action",
		data:{"businessId":dataid},
			dataType : "json",
			success : function(data){
				if(data){
					var liStr = '';
					if (data.length == 0) {
						liStr += "<li class=\"clearfix m-b input-group\">" + 
								 "<p class=\"dialog-text input-group\" id=\"rootAnno\" name=\"rootAnno\">没有批注内容</p></li>";
					}
					var currentUser = $('#displayName').val();//当前登录人
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
										 "<i class=\"fa fa-comment input-group-btn  p-l\"></i><strong>" + 
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