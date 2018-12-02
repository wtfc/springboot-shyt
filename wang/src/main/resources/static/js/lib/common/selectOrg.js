selectOrg = {};		//选择控件[组织]主对象
var oData = [],		//全组织数据
zkCount = 0;		//展开收起计数
selectOrg.org = {}; 	//组织对象
selectOrg.org.url = getRootPath()+"/department/getAllOrgNoDeptTree.action";	//组织数据URL

/**
 * 选择控件初始化
 * [后三位参数采用默认可以不填,不采用默认必须依次填写,最后一个参数是用来回显数据的,要是没有回显可以不填]
 * @param[*]代表必填项
 * @param[*] showControlDivId 	要显示控件的divId
 * @param[*] controlId 			要显示控件的controlId[一个页面上重复调用不能重名][规则是"id-name",id不要重名]
 * @param isCheckOrRadio		是单选还是多选[false:单选,true:多选][默认单选]
 * @param echoData				回显数据[格式为JSON][默认undefined]
 * 								echoData 单选的{id:value,text:value} , 多选的[{id:value,text:value},{id:value,text:value}]
 */
selectOrg.init = function(showControlDivId, controlId, isCheckOrRadio, echoData, isReadonly) {
	var x = 1000, y = 10;
    this.rand = parseInt(Math.random() * (x - y + 1) + y);
	
	var $showControlDivId_org = $("#" + showControlDivId);
	$showControlDivId_org.empty(); 
	
	//组织控件
	var treeId    = "tree"   + this.rand,	//DivID
	myTreeId  = "myTree" + this.rand,		//树控件ID
	orgbtnId  = "orgbtn" + this.rand,		//组织按钮ID
	openBtnId = "open"   + this.rand,		//打开按钮ID
	clearBtnId = "clearBtn" + this.rand;	
	
	/**
	 * 组织树界面
	 */
	var orgHtml = ['<div class="modal fade" id="'+treeId+'" aria-hidden="false">'];
		orgHtml.push('<div class="modal-dialog">');
			orgHtml.push('<div class="modal-content">');
				orgHtml.push('<div class="modal-header">');
					orgHtml.push('<button type="button" class="close" data-dismiss="modal">×</button>');
						orgHtml.push('<h4 class="modal-title">选择</h4>');
							orgHtml.push('</div>');
								orgHtml.push('<div class="modal-body">');
									orgHtml.push('<div id="'+myTreeId+'" class="ztree"></div>');
								orgHtml.push('</div>');
						orgHtml.push('<div class="modal-footer no-all form-btn">');
								orgHtml.push('<button class="btn dark" type="button" onClick="showSelectOrgValue(\''+myTreeId+'\',\''+controlId+'\',\''+treeId+'\','+isCheckOrRadio+')" id="'+orgbtnId+'">确 定</button>');
								orgHtml.push('<button class="btn" type="reset" id="close" onClick="selectOrg.org.close(\''+treeId+'\',\''+controlId+'\');">取 消</button>');
						orgHtml.push('</div>');
				orgHtml.push('</div>');
		orgHtml.push('</div>');
	orgHtml.push('</div>');
	
	/**
	 * 输入框与选择按钮界面[组织控件主界面]
	 */
	var mainHtmlCheck = ['<div class="select2-wrap input-group w-p100">'];
	mainHtmlCheck.push('<div class="fl w-p100">');	//btn-in-area 
	mainHtmlCheck.push('<input type="hidden" id="'+controlId+'" name="'+controlId.split("-")[1]+'" search="search" style="width:100%"/></div>');
	mainHtmlCheck.push('<a class="btn btn-file no-all input-group-btn" href="#" onClick="selectOrg.org.open('+isCheckOrRadio+',\''+treeId+'\',\''+myTreeId+'\',\''+controlId+'\');" id="'+openBtnId+'" role="button"');
	mainHtmlCheck.push('data-toggle="modal"><i class="fa fa-group"></i></a>');
	mainHtmlCheck.push('<div class="input-group-btn m-l-xs selection-tree-btn fr">');
	mainHtmlCheck.push('<a class="a-icon i-trash fr m-b" href="#" id="'+clearBtnId+'"><i class="fa fa-trash"></i>清空</a>');
	mainHtmlCheck.push('<a class="a-icon i-new zk fr" href="#"><i class="fa fa-chevron-down"></i>展开</a>');
	mainHtmlCheck.push('<a class="a-icon i-new sq fr" href="#"><i class="fa fa-chevron-up"></i>收起</a></div>');
	mainHtmlCheck.push('</div><label class="help-block hide"></label>');
	
	var mainHtmlRadio = ['<div class="select2-wrap input-group  w-p100">'];
	mainHtmlRadio.push('<input type="hidden" id="'+controlId+'" name="'+controlId.split("-")[1]+'" search="search" style="width:100%"/>');
	mainHtmlRadio.push('<a class="btn btn-file no-all input-group-btn" href="#"');
	mainHtmlRadio.push('onClick="selectOrg.org.open('+isCheckOrRadio+',\''+treeId+'\',\''+myTreeId+'\',\''+controlId+'\');" id="'+openBtnId+'" role="button"');
	mainHtmlRadio.push('data-toggle="modal"><i class="fa fa-group"></i></a>');
	mainHtmlRadio.push('</div><label class="help-block hide"></label>');
	
	if(isCheckOrRadio){
		$showControlDivId_org.append(mainHtmlCheck.join(''));//添加控件到外层DIV
	}else{
		$showControlDivId_org.append(mainHtmlRadio.join(''));//添加控件到外层DIV
	}
	
	$(orgHtml.join('')).appendTo("body");
	
	//查询组织数据用于搜索
	if(oData.length == 0){
		$.ajax({
			async: false,
			url : selectOrg.org.url,
			type : 'post',
			success : function(data) {
				$.each(data, function(i, o) {	
					oData[i] = {
						id : o.id,						//ID
						text : o.name,					//显示的内容
						queue: o.queue,					//排序
						parentId: o.parentId,			//组织父节点
						deptType: o.deptType,			//组织类型
						isChecked: o.isChecked,
						jp: Pinyin.GetJP(o.name)		//汉字的简拼
					};
				});
				select2ToOrg(oData, controlId, isCheckOrRadio);
				if(echoData != undefined || echoData != null || echoData != "")
					select2EchoToOrg(controlId, echoData);
			},
			error: function(){
				msgBox.tip({content: "获取组织失败", type:'fail'});
			}
		});
	}else{
		select2ToOrg(oData, controlId, isCheckOrRadio);
		if(echoData != undefined || echoData != null || echoData != "")
			select2EchoToOrg(controlId, echoData);
	}
	$("#"+ controlId).bind("change", function(e){
		if(e.added){
			selectOrg.openPutAwayClear(controlId);
		}
		if(e.removed){
			selectOrg.openPutAwayClear(controlId);
		}
	});
	$("#"+clearBtnId).bind("click", function(){
		zkCount = 0;
		selectOrg.clearValue(controlId);
		selectOrg.openPutAwayClear(controlId);
	});
};

