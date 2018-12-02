
 /**
 * 阅读邮件JS
 * 
 * @Author zhanglg
 */

var mailDetail = {};

// 初始化文件夹列表
mailDetail.initFolders = function() {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/initMailFolders.action",
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				var folders = "";
				$.each(data, function(i, n) {
					folders += "<li><a href=\"javascript:mailDetail.moveTo("
							+ n.id + ");\">" + n.folderName + "</a></li>";
				});
				$('#mailFolder').html(folders);
			}
		}
	});
};
// 返回功能
mailDetail.back = function() {
	var url=$('#returnURL').val();
//	loadrightmenu(url);
	var strArray=unescape(url).split("?");
	$.ajax({
		url : getRootPath() +strArray[0],
		type : 'POST',
		data:strArray[1],
		dataType : "html",
		success : function(data) {
			//$("#scrollable").html(data);// 要刷新的div
			loadrightmenu(unescape(url));
		}
	});
};
// 回复/回复所有
mailDetail.reply = function(isAll,content) {
	if($('#encryption').val()=="1" && $('#wrongPassword').val()=="true"){
		msgBox.tip({
			content: $.i18n.prop("JC_OA_IC_079"),
			callback:function(){
			}
		});
	}
	else{
		var folderId = $("#folderId").val();
		content = content.replace(/\n/g, "<br/>");
		window.open(encodeURI(getRootPath() + "/ic/mail/manageReplyMail.action?id="+$('#id').val()+"&reply="+isAll+"&folderId="+folderId+"&encryption="+$('#encryption').val()+"&mailboxId="+$('#mailboxId').val()),"","width="+(window.screen.availWidth-10)+",height="+(window.screen.availHeight-30)+ ",top=0,left=0,toolbar=no,menubar=no, resizable=yes,location=no, status=no");
	}
	//	data= {
//			"id" : $('#id').val(),
//			"reply" : isAll
//	};
//	if(content!=null && content !=""){
//		data.mailContent=content;
//	}
//	$.ajax({
//		url : getRootPath() + "/ic/mail/manageReplyMail.action",
//		type : 'GET',
//		data :data,
//		dataType : "html",
//		success : function(data) {
//			$("#scrollable").html(data);// 要刷新的div
//		}
//	});
};
mailDetail.replyS = function() {
	mailDetail.reply(0,"");
};
mailDetail.replyA = function() {
	mailDetail.reply(1,"");
};
mailDetail.replySwitchToWhole=function(){
	mailDetail.reply(0,$('#fastContent').val());
};

// 转发
mailDetail.forwarding = function() {
	if($('#encryption').val()=="1" && $('#wrongPassword').val()=="true"){
		msgBox.tip({
			content: $.i18n.prop("JC_OA_IC_079"),
			callback:function(){
			}
		});
	}
	else{
		window.open(encodeURI(getRootPath() + "/ic/mail/manageForwarding.action?ids="+$('#id').val()),"","width="+(window.screen.availWidth-10)+",height="+(window.screen.availHeight-30)+ ",top=0,left=0,toolbar=no,menubar=no, resizable=yes,location=no, status=no");
	}
//	$.ajax({
//		url : getRootPath() + "/ic/mail/manageForwarding.action",
//		type : 'GET',
//		data : "ids=" + $('#id').val(),
//		dataType : "html",
//		success : function(data) {
//			$("#scrollable").html(data);// 要刷新的div
//		}
//	});
};

// 删除
mailDetail.deleteMail = function() {
	$(".outgoing-mail").css("display","none");
	var opt={
			content:$.i18n.prop("JC_OA_IC_054"),
			success:function(){
				$.ajax({
					type : "GET",
					url : getRootPath() + "/ic/mail/moveToByIds.action",
					data : {
						"ids" : $('#id').val(),
						"fromFolder":$('#folder').val(),
						"mrids":$("#recId").val(),
						"toFolder" : 4
					},
					dataType : "json",
					success : function(data) {
						if (data > 0) {
							msgBox.tip({
								type:"success",
								content: $.i18n.prop("JC_OA_IC_048")
							});
							if($("#index").val()==0
									&&(!$("#showPre").val()||$("#showPre").val()=="false")
									&&(!$("#showNext").val()||$("#showNext").val()=="false")){
								loadrightmenu("/ic/mail/manageUnread.action");
							}else if((!$("#showNext").val()||$("#showNext").val()=="false")
									&&($("#showPre").val()||$("#showPre").val()=="true")){
								mailDetail.navigate(-1);
							}else{
								mailDetail.navigate(1);
							}
							
						}
					}
				});
			},
			cancel:function(){
				
			}
	};
	
	msgBox.confirm(opt);
	
};

