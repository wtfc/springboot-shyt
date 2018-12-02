//初始化方法
var userEdit = {};
var toaShytCustomerModule = {};
var shytCustomer = {};
//重复提交标识
toaShytCustomerModule.subState = false;
//获取修改信息
toaShytCustomerModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/shyt/toaShytCustomer/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaShytCustomerModule.clearForm();
				$("#toaShytCustomerForm").fill(data);
			}
		}
	});
};

toaShytCustomerModule.spCall = function(data, controlId) {
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

//添加修改公用方法
toaShytCustomerModule.saveOrModify = function (hide) {
	if(toaShytCustomerModule.subState)return;
		toaShytCustomerModule.subState = true;
	if ($("#toaShytCustomerForm").valid()) {
		var url = getRootPath()+"/shyt/toaShytCustomer/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/shyt/toaShytCustomer/update.action";
		}
		var formData = $("#toaShytCustomerForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaShytCustomerModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/shyt/toaShytCustomer/manage.action","","/shyt/toaShytCustomer/manage.action");
						}
					});
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
				toaShytCustomerModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaShytCustomerModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaShytCustomerModule.clearForm = function(){
	$('#toaShytCustomerForm')[0].reset();
	hideErrorMessage();
};
toaShytCustomerModule.showDeptTree = function (){
	$('#myModal').modal('show');
	//deptTree2 = new deptTree();
	//deptTree2.show("deptTreeDiv", "userForm #deptId", "userForm #deptName",null,false);
	userDeptTree.show("deptTreeDiv", "toaShytCustomerForm #deptId", "toaShytCustomerForm #department",null,false);
};

userEdit.validateDeptName = function(){
	if($("#toaShytCustomerForm #department").val() == ""){
		userEdit.subState = false;
		$("#deptError").html("此信息不能为空").show();
		return true;
	} else {
		$("#deptError").hide();
		return false;
	}
};

//提取公司名称
toaShytCustomerModule.getWorkTask=function(){
	toaShytCustomerModule.backFnServerParams = function(aoData){
		 getTableParameters(toaShytCustomerModule.backTable, aoData);
		 var companyName = $("#customerForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	toaShytCustomerModule.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"toaShytCustomerModule.assignment('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (toaShytCustomerModule.backTable == null) {
		toaShytCustomerModule.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/contract/toaContractIncontract/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": toaShytCustomerModule.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				toaShytCustomerModule.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		toaShytCustomerModule.backTable.fnDraw();
	}
};
toaShytCustomerModule.assignment=function(t,s){
	$("#companyId").val(t);
	$("#companyName").val(s);
};
toaShytCustomerModule.closeWin=function(){
	$("#companyId").val("");
	$("#companyName").val("");
	toaShytCustomerModule.getWorkTask();
};
toaShytCustomerModule.queryReset = function(){
	$('#customerForm')[0].reset();
};

//提取公司名称
shytCustomer.getWorkTask1=function(){
	shytCustomer.backFnServerParams = function(aoData){
		 getTableParameters(shytCustomer.backTable, aoData);
		 var companyName = $("#customerForm1 #companyName11").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	shytCustomer.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"shytCustomer.assignment1('"+full.id+"','"+full.companyName+"')\" />";
	    }},
	    {mData: "companyName"}
	 ];
	if (shytCustomer.backTable == null) {
		shytCustomer.backTable = $('#issuedTaskTable1').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/contract/toaContractIncontract/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": shytCustomer.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				shytCustomer.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
		} );
	} else {
		shytCustomer.backTable.fnDraw();
	}
};
shytCustomer.assignment1=function(t,s){
	//$("#companyId").val(t);
	$("#linkUser").val(s);
};
shytCustomer.closeWin1=function(){
	//$("#companyId").val("");
	$("#linkUser").val("");
	shytCustomer.getWorkTask1();
};
shytCustomer.queryReset1 = function(){
	$('#customerForm1')[0].reset();
};

