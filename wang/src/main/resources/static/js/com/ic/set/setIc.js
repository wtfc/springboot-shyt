var setIc = {};
setIc.pageRows = null;
//重复提交标识
setIc.subState = false;

//添加修改公用方法
setIc.save = function (hide) {
	if (setIc.subState)return;
	setIc.subState = true;
	if ($("#setForm").valid()) {
		$('#dataLoad').fadeIn();
		var url = getRootPath()+"/ic/set/save.action?time=" + new Date();
		if ($("#id").val() != 0) {
			url = getRootPath()+"/ic/set/update.action?time=" + new Date();
		}
		var formData = $("#setForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				$('#dataLoad').fadeOut();
				setIc.subState = false;
				//getToken();
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:setIc.setIcList()
					});
					if ($("#id").val() == 0) {
						setSmsEdit.initForm();
					}
					if (hide) {
						$('#news').modal('hide');
					}
				} else {
					if(data.labelErrorMessage){
						showErrors("setForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
				}
			},
			error : function() {
				setIc.subState = false;
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
				});
			}
		});
	} else {
		setIc.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//删除短信设置
setIc.deleteSet = function (id) {
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
			setIc.deleteCallBack(ids);
		}
	});
};

//删除用户回调方法
setIc.deleteCallBack = function(ids) {
	$('#dataLoad').fadeIn();
	$.ajax({
		type : "POST",
		url : getRootPath()+"/ic/set/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			$('#dataLoad').fadeOut();
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback:setIc.setIcList()
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
		}
	});
};


//获取修改信息
setIc.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/set/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				setSmsEdit.initForm();
				$("#setForm").fill(data);
				$("#savaOrModify").hide();
				$("#savaAndClose").hide();
				$("#sava").show();
				$("#saveOrUpdateName").html("编辑");
				$("#sava").addClass("dark");
				//回显操作人员
				//$("#controlUserName").val(data.controlUserName);
				//var myDate = new Date();
				//var str  = myDate.format('yyyy-MM-dd hh:mm:ss');
				//设置时间
				//$("#startDate").val(str);
				//0为级别，1为个人
				if(data.setType==0){
					//设置级别单选按钮选中
					$("#setType0").prop("checked",true);
					setIc.onclickLevel();
					$("#statisticsType").val("0");
					$("#statisticsRank").val(data.rankId);
					//清空人员选择id为rankId-rankId，name为rankId的value
					$("#rankId-rankId").val("");
					//遍历选择框回显选中
					$("#setForm #rankId option").each(function(){
						   if($(this).val() == data.rankId){
							   $(this).prop("selected", true);
						   }
					});
				}else{
					//选中个人单选按钮
					$("#setType1").prop("checked",true);
					$("#rankId").val("");
					$("#statisticsType").val("1");
					$("#statisticsRank").val(data.rankId);
					setIc.onclickPerson();
					select2InitEchoToPerson("setForm #rankId-rankId", {id:data.rankId,text:data.rankName});
				}
				$('#news').modal('show');
				
			}
		}
	});
};

//显示列信息
setIc.oTableAoColumns = [
	//不需要排序的列直接用mData function方式
	{mData: "setType", mRender : function(mData) {
			return mData == 1 ? "个人" : "级别";
		}
	},
	{mData: "rankId", mRender : function(mData, type, full){
		return full.rankName;
		}
	},
	{mData: "maxnum"},
	{mData: "startDate"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
];

//组装后台参数
setIc.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(setIc.oTable, aoData);
	//组装查询条件
	var rankId = $("#setSearchForm #srankId").val();
	if(rankId==null||rankId==""){
		rankId = $("#setSearchForm #srankId-srankId").val();
	}
	var setType = $("#setSearchForm #ssetType").val();
	var maxnum = $("#setSearchForm #num").val();
	var setDateStart = $("#setSearchForm #setDateStart").val();
	var setDateEnd = $("#setSearchForm #setDateEnd").val();
	if(rankId != ""){
		aoData.push({ "name": "rankId", "value": rankId});
	}
	if(setType != ""){
		aoData.push({ "name": "setType", "value": setType});
	}
	if(maxnum != ""){
		aoData.push({ "name": "maxnum", "value": maxnum});
	}
	if(setDateStart != ""){
		aoData.push({ "name": "setDateStart", "value": setDateStart});
	}
	if(setDateEnd != ""){
		aoData.push({ "name": "setDateEnd", "value": setDateEnd});
	}
};

//分页查询用户
setIc.setIcList = function () {
	$('#IP-edit').fadeIn();
	//table对象为null时初始化
	if (setIc.oTable == null) {
		setIc.oTable = $('#setTable').dataTable( {
			iDisplayLength: setIc.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/ic/set/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: setIc.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				setIc.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [1,4]}]
		} );
	} else {
		//table不为null时重绘表格
		setIc.oTable.fnDraw();
	}
};


