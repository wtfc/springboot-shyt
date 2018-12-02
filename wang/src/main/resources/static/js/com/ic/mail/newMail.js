/**
 * 撰写邮件
 */

var newmail = {};

newmail.intervalID=null;
newmail.subState = false;
newmail.contentEditor=null;
newmail.signStart="<!--signature start --><br/><br/><br/><br/><br/>--------<br />";
newmail.signEnd="<!--signature end-->";
newmail.signRegx=new RegExp("<!--signature start -->.*<!--signature end-->");

//判断页面离开时候使用
newmail.oldTo = null;
newmail.oldCc = null;
newmail.oldBcc = null;
newmail.oldSs = null;
newmail.oldAttach = "";

var oldExternalTo = "";
var oldExternalCc = "";
var oldExternalBcc = "";
var oldExternalSs = "";
var oldContent = "";
newmail.mailTitleState = false;
newmail.attachState = false;
newmail.contentState = false;
//邮箱选择事件
newmail.mailbox=function(id,address){
	ie8StylePatch();
	$('#mailboxId').val(id);
	if(id==1) {
		$('#senderUserId').show();
		$('#senderUserIdText').show();
		$('.internalText').show();
		$('#senderMail').hide();
		$('#senderMailText').hide();
		$('.externalText').hide();
		
		$('#signature').attr("disabled","disabled");
		$('#encryption').removeAttr("disabled");
		if($('#encryption').val()){
			$('#encryptionPass').show();
			$('#confirmPass').show();
		}
		$('#replyTexting').removeAttr("disabled");
		newmail.replyTextingChecked();
		$('#smsAlert').removeAttr("disabled");
		$('#encryptionPass').removeAttr("disabled");
		$('#confirmPass').removeAttr("disabled");
		//设置ueditor 图片按钮可用
		//$("#edui73_state").removeClass("edui-state-disabled");

		// newmail.contentEditor.getAllHtml();
		jQuery.ajax({
			url : getRootPath() + "/ic/mail/querySign.action?t="
					+ (new Date()).getTime(),
			type : 'POST',
			data : {'id' : $('#mailboxId').val()},
			dataType : 'json',
			success : function(data) {
				if (data != null) {
					var replySign ="";
					var sign = "";
					if(data.replySign !=null && data.replySign !=''){
						replySign = newmail.signStart + data.replySign.signContent;
					}
					if(data.sign !=null && data.sign !=''){
						sign = newmail.signStart + data.sign.signContent;
					}
					if ($("#isNew").val() == 1) {
						newmail.contentEditor.ready(function() {
							newmail.contentEditor.setContent("", false);
							newmail.contentEditor.setContent(replySign
								+ newmail.signEnd, true);
							});
						
					} else if ($("#isNew").val() == 2) {
						
					} else {
						newmail.contentEditor.ready(function() {
							newmail.contentEditor.setContent("", false);
							newmail.contentEditor.setContent(sign
									+newmail.signEnd, true);
							 //原始附件数量
						    newmail.oldAttach = $("#attachList li").length;
						    newmail.contentEditor.ready(function() {
							  oldContent = newmail.contentEditor.getContent();
							});
						});
					}
				} else {
					$("#mailContent").val(' ');
				}
				
			}
		});
	} else {
		$('#senderUserId').hide();
		$('#senderUserIdText').hide();
		$('.internalText').hide();
		$('.externalText').show();
		$('#senderMail').show();
		$('#senderMailText').show();
		$('#senderMail').val(address);
		$('#senderMailText').html(address);
		
		$('#signature').removeAttr("disabled");
		$('#encryption').attr("disabled","disabled");
		$('#encryption').removeAttr("checked");
		$('#encryptionPass').val("");
		$('#encryptionPass').attr("disabled","disabled");
		$('#confirmPass').val("");
		$('#confirmPass').attr("disabled","disabled");
		$('#replyTexting').attr("disabled","disabled");
		$('#replyTextingTime').attr("disabled","disabled");
		$('#smsAlert').attr("disabled","disabled");
		//设置ueditor图片功能按钮不可用
		//$("#edui73_state").addClass("edui-state-disabled");
		if ($("#isNew").val() == 1) {
			newmail.contentEditor.ready(function() {
				newmail.contentEditor.setContent("", false);
				newmail.signature();
				});
		} else if ($("#isNew").val() == 2) {
			
		} else {
			newmail.contentEditor.ready(function() {
				newmail.contentEditor.setContent("", false);
				newmail.signature();
				 //原始附件数量
			    newmail.oldAttach = $("#attachList li").length;
			    newmail.contentEditor.ready(function() {
				  oldContent = newmail.contentEditor.getContent();
				});
				});
		}
	}
	$('#selected-mailbox').html(address);
	ie8StylePatch();
};

newmail.sendMail = function(userIds){
	
	if(!$(".email-qfdx").is(":visible")){
		//非群发单显
		select2InitEchoToPerson("mailForm #innerSs-innerSs",null);
	}
	else{
		select2InitEchoToPerson("mailForm #innerTo-innerTo",null);
		select2InitEchoToPerson("mailForm #innerCc-innerCc",null);
		select2InitEchoToPerson("mailForm #innerBcc-innerBcc",null);
	}
	var mailboxId = $("#mailboxId").val();
	if(mailboxId != 1){
		newmail.externalvalidate();
	}
	if ($("#mailForm").valid()) {
		if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
			window.parent.valPageSave.value = "false";
		}
		if(mailboxId != 1){
			$("#toValid").val("");
			$("#showSingleValid").val();
		}
		var url = getRootPath()+"/ic/mail/sendMail.action?time=" +(new Date()).getTime();
		newmail.contentEditor.sync('mailForm');
		var formData = $('#mailForm').serializeArray();
		newmail.addAttachParams(formData);	
		if(mailboxId != 1){
			newmail.externalUser(formData);
		}

		formData.push({"name": "smsReceiver", "value": userIds});
		clearInterval(newmail.intervalID);
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: $.i18n.prop("JC_OA_IC_001"),
						callback:function(){
							newmail.back("/ic/mail/manage.action/3");
						}
					});
					$("#dataLoad_mail").fadeOut();
				} else {
					if(data.labelErrorMessage){
						showErrors("mailForm", data.labelErrorMessage);
					} else if (data.errorMessage) {
						msgBox.tip({
								content: data.errorMessage
						});
					} else {
						msgBox.tip({
							content:$.i18n.prop("JC_OA_IC_002"),
							type:"fail"
						});
					}
					$("#dataLoad_mail").fadeOut();
				}
			},
			error : function() {
				newmail.subState = false;
				msgBox.tip({
					content:$.i18n.prop("JC_OA_IC_002"),
					type:"fail"
				});
				$("#dataLoad_mail").fadeOut();
			}
		});
	}else {
		$("#dataLoad_mail").fadeOut();
		newmail.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};
