var resource={};
//修改保存方法
resource.MenuEditSubmit = function() {
	if($("#menuEditForm #editparentId").val() == -1 && $("#menuEditForm #name").val().length > 4){
		msgBox.tip({content: '一级导航栏名称最多可添加4个字', type:'fail'});
		return;
	}
		
	if ($("#menuEditForm").valid()) {
		var formData = $("#menuEditForm").serializeArray();
		var addUrl = getRootPath() + "/sys/menu/save.action?time="
				+ new Date();
		var updateUrl = getRootPath()
				+ "/sys/menu/update.action?time=" + new Date();
		var url;

		if ($("#menuEditForm #id").val().length > 0) {
			url = updateUrl;
		} else {
			url = addUrl;
		}
				
		jQuery.ajax({
			url : url+"&token="+$("#menuEditForm #token").val(),
			type : 'post',
			data : formData,
			success : function(data, textStatus, xhr) {
				getToken();
				if (data.errorMessage != null) {
					//alert(data.errorMessage);
					msgBox.tip({content: data.errorMessage, type:'fail'});
				} else {
					//alertx("保存成功");
					msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
					$('#myModal-edit').modal('hide');
					if($("#editparentId").val() == -1)
						divname = 'firstmenu';
					else
						divname = 'divname'+$("#editparentId").val();
					resource.loadMenuTree($("#editparentId").val(),divname);
				}
			},
			error : function() {
				msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
				//alert("保存数据错误。");
			}
		});
	}
};

// 新增保存方法
resource.MenuAddSubmit = function() {
	if($("#menuAddForm #addparentId").val() == -1 && $("#menuAddForm #name").val().length > 4){
		msgBox.tip({content: '一级导航栏名称最多可添加4个字', type:'fail'});
		return;
	}
	
	if ($("#menuAddForm").valid()) {
		var formData = $("#menuAddForm").serializeArray();
		
		var addUrl = getRootPath()
				+ "/sys/menu/save.action?time="
				+ new Date();
		var updateUrl = getRootPath()
				+ "/sys/menu/update.action?time="
				+ new Date();
		var url;
		if ($("#menuAddForm #id").val().length > 0) {
			url = updateUrl;
		} else {
			url = addUrl;
		}
		
		jQuery.ajax({
			url : url+"&token="+$("#menuAddForm #token").val(),
			type : 'post',
			data : formData,
			success : function(data, textStatus, xhr) {
				
				if (data.errorMessage != null) {
					//alert(data.errorMessage);
					msgBox.tip({content: data.errorMessage, type:'fail'});
				} else {
					//alertx("保存成功");
					msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
					$('#new-zy').modal('hide');
					if($("#addrootNode").val() == -1)
						divname = 'firstmenu';
					else
						divname = 'divname'+$("#addrootNode").val();
					resource.loadMenuTree($("#addrootNode").val(),divname);
				}
			},
			error : function() {
				msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
				//alert("保存数据错误。");
			}
		});
	}
};			

//新增保存方法
resource.MenuPowerSubmit = function() {
		if ($("#menuPowerForm").valid()) {
		var formData = $("#menuPowerForm").serializeArray();
		
		var addUrl = getRootPath()
				+ "/sys/menu/save.action?time="
				+ new Date();
		var updateUrl = getRootPath()
				+ "/sys/menu/update.action?time="
				+ new Date();
		var url;
		
		if ($("#idPower").val().length > 0) {
			url = updateUrl;
		} else {
			url = addUrl;
		}
		
		jQuery.ajax({
			url : url+"&token="+$("#menuPowerForm #token").val(),
			type : 'post',
			data : formData,
			success : function(data, textStatus, xhr) {
				getToken();
				if (data.errorMessage != null) {
					//alert(data.errorMessage);
					msgBox.tip({content: data.errorMessage, type:'fail'});
				} else {
					//alertx("保存成功");
					msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
					$('#new-qx').modal('hide');
					if($("#powerrootNode").val() == -1)
						divname = 'firstmenu';
					else
						divname = 'divname'+$("#powerrootNode").val();
					resource.loadMenuTree($("#powerrootNode").val(),divname);
				}
			},
			error : function() {
				//alert("保存数据错误。");
				msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
			}
		});
	}
};

// 动态加载下级菜单 
resource.loadMenuTree = function(parentid,divname){
	htmlobj=$.ajax({url:getRootPath()+"/sys/menu/manageLoadResource.action?id="+parentid,async:false});
	$("#"+divname).html(htmlobj.responseText);
	$("i[data-toggle='tooltip']").tooltip();
};

//处理缓存问题 清空form表单中值
resource.clearForm = function(form) { 
   // iterate over all of the inputs for the form 
   // element that was passed in 
   $(':input', form).each(function() { 
     var type = this.type;
     var tag = this.tagName.toLowerCase(); // normalize case 
     // it's ok to reset the value attr of text inputs, 
     // password inputs, and textareas 
     if (type == 'text' || type == 'password' || tag == 'textarea') 
       this.value = ""; 
     // checkboxes and radios need to have their checked state cleared 
     // but should *not* have their 'value' changed 
     else if (type == 'checkbox' || type == 'radio'){
    	 if(this.value == 0)
    		 this.checked = true;
    	 else
    		 this.checked = false; 
     } 
     //  this.checked = false; 
     // select elements need to have their 'selectedIndex' property set to -1 
     // (this works for both single and multiple select elements) 
     //else if (tag == 'select') 
     //  this.selectedIndex = -1; 
   }); 
}; 


