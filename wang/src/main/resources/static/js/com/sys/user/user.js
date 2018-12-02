var user = {};

user.pageRows = null;

//初始化密码
user.initPassword = function(id) {
	confirmx($.i18n.prop("JC_SYS_093"),function(){
		jQuery.ajax({
			url : getRootPath()+"/sys/user/initPassword.action",
			type : 'POST',
			data : {"id": id},
			success : function(data) {
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage
					});
				} else {
					msgBox.tip({
						content: data.errorMessage
					});
				}
			},
			error : function() {
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_091")
				});
			}
		});
	});
};

//删除用户
user.deleteUser = function (id) {
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
			content: $.i18n.prop("JC_SYS_061")
		});
		return;
	}
	msgBox.confirm({
		content: $.i18n.prop("JC_SYS_034"),
		success: function(){
			user.deleteCallBack(ids);
		}
	});
};

//删除用户回调方法
user.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/user/deleteByIds.action",
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				msgBox.tip({
					type:"success",
					content: data.successMessage
				});
			} else {
				msgBox.info({
					content: data.errorMessage
				});
			}
			user.userList();
		}
	});
};

//分页处理 start
//分页对象
user.oTable = null;

//分页查询用户
user.userList = function () {
	$('#IP-edit').fadeIn();
	
	//显示列信息
	user.oTableAoColumns = [
	    //不需要排序的列直接用mData function方式
		{mData: function(source) {
				if(!source.isSystemAdmin){
					if(source.status != "status_3"){
						return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
					} else {
						return null;
					}
				} else {
					return null;
				}
			}
		},
		{mData: "displayName"},
		//需要排序列需要指定属性mData
		{mData: "sex", mRender : function(mData, type, full) {
				return full.sexValue;
			}
		},
		{mData: "deptName"},
		{mData: "dutyId", mRender : function(mData, type, full){
				return full.dutyIdValue;
			}
		},
		{mData: "status", mRender : function(mData, type, full){
				return full.statusValue;
			}
		},
		//设置权限按钮
		{mData: function(source) {
			return userOTableSetButtons(source);
		}}
	 ];
	
	//组装后台参数
	user.oTableFnServerParams = function(aoData){
		//排序条件
		getTableParameters(user.oTable, aoData);
		//组装查询条件
		var deptIds = $("#userListForm #deptIds").val();
		var sex = $("#userListForm #sex").val();
		var status = $("#userListForm #status").val();
		var displayName = $("#userListForm #displayName").val();
		
		if(deptIds.length > 0){
			aoData.push({ "name": "deptIds", "value": deptIds});
		}
		if(sex !=null && sex != ""){
			aoData.push({ "name": "sex", "value": sex});
		}
		if(status !=null && status != ""){
			aoData.push({ "name": "status", "value": status});
		}
		if(displayName != ""){
			aoData.push({ "name": "displayName", "value": displayName});
		}
	};
	
	user.oTableRetrieveData = function ( sSource, aoData, fnCallback ) {
	    $.ajax({
			type : "GET",
			url : sSource,
			data : aoData,
			dataType : "json",
			success : function(resp) {
				fnCallback(resp);
				//重新绑定编辑和删除提交框
				$("i[data-toggle='tooltip']").tooltip();
				treeHightReset("treeDemo");
				ie8TableStyle();
				//判断操作列没有按钮时隐藏操作列 没有操作列不需要添加此方法
				//setColumnVis(user.oTable, 6);
			}
		});
	};
	
	//table对象为null时初始化
	if (user.oTable == null) {
		user.oTable = $('#userTable').dataTable( {
			iDisplayLength: user.pageRows,//每页显示多少条记录
			sAjaxSource: getRootPath()+"/sys/user/manageList.action",
			fnServerData: user.oTableRetrieveData,//查询数据回调函数
			aoColumns: user.oTableAoColumns,//table显示列
			fnServerParams: function ( aoData ) {//传参
				user.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//fnDrawCallback: rememberPage,
			//默认不排序列 bSortable: false, aTargets: [0,6] 表格默认第一列和第7列不进行排序
	        aoColumnDefs: [{bSortable: false, aTargets: [0,6]}],
		} );
		
	} else {
		//table不为null时重绘表格
		user.oTable.fnDraw();
	}
};

//分页处理 end
user.queryReset = function(){
	$('#userListForm #status').val('');
	$('#userListForm #sex').val('');
	$('#userListForm #displayName').val('');
	deptTreeToPage.cancelCheck();
};

//加载添加DIV
user.loadAddHtml = function (){
	if($.trim($("#userEdit").html()).length == 0){
		$("#userEdit").load(getRootPath()+"/sys/user/userEdit.action",null,function(){userEdit.showAddDiv();});
	}
	else{
		userEdit.showAddDiv();
	}
};

//加载修改div
user.loadUpdateHtml = function (id){
	if($.trim($("#userEdit").html()).length == 0){
		$("#userEdit").load(getRootPath()+"/sys/user/userEdit.action",null,function(){userEdit.get(id);});
	}
	else{
		userEdit.get(id);
	}
};


//初始化
jQuery(function($) 
{
	//计算分页显示条数
	user.pageRows =  10;
	
	$("#queryUser").click(user.userList);
	$("#showAddDiv").click(user.loadAddHtml);
	$("#showAddDiv_t").click(user.loadAddHtml);
	$("#deleteUsers").click("click", function(){user.deleteUser(0);});
	$("#queryReset").click(user.queryReset);
	
	//初始化列表
	deptTreeToPage.show("treeDemo", user.userList);
});