newmail.send=function(){
	$("#dataLoad_mail").fadeIn();
	var mailboxId = $("#mailboxId").val();
	if(!$(".email-qfdx").is(":visible")){
	if(mailboxId!=1){
		if( typeof $("[name = 'externalTo'] #outSide").val() == "undefined" || $("[name = 'externalTo'] #outSide").val() == null){
			$("#dataLoad_mail").fadeOut();
			msgBox.tip({
				content:$.i18n.prop("JC_OA_IC_081"),
				type:"fail"
			});
			return;
		}
	}
	else{
       if($.trim($("#innerTo-innerTo").val()) == "" && $.trim($("#innerTo-innerTo").val())==""){			
    	   $("#dataLoad_mail").fadeOut();
    	   msgBox.tip({
				content:$.i18n.prop("JC_OA_IC_081"),
				type:"fail"
			});
    	   return;
		}
	}
	}
	if($.trim($("#mailForm #mailTitle").val()) == ""){
		$("#dataLoad_mail").fadeOut();
		msgBox.confirm({
			content: $.i18n.prop("JC_OA_IC_061"),
			success: function(){
				$("#dataLoad_mail").fadeIn();
				newmail.isHaveMessage();
			}
		});
	}else{
		newmail.isHaveMessage();
	}
};

newmail.isHaveMessage = function(){
	var userIds = "";
	//如果是群发单显只有一个人员选择树，不需要去重
	if($("#innerSs-innerSs").val()!=null&&$("#innerSs-innerSs").val()!=""){
		userIds = $("#innerSs-innerSs").val();
	//如果不是群发单显则接受用户可能是收件人、抄送人和密送人
	}else{
		if($("#innerTo-innerTo").val()!=null&&$("#innerTo-innerTo").val()!=""){
			userIds +=	$("#innerTo-innerTo").val();	
		}
		if($("#innerCc-innerCc").val()!=null&&$("#innerCc-innerCc").val()!=""){
			if(userIds!=""){
				userIds +=","+$("#innerCc-innerCc").val();	
			}else{
				userIds +=$("#innerCc-innerCc").val();
			}
			
		}
		if($("#innerBcc-innerBcc").val()!=null&&$("#innerBcc-innerBcc").val()!=""){
			if(userIds!=""){
				userIds +=","+$("#innerBcc-innerBcc").val();	
			}else{
				userIds +=$("#innerBcc-innerBcc").val();
			}
		}
		
		
		var arr = userIds.split(",");
		var newarr = new Array();
		var flag = true;
		//去掉重复用户
		for(var i=0;i<arr.length;i++){
			flag = true;
			if(i==0){
				newarr.push(arr[i]);
				continue;
			}else{
				for(var j=0;j<newarr.length;j++){
					//如果newarr数组中已存在该数据flag设为false，不在添加该数据
					if(arr[i]==newarr[j]){
						flag = false;
						break;
					}
				}
				if(flag){
					newarr.push(arr[i]);
				}
			}
			
		}
		userIds = newarr.toString();
		
	}
	if($('input[name="smsAlert"]:checked').val()==1){
		jQuery.ajax({
			url : getRootPath()+"/ic/mail/validRemind.action?time=" + new Date(),
			type : 'get',
			async: false,
			dataType : "json",
			data:{'userIds':userIds},
			success : function(data) {
				if(data.success=="success"){
					newmail.sendMail(userIds);
				}else{
					if(data.success){
						$("#dataLoad_mail").fadeOut();
						msgBox.confirm({
							content: data.successMessage,
							success: function(){
								$("#dataLoad_mail").fadeIn();
								newmail.sendMail(userIds);
							},
							cancel:function(){
							}
						});
						
					}else{
						$("#dataLoad_mail").fadeOut();
						msgBox.info({
							content: data.errorMessage
						});
					} 
				}
			},
			error : function() {
				$("#dataLoad_mail").fadeOut();
				msgBox.tip({
					type:"fail",
					content:$.i18n.prop("JC_OA_PO_015"),
					callback:function(){
					}
				});
			}
		});
	}else{
		newmail.sendMail(userIds);
	}
};

//返回列表页
newmail.back = function(url) {
	if($('#id')==""){
		newmail.deleteAttach();
	}
	//如果是内嵌窗口，会有这个方法
	if($('#open').val()!="true"){
		loadrightmenu(url,undefined,url);
	}
	else{
		window.close();
	}
};
newmail.backToInbox=function(){
	newmail.clearAttachNew();
	var url=$('#returnURL').val();
	if(url==""){
		if($("#folderId").val()==2){
			url="/ic/mail/manage.action/2";
		}else{
			url="/ic/mail/manage.action/1";
		}
	}
	if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
		if(window.parent.valPageSave.value != "true"){
			newmail.back(url);
		}
	else{
		 window.parent.valPageSave.value = "false";
		var opt = {
				content:$.i18n.prop("JC_OA_IC_096"),
				success:function(){
					newmail.save();
					//刷新左侧菜单
					loadrightmenu(url,'',url);
					if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
					window.parent.valPageSave.value = "false";
					}
				},
				noback:function(){
					//刷新左侧菜单
					loadrightmenu(url,'',url);
					
				},
				cancel:function(){
					if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
						window.parent.valPageSave.value = "true";
					}
				},
				buttons:{"离开并存草稿":"yes", "取消":"no","离开":"cancel"}
			};
		msgBox.confirm(opt);
	   }
	}		
};

//比较标题，不为空时离开页面提示
//IE8及以上浏览器支持
var oldTitle =$("#mailTitle").val();
newmail.OnInput = function (event) {
	if(oldTitle != event.target.value){
			newmail.mailTitleState = true;
	}
	else{
			newmail.mailTitleState = false;
	}
	newmail.compareAll();
};
//IE8
newmail.OnPropChanged =  function (event) {
	if(oldTitle != event.srcElement.value ){
			newmail.mailTitleState = true;
	}
	else{
			newmail.mailTitleState = false;
	}
	newmail.compareAll();
};

