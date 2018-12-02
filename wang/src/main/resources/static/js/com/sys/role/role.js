var role = {};
var roleButton = {};
role.otherDeptCount = 1;
role.pageRows = null;
//重复提交标识
role.subState = false;


//删除角色
roleButton.deleteRole = function (id,personNum) {
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
			type:"fail",
			content:"请选择要删除的数据"
		});
		return;
	}
	
	if(personNum > 0){
		msgBox.confirm({
			content: $.i18n.prop("JC_SYS_072"),
			fontSize : 's',
			success: function(){
				role.deleteCallBack(ids);
			}
		});
	}else{
		msgBox.confirm({
			content: $.i18n.prop("JC_SYS_034"),
			success: function(){
				role.deleteCallBack(ids);
			}
		});
	}
	
};

//删除用户回调方法
role.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/role/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				msgBox.tip({
					type:"success",
					content:"删除成功"
				});
				role.roleList();
			}
		}
	});
};

//获取修改信息
roleButton.get = function (id) {
	//getToken();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/sys/role/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				$("#roleForm").fill(data);
				$("#roleForm #nameOld").val(data.name);
				$("#savaAndClose").addClass("dark");
				$("#savaAndClose").html("保  存");
				$("#roleClickType").html("编辑");
				//填充权限
				$("#savaAndKeep").hide();
				$('#myModal-edit').modal('show');
			}
		}
	});
};

//分页处理 start
//分页对象
role.oTable = null;
//显示列信息
role.oTableAoColumns = [
    //不需要排序的列直接用mData function方式
	{mData: "name"},
	{mData: "deptName"},
	{mData: "personNum"},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtons(source);
	}}
 ];


//分页查询用户
role.roleList = function () {
	//组装后台参数
	role.oTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(role.oTable, aoData);
		//组装查询条件
		var deptIds = $("#roleListForm #deptIds").val();
		if(deptIds.length > 0 && deptIds != -100){
			aoData.push({ "name": "deptIds", "value": deptIds});
		}else{
			aoData.push({ "name": "deptIds", "value": -100});
		}
		
	};
	//table对象为null时初始化
	if (role.oTable == null) {
		role.oTable = $('#roleTable').dataTable( {
			iDisplayLength: role.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/sys/role/manageList.action",
			fnServerData: oTableRetrieveData,//查询数据回调函数
			aoColumns: role.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				role.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [3]}],
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
		} );
	} else {
		//table不为null时重绘表格
		role.oTable.fnDraw();
	}
	

};

//加载授权DIV
roleButton.loadUpdateAuthorizeHtml = function (id){
	if(true){
		$("#roleAuthorize").load(getRootPath()+"/sys/role/roleAuthorize.action",null,function(responseTxt,statusTxt,xhr){
			if(statusTxt=="success"){
				roleButton.showAuthorize(id);
			}
		});
	}
	else{
		roleButton.showAuthorize(id);
	}
};


//显示授权页面
roleButton.showAuthorize = function(roleId){
	selectControl.init("","name1-name", true, true);
	selectControl.openPerson("userSelectDiv");
	roleAuthorize.initRoleMenusForm();
	$('#myModal-authroize').modal('show');
	$('#roleMenusForm #id').val(roleId);
	$('#roleMenusForm #roleId').val(roleId);
	var menuzTree = new menuTree(roleId);
	menuzTree.show("menuTreeDiv");
	var deptzTree = new deptTree();
	deptzTree.show("deptTreeDiv",'','checkbox',true);
	$.ajax({
		type : "post",
		url : getRootPath()+"/sys/role/getMenusByRole.action",
		data : {"roleId": roleId},
		dataType : "json",
		async:false,
		success : function(data) {
			if (data.length > 0) {
				$.each(data, function(i, o) {
					var treeObj = $.fn.zTree.getZTreeObj("menuTreeDiv");
					var node = treeObj.getNodeByParam("id", o.menuId);
					if(node != null){
						treeObj.checkNode(node, true, false);
					}
				
				});
			}else{
				roleAuthorize.showOrHideWeight({"value":1});
			}
			    $.ajax({
					type : "post",
					url : getRootPath()+"/sys/role/getExtsByRole.action",
					data : {"roleId": roleId},
					dataType : "json",
					success : function(data) {
						if (data.length > 0) {
							roleAuthorize.showOrHideWeight({"value":data[0].permissionType});
							if(data[0].permissionType == '4'){
								$.ajax({
									type : "post",
									url : getRootPath()+"/sys/role/getBlcoksByRole.action",
									data : {"roleId": roleId},
									dataType : "json",
									success : function(data) {
										if (data.length > 0) {
											$.each(data, function(i, o) {
												var treeObj = $.fn.zTree.getZTreeObj("deptTreeDiv");
												var node = treeObj.getNodeByParam("id", o.deptId);
												if(node != null && o.isChecked == '1'){
													treeObj.checkNode(node, true, false);
												}
											});
										}
									}
								});
							}
							$("#roleBlocksForm").fill(data[0]);
						}
					}
			    });
				$("#roleMenusForm").fill(data[0]);
				
			
		}
	});
	
	jQuery.ajax({
		url : getRootPath()+"/sysUserRole/getUserIdByRoleId.action?time=" + new Date(),
		async : false,
		type : 'POST',
		data : {roleId:roleId},
		success : function(echoData) {
			$("input:checkbox:checked").each(function(i){
				this.checked = false;
			})
			$("select[name='backValue']").empty();
			
			var inputs = $("#openPersonDiv input[type='checkbox']");
			var arrayAllCheckbox = [];	//每组全选按钮对象
			var checkboxArray = [];		//用来存放checkbox对象。
			for(var i=0;i<inputs.length;i++){
				var obj = inputs[i];
				if(obj.type=='checkbox'){
					if(obj.value != "on"){
						checkboxArray.push(obj);
					}else{
						arrayAllCheckbox.push(obj);
					}
				}
			}
			
			var len = [];	
			for(var i=0;i<echoData.length;i++){
				var id = echoData[i].userId;
				for(var j=0;j<checkboxArray.length;j++){
					var cbValue = checkboxArray[j].value.split(",");
					if(id == cbValue[0]){
						checkboxArray[j].checked = true;
						$("#openPersonDiv #backValue").append("<option value='"+cbValue[0]+"'>"+cbValue[1]+"</option>");
					}else{
						len.push(checkboxArray[j]);
					}
				}
				checkboxArray = len;
				len = [];
			}
			
			var k;
			for(var i=0;i<arrayAllCheckbox.length;i++){
				k=0;
				var arrayAllCheckboxId = arrayAllCheckbox[i].id;
				var array = $("#openPersonDiv input[name='"+arrayAllCheckboxId+"']");
				for(var j=0;j<array.length;j++){
					if(array[j].checked){
						k++;
					}
				}
				if(k == array.length){
					arrayAllCheckbox[i].checked = true;
				}
			}
			
			$.each(echoData,function(i,o){
				//$("#"+o.userId).click();
				//selectControl.selectUserDept(o.roleId,o.roleId + "," + o.roleName,o.deptName);
			})
		},
		error : function() {
		}
	});
	//getToken();
}





