// 清空详细页各项内容
anno.clearDetailInfo = function() {
	$("#detailTitle").html("");
	$("#detailStartTime").html("");
	$("#detailPeriodTypeStartEndDate").html("");
	$("#detailShare").html("");
	$("#detailContent").html("");
	$("#detailRemind").html("");
	$("#comment1").empty();
};

anno.isLeaderDiary = function(sourceUser,targetUser,dataId){
	jQuery.ajax({
		url : getRootPath()+"/po/plan/isPlanLeader.action?time="+new Date(),
		type : 'GET',
		data : {"sourceUser": sourceUser,"targetUser":targetUser},
		success : function(data) {
			if(data.isLeader == "true"||sourceUser == targetUser){//当前登录人是计划申请人的上级或者当前登录人是计划申请人
				$('div[class="input-group-btn"]').show();//领导批注区域整体展示
				$("#leaderIdeaForm").show();//批注回复内容展示
			}else{
				$('div[class="input-group-btn"]').hide();//领导批注区域整体展示
				$("#leaderIdeaForm").hide();//批注回复内容展示
			}
		}
	});
}

// 根据日程id查询并显示该日程详细信息 用于详细信息和修改返显
anno.getDetailData = function(id) {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/po/diary/get.action?time=" + new Date(),
		data : {
			"id" : id
		},
		success : function(data) {
			if(data.id !=null){
				hideErrorMessage();// 清除验证信息
				anno.clearDetailInfo();
				//anno.getPeriodWay(data.periodType, data.periodWay);
				$("#detailTitle1").text(data.title);
				$("#detailStartTime").text(data.starttime);
				$("#detailEndTime").text(data.endtime);
				var period = "";
				var periodStart="";
				var periodEnd="";
				if(data.periodStartdate!=null&&data.periodStartdate!=""){
					periodStart=data.periodStartdate.substring(0,10);
				}
				if(data.periodEnddate!=null&&data.periodEnddate!=""){
					periodEnd=data.periodEnddate.substring(0,10);
				}
				switch (data.periodType) {
				case "0":
					period = "无定期";
					break;
				case "1":
					period = "从" + periodStart + " 至 "
							+ periodEnd + "的每一天";
					break;
				case "2":
					var str = data.periodWay.split(":")[1].split(",");
					var s = "";
					var weeks = "";
					for (var i = 0; i < str.length; i++) {
						switch (str[i]) {
						case "1":
							s = "周日";
							break;
						case "2":
							s = "周一";
							break;
						case "3":
							s = "周二";
							break;
						case "4":
							s = "周三";
							break;
						case "5":
							s = "周四";
							break;
						case "6":
							s = "周五";
							break;
						case "7":
							s = "周六";
							break;
						default:
							break;
						}
						weeks += s + ",";
					}
					weeks = weeks.substring(0, weeks.length - 1);
					period = "从" + periodStart + " 至 "
							+ periodEnd + "的每周" + weeks;
					break;
				case "3":
					var str = data.periodWay.split(":")[1];
					period = "从" + periodStart + " 至 "
							+ periodEnd + "的每月" + str + "日";
					break;
				case "4":
					var month = data.periodWay.split(":")[1];
					var m = parseInt(month) ;
					var day = data.periodWay.split(":")[2];
					period = "从" + periodStart + " 至 "
							+ periodEnd + "的每年" + m + "月" + day + "日";
					break;
				default:
					break;
				}
				$("#detailPeriodTypeStartEndDate").html(period);
				$("#detailShare").text(data.shareScopeIdValue);// 共享范围
				var content = data.content;
				if(content){
					content = data.content.replace(/\r\n/g, "<br/>");
				}
				$("#detailContent").html(content);
				//添加提醒设置 lihongyu 2014-10-21 start
				$("#tempRemind").val(data.remind);
				var detailRemind = "<input type='hidden' id='detailRemindReadonly' name='detailRemindReadonly'/>"
					+ "<a id='detailRemindSet' name='detailRemindSet' class='a-icon i-new m-r-xs' href='#' role='button' data-toggle='modal'>"
					+ "提醒设置</a>";
					$("#detailRemind").html(detailRemind);//提醒
			    //添加提醒设置 lihongyu 2014-10-21 end
				$("#userid").val(data.possessorId);
				var targetUser = $('#currentUserId').val();//当前登录人
				var sourceUser = data.createUser;
				anno.initDiaryAnno(data.id);
				anno.isLeaderDiary(sourceUser,targetUser,data.id);
				$("#annoTokenAnno").val(data.annoTokenAnno);
				$('#Schedule-detail').modal('show');
				// 更新阅读情况
				$.ajax({
					type : "GET",
					url : getRootPath() + "/po/diary/savaReadingStatus.action",
					data : {
						'id' : data.id,
						'createUser' : data.createUser
					},
					dataType : "json",
					success : function(data) {
					}
				});
			}else{
				msgBox.tip({
					type : "fail",
					content : $.i18n.prop("JC_SYS_128")
				});
			}
		},error:function(data){
			msgBox.tip({
				type : "fail",
				content : $.i18n.prop("JC_SYS_128")
			});
		}
	});
};

