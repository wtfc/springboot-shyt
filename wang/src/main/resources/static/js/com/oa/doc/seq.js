var seq = {};
seq.pageRows = null;
//重复提交标识
seq.subState = false;

function selectControlCallback(data) {
	opts['echoData'] = [];
	if(data && data.length > 0) {
		opts['echoData'] = data;
	}
}

var opts = {
	single:false,                       //单选
	widgetId : "seqControlId",			//控件ID
	widgetName	: "seqControlId",		//控件Name
	echoData: [],//回显数据
	callbackFunction: selectControlCallback	//回调函数
};

//分页处理 start
//分页对象
seq.oTable = null;
//显示列信息
seq.oTableAoColumns = [
    {mData: function(source) {
    	return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
  	}},
  	{ "mData": "seqName"},
	{ "mData": "seqSpecies", "mRender" : function(mData) {
		return mData == 0 ? "发文" : "收文";
	}},
	{ "mData": "numberValue"},
	{ "mData": "createDate"},
	{ "mData": null, "mRender" : function(mData, type, full) {
		return oTableSetButtons(mData, type, full);
	}}
 ];

//组装后台参数
seq.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(seq.oTable, aoData);
	//组装查询条件
	$.each($("#seqListForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询
seq.seqList = function () {
	$('#seq-list').fadeIn();
	if (seq.oTable == null) {
		seq.oTable = $('#seqTable').dataTable( {
			"iDisplayLength": seq.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/doc/seq/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": seq.oTableAoColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				seq.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,5]}]
		} );
	} else {
		seq.oTable.fnDraw();
	}
};
//分页处理 end

seq.queryReset = function(){
	$('#seqListForm')[0].reset();
	//seq.seqList();
};

//清空表单
seq.clearForm = function(){
	$('#seqForm')[0].reset();
	$('#seqForm #seqNameOld').val('');
	$("#isYear").prop("checked",true);
	seq.isYearShow();
	$("#numberView").html('');
	opts['echoData'] = [];
	$("#seqControlTree").empty();
	$("#seqControlTree").deptAndPersonControl(opts);
	hideErrorMessage();
};

//显示添加文号弹出层
seq.showAddDiv = function (){
	//fgetToken();
	seq.clearForm();
	$("#id").val(0);
	$("#saveOrModify").show();
	$("#saveAndClose").removeClass("dark");
	$("#saveAndClose").html("保存退出");
	$("#Modal_Titile").text("新增");
	$('#seq-edit').modal('show');
};

//获取修改信息
seq.get = function (id) {
	seq.clearForm();
	$("#dataLoad").show();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/doc/seq/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$("#seqForm").fill(data);
				$("#seqForm #seqNameOld").val(data.seqName);
				if(data.dateFormat==null||data.dateFormat==''){
					$("#isYear").prop("checked",false);
					$("#seqForm #parameter").val(",,");
				}else{
					$("#isYear").prop("checked",true);
					$("#seqForm #parameter").val(",,,,,,,,");
				}
				seq.isYearShow();
				seq.toNumberView();
				opts['echoData'] = [];
				if(data.controls) {
					for(var i = 0; i < data.controls.length; i++) {
						opts['echoData'].push({id:data.controls[i]['id'],text:data.controls[i]['text'],type:data.controls[i]['type']});
					}
				}  
				$("#seqControlTree").empty();
				$("#seqControlTree").deptAndPersonControl(opts);
				$("#saveOrModify").hide();
				$("#saveAndClose").addClass("dark");
				$("#saveAndClose").html("保 存");
				$("#Modal_Titile").text("编辑");
				$('#seq-edit').modal('show');
			}
			$("#dataLoad").hide();
		}
	});
};