role.organizationData = function(treeObj,pId,roleBlocks){
	var node = treeObj.getNodeByParam("id", pId);
	if(node == null)return;
	var nodeId = node.id;
	var pId = node.pId;
	for(var i = 0 ; i < roleBlocks.length ; i ++){
		var flag = true;
		if(roleBlocks[i].deptId == nodeId){
			flag = false;
		}
		if(flag){
			roleBlocks.push({"roleId":$('#roleMenusForm #roleId').val(),"deptId":nodeId,"isChecked":"0"}); //获取选中节点的值
		}
	}
	role.organizationData(treeObj,pId,roleBlocks);
}


//加载添加DIV
role.loadAddHtml = function (){
	if($.trim($("#roleEdit").html()) == ""){
		$("#roleEdit").load(getRootPath()+"/sys/role/roleEdit.action",null,function(){
			role.showAddDiv();
			ie8StylePatch();
			});
	}
	else{
		role.showAddDiv();
	}
};

//显示添加用户弹出层
role.showAddDiv = function (){
	roleEdit.initForm();
	var deptIds = $("#roleListForm #deptIds").val();
	var deptId = $("#roleListForm #deptId").val();
	var deptArray = deptIds.split(",");
	if(deptArray.indexOf(deptId) == -1){
		msgBox.tip({
			type:"fail",
			content:"您无权限在此组织下建立角色"
		});
		return;
	}
	
	
	if($('#roleListForm #deptId').val() == ''){
		msgBox.tip({
			type:"fail",
			content:"请选择组织"
		});
		return;
	}
	
	//getToken();
	$("#roleForm #nameOld").val('');
	$("#id").val(0);
	$("#roleClickType").html("新增");
	$("#savaAndClose").html("保存退出");
	$("#savaAndClose").removeClass("dark");
	$("#savaAndKeep").html("保存继续");
	$("#savaAndKeep").show();
	$("#saveOrModify").show();
	$('#myModal-edit').modal('show');
	
	var deptId = $('#roleListForm #deptId').val();
	var deptName = $('#roleListForm #deptName').val();
	//如果是部门同步到添加页面
	if(deptId > 0 && deptName != ""){
		$('#roleForm #deptId').val(deptId);
		$('#roleForm #deptName').val(deptName);
	}
};

//加载修改div
roleButton.loadUpdateHtml = function (id){
	if($.trim($("#roleEdit").html()) == ""){
		$("#roleEdit").load(getRootPath()+"/sys/role/roleEdit.action",null,function(){
			roleButton.get(id);
			ie8StylePatch();
			});
		
	}
	else{
		roleButton.get(id);
	}
};


//初始化
jQuery(function($) 
		{
	//计算分页显示条数
	role.pageRows = TabNub>0 ? TabNub : 10;
	$("#showAddDiv").click(role.loadAddHtml);
	$("#showAddDivS").click(role.loadAddHtml);
	
	$("#deleteRoles").click("click", function(){roleButton.deleteRole(0);});
	
	roleDeptTree.show("treeDemo",role.roleList);
	
});