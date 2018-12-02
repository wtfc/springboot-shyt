/*
 * 已发送JS
 * @author zhanglg
 */
var mail = {};

// 重复提交标识
mail.subState = false;

mail.constants={
		SEARCH_METHOD_NON:"0",
		SEARCH_METHOD_SIMPLE:"1",
		SEARCH_METHOD_COMPLEX:"2"
};


mail.options = {
	pressing : "<span class=\"red-dark f18\">!</span>",
	unread : "<i class=\"fa fa-mail m-r-xs green-dark color-555\"></i>",
	read : "<i class=\"fa fa-email-open m-r-xs color-555\"></i>",
	file : "<i class=\"fa fa-paper-clip\"></i>",
	draft: "<i class=\"fa fa-draftbox m-r-xs\" style=\"font-size:18px;\"></i>",
	wasteBox:"<i class=\"fa fa-wastebox m-r-xs\" style=\"font-size:18px;\"></i>",
	send:"<i class=\"fa fa-Outbox m-r-xs\" style=\"font-size:18px;\"></i>",
	search:mail.constants.SEARCH_METHOD_NON,
	listURL: "/ic/mail/manageList.action?t="+(new Date()).getTime(),
	mailbox:"1",
	index:0
};

// 清空表单
mail.clearForm = function() {
	 $('#mailForm').find("input[type=text]").val("");
	 $('#folderform').find("input[type=text]").val("");
	 $('#folderform').find("input[type=checkbox]").attr("checked",false);
	 $('#mailForm').find("input[type=checkbox]").attr("checked",false);
	 $('#list').find("input[type=checkbox]").attr("checked",false);
	 $('#mailTable').find("input[type=checkbox]").attr("checked",false);
	 
};

