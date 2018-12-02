var department = {}, departmentUser = {};

department.pageCount = 10;

//重复提交标识
department.subState = false;

//分页处理 start
//分页对象
department.oTable = null;
departmentUser.oTable = null;

//显示列信息
//部门
department.oTableAoColumns = [
	{ "mData": "name" },
	{ "mData": "displayName" },
	{ 
		"mData": "deptType", 
		"mRender" : function(mData, type, full) {
			return full.deptType == 0 ? "部门":"机构";
		}
	},
	{ 
		"mData": "id", 
		"mRender" : function(mData, type, full) {
			return oTableSetButtons(mData, type, full);
		}
	},
	{ 
		"mData": "parentId",
		"bVisible": false 
	}
];

//人员
departmentUser.oTableAoColumns = [
	{ "mData": "displayName" },
	{ "mData": "dutyId", "mRender" : function(mData, type, full) { return full.dutyIdValue; }},
	{ "mData": "level", "mRender" : function(mData, type, full) { return full.levelValue; }},
	{ "mData": "sex", "mRender" : function(mData, type, full) { return full.sexValue; }}
];

//组装后台参数
department.oTableFnServerParams = function(aoData){
	//排序条件、页数
	getTableParameters(department.oTable, aoData);
	//查询条件
	if ($("#deptIds").val().length > 0) {
		aoData.push({ "name": "deptIds", "value": $("#deptIds").val()});
	} 
};

departmentUser.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(departmentUser.oTable, aoData);
	//查询条件
	var deptIds = $("#userDeptId").val();
	if (deptIds.length > 0) {
		aoData.push({ "name": "deptIds", "value": deptIds});
	} 
};

