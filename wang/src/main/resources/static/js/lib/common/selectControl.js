/**
 * 	依赖:	JQuery控件
 * 			zTree控件
 * 			structure自定义控件
 * 			ChinesePY汉字转拼音
 * 			select2选择控件
 * 
 * 	详见:[structure.jsp中引用的css与js]	
 * 
 * 	选择控件中有两种组件:[1.组织控件、2.组织人员控件.分别支持多选与单选]
 * 
 * 	选择控件调用方法:[
 * 		在相对应需要引入控件的页面引入依赖及其本控件的JS调用
 * 		selectControl.init = function(showControlDivId, controlId, isCheckOrRadio, isPersonOrOrg, orgOrDept, echoData);
 * 		参数:[
 *			showControlDivId 	是要显示本控件的DivID		注释:页面多次调用控件传入的值不能重复
 *			controlId 			是本控件中文本框的id与name	注释:页面多次调用控件传入的值不能重复[*规则是"id-name",id不要重名]以便后台取值
 *			isCheckOrRadio		是多选还是单选				注释:true:多选,flase:单选
 *			isPersonOrOrg		是人员还是组织				注释:true:多选,flase:单选
 *			orgOrDept			机构/部门					注释:1:机构,0:部门,“”或者null全部
 *			echoData			是回显数据					注释:没有回显数据可以不填
 * 		]
 * 		例子:[
 * 			组织调用:
 * 			1.单选
 * 				selectControl.init("controlTree1","id-name", false, false);
 * 			2.多选
 * 				selectControl.init("controlTree1","id-name", true, false);
 * 			3.回显
 * 			3.1.单值
 * 				selectControl.init("controlTree1","id-name", false, false, {id:value,text:value});
 * 			3.2.多值
 * 				selectControl.init("controlTree1","id-name", true, false, [{id:value,text:value},{id:value,text:value}]);
 * 			人员调用:
 * 			1.单选
 * 				selectControl.init("controlTree1","id-name", false, true);
 * 			2.多选
 * 				selectControl.init("controlTree1","id-name", true, true);
 * 			3.回显
 * 			3.1.单值
 * 				selectControl.init("controlTree1","id-name", false, true, {id:value,text:value});
 * 			3.2.多值
 * 				selectControl.init("controlTree1","id-name", true, true, [{id:value,text:value},{id:value,text:value}]);
 * 		]
 * 	]
 * 	
 * 	清除:[引用一次出现多个控件,初始化调用selectControl.clear方法]
 *  清值:[重置按钮中调用selectControl.clearValue方法传入控件ID]
 */

selectControl = {};	//选择控件[人员、组织]主对象

selectControl.index = 0;	//控件ID,Name下标标示

selectControl.controlDivId = "";

//selectControl.isCheckOrRadio = false;
//selectControl.isPersonOrOrg = true;
//selectControl.echoData = undefined;

var personData = [];	//搜索框人员数据
var orgData = [];		//组织数据
var orgDataByOrg = [];
var orgDataByDept = [];

selectControl.person = {};//人员对象
selectControl.person.url = getRootPath()+"/department/searchUserList.action";	//人员数据URL

selectControl.org = {}; //组织对象
selectControl.org.url = getRootPath()+"/department/deptTree.action";	//组织数据URL
selectControl.org.deptType = "";

/**
 * 选择控件初始化
 * [后三位参数采用默认可以不填,不采用默认必须依次填写,最后一个参数是用来回显数据的,要是没有回显可以不填]
 * @param[*]代表必填项
 * 
 * @param[*] showControlDivId 	要显示控件的divId
 * @param[*] controlId 			要显示控件的controlId[一个页面上重复调用不能重名][规则是"id-name",id不要重名]
 * @param isCheckOrRadio		是单选还是多选[false:单选,true:多选][默认单选]
 * @param isPersonOrOrg			是人员还是组织[false:组织,true:人员][默认人员]
 * @param orgOrDept				机构\部门[1:组织,0:部门,""或者null:全部][默认全部]
 * @param echoData				回显数据[格式为JSON][默认undefined]
 * 								echoData 单选的{id:value,text:value} , 多选的[{id:value,text:value},{id:value,text:value}]
 */