// 分页对象
mail.oTable = null;
// 列数据填充模型
mail.oTableAoColumns = [
		{// 复选框
			mData : function(source) {
				return "<input type=\"checkbox\" name=\"ids\" value="
						+ source.id + "><input type=\"hidden\" name=\"mrid\" id=\"mrid\" value="
						+ source.receivers[0].id + ">";
			}
		},
		{// 状态
			mData : function(source) {
				var status = "";
				if(source.receivers[0].folderId == 2)
				{
					status += mail.options.draft;
				}else if(source.receivers[0].folderId == 3){
					status += mail.options.send;
				}else if(source.receivers[0].folderId ==4){
					status += mail.options.wasteBox;
				} else if (source.receivers[0].readFlag == 1) {
					status += "<a href=\"javascript:mail.setReadStatusById("
							+ source.id + "," + source.receivers[0].id
							+ ",0);	\" id=\"lightread"+ source.receivers[0].id  + "\">" + mail.options.read + "</a>";
				} else {
					status += "<a href=\"javascript:mail.setReadStatusById("
							+ source.id
							+ "," + source.receivers[0].id + ",1);\" id=\"lightread"
							+ source.receivers[0].id  + "\">"
							+ mail.options.unread + "</a>";
				}
				
				if (source.pressing == 1) {
					status += mail.options.pressing;
				}	
				return status;
			}
		},
		{// 发件人
			mData :function(source){
				if(source.receivers[0].folderId==3){
//					if(source.receiveUsers.length>7&&source.mailboxId==1||source.receiveUsers.length>18&&source.mailboxId!=1){
//						if(source.mailboxId==1){
//							return source.receiveUsers.substring(0,7)+"...";
//						}else{
//							return source.receiveUsers.substring(0,18)+"...";
//						}
//						
//					}else{
						return source.receiveUsers;
//					}
				}else{
					return source.mailboxId>1? source.senderMail:source.senderUserName;
				}
			}
		},
		{
			mData :function(source){
				if(source.receivers[0].folderId==3){
//					return "<a class=\"dark email-pucker\"href=\"#email-list\" role=\"button\" data-toggle=\"modal\" id=\"email-aid\">"+
//		     		   "<i class=\"fa fa-users blue-dark\" data-toggle=\"tooltip\" data-placement=\"top\"" +
//		     		   "title=\"\" data-container=\"body\" data-original-title=\"点击查看收件人\" onclick=\"mail.tooltip(this,"+source.id+","+ source.receivers[0].folderId + ")\"></i></a>";
					return "<div class=\"pomailDiv accordion-toggle dark\" role=\"button\" onmouseleave=\"mail.isTooltip();\"  onmouseenter=\"mail.tooltip(this,"+source.id+","+ source.receivers[0].folderId + ");\"><i class=\"fa fa-users\"  ></i></div>";
				}else{
					return null;
				}
			}
			
		    
		},
		{// 是否在线
			mData :function(source){
				if(source.receivers[0].folderId==1&&source.mailboxId==1){
					if(source.isOnline=="1"){
						return "<i class=\"fa fa-user blue-dark\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"在线\"></i>";
					}else if(source.isOnline=="0"){
						return "<i class=\"fa fa-user\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"离线\"></i>";
					}else{
						return "";
					}
				}else{
					return null;
				}
			}
		},
		{// 星标
			mData : function(source) {
				if(source.receivers[0].folderId!=2){
					if (source.receivers[0].starMail == 1) {
						if(source.receivers[0].folderId == 4){
							return "<i class=\"fa fa-star yellow-dark fr f-s-md\" id=\"lightstar"
							+ source.receivers[0].id  + "\"></i>";
						}
						else{							
							return "<a href=\"javascript:mail.setStar("
							+ source.id
							+ ","+source.receivers[0].folderId+","+ source.receivers[0].id + ");\" class=\"fr\"><i class=\"fa fa-star yellow-dark f-s-md\" id=\"lightstar"
							+ source.receivers[0].id  + "\"></i></a>";
						}
					} else {
						if(source.receivers[0].folderId != 4){
						return "<a href=\"javascript:mail.setStar("
								+ source.id
								+ ","+source.receivers[0].folderId+","+ source.receivers[0].id + ");\" class=\"fr\"><i class=\"fa fa-star f-s-md\" id=\"lightstar"
								+ source.receivers[0].id  + "\"></i></a>";
						}
						else{
							return "";
						}
					}
				}else{
					return "";
				}
				
			}
		},
		{// 标题
			mData : function(source) {
				var mailTitle;
				if(source.mailTitle != null && source.mailTitle != "" ){
					mailTitle = $.escapeHtml(source.mailTitle);
				}
				else{
					mailTitle = "( 无主题  )";
				}
				
				if(source.receivers[0].folderId == 2){
					if (source.receivers[0].starMail == 1) {
						
						return "<div class=\"email-theme red-dark\" id=\"m_title_" + source.receivers[0].id  + "\" title=\"" + mailTitle + "\" style = \" cursor :pointer;\" onclick = mail.write(" + source.id
								+ ","+ source.receivers[0].id + ");>" + mailTitle + "</div>";
					} else {
						if(source.receivers[0].readFlag == 1)
							{
							return "<div class=\"email-theme\" id=\"m_title_" + source.receivers[0].id  + "\" title=\"" + mailTitle + "\" style = \" cursor :pointer \" onclick = mail.write(" + source.id
							+ ","+ source.receivers[0].id + ");>"
							+ mailTitle + "</div>";
							
							}else{
								return "<div class=\"email-theme blue-dark bold\" id=\"m_title_" + source.receivers[0].id  + "\" title=\"" + mailTitle + "\" style = \" cursor :pointer;\" onclick=mail.write(" + source.id
								+ ","+ source.receivers[0].id + ");>"
								+ mailTitle + "</div>";
							}
					}
				}
				else{
					if (source.receivers[0].starMail == 1) {
						
						if(source.receivers[0].readFlag == 1){
							return "<div class=\"email-theme red-dark\" id=\"m_title_" + source.receivers[0].id  + "\" style = \"cursor :pointer;\" onclick =mail.detail(" + source.id
							+ ","+source.index+","+ source.receivers[0].id + ","+ source.receivers[0].folderId + ","+ source.showNext+ ","+ source.showPre+ ");>" + mailTitle + "</div>";
						}else{
							return "<div class=\"email-theme red-dark bold\" id=\"m_title_" + source.receivers[0].id  + "\" title=\"" + mailTitle + "\" style = \" cursor :pointer; \" onclick =mail.detail(" + source.id
							+ ","+source.index+","+ source.receivers[0].id + ","+ source.receivers[0].folderId + ","+ source.showNext+ ","+ source.showPre+ ");>" + mailTitle + "</div>";
						}
						
					} else {
						if(source.receivers[0].readFlag == 1){
							return "<div class=\"email-theme accordion-toggle\" id=\"m_title_" + source.receivers[0].id  + "\" title=\"" + mailTitle + "\" style = \" cursor :pointer \" onclick = mail.detail(" + source.id
							+ ","+source.index+","+ source.receivers[0].id + ","+ source.receivers[0].folderId + ","+ source.showNext+ ","+ source.showPre+ ");>"
							+ mailTitle + "</div>";
							
						}
						else{
							return "<div class=\"email-theme blue-dark bold\" id=\"m_title_" + source.receivers[0].id  + "\" title=\"" + mailTitle + "\"  id=\"m_title_" + source.id + "\"style = \" cursor :pointer; \" onclick =mail.detail(" + source.id
							+ ","+source.index+","+ source.receivers[0].id + ","+ source.receivers[0].folderId + ","+ source.showNext+ ","+ source.showPre+ ");>"
							+ mailTitle + "</div>";
						}
						
					}
			    }
			}
		},
		{// 附件
			mData : function(source) {
				if (source.isfile == 1) {
					return mail.options.file;
				}
				return "";
			}
		},
		{// 收件人 
			mData:function(source){
//				var title="";
//				$.each(source.receivers,function(i,n){
//					if(source.mailboxId==1){
//						title+=n.receiveUserName;
//					}
//					else{
//						title+=n.receiveMail;
//					}
//				});
				
//				return "<i class=\"fa fa-users\" data-toggle=\"tooltip\" data-placement=\"top\" title=\""+title+"\" data-container=\"body\" data-original-title=\"收件人a,收件人b\"></i>";
				//Tooltip
				if(source.receivers[0].folderId != 3){
					if (source.isHaveReceiveUser=="0") {
						return "<div class=\"pomailDiv accordion-toggle dark\" role=\"button\" onmouseleave=\"mail.isTooltip();\" onmouseenter=\"mail.tooltip(this,"+source.id+","+ source.receivers[0].folderId + ");\"><i class=\"fa fa-users\"  ></i></div>";
//						return "<i class=\"fa fa-users\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"无收件人\"></i>";
					}else{
//						return "<a class=\"dark email-pucker\"href=\"#email-list\" role=\"button\" data-toggle=\"modal\" id=\"email-aid\">"+
//			     		   "<i class=\"fa fa-users blue-dark\" data-toggle=\"tooltip\" data-placement=\"top\"" +
//			     		   "title=\"\" data-container=\"body\" data-original-title=\"点击查看收件人\" onclick=\"mail.tooltip(this,"+source.id+","+ source.receivers[0].folderId + ")\"></i></a>";
					return "<div class=\"pomailDiv accordion-toggle dark\" role=\"button\" onmouseleave=\"mail.isTooltip();\" onmouseenter=\"mail.tooltip(this,"+source.id+","+ source.receivers[0].folderId + ");\"><i class=\"fa fa-users blue-dark\"  ></i></div>";
				  }
				}else{
					//return source.mailboxId>1? source.senderMail:source.senderUserName;
					return null;
				}
				
        	
				//return "<i class=\"fa fa-users\" id=\"tooltip\" onmouseover=\"mail.tooltip(this,"+source.id+")\"></i>";
			}
		}
		, {// 时间
			mData : function(source){
				if($('#mailFolderId').val() == "2" || $('#mailFolderId').val() == "3"){
					return source.senderTime;
				}else{
					return source.receiveTime2;
				}
			}
		} ,
		{// 操作
			mData : function(source){
			if($('#mailFolderId').val() == "3" ){
				if(source.mailboxId =="1"){ 
					if(source.isRead=="1"){
						return "";
					}
					else{
						return "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"mail.mailRecover("+source.id+")\" role=\"button\" data-toggle=\"modal\">召回</i></a>";
					}
					}
				else{
						return "";
					}
				}else{
					return "";
				}
			}
		}
		];

var req;

