var column = {};

column.pageCount = 10;

// 重复提交标识
column.subState = false;

// 分页处理 start
// 分页对象
column.oTable = null;

// 显示列信息
column.oTableAoColumns = [
	{ "mData": "columnName" },
	{ 
		"mData": "isPublic", 
		"mRender" : function(mData, type, full) {
			return full.isPublic == 1 ? "公开":"不公开";
		}
	},
	{ "mData": "displayName" },
	{ "mData": "createDate" },
	// 设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
];

// 组装后台参数
column.oTableFnServerParams = function(aoData){
	// 排序条件、页数
	getTableParameters(column.oTable, aoData);
	
	// 查询条件
	if ($("#parentId").val().length > 0) {
		aoData.push({ "name": "parentId", "value": $("#parentId").val()});
	} 
};


// 分页查询
column.columnList = function () {
	if (column.oTable == null) {
		column.oTable = 
			$('#columnTable').dataTable({
				"iDisplayLength": column.pageCount,								// 每页显示多少条记录
				"sAjaxSource": getRootPath()+"/im/column/manageList.action",	// 查询URL
				"fnServerData": oTableRetrieveData,									// 查询数据回调函数
				"aoColumns": column.oTableAoColumns,							// table显示列
				"fnServerParams": function ( aoData ) {								// 传参
					column.oTableFnServerParams(aoData);
				},
		        "aoColumnDefs": [													// 默认不排序列
					{"bSortable": false, "aTargets": [0,1,2,4]}
		        ],
		        fnDrawCallback : function(oSettings) {
		    	    if($("#treeDemo")[0]){
		    	    	var content = $("#content").height();
		    	        var headerHeight_1 = $('#header_1').height() || 0;
		    	        var headerHeight_2 = $("#header_2").height() || 0;
		    	        $(".tree-right").css("padding-left","215px");
		    			$("#LeftHeight").height(content-80-headerHeight_1-headerHeight_2);
		    	        var lh = $("#LeftHeight").height()  
		    	        if($("#scrollable").scrollTop()>=113){
		    	            $("#LeftHeight").addClass("fixedNav");
		    	            $("#LeftHeight").height(lh + 113)
		    	        }else{
		    	            var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop()
		    	            $("#LeftHeight").height(lh + a)
		    	            $("#LeftHeight").removeClass("fixedNav");
		    	        }
		    	    }
		    	}
			});
	} else {
		column.oTable.fnDraw();
	}
};

// 分页处理 end

/**
 * 显示部门添加DIV
 */
column.showAddcolumnDiv = function(){
//	if($.trim($("#infoColumnAddOrModify").html()) == ""){
		var url=getRootPath()+"/im/column/infoColumnAddOrModifyShow.action";
		$("#infoColumnAddOrModify").load(url,null,column.showAddcolumnDivCallBack);
//	} else {
//		column.showAddcolumnDivCallBack();
//	}
};
//新建页面回调方法
column.showAddcolumnDivCallBack=function(){
	column.clearForm();
	hideErrorMessage();
	$("#title").html("新增");
	column.initColumn(null,$("#columnShowForm #columnId").val());
	$("#columnForm #quene").empty();
	$("#columnForm #quene").html("<option>-请选择-</option>");
	workflow.getFlowsByIden("infoOrgType","selectPI");
	$("#add-column").modal("show");
	// 切换所属栏目触发事件来确定排序下拉的数据
	$('#columnParentId').change(function(){
		$('#columnParentId option:selected').each(function(){
			var columnLevelVal = $(this).attr('columnLevel');// 获取被选中栏目的级别 
			$("#columnForm #columnLevel").val(parseInt(columnLevelVal)+1);
			var idVal = $(this).val();// 获取被选中栏目的id值
			column.getChildrenColumn($("#id").val(),idVal,null,null,columnLevelVal,3);
		});
	});
	$("#checkerTree").deptAndPersonControl(opts);
	$("#saveColumn").click(column.saveColumn);// 新增
	$("#closeColumn").click(column.closeColumn);// 关闭
	
	ie8StylePatch();
}

/**
 * 保存栏目
 */
