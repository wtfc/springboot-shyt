var suggest = {};
//重复提交标识
suggest.subState = false;
suggest.pageRows = null;

//添加附件
suggest.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	
	formData.push({"name": "fileids", "value": fileids});
};

//添加修改公用方法
suggest.saveOrModify = function (hide) {
	if (suggest.subState)return;
	suggest.subState = true;
	if ($("#suggestForm").valid()) {
		var url = getRootPath()+"/ic/suggest/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/ic/suggest/update.action?time=" + new Date();
		}
		var formData = $("#suggestForm").serializeArray();
		formData.push({"name": "token", "value": $('#token').val()});
		suggest.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				suggest.subState = false;
				$('#token').val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					$("#userSelect").hide();
					$("#controlTreeTo").show();
					suggest.clearForm();
					$("#suggestForm")[0].reset();
					$('#suggestForm #count').html(300);
					if ($("#id").val() == 0) {
						suggest.clearForm();
					} else {
						$("#modifyDate").val(data.modifyDate);
					}
					suggest.suggestList();
					if (hide) {
						$('#myModal-edit').modal('hide');
					}
					clearTable();
					showAttachList(true);
				} else {
					if(data.labelErrorMessage){
						showErrors("suggestForm", data.labelErrorMessage);
					} else if (data.errorMessage) {
						msgBox.tip({
							content: data.errorMessage,
							type:"fail"
						});
					} else {
						msgBox.tip({
							content: $.i18n.prop("JC_SYS_002"),
							type:"fail"
						});
					}
				}
			},
			error : function() {
				suggest.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002"),
					type:"fail"
				});
			}
		});
	}else{
		suggest.subState = false;
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_067"),
			type:"fail"
		});
	} 
};
//保存回复方法
suggest.saveRep = function (hide) {
	if ($("#replyForm").valid()) {
		var url = getRootPath()+"/ic/suggest/saveRep.action?time=" + new Date();
		var formData = $("#replyForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				if(data.success=='true'){
					msgBox.tip({
						content: data.successMessage,
						type:"success"
					});
					suggest.suggestList();
					if (hide) {
						$('#reply').modal('hide');
					}
					suggest.clearForm();
				} else {
					if (data.errorMessage) {
						msgBox.tip({
							content: data.errorMessage,
							type:"fail"
						});
					} else {
						msgBox.tip({
							content: $.i18n.prop("JC_SYS_002"),
							type:"fail"
						});
					}
				}
			},
			error : function() {
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002"),
					type:"fail"
				});
			}
		});
	} else {
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_067"),
			type:"fail"
		});
	}
};
//清空表单
suggest.clearForm = function () {
	$('#suggestForm').find("input[type=text]").val("");
	$('#suggestForm').find("input[type=checkbox]").attr("checked",false);
	$('#suggestListForm').find("select[opting=selected]").val("");
	$('#list').find("input[type=checkbox]").attr("checked",false);
	$('#suggestForm').find("textarea").val("");
	$('#replyForm').find("input[type=text]").val("");
	$('#replyForm').find("textarea").val("");
	$('#viewForm').find("input[type=text]").val("");
	$('#viewForm').find("textarea").val("");
	$('#repContentList').empty();
	//$('#fileupload #attacthItem tbody').empty();
	suggest.initSuggestTypeList();
	select2InitEchoToPerson("suggestForm #recipientIds-recipientIds",null);
};
//删除建议
suggest.deleteSuggest = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/suggest/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			var ids = String(id);
			if (id == 0) {
				var idsArr = [];
				$("[name='ids']:checked").each(function() {
					idsArr.push($(this).val());
				});
				ids = idsArr.join(",");
			}
			if (ids.length == 0) {
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_061"),
					type:"fail"
				});
				return;
			}
			msgBox.confirm({
				content: $.i18n.prop("JC_SYS_034"),
				success: function(){
					suggest.deleteCallBack(ids);
				}
			});
		},
		error : function() {
			msgBox.tip({
				content:$.i18n.prop("JC_OA_COMMON_014")
			});
		}
	});
};

suggest.deleteCallBack = function(ids) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/suggest/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				suggest.clearForm();
				suggest.suggestList();
			}else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

