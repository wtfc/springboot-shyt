var portal = {}, pageCount=10;
  	
//分页处理 start
//分页对象
portal.oTable = null;

//显示列信息
//门户
portal.oTableAoColumns = [
	//不需要排序的列直接用mData function方式
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{ "mData": "portalName"},
	{mData: "portalStatus", mRender : function(mData, type, full){
			return full.portalStatusValue;
		}
	},
	{mData: "portalType", mRender : function(mData, type, full){
		return full.portalTypeValue;
	}
	},
	{ "mData": "sequence" },
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtonsportal(source);
	}}
];

portal.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(portal.oTable, aoData);
	
	var portalName = $("#portalListForm #portalName").val();
	var portalStatus = $("#portalListForm #portalStatus").val();
	
	if(portalName != ""){
		aoData.push({ "name": "portalName", "value": $.trim(portalName)});
	}
	if(portalStatus != ""){
		aoData.push({ "name": "portalStatus", "value": $.trim(portalStatus)});
	}
};

//分页查询
portal.portalList = function () {
	if (portal.oTable == null) {
		portal.oTable = $('#portalTable').dataTable( {
			"iDisplayLength": portal.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/portal/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": portal.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				portal.oTableFnServerParams(aoData);
			}
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5]}
	        ]
		} );
	} else {
		portal.oTable.fnDraw();
	}
};
	
//处理缓存问题 清空form表单中值
portal.clearForm = function(form){	    	  
	   $(':input', form).each(function() { 
	     var type = this.type;
	     var tag = this.tagName.toLowerCase(); // normalize case 
	     if (type == 'text' || type == 'password' || tag == 'textarea') 
	       this.value = "";
	     else if (tag == 'select')
	       this.value = "";
	   }); 
};

//加载添加DIV
portal.loadAddHtml = function (){
	if($.trim($("#portalEdit").html()) == ""){
		$("#portalEdit").load(getRootPath()+"/sys/portal/portalEdit.action",null,portal.createportal);
	}
	else{
		portal.createportal();
	}
};

//新增弹出对话框方法
portal.createportal = function(){
	hideErrorMessage();
	$("#id").attr("value","");
	portal.clearForm(portalForm);
	$("#titleportal").html("新增");
	$('#myModal-edit').modal('show');
	ie8StylePatch();
};
		 
//加载修改div
portal.loadUpdateHtml = function (id){
	if($.trim($("#portalEdit").html()) == ""){
		$("#portalEdit").load(getRootPath()+"/sys/portal/portalEdit.action",null,function(){portal.get(id);});
	}
	else{
		portal.get(id);
	}
};

//修改弹出对话框方法
portal.get = function(id){
	  hideErrorMessage();
  	  jQuery.ajax({
            url: getRootPath()+"/sys/portal/get.action?id="+id+"&time="+new Date(),
            type: 'post',
            dataType: 'json',
            success: function(data, textStatus, xhr) {
              portal.clearForm(portalForm);
          	  $("#portalForm").fill(data);
          	  $("#titleportal").html("编辑");
          	  $('#myModal-edit').modal('show');
          	  ie8StylePatch();
            },error:function(){
          	 // alert("加载数据错误。");
          	  msgBox.tip({content: "加载数据错误", type:'fail'});
            }
       });
};

//删除门户
portal.deleteportal = function (id) {
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
		msgBox.info({content: "请选择要删除的门户", type:'fail'});
		return;
	}
	confirmx("您确定要删除选中的数据吗?",function(){portal.deleteCallBack(ids);});
};

//删除门户回调方法
portal.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/portal/deleteByIds.action?time=" + new Date(),
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				//alertx("删除成功");
				msgBox.tip({content: "删除成功", type:'success'});
				portal.portalList();
			}
		}
	});
};

//加载添加DIV
portal.loadshareHtml = function (id){
	if($.trim($("#portalshareEdit").html()) == ""){
		$("#portalshareEdit").load(getRootPath()+"/sys/rolePortal/rolePortalEdit.action",null,function(){portal.shareportal(id);});
	}
	else{
		portal.shareportal(id);
	}
};

//门户共享范围方法
portal.shareportal = function(id) {
	portal.shareModalLoad();
	portal.clearShareModal();
	$("#portalId").val(id);
	var url = getRootPath()+"/sys/rolePortal/getIds.action";
	jQuery.ajax({
		  type : "POST",
		  url: url,
	      data : {"portalId": id},
		  dataType : "json",
		  success : function(data) {
			  if(data.userids!=null||data.userids!="")
				  leftRightSelect.setData("UserleftRight",data.userids);
			  if(data.userids!=null||data.userids!="")
				  leftRightSelect.setData("RoleleftRight",data.roleids);
			  if(data.userids!=null||data.userids!="")
				  setOrgData("DeptleftRight-DeptleftRight",data.deptids);
			  if(data.userids!=null||data.userids!="")
				  setOrgData("OrganleftRight-OrganleftRight",data.organids);
			  $("#tokens").val(data.token);
			  $('#myModal-share').modal('show');
			  ie8StylePatch();
		  },
	      error:function(){
			  //alert("保存数据错误。");
			  
		  }
		});
};

portal.queryReset = function(){
	$('#portalListForm')[0].reset();
};

portal.shareModalLoad = function (){
	leftRightSelect.init({
		containerId:"UserResultDiv",
		moduleId:"UserleftRight",
		isCheck:true,
		url:getRootPath()+"/sys/rolePortal/getData.action?type=user",
		title:"用户",
		callback:function(data){

		}
	});		
	
	leftRightSelect.init({
		containerId:"RoleResultDiv",
		moduleId:"RoleleftRight",
		isCheck:true,
		url:getRootPath()+"/sys/rolePortal/getData.action?type=role",
		title:"角色",
		callback:function(data){

		}
	});
	
/*	leftRightSelect.init({
		containerId:"DeptResultDiv",
		moduleId:"DeptleftRight",
		isCheck:true,
		url:getRootPath()+"/sys/rolePortal/getData.action?type=dept",
		title:"部门",
		callback:function(data){

		}
	});*/
	selectControl.init("DeptResultDiv", "DeptleftRight-DeptleftRight", true, false, 0);	//部门
	
	/*leftRightSelect.init({
		containerId:"OrganResultDiv", 
		moduleId:"OrganleftRight",
		isCheck:true,
		url:getRootPath()+"/sys/rolePortal/getData.action?type=organ",
		title:"机构",
		callback:function(data){

		}
	});*/
	selectControl.init("OrganResultDiv", "OrganleftRight-OrganleftRight", true, false, 1);	//机构
};

portal.clearShareModal = function (){
	$("#UserleftRight").val();
	$("#RoleleftRight").val();
	$("#DeptleftRight-DeptleftRight").val();
	$("#OrganleftRight-OrganleftRight").val();
};
//初始化
{
	//计算分页显示条数
	portal.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryportal").click(portal.portalList);
	$("#queryReset").click(portal.queryReset);
	$("#portalTop").click(portal.loadAddHtml);
	$("#portalBottom").click(portal.loadAddHtml);
	$("#deleteportals").click("click", function(){portal.deleteportal(0);});
	//初始化列表方法 
	portal.portalList();
	
}


