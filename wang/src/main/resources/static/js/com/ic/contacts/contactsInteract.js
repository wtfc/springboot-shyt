var contacts = {};

//分页变量
contacts.pageRows = null;

//重复提交标识
contacts.subState = false;

//分页对象内部联系人
contacts.oTable = null;
//分页对象外部联系人
contacts.oExternalTable = null;

//清空表单
contacts.clearForm = function () {
	if($('#contactsForm')[0] != undefined){
		$('#contactsForm')[0].reset();
	}
};
//---2014.11.17 xuweiping add 导出到Excel---begin
contacts.exportExcel=function(flag){
	var url;
	if(flag == 1){ //内部联系人
		var userName = $("#internalListForm #userNameTemp").val();
		var sex = $("#internalListForm #sexTemp").val();
		var deptIds = $("#internalListForm #name4-nameTemp").val();
		var officeTel = $("#internalListForm #officeTelTemp").val();
		var phone = $("#internalListForm #phoneTemp").val();
		var groupTel = $("#internalListForm #groupTelTemp").val();
		var mail = $("#internalListForm #mailTemp").val();
		
		url = getRootPath()+"/ic/contacts/exportExcelForInternal.action?displayName="+userName
									+"&sex="+sex
									+"&deptIds="+deptIds
									+"&officeTel="+officeTel
									+"&mobile="+phone
									+"&groupTel="+groupTel
									+"&email="+mail
									+"&time="+new Date();
		window.location.href=url;
	}else{
		var userName = $("#externalListForm #userNameTemp").val();
		var sex = $("#externalListForm #sexTemp").val();
		var simpleName = $("#externalListForm #simpleNameTemp").val();
		var groupId = $("#externalListForm #groupIdTemp").val();
		var mail = $("#externalListForm #mailTemp").val();
		var phone = $("#externalListForm #phoneTemp").val();
		
		url = getRootPath()+"/ic/contacts/exportExcelForExternal.action?userName="+userName
									+"&sex="+sex
									+"&simpleName="+simpleName
									+"&phone="+phone
									+"&groupId="+groupId
									+"&mail="+mail
									+"&time="+new Date();
		window.location.href=url;
	}
};

//---2014.11.17 xuweiping add 导出到Excel---end


//删除联系人
contacts.deleteContacts = function (id) {
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
			contacts.deleteCallBack(ids);
		}
	});
};

contacts.deleteCallBack = function(ids) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/contacts/delete.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				contacts.clearForm();
				contacts.contactsExternalList();
				$("[name='contactsChecked']").each(function() {
					$(this).attr("checked", false);
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};


//分页处理 start

//显示内部联系人列信息
contacts.oTableInternalColumns = [
	{ "mData": "displayName" },
	{ "mData": "sex", mRender : function(mData, type, full) {
		return full.sexValue;
	}
    },
	{ "mData": "deptName" },
	{ "mData": "officeTel" },
	{ "mData": "mobile" },
	{ "mData": "groupTel" },
	{ "mData": "email" }
 ];

//显示外部联系人列信息
contacts.oTableExternalColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
    { "mData": "groupName", 
	   	 mRender : function(mData) {				     
		      if (mData == null||mData=="") {
		    	  return "未分组";
				}
		      return mData;
			}
	    },
	{ "mData": "userName" },
	{ "mData": "simpleName" },
	{ "mData": "sex", mRender : function(mData, type, full) {
		return full.sexValue;
	}
    },
	{ "mData": "phone" },
	{ "mData": "mail" },
	
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
 ];