/**
 * 组织select2初始化数据
 * @param oData 			数据
 * @param controlId 		控件ID
 * @param isCheckOrRadio 	单选或多选
 */
function select2ToOrg(oData, controlId, isCheckOrRadio){
	$("#"+controlId).select2({
	    placeholder: " ",						//文本框占位符显示
	    allowClear: true,						//允许清除
	    maximumInputLength: 10,					//最大输入长度
	    multiple: isCheckOrRadio ? true : false,//单选or多选
	    query: function (query){
	        var data = {results: []};
	        $.each(oData, function(){
	            if(query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
	            		|| this.jp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0){
	            	data.results.push({id: this.id, text: this.text, disabled: this.isChecked==1?false:true});
	            }
	        });
	        query.callback(data);
	    }
	});
	zkCount=0;
	selectOrg.openPutAwayClear(controlId);
}

/**
 * 组织select2初始化回显
 * @param controlId 控件ID
 * @param echoData 回显数据
 */
function select2EchoToOrg(controlId, echoData){
	$("#"+controlId).select2("data", echoData);
	selectOrg.openPutAwayClear(controlId);
}

/**
 * 打开组织树
 * @param isCheckOrRadio 	单选多选
 * @param treeId			显示树DivID
 * @param myTreeId			树控件ID
 * @param controlInuptId 	文本控件ID
 */
selectOrg.org.open = function(isCheckOrRadio, treeId, myTreeId, controlInuptId){
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
				return false;
			}
		}
	};

	/**
	 * tree控件的设置[多选]
	 */
	var settingCheck = {
		check:{
			enable: true,
			chkStyle: "checkbox",
			chkboxType: { "Y" : "s", "N" : "ps" }
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
			beforeClick: function(id, node){}
		}
	};
	var zNodes = [];
	$.each(oData, function(i, o) {
		zNodes[i] = {
			id : o.id,
			pId : o.parentId,
			name : o.text,
			deptType : o.deptType,
			isChecked: o.isChecked,
			chkDisabled: o.isChecked == 1 ? false : true
		};
	});
	var zTreeObject = $.fn.zTree.init($("#"+treeId+" #"+myTreeId), isCheckOrRadio ? settingCheck : settingRadio, zNodes);
	zTreeObject.expandAll(true);
	var flagEdata = 0;
	var eData = returnOrgValue(controlInuptId);
	if(eData != null){
		$(eData.split(",")).each(function(i, v){
			var node = zTreeObject.getNodeByParam("id", v.split(":")[0], null);
			if(node != null){
				zTreeObject.checkNode(node, true, false);
				flagEdata++;
			}else{
				flagEdata--;
			}
		});
	}
	$("#"+treeId).modal("show");
};

