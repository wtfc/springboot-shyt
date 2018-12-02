var sendSimulationForm = {
		
		folderId:'',
		parentFolderId:''
};

//重复提交标识
sendSimulationForm.subState = false;
function addPrintedNum() {
	Doc['printedNum'] = (+Doc['printedNum']) + (+Doc['printNumThisTime']);
	//调用后台更新已打印份数
	var url = getRootPath()+"/doc/send/updatePrintedNum.action?time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : {id:Doc['id'],printedNum:Doc['printedNum']},
		success : function(data) {
			 
		},
		error : function() {
			 
		}
	});
}
//添加附件
sendSimulationForm.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name":"fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
	formData.push({"name":"templateType", "value": Doc['templateType']});
};

function validateForm(type){
	if(type=="Reject"){//退回按钮不验证
		return true;
	}else if(type=="Save"){//暂存不进行必填项校验
		return $("#sendSimulationForm").valid();
	}else if(type=="Submit"){//提交情况判断	
		return $("#sendSimulationForm").valid();
	}else{
		return $("#sendSimulationForm").valid();
	}
}

//提交收文单方法
function insert (status,jumpFun){
	$("#dataLoad").show();
	var result = true; //流程提交返回值
	$("#formContent").val($("#sendSimulationForm").formhtml());//获取发文档html
	/*//主送 获取人员组件选择人名的集合
	var mainSendUserNameTemp = returnValue("mainSendUserId-mainSendUserId");
	if(mainSendUserNameTemp != null){
		var mainSendUserName = "";
		var temp = mainSendUserNameTemp.split(",");
		for(var i=0; i<temp.length;i++){
			mainSendUserName += (temp[i].split(":")[1]+",");
		}
		$("#mainSendUserName").val(mainSendUserName.substring(0, mainSendUserName.length-1));
	};
	//抄送 获取人员组件选择人名的集合
	var copySendUserNameTemp = returnValue("copySendUserId-copySendUserId");
	if(copySendUserNameTemp != null){
		var copySendUserName = "";
		var temp = copySendUserNameTemp.split(",");
		for(var i=0; i<temp.length;i++){
			copySendUserName += (temp[i].split(":")[1]+",");
		}
		$("#copySendUserName").val(copySendUserName.substring(0, copySendUserName.length-1));
	};
	//抄报  获取人员组件选择人名的集合
	var remark1SendUserNameTemp = returnValue("remark1SendUserId-remark1SendUserId");
	if(remark1SendUserNameTemp != null){
		var remark1SendUserName = "";
		var temp = remark1SendUserNameTemp.split(",");
		for(var i=0; i<temp.length;i++){
			remark1SendUserName += (temp[i].split(":")[1]+",");
		}
		$("#remark2").val(remark1SendUserName.substring(0, remark1SendUserName.length-1));
	};*/
	
	//获取部门名称deptName
	var deptName = returnValue("deptId-deptId");
	if(deptName != null){
		$("#deptName").val(deptName.split(":")[1]);
	}
	if(sendSimulationForm.subState)return;
	sendSimulationForm.subState = true;
	var url = getRootPath()+"/doc/send/save.action?time=" + new Date();
	var formData = $("#sendSimulationForm").serializeArray();
	sendSimulationForm.addFormParameters(formData);
	setDocValue(formData);
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : formData,
		success : function(data) {
			sendSimulationForm.subState = false;
			//getToken();
			if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback:jumpFun
				});
			} else {
				if(data.labelErrorMessage){
					showErrors("sendSimulationForm", data.labelErrorMessage);
				} else{
					msgBox.info({
						content: data.errorMessage
					});
				}
			}
			$("#dataLoad").hide();
		},
		error : function() {
			sendSimulationForm.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_002")
			});
			$("#dataLoad").hide();
		}
	});
	return result;
};

function validateFormFail(flag){
	sendSimulationForm.subState = false;
	var msg = $.i18n.prop("JC_SYS_067");
	if(flag) {
		msg = flag;
	}  
	msgBox.info({
		content:msg,
		success:function(){
			fnCall();
		}
	});
	$("#dataLoad").hide();
}