column.saveColumn = function(){
	var columnParentId=$("#columnParentId").val();
	var columnNameTmp=document.getElementsByName("columnName");
	var workflowsTmp=$("#workflows").val();
	if(workflowsTmp==null||workflowsTmp==""||columnNameTmp==null||columnNameTmp==""||columnParentId==null||columnParentId==""){
		$("#columnForm").valid();
		return;
	}
	if(!DeleteCascade.syncCheckExist("parentColumnId",columnParentId)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_015")
		});
		return;
	}else{
		if(column.subState)return;
		column.subState = true;
		if ($("#columnForm").valid()) {
			var url = getRootPath()+"/im/column/save.action";
			if ($("#id").val() != 0) {
				url = getRootPath()+"/im/column/update.action";
			}
			var formData = $("#columnForm").serializeArray();
			formData.push({"name": "checkerRange", "value": opts['echoData']});
			jQuery.ajax({
				url : url,
				type : 'POST',
				contentType: "application/json;charset=UTF-8",// 一对多关系必填否则去掉
				data : JSON.stringify(serializeJson(formData)),
				success : function(data) {
					column.subState = false;
					if(data.success == "true"){
//						getToken();
						msgBox.tip({
							content: $.i18n.prop("JC_SYS_001"), 
							type:'success'
						});
						$("input[type=reset]").trigger("click");
						var o = data.column;
						//重新加载树，以便排序
						column.tree();
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var nodes = treeObj.getNodeByParam("id", o.columnParentId, null);
						treeObj.selectNode(nodes, false);
						nodes.checkState = true;

						treeObj.expandNode(nodes, true, false);
						treeObj.setting.callback.onClick(null, nodes.id, nodes);
						column.columnList();
						$('#add-column').modal('hide');
						if($("#modifyFlag").val()=="1"){//清空修改标识
							$("#modifyFlag").val("");
						}
					} else {
						if(data.labelErrorMessage != null && data.labelErrorMessage != ""){
							msgBox.info({
								content: data.labelErrorMessage, 
								type:'fail'
							});
						} else {
							msgBox.tip({
								content: data.errorMessage, 
								type:'fail'
							});
						}
					}
				},
				error : function() {
					column.subState = false;
					msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
				}
			});
		}else{
			column.subState = false;
		}
	}
}

/**
 * 显示修改部门DIV
 */
