var machine = {};

//分页处理 start
//分页对象
machine.oTable = null;

machine.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id +">";
		}
	},
	{ "mData": "companyName"},
	{ "mData": "machina"},
	{ "mData": "machinaNumber"},
	{ "mData": "contact" },
	{ "mData": "tel" },
	{ "mData": "type" },
	{ "mData": "origin" },
	{ "mData": "intDate" },
	{mData: function(source) {
		return oTableSetButtones(source);
		alert(source);
	}}
];

machine.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(machine.oTable, aoData);
	
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
machine.machineList = function () {
	
	if (machine.oTable == null) {
		machine.oTable = $('#machineTable').dataTable( {
			
			"iDisplayLength": machine.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/machine/machine/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": machine.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				machine.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,5]}
	        ]
		} );
	} else {
		machine.oTable.fnDraw();
	}
};

$(document).ready(function(){
	
	//计算分页显示条数
	machine.pageCount = TabNub>0 ? TabNub : 10;
	//初始化列表方法
	machine.machineList();
	
	
	/*$("#addmachineButton").click(machine.loadModule);
	$("#showAddDiv_t").click(machine.loadModule);
	
	$("#querymachine").click(machine.machineList);
	$("#queryReset").click(machine.queryReset);
	$("#deletemachines").click("click", function(){machine.deletemachine(0);});*/
});