//修改发文单方法
function update(type,jumpFun) {
	$("#dataLoad").show();
	$("#formContent").val($("#sendSimulationForm").formhtml());//获取发文档html
	/*//主送 获取人员组件选择人名的集合
	var mainSendUserNameTemp = returnValue("mainSendUserId-mainSendUserId");
	if(mainSendUserNameTemp != null){
		var mainSendUserName = "";
		var temp = mainSendUserNameTemp.split(",");
		for(var i=0; i<temp.length;i++){
			mainSendUserName += (temp[i].split(":")[1]+",");
		}
		$("#mainSendUserName").val(mainSendUserName.substring(0, mainSendUserName.length-1));
	};
	
	//抄送 获取人员组件选择人名的集合
	var copySendUserNameTemp = returnValue("copySendUserId-copySendUserId");
	if(copySendUserNameTemp != null){
		var copySendUserName = "";
		var temp = copySendUserNameTemp.split(",");
		for(var i=0; i<temp.length;i++){
			copySendUserName += (temp[i].split(":")[1]+",");
		}
		$("#copySendUserName").val(copySendUserName.substring(0, copySendUserName.length-1));
	};*/

	//获取部门名称deptName
	var deptName = returnValue("deptId-deptId");
	if(deptName != null){
		$("#deptName").val(deptName.split(":")[1]);
	}
	
	var result = true;
	var url = getRootPath() + "/doc/send/update.action";
	if (sendSimulationForm.subState)
		return;
	sendSimulationForm.subState = true;
	var formData = $("#sendSimulationForm").serializeArray();
	sendSimulationForm.addFormParameters(formData);
	setDocValue(formData);
	jQuery.ajax({
		url : url,
		type : 'Post',// 流程type类型（提交）
		async:false,
		data : formData,
		success : function(data) {
			sendSimulationForm.subState = false;
			//getToken();
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback : jumpFunSendWaitTransact
				});
			} else {
				msgBox.info({
					content: "提交失败"
				});
				/*if(data.labelErrorMessage){
					showErrors("sendSimulationForm", data.labelErrorMessage);
				} else{
					msgBox.info({
						content: data.errorMessage
					});
				}*/
			}
			$("#dataLoad").hide();
		},
		error : function() {
			sendSimulationForm.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_002")
			});
			$("#dataLoad").hide();
		}
	});
	return result;
}

//工作流修改回调方法，跳转待办列表页
function jumpFunSendWaitTransact(){
	loadrightmenu("/workFlow/form/jumpToList.action?url=/doc/send/sendWaitTransact&time=" + new Date(),'unknown');
}

