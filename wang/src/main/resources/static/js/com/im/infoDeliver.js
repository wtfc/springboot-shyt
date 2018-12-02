infoDeliver = {};

/**重复提交标识,false时,允许提交表单*/
infoDeliver.subState = false;
infoDeliver.openUserAndDept = null;
infoDeliver.columnFlag = "";

function validateForm(){
	initValid();
	return $("#infoDeliverForm").valid(); 
}
/**校验失败时调用此方法*/
function validateFormFail(){
	infoDeliver.subState = false;
	msgBox.info({
		content: $.i18n.prop("JC_SYS_067")
	});
}


/**增加业务方法*/
function insert(status,jumpFun){
	var result = true; //流程提交返回值
	if(infoDeliver.subState)return;
	infoDeliver.subState = true;
	var postData = $("#infoDeliverForm").serializeArray();
	//添加其他信息
	infoDeliver.addFormParameters(postData);
	jQuery.ajax({
		url : getRootPath() + "/im/info/save.action",
		type : 'POST',
		async : false,
		contentType: "application/json;charset=UTF-8",//一对多关系必填否则去掉
		data : JSON.stringify(serializeJson(postData)),
		success : function(data) {
			infoDeliver.subState = false;
//			getToken();
			if (data.success == "true") {
				if(status=="Save"){
					msgBox.tip({
						type : "success",
						content : $.i18n.prop("JC_WORKFLOW_001"),
						callback : jumpFun
					})
				}else{
					msgBox.tip({
						type : "success",
						content : data.successMessage,
						callback : jumpFun
					})
				}
			} else {
				if(data.labelErrorMessage){
					showErrors("infoDeliverForm", data.labelErrorMessage);
				} else if (data.errorMessage) {
					if (data.errorMessage == "tokenError") {
						alertx(tokenMessage);
					} else if(data.errorMessage == "concurrentError"){
						alertx(concurrentMessage);
					} else {
						alertx(data.errorMessage);
					}
				} else {
					msgBox.tip({
						type:"fail",
						content: data.errorMessage
					});
				}
			}
		},
		error : function() {
			infoDeliver.subState = false;
			msgBox.info({
				content: $.i18n.prop("JC_WORKFLOW_002")
			});
		}
	});
	return result;
}

//信息审批方法
function update(type, jumpFun) {
	var result = true; //流程提交返回值
	if (infoDeliver.subState)
		return;
	var postData = $("#infoDeliverForm").serializeArray();
	infoDeliver.addFormParameters(postData);
	jQuery.ajax({
		url : getRootPath() + "/im/info/update.action",
		async : false,
		type : 'POST',
		contentType: "application/json;charset=UTF-8",//一对多关系必填否则去掉
		data : JSON.stringify(serializeJson(postData)),
		success : function(data) {
			infoDeliver.subState = false;
//			get();
			if (data.success == "true") {
				if(type == "Reject"){//退回操作
					msgBox.tip({
						type : "success",
						content : $.i18n.prop("JC_WORKFLOW_001"),
						callback : jumpFun
					})
				}else if(type == "Save"){
					msgBox.tip({
						type : "success",
						content : $.i18n.prop("JC_WORKFLOW_001"),
						callback : jumpFun
					})
				}else{
					msgBox.tip({
						type : "success",
						content : data.successMessage,
						callback : jumpFun
					})
				}
			} else {
				if (data.labelErrorMessage) {
					showErrors("planForm", data.labelErrorMessage);
				} else if (data.errorMessage) {
					if (data.errorMessage == "tokenError") {
						alertx(tokenMessage);
					} else if (data.errorMessage == "concurrentError") {
						alertx(concurrentMessage);
					} else {
						alertx(data.errorMessage);
					}
				} else {
					msgBox.tip({
						type : "fail",
						content : data.errorMessage
					});
				}
			}
		},
		error : function() {
			infoDeliver.subState = false;
			msgBox.info({
				content : $.i18n.prop("JC_WORKFLOW_002")
			});
		}
	});
	return result;
}

