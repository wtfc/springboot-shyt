////////////////////////////////////////////////////////////////////原common.js内容

var tokenMessage = "请不要重复提交";
var concurrentMessage = "并发提交出错";

if(typeof(rootPath) == "undefined"){
	var rootPath = "";
}

function setRootPath(path){
	rootPath = path;
}

function getRootPath() {
    return rootPath;
}

if(typeof(sysTheme) == "undefined"){
	var sysTheme = "0";
}

function setTheme(theme){
	sysTheme = theme;
}

function getTheme(){
	return sysTheme;
}
//判断当前左菜单是否隐藏
var isConcealMenu = false;

/**
 * ie6-8在数组上增加indexOf方法
 */
if (!$.support.leadingWhitespace) {
	if (!Array.prototype.indexOf)
	{
	  Array.prototype.indexOf = function(elt /*, from*/)
	  {
	    var len = this.length >>> 0;
	
	    var from = Number(arguments[1]) || 0;
	    from = (from < 0)
	         ? Math.ceil(from)
	         : Math.floor(from);
	    if (from < 0)
	      from += len;
	
	    for (; from < len; from++)
	    {
	      if (from in this &&
	          this[from] === elt)
	        return from;
	    }
	    return -1;
	  };
	}
}

//根据name获取json数组对应的value
Array.prototype.getValue = function (name){
	for (var i=0; i < this.length; i++) {
        if (this[i].name == name) {
            return this[i].value;
        }
    }
};

//将表单序列化数组转成json格式
function serializeJson(array){
    var serializeObj={};
    $(array).each(function(){

        if(serializeObj[this.name]){
            if($.isArray(serializeObj[this.name])){
                serializeObj[this.name].push(this.value);
            }else{
                serializeObj[this.name]=[serializeObj[this.name],this.value];
            }
        }else{
            serializeObj[this.name]=this.value;
        }
    });
    return serializeObj;
};

//获取token
function getToken(formId) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/system/token.action",
		data : null,
		cache : false,
		dataType : "json",
		success : function(data) {
			if (data) {
				if(formId){
					$("#"+formId+" #token").val(data.token);
				} else {
					$("#token").val(data.token);
				}
			}
		}
	});
};

//显示表单错误信息
function showErrors(formId, errorMessage){
	$.each(errorMessage, function(i, o) {
		var parent_ = $("#"+formId+" [id='"+o.code+"']").parent();
		var label_ = parent_.children("label");
		if(label_.length == 0){
			$("<label>")
			.attr("for", o.code)
			.addClass("error")
			.html(o.message || "")
			.insertAfter($("#"+formId+" [id='"+o.code+"']"));
		} else {
			label_.html(o.message);
			label_.css('display','block');
		}
	});
}

//清除验证信息
function hideErrorMessage(){
	$("label.error").remove();
	$(".error").removeClass("error");
}

function alertx(mess){
	msgBox.tip({content:mess});
}

//确认对话框
function confirmx(mess,fnCall){
	var opt = {
		content:mess,
		success:function(){
			fnCall();
		}
	}
	msgBox.confirm(opt);
	return false;
}

//流程打开表单
function openform(action,workid,dataid,entrance,type,beforeQuery){
	if(beforeQuery!=null&&beforeQuery.length>0){
		if(!beforeQuery(dataid)){
			return;
		}
	}
	if(type==null||type.length==0){
		type = "todo";
	}
	if(action==null){
		action="/workFlow/form/openformToAction.action"
	}
	if(action.indexOf("?")==-1){
		action+="?entrance="+entrance+"&scanType="+type+"&workId="+workid;
	}else{
		action+="&entrance="+entrance+"&scanType="+type+"&workId="+workid;
	}
	loadrightmenu(action,"unknown");}

//流程打开表单(不是相关人)
function openformNoPerson(workid,dataid,entrance,type){
	if(type==null||type.length==0){
		type = "todo";
	}
	loadrightmenu("/workFlow/form/openFormWithNoPerson.action?entrance="+entrance+"&scanType="+type+"&workId="+workid,"unknown");
}

//流程初始化函数
//判断流程状态选择框
function initWorkFlowStatus(formId){
	var entrance = $("#entrance").val();
	var tdEle = null;
	if(formId!=null&&formId.length>0){
		tdEle = $("#"+formId+" #processStatus");
	}else{
		tdEle = $("#processStatus");
	}
	
	if(entrance=="workflow"&&tdEle.length>0){
		tdEle = tdEle.parent();
		var entranceType = $("#entranceType").val();
		if(entranceType=="todo"){
			var inputStr = "<input type='hidden' name='processStatus' id='processStatus' value='1'>";
			tdEle.html(inputStr);
			tdEle.append("待办");
		}else if(entranceType=="done"){
			var inputStr = "<input type='hidden' name='processStatus' id='processStatus' value='3'>";
			tdEle.html(inputStr);
			tdEle.append("已办");
		}else if(entranceType=="agent"){
			var inputStr = "<input type='hidden' name='processStatus' id='processStatus' value='99'>";
			tdEle.html(inputStr);
			tdEle.append("代办");
		}
	}
}

//获得流程列表相应的按钮打开表单
/**
 * opt:beforeQuery	//查看前判断
 * opt:deleteFun 	//删除业务对应数据
 */