//移动到···
mailDetail.moveTo = function(folder) {
	var url=$('#returnURL').val();
	$(".outgoing-mail").css("display","none");
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/moveToByIds.action",
		data : {
			"ids":$('#id').val(),
			"mrids":$("#recId").val(),
			"fromFolder":$('#folder').val(),
			"toFolder":folder
		},
		dataType : "json",
		success : function(data) {
			if (data) {
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_049"),
					callback:function(){
						if($("#index").val()==0
								&&(!$("#showPre").val()||$("#showPre").val()=="false")
								&&(!$("#showNext").val()||$("#showNext").val()=="false")){
							loadrightmenu(unescape(url));
						}else if((!$("#showNext").val()||$("#showNext").val()=="false")
								&&($("#showPre").val()||$("#showPre").val()=="true")){
							mailDetail.navigate(-2);
						}else{
							mailDetail.navigate(2);
						}
					}
				});
				
			}
			else{
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		}
	});

};

//判断更多显示和隐藏
mailDetail.showMore = function(){
	//收信人span高度
	var toSpan_height=$("#receiverSpan").height();
	//群发单显span高度
	var singleSpan_height=$("#showSingleSpan").height();
	//抄送人span高度
	var ccSpan_height=$("#receiverCcSpan").height();
	//密送人span高度
	var bccSpan_height=$("#receiverBccSpan").height();
	if($("#mailto").val()==""||($("#mailto").val()!=""&&toSpan_height<24)){
		$("#mailtoShow").hide();
	}else{
		$("#mailtoShow").show();
	}
	
	if($("#mailcc").val()==""||($("#mailcc").val()!=""&&ccSpan_height<24)){
		$("#mailccShow").hide();
	}else{
		$("#mailccShow").show();
	}
	
	if($("#mailbcc").val()==""||($("#mailbcc").val()!=""&&bccSpan_height<24)){
		$("#mailbccShow").hide();
	}else{
		$("#mailbccShow").show();
	}
	
	if($("#mailShowSingle").val()==""||($("#mailShowSingle").val()!=""&&singleSpan_height<24)){
		$("#mailShowShowSingle").hide();
	}else{
		$("#mailShowShowSingle").show();
	}
};

