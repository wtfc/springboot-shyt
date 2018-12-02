var suggestReplyDiv = {};
//重复提交标识
suggestReplyDiv.subState = false;
suggestReplyDiv.pageRows = null;

//该变输入字数显示
suggestReplyDiv.checkLen  =  function (obj,flag)
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

//保存回复方法
suggestReplyDiv.saveRepDiv = function (hide) {
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

suggestReplyDiv.closeModelDiv = function(flag){
	if(flag==1){
		$('#myModal-edit').modal('hide');
	}else{
		$('#reply').modal('hide');
	}
	$("#userSelect").hide();
	$("#controlTreeTo").show();
	suggest.clearForm();
	// $("#suggestForm")[0].reset();
	$('#suggestForm #count').html(300);
};

//初始化
jQuery(function($) 
{
	$("#repSave").click(function(){suggestReplyDiv.saveRepDiv(true);});//回复：保存回复
	$("#repClose").click(function(){suggestReplyDiv.closeModelDiv(0);});//关闭

	//内容字数递减控制
	$("#suggestContent").on("postpaste", function() { suggestReplyDiv.checkLen (this,0);}).pasteEvents();
	$("#repContent").on("postpaste", function() { suggestReplyDiv.checkLen (this,1);}).pasteEvents();
	$("#suggestContent").keyup(function() { suggestReplyDiv.checkLen (this,0);}); 
	$("#repContent").keyup(function() { suggestReplyDiv.checkLen (this,1);}); 
	ie8StylePatch();
});
//@ sourceURL=suggestInteract.js