//流程相关 回显数据
sendSimulationForm.initPage=function () {
	$("#dataLoad").show();
	/*$.ajax({
		type : "GET",
		url : getRootPath() + "/doc/send/get.action",
		data : {
			"piId" : piId,
			time:new Date()
		},
		dataType : "json",
		async : false,
		success : function(data) {*/
	var sendJson=$('#sendJson').val();
	var data=null;
	if(sendJson.length>0)
		{
		data=eval("("+sendJson+")");
		}
	else
		data="";
			if (data) {
				Doc['printNum'] = data.printNum ? data.printNum : 0;
				Doc['printedNum'] = data.printedNum ? data.printedNum : 0;
				Doc['controlPrint'] = data.controlPrint;
				sendSimulationForm.initDocValue(data);
				// 清除验证信息
				hideErrorMessage();
				$("#sendSimulationForm").fill(data);
				Doc['templateType'] = data.templateType ? data.templateType : "0";
				Doc['id'] = data.id;
				//附件使用 start
				$("#businessId").val(data.id);
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				echoAttachList(false);
				clearTable();
				//附件使用 end
				//编号
				$("#noValueOld").val(data.noValue);//文号手动输入修改时校验使用
				$("#docNoIdOld").val(data.docNoId);//文号选择文号修改时校验使用
				//拟稿人
				if(data.substitusUserId !=null && data.substitusUserId!=""){
					$("#substitusUserTree").empty();
					selectControl.init("substitusUserTree","substitusUserId-substitusUserId", false, true,0,{id:data.substitusUserId,text:data.substitusUser});
				}
				/*if(data.proofUserId !=null && data.proofUserId!=""){
					$("#proofUserTree").empty();
					selectControl.init("proofUserTree","proofUserId-proofUserId", false, true,0,{id:data.proofUserId,text:data.proofUser});
				}*/
				//核稿人
				if(data.reviewUserId !=null && data.reviewUserId!=""){
					$("#reviewUserTree").empty();
					selectControl.init("reviewUserTree","reviewUserId-reviewUserId", false, true,0,{id:data.reviewUserId,text:data.reviewUser});
				}
				//拟稿单位
				if(data.deptId !=null && data.deptId!=""){
					$("#deptIdTree").empty();
					selectControl.init("deptIdTree","deptId-deptId", false, false,0,{id:data.deptId,text:data.deptName});
				}
				/*if(data.mainSendUserNameArr !=null && data.mainSendUserNameArr != ""){
					$("#mainSendUserIdTree").empty();
					selectControl.init("mainSendUserIdTree","mainSendUserId-mainSendUserId", true, true,null,data.mainSendUserNameArr);
				}
				if(data.copySendUserNameArr !=null && data.copySendUserNameArr != ""){
					$("#copySendUserIdTree").empty();
					selectControl.init("copySendUserIdTree","copySendUserId-copySendUserId", true, true,null,data.copySendUserNameArr);
				}*/
				$("#dataLoad").hide();
			    }else{
				$("#dataLoad").hide();
			    }
		/*},
		error : function() {
			$("#dataLoad").hide();
	}
	});*/
	
};
/*function loadForm(piId) {
	$("#dataLoad").show();
	//主送
	selectControl.init("mainSendUserIdTree","mainSendUserId-mainSendUserId", true, true);
	//抄送
	selectControl.init("copySendUserIdTree","copySendUserId-copySendUserId", true, true);
	//拟稿人
	selectControl.init("substitusUserTree","substitusUserId-substitusUserId", false, true);
	//校对人
	selectControl.init("proofUserTree","proofUserId-proofUserId", false, true);
	//复核人
	selectControl.init("reviewUserTree","reviewUserId-reviewUserId", false, true);
	//主办部门
	selectControl.init("deptIdTree","deptId-deptId", false, false, 0);
	$.ajax({
		type : "GET",
		url : getRootPath() + "/doc/send/get.action",
		data : {
			"piId" : piId,
			time:new Date()
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data) {
				sendSimulationForm.initDocValue(data);
				// 清除验证信息
				hideErrorMessage();
				$("#sendSimulationForm").fill(data);
				Doc['templateType'] = data.templateType ? data.templateType : "0";
				Doc['id'] = data.id;
				//附件使用 start
				$("#businessId").val(data.id);
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				echoAttachList(false);
				clearTable();
				//附件使用 end
				$("#noValueOld").val(data.noValue);//文号手动输入修改时校验使用
				$("#docNoIdOld").val(data.docNoId);//文号选择文号修改时校验使用
				if(data.substitusUserId !=null && data.substitusUserId!=""){
					$("#substitusUserTree").empty();
					selectControl.init("substitusUserTree","substitusUserId-substitusUserId", false, true,0,{id:data.substitusUserId,text:data.substitusUser});
				}
				if(data.proofUserId !=null && data.proofUserId!=""){
					$("#proofUserTree").empty();
					selectControl.init("proofUserTree","proofUserId-proofUserId", false, true,0,{id:data.proofUserId,text:data.proofUser});
				}
				if(data.reviewUserId !=null && data.reviewUserId!=""){
					$("#reviewUserTree").empty();
					selectControl.init("reviewUserTree","reviewUserId-reviewUserId", false, true,0,{id:data.reviewUserId,text:data.reviewUser});
				}
				if(data.deptId !=null && data.deptId!=""){
					$("#deptIdTree").empty();
					selectControl.init("deptIdTree","deptId-deptId", false, false,0,{id:data.deptId,text:data.deptName});
				}
				if(data.mainSendUserNameArr !=null && data.mainSendUserNameArr != ""){
					$("#mainSendUserIdTree").empty();
					selectControl.init("mainSendUserIdTree","mainSendUserId-mainSendUserId", true, true,null,data.mainSendUserNameArr);
				}
				if(data.copySendUserNameArr !=null && data.copySendUserNameArr != ""){
					$("#copySendUserIdTree").empty();
					selectControl.init("copySendUserIdTree","copySendUserId-copySendUserId", true, true,null,data.copySendUserNameArr);
				}
				$("#dataLoad").hide();
			}else{
				$("#dataLoad").hide();
			}
		},
		error : function() {
			$("#dataLoad").hide();
		}
	});
	
};*/
sendSimulationForm.initDocValue = function(data) {
	Doc['composingFile'] = data.composingFile;
	Doc['viewFile'] = data.viewFile;
	Doc['reviewFile'] = data.reviewFile;
	Doc['sealFile'] = data.sealFile;
	Doc['contentFile'] = data.contentFile;
}
//字典项初始化
sendSimulationForm.initDic = function(){
	dic.fillDics("sendSimulationForm #priority", "priority_type",false,0);
	dic.fillDics("sendSimulationForm #secret", "secret_type",false,0);
	dic.fillDics("sendSimulationForm #docFlowType", "docFlowType",false,3);
	dic.multistepDropDown($('#docFlowType').val(),'docType');
};



