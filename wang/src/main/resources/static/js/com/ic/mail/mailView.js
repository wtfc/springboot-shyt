
 /**
 * 阅读邮件JS
 * 
 * @Author zhanglg
 */

var mailView = {};

// 初始化文件夹列表
mailView.initFolders = function() {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/initMailFolders.action",
		dataType : "json",
		success : function(data) {
			if (data != null && data.length > 0) {
				var folders = "";
				$.each(data, function(i, n) {
					folders += "<li><a href=\"javascript:mailView.moveTo("
							+ n.id + ");\">" + n.folderName + "</a></li>";
				});
				$('#mailFolder').html(folders);
			}
		}
	});
};
// 返回功能
mailView.back = function() {
	var url=$('#returnURL').val();
//	loadrightmenu(url);
	var strArray=unescape(url).split("?");
	$.ajax({
		url : getRootPath() +strArray[0],
		type : 'POST',
		data:strArray[1],
		dataType : "html",
		success : function(data) {
			$("#scrollable").html(data);// 要刷新的div
		}
	});
};
// 回复/回复所有
mailView.reply = function(isAll,content) {
	if($('#tencryption').val()=="1" && $('#twrongPassword').val()=="true"){
		msgBox.tip({
			content: $.i18n.prop("JC_OA_IC_079"),
			callback:function(){
			}
		});
	}
	else{
		var folderId = $("#folderId").val();
		content = content.replace(/\n/g, "<br/>");
		window.open(encodeURI(getRootPath() + "/ic/mail/manageReplyMail.action?id="+$('#tid').val()+"&reply="+isAll+"&mailContent="+content+"&folderId="+folderId+"&encryption="+$('#tencryption').val()+"&mailboxId="+$('#mailboxId').val()),"","width="+(window.screen.availWidth-10)+",height="+(window.screen.availHeight-30)+ ",top=0,left=0,toolbar=no,menubar=no, resizable=yes,location=no, status=no");
	}
};
mailView.replyS = function() {
	mailView.reply(0,"");
};
mailView.replyA = function() {
	mailView.reply(1,"");
};
mailView.replySwitchToWhole=function(){
	mailView.reply(0,$('#fastContent').val());
};

// 转发
mailView.forwarding = function() {
		if($('#tencryption').val()=="1" && $('#twrongPassword').val()=="true"){
			msgBox.tip({
				content: $.i18n.prop("JC_OA_IC_079"),
				callback:function(){
				}
			});
		}
		else{
			window.open(encodeURI(getRootPath() + "/ic/mail/manageForwarding.action?ids="+$('#tid').val()),"","width="+(window.screen.availWidth-10)+",height="+(window.screen.availHeight-30)+ ",top=0,left=0,toolbar=no,menubar=no, resizable=yes,location=no, status=no");
		}
};
// 删除
mailView.deleteMail = function() {
	var opt={
			content:$.i18n.prop("JC_OA_IC_054"),
			success:function(){
				$.ajax({
					type : "GET",
					url : getRootPath() + "/ic/mail/moveToByIds.action",
					data : {
						"ids" : $('#tid').val(),
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
								loadrightmenu("/ic/mail/manage.action?fid="+$('#folder').val());
							}else if((!$("#showNext").val()||$("#showNext").val()=="false")
									&&($("#showPre").val()||$("#showPre").val()=="true")){
								mailView.navigate(-2);
							}else{
								mailView.navigate(2);
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

mailView.deleteMailPhy = function(){
	msgBox.confirm({
		content: $.i18n.prop("JC_OA_IC_056"),
		success:function(){
			mailView.shiftDelete();
		}
	});
};

mailView.shiftDelete = function(){
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/deleteByIds.action",
		data : {
			"ids" : $("#id").val()
		},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_057")
				});
				if($("#index").val()==0
						&&(!$("#showPre").val()||$("#showPre").val()=="false")
						&&(!$("#showNext").val()||$("#showNext").val()=="false")){
					loadrightmenu("/ic/mail/manage.action?fid="+$('#folder').val());
				}else if((!$("#showNext").val()||$("#showNext").val()=="false")
						&&($("#showPre").val()||$("#showPre").val()=="true")){
					mailView.navigate(-2);
				}else{
					mailView.navigate(2);
				}
			}
		}
	});
};