//查询条件重置
setIc.resetSearch = function(){
	$('#setSearchForm')[0].reset();
	$('#setSearchForm').find("input[type=hidden]").val("");
	selectControl.clearValue("srankId-srankId");
	$("#whole").prop("checked",true);
	setIc.changeType(-1);
	hideErrorMessage();
};

//加载修改div
setIc.loadUpdateHtml = function (id){
	if($.trim($("#setSmsEdit").html()) == ""){
		$("#setSmsEdit").load(getRootPath()+"/ic/set/setSmsEdit.action",null,function(){setIc.get(id);});
	}
	else{
		setIc.get(id);
	}
};

//加载添加DIV
setIc.showAddDiv = function (){
	if($.trim($("#setSmsEdit").html()) == ""){
		$("#setSmsEdit").load(getRootPath()+"/ic/set/setSmsEdit.action",null,setIc.showAddDivs);
	}
	else{
	
		setIc.showAddDivs();
	}
};
//弹出新增设置层
setIc.showAddDivs = function(){
	setSmsEdit.initForm();
//	getToken();
	$("#id").val(0);
	$("#saveOrUpdateName").html("新增");
	$("#savaAndClose").removeClass("dark");
	$("#savaOrModify").show();
	$("#savaAndClose").show();
	$("#sava").hide();
	$('#news').modal('show');
//	$('#startDate').val(getFormatDate());

	//alert(getFormatDate());
	$.ajax({
		type : "GET",
		url : getRootPath()+"/ic/set/getFormatDate.action",
		success : function(data) {
			$('#startDate').val(data);
			
		}
	});
	
};





//点击级别或个人单选按钮改变查询对应类别下拉框和个人人员选择树
setIc.changeType = function (type){
	//如果选择级别隐藏人员选择，显示select下拉框，否则显示人员选择，隐藏下拉框
	if(type.value==0){
		$("#setSearchForm #ssetType").val("0");
		$("#setSearchForm #srankId").css('display','block');
		$("#setSearchForm #controlTreeSearch").css('display','none');
		$("#srankId").prop("disabled",false);
		//清空人员选择树
		selectControl.clearValue("srankId-srankId");
	}else if(type.value==1){
		$("#setSearchForm #ssetType").val("1");
		//显示人员选择树
		$("#setSearchForm #controlTreeSearch").css('display','block');
		//隐藏下拉框
		$("#setSearchForm #srankId").css('display','none');
		$("#setSearchForm #srankId").val("");
	}else{
		$("#setSearchForm #ssetType").val("-1");
		$("#setSearchForm #srankId").css('display','block');
		$("#setSearchForm #controlTreeSearch").css('display','none');
		$("#srankId").prop("disabled",true);
		//清空人员选择树
		selectControl.clearValue("srankId-srankId");
	}
};

//点击级别单选按钮
setIc.onclickLevel = function(){
	hideErrorMessage();
	$("#setForm #setType").val("0");
	//显示下拉框
	$("#setForm #rankId").css('display','block');
	//隐藏人员选择树
	$("#setForm #controlTree").css('display','none');
	var required = '<span class="required">*</span>';
	$("#setForm #change").html(required+"级别");
	//清空人员选择树
	selectControl.clearValue("rankId-rankId");
	//清空人员选择树校验信息
	$("#controlTree .help-block").children().html("");
};

//点击个人单选按钮
setIc.onclickPerson=function(){
	hideErrorMessage();
	$("#setForm #setType").val("1");
	//隐藏下拉框
	$("#setForm #rankId").css('display','none');
	//显示人员选择树
	$("#setForm #controlTree").css('display','block');
	//清空选择框值
	$("#setForm #rankId").val("");
	var required = '<span class="required">*</span>';
	$("#setForm #change").html(required+"个人");
};

//初始化方法按如下结构编写
jQuery(function($){
	//计算分页显示条数
	setIc.pageRows = TabNub>0 ? TabNub : 10;
    //初始化内容
	$("#showAddDiv").click(setIc.showAddDiv);
	$("#showAddDiv_t").click(setIc.showAddDiv);
	//$("#savaAndClose").click(function(){setIc.save(true);});
	//$("#sava").click(function(){setIc.save(true);});
	//$("#savaOrModify").click(function(){setIc.save(false);});
	//查询
	$("#querySet").click(setIc.setIcList);
	//重置
	$("#resetSearch").click(setIc.resetSearch);
	//$("input[name='type-1']").change(function(){setIc.showSelectOrTree(this);});
	$("input[name='type-2']").change(function(){setIc.changeType(this);});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	$("#whole").prop("checked",true);
	$("#srankId").prop("disabled",true);
	//初始化字典
	//setIc.initDic();
	//弹出新增修改人员选择树
	//selectControl.init("controlTree","rankId-rankId", false, true);//单选人员
	//弹出查询人员选择树
	selectControl.init("controlTreeSearch","srankId-srankId", false, true);//单选人员
	//显示列表
	setIc.setIcList();
	//$("#rankId-rankId").addClass("rankIdValidate");
});