//分页查询内部联系人
contacts.contactsInternalList = function () {
	//组装后台参数
	contacts.oTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(contacts.oTable, aoData);
		//组装查询条件,同时将查询条件值赋给临时变量，用于导出Excel时使用----xuweiping 2014.11.17 add
		//姓名
		var userName =$("#internalListForm #userName").val();
		$("#internalListForm #userNameTemp").val(userName);
		//性别
		var sex = $("#internalListForm #sex").val();
		$("#internalListForm #sexTemp").val(sex);
		//简称
		// var simpleName = $("#internalListForm #simpleName").val();
		//邮箱地址
		var mail = $("#internalListForm #mail").val();
		$("#internalListForm #mailTemp").val(mail);
		//移动电话
		var phone = $("#internalListForm #phone").val(); 
		$("#internalListForm #phoneTemp").val(phone);
		//集团号码
		var groupTel = $("#internalListForm #groupTel").val(); 
		$("#internalListForm #groupTelTemp").val(groupTel);
		//部门ID
		var controlTree = $("#name4-name").val(); 
		$("#name4-nameTemp").val(controlTree);
		// 办公电话
		var officeTel = $("#internalListForm #officeTel").val(); 
		$("#internalListForm #officeTelTemp").val(officeTel);
		
		if(userName != ""){
			aoData.push({ "name": "displayName", "value": userName});
		}
		if(sex !=null && sex != ""){
			aoData.push({ "name": "sex", "value": sex});
		}
		if(mail != ""){
			aoData.push({ "name": "email", "value": mail});
		}
//		if(simpleName != ""){
//			aoData.push({ "name": "loginName", "value": simpleName});
//		}
		if(phone != ""){
			aoData.push({ "name": "mobile", "value": phone});
		}
		if(groupTel != ""){
			aoData.push({ "name": "groupTel", "value": groupTel});
		}
		if(officeTel != ""){
			aoData.push({ "name": "officeTel", "value": officeTel});
		}
		if(controlTree != "" && controlTree != undefined){
			aoData.push({ "name": "deptIds", "value": controlTree});
		}
	};
	
	if (contacts.oTable == null) {
		contacts.oTable = $('#internal').dataTable( {
			iDisplayLength: contacts.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/contacts/getUserList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: contacts.oTableInternalColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				contacts.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置默认排序列
			//默认不排序列
	        aoColumnDefs: [{"bSortable": false, "aTargets": []}]
		} );
	} else {
		contacts.oTable.fnDraw();
	}
};
//分页处理 end

//分页查询外部联系人
contacts.contactsExternalList = function () {
	//组装后台参数
	contacts.oTableExternalParams = function(aoData){
		//排序条件
		getTableParameters(contacts.oExternalTable, aoData);
		//组装查询条件
		//姓名
		var userName =$("#externalListForm #userName").val();
		$("#externalListForm #userNameTemp").val(userName);
		//性别
		var sex = $("#externalListForm #sex").val();
		$("#externalListForm #sexTemp").val(sex);
		//简称
		var simpleName = $("#externalListForm #simpleName").val();
		$("#externalListForm #simpleNameTemp").val(simpleName);
		//组别
		var groupId = $("#externalListForm #contactsGroupId").val();
		$("#externalListForm #groupIdTemp").val(groupId);
		//邮箱地址
		var mail = $("#externalListForm #mail").val();
		$("#externalListForm #mailTemp").val(mail);
		//移动电话
		var phone = $("#externalListForm #phone").val(); 
		$("#externalListForm #phoneTemp").val(phone);
		if(userName != ""){
			aoData.push({ "name": "userName", "value": userName});
		}
		if(sex !=null && sex != ""){
			aoData.push({ "name": "sex", "value": sex});
		}
		if(simpleName != ""){
			aoData.push({ "name": "simpleName", "value": simpleName});
		}
		if(groupId != ""){
			aoData.push({ "name": "groupId", "value": groupId});
		}
		if(mail != ""){
			aoData.push({ "name": "mail", "value": mail});
		}
		if(phone != ""){
			aoData.push({ "name": "phone", "value": phone});
		}
	};
	if (contacts.oExternalTable == null) {
		contacts.oExternalTable = $('#external').dataTable( {
			iDisplayLength: contacts.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/contacts/getContactsList.action",
			fnServerData: oTableRetrieveDataContacts,//查询数据回调函数
			aoColumns: contacts.oTableExternalColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				contacts.oTableExternalParams(aoData);
			},
			aaSorting:[],//设置默认排序列
			//默认不排序列
	        aoColumnDefs: [{"bSortable": false, "aTargets": [0,7]}]
		} );
	} else {
		contacts.oExternalTable.fnDraw();
		//设置隔行变色
		$("#external tr:odd td").css("background-color","#fff");
		$("#external tr:even td").css("background-color","#fdfcfb");
	}
};
//分页处理 end

