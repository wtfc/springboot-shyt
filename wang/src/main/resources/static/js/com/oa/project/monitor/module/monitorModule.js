
//初始化方法
var monitorModule = {};
//重复提交标识
monitorModule.subState = false;
//获取修改信息
monitorModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/project/monitor/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				monitorModule.clearForm();
				getUE();
				$("#monitorForm").fill(data);
				$('#businessId').val(data.id);
				$('#businessSource').val('0');
				showAttachList(false);
				echoAttachList(false);
			}
		}
	});
};

//添加修改公用方法
monitorModule.saveOrModify = function (hide) {
	if(monitorModule.subState)return;
		monitorModule.subState = true;
	if ($("#monitorForm").valid()) {
		var url = getRootPath()+"/project/monitor/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/project/monitor/update.action";
		}
		var formData = $("#monitorForm").serializeArray();
		monitorModule.addFormParameters(formData);
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				monitorModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/project/monitor/manageForm.action","","/project/monitor/manageForm.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("monitorForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
					$("#token").val(data.token);
				}
			},
			error : function() {
				monitorModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		monitorModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//附言
monitorModule.equipmentInOutForm3 = function (hide) {
	if(monitorModule.subState)return;
	monitorModule.subState = true;
	var idd=$("#monitorForm #id").val();
	var statuss=$("#monitorForm #status").val();
		var	url = getRootPath()+"/machine/exchange/save.action";
		var formData = $("#monitorForm1").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				monitorModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/project/monitor/manageForm.action?id="+idd+"&status="+statuss+"","","/project/monitor/manageForm.action?id="+idd+"&status="+statuss+"");
							}
					});
					$("#token").val(data.token);
				} 
			},
		});
};
//添加附件
monitorModule.addFormParameters = function (formData){
	var fileids = new Array();
	$.each($("input[name='fileid']"), function(i, o) {
		fileids.push(o.value);
	});
	formData.push({"name": "fileids", "value": fileids});
	formData.push({"name":"delattachIds","value":$("#delattachIds").val()});//修改时物理删除无用文件的id
};
//清空表单数据
monitorModule.clearForm = function(){
	$('#monitorForm')[0].reset();
	hideErrorMessage();
};

var getUE = function(){
	 UE.getEditor("operate",{
		initialFrameHeight : 200,
		initialFrameWidth : "100%",
		toolbars: [
           [
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
            'insertimage', //插入图片
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
            'lineheight', //行间距
            'edittip ', //编辑提示
            'emotion', //表情
            'spechars', //特殊字符
            'cleardoc', //清空文档
           // 'fullscreen', //全屏
            ]
       ],
       insertorderedlist:{        
               'decimal' : '' 
           },//有序列表
       insertunorderedlist : {          
       'disc' : '',    // '● 小圆点'
       }
	});	
};

monitorModule.spCall = function(data, controlId) {
	//id
	var id = "";
	//姓名
	var name = "";
	//dataJson
	var userJson = "";
	//遍历数据
	if(data != null || data.length > 0){
		$.each(data, function(i, val){
			id += val.id + ",";
			name += val.text + ",";
			userJson += "{id:"+val.id+", text:'"+val.text+"'},";
		});
	}
	$('#userJson').val("[" + userJson.substring(0, userJson.length-1) + "]");  //用于数据回显
	$("#"+controlId).val(name.substring(0, name.length-1));
	$("#membersId").val(id.substring(0, id.length-1));
	//清空验证信息
	hideErrorMessage();
};

$(document).ready(function(){
	//getUE();
});