//比较全部内容是否改动
newmail.compareAll = function(){
	//内容
	var newContent = UE.getEditor('mailContent').getContent();
	if(newContent != "" &&  newContent !=null){
		newContent = newContent.replace(/<[^>]+>/g,"");
		newContent = newContent.replace(/&nbsp;/ig, "");
		newContent = newContent.replace(/\s+/g,"");
	}
	if(oldContent != "" &&  oldContent !=null){
		oldContent = oldContent.replace(/<[^>]+>/g,"");
		oldContent = oldContent.replace(/&nbsp;/ig, "");
		oldContent = oldContent.replace(/\s+/g,"")
	}
	//收件人
	var newTo = returnValue("innerTo-innerTo");
	//抄送
	var newCc = returnValue("innerCc-innerCc");
	//密送
	var newBcc = returnValue("innerBcc-innerBcc");
	//群发单显
	var newSs = returnValue("innerSs-innerSs");
	//外部收件人
	var newExternalTo = "";
		$.each($("[name = 'externalTo'] #outSide"),function(i,o){
			newExternalTo +=o.value+","	
		});
	//外部抄送	
	var newExternalCc = "";
		$.each($("[name = 'externalCc'] #outSide"),function(i,o){
			newExternalCc +=o.value+","	
		});
	//外部密送
	var newExternalBcc = "";
		$.each($("[name = 'externalBcc'] #outSide"),function(i,o){
			newExternalBcc +=o.value+","	
		});
	//外部群发单显	
	var newExternalSs = "";
		$.each($("[name = 'externalShowSingle'] #outSide"),function(i,o){
			newExternalSs +=o.value+","	
		});
	if(newContent != oldContent || newmail.oldTo != newTo || newmail.mailTitleState  || newmail.attachState || newmail.oldCc != newCc || newmail.oldBcc != newBcc || newmail.oldSs != newSs || oldExternalTo != newExternalTo || oldExternalCc != newExternalCc || oldExternalBcc != newExternalBcc || oldExternalSs != newExternalSs){
		window.parent.valPageSave.value = "true";
	}
	else{
		window.parent.valPageSave.value = "false";
	}
}
//存草稿
newmail.save=function(){
	if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
		window.parent.valPageSave.value = "false";
	}
	var url =getRootPath()+ "/ic/mail/save.action?time=" + (new Date()).getTime();
	
	newmail.contentEditor.sync('mailForm');
	var formData = $('#mailForm').serializeArray();
	var mailboxId = $("#mailboxId").val();
	if(mailboxId != 1){
	newmail.externalUser(formData);
	}
	newmail.addAttachParams(formData);
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : formData,
		success : function(data) {      
           if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_062"),
					callback:function(){
						newmail.back("/ic/mail/manage.action/2");
					}
				});
				clearInterval(newmail.intervalID);
			}else {
				if(data.labelErrorMessage){
					showErrors("mailForm", data.labelErrorMessage);
				} else if (data.errorMessage) {
					msgBox.tip({
							content: data.errorMessage
					});
				} else {
					msgBox.tip({
						content:$.i18n.prop("JC_OA_IC_063"),
						type:"fail"
					});
				}
			}
		},
		error : function() {
			newmail.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_OA_IC_063"),
				type:"fail"
			});
		}
	});
};



//自动保存
newmail.autosave=function(){
var url = getRootPath()+"/ic/mail/save.action?time=" + (new Date()).getTime();
	
	newmail.contentEditor.sync('mailForm');
	var formData = $('#mailForm').serializeArray();
	var mailboxId = $("#mailboxId").val();
	if(mailboxId != 1){
	newmail.externalUser(formData);
	}
	newmail.addAttachParams(formData);
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : formData,
		success : function(data) {
			if(data.success == "true"){		
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_062")
				});
				$('#id').val(data.id);
			} else {
				if(data.labelErrorMessage){
					showErrors("mailForm", data.labelErrorMessage);
				} else if (data.errorMessage) {
					msgBox.tip({
							content: data.errorMessage
					});
				} else {
					msgBox.tip({
						content:$.i18n.prop("JC_OA_IC_063"),
						type:"fail"
					});
				}
			}
		}
	});
};

/**
 * 插入签名
 */
newmail.signature=function(){
		//得到样式
		var content=newmail.contentEditor.getAllHtml();
		jQuery.ajax({
				url : getRootPath()+"/ic/mail/getSign.action?t="+(new Date()).getTime(),
				type : 'POST',
				data : {'id':$('#mailboxId').val()},
				dataType:'json',
				success : function(data) {
					if(data!= null){
						//$("#isNew").val()==1为回复/全部回复/转发
						if($("#isNew").val()==1){
							//没设置此邮箱回复/转发签名
							if(data.replySign==null){
								/*msgBox.info({
									content:$.i18n.prop("JC_OA_IC_071","回复/转发")
								});*/
							//已设置签名
							}else{
								//已设置签名但内容为空
								if(data.replySign.signContent==""){
									/*msgBox.info({
										content:$.i18n.prop("JC_OA_IC_072","回复/转发")
									});*/
								//已设置签名内容不为空将签名添加到UEditor中
								}else{
//									newmail.contentEditor.setContent(content+newmail.signStart+data.replySign.signContent+newmail.signEnd);
									newmail.contentEditor.setContent(newmail.signStart+data.replySign.signContent+newmail.signEnd, true);
								}
							}
						//直接写邮件
						}else{
							//没设置此邮箱新增签名
							if(data.sign==null){
								/*msgBox.info({
									content:$.i18n.prop("JC_OA_IC_071","默认")
								});*/
							//已设置签名
							}else{
								//已设置签名但内容为空
								if(data.sign.signContent==""){
									/*msgBox.info({
										content:$.i18n.prop("JC_OA_IC_072","新增")
									});*/
								//已设置签名内容不为空将签名添加到UEditor中
								}else{
//									newmail.contentEditor.setContent(content+newmail.signStart+data.sign.signContent+newmail.signEnd);
									newmail.contentEditor.setContent(newmail.signStart+data.sign.signContent+newmail.signEnd, true);
								}
							}
							
						}
					} 
				}
		});
		//切换邮件设置内容
		oldContent = newmail.contentEditor.getContent();
		newmail.compareAll();
};

//添加附件
newmail.addAttachParams = function (formData){
	var fileids = new Array();
	
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	if($("#attachList li").length>0){
		formData.push({"name": "isHaveAttach", "value":1});
	}else{
		formData.push({"name": "isHaveAttach", "value":0});
	}
	var files = "";
	if(fileids.length==0){
		files = $("#files").val();
		formData.push({"name": "fileids", "value": files});
	}else{
		formData.push({"name": "fileids", "value": fileids});
	}
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};

//清空附件列表
newmail.clearAttach = function(){
	newmail.fileupload();
	var newAttach = $("#attachList li").length;
	if(newmail.oldAttach != newAttach ){
			newmail.attachState = true;
	}else{
			newmail.attachState =false;
	}
	newmail.compareAll();
};
//清空附件列表(新)
newmail.clearAttachNew = function(){
	var newAttach = $("#attachList li").length;
	if(newmail.oldAttach != newAttach ){
			newmail.attachState = true;
	}else{
			newmail.attachState =false;
	}
	newmail.compareAll();
};
//后台传入参数：收件人、密送人、抄送人、群发单显
newmail.externalUser = function (formData){
	//收件人
	var to = "";
	var externalTo = new Array();
	$.each($("[name = 'externalTo'] #outSide"),function(i,o){
		externalTo.push(o.value);		
	});
	to = externalTo.join(",");
	
	//群发单显
	var showSingle = "";
	var ss = new Array();
	$.each($("[name = 'externalShowSingle'] #outSide"),function(i,o){
		ss.push(o.value);		
	});
	showSingle = ss.join(",");
	//抄送人
	var cc = "";
	var externalcc = new Array();
	$.each($("[name = 'externalCc'] #outSide"),function(i,o){
		externalcc.push(o.value);		
	});
	cc = externalcc.join(",");
	
	//密送人
	var bcc = "";
	var externalbcc = new Array();
	$.each($("[name = 'externalBcc'] #outSide"),function(i,o){
		externalbcc.push(o.value);		
	});
	bcc = externalbcc.join(",");
	formData.push({"name": "to", "value": to});
	formData.push({"name": "showSingle", "value": showSingle});
	formData.push({"name": "cc", "value": cc});
	formData.push({"name": "bcc", "value": bcc});
};