column.updateColumnShow = function(columnId){
	if(columnId==""){
		msgBox.info({
			content: '请选择要修改的栏目', 
			type:'fail'
		});
		return;
	}
//	if($.trim($("#infoColumnAddOrModify").html()) == ""){
	var url=getRootPath()+"/im/column/infoColumnAddOrModifyShow.action?id="+columnId;
	$("#infoColumnAddOrModify").load(url,null, function(){
		column.showUpdateColumnCallBack(columnId);
	});
//	} else {
//		column.showUpdateColumnCallBack(columnId);
//	}
}
//编辑按键回调
column.showUpdateColumnCallBack=function(columnId){
	
	workflow.getFlowsByIden("infoOrgType","selectPI");
//	var url = getRootPath()+"/im/column/get.action";
//	var params = {
//		time: new Date(),
//		id: columnId
//	};
//	$.ajax({
//		url: url,
//        type: 'post',
//        data: params,
//        async: false,
//        success: function(data) {
	var columnJson=$("#columnJson").val();
	var data=eval("("+columnJson+")");
        	$("#columnForm").fill(data);
        	$("#columnNameTmp").val(data.columnName);//用于编辑时去掉当前编辑的栏目 
        	if(data.isPublic == '1'){
				$('#columnForm #isPublic').prop("checked",true);
			}else{
				$('#columnForm #isnoPublic').prop("checked",true);
			}
        	if(data.columnPosition == '1'){
				$('#columnForm #after').prop("checked",true);
			}else{
				$('#columnForm #before').prop("checked",true);
			}
        	// 显示查看范围
        	opts['echoData'] = [];
			 if(data.checkerRange) {
				 for(var i = 0; i < data.checkerRange.length; i++) {
					 opts['echoData'].push({id:data.checkerRange[i]['id'],text:data.checkerRange[i]['text'],type:data.checkerRange[i]['type']});
				 }
			 }  
			 $("#checkerTree").empty();
			 $("#checkerTree").deptAndPersonControl(opts);
			 $("#oldQuene").val(data.quene);
			 $("#oldLevel").val(data.columnLevel);
			 $("#oldParentId").val(data.columnParentId);
			 //重新加载所属栏目
			 column.initColumn(data.id,data.columnParentId,data.quene,data.columnPosition);
			 
			 //-----Xuweiping 2014.10.20 ---修改时切换所属栏目不自动更新排序下拉数据---begin-------
			 // 切换所属栏目触发事件来确定排序下拉的数据
				$('#columnParentId').change(function(){
					$('#columnParentId option:selected').each(function(){
						var columnLevelVal = $(this).attr('columnLevel');// 获取被选中栏目的级别 
						$("#columnForm #columnLevel").val(parseInt(columnLevelVal)+1);
						var idVal = $(this).val();// 获取被选中栏目的id值
						column.getChildrenColumn($("#id").val(),idVal,null,null,columnLevelVal,3);
					});
				});
				//-------end-------
				
			 //判断选择了哪个流程
			 if(data.piId != null && data.piId != 'undefined'){
					$('#columnForm #workflows option').each(function(ii,oo){
						if($(this).val() == data.piId){
							$(this).prop("selected",true);
						}else{
							$(this).prop("selected",false);
						}
					});
				}
			hideErrorMessage();		
			$("#oldName").val(data.columnName);
			$("#modifyFlag").val("1");//标识为修改状态
        	$("#title").html("编辑");
        	$("#add-column").modal("show");
      	  	$("#saveColumn").click(column.saveColumn);// 新增
      	  	$("#closeColumn").click(column.closeColumn);// 关闭
      	  	ie8StylePatch();
//        },error:function(){
//        	msgBox.info({content: $.i18n.prop("JC_SYS_004"), type:'fail'});
//        }
//    });
}
/*** 获取所有的栏目，加载到“所属栏目”下**/
column.initColumn = function(id,parentId,quene,columnPosition){
	var queryColumnUrl = getRootPath()+"/im/column/initColumn.action";
	$.getJSON(queryColumnUrl, function(data) {
		$("#columnForm #columnParentId").empty();
		var option = "";
		var select = $("#columnForm #columnParentId");
		if($("#modifyFlag").val()=="1"){//编辑时去掉当前编辑的栏目
			var num;
			$.each(data.columnList, function(i, o) {
				if(o.columnName==$("#columnNameTmp").val()){
					num=i;
				}
			});
			data.columnList.splice(num,1);//去掉当前编辑的栏目
		}
		select.append("<option value=''>-请选择-</option>");
		$.each(data.columnList, function(i, o) {
			var str="";
			for(var m=0;m<parseInt(o.columnLevel);m++ ){
				str += "&nbsp;&nbsp;";
			}
			var op = $("<option quene='"+o.quene+"' columnLevel='"+o.columnLevel+"' class='"+o.id+"'>"+str+o.columnName+"</option>");
			op.val(o.id);

			if(o.columnParentId == 0){
				select.append(op);
			}else{
				var p = $("."+o.columnParentId);
				p.after(op);
			}
		});
		if(parentId != null && parentId != ""){
			$('#columnForm #columnParentId option').each(function(ii,oo){
				if($(this).val() == parentId){
					$(this).prop("selected",true);
					$("#columnLevel").val(parseInt($(this).attr('columnLevel'))+1);
					column.getChildrenColumn(id,parentId,quene,columnPosition,parseInt($(this).attr('columnLevel')),3);
				}else{
					$(this).prop("selected",false);
				}
			});
		}else{
			$("#columnLevel").val(parseInt($(this).attr('columnLevel'))+1);
		}
	});
}
/**传入参数：id:当前栏目id，parentId:当前栏目父id，quene：当前栏目的序号值，columnPosition：当前栏目的 前后值，columnLevel：当前栏目的级别，myOrderBy：决定使用哪个排序字段（祥见map.xml文件）**/
column.getChildrenColumn = function(id,parentId,quene,columnPosition,columnLevel,myOrderBy){
	//获取所属栏目下的子栏目
	var url = getRootPath()+"/im/column/initColumn.action";
	$.ajax({
		url: url,
	    type: 'post',
	    data: {id:id,columnParentId:parentId,columnLevel:columnLevel,myOrderBy:myOrderBy},
	    async: false,
	    success: function(data) {
			var option = "";
			$("#quene").empty();
			//如果所属栏目下没有子栏目，则quene的值为1
			if(data.columnList.length == 0){
				$("#columnForm #quene").append("<option value='1'>-请选择-</option>");
				$("#columnForm #before").prop("checked",true);
			}else{
				$.each(data.columnList, function(i, o) {
					if( i ==data.columnList.length -1){
						option += "<option value='"+o.quene+"' columnCompareId='"+o.id+"' selected='selected'>" + o.columnName+"</option>";
						$("#columnLevel").val(parseInt(columnLevel)+1);
						$("#columnCompareId").val(o.id);
					}else{
						option += "<option value='"+o.quene+"' columnCompareId='"+o.id+"' >" + o.columnName+"</option>";
					}
				});
				$("#columnForm #quene").append(option);
			}
			var selected = 0;
			if(quene != null && quene != 'undefined'){
				$('#columnForm #quene option').each(function(ii,oo){
					if(columnPosition != null && columnPosition !='undefined'){
						if(columnPosition == "0"){
							if(parseInt($(this).val()) == parseInt(quene)+1){//通过序号加1，定位到后一个栏目
								selected = parseInt($(this).val());
								$("#columnLevel").val(parseInt(columnLevel)+1);
								$("#before").prop("checked",true);
							}
						}
						if(columnPosition == "1"){
							if(parseInt($(this).val()) == parseInt(quene)-1){//通过序号减1，定位到前一个栏目
								selected = parseInt($(this).val());
								$("#columnLevel").val(parseInt(columnLevel)+1);
							}
						}
					}
				});
				if(selected == 0){
					$('#columnForm #quene').val(1);//使被定位到的栏目被选中
				}else{
					$('#columnForm #quene').val(selected);//使被定位到的栏目被选中
				}
			}
	    }
	});
}

