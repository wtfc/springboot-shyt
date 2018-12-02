var receiveSimulationForm = {
		folderId:'',
		parentFolderId:''
};
var receiveToSend = {};
//重复提交标识
receiveSimulationForm.subState = false;

//添加附件
receiveSimulationForm.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
	formData.push({"name": "fileids", "value": fileids});
};

//来文单位分页对象
receiveSimulationForm.fromDeptTable = null;
//显示来文单位信息
receiveSimulationForm.showFromDeptDiv = function(){
	$('#fromDept-list').modal('show');
	
	receiveSimulationForm.fromDeptFnServerParams = function(aoData){
		getTableParameters(receiveSimulationForm.fromDeptTable, aoData);
	};
	
	//显示来文单位列信息
	receiveSimulationForm.fromDeptColumns = [
        {mData: function(source) {
        	var fromDeptStr="";
        	if($('#sendDeptId').val()==source.id && $('#sendDeptType').val() == source.description){//因为内部机构和外部机构的id可能一样，所以必须加上机构类型来判断
        		fromDeptStr="<input type='radio' name='fromDepts' value='"+source.id+"#"+source.name+"#"+source.description+"' checked>";
        	}else{
        		fromDeptStr= "<input type='radio' name='fromDepts' value='"+source.id+"#"+source.name+"#"+source.description+"'>";
        	}
        	return fromDeptStr;
       	}},	                                           
		{ "mData": "name" },
		{ "mData": "offcial"},
		{ "mData": "description"}
	 ];
	if (receiveSimulationForm.fromDeptTable == null) {
		receiveSimulationForm.fromDeptTable = $('#fromDeptListTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/fromDept/manageDeptAndOrgList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": receiveSimulationForm.fromDeptColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				receiveSimulationForm.fromDeptFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3]}]
		} );
	} else {
		receiveSimulationForm.fromDeptTable.fnDraw();
	}
};

//选择来文单位方法
receiveSimulationForm.chooseFromDept = function(){
	var fromDeptName = $('input[name="fromDepts"]:checked').val();
	if (fromDeptName == null) {
		msgBox.info({
			content: $.i18n.prop("JC_SYS_029","来文单位")
		});
		return;
	}else{
	$("#sendDeptId").val(fromDeptName.split("#")[0]);
	$("#sendDept").val(fromDeptName.split("#")[1]);
	$("#sendDeptType").val(fromDeptName.split("#")[2]);
	//$("#sendDept").val(fromDeptName);
	}
	$('#fromDept-list').modal('hide');
	
};

//跳转发文拟文列表页面
receiveSimulationForm.showReceiveSimulation = function (){
	loadrightmenu("/doc/receive/receiveSimulationForm.action?time=" + new Date(),'unknown');
};

