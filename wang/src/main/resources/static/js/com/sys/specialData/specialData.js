$(document).ready(function(){
	
	//计算分页显示条数
	specialData.pageCount = TabNub>0 ? TabNub : 10;
	
	
	//初始化列表方法
	specialData.specialDataList();
	
	
	$("#addSpecialDataButton").click(specialData.loadModule);
	$("#showAddDiv_t").click(specialData.loadModule);
	
	$("#querySpecialData").click(specialData.specialDataList);
	$("#queryReset").click(specialData.queryReset);
	$("#deleteSpecialDatas").click("click", function(){specialData.deleteSpecialData(0);});
	



});
  	

	
var specialData = {};

//分页处理 start
//分页对象
specialData.oTable = null;

specialData.oTableAoColumns = [
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{ "mData": "infoName" },
	{ "mData": "infoType" , mRender : function(mData, type, full){
		if(full.infoType == 0)
			return "节日";
		else
			return "生日";
		}
	},
	{ "mData": "specialData" },
	{ "mData": "openLevel" , mRender : function(mData, type, full){
		if(full.openLevel == 1)
			return "完全公开";
		else
			return "部分公开";
		}
	},
	{mData: function(source) {
		return oTableSetButtones(source);
	}}
];

specialData.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(specialData.oTable, aoData);
	
	var infoName = $("#specialDataQueryForm #infoName").val();
	if(infoName != ""){
		aoData.push({ "name": "infoName", "value": infoName});
	}
	var infoType = $("#specialDataQueryForm #infoType").val();
	if(infoType != ""){
		aoData.push({ "name": "infoType", "value": infoType});
	}
	
	
	
};

//分页查询
specialData.specialDataList = function () {
	if (specialData.oTable == null) {
		specialData.oTable = $('#specialDataTable').dataTable( {
			"iDisplayLength": specialData.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/specialData/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": specialData.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				specialData.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,5]}
	        ]
		} );
	} else {
		specialData.oTable.fnDraw();
	}
};

//删除对象
specialData.deleteSpecialData = function (id) {
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
			specialData.deleteCallBack(ids);
		}
	});
};

//删除用户回调方法
specialData.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/specialData/deleteByIds.action",
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
			specialData.specialDataList();
		}
	});
};

specialData.queryReset = function(){
	$('#specialDataQueryForm')[0].reset();
};
	
 //加载添加DIV
specialData.loadModule = function (){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/sys/specialData/loadForm.action",null,function(){
			specialDataModule.show();
		});
	}
	else{
		specialDataModule.show();
	}
	
};
		
//加载修改div
specialData.loadModuleForUpdate = function (id){
	if($.trim($("#formModuleDiv").html()).length == 0){
		$("#formModuleDiv").load(getRootPath()+"/sys/specialData/loadForm.action",null,function(){
			specialDataModule.get(id);
		});
	}
	else{
		specialDataModule.get(id);
	}
	
};

//加载添加DIV
specialData.loadshareHtml = function (id){
	if($.trim($("#specialDatashareEdit").html()) == ""){
		$("#specialDatashareEdit").load(getRootPath()+"/sys/specialdataShare/loadForm.action",null,function(){specialData.shareportal(id);});
	}
	else{
		specialData.shareportal(id);
	}
};

//共享范围方法
specialData.shareportal = function(id) {
	specialData.shareModalLoad();
	specialData.clearShareModal();
	$("#specialdataId").val(id);
	var url = getRootPath()+"/sys/specialdataShare/getIds.action";
	jQuery.ajax({
		  type : "POST",
		  url: url,
	      data : {"specialdataId": id},
		  dataType : "json",
		  success : function(data) {
			  if(data.userids!=null||data.userids!="")
				  leftRightSelect.setData("UserleftRight",data.userids);
			  if(data.userids!=null||data.userids!="")
				  setOrgData("DeptleftRight-DeptleftRight",data.deptids);
			  if(data.userids!=null||data.userids!="")
				  setOrgData("OrganleftRight-OrganleftRight",data.organids);
			  $("#tokens").val(data.token);
			  $('#myModal-share').modal('show');
			  ie8StylePatch();
		  },
	      error:function(){
			  //alert("保存数据错误。");
			  
		  }
		});
};
	
specialData.shareModalLoad = function (){
	leftRightSelect.init({
		containerId:"UserResultDiv",
		moduleId:"UserleftRight",
		isCheck:true,
		url:getRootPath()+"/sys/rolePortal/getData.action?type=user",
		title:"用户",
		callback:function(data){

		}
	});		
	
	selectControl.init("DeptResultDiv", "DeptleftRight-DeptleftRight", true, false, 0);	//部门
	
	selectControl.init("OrganResultDiv", "OrganleftRight-OrganleftRight", true, false, 1);	//机构
};

specialData.clearShareModal = function (){
	$("#UserleftRight").val();
	$("#DeptleftRight-DeptleftRight").val();
	$("#OrganleftRight-OrganleftRight").val();
};