//tab页切换显示分页
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
	var dataId = $(e.target).attr("data-id");
	//内部联系人
	if(dataId =="internalContacts"){
		contacts.internalListFormClean();
		contacts.contactsInternalList();
		$('#showAddDiv_1').hide();
	}
	//外部联系人
	else if(dataId =="externalContacts"){
		$("[name='contactsChecked']").each(function() {
			$(this).attr("checked", false);
		});
		contacts.externalListFormClean();
		contacts.contactsExternalList();
		$('#showAddDiv_1').show();
	}
});

//加载添加联系人新增DIV
contacts.loadAddHtml = function (){
	if($.trim($("#contactsDiv").html()) == ""){
		$("#contactsDiv").load(getRootPath()+"/ic/contacts/contactsDiv.action",contacts.showAddDiv);
	}
	else{
		contacts.showAddDiv();
	}
};

//加载添加联系人修改DIV
contacts.getAddHtml = function (id){
	if($.trim($("#contactsDiv").html()) == ""){
		$("#contactsDiv").load(getRootPath()+"/ic/contacts/contactsDiv.action",null,function(){contactsDiv.get(id);});
	}
	else{
		contactsDiv.get(id)
	}
};

//加载添加联系人组新增DIV
contacts.loadGroupHtml = function (){
	if($.trim($("#groupDiv").html()) == ""){
		$("#groupDiv").load(getRootPath()+"/ic/contacts/groupDiv.action",contacts.showGroupsDiv);
	}
	else{
		contacts.showGroupsDiv();
	}
};

//清空内部联系人查询条件
contacts.internalListFormClean = function(){
	$('#internalListForm')[0].reset();
	selectControl.clearValue("name4-name");
};
//清空外部联系人查询条件
contacts.externalListFormClean = function(){
	$('#externalListForm')[0].reset();
};

//显示添加联系人弹出层
contacts.showAddDiv = function (){
	//清除验证信息
	hideErrorMessage();
	contacts.clearForm();
	$("#id").val(0);
	$("#userNameOld").val("");
	$("#mailOld").val("");
	$("#phoneOld").val("");
	$("#savaOrModify").show();
	$("#mailContacts").html("新增");
	$("#saveAndClose").removeClass("dark");
	$("#saveAndClose").html("保存退出");
	$('#editContacts').modal('show');
};
//联系人组别弹出层
contacts.showGroupsDiv = function(){
	$("[name='groupChecked']").each(function() {
		$(this).attr("checked", false);
	});
	$("#groupForm [name='ids']").each(function() {
		$(this).attr("checked", false);
	});
	$('#group').modal('show');	
};

//初始化数据
jQuery(function($) 
{ 
	//计算分页显示条数
	contacts.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryInternal").click(contacts.contactsInternalList);
	$("#queryExternal").click(contacts.contactsExternalList); 
	$("#queryResetInternal").click(contacts.internalListFormClean); 
	$("#queryResetExternal").click(contacts.externalListFormClean); 
	$("#deleteContacts").click("click", function(){contacts.deleteContacts(0);});
	$("#showAddDiv").click(contacts.loadAddHtml);	
	$("#showAddDiv_1").click(contacts.loadAddHtml);
	$("#conGroups").click(contacts.loadGroupHtml);
	contacts.contactsInternalList();
	selectControl.init("controlTree", "name4-name", true, false, 0);	//部门
});

oTableRetrieveDataContacts = function ( sSource, aoData, fnCallback ) {
    $.ajax({
		type : "GET",
		url : sSource,
		data : aoData,
		dataType : "json",
		success : function(resp) {
			fnCallback(resp);
			//重新绑定编辑和删除提交框
			$("i[data-toggle='tooltip']").tooltip();
			ie8TableStyle();
			$("#external tr:odd td").css("background-color","#fff");
			$("#external tr:even td").css("background-color","#fdfcfb");
		}
	});
};
//@ sourceURL=contactsInteract.js