//分页查询
department.departmentList = function () {
	if (department.oTable == null) {
		department.oTable = 
			$('#departmentTable').dataTable({
				"iDisplayLength": department.pageCount,								//每页显示多少条记录
				"sAjaxSource": getRootPath()+"/department/searchManageList.action",	//查询URL
				"fnServerData": oTableRetrieveData,									//查询数据回调函数
				"aoColumns": department.oTableAoColumns,							//table显示列
				"fnServerParams": function ( aoData ) {								//传参
					department.oTableFnServerParams(aoData);
				},
				aaSorting:[],
		        "aoColumnDefs": [													//默认不排序列
					{"bSortable": false, "aTargets": [3,4]}
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
		department.oTable.fnDraw();
	}
};

departmentUser.departmentUserList = function () {
	if (departmentUser.oTable == null) {
		departmentUser.oTable = 
			$('#uTable').dataTable({
				"iDisplayLength": department.pageCount,								//每页显示多少条记录
				"sAjaxSource": getRootPath()+"/department/userManageList.action",	//查询URL
				"fnServerData": oTableRetrieveData,									//查询数据回调函数
				"aoColumns": departmentUser.oTableAoColumns,						//table显示列
				"fnServerParams": function (aoData) {								//传参
					departmentUser.oTableFnServerParams(aoData);
				},
				aaSorting:[],
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
		departmentUser.oTable.fnDraw();
	}
};
//分页处理 end

/**
 * 添加--添加部门html
 */
department.showDeptInsertHtml = function(){
	$("#deptInsertHtml").load(getRootPath()+"/department/showDeptInsertHtml.action", null, department.showAddDepartmentDiv);
}

/**
 * 显示部门添加DIV
 */
department.showAddDepartmentDiv = function(){
	selectControl.init("userInsertDivId","insert-leaderId", false, true);
	$("#departmentForm")[0].reset();
	hideErrorMessage();
	selectControl.clearValue("insert-leaderId");
	if($("#deptId").val()==null || $("#deptId").val()==""){
		msgBox.info({content:$.i18n.prop("JC_SYS_117"),type:'fail'});
		return;
	}
	var params = {
		id: $("#deptId").val()
	};
	$.ajax({
		url: getRootPath()+"/department/queryOne.action",
        type: 'post',
        data: params,
        async: false,
        success: function(data, textStatus, xhr) {
        	if(data.errorMessage != null){
        		msgBox.info({content: data.errorMessage, type:'fail'});
      	  	}else{
      	  		//显示选中部门名称写入部门ID--thisNodeName,thisNodeId
      	  		if(data.deptType == 0){
      	  			$("#departmentForm input[id=deptType][value=1]").attr("disabled", true);
      	  		} else {
      	  			$("#departmentForm input[id=deptType][value=1]").attr("disabled", false);
      	  		}
      	  		if(data.name != null && data.name != ""){
      	  			$("#thisNodeName").html(data.name);
      	  			$("#parentId").val(data.id);
      	  		}else{
      	  			//目前没有用到
      	  			$("#thisNodeName").html("无上级组织");
      	  		}
      	  		$("#add-dept").modal("show");
      	  		ie8StylePatch();
      	  	}
        },error:function(){
        	msgBox.info({content: $.i18n.prop("JC_SYS_116"), type:'fail'});
        }
    });
};

/**
 * 修改--添加部门html
 */
department.showDeptEditHtml = function(deptId){
	$("#deptEditHtml").load(getRootPath()+"/department/showDeptEditHtml.action", null, function(){department.updateDepartment(deptId)});
}

/**
 * 显示修改部门DIV
 */
department.updateDepartment = function(deptId){
	if(deptId==""){
		msgBox.info({content:$.i18n.prop("JC_SYS_119"), type:'fail'});
		return;
	}
	hideErrorMessage();
	var url = getRootPath()+"/department/queryOne.action";
	var params = {
		id: deptId
	};
	$.ajax({
		url: url,
        type: 'post',
        data: params,
        async: false,
        success: function(data, textStatus, xhr) {	
        	$("#departmentUpdateForm #id").val(data.id);
        	$("#departmentUpdateForm #name").val(data.name);
        	$("#departmentUpdateForm #queue").val(data.queue);
        	if(data.parentName != null && data.parentName != ""){
        		$("#departmentUpdateForm #thisNodeName").html(data.parentName);
            	$("#departmentUpdateForm #parentId").val(data.parentId);
  	  		}else{
  	  			$("#departmentUpdateForm #thisNodeName").html("全局组织");
  	  			$("#departmentUpdateForm #parentId").val("");
  	  		}
        	$("#departmentUpdateForm #deptDesc").val(data.deptDesc);
        	selectControl.init("userUpdateDivId","update-leaderId", false, true, null, data.leaderId==null || data.leaderId==0 ?"":{id:data.leaderId,text:data.displayName});
      	  	$("#update-dept").modal("show");
      	  	ie8StylePatch();
        },error:function(){
        	msgBox.info({content: $.i18n.prop("JC_SYS_004"), type:'fail'});
        }
    });
}

/**
 * 删除组织
 */
department.deleteDepartment = function(deptId){
	if(deptId==""){
		msgBox.info({content:$.i18n.prop("JC_SYS_120"), type:'fail'});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"), 
		type: 'fail', 
		success: function(){
			var url = getRootPath()+"/department/logicDeleteById.action";
			var params = {
				id: deptId
			};
			jQuery.ajax({
				url : url,
				type : 'POST',
				data : params,
				success : function(data) {
					if(data.success == "true"){
						msgBox.tip({content: $.i18n.prop("JC_SYS_005"), type:'success'});
						var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
						var node = treeObj.getNodeByParam("id", deptId, null);
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
	});
}

/**
 * 机构左侧组织树
 */
department.tree = function(){
	
	/**
	 * 获取可操作的子节点
	 */
	function getChildNodesId(node){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var childNodes = treeObj.transformToArray(node);
	    var sNodes = "";
	    var aNodes = "";
	    for(var i = 0; i < childNodes.length; i++) {
	    	if(childNodes[i].isChecked == 1){
	    		if(childNodes[i].deptType == 1 && node[0].userType == 1){
	    			sNodes = sNodes + childNodes[i].id + ",";
	    		}else if(childNodes[i].deptType == 0){
	    			aNodes = aNodes + childNodes[i].id + ",";
	    		}
	    	}
	    }
	    if(node[0].userType == 1){
	    	return (sNodes+aNodes).length > 0 ? (sNodes+aNodes).substring(0, (sNodes+aNodes).length-1) : "";
	    }else {
	    	return aNodes.length > 0 ? aNodes.substring(0, aNodes.length-1):"";
	    }
	}
	
	function getChildNodesIdForUser(node){
		var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
		var childNodes = treeObj.transformToArray(node);
	    var nodes = "";
	    for(var i = 0; i < childNodes.length; i++) {
	    	if(childNodes[i].isChecked == 1){
	    		nodes = nodes + childNodes[i].id + ",";
	    	}
	    }
	    return nodes.substring(0, nodes.length-1);
	};
	
	function onBeforeClick(id, node){
		if(node.isChecked == 0 && node.pId != null){
			return false;
		}else{
			return true;
		}
	}
	
	function onClick(event, treeId, treeNode) {
		//$("#userDeptId").val(treeNode.id);只查询部门下的人员
	    $("#id").val(treeNode.id);
	    $("#deptName").html(treeNode.name); //部门名称
		$("#leaderName").html(treeNode.leaderName);//负责人
		$("#deptType").html(treeNode.deptType == 0 ? "部门" : "机构");//组织类型
		$("#deptId").val(treeNode.id);
		var nodes = treeNode.children;
		if(nodes != undefined && nodes.length > 0){
			$("#deptIds").val(getChildNodesId(nodes));//获取根节点下所有节点
			$("#userDeptId").val(getChildNodesIdForUser(nodes));
		}else{
			$("#deptIds").val("0");
			$("#userDeptId").val(treeNode.id);
		}
		department.departmentList();
		departmentUser.departmentUserList();
		checkNode(treeNode);
	}
	
	function onRemove(event, treeId, treeNode) {
		$("#deptId").val("");
	    $("#deptName").html(""); //部门名称
		$("#leaderName").html("");//负责人
		$("#deptType").html("");//组织类型
		department.departmentList();
		departmentUser.departmentUserList();
	}
	
	var setting = {
		check:{
			enable: false,//设置 zTree 的节点上是否显示 checkbox/radio
			nocheckInherit: false,//是否自动继承父节点属性
			chkStyle: "radio",//勾选框类型(checkbox 或 radio)
			radioType : "all"//radio的分组范围[setting.check.enable=true且setting.check.chkStyle="radio"时生效]
		},
		view:{
			selectedMulti: false,//设置是否允许同时选中多个节点
			showLine: true//设置 zTree 是否显示节点之间的连线
		},
		data:{
			simpleData:{
				enable:true
			}
		},
		callback:{
			beforeClick: onBeforeClick,
			onClick: onClick,
			onRemove : onRemove
		}
	};
	
	var deptTreeUrl = getRootPath()+"/system/managerDeptTree.action";
	
	$.getJSON(deptTreeUrl, function(data) {
		var zNodes = [];
		$.each(data, function(i, o) {
			zNodes[i] = {
				id : o.id,
				pId : o.parentId,
				name : o.name,
				deptType : o.deptType,
				checkState : false,
				iconSkin : o.deptType==0 ? "fa-flag" : "fa-office",
				leaderName: o.displayName,
				isChecked: o.isChecked,
				userType: o.userType,
				queue: o.queue
			};
		});
		var tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
		//tree.expandAll(true);
		var nodes = tree.getNodes();
		if(nodes != null && nodes.length > 0){
			tree.expandNode(nodes[0], true, false);
			tree.selectNode(nodes[0], false);
			setting.callback.onClick(null, nodes[0].id, nodes[0]);
		}
	});
	
}
/**
 * 检查节点可操作的动作
 * @param node	节点
 */
function checkNode(node){
	if(node.pId == null || node.pId == 0){	//根节点
		$("#delDept").attr("disabled", true);
		if(node.userType == 1){
			$("#newDept").attr("disabled", false);
			$("#newDept-foot").attr("disabled", false);
			$("#editDept").attr("disabled", false);
			$("#departmentForm input[id=deptType][value=1]").attr("disabled", false);
		}else{
			$("#newDept").attr("disabled", true);
			$("#newDept-foot").attr("disabled", true);
			$("#editDept").attr("disabled", true);
		}
	}else{	//子节点
		if(node.deptType == 1){	//机构
			if(node.userType == 1){
				$("#newDept").attr("disabled", false);
				$("#newDept-foot").attr("disabled", false);
				$("#editDept").attr("disabled", false);
				$("#delDept").attr("disabled", false);
				$("#departmentForm input[id=deptType][value=0]").attr("checked", true);
				$("#departmentForm input[id=deptType][value=1]").attr("checked", false);
				$("#departmentForm input[id=deptType][value=1]").attr("disabled", false);
			}else{
				$("#newDept").attr("disabled", false);
				$("#newDept-foot").attr("disabled", false);
				$("#editDept").attr("disabled", true);
				$("#delDept").attr("disabled", true);
				$("#departmentForm input[id=deptType][value=0]").attr("checked", true);
				$("#departmentForm input[id=deptType][value=1]").attr("checked", false);
		  		$("#departmentForm input[id=deptType][value=1]").attr("disabled", true);
			}
		}else{	//部门
			if(node.userType == 1 || node.userType == 2){
				$("#newDept").attr("disabled", false);
				$("#newDept-foot").attr("disabled", false);
				$("#editDept").attr("disabled", false);
				$("#delDept").attr("disabled", false);
				$("#departmentForm input[id=deptType][value=0]").attr("checked", true);
				$("#departmentForm input[id=deptType][value=1]").attr("checked", false);
		  		$("#departmentForm input[id=deptType][value=1]").attr("disabled", true);
			}else{
				$("#newDept").attr("disabled", true);
				$("#newDept-foot").attr("disabled", true);
				$("#editDept").attr("disabled", true);
				$("#delDept").attr("disabled", true);
				$("#departmentForm input[id=deptType][value=0]").attr("checked", true);
				$("#departmentForm input[id=deptType][value=1]").attr("checked", false);
		  		$("#departmentForm input[id=deptType][value=1]").attr("disabled", true);
			}
		}
	}
}

department.tabOrg = function(){
	$("#newDept").show();
}

department.tabUser = function(){
	$("#newDept").hide();
}

/**
 * 初始化
 */
jQuery(function($) {
	department.pageCount = TabNub > 0 ? TabNub : department.pageCount;
	$("#newDept").click(department.showDeptInsertHtml);//显示新增界面
	$("#newDept-foot").click(department.showDeptInsertHtml);//显示新增界面
	$("#tabOrg").click(department.tabOrg);//tab组织
	$("#tabUser").click(department.tabUser);//tab人员
	department.tree();
});