//获取修改信息
suggest.get = function (id,flag) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/suggest/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				suggest.clearForm();
				if(flag=='edit'){//编辑弹出层(暂时不需要编辑功能)
					$("#suggestForm").fill(data);
					//$("#oldName").val(data.suggestTitle);
					$("#saveOrModify").hide();
					$('#myModal-edit').modal('show');
				}else if(flag == 'rep'){//回复弹出层
					$('#replyForm #count').html(300);
					$("#replyForm #suggestId").val(data.id);//将id赋给回复页中的suggestId
					$("#r_suggestTitle").html(data.suggestTitle);
					if(data.suggestWay == 1){
						$("#r_createUserName").html("（匿名）");
					}else{
						$("#r_createUserName").html(data.displayName);//发起人
					}
					$("#r_suggestTypeName").html(data.suggestTypeName);
					$("#r_createDate").html(data.createDate);
					if(data.userId == data.createUser){
						$("#r_recipientNames").html(data.recipientNames);//接收人名字显示
					}else{
						//是群发单显
						if(data.singleShow=='1'){
							$("#r_recipientNames").html(data.userName);//群发单显‘是’，只显示当前登录人名称
						}else{
							$("#r_recipientNames").html(data.recipientNames);//群发单显‘否’，显示所有接收人的名字
						}
					}
					var str = "";
					if(data.sugRepList && data.sugRepList.length > 0) {
						var n = data.sugRepList.length;
						for(var i=0;i<data.sugRepList.length;i++){
							var o = data.sugRepList[i];
							str ="<li class=\"clearfix m-b\">"+"<p class=\"dialog-text input-group \">";
							if(o.replyerName == data.displayName){
								str += "<i class=\"fa fa-comment input-group-btn p-r dialog-text-blue\"></i>";//发起人回复的信息小图标显示为蓝色，否则显示红色
							}else{
								str += "<i class=\"fa fa-comment input-group-btn p-r\"></i>";
							}
							if(data.suggestWay=='1'){//如果是匿名
								if(data.userName == data.displayName){//如果当前登录人是建议发起人
									str += "<strong>"+o.replyerName+"：</strong>";
								}else{
									if(data.userName == o.replyerName){//如果当前登录人是回复人
										str += "<strong>"+o.replyerName+"：</strong>";
									}else{
										if(o.replyerName == data.displayName){//如果回复人是建议发起人
											str += "<strong>（匿名）：</strong>";
										}else{
											str += "<strong>"+o.replyerName+"：</strong>";
										}
									}
								}
							}else{
								str += "<strong>"+o.replyerName+"：</strong>";
							}
							
							str += o.repContent +"<span>"+o.repTime+"</span>"+"</p>"+"</li>";
							
							if(data.singleShow=='1'){//如果是群发单显，只显示自己回复的信息和发起人回复的信息
								if(data.userName == data.displayName){//如果当前登录人是发起人，可以看到
									$("#repContentList").append(str);
								}else{
									if(o.replyerName == data.userName || o.replyerName == data.displayName){
										$("#repContentList").append(str);
									}else{
										n--;
									}
								}
							}else{
								$("#repContentList").append(str);
							}
						}
						if(parseInt(n)>0){
							$('#rep_Div').show();
						}else{
							$('#rep_Div').hide();
						}
					}else{
						$('#rep_Div').hide();
					}
					$("#r_suggestContent").html(data.suggestContent);
					//附件使用 start
					$("#businessId").val(data.id);
					clearDelAttachIds();//设置附件上传为逻辑删除
					clearTable();
					echoAttachListDul(false,"attachList_r");
					$('#isshow').val('1');//只允许附件下载
					//附件使用 end
					$('#reply').modal('show');
				}
			}
		},
		error : function() {
			msgBox.tip({
				content:$.i18n.prop("JC_OA_COMMON_014")
			});
		}
	});
};

