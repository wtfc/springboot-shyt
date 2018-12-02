$(document).ready(function(){
	
	//计算分页显示条数
	specialdataShare.pageCount = TabNub>0 ? TabNub : 10;
	
	
	//初始化列表方法
	specialdataShare.specialdataShareList();
	
	
	$("#addSpecialdataShareButton").click(specialdataShare.loadModule);
	$("#showAddDiv_t").click(specialdataShare.loadModule);
	
	$("#querySpecialdataShare").click(specialdataShare.specialdataShareList);
	$("#queryReset").click(specialdataShare.queryReset);
	$("#deleteSpecialdataShares").click("click", function(){specialdataShare.deleteSpecialdataShare(0);});
	



});
  	

	
var specialdataShare = {};

//分页处理 start
//分页对象
specialdataShare.oTable = null;

specialdataShare.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{ "mData": "specialdataId" },
	{ "mData": "shareDept" },
	{ "mData": "shareUsers" },
	{ "mData": "shareOrg" },
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

specialdataShare.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(specialdataShare.oTable, aoData);
	
	var createDateBegin = $("#createDateBegin").val();
	if(createDateBegin.length > 0){
		aoData.push({ "name": "createDateBegin", "value": createDateBegin});
	}
	var createDateEnd = $("#createDateEnd").val();
	if(createDateEnd.length > 0){
		aoData.push({ "name": "createDateEnd", "value": createDateEnd});
	}
	
	
	
};

//分页查询
specialdataShare.specialdataShareList = function () {
	if (specialdataShare.oTable == null) {
		specialdataShare.oTable = $('#specialdataShareTable').dataTable( {
			"iDisplayLength": specialdataShare.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/specialdataShare/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": specialdataShare.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				specialdataShare.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,5]}
	        ]
		} );
	} else {
		specialdataShare.oTable.fnDraw();
	}
};

//删除对象
specialdataShare.deleteSpecialdataShare = function (id) {
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
			specialdataShare.deleteCallBack(ids);
		}
	});
};

//删除用户回调方法
specialdataShare.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/specialdataShare/deleteByIds.action",
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
			specialdataShare.specialdataShareList();
		}
	});
};

specialdataShare.queryReset = function(){
	$('#specialdataShareQueryForm')[0].reset();
};
	
 //加载添加DIV
specialdataShare.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/sys/specialdataShare/loadForm.action",null,function(){
			specialdataShareModule.show();
		});
	}
	else{
		specialdataShareModule.show();
	}
};
		
//加载修改div
specialdataShare.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/sys/specialdataShare/loadForm.action",null,function(){
			specialdataShareModule.get(id);
		});
	}
	else{
		specialdataShareModule.get(id);
	}
};
	

