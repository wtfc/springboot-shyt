var no = {};
no.pageRows = null;
//重复提交标识
no.subState = false;

function selectControlCallback(data) {
	opts['echoData'] = [];
	if(data && data.length > 0) {
		opts['echoData'] = data;
	}
}

var opts = {
	single:false,                       //单选
	widgetId : "noControlId",			//控件ID
	widgetName	: "noControlId",		//控件Name
	echoData: [],//回显数据
	callbackFunction: selectControlCallback	//回调函数
};

//分页处理 start
//分页对象
no.oTable = null;
//显示列信息
no.oTableAoColumns = [
    {mData: function(source) {
    	return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
  	}},
	{ "mData": "noName"},
	{ "mData": "noType", "mRender" : function(mData) {
		return mData == 0 ? "发文" : "收文";
	}},
	{ "mData": "numberValue"},
	{ "mData": "createDate"},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
no.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(no.oTable, aoData);
	//组装查询条件
	$.each($("#noListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
no.noList = function () {
	$('#no-list').fadeIn();
	if (no.oTable == null) {
		no.oTable = $('#noTable').dataTable( {
			"iDisplayLength": no.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/no/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": no.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				no.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,5]}]
		} );
	} else {
		no.oTable.fnDraw();
	}
};
//分页处理 end

no.queryReset = function(){
	$('#noListForm')[0].reset();
	//no.noList();
};

//清空表单
no.clearForm = function(){
	$('#noForm')[0].reset();
	$("#noForm #noNameOld").val("");
	$("#isYear").prop("checked",true);
	no.isYearShow();
	no.toNumberView();
	opts['echoData'] = [];
	$("#noControlTree").empty();
	$("#noControlTree").deptAndPersonControl(opts);
	hideErrorMessage();
};

//显示添加文号弹出层
no.showAddDiv = function (){
	//getToken();
	no.clearForm();
	$("#id").val(0);
	$("#saveOrModify").show();
	$("#saveAndClose").removeClass("dark");
	$("#saveAndClose").html("保存退出");
	$("#Modal_Titile").text("新增");
	$('#no-edit').modal('show');
};

//获取修改信息
no.get = function (id) {
	no.clearForm();
	$("#dataLoad").show();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/doc/no/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$("#noForm").fill(data);
				$("#noForm #noNameOld").val(data.noName);//校验名称重复使用
				var parameter = data.parameter;
				if(data.dateFormat==null||data.dateFormat==''){
					$("#isYear").prop("checked",false);
					$("#noForm #parameter1").val(parameter.substring(0,parameter.indexOf(',')));
					$("#noForm #parameter2").val('');	
					$("#noForm #parameter3").val('');	
					$("#noForm #parameter4").val(parameter.substring(parameter.lastIndexOf(',')+1,parameter.length));
				}else{
					$("#isYear").prop("checked",true);
					$("#noForm #parameter1").val(parameter.substring(0,parameter.indexOf('@@##')));
					$("#noForm #parameter2").val(parameter.substring(parameter.indexOf('@@##')+4,parameter.indexOf(',')));
					$("#noForm #parameter3").val(parameter.substring(parameter.indexOf(',')+1,parameter.indexOf(',',parameter.indexOf(',')+1)));
					$("#noForm #parameter4").val(parameter.substring(parameter.lastIndexOf(',')+1,parameter.length));
				}
				no.isYearShow();
				no.toNumberView();
				opts['echoData'] = [];
				if(data.controls) {
					for(var i = 0; i < data.controls.length; i++) {
						opts['echoData'].push({id:data.controls[i]['id'],text:data.controls[i]['text'],type:data.controls[i]['type']});
					}
				}  
				$("#noControlTree").empty();
				$("#noControlTree").deptAndPersonControl(opts);
				$("#saveOrModify").hide();
				$("#saveAndClose").addClass("dark");
				$("#saveAndClose").html("保 存");
				$("#Modal_Titile").text("编辑");
				$('#no-edit').modal('show');
			}
			$("#dataLoad").hide();
		}
	});
};