// 上一封、下一封
mailDetail.navigate = function(forward) {
	$(".outgoing-mail").css("display","none");
	$.ajax({
		url : getRootPath() + "/ic/mail/navigateUnRead.action",
		type : 'POST',
		data : {
			"isAllMail":$('#isAllMail').val(),
			"mailboxId":$('#mailboxId').val(),
			"receivers[0].folderId":$('#folder').val(),
			"senderUserId":$('#senderUserId').val(),
			"senderMail":$('#senderMail').val(),
			"receivers[0].readFlag":0,
			"mailTitle":$('#mailTitleSearch').val(),
			"mailEasyTitle":$('#mailEasyTitleSearch').val(),
			"searchReceiveTimeBegin":$('#searchReceiveTimeBegin').val(),
			"searchReceiveTimeEnd":$('#searchReceiveTimeEnd').val(),
			"index":$('#index').val(),
			"folderId":$('#folderId').val(),
			"forward" : forward,
			"showNext":$('#showNext').val()
		},
		dataType : "json",
		success : function(data) {
			if (data != null) {
				var receiver="";
				if(data.receivers!=null)
				{
					$.each(data.receivers,function(i,n){
						if(data.mailboxId==1){
							receiver+=(n.receiveUserName+",");
						}
						else{
						receiver+=(n.receiveMail+",");
						}
					});
				}
				var attach="";
				if(data.attachs!=null && data.attachs.length !=0)
				{
					attach="<dt class=\"control-label\">附　件</dt>" +
							"<dd class=\"controls\">"+
							"<span >";
					$.each(data.attachs,function(i,n){
						if(data.mailboxId==1){
							attach += "<i class=\"fa fa-paper-clip\"></i>&nbsp<a href=\"javascript:mailDetail.download('"+n.fileName+"','"+n.resourcesName+"')\""+
							" class=\"blue-dark m-r-xs\">"+n.fileName+" </a>&nbsp;";
							if(n.fileSize/1000<1000){
								attach+=(n.fileSize/1000).toFixed(2);
								attach+=" KB</span>&nbsp;";
							} else if(n.fileSize/1000>1000 && n.fileSize/1000<1000000){
								attach+=(n.fileSize/1000000).toFixed(2);
								attach+=" MB</span>&nbsp;";
							} else if(n.fileSize/1000>1000000){
								attach+=(n.fileSize/1000000000).toFixed(2);
								attach+=" GB</span>&nbsp;";
							}
						}else{
							attach += "<i class=\"fa fa-paper-clip\"></i>&nbsp<a href=\"javascript:mailDetail.downloadExt('"+n.fileName+"','"+data.id+"')\""+
							" class=\"blue-dark m-r-xs\">"+n.fileName+" </a>&nbsp;";
						}
					});
					attach += "</span>" +
							"</dd>";
					$("#attachList").html(attach);
				}else{
					$("#attachList").html("");
				}
				
				//显示内部邮件状态
				if(data.mailboxId==1){
					//群发单显
					if(data.showSingle!=null && data.showSingle.length>0){
						$('#dlShowSingle').removeClass("hide");
						$('#showSingleSpan').html(data.newSs);
					}
					else{
						$('#dlShowSingle').addClass("hide");
						$('#showSingleSpan').html("");
					}
					//收件人
					if(data.to!=null && data.to.length>0){
						$('#dlTo').removeClass("hide");
						$('#receiverSpan').html(data.newTo);
					}
					else{
						$('#dlTo').addClass("hide");
						$('#receiverSpan').html("");
					}
					//抄送人
					if(data.cc!=null&& data.cc.length>0){
						$('#dlCc').removeClass("hide");
						$('#receiverCcSpan').html(data.newCc);
					}
					else{
						$('#dlCc').addClass("hide");
						$('#receiverCcSpan').html("");
					}
					//密送人
					if(data.bcc!=null&& data.bcc.length>0){
						$('#dlBcc').removeClass("hide");
						$('#receiverBccSpan').html(data.newBcc);
					}
					else{
						$('#dlBcc').addClass("hide");
						$('#receiverBccSpan').html("");
					}
			}else{
					//群发单显
					if(data.showSingle!=null && data.showSingle.length>0){
						$('#dlShowSingle').removeClass("hide");
						$('#showSingleSpan').html(data.showSingle);
					}
					else{
						$('#dlShowSingle').addClass("hide");
						$('#showSingleSpan').html("");
					}
					//收件人
					if(data.to!=null && data.to.length>0){
						$('#dlTo').removeClass("hide");
						$('#receiverSpan').html(data.to);
					}
					else{
						$('#dlTo').addClass("hide");
						$('#receiverSpan').html("");
					}
					//抄送人
					if(data.cc!=null&& data.cc.length>0){
						$('#dlCc').removeClass("hide");
						$('#receiverCcSpan').html(data.cc);
					}
					else{
						$('#dlCc').addClass("hide");
						$('#receiverCcSpan').html("");
					}
					//密送人
					if(data.bcc!=null&& data.bcc.length>0){
						$('#dlBcc').removeClass("hide");
						$('#receiverBccSpan').html(data.bcc);
					}
					else{
						$('#dlBcc').addClass("hide");
						$('#receiverBccSpan').html("");
					}
			}
				$('#id').val(data.id);
				$('#index').val(data.index);
				$('#mailTitle').html(data.mailTitle);
				//$('#senderMail').val(data.senderMail);
//				$('#receiver').html(receiver);
				if(data.mailboxId==1){
					$('#senderMailEcho').html("<a style = \"cursor :pointer\" class=\"email-receivers\" onclick=\"showOnlinePerson.showPersonInfo("+data.senderUserId+");\">"+data.senderUserName+"</a>");
				}
				else{
					$('#senderMailEcho').html(data.senderMail);
				}
				$('#senderTime').html(data.senderTime);
				$('#attach').html(data.senderMail);
				$('#short-header').html(data.senderMail+","+data.senderTime);
				$('#recId').val(data.receiver.id);
				$('#showNext').val(data.showNext);
				$('#showPre').val(data.showPre);
				
				//上一封下一封星标状态修改 start add songht
				if($('#folderId').val()!=4){
					$('#detailStarMail').children().remove();
					//$('#topStarButton').unbind("click"); //移除click
					$('#topStarButton').removeAttr("onclick"); //移除click
	
//					$("#topStarButton").bind("click", function(){
//						mailDetail.setStar(data.id,data.receiver.folderId,data.receiver.id);
//					});
					$("#topStarButton").attr("onclick", 'mailDetail.setStar('+data.id+','+data.receiver.folderId+','+data.receiver.id+')');
					if(data.receiver.starMail==1){
						$('#detailStarMail').html('<a href="javascript:mailDetail.setStar('+data.id+','+data.receiver.folderId+','+data.receiver.id+');" class="fr"><i class="fa fa-star yellow-dark fr m-r-xs" id="lightstar'+data.receiver.id+'"></i></a>');
					}else{
						$('#detailStarMail').html('<a href="javascript:mailDetail.setStar('+data.id+','+data.receiver.folderId+','+data.receiver.id+');" class="fr"><i class="fa fa-star fr text-grey-999 m-t-mxs" id="lightstar'+data.receiver.id+'"></i></a>');
					}
				}else{
					$('#detailStarMailForWaste').children().remove();
					if(data.receiver.starMail==1){
						$('#detailStarMailForWaste').html('<i class="fa fa-star yellow-dark fr m-t-mxs"></i>');
					}
				}
				/*$('#detailStarMail').children().remove();
				if(data.receiver.starMail==1){
					$('#detailStarMail').html('<a href="javascript:mailDetail.setStar('+data.id+','+data.receiver.folderId+','+data.receiver.id+');" class="fr"><i class="fa fa-star yellow-dark fr m-t-mxs" id="lightstar'+data.receiver.id+'"></i></a>');
				}else{
					$('#detailStarMail').html('<a href="javascript:mailDetail.setStar('+data.id+','+data.receiver.folderId+','+data.receiver.id+');" class="fr"><i class="fa fa-star fr text-grey-999 m-t-mxs" id="lightstar'+data.receiver.id+'"></i></a>');
				}*/
				//上一封下一封星标状态修改 end 
				
				//上一封下一封往来邮件不刷新，因为发送人和发送邮箱没有更新 add by songht 
				$("#mailSenderUserId").val(data.senderUserId);
				$("#mailSenderMail").val(data.senderMail);
				
				//如果发送人为当前登录人或者发送邮箱为当前邮箱隐藏往来邮件，否则显示往来邮件 add by songht 
				if((data.senderUserId!=""&&data.senderUserId==$('#currentUserId').val())||(data.senderMail!=""&&data.senderMail==$('#mailboxAddress').val())){
					$('#outGoingDiv').css("display","none");
				}else{
					$('#outGoingDiv').css("display","");
				}
				
				//上一封下一封邮件加密问题 add by songht  start 
				$("#encryption").val(data.encryption);
				$("#wrongPassword").val(data.wrongPassword);
				//不是加密邮件或者是加密邮件但密码输入正确显示附件
				if((data.encryption==""||data.encryption==0)||(data.encryption==1&&data.wrongPassword=="false")){
					$('#attachList').css("display","block");
				}else{
					$('#attachList').css("display","none");
				}
				//上一封下一封邮件加密问题 add by songht  end
				
				//上一封下一更更多显示隐藏 包括收件人、抄送人 、密送人、群发单显 add by songht start
				$("#mailto").val(data.to);
				$("#mailcc").val(data.cc);
				$("#mailbcc").val(data.bcc);
				$("#mailShowSingle").val(data.showSingle);
				mailDetail.showMore();
				//上一封下一更更多显示隐藏 包括收件人、抄送人 、密送人、群发单显 add by songht end
				
				if(data.encryption==1){
					$('#mailContent').hide();
					$('#password').show();
					$('#errorMsg').remove();
				}
				else{
					$('#mailContent').show();
					$('#mailContent').html(data.mailContent);
					$('#password').hide();
				}
				
				if(data.showPre==false){
					$('#btnPre').attr("disabled","disabled");
				}
				else{
					$('#btnPre').removeAttr("disabled");					
				}
				if(data.showNext==false){
					$('#btnNext').attr("disabled","disabled");
				}
				else{
					$('#btnNext').removeAttr("disabled");
				}
				
			}
			parent.msgTip.reminders();
		}
	});
};
// 查看往来
mailDetail.relation = function() {
	var data = {};
	if($("#mailboxId").val() == 1){
		data = { "senderUserId":$('#mailSenderUserId').val(),"mailboxId":$("#mailboxId").val()};
	}else{
		data = { "senderUserId":$('#mailSenderUserId').val(),"senderMail":$('#mailSenderMail').val(),"receiveMail":$('#receiveMail').val(),"mailboxId":$("#mailboxId").val()};
	}
	$.ajax({
		url : getRootPath() + "/ic/mail/viewToAndFrom.action",
		type : 'POST',
		data : data,
		dataType : "json",
		success : function(data) {
			if(data.mailList!=null)
			{
				var relationMail="";
				var relationAttach="";
				var attachCount=0;
				$.each(data.mailList,function(i,n){
					relationMail+="<li class=\"clearfix\">";
					relationMail+="<p>";
					if (n.pressing == 1) {
						relationMail+="<b class=\"red-dark f18\">! </b>";
					}
					if(n.isfile=="1"){
						relationMail+="<i class=\"fa fa-paper-clip\"></i>&nbsp;";
					}
					relationMail+="<a href=\"javascript:mailDetail.preview(" +n.id+")\">";
					relationMail+=n.mailTitle;
					relationMail+="</a>";
					//relationMail+="<i class=\"m-l-xs fa\"></i>";
					relationMail+="</p>";
					relationMail+="<p>";
					relationMail+="		<span class=\"fl\">" ;
					relationMail+=n.senderTime;
					relationMail+="</span><span ";
					relationMail+="		class=\"fr ";
					if($('#mailboxId').val()==1){
						if(n.senderUserId==$('#mailSenderUserId').val())
						{
							relationMail+="outgoing-blue\">已接收邮件";
						}
						else
						{
							relationMail+="outgoing-green\">已发送邮件";
						}
					}else{
						if(n.senderMail==$('#senderMail').val())
						{
							relationMail+="outgoing-blue\">已接收邮件";
						}
						else
						{
							relationMail+="outgoing-green\">已发送邮件";
						}
					}
					relationMail+="</span>";
					relationMail+="</p>";
					relationMail+=" </li>";
				});
				$.each(data.attachList,function(i,m){
					attachCount++;
					relationAttach+="<li class=\"clearfix\">";
					relationAttach+="<p>";
					if($('#mailboxId').val()==1){
						relationAttach+="<a class=\"blue-dark m-r-xs\" href=\"javascript:mailDetail.downloadE('" +m.fileName+"','"+m.resourcesName+"','"+m.id+"')\">";
					}
					else{
						relationAttach+="<a class=\"blue-dark m-r-xs\" href=\"javascript:mailDetail.downloadExt('" +m.fileName+"','"+m.businessId+"')\">";
					}
					relationAttach+=m.fileName;
					relationAttach+="</a>";
					relationAttach+="<span class=\"fr\">";
					if(m.fileSize/1000<1000){
						relationAttach+=(m.fileSize/1000).toFixed(2);
						relationAttach+=" KB</span></p>";
					} else if(m.fileSize/1000>1000 && m.fileSize/1000<1000000){
						relationAttach+=(m.fileSize/1000000).toFixed(2);
						relationAttach+=" MB</span></p>";
					} else if(m.fileSize/1000>1000000){
						relationAttach+=(m.fileSize/1000000000).toFixed(2);
						relationAttach+=" GB</span></p>";
					}
					relationAttach+="<p>";
					relationAttach+="	<span class=\"fl\">";
					//relationAttach+=n.receiveTime2;
					//relationAttach+="</span><a href=\"javascript:mailDetail.detail(" +n.id+")\"";
					relationAttach+="</span><span ";
					relationAttach+="class=\"fr ";
//					if(n.senderUserId==$('#senderUserId').val())
//					{
//						relationAttach+="outgoing-blue\">接收邮件</a>";
//					}
//					else
//						{
						relationAttach+="outgoing-green\">";
						//}
					relationAttach+="</span></p>";
					relationAttach+="</li>";
				});
				$('#relationMail').html(relationMail);
				$('#relationAttach').html(relationAttach);
				$('#relationMailCount').html(data.mailCount);
				$('#relationAttachCount').html(data.attachCount);
			}
		}
	});
};

