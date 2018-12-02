var noticeMsg = {};

noticeMsg.oTableAoColumns = [
                    {"mData": function(source) {
                    		return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
                    	}
                    },
                    { "mData": "title" },
                    { "mData": "noticeType" },
                    {mData: "sendUser", mRender : function(mData, type, full){
            				return full.sendUserStr;
            			}
                    },
                    {mData: "readFlag", mRender : function(mData, type, full){
            			return full.readFlagMsg;
	            		}
	            	},
                	{ "mData": function(source) {
                		var btnStr  = "";
                		if(source.readFlag == "0"){
                			btnStr += "<a class='a-icon i-new m-r-xs' href='javascript:void(0)' onclick=\"noticeMsg.readBatch('"+source.id+"')\" data-original-title='已读'>已读</a>";
                		}
                		btnStr += "<a class='a-icon i-remove' href='javascript:void(0)' onclick=\"noticeMsg.deleteBusiness('"+source.id+"')\"><i class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' data-container='body' data-original-title='删除'></i></a>"
                		return btnStr;
                	}}
                  ];
noticeMsg.oTable = null;

noticeMsg.pageRows = null;

noticeMsg.noticeMsgList = function () {
	if (noticeMsg.oTable == null) {
		noticeMsg.oTable = $('#noticeMsgTable').dataTable( {
			"iDisplayLength": noticeMsg.pageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/noticeMsg/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": noticeMsg.oTableAoColumns,//table显示列
			 aaSorting:[],//设置表格默认排序列
			//传参
			"fnServerParams": function ( aoData ) {
				noticeMsg.oTableFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,5]}]
		} );
	} else {
		noticeMsg.oTable.fnDraw();
	}
};

//组装后台参数
noticeMsg.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(noticeMsg.oTable, aoData);
	//组装查询条件
	var titleValue = $("#noticeQueryForm #title").val();
	var noticeTypeValue = $("#noticeQueryForm #noticeType").val();
	var readFlagValue = $("#noticeQueryForm #readFlag").val();
	
	if(titleValue.length > 0){
		aoData.push({ "name": "title", "value": titleValue});
	}
	if(noticeTypeValue.length > 0){
		aoData.push({ "name": "noticeType", "value": $("#noticeQueryForm #noticeType").find("option:checked").html()});
	}
	if(readFlagValue.length > 0){
		aoData.push({ "name": "readFlag", "value": readFlagValue});
	}
};

//删除方法
noticeMsg.deleteBusiness = function(ids){
	var id = String(ids);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		alertx("请选择要删除的记录");
		return;
	}
	confirmx($.i18n.prop("JC_SYS_034"),function(){
		noticeMsg.deleteCallBack(ids);
	});
};

//删除用户回调方法
noticeMsg.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/noticeMsg/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				msgBox.tip({
					type:"success",
					content:"删除成功"
				});
				noticeMsg.noticeMsgList();
			}
		}
	});
};

//已读用户回调方法
noticeMsg.readBatch = function(ids) {
	var id = String(ids);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		alertx("请选择要已读的记录");
		return;
	}
	noticeMsg.readNotice(ids);
};

//已读用户回调方法
noticeMsg.readNotice = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/noticeMsg/readNoticeByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				noticeMsg.noticeMsgList();
			}
		}
	});
};

noticeMsg.queryReset = function(){
	$('#noticeQueryForm')[0].reset();
};

jQuery(function($) {
	
	//计算分页显示条数
	noticeMsg.pageRows = TabNub>0 ? TabNub : 10;
	noticeMsg.noticeMsgList();
	
	$("#queryNoticeMsg").click(noticeMsg.noticeMsgList);
	$("#queryReset").click(noticeMsg.queryReset);
	$("#readBtn").click(function(){noticeMsg.readBatch(0);});
	$("#deleteBtn").click(function(){noticeMsg.deleteBusiness(0);});
});