var signType=$("#signType").val();
function setDocValue(formData) {
	
	if(signType == 0 || signType == "0") {
		//点聚
		if(Doc['functionType'] == 0) {
			formData.push({"name": "contentFile", "value": Doc['contentFile']});
			formData.push({"name": "reviewFile", "value": Doc['reviewFile']});
			formData.push({"name": "sealFile", "value": Doc['reviewFile']});
			formData.push({"name": "viewFile", "value": Doc['viewFile']});
			
		} else if(Doc['functionType'] == 1) {
			//编辑正文操作
			formData.push({"name": "contentFile", "value": Doc['contentFile']});
			formData.push({"name": "reviewFile", "value": Doc['reviewFile']});
			formData.push({"name": "sealFile", "value": Doc['reviewFile']});
			formData.push({"name": "viewFile", "value": Doc['viewFile']});
		} else if(Doc['functionType'] == 2) {
			formData.push({"name": "reviewFile", "value": Doc['reviewFile']});
		} else if(Doc['functionType'] == 3) {
			//盖章
			formData.push({"name": "sealFile", "value": Doc['sealFile']});
			formData.push({"name": "viewFile", "value": Doc['viewFile']});
		} else if(Doc['functionType'] == 4) {
			//排版
			//formData.push({"name": "templateType", "value": Doc['templateType']});
			formData.push({"name": "composingFile", "value": Doc['composingFile']});
			formData.push({"name": "viewFile", "value": Doc['viewFile']});
			formData.push({"name": "sealFile", "value": Doc['reviewFile']});
			formData.push({"name": "reviewFile", "value": Doc['reviewFile']});
		}
		formData.push({"name": "templateType", "value": Doc['templateType']});
	} else {
		//金格
		formData.push({"name": "contentFile", "value": Doc['contentFile']});
		formData.push({"name": "reviewFile", "value": Doc['reviewFile']});
		formData.push({"name": "composingFile", "value": Doc['composingFile']});
		formData.push({"name": "sealFile", "value": Doc['sealFile']});
		formData.push({"name": "viewFile", "value": Doc['viewFile']});
	}
}

var Doc = {
		templateType:0,
		fileName:'',
		fileSize:'',
		filePath:'',
		fileContent:'',
		reviewFile:'',
		viewFile:'',
		composingFile:'',
		sealFile:'',
		contentFile:'',
		id:'',
		docText:'',
		printNum : 0,
		printedNum : 0,
		printNumThisTime:0,//这次打印的份数
		controlPrint : 0,
		functionType:0/*0只读  1编辑 2审阅3盖章4排版*/
};