selectControl.init = function(showControlDivId, controlId, isCheckOrRadio, isPersonOrOrg, orgOrDept, echoData) {
	
	//isCheckOrRadio = arguments[2]!=undefined ? isCheckOrRadio : selectControl.isCheckOrRadio;
	//isPersonOrOrg = arguments[3]!=undefined ? isPersonOrOrg : selectControl.isPersonOrOrg;
	//echoData = arguments[4]!=undefined ? echoData : selectControl.echoData;
	
	//console.info(showControlDivId+"::"+controlId+"::"+isCheckOrRadio+"::"+isPersonOrOrg+"::"+echoData);
	selectControl.controlDivId = showControlDivId;
	this.index = parseInt(selectControl.index) + 1;//控件下标
	
	if(isPersonOrOrg){
		//人员控件
		var openStructureId = "openStructure"+ this.index;
		
		/**
		 * 输入框与选择按钮界面
		 */
		var personHtml = '<div class="select2-wrap input-group w-p100">'+
						    '<input type="hidden" id="'+controlId+'" name="'+controlId.split("-")[1]+'" search="search" style="width:100%"/>'+
							'<a class="btn btn-file no-all input-group-btn" href="#"'+
								'onClick="selectControl.person.structureOpen('+isCheckOrRadio+',\''+controlId+'\');" id="'+openStructureId+'" role="button"'+
								'data-toggle="modal"><i class="fa fa-user"></i>'+
							'</a>'+
					     '</div>'; 
		
		$("#" + showControlDivId).append(personHtml);//添加控件到外层DIV
		
		//查询人员数据用于搜索
		$.ajax({
			url : selectControl.person.url,
			type : 'post',
			data: {
				time: new Date()
			},
			success : function(data) {
				$.each(data, function(i, o) {
					personData[i] = {
						id : o.id,								//ID
						text : o.displayName,					//显示的内容
						hp: Pinyin.GetHP(o.displayName),		//两个汉字输入全拼,三个以上的汉字，第一个汉字全拼，后面的汉字简拼
						qp: Pinyin.GetQP(o.displayName),		//汉字的全拼
						jp: Pinyin.GetJP(o.displayName),		//汉字的简拼
						wc: Pinyin.getWordsCode(o.displayName)	//单词首字母获取
					};
				});
				select2InitToPerson(personData, controlId, isCheckOrRadio);
				if(echoData != undefined || echoData != null || echoData != "")
					select2InitEchoToPerson(controlId, echoData);
			},
			error: function(){
				alertx("获取人员失败");
			}
		});
	}else{
		
		//组织控件
		var treeId    = "tree"   + this.index;		//DivID
		var myTreeId  = "myTree" + this.index;		//树控件ID
		var orgbtnId  = "orgbtn" + this.index;		//组织按钮ID
		var openBtnId = "open"   + this.index;		//打开按钮ID
		
		/**
		 * 组织树界面
		 */
		var orgHtml = '<div class="modal fade panel" id="'+treeId+'" aria-hidden="false">'+
						'<div class="modal-dialog">'+
							'<div class="modal-content">'+
								'<div class="modal-header">'+
									'<button type="button" class="close" data-dismiss="modal">×</button>'+
									'<h4 class="modal-title">选择</h4>'+
								'</div>'+
								'<div class="modal-body">'+
									'<div id="'+myTreeId+'" class="ztree"></div>'+
								'</div>'+
								'<div class="modal-footer no-all form-btn">'+
									'<button class="btn dark" type="button" onClick="showOrgValue(\''+myTreeId+'\',\''+controlId+'\',\''+treeId+'\','+isCheckOrRadio+')" id="'+orgbtnId+'">确 定</button>'+
									'<button class="btn" type="reset" id="close" onClick="selectControl.org.close(\''+treeId+'\');">关 闭</button>'+
								'</div>'+
							'</div>'+
						'</div>'+
					  '</div>';
		
		/**
		 * 输入框与选择按钮界面[组织控件主界面]
		 */
		var mainHtml = '<div class="select2-wrap input-group  w-p100">'+
					   	'<input type="hidden" id="'+controlId+'" name="'+controlId.split("-")[1]+'" search="search" style="width:100%"/>'+
						'<a class="btn btn-file no-all input-group-btn" href="#"'+
							'onClick="selectControl.org.open('+isCheckOrRadio+',\''+treeId+'\',\''+myTreeId+'\',\''+controlId+'\',\''+orgOrDept+'\');" id="'+openBtnId+'" role="button"'+
							'data-toggle="modal"><i class="fa fa-user"></i>'+
						'</a>'+
					   '</div>';
		
		$("#" + showControlDivId).append(mainHtml).append(orgHtml);//添加控件到外层DIV
		
		//查询组织数据用于搜索
		$.ajax({
			url : selectControl.org.url,
			type : 'post',
			data: {
				time: new Date(),
				deptType: orgOrDept
			},
			success : function(data) {
				$.each(data, function(i, o) {
					if(orgOrDept == 1){
						orgDataByOrg[i] = {
							id : o.id,						//ID
							text : o.name,					//显示的内容
							parentId: o.parentId,			//组织父节点
							deptType: o.deptType,			//组织类型
							hp: Pinyin.GetHP(o.name),		//两个汉字输入全拼,三个以上的汉字，第一个汉字全拼，后面的汉字简拼
							qp: Pinyin.GetQP(o.name),		//汉字的全拼
							jp: Pinyin.GetJP(o.name),		//汉字的简拼
							wc: Pinyin.getWordsCode(o.name)	//单词首字母获取
						};
					}else if(orgOrDept == 0){
						orgDataByDept[i] = {
							id : o.id,						//ID
							text : o.name,					//显示的内容
							parentId: o.parentId,			//组织父节点
							deptType: o.deptType,			//组织类型
							hp: Pinyin.GetHP(o.name),		//两个汉字输入全拼,三个以上的汉字，第一个汉字全拼，后面的汉字简拼
							qp: Pinyin.GetQP(o.name),		//汉字的全拼
							jp: Pinyin.GetJP(o.name),		//汉字的简拼
							wc: Pinyin.getWordsCode(o.name)	//单词首字母获取
						};
					}else{
						orgData[i] = {
							id : o.id,						//ID
							text : o.name,					//显示的内容
							parentId: o.parentId,			//组织父节点
							deptType: o.deptType,			//组织类型
							hp: Pinyin.GetHP(o.name),		//两个汉字输入全拼,三个以上的汉字，第一个汉字全拼，后面的汉字简拼
							qp: Pinyin.GetQP(o.name),		//汉字的全拼
							jp: Pinyin.GetJP(o.name),		//汉字的简拼
							wc: Pinyin.getWordsCode(o.name)	//单词首字母获取
						};
					}
				});
				if(orgOrDept == 1){
					select2InitToOrg(orgDataByOrg, controlId, isCheckOrRadio);
				}else if(orgOrDept == 0){
					select2InitToOrg(orgDataByDept, controlId, isCheckOrRadio);
				}else{
					select2InitToOrg(orgData, controlId, isCheckOrRadio);
				}
				
				if(echoData != undefined || echoData != null || echoData != "")
					select2InitEchoToOrg(controlId, echoData);
			},
			error: function(){
				alertx("获取组织失败");
			}
		});
	}
};

