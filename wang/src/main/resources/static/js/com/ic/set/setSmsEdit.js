var setSmsEdit = {};
setSmsEdit.pageRows = null;
//重复提交标识
setSmsEdit.subState = false;

//添加修改公用方法
setSmsEdit.save = function (hide) {
	if (setSmsEdit.subState)return;
	setSmsEdit.subState = true;
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
				setSmsEdit.subState = false;
				//getToken();
				$("#token").val(data.token);
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
				setSmsEdit.subState = false;
				msgBox.tip({
					content: jQuery.validator.format($.i18n.prop("JC_SYS_002"))
				});
			}
		});
	} else {
		setSmsEdit.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067"),
			success:function(){
				fnCall();
			}
		});
	}
};

//清空表单
setSmsEdit.clearForm = function () {
	$('#setForm')[0].reset();
	hideErrorMessage();
};

setSmsEdit.initForm = function(){
	//设置默认显示下拉框隐藏人员选择
	$("#setForm #rankId").css('display','block');
	$("#setForm #controlTree").css('display','none');
	setSmsEdit.clearForm();
	var required = '<span class="required">*</span>';
	$("#setForm #change").html(required+"级别");
	//设置默认值为级别
	$("#setForm #setType").val("0");
	//清空人员选择树
	selectControl.clearValue("rankId-rankId");	
	//setSmsEdit.setParam();
};

setSmsEdit.close=function(){
	$('#news').modal('hide');
};


//点击级别或个人单选按钮改变新增修改对应类别下拉框和个人人员选择树
setSmsEdit.showSelectOrTree = function (type){
	//如果选择级别隐藏人员选择，显示select下拉框，否则显示人员选择，隐藏下拉框
	if(type.value==0){
		setSmsEdit.onclickLevel();
	}else{
		setSmsEdit.onclickPerson();
	}
};

//点击级别单选按钮
setSmsEdit.onclickLevel = function(){
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
setSmsEdit.onclickPerson=function(){
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
	$("#savaAndClose").click(function(){setSmsEdit.save(true);});
	$("#sava").click(function(){setSmsEdit.save(true);});
	$("#savaOrModify").click(function(){setSmsEdit.save(false);});
	$("input[name='type-1']").change(function(){setSmsEdit.showSelectOrTree(this);});
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//弹出新增修改人员选择树
	selectControl.init("controlTree","rankId-rankId", false, true);//单选人员
	$("#rankId-rankId").addClass("rankIdValidate");
	$("#rankId").addClass("rankIdV");
	ie8StylePatch();
});