//后台传入参数：收件人、密送人、抄送人、群发单显
newmail.externalvalidate = function (){
	//收件人
	var to = "";
	var externalTo = new Array();
	$.each($("[name = 'externalTo'] #outSide"),function(i,o){
		externalTo.push(o.value);		
	});
	to = externalTo.join(",");
	
	//群发单显
	var showSingle = "";
	var ss = new Array();
	$.each($("[name = 'externalShowSingle'] #outSide"),function(i,o){
		ss.push(o.value);		
	});
	showSingle = ss.join(",");
	if(showSingle==""){
		if(to!=""){
			$("#toValid").val(to);
		}else{
			$("#toValid").val("");
		}
	}else{
		$("#showSingleValid").val(showSingle);
	}
};

//撰写新邮件不保存时应该删除附件
newmail.deleteAttach = function() {
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
			
		}
	});
};
//退出本页面前回调
function pageRedirecting(){
	clearInterval(newmail.intervalID);
//	if(newmail.contentEditor)
//	{
//		newmail.contentEditor.destroy();
//	}
};
//群发单显切换
/**
 * @TODO 这个方法中使用Hack的方式操作了人员选择树
 * 使用了人员选择树的内部方法：returnValue("")和 select2InitEchoToPerson("" ,obj );
 */
newmail.switchSingleShow=function(){
		hideErrorMessage();
        $(".email-cs-ms").toggle();
        $(".email-btn-group").toggle();
        $(".email-sjr").toggle();
        $(".email-qfdx").toggle();
       if(!$(".email-qfdx").is(":visible")){
    	   $(this).text("使用群发单显");
    	   //同步 群发单显 的人到收件人中
    	   var ss=returnValue("innerSs-innerSs");
    	   if(ss!=null && ss!="undefined:undefined"){
    		   var kvArray="[";
    		   $.each(ss.split(","),function(i,n){
     			  kvArray+=("{id:"+n.split(":")[0]+",text:\""+n.split(":")[1]+"\"}");
     			  kvArray+=",";
     		  }); 
    		   var lastIndex = kvArray.lastIndexOf(',');
				if (lastIndex > -1) {
					kvArray = kvArray.substring(0, lastIndex) + kvArray.substring(lastIndex + 1, kvArray.length);
				}
    		   kvArray+="]";
         	  
        	   select2InitEchoToPerson("mailForm #innerTo-innerTo",eval(kvArray));
        	   //所有收件人已经从群发单显同步到“收件人”，可以清空抄送、密送
        	   select2InitEchoToPerson("mailForm #innerCc-innerCc",null);
        	   select2InitEchoToPerson("mailForm #innerBcc-innerBcc",null);
        	   //清空群发
        	   select2InitEchoToPerson("mailForm #innerSs-innerSs",null);
    	   }
    	   
    	   $("#externalTo").html($("#externalShowSingle").html());
    	   $("#externalShowSingle").empty();
    	   outSideUser.initInputEvent("externalTo");
    	   
    	   $("#externalCc").append("<li class='select2-search-field email-list-shou'><input type='text' class='email-temp-input'></li>");
    	   outSideUser.initInputEvent("externalCc");
    	   $("#externalBcc").append("<li class='select2-search-field email-list-shou'><input type='text' class='email-temp-input'></li>");
    	   outSideUser.initInputEvent("externalBcc");
    	  // $('#to').val($('#showSingle').val());
    	  // $('#showSingle').val("");
       }
       else{
    	   $(this).text("取消群发单显");
    	   hideErrorMessage();
    	  //同步”收件人、抄送，密送”到群发单显
    	   
    	  // if(returnValue("innerSs-innerSs")==null ||returnValue("innerSs-innerSs")=="undefined:undefined") { 注：此行代码是控制在第一次选择群发单显的时候同步数据
    	   var kvArray="[";
     	   var to=new String(returnValue("innerTo-innerTo"));
	 		  if(to!=null && to!="undefined:undefined"&&to!="null"){
	 			  $.each(to.split(","),function(i,n){
	     			  kvArray+=("{id:"+n.split(":")[0]+",text:\""+n.split(":")[1]+"\"}");
	     			  kvArray+=",";
	     		  });
	 		  }
	 		 
	 		  var cc=returnValue("innerCc-innerCc");
	 		  if(cc!=null && cc!="undefined:undefined"&&cc!="null"){
		    		  $.each(cc.split(","),function(i,n){
		    			  kvArray+=("{id:"+n.split(":")[0]+",text:\""+n.split(":")[1]+"\"}");
		    			  kvArray+=",";
		    		  });
	 		  }
	 		  var bcc=returnValue("innerBcc-innerBcc");
	 		  if(bcc!=null && bcc!="undefined:undefined"&&bcc!="null"){
		    		  $.each(bcc.split(","),function(i,n){
		    			  kvArray+=("{id:"+n.split(":")[0]+",text:\""+n.split(":")[1]+"\"}");
		    			  kvArray+=",";
		    		  });
	 		  }
		 		    var lastIndex = kvArray.lastIndexOf(',');
						if (lastIndex > -1) {
							kvArray = kvArray.substring(0, lastIndex) + kvArray.substring(lastIndex + 1, kvArray.length);
						}			
	 		  kvArray+="]"; 
	    	   select2InitEchoToPerson("mailForm #innerSs-innerSs",eval(kvArray));
	    	   //所有人员已经同步到群发单显，清空收件人、抄送、密送
	    	   select2InitEchoToPerson("mailForm #innerTo-innerTo",null);
	    	   select2InitEchoToPerson("mailForm #innerCc-innerCc",null);
	    	   select2InitEchoToPerson("mailForm #innerBcc-innerBcc",null);
    	   //}
	    	   
	    	   
	    	   $("#externalTo").find("input[type='text']").remove();
	    	   var externalToHtml = $("#externalTo").html();
	    	   
	    	   $("#externalCc").find("input[type='text']").remove();
	    	   var externalCcHtml = $("#externalCc").html();
	    
	    	   
	    	   $("#externalBcc").find("input[type='text']").remove();
	    	   var externalBccHtml = $("#externalBcc").html();
	    	   
	    	   var ss="";
	    	   if(externalToHtml != null && externalToHtml != ""){
	    		   ss+=externalToHtml;
	    		   $("#externalTo").empty();
	    	   }
	    	   if(externalCcHtml != null && externalCcHtml != ""){
	    		   ss+=externalCcHtml;
	    		   $("#externalCc").empty();
	    	   }
	    	   if(externalBccHtml != null && externalBccHtml != ""){	    		  
	    		   ss+=externalBccHtml;
	    		   $("#externalBcc").empty();
	    	   }
	    	   $("#externalShowSingle").html(ss);
	    	   
	    	   //删除数据库无记录重复的显示项
	    	   var deleteArray = new Array();
	    	   var deleteRepeat = $("#externalShowSingle").find("span[name^='i']");
	    	   for(var i = 0 ; i < deleteRepeat.length; i ++){
	    		   var index = 0;
	    		   for(var x = 0 ; x < deleteRepeat.length; x ++){
	    			   if($(deleteRepeat.get(i)).attr("name").substring(2) == $(deleteRepeat.get(x)).attr("name").substring(2)){
	    				   index = index + 1;
	    			   }
	    		   }
	    		   if(index > 1){
	    			   var deleteItem = ($("span[name$="+$(deleteRepeat.get(i)).attr("name").substring(2)+"]"));
	    			   if(deleteItem.length > 1){
	    				   $($(deleteItem.get(1)).remove());
	    			   }
	    		   }
	    	   }
	    	   //删除数据库记录重复的显示项
	    	   deleteArray = new Array();
	    	   deleteRepeat = $("#externalShowSingle").find("span[id]");
	    	   for(var i = 0 ; i < deleteRepeat.length; i ++){
	    		   var index = 0;
	    		   for(var x = 0 ; x < deleteRepeat.length; x ++){
	    			   if($(deleteRepeat.get(i)).attr("id") == $(deleteRepeat.get(x)).attr("id")){
	    				   index = index + 1;
	    			   }
	    		   }
	    		   if(index > 1){
	    			   var deleteItem = ($("span[id="+$(deleteRepeat.get(i)).attr("id")+"]"));
	    			   if(deleteItem.length > 1){
	    				   $($(deleteItem.get(1)).remove());
	    			   }
	    		   }
	    	   }
	    	   
	    	   if($("#externalShowSingle").find("span").length > 0){
	    		   $($("#externalShowSingle").find("span").get($(ss).find("span").length-1)).after("<input type='text' class='email-temp-input'>");
	    		   outSideUser.initInputEvent("externalShowSingle");
	    	   }else{
	    		   $("#externalShowSingle").append("<li class='select2-search-field email-list-shou'><input type='text' class='email-temp-input'></li>");
	    		   outSideUser.initInputEvent("externalShowSingle");
	    	   }
       }
};