mail.tooltip=function(element,id,folderId){
	var title = "",old = id;
	$('#consignee').html("");
	req = $.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/getReceiveUser.action",
		data : {
			"id" : id,
			"folderId":folderId,
			"consigneeStatus" :"consigneeStatus"
		},
		dataType : "json",
		success : function(data) {
			if(data.receivers != undefined && data.receivers != ""){
				for(var i=0;i<data.receivers.length;i++){
					if(data.receivers[i].readDate!="" && data.receivers[i].readDate!=null){
						title += "<span class=\"email-f-c\">";
						title += (data.mailboxId==1)?data.receivers[i].receiveUserName:data.receivers[i].receiveMail;
						title += ("("+data.receivers[i].readDate+")");
						title += "</span>";
					}else{
						title += (data.mailboxId==1)?data.receivers[i].receiveUserName:data.receivers[i].receiveMail;
					}
					if(i != data.receivers.length-1){
						title += ";";
					}
				}
			}else{
				title = "此邮件无收件人";
			}
			if(title != ""){
				var options = {
			            "placement" : (folderId == 3)?"right":"left",
			            "trigger" : "hover",    	// 显示方式  
			            "content" : title,        	// tooltip中内容  
			            "html" : "true",   			// 如果为true，title可以为html代码  
			            "titleId":id
			    	}; 
				$(element).pomail(options);
			}
		},
		error : function() {
			if(req.statusText == "abort") return;
			msgBox.tip({
				content: $.i18n.prop("JC_OA_IC_040")
			});
		}
	});
};

mail.isTooltip = function(){
	if(req != null)
	    req.abort();
}

// 组装后台参数
mail.oTableFnServerParams = function(aoData) {
	// 排序条件
	getTableParameters(mail.oTable, aoData);
	// 组装查询条件
	aoData.push({ "name": "receivers[0].folderId", "value": $('#mailFolderId').val()});
	mail.processUrl("receivers[0].folderId="+$('#mailFolderId').val());
	mail.processUrl("mailFolderName="+$('#mailFolderName').val());
	if($('#starMail').val()!=""){
		aoData.push({"name":"receivers[0].starMail","value":$('#starMail').val()});
		mail.processUrl("receivers[0].starMail="+$('#starMail').val());
		aoData.push({"name":"excludeFolderId","value":$('#excludeFolderId').val()});
		mail.processUrl("excludeFolderId="+$('#excludeFolderId').val());
	}
	//未读邮箱时'#readFlag').val()=0
//	if($('#readFlag').val()!=""){
//		aoData.push({"name":"receivers[0].readFlag","value":$('#readFlag').val()});
//		mail.processUrl("receivers[0].readFlag="+$('#readFlag').val());
//	}
	if(mail.options.mailbox!="0"){
		aoData.push({"name":"mailboxId","value":mail.options.mailbox});
		mail.processUrl("mailboxId="+mail.options.mailbox);
		mail.processUrl("mailboxAddress="+$('#selected-mailbox').html());
	}
	//简单搜索
	if(mail.options.search==mail.constants.SEARCH_METHOD_SIMPLE)
	{
		aoData.push({"name":"mailEasyTitle","value":$('#simple-search-title').val()});
		mail.processUrl("mailEasyTitle="+$('#simple-search-title').val());
	}
	//高级搜索
	else if(mail.options.search==mail.constants.SEARCH_METHOD_COMPLEX)
	{
		aoData.push({"name":"mailTitle","value":$('#complex-search-title').val()});
		mail.processUrl("mailTitle="+$('#complex-search-title').val());
		if($('#mailFolderId').val()=="3"){
			if($('#selected-mailbox').html()=="内部邮箱"){
				aoData.push({"name":"receivers[0].receiveUserIdSearch","value":$('#senderUserId').val()});
				mail.processUrl("receivers[0].receiveUserIdSearch="+$('#senderUserId').val());
			}else{
				aoData.push({"name":"receivers[0].receiveMailSearch","value":$('#complex-search-sender').val()});
				mail.processUrl("receivers[0].receiveMailSearch="+$('#complex-search-sender').val());
			}
		}else{
			if($('#selected-mailbox').html()=="内部邮箱"){
				aoData.push({"name":"senderUserId","value":$('#senderUserId').val()});
				mail.processUrl("senderUserId="+$('#senderUserId').val());
			}else{
				aoData.push({"name":"senderMail","value":$('#complex-search-sender').val()});
				mail.processUrl("senderMail="+$('#complex-search-sender').val());
			}
		}
		//收件人条件、时间条件
		aoData.push({"name":"searchReceiveTimeBegin","value":$('#searchReceiveTimeBeginDiv').val()});
		mail.processUrl("searchReceiveTimeBegin="+$('#searchReceiveTimeBeginDiv').val());
		aoData.push({"name":"searchReceiveTimeEnd","value":$('#searchReceiveTimeEndDiv').val()});
		mail.processUrl("searchReceiveTimeEnd="+$('#searchReceiveTimeEndDiv').val());
		
		aoData.push({"name":"receivers[0].readFlag","value":$('#complex-search-mailStatus').val()});
		mail.processUrl("receivers[0].readFlag="+$('#complex-search-mailStatus').val());
	}
};