function getWorkflowListButton(opt){
	var buttonStr = "";
	var openFormBtnStr = "";
	var buttonLeablStr = "查看";
	if(opt.beforeQuery==null){
		opt.beforeQuery ="";
	}
	if(opt.entranceType != "myBusiness"){
		if(opt.entranceType =="todo"||opt.entranceType =="done"){
			if(opt.processStatus.indexOf("1")!=-1){
				buttonLeablStr = "办理";
				openFormBtnStr = "<a class='a-icon i-new m-r-xs' href='javascript:void(0)' onclick='openform(\""+opt.action+"\",\""+opt.workId+"\",\""+opt.workflowId+"\",\""+opt.entrance+"\",\"todo\")' >"+buttonLeablStr+"</a>";
			}else{
				openFormBtnStr = "<a class='a-icon i-new m-r-xs' href='javascript:void(0)' onclick='openform(\""+opt.action+"\",\""+opt.workId+"\",\""+opt.workflowId+"\",\""+opt.entrance+"\",\"done\",\""+opt.beforeQuery+"\")' >"+buttonLeablStr+"</a>";
			}
		}else{
			if(opt.noPerson==true){
				openFormBtnStr = "<a class='a-icon i-new m-r-xs' href='javascript:void(0)' onclick='openformNoPerson(\""+opt.workId+"\",\""+opt.workflowId+"\",\""+opt.entrance+"\",\"view\",\""+opt.beforeQuery+"\")' >"+buttonLeablStr+"</a>";
			}else{
				openFormBtnStr = "<a class='a-icon i-new m-r-xs' href='javascript:void(0)' onclick='openform(\""+opt.action+"\",\""+opt.workId+"\",\""+opt.workflowId+"\",\""+opt.entrance+"\",\"view\",\""+opt.beforeQuery+"\")' >"+buttonLeablStr+"</a>";
			}
			
		}
	}else{
		if(opt.flowStatus == "0"){
			buttonLeablStr = "申请";
			openFormBtnStr = "<a class='a-icon i-new m-r-xs' href='javascript:void(0)' onclick='openform(\""+opt.action+"\",\""+opt.workId+"\",\""+opt.workflowId+"\",\""+opt.entrance+"\",\"qicao\")' >"+buttonLeablStr+"</a>";
		}else{
			openFormBtnStr = "<a class='a-icon i-new m-r-xs' href='javascript:void(0)' onclick='openform(\""+opt.action+"\",\""+opt.workId+"\",\""+opt.workflowId+"\",\""+opt.entrance+"\",\"view\",\""+opt.beforeQuery+"\")' >"+buttonLeablStr+"</a>";
		}
	}
	 
	buttonStr = openFormBtnStr + "<a class='a-icon i-new m-r-xs view-history' href='javascript:void(0)' onclick='window.open(\""+getRootPath()+"/workFlow/processHistory/getHistoryDetail.action?workId="+opt.workId+"\")' >流程历史</a>";
	
	if(opt.showDeleteBtn == true&&opt.flowStatus!=null){
		if(opt.flowStatus=="0"){
			buttonStr += "<a class='a-icon m-r-xs i-remove' href='javascript:void(0)' onclick=\"javascript:"+opt.deleteFun+"\"><i class='fa fa-remove' data-toggle='tooltip' data-placement='top' title='' data-container='body' data-original-title='删除'></i></a>";
		}
	}
	return buttonStr;
}

function isIE(){
    return navigator.appName.indexOf("Microsoft Internet Explorer")!=-1 && document.all;
}

function isIE6() {
    return navigator.userAgent.toLowerCase().indexOf("msie 6.0")=="-1"?false:true;
}

function isIE7(){
    return navigator.userAgent.toLowerCase().indexOf("msie 7.0")=="-1"?false:true;
}

function isIE8(){
    return navigator.userAgent.toLowerCase().indexOf("msie 8.0")=="-1"?false:true;
}

function isIE11(){
    return navigator.appName.indexOf("Netscape")!=-1
}

function isNN(){
   return navigator.userAgent.indexOf("Netscape")!=-1;
}

function isOpera(){
    return navigator.appName.indexOf("Opera")!=-1;
}

function isFF(){
    return navigator.userAgent.indexOf("Firefox")!=-1;
}

function isChrome(){
    return navigator.userAgent.indexOf("Chrome") > -1;
}

/**
 * 比较两个id（逗号分隔）字串差异，返回新增部分
 * @param a
 * @param b
 */
function betw(a,b)
{
var as = a.split(",");
var bs = b.split(",");

var r = new Array();
var rc =0;
for(var i=0;i<bs.length;i++)
{
	var c =0;
	for(var j=0;j<as.length;j++)
	{
		if(bs[i] == as[j])
		{
			c++;
		}
	}
	if(c==0)
	{
		r[rc++] = bs[i];	
	}	
}
return r;
}

/**
 * 格式化人员字符串，返回人员格式数组
 */
function formatUser(userStr){
	var result = new Array();
	var users = userStr.split(",");
	for(var i=0;i<users.length;i++){
		var userItem = {};
		userItem.id = users[i].split(":")[0];
		userItem.name = users[i].split(":")[1];
		result.push(userItem);
	}
	return result;
}

/**
 * IE8下菜单提供跳转不起作用,可以暂时调用这个方法替代
 * @param url 跳转的URL
 */
function jump(url){
	$.ajax({
		url: url,
		type: "post",
		success : function(data){
			$("#scrollable").html(data);
		}
	});
}

/**
 * 将textarea里的内容转换为hmtl
 */
function formatToHtml(str){
	var string=str.replace(/\r\n/g,"<br>")  
    string=string.replace(/\n/g,"<br>"); 
	return string;
}

/**
 * 将html里的内容转换为textarea的内容
 */
function formatToValue(str){
	var string=str.replace(/<br>/g,"\r\n");
	string=string.replace(/<BR>/g,"\r\n")
	return string;
}

/**
 * 根据节点获取子节点信息
 * @param node
 * @returns
 */
