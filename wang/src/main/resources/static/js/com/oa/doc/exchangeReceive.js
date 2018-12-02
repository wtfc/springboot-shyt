var exchangeReceive = {};
exchangeReceive.pageRows = null;
//重复提交标识
exchangeReceive.subState = false;
//转发文对象
var receiveToSend = {};
//附件下载
var attachDownLoad = {};
//分页处理 start
//分页对象
exchangeReceive.oTable = null;
//显示列信息
exchangeReceive.oTableAoColumns = [
	{ "mData": "status", "mRender" : function(mData) {
		return mData == 0 ? "未签收" : "已签收";
	}},
	{ "mData": "title"},
	{ "mData": "sendDeptName"},
	{ "mData": "isUrgent", "mRender" : function(mData) {
		return mData == 0 ? "否" : "是";
	}},
	{ "mData": "sendDate"},
	{ "mData": "receiveDate"},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
exchangeReceive.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(exchangeReceive.oTable, aoData);
	//组装查询条件
	$.each($("#exchangeReceiveListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
exchangeReceive.exchangeReceiveList = function () {
	$('#exchangeReceive-list').fadeIn();
	if (exchangeReceive.oTable == null) {
		exchangeReceive.oTable = $('#exchangeReceiveTable').dataTable( {
			"iDisplayLength": exchangeReceive.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/exchangeReceive/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": exchangeReceive.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				exchangeReceive.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [6]}]
		} );
	} else {
		exchangeReceive.oTable.fnDraw();
	}
};
//分页处理 end

exchangeReceive.queryReset = function(){
	$('#exchangeReceiveListForm')[0].reset();
	//清空机构控件值
	selectControl.clearValue("sendDeptId-sendDeptId");
	//exchangeReceive.exchangeReceiveList();
};

//添加成功清空表单数据
exchangeReceive.initForm = function(){
	exchangeReceive.clearForm();
};

//清空表单
exchangeReceive.clearForm = function(){
	$('#exchangeReceiveAddForm')[0].reset();
	hideErrorMessage();
};

//签收或拒收操作
exchangeReceive.updateExchangeReceiveStatus = function(id,status,modifyDate){
	var statusCue = "";
	if(status==2){
		statusCue = $.i18n.prop("JC_OA_DOC_009");
	}
	if(status==3){
		statusCue = $.i18n.prop("JC_OA_DOC_010");
	}
	confirmx(statusCue,function(){
		$("#dataLoad").show();
		jQuery.ajax({
			url : getRootPath()+"/doc/exchangeReceive/updateExchangeReceiveStatus.action",
			type : 'POST',
			data : {"id": id,"modifyDate": modifyDate,"status": status},
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					exchangeReceive.exchangeReceiveList();
				} else {
					msgBox.tip({
						content: data.errorMessage
					});
				}
				$("#dataLoad").hide();
			},
			error : function() {
				$("#dataLoad").hide();
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		});
	});	
};

//查看公文
exchangeReceive.showDocView = function(id){
	$("#dataLoad").show();
	var url = getRootPath()+"/doc/exchangeReceive/getSendOrReceiveView.action";
	var params = {
		time: new Date(),
		id: id
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        success: function(data) {
        	$("#dataLoad").hide();
        	if(data){
        		openformNoPerson(data.workId,data.piId,"business","view");
        		//openform(data.workId,data.piId,"business","view");		
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
};
var Doc = {
		id:'',
		viewFile:''
};
exchangeReceive.viewDoc = function(id){
	var url = getRootPath()+"/doc/exchangeSend/get.action";
	var params = {
		time: new Date(),
		id: id
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        success: function(data) {
        	url = getRootPath()+"/doc/send/getSendByWorkflowInstance.action";
        	$.ajax({
        		url: url,
        		cache:false,
                type: 'post',
                data: {id:data.docId},
                success: function(data) {
                	url = getRootPath()+"/doc/send/getSendByWorkflowInstance.action";
                	Doc['id'] = data.id;
                	Doc['viewFile'] = data.viewFile;
                	window.open(getRootPath()+"/doc/send/viewDoc.action");
                },
        		error : function() {
        			$("#dataLoad").hide();
        			msgBox.tip({
        				content: $.i18n.prop("JC_SYS_060")
        			});			
        		}
            });
        },
		error : function() {
			$("#dataLoad").hide();
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_060")
			});			
		}
    });
	
}

//发文流程分页对象
exchangeReceive.sendWorkflowTable = null;
//转发文页面显示
exchangeReceive.showSendWorkflowDiv = function(exchangeReceiveId){
	$('#exchangeReceive-send').modal('show');
	
	exchangeReceive.sendWorkflowFnServerParams = function(aoData){
		getTableParameters(exchangeReceive.sendWorkflowTable, aoData);
	};
	
	//显示发文流程列信息
	exchangeReceive.sendWorkflowColumns = [	                                           
		{ "mData": "flowName"},
       	{ "mData": null, "mRender" : function(mData, type, full) {
       		var start = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"startSendDoc("+ full.id+ ",'"+ full.flowId+ "',"+exchangeReceiveId+")\" role=\"button\" data-toggle=\"modal\"><i data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\"></i>发起</a> ";
       		var flow = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"workFlow.openProcessDefinitionMap('"+ full.flowId+ "')\" role=\"button\" data-toggle=\"modal\"><i data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\"></i>查看流程图</a>";
       		return start + flow;
       	}}
	 ];
	if (exchangeReceive.sendWorkflowTable == null) {
		exchangeReceive.sendWorkflowTable = $('#exchangeReceiveSendTable').dataTable( {
			"sAjaxSource": getRootPath()+"/doc/receive/getReceiveFLows.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": exchangeReceive.sendWorkflowColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				exchangeReceive.sendWorkflowFnServerParams(aoData);
			},
			bPaginate:false,
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		exchangeReceive.sendWorkflowTable.fnDraw();
	}
};

//转发文 跳转发文单
function startSendDoc(id,processDefinitionKey,exchangeReceiveId) {
	var url = getRootPath()+"/doc/exchangeReceive/getSendOrReceiveForSend.action";
	var params = {
			time: new Date(),
			id: exchangeReceiveId
	};
	$.ajax({
		url: url,
		type: 'post',
		data: params,
		success: function(data) {
			$("#dataLoad").hide();
			if(data){
				if(data.send){
					receiveToSend['id']=data.send.id;
					receiveToSend['title']=data.send.title;
					receiveToSend['docType']=0;//发文标识 附件使用	        			
				}else{
					receiveToSend['id']=data.receive.id;
					receiveToSend['sendDeptId']=data.receive.sendDeptId;
					receiveToSend['sendDept']=data.receive.sendDept;
					receiveToSend['sendDeptType']=data.receive.sendDeptType;
					receiveToSend['title']=data.receive.title;
					receiveToSend['docType']=1;//收文标识 附件使用		        			
				}
				var entrance = "business";
				var scanType = "create";
				setUrlParameter($.toJSON(receiveToSend));
				loadrightmenu("/workFlow/processDefinition/toStartProcess.action?scanType="+scanType+"&id_="+id+"&processDefinitionKey="+processDefinitionKey+"&entrance="+entrance,'unknown');
			}else{
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_055")
				});
			}
		},
		error : function() {
			$("#dataLoad").hide();
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_055")
			});			
		}
	});
	/*confirmx($.i18n.prop("JC_SYS_030"),function(){
	});	*/
}
//选择发文流程方法
//receiveSimulationForm.chooseFromDept = function(){
//	var fromDeptName = $('input[name="fromDepts"]:checked').val();
//	if (fromDeptName == null) {
//		msgBox.info({
//			content: $.i18n.prop("JC_SYS_029","来文单位")
//		});
//		return;
//	}
//	$("#sendDept").val(fromDeptName);
//	$('#fromDept-list').modal('hide');
//};
//附件下载
exchangeReceive.showDownLoadDiv = function(exchangeSendId,docType){
	$('#Attach-DownLoad').modal('show');
	// 附件管理
		$.ajax({
			type : "POST",
			url : getRootPath()+"/doc/exchangeReceive/selectAttach.action",
			data : {"exchangeSendId": exchangeSendId,"docType": docType},
			dataType : "json",
			success : function(data) {
				if (data.success == "true") {
					if(data.sealFile != "" && data.sealFile != null){
						Aip.openFile({fileContent:data.sealFile,state:0,isShowAlert:false,isPrint:true,height:400,isNoButton:true,isSaveToLocal:false,isSaveToService:false,type:"aip",callback:function(a){
						}});
					}
					exchangeReceive.renderFiling(data.attachList,data.sealFile);
				} else {
					msgBox.info({
						content: data.errorMessage
					});
				}
			}
			
		});

	//界面渲染
	exchangeReceive.renderFiling = function(attach,sealFile) {
		var tbody = $('#fileDownLoadTable > tbody');
		var dirRow = "";
		var aip = Aip.getAip();
		if(sealFile != "" && sealFile != null){
			dirRow ="<tr><td>公文正文.aip</td><td>"+bytesToSize(aip.GetCurrFileSize())+"</td></tr>";
		}
		tbody.empty();
		$.each(attach, function(i, o) {
			dirRow += ("<tr>");
			dirRow += ("<td>"+o.fileName+"</td>");
			dirRow += ("<td>"+o.attachSize+"</td>");
			dirRow += "</tr>";
		});
		tbody.append(dirRow);
		//如果没有记录，显示空白行
		// archive_doc.processEmptyTable();
		if(tbody.children().length==0){
			tbody.append(exchangeReceive.createFiling());
			return ;
		}
		//IE8兼容处理
		ie8StylePatch();
	};
	//生成空白行
	exchangeReceive.createFiling=function(){
		var row='<tr class="empty_row"><td valign="top" colspan="8" class="dataTables_empty">当前目录下无附件</td></tr>';
		return row;
	};
	// 附件信息end
	
	//附件下载
	$("#saveOrModifyFiling").unbind();
	$("#saveOrModifyFiling").click(function(){
		//调用归档
		saveAIPAttachToService_();
		exchangeReceive.filing(exchangeSendId,docType);
		});
};
//下载文档
exchangeReceive.filing = function(exchangeSendId,docType) {
	$.ajax({
		type : "post",
		url : getRootPath()+"/doc/exchangeReceive/fileDownloadcheck.action?exchangeSendId="+ exchangeSendId +"&docType="+docType +"&fileName="+attachDownLoad['fileName'],
		success : function(data) {
			if (data == true) {
				window.open(getRootPath() + "/doc/exchangeReceive/fileDownload.action?exchangeSendId="+ exchangeSendId +"&docType="+docType+"&fileName="+attachDownLoad['fileName'], "下载附件");
			} else {
				msgBox.info({
					content: $.i18n.prop("JC_OA_ARCHIVE_023")
				});
			}
		}
	});
	return;
};
/**
 * 将文件保存到服务器
 * 返回文件文件属性
 * 文件的服务器全路径
 * 文件名称
 * 文件大小 例如 2m
 */