/**
 * 人员select2初始化数据
 * @param personData 		数据
 * @param controlId 		控件ID
 * @param isCheckOrRadio 	单选或多选
 */
function select2InitToPerson(personData, controlId, isCheckOrRadio){
	$("#"+controlId).select2({
	    placeholder: "搜索人员",		//文本框占位符显示
	    //minimumInputLength: 1,	//最小输入长度,进行搜索
	    //maximumInputLength: 3,	//最大输入长度
	    multiple: isCheckOrRadio ? true : false,//单选or多选
	    query: function (query){
	        var data = {results: []};
	        $.each(personData, function(){
	            if(query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0 
	            		|| this.hp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
	            		|| this.qp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
	            		|| this.jp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
	            		|| this.wc.toUpperCase().indexOf(query.term.toUpperCase()) >= 0){
	                data.results.push({id: this.id, text: this.text});
	            }
	        });
	        query.callback(data);
	    }
	});
}

/**
 * 人员select2初始化回显
 * @param controlId 控件ID
 * @param echoData 回显数据
 */
function select2InitEchoToPerson(controlId, echoData){
	$("#"+controlId).select2("data", echoData);
}

/**
 * 组织select2初始化数据
 * @param orgData 			数据
 * @param controlId 		控件ID
 * @param isCheckOrRadio 	单选或多选
 */