/**
 * 关闭组织树界面
 * @param treeId 	显示树控件的DivID
 * @param controlId 控件的ID
 */
selectOrg.org.close = function(treeId, controlId){
	$("#"+treeId).modal("hide");
	selectOrg.openPutAwayClear(controlId);
};

/**
 * 清空控件值
 * @param controlInuptId 	文本控件ID
 */
selectOrg.clearValue = function(controlInuptId){
	$("#"+controlInuptId).select2("data","");
	selectOrg.openPutAwayClear(controlInuptId);
}

/**
 * 组织选择回显值[回写值]
 * @param myTreeId			组织控件ID
 * @param controlInuptId 	文本控件ID
 * @param treeId			显示树DivID
 * @param isCheckOrRadio	单选多选
 */
function showSelectOrgValue(myTreeId, controlInuptId, treeId, isCheckOrRadio){
	var treeObj = $.fn.zTree.getZTreeObj(myTreeId);
	var nodes = treeObj.getChangeCheckedNodes();
	if(nodes.length == 0){
		return false;
	}
	var rov = [];//人员控件返回的数据集合
	$.each(nodes,function(i, val){
		rov[i] = {
			id: this.id,
			text: this.name
		}
	});
	if(isCheckOrRadio){
		$("#"+controlInuptId).select2("data", rov);//多选回显值
	}else{
		if(rov.length > 0)
			$("#"+controlInuptId).select2("data", {id: nodes[0].id, text: nodes[0].name});//单选回显值
	}
	$("#"+treeId).modal("hide");
	selectOrg.removeValidSelect2(controlInuptId, isCheckOrRadio);
	selectOrg.openPutAwayClear(controlInuptId);
}

/**
 * 返回选中的值
 * @param inputId	文本控件ID
 * @returns	单选返回格式[id:name]、多选返回格式[id:name,id:name]
 */
function returnOrgValue(inputId){
	if($("#"+inputId).select2("data") == null || $("#"+inputId).select2("data").length == 0 ){
		return null;
	}
	var v = "";
	if($("#"+inputId).select2("data").length > 0){
		$.each($("#"+inputId).select2("data"), function(j, d){
			v += d.id+":"+d.text+",";
		});
	}else{
		v += $("#"+inputId).select2("data").id+":"+$("#"+inputId).select2("data").text+",";
	}
	return v.substring(0, v.length-1);
}

/**
 * 人员控件的展开、收起
 */
selectOrg.openPutAwayClear = function(controlId){
	var ell = $("#"+controlId);
	var el = ell.closest(".select2-wrap") || null;
	var $parent = el.find(".selection-tree-btn").parent();
	//var tree_lh = $parent.find(".select2-choices").height();
	var tree_lh = $parent.find(".select2-choices").actual("height");
	if(tree_lh >= 66){
		el.find(".select2-container").css("max-height","67px");
		el.find(".selection-tree-btn").show();
		if(zkCount==0){
			el.find(".selection-tree-btn .sq").hide();
			el.find(".selection-tree-btn .zk").show();
			//zkCount++;
		}else{
			el.find(".selection-tree-btn .sq").show();
			el.find(".selection-tree-btn .zk").hide();
		}
	}else{
		el.find(".selection-tree-btn .sq").hide();
		el.find(".selection-tree-btn .zk").hide();
		el.find(".selection-tree-btn").hide();
	}
	$parent.find(".zk").click(function(){
		var $this = this;
		var $parent = el.find(".selection-tree-btn");
		el.find(".select2-container").css("max-height","100%");
		$($this).hide();
		$parent.find(".sq").show();
		selectOrg.setPaddingTop($parent);
	});
	$parent.find(".sq").click(function(){
		var $this = this;
		var $parent = el.find(".selection-tree-btn");
		el.find(".select2-container").css("max-height","67px");
		$($this).hide();
		$parent.find(".zk").show();
		var $postscript_box = $(".postscript_box");
		if($postscript_box.length){
			$postscript_box.hide();
			$postscript_box.css('bottom',0-$('#scrollable').scrollTop());
			$postscript_box.show();
		}
		selectOrg.setPaddingTop($parent);
	});
}

/**
 * 清除select2验证信息
 * @params controlId 控件ID
 * @params isCheckOrRadio true多选,false单选
 */
selectOrg.removeValidSelect2 = function(controlId, isCheckOrRadio){
	$("#s2id_"+controlId).removeClass("error"); 
	if(isCheckOrRadio){
		$("#s2id_"+controlId).parent().parent().next(".help-block").html("");
	}else{
		$("#s2id_"+controlId).parent().next(".help-block").html("");
	}
}

/**
 * 人员选择框select2只读
 */
selectOrg.select2Readonly = function(controlId){
	$("#"+controlId).select2("readonly", true);
}

/**
 * 重置弹出层的上边距
 */
selectOrg.setPaddingTop = function(parent){
	var modal = parent.closest(".modal");
	if(modal){
		modal.modal("setPaddingTop");
	}
}