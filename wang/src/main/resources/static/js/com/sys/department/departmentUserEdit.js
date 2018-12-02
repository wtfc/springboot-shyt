var departmentEdit = {};

//重复提交标识
departmentEdit.subState = false;

/**
 * 修改组织
 */
departmentEdit.updateDept = function(){
	if(departmentEdit.subState)	return;
	departmentEdit.subState = true;
	if ($("#departmentUpdateForm").valid()) {
		var url = getRootPath()+"/department/update.action";
		var formData = $("#departmentUpdateForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : formData,
			success : function(data) {
				departmentEdit.subState = false;
				if(data.success == "true"){
					$("#deptToken").val(data.token);
					var leaderName = returnValue("update-leaderId") != null ? returnValue("update-leaderId").split(":")[1] : null;
					msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
					$("input[type=reset]").trigger("click");
					var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
					var nodes = treeObj.getSelectedNodes();
					var node = treeObj.getNodeByParam("id", data.dept.id, null);
					node.name = data.dept.name;
					node.queue = data.dept.queue;
					node.leaderName = leaderName == null ? "" : leaderName;
					treeObj.updateNode(node);
					while(!node.isFirstNode){
						var pNode = node.getPreNode();
						treeObj.moveNode(pNode, node, "prev");
					}
					while(true){
						var nNode = node.getNextNode();
						if(nNode != null){
							if(node.queue > nNode.queue){
								treeObj.moveNode(nNode, node, "next");
							}else{
								break;
							}
						}else{
							break;
						}
					}
					$("#id").val(data.dept.id);
				    $("#deptName").html(data.dept.name); //部门名称
					$("#leaderName").html(leaderName == null ? "" : leaderName);//负责人
					$("#deptType").html(data.dept.deptType == 0 ? "部门" : "机构");//组织类型
					$("#deptId").val(data.dept.id);
					treeObj.selectNode(nodes[0], false);
					treeObj.setting.callback.onClick(null, nodes[0].id, nodes[0]);
					$('#update-dept').modal('hide');
				} else {
					$("#deptToken").val(data.token);
					if(data.errorMessage != null && data.errorMessage != ""){
						msgBox.info({content: data.errorMessage, type:'fail'});
					} else {
						msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
					}
				}
			},
			error : function() {
				departmentEdit.subState = false;
				msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
			}
		});
	}else{
		msgBox.info({content: $.i18n.prop("JC_SYS_118"), type:'fail'});
		departmentEdit.subState = false;
	}
}

/**
 * 初始化
 */
jQuery(function($) {
	$("#updateDept").click(departmentEdit.updateDept);//修改
});