var toaFinanceBill = {};

//分页处理 start
//分页对象
toaFinanceBill.oTable = null;

toaFinanceBill.oTableAoColumns = [
	{mData: function(source) {
		if(source.state==0||source.state==1){
			return "";
		}else{
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	}},
	{ "mData":"billDate"},	{ "mData":"companyName"},
	{ "mData":"serviceTypeValue"},	{ "mData":"ticket"},	{ "mData":"billMoney"},	{ "mData":"payDate"},	{ "mData":"sale"},	{ "mData":"salePhone"},
	{ "mData":"oweMoney"},	{ mData: function(source) {
		if(source.state==0){
			return "财务审核";
		}else if(source.state==1){
			return "业务审核";
		}else if(source.state==2){
			return "请打印";
		}else if(source.state==3){
			return "已打印";
		}else if(source.state==4){
			return "开票中(回款中)";
		}else if(source.state==5){
			return "已开票，已回款";
		}else if(source.state==6){
			return "账单退回";
		}
	}},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}];

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
	
	var oweMoney = $("#toaFinanceBillQueryForm #oweMoney").val();
	if(oweMoney.length > 0){
		aoData.push({ "name": "oweMoney", "value":oweMoney});
	}
	
	var billDate = $("#toaFinanceBillQueryForm #billDate").val();
	if(billDate.length > 0){
		aoData.push({ "name": "billDate", "value":billDate});
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
$(document).ready(function(){
	//计算分页显示条数
	toaFinanceBill.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaFinanceBill.toaFinanceBillList);
	$("#queryReset").click(toaFinanceBill.queryReset);
	//初始化列表方法
	toaFinanceBill.toaFinanceBillList();
});