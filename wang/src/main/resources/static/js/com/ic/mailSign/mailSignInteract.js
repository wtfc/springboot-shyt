var editor = null;

var mailSign = {};
//重复提交标识
mailSign.subState = false;
//保存邮箱默认签名设置
mailSign.save_setDefault = function () {
		if($('#mailboxId').val()==""|| $('#mailboxId').val() == null){
			msgBox.tip({
				content: $.i18n.prop("JC_OA_IC_059"),
				type:"fail"
			});
			return;
		}
		var url = "";
		if($('#mailboxId').val()==1){
			url = getRootPath()+"/ic/mailSign/saveMailbox.action";
		}else{
			url = getRootPath()+"/ic/mailSign/updateMailbox.action";
		}
		$('#mailboxVal').val($('#mailboxId').val());
		var formData = $("#setDefaultForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						content: data.successMessage,
						type:"success"
					});
					if($('#mailboxId').val()==1){
						mailSign.isSelect = true;
					}
					mailSign.isShow = false;
					mailSign.initMailboxList();
				} else {
					msgBox.tip({
						content: data.errorMessage,
						type:"fail"
					});
				}
			}
		});
};
//修改邮箱默认签名设置
mailSign.savaOrModify = function (hide) {
	if (mailSign.subState)return;
	mailSign.subState = true;
	if ($("#mailSignForm").valid()) {
		var url = getRootPath()+"/ic/mailSign/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/ic/mailSign/update.action?time=" + new Date();
		}
		editor.sync("mailSignForm");
		var formData = $("#mailSignForm").serializeArray();
		formData.push({"name": "token", "value": $('#token').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				mailSign.subState = false;
				$('#token').val(data.token);
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if ($("#id").val() == 0) {
						mailSign.clearForm();
					} else {
						$("#modifyDate").val(data.modifyDate);
					}
					mailSign.isShow = false;
					mailSign.loadSign("list");
					mailSign.initMailboxList();
					mailSign.initMailSignList();
					mailSign.change();
					if (hide) {
						$('#myModal-edit').modal('hide');
					}
				} else {
					if(data.labelErrorMessage){
						msgBox.tip({
							content: data.labelErrorMessage[0].message,
							type:"fail"
						});
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
				mailSign.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002"),
					type:"fail"
				});
			}
		});
	}else{
		mailSign.subState = false;
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_067"),
			type:"fail"
		});
	} 
};
//清空表单
mailSign.clearForm = function () {
	$('#mailSignForm').find("input[type=text]").val("");
	$('#mailSignForm').find("textarea").val("");
	//editor.setContent("");
	 editor.execCommand('cleardoc');
};

//删除签名
mailSign.deleteMailSign = function (id) {
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			mailSign.deleteCallBack(id);
		}
	});
};

mailSign.deleteCallBack = function(id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/mailSign/delete.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				mailSign.clearForm();
				mailSign.loadSign("list");
				mailSign.isShow = false;
				mailSign.initMailboxList();
				mailSign.initMailSignList();
				mailSign.change();
			} else if (data.errorMessage) {
				msgBox.tip({
					content: data.errorMessage,
					type:"fail"
				});
			}
		}
	});
};

//获取修改信息
mailSign.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/mailSign/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				mailSign.clearForm();
				$("#mailSignForm").fill(data);
				editor.setContent(data.signContent);
				$("#savaOrModify").hide();
				$("#oldName").val(data.signTitle);
				$("#title").html("编辑");
				$("#savaAndClose").addClass("dark");
				$("#savaAndClose").html("保 存");
				$('#myModal-edit').modal('show');
			}
		}
	});
};