function add(){
	if(document.all.customerTable1.style.display=="none"){
		$("#customerTable1").attr("style","display:");
	}else if(document.all.customerTable2.style.display=="none"){
		$("#customerTable2").attr("style","display:");
	}else if(document.all.customerTable3.style.display=="none"){
		$("#customerTable3").attr("style","display:");
	}else if(document.all.customerTable4.style.display=="none"){
		$("#customerTable4").attr("style","display:");
	}else if(document.all.customerTable5.style.display=="none"){
		$("#customerTable5").attr("style","display:");
	}else if(document.all.customerTable6.style.display=="none"){
		$("#customerTable6").attr("style","display:");
	}else if(document.all.customerTable7.style.display=="none"){
		$("#customerTable7").attr("style","display:");
	}else if(document.all.customerTable8.style.display=="none"){
		$("#customerTable8").attr("style","display:");
	}else if(document.all.customerTable9.style.display=="none"){
		$("#customerTable9").attr("style","display:");
	}
};
function del9(){
	if(document.all.customerTable9.style.display==""){
		$("#customerTable9").attr("style","display:none");
		$("#name9").val("");
		$("#job9").val("");
		$("#tel9").val("");
		$("#idCard9").val("");
		$("#email9").val("");
		$("#weixin9").val("");
	}
}
function del8(){
	if(document.all.customerTable8.style.display==""){
		$("#customerTable8").attr("style","display:none");
		$("#name8").val("");
		$("#job8").val("");
		$("#tel8").val("");
		$("#idCard8").val("");
		$("#email8").val("");
		$("#weixin8").val("");
	}
};
	function del7(){
		if(document.all.customerTable7.style.display==""){
		$("#customerTable7").attr("style","display:none");
		$("#name7").val("");
		$("#job7").val("");
		$("#tel7").val("");
		$("#idCard7").val("");
		$("#email7").val("");
		$("#weixin7").val("");
	}
};
	function del6(){
		if(document.all.customerTable6.style.display==""){
		$("#customerTable6").attr("style","display:none");
		$("#name6").val("");
		$("#job6").val("");
		$("#tel6").val("");
		$("#idCard6").val("");
		$("#email6").val("");
		$("#weixin6").val("");
	}
};
	function del5(){
		if(document.all.customerTable5.style.display==""){
		$("#customerTable5").attr("style","display:none");
		$("#name5").val("");
		$("#job5").val("");
		$("#tel5").val("");
		$("#idCard5").val("");
		$("#email5").val("");
		$("#weixin5").val("");
	}
};
	function del4(){
		if(document.all.customerTable4.style.display==""){
		$("#customerTable4").attr("style","display:none");
		$("#name4").val("");
		$("#job4").val("");
		$("#tel4").val("");
		$("#idCard4").val("");
		$("#email4").val("");
		$("#weixin4").val("");
	}
};
	function del3(){
		if(document.all.customerTable3.style.display==""){
		$("#customerTable3").attr("style","display:none");
		$("#name3").val("");
		$("#job3").val("");
		$("#tel3").val("");
		$("#idCard3").val("");
		$("#email3").val("");
		$("#weixin3").val("");
	}
};
	function del2(){
		if(document.all.customerTable2.style.display==""){
		$("#customerTable2").attr("style","display:none");
		$("#name2").val("");
		$("#job2").val("");
		$("#tel2").val("");
		$("#idCard2").val("");
		$("#email2").val("");
		$("#weixin2").val("");
	}
};
	function del1(){
		if(document.all.customerTable1.style.display==""){
		$("#customerTable1").attr("style","display:none");
		$("#name1").val("");
		$("#job1").val("");
		$("#tel1").val("");
		$("#idCard1").val("");
		$("#email1").val("");
		$("#weixin1").val("");
	}
};

/*function addPrice(){
	if(document.all.price1.style.display=="none"){
		$("#price1").attr("style","display:");
	}else if(document.all.price2.style.display=="none"){
		$("#price2").attr("style","display:");
	}else if(document.all.price3.style.display=="none"){
		$("#price3").attr("style","display:");
	}else if(document.all.price4.style.display=="none"){
		$("#price4").attr("style","display:");
	}
};
function delPrice4(){
	if(document.all.price4.style.display==""){
		$("#price4").attr("style","display:none");
//			$("#qQ").val("");
//			$("#qQ").rules("remove");
//			$("#qq").prop("checked",false);
		$("#price4").find("input").val(""); 
	}
};
	function delPrice3(){ 
		if(document.all.price3.style.display==""){
		$("#price3").attr("style","display:none");
		$("#price3").find("input").val(""); 
	}
	};
	function delPrice2(){
		if(document.all.price2.style.display==""){
		$("#price2").attr("style","display:none");
		$("#price2").find("input").val(""); 
	}
	};
	function delPrice1(){
		if(document.all.price1.style.display==""){
		$("#price1").attr("style","display:none");
		$("#price1").find("input").val(""); 
	}
};*/
jQuery(function($) 
		{
			$("#showDeptTree").click(toaShytCustomerModule.showDeptTree);
});