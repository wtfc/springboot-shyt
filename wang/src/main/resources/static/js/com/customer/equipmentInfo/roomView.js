var roomView = {};
var companyModule={};
var company={};

//分页处理 start
//分页对象
roomView.oTable = null;

roomView.oTableAoColumns = [
    { "mData": "clientName" },
	{ "mData": "type" },
	{ "mData": "contact"},
	{ "mData": "address"},
	{ "mData": "machinaNumber"},
	{ "mData": "onlineDate" },
	{ "mData": "ip" },
	{ "mData": "interchangerOne"},
	{ "mData": "serialNumber"},
	{ "mData": "sn"},
	{ "mData": function(source) {
		return oTableSetButtones(source);
	}}
];

roomView.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(roomView.oTable, aoData);

	var companyName = $("#equipmentQueryForm #companyName").val();
	if(companyName.length > 0){
		aoData.push({ "name": "clientName", "value": companyName});
	}
	
	var ip = $("#equipmentQueryForm #ip").val();
	if(ip.length > 0){
		aoData.push({ "name": "ip", "value": ip});
	}
	var serialNumber = $("#equipmentQueryForm #serialNumber").val();
	if(serialNumber.length > 0){
		aoData.push({ "name": "serialNumber", "value": serialNumber});
	}
	var onlineDate = $("#equipmentQueryForm #onlineDate").val();
	if(onlineDate.length > 0){
		aoData.push({ "name": "onlineDate", "value": onlineDate});
	}
	var contactArr=document.getElementById("contact").options;//得到select所有内容
	var contact = $("#equipmentQueryForm #contact").val();//得到选中的select内容
	for(var index=0;index<contactArr.length;index++){
		if(contact==contactArr[index].value){
			aoData.push({ "name": "contact", "value": contactArr[index].innerText});
		}
	}
};

//重置按钮功能
roomView.queryReset = function(){
	$('#equipmentQueryForm')[0].reset();
};


//分页查询
roomView.equipmentList = function () {
	
	if (roomView.oTable == null) {
		roomView.oTable = $('#equipmentTable').dataTable( {
			
			"iDisplayLength": roomView.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/customer/equipmentInfo/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": roomView.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				roomView.oTableFnServerParams(aoData);
			},
			
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7,8,9]}
	        ]
		} );
	} else {
		roomView.oTable.fnDraw();
	}
};

//设置每行按钮
function oTableSetButtones (source) {
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\"#myModal-edit\" onclick=\"roomView.loadModuleForUpdate("+ source.id+ ")\">操作</a>"; 
	return read;
}; 

//加载操作div
roomView.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/customer/equipmentInfo/loadForm.action",null,function(){
			roomViewModule.get(id);
		});
	}
	else{
		roomViewModule.get(id);
	}
};

//提取公司名称
companyModule.getWorkTask=function(){
	company.backFnServerParams = function(aoData){
		 getTableParameters(company.backTable, aoData);
		 var companyName = $("#companyForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	company.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"companyModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (company.backTable == null) {
		company.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": company.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				company.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		company.backTable.fnDraw();
	}
};
companyModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#companyName").val(s);
	toaMachineRestartModule.toaMachineRestartModuleChange();
};
companyModule.closeWin=function(){
	$("#companyId").val("");
	$("#companyName").val("");
	companyModule.getWorkTask();
};
companyModule.queryReset = function(){
	$('#companyForm')[0].reset();
};

$(document).ready(function(){
	$("#contact").prepend("<option value='0'></option>"); //为Select插入一个Option(第一个位置) 
	document.getElementById("contact").value=0;
	//计算分页显示条数
	roomView.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryMachine").click(roomView.equipmentList);
	$("#queryReset").click(roomView.queryReset);
	//初始化列表方法
	roomView.equipmentList();
});