///** 批注回复 */
//anno.commentReply = function(e) {
//	var parentId = $(e).parent().prev().attr('data');
//	var id = $(e).attr('id');
//	$(e).toggle();//点击的回复按钮隐藏
//	var $showArea = $(e).parents('li').find('div[class*="hf-area"][for="'+id+'"]');
//	$showArea.find('a[name="send"]').attr('data',parentId);
//	$showArea.find('textarea').val('');
//	$showArea.toggle();//显示当前批注的回复文本域
//	$('a[name="reply"]').not($(e)).show();//显示其他回复按钮
//	$showArea.css("display","table-row");//添加样式
//	$(e).parents('ul').find('div[class*="hf-area"]').not($showArea).hide();
//};

/** 批注发送 */
anno.commentDiarySend = function(e) {
	var businessId = $('#diaryId').val();
	var annoParentId = $(e).attr('data');
	var rootParentId = $(e).parents('li').find('p[name="rootAnno"]').attr('data');
	//var content = $(e).parent().prev('textarea').val();
	var content = $(e).parent().parent().find("textarea").val();
	iePlaceholderPatch();
	if(!DeleteCascade.syncCheckExist("diaryAnno",businessId)){
//if(!DeleteCascade.syncCheckExist("workLogAnno",businessId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		anno.subState = false;
		return;
	}
	if($('#commentForm1').valid()){
		$.ajax({
			type : "POST",
			url : getRootPath() + "/po/diary/annoReply.action?time=" + new Date(),
			data : {
				"businessId" : businessId,
				"annoParentId" : annoParentId,
				"rootParentId" : rootParentId,
				"content" : content,
				"byAnnoUserId":$("#userid").val(),
				"annoName":$("#detailTitle1").html()
			},
			dataType : "json",
			success : function(data) {
				if (data.success == "true") {
					msgBox.tip({
						type : "success",
						content : data.successMessage,
						callback : function() {
							$('#comment1').empty();
							anno.initDiaryAnno(businessId);
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
		anno.subState = false;//更新重复提交状态标识
		msgBox.info({
			content:$.i18n.prop("JC_SYS_118"),
			success:function(){
//				fnCall();
			}
		});
	}
};

/** 保存领导批注 */
anno.saveLeaderIdeaForm = function() {
	var dataId = $('#diaryId').val();
	if (dataId == '')
		return;
	// 校验是否重复提交
	if (anno.subState)
		return;
	anno.subState = true;
	// 校验表单信息
	if(!DeleteCascade.syncCheckExist("diaryAnno",dataId)){
//	if(!DeleteCascade.syncCheckExist("workLogAnno",dataId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		anno.subState = false;
		return;
	}
	if ($("#leaderIdeaForm").valid()) {
		var url = getRootPath() + "/po/anno/save.action?time=" + new Date();
		// 将表单序列化成json格式
		var formData = $("#leaderIdeaForm").serializeArray();
		formData.push({
			"name" : "businessId",
			"value" : dataId
		});
		formData.push({
			"name" : "annoType",
			"value" : 1
		});
		formData.push({
			"name" : "annoName",
			"value" : $("#detailTitle1").text()
		});
		formData.push({
			"name" : "byAnnoUserId",
			"value" : $("#userid").val()
		});
		formData.push({
			"name" : "token",
			"value" : $("#annoTokenAnno").val()
		});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				anno.subState = false;// 更新重复提交状态标识
//				getToken();// 更新token
				if (data.success == "true") {
					msgBox.tip({
						type : "success",
						content : data.successMessage,
						callback : function() {
							$('#leaderIdeaContent').val('');
							$('#comment1').empty();
							anno.initDiaryAnno(dataId);
//							$("#token").val(data.token);
							$("#annoTokenAnno").val(data.token);
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
					$("#annoTokenAnno").val(data.token);
				}
			},
			error : function() {
				anno.subState = false;
				msgBox.tip({
					type : "fail",
					content : $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		anno.subState = false;
		msgBox.info({
			type : "fail",
			content : $.i18n.prop("JC_SYS_118")
		});
	}
};
//提醒设置(仅回显)
anno.showRemindReadonly= function(){
	$(document).showRemind_({
		tableName:'TTY_PO_DIARY',
		readonly:true,
		remind:$("#tempRemind").val(),
		callback:function(r) {
			para_week = r;
			$("#detailRemindReadonly").val(r);
		}
	});
};
/** 初始化批注列表 */
anno.initDiaryAnno = function(dataid) {
	$.ajax({
				type : "GET",
				url : getRootPath() + "/po/diary/queryAnno.action",
				data : {
					"businessId" : dataid
				},
				async : false,
				dataType : "json",
				success : function(data) {
					if (data) {
						var liStr ='';
						if(data.length==0){
							liStr +="<li class=\"clearfix m-b input-group\">"+
				                    "<p class=\"dialog-text input-group\" name=\"rootAnno\">没有批注内容</p></li>";
						}
						var currentUser = $('#currentUser').val();
						for(var i=0;i<data.length;i++){
							
							var annoParent = data[i];
							liStr += "<li class=\"clearfix m-b input-group\">"+
					                    "<p class=\"dialog-text input-group\" name=\"rootAnno\" data=\""+annoParent.id+"\">"+
					                    "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>"+annoParent.annoUserIdValue+"：</strong>"+annoParent.content+
					                    "<span>"+annoParent.annoDate+"</span>"+
						                "</p>"+
						                "<div class=\"input-group-btn\">"+
						                    "<a href=\"#\" class=\"a-icon i-new btn-hf\" name=\"reply\" id=\"anno"+i+"\">回 复</a>"+
						                "</div>";
							liStr += "<div class=\"hide hf-area\" for=\"anno"+i+"\">"+
						                 "<textarea rows=\"3\" name=\"replyContent\" placeholder=\""+currentUser+"回复"+annoParent.annoUserIdValue+":\"></textarea>"+
						                  "<div class=\"input-group-btn p-l\">"+
						                   " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
						                  "</div>"+
						                "</div>";
							               
							                    
							                
							
							//批注回复
							var replyList = annoParent.annoReplyList;
							if(replyList){
								for(var j=0;j<replyList.length;j++){
									var annoReply = replyList[j];
									liStr += "<div class=\"dis-row\">"+
							                    "<p class=\"dialog-text input-group\" data=\""+annoReply.id+"\">"+
							                    	"<input type=\"hidden\" name=\"\">"+
							                        "<i class=\"fa fa-comment input-group-btn p-l\"></i><strong>"+annoReply.annoUserIdValue+"回复"+annoReply.parentUserName+"：</strong>"+annoReply.content+
							                        "<span>"+annoReply.annoDate+"</span>"+
							                   "</p>"+
							                    "<div class=\"input-group-btn\">"+
							                        "<a href=\"#\" class=\"a-icon i-new btn-hf\" name=\"reply\" id=\"reply"+j+"\">回 复</a>"+
							                    "</div>"+
						                    "</div>"+
									
											"<div class=\"hide hf-area\" for=\"reply"+j+"\">"+
							                 "<textarea rows=\"3\" name=\"replyContent\" placeholder=\""+currentUser+"回复"+annoReply.annoUserIdValue+":\"></textarea>"+
							                  "<div class=\"input-group-btn p-l\">"+
							                   " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
							                  "</div>"+
							                "</div>";
								}
							}
							liStr += /*"<div class=\"hide hf-area\">"+
						                 "<textarea rows=\"3\"></textarea>"+
						                  "<div class=\"input-group-btn\">"+
						                   " <a href=\"#\" class=\"a-icon i-new\" name=\"send\">提交回复</a>"+
						                  "</div>"+
						                "</div>"+*/
						            "</li>";
						}
						$('#comment1').append(liStr);
					}
				}
			});
};