mailDetail.detail = function(id) {
	$.ajax({
		url : getRootPath() + "/ic/mail/manageDetail.action",
		type : 'GET',
		data : "id=" + id,
		dataType : "html",
		success : function(data) {
			$("#scrollable").html(data);// 要刷新的div
		}
	});
};

mailDetail.postPsw=function(){
	$.ajax({
		url : getRootPath() + "/ic/mail/manageUnReadDetail.action",
		type : 'GET',
		data : {"id":$('#id').val(),
			"mrid":$("#recId").val(),
			"index":$('#index').val(),
			"password":$('#encryptionPass').val(),
			"returnURL":$('#returnURL').val(),
			'showNext':$('#showNext').val(),
			'showPre':$('#showPre').val()
			},
			
		dataType : "html",
		success : function(data) {
			$("#scrollable").html(data);// 要刷新的div
		}
	});
};

//portal查看加密邮件
mailDetail.postPortalPsw=function(){
	$.ajax({
		url : getRootPath() + "/ic/mail/managePortletView.action",
		type : 'GET',
		data : {"id":$('#id').val(),
			"mrid":$("#recId").val(),
			"index":$('#index').val(),
			"password":$('#encryptionPass').val(),
			"returnURL":$('#returnURL').val(),
			'showNext':$('#showNext').val(),
			'showPre':$('#showPre').val()
			},
			
		dataType : "html",
		success : function(data) {
			$("#scrollable").html(data);// 要刷新的div
		}
	});
};