/*
 * 弹出编辑正文页面
 * */
	function editDoc(){
		if(signType == 0 || signType == "0"){
		Doc['functionType'] = 1;
		window.open(getRootPath()+"/doc/send/editDoc.action");//点聚
		}else{
		window.open(getRootPath()+"/doc/send/editDocJg.action");//金格
		}
	}
	
	/*
	 * 弹出审阅正文页面
	 * */
	function reviewDoc() {
		if(signType == 0 || signType == "0"){
		Doc['functionType'] = 2;
		window.open(getRootPath()+"/doc/send/reviewDoc.action");//点聚
		}else{
		window.open(getRootPath()+"/doc/send/reviewDocJg.action");//金格
		}
	}
	
	/*
	 * 弹出查看正文页面
	 * */
	function viewDoc(a) {
		if(signType == 0 || signType == "0"){
		Doc['functionType'] =0;
		window.open(getRootPath()+"/doc/send/viewDoc.action");
		}else{
			if(Doc['sealFile']!=null&&Doc['sealFile']!=""){
				window.open(getRootPath()+"/doc/send/viewDocJgPDF.action");
			}else{
				window.open(getRootPath()+"/doc/send/viewDocJgWord.action");
			}
		}
	}
	/*
	 * 弹出盖章页面
	 * */
	function viewSeal(a) {
		if(signType == 0 || signType == "0"){
		Doc['functionType'] =3;
		window.open(getRootPath()+"/doc/send/sealDoc.action");
		}else{
		window.open(getRootPath()+"/doc/send/sealDocJg.action");
		}
	}
	/*
	 * 弹出排版页面
	 * */
	function typeSetting() {
		if(signType == 0 || signType == "0"){
		Doc['functionType'] =4;
		window.open(getRootPath()+"/doc/send/typesettingDoc.action");
		}else{
			var id=$('#id').val();
			window.open(getRootPath()+"/doc/send/typesettingDocJg.action?id="+id+"");
		}
	}
	function saveEdit() {
		$.ajax({
			type : "post",
			url : getRootPath() + "/doc/send/updateFile.action",
			data : {id:Doc['id'],contentFile:Doc['contentFile'],sealFile:Doc['reviewFile'],viewFile:Doc['viewFile'],reviewFile:Doc['reviewFile']},
			dataType : "json",
			cache:false,
			success : function(data) {
				 alert('ok');
			}
		});
	}
	function savecompose() {
		$.ajax({
			type : "post",
			url : getRootPath() + "/doc/send/updateFile.action",
			data : {id:Doc['id'],templateType:Doc['templateType'],composingFile:Doc['composingFile'],viewFile:Doc['viewFile'],sealFile:Doc['reviewFile'],reviewFile:Doc['reviewFile']},
			dataType : "json",
			cache:false,
			success : function(data) {
				 alert('ok');
			}
		});
	}
	function saveReview() {
		$.ajax({
			type : "post",
			url : getRootPath() + "/doc/send/updateFile.action",
			data : {id:Doc['id'],reviewFile:Doc['reviewFile']},
			dataType : "json",
			cache:false,
			success : function(data) {
				 alert('ok');
			}
		});
	}
	function saveseal() {
		$.ajax({
			type : "post",
			url : getRootPath() + "/doc/send/updateFile.action",
			data : {id:Doc['id'],sealFile:Doc['sealFile'],viewFile:Doc['viewFile']},
			dataType : "json",
			cache:false,
			success : function(data) {
				 alert('ok');
			}
		});
	}

