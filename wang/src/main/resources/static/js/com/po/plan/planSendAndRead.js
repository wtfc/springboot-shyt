var  planSendAndRead = {};
//定义阅读分页变量
planSendAndRead.readPageRows = null;
//阅读分页对象
planSendAndRead.oTableRead = null;

/**
 * 转发人员弹出层初始化
 */
planSendAndRead.initSendUser = function (id) {
	if(!DeleteCascade.syncCheckExist("planSendAndRead",id)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		$('#showSendUserTree').modal('show');
		//清空人员选择树隐藏域
		selectControl.clearValue("sendApplyId-sendIdsPrimaryKeys");
		//弹出层清空方法
		$("#sendUserTree").empty();
		//计划转发弹出层人员选择树初始化
		selectControl.init("sendUserTree","receiveIds-receiveIds", true, true);
		//解除工作任务按钮绑定事件
		$("#sendPlan").unbind();
		//转发按钮绑定事件
		$("#sendPlan").click(function(){planSendAndRead.insertSendUser(id);});
	}
};

/**
 * 转发人员确定方法
 */
planSendAndRead.insertSendUser = function (id){
	if(!DeleteCascade.syncCheckExist("planSendAndRead",id)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		if($("#receiveIds-receiveIds").val() == ""){
			msgBox.tip({
				type : "fail",
				content : $.i18n.prop("JC_OA_PO_030"),
			})
		}else{
			//转发人员名称集合获取
			var sendPlanUserNamesTemp = returnValue("receiveIds-receiveIds");
			if(sendPlanUserNamesTemp != null){
				var sendPlanUserNames = "";
				var temp = sendPlanUserNamesTemp.split(",");
				for(var i=0; i<temp.length;i++){
					sendPlanUserNames += (temp[i].split(":")[1]+",");
				}
			};
			var url = getRootPath()+"/po/planSend/save.action?time=" + new Date();
			var formData = $("#sendUserForm").serializeArray();
			formData.push({"name": "planId", "value": id});
			formData.push({"name": "sendPlanUserNames", "value": sendPlanUserNames.substring(0, sendPlanUserNames.length-1)});
			jQuery.ajax({
				url : url,
				type : 'POST',
				data : formData,
				success : function(data) {
					msgBox.tip({
						type:"success",
						content:data.successMessage
					})
					$('#showSendUserTree').modal('hide');
				}
			});
		}
	}
};

//阅读显示列信息
planSendAndRead.oTableReadAoColumns = [
	{mData: "readerName"},//阅读人
	{mData: "readingDate"}//阅读时间
 ];

/**
 * 初始化阅读情况列表
 */
planSendAndRead.initReadList = function (planId){
	if(!DeleteCascade.syncCheckExist("planSendAndRead",planId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		$('#read').modal('show');
		//阅读情况分页显示条数
		planSendAndRead.readPageRows = TabNub > 0 ? TabNub : 10;
		planSendAndRead.oTableReadFnServerParams = function(aoData){
			 getTableParameters(planSendAndRead.oTableRead, aoData);
			 aoData.push({ "name": "worklogId", "value": planId});
		     aoData.push({ "name": "businessType", "value": 0});
		};
		if (planSendAndRead.oTableRead == null) {
			planSendAndRead.oTableRead = $('#readTable').dataTable( {
				"iDisplayLength": planSendAndRead.readPageRows,//每页显示多少条记录
				"sAjaxSource": getRootPath()+"/po/readingStatus/queryAllByDataTable.action?time=" + new Date(),//后台分页查询地址url
				"fnServerData": oTableRetrieveData,//查询数据回调函数
				"aoColumns": planSendAndRead.oTableReadAoColumns,//table显示列
				//传参
				"fnServerParams": function ( aoData ) {
					planSendAndRead.oTableReadFnServerParams(aoData);
				},
				bPaginate:false,
				//默认不排序列
		        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1]}]
			} );
		} else {
			//table不为null时重绘表格
			planSendAndRead.oTableRead.fnDraw();
		}
	}
};