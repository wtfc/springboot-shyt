var portlet = {}, pageCount=10;
  	
//重复提交标识
portlet.subState = false;
//分页处理 start
//分页对象
portlet.oTable = null;

//显示列信息
//门户
portlet.oTableAoColumns = [
	//不需要排序的列直接用mData function方式
	{mData: function(source) {
			return "<input type=\"checkbox\" name=\"ids\" value="+ source.id + ">";
		}
	},
	{ "mData": "portalName"},
	{ "mData": "letTitle" },
	{ "mData": "functionName" },
	{ "mData": "viewType", mRender : function(mData, type, full){
		if(mData == 'onetable')
			return "单组件";
		else if(mData == 'shortcut')
			return "快捷方式组件";
		else if(mData == 'textareaEdit')
			return "文本域组件";
		else if(mData == 'freeJsp')
			return "公文查询组件";
		else if(mData == 'printEdit')
			return "单图片组件";
		else if(mData == 'friendlyLink')
			return "友情链接组件";
		else 
			return "多组件";
	}},
	//设置权限按钮
	{mData: function(source) {
		return oTableSetButtonsportlet(source);
	}}
];

portlet.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(portlet.oTable, aoData);
	
	var portalId = $("#portletListForm #portalId").val();
	var functionName = $("#portletListForm #functionName").val();
	
	if(portalId != ""){
		aoData.push({ "name": "portalId", "value": $.trim(portalId)});
	}
	if(functionName != ""){
		aoData.push({ "name": "functionName", "value": $.trim(functionName)});
	}
};

//分页查询
portlet.portletList = function () {
	if (portlet.oTable == null) {
		portlet.oTable = $('#portletTable').dataTable( {
			"iDisplayLength": portlet.pageCount,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/sys/portlet/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": portlet.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				portlet.oTableFnServerParams(aoData);
			}
			//默认不排序列
	        ,"aoColumnDefs": [
				{"bSortable": false, "aTargets": [0,1,2,3,4,5]}
	        ]
		} );
	} else {
		portlet.oTable.fnDraw();
	}
};
	
//处理缓存问题 清空form表单中值
portlet.clearForm = function(form){	    	  
	   $(':input', form).each(function() { 
	     var type = this.type;
	     var tag = this.tagName.toLowerCase(); // normalize case 
	     if (type == 'text' || type == 'password' || tag == 'textarea') 
	       this.value = "";
	     else if (tag == 'select')
	           this.selectedIndex = 0;
	   }); 
};

//加载添加DIV
portlet.loadAddHtml = function (){
	if($.trim($("#portletEdit").html()) == ""){
		$("#portletEdit").load(getRootPath()+"/sys/portlet/portletEdit.action",null,portlet.createportlet);
	}
	else{
		portlet.createportlet();
	}
};

//新增弹出对话框方法
portlet.createportlet = function(){
	hideErrorMessage();
	$("#id").attr("value","");
	portlet.clearForm(portletForm);
	portlet.clearFunction();
	$("#titleportlet").html("新增");
	$('#myModal-edit').modal('show');
	ie8StylePatch();
};
		 
//加载修改div
portlet.loadUpdateHtml = function (id){
	if($.trim($("#portletEdit").html()) == ""){
		$("#portletEdit").load(getRootPath()+"/sys/portlet/portletEdit.action",null,function(){portlet.get(id);});
	}
	else{
		portlet.get(id);
	}
};

//修改弹出对话框方法
portlet.get = function(id){
	  hideErrorMessage();
	  getToken();
  	  jQuery.ajax({
            url: getRootPath()+"/sys/portlet/get.action?id="+id+"&time="+new Date(),
            type: 'post',
            dataType: 'json',
            success: function(data, textStatus, xhr) {
              portlet.clearForm(portletForm);
          	  $("#portletForm").fill(data);
          	  $("#titleportlet").html("编辑");
          	  portlet.loadSelFunction();
          	  $('#myModal-edit').modal('show');
          	  ie8StylePatch();
            },error:function(){
          	 // alert("加载数据错误。");
          	  msgBox.tip({content: "加载数据错误", type:'fail'});
            }
       });
};

//删除门户
portlet.deleteportlet = function (id) {
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
	confirmx("您确定要删除选中的数据吗?",function(){portlet.deleteCallBack(ids);});
};