function saveAIPAttachToService_() {
	//aip.CurrAction=2568;
	//aip.ShowView = 128;
	//alert(Aip.setting_.isShowAlert);
	saveAIPToService_();
/*	if(Aip.setting_.isShowAlert) {
		msgBox.confirm({
			content: Aip.setting_.alertMsg,
			success: function(){
				saveAIPToService_();
			}
		});
	} else {
	}*/
	/*msgBox.confirm({
		content: $.i18n.prop("JC_SYS_066"),
		success: function(){
			
		}
	});*/
	
}

function saveAIPToService_(){
	 try{ 
		 var aip = Aip.getAip();
		 if(Aip.setting_.state == 1) {
			    if("aip" == Aip.setting_.type && Aip.setting_.isFile) {
			    	
			    } else {
			    	aip.ShowView = 32;
			    	var tempfile = aip.GetTempFileName('');
			    	aip.GetOriginalFile(tempfile, -1);
			    	aip.LoadOriginalFile(tempfile, 'doc');
			    }
	        }
		 var docText = aip.GetDocText();
		 var  returnValue = null;
		 if(docText) {
			 aip.HttpInit();          //初始化Http引擎   
			 aip.HttpAddPostString("Company","Dianju");//通过控件post字符串参数
			 aip.HttpAddPostCurrFile("file");
			 //var a = webObj.SaveTo("c:\\webOffice.doc");
			//var result = aip.HttpPost("http://172.16.3.20:8080/goa/common/dj/saveAip.action");
			 var param = '/common/dj/uploadFile.action';
			 if(Aip.setting_.isTemplate) {
				 param = '/common/dj/uploadFile.action?template=1';
			 }  
			 returnValue = aip.HttpPost(path_ + param);    // 判断上传是否成功 
			 
			 if(Aip.setting_.callback) {
				 //var b = aip.GetCurrFileBase64();
				 //Aip.setting_.callback({fileContent:b});
				 if(returnValue) {
					 //返回值包括
					 //fileName
					 //filePath
					 //fileSize
					 var o = JSON.parse(returnValue);
					 attachDownLoad['fileName']=o.fileName;
					 Aip.setting_.callback(o);
				 } else {
					 Aip.setting_.callback(null);
				 }
			 }
		 } else{
			 if(Aip.setting_.callback) {
				 Aip.setting_.callback(null);
			 }
		 }
	      //  alert("==="+ returnValue);
	     /*   */
	    }catch(e){
	        //alert("异常\r\nError:"+e+"\r\nError Code:"+e.number+"\r\nError Des:"+e.description);   
	    } 
}
//公文正文aip文件大小由字节转换KB、MB方法
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1000, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));

  return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}
//初始化
jQuery(function($){
	//计算分页显示条数
	exchangeReceive.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryExchangeReceive").click(exchangeReceive.exchangeReceiveList);
	$("#queryReset").click(exchangeReceive.queryReset);
	//保存分发内容
	$("#saveExchangeReceive").click(exchangeReceive.saveExchangeReceive);
	//回复接收公文
	$("#updateExchangeReceiveReply").click(exchangeReceive.updateExchangeReceiveReply);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//机构选择
	selectControl.init("controlTreeSendDept","sendDeptId-sendDeptId", false, false,1);
    
	exchangeReceive.exchangeReceiveList();
});