function select2InitToOrg(orgData, controlId, isCheckOrRadio){
	$("#"+controlId).select2({
	    placeholder: "搜索组织",						//文本框占位符显示
	    allowClear: true,
	    //minimumInputLength: 1,					//最小输入长度,进行搜索
	    //maximumInputLength: 8,					//最大输入长度
	    multiple: isCheckOrRadio ? true : false,	//单选or多选
	    query: function (query){
	        var data = {results: []};
	        $.each(orgData, function(){
	            if(query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0 
	            		|| this.hp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
	            		|| this.qp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
	            		|| this.jp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
	            		|| this.wc.toUpperCase().indexOf(query.term.toUpperCase()) >= 0){
	            	//data.results.push({id: this.id+","+this.parentId, text: this.deptType==0?"[部门]":"[机构]"+this.text});
	                data.results.push({id: this.id, text: this.text});
	            }
	        });
	        query.callback(data);
	    }
	});
}

/**
 * 组织select2初始化回显
 * @param controlId 控件ID
 * @param echoData 回显数据
 */
function select2InitEchoToOrg(controlId, echoData){
	$("#"+controlId).select2("data", echoData);
}

/**
 * 打开人员控件
 * @param isCheckOrRadio 	单选多选
 * @param controlId			文本控件ID
 */
selectControl.person.structureOpen = function(isCheckOrRadio, controlInuptId){
	var opt = {
		selectType: 'radio',
		callback: function toHandWork(users){
			showPersonValue(users, controlInuptId, isCheckOrRadio);
		}
	};
	if(isCheckOrRadio){
		opt = {
			selectType: 'check',
			callback: function toHandWork(users){
				showPersonValue(users, controlInuptId, isCheckOrRadio);
			}
		};
	}
	var structureItem = structure.init(opt);
	structureItem.open();
};

/**
 * 人员选择回调函数中调用[回写值]
 * @param users				人员控件返回的UsersJSON数据
 * @param controlInuptId 	文本控件ID
 * @param isCheckOrRadio	单选多选
 */
function showPersonValue(users, controlInuptId, isCheckOrRadio){
	/*$.each($("input[search=search]"), function(i, val){
		alert($(this).select2("val"));
    });*/
	//$("#"+controlId).select2("data", {id: users[0].id, text: users[0].name});//回显值
	var rpv = [];//人员控件返回的数据集合
	$.each(users,function(i, val){
		rpv[i] = {
			id: this.id,
			text: this.name
		}
	});
	//console.info(rv);
	if(isCheckOrRadio)
		$("#"+controlInuptId).select2("data", rpv);//多选回显
	else
		$("#"+controlInuptId).select2("data", {id: users[0].id, text: users[0].name});//单选回显
}

/**
 * 打开组织树
 * @param isCheckOrRadio 	单选多选
 * @param treeId			显示树DivID
 * @param myTreeId			树控件ID
 * @param controlInuptId 	文本控件ID
 * @param orgOrDept 		机构/部门
 */