// 分页查询
mail.mailList = function() {
	mail.options.index=0;
	if (mail.oTable == null) {
		mail.oTable = $('#mailTable').dataTable({
			bDestroy:true,
		    bSort: false,
			iDisplayLength : mail.pageRows,
			sAjaxSource : getRootPath() +mail.options.listURL,
			fnServerData : oTableRetrieveData,
			aoColumns : mail.oTableAoColumns,
			fnServerParams : function(aoData) {
				mail.oTableFnServerParams(aoData);
			},
		    fnInitComplete: function(){   
				 ListTable();
	        },
			//aaSorting:[],//设置默认排序列
			//默认不排序列
	       //aoColumnDefs: [{"bSortable": false, "aTargets": [0, 1, 2, 3, 4, 5, 6,7,8,9,10]}]
		});
	} else {
		mail.oTable.fnDraw();
	}
};
// 设置已读状态
mail.setReadStatusR=function(){
	mail.setReadStatus(1);
};
mail.setReadStatusUR=function(){
	mail.setReadStatus(0);
};
mail.setReadStatus = function(flag) {
	var idsArr = [];
	var mrIdsArr = [];
	var ishaveRecoverMail = false;
	var mrid;
	$("[name='ids']:checked").each(function() {
		idsArr.push($(this).val());
		mrid = $(this).next().val();
		mrIdsArr.push(mrid);
		if(mail.isMailRecover(mrid)){
			ishaveRecoverMail = true;
			return false;
		}
	});
	if(ishaveRecoverMail){
		mail.mailList();
		return;
	}
	var ids = idsArr.join(",");
	var mrids = mrIdsArr.join(",");

	if (ids.length == 0) {
		msgBox.tip({
			content:$.i18n.prop("JC_OA_IC_052"),
			type:"fail"
		});

		return;
	}
	
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/setReadStatus.action",
		data : {
			"ids" : ids,
			"mrids":mrids,
			"status":flag
		},
		dataType : "json",
		success : function(data) {
			if (data ==true) {
				mail.clearForm();
				for(var i=0;i<mrIdsArr.length;i++){
					var id = mrIdsArr[i];
					if(flag == 0){
						if(!$("#lightstar" + id).hasClass("yellow-dark")){
							$("#m_title_" + id).addClass("blue-dark");
						}
						//收件箱因为发件箱无需更换图表
						if($("#mailFolderId").val()==1){
							$("#lightread" + id).find("i").removeClass("fa-email-open");
							$("#lightread" + id).find("i").addClass("fa-mail green-dark");
						}
						$("#m_title_" + id).addClass("bold");
					}else{
						$("#m_title_" + id).removeClass("blue-dark");
						//收件箱
						if($("#mailFolderId").val()==1){
							$("#lightread" + id).find("i").removeClass("fa-mail green-dark");
							$("#lightread" + id).find("i").addClass("fa-email-open");
						}
						$("#m_title_" + id).removeClass("bold");
					}
				}
				$("#mailTable").hide();
				$("#mailTable").show();
//				mail.mailList();
			}
			//设置首页状态
			parent.msgTip.reminders();	
		}
	});
};
//点击图标设置已读、未读
mail.setReadStatusById = function(id,mrids,flag) {
	if(mail.isMailRecover(mrids)){
		mail.mailList();
		return;
	}
	if($("#lightread" + mrids).find("i").hasClass("fa-email-open")){
		flag = 0;
	}else{
		flag = 1;
	}
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/setReadStatus.action",
		data : {
			"ids" : id,
			"mrids":mrids,
			"status":flag
		},
		dataType : "json",
		success : function(data) {
			if (data ==true) {
				mail.clearForm();
				if($("#lightread" + mrids).find("i").hasClass("fa-email-open")){
					if(!$("#lightstar" + mrids).hasClass("yellow-dark")){
						$("#m_title_" + mrids).addClass("blue-dark");
					}
					$("#lightread" + mrids).find("i").removeClass("fa-email-open");
					$("#lightread" + mrids).find("i").addClass("fa-mail green-dark");
					$("#m_title_" + mrids).addClass("bold");
				}else{
					if(!$("#lightstar" + mrids).hasClass("yellow-dark")){
						$("#m_title_" + mrids).removeClass("blue-dark");
					}
					$("#lightread" + mrids).find("i").removeClass("fa-mail green-dark");
					$("#lightread" + mrids).find("i").addClass("fa-email-open");
					$("#m_title_" + mrids).removeClass("bold");
				}
				$("#mailTable").hide();
				$("#mailTable").show();
//				mail.mailList();
				//设置首页状态	
				parent.msgTip.reminders();
			}
		}
	});
};
// 转发
mail.forwarding = function() {
	var idsArr = [];
	$("[name='ids']:checked").each(function() {
		idsArr.push($(this).val());
	});
	var ids = idsArr.join(",");

	if (ids.length == 0) {
		msgBox.tip({
			content:$.i18n.prop("JC_OA_IC_052"),
			type:"fail"
		});
		return;
	}
	loadrightmenu("/ic/mail/manageForwarding.action?ids="+ids);
	
};
// 删除点击事件
mail.deleteClick = function() {
	var idsArr = [];
	var mrIdsArr = [];
	var ishaveRecoverMail = false;
	var mrid;
	$("[name='ids']:checked").each(function() {
		idsArr.push($(this).val());
		mrid = $(this).next().val();
		mrIdsArr.push(mrid);
		if(mail.isMailRecover(mrid)){
			ishaveRecoverMail = true;
			return false;
		}
	});
	if(ishaveRecoverMail){
		mail.mailList();
		return;
	}
	var ids = idsArr.join(",");
	var mrids = mrIdsArr.join(",");
	if (ids.length == 0) {
		msgBox.tip({
			content:$.i18n.prop("JC_OA_IC_053"),
			type:"fail"
		});
		return;
	}

	msgBox.confirm({
		content:$.i18n.prop("JC_OA_IC_054"),
		success:function(){mail.deleteMail(ids,mrids,idsArr);
		}
	});

};
// 删除处理
mail.deleteMail = function(ids,mrids,idsArr) {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/moveToByIds.action",
		data : {
			"ids" : ids,
			"mrids":mrids,
			"fromFolder":$('#mailFolderId').val(),
			"toFolder":4
		},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				msgBox.tip({
					content:$.i18n.prop("JC_OA_IC_048"),
					type:"success"
				});
				mail.clearForm();
				mail.mailList();
				//获得datatable分页当页第一条记录是中条数的第多少条
				start = $("#mailTable").dataTable().fnSettings()._iDisplayStart; 
				//获得datatable一共多少条
				total = $("#mailTable").dataTable().fnSettings().fnRecordsDisplay(); 
				//获得datatable每页多少条
				len   = $("#mailTable").dataTable().fnSettings()._iDisplayLength;
				//如果是最后一页，将当前页记录全部删除跳转至前一页
				if((total-start)<=len&&((total-start)-idsArr.length)==0){
					$("#mailTable").dataTable().fnPageChange( 'previous', true );
				}
			}
		}
	});
};
//删除草稿点击事件
mail.deleteDraftClick = function() {
	var idsArr = [];
	$("[name='ids']:checked").each(function() {
		idsArr.push($(this).val());
	});
	var ids = idsArr.join(",");

	if (ids.length == 0) {
		msgBox.tip({
			content:$.i18n.prop("JC_OA_IC_053"),
			type:"fail"
		});
		return;
	}

	msgBox.confirm({
		content:$.i18n.prop("JC_OA_IC_055"),
		success:function(){mail.deleteDraft(ids);
		}
	});
};
// 删除草稿处理
mail.deleteDraft = function(ids) {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/deleteByIds.action",
		data : {
			"ids" : ids
		},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_068")
				});

				mail.clearForm();
				mail.mailList();
			}
		}
	});
};