mailDetail.fastreply = function() {
	if($('#encryption').val()=="1" && $('#wrongPassword').val()=="true"){
		msgBox.tip({
			content: $.i18n.prop("JC_OA_IC_079"),
			callback:function(){
			}
		});
	}else{	
	var url = getRootPath()+"/ic/mail/fastReply.action?time=" + (new Date()).getTime();

jQuery.ajax({
	url : url,
	type : 'POST',
	data :  {
		"id" : $('#id').val(),
		"folderId":$("folderId").val(),
		"mailContent":$('#fastContent').val()
	},
	success : function(data) {
		if(data.success == "true"){
			msgBox.tip({
				type:"success",
				content: $.i18n.prop("JC_OA_IC_050")
			});
				$(".fast-reply").show();
				$("#fastContent").val("");
				$(".fast-reply-box").hide();
				$(".fast-reply").val("邮件发送成功！再回一封邮件");
		} else {
			if(data.labelErrorMessage){
				showErrors("mailForm", data.labelErrorMessage);
			} else{
				msgBox.info({
					content: data.errorMessage
				});
			}
			$(".fast-reply").show();
			$("#fastContent").val("");
			$(".fast-reply-box").hide();
			$(".fast-reply").val("可快速回复：");
		}
	},
	error : function() {
		newmail.subState = false;
		msgBox.tip({
			content: $.i18n.prop("JC_OA_IC_051"),
			type:"fail"
		});
		$(".fast-reply").show();
		$("#fastContent").val("");
		$(".fast-reply-box").hide();
		$(".fast-reply").val("可快速回复：");
	}
});
	}
};