//回复邮件提醒事件
newmail.replyTextingChecked = function (){
	if($("input[name='replyTexting']:checked").val() == 1){
		$('#replyTextingTime').removeAttr("disabled");
	}
	else{
		$('#replyTextingTime').attr("disabled","disabled");
		$('#replyTextingTime').val(0);
		$('#replyTextingTime').removeClass("error");
		$("#replyTextingTime").next().html("0");
	}
};

//回复、转发外部联系人回显
 newmail.getReplyMail = function() {
    var to = $("#externalto").val();
    var cc = $("#externalcc").val();
    var bcc = $("#externalbcc").val();
    var ss = $("#externalss").val();
    //收件人
    if(to!=null && to!="undefined:undefined:undefined"&&to!="null" && to!=""){
	var externalTo =eval(to);
	var appendHtml="";
	  for(var i=0;i<externalTo.length;i++){
		  if(externalTo[i].contactsId !=null && externalTo[i].contactsId !="" && externalTo[i].contactsId !="undefined"){
			    var addHtml = getOutSideUserReply(externalTo[i].contactsId);
				appendHtml  += "<li class='select2-search-field'><span name='ench' class='select2-search-choice' id='"+externalTo[i].contactsId+"'><input type='hidden' id='outSide' name='outSide' value='"+externalTo[i].receiveMail+"'>"
				$("[name = 'externalTo'] #outSide").remove();
				appendHtml += "<a style='color: #c30 !important;font-family: verdana;' onclick=outSideUser.removeParent(this) >x&nbsp;</a><font>"+externalTo[i].userName+"</font>&nbsp;"+addHtml+"&nbsp;";
				$("#externalTo").find("input[type='text']").remove();
				appendHtml += "</span></li>";
		  }
		  else{
			    var nameIndex = "i" + outSideUser.index++;
			    var addHtml = getOutSideUserReplyO(externalTo[i].receiveMail,nameIndex);
			    var str = "email-list-shou";
			    if(!addHtml) str = "";
				appendHtml += "<li class='select2-search-field "+str+"'><span name="+nameIndex+""+externalTo[i].receiveMail+" ondblclick=outSideUser.editAnyOneUser(this) class='select2-search-choice' ><input type='hidden' id='outSide' name='outSide' value='"+externalTo[i].receiveMail+"'>"
				appendHtml += "<a style='color: #c30 !important;font-family: verdana;' onclick=outSideUser.removeParent(this) >x&nbsp;</a><font>" + externalTo[i].receiveMail + "</font>&nbsp;"+addHtml+"&nbsp;";
				$("#externalTo").find("input[type='text']").remove();
				appendHtml += "</span></li>";
		  }
      }
	  $("#externalTo").append(appendHtml);
	  
	  if($("#externalTo").find("span").length > 0){
 		   $($("#externalTo").find("span").get($("#externalTo").find("span").length-1)).after("<input type='text' class='email-temp-input'>");
 		   outSideUser.initInputEvent("externalTo");
 	   }else{
 		   $("#externalTo").append("<input type='text' class='email-temp-input'>");
 		   outSideUser.initInputEvent("externalTo");
 	   }
	  $.each($("[name = 'externalTo'] #outSide"),function(i,o){
			oldExternalTo += o.value+",";
		});
     }
    //抄送
    if(cc!=null && cc!="undefined:undefined:undefined"&&cc!="null" && cc!=""){
    	var externalCc =eval(cc);
    	var appendHtml="";
    	  for(var i=0;i<externalCc.length;i++){
    		  if(externalCc[i].contactsId !=null && externalCc[i].contactsId !="" && externalCc[i].contactsId !="undefined"){
    	   			 var addHtml = getOutSideUserReply(externalCc[i].contactsId);
    	   				appendHtml  += "<li class='select2-search-field'><span name='ench' class='select2-search-choice' id='"+externalCc[i].contactsId+"'><input type='hidden' id='outSide' name='outSide' value='"+externalCc[i].receiveMail+"'>"
    	   				$("[name = 'externalCc'] #outSide").remove();
    	   				appendHtml += "<a style='color: #c30 !important;font-family: verdana;' onclick=outSideUser.removeParent(this) >x&nbsp;</a><font>"+externalCc[i].userName+"</font>&nbsp;"+addHtml+"&nbsp;";
    	   				$("#externalCc").find("input[type='text']").remove();
    	   				appendHtml += "</span></li>";
    	   		  }
    	   		  else{
    	   			    var nameIndex = "i" + outSideUser.index++;
    	   			    var addHtml = getOutSideUserReplyO(externalCc[i].receiveMail,nameIndex);
    	   			    var str = "email-list-shou";
    				    if(!addHtml) str = "";
    	   				appendHtml += "<li class='select2-search-field "+str+"'><span name="+nameIndex+""+externalCc[i].receiveMail+" ondblclick=outSideUser.editAnyOneUser(this) class='select2-search-choice' ><input type='hidden' id='outSide' name='outSide' value='"+externalCc[i].receiveMail+"'>"
    	   				appendHtml += "<a style='color: #c30 !important;font-family: verdana;' onclick=outSideUser.removeParent(this) >x&nbsp;</a><font>" + externalCc[i].receiveMail + "</font>&nbsp;"+addHtml+"&nbsp;";
    	   				$("#externalCc").find("input[type='text']").remove();
    	   				appendHtml += "</span></li>";
    	      }
          }
    	  $("#externalCc").append(appendHtml);
    	  if($("#externalCc").find("span").length > 0){
     		   $($("#externalCc").find("span").get($("#externalCc").find("span").length-1)).after("<input type='text' class='email-temp-input'>");
     		   outSideUser.initInputEvent("externalCc");
     	   }else{
     		   $("#externalCc").append("<input type='text' class='email-temp-input'>");
     		   outSideUser.initInputEvent("externalCc");
     	   }
    	   $.each($("[name = 'externalCc'] #outSide"),function(i,o){
    		 oldExternalCc += o.value+",";
  		   });
       }
    
    //密送
    if(bcc!=null && bcc!="undefined:undefined:undefined"&&bcc!="null" && bcc!=""){
    	var externalBcc =eval(bcc);
    	var appendHtml="";
    	  for(var i=0;i<externalBcc.length;i++){
			  if(externalBcc[i].contactsId !=null && externalBcc[i].contactsId !="" && externalBcc[i].contactsId !="undefined"){
	 			    var addHtml = getOutSideUserReply(externalBcc[i].contactsId);
	 				appendHtml  += "<li class='select2-search-field'><span name='ench' class='select2-search-choice' id='"+externalBcc[i].contactsId+"'><input type='hidden' id='outSide' name='outSide' value='"+externalBcc[i].receiveMail+"'>"
	 				$("[name = 'externalBcc'] #outSide").remove();
	 				appendHtml += "<a style='color: #c30 !important;font-family: verdana;' onclick=outSideUser.removeParent(this) >x&nbsp;</a><font>"+externalBcc[i].userName+"</font>&nbsp;"+addHtml+"&nbsp;";
	 				$("#externalBcc").find("input[type='text']").remove();
	 				appendHtml += "</span></li>";
	 		  }
	 		  else{
	 			    var nameIndex = "i" + outSideUser.index++;
	 			    var addHtml = getOutSideUserReplyO(externalBcc[i].receiveMail,nameIndex);
	 			    var str = "email-list-shou";
				    if(!addHtml) str = "";
	 				appendHtml += "<li class='select2-search-field "+str+"'><span name="+nameIndex+""+externalBcc[i].receiveMail+" ondblclick=outSideUser.editAnyOneUser(this) class='select2-search-choice' ><input type='hidden' id='outSide' name='outSide' value='"+externalBcc[i].receiveMail+"'>"
	 				appendHtml += "<a style='color: #c30 !important;font-family: verdana;' onclick=outSideUser.removeParent(this) >x&nbsp;</a><font>" + externalBcc[i].receiveMail + "</font>&nbsp;"+addHtml+"&nbsp;";
	 				$("#externalBcc").find("input[type='text']").remove();
	 				appendHtml += "</span></li>";
	 		  }
          }
    	  $("#externalBcc").append(appendHtml);
    	  if($("#externalBcc").find("span").length > 0){
     		   $($("#externalBcc").find("span").get($("#externalBcc").find("span").length-1)).after("<input type='text' class='email-temp-input'>");
     		   outSideUser.initInputEvent("externalBcc");
     	   }else{
     		   $("#externalBcc").append("<input type='text' class='email-temp-input'>");
     		   outSideUser.initInputEvent("externalBcc");
     	   }
    	  $.each($("[name = 'externalBcc'] #outSide"),function(i,o){
    		  oldExternalBcc += o.value+",";
   		   });
       }
};