//显示添加签名弹出层
mailSign.showAddDiv = function (){
	//判断重复提交
	// getToken();
	//清除验证信息
	hideErrorMessage();
	mailSign.clearForm();
	$("#id").val(0);
	$("#title").html("新增");
	$("#savaAndClose").removeClass("dark");
	$("#savaAndClose").html("保存退出");
	$("#savaOrModify").show();
	$('#myModal-edit').modal('show');
};
//动态加载签名内容
mailSign.loadSign = function(divname){
	htmlobj=$.ajax({url:getRootPath()+"/ic/mailSign/manageLoadSign.action",async:false});
	$("#"+divname).html(htmlobj.responseText);
	 $("i[data-toggle='tooltip']").tooltip();
};
//初始化设置默认签名的邮箱
mailSign.isShow = true;
mailSign.isSelect = false;
mailSign.count = 0;
mailSign.initMailboxList = function () {
	$.ajaxSetup({ 
	    async : false 
	});
	$.get(getRootPath()+"/ic/mailSign/mailboxList.action",null,function(data){
		$("#mailboxId").empty();
		var option = "";
		//该用户在我的internet邮箱中没添加外部邮箱，并且没保存内部邮箱签名设置（内部邮箱在保存签名设置之后才存到mailbox表中）
		if(data == ""){
			if(mailSign.isShow){
				option = "<option value=\"\">-请选择-</option>"+"<option id='currenoptiontUser' value='1'>"+$("#currentUser").val()+"</option>";
			}else{
				//新增邮件签名时如果选择内部邮箱即当前登陆用户名
				if($("#mailboxVal").val()==1){
					option = "<option value=\"\">-请选择-</option>"+"<option id='currenoptiontUser' value='1' selected>"+$("#currentUser").val()+"</option>";
				}else{
					option = "<option value=\"\">-请选择-</option>"+"<option id='currenoptiontUser' value='1' >"+$("#currentUser").val()+"</option>";
				}
			}
			$("#mailboxId").append(option);
		}else{
			var option1 = "";
			var isadd = false;//没有添加了当前人内部邮箱签名为false，添加了为true
			var isfirst = false;//内部邮箱是不是第一个
			//循环遍历邮箱如果当前人内部设置了邮箱签名将isadd置为true
			$.each(data, function(i, o) {
				//如果设置内部签名
				if(o.receiveService==1){
					//如第一次设置内部邮箱默认签名设置mailSign.isSelect等于true，内部邮箱回显被选中
					if(mailSign.isSelect){
						option1 = "<option value=\"\">-请选择-</option>"+"<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"' selected>"+$("#currentUser").val()+"</option>";
						mailSign.isSelect = false;
					}else{
						option1 = "<option value=\"\">-请选择-</option>"+"<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"' "+(o.id== $('#mailboxVal').val()?'selected':' ')+">"+$("#currentUser").val()+"</option>";
					}
					isadd = true;
				}
			});
			$.each(data, function(i, o) {
				//如果只存在内部签名
				if(data.length==1&&o.receiveService==1){
					option = option1;
					$("#mailboxId").append(option);
					return false;
				}
				//如果内部签名是第一个
				if(i==0&&o.receiveService==1){
					isfirst = true;
				}
				//如果当前人存在内部邮箱签名显示过滤掉
				if(o.receiveService==1){
					return true;
				}
				if(mailSign.isShow){
					if(isadd){
						if(i==0){
							//如果查询结果第一个不是内部邮箱签名，第一个应加上请选择和内部邮箱（及当前登录人姓名）
							if(o.receiveService!=1){
								option = option1+"<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"'>"+o.address+"</option>";
							}
						}else{
							//如果查询结果第一个是内部邮箱签名，第二个应加上请选择和内部邮箱（及当前登录人姓名）
							if(isfirst){
								option = option1+"<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"'>"+o.address+"</option>";
								isfirst = false;
							}else{
								option = "<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"'>"+o.address+"</option>";
							}
						}
					}else{
						if(i==0){
							option = "<option value=\"\">-请选择-</option>"+"<option id='currenoptiontUser' value='1'>"+$("#currentUser").val()+"</option>"+"<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"'>"+o.address+"</option>";
						}else{
							option = "<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"'>"+o.address+"</option>";
						}
					}
				}else{
					if(isadd){
						if(i==0){
							//如果查询结果第一个不是内部邮箱签名，第一个应加上请选择和内部邮箱（及当前登录人姓名）
							if(o.receiveService!=1){
								option = option1+"<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"' "+(o.id== $("#mailboxVal").val()?"selected":" ")+">"+o.address+"</option>";
							}
						}else{
							//如果查询结果第一个是内部邮箱签名，第二个应加上请选择和内部邮箱（及当前登录人姓名）
							if(isfirst){
								option = option1+"<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"' "+(o.id== $("#mailboxVal").val()?"selected":" ")+">"+o.address+"</option>";
								isfirst = false;
							}else{
								option = "<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"' "+(o.id== $("#mailboxVal").val()?"selected":" ")+">"+o.address+"</option>";
							}
						}
					}else{
						if(i==0){
							option = "<option value=\"\">-请选择-</option>"+"<option id='currenoptiontUser' value='1'>"+$("#currentUser").val()+"</option>"+"<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"' "+(o.id== $("#mailboxVal").val()?"selected":" ")+">"+o.address+"</option>";
						}else{
							option = "<option signId='"+o.signId+"' replySignId='"+o.replySignId+"' modifyDate='"+o.modifyDate+"' value='"+o.id+"' "+(o.id== $("#mailboxVal").val()?"selected":" ")+">"+o.address+"</option>";
						}
					}
				}
				$("#mailboxId").append(option);
			});
		}
		$('#mailboxId option:selected').each(function(){
			$("#mailBoxModifyDate").val($(this).attr('modifyDate'));//获取该邮箱的modifyDate
		});
	});
};
//初始化设置默认签名的下拉
mailSign.initMailSignList = function () {
	$.ajaxSetup({ 
	    async : false 
	});
	$.get(getRootPath()+"/ic/mailSign/mailSignList.action",null,function(data){
		$("#signId").empty();
		$("#replySignId").empty();
		var option1 = "";
		var option2 = "";
		if(data == ""){
			option1 = "<option value=\"\">-请选择-</option>";
			option2 = "<option value=\"\">-请选择-</option>";
			$("#signId").append(option1);
			$("#replySignId").append(option2);
		}else{
			$.each(data, function(i, o) {
				if(i==0){
					option1 = "<option value=\"\">-请选择-</option>"+"<option value='"+o.id+"'>"+o.signTitle+"</option>";
					option2 = "<option value=\"\">-请选择-</option>"+"<option value='"+o.id+"'>"+o.signTitle+"</option>";
				}else{
					option1 = $("<option>").val(o.id).text(o.signTitle);
					option2 = $("<option>").val(o.id).text(o.signTitle);
				}
				$("#signId").append(option1);
				$("#replySignId").append(option2);
			});
		}
	});
};