//分页处理 start
//分页对象
suggest.oTable = null;
//显示列信息
suggest.oTableAoColumns = [
	{ "mData": "suggestTitle" },
	{ "mData": "suggestTypeName" },
	{mData: "repStatus", mRender : function(mData, type, full) {
			if(full.singleShow == '1'){//如果是群发单显
				if(parseInt(full.isRep) > 0 || parseInt(full.createUserRep) > 0){//如果当前登录人已经回复或者 发起人已经回复 则显示已回复
					return "已回复";
				}else{
					if(full.userId == full.createUser && full.repStatus == '1'){//否则，如果当前登录人是发起人并且已经有人回复了，那么显示已回复
						return "已回复";
					}else{
						return "未回复";
					}
				}
			}else{//不是群发单显，正常按照repStatus字段判断
				if(full.repStatus == '1'){
					return "已回复";
				}else{
					return "未回复";
				}
			}
		}
	},

	{mData: "disposeType", mRender : function(mData, type, full){
			return full.userId==full.createUser?"发起":"接收";
		}
	},
	{mData: "displayName", mRender : function(mData, type, full){
			return full.suggestWay=='0'?full.displayName:"（匿名）";
		}
	},
	{ "mData": "createDate" },
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
 ];

suggest.formReset = function(){
	$('#suggestListForm')[0].reset();
	$("#controlTreeToCreateUser").attr("style","display:block;");
	$('#controlTreeToCreateUser a span.select2-chosen').html("");
	$('#createUser-createUser').val("");
	$("#suggestWay_f").prop("disabled",false);
	
};


//分页查询建议
suggest.suggestList = function () {
	$('#IP-edit').fadeIn();
//组装后台参数
	suggest.oTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(suggest.oTable, aoData);
		//组装查询条件
		if ($("#suggestListForm #suggestTitle").val()!="") {
			aoData.push({ "name": "suggestTitle", "value": $("#suggestListForm #suggestTitle").val()});
		}
		if ($("#createUser-createUser").val()) {
			aoData.push({ "name": "createUser", "value": $("#createUser-createUser").val()});
		}
		if ($("#suggestListForm #suggestTypeId").val()!="" && $("#suggestListForm #suggestTypeId").val()!="0") {
			aoData.push({ "name": "suggestTypeId", "value": $("#suggestListForm #suggestTypeId").val()});
		}
		if ($("#suggestListForm #disposeType_s").prop('checked')) {
			aoData.push({ "name": "disposeType_s", "value": $("#suggestListForm #disposeType_s").val()});
		}
		if ($("#suggestListForm #disposeType_r").prop('checked')) {
			aoData.push({ "name": "disposeType_r", "value": $("#suggestListForm #disposeType_r").val()});
		}
		if ($("#suggestListForm #repStatus_n").prop('checked')) {
			aoData.push({ "name": "repStatus_n", "value": $("#suggestListForm #repStatus_n").val()});
		}
		if ($("#suggestListForm #repStatus_y").prop('checked')) {
			aoData.push({ "name": "repStatus_y", "value": $("#suggestListForm #repStatus_y").val()});
		}
		if ($("#suggestListForm #suggestWay_t").prop('checked')) {
			aoData.push({ "name": "suggestWay_t", "value": $("#suggestListForm #suggestWay_t").val()});
		}
		if ($("#suggestListForm #suggestWay_f").prop('checked')) {
			aoData.push({ "name": "suggestWay_f", "value": $("#suggestListForm #suggestWay_f").val()});
		}
		if ($("#suggestListForm #startCreateDate").val()!="") {
			aoData.push({ "name": "startCreateDate", "value": $("#suggestListForm #startCreateDate").val()});
		}
		if ($("#suggestListForm #endCreateDate").val()!="") {
			aoData.push({ "name": "endCreateDate", "value": $("#suggestListForm #endCreateDate").val()});
		}
	};
	if (suggest.oTable == null) {
		suggest.oTable = $('#suggestTable').dataTable( {
			iDisplayLength: suggest.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/suggest/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: suggest.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				suggest.oTableFnServerParams(aoData);
			},
			aaSorting:[],
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [2,3,6]}]
		} );
	} else {
		suggest.oTable.fnDraw();
	}
};
//分页处理 end

//显示添加建议弹出层
suggest.showAddDiv = function (){
	suggest.clearForm();
	hideErrorMessage();
	//附件使用 start
	$("#businessId").val("0");
	$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
	clearDelAttachIds();//设置附件上传为逻辑删除
	$("#islogicDel").val("1");//附件使用 逻辑删除
	clearTable();
	echoAttachList(false);
	clearTable();
	$('#isshow').val('0');
	//附件使用 end
	$("#id").val(0);
	$('#suggestForm #count').html(300);
	$("#saveOrModify").show();
	$("#isshow").val(0);
	$("#businessId").val(1);
	$('#myModal-edit').modal('show');
};