//附件下载
mailDetail.download = function(fileName,resourcesName) {
	window.open(encodeURI(getRootPath()+"/content/attach/download.action?fileName="+encodeURIComponent(fileName)+"&resourcesName="+resourcesName),"下载附件");
};
//解密附件下载
mailDetail.downloadE = function(fileName,resourcesName,id) {
	//Ajax判断是否需要密码
	jQuery.ajax({
		url : getRootPath()+"/ic/mail/getMailByAttach.action",
		type : 'POST',
		data : {id:id},
		success : function(data) {
			if(data.mail!= null){
				//如果加密邮件，需要输入密码
				if(data.mail.encryption==1){
					$('#attachId').val(data.mail.id);
					$('#attachFileName').val(fileName);
					$('#attachResourcesName').val(resourcesName);
					$('#inputPassword').modal('show');
				}
				else{
					window.open(encodeURI(getRootPath()+"/content/attach/download.action?fileName="+encodeURIComponent(fileName)+"&resourcesName="+resourcesName),"下载附件");
				}
			} 
			else{
				//没查到邮件
				msgBox.info({
					content: $.i18n.prop("JC_SYS_060")
				});
			}
		}
	});
};

mailDetail.passwordOk=function(){
	//Ajax判断密码是否right
	jQuery.ajax({
		url : getRootPath()+"/ic/mail/checkEncryptionPass.action",
		type : 'POST',
		data : {id:$('#attachId').val(),
			password:$('#attachpassword').val()},
		success : function(data) {
			if(data.wrong==true){
				msgBox.info({
					content: data.errorMessage
				});
			} 
			else{
				window.open(encodeURI(getRootPath()+"/content/attach/download.action?fileName="+encodeURIComponent($('#attachFileName').val())+"&resourcesName="+$('#attachResourcesName').val()),"下载附件");
				$('#attachId').val("");
				$('#attachpassword').val("");
				$('#attachFileName').val("");
				$('#attachResourcesName').val("");
				$('#inputPassword').modal('hide');
			}
		}
	});
};

