var out={};
out.otherDeptCount = 1;
out.pageRows = null;
//重复提交标志位初始化,false时允许提交表单
out.subState = false;

//判断发送的用户是否存在电话号,如果存在提示是否继续发送,如果是执行保存方法.如果都存在电话号就直接执行保存方法
out.saveCheck = function(){
//	if(!out.mesFunctionIsOpen()){
//		return;
//	}
	//收件人
	var to = "";
	var externalTo = new Array();
	$.each($("[name = 'outIcArea'] #outSide"),function(i,o){
		externalTo.push(o.value);		
	});
	to = externalTo.join(",");
	$("#outLinkman").val(to);
	//判断内部联系和和外部联系人是否都为空
	if($("#userId-userId").val()==""&&$("#outLinkman").val()==""){
		out.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_OA_IC_085"),
			success:function(){
				fnCall();
			}
		});
		return;
	}
	if ($("#outForm").valid()) {
		var url = getRootPath()+"/ic/out/sendValidate.action?time=" + new Date();
		var formData = $("#outForm").serializeArray();
		//添加其他表单信息
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				if(data.success=="success"){
					out.savaOrModify();
				}else{
					if(data.success == true){
						msgBox.confirm({
							content: data.successMessage,
							success: function(){
								out.savaOrModify();
							}
						});
					}else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
				
			},
			error : function() {
				out.subState = false;
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_OA_IC_004"))
				});
			}
		});
	} else {
		out.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//添加修改公用方法
out.savaOrModify = function () {
	if (out.subState)return;
	out.subState = true;
	
	//TODO 此处为短信定时发送使用暂时去掉 需要时将代码注释去掉即可  start 
	//是否勾选定时发送
//	if($("#s-dsfs").is(':checked')){
//		$("#smmscheduler").val("true");
//	}
	//TODO 此处为短信定时发送使用暂时去掉 需要时将代码注释去掉即可  end
	
	//是否添加签名
	if($("#addName").is(':checked')){
		$("#addName").val("true");
	}
	if ($("#outForm").valid()) {
		$('#dataLoad').fadeIn();
		var url = getRootPath()+"/ic/out/sendAndSave.action?time=" + new Date();
		var formData = $("#outForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});
		//添加其他表单信息
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				out.subState = false;
				$("#token").val(data.token);
				//getToken();
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
						}
					});
					if ($("#id").val() == 0) {
						out.initForm();
						//loadrightmenu("/ic/out/manage.action");
						$('#dataLoad').fadeOut();
					}
				} else {
					$('#dataLoad').fadeOut();
					if(data.labelErrorMessage){
						showErrors("outForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				out.subState = false;
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_OA_IC_004"))
				});
			}
		});
	} else {
		out.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//out.mesFunctionIsOpen = function(){
//	var flag = true;
//	var url = getRootPath()+"/ic/out/mesFunctionIsOpen.action?time=" + new Date();
//	//添加其他表单信息
//	jQuery.ajax({
//		url : url,
//		type : 'POST',
//		async:false,
//		success : function(data) {
//			if(!data.success){
//				msgBox.info({
//					content: data.errorMessage
//				});
//				out.subState = false;
//				flag = false;
//			}
//		},
//	});
//	return  flag;
//};

//添加成功清空表单数据
out.initForm = function(){
	out.addmSgPrefix();
	//清空人员选择树
	selectControl.clearValue("userId-userId");	
	$('#userId').val("");
	$('#outLinkman').val("");
	//$('#dsfs').attr("disabled",true);
	$('#count').html(300);
	$.each($("[name = 'outIcArea'] #outSide"),function(i,o){
		$(o).parent().remove();
		$(o).parent().siblings().remove();
	});
	out.clearForm();
};

//清空表单
out.clearForm = function () {
	$('#outForm')[0].reset();
	hideErrorMessage();
};

//字典项初始化
//out.initDic = function(){
//	dic.fillDics("outForm #sendType", "sendType",false,1);
//};

//重置方法
//out.formReset = function(){
//	out.clearForm();
//	//清空人员选择树
//	selectControl.clearValue("userId-userId");	
//};

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
out.checkLen  =  function (obj)
{
	var namelength = 0;
	if($("#addName").is(':checked')){
		namelength = out.addName();
	}	
	var maxChars = 300-namelength;//最多字符数
	if (obj.value.length > maxChars){
		obj.value = obj.value.substring(0,maxChars);
		if (obj.value.length > maxChars){
			//加此if原因：避免在剩余1个字数时输入回车键显示剩余-1字问题
			obj.value = obj.value.substring(0,maxChars-1);
		}
	}
	var curr = maxChars - obj.value.length;
	$("#outForm #count").html(curr.toString());
};

out.addmSgPrefix = function(){
	$.ajax({
		type : "POST",
		url : getRootPath()+"/ic/out/addmSgPrefix.action",
		dataType:"json",
		success : function(data) {
			if(data){
				$("#text").val(data.mSgPrefixName);
				$("#count").html(300-data.mSgPrefixName.length);
			}else{
				$("#text").val(data.mSgPrefixName);
				$("#count").html(300);
			}
		}
	});
};

//标志位
var flag = 0;
//附带个人姓名
out.addName = function(){
	//个人姓名长度
	var count = 0;
	$.ajax({
		type : "POST",
		url : getRootPath()+"/ic/out/addName.action",
		async:false,
		success : function(data) {
			if(data){
				count=data;
				out.isAddName(count);
			}
		}
	});
	return count;
	
};

out.isAddName = function(count){
	//是否添加签名
	if($("#addName").is(':checked')){
		//得到总字数
		var curr = $("#text").val().length+count;
		//前缀、内容和个人签名总长度
		if(curr>300){
			//最后添加签名将复选框反选
			if(flag ==0){
				msgBox.info({
					content:$.i18n.prop("JC_OA_IC_017"),
				});
				$("#addName").prop("checked",false);
				$("#count").html($("#count").html());
			}
		}else{
			$("#count").html($("#count").html()-count);
			flag = 1;
		}
	}else{
		//flag==1代表是选中状态变成没选中状态
		if(flag == 1){
			flag = 0;
			$("#count").html($("#count").html()-(-count));
			count = -count;
		//初始状态就是没有选中
		}else{
			count = 0;
		}
	}
};

//全角转换为半角函数 
//out.ToCDB = function(str) 
//{ 
//	var tmp = ""; 
//	for(var i=0;i<str.length;i++) 
//	{ 
//		if(str.charCodeAt(i)>65248&&str.charCodeAt(i)<65375) 
//		{ 
//			tmp += String.fromCharCode(str.charCodeAt(i)-65248); 
//		} 
//		else 
//		{ 
//			tmp += String.fromCharCode(str.charCodeAt(i)); 
//		} 
//	} 
//	return tmp ;
//};



//该变输入字数显示
//out.checkSign  =  function (obj)
//{
//	var outLinkMan = out.ToCDB(obj.value);
//	$("#outLinkman").val(outLinkMan);
//};

//定时发送是否选中
//out.changeDate = function(){
//	if(!$("#s-dsfs").is(':checked')){
//		$("#dsfs").val("");
//		$("#dsfs").next().html("");
//		$("#dsfs").removeClass("error");
//	}
//};

//初始化方法按如下结构编写
jQuery(function($){
	
    //初始化内容
	$("#sendMessage").click(function(){out.saveCheck();});
	//清空方法
	//$("#formReset").click(function(){out.formReset();});
	//内容字数递减控制
	$("#text").on("postpaste", function() { out.checkLen (this);}).pasteEvents();
	$("#text").keyup(function() { out.checkLen (this);}); 
	//$("#outLinkman").keyup(function() { out.checkSign (this);}); 
	//$("#outLinkman").on("postpaste", function() {  out.checkSign (this);}).pasteEvents();
	$("#addName").change(out.addName);
	//$("#s-dsfs").change(out.changeDate);
	selectControl.init("controlTree","userId-userId", true, true);//多选人员
	$(".datepicker-input").datepicker();
	//out.initDic();
	//getToken();
	out.addmSgPrefix();
});