//添加修改公用方法
no.saveOrModify = function (hide) {
	if (no.subState)return;
	if ($("#noForm").valid()) {
		var numberCeiling = $.trim($("#numberCeiling").val());
		var numberDigit = $("#numberDigit").val();
		var numberDigitNum = +numberDigit;
		if(numberCeiling.length > numberDigitNum) {
	//		alert("上限值位数不能大于流水号位数");
			msgBox.info({
				content: "上限值位数不能大于流水号位数"
			});
			return;
		}
		$("#dataLoad").show();
		
		no.subState = true;
		//按照编号规则进行参数拼装
		if($("#isYear").is(":checked")){//包含年份
			$("#noForm #parameter").val($("#parameter1").val()+"@@##"+$("#parameter2").val()+","+$("#parameter3").val()+",,,,,,,"+$("#parameter4").val());
		}else{//不包含年份
			$("#noForm #parameter").val($("#parameter1").val()+",,"+$("#parameter4").val());
		}
		var url = getRootPath()+"/doc/no/save.action?token="+$('#token').val();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/doc/no/update.action?modifyDate="+$('#noForm #modifyDate').val();
		}
		var obj = {controls:opts['echoData'],id:$('#noForm #id').val(),noName:$('#noForm #noName').val(),noType:$('#noForm input[type="radio"][name="noType"]:checked').attr('value'),parameter:$('#noForm #parameter').val(), numberStart:$('#noForm #numberStart').val(), numberCeiling:$('#noForm #numberCeiling').val(), numberDigit:$('#noForm #numberDigit').val(), numberResetRules:$('#noForm #numberResetRules').val(), ruleName:$('#noForm #ruleName').val(), isYear:$('#noForm input[type="checkbox"][name="isYear"]:checked').attr('value')};
		var p = JSON.stringify(obj,replaceJsonNull);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : p,
			success : function(data) {
				no.subState = false;
				$("#token").val(data.token);
				//getToken();
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if ($("#id").val() == 0) {
						no.clearForm();
					}
					if (hide) {
						$('#no-edit').modal('hide');
					}
					no.noList();
				} else {
					if(data.labelErrorMessage){
						showErrors("noForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
				$("#dataLoad").hide();
			},
			error : function() {
				$("#dataLoad").hide();
				no.subState = false;
				content: $.i18n.prop("JC_SYS_002");
			}
		});
	} else {
		$("#dataLoad").hide();
		no.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//删除文号
no.deleteNo = function (id) {
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
	confirmx($.i18n.prop("JC_SYS_034"),function(){
		no.deleteCallBack(ids);
	});
};

//删除文号回调方法
no.deleteCallBack = function(ids) {
	$("#dataLoad").show();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/no/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				no.noList();
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			$("#dataLoad").hide();
		}
	});
};

no.isYearShow = function(){
	if($("#isYear").is(":checked")){
		$("#isYearContent").show();
		$("#yearView").show();
	}else{
		$("#isYearContent").hide();
		$("#yearView").hide();
	}
};

//禁用启用操作
no.updateIsUse = function(id,isUse,modifyDate){
	var isUseCue;
	if(isUse==0){
		isUseCue = $.i18n.prop("JC_OA_DOC_007");
	}else{
		isUseCue = $.i18n.prop("JC_OA_DOC_008");
	}
	confirmx(isUseCue,function(){
		$("#dataLoad").show();
		jQuery.ajax({
			url : getRootPath()+"/doc/no/updateIsUse.action",
			type : 'POST',
			data : {"id": id,"isUse": isUse,"modifyDate": modifyDate},
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					no.noList();
				} else {
					msgBox.tip({
						content: data.errorMessage
					});
				}
				$("#dataLoad").hide();
			},
			error : function() {
				$("#dataLoad").hide();
				msgBox.tip({
					content:$.i18n.prop("JC_SYS_004")
				});
			}
		});
	});	
};

no.toNumberView = function(){
	if($.trim($("#parameter1").val())!=""){
		$("#firstView").html($("#parameter1").val());
	}else{
		$("#firstView").html('');
	}
	if($.trim($("#parameter2").val())!=""){
		$("#leftView").html($("#parameter2").val());
	}else{
		$("#leftView").html('');
	}
	if($.trim($("#parameter3").val())!=""){
		$("#rightView").html($("#parameter3").val());
	}else{
		$("#rightView").html('');
	}
	if($.trim($("#parameter4").val())!=""){
		$("#lastView").html($("#parameter4").val());
	}else{
		$("#lastView").html('');
	}
	if($.trim($("#numberStart").val())!=""){
		$("#numberView").html(no.toSpeclen(parseInt($("#numberStart").val()),$("#numberDigit").val()));
	}else{
		$("#numberView").html('');
	}
};

no.toSpeclen = function(num,len){
	if(typeof num != "number"){return;}
	while(num.toString().length < len){
		num = "0" + num;
	}
	return num;
};

//初始化
jQuery(function($){
	//计算分页显示条数
	no.pageRows = TabNub>0 ? TabNub : 10;
	$("#queryNo").click(no.noList);
	$("#queryReset").click(no.queryReset);
	//添加页面
	$("#showAddDiv").click(no.showAddDiv);
	$("#showAddDiv_t").click(no.showAddDiv);
	//添加修改方法
	$("#saveAndClose").click(function(){no.saveOrModify(true);});
	$("#saveOrModify").click(function(){no.saveOrModify(false);});
	//删除方法
	$("#deleteNos").click("click", function(){no.deleteNo(0);});
	//是否包含年份
	$("#isYear").click(function(){no.isYearShow();});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//范围使用的人员部门选择树
	$("#noControlTree").deptAndPersonControl(opts);
	//预览
	$("#numberStart").change(function(){no.toNumberView();});
	$("#numberDigit").change(function(){no.toNumberView();});
	$("#parameter1").change(function(){no.toNumberView();});
	$("#parameter2").change(function(){no.toNumberView();});
	$("#parameter3").change(function(){no.toNumberView();});
	$("#parameter4").change(function(){no.toNumberView();});
	no.noList();
});