function getChildNodesId(node, treeDivId){
	var treeObj = $.fn.zTree.getZTreeObj(treeDivId);
	var childNodes = treeObj.transformToArray(node);
    var nodes = "";
    for(var i = 0; i < childNodes.length; i++) {
    	if(childNodes[i].type == 0){
    		nodes += childNodes[i].id + ",";
    	}
    }
    return nodes.length>0?nodes.substring(0, nodes.length-1):null;
}

function treeHightReset(treeId){
	if($("#"+treeId)[0]){
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

////////////////////////////////////////////////////////////////////原extendFunction.js内容

/**
 * 转义字符串的html标签
 */
$.extend(
		{ 
			escapeHtml : function(text) {
				  return text?$('<p/>').text(text).html():text;
			}
		}
);

$.extend({
	isAlpha:function(s){
		return /[a-z|A-Z]/.test(s);
	}
});

$.extend({
	hasSpecialChar:function(s,charset){
		//var charset = "@#$%&*~";
		var specialCharsetRegExp =  new RegExp(/[\@\#\$\%\&\*\~]+/);
		if(typeof charset ==="string"){
			var str = "";
			for(i=0;i<charset.length;i++){
				if(!$.isAlpha(charset.charAt(i))){
					str+="\\"+charset.charAt(i);
				}
			}
			specialCharsetRegExp = new RegExp("["+str+"]+");
		}
		if(!s){
			return false;
		}
		else{
			return specialCharsetRegExp.test(s);
		} 
	}
	
});

$.extend({
	null2Blank:function(s){
		//var charset = "@#$%&*~";
		if(s==null || typeof(s)=== "undefined"){
			return "";
		}
		else{
			return s;
		}
	}
	
});
$.extend({
	blank2HtmlBlank:function(s){
		//var charset = "@#$%&*~";
		if(s==null || typeof(s)=== "undefined"){
			return "";
		}
		else{
			return s.replace(/\s/g,"&nbsp;");
		}
	}
});

//////////////////////////////////////////////////////////////////原msgBox.js内容

/**
 * 提示框相关js
 */
var msgBox = {};

/**
 * 提示框(长信息)
 * opt:content:警告内容
 * 	   type:类型success/fail
 */
msgBox.info = function(opt){
	if(opt.type==null){
		opt.type="fail";
	}
	if(opt.callback!=null){
		jBox.setDefaults({ defaults: {closed:opt.callback} });
	}else{
		jBox.setDefaults({ defaults: {closed:function(){}} });
	}
	if(opt.type=="success"){
		jBox.info('',opt.content);
	}else{
		jBox.error('', opt.content,'S');
	}
	ie8InRounded();
}


/**
 * 提示框(短信息)
 * opt:type:类型success/fail
 * 	   content:内容
 */
msgBox.tip = function(opt){
	if(opt.type==null){
		opt.type="fail";
	}
	if(opt.callback!=null){
		jBox.setDefaults({ defaults: {closed:opt.callback} });
	}else{
		jBox.setDefaults({ defaults: {closed:function(){}} });
	}
	if(opt.type=="success"){
		jBox.tip(opt.content,'',{
			closed:function(){
				if(opt.callback!=null){
					opt.callback();
				}
			}	
		});
	}else{
		jBox.tip(opt.content,'error',{
			closed:function(){
				if(opt.callback!=null){
					opt.callback();
				}
			}	
		});
	}
	ie8InRounded();
}


/**
 * 确认对话框
 * opt:content:提示内容
 * 	   success:确认时回调函数
 * 	   noback :点击离开时回调函数
 * 	   cancel:取消时回调函数
 * 	   fontSize:B/S
 * 	   buttons:  按钮true/false 样例：{"是":"yes","否":"no","取消":"cancel"};
 * 
 *     按钮默认不添加只显示-> 是 否 添加参数后会覆盖，
 *     如需要3个按钮添加值为cancel的按钮名称和noback回调函数 
 */
msgBox.confirm = function(opt){
	//opt.buttons = {"保存并且继续":"yes", "取消":"no"}
	if(opt.buttons == null || opt.buttons == undefined){
		opt.buttons = {"是":"yes","否":"no"};
	}
	jBox.setDefaults({ defaults: {closed:function(){},buttons:opt.buttons}});
	var submit = function(v,h,f){
		switch (v) {
		case 'yes':
			if(opt.success!=null){
				 opt.success();
			}
			break;
		case 'no':
			if(opt.cancel!=null){
        		opt.cancel();
			}
			break;
		case 'cancel':
			if(opt.noback!=null){
        		opt.noback();
			}
			break;
		}

	};
	var fontSize = "";
	if(opt.fontSize=="S"||opt.fontSize=="s"){
		 fontSize = "S";
	}
	jBox.warning("", opt.content, submit,fontSize);
	ie8InRounded();
};

//////////////////////////////////////////////////////////////////原dic.js内容

/**
 * 字典相关js
 */
var dic = {};

/**
 * @description 获得启动的字典，并放到固定的select中
 * @param elementId 字典对应select组件的id
 * @param typeCode 字典类型code
 * @param async为true时为同步请求
 * @param defaultType 1:请选择  2：全部 3：无
 */
dic.fillDics = function(elementId,typeCode,async,defaultType){
	if(typeCode==null||typeCode.length==0){
		//alert("字典类型为空");
		return;
	}
	var selectObj = $('#'+elementId);
	if(selectObj==null||selectObj.length==0){
		//alert("没有对应的的组件,id为"+elementId);
		return;
	}
	var ajaxData = {
		typeCode:typeCode,
		time:new Date()
	};
	
	//改为同步
//	$.ajaxSetup({ 
//	    async : false 
//	});
	
	$.ajax({
		url: getRootPath()+"/dic/getDics.action",
		type: "GET",
		async: false,
		success:function(data){
			dic.fillElement(selectObj,data,defaultType)},
		data:ajaxData
	});
/*	$.get(getRootPath()+"/dic/getDics.action",ajaxData,function(data){
		dic.fillElement(selectObj,data,defaultType);
	});*/
};

/**
 * @description 获得所有字典(包含未启动的)，并放到固定的select中
 * @param elementId 字典对应select组件的id
 * @param typeCode 字典类型code
 * @param defaultType 1:请选择  2：全部 3：无
 */
dic.fillAllDics = function(elementId,typeCode,defaultType){
	if(typeCode==null||typeCode.length==0){
		//alert("字典类型为空");
		return;
	}
	var selectObj = $('#'+elementId);
	if(selectObj==null||selectObj.length==0){
		//alert("没有对应的的组件,id为"+elementId);
		return;
	}
	var ajaxData = {
		typeCode:typeCode,
		time:new Date()
	};
	$.get(getRootPath()+"/dic/getDicsAll.action",ajaxData,function(data){
		dic.fillElement(selectObj,data,defaultType);
	});
};

/**
 * @description 获得启动的字典，并放到固定的select中
 * @param elementId 字典对应select组件的id
 * @param data 字典项数据
 */
dic.fillElement = function(selectObj,data,defaultType){
	selectObj.html("");
	if(defaultType==null){
		defaultType == "1";
	}
	if(defaultType=="1"||defaultType==1){
		defaultType = "1";
		selectObj.html("<option value=''>-请选择-</option>");
	}else if(defaultType=="2"||defaultType==2){
		defaultType = "2";
		selectObj.html("<option value=''>-全部-</option>");
	}else if(defaultType=="3"||defaultType==3){
		defaultType = "3";
		selectObj.html("");
	}
	
	var selectValue = selectObj.attr("code");
	for(var i=0;i<data.length;i++){
		var selectStr = "";
		if(selectValue==null||selectValue.length==0){
			if(defaultType!="2"&&data[i].defaultValue == 1){
				selectStr = "selected";
			}
		}
		if(selectValue!=null&&selectValue.length>0){
			selectStr = "selected";
		}
		
		var optionStr = "<option value='"+data[i].code+"' "+selectStr+">"+data[i].value+"</option>";
		selectObj.append(optionStr);
	}
};

/**
 * @description 获得启动的字典，并返回toString
 * @param typeCode 字典类型code
 */
dic.toString = function(typeCode){
	if(typeCode){
		var ajaxData = {typeCode:typeCode,time:new Date()};
		var html = "<option value=''>请选择</option>";
		$.ajax({
			type : "GET",
			url : getRootPath()+"/dic/getDics.action",
			async:false,
			data : ajaxData,
			dataType : "json",
			success : function(data) {
				if (data) {
					var selectStr = "";
					for(var i=0;i<data.length;i++){
						if(data[i].defaultValue == 1){
							selectStr = "selected";
						}
						var optionStr = "<option value='"+data[i].code+"' "+selectStr+">"+data[i].value+"</option>";
						html = html.concat(optionStr);
					}
				}
			}
		});
		return html;
	}
};

/**
 * 多级联动下拉菜单方法
 * @param[*]代表必填项
 * @param[*] typeCode 字典码
 * @param[*] selectId 被替换select组件的id值
 * @param[*] multistId 被替换的下拉菜单区域id值
 * @param selectValue 被选中的下拉值
 */
dic.multistepDropDownCache = function(typeCode,selectId){
	$("#"+selectId).empty();
	$("#"+selectId).html($("#"+typeCode).html());
};

/**
 * 多级联动下拉菜单方法
 * @param[*]代表必填项
 * @param[*] typeCode 字典码
 * @param[*] selectId 被替换select组件的id值
 * @param selectValue 被选中的下拉值
 */
dic.multistepDropDown = function(typeCode,selectId,selectValue){
	$("#"+selectId).empty();
	$.ajax({
        type: 'post',
        async: false,
        dataType : 'json',
        url: getRootPath()+"/dic/getDicJSONInfo.action?typeCode="+typeCode+"&time="+new Date(),
        success: function (json) {
        	$(json).each(function(){
        		var opt = $("<option/>").text(this.value).attr("value", this.code); 
        		if(this.code == selectValue)
        			opt = $("<option/>").text(this.value).attr("value", this.code).attr('selected',true); 
        		$("#"+selectId).append(opt);
        	});
        }
    });
};


//////////////////////////////////////////////////////////////////原workFlowUtils.js内容

var workFlow = {};
//打开流程模板图
workFlow.openProcessDefinitionMap = function(flowId){
	window.open(getRootPath()+"/horizon/workflow/HorizonSummaryInstance.jsp?only=true&flowid="+flowId+"&identifier=system");
};

//////////////////////////////////////////////////////////////////原menu.js内容

$(document).ready(function() {
    $("#dataLoad").fadeOut(); //页面加载完毕后即将DIV隐藏
    ie8InRounded();
});
	/**
	 * url:链接
	 * oldcrumbs:原页面包屑
	 * newcrumbs:参数值为 方式1.unknown自动获取跳转页面h1中信息 方式2.为空则根据资源菜单进行查询组装 方式3.其他数值则在跳转页自动拼装 方式4.copy自动拷贝上页路径信息
	 * （注：采用方式1和方式3时h1中内容需要自行填写，必填）
	 */
	loadrightmenu =function(url,newcrumbs,loadmenubarurl){//加载右侧功能界面方法
		if(typeof(window.parent.valPageSave) != 'undefined' && window.parent.valPageSave.value != null
				&& window.parent.valPageSave.value == 'true'){
			var opt = {
				content:$.i18n.prop("JC_OA_IC_096"),
				success:function(){
					if(document.getElementById('mainFrame') != null){
						var mainWindow = document.getElementById('mainFrame').contentWindow;
						mainWindow.pageMethon();//名称统一 ，方法内具体实现使用人自行开发
					}
					window.parent.valPageSave.value = "false";
					
					loadMenuMethon(url,newcrumbs,loadmenubarurl);
				},
				noback:function(){
					window.parent.valPageSave.value = "false";
					loadMenuMethon(url,newcrumbs,loadmenubarurl);
				},
				cancel:function(){
					anchormenu(window.parent.loadmenubarurl.value);
				},
				buttons:{"离开并存草稿":"yes", "取消":"no","离开":"cancel"}
			};
			msgBox.confirm(opt);
			
			/*if(confirm("您正在操作的内容还没有保存，确认离开吗？")){
				if(document.getElementById('mainFrame') != null){
					var mainWindow = document.getElementById('mainFrame').contentWindow;
					mainWindow.pageMethon();//名称统一 ，方法内具体实现使用人自行开发
				}
				window.parent.valPageSave.value = "false";
			}else {
				return false;
			}*/
		} else {
			loadMenuMethon(url,newcrumbs,loadmenubarurl);
		}
		
	};

	loadMenuMethon = function(url,newcrumbs,loadmenubarurl){
		if(url == null || url == ''){
			$("#scrollable").html('');
			$("#dataLoad").fadeOut();   //页面加载完毕后即将DIV隐藏
			return false;
		}
		
		if(newcrumbs != null && newcrumbs != ''){
			if(typeof(window.parent.newcrumbs) != 'undefined'){
				window.parent.newcrumbs.value = newcrumbs;
				window.parent.oldcrumbs.value = $(".crumbs").html();
				if($(".con-heading >h1").html() != undefined && $(".con-heading >h1").html() != ''){
					window.parent.copycrumbs.value = $(".con-heading >h1").html();
				}else if($(".con-header >h1").html() != undefined && $(".con-header >h1").html() != ''){
					window.parent.copycrumbs.value = $(".con-header >h1").html();
				}
			}
		}
		
		if(loadmenubarurl != null && loadmenubarurl != ''){
			if(typeof(window.parent.loadmenubarurl) != 'undefined' ){
				window.parent.loadmenubarurl.value = loadmenubarurl;
				if(typeof(window.parent.loadmenubarid) != 'undefined')
					window.parent.loadmenubarid.value = 0;
			}
			anchormenu(loadmenubarurl);
		}
		
		//记录跳转url
		if(parent.historyUrl!=null){
			parent.historyUrl.push({
				url:url,
				newcrumbs:newcrumbs,
				loadmenubarurl:loadmenubarurl
			});
		}
		
		/*window.parent.mainFrame.src = 'about:blank';
		if(navigator.userAgent.indexOf("MSIE")>0){ //是否是IE浏览器
			if(navigator.userAgent.indexOf("MSIE 8.0")>0){//IE8.0
				window.parent.mainFrame.document.write('');
				window.parent.mainFrame.document.clear();
			}
		}*/
		window.parent.mainFrame.location.href=getRootPath()+url;
		//window.parent.mainFrame.location.replace(getRootPath()+url);
	};
	
	/**
	 * url:链接
	 * oldcrumbs:原页面包屑
	 * newcrumbs:参数值为 方式1.unknown自动获取跳转页面h1中信息 方式2.为空则根据资源菜单进行查询组装 方式3.其他数值则在跳转页自动拼装 方式4.copy自动拷贝上页路径信息
	 * （注：采用方式1和方式3时h1中内容需要自行填写，必填）
	 */
	loadrightmenuFordiary =function(url,newcrumbs,loadmenubarurl){//加载右侧功能界面方法
		
		if(url == null || url == ''){
			$("#scrollable").html('');
			$("#dataLoad").fadeOut();   //页面加载完毕后即将DIV隐藏
			return false;
		}
		
		if(newcrumbs != null && newcrumbs != ''){
			if(typeof(window.parent.newcrumbs) != 'undefined'){
				window.parent.newcrumbs.value = newcrumbs;
				window.parent.oldcrumbs.value = $(".crumbs").html();
				if($(".con-heading >h1").html() != undefined && $(".con-heading >h1").html() != ''){
					window.parent.copycrumbs.value = $(".con-heading >h1").html();
				}else if($(".con-header >h1").html() != undefined && $(".con-header >h1").html() != ''){
					window.parent.copycrumbs.value = $(".con-header >h1").html();
				}
			}
		}
		
		if(loadmenubarurl != null && loadmenubarurl != ''){
			if(typeof(window.parent.loadmenubarurl) != 'undefined' ){
				window.parent.loadmenubarurl.value = loadmenubarurl;
				if(typeof(window.parent.loadmenubarid) != 'undefined')
					window.parent.loadmenubarid.value = 0;
			}
			anchormenu(loadmenubarurl);
		}
		
		//记录跳转url
		if(parent.historyUrl!=null){
			parent.historyUrl.push({
				url:url,
				newcrumbs:newcrumbs,
				loadmenubarurl:loadmenubarurl
			});
		}
		
		window.parent.mainFrame.src = 'about:blank';
		if(navigator.userAgent.indexOf("MSIE")>0){ //是否是IE浏览器
			if(navigator.userAgent.indexOf("MSIE 8.0")>0){//IE8.0
				window.parent.mainFrame.document.write('');
				window.parent.mainFrame.document.clear();
			}
		}
		window.parent.mainFrame.location.href=getRootPath()+url;
		//window.parent.mainFrame.location.replace(getRootPath()+url);
	};
	
	/**
	 * 锚点定位方法
	 */
	anchormenu = function(url){
    	jQuery.ajax({
			url : getRootPath()+"/sys/menu/anchorAvigation.action?id=0&actionName="+url+"&time="+new Date(),
			type : 'post',
			async: false,
			success : function(data,textStatus, xhr) {
				var menulist = data.navigationMenuList;
				//alert(menulist != undefined);
				if(menulist != undefined && menulist.length > 0){
					var val = 'menu'+menulist[0].id;
					//closejcGOA_menu();
		    		$(".menus",parent.document).each(function(i) {
		                if($(this).attr("id") == val){
		                	$(this).css('display','');
		                } else {
		                	$(this).css('display','none');
		                }
		            });
		    		
		            $(".ulmenus",parent.document).each(function(i) {
		                $(this).css('display','none');
		            });
		            
		            $(".liAMneu",parent.document).each(function(i) {
			            $(this).removeClass('active');
			        });
		            	
		    		var menulistsize = menulist.length;
		    		for(var i=0;i<menulistsize;i++){
		    			$("li[name=menu"+menulist[i].id+"]",parent.document).siblings().removeClass('active');
		    			$("li[name=menu"+menulist[i].id+"]",parent.document).addClass('active');
		    			$('#floor'+menulist[i].id,parent.document).addClass('active');
		    			if(typeof($('#ul'+menulist[i].id,parent.document)) != 'undefined'){
		    				$('#ul'+menulist[i].id,parent.document).css("display","block");
		    			}
		    			if(i == 3){
		    				window.parent.loadmenubarName3.value = menulist[i].name;
		    			}else if(i == 2){
		    				window.parent.loadmenubarName2.value = menulist[i].name;
		    			}else if(i == 1){
		    				window.parent.loadmenubarName1.value = menulist[i].name;
		    			}else if(i == 0){
		    				window.parent.loadmenubarName.value = menulist[i].name;
		    			}
		    		}
				}
			},error:function (XMLHttpRequest, textStatus, errorThrown){
				alert(XMLHttpRequest.readyState);
				alert(XMLHttpRequest.status);
		 	}
		});
    };
	
	/**
	 * 返回跳转页面(不记录跳转url)
	 * url:链接
	 * oldcrumbs:原页面包屑
	 * newcrumbs:参数值为 方式1.unknown自动获取跳转页面h1中信息 方式2.为空则根据资源菜单进行查询组装 方式3.其他数值则在跳转页自动拼装 方式4.copy自动拷贝上页路径信息 方式5.copyforurl根据url查询信息后拼装路径
	 * （注：采用方式1和方式3时h1中内容需要自行填写，必填）
	 */
	loadrightmenuForback =function(url,newcrumbs,loadmenubarurl){//加载右侧功能界面方法
		
		if(url == null || url == ''){
			$("#scrollable").html('');
			$("#dataLoad").fadeOut();   //页面加载完毕后即将DIV隐藏
			return false;
		}
		
		if(newcrumbs != null && newcrumbs != ''){
			if(typeof(window.parent.newcrumbs) != 'undefined'){
				window.parent.newcrumbs.value = newcrumbs;
				window.parent.oldcrumbs.value = $(".crumbs").html();
			}
		}
		
		if(loadmenubarurl != null && loadmenubarurl != ''){
			if(typeof(window.parent.loadmenubarurl) != 'undefined' ){
				window.parent.loadmenubarurl.value = loadmenubarurl;
				if(typeof(window.parent.loadmenubarid) != 'undefined')
					window.parent.loadmenubarid.value = 0;
			}
		}
		
		window.parent.mainFrame.location.href=getRootPath()+url;
	};
	
	/**
	 * 返回方法
	 */
	goBack = function(){
		var historyObj = null;
		if(parent.historyUrl!=null){
			parent.historyUrl.pop();
			if(parent.historyUrl.length==0){
				historyObj ={
					url:"/sys/portal/manageView.action?portalId=8&portalType=ptype_org"
				};
				loadrightmenuForback(historyObj.url,historyObj.newcrumbs,historyObj.loadmenubarurl);
				parent.loadleftMenu1('1');
			}else{
				historyObj = parent.historyUrl[parent.historyUrl.length-1];
				loadrightmenuForback(historyObj.url,historyObj.newcrumbs,historyObj.loadmenubarurl);
			}
		}
	};
	
	navigationbar = function(menuid,url){//加载右侧导航栏方法 //跳转页面链接
		$.ajaxSetup ({
			cache: false //设置成false将不会从浏览器缓存读取信息
		});
		$(".loading").each(function() {
			$(this).fadeOut();
		});
		if(typeof(window.parent.newcrumbs) != 'undefined' && window.parent.newcrumbs.value != null && window.parent.newcrumbs.value != '' 
			&& typeof(window.parent.oldcrumbs) != 'undefined' && window.parent.oldcrumbs.value != null 
			&& window.parent.oldcrumbs.value != 'undefined' && window.parent.oldcrumbs.value != ''){
			if(window.parent.newcrumbs.value == 'unknown'){
				if($(".con-heading >h1").html() != undefined && $(".con-heading >h1").html() != ''){
					$(".crumbs").html(window.parent.oldcrumbs.value+"<i></i>"+$(".con-heading >h1").html());
					window.parent.newcrumbs.value = "";
					window.parent.oldcrumbs.value = "";
					window.parent.copycrumbs.value = "";
					return;
				}else if($(".con-header >h1").html() != undefined && $(".con-header >h1").html() != ''){
					$(".crumbs").html(window.parent.oldcrumbs.value+"<i></i>"+$(".con-header >h1").html());
					window.parent.newcrumbs.value = "";
					window.parent.oldcrumbs.value = "";
					window.parent.copycrumbs.value = "";
					return;
				}
			}else if(window.parent.newcrumbs.value == 'copy'){
				$(".crumbs").html(window.parent.oldcrumbs.value);
				$(".con-heading >h1").html(window.parent.copycrumbs.value);
				$(".con-header >h1").html(window.parent.copycrumbs.value);
				window.parent.newcrumbs.value = "";
				window.parent.oldcrumbs.value = "";
				window.parent.copycrumbs.value = "";
				return;
			}else {
				$(".con-heading >h1").html(window.parent.newcrumbs.value);
				$(".con-header >h1").html(window.parent.newcrumbs.value);
				$(".crumbs").html(window.parent.oldcrumbs.value+"<i></i>"+window.parent.newcrumbs.value);
				window.parent.newcrumbs.value = "";
				window.parent.oldcrumbs.value = "";
				window.parent.copycrumbs.value = "";
				return;
			}
		}
		
		if(typeof(window.parent.loadmenubarurl) != 'undefined' && window.parent.loadmenubarurl.value != null){
			url = window.parent.loadmenubarurl.value;
			//window.parent.loadmenubarurl.value = "";
		}
		
		if((menuid == null || menuid == 0) && (url == null || url == '' || url == 'null')){
			$("#dataLoad").fadeOut();   //页面加载完毕后即将DIV隐藏
			return;
		}
		
		if(menuid != null && menuid != 0){
			var floors = window.parent.loadmenubarFloors.value;
			if(floors == 2){
				window.parent.loadmenubarName3.value = "";
			}else if(floors == 1){
				window.parent.loadmenubarName2.value = "";
				window.parent.loadmenubarName3.value = "";
			}else if(floors == 0){
				window.parent.loadmenubarName1.value = "";
				window.parent.loadmenubarName2.value = "";
				window.parent.loadmenubarName3.value = "";
			}
			var headone = '<a href="#" onclick="homeloadmenu()">首页</a>';
			var headtwo = window.parent.loadmenubarName.value;
			var headthree = window.parent.loadmenubarName1.value;
			var headfout = window.parent.loadmenubarName2.value;
			var headfive = window.parent.loadmenubarName3.value;
			
			var returnstr = "";
			if(headfive != null && headfive != ""){
				returnstr = "<h1>"+headfive+"</h1>"+"<div class=\"crumbs\">"+headone+"<i></i>"
						+headtwo+"<i></i>"+headthree+"<i></i>"+headfout+"<i></i><span>"+headfive+"</span></div>";
			}else if(headfout != null && headfout != ""){
				returnstr = "<h1>"+headfout+"</h1>"+"<div class=\"crumbs\">"+headone+"<i></i>"
						+headtwo+"<i></i>"+headthree+"<i></i><span>"+headfout+"</span></div>";
			}else if(headthree != null && headthree != ""){
				returnstr = "<h1>"+headthree+"</h1>"+"<div class=\"crumbs\">"+headone+"<i></i>"
						+headtwo+"<i></i><span>"+headthree+"</span></div>";
			}else if(headtwo != null && headtwo != ""){
				returnstr = "<h1>"+headtwo+"</h1>"+"<div class=\"crumbs\">"+headone+"<i></i><span>"
						+headtwo+"</span></div>";
			}
			$("#navigationMenu").html(returnstr);
			return;
		}
		jQuery.ajax({
			url : getRootPath()+"/sys/menu/managenAvigation.action?id="+menuid+"&actionName="+url+"&time="+new Date(),
			type : 'POST',
			success : function(data) {
				var headtitle = "";
				if(typeof(window.parent.newcrumbs) != 'undefined' && window.parent.newcrumbs.value != null 
					&& window.parent.newcrumbs.value != '' && window.parent.newcrumbs.value == 'copyforurl'){
					if($(".con-heading >h1").html() != undefined && $(".con-heading >h1").html() != ''){
						headtitle = $(".con-heading >h1").html();
						$("#navigationMenu").html(data);//要刷新的div
						$(".con-heading >h1").html(headtitle);
						$(".crumbs").append("<i></i>"+headtitle);
					}else if($(".con-header >h1").html() != undefined && $(".con-header >h1").html() != ''){
						headtitle = $(".con-header >h1").html();
						$("#navigationMenu").html(data);//要刷新的div
						$(".con-header >h1").html(headtitle);
						$(".crumbs").append("<i></i>"+headtitle);
					}else {
						$("#navigationMenu").html(data);//要刷新的div
					}
				} else {
					$("#navigationMenu").html(data);//要刷新的div
				}
				$("#dataLoad").fadeOut();   //页面加载完毕后即将DIV隐藏
			}
		});
	};
	/*
	 * data:参数
	 */
	setUrlParameter = function(data){
		if(typeof(window.parent.urlParameter) != 'undefined')
			window.parent.urlParameter.value = data;
	};
	
	getUrlParameter = function(){
		if(typeof(window.parent.urlParameter) != 'undefined' && window.parent.urlParameter.value != null)
			return window.parent.urlParameter.value;
		else 
			return "";
	};
	
	//判断页面是否正常退出系统，不是进行提示；----start----
	function promptOnClose(e){  
		//var promptString = '提示：当前操作可能会造成数据丢失';  
	    //return promptString;  
	}  
	
	
	
	function turnVoice(obj){
    	if($(obj).hasClass("on")){
    		$.cookie('voiceSwitch', 'false'); 
    		$(obj).removeClass("on icon-voice").addClass("off icon-no-voice");
    	}else{
    		$.cookie('voiceSwitch', 'true'); 
    		$(obj).removeClass("off icon-no-voice").addClass("on icon-voice");
    	}
    }
	//播放声音
	function playVoice(obj){
		if($.cookie("voiceSwitch") == 'true'){
			if(isIE()){
				$("#"+obj).append("<embed hidden='true' height='0' width='0' src='"+getRootPath()+"/upload/voice/test.wav' /></embed>");
			}else{
				$("#"+obj).append("<object height='0' width='0' data='"+getRootPath()+"/upload/voice/test.wav'></object>");
			}
		}
	}
	
	function promptOnUnLoad(e){ 
		if(document.getElementById('mainFrame') != null){
			var mainWindow = document.getElementById('mainFrame').contentWindow;
			if(menuswrite.statue && typeof mainWindow.pageRedirecting!='undefined' ){
				mainWindow.pageRedirecting();//名称统一 ，方法内具体实现使用人自行开发
				menuswrite.statue = false;
			}
		}
	}  
	
	if (window != top) {  
		
	} else {
		/*if (window.Event) {  
			window.onbeforeunload = function(event){  
				return promptOnClose(event);  
			};  
		}  
		else {  
			window.onbeforeunload = function(){  
				return promptOnClose(event);  
			};  
		}*/
		if (window.Event) {  
			window.onunload  = function(event){  
				return promptOnUnLoad(event);  
			};  
		}  
		else {  
			window.onunload  = function(){  
				return promptOnUnLoad(event);  
			};  
		}
	}
	//判断页面是否正常退出系统，不是进行提示；----start----
	
	//判断当前用户是否已分配指定菜单并导向指定菜单-----start-----
	//参数menuid 为菜单id
	loadSpecifyMenu = function(menuid){
		var returnvalue;
		jQuery.ajax({
			url : getRootPath()+"/sys/menu/valUserMenu.action?id="+menuid,
			type : 'POST',
			async:false,
			success : function(data) {
				if(data.success == "false"){
					msgBox.info({content: "您未被分配此菜单，请<br/>联系管理员", type:'fail'});
					returnvalue = false;
				}else {
				//	loadrightmenu(data.menuVo.actionName);
				//	navigationbar(menuid);
					returnvalue = true;
				}
			},
			error:function(){
				$("#dataLoad").fadeOut();   //页面加载完毕后即将DIV隐藏
				returnvalue = false;
			}
		});
		return returnvalue;
	};
	//判断当前用户是否已分配指定菜单并导向指定菜单-----end-----
	
	//首页链接导入右侧界面---start----
	homeloadmenu = function(){
		window.parent.location.reload();
	};
	//首页链接导入右侧界面---end----
	
	//用于JSON.stringify的第二个参数，避免WinXP+IE8环境下转换JsonString时出现“null"的数据
	replaceJsonNull=function(key,value){
		var agent = navigator.userAgent.toLowerCase(); 
		if(agent.indexOf("msie 8.0")>0 ){
			if (Object.prototype.toString.apply(value) === '[object Array]'){
				return value;
			}
			else if(value==="" || value==null || value==="null"){
				return "";
			}
			else
				return value;
		}
		else
			return value;
	};
	
	//初始化
	jQuery(function($) 
	{
		if(typeof(window.parent.loadmenubarid) != 'undefined' && window.parent.loadmenubarid.value != null)
			navigationbar(window.parent.loadmenubarid.value);
		else {
			$(".loading").each(function() {
				$(this).fadeOut();
			});
		}
				$.type = function(o) { 
			        var _toS = Object.prototype.toString; 
			        var _types = { 
			            'undefined': 'undefined', 
			            'number': 'number', 
			            'boolean': 'boolean', 
			            'string': 'string', 
			            '[object Function]': 'function', 
			            '[object RegExp]': 'regexp', 
			            '[object Array]': 'array', 
			            '[object Date]': 'date', 
			            '[object Error]': 'error' 
			        }; 
			        return _types[typeof o] || _types[_toS.call(o)] || (o ? 'object' : 'null'); 
			    }; 
			    var $specialChars = { '\b': '\\b', '\t': '\\t', '\n': '\\n', '\f': '\\f', '\r': '\\r', '"': '\\"', '\\': '\\\\' }; 
			    var $replaceChars = function(chr) { 
			        return $specialChars[chr] || '\\u00' + Math.floor(chr.charCodeAt() / 16).toString(16) + (chr.charCodeAt() % 16).toString(16); 
			    }; 

			    $.toJSON = function(o) { 
			        var s = []; 
			        switch ($.type(o)) { 
			            case 'undefined': 
			                return 'undefined'; 
			                break; 
			            case 'null': 
			                return 'null'; 
			                break; 
			            case 'number': 
			            case 'boolean': 
			            case 'date': 
			            case 'function': 
			                return o.toString(); 
			                break; 
			            case 'string': 
			                return '"' + o.replace(/[\x00-\x1f\\"]/g, $replaceChars) + '"'; 
			                break; 
			            case 'array': 
			                for (var i = 0, l = o.length; i < l; i++) { 
			                    s.push($.toJSON(o[i])); 
			                } 
			                return '[' + s.join(',') + ']'; 
			                break; 
			            case 'error': 
			            case 'object': 
			                for (var p in o) { 
			                    s.push(p + ':' + $.toJSON(o[p])); 
			                } 
			                return '{' + s.join(',') + '}'; 
			                break; 
			            default: 
			                return ''; 
		               break; 
			        } 
		    }; 

			$.evalJSON = function(s) { 
			       if ($.type(s) != 'string' || !s.length) return null; 
			        return eval('(' + s + ')'); 
			}; 


	});