/**
 * 删除栏目
 */
column.deleteColumn = function (id) {
	var ids = String(id);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		msgBox.tip({
			content: $.i18n.prop("JC_SYS_061"),
			type:"fail"
		});
		return;
	}
	if(!DeleteCascade.syncCheckCanBatchDelete("columnId",ids)){
		msgBox.info({
			content: $.i18n.prop("JC_OA_COMMON_013")
		});
		return;
	}else{
		msgBox.confirm({
			content: $.i18n.prop("JC_OA_IM_005"),
			success: function(){
				column.deleteCallBack(ids);
			}
		});
	}
};

column.deleteCallBack = function(columnId){

	var url = getRootPath()+"/im/column/deleteByIds.action";
	var params = {
		ids: columnId
	};
	jQuery.ajax({
		url : url,
		type : 'POST',
		data : params,
		success : function(data) {
			if(data.success == "true"){
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_005"),
					type:'success'
				});
				var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
				var node = treeObj.getNodeByParam("id", columnId, null);
				var pNode = node.getParentNode();
				treeObj.removeNode(node, true);
				treeObj.selectNode(pNode, false);
				treeObj.setting.callback.onClick(null, pNode.id, pNode);
			} else {
				if(data.labelErrorMessage != null && data.labelErrorMessage != ""){
					msgBox.info({content: data.labelErrorMessage, type:'fail'});
				} else {
					msgBox.tip({content: $.i18n.prop("JC_SYS_006"), type:'fail'});
				}
			}
		},
		error : function() {
			msgBox.tip({content: $.i18n.prop("JC_SYS_006"), type:'fail'});
		}
	});
}

/**
 * 机构左侧栏目树
 */
