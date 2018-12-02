var sendFinishTransact = {};
sendFinishTransact.pageRows = null;
//重复提交标识
sendFinishTransact.subState = false;

//分页处理 start
//分页对象
sendFinishTransact.oTable = null;
//显示列信息

/*
 * 
 *   <th class="w300">编号</th>
                        <th class="w100">报销人员</th>
                        <th class="w100">报销部门</th>
                        <th class="w170">申请日期</th>
                        <th class="w170">报销金额合计(元)</th>
                        <th class="w100">出纳</th>
                        <th class="w100">经手人</th>
                        <th class="w100">领款人</th>
                        <th class="w100">领取状态</th>
                        <th class="w170">操作</th>
                        
                        
	
	{ "mData": "gradeSum"},
	
	
 * */
sendFinishTransact.oTableAoColumns = [
    { "mData": "reNum"},
    { "mData": "rePeoName"},
	{ "mData": "reDeptName"},
	{ "mData": "reDate"},
	{ "mData": "reSunMoney"},
	{ "mData": "reTellerName"},
	{ "mData": "reHandleName"},
	{ "mData": "reDrawName"},
	{ "mData": "reStatus","mRender" : function(mData, type, full) {
		if(full.reStatus == 1) {
			return "已领取";
		}  else {
			return "未领取";
		}
	}},
	//设置权限按钮
	{ "mData":null,"mRender":function(mData,type,full) {
		var opt ={};
		opt.workId = full.workId;
		opt.processStatus = full.processStatus;
		opt.flowStatus = full.flowStatus;
		opt.workflowId = full.piId;
		opt.entrance = "business";
		opt.entranceType = "view";
		return oTableSetButtons(mData,type,full) + getWorkflowListButton(opt);
	}}
 ];

//组装后台参数
sendFinishTransact.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(sendFinishTransact.oTable, aoData);
	//组装查询条件
	$.each($("#sendFinishTransactListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
	aoData.push({"name":"flowStatusForQuery","value":"2"});
};

//分页查询
sendFinishTransact.sendFinishTransactList = function () {
	$('#sendFinishTransact-list').fadeIn();
	var path = getRootPath()+"/finance/reimbursement/reimbursementMoneyList.action";
 	if (sendFinishTransact.oTable == null) {
		sendFinishTransact.oTable = $('#sendFinishTransactTable').dataTable( {
			"iDisplayLength": sendFinishTransact.pageRows,//每页显示多少条记录
			 
			"sAjaxSource": path,
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": sendFinishTransact.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				sendFinishTransact.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,2,5,6,7,8,9]}]
		} );
	} else {
		sendFinishTransact.oTable.fnDraw();
	}
};
//分页处理 end

sendFinishTransact.queryReset = function(){
	$('#sendFinishTransactListForm')[0].reset();
	selectControl.clearValue("createUser-createUser");
	selectControl.clearValue("reDraw-reDraw");
	selectControl.clearValue("reHandle-reHandle");
	selectControl.clearValue("reTeller-reTeller");
	selectControl.clearValue("reDept-reDept");
	
	//sendFinishTransact.sendFinishTransactList();
};
sendFinishTransact.flowClose = function(){
	selectId = "";
	$('#addFlows').modal('hide');
	//sendFinishTransact.sendFinishTransactList();
};
var selectId = "";
//分发公文
sendFinishTransact.showDistributionDiv = function (id,modifyDate){
	//$(document).showInsideOutAddDiv_(sendFinishTransact.sendFinishTransactList,id,0);
	//alert(id);
	selectId = id;
	$('#modifyDate').attr("value",modifyDate);
	$('#addFlows').modal('show');
};
sendFinishTransact.savesaveSelected = function (id){
	//$(document).showInsideOutAddDiv_(sendFinishTransact.sendFinishTransactList,id,0);
	//alert(id);
	var reTellerId = returnValue("reTeller-reTeller");
	var reHandleId = returnValue("reHandle-reHandle");
	var reDrawId = returnValue("reDraw-reDraw");
	if(!reTellerId) {
		msgBox.info({
			content : "请选择出纳"
		});
		return;
	}
	if(!reHandleId) {
		msgBox.info({
			content : "请选择经手人"
		});
		return;
	}
	if(!reDrawId) {	
		msgBox.info({
			content : "请选择领款人"
		});
		return;
	}
	var i = reTellerId.indexOf(":");
	var j = reHandleId.indexOf(":");
	var k = reDrawId.indexOf(":");
	if(sendFinishTransact.subState)return;
	sendFinishTransact.subState = true;
	var mdate = $("#modifyDate").val();
	var url = getRootPath()+"/finance/reimbursement/updateStatus.action?time=" + new Date();
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : {id:selectId,reStatus:"1",modifyDate:mdate,reTeller:reTellerId.substring(0,i),reHandle:reHandleId.substring(0,j),reDraw:reDrawId.substring(0,k)},
		success : function(data) {
			sendFinishTransact.subState = false;
			if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: "领款成功",
				});
				sendFinishTransact.sendFinishTransactList();
				sendFinishTransact.flowClose();
			} else {
				msgBox.info({
					content:"系统更新失败，请稍后再试"
				});
			}
		},
		error : function() {
			sendFinishTransact.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_002")
			});
		}
	});
};

//初始化
jQuery(function($){
 //
	sendFinishTransact.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySendFinishTransact").click(sendFinishTransact.sendFinishTransactList);
	//查询使用人员选择树
	selectControl.init("queryCreateUserTree","createUser-createUser", false, true);
	selectControl.init("querychunaTree","reTeller-reTeller", false, true);
	selectControl.init("queryjingshouTree","reHandle-reHandle", false, true);
	selectControl.init("querylingkuanTree","reDraw-reDraw", false, true);
	selectControl.init("reDeptTree","reDept-reDept", false, false, 0);
	$("#queryReset").click(sendFinishTransact.queryReset);
	$("#flowClose").click(sendFinishTransact.flowClose);
	$("#saveSelectedFlows").click(sendFinishTransact.savesaveSelected);
	//日历控件重新初始化
	$(".datepicker-input").datepicker();		
	sendFinishTransact.sendFinishTransactList();
	

	$("#sendFinishTransactListForm").validate({
		ignore: ".ignore",
        rules: {
 		  title: 
			{
				maxlength: 100,
				fileName:true
			}
	    }
	});
});