// 新增菜单弹出对话框方法
resource.createMenu = function(menuid){
	hideErrorMessage();
    $("#menuAddForm #id").attr("value","");
	jQuery.ajax({
        url: getRootPath()+"/sys/menu/getaddpage.action?id="+menuid+"&time="+new Date(),
        type: 'post',
        dataType: 'json',
        success: function(data, textStatus, xhr) {
          //设置token
          $("#menuAddForm #token").attr("value",data.token);
          resource.clearForm(menuAddForm);
      	  $("#menuAddForm").fill(data.menuvos);
        },error:function(){
      	  //alert("加载数据错误。");
      	  msgBox.info({content: "加载数据错误", type:'fail'});
        }
      });
};

// 新增权限弹出对话框方法
resource.createMenuPower = function(menuid){
	hideErrorMessage();
	$("#menuPowerForm #idPower").attr("value","");
	jQuery.ajax({
        url: getRootPath()+"/sys/menu/getaddpage.action?id="+menuid+"&time="+new Date(),
        type: 'post',
        dataType: 'json',
        success: function(data, textStatus, xhr) {
          resource.clearForm(menuPowerForm);
      	  $("#menuPowerForm").fill(data.menuvos);
      	  //设置token
          $("#menuPowerForm #token").attr("value",data.token);
      	  $("#titlename").html("新增");
        },error:function(){
      	  //alert("加载数据错误。");
      	  msgBox.info({content: "加载数据错误", type:'fail'});
        }
      });
};

//修改弹出对话框方法
resource.updateMenu = function(menuid,menutype){
	hideErrorMessage();
	jQuery.ajax({
        url: getRootPath()+"/sys/menu/geteditpage.action?id="+menuid+"&time="+new Date(),
        type: 'post',
        dataType: 'json',
        success: function(data, textStatus, xhr) {
        	if(menutype == '1'){
        		resource.clearForm(menuPowerForm);
             	$("#menuPowerForm").fill(data.menuvos);
             	//设置token
                $("#menuPowerForm #token").attr("value",data.token);
        	} else {
        		resource.clearForm(menuEditForm);
            	$("#menuEditForm").fill(data.menuvos);
            	//设置token
                $("#menuEditForm #token").attr("value",data.token);
        	}
        	
        	$("#titlename").html("编辑");
        },error:function(){
      	  //alert("加载数据错误。");
      	  msgBox.info({content: "加载数据错误", type:'fail'});
        }
     });
};

resource.confirmDelMenu = function(menuid,parentId){
	var divname;
	if(parentId == -1)
		divname = 'firstmenu';
	else
		divname = 'divname'+parentId;
	if(resource.valClickMenu(menuid) && resource.valMenuSel(menuid)){
		confirmx("是否删除数据?",function(){resource.deleteMenu(menuid,parentId,divname);});
	}
};

//删除方法
resource.deleteMenu = function(menuid,parentId,divname) {
	jQuery.ajax({
		url : getRootPath() + "/sys/menu/delete.action?id="+menuid+"&time="+new Date(),
		type : 'post',
		success : function(data, textStatus, xhr) {
			//alertx("删除成功。");
			msgBox.tip({content: "删除成功", type:'success'});
			resource.loadMenuTree(parentId,divname);
		},
		error : function() {
			//alert("删除数据错误。");
			msgBox.tip({content: "删除数据错误", type:'fail'});
		}
	});
};

//验证被删除的菜单是否存在子菜单
resource.valClickMenu = function(menuid) {
	var checkRepeat = false;
	
	jQuery.ajax({
		url: getRootPath()+"/sys/menu/valClickMenu.action?id="+menuid,
		type: "get",
		async:false,
		dataType: 'json',
		success: function(data, textStatus, xhr) {
			if(data.success == "false"){
				msgBox.info({content: "请先删除子菜单或操作权限", type:'fail'});
				checkRepeat = false;
			}else {
				checkRepeat = true;
			}
		}
	});	
	
	return checkRepeat;
};

//验证被删除的菜单是否被选中
resource.valMenuSel = function(menuid) {
	var checkRepeat = false;
	
	jQuery.ajax({
		url: getRootPath()+"/sys/menu/valMenuSel.action?id="+menuid,
		type: "get",
		async:false,
		dataType: 'json',
		success: function(data, textStatus, xhr) {
			if(data.success == "false"){
				msgBox.info({content: "菜单资源已被角色绑定，<br/>请在角色设置中先解除<br/>角色资源绑定关系", type:'fail'});
				checkRepeat = false;
			}else {
				checkRepeat = true;
			}
		}
	});	
	
	return checkRepeat;
};