mailDetail.passwordCancel=function(){
	$('#attachId').val("");
	$('#attachpassword').val("");
	$('#attachFileName').val("");
	$('#attachResourcesName').val("");
	$('#inputPassword').modal('hide');
};

mailDetail.downloadExt=function(fileName,id){
	window.open(getRootPath()+"/ic/mail/download.action?fileName="+fileName+"&mailId="+id,"下载附件");
};

mailDetail.preview=function(id){
	var url = getRootPath()+"/ic/mail/manageView.action?time=" + (new Date()).getTime();
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : {id:id},
		success : function(data) {
			if(data!= null){
				$('#previewer').html(data);
				$('#previewer').modal('show');
			} 
		}
	});
};

//单邮件设置星标
mailDetail.setStar=function(id,folderId,mrids){

	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/setStarByIds.action",
		data : {
			"ids" : id,
			"mrids":mrids,
			"folder":folderId
		},
		dataType : "json",
		success : function(data) {

			var temp = $("#lightstar" + mrids);
			var id = temp.attr("id");
			if (data > 0) {
				//设置星标颜色
				if(temp.hasClass("yellow-dark")){
					temp.parent().html('<i class="fa fa-star fr text-grey-999 m-t-mxs" id="'+id+'"></i>');
				}else if(temp.hasClass("text-grey-999")){
					temp.parent().html('<i class="fa fa-star fr yellow-dark m-t-mxs" id="'+id+'"></i>');
				}
			}
		}
	});
};

