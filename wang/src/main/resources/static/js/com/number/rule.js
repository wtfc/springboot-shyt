var rule = {};

rule.action = function() {
	if ($("#actionType").val() == "save") {
		rule.validationRule();
	} else if ($("#actionType").val() == "update") {
		rule.validationRuleForUpdate();
	}
}

rule.init = function() {
	$("#showCreate").click(rule.showCreate);
	$("#showCreate_t").click(rule.showCreate);
	
	$("#actionType").val("save");

	for ( var i = 1; i <= 10; i++) {
		$("#s" + i).bind("click", function() {
			rule.showValue(this.id);
		});
	}

	$("#r1").bind("click", function() {
		rule.showDefault();
	});

	$("#r2").bind("click", function() {
		rule.showDate();
	});

	$("#numberType").bind("change", function() {
		rule.changeNumberType();
	});
}

rule.showCreate = function() {
	$("#actionType").val("save");
	$('#myModal-edit').modal('show');
	rule.ruleFormInit();
	// 重置流水号信息
	rule.changeNumberType();
}

rule.showDate = function() {
	$("#manual").show();
	$("#default").hide();
}

rule.showDefault = function() {
	$("#manual").hide();
	$("#default").show();
}

rule.changeNumberType = function() {
	if ($("#numberType").val() == 0) {
		$("#tr_digit").show();
		$("#tr_placeholder").show();
	} else {
		$("#tr_digit").hide();
		$("#tr_placeholder").hide();
	}
}

rule.showValue = function(value) {
	if ($('#l' + value.substring(1)).css('display') == "none")
		$('#l' + value.substring(1)).css('display', 'block');
	else
		$('#l' + value.substring(1)).css('display', 'none');
}

rule.showUpdate = function(ruleName) {
	$("#actionType").val("update");
	$('#myModal-edit').modal('show');
	rule.ruleFormInit();
	rule.showUpdateInfo(ruleName);
}

rule.showUpdateInfo = function(ruleName) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/number/get.action",
		data : {"ruleName": ruleName},
		dataType : "json",
		success : function(data) {
			if (data) {
				$("#ruleName").val(data.ruleName);
				$("#ruleName").attr("readonly", "readonly");
				$("#numberStart").val(data.numberStart);
				if (data.numberStart != "")
					$("#numberStart").attr("readonly", "readonly");
				if (data.dateType == 1) {
					$("#r1").attr("checked", true);
					$("#r2").attr("checked", false);
					$("#default").show();
					$("#manual").hide();
					$("#defaultDate").val(data.dateFormat);
					$("#defaultSplit").val(data.dateSplit);

					var par = data.parameter.split(",");
					if ($.trim(par[0])) {
						$("#l1").show();
						$("#v1").val($.trim(par[0]));
					}
					if ($.trim(par[1])) {
						$("#l2").show();
						$("#v2").val($.trim(par[1]));
					}
					if ($.trim(par[2])) {
						$("#l10").show();
						$("#v10").val($.trim(par[2]));
					}
				} else if (data.dateType == 2) {
					$("#r1").attr("checked", false);
					$("#r2").attr("checked", true);
					$("#default").hide();
					$("#manual").show();
					var dateFormats = data.dateFormat.split("-");
					$("#year").val(dateFormats[0]);
					$("#month").val(dateFormats[1]);
					$("#day").val(dateFormats[2]);
					$("#hour").val(dateFormats[3]);
					$("#minute").val(dateFormats[4]);
					$("#second").val(dateFormats[5]);
					$("#millisecond").val(dateFormats[6]);
					$("#manualSplit").val(data.dateSplit);

					var par = data.parameter.split(",");
					if ($.trim(par[0])) {
						$("#l1").show();
						$("#v1").val($.trim(par[0]));
					}
					var j = 3;
					for ( var i = 1; i < 9; i++) {
						if ($.trim(par[i])) {
							$("#l" + j).show();
							$("#v" + j).val($.trim(par[i]));
						}
						j++;
					}
				}
				$("#numberResetRules").val(data.numberResetRules);
				$("#numberState").val(data.numberState);
				$("#numberCeiling").val(data.numberCeiling);
				$("#numberDigit").val(data.numberDigit);
				$("#numberPlaceholder").val(data.numberPlaceholder);
				$("#numberType").val(data.numberType);
				$("#numberType").attr("disabled", true);
				$("#numberStep").val(data.numberStep);
				$("#dateIndex").val(data.dateIndex);
				$("#numberModule").val(data.numberModule);
				$("#ruleDisplayname").val(data.ruleDisplayname);
				// 重置流水号信息
				rule.changeNumberType();
			
			}
		}
	});
}

