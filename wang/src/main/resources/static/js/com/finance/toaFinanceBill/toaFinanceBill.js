var toaFinanceBill = {};

//分页处理 start
//分页对象
toaFinanceBill.oTable = null;

toaFinanceBill.oTableAoColumns = [
	{mData: function(source) {
		if(source.state==0){
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}else{
			return "";
		}
	}},
	{ "mData":"billDate"},	{ "mData":"companyName"},
	{ "mData":"serviceTypeValue"},	{ "mData":"ticket"},	{ "mData":"billMoney"},	{ "mData":"payDate"},	{ "mData":"sale"},	{ "mData":"salePhone"},	{ mData: function(source) {
		if(source.state==0){
			return "业务审核";
//		}else if(source.state==1){
//			return "请打印";
//		}else if(source.state==2){
//			return "已打印";
		}else if(source.state==1){
			return "开票中(回款中)";
		}else if(source.state==2){
			return "已开票，已回款";
		}else if(source.state==3){
			return "账单退回";
		}
	}},	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaFinanceBill.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaFinanceBill.oTable, aoData);
	
	var companyName = $("#toaFinanceBillQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	
	var state = $("#toaFinanceBillQueryForm #state").val();
	if(state.length > 0){
		aoData.push({ "name": "state", "value":state});
	}
	
};

//重置按钮功能
toaFinanceBill.queryReset = function(){
	$('#toaFinanceBillQueryForm')[0].reset();
};


//分页查询
toaFinanceBill.toaFinanceBillList = function () {
	if (toaFinanceBill.oTable == null) {
		toaFinanceBill.oTable = $('#toaFinanceBillTable').dataTable( {
			"iDisplayLength": toaFinanceBill.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/finance/toaFinanceBill/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaFinanceBill.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaFinanceBill.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10]}
	        ]
		} );
	} else {
		toaFinanceBill.oTable.fnDraw();
	}
};

toaFinanceBill.deleteToaFinanceBill = function (id) {
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
			toaFinanceBill.deleteTimeSet(ids);
		}
	});
};


toaFinanceBill.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/finance/toaFinanceBill/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			toaFinanceBill.toaFinanceBillList();
		}
	});
};

//审批提交至客户维护部
toaFinanceBill.chooseState = function (id) {
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
			content: $.i18n.prop("JC_SYS_143")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_144"),
		success: function(){
			toaFinanceBill.createState(ids);
		}
	});
};


toaFinanceBill.createState = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/finance/toaFinanceBill/state.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			toaFinanceBill.toaFinanceBillList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	toaFinanceBill.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaFinanceBill.toaFinanceBillList);
	$("#queryReset").click(toaFinanceBill.queryReset);
	$("#createBill").click("click", function(){toaFinanceBill.chooseState(0);});
	//初始化列表方法
	toaFinanceBill.toaFinanceBillList();
});