//彻底删除点击事件
mail.shiftDeleteClick = function() {
	var mrIdsArr = [];
	$("[name='ids']:checked").each(function() {
		mrIdsArr.push($(this).next().val());
	});
	var mrids = mrIdsArr.join(",");

	if (mrids.length == 0) {
		msgBox.tip({
			content:$.i18n.prop("JC_OA_IC_053"),
			type:"fail"
		});

		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_OA_IC_056"),
		success:function(){mail.shiftDelete(mrids,mrIdsArr);
		}
	});
};
// 彻底删除处理
mail.shiftDelete = function(ids,mrIdsArr) {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/deleteByIds.action",
		data : {
			"ids" : ids,
			"mailboxId":mail.options.mailbox
		},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_057")
				});

				mail.clearForm();
				mail.mailList();
				//获得datatable分页当页第一条记录是中条数的第多少条
				start = $("#mailTable").dataTable().fnSettings()._iDisplayStart; 
				//获得datatable一共多少条
				total = $("#mailTable").dataTable().fnSettings().fnRecordsDisplay(); 
				//获得datatable每页多少条
				len   = $("#mailTable").dataTable().fnSettings()._iDisplayLength;
				//如果是最后一页，将当前页记录全部删除跳转至前一页
				if((total-start)<=len&&((total-start)-mrIdsArr.length)==0){
					$("#mailTable").dataTable().fnPageChange( 'previous', true );
				}
			}
		}
	});
};

// 移动
mail.moveTo=function(folder){
	var idsArr = [];
	var mrIdsArr = [];
	var ishaveRecoverMail = false;
	var mrid;
	$("[name='ids']:checked").each(function() {
		idsArr.push($(this).val());
		mrid = $(this).next().val();
		mrIdsArr.push(mrid);
		if(mail.isMailRecover(mrid)){
			ishaveRecoverMail = true;
			return false;
		}
	});
	if(ishaveRecoverMail){
		mail.mailList();
		return;
	}
	var ids = idsArr.join(",");
	var mrids = mrIdsArr.join(",");

	if (ids.length == 0) {
		msgBox.tip({
			content:$.i18n.prop("JC_OA_IC_058"),
			type:"fail"
		});
		ie8StylePatch();
		iePlaceholderPatch();
		$("#simple-search-title").trigger("blur.placeholder");
		return;
	}
	
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/moveToByIds.action",
		data : {
			"ids" : ids,
			"mrids":mrids,
			"fromFolder":$('#mailFolderId').val(),
			"toFolder":folder
		},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				msgBox.tip({
					type:"success",
					content: $.i18n.prop("JC_OA_IC_049")
				});

				mail.clearForm();
				mail.mailList();
				
				//获得datatable分页当页第一条记录是中条数的第多少条
				start = $("#mailTable").dataTable().fnSettings()._iDisplayStart; 
				//获得datatable一共多少条
				total = $("#mailTable").dataTable().fnSettings().fnRecordsDisplay(); 
				//获得datatable每页多少条
				len   = $("#mailTable").dataTable().fnSettings()._iDisplayLength;
				//如果是最后一页，将当前页记录全部删除跳转至前一页
				if((total-start)<=len&&((total-start)-mrIdsArr.length)==0){
					$("#mailTable").dataTable().fnPageChange( 'previous', true );
				}
			}
		}
	});
};
// 单邮件设置星标
mail.setStar=function(id,folderId,mrids){
	if(mail.isMailRecover(mrids)){
		mail.mailList();
		return;
	}
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
			if (data > 0) {
				//设置星标颜色
				$("#lightstar" + mrids).toggleClass("yellow-dark");
				$("#mailTable").hide();
				$("#mailTable").show();
				//如果设为星标添加红色
				if($("#lightstar" + mrids).hasClass("yellow-dark")){
					//如果添加星标判断是否是未读，如果是未读去掉蓝色
					if($("#m_title_" + mrids).hasClass("bold")){
						$("#m_title_" + mrids).removeClass("blue-dark");
					}
					$("#m_title_" + mrids).addClass("red-dark");
				}else{
					//如果取消星标判断是否是未读，如果是未读添加蓝色
					if($("#m_title_" + mrids).hasClass("bold")){
						$("#m_title_" + mrids).addClass("blue-dark");
					}
					$("#m_title_" + mrids).removeClass("red-dark");
				}
			}
		}
	});
};
// 设置星标
mail.setStarByIds = function() {
	var idsArr = [];
	$("[name='ids']:checked").each(function() {
		idsArr.push($(this).val());
	});
	var ids = idsArr.join(",");

	if (ids.length == 0) {
		msgBox.tip({
			content:$.i18n.prop("JC_OA_IC_052"),
			type:"fail"
		});
		return;
	}
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/setStarById.action",
		data : {
			"ids" : ids,
			"folder":$('#mailFolderId').val()
		},
		dataType : "json",
		success : function(data) {
			if (data ==true) {
				mail.mailList();
			}
		}
	});
};
//判断操作数据是否被召回
mail.isMailRecover = function(mrid){
	var flag = false;
	$.ajax({
		url:getRootPath()+"/ic/mailRecord/get.action",
		type:"POST",
		data:{'id':mrid},
		async:false,
		success:function(data){
			if(data.recoverFlag==1){
				msgBox.tip({
					content:$.i18n.prop("JC_OA_IC_095"),
					type:"fail"
				});
				flag = true;
			}
		}
	});
	return flag;
};