rule.validationRuleForUpdate = function() {
	var re = /^\+?[1-9][0-9]*$/;
	if (Number($("#numberDigit").val()) < Number($("#numberStart").val().length)) {
		alert("流水号位数不正确!");
		$("#numberDigit").focus();
		return;
	}
	if ($("#numberStart").val() != "" && $("#numberCeiling").val() != ""
			&& Number($("#numberCeiling").val()) <= Number($("#numberStart").val())) {
		alert("流水号上限输入不正确!");
		$("#numberCeiling").focus();
		return;
	} else {
		rule.updateRule();
	}
}

rule.updateRule = function() {
	$("#action").val("update");
	var params = $('input,select', '#ruleForm').serializeArray();
	if ($("input[name='dateType']:checked").val() == 1) {
		rule.setParams(params, "split", $("#defaultSplit").val());
		rule.setParams(params, "dateFormat", $("#defaultDate").val());
		rule.setParams(params, "parameter", $("#v1").val() + ","
				+ $("#v2").val() + "," + $("#v10").val());
		if ($("#defaultSplit").val() == "年-月-日")
			rule.setParams(params, "splitState", 2);
		else if ($("#defaultSplit").val() == "manual")
			rule.setParams(params, "splitState", 3);
		else
			rule.setParams(params, "splitState", 1);
	} else if ($("input[name='dateType']:checked").val() == 2) {
		rule.setParams(params, "split", $("#manualSplit").val());
		var dateArray = new Array();
		dateArray.push($("#year").val());
		dateArray.push($("#month").val());
		dateArray.push($("#day").val());
		dateArray.push($("#hour").val());
		dateArray.push($("#minute").val());
		dateArray.push($("#second").val());
		dateArray.push($("#millisecond").val());
		rule.setParams(params, "dateFormat", dateArray.join("-"));
		var par = new Array();
		for ( var i = 1; i <= 10; i++) {
			if (i != 2) {
				par.push($("#v" + i).val());
			}
		}
		rule.setParams(params, "parameter", par.join(","));
		rule.setParams(params, "splitState", 0);
		if ($("#manualSplit").val() == "年-月-日-小-时-分-钟-秒-毫秒")
			rule.setParams(params, "splitState", 2);
		else if ($("#manualSplit").val() == "manual")
			rule.setParams(params, "splitState", 3);
		else if ($("#manualSplit").val() != "")
			rule.setParams(params, "splitState", 1);
	}

	$.ajax({
		type : "POST",
		url : getRootPath()+"/number/update.action",
		data : params,
		dataType : "json",
		success : function(data) {
			if (data) {
				if (data.success == "true") {
					alert("规则修改成功!");
					rule.getRuleData();
				} else
					alert("规则修改失败!");
			}
		}
	});
}

rule.validationRule = function() {
	var re = /^\+?[1-9][0-9]*$/;
	var reg = /^[a-z0-9.]*$/gi;
	var zf = /^[A-Za-z]+$/;
	if ($("#ruleName").val() == "") {
		alert("规则名称不能为空!");
		$("#ruleName").focus();
		return;
	} else if (!reg.test($("#ruleName").val())) {
		alert("规则名称只能为字母或者数字!");
		$("#ruleName").focus();
		return;
	} else if ($("#numberType").val() == 0
			&& Number($("#numberDigit").val()) < Number($("#numberStart").val().length)) {
		alert("流水号位数不正确!");
		$("#numberDigit").focus();
		return;
	} else if ($("#numberType").val() == 0 && !re.test($("#numberStart").val())) {
		alert("流水号初始值输入格式不正确!");
		$("#numberStart").focus();
	} else if ($("#numberType").val() == 1 && !zf.test($("#numberStart").val())) {
		alert("流水号初始值输入格式不正确!");
		$("#numberStart").focus();
	} else if ($("#numberStart").val() != "" && $("#numberCeiling").val() != "" && $("#numberType").val() == 0 && !re.test($("#numberCeiling").val())) {
		alert("流水号上限值输入格式不正确!");
		$("#numberCeiling").focus();
	} else if ($("#numberStart").val() != "" && $("#numberCeiling").val() != "" && $("#numberType").val() == 1 && !zf.test($("#numberCeiling").val())) {
		alert("流水号上限值输入格式不正确!");
		$("#numberCeiling").focus();
	} else if ($("#numberStart").val() != "" && $("#numberCeiling").val() != ""
			&& Number($("#numberCeiling").val()) <= Number($("#numberStart").val())) {
		alert("流水号上限输入不正确!");
		$("#numberCeiling").focus();
		return;
	} else {
		var ruleName = $('#ruleName').val();
		var url = getRootPath()+"/number/checkRuleName.action";
		
		jQuery.ajax({
			url : url,
			type : 'GET',
			data : {"ruleName": ruleName},
			success : function(data) {
				if(data){
					alertx("规则名称已经存在");
					$('#ruleName').focus();
				} else {
					rule.saveRule();
				}
			}
		});
	}
}

rule.setParams = function(params, name, value) {
	var obj = {};
	obj.name = name;
	obj.value = value;
	params.push(obj);
	return params;
}

