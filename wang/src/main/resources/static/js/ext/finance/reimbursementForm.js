var reimbursementForm = {};
//重复提交标识
reimbursementForm.subState = false;

//添加附件
reimbursementForm.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name":"fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};

function validateForm(){
	return $("#reimbursementForm").valid();
}

//提交方法
function insert (status,jumpFun){
	var result = true; //流程提交返回值
	if(reimbursementForm.subState)return;
	reimbursementForm.subState = true;
	var url = getRootPath()+"/finance/reimbursement/save.action?time=" + new Date();
	var formData = $("#reimbursementForm").serializeArray();
	reimbursementForm.addFormParameters(formData);
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : formData,
		success : function(data) {
			reimbursementForm.subState = false;
			getToken();
			if(data.success == "true"){
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback:jumpFun
				});
			} else {
				if(data.labelErrorMessage){
					showErrors("reimbursementForm", data.labelErrorMessage);
				} else{
					msgBox.info({
						content: data.errorMessage
					});
				}
			}
		},
		error : function() {
			reimbursementForm.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_002")
			});
		}
	});
	return result;
};

function validateFormFail(){
	reimbursementForm.subState = false;
	msgBox.info({
		content:$.i18n.prop("JC_SYS_067"),
		success:function(){
			fnCall();
		}
	});
}

//修改方法
function update(type,jumpFun) {
	var result = true;
	var url = getRootPath() + "/finance/reimbursement/update.action";
	if (reimbursementForm.subState)
		return;
	reimbursementForm.subState = true;
	var formData = $("#reimbursementForm").serializeArray();
	reimbursementForm.addFormParameters(formData);
	jQuery.ajax({
		url : url,
		type : 'Post',// 流程type类型（提交）
		async:false,
		data : formData,
		success : function(data) {
			reimbursementForm.subState = false;
			getToken();
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage,
					callback : jumpFun
				});
			} else {
				msgBox.info({
					content: "提交失败"
				});
			}
		},
		error : function() {
			reimbursementForm.subState = false;
			msgBox.tip({
				content: $.i18n.prop("JC_SYS_002")
			});
		}
	});
	return result;
}

reimbursementForm.init = function() {
	$.ajax({
		type : "POST",
		url : getRootPath() + "/finance/reimbursement/initInfo.action?time="+new Date(),
		dataType : "json",
		async : false,
		success : function(data) {
			if (data) {
				$("#rePeoName").text(data.user.displayName);
				$("#reDeptName").text(data.user.deptName);
				$("#reDate").val(data.reDate);
				$("#reNum").text(data.reNum);
			}
		}
	});

};

//流程相关 回显数据
function loadForm(piId) {
	$.ajax({
		type : "GET",
		url : getRootPath() + "/finance/reimbursement/get.action",
		data : {
			"piId" : piId,
			time:new Date()
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data) {
				// 清除验证信息
				hideErrorMessage();
				$("#reimbursementForm").fill(data);
				$("#rePeoName").text(data.rePeoName);
				$("#reDeptName").text(data.reDeptName);
				$("#reNum").text(data.reNum);
				$("#gradeSumText").text(data.gradeSum);
				$("#reSunMoneyText").text(data.reSunMoney);
				$("#reSunMoneyBigText").text(data.reSunMoneyBig);
				//附件使用 start
				$("#businessId").val(data.id);
				clearDelAttachIds();//设置附件上传为逻辑删除
				$("#islogicDel").val("1");//附件使用 逻辑删除
				echoAttachList(false);
				clearTable();
				//附件使用 end
				if(null!=data.reimbursementGradeList&&"undefined"!=data.reimbursementGradeList){
					for(var i =1; i<=data.reimbursementGradeList.length; i++){
						$("#subject"+i).val(data.reimbursementGradeList[i-1].subject);
						$("#money"+i).val(data.reimbursementGradeList[i-1].money);
						$("#date"+i).val(data.reimbursementGradeList[i-1].date);
						$("#discribe"+i).val(data.reimbursementGradeList[i-1].discribe);
						$("#remark"+i).val(data.reimbursementGradeList[i-1].remark);
					}
					for(var i =data.reimbursementGradeList.length+1; i<=5; i++){
						$("#tr"+i).hide();
					}					
				}
			}
		},
		error : function() {

		}
	});
	
};

//点击关闭附件弹出层是清空内容
reimbursementForm.fileupload = function (){
	$("#businessId").val("0");
	showAttachList(false);
};

//点击关闭时删除一上传文件
reimbursementForm.deleteAttach = function() {
	if($("#fileids").val().length > 0 && $("#fileid_old").val() != $("#fileids").val()){
		var ids = $("#fileids").val();
		$.ajax({
			type : "POST",
			url : getRootPath()+"/content/attach/delete.action",
			data : {"ids": ids},
			dataType : "json",
			success : function(data) {
				carInfo.carInfoList();
			}
		});
	}
};

//主函数
function DX(n) {
    if (!/^(0|[1-9]\d*)(\.\d+)?$/.test(n))
        return "数据非法";
    var unit = "千百拾亿千百拾万千百拾元角分", str = "";
        n += "00";
    var p = n.indexOf('.');
    if (p >= 0)
        n = n.substring(0, p) + n.substr(p+1, 2);
        unit = unit.substr(unit.length - n.length);
    for (var i=0; i < n.length; i++)
        str += '零壹贰叁肆伍陆柒捌玖'.charAt(n.charAt(i)) + unit.charAt(i);
    return str.replace(/零(千|百|拾|角)/g, "零").replace(/(零)+/g, "零").replace(/零(万|亿|元)/g, "$1").replace(/(亿)万|壹(拾)/g, "$1$2").replace(/^元零?|零分/g, "").replace(/元$/g, "元整");
}
//以下为测试输出

function sumMoney() {
	var sm = 0;
	for(var i=1;i<=5; i++){
		if($("#money"+i).val()!=""){
			sm+=parseFloat($("#money"+i).val());
		}
	}
	$("#reSunMoneyText").text(sm);
	$("#reSunMoney").val(sm);
	$("#reSunMoneyBigText").text(DX(sm));
	$("#reSunMoneyBig").val(DX(sm));
	sumOpt();
};

function sumOpt(){
	var sm = "";
	for(var i=1;i<=5; i++){
		if($("#subject"+i).val()!=""){
			var money = 0;
			if($("#money"+i).val()!=""){
				money = $("#money"+i).val();
			}
			sm+=$("#subject"+i).find("option:selected").text()+":"+money+"元；";
		}
	}
	$("#gradeSumText").text(sm);
	$("#gradeSum").val(sm);
	
}
//流程相关 初始化方法
function initFun() {
	reimbursementForm.init();
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//附件使用
	$("#closebtn").click(reimbursementForm.fileupload);
	$("#closeModalBtn").click(reimbursementForm.fileupload);
	$("#queryattach").click(reimbursementForm.fileupload);
	getToken();
};