selectControl.org.open = function(isCheckOrRadio, treeId, myTreeId, controlInuptId, orgOrDept){
	/**
	 * tree控件的设置[单选][默认]
	 */
	var settingRadio = {
		check:{
			enable: true,
			nocheckInherit: true,
			chkStyle: "radio",
			radioType : "all"
		},
		view:{
			selectedMulti: false,
			showLine: false
		},
		data:{
			simpleData:{
				enable:true
			}
		},
		callback:{
			beforeClick: function(id, node){
				return true;
			}
		}
	};

	/**
	 * tree控件的设置[多选]
	 */
	var settingCheck = {
		check:{
			enable: true,
			chkStyle: "checkbox"
		},
		view:{
			selectedMulti: false,
			showLine: false
		},
		data:{
			simpleData:{
				enable:true
			}
		},
		callback:{
			beforeClick: function(id, node){
				return true;
			}
		}
	};
	/*var params = {
		time: new Date()
	};
	$.ajax({
		url : selectControl.org.url,
		type : 'post',
		data: params,
		success : function(data) {
			zNodes = [];
			$.each(data, function(i, o) {
				zNodes[i] = {
					id : o.id,
					pId : o.parentId,
					name : o.name,
					deptType : o.deptType
				};
			});
			var zTreeObject = $.fn.zTree.init($("#"+myTreeId), isCheckOrRadio ? settingCheck : settingRadio, zNodes);
			zTreeObject.expandAll(true);
		},
		error: function(){
			alert("打开组织树失败");
		}
	});*/
	var zNodes = [];
	
	if(orgOrDept==1){
		$.each(orgDataByOrg, function(i, o) {
			zNodes[i] = {
				id : o.id,
				pId : o.parentId,
				name : o.text,
				deptType : o.deptType
			};
		});
	}else if(orgOrDept==0){
		$.each(orgDataByDept, function(i, o) {
			zNodes[i] = {
				id : o.id,
				pId : o.parentId,
				name : o.text,
				deptType : o.deptType
			};
		});
	}else{
		$.each(orgData, function(i, o) {
			zNodes[i] = {
				id : o.id,
				pId : o.parentId,
				name : o.text,
				deptType : o.deptType
			};
		});
	}
	
	var zTreeObject = $.fn.zTree.init($("#"+myTreeId), isCheckOrRadio ? settingCheck : settingRadio, zNodes);
	zTreeObject.expandAll(true);
	
	$("#"+treeId).modal("show");
};

/**
 * 关闭树界面
 * @param treeId 显示树控件的DivID
 */
selectControl.org.close = function(treeId){
	$("#"+treeId).modal("hide");
};

/**
 * 清除控件
 */
selectControl.clear = function(){
	$("#"+selectControl.controlDivId).html("");
}

/**
 * 清空控件值
 * @param controlInuptId 	文本控件ID
 */
selectControl.clearValue = function(controlInuptId){
	$("#"+controlInuptId).select2("data","");
}

/**
 * 组织选择[回写值]
 * @param myTreeId			组织控件ID
 * @param controlInuptId 	文本控件ID
 * @param treeId			显示树DivID
 * @param isCheckOrRadio	单选多选
 */
function showOrgValue(myTreeId, controlInuptId, treeId, isCheckOrRadio){
	var treeObj = $.fn.zTree.getZTreeObj(myTreeId);
	var nodes = treeObj.getChangeCheckedNodes();
	//console.info(nodes);
	var rov = [];//人员控件返回的数据集合
	$.each(nodes,function(i, val){
		rov[i] = {
			id: this.id,
			text: this.name
		}
	});
	//console.info(nodes[0].id +"::"+ nodes[0].name);
	if(isCheckOrRadio)
		$("#"+controlInuptId).select2("data", rov);//多选回显值
	else
		$("#"+controlInuptId).select2("data",{id: nodes[0].id, text: nodes[0].name});//单选回显值
	$("#"+treeId).modal("hide");
}

/**
 * 返回选中的值
 * @param arrayInputId	文本控件ID数组
 * @returns	单选返回格式[id:name]、多选返回格式[id:name,id:name]
 */
function returnValue(arrayInputId){
	var v = "";
	$.each(arrayInputId.split(","),function(i, val){
		if($("#"+val).select2("data").length > 0){
			$.each($("#"+val).select2("data"),function(j, d){
				v += d.id+":"+d.text+",";
			});
		}else{
			v += $("#"+val).select2("data").id+":"+$("#"+val).select2("data").text+",";
		}
	});
	return v.substring(0,v.length-1);
}