//文号下拉列表初始化
sendSimulationForm.InitSendNo = function(){
	var selectObj = $("#sendNoListForm #docNoId1");
	selectObj.html("<option value=''>-请选择-</option>");
	var url = getRootPath()+"/doc/no/queryAllByControlSide.action";
	var params = {
		time: new Date(),
		noType:0
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        success: function(data) {
        	if(data){
        		for(var i=0;i<data.length;i++){
        			var optionStr = "<option value='"+data[i].id+"#"+data[i].ruleName+"'>"+data[i].noName+"</option>";
        			selectObj.append(optionStr);
        		}
        	};
        }
    });		
};
var tree;
var setting={
		check:{
			enable:false,
			nocheckInherit:false,
			chkStyle:"radio",
			radioType:"all"
		},
		view:{
			selectedMulti:false,
			showLine:true
		},
		data:{
			simpleData:{enable:true}
		},
		callback: {
			//beforeCheck:beforeCheck,
			onClick: onClick
			//onCheck: onCheck
		}
};
function onClick(e,treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	 //children
	var node = zTree.getNodeByTId(treeId);
	zTree.expandNode(treeNode);
	var nodes = zTree.getSelectedNodes();
	//alert(nodes[0].id);
	sendSimulationForm['folderId'] = nodes[0].id;
	sendSimulationForm['parentFolderId'] = nodes[0].pId;
}
sendSimulationForm.closeFiling = function() {
	sendSimulationForm['folderId'] = '';
	$("#myModal-edit").modal('hide');
}
sendSimulationForm.filing = function() {
	if(!sendSimulationForm['folderId'] || !sendSimulationForm['parentFolderId'] || '0' == sendSimulationForm['parentFolderId']) {
		msgBox.info({
			content : $.i18n.prop("JC_OA_ARCHIVE_018")
		});
		return;
	}
	//调用归档
	saveAIPFileToService_();
}

//保存归档数据
sendSimulationForm.saveToFiling = function(data){
//	alert(data['fileName']);
	//追加workid
	$("form").find("[workFlowType='true']").remove();
	$("[workFlowType='true']").each(function(index,item){
		item = $(item);
		var eleHtml = "<input type='hidden' workFlowType='true' id='"+item.attr("id")+"' name='"+item.attr("name")+"' value='"+item.val()+"'>"
		//$("form").append(eleHtml);由于点击归档按钮后，附言无法增加，所以去掉，暂不知会有什么影响。20150421 xuweiping
	});
	var p = JSON.stringify({folderId:sendSimulationForm['folderId'],piId:$('#id').val(),formContent:$('#sendSimulationForm').formhtml()},replaceJsonNull);
	if(data) {
		p = JSON.stringify({folderId:sendSimulationForm['folderId'],piId:$('#id').val(),formContent:$('#sendSimulationForm').formhtml(),list:[{fileSize:data['fileSize'],suffix:"aip",name:"公文正文.aip",url:data['relativePath']}]},replaceJsonNull);
	}
	jQuery.ajax({
		url: getRootPath() + "/doc/send/saveFilingInfo.action",
		type: 'post',
		contentType:"application/json",  
		data: p,
		//async:false,
		cache:false,
		success: function(data, textStatus, xhr) {
			if(data.success == "true") {
				$("#myModal-edit").modal('hide');
				sendSimulationForm['folderId'] = '';
				msgBox.tip({
					type:"success",
					content : $.i18n.prop("JC_OA_ARCHIVE_020")
				});
			} else {
				msgBox.info({
					content : data.errorMessage
				});
			}
			//$('#home').load('/login/entryHomePage.action');
			//var ddata = $.parseJSON(data); 
		},error:function(e){
			alert(e);
		}
	});
	/*alert(data['fileName']);
	 var files = [];
	 files[0] = {fileName:'id1',code:'44'};
	 files[1] = {fileName:'id71',code:'474'};
	 var p = JSON.stringify(files);*/
};
sendSimulationForm.showFilingDiv = function(){
	if(!$('#id').val()){
		return;
	}
	$.ajax({
		type : "GET",
		cache:false,
		url : getRootPath() + "/doc/send/loadFilingInfo.action",
		data : {
			"id" : $('#id').val(),
			time:new Date()
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data) {
				if (data.success == "true") {
					if(data.folders != null && data.folders.length > 0){
						//$("#folderSelector").empty();
						var zNodes=[];
						$.each(data.folders,function(i,f){
							zNodes[i]={
									id:f.id,
									pId:f.parentFolderId,
									name:f.folderName
							};
						});
						tree=$.fn.zTree.init($("#folderSelector"),setting,zNodes);
						tree.expandAll(true);
					}
					$('#myModal-edit').modal('show');
					Aip.openFile({fileContent:data.send['viewFile'],state:0,isShowAlert:false,isPrint:true,height:400,isNoButton:true,isSaveToLocal:false,isSaveToService:false,type:"aip",callback:function(a){
						//window.close();
						sendSimulationForm.saveToFiling(a);
					}});
				} else {
					msgBox.info({
						content : data.errorMessage
					});
				}
				
			}else{
				
				//$("#dataLoad").hide();
			}
		},
		error : function() {
			//$("#dataLoad").hide();
		}
	});
};


