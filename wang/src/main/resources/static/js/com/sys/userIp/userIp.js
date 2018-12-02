var userIp = {}, pageCount=10;
  	
//重复提交标识
userIp.subState = false;
//分页处理 start
//分页对象
userIp.oTable = null;

//显示列信息
//常用词
userIp.oTableAoColumns = [
	//不需要排序的列直接用mData function方式
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{ "mData": "userId", mRender : function(mData, type, full) {
		return full.userName;
	}},
	{ "mData": "deptName" },
	{ "mData": "userStartIp", mRender : function(mData, type, full){
		if(full.userEndIp != '')
			return full.userStartIp+"-"+full.userEndIp;
		else 
			return full.userStartIp;
	}},
	{ "mData": "modifyDate" },
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtonsUserIp(source);
	}}
];

userIp.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(userIp.oTable, aoData);
	
	var userId = $("#userIpListForm #user-userId").val();
	//var deptName = $("#userIpListForm #deptName").val();
	
	if(userId > 0){
		aoData.push({ "name": "userId", "value": userId});
	}
//	if(deptName != ""){
//		aoData.push({ "name": "deptName", "value": $.trim(deptName)});
//	}
};

//分页查询
userIp.userIpList = function () {
	if (userIp.oTable == null) {
		userIp.oTable = $('#userIpTable').dataTable( {
			"iDisplayLength": userIp.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/userIp/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": userIp.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				userIp.oTableFnServerParams(aoData);
			},
			aaSorting:[]//设置表格默认排序列
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,5]}
	        ]
		} );
	} else {
		userIp.oTable.fnDraw();
	}
};
	
//处理缓存问题 清空form表单中值
userIp.clearForm = function(form){	    	  
	   $(':input', form).each(function() { 
	     var type = this.type;
	     var tag = this.tagName.toLowerCase(); // normalize case 
	     if (type == 'text' || type == 'password' || tag == 'textarea') 
	       this.value = "";
	     //else if (tag == 'select')
	         //  this.selectedIndex = -1;
	   }); 
};


userIp.changetype = function(){
	  var val=$('input:radio[name="bindType"]:checked').val();
	  if(val == 2){
		  $("#ipz").hide();
		  $("#userIpDiv").show();
	  } else {
		  $("#ipz").show();
		  $("#userIpDiv").hide();
		
	  }
};


//加载添加DIV
userIp.loadAddHtml = function (){
	if($.trim($("#userIpEdit").html()) == ""){
		$("#userIpEdit").load(getRootPath()+"/sys/userIp/userIpEdit.action",null,userIp.createuserIp);
	}
	else{
		userIp.createuserIp();
	}
};

//新增弹出对话框方法
userIp.createuserIp = function(){
	hideErrorMessage();
	$("#id").attr("value","");
	userIp.clearForm(userIpForm);
	selectControl.init("userFormTree","add-userId", false, true);
	$("input[name=bindType]").get(0).checked = true;
	$("#titleuserIp").html("新增");
	userIp.changetype();
	$('#myModal-edit').modal('show');
	ie8StylePatch();
};
		 		
//加载修改div
userIp.loadUpdateHtml = function (id){
	if($.trim($("#userIpEdit").html()) == ""){
		$("#userIpEdit").load(getRootPath()+"/sys/userIp/userIpEdit.action",null,function(){userIp.get(id);});
	}
	else{
		userIp.get(id);
	}
};

//修改弹出对话框方法
userIp.get = function(id){
	  hideErrorMessage();
  	  jQuery.ajax({
            url: getRootPath()+"/sys/userIp/get.action?id="+id+"&time="+new Date(),
            type: 'post',
            dataType: 'json',
            success: function(data, textStatus, xhr) {
              userIp.clearForm(userIpForm);
              $("#userIpForm #userId").val(data.userId);
          	  $("#userIpForm").fill(data);
          	  selectControl.init("userFormTree","add-userId", false, true, null, {id:data.userId,text:data.userName});
          	  if(data.bindType == 1)
          		$("input[name=bindType]").get(0).checked = true;
          	  else
          		$("input[name=bindType]").get(1).checked = true;
          	  $("#titleuserIp").html("编辑");
          	  userIp.changetype();
          	  $('#myModal-edit').modal('show');
          	  ie8StylePatch();
            },error:function(){
          	 // alert("加载数据错误。");
          	  msgBox.tip({content: "加载数据错误", type:'fail'});
            }
       });
};

//删除绑定IP
userIp.deleteuserIp = function (id) {
	var ids = String(id);
	if (id == 0) {
		var idsArr = [];
		$("[name='ids']:checked").each(function() {
			idsArr.push($(this).val());
		});
		ids = idsArr.join(",");
	}
	if (ids.length == 0) {
		//alertx("请选择要删除的常用词");
		msgBox.info({content: "请选择要删除的绑定IP", type:'fail'});
		return;
	}
	confirmx($.i18n.prop("JC_SYS_034"),function(){userIp.deleteCallBack(ids);});
};

//删除常用词回调方法
userIp.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/userIp/deleteByIds.action?time=" + new Date(),
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				//alertx("删除成功");
				msgBox.tip({content: "删除成功", type:'success'});
				userIp.userIpList();
			}
		}
	});
};

userIp.queryReset = function(){
	$('#userIpListForm')[0].reset();
	selectControl.clearValue("user-userId");
};

//初始化
jQuery(function($) 
{
	//计算分页显示条数
	userIp.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryuserIp").click(userIp.userIpList);
	$("#queryReset").click(userIp.queryReset);
	$("#userIpTop").click(userIp.loadAddHtml);
	$("#userIpBottom").click(userIp.loadAddHtml);
	$("#deleteuserIps").click("click", function(){userIp.deleteuserIp(0);});
	//初始化列表方法 
	userIp.userIpList();    	
	selectControl.init("userTree","user-userId", false, true);
});	


