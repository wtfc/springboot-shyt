var portalEdit = {};
  	
//重复提交标识
portalEdit.subState = false;

//验证名称是否重复
portalEdit.valNameEcho = function(){
	var returnvalNameEcho = false;
	var formData = $("#portalForm").serializeArray();
	jQuery.ajax({
		url: getRootPath()+"/sys/portal/valNameEcho.action?time="+new Date(),
		type: "post",
		async:false,
		data: formData,
		success: function(data, textStatus, xhr) {
			if(data.success == "false"){
				msgBox.info({content: "门户名称重复，请重新命名", type:'fail'});
				returnvalNameEcho = false;
			}else {
				returnvalNameEcho = true;
			}
		}
	});
	return returnvalNameEcho;
};

//保存方法
portalEdit.portalSubmit = function(){
	if (portalEdit.subState)return;
	  
	  //alert(portal.checkRepeat);
	if($("#portalForm").valid() && portalEdit.valNameEcho()){
	  var formData = $("#portalForm").serializeArray();
	  var addUrl = getRootPath()+"/sys/portal/save.action?time="+new Date();
	  var updateUrl = getRootPath()+"/sys/portal/update.action?time="+new Date();
	  var url;
	  
	  if($("#id").val().length > 0){
		  url = updateUrl;
	  }else{
		  url = addUrl;
	  }
	  jQuery.ajax({
		  url: url,
		  type: 'post',
		  data: formData,
		  success: function(data, textStatus, xhr) {
			  portalEdit.subState = false;
			  $("#token").val(data.token);
			  if(data.errorMessage!=null){
				  //alert(data.errorMessage);
				  //msgBox.tip({content: data.errorMessage, type:'fail'});
			  }else{
				  //alertx("保存成功");
				  if($("#id").val().length > 0)
					  msgBox.tip({content: $.i18n.prop("JC_SYS_003"), type:'success'});
				  else
					  msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
				  $('#myModal-edit').modal('hide');
				  portal.portalList();
			  }
		  },error:function(){
			  //alert("保存数据错误。");
			  if($("#id").val().length > 0)
				  msgBox.tip({content: $.i18n.prop("JC_SYS_004"), type:'fail'});
			  else
				  msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
		  }
		});
	} else {
		portalEdit.subState = false;
	}
};

//门户共享范围保存
portalEdit.shareSubmit = function(){
	if (portalEdit.subState)return;
	var token = $("#token").val();
	var url = getRootPath()+"/sys/rolePortal/save.action";
	  jQuery.ajax({
		  type : "POST",
		  url: url,
	      data : {"userids": $("#UserleftRight").val(),"roleids": $("#RoleleftRight").val(),"deptids": $("#DeptleftRight-DeptleftRight").val(),"organids": $("#OrganleftRight-OrganleftRight").val(),"portalId":$("#portalId").val(),"token":token},
		  dataType : "json",
		  success : function(data) {
			  portalEdit.subState = false;
			  $("#token").val(data.token);
			  $('#myModal-share').modal('hide');
			  portal.portalList();
			  msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
			},
	      error:function(){
			  msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
		  }
		});
	 
};

//初始化
{
	$("#portalbtn").click(portalEdit.portalSubmit);
	$("#closebtn").click(portal.clearForm(portalForm));
}