// 阅读
mail.detail = function(id,index,mrid,folderId,showNext,showPre) {
	if(mail.isMailRecover(mrid)){
		mail.mailList();
		return;
	}
	var mailboxId = mail.options.mailbox;
	var mailTitle = $('#mailTitle').val();
	var mailEasyTitle = $('#mailEasyTitle').val();
	var searchReceiveTimeBegin = $('#searchReceiveTimeBegin').val();
	var searchReceiveTimeEnd = $('#searchReceiveTimeEnd').val();
	var sendMail = $('#senderMail').val();
	var senderUserIdNav = $('#senderUserIdNav').val();
	var receiversReadFlag = $('#receiversReadFlag').val();
//	var url=escape($('#url').val());
	var url=$('#url').val().replace("\?",escape("?")).replace(/&/g,escape("&")).replace(/=/g,escape("="));
	//loadrightmenu("/ic/mail/manageDetail.action?id="+id+"&index="+index+"&mrid="+mrid+"&folderId="+folderId+"&returnURL="+url);
	jQuery.ajax({
		url : getRootPath()+"/ic/mail/manageDetail.action?id="+id+"&index="+index+"&mrid="+mrid+"&folderId="+folderId,
		type : 'POST',
		data:{
			'returnURL':url,
			'mailboxId':mailboxId,
			'mailTitle':mailTitle,
			'mailEasyTitle':mailEasyTitle,
			'searchReceiveTimeBegin':searchReceiveTimeBegin,
			'searchReceiveTimeEnd':searchReceiveTimeEnd,
			'senderMail':sendMail,
			'senderUserId':senderUserIdNav,
			'receivers[0].readFlag':receiversReadFlag,
			'showNext':showNext,
			'showPre':showPre
		},
		async:false,
		success : function(data) {
			if(data.success == "false"){
				msgBox.info({content: $.i18n.prop("JC_OA_IC_040"), type:'fail'});
			}else {
				$("#scrollable").html(data);
			}
			//设置首页状态	
			parent.msgTip.reminders();
		}
	});
};
//编辑草稿
mail.write=function(id,mrid){
	var url=$('#url').val().replace("\?",escape("?")).replace(/&/g,escape("&")).replace(/=/g,escape("="));
	loadrightmenu("/ic/mail/manageWrite.action?id="+id+"&mrid="+mrid+"&url="+url);
};
//设置其它文件夹条件
mail.fid=function(id,folderName){
	var mfid = $('#mailFolderId').val(id);
	$('#mailFolderId').val(id)
	$('#selected-fid').html(folderName);
	$('#mailFolderName').val(folderName);
//判断其他文件显示已发送
	if(id == 1 || mfid == 1){
	  $(".inBoxR").hide();
	}else{
	  $(".inBoxR").show();
	}
//判断当前文件在移动列表中不显示
	$.each($("[class = 'otherFolder']"),function(i,o){
		if(id == o.value){
			$(this).addClass("hide");			
		}
	});
//显示移动至列表不包含当前选中的文件夹	
	$.each($("[class = 'otherFolder hide']"),function(i,f){
		if(id != f.value){
			$(this).removeClass("hide");
		}
	});
	mail.processUrl("receivers[0].folderId="+$('#mailFolderId').val());
	mail.processUrl("mailFolderName="+$('#mailFolderName').val());
	mail.oTable=null;
	mail.mailList();
	iePlaceholderPatch();
	$("#simple-search-title").trigger("blur.placeholder");
};

mail.processUrl=function(param){
	var url=$('#url').val();
	if(url.indexOf('?')==-1){
		url+='?';
	}
	
	var para=param.split('=')[0];
	var value=param.split('=')[1];
	
//	var reg=new RegExp("(?<=)("+para.replace("\[","\\[").replace("\]","\\]")+"=.)(?=\&?)");
	var reg=new RegExp("("+para.replace("\[","\\[").replace("\]","\\]")+"=.*)(?=\&?)");
	if(reg.exec(url)){
		url=url.replace(reg,param);
	}
	else{
		url+=("&"+param);
	}
	
	$('#url').val(url);
};
// 保存文件夹
mail.saveFolder=function(hide){
	if (mail.subState)return;
	mail.subState = true;
	if ($("#folderform").valid()) {
		var url = getRootPath()+"/ic/mail/saveFolder.action?time=" +(new Date()).getTime();
		var formData = $("#folderform").serializeArray();
		formData.push({"name": "token", "value": $('#token').val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				mail.subState = false;
				$('#token').val(data.token);	
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if ($("#id").val() == 0) {
						mail.clearForm();
					} else {
						$("#modifyDate").val(data.modifyDate);
					}
					mail.initFolders();
					if (hide) {
						$('#new-folder').modal('hide');
					}
				} else {
					if(data.labelErrorMessage){
						showErrors("folderForm", data.labelErrorMessage);
					} else if (data.errorMessage) {
						msgBox.tip({
							content: data.errorMessage,
							type:"fail"
						});
					} else {
						msgBox.tip({
							content: data.errorMessage,
							type:"fail"
						});

					}
				}
			},
			error : function() {
				mail.subState = false;
				msgBox.tip({
					content: data.errorMessage,
					type:"fail"
				});

			}
		});
	} else {
		mail.subState = false;
		msgBox.info({
			content: $.i18n.prop("JC_SYS_067")
		});
	}
};
//
mail.showFolderForm=function(){
	// 清除验证信息
	hideErrorMessage();
	$('#newFolders').html("新增");
	mail.clearForm();
	$('#id').val(0);
	mail.subState = false;
	$('#new-folder').modal('show');
};