//加载添加DIV
suggest.loadAddHtml = function (){
	if($.trim($("#myModal-editDiv").html()) == ""){
		$("#myModal-editDiv").load(getRootPath()+"/ic/suggest/suggestDiv.action",suggest.showAddDiv);
	}
	else{
		suggest.showAddDiv();
	}
};

//加载添加DIV
suggest.replyDiv = function (id,flag){
	if($.trim($("#replyDiv").html()) == ""){
		$("#replyDiv").load(getRootPath()+"/ic/suggest/suggestReplyDiv.action",null,function(){suggest.get(id,flag);});
	} else {
		suggest.get(id,flag);
	}
};

//初始化建议类型下拉数据
suggest.initSuggestTypeList = function () {
	$.get(getRootPath()+"/ic/suggest/getSuggestTypeList.action",null,function(data){
		$("#suggestListForm #suggestTypeId").empty();
		$("#suggestForm #suggestTypeId").empty();
		var option1 ="";
		var option = "";
		if(data == ""){
			option1 = "<option value=\"\">-全部-</option>";
			option = "<option value=\"\">-请选择-</option>";
			$("#suggestListForm #suggestTypeId").append(option1);
			$("#suggestForm #suggestTypeId").append(option);
		}else{
			$.each(data, function(i, o) {
				//添加上是否固定接收人isfixed和接收人id字符串recipientIds作为自定义属性，方便下拉的change事件使用
				if(i==0){
					option1 = "<option value=\"\">-全部-</option>"+"<option isFixed='"+o.isFixed+"' recipientIds='"+o.recipientIds+"' value='"+o.id+"'>"+o.typeName+"</option>";
					option = "<option value=\"\">-请选择-</option>"+"<option isFixed='"+o.isFixed+"' recipientIds='"+o.recipientIds+"' value='"+o.id+"'>"+o.typeName+"</option>";
				}else{
					option1 = "<option isFixed='"+o.isFixed+"' recipientIds='"+o.recipientIds+"' value='"+o.id+"'>"+o.typeName+"</option>";
					option = "<option isFixed='"+o.isFixed+"' recipientIds='"+o.recipientIds+"' value='"+o.id+"'>"+o.typeName+"</option>";
				}
				$("#suggestListForm #suggestTypeId").append(option1);
				$("#suggestForm #suggestTypeId").append(option);
			});
		}
	});
};
//获取接收人名称串
suggest.getUserNames = function(ids,flag){
	if(ids == ""){
		$("#controlTreeTo").show();
		$('#userSelect').hide();
		select2InitEchoToPerson("suggestForm #recipientIds-recipientIds",null);
	}else{
		$.ajax({
			type : "GET",
			url : getRootPath()+"/ic/suggest/getUserNames.action",
			contentType: "application/text;charset=UTF-8",
			data : {"ids": ids},
			dataType : "json",
			success : function(data) {
				if(flag == 0){//不固定接收人
					$("#controlTreeTo").show();
					$('#userSelect').hide();
					select2InitEchoToPerson("suggestForm #recipientIds-recipientIds",eval(data.recipientNames));
				}else{//固定接收人
					$('#userSelect').show();
					$("#controlTreeTo").hide();
					$('#userName').val(data.returnNames);
					$('#recipientIds-recipientIds').val(data.returnIds);
				}
			}
		});
	}
};

suggest.closeModel = function(flag){
	if(flag==1){
		$('#myModal-edit').modal('hide');
	}else{
		$('#reply').modal('hide');
	}
	$("#userSelect").hide();
	$("#controlTreeTo").show();
	suggest.clearForm();
	$("#suggestForm")[0].reset();
	$('#suggestForm #count').html(300);
};

//鼠标右键粘贴事件
$.fn.pasteEvents = function( delay ) {    
	if (delay == undefined) delay = 20;     
	return $(this).each(function() {         
		var $el = $(this);         
		$el.on("paste", function() {            
			$el.trigger("prepaste");             
			setTimeout(function() { $el.trigger("postpaste"); }, delay);        
		});     
	}); 
};