//显示文号选择页面
sendSimulationForm.showSendNoDiv = function(){
	$('#send-no').modal('show');
	//当第一次调用文号时后台加载数据
	if(document.getElementById("docNoId1").options.length==0){
		//文号下拉列表初始化
		sendSimulationForm.InitSendNo();
	};
};
//根据下拉选中值获取生成的文号
sendSimulationForm.getSendNo = function(){
	var docNoId = $("#docNoId1").val();
	if(docNoId!=''){
		if(docNoId.split("#")[0]==$("#docNoIdOld").val()){//如果修改时选择原来的编号不进行后台获取
			$("#docNoIdView").html($("#noValueOld").val());
		}else{//加载新的文号
			$("#dataLoad").show();
			var r = docNoId.split("#")[1];
			var url = getRootPath()+"/doc/no/getSendNo.action";
			var params = {
					time:new Date(),
					ruleName:r,
					noType:0
			};
			$.ajax({
				url: url,
				type: 'post',
				data: params,
				success: function(data) {
					$("#dataLoad").hide();
					if(data){
						var sn = data.sendNumber;
						var snResult = sn.substring(0,1);
						if(snResult=='1'){
							$("#docNoIdView").html(sn.substring(1));
						}else{
							$("#docNoIdView").html('');
							var mess;
							if(snResult=='-'){
								mess = '流水号大于上限';
							}else if(snResult=='6'){
								mess = '参数错误';
							}else{
								mess = $.i18n.prop("JC_SYS_060");
							}
							msgBox.tip({
								content: mess
							});          			
						}
						
					}else{
						msgBox.tip({
							content: $.i18n.prop("JC_SYS_060")
						});        		
					}
				},
				error : function() {
					$("#dataLoad").hide();
					msgBox.tip({
						content: $.i18n.prop("JC_SYS_060")
					});			
				}
			});	
		}
	}else{
		$("#docNoIdView").html("");
	}
};
//确定选择的文号
sendSimulationForm.setSendNo = function(){
	var snType = $('input[name="snType"]:checked').val();
	var docNoId = $("#docNoId1").val();
	if(snType==1){//选择文号
		if(docNoId!=''){
			if(docNoId.split("#")[0]==$("#docNoIdOld").val()){//如果修改是选择原来的编号规则不进行获取
				$("#docNoId").val($("#docNoIdOld").val());			
				$("#noValue").val($("#noValueOld").val());
			}else{//如果是新的编号规则进行重新获取
				$("#docNoId").val(docNoId.split("#")[0]);
				$("#noValue").val($("#docNoIdView").html());				
			}
		}else{
			$("#docNoId").val('');
			$("#noValue").val('');		
		}
	}else{//手动输入
		$("#docNoId").val('');
		$("#noValue").val($("#docNoId2").val());
	}
};
sendSimulationForm.radioChange = function(){
	var snType = $('input[name="snType"]:checked').val();
	if(snType==1){//选择文号
		$("#docNoId2").prop("disabled",true);
		$("#docNoId2").val('');
		$("#docNoId1").prop("disabled",false);
	}else{//手动输入
		$("#docNoId2").prop("disabled",false);
		$("#docNoId1").val('');
		$("#docNoIdView").html('');
		$("#docNoId1").prop("disabled",true);
	}
};
//分发公文
sendSimulationForm.showDistributionDiv = function (){
	var id = $("#id").val();
	if(id==0){
		msgBox.tip({
			content: "没有数据不能分发公文"
		});		
	}else{
		$(document).showInsideOutAddDiv_(null,id,0);
	}
};

//点击关闭附件弹出层是清空内容
sendSimulationForm.fileupload = function (){
	$("#businessId").val("0");
	showAttachList(false);
};