//点击关闭附件弹出层是清空内容
newmail.fileupload = function (){
	$("#businessId").val("0");
	showAttachList(true);
	if($("#mailTitle").val()==''){
		var strName = $("#attachList").text().split(" KB");
		var name = strName[0].substring(0,strName[0].lastIndexOf("."));
		$("#mailTitle").val(name.substring(0, name.lastIndexOf(".")));
		newmail.attachState = true;
	}
};

//抄送、密送回显人员
newmail.echoUser = function(){
	var echoCc = $("#echoCc").val();
	var echoBcc = $("#echoBcc").val();
	var echoSs = $("#echoSs").val();
	var externalcc = $("#externalcc").val();
	var externalbcc = $("#externalbcc").val();
	var externalss = $("#externalss").val();
	if(echoCc!=null && echoCc!=""){
		$(".email-csr").show();
		$(".email-btn-csr").html("删除抄送");
	}
	if(echoBcc!=null && echoBcc!=""){
		$(".email-msr").show();
		$(".email-btn-msr").html("删除密送");
	}
	if(echoSs!=null && echoSs!="undefined:undefined"&&echoSs!="null"&& echoSs!=""){
		
		hideErrorMessage();
        $(".email-cs-ms").toggle();
        $(".email-btn-group").toggle();
        $(".email-sjr").toggle();
        $(".email-qfdx").toggle();
       if(!$(".email-qfdx").is(":visible")){
    	   $(".email-btn-qfdx").text("使用群发单显");
       }else{
    	   $(".email-btn-qfdx").text("取消群发单显");
    	   hideErrorMessage();
    	   select2InitEchoToPerson("mailForm #innerSs-innerSs",eval(echoSs));
       }
	}
    if(externalcc!=null && externalcc!="undefined:undefined:undefined"&&externalcc!="null"&&externalcc!=""){
    	$(".email-csr").show();
    	$(".email-btn-csr").html("删除抄送");
	}
    if(externalbcc!=null && externalbcc!="undefined:undefined:undefined"&&externalbcc!="null"&&externalbcc!=""){
    	$(".email-msr").show();
    	$(".email-btn-msr").html("删除密送");
	}
	 //群发单显
    if(externalss!=null && externalss!="undefined:undefined:undefined"&&externalss!="null" && externalss!=""){
    	hideErrorMessage();
        $(".email-cs-ms").toggle();
        $(".email-btn-group").toggle();
        $(".email-sjr").toggle();
        $(".email-qfdx").toggle();
        if(!$(".email-qfdx").is(":visible")){
     	   $(".email-btn-qfdx").text("使用群发单显");
        }else{
     	   $(".email-btn-qfdx").text("取消群发单显");
    	var externalShowSingle =eval(externalss);
    	var appendHtml="";
    	  for(var i=0;i<externalShowSingle.length;i++){
    		  if(externalShowSingle[i].contactsId !=null && externalShowSingle[i].contactsId !="" && externalShowSingle[i].contactsId !="undefined"){
    			    var addHtml = getOutSideUserReply(externalShowSingle[i].contactsId);
    			    appendHtml  += "<li class='select2-search-field '><span name='ench' class='select2-search-choice' id='"+externalShowSingle[i].contactsId+"'><input type='hidden' id='outSide' name='outSide' value='"+externalShowSingle[i].receiveMail+"'>"
    				$("[name = 'externalShowSingle'] #outSide").remove();
    				appendHtml += "<a style='color: #c30!important;font-family: verdana;' onclick=outSideUser.removeParent(this) >x&nbsp;</a><font>"+externalShowSingle[i].userName+"</font>&nbsp;"+addHtml+"&nbsp;";
    				$("#externalShowSingle").find("input[type='text']").remove();
    				appendHtml += "</span></li>";
    		  }
    		  else{
    			    var nameIndex = "i" + outSideUser.index++;
    			    var addHtml = getOutSideUserReplyO(externalShowSingle[i].receiveMail,nameIndex);
    			    var str = "email-list-shou";
				    if(!addHtml) str = "";
    				appendHtml += "<li class='select2-search-field "+str+"'><span name="+nameIndex+""+externalShowSingle[i].receiveMail+" ondblclick=outSideUser.editAnyOneUser(this) class='select2-search-choice' ><input type='hidden' id='outSide' name='outSide' value='"+externalShowSingle[i].receiveMail+"'>"
    				appendHtml += "<a style='color: #c30!important;font-family: verdana;' onclick=outSideUser.removeParent(this) >x&nbsp;</a><font>" + externalShowSingle[i].receiveMail + "</font>&nbsp;"+addHtml+"&nbsp;";
    				$("#externalShowSingle").find("input[type='text']").remove();
    				appendHtml += "</span></li>";
    		  }
          }
    	  $("#externalShowSingle").append(appendHtml);
    	  if($("#externalShowSingle").find("span").length > 0){
     		   $($("#externalShowSingle").find("span").get($("#externalShowSingle").find("span").length-1)).after("<input type='text' class='email-temp-input'>");
     		   outSideUser.initInputEvent("externalShowSingle");
     	   }else{
     		   $("#externalShowSingle").append("<input type='text' class='email-temp-input'>");
     		   outSideUser.initInputEvent("externalShowSingle");
     	   }
    	  $.each($("[name = 'externalShowSingle'] #outSide"),function(i,o){
    		  oldExternalSs += o.value+",";
   		   });
        }
     }
};