// 移动到···
mailView.moveTo = function(folder) {
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
			if (data > 0) {
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_049")
				});
				if($("#index").val()==0
						&&(!$("#showPre").val()||$("#showPre").val()=="false")
						&&(!$("#showNext").val()||$("#showNext").val()=="false")){
					loadrightmenu("/ic/mail/manage.action?fid="+$('#folder').val());
				}else if((!$("#showNext").val()||$("#showNext").val()=="false")
						&&($("#showPre").val()||$("#showPre").val()=="true")){
					mailView.navigate(-2);
				}else{
					mailView.navigate(2);
				}
			}
		}
	});

};
// 上一封、下一封
mailView.navigate = function(forward) {
	$.ajax({
		url : getRootPath() + "/ic/mail/navigate.action",
		type : 'GET',
		data : {
			
			"mailboxId":$('#mailboxId').val(),
			"receivers[0].folderId":$('#folder').val(),
			"receivers[0].senderUserId":$('#senderUserId').val(),
			"receivers[0].senderMail":$('#senderMail').val(),
			"mailTitle":$('#mailTitleSearch').val(),
			"searchReceiveTimeBegin":$('#searchReceiveTimeBegin').val(),
			"searchReceiveTimeEnd":$('#searchReceiveTimeEnd').val(),
			"index":$('#index').val(),
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
					attach="<dt class=\"control-label\">附 件</dt>" +
							"<dd class=\"controls\">"+
							"<span >";
					$.each(data.attachs,function(i,n){
						if(data.mailboxId==1){
							attach += "<a href=\"javascript:mailView.download('"+n.fileName+"','"+n.resourcesName+"')\""+
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
							attach += "<a href=\"javascript:mailView.downloadExt('"+n.fileName+"','"+data.id+"')\""+
							" class=\"blue-dark m-r-xs\">"+n.fileName+" </a>&nbsp;";
						}
					});
					attach += "</span><i class=\"fa fa-paper-clip\"></i>" +
							"</dd>";
					$("#attachList").html(attach);
				}else{
					$("#attachList").html("");
				}
				
				$('#id').val(data.id);
				$('#index').val(data.index);
				
				$('#mailTitle').html(data.mailTitle);
				$('#senderMail').html(data.senderMail);
				$('#receiver').html(receiver);
				$('#senderTime').html(data.senderTime);
				$('#attach').html(data.senderMail);
				$('#short-header').html(data.senderMail+","+data.senderTime);
				$('#recId').val(data.receiver.id);
				$('#showNext').val(data.showNext);
				$('#showPre').val(data.showPre);         
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
		}
	});
};
// 查看往来
mailView.relation = function() {
	var data = {};
	if($("#mailboxId").val() == 1){
		data = { "senderUserId":$('#senderUserId').val(),"mailboxId":$("#mailboxId").val()};
	}else{
		data = { "senderUserId":$('#senderUserId').val(),"senderMail":$('#senderMail').val(),"receiveMail":$('#receiveMail').val(),"mailboxId":$("#mailboxId").val()};
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
					relationMail+="<a href=\"javascript:mailView.preview(" +n.id+")\">";
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
						if(n.senderUserId==$('#senderUserId').val())
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
						relationAttach+="<a class=\"blue-dark m-r-xs\" href=\"javascript:mailView.download('" +m.fileName+"','"+m.resourcesName+"')\">";
					}
					else{
						relationAttach+="<a class=\"blue-dark m-r-xs\" href=\"javascript:mailView.downloadExt('" +m.fileName+"','"+m.businessId+"')\">";
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
					//relationAttach+="</span><a href=\"javascript:mailView.detail(" +n.id+")\"";
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

mailView.detail = function(id) {
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

mailView.postPsw=function(id){
	$.ajax({
		url : getRootPath() + "/ic/mail/manageView.action",
		type : 'GET',
		data : {"id":id,
			"password":$('#viewPass').val()
			},
			
		dataType : "html",
		success : function(data) {
			$('#previewer').html(data);// 要刷新的div
		}
	});
};

mailView.fastreply = function() {
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
			} else {
				if(data.labelErrorMessage){
					showErrors("mailForm", data.labelErrorMessage);
				} else{
					msgBox.info({
						content: data.errorMessage
					});
				}
			}
		},
		error : function() {
			newmail.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_OA_IC_051"),
				type:"fail"
			});
		}
	});
	}
};

//附件下载
mailView.download = function(fileName,resourcesName) {
	window.open(encodeURI(getRootPath()+"/content/attach/download.action?fileName="+encodeURIComponent(fileName)+"&resourcesName="+resourcesName),"下载附件");
};

mailView.downloadExt=function(fileName,id){
	window.open(encodeURI(getRootPath()+"/ic/mail/download.action?fileName="+encodeURIComponent(fileName)+"&mailId="+id),"下载附件");
};

mailView.preview=function(id){
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

// 初始化
jQuery(function($) {
	//设置首页状态	
	if(parent.msgTip != undefined){
		parent.msgTip.reminders();
	}else{
		opener.parent.msgTip.reminders();
	}
	$('#tbtnReply').click(mailView.replyS);
	$('#tbtnReplyAll').click(mailView.replyA);
	$('#tbtnForwarding').click(mailView.forwarding);
	$('#tbtnPswOk').click(mailView.postPsw);
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
	if($("#mailtoview").val().length<=59){
		$("#mailtoShowView").hide();
	}
	if($("#mailccview").val().length<=59){
		$("#mailccShowView").hide();
	}
	if($("#mailbccview").val().length<=59){
		$("#mailbccShowView").hide();
	}
	if($("#mailShowSingleview").val().length<=59){
		$("#mailShowShowSingleView").hide();
	}
	 $('#mailContent a').removeAttr('target');
	 $('#mailContent a').attr('target', '_blank');
});

//@ sourceURL=mailView.js