sendSimulationForm.sendDoc = function (){
	$("#sendFlows").hide();
	
};

//点击关闭时删除一上传文件
sendSimulationForm.deleteAttach = function() {
	if($("#fileids").val().length > 0 && $("#fileid_old").val() != $("#fileids").val()){
		var ids = $("#fileids").val();
		$.ajax({
			type : "POST",
			url : getRootPath()+"/content/attach/delete.action",
			data : {"ids": ids},
			dataType : "json",
			success : function(data) {
				carInfo.carInfoList();
			}
		});
	}
};

//弹出打印页面
sendSimulationForm.showDocSendPrint = function() {
	//追加workid
	$("form").find("[workFlowType='true']").remove();
	$("[workFlowType='true']").each(function(index,item){
		item = $(item);
		var eleHtml = "<input type='hidden' workFlowType='true' id='"+item.attr("id")+"' name='"+item.attr("name")+"' value='"+item.val()+"'>"
		//$("form").append(eleHtml);由于点击打印按钮后，附言无法增加，所以去掉，暂不知会有什么影响。20150422 xuweiping
	});
	$("#formContent").val($("#sendSimulationForm").formhtml());//获取发文档html
	window.open(getRootPath()+'/doc/send/manageDocPrint.action?time='+new Date());
};

//流程相关 初始化方法
function initFun() {
	//转发文使用
	if(typeof(getUrlParameter()) != "undefined" && getUrlParameter()!='') {
			var receiveToSend = eval('(' + getUrlParameter() + ')');
			if(receiveToSend.title) {
				$('#title').val(receiveToSend.title);
			}
			if(receiveToSend['id']) {
				//转发文通过收文id 复制收文的附件
				//附件使用 start
				$("#businessId").val(receiveToSend['id']);
				$("#businessTable").val('TOA_DOC_RECEIVE');
				$("#iscopy").val("1");//转发文使用复制附件标识（1：复制附件2：复制附件和正文转附件）
				if (typeof(receiveToSend['docType']) != "undefined"){//公文交换转发文获取公文类型
					if(receiveToSend['docType']==0){
						$("#businessTable").val('TOA_DOC_SEND');
						$("#iscopy").val("2");//转发文使用复制附件标识（1：复制附件2：复制附件和正文转附件）
					}
				}
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				echoAttachList(false);
				$("#iscopy").val("0");//不进行复制
				$("#businessId").val("0");//附件弹出层列表不与数据库交互
				//附件使用 end
			}
			//receiveToSend={};//清空对象
			setUrlParameter('');
	}
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//文号选择页面
	$("#showSendNoDiv").click(sendSimulationForm.showSendNoDiv);
	//根据选择的文号名称获取当前文号
	$("#docNoId1").change(sendSimulationForm.getSendNo);
	//确认选择的文号
	$("#chooseNo").click(sendSimulationForm.setSendNo);
	//附件使用
	$("#closebtn").click(sendSimulationForm.fileupload);
	$("#saveOrModifyFiling").click(sendSimulationForm.filing);
	$("#filingClose").click(sendSimulationForm.closeFiling);
	$("#closeModalBtn").click(sendSimulationForm.fileupload);
	$("#queryattach").click(sendSimulationForm.fileupload);
	//主送
	selectControl.init("mainSendUserIdTree","mainSendUserId-mainSendUserId", true, true);
	//抄送
	selectControl.init("copySendUserIdTree","copySendUserId-copySendUserId", true, true);
	//抄报机关 remark1 id，remark2 名称
	selectControl.init("remark1SendUserIdTree","remark1SendUserId-remark1SendUserId", true, true);
	//拟稿人
	selectControl.init("substitusUserTree","substitusUserId-substitusUserId", false, true);
	//校对人
	selectControl.init("proofUserTree","proofUserId-proofUserId", false, true);
	//复核人
	selectControl.init("reviewUserTree","reviewUserId-reviewUserId", false, true);
	//主办部门
	selectControl.init("deptIdTree","deptId-deptId", false, false, 0);
	//getToken();
	sendSimulationForm.initPage();
	formDetail.initForm();
};
$(document).ready(function(){
	initFun();
});