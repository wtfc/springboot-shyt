/*
 * 未读邮件JS
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
	search:mail.constants.SEARCH_METHOD_NON,
	listURL: "/ic/mail/manageUnReadList.action?t="+(new Date()).getTime(),
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
				
				if (source.receivers[0].readFlag == 1) {
					status += "<a href=\"javascript:mail.setReadStatusById("
						+ source.id + "," + source.receivers[0].id
						+ ",0);	\">" + mail.options.read + "</a>";
				} else {
					status += "<a href=\"javascript:mail.setReadStatusById("
						+ source.id
						+ "," + source.receivers[0].id + ",1);\">"
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
				return source.mailboxId>1? source.senderMail:source.senderUserName;
				}
		},
		{// 是否在线
			mData :function(source){
				if(source.mailboxId==1){
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
				if (source.receivers[0].starMail == 1) {
					return "<a href=\"javascript:mail.setStar("
							+ source.id
							+ ","+source.receivers[0].folderId+","+ source.receivers[0].id + ");\" class=\"fr\"><i class=\"fa fa-star yellow-dark f-s-md\" id=\"lightstar"
							+ source.receivers[0].id  + "\"></i></a>";
				} else {
					return "<a href=\"javascript:mail.setStar("
							+ source.id
							+ ","+source.receivers[0].folderId+","+ source.receivers[0].id + ");\" class=\"fr\"><i class=\"fa fa-star f-s-md\" id=\"lightstar"
							+ source.receivers[0].id  + "\"></i></a>";
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
				
				if (source.receivers[0].starMail == 1) {
					return "<div class=\"email-theme red-dark bold\" id=\"m_title_" + source.receivers[0].id  + "\" title=\"" + mailTitle + "\"  style = \" cursor :pointer;\" onclick = mail.detail(" + source.id
							+ ","+source.index+","+ source.receivers[0].id + ","+ source.receivers[0].folderId + ","+ source.showNext+ ","+ source.showPre+ ");>" + mailTitle + "</div>";
				} else {
					return "<div class=\"email-theme blue-dark bold\" id=\"m_title_" + source.receivers[0].id  + "\" title=\"" + mailTitle + "\" style = \" cursor :pointer;\" onclick =mail.detail(" + source.id
							+ ","+source.index+","+ source.receivers[0].id + ","+ source.receivers[0].folderId + ","+ source.showNext+ ","+ source.showPre+ ");>"
							+ mailTitle + "</div>";
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
				if (source.isHaveReceiveUser=="0") {
//					return "<i class=\"fa fa-users\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"无收件人\"></i>";
					return "<div class=\"pomailDiv accordion-toggle dark\" role=\"button\" onmouseleave=\"mail.isTooltip();\"  onmouseenter=\"mail.tooltip(this,"+source.id+","+ source.receivers[0].folderId + ");\"><i class=\"fa fa-users\"  ></i></div>";
				}else{
//					return "<a class=\"dark\"href=\"#email-list\" role=\"button\" data-toggle=\"modal\">"+
//		     		   "<i class=\"fa fa-users blue-dark\" data-toggle=\"tooltip\" data-placement=\"top\"" +
//		     		   "title=\"\" data-container=\"body\" data-original-title=\"点击查看收件人\" onclick=\"mail.tooltip(this,"+source.id+","+ source.receivers[0].folderId + ")\"></i></a>";
					return "<div class=\"pomailDiv accordion-toggle dark\" role=\"button\" onmouseleave=\"mail.isTooltip();\"  onmouseenter=\"mail.tooltip(this,"+source.id+","+ source.receivers[0].folderId + ");\"><i class=\"fa fa-users blue-dark\"  ></i></div>";
				}
        	
			}
		}
		,{// 时间
			mData : function(source){
				if($('#mailFolderId').val() == "2" || $('#mailFolderId').val() == "3"){
					return source.senderTime;
				}else{
					return source.receiveTime2;
				}
			}
		} ];

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
			            "placement" : "left",
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
//	//未读邮箱时'#readFlag').val()=0
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
		
		if($('#selected-mailbox').html()=="内部邮箱"){
			aoData.push({"name":"senderUserId","value":$('#senderUserId').val()});
			mail.processUrl("senderUserId="+$('#senderUserId').val());
		}else{
			aoData.push({"name":"senderMail","value":$('#complex-search-sender').val()});
			mail.processUrl("senderMail="+$('#complex-search-sender').val());
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
		//	aaSorting:[],//设置默认排序列
			//默认不排序列
	      //  aoColumnDefs: [{"bSortable": false, "aTargets": [0, 1, 2, 3, 4, 5, 6,7,8]}]
		});
	} else {
		mail.oTable.fnDraw();
	}
};
// 设置已读状态
mail.setReadStatusR=function(){
	mail.setReadStatus(1);
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
				mail.mailList();
			}
			//设置首页状态	
			parent.msgTip.reminders();
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
	});
};
//点击图标设置已读、未读
mail.setReadStatusById = function(id,mrids,flag) {
	if(mail.isMailRecover(mrids)){
		mail.mailList();
		return;
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
				mail.mailList();
				//获得datatable分页当页第一条记录是中条数的第多少条
				start = $("#mailTable").dataTable().fnSettings()._iDisplayStart; 
				//获得datatable一共多少条
				total = $("#mailTable").dataTable().fnSettings().fnRecordsDisplay(); 
				//如果是最后一页，只有一条记录点未读图标
				if((total-start)==1){
					$("#mailTable").dataTable().fnPageChange( 'previous', true );
				}
			}
			//设置首页状态
			parent.msgTip.reminders();	
		}
	});
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
		url : getRootPath() + "/ic/mail/deleteUnRead.action",
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
				$("#lightstar" + mrids).toggleClass("yellow-dark");
				//如果设为星标添加红色
				if($("#lightstar" + mrids).hasClass("yellow-dark")){
					//如果添加星标判断是否是未读，如果是未读去掉蓝色
					$("#m_title_" + mrids).addClass("red-dark");
					$("#m_title_" + mrids).removeClass("blue-dark");
				}else{
					//如果取消星标判断是否是未读，如果是未读添加蓝色
					$("#m_title_" + mrids).removeClass("red-dark");
					$("#m_title_" + mrids).addClass("blue-dark");
				}
				$("#mailTable").hide();
				$("#mailTable").show();
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

//阅读
mail.detail = function(id,index,mrid,folderId,showNext,showPre) {
	if(mail.isMailRecover(mrid)){
		mail.mailList();
		return;
	}
	var mailboxId = mail.options.mailbox;
	var mailTitle = $('#mailTitle').val();
	var mailEasyTitle = $('#mailEasyTitle').val();
	var searchReceiveTimeBegin = $('#searchReceiveTimeBeginDiv').val();
	var searchReceiveTimeEnd = $('#searchReceiveTimeEndDiv').val();
	var sendMail = $('#senderMail').val();
	var senderUserIdNav = $('#senderUserIdNav').val();
//	var url=$('#url').val().replace("\?",escape("?")).replace(/&/g,escape("&")).replace(/=/g,escape("="));
//	loadrightmenu("/ic/mail/manageUnReadDetail.action?id="+id+"&index="+index+"&mrid="+mrid
//			+"&mailTitle="+mailTitle+"&senderMail="+sendMail+"&senderUserId="+senderUserIdNav+"&searchReceiveTimeBegin="+searchReceiveTimeBegin
//			+"&searchReceiveTimeEnd="+searchReceiveTimeEnd+"&showNext="+showNext+"&showPre="+showPre+"&folderId="+folderId+"&returnURL="+url);
	
	var url=$('#url').val().replace("\?",escape("?")).replace(/&/g,escape("&")).replace(/=/g,escape("="));
	//loadrightmenu("/ic/mail/manageDetail.action?id="+id+"&index="+index+"&mrid="+mrid+"&folderId="+folderId+"&returnURL="+url);
	jQuery.ajax({
		url : getRootPath()+"/ic/mail/manageUnReadDetail.action?id="+id+"&index="+index+"&mrid="+mrid+"&folderId="+folderId,
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

mail.processUrl=function(param){
	var url=$('#url').val();
	if(url.indexOf('?')==-1){
		url+='?';
	}
	var para=param.split('=')[0];
	var value=param.split('=')[1];
	var reg=new RegExp("("+para.replace("\[","\\[").replace("\]","\\]")+"=.*)(?=\&?)");
	if(reg.exec(url)){
		url=url.replace(reg,param);
	}
	else{
		url+=("&"+param);
	}
	
	$('#url').val(url);
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
		mail.options.listURL= "/ic/mail/manageUnReadList.action";
	}
	else{
		$('#mailEasyTitle').val($('#simple-search-title').val());
		mail.options.search=mail.constants.SEARCH_METHOD_SIMPLE;
		mail.options.listURL= "/ic/mail/manageSearchUnRead.action";
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
	mail.options.search=mail.constants.SEARCH_METHOD_COMPLEX;
	mail.options.listURL= "/ic/mail/manageSearchUnRead.action";
	if($('#selected-mailbox').html()=="内部邮箱"){
		if(returnValue("senderUserId-senderUserId") !=null && returnValue("senderUserId-senderUserId") !=""){
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
};

//弹出高级搜索
mail.advancedSearch = function(){
//	if($('#simple-search-title').val()!=null && $('#simple-search-title').val()!=""){
//		$('#complex-search-title').val($('#simple-search-title').val());
//	}
	$('#advanced-search').modal('show');
};

// 初始化
jQuery(function($) {
	// 计算分页显示条数
	mail.pageRows = TabNub > 0 ? TabNub : 10;
	// 绑定事件
	$('#btnSetRead').click(mail.setReadStatusR);
	$('#btnDelete').click(mail.deleteClick);
	
	$('#simple-search').click(mail.simpleSearch);
	$('#btnSearch').click(mail.complexSearch);
	$('#btnSearchReset').click(mail.resetSearch);
	$('#advancedSearch').click(mail.advancedSearch);
	//人员选择树
	selectControl.init("controlTreeSender","senderUserId-senderUserId", false, true);//多选人员
	if($('#selected-mailbox').html()=="内部邮箱"){
		$('.internalText').show();
	}
	else{
		$('.externalText').show();
	}
	if($('#returned').val()=="true"){
		var mailboxId=$('#mailboxId').val();
		var mailTitle=$('#mailTitle').val();
		var mailEasyTitle = $('#mailEasyTitle').val();
		var senderUserId=$('#senderUserIdNav').val();
		var senderMail=$('#senderMail').val();
		var searchReceiveTimeBegin=$('#searchReceiveTimeBegin').val();
		var searchReceiveTimeEnd=$('#searchReceiveTimeEnd').val();
		if(mailboxId!=""){
			mail.mailbox($('#mailboxId').val(), $('#mailboxAddress').val());
		}
		if(mailboxId==""){
			mail.mailbox(0,"全部邮箱");
		}
		if(senderUserId!="" || senderMail!=""|| searchReceiveTimeBegin!="" ||searchReceiveTimeEnd!=""){
			mail.options.search=mail.constants.SEARCH_METHOD_COMPLEX;
			$('#complex-search-title').val(mailTitle);
			mail.options.search=mail.constants.SEARCH_METHOD_COMPLEX;
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
	//如果参数中有AllMailbox，查询全部邮件
//	if($('#allMailbox').val()=="true"){
//		mail.mailbox(0,'全部邮箱');
//	}
	if($('#selected-mailbox').html()=="内部邮箱")
		{
		$('#btnForwarding').hide();
		}
	
	$(".datepicker-input").datepicker();
	
});

//@ sourceURL=mailInteract.js