//删除门户回调方法
portlet.deleteCallBack = function(ids) {
	$.ajax({
		type : "POST",
		url : getRootPath()+"/sys/portlet/deleteByIds.action?time=" + new Date(),
		data : {"ids": ids},
		dataType : "json",
		success : function(data) {
			if (data > 0) {
				//alertx("删除成功");
				msgBox.tip({content: "删除成功", type:'success'});
				portlet.portletList();
			}
		}
	});
};

portlet.queryReset = function(){
	$('#portletListForm')[0].reset();
};

portlet.clearFunction = function(e){
	$("#portletForm #optfunctions").find("option").remove();
	$("#portletForm #optionfunctions").find("option").remove();
	
	$("#portletForm #functionId").val("");
	$("#portletForm #functionName").val("");
	
	if($("#viewType").val() == 'textareaEdit'){
		$("#isnotedit").css('display','none');
		$("#portletForm #functionId").val(0);
		$("#portletForm #functionName").val("文本域");
	}else if($("#viewType").val() == 'freeJsp'){
		$("#isnotedit").css('display','none');
		$("#portletForm #functionId").val(0);
		$("#portletForm #functionName").val("公文查询组件");
	}else if($("#viewType").val() == 'printEdit'){
		$("#isnotedit").css('display','none');
		$("#portletForm #functionId").val(0);
		$("#portletForm #functionName").val("单图片");
	}else if($("#viewType").val() == 'friendlyLink'){
		$("#isnotedit").css('display','none');
		$("#portletForm #functionId").val(0);
		$("#portletForm #functionName").val("友情链接");
	}else {
		$("#isnotedit").css('display','');
		$("#portletForm #tempfunctions").find("option").each(function(){
			if($("#viewType").val() == 'shortcut' && $(this).attr("id").indexOf("pviewType_7_") != -1){
				$("#portletForm #optionfunctions").append($(this).clone());
			} else 
			if($("#viewType").val() == 'onetable' && $(this).attr("id").indexOf("pviewType_7_") == -1){
				$("#portletForm #optionfunctions").append($(this).clone());
			} else
			if($("#viewType").val() == 'moretable' && $(this).attr("id").indexOf("pviewType_7_") == -1){
				$("#portletForm #optionfunctions").append($(this).clone());
			}
		});
	}
};

portlet.loadSelFunction = function(){
	var funids = ","+$("#portletForm #functionId").val()+",";
	$("#portletForm #optionfunctions").find("option").each(function(){
		if(funids.indexOf(","+$(this).val()+",") > -1){
			$("#portletForm #optfunctions").append($(this).clone());
			$(this).remove();
		}
	});
	/*//var funnames = ","+$("#portletForm #functionName").val()+",";
	$("#portletForm #optionfunctions").find("option").each(function(){
		if(funnames.indexOf(","+$(this).text()+",") > -1){
			$("#portletForm #optfunctions").append($(this).clone());
			$(this).remove();
		}
	});*/
};

//加载添加DIV
portlet.loadshareHtml = function (id){
	if($.trim($("#portletshareEdit").html()) == ""){
		$("#portletshareEdit").load(getRootPath()+"/sys/rolePortal/rolePortletsEdit.action",null,function(){portlet.shareportlet(id);});
	}
	else{
		portlet.shareportlet(id);
	}
};

//门户共享范围方法
portlet.shareportlet = function(id) {
	portlet.shareModalLoad();
	portlet.clearShareModal();
	$("#portletId").val(id);
	var url = getRootPath()+"/sys/rolePortal/getIds.action";
	jQuery.ajax({
		  type : "POST",
		  url: url,
	      data : {"portaletId": id},
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

portlet.shareModalLoad = function (){
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
	
	/*leftRightSelect.init({
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

portlet.clearShareModal = function (){
	$("#UserleftRight").val();
	$("#RoleleftRight").val();
	$("#DeptleftRight-DeptleftRight").val();
	$("#OrganleftRight-OrganleftRight").val();
};
//初始化
{
	//计算分页显示条数
	portlet.pageCount = TabNub>0 ? TabNub : 10;
	$("#queryportlet").click(portlet.portletList);
	$("#queryReset").click(portlet.queryReset);
	$("#portletTop").click(portlet.loadAddHtml);
	$("#portletBottom").click(portlet.loadAddHtml);
	$("#deleteportlets").click("click", function(){portlet.deleteportlet(0);});
	//初始化列表方法 
	portlet.portletList();    
	
};	