// 删除文件夹
mail.deleteFolder=function(id){
	var ids = String(id);
	var mfid = $("#mailFolderId").val();
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		msgBox.tip({
			content: $.i18n.prop("JC_OA_IC_053"),
			type:"fail"
		});
		return;
	}
	if(id == mfid){
		msgBox.tip({
			content: $.i18n.prop("JC_OA_IC_084"),
			type:"fail"
		});
		return;
	}
	if(!DeleteCascade.syncCheckCanBatchDelete("mailFolder",ids)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_IC_067")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			mail.deleteCallBack(ids);
		}
	});
};
mail.deleteCallBack = function(ids) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/mail/deleteFolder.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_005"),
					type:"success"
				});
				mail.clearForm();
				mail.initFolders();
			}
			else{
				msgBox.tip({
					content: data.errorMessage,
					type:"fail"
				});
			}
		}
	});
};
//初始化文件夹列表
mail.initFolders=function(){
	$.ajax({
		type : "GET",
		url : getRootPath() + "/ic/mail/initMailFolders.action",
		dataType : "json",
		success : function(data) {
			var folders="";
			var folderRows="";
			var folderIdCondition= "";
			if(data.length == 0){
				folderIdCondition = "<li id=\"noFolder\"><em class=\"m-l\">还没有其他文件夹</em></li>"
									+"<li><a href=\"javascript:mail.fid(1,'其他文件夹')\">返回收件箱</a></li>";
				$('#selected-fid').html("其他文件夹");
			}else{
				folderIdCondition ="<li><a href=\"javascript:mail.fid(1,'其他文件夹')\">返回收件箱</a></li>";
			}
			if (data!=null && data.length>0) {
				$.each(data,function(i,n){
					var mfid = $("#mailFolderId").val();
					if(n.id == mfid){
						folders+="<li class='otherFolder hide' value="+n.id+"><a href=\"javascript:mail.moveTo("+n.id+");\">"+n.folderName+"</a></li>";	
					}else{
						folders+="<li class='otherFolder' value="+n.id+"><a href=\"javascript:mail.moveTo("+n.id+");\">"+n.folderName+"</a></li>";	
					}
					folderRows+=
						("<tr>"
						+"<td>"+n.folderName+"</td>"
						+"<td class=\"w100\"><a class=\"a-icon i-edit m-r-xs\""
						+"	href=\"#\" role=\"button\" data-toggle=\"modal\" onclick=\"mail.get("+n.id+")\"> <i"
						+"		class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\""
						+"		title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i>"
						+"</a> <a class=\"a-icon i-remove\" href=\"javascript:mail.deleteFolder("+n.id+");\"> <i class=\"fa fa-remove\""
						+"		data-toggle=\"tooltip\" data-placement=\"top\" title=\"\""
						+"		data-container=\"body\" data-original-title=\"删除\"></i>"
						+"</a></td>"
						+"</tr>");
					folderIdCondition+="<li><a href=\"javascript:mail.fid("+n.id+",'"+n.folderName+"')\">"+n.folderName+"</a></li>";
				});
				$('#moveTo .otherFolder').remove();
				
			} 
			$('#mailFolder').html(folderIdCondition);
			$('#moveTo').append(folders);
			$('#mailFolderTable').html(folderRows);
			$("[data-toggle=tooltip]").tooltip();
			$('#cog-folder').modal("setPaddingTop");
			//最后一个文件夹删除后，移动至下拉列表未实时更新，更改为删除后将其他文件夹节点删除 start add by songht
			if(data.length==0){
				$('#moveTo .otherFolder').remove();
			}
		}
	});
	
};
//获取修改文件夹信息
mail.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/mail/getFolder.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$('#newFolders').html("编辑");
				mail.clearForm();
				$("#folderform").fill(data);
				$('#new-folder').modal('show');
			}
		}
	});
};
// 收邮件
mail.receive=function(){
	if(mail.options.mailbox=="1"){
		return;
	}
	$(".mail-loading").removeClass("hide");
	$.ajax({
			type : "GET",
			url : getRootPath() + "/ic/mail/receive.action",
			data:{"mailboxId":mail.options.mailbox},
			dataType : "json",
			success : function(data) {
				if (data >0) {
					mail.mailList();
				}
				$(".mail-loading").addClass("hide");
			}
		});

	
};
//简单搜索
mail.simpleSearch=function(){
	mail.resetHidden();
	if($('#simple-search-title').val()==null || $('#simple-search-title').val()=="")
	{
		mail.resetSearch();
		mail.options.search=mail.constants.SEARCH_METHOD_NONE;
		mail.options.listURL= "/ic/mail/manageList.action";
	}
	else{
		$('#mailEasyTitle').val($('#simple-search-title').val());
		mail.options.search=mail.constants.SEARCH_METHOD_SIMPLE;
		mail.options.listURL= "/ic/mail/manageSearch.action";
	}
	mail.oTable=null;
	mail.mailList();
};
//设置高级搜索条件
mail.complexSearch=function(){
	mail.resetHidden();
	if($('#complex-search-title').val()!=""){
		$('#mailTitle').val($('#complex-search-title').val());
	}
	if($('#searchReceiveTimeBeginDiv').val()!=""){
		$('#searchReceiveTimeBegin').val($('#searchReceiveTimeBeginDiv').val());
	}
	if($('#searchReceiveTimeEndDiv').val()!=""){
		$('#searchReceiveTimeEnd').val($('#searchReceiveTimeEndDiv').val());
	}
	
	if($('#senderUserId-senderUserId').val()!=""){
		$('#senderUserIdNav').val($('#senderUserId-senderUserId').val());
	} else {
		$('#senderUserId').val('');
	}
	if($('#complex-search-sender').val()!=""){
		$('#senderMail').val($('#complex-search-sender').val());
	}
	if($('#complex-search-mailStatus option:selected').val()!=""){
		$('#receiversReadFlag').val($('#complex-search-mailStatus option:selected').val());
	}
	//
	mail.options.search=mail.constants.SEARCH_METHOD_COMPLEX;
	mail.options.listURL= "/ic/mail/manageSearch.action";
	if($('#selected-mailbox').html()=="内部邮箱"){
		
		if($("#mailFolderId")!="3"&&returnValue("senderUserId-senderUserId") !=null && returnValue("senderUserId-senderUserId") !=""){
	    $("#senderUserId").val(returnValue("senderUserId-senderUserId").split(":")[0]);
		}
	}
	$('#advanced-search').modal('hide');
	mail.oTable=null;
	//清空简单搜索
	$('#simple-search-title').val("");
	mail.mailList();
};

//清空隐藏域
mail.resetHidden = function(){
	$('#mailTitle').val("");
	$('#senderUserIdNav').val("");
	$('#senderMail').val("");
	$('#receiversReadFlag').val("");
	$('#searchReceiveTimeBegin').val("");
	$('#searchReceiveTimeEnd').val("");
	$('#mailboxAddress').val("");
	$('#mailboxId').val("");
	$('#returned').val("");
	
};

