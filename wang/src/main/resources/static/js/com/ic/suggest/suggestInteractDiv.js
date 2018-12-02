var suggestDiv = {};
//重复提交标识
suggestDiv.subState = false;
suggestDiv.pageRows = null;

//添加附件
suggestDiv.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	
	formData.push({"name": "fileids", "value": fileids});
};

//添加修改公用方法
suggestDiv.saveOrModify = function (hide) {
	if (suggestDiv.subState)return;
	suggestDiv.subState = true;
	if ($("#suggestForm").valid()) {
		var url = getRootPath()+"/ic/suggest/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/ic/suggest/update.action?time=" + new Date();
		}
		var formData = $("#suggestForm").serializeArray();
		formData.push({"name": "token", "value": $('#token').val()});
		suggestDiv.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				suggestDiv.subState = false;
				$('#token').val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					$("#userSelect").hide();
					$("#controlTreeTo").show();
					suggestDiv.clearForm();
					$("#suggestForm")[0].reset();
					$('#suggestForm #count').html(300);
					if ($("#id").val() == 0) {
						suggestDiv.clearForm();
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
				suggestDiv.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002"),
					type:"fail"
				});
			}
		});
	}else{
		suggestDiv.subState = false;
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_067"),
			type:"fail"
		});
	} 
};
//保存回复方法
suggestDiv.saveRep = function (hide) {
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
					suggestDiv.clearForm();
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

//显示添加建议弹出层
suggestDiv.showAddDiv = function (){
	// suggest.clearForm();
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
suggestDiv.loadAddHtml = function (){
	alert($.trim($("#myModal-edit").html()));
	$("#myModal-edit").load(getRootPath()+"/ic/suggest/suggestDiv.action",suggest.showAddDiv);
};

//初始化建议类型下拉数据
suggestDiv.initSuggestTypeList = function () {
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
suggestDiv.getUserNames = function(ids,flag){
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

//清空表单
suggestDiv.clearForm = function () {
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
	suggestDiv.initSuggestTypeList();
	select2InitEchoToPerson("suggestForm #recipientIds-recipientIds",null);
};

suggestDiv.closeModel = function(flag){
	if(flag==1){
		$('#myModal-edit').modal('hide');
	}else{
		$('#reply').modal('hide');
	}
	$("#userSelect").hide();
	$("#controlTreeTo").show();
	suggestDiv.clearForm();
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
suggestDiv.checkLen  =  function (obj,flag)
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
suggestDiv.showFileUploadDiv = function(flag){
	if(flag == 1){
		$("#closebtn").attr("onclick","clearTable()");
		$("#clbtn").attr("onclick","clearTable()");
	}else{
		$("#closebtn").attr("onclick","");
		$("#clbtn").attr("onclick","");
	}
	$('#file-edit').modal('show');
};

suggestDiv.deleteAttach = function() {
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
			suggestDiv.clearForm();
			suggestDiv.suggestList();
		}
	});
};
//初始化
jQuery(function($) 
{
	$("#saveAndClose").click(function(){suggestDiv.saveOrModify(true);});//保存关闭
	$("#saveOrModify").click(function(){suggestDiv.saveOrModify(false);});//保存继续
	$("#repClose").click(function(){suggestDiv.closeModel(0);});//关闭
	$("#addClose").click(function(){suggestDiv.closeModel(1);});//关闭
	
	selectControl.init("controlTreeTo","recipientIds-recipientIds", true, true,null,null);//多选人员
	selectControl.init("controlTreeToCreateUser","createUser-createUser", false, true,null,null);//单选人员
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	
	//初始化建议类型的下拉选择
	suggestDiv.initSuggestTypeList();
	
	//内容字数递减控制
	$("#suggestContent").on("postpaste", function() { suggestDiv.checkLen (this,0);}).pasteEvents();
	$("#repContent").on("postpaste", function() { suggestDiv.checkLen (this,1);}).pasteEvents();
	$("#suggestContent").keyup(function() { suggestDiv.checkLen (this,0);}); 
	$("#repContent").keyup(function() { suggestDiv.checkLen (this,1);}); 

	//改变建议类型时，带入默认的接收人
	$('#suggestForm #suggestTypeId').change(function(){
		$('#suggestForm #suggestTypeId option:selected').each(function(){
			suggest.getUserNames($(this).attr('recipientIds'),$(this).attr('isFixed'));
		});
	});
	ie8StylePatch();
});
//@ sourceURL=suggestInteract.js