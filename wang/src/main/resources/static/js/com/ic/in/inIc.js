var inIc={};
//分页处理 start
inIc.pageRows = null;
//分页对象
inIc.oTable = null;

//删除短信
inIc.deleteInMes = function (id) {
	var ids = String(id);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		msgBox.info({
			content: $.i18n.prop("JC_SYS_061")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			inIc.deleteCallBack(ids);
		}
	});
};

//删除用户回调方法
inIc.deleteCallBack = function(ids) {
	$('#dataLoad').fadeIn();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/ic/in/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			$('#dataLoad').fadeOut();
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback:inIc.inIcList()
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};

//回显短信内容
inIc.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/in/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if(data){
				$('#r_originator').html(data.originator);
				$('#r_text').html(data.text);
				$('#r_receiveDate').html(data.receiveDate);
				$('#contentDiv').modal('show');
			}
		}
	});
};

//显示列信息
inIc.oTableAoColumns = [
	//不需要排序的列直接用mData function方式
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{mData: "originator"},
	{mData: "text"},
	{mData: "receiveDate"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
];

//组装后台参数
inIc.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(inIc.oTable, aoData);
	//组装查询条件
	var text = $("#inQueryForm #text").val();
	var inDateStart = $("#inQueryForm #inDateStart").val();
	var inDateEnd = $("#inQueryForm #inDateEnd").val();
	var originator = $("#inQueryForm #originator").val();
	var userId = $("#inQueryForm #userId-userId").val();
	var originatorOut = $("#outSearchForNum").val();
	if(text != ""){
		aoData.push({ "name": "text", "value": text});
	}
	if(inDateStart != ""){
		aoData.push({ "name": "inDateStart", "value": inDateStart});
	}
	if(inDateEnd != ""){
		aoData.push({ "name": "inDateEnd", "value": inDateEnd});
	}
	if(userId != ""){
		aoData.push({ "name": "userId", "value": userId});
	}
	if(originator != ""){
		aoData.push({ "name": "originator", "value": originator});
	}
	if(originatorOut != ""){
		aoData.push({ "name": "originatorOut", "value": originatorOut});
	}
};

//分页查询用户
inIc.inIcList = function () {
	//table对象为null时初始化
	if (inIc.oTable == null) {
		inIc.oTable = $('#inIcTable').dataTable( {
			iDisplayLength: inIc.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/in/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: inIc.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				inIc.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,1,4]}]
		} );
	} else {
		//table不为null时重绘表格
		inIc.oTable.fnDraw();
	}
};

inIc.closeModel = function(){
	$('#contentDiv').modal('hide');
};

//重置方法
inIc.formReset = function(){
	$('#inQueryForm')[0].reset();
	$('#outSearchForNum').val("");
	//清空人员选择树
	selectControl.clearValue("userId-userId");	
};

//初始化方法按如下结构编写
jQuery(function($){
	//计算分页显示条数
	inIc.pageRows = TabNub>0 ? TabNub : 10;
    //初始化内容
	$("#queryIn").click(inIc.inIcList);
	$("#resetIn").click(inIc.formReset);
	$("#deleteInMes").click("click", function(){inIc.deleteInMes(0);});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	selectControl.init("controlTree","userId-userId", false, true);//单选人员
	inIc.inIcList();
	
});

//@ sourceURL=inIc.js