mailSign.change = function(){
	//切换邮箱触发事件
	$('#mailboxVal').val($('#mailboxId').val());
	$('#mailboxId option:selected').each(function(){
		var signVal = $(this).attr('signId');//获取该邮箱的新邮件的签名ID
		var replySignVal = $(this).attr('replySignId');//获取该邮箱的转发/回复邮件的签名ID
		$("#mailBoxModifyDate").val($(this).attr('modifyDate'));//获取该邮箱的modifyDate
		//循环判断 确定哪个被选中
		if(signVal != null && signVal !='null'){
			$('#signId option').each(function(){
				if($(this).val() == signVal){
					$(this).prop("selected",true);
				}
			});
		}else{
			$("#signId").val("");
		}
		if(replySignVal != null && replySignVal !='null'){
			$('#replySignId option').each(function(){
				if($(this).val() == parseInt(replySignVal)){
					$(this).prop("selected",true);
				}
			});
		}else{
			$("#replySignId").val("");
		}
	});
};

//初始化
jQuery(function($) 
{
	$("#showAddDiv").click(mailSign.showAddDiv);
	$("#showAddDiv_t").click(mailSign.showAddDiv);
	$("#savaAndClose").click(function(){mailSign.savaOrModify(true);});
	$("#savaOrModify").click(function(){mailSign.savaOrModify(false);});
	$("#save_setDefault").click(function(){mailSign.save_setDefault();});//保存邮箱设置默认签名
	 $("i[data-toggle='tooltip']").tooltip();
	//初始化下拉数据
	mailSign.loadSign("list");
	mailSign.initMailboxList();
	mailSign.initMailSignList();
	//切换邮箱触发事件
	$('#mailboxId').change(function(){
		mailSign.change();
	});
	editor = UE.getEditor("signContent",{
		initialFrameWidth : "100%",
		initialFrameHeight:240,
		maximumWords:300,
		autoHeightEnabled:false,
		toolbars: [
		           ['source', 'undo', 'redo'],
		           ['fontfamily','fontsize','bold', 'italic', 'underline', 'fontborder', 'strikethrough', 'superscript', 
		            'subscript', 'removeformat', 'formatmatch', 'autotypeset', 'blockquote', 'pasteplain', 
		            '|', 'forecolor', 'backcolor', 'insertorderedlist', 'insertunorderedlist', 'selectall', 'cleardoc']
		       ],
		       insertorderedlist:{        
	                'decimal' : '' 
	            },//有序列表
	           insertunorderedlist : {          
	           'disc' : '',    // '● 小圆点'
	             } 
	///,'insertimage'
	});
	
});