column.tree = function(){
	
	function onCheck(id, node){
		var ids = [], nodes = tree.getCheckedNodes(true);
		for(var i=0; i<nodes.length; i++) {
			if(nodes[i].columnType == 1)
				ids.push(nodes[i].id);
		}
		$("#id").val(ids.join(","));
		column.columnList();
	}
	function onClick(event, treeId, treeNode) {
		$("#columnId").val(treeNode.id);
		if($("#columnId").val() == 1 || $("#columnId").val() == ""){///表示选中的树节点是根节点
			$("#editColumn").attr("disabled", true);
			$("#delColumn").attr("disabled", true);
		}else{
			if(treeNode.isParent){//判断是否是父节点（是否有子节点）
				$("#delColumn").attr("disabled", true);
			}else{
				$("#delColumn").attr("disabled", false);
			}
			$("#editColumn").attr("disabled", false);
		}
	    $("#parentId").val(treeNode.id);
	    $("#columnShowForm #columnName").html(treeNode.name); // 栏目名称
		$("#columnShowForm #isPublic").html(treeNode.isPublic == 0 ? "不公开" : "公开");// 是否公开
		
		column.columnList();
	}
	
	function onRemove(event, treeId, treeNode) {
		$("#columnId").val("");
	    $("#columnShowForm #columnName").html(""); // 栏目名称
		$("#columnShowForm #isPublic").html("");// 是否公开
		column.columnList();
	}
	var setting = {
		check:{
			enable: false,// 设置 zTree 的节点上是否显示 checkbox/radio
			nocheckInherit: false,// 是否自动继承父节点属性
			chkStyle: "radio",// 勾选框类型(checkbox 或 radio)
			radioType : "all"// radio的分组范围[setting.check.enable=true且setting.check.chkStyle="radio"时生效]
		},
		view:{
			selectedMulti: false,// 设置是否允许同时选中多个节点
			showLine: true// 设置 zTree 是否显示节点之间的连线
		},
		data:{
			simpleData:{
				enable:true
			}
		},// 确定zTree数据不需要转换为JSON格式,true是需要
		callback:{// 回调函数 其中常用的就是onCheck，onClick
			beforeClick: function(id, node){
				return true;
			},
			// onCheck: onCheck, //用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
			onClick: onClick,	// 用于捕获节点被点击的事件回调函数
			onRemove : onRemove
		}
	};
	var url = getRootPath()+"/im/column/queryColumnTreeForInit.action";
	jQuery.ajax({
		url : url,
		type : 'GET',
		cache: false,
		async : false, 
		success : function(data) {
			var zNodes = [];
			$.each(data, function(i, o) {
				zNodes[i] = {
					id : o.id,
					pId : o.columnParentId,
					name : o.columnName,
					quene : o.quene,
					isPublic: o.isPublic,
					checkState : false
				};
			});
			var tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			tree.expandAll(true);
			var nodes = tree.getSelectedNodes();
			if(nodes != null && nodes.length > 0){
				tree.expandNode(nodes[0], true, false);
				tree.selectNode(nodes[0], false);
				setting.callback.onClick(null, nodes[0].id, nodes[0]);
			}
		}
	});
}
// 清空表单
column.clearForm = function () {
	$('#columnForm').find("input[type=text]").val("");
	$('#id').val("");
	opts['echoData'] = [];
	$("#checkerTree").empty();
	$("#checkerTree").deptAndPersonControl(opts);
	$('#columnForm')[0].reset();
	$('#columnForm').find("textarea").val("");
};

function selectControlCallback(data) {
	// type = 1 人员，type = 2 组织
	opts['echoData'] = [];
	if(data && data.length > 0) {
		opts['echoData'] = data;
	}
}
var opts = {
		widgetId : "aaa",							// 控件ID
		widgetName : "bbb",		
		// 回显数据，没有可以不写
		echoData: [],
		callbackFunction: selectControlCallback
	};
column.closeColumn=function(){
	if($("#modifyFlag").val()=="1"){//清空修改标识
		$("#modifyFlag").val("");
	}
	$('#add-column').modal('hide');
};
/**
 * 初始化
 */
jQuery(function($) {
//	workflow.getFlowsByIden("infoOrgType","selectPI");
//	if($("select[name=workflows] option").size()<=1){
	if(workflow.isHasFlowsByIden("infoOrgType","selectPI")){
		loadrightmenu('/im/info/infoColumnSelect.action');
		return false;
	}else{
		column.pageCount = TabNub > 0 ? TabNub : column.pageCount;
		$("#newColumn").click(column.showAddcolumnDiv);// 显示新增界面
		$("#newColumn-foot").click(column.showAddcolumnDiv);// 显示新增界面
		$("#updateColumn").click(column.updateColumn);// 修改
		column.tree();
		
		column.columnList();
		
		//为流程ID选择时添加事件，来显示可发布人范围
		/*$('#selectPI').on('change','#workflows',function(){
			$('#workflows option:selected').each(function(){
				var piIdVal = $(this).val();// 获取被选中流程的id值
				// 获取所属栏目下的子栏目
				var url = getRootPath()+"/im/column/getPublisherRange.action";
				$.ajax({
					url: url,
			        type: 'post',
			        data: {flowId:piIdVal},
			        dataType : "json",
			        success: function(data) {	
						$("#publisherRange").val(data.userNames);
			        }
				});
			});
		});*/
	}
});
// 便于chrome中js调试
//@ sourceURL=infoColumn.js