//重置高级搜索条件
mail.resetSearch=function(){
	
	mail.options.search=mail.constants.SEARCH_METHOD_NONE;
	mail.options.listURL= "/ic/mail/manageList.action";
	mail.oTable=null;
	//add start by songht 清空
	$('#adSearchForm')[0].reset();
	//add end by songht 清空
	//清空人员控件值
	selectControl.clearValue("senderUserId-senderUserId");
	mail.resetHidden();
	//$('#advanced-search').modal('hide');
	
	//mail.mailList();
};
//邮箱选择事件
mail.mailbox=function(id,address){
	mail.options.mailbox=id;
	$('#selected-mailbox').html(address);
	mail.oTable=null;
	if(id==0){
		$('#adSearchForm')[0].reset();
	}
	mail.mailList();
	if(id==1)
	{
		$('.internalText').show();
		$('.externalText').hide();
		$('#btnForwarding').hide();
		$("#advancedSearch").show();
	}
	else if(id==0){
		$("#advancedSearch").hide();
		$('#btnForwarding').hide();
	}
	else	
	{   $('.internalText').hide();
		$('.externalText').show();
		$('#btnForwarding').hide();
		$("#advancedSearch").show();
	}
	mail.receive();
	ie8StylePatch();
	iePlaceholderPatch();
	$("#simple-search-title").trigger("blur.placeholder");
};

//弹出高级搜索
mail.advancedSearch = function(){
//	if($('#simple-search-title').val()!=null && $('#simple-search-title').val()!=""){
//		$('#complex-search-title').val($('#simple-search-title').val());
//	}
	if($('#mailFolderId').val()=='3'){
		$('#sendUserTd').html("收件人");
	}
	$('#advanced-search').modal('show');
};

//邮件追回
mail.mailRecover = function (id) {
	msgBox.confirm({
		content : $.i18n.prop("JC_OA_IC_086"),
		success : function() {
			$.ajax({
				type : "GET",
				url : getRootPath()+"/ic/mail/mailRecover.action",
				data : {"id": id},
				dataType : "json",
				success : function(data) {
					if (data.success == "true") {
						msgBox.tip({
							content: $.i18n.prop("JC_OA_IC_077"),
							type:"success"
						});
						mail.mailList();
					}
					else{
						msgBox.tip({
							content: data.errorMessage,
							type:"fail"
						});
						mail.mailList();
					}
				}
			});
		}
	});
};
// 初始化
jQuery(function($) {
	
	//人员选择树
	selectControl.init("controlTreeSender","senderUserId-senderUserId", false, true);//多选人员
	// 计算分页显示条数
	mail.pageRows = TabNub > 0 ? TabNub : 10;
	// 绑定事件
	$('#btnSetRead').click(mail.setReadStatusR);
	$('#btnSetUnread').click(mail.setReadStatusUR);
	$('#btnForwarding').click(mail.forwarding);
	$('#btnDelete').click(mail.deleteClick);
	$('#btnDeleteDraft').click(mail.shiftDeleteClick);
	$('#btnShiftDelete').click(mail.shiftDeleteClick);
	$('#advancedSearch').click(mail.advancedSearch);
	$('#show-new-folder').click(mail.showFolderForm);
	$('#show_folder').click(mail.showFolderForm);
	
	$('#saveForder').click(function(){mail.saveFolder(true);});
	
	$('#simple-search').click(mail.simpleSearch);
	$('#btnSearch').click(mail.complexSearch);
	$('#btnSearchReset').click(mail.resetSearch);
	
	if($('#selected-mailbox').html()=="内部邮箱"){
		$('.internalText').show();
	}
	else{
		$('.externalText').show();
	}
	//如果是返回
	if($('#returned').val()=="true"){
		var mailboxId=$('#mailboxId').val();
		var mailTitle=$('#mailTitle').val();
		var mailEasyTitle = $('#mailEasyTitle').val();
		var senderUserId=$('#senderUserIdNav').val();
		var senderMail=$('#senderMail').val();
		var readFlag=$('#receiversReadFlag').val();
		var searchReceiveTimeBegin=$('#searchReceiveTimeBegin').val();
		var searchReceiveTimeEnd=$('#searchReceiveTimeEnd').val();
		if(mailboxId!=""){
			mail.mailbox($('#mailboxId').val(), $('#mailboxAddress').val());
		}
		if(mailboxId==""){
			mail.mailbox(0,"全部邮箱");
		}
		
		if(senderUserId!="" || senderMail!="" || readFlag!="" || searchReceiveTimeBegin!="" ||searchReceiveTimeEnd!=""){
				mail.options.search=mail.constants.SEARCH_METHOD_COMPLEX;
				$('#complex-search-title').val(mailTitle);
				$('#complex-search-sender').val(senderMail);
				$('#searchReceiveTimeBeginDiv').val(searchReceiveTimeBegin);
				$('#searchReceiveTimeEndDiv').val(searchReceiveTimeEnd);
				$('#complex-search-mailStatus').val(readFlag);
				if(senderUserId!=""){
					$.ajax({
						type:"GET",
						url:getRootPath()+"/ic/mail/getDiplayName.action",
						async:false,
						data:{"ids": senderUserId},
						dataType:"json",
						success:function(data){
							select2InitEchoToPerson("senderUserId-senderUserId", {id:senderUserId,text:data.displayName});
						}
					});
				}
				//设置人员控件值
				//selectControl.clearValue("senderUserId-senderUserId");
				$('#senderUserId').val(senderUserId);
				$('#senderMail').val(senderMail);
			
		}else{
			mail.options.search=mail.constants.SEARCH_METHOD_SIMPLE;
			$('#simple-search-title').val(mailEasyTitle);
		}
	}else{
		mail.receive();
	}
	if($('#mailFolderId').val()!="" && $('#mailFolderName').val()!=""){
		mail.fid($('#mailFolderId').val(), $('#mailFolderName').val());
	}else{
		mail.mailList();
	}
	if($('#selected-mailbox').html()=="内部邮箱")
		{
		$('#btnForwarding').hide();
		}
	
	$(".datepicker-input").datepicker();
	
});

//@ sourceURL=mailInteract.js