//添加修改公用方法
seq.saveOrModify = function (hide) {
	if (seq.subState)return;
	if ($("#seqForm").valid()) {
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
		//按照编号规则进行参数拼装
		if($("#isYear").is(":checked")){//包含年份
			$("#seqForm #parameter").val(",,,,,,,,");
		}else{//不包含年份
			$("#seqForm #parameter").val(",,");
		}
		var url = getRootPath()+"/doc/seq/save.action?token="+$('#token').val();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/doc/seq/update.action?modifyDate="+$('#seqForm #modifyDate').val();
		}
		var obj = {controls:opts['echoData'],id:$('#seqForm #id').val(),seqName:$('#seqForm #seqName').val(),seqSpecies:$('#seqForm input[type="radio"][name="seqSpecies"]:checked').attr('value'), parameter:$('#seqForm #parameter').val(), numberStart:$('#seqForm #numberStart').val(), numberCeiling:$('#seqForm #numberCeiling').val(), numberDigit:$('#seqForm #numberDigit').val(), numberResetRules:$('#seqForm #numberResetRules').val(), ruleName:$('#seqForm #ruleName').val(), isYear:$('#seqForm input[type="checkbox"][name="isYear"]:checked').attr('value')};
		var p = JSON.stringify(obj,replaceJsonNull);
		seq.subState = true;
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			contentType: "application/json;charset=UTF-8",
			data : p,
			success : function(data) {
				seq.subState = false;
				$("#token").val(data.token);
				//getToken();
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					if ($("#id").val() == 0) {
						seq.clearForm();
					}
					if (hide) {
						$('#seq-edit').modal('hide');
					}
					seq.seqList();
				} else {
					if(data.labelErrorMessage){
						showErrors("seqForm", data.labelErrorMessage);
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
				seq.subState = false;
				content: $.i18n.prop("JC_SYS_002");
			}
		});
	} else {
		$("#dataLoad").hide();
		seq.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//删除文号
seq.deleteSeq = function (id) {
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
		seq.deleteCallBack(ids);
	});
};

//删除文号回调方法
seq.deleteCallBack = function(ids) {
	$("#dataLoad").show();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/doc/seq/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
				seq.seqList();
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			$("#dataLoad").hide();
		}
	});
};

seq.isYearShow = function(){
	if($("#isYear").is(":checked")){
		$("#yearView").show();
	}else{
		$("#yearView").hide();
	}
};

//禁用启用操作
seq.updateIsUse = function(id,isUse,modifyDate){
	var isUseCue;
	if(isUse==0){
		isUseCue = $.i18n.prop("JC_OA_DOC_007");
	}else{
		isUseCue = $.i18n.prop("JC_OA_DOC_008");
	}
	confirmx(isUseCue,function(){
		$("#dataLoad").show();
		jQuery.ajax({
			url : getRootPath()+"/doc/seq/updateIsUse.action",
			type : 'POST',
			data : {"id": id,"isUse": isUse,"modifyDate": modifyDate},
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
					seq.seqList();
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

seq.toNumberView = function(){
	if($.trim($("#numberStart").val())!="")
		$("#numberView").html(seq.toSpeclen(parseInt($("#numberStart").val()),$("#numberDigit").val()));
};

seq.toSpeclen = function(num,len){
	if(typeof num != "number"){return;}
	while(num.toString().length < len){
		num = "0" + num;
	}
	return num;
};

//初始化
jQuery(function($){
	//计算分页显示条数
	seq.pageRows = TabNub>0 ? TabNub : 10;
	$("#querySeq").click(seq.seqList);
	$("#queryReset").click(seq.queryReset);
	//添加页面
	$("#showAddDiv").click(seq.showAddDiv);
	$("#showAddDiv_t").click(seq.showAddDiv);
	//添加修改方法
	$("#saveAndClose").click(function(){seq.saveOrModify(true);});
	$("#saveOrModify").click(function(){seq.saveOrModify(false);});
	//删除方法
	$("#deleteSeqs").click("click", function(){seq.deleteSeq(0);});
	//是否包含年份
	$("#isYear").click(function(){seq.isYearShow();});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//范围使用的人员部门选择树
	$("#seqControlTree").deptAndPersonControl(opts);
	//预览
	$("#numberStart").change(function(){seq.toNumberView();});
	$("#numberDigit").change(function(){seq.toNumberView();});
	seq.seqList();
});