receiveSimulationForm.initPage=function(){
	var receiveJson=$('#receiveJson').val();
	var data=null;
	if(receiveJson.length>0)
		{
		data=eval("("+receiveJson+")");
		}
	else{
		data="";
	}
	//$("#dataLoad").show();
			if (data) {
				// 清除验证信息
				hideErrorMessage();
				$("#receiveSimulationForm").fill(data);
				//转发文使用 start
				receiveToSend['id']=data.id;
				receiveToSend['title']=data.title;
				//转发文使用 end
			    $('#receiveDate').val(data.receiveDateStr);
				$("#seqValueOld").val(data.seqValue);//编号手动输入修改时校验使用
				$("#seqIdOld").val(data.seqId);//编号选择编号修改时校验使用
			    if(data.substitusUserId !=null &&data.substitusUserId!=""){
					$("#substitusUserTree").empty();
					selectControl.init("substitusUserTree","substitusUserId-substitusUserId", false, true,0,{id:data.substitusUserId,text:data.substitusUser});
				}
			    if(data.contractorsId !=null &&data.contractorsId!=""){
			    	$("#contractorsTree").empty();
			    	selectControl.init("contractorsTree","contractorsId-contractorsId", false, true,0,{id:data.contractorsId,text:data.contractors});
			    }
			    //附件使用 start
				$("#businessId").val(data.id);
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				echoAttachList(false);
				clearTable();
				//附件使用 end
		}
			
};
//function initFun(){
//	//alert('initfun');
//}
/*function loadForm(piId) {
	//$("#dataLoad").show();
	//承办人选择树
	selectControl.init("contractorsTree","contractorsId-contractorsId", false, true);
	//核稿人选择树
	selectControl.init("substitusUserTree","substitusUserId-substitusUserId", false, true);
	$.ajax({
		type : "GET",
		url : getRootPath() + "/doc/receive/get.action",
		data : {
			"piId" : piId,
			time:new Date()
		},
		async:false,
		dataType : "json",
		success : function(data) {
			if (data) {
				// 清除验证信息
				hideErrorMessage();
				$("#receiveSimulationForm").fill(data);
				//转发文使用 start
				receiveToSend['id']=data.id;
				receiveToSend['title']=data.title;
				//转发文使用 end
				//附件使用 start
				$("#businessId").val(data.id);
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				echoAttachList(false);
				clearTable();
				//附件使用 end
			    $('#receiveDate').val(data.receiveDateStr);
				$("#seqValueOld").val(data.seqValue);//编号手动输入修改时校验使用
				$("#seqIdOld").val(data.seqId);//编号选择编号修改时校验使用
			    if(data.substitusUserId !=null &&data.substitusUserId!=""){
					$("#substitusUserTree").empty();
					selectControl.init("substitusUserTree","substitusUserId-substitusUserId", false, true,0,{id:data.substitusUserId,text:data.substitusUser});
				}
			    if(data.contractorsId !=null &&data.contractorsId!=""){
			    	$("#contractorsTree").empty();
			    	selectControl.init("contractorsTree","contractorsId-contractorsId", false, true,0,{id:data.contractorsId,text:data.contractors});
			    }
			}
	},
		error:function(msg){
			
	}
	});
	//$("#dataLoad").hide();
}*/

function validateForm(type){
	if(type=="Reject"){//退回按钮不验证
		return true;
	}else if(type=="Save"){//暂存不进行必填项校验
		return $("#receiveSimulationForm").valid();
	}else{
		var data=$("#receiveSimulationForm #title").val();
		data=$.trim(data);
		$("#receiveSimulationForm #title").val(data);
		if(data==""){
			$("#receiveSimulationForm").valid();
		}
		var da=$("#receiveSimulationForm #sendDept").val();
		da=$.trim(da);
		$("#receiveSimulationForm #sendDept").val(da);
		if(da==""){
			$("#receiveSimulationForm").valid();
		}
		return $("#receiveSimulationForm").valid();
	}
}

function addContractors(formData) {
	/*
	 * var mainSendUserNameTemp = returnValue("mainSendUserId-mainSendUserId");
	if(mainSendUserNameTemp != null){
		var mainSendUserName = "";
		var temp = mainSendUserNameTemp.split(",");
		for(var i=0; i<temp.length;i++){
			mainSendUserName += (temp[i].split(":")[1]+",");
		}
		$("#mainSendUserName").val(mainSendUserName.substring(0, mainSendUserName.length-1));
		$("#mainSendUserNames").val(mainSendUserName.substring(0, mainSendUserName.length-1));
	};
	 * */
	var contractors = returnValue("contractorsId-contractorsId");
	if(contractors) {
		var index = contractors.indexOf(":");
		formData.push({"name": "contractors", "value": contractors.substring(index + 1)});
	}
}
function insert(type, f) {
	//alert(type);
	//alert('***'+f);
	if (receiveSimulationForm.subState) {
		return false;
	}
	/*if(!$("#receiveSimulationForm").valid()){
		return false;
	}*/
	$("#formContent").val($("#receiveSimulationForm").formhtml());//获取收文表单html
	receiveSimulationForm.subState = true;
	var url = getRootPath()+"/doc/receive/save.action";
	var formData = $("#receiveSimulationForm").serializeArray();
	receiveSimulationForm.addFormParameters(formData);
	addContractors(formData);
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : formData,
		cache:false,
		success : function(data) {
			receiveSimulationForm.subState = false;
		//	getToken();
			if(data.success == "true"){
			//	alert(11111);
				//receiveSimulationForm.showReceiveSimulation();
				//loadrightmenu(getRootPath()+"/doc/receive/receivePassTransact.action",true);
				//f();
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback : f
				});
			} else {
				if(data.labelErrorMessage){
					showErrors("receiveSimulationForm", data.labelErrorMessage);
				} else{
					msgBox.info({
						content: data.errorMessage
					});
				}
			}
		},
		error : function() {
			receiveSimulationForm.subState = false;
			msgBox.tip({
				content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
			});
		}
	});
}
function update(type, f) {
	var url = getRootPath() + "/doc/receive/update.action";
	if (receiveSimulationForm.subState) {
		return;
	}
	receiveSimulationForm.subState = true;
	$("#dataLoad").show();
	$("#formContent").val($("#receiveSimulationForm").formhtml());//获取收文表单html
	var formData = $("#receiveSimulationForm").serializeArray();
	receiveSimulationForm.addFormParameters(formData);
	addContractors(formData);
	jQuery.ajax({
		url : url,
		type : 'Post',// 流程type类型（提交）
		async:false,
		data : formData,
		success : function(data) {
			receiveSimulationForm.subState = false;
			//getToken();
			if (data.success == "true") {
				//f();
				//alert(loadrightmenu);
				//loadrightmenu(getRootPath()+"/doc/receive/receiveWaitTransact.action",true);
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback : jumpFunReceiveToDo
					//callback : f()
				});
			} else {
				if(data.labelErrorMessage){
					showErrors("receiveSimulationForm", data.labelErrorMessage);
				} else{
					msgBox.info({
						content: data.errorMessage
					});
				}
			}
			$("#dataLoad").hide();
		},
		error : function() {
			receiveSimulationForm.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_002")
			});
			$("#dataLoad").hide();
		}
	});
	/*if ($("#receiveSimulationForm").valid()) {
	} else {
		receiveSimulationForm.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}*/
}