rule.saveRule = function() {
	$("#action").val("save");
	var params = $('input,select', '#ruleForm').serializeArray();
	if ($("input[name='dateType']:checked").val() == 1) {
		rule.setParams(params, "split", $("#defaultSplit").val());
		rule.setParams(params, "dateFormat", $("#defaultDate").val());
		rule.setParams(params, "parameter", $("#v1").val() + ","
				+ $("#v2").val() + "," + $("#v10").val());
		if ($("#defaultSplit").val() == "年-月-日")
			rule.setParams(params, "splitState", 2);
		else if ($("#defaultSplit").val() == "manual")
			rule.setParams(params, "splitState", 3);
		else
			rule.setParams(params, "splitState", 1);
	} else if ($("input[name='dateType']:checked").val() == 2) {
		rule.setParams(params, "split", $("#manualSplit").val());
		var dateArray = new Array();
		dateArray.push($("#year").val());
		dateArray.push($("#month").val());
		dateArray.push($("#day").val());
		dateArray.push($("#hour").val());
		dateArray.push($("#minute").val());
		dateArray.push($("#second").val());
		dateArray.push($("#millisecond").val());
		rule.setParams(params, "dateFormat", dateArray.join("-"));
		var par = new Array();
		for ( var i = 1; i <= 10; i++) {
			if (i != 2) {
				par.push($("#v" + i).val());
			}
		}
		rule.setParams(params, "parameter", par.join(","));
		rule.setParams(params, "splitState", 0);
		if ($("#manualSplit").val() == "年-月-日-小-时-分-钟-秒-毫秒")
			rule.setParams(params, "splitState", 2);
		else if ($("#manualSplit").val() == "manual")
			rule.setParams(params, "splitState", 3);
		else if ($("#manualSplit").val() != "")
			rule.setParams(params, "splitState", 1);
	}
	
	$.ajax({
		type : "POST",
		url : getRootPath()+"/number/save.action",
		data : params,
		dataType : "json",
		success : function(data) {
			if (data) {
				if (data.success == "true") {
					alert("规则添加成功!");
					rule.getRuleData();
					rule.ruleFormInit();
				} else
					alert("规则添加失败!");
			}
		}
	});
}

rule.ruleFormInit = function() {
	$('#ruleForm')[0].reset();
	$("#ruleName").attr("readonly", false);
	$("#numberStart").attr("readonly", false);
	$("#numberType").attr("disabled", false);
	$("#default").show();
	$("#manual").hide();
	for ( var i = 1; i <= 10; i++) {
		$("#l" + i).hide();
	}
	rule.changeNumberType();
}

rule.deleteRule = function(ruleName) {
	if (confirm("确定要删除吗?")) {
		$.ajax({
			type : "POST",
			url : getRootPath()+"/number/delete.action",
			data : {"ruleName": ruleName},
			dataType : "json",
			success : function(data) {
				if (data) {
					if (data == 1) {
						alert("规则删除成功!");
						rule.getRuleData();
					} else
						alert("规则删除失败!");
				}
			}
		});
	}
}

rule.getRuleData = function() {
	var url = getRootPath()+"/number/manageList.action";
	$.getJSON(
		url,
		function(data) {
			if (data) {
				$('#rules').html("");
				$.each(data, function(i, n) {
					var dateFormats = n.dateFormat.split("-");
					var resetRules = '无';
					if (n.numberResetRules == 1)
						resetRules = '按日';
					if (n.numberResetRules == 2)
						resetRules = '按月';
					if (n.numberResetRules == 3)
						resetRules = '按年';

					var html = "<tr><td>"
							+ n.ruleName
							+ "</td>"
							+ "<td>"
							+ dateFormats.join("")
							+ "</td>"
							+ "<td>"
							+ n.dateSplit
							+ "</td>"
							+ "<td>"
							+ (n.numberStart ? n.numberStart
									: "")
							+ "</td>"
							+ "<td>"
							+ (n.numberDigit ? n.numberDigit
									: "")
							+ "</td>"
							+ "<td>"
							+ (n.numberValue ? n.numberValue
									: "")
							+ "</td>"
							+ "<td>"
							+ (n.numberCeiling ? n.numberCeiling
									: "")
							+ "</td>"
							+ "<td>"
							+ (n.numberPlaceholder ? n.numberPlaceholder
									: "")
							+ "</td>"
							+ "<td>"
							+ resetRules
							+ "</td>"
							+ "<td>"
							+ ((n.numberState == 1) ? '是'
									: '否')
							+ "</td>"
							+ "<td>"
							+ (n.parameter ? n.parameter
									: "")
							+ "</td>"
							+ "<td>"
							+ "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"rule.showUpdate('"+ n.ruleName+ "')\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a>"
							+ "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"rule.deleteRule('"+ n.ruleName+ "')\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"></i></a>"
							+ "</td></tr>";
					$('#rules').append(html);
				});

			}
	});
}

$(document).ready(function() {
	rule.getRuleData();
	rule.init();
});