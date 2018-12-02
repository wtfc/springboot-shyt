var taskArra = {};
var currentUserId=document.getElementById("currentUserId").value;
taskArra.pageRows = null;
var para_week=null;//提醒设置
taskArra.status=0;//用于限制暂存重复提交
//重复提交标识
taskArra.subState = false;
var isSave=0;//用于超期提醒设置预览
//清空表单
taskArra.clearForm = function () {
	$('#zdsz')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("sponsorName-sponsorId");
	selectControl.clearValue("userName-directorId");
	selectControl.clearValue("prticipantsName-prticipantsId");
};
//人员选择树--负责人
selectControl.init("showDirectorIdTree","userName-directorId", false, true);
//人员选择树--参与人
selectControl.init("prticipantsTree","prticipantsName-prticipantsId", true, true);
//人员选择树--申请人
selectControl.init("showapplyNameTree","senderText-applyId", false, true);
//暂存模板
taskArra.tempAddTask = function (callMothed) {
	if (taskArra.subState)return;
	taskArra.subState = true;
	//暂存模板时，将隐藏变量赋空，防止当暂存任务保存模板时出现问题 start
	$("#taskId").val("");
	$("#statusArr").val("");
	//暂存模板时，将隐藏变量赋空，防止当暂存任务保存模板时出现问题 end
	$("#status").val("5");
	if ($("#zdsz").valid()) {
		var re=taskArra.compareStaAndEnd();//汇报时限天数校验
		if(re==1){
			msgBox.info({
				type:"fail",
				content: $.i18n.prop("JC_OA_PO_044")
			});
			taskArra.subState = false;
			return;
		}
		taskArra.remindDefault();
		var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
		var formData = $("#zdsz").serializeArray();
		formData.push({"name": "remindContent", "value": $("#remindContent").val()});
		formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
		formData.push({"name": "token", "value": $("#token").val()}); //添加 token校验
		taskArra.addFormParameters(formData);
		if(callMothed!=""){//用于其他功能页面调用本页面时，关闭实际的DIV窗口
			jQuery.ajax({
				url : url,
				type : 'POST',
				cache: false,
				contentType: "application/json;charset=UTF-8",
				data : JSON.stringify(serializeJson(formData),replaceJsonNull),
				success : function(data) {
					taskArra.subState = false;
					if(data.success == "true"){
						callMothed();
					} else {
						if(data.labelErrorMessage){
							msgBox.info({
								type:"fail",
								content: data.labelErrorMessage
							});
						}else {
							msgBox.info({
								type:"fail",
								content: data.errorMessage
							});
						}
					}
				},
				error : function() {
					taskArra.subState = false;
					msgBox.tip({
						type:"fail",
						content: $.i18n.prop("JC_SYS_055")
					});
				}
			});
		}else{
			jQuery.ajax({
				url : url,
				type : 'POST',
				cache: false,
				contentType: "application/json;charset=UTF-8",
				data : JSON.stringify(serializeJson(formData),replaceJsonNull),
				success : function(data) {
					taskArra.subState = false;
					$("#token").val(data.token);
					if(data.success == "true"){
						msgBox.tip({
							type:"success",
							content: data.successMessage
						});
					} else {
						if(data.labelErrorMessage){
							msgBox.info({
								type:"fail",
								content: data.labelErrorMessage
							});
						}else {
							msgBox.info({
								type:"fail",
								content: data.errorMessage
							});
						}
					}
				},
				error : function() {
					taskArra.subState = false;
					msgBox.tip({
						type:"fail",
						content: $.i18n.prop("JC_SYS_055")
					});
				}
			});
		}
	} else {
		taskArra.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//暂存
taskArra.tempAddWorkTask = function () {
	if (taskArra.subState)return;
	taskArra.subState = true;
	$("#status").val("8");
	if ($("#zdsz").valid()) {
		var re=taskArra.compareStaAndEnd();//汇报时限天数校验
		if(re==1){
			msgBox.info({
				type:"fail",
				content: $.i18n.prop("JC_OA_PO_044")
			});
			taskArra.subState = false;
			return;
		}
		taskArra.remindDefault();
		var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
		var formData = $("#zdsz").serializeArray();
		formData.push({"name": "remindContent", "value": $("#remindContent").val()});
		formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
		formData.push({"name": "token", "value": $("#token").val()}); //添加 token校验
		taskArra.addFormParameters(formData);
			jQuery.ajax({
				url : url,
				type : 'POST',
				cache: false,
				contentType: "application/json;charset=UTF-8",
				data : JSON.stringify(serializeJson(formData),replaceJsonNull),
				success : function(data) {
					taskArra.subState = false;
					$("#token").val(data.token);
					if(data.success == "true"){
						taskArra.successCallBack();
//						msgBox.tip({
//							type:"success",
//							content: data.successMessage
//						});
					} else {
						if(data.labelErrorMessage){
							msgBox.info({
								type:"fail",
								content: data.labelErrorMessage
							});
						}else {
							msgBox.info({
								type:"fail",
								content: data.errorMessage
							});
						}
					}
				},
				error : function() {
					taskArra.subState = false;
					msgBox.tip({
						type:"fail",
						content: $.i18n.prop("JC_SYS_055")
					});
				}
			});
	} else {
		taskArra.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//保存
taskArra.addOrTempTask = function (callMothed) {
	if (taskArra.subState)return;
	taskArra.subState = true;
	$("#status").val("0");
	if ($("#zdsz").valid()) {
		var re=taskArra.compareStaAndEnd();//汇报时限天数校验
		if(re==1){
			msgBox.info({
				type:"fail",
				content: $.i18n.prop("JC_OA_PO_044")
			});
			taskArra.subState = false;
			return;
		}
		taskArra.remindDefault();
		//暂存任务保存时，查询其是否存在父任务 start
		if(taskArra.getParentTaskForZC($('#taskId').val())==1){
			var opt={
					content: $.i18n.prop("JC_OA_PO_055"),
					success: function(){
						if(taskArra.isRemind()==false){
							var opt={
							    content: $.i18n.prop("JC_OA_PO_034"),
								success: function(){
									var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
									var formData = $("#zdsz").serializeArray();
									formData.push({"name": "remindContent", "value": $("#remindContent").val()});
									formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
									formData.push({"name": "token", "value": $("#token").val()}); //添加 token校验
									taskArra.addFormParameters(formData);
									if(callMothed!=""){//用于其他功能页面调用本页面时，关闭实际的DIV窗口
										jQuery.ajax({
											url : url,
											type : 'POST',
											cache: false,
											contentType: "application/json;charset=UTF-8",
											data : JSON.stringify(serializeJson(formData),replaceJsonNull),
											success : function(data) {
												taskArra.subState = false;
												if(data.success == "true"){
													callMothed();
												} else {
													if(data.labelErrorMessage){
														msgBox.info({
															type:"fail",
															content: data.labelErrorMessage
														});
													}else {
														msgBox.info({
															type:"fail",
															content: data.errorMessage
														});
													}
												}
											},
											error : function() {
												taskArra.subState = false;
												msgBox.tip({
													type:"fail",
													content: $.i18n.prop("JC_SYS_055")
												});
											}
										});
									}else{
										jQuery.ajax({
											url : url,
											type : 'POST',
											cache: false,
											contentType: "application/json;charset=UTF-8",
											data : JSON.stringify(serializeJson(formData),replaceJsonNull),
											success : function(data) {
												$("#token").val(data.token);
												taskArra.subState = false;
												if(data.success == "true"){
													taskArra.successCallBack();
												} else {
													if(data.labelErrorMessage){
														msgBox.info({
															type:"fail",
															content: data.labelErrorMessage
														});
													}else {
														msgBox.info({
															type:"fail",
															content: data.errorMessage
														});
													}
												}
											},
											error : function() {
												taskArra.subState = false;
												msgBox.tip({
													type:"fail",
													content: $.i18n.prop("JC_SYS_055")
												});
											}
										});
									}	
								},
								cancel:function(){
									taskArra.subState = false;
								}
							}
							msgBox.confirm(opt);
						}else{
							var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
							var formData = $("#zdsz").serializeArray();
							formData.push({"name": "remindContent", "value": $("#remindContent").val()});
							formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
							formData.push({"name": "token", "value": $("#token").val()}); //添加 token校验
							taskArra.addFormParameters(formData);
							if(callMothed!=""){//用于其他功能页面调用本页面时，关闭实际的DIV窗口
								jQuery.ajax({
									url : url,
									type : 'POST',
									cache: false,
									contentType: "application/json;charset=UTF-8",
									data : JSON.stringify(serializeJson(formData),replaceJsonNull),
									success : function(data) {
										$("#token").val(data.token);
										taskArra.subState = false;
										if(data.success == "true"){
											callMothed();
										} else {
											if(data.labelErrorMessage){
												msgBox.info({
													type:"fail",
													content: data.labelErrorMessage
												});
											}else {
												msgBox.info({
													type:"fail",
													content: data.errorMessage
												});
											}
										}
									},
									error : function() {
										taskArra.subState = false;
										msgBox.tip({
											type:"fail",
											content: $.i18n.prop("JC_SYS_055")
										});
									}
								});
							}else{
								jQuery.ajax({
									url : url,
									type : 'POST',
									cache: false,
									contentType: "application/json;charset=UTF-8",
									data : JSON.stringify(serializeJson(formData),replaceJsonNull),
									success : function(data) {
										$("#token").val(data.token);
										taskArra.subState = false;
										if(data.success == "true"){
											taskArra.successCallBack();
										} else {
											if(data.labelErrorMessage){
												msgBox.info({
													type:"fail",
													content: data.labelErrorMessage
												});
											}else {
												msgBox.info({
													type:"fail",
													content: data.errorMessage
												});
											}
										}
									},
									error : function() {
										taskArra.subState = false;
										msgBox.tip({
											type:"fail",
											content: $.i18n.prop("JC_SYS_055")
										});
									}
								});
							}
						}
					},
					cancel:function(){
						taskArra.subState = false;
					}
			}
			msgBox.confirm(opt);
		}
		//暂存任务保存时，查询其是否存在父任务 end
		else{
			if(taskArra.isRemind()==false){
				var opt={
				    content: $.i18n.prop("JC_OA_PO_034"),
					success: function(){
						var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
						var formData = $("#zdsz").serializeArray();
						formData.push({"name": "remindContent", "value": $("#remindContent").val()});
						formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
						formData.push({"name": "token", "value": $("#token").val()}); //添加 token校验
						taskArra.addFormParameters(formData);
						if(callMothed!=""){//用于其他功能页面调用本页面时，关闭实际的DIV窗口
							jQuery.ajax({
								url : url,
								type : 'POST',
								cache: false,
								contentType: "application/json;charset=UTF-8",
								data : JSON.stringify(serializeJson(formData),replaceJsonNull),
								success : function(data) {
									taskArra.subState = false;
									if(data.success == "true"){
										callMothed();
									} else {
										if(data.labelErrorMessage){
											msgBox.info({
												type:"fail",
												content: data.labelErrorMessage
											});
										}else {
											msgBox.info({
												type:"fail",
												content: data.errorMessage
											});
										}
									}
								},
								error : function() {
									taskArra.subState = false;
									msgBox.tip({
										type:"fail",
										content: $.i18n.prop("JC_SYS_055")
									});
								}
							});
						}else{
							jQuery.ajax({
								url : url,
								type : 'POST',
								cache: false,
								contentType: "application/json;charset=UTF-8",
								data : JSON.stringify(serializeJson(formData),replaceJsonNull),
								success : function(data) {
									$("#token").val(data.token);
									taskArra.subState = false;
									if(data.success == "true"){
										taskArra.successCallBack();
									} else {
										if(data.labelErrorMessage){
											msgBox.info({
												type:"fail",
												content: data.labelErrorMessage
											});
										}else {
											msgBox.info({
												type:"fail",
												content: data.errorMessage
											});
										}
									}
								},
								error : function() {
									taskArra.subState = false;
									msgBox.tip({
										type:"fail",
										content: $.i18n.prop("JC_SYS_055")
									});
								}
							});
						}	
					},
					cancel:function(){
						taskArra.subState = false;
					}
				}
				msgBox.confirm(opt);
			}else{
				var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
				var formData = $("#zdsz").serializeArray();
				formData.push({"name": "remindContent", "value": $("#remindContent").val()});
				formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
				formData.push({"name": "token", "value": $("#token").val()}); //添加 token校验
				taskArra.addFormParameters(formData);
				if(callMothed!=""){//用于其他功能页面调用本页面时，关闭实际的DIV窗口
					jQuery.ajax({
						url : url,
						type : 'POST',
						cache: false,
						contentType: "application/json;charset=UTF-8",
						data : JSON.stringify(serializeJson(formData),replaceJsonNull),
						success : function(data) {
							$("#token").val(data.token);
							taskArra.subState = false;
							if(data.success == "true"){
								callMothed();
							} else {
								if(data.labelErrorMessage){
									msgBox.info({
										type:"fail",
										content: data.labelErrorMessage
									});
								}else {
									msgBox.info({
										type:"fail",
										content: data.errorMessage
									});
								}
							}
						},
						error : function() {
							taskArra.subState = false;
							msgBox.tip({
								type:"fail",
								content: $.i18n.prop("JC_SYS_055")
							});
						}
					});
				}else{
					jQuery.ajax({
						url : url,
						type : 'POST',
						cache: false,
						contentType: "application/json;charset=UTF-8",
						data : JSON.stringify(serializeJson(formData),replaceJsonNull),
						success : function(data) {
							$("#token").val(data.token);
							taskArra.subState = false;
							if(data.success == "true"){
								taskArra.successCallBack();
							} else {
								if(data.labelErrorMessage){
									msgBox.info({
										type:"fail",
										content: data.labelErrorMessage
									});
								}else {
									msgBox.info({
										type:"fail",
										content: data.errorMessage
									});
								}
							}
						},
						error : function() {
							taskArra.subState = false;
							msgBox.tip({
								type:"fail",
								content: $.i18n.prop("JC_SYS_055")
							});
						}
					});
				}
			}
		}
	} else {
		taskArra.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
//保存
taskArra.addOrTempTaskNew = function (callMothed,tokenId) {
	if (taskArra.subState)return;
	taskArra.subState = true;
	$("#status").val("0");
	if ($("#zdsz").valid()) {
		taskArra.remindDefault();
		if(taskArra.isRemind()==false){
			var opt={
			    content: $.i18n.prop("JC_OA_PO_034"),
				success: function(){
					var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
					var formData = $("#zdsz").serializeArray();
					formData.push({"name": "remindContent", "value": $("#remindContent").val()});
					formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
					formData.push({"name": "token", "value": $("#token").val()}); //添加 token校验
					taskArra.addFormParameters(formData);
					if(callMothed!=""){//用于其他功能页面调用本页面时，关闭实际的DIV窗口
						jQuery.ajax({
							url : url,
							type : 'POST',
							cache: false,
							contentType: "application/json;charset=UTF-8",
							data : JSON.stringify(serializeJson(formData),replaceJsonNull),
							success : function(data) {
								taskArra.subState = false;
								if(data.success == "true"){
									callMothed();
								} else {
									if(data.labelErrorMessage){
										msgBox.info({
											type:"fail",
											content: data.labelErrorMessage
										});
									}else {
										msgBox.info({
											type:"fail",
											content: data.errorMessage
										});
									}
								}
							},
							error : function() {
								taskArra.subState = false;
								msgBox.tip({
									type:"fail",
									content: $.i18n.prop("JC_SYS_055")
								});
							}
						});
					}else{
						jQuery.ajax({
							url : url,
							type : 'POST',
							cache: false,
							contentType: "application/json;charset=UTF-8",
							data : JSON.stringify(serializeJson(formData),replaceJsonNull),
							success : function(data) {
								if(tokenId!=""){
									$("#token").val(tokenId);
								}else{
									$("#token").val(data.token);
								}
								taskArra.subState = false;
								if(data.success == "true"){
									taskArra.successCallBack();
								} else {
									if(data.labelErrorMessage){
										msgBox.info({
											type:"fail",
											content: data.labelErrorMessage
										});
									}else {
										msgBox.info({
											type:"fail",
											content: data.errorMessage
										});
									}
								}
							},
							error : function() {
								taskArra.subState = false;
								msgBox.tip({
									type:"fail",
									content: $.i18n.prop("JC_SYS_055")
								});
							}
						});
					}	
				},
				cancel:function(){
					taskArra.subState = false;
				}
			}
			msgBox.confirm(opt);
		}else{
			var url = getRootPath()+"/po/workTask/save.action?time=" + new Date();
			var formData = $("#zdsz").serializeArray();
			formData.push({"name": "remindContent", "value": $("#remindContent").val()});
			formData.push({"name": "remindPerId", "value": $("#remindPerId").val()});
			formData.push({"name": "token", "value": $("#token").val()}); //添加 token校验
			taskArra.addFormParameters(formData);
			if(callMothed!=""){//用于其他功能页面调用本页面时，关闭实际的DIV窗口
				jQuery.ajax({
					url : url,
					type : 'POST',
					cache: false,
					contentType: "application/json;charset=UTF-8",
					data : JSON.stringify(serializeJson(formData),replaceJsonNull),
					success : function(data) {
						if(tokenId!=""){
							$("#token").val(tokenId);
						}else{
							$("#token").val(data.token);
						}
						taskArra.subState = false;
						if(data.success == "true"){
							callMothed();
						} else {
							if(data.labelErrorMessage){
								msgBox.info({
									type:"fail",
									content: data.labelErrorMessage
								});
							}else {
								msgBox.info({
									type:"fail",
									content: data.errorMessage
								});
							}
						}
					},
					error : function() {
						taskArra.subState = false;
						msgBox.tip({
							type:"fail",
							content: $.i18n.prop("JC_SYS_055")
						});
					}
				});
			}else{
				jQuery.ajax({
					url : url,
					type : 'POST',
					cache: false,
					contentType: "application/json;charset=UTF-8",
					data : JSON.stringify(serializeJson(formData),replaceJsonNull),
					success : function(data) {
						if(tokenId!=""){
							$("#token").val(tokenId);
						}else{
							$("#token").val(data.token);
						}
						taskArra.subState = false;
						if(data.success == "true"){
							taskArra.successCallBack();
						} else {
							if(data.labelErrorMessage){
								msgBox.info({
									type:"fail",
									content: data.labelErrorMessage
								});
							}else {
								msgBox.info({
									type:"fail",
									content: data.errorMessage
								});
							}
						}
					},
					error : function() {
						taskArra.subState = false;
						msgBox.tip({
							type:"fail",
							content: $.i18n.prop("JC_SYS_055")
						});
					}
				});
			}
		}
} else {
	taskArra.subState = false;
	msgBox.info({
		content:$.i18n.prop("JC_SYS_067"),
		success:function(){
			fnCall();
		}
	});
}
};
//保存成功跳转
taskArra.successCallBack=function(){
	msgBox.tip({
		type:"success",
		content:$.i18n.prop("JC_SYS_001"),
		callback:function(){
			loadrightmenu('/po/workTask/queryTask.action');
		}
	});
};
//已发任务
taskArra.backTable = null;
taskArra.getWorkTask=function(){
	taskArra.backFnServerParams = function(aoData){
		 getTableParameters(taskArra.backTable, aoData);
		 aoData.push({ "name": "createUser", "value": currentUserId});//任务模板查询创建人为当前登录人
		 aoData.push({ "name": "isTemplet", "value": "0"});
	};
	//显示列信息
	taskArra.backColumns = [
	    {mData: "taskName",mRender : function(mData, type, full) {
	    	if(full.status=='3'){
	    		return "<input type=\"radio\" name=\"optionsRadios\"  onclick=\"taskArra.assignment('"+full.taskId+"','')\" />";
	    	}else{
	    		return "<input type=\"radio\" name=\"optionsRadios\"  onclick=\"taskArra.assignment('"+full.id+"','')\" />";
	    	}
	    }},
       	{mData: "taskName"},
       	{mData: "taskName",mRender : function(mData, type, full) {
       		return "<a href=\"#\" onclick=\"taskArra.deleteWorkTask('"+full.id+"','1')\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a>";
		}}
	 ];
	if (taskArra.backTable == null) {
		taskArra.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfModel.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskArra.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				taskArra.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		taskArra.backTable.fnDraw();
	}
};
//暂存模板
taskArra.backTableZC = null;
taskArra.getWorkTaskZC=function(){
	taskArra.backFnServerParamsZC = function(aoData){
		 getTableParameters(taskArra.backTableZC, aoData);
		 aoData.push({ "name": "createUser", "value": currentUserId});//任务模板查询创建人为当前登录人
		 aoData.push({ "name": "status", "value": "5"});
		 aoData.push({ "name": "isTemplet", "value": "0"});
	};
	//显示列信息
	taskArra.backColumnsZC = [
	    {mData: "taskName",mRender : function(mData, type, full) {
	    	return "<input type=\"radio\" name=\"optionsRadiosZc\"  onclick=\"taskArra.assignment('"+full.id+"','5')\" />";
		}},            
       	{mData: "taskName"},
       	{mData: "taskName",mRender : function(mData, type, full) {
			return "<a href=\"#\" onclick=\"taskArra.deleteWorkTask('"+full.id+"','0')\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a>";
		}}
	 ];
	if (taskArra.backTableZC == null) {
		taskArra.backTableZC = $('#workTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfModel.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskArra.backColumnsZC,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				taskArra.backFnServerParamsZC(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		taskArra.backTableZC.fnDraw();
	}
};

//暂存
taskArra.backTableZCTask = null;
taskArra.getWorkTaskZCTask=function(){
	taskArra.backFnServerParamsZCTask = function(aoData){
		 getTableParameters(taskArra.backTableZCTask, aoData);
		 aoData.push({ "name": "sponsorId", "value": currentUserId});//暂存任务查询发起人为当前登录人
		 aoData.push({ "name": "status", "value": "8"});
		 aoData.push({ "name": "isTemplet", "value": "0"});
	};
	//显示列信息
	taskArra.backColumnsZCTask = [
	    {mData: "taskName",mRender : function(mData, type, full) {
	    	return "<input type=\"radio\" name=\"optionsRadiosZc\"  onclick=\"taskArra.assignment('"+full.id+"','8')\" />";
		}},            
       	{mData: "taskName"},
       	{mData: "taskName",mRender : function(mData, type, full) {
			return "<a href=\"#\" onclick=\"taskArra.deleteWorkTaskNew('"+full.id+"')\" class=\"a-icon i-remove\"><i data-original-title=\"删除\" data-container=\"body\" title=\"\" data-placement=\"top\" data-toggle=\"tooltip\" class=\"fa fa-remove\"></i></a>";
		}}
	 ];
	if (taskArra.backTableZCTask == null) {
		taskArra.backTableZCTask = $('#taskTempTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/workTask/manageListOfModel.action?time=" + new Date(),
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": taskArra.backColumnsZCTask,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				taskArra.backFnServerParamsZCTask(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2]}]
		} );
	} else {
		taskArra.backTableZCTask.fnDraw();
	}
};
//提取任务模板中任务名称
taskArra.assignment=function(t,s){
	$("#taskNameVal").val(t);
	if(s!=null && s!=""){
		$("#taskStatus").val(s);
	}else{
		$("#taskStatus").val("");
	}
};
//提取任务模板中任务名称
taskArra.getTaskName=function (){
	var taskIdTemp=$("#taskNameVal").val();
	if(taskIdTemp==null || taskIdTemp==''){
		msgBox.info({
			type:"fail",
			content:$.i18n.prop("JC_SYS_029","要提取的任务")//任务模板
		});
		return;
	}
	var taskSta=$("#taskStatus").val();
	if(null!=taskIdTemp){
		var dataTemp;
		if(taskSta=='5'){
			dataTemp={"id": taskIdTemp,"status":'5',"isTemplet":'0'};//添加是否取消模板
		}else{
			dataTemp={"id": taskIdTemp};
		}
		$("#taskStatus").val("");
		$.ajax({
			type : "GET",
			url : getRootPath()+"/po/workTask/getAll.action?time=" + new Date(),
			data : dataTemp,
			dataType : "json",
			success : function(data) {
				if (data) {
					$("#taskName").val(data.taskName);
					$("#taskWorkType").val(data.taskWorkType);
					//$("#taskOrigin").val(data.taskOrigin);
					//人员选择树--发起人
					$("#sponsorName-sponsorId").select2("data", {id:data.sponsorId,text:data.sponsor});				
					//人员选择树--负责人
					$("#userName-directorId").select2("data", {id:data.directorId,text:data.director});					
					//人员选择树--参与人
					var prtTemp= new Array();//ID
					var prtValueTemp= new Array();//NAME
					var objs = "[";
					var prtIdValue="";
					if(data.prticipantsId!=null && data.prticipants!=null ){//判断参与人不为空的情况
						prtTemp=data.prticipantsId.split(",");
						prtValueTemp=data.prticipants.split(",");
						if(prtTemp!=null && prtTemp.length>0 && prtValueTemp!=null && prtValueTemp.length>0){
							for (i=0;i<prtTemp.length ;i++ )   
						    {
								if(i==prtTemp.length-1){
									prtIdValue+="{id:"+prtTemp[i]+",text:\""+prtValueTemp[i]+"\"}";
								}else{
									prtIdValue+="{id:"+prtTemp[i]+",text:\""+prtValueTemp[i]+"\"},";
								}
						    }
						}
						objs += prtIdValue + "]";
						$("#prticipantsName-prticipantsId").select2("data", eval(objs));
					}else{
						selectControl.clearValue("prticipantsName-prticipantsId");
					}
					$("#taskContent").val(data.content);
					$("#standard").val(data.standard);
					$("#startTime").val(data.startTime);
					$("#endTime").val(data.endTime);
//					$("#reportDay").val(data.reportDay);
//					if(data.reportType==1){
//						$("#notRemind").attr("checked","checked");
//						$("#isRemind").removeAttr("checked");
//						$('#isRemind').attr("disabled",false);
//						$('#notRemind').attr("disabled",false);						
//					}else{
//						$("#isRemind").attr("checked","checked");
//						$("#notRemind").removeAttr("checked");
//						$('#isRemind').attr("disabled",true);
//						$('#notRemind').attr("disabled",true);
//					}
					//用于暂存任务提取时赋值 start
					$("#taskId").val(data.id);
					$("#statusArr").val(data.status);
					//用于暂存任务提取时赋值 end
				}
			},
			error : function() {
				taskArra.subState = false;
				msgBox.info({
					type:"fail",
					content: $.i18n.prop("JC_OA_COMMON_014")
				});
			}
		});
	}
	$('#new-agency').modal('hide');
};
//取消任务模板
taskArra.deleteWorkTask=function(t,dType){
	if(t!=null && t!=""){
		var opt={
			content:$.i18n.prop("JC_SYS_034"),
			success:function(){
				 $.ajax({
						type : "POST",
						url : getRootPath()+"/po/workTask/cancalTemplate.action?time=" + new Date(),
						data : {"id": t,"isTemplet":"1"},
						dataType : "json",
						success : function(data) {
							taskArra.subState=false;
							if (data > 0) {
								msgBox.tip({
									type:"success",
									content:$.i18n.prop("JC_SYS_005")
								});
							}
							if(dType==0){//暂存模板
								taskArra.getWorkTaskZC();
							}else if(dType==1){//已发任务
								taskArra.getWorkTask();
							}else{//暂存
								taskArra.getWorkTaskZCTask();
							}
						}
					});
			},
			error : function() {
				taskArra.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			},
			cancel:function(){
				taskArra.subState=false;
			}
		}
		msgBox.confirm(opt);
	}
};
//暂存任务(删除)
taskArra.deleteWorkTaskNew=function(t){
	if(t!=null && t!=""){
		var opt={
			content:$.i18n.prop("JC_SYS_034"),
			success:function(){
				 $.ajax({
						type : "POST",
						url : getRootPath()+"/po/workTask/deleteByIds.action",
						data : {"ids": t},
						dataType : "json",
						success : function(data) {
							taskArra.subState=false;
							if (data > 0) {
								msgBox.tip({
									type:"success",
									content:$.i18n.prop("JC_SYS_005")
								});
							}
							taskArra.getWorkTaskZCTask();
						}
					});
			},
			error : function() {
				taskArra.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_055")
				});
			},
			cancel:function(){
				taskArra.subState=false;
			}
		}
		msgBox.confirm(opt);
	}
};
//选择计划
taskArra.getPlan=function(){
	planRowDetail.resetPlanBoxList();
	//显示计划弹出层内容
	$('#viewPlan').modal('show');
	//初始化查看任务弹出层列表
	planRowDetail.planRowDetailInit();
//	$("#viewPlan").modal("setPaddingTop");//UI调整，处理弹出层首次显示下方按钮遮挡问题
};
//添加附件
taskArra.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name":"fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};

//点击关闭附件弹出层是清空内容
taskArra.fileupload = function (){
	$("#businessId").val("0");
//	showAttachList(false);
	showAttachList(true);
};
//点击关闭时删除一上传文件
taskArra.deleteAttach = function() {
	if($("#fileids").val().length > 0 && $("#fileid_old").val() != $("#fileids").val()){
		var ids = $("#fileids").val();
		$.ajax({
			type : "POST",
			url : getRootPath()+"/content/attach/delete.action",
			data : {"ids": ids},
			dataType : "json",
			success : function(data) {
				carInfo.carInfoList();
			}
		});
	}
};

//关闭任务模板
taskArra.closeWin=function(){
	$("#taskNameVal").val("");
	taskArra.getWorkTaskZCTask();
	taskArra.getWorkTask();
	taskArra.getWorkTaskZC();
};
//超期提醒设置预览
taskArra.showRemindSet=function(){
	var t=$('input[name="remindType"]:checked').val();
	var s="";
	if(t==1){
		s="短信";
	}else if(t==2){
		s="邮件";
	}else{
		s="不提醒";
	}
	$('#optionsRadios2').val(s);//提醒方式
	var directorId=returnValue('userName-directorId');
	var prticipantsId=returnValue('prticipantsName-prticipantsId');
	var dire=$('#userName-directorId').val();
	var prti=$('#prticipantsName-prticipantsId').val();
	var perId='';
	if(directorId!=null && prticipantsId==null){
		var director= new Array(); 
		director=directorId.split(":");
		perId+=director[1];
	}
	if(directorId==null && prticipantsId!=null){
		var prticipants= new Array(); 
		prticipants=prticipantsId.split(",");
		var participart='';
		for(var i=0;i<prticipants.length;i++){
			if(i==(prticipants.length-1)){
				participart+=prticipants[i].split(":")[1];
			}else{
				participart+=prticipants[i].split(":")[1]+',';
			}
		}
		perId+=participart;
	}
	if(directorId!=null && prticipantsId!=null){
		var director= new Array(); 
		director=directorId.split(":");
		var prticipants= new Array(); 
		prticipants=prticipantsId.split(",");
		var participart='';
		for(var i=0;i<prticipants.length;i++){
			if(prticipants[i].split(":")[1]!=director[1]){
				participart+=prticipants[i].split(":")[1]+',';
			}
		}
		if(participart !=''){
			perId+=director[1]+','+participart;
		}else{
			perId+=director[1];
		}
		
	}
	var per="";
	if(dire!=null && prti==null){
		per+=dire;
	}
	if(dire==null && prti!=null){
		per+=prti;
	}
	var partis='';
	if(dire!=null && prti!=null){
		var prt= new Array(); 
		prt=prti.split(",");
		for(var i=0;i<prt.length;i++){
			if(i==(prt.length-1)){
				if(dire!=prt[i]){
					partis+=prt[i];
				}
			}else{
				if(dire!=prt[i]){
					partis+=prt[i]+',';
				}
			}
		}
		if(partis!=''){
			per+=dire+','+partis;
		}else{
			per+=dire;
		}
	}
	if(t!=0){
		$("#remindContent").attr("disabled",false);
		if(isSave==0){
			$('#remindContent').val($('#taskName').val());//提醒内容
		}else{
			if($('#remindContent').val()==null || $('#remindContent').val()==''){
				$('#remindContent').val($('#taskName').val());//提醒内容
				isSave=0;
			}
		}
		$('#remindPerId').val(per);
		$('#remindPerName').val(perId);
		$('#remindContentTemp').val($('#remindContent').val());
	}else{
		$('#remindContent').val("");
		$('#remindPerId').val("");
		$('#remindPerName').val("");
		$('#remindContentTemp').val("");
		$("#remindContent").attr("disabled",true);
		isSave=0;
	}
	$('#remindWindow').modal('show');
};
//超期提醒-保存
taskArra.showRemindSetSave=function(){
	var t=$('input[name="remindType"]:checked').val();
	if(t!=0){
		if($('#remindContent').val()==null || $('#remindContent').val()==''){
			isSave=0;
		}else{
			$('#remindContentTemp').val($('#remindContent').val());
			isSave=1;
		}
	}
};
//超期提醒-关闭
taskArra.showRemindSetClose=function(){
	var t=$('input[name="remindType"]:checked').val();
	if(t!=0){
		if(isSave==1){
			$('#remindContent').val($('#remindContentTemp').val());
		}else{
			$('#remindContent').val($('#taskName').val());
		}
	}else{
		$('#remindContent').val($('#taskName').val());
	}
};
//超期提醒默认设置
taskArra.remindDefault=function(){
	var t=$('input[name="remindType"]:checked').val();
	if(t!=0){
		var dire=$('#userName-directorId').val();
		var prti=$('#prticipantsName-prticipantsId').val();
		if($('#remindPerId').val()==null || $('#remindPerId').val()==''){
			$('#remindPerId').val(dire+','+prti);
		}
		if($('#remindContent').val()==null || $('#remindContent').val()==''){
			$('#remindContent').val($('#taskName').val());
		}
	}
};
//过滤
taskArra.getPlanId=function(pId){
	var planId='';
	if(pId !=null && pId !=''){
		planId=pId.split('_');
		if(planId!=null){
			planId=planId[1];
		}
	}
	return planId;
};
//导入计划
taskArra.planBoxFillData=function() {
	var chkPlanValue = "";//未处理的计划内容字符串
	var standardValue="";//完成标准
	//处理任务内容字符串方法
	var checkNum=$("input[name^=checkPlanIds]:checked").length;
	//用于存放导入计划的主表id
	var planIdList = new Array();
	$("input[name^=checkPlanIds]:checked").each(function(i,v){
		if(i==checkNum-1){
			chkPlanValue+=$("input[name="+v.name+"]").parent().parent().children("td").get(1).innerHTML+'；';//主要完成事项
			standardValue+=$("input[name="+v.name+"]").parent().parent().children("td").get(2).innerHTML+'；';//完成标准
			planIdList.push(taskArra.getPlanId(v.id));
		}else{
			chkPlanValue+=$("input[name="+v.name+"]").parent().parent().children("td").get(1).innerHTML+'；\n';//主要完成事项
			standardValue+=$("input[name="+v.name+"]").parent().parent().children("td").get(2).innerHTML+'；\n';//完成标准
			planIdList.push(taskArra.getPlanId(v.id));
		}
	});
	//工作计划是否删除校验 start
	if(planIdList!=null){
		for(var i=0;i<planIdList.length;i++){
			if(!DeleteCascade.syncCheckExist("planAnno",planIdList[i])){
				msgBox.info({
					content: $.i18n.prop("JC_OA_COMMON_015")
				});
				return;
			}
		}
	}
	//工作计划是否删除校验 end
	
	//如果没有计划内容为空,弹出提示
	if(chkPlanValue == ""){
		msgBox.info({
			type:"fail",
			content: $.i18n.prop("JC_OA_PO_042")
		});
	}else{
		if($("#taskContent").val() !=''){
			chkPlanValue=$("#taskContent").val()+'\n'+chkPlanValue;
			standardValue=$("#standard").val()+'\n'+standardValue;
		}
		$("#taskContent").val(chkPlanValue);
		$("#standard").val(standardValue);
		$("#viewPlan").modal("hide");//弹出层隐藏
	}
};

//超期校验
taskArra.isRemind=function(){
	var endDate=$("#endTime").val();//结束日期
	var dd = new Date(); 
	var x = dd.toLocaleString(); 
	var yp = x.indexOf('年'), 
	mp = x.indexOf('月'), 
	dp = x.indexOf('日'); 
	var y = x.substr(0,yp), 
	m = x.substr(yp + 1,mp - yp - 1), 
	d = x.substr(mp + 1,dp - mp - 1); 
	var o =[y,m,d];
	var nowDate=o.join('-');
	return taskArra.compareDate(endDate,nowDate);
};
//js日期比较(yyyy-mm-dd)
taskArra.compareDate=function(a, b) {
   var arr = a.split("-");
   var starttime = new Date(arr[0], arr[1], arr[2]);
   var starttimes = starttime.getTime();
   var arrs = b.split("-");
   var lktime = new Date(arrs[0], arrs[1], arrs[2]);
   var lktimes = lktime.getTime();
   if (starttimes >= lktimes) {
       return true;
   }
   else{
	   return false;
   } 
};
//用于汇报提醒中短信提醒判断
taskArra.remindMessage=function(){
	var reg1 =  /^\d+$/;
	var reportDay=$('#reportDay').val();
	if(reportDay==null || reportDay.trim()==''){
		$('#reportDay').val(0);
	}
	 if(reportDay.trim().match(reg1)==null) {
		 $('#isRemind').attr("disabled",true);
		 $('#notRemind').attr("disabled",true);
		 $('#isRemind').attr("checked",true);
		 $('#notRemind').attr("checked",false);
	 }else{
		 if(reportDay >0 && reportDay<1000){
			 $('#isRemind').attr("disabled",false);
			 $('#notRemind').attr("disabled",false);
		 }else{
			 $('#isRemind').attr("disabled",true);
			 $('#notRemind').attr("disabled",true);
			 $('#isRemind').attr("checked",true);
			 $('#notRemind').attr("checked",false);
		 }
	 }
};
//汇报时限天数校验
taskArra.compareStaAndEnd=function(){
	var reVal=0;
	var startTime=$('#startTime').val();
	var endTime=$('#endTime').val();
	var reportDay=$('#reportDay').val();
	if(startTime!=null && endTime!=null && reportDay!=null && startTime!='' && endTime!=''){
		   var aDate  =  startTime.split("-");
	       var oDate1  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);    //转换为12-18-2006格式  
	       var aDate  =  endTime.split("-");
	       var oDate2  =  new  Date(aDate[1]  +  '-'  +  aDate[2]  +  '-'  +  aDate[0]);  
	       var iDays  =  parseInt(Math.abs(oDate1  -  oDate2)  /  1000  /  60  /  60  /24);    //把相差的毫秒数转换为天数  
	       if(iDays<reportDay){
	    	   reVal=1;
	       }
	}
	return reVal;
};
//暂存任务保存时，查询其父子关系
taskArra.getParentTaskForZC = function (id) {
	var reVal=0;
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/workTask/get.action?time=" + new Date(),
		data : {"id": id},
        cache : false, 
        async : false,
		dataType : "json",
		success : function(data) {
			if (data.status =='8' && data.parentTaskid !=-1) {//暂存状态 且 存在父任务 且 无非暂存任务
				$.ajax({
					type : "GET",
					url : getRootPath()+"/po/workTask/get.action?time=" + new Date(),
					data : {"id": data.parentTaskid},
			        cache : false, 
			        async : false,
					dataType : "json",
					success : function(data) {
						if (data.normalToCount==0) {//暂存状态 且 存在父任务 且 无非暂存任务
							reVal=1;
						}
					}
				});
			}
		}
	});
	return reVal;
};
//初始化 
jQuery(function($)
{
	//计算分页显示条数
	taskArra.pageRows = TabNub>0 ? TabNub : 10;
	//保存
	$("#addTask").click(function(){taskArra.addOrTempTask("");});
	//暂存模板
	$("#addTaskModel").click(function(){taskArra.tempAddTask("");});
	//重置
	$("#resetInfo").click(function(){taskArra.clearForm();});
	//提取任务模板中任务名称
	$("#extractButtion").click(function(){taskArra.getTaskName();});
	//选择工作计划
	$("#selectPlan").click(function(){taskArra.getPlan();});
	//任务布置首次进入页面 人员选择树--发起人默认为当前登录人
	selectControl.init("showSponsorTree","sponsorName-sponsorId", false, true, null, {id:$('#userId').val(),text:$('#userName').val()});
	//绑定查看计划弹出层查询按钮事件(查看计划)
	$("#queryPlanBox").click(function(){planRowDetail.planRowDetailList();});
	//绑定查看计划弹出层重置按钮事件(查看计划)
	$("#resetPlanBox").click(function(){planRowDetail.resetPlanBoxList();});
	//绑定导入计划按钮事件(查看计划)
	$("#importContentBtn").click(function(){taskArra.planBoxFillData();});
	//附件使用
	$("#closebtn").click(function(){taskArra.fileupload();});
	$("#closeModalBtn").click(function(){taskArra.fileupload();});
	$("#queryattach").click(function(){taskArra.fileupload();});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//超期提醒保存
	$("#remindSave").click(function(){taskArra.showRemindSetSave();});
	//超期提醒关闭
	$("#remindClose").click(function(){taskArra.showRemindSetClose();});
	//暂存
	$("#tempAddTask").click(function(){taskArra.tempAddWorkTask();});
});