function validateFormFail(flag){
	receiveSimulationForm.subState = false;
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
	//$("#dataLoad").hide();
}

//工作流修改回调方法，跳转待办列表页
function jumpFunReceiveToDo(){
	loadrightmenu("/workFlow/form/jumpToList.action?url=/doc/receive/receiveToDoList&time=" + new Date(),'unknown');
}

//流水号下拉列表初始化
receiveSimulationForm.InitSeq = function(){
	var selectObj = $("#seqListForm #seqId1");
	selectObj.html("<option value=''>-请选择-</option>");
	var url = getRootPath()+"/doc/seq/queryAllByControlSide.action";
	var params = {
		time: new Date(),
		seqSpecies:1
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        success: function(data) {
        	if(data){
        		for(var i=0;i<data.length;i++){
        			var optionStr = "<option value='"+data[i].id+"#"+data[i].ruleName+"'>"+data[i].seqName+"</option>";
        			selectObj.append(optionStr);
        		}
        	};
        }
    });		
};
//文号下拉列表初始化
receiveSimulationForm.InitNo = function(){
	var selectObj = $("#noListForm #docNoId1");
	selectObj.html("<option value=''>-请选择-</option>");
	var url = getRootPath()+"/doc/no/queryAllByControlSide.action";
	var params = {
			time: new Date(),
			noType:1
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
//显示流水号选择页面
receiveSimulationForm.showReceiveSeqDiv = function(){
	$('#receive-seq').modal('show');
	//当第一次调用流水号时后台加载数据
	if(document.getElementById("seqId1").options.length==0){
		//流水号下拉列表初始化
		receiveSimulationForm.InitSeq();
	};

};
//显示文号选择页面
receiveSimulationForm.showReceiveNoDiv = function(){
	$('#receive-no').modal('show');
	//当第一次调用流水号时后台加载数据
	if(document.getElementById("docNoId1").options.length==0){
		//文号下拉列表初始化
		receiveSimulationForm.InitNo();
	};

};
//根据下拉选中值获取生成的流水号
receiveSimulationForm.getSeq = function(){
	var seqId = $("#seqId1").val();
	if(seqId!=''){
		if(seqId.split("#")[0]==$("#seqIdOld").val()){//如果修改时选择原来的编号不进行后台获取
			$("#seqIdView").html($("#seqValueOld").val());
		}else{//加载新的编号
			$("#dataLoad").show();
			var r = seqId.split("#")[1];
			var url = getRootPath()+"/doc/seq/getReceiveSeq.action";
			var params = {
					time:new Date(),
					ruleName:r,
					seqSpecies:1
			};
			$.ajax({
				url: url,
				type: 'post',
				data: params,
				success: function(data) {
					$("#dataLoad").hide();
					if(data){
						var sn = data.receiveNumber;
						var snResult = sn.substring(0,1);
						if(snResult=='1'){
							$("#seqIdView").html(sn.substring(1));
						}else{
							$("#seqIdView").html('');
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
		$("#seqIdView").html("");
	}
};
//根据下拉选中值获取生成的文号
receiveSimulationForm.getReceiveNo = function(){
	var docNoId = $("#docNoId1").val();
	if(docNoId!=''){
		if(docNoId.split("#")[0]==$("#docNoIdOld").val()){//如果修改时选择原来的编号不进行后台获取
			$("#docNoIdView").html($("#noValueOld").val());
		}else{//加载新的编号
			$("#dataLoad").show();
			var r = docNoId.split("#")[1];
			var url = getRootPath()+"/doc/no/getReceiveNo.action";
			var params = {
					time:new Date(),
					ruleName:r,
					noType:1
			};
			$.ajax({
				url: url,
				type: 'post',
				data: params,
				success: function(data) {
					$("#dataLoad").hide();
					if(data){
						var sn = data.receiveNumber;
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
//确定选择的编号
receiveSimulationForm.setSeq = function(){
	var snType = $('input[name="snType1"]:checked').val();
	var seqId = $("#seqId1").val();
	if(snType==1){//选择编号
		if(seqId!=''){
			if(seqId.split("#")[0]==$("#seqIdOld").val()){//如果修改是选择原来的编号规则不进行获取
				$("#seqId").val($("#seqIdOld").val());			
				$("#seqValue").val($("#seqValueOld").val());
			}else{//如果是新的编号规则进行重新获取
				$("#seqId").val(seqId.split("#")[0]);
				$("#seqValue").val($("#seqIdView").html());				
			}
		}else{
			$("#seqId").val('');
			$("#seqValue").val('');		
		}
	}else{//手动输入
		$("#seqId").val('');
		$("#seqValue").val($("#seqId2").val());
	}
};
//确定选择的文号
receiveSimulationForm.setNo = function(){
	var snType = $('input[name="snType2"]:checked').val();
	var noId = $("#docNoId1").val();
	if(snType==1){//选择文号
		if(noId!=''){
			if(noId.split("#")[0]==$("#docNoIdOld").val()){//如果修改是选择原来的文号规则不进行获取
				$("#docNoId").val($("#docNoIdOld").val());			
				$("#noValue").val($("#noValueOld").val());
			}else{//如果是新的文号规则进行重新获取
				$("#docNoId").val(noId.split("#")[0]);
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
receiveSimulationForm.radioChange = function(flag){
	if(flag == 1){//编号、流水号
		var snType = $('input[name="snType1"]:checked').val();
		if(snType==1){//选择编号
			$("#seqId2").prop("disabled",true);
			$("#seqId2").val('');
			$("#seqId1").prop("disabled",false);
		}else{//手动输入
			$("#seqId2").prop("disabled",false);
			$("#seqId1").val('');
			$("#seqIdView").html('');
			$("#seqId1").prop("disabled",true);
		}
	}else{//文号
		var snType = $('input[name="snType2"]:checked').val();
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
	}
};
//分发公文
receiveSimulationForm.showDistributionDiv = function (){
	var id = $("#id").val();
	if(id==0){
		msgBox.tip({
			content: "没有数据不能分发公文"
		});		
	}else{
		$(document).showInsideOutAddDiv_(null,id,1);
	}
};

//点击关闭附件弹出层是清空内容
receiveSimulationForm.fileupload = function (){
	$("#businessId").val("0");
	showAttachList(false);
};

//点击关闭时删除一上传文件
receiveSimulationForm.deleteAttach = function() {
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
receiveSimulationForm.closeSendForm = function (){
	$("#sendFlows").hide();
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
	receiveSimulationForm['folderId'] = nodes[0].id;
	receiveSimulationForm['parentFolderId'] = nodes[0].pId;
}
receiveSimulationForm.filing = function() {
	if(!receiveSimulationForm['folderId'] || !receiveSimulationForm['parentFolderId'] || '0' == receiveSimulationForm['parentFolderId']) {
		msgBox.info({
			content : $.i18n.prop("JC_OA_ARCHIVE_018")
		});
		return;
	}
	//调用归档
	receiveSimulationForm.saveToFiling();
}
receiveSimulationForm.closeFiling = function() {
	receiveSimulationForm['folderId'] = '';
	$("#myModal-edit").modal('hide');
}
//保存归档数据
receiveSimulationForm.saveToFiling = function(){
//	alert(data['fileName']);
	//追加workid
	$("form").find("[workFlowType='true']").remove();
	$("[workFlowType='true']").each(function(index,item){
		item = $(item);
		var eleHtml = "<input type='hidden' workFlowType='true' id='"+item.attr("id")+"' name='"+item.attr("name")+"' value='"+item.val()+"'>"
		//$("form").append(eleHtml);由于点击归档按钮后，附言无法增加，所以去掉，暂不知会有什么影响。20150421 xuweiping
	});
	var p = JSON.stringify({folderId:receiveSimulationForm['folderId'],piId:$('#id').val(),formContent:$('#receiveSimulationForm').formhtml()},replaceJsonNull);
	/*if(data) {
		p = JSON.stringify({folderId:receiveSimulationForm['folderId'],piId:$('#id').val(),list:[{fileSize:data['fileSize'],suffix:"aip",name:data['fileName'],url:data['relativePath']}]});
	}*/
	jQuery.ajax({
		url: getRootPath() + "/doc/receive/saveFilingInfo.action",
		type: 'post',
		contentType:"application/json",  
		data: p,
		//async:false,
		cache:false,
		success: function(data, textStatus, xhr) {
			if(data.success == "true") {
				$("#myModal-edit").modal('hide');
				receiveSimulationForm['folderId'] = '';
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
receiveSimulationForm.showFilingDiv = function(){
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
					/*Aip.openFile({fileContent:data.send['viewFile'],state:0,isShowAlert:false,isPrint:true,height:400,isNoButton:true,isSaveToLocal:false,isSaveToService:false,type:"aip",callback:function(a){
						//window.close();
						receiveSimulationForm.saveToFiling(a);
					}});*/
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



/*
 * 转发文
 * */
	function startSendDoc(id,processDefinitionKey) {
		
		confirmx($.i18n.prop("JC_SYS_030"),function(){
			var entrance = "business";
			var scanType = "create";
			setUrlParameter($.toJSON(receiveToSend));
			loadrightmenu("/workFlow/processDefinition/toStartProcess.action?scanType="+scanType+"&id_="+id+"&processDefinitionKey="+processDefinitionKey+"&entrance="+entrance);
		});	
	}
	function changeSend() {
		$('#sendFlows').modal('show');
		showSendFlows();
	}
	var sendFlowsTable = null;
	var oTableAoColumns = [
		               /*	{mData: function(source) {
		               			return "<input type='radio' name='sendFlowIds' value="+ source.id + ">";
		               		}
		               	},*/
		              /* 	{ "mData": "flowType", "mRender" : function(mData) {
		               			return mData == 0 ? "发文" : "收文";
		               		}
		               	},*/
		               	{ "mData": "flowName" },
		               	{ "mData": null, "mRender" : function(mData, type, full) {
		               		var start = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"startSendDoc("+ full.id+ ",'"+ full.flowId+ "')\" role=\"button\" data-toggle=\"modal\"><i data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\"></i>发起</a> ";
		               		var flow = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"workFlow.openProcessDefinitionMap('"+ full.flowId+ "')\" role=\"button\" data-toggle=\"modal\"><i data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\"></i>查看流程图</a>";
		               		return start + flow;
		               		//return oTableSetButtons(mData, type, full);
		               	}}
		             /*  	{ "mData": "isUse", "mRender" : function(mData) {
		               		return mData == 1 ? "启用" : "禁用";
		               	}
		               	},
		               	{ "mData": "createFlowUser"},*/
		               	/*{ "mData": function(source) {
		               		return oTableSetButtons(source);
		               	}}*/
		                ];
	var oTableFnServerFlowParams = function(aoData){
    	//排序条件
    	getTableParameters(sendFlowsTable, aoData);
    	//组装查询条件
    	/*$.each($("#searchFlowForm").serializeArray(), function(i, o) {
    		if(o.value){
    			aoData.push({ "name": o.name, "value": o.value});
    		}
    	});*/
    };
	function showSendFlows() {
		 if (sendFlowsTable == null) {
			 sendFlowsTable = $('#sendFlowsTable').dataTable( {
	      			//iDisplayLength: 5,//每页显示多少条记录
	      			bPaginate: false,
	      			sAjaxSource: getRootPath()+"/doc/send/manageSendFlowList.action",
	      			fnServerData: oTableRetrieveData,//查询数据回调函数
	      			aoColumns: oTableAoColumns,//table显示列
	      			fnServerParams: function ( aoData ) {//传参
	      				oTableFnServerFlowParams(aoData);
	      			},
	      			aaSorting:[],
	      			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	      			aoColumnDefs: [{bSortable: false, aTargets: [0,1]}]
	      			
	      		} );
	      	} else {
	      		sendFlowsTable.fnDraw();
	      	}
	}
	
	//弹出打印页面
	receiveSimulationForm.showDocSendPrint = function() {
		//追加workid
		$("form").find("[workFlowType='true']").remove();
		$("[workFlowType='true']").each(function(index,item){
			item = $(item);
			var eleHtml = "<input type='hidden' workFlowType='true' id='"+item.attr("id")+"' name='"+item.attr("name")+"' value='"+item.val()+"'>"
			//$("#form").append(eleHtml);由于点击打印按钮后，附言无法增加，所以去掉，暂不知会有什么影响。20150421 xuweiping
		});
		$("#formContent").val($("#receiveSimulationForm").formhtml());//获取发文档html
		window.open(getRootPath()+'/doc/send/manageDocPrint.action?time='+new Date());
	};
	
//初始化
function initFun(){
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
				if(receiveToSend['docType']==1){
					$("#businessTable").val('TOA_DOC_RECEIVE');
					$("#iscopy").val("2");//转发文使用复制附件标识（1：复制附件2：复制附件和正文转附件）
				} else if(receiveToSend['docType'] == 0) {
					$("#businessTable").val('TOA_DOC_SEND');
				}
			}
			clearDelAttachIds();//设置附件上传为逻辑删除
			$("#islogicDel").val("1");//附件使用 逻辑删除
			echoAttachList(false);
			$("#iscopy").val("0");//不进行复制
			$("#businessId").val("0");//附件弹出层列表不与数据库交互
			//附件使用 end
		}
		if (receiveToSend['sendDeptId']) {
			$("#sendDeptId").val(receiveToSend['sendDeptId']);
		}
		if (receiveToSend['sendDept']) {
			$("#sendDept").val(receiveToSend['sendDept']);
		}
		if (receiveToSend['sendDeptType']) {
			$("#sendDeptType").val(receiveToSend['sendDeptType']);
		}
		//receiveToSend={};//清空对象
		setUrlParameter('');
        }
	//承办人选择树
	selectControl.init("contractorsTree","contractorsId-contractorsId", false, true);
	//核稿人选择树
	selectControl.init("substitusUserTree","substitusUserId-substitusUserId", false, true);
	//来文单位
	$("#showFromDeptDiv").click(receiveSimulationForm.showFromDeptDiv);
	$("#chooseFromDept").click(receiveSimulationForm.chooseFromDept);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//流水号选择页面
	$("#showSeqDiv").click(receiveSimulationForm.showReceiveSeqDiv);
	//文号选择页面
	$("#showNoDiv").click(receiveSimulationForm.showReceiveNoDiv);
	
	//根据选择的流水号名称获取当前流水号
	$("#seqId1").change(receiveSimulationForm.getSeq);
	//根据选择的流水号名称获取当前文号
	$("#docNoId1").change(receiveSimulationForm.getReceiveNo);
	//确认选择的流水号
	$("#chooseSeq").click(receiveSimulationForm.setSeq);
	//确认选择的文号
	$("#chooseNo").click(receiveSimulationForm.setNo);
	//附件使用
	$("#closebtn").click(receiveSimulationForm.fileupload);
	$("#closeModalBtn").click(receiveSimulationForm.fileupload);
	$("#queryattach").click(receiveSimulationForm.fileupload);
	$("#flowClose").click(receiveSimulationForm.closeSendForm);
	$("#saveOrModifyFiling").click(receiveSimulationForm.filing);
	$("#filingClose").click(receiveSimulationForm.closeFiling);
	//getToken();
	receiveSimulationForm.initPage();
	formDetail.initForm();
};
$(document).ready(function(){
	initFun();
});