function loadForm(piId) {
	if (piId != null && piId.length > 0) {
		var ajaxData = {
			piId : piId,
			time : new Date()
		};
		jQuery.ajax({
			url : getRootPath() + "/im/info/get.action",
			type : 'GET',
			data : ajaxData,
			async : false,
			success : function(data) {
//		var infoJson=$("#infoJson").val();
//		var data=eval("("+infoJson+")");
				if(data){
					$("#infoDeliverForm").fill(data);
					//UE.getEditor("infoContent").setContent(data.infoContent);
					//ueditor初始化
					infoDeliver.contentEditor=UE.getEditor("infoContent",{
						initialFrameHeight : 400,
						initialFrameWidth : "100%",
						toolbars: [
						           [//'source', //源代码
						            'undo', //撤销
						            'redo', //重做
						            
						            'fontfamily', //字体
						            'fontsize', //字号
						            'bold', //加粗
						            'italic', //斜体
						            'underline', //下划线
						            'strikethrough', //删除线
						            'forecolor', //字体颜色
						            'backcolor', //背景色
						            'horizontal', //分隔线
						            'insertorderedlist', //有序列表
						            'insertunorderedlist', //无序列表
						            'touppercase', //字母大写
						            'tolowercase', //字母小写
						            'removeformat', //清除格式
						           
						            'indent', //首行缩进
						            'justifyleft', //居左对齐
						            'justifyright', //居右对齐
						            'justifycenter', //居中对齐
						            'justifyjustify', //两端对齐
						            'subscript', //下标
						            'superscript', //上标
						            'fontborder', //字符边框
						            'blockquote', //引用
						            'pasteplain', //纯文本粘贴模式
						            'selectall', //全选
						            'searchreplace', //查询替换
						            
						            ],[
						            'inserttable', //插入表格
						            'insertrow', //前插入行
						            'insertcol', //前插入列
						            'mergeright', //右合并单元格
						            'mergedown', //下合并单元格
						            'deleterow', //删除行
						            'deletecol', //删除列
						            'splittorows', //拆分成行
						            'splittocols', //拆分成列
						            'splittocells', //完全拆分单元格
						            'deletecaption', //删除表格标题
						            'inserttitle', //插入标题
						            'mergecells', //合并多个单元格
						            'deletetable', //删除表格
						            'edittable', //表格属性
						            'edittd', //单元格属性
						            
						            'time', //时间
						            'date', //日期
						            'link', //超链接
						            'unlink', //取消链接
						            'paragraph', //段落格式
						            'rowspacingtop', //段前距
						            'rowspacingbottom', //段后距
						            'directionalityltr', //从左向右输入
						            'directionalityrtl', //从右向左输入
						            //'pagebreak', //分页
						            'lineheight', //行间距
						            'edittip ', //编辑提示
						            'emotion', //表情
						            'spechars', //特殊字符
						            'cleardoc', //清空文档
						            'fullscreen', //全屏
						            'insertimage',//插入图片
						            ]
						       ]
					});
					$('#sendName').html(data.sendName);
					$('#sendDepName').html(data.sendDepName);
					
					//栏目回显
					infoDeliver.initColumnData(data.columnId);
					//有效期限回显
					if(data.effectiveTime==-1){
						$('#effectiveTime1').prop('checked','true');
						$('#effectiveTime2').attr('readonly','readonly');
					}else if(data.effectiveTime!=null&&data.effectiveTime!=-1){
						$('#effectiveTime1').removeAttr('checked');
						$('#effectiveTime2').removeAttr('readonly').val(data.effectiveTime);
					}
					
					//首页图片回显
					if(data.portalPic!=null&&data.portalPic!=''){
						$("#portalPicImg").attr("src", getRootPath()+"/"+data.portalPic+"/portalPic.png");
					}
					//附件回显
					$('#businessId').val(data.id);
					$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
//					showAttachList(false);//附件填出层数据回显方法
					echoAttachList(false);//附件列表数据回显方法	
					clearTable();
					//公开范围回显
					var echoData = [];
					if(data.isOpen==1){
						//显示使用范围
						var openUserAndDept = data.openUserAndDept;
						if(openUserAndDept!=null&&openUserAndDept.length>0) {
							for(var i = 0; i < openUserAndDept.length; i++) {
								echoData.push({id:openUserAndDept[i]['id'],text:openUserAndDept[i]['text'],type:openUserAndDept[i]['type']});
							}
						}  
						infoDeliver.initDeptAndPersonControl(echoData);
						$('tr[class*="open_range"]').show();
					}
					
				}else{
					infoDeliver.contentEditor=UE.getEditor("infoContent",{
						initialFrameHeight : 400,
						initialFrameWidth : "100%",
						toolbars: [
						           [//'source', //源代码
						            'undo', //撤销
						            'redo', //重做
						            
						            'fontfamily', //字体
						            'fontsize', //字号
						            'bold', //加粗
						            'italic', //斜体
						            'underline', //下划线
						            'strikethrough', //删除线
						            'forecolor', //字体颜色
						            'backcolor', //背景色
						            'horizontal', //分隔线
						            'insertorderedlist', //有序列表
						            'insertunorderedlist', //无序列表
						            'touppercase', //字母大写
						            'tolowercase', //字母小写
						            'removeformat', //清除格式
						           
						            'indent', //首行缩进
						            'justifyleft', //居左对齐
						            'justifyright', //居右对齐
						            'justifycenter', //居中对齐
						            'justifyjustify', //两端对齐
						            'subscript', //下标
						            'superscript', //上标
						            'fontborder', //字符边框
						            'blockquote', //引用
						            'pasteplain', //纯文本粘贴模式
						            'selectall', //全选
						            'searchreplace', //查询替换
						            
						            ],[
						            'inserttable', //插入表格
						            'insertrow', //前插入行
						            'insertcol', //前插入列
						            'mergeright', //右合并单元格
						            'mergedown', //下合并单元格
						            'deleterow', //删除行
						            'deletecol', //删除列
						            'splittorows', //拆分成行
						            'splittocols', //拆分成列
						            'splittocells', //完全拆分单元格
						            'deletecaption', //删除表格标题
						            'inserttitle', //插入标题
						            'mergecells', //合并多个单元格
						            'deletetable', //删除表格
						            'edittable', //表格属性
						            'edittd', //单元格属性
						            
						            'time', //时间
						            'date', //日期
						            'link', //超链接
						            'unlink', //取消链接
						            'paragraph', //段落格式
						            'rowspacingtop', //段前距
						            'rowspacingbottom', //段后距
						            'directionalityltr', //从左向右输入
						            'directionalityrtl', //从右向左输入
						            //'pagebreak', //分页
						            'lineheight', //行间距
						            'edittip ', //编辑提示
						            'emotion', //表情
						            'spechars', //特殊字符
						            'cleardoc', //清空文档
						            'fullscreen', //全屏
						            'insertimage',//插入图片
						            ]
						       ]
					});
				}
			}
		});
	}
}

