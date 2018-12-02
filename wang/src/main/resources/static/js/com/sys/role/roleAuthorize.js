var roleAuthorize = {};
roleAuthorize.subState = false;


roleAuthorize.saveRoleMenu = function () {
	
	if (roleAuthorize.subState)return;
	roleAuthorize.subState = true;
	if ($("#roleBlocksForm").valid()) {
		var url = getRootPath()+"/sys/role/saveRoleMenu.action?time=" + new Date();
		$("#roleMenusForm #token").val($("#roleForm #token").val());
		var formData = $("#roleMenusForm").serializeArray();
		formData = roleAuthorize.addFormParameters(formData);
		//添加其他表单信息
		jQuery.ajax({
			url : url,
			type : 'POST',
			data : JSON.stringify(serializeJson(formData)),
			contentType: "application/json;charset=UTF-8",
			success : function(data) {
				roleAuthorize.subState = false;
				//getToken();
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content:"保存成功"
					});
					roleAuthorize.initRoleMenusForm();
					$('#myModal-authroize').modal('hide');
					$("#roleForm #token").val(data.token);
					role.roleList();
				} else {
					if(data.labelErrorMessage){
						showErrors("roleForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content:data.errorMessage
						});
					}
				}
			},
			error : function() {
				roleAuthorize.subState = false;
				alertx("保存失败");
			}
		});
	} else {
		roleAuthorize.subState = false;
		msgBox.info({
			content:"表单内容填写不完整"
		});
	}
};

//添加部门、角色、机构表单数据
roleAuthorize.addFormParameters = function (formData){
	//根据实际业务组装json对象
	var roleExts = new Array();
	var roleMenus = new Array();
	var roleBlocks = new Array();
	var roleUsers = new Array();
	var treeObj=$.fn.zTree.getZTreeObj("menuTreeDiv");
    nodes=treeObj.getCheckedNodes(true);
    for(var i=0;i<nodes.length;i++){
    	if( $("#permissionType").val() == '1'){
//    		$("#weightRule").val('');
//    		$("#weight").val('0');
    	}
    	roleMenus.push({"roleId":$('#roleMenusForm #roleId').val(),"menuId":nodes[i].id,"permissionType":$("#permissionType").val()}); //获取选中节点的值
    }
    //组织菜单数据
	formData.push({"name": "roleMenus", "value": roleMenus});
	
	//组织自定义部门数据
	var treeObj=$.fn.zTree.getZTreeObj("deptTreeDiv");
    nodes=treeObj.getCheckedNodes(true);
    for(var i=0;i<nodes.length;i++){
    	roleBlocks.push({"roleId":$('#roleMenusForm #roleId').val(),"deptId":nodes[i].id,"isChecked":"1"}); //获取选中节点的值
    }
	nodes = treeObj.getNodes();
	var childNodes = treeObj.transformToArray(nodes);
	for(var i=0;i<childNodes.length;i++){
		var node = childNodes[i];
		if(node.getCheckStatus().half && !node.getCheckStatus().checked){
			roleBlocks.push({"roleId":$('#roleMenusForm #roleId').val(),"deptId":node.id,"isChecked":"0"}); //获取选中节点的值
		}
	}
    formData.push({"name": "roleBlocks", "value": roleBlocks});
    //族们自定义权限数据
    roleExts.push({"roleId":$('#roleMenusForm #roleId').val(),"permissionType":$("#permissionType").val()});
    formData.push({"name": "roleExts", "value": roleExts});
    
    //组织部门人员数据
	$("select[name='backValue']").find("option").each(function(i, val){
		roleUsers.push({"roleId":$('#roleMenusForm #roleId').val(),"userId":this.value}); //获取选中节点的值
	});
	formData.push({"name": "sysUserRoles", "value": roleUsers});
    
	return formData;
};


roleAuthorize.initRoleMenusForm = function(){
	roleAuthorize.clearRoleMenusForm();
};

roleAuthorize.clearRoleMenusForm = function(){
	$('#roleMenusForm')[0].reset();
	$('#roleBlocksForm')[0].reset();
	hideErrorMessage();
}

roleAuthorize.showOrHideWeight = function(obj){
	if(obj.value == 1){
		$("#weightLayer1").css('display','none');
		$("#weightLayer2").css('display','none');
		$("#deptTreeDiv").css('display','none');
		$("#deptTreeDiv").removeClass("inline");
		$("#deptTreeDiv").addClass("none");
	}else{
		$("#weightLayer1").css('display','');
		$("#weightLayer2").css('display','');
		$("#deptTreeDiv").css('display','none');
		$("#deptTreeDiv").removeClass("inline");
		$("#deptTreeDiv").addClass("none");
		//自定义，此时加载部门树
		if(obj.value == 4){
			$("#deptTreeDiv").css('display','none');
			$("#deptTreeDiv").removeClass("none");
			$("#deptTreeDiv").addClass("inline");
		}
	}
}

jQuery(function($){
	$("#savaRoleMenu").click(function(){roleAuthorize.saveRoleMenu();});
	$("#permissionType").change(function(){roleAuthorize.showOrHideWeight(this);});
	
	$("#closeRoleMenu").click(function(){
		$('#myModal-authroize').modal('hide');
	});
	
});