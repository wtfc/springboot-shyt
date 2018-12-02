var toaShytCustomer = {};

//分页处理 start
//分页对象
toaShytCustomer.oTable = null;

toaShytCustomer.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData":"companyName"},	{ "mData":"department"},	{ "mData":"sale"},	{ "mData":"tradeUser"},	{ "mData":"linkUser"},	{ "mData":"trade"},	{ "mData":"memberType"},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

toaShytCustomer.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(toaShytCustomer.oTable, aoData);
	
	var companyName = $("#toaShytCustomerQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "companyName", "value":companyName});
	}
	var memberType = $("#toaShytCustomerQueryForm #memberType").val();
	if(memberType.length > 0){
		aoData.push({ "name": "memberType", "value":memberType});
	}
	var tradeUser = $("#toaShytCustomerQueryForm #tradeUser").val();
	if(tradeUser.length > 0){
		aoData.push({ "name": "tradeUser", "value":tradeUser});
	}
	var linkUser = $("#toaShytCustomerQueryForm #linkUser").val();
	if(linkUser.length > 0){
		aoData.push({ "name": "linkUser", "value":linkUser});
	}
	
};

//重置按钮功能
toaShytCustomer.queryReset = function(){
	$('#toaShytCustomerQueryForm')[0].reset();
};


//分页查询
toaShytCustomer.toaShytCustomerList = function () {
	if (toaShytCustomer.oTable == null) {
		toaShytCustomer.oTable = $('#toaShytCustomerTable').dataTable( {
			"iDisplayLength": toaShytCustomer.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/managePermissionList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaShytCustomer.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				toaShytCustomer.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8]}
	        ]
		} );
	} else {
		toaShytCustomer.oTable.fnDraw();
	}
};

toaShytCustomer.deleteToaShytCustomer = function (id) {
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
			toaShytCustomer.deleteTimeSet(ids);
		}
	});
};


toaShytCustomer.deleteTimeSet = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/shyt/toaShytCustomer/deleteByIds.action",
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
			toaShytCustomer.toaShytCustomerList();
		}
	});
};

//获取修改信息
toaShytCustomer.updateToaShytCustomer = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/shyt/toaShytCustomer/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaShytCustomer.clearForm();
				$("#toaShytCustomerTradeForm").fill(data);
				$("#tradeLastUser").val(data.tradeUser);
				//$("#userName-tradeUserId").select2("data", {id:data.createUser,text:data.tradeUser});					
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//重复提交标识
toaShytCustomer.subState = false;

/*toaShytCustomer.spCall = function(data, controlId) {
	$("#toaShytCustomerTradeForm #tradeUser").val("");
	//id
	var id = "";
	//姓名
	var name = "";
	//dataJson
	var userJson = "";
	//遍历数据
	if(data != null || data.length > 0){
		$.each(data, function(i, val){
			id += val.id + ",";
			name += val.text + ",";
			userJson += "{id:"+val.id+", text:'"+val.text+"'},";
		});
	}
	$('#userJson').val("[" + userJson.substring(0, userJson.length-1) + "]");  //用于数据回显
	$("#"+controlId).val(name.substring(0, name.length-1));
	$("#membersId").val(id.substring(0, id.length-1));
	//清空验证信息
	hideErrorMessage();
};
*/

selectControl.init("showTradeUser","userName-tradeUserId", false, true);

toaShytCustomer.clearForm=function(){
	$('#toaShytCustomerTradeForm')[0].reset();
	//清空人员选择树隐藏域
	selectControl.clearValue("userName-tradeUserId");
	hideErrorMessage();
};

toaShytCustomer.saveOrModify = function (hide) {
	if(toaShytCustomer.subState)return;
	toaShytCustomer.subState = true;
	if ($("#toaShytCustomerTradeForm").valid()) {
		var tradeUserId=$("#userName-tradeUserId").val();
		url = getRootPath()+"/shyt/toaShytCustomer/updateTrade.action";
		var formData = $("#toaShytCustomerTradeForm").serializeArray();
		formData.push({"name":"createUser","value":tradeUserId});
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaShytCustomer.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/shyt/toaShytCustomer/manage.action","","/shyt/toaShytCustomer/manage.action");
						}
					});
					if (hide) {
						$('#myModal-edit').modal('hide');
					} else {
						//toaShytCustomer.clearForm();
					}
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("monitorForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
					$("#token").val(data.token);
				}
			},
			error : function() {
				toaShytCustomer.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaShytCustomer.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};

$(document).ready(function(){
	//计算分页显示条数
	toaShytCustomer.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(toaShytCustomer.toaShytCustomerList);
	$("#queryReset").click(toaShytCustomer.queryReset);
	//初始化列表方法
	toaShytCustomer.toaShytCustomerList();
});