// 初始化
jQuery(function($) {
	$('#btnBack').click(mailDetail.back);
	$('#btnReply').click(mailDetail.replyS);
	$('#btnReplyAll').click(mailDetail.replyA);
	$('#btnForwarding').click(mailDetail.forwarding);
	$('#btnDelete').click(mailDetail.deleteMail);
	$('#passwordOk').click(mailDetail.passwordOk);
	$('#passwordCancel').click(mailDetail.passwordCancel);
	$('#btnPortalPswOk').click(mailDetail.postPortalPsw);
	$('#btnPswOk').click(mailDetail.postPsw);

	mailDetail.initFolders();
	
	//修改如果发送人为当前用户或发送邮箱为当前邮箱时不显示往来邮件add by songht
	if(($("#mailboxId").val()==1&&$("#mailSenderUserId").val()!=""&&$("#mailSenderUserId").val()==$('#currentUserId').val())||($("#mailSenderMail").val()!=""&&$("#mailSenderMail").val()==$('#mailboxAddress').val())){
		$('#outGoingDiv').css("display","none");
	}
	//修改如果发送人为当前用户或发送邮箱为当前邮箱时不显示往来邮件add by songht end 
	
	//不是加密邮件或者是加密邮件但密码输入正确显示附件，$("#wrongPassword").val()=="false"为解密成功 add by songht start 
	if(($("#encryption").val()==""||$("#encryption").val()==0)||($("#encryption").val()==1&&$("#wrongPassword").val()=="false")){
		$('#attachList').css("display","block");
	}
	//不是加密邮件或者是加密邮件但密码输入正确显示附件 add by songht end
	
	//收件人、抄送人、密送人和群发单显更多隐藏 add by songht start 
	mailDetail.showMore();
	//收件人、抄送人、密送人和群发单显更多隐藏 add by songht end
	
	$(".mail-options-down").click(function(){
		$("#mail-options").slideDown(500);
		$(this).hide();
		$(".mail-options-up").show();
	});
	$(".mail-options-up").click(function(){
		$("#mail-options").slideUp(500);
		$(this).hide();
		$(".mail-options-down").show();
	});
	$(".fast-reply").focus(function(){
			$(this).hide();
			if(navigator.userAgent.indexOf("MSIE")>0){
				if($(this).prev().is('span')){$(this).prev().hide()}
			}
			$(".fast-reply-box").show();
			$('#fastContent').focus();
		});
	$(".close-fast-reply").click(function(){
		$(".fast-reply").show();
		$("#fastContent").val("");
		$(".fast-reply-box").hide();
		$(".fast-reply").val("可快速回复：");

	});
	$(".outgoing-mail-down").click(function(){
			$(".outgoing-mail").slideToggle(500);
			mailDetail.relation();
		});
		
	$(".simple-down").click(function(){
			$(".corpus").slideDown(500);
			$(".simple").slideUp(500);
		});
		$(".corpus-up").click(function(){
			$(".corpus").slideUp(500);
			$(".simple").slideDown(500);
		});
	$(".outgoing-mail-up").click(function (e) {
		 if($(e.target).attr('href') == undefined ){
			 $(e.target).hasClass('fa-angle-down') ?
				$(e.target).removeClass('fa-angle-down').addClass('fa-angle-up') :
				$(e.target).removeClass('fa-angle-up').addClass('fa-angle-down');
			 $(".outgoing-email-content").stop(true,true).slideToggle(500);
		 }
	});
	 $(".outgoing-acces-up").click(function (e) {
		if($(e.target).attr('href') == undefined ){
			$(e.target).hasClass('fa-angle-down') ?
				$(e.target).removeClass('fa-angle-down').addClass('fa-angle-up') :
				$(e.target).removeClass('fa-angle-up').addClass('fa-angle-down');
			$(".outgoing-acces-content").stop(true,true).slideToggle(500);
		}
		setTimeout(function(){$(".outgoing-body").scrollTop($(".outgoing-body").scrollTop()+275)},600);
	});
	//更多切换事件
	 $(".email-receiver").click(function(e){
	 	var conn = $($(e.target).closest(".controls").children()[0])
	 	var i = $($(e.target).children()[0])
	  	if(!$(e.target).attr('href')) i = $(e.target)
	 	if(conn.hasClass('p-hide')){
	 		conn.parent().css('height','auto')
	 		conn.removeClass('p-hide').addClass('p-show')
	 		i.removeClass('fa-angle-down').addClass('fa-angle-up')
	 	}else{
	 		conn.removeClass('p-show').addClass('p-hide')
	 		i.removeClass('fa-angle-up').addClass('fa-angle-down')
	 	}
	  });
	 $('#mailContent a').removeAttr('target');
	 $('#mailContent a').attr('target', '_blank');
});

//@ sourceURL=mailDetail.js