/**更新信息阅读状态*/
infoDeliver.reading = function(id){
	$.ajax({
		type : "GET",
		url : getRootPath()+"/im/infoRead/save.action?time=" + new Date(),
		data : {'infoId':id},
		dataType : "json",
		success : function(data) {
			if (data.success == "true") {
				
			}
		}
	});
};

infoDeliver.addFormParameters = function(postData){
	if($('#effectiveTime1').prop('checked')){
		postData.push({"name": "effectiveTime", "value": $('#effectiveTime1').val()});
	}else{
		var effectiveTimeVal =  $('#effectiveTime2').val();
		if(effectiveTimeVal!=''){
			postData.push({"name": "effectiveTime", "value": effectiveTimeVal});
		}
	}
	
	//附件
	var fileids = new Array();
	$.each($("#file-edit").find("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	
	postData.push({"name": "fileids", "value": fileids});
	postData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
	
	postData.push({"name": "openUserAndDept", "value": infoDeliver.openUserAndDept});
};

//获得已上传的附件
infoDeliver.getPortalPic = function(){
	$('#photoupload table tbody').empty();
    $('#photoupload').addClass('fileupload-processing');
    $.ajax({
        url: getRootPath()+"/im/info/infoPortalList.action",
        data : {"id": $("#id").val(), fileFolderName: $("#portalPic").val()},
        dataType: 'json',
        context: $('#photoupload')[0]
    }).always(function () {
        $(this).removeClass('fileupload-processing');
    }).done(function (result) {
        $(this).fileupload('option', 'done')
            .call(this, $.Event('done'), {result: result});
    });
    $('#portalPicUpload').modal('show');
};

/**初始化栏目树*/
infoDeliver.initColumn = function(data){
	var initValue = [];
	if(data)initValue = data;
	if(scanType=='create'){
		initValue.push({'':""}); 
	}
	$("#column").treeSelectControl({
		controlId:"columnId",   //必须
		controlName:"columnId", //必须
		multiMode:false,   //必须
		containerId:"column",  //必须，外层容器ID
		url:getRootPath()+"/im/column/queryColumnTreeByUser.action", //必须
		callbackFunction:infoDeliver.columnCallback,  //可选值变化回调函数
		mappings:{id:"id",name:"columnName",parentId:"columnParentId",title:"piId",nodeType:"nodeType"},  //可选
		initValue:initValue  //可选
	});
	
};

/**栏目树的回调方法*/
infoDeliver.columnCallback = function(selectedData){
	msgBox.confirm({
		content: '修改栏目需重新添加数据</br>是否修改？',
		success: function(){
			var data;
			if(selectedData!=null){
				data = selectedData.split(':');
			}
			if(data){
				var columnId = data[0];
				var echoData = [];
				infoDeliver.initData(columnId);
			}
		},
		cancel: function(){
			$("#columnId").select2("data", infoDeliver.columnFlag);
		}
	});
	
	//infoDeliver.initDeptAndPersonControl(echoData);
};

/**根据栏目id获得栏目信息*/
infoDeliver.getColumn = function(columnId){
	var column = null;
	jQuery.ajax({
		url : getRootPath() + "/im/column/get.action?time="+new Date(),
		type : 'GET',
		data : {id:columnId},
		async : false,
		success : function(data) {
			if(data){
				column = data;
			}
		},
		error : function() {
			alertx("查询失败");
		}
	});
	return column;
};

/**组织页面缓存数据改变栏目后回显*/
infoDeliver.initData = function(columnId){
	//var postData = $("#infoDeliverForm").serializeArray();
	var flowKey;
	var postData = [];
	var columnObj;
	var data = infoDeliver.getColumn(columnId);
	columnObj = {'columnId': data.id, 'columnName': data.columnName};
	flowKey = data.piId;
	postData.push({'name': 'column', 'value': columnObj});
	
	//将json对象转为字符串并将单引号转义
	var jsonStr = JSON.stringify(serializeJson(postData)).replace(/\"/g,"\\\'");
	var url = '/workFlow/processDefinition/toStartProcess.action?flowIden='+flowKey;
	loadrightmenu(url,true,{'condition':jsonStr});
};

/**人员部门树的回调方法(备用)*/
infoDeliver.deptPersonCallback = function(data) {
	infoDeliver.openUserAndDept = data;
};

/**初始化人员部门树*/
infoDeliver.initDeptAndPersonControl = function(echoData){
	//通过columnId或得栏目中设置的可发布人模板
	infoDeliver.openUserAndDept = echoData;
	var opts = {
		widgetId : "openDeptAndPersonIds",	//控件ID
		widgetName : "openDeptAndPersonIds",//控件name
		echoData: echoData,	//回显数据, 没有可以不写
		callbackFunction : infoDeliver.deptPersonCallback//回调函数
	};
	$("#deptAndPerson").deptAndPersonControl(opts);
};

/**栏目信息及栏目设置的公开范围回显*/
infoDeliver.initColumnData = function(columnId){
	if(columnId){
		//回显栏目对应的可查看人
		var data = infoDeliver.getColumn(columnId);
		var columnName=data.columnName
		var checkerRange = data.checkerRange;
		var echoData = [];
		if(checkerRange) {
			 for(var i = 0; i < checkerRange.length; i++) {
				 echoData.push({id:checkerRange[i]['id'],text:checkerRange[i]['text'],type:checkerRange[i]['type']});
			 }
		 }  
		infoDeliver.initDeptAndPersonControl(echoData);
	}
	//栏目回显
	$('#column').html(columnName);
}

/**附件上传方法*/
infoDeliver.uploadAttach = function(){
//	$('#file-edit').find('.files').empty();
//	var id = $('#id').val();
//	if(id != ''){
//		//设置附件数据
//		$('#businessId').val(id);
//		$('#businessSource').val('0');//默认的值为0 表示来源于OA，1表示来源于CRM
////		showAttachList();//附件弹出层数据回显方法
//	}
/*	$('#businessId').val("0");
	showAttachList(false);//附件弹出层数据回显方法
	echoAttachList();//附件列表数据回显方法
*/	$('#file-edit').modal('show');
};

/**附件上传关闭按钮方法*/
infoDeliver.attachClose = function(){
	$("#businessId").val("0");
	showAttachList(false);//id传入回显的容器
	$('#file-edit').modal('hide');
	//clearTable();
};


/**页面初始化方法*/
function initFun(){
	var columnId =getUrlParameter();//modify by liuxl
	if(columnId!=''){
//		condition = condition.replace(/\\\'/g,"\"");//转义的单引号还原
//		testJson = eval("(" + condition + ")");//将字符串转为对象
//		$('#columnId').val(testJson.columnId);
		$('#columnId').val(columnId);//modify by liuxl
		infoDeliver.initColumnData(columnId);//modify by liuxl
	}
	//create为发布
	var scanType=$('#scanType').val();
	//回显发布人和发布部门
	if(scanType=='create'){
		$("#sendName").append($("#sendDisplayName").val());
		$("#sendDepName").html($("#sendDisplayDepName").val());
	}
//	getToken();
	//初始化日期控件
	$(".datepicker-input").datepicker();
	//绑定是否公开显示和隐藏人员选择
//	$('#infoDeliverForm').on('click','input[name="isOpen"]',function(){
//		var currentChecked = $(this).val();
//		$(this).parents('tr').next('.open_range').toggle();
//	});
	
	//绑定表单验证
	initValid();
	
	//绑定有效期限的操作
	$('#effectiveTime1').click(function(){
		if($('#effectiveTime1').prop('checked')){
			$('#effectiveTime2').attr('readonly','readonly').val('').removeClass('error');
			//$('#effectiveTime2').attr('readonly','readonly').removeClass('error');
			$('label[for="effectiveTime2"][class="error"]').remove();
		}else{
			$('#effectiveTime2').removeAttr('readonly');
		}
	});
	
	//绑定首页图片上传按钮
	$("#uploadPhoto").click(infoDeliver.getPortalPic);
	//绑定附件上传
	$('#attachs').click(infoDeliver.uploadAttach);
	//绑定附件上传关闭按钮
	$('#closeModalBtn').click(infoDeliver.attachClose);
	$('#closebtn').click(infoDeliver.attachClose);
}
/**
 * 初始化方法
 */
jQuery(function($){
	initFun();
	loadForm($("#piId").val());
	formDetail.initForm();
});
//@sourceURL=infoDeliver.js