//迁移资源弹出对话框方法
resource.moveMenu = function(menuid){
	hideErrorMessage();
	$("#moveMenuForm #idmove").attr("value","");
	jQuery.ajax({
        url: getRootPath()+"/sys/menu/geteditpage.action?id="+menuid+"&time="+new Date(),
        type: 'post',
        dataType: 'json',
        success: function(data, textStatus, xhr) {
          //设置token
          $("#moveMenuForm #token").attr("value",data.token);
          resource.clearForm(moveMenuForm);
      	  $("#moveMenuForm").fill(data.menuvos);
      	  resource.moveTreeMenu();
        },error:function(){
      	  //alert("加载数据错误。");
      	  msgBox.info({content: "加载数据错误", type:'fail'});
        }
      });
};

resource.beforeClick = function(treeId, treeNode) { 
	var check = (treeNode && !treeNode.isParent); 
	if (!check) alert("只能选择..."); 
	return check; 
}; 

resource.onClick = function (e, treeId, treeNode) { 
	var zTree = $.fn.zTree.getZTreeObj("moveMenutree"), 
	nodes = zTree.getSelectedNodes(), 
	v = ""; 
	vid = "";
	nodes.sort(function compare(a,b){return a.id-b.id;}); 
	for (var i=0, l=nodes.length; i<l; i++) { 
		v += nodes[i].name + ",";
		vid += nodes[i].id + ",";
	} 
	if (v.length > 0 ) 
		v = v.substring(0, v.length-1); 
	if (vid.length > 0 ) 
		vid = vid.substring(0, vid.length-1); 
	$("#moveparentName").val(v);
	$("#moveparentId").val(vid);
};  

resource.moveTreeMenu = function(){
	//权限树
	var setting = { 
		check:{
			enable: false,//设置 zTree 的节点上是否显示 checkbox/radio
			nocheckInherit: false,//是否自动继承父节点属性
			chkStyle: "radio",//勾选框类型(checkbox 或 radio)
			radioType : "all"//radio的分组范围[setting.check.enable=true且setting.check.chkStyle="radio"时生效]
		},
		view:{
			selectedMulti: false,//设置是否允许同时选中多个节点
			showLine: false//设置 zTree 是否显示节点之间的连线
		},
		data:{
			simpleData:{
				enable:true//确定zTree数据不需要转换为JSON格式,true是需要
			}
		},
		callback:{
			//beforeClick: resource.beforeClick,
			//onCheck: onCheck,	//用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
			onClick: resource.onClick//,	//用于捕获节点被点击的事件回调函数
			//onRemove : onRemove
		}
	};
	
	var menuTreeUrl = getRootPath()+"/sys/menu/treeResource.action";
	var porentid = $("#moveparentId").val();
	
	$.ajax({
		url : menuTreeUrl,
		type : 'POST',
		success : function(data) {
			var zNodes = [];
			$.each(data.menuList, function(i, o) {
				if(o.id == -1){
					zNodes[i] = {
						id : o.id,
						pId : o.parentId,
						name : o.name,
						open:true
					};
				}else {
					zNodes[i] = {
						id : o.id,
						pId : o.parentId,
						name : o.name
					};
				}
				if(o.id == porentid){
					$("#leadernode").val(o.name);
				}
			});
			$.fn.zTree.init($("#moveMenutree"), setting, zNodes);
			/* tree.expandAll(true);
			var nodes = tree.getNodes();
			if(nodes != null && nodes.length > 0){
				tree.expandNode(nodes[0], true, false);
			} */
		}
	});
};

//新增保存方法
resource.MenuMoveSubmit = function() {
	if($("#moveparentId").val() == $("#idmove").val()){
		msgBox.tip({content: $.i18n.prop("JC_SYS_138"), type:'fail'});
	} else {
	if ($("#moveMenuForm").valid()) {
		var formData = $("#moveMenuForm").serializeArray();
		var updateUrl = getRootPath()+ "/sys/menu/update.action?time=" + new Date();
		jQuery.ajax({
			url : updateUrl+"&token="+$("#moveMenuForm #token").val(),
			type : 'post',
			data : formData,
			success : function(data, textStatus, xhr) {
				if (data.errorMessage != null) {
					//alert(data.errorMessage);
					msgBox.tip({content: data.errorMessage, type:'fail'});
				} else {
					//alertx("保存成功");
					msgBox.tip({content: $.i18n.prop("JC_SYS_001"), type:'success'});
					$('#new-qy').modal('hide');
					resource.loadMenuTree(-1,'firstmenu');
				}
			},
			error : function() {
				msgBox.tip({content: $.i18n.prop("JC_SYS_002"), type:'fail'});
				//alert("保存数据错误。");
			}
		});
	}
	}
};
    
//初始化
jQuery(function($) 
{
	   $("#MenuEditBtn").click(resource.MenuEditSubmit);
	   $("#MenuAddBtn").click(resource.MenuAddSubmit);
	   $("#MenuPowerBtn").click(resource.MenuPowerSubmit);
	   $("#moveMenuBtn").click(resource.MenuMoveSubmit);
	   $("#menuall").click();  
	   $("i[data-toggle='tooltip']").tooltip();
});	


