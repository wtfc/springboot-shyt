var customerPeople = {};

//分页处理 start
//分页对象
customerPeople.oTable = null;

customerPeople.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "extStr2"},
	{ "mData": "companyName"},
	{ "mData": "name" },
	{ "mData": "job"},
	{ "mData": "tel"},
	{ "mData": "idCard"},
	{ mData: function(source) {
		if(source.extStr3==1){
			return "是";
		}else{
			return "否";
		}
	}},
	{ "mData": "extStr4"},
	{ "mData": "extDate1"},
	{ "mData": "extNum1"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

customerPeople.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(customerPeople.oTable, aoData);
	
	var companyName = $("#customerPeopleQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var name = $("#customerPeopleQueryForm #name").val();
	if(name.length > 0){
		aoData.push({ "name": "name", "value": name});
	}
	var extStr1 = $("#customerPeopleQueryForm #extStr1").val();
	if(extStr1.length > 0){
		aoData.push({ "name": "extStr1", "value": extStr1});
	}
	var extStr2= $("#customerPeopleQueryForm #extStr2").val();
	if(extStr2.length > 0){
		aoData.push({ "name": "extStr2", "value": extStr2});
	}
};

//重置按钮功能
customerPeople.queryReset = function(){
	$('#customerPeopleQueryForm')[0].reset();
};


//分页查询
customerPeople.customerPeopleList = function () {
	
	if (customerPeople.oTable == null) {
		customerPeople.oTable = $('#customerPeopleTable').dataTable( {
			
			"iDisplayLength": customerPeople.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/customerPeople/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": customerPeople.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				customerPeople.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9,10,11]}
	        ]
		} );
	} else {
		customerPeople.oTable.fnDraw();
	}
};

customerPeople.deleteCustomerPeople = function (id) {
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
			customerPeople.deleteTimeSet(ids);
		}
	});
};


customerPeople.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/shyt/customerPeople/deleteByIds.action",
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
			customerPeople.customerPeopleList();
		}
	});
};
$(document).ready(function(){
	//计算分页显示条数
	customerPeople.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(customerPeople.customerPeopleList);
	$("#queryReset").click(customerPeople.queryReset);
	//初始化列表方法
	customerPeople.customerPeopleList();
});