newmail.changePassWorld = function(){
	if($("input[name='encryption']:checked").val() == 1){
		$(".encryption-td").show();
	}
	else{
		$(".encryption-td").hide();
		$('#encryptionPass').val("");
		$('#encryptionPass').removeClass("error");
		$("#encryptionPass").next(".error").remove();
		$('#confirmPass').val("");
		$('#confirmPass').removeClass("error");
		$("#confirmPass").next(".error").remove();
	}
};

pageMethon = function(){
	newmail.save();
};
//add by lihongyu at 2015-3-26 start
//预览
newmail.backToInboxNew=function(){
	newmail.clearAttachNew();
	if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
		if(window.parent.valPageSave.value != "true"){
			newmail.previewNew();
		}
	else{
		 window.parent.valPageSave.value = "false";
		var opt = {
				content:$.i18n.prop("JC_OA_IC_097"),
				success:function(){
					newmail.saveNew();
					newmail.previewNew();
					window.parent.valPageSave.value = "false";
				},
				cancel:function(){
					if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
						window.parent.valPageSave.value = "true";
					}
				}
			};
		msgBox.confirm(opt);
	   }
	}		
};
//存草稿(新)
newmail.saveNew=function(){
	if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
		window.parent.valPageSave.value = "false";
	}
	var url =getRootPath()+ "/ic/mail/save.action?time=" + (new Date()).getTime();
	
	newmail.contentEditor.sync('mailForm');
	var formData = $('#mailForm').serializeArray();
	var mailboxId = $("#mailboxId").val();
	if(mailboxId != 1){
	newmail.externalUser(formData);
	}
	newmail.addAttachParams(formData);
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : formData,
		success : function(data) {      
           if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_062"),
					callback:function(){
//						newmail.back("/ic/mail/manage.action/2");
					}
				});
				clearInterval(newmail.intervalID);
			}else {
				if(data.labelErrorMessage){
					showErrors("mailForm", data.labelErrorMessage);
				} else if (data.errorMessage) {
					msgBox.tip({
							content: data.errorMessage
					});
				} else {
					msgBox.tip({
						content:$.i18n.prop("JC_OA_IC_063"),
						type:"fail"
					});
				}
			}
		},
		error : function() {
			newmail.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_OA_IC_063"),
				type:"fail"
			});
		}
	});
};
//邮件预览(新)
newmail.preview=function(){
	newmail.backToInboxNew();
};
//邮件预览
newmail.previewNew=function(){
	var url = getRootPath()+"/ic/mail/managePreview.action?time=" + (new Date()).getTime();
	newmail.contentEditor.sync('mailForm');
	var formData = $('#mailForm').serializeArray();
	var mailboxId = $("#mailboxId").val();
	if(mailboxId != 1){
	newmail.externalUser(formData);
	}
	newmail.addAttachParams(formData);
	formData.push({"name": "token", "value": $("#token").val()});
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : formData,
		success : function(data) {
			if(data!= null){
				$('#previewer').html(data);
				$('#previewer').modal('show');
			} 
		}
	});
};
//add by lihongyu at 2015-3-26 end
jQuery(function($) {

	if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null){
		window.parent.valPageSave.value = "false";
		window.parent.loadmenubarurl.value = window.parent.valPageSaveMneuBar.value;
	}
	//$('#signature').click(newmail.signature);
	$('#btnSend').click(newmail.send);
	$('#btnSave').click(newmail.save);
	$('#btnPreview').click(newmail.preview);
	$('#btnCancel').click(newmail.backToInbox);
	$(".email-btn-qfdx").click(newmail.switchSingleShow);
	$(".innermail").click(newmail.replyTextingChecked);
    $(".email-btn-csr").click(function(){
        $(".email-csr").toggle();
        if(!$(".email-csr").is(":visible")){
        	$(this).text("添加抄送");
        	if($("#innerCc-innerCc").val()!=null&&$("#innerCc-innerCc").val()!=""){
        		selectControl.clearValue("innerCc-innerCc");
        	}
        	//删除抄送
        	$("[name = 'externalCc'] #outSide").parent().remove();
        }
        else{
        	$(this).text("删除抄送"); 	
        }
    });
    $(".email-btn-msr").click(function(){
        $(".email-msr").toggle();
        if(!$(".email-msr").is(":visible")){
        	$(this).text("添加密送");
        	if($("#innerBcc-innerBcc").val()!=null&&$("#innerBcc-innerBcc").val()!=""){
        		selectControl.clearValue("innerBcc-innerBcc");
        	}
        	 //删除密送
        	$("[name = 'externalBcc'] #outSide").parent().remove();
        }
        else{
        	$(this).text("删除密送");
        }
    });
    $(".mail-options-btn").click(function(){
		$("#mail-options").slideToggle();
		$(this).find("i").hasClass("fa-chevron-down")?$(this).find("i").removeClass("fa-chevron-down").addClass("fa-chevron-up"):$(this).find("i").removeClass("fa-chevron-up").addClass("fa-chevron-down");
	});
	//$(document).on('change','.encryption',function(e){$(e.target).is(':checked')?$(".encryption-td").show() : $(".encryption-td").hide();});
	$("#encryption").change(newmail.changePassWorld);
//    if($('#open').val()!="true"){
//    	// TODO 退出本页是调用一个方法 这个方法暂时还没有提供   关注
//		//menuswrite.statue = true;
//	}
	
	newmail.intervalID=setInterval(newmail.autosave, $('#autoSaveInterval').val());
	
	newmail.contentEditor=UE.getEditor("mailContent",{
		initialFrameHeight : 400,
		initialFrameWidth : "100%",
		toolbars: [
		           [
		            'undo', //撤销
		            'redo', //重做
		            
		            'fontfamily', //字体
		            'fontsize', //字号
		            'bold', //加粗
		            'italic', //斜体
		            'underline', //下划线
		            'strikethrough', //删除线
		            'forecolor', //字体颜色
		            'backcolor', //背景色
		            'horizontal', //分隔线
		            'insertorderedlist', //有序列表
		            'insertunorderedlist', //无序列表
		            'touppercase', //字母大写
		            'tolowercase', //字母小写
		            'removeformat', //清除格式
		           
		            'indent', //首行缩进
		            'justifyleft', //居左对齐
		            'justifyright', //居右对齐
		            'justifycenter', //居中对齐
		            'justifyjustify', //两端对齐
		            'subscript', //下标
		            'superscript', //上标
		            'fontborder', //字符边框
		            'blockquote', //引用
		            'pasteplain', //纯文本粘贴模式
		            'selectall', //全选
		            'searchreplace', //查询替换
		            
		            ],[
		            'inserttable', //插入表格
		            'insertimage', //插入图片
		            'insertrow', //前插入行
		            'insertcol', //前插入列
		            'mergeright', //右合并单元格
		            'mergedown', //下合并单元格
		            'deleterow', //删除行
		            'deletecol', //删除列
		            'splittorows', //拆分成行
		            'splittocols', //拆分成列
		            'splittocells', //完全拆分单元格
		            'deletecaption', //删除表格标题
		            'inserttitle', //插入标题
		            'mergecells', //合并多个单元格
		            'deletetable', //删除表格
		            'edittable', //表格属性
		            'edittd', //单元格属性
		            
		            'time', //时间
		            'date', //日期
		            'link', //超链接
		            'unlink', //取消链接
		            'paragraph', //段落格式
		            'rowspacingtop', //段前距
		            'rowspacingbottom', //段后距
		            'directionalityltr', //从左向右输入
		            'directionalityrtl', //从右向左输入
		            'lineheight', //行间距
		            'edittip ', //编辑提示
		            'emotion', //表情
		            'spechars', //特殊字符
		            'cleardoc', //清空文档
		            'fullscreen', //全屏
		            ]
		       ],
		       insertorderedlist:{        
	                'decimal' : '' 
	            },//有序列表
	           insertunorderedlist : {          
	           'disc' : '',    // '● 小圆点'
	             }
	});
	$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
	if($('#mailboxId').val()=="1"){
		$("#contreeTreeTo input[type='text']").prop("focus",true);
	}
	else{
		$('#to').prop("focus",true);
	}
	clearDelAttachIds();//设置附件上传为逻辑删除
//	$("#islogicDel").val("0");//附件使用 逻辑删除
	$("#islogicDel").val("2");//附件使用 逻辑删除
	echoAttachList(true);
	$("#queryattach").click(newmail.fileupload);
	clearTable();
	//回复、转发外部联系人回显

	if($('#mailboxId').val() != 1){
		newmail.getReplyMail();		
	}
	//草稿回显人员
	newmail.echoUser();
	//判断更多选项如果有条件进行展开
	 if($('input[name="pressing"]:checked').val() == 1 || $('input[name="smsAlert"]:checked').val() == 1 || $('input[name="encryption"]:checked').val() == 1  ||  $('input[name="replyTexting"]:checked').val() == 1 ){
	    	$("#mail-options").slideToggle();
	    }

	var mailboxId = $("#mailboxId").val();
	var senderMail = $("#senderMail").val();
	var displayName = $("#displayName").val();
    if(mailboxId != null && mailboxId != ""){
    	if(mailboxId == 1){
    	    selectControl.init("controlTreeTo","innerTo-innerTo", true, true,null,eval($('#echoTo').val()));//多选人员
    		selectControl.init("controlTreeCc","innerCc-innerCc", true, true,null,eval($('#echoCc').val()));//多选人员
    		selectControl.init("controlTreeBcc","innerBcc-innerBcc", true, true,null,eval($('#echoBcc').val()));//多选人员
    		selectControl.init("controlTreeSs","innerSs-innerSs", true, true,null,eval($('#echoSs').val()));//多选人员
    		newmail.mailbox("1",displayName);
    		newmail.oldTo = returnValue("innerTo-innerTo");
    	    newmail.oldCc = returnValue("innerCc-innerCc");
    	    newmail.oldBcc = returnValue("innerBcc-innerBcc");
    	    newmail.oldSs = returnValue("innerSs-innerSs");
    	}
    	else{
    		newmail.mailbox(mailboxId,senderMail);	
    		//外部邮件清空人员选择回显值
    		$('#echoTo').val("");
    		$('#echoCc').val("");
    		$('#echoBcc').val("");
    		$('#echoSs').val("");
    	}
    }
    else{
        selectControl.init("controlTreeTo","innerTo-innerTo", true, true,null,eval($('#echoTo').val()));//多选人员
    	selectControl.init("controlTreeCc","innerCc-innerCc", true, true,null,eval($('#echoCc').val()));//多选人员
    	selectControl.init("controlTreeBcc","innerBcc-innerBcc", true, true,null,eval($('#echoBcc').val()));//多选人员
    	selectControl.init("controlTreeSs","innerSs-innerSs", true, true,null,eval($('#echoSs').val()));//多选人员
    	newmail.mailbox("1",displayName);
    	newmail.oldTo = returnValue("innerTo-innerTo");
        newmail.oldCc = returnValue("innerCc-innerCc");
        newmail.oldBcc = returnValue("innerBcc-innerBcc");
        newmail.oldSs = returnValue("innerSs-innerSs");
    }
    if ($("#folderId").val() == 2) {
		    newmail.oldAttach = $("#attachList li").length;
		    newmail.contentEditor.ready(function() {
			  oldContent = newmail.contentEditor.getContent();
			});
	}
});

//@ sourceURL=newMail.js