//该变输入字数显示
suggest.checkLen  =  function (obj,flag)
{
	var maxChars = 300;//最多字符数
	if (obj.value.length > maxChars){
		obj.value = obj.value.substring(0,maxChars);
		if (obj.value.length > maxChars){
			//加此if原因：避免在剩余1个字数时输入回车键显示剩余-1字问题
			obj.value = obj.value.substring(0,maxChars-1);
		}
	}

	var curr = maxChars - obj.value.length;
	if(flag==0){
		$("#suggestForm #count").html(curr.toString());
	}else{
		$("#replyForm #count").html(curr.toString());
	}
};
//显示附件上传的Div0和1控制是添加附件还是查看附件
suggest.showFileUploadDiv = function(flag){
	if(flag == 1){
		$("#closebtn").attr("onclick","clearTable()");
		$("#clbtn").attr("onclick","clearTable()");
	}else{
		$("#closebtn").attr("onclick","");
		$("#clbtn").attr("onclick","");
	}
	$('#file-edit').modal('show');
};

suggest.deleteAttach = function() {
	var ids = "";
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	if(fileids == null){
		return;
	}
	ids = fileids.join(",");
	$.ajax({
		type : "POST",
		url : getRootPath()+"/content/attach/delete.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			suggest.clearForm();
			suggest.suggestList();
		}
	});
};
//初始化
jQuery(function($) 
{
	//计算分页显示条数
	suggest.pageRows = TabNub>0 ? TabNub : 10;
	$("#querysuggest").click(suggest.suggestList);//查询按钮
	$("#showAddDiv").click(suggest.loadAddHtml);//显示弹出层
	$("#showAddDiv_t").click(suggest.loadAddHtml);//显示弹出层
	$("#saveAndClose").click(function(){suggest.saveOrModify(true);});//保存关闭
	$("#saveOrModify").click(function(){suggest.saveOrModify(false);});//保存继续
	$("#repSave").click(function(){suggest.saveRep(true);});//回复：保存回复
	$("#repClose").click(function(){suggest.closeModel(0);});//关闭
	$("#addClose").click(function(){suggest.closeModel(1);});//关闭
	$("#resetForm").click(function(){suggest.formReset();});//重置
	//$("#uploadFile").click(function(){suggest.showFileUploadDiv(0);});//弹出附件上传页面
	//$("#queryFile").click(function(){suggest.showFileUploadDiv(1);});//弹出附件上传页面	
	//选择了匿名之后，屏蔽掉发起人选项
	$("#suggestWay_f").change(function(){
		if($("#suggestWay_f").prop("checked")){
			$("#controlTreeToCreateUser").attr("style","display:none;");
		}else{
			$("#controlTreeToCreateUser").attr("style","display:block;");
		}
	});
	
	selectControl.init("controlTreeTo","recipientIds-recipientIds", true, true,null,null);//多选人员
	selectControl.init("controlTreeToCreateUser","createUser-createUser", false, true,null,null);//单选人员
	//当查询条件中选择了发起人员时，将发起方式中的匿名置为disabled
	$("#createUser-createUser").on("change",function(){
		if($("#createUser-createUser").val() != null && $("#createUser-createUser").val() != ""){
			$("#suggestWay_f").prop("disabled",true);
		}else{
			$("#suggestWay_f").prop("disabled",false);
		}
	});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	suggest.suggestList();
	//初始化建议类型的下拉选择
	suggest.initSuggestTypeList();
	
	//内容字数递减控制
	$("#suggestContent").on("postpaste", function() { suggest.checkLen (this,0);}).pasteEvents();
	$("#repContent").on("postpaste", function() { suggest.checkLen (this,1);}).pasteEvents();
	$("#suggestContent").keyup(function() { suggest.checkLen (this,0);}); 
	$("#repContent").keyup(function() { suggest.checkLen (this,1);}); 
	//改变建议类型时，带入默认的接收人
	$('#suggestForm #suggestTypeId').change(function(){
		$('#suggestForm #suggestTypeId option:selected').each(function(){
			suggest.getUserNames($(this).attr('recipientIds'),$(this).attr('isFixed'));
		});
	});
	
});
//@ sourceURL=suggestInteract.js