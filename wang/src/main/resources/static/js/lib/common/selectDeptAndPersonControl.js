/**
 * 组织人员控件 
 * deptAndPersonControl 0.1 
 * Copyright (c) 2014 张继伟 
 * Date: 2014-05-27
 */
(function($) {
	
	var zkAndSqCount = 0,			//	控制展开收起用
	 	personData = [],			// 	人员数据
	 	orgData = [], 				//	组织数据
	 	index = 0, 					// 	控件ID,Name下标标示
	 	mainInputId = "",			//	主页文本框控件ID
	 	mainInputName = "",			//	主页文本框控件Name
	 	openBtnId = "",				//	主页按钮控件ID
		openDeptAndPersonDivId = "",//	部门与人员DIV的ID
		treeId = "",				//	树控件的ID
		selectPersonId = "",		//	选择人员控件ID
		selectDeptAndPersonId = "",	//	选择部门与人员控件的ID
		rightId = "",				//	向右按钮ID
		leftId = "",				//	向左按钮ID
		upId = "",					//	向上按钮ID
		downId = "",				//	向下按钮ID
		confirmBtnId = "",			//	确认按钮ID
		clearBtnId = "",			//	清除按钮ID
		orgPersonData = new Array();//	组织人员数据 
	
	$.fn.deptAndPersonControl = function(options) {
		var opts = $.extend({}, $.fn.deptAndPersonControl.defaults, options);
		return this.each(function() {
			var $this = $(this);
			var o = $.meta ? $.extend({}, opts, $this.data()) : opts;
			init(o.widgetId, o.widgetName);
			var inputId   = o.widgetId == null ? mainInputId : o.widgetId;
			var inputName = o.widgetName == null ? mainInputName : o.widgetName;
			var mainHtmlCheck = // 主页面
				['<div class="select2-wrap input-group w-p100">'];
			mainHtmlCheck.push('<div class="fl w-p100">'); //btn-in-area 
			mainHtmlCheck.push('<input type="hidden" id="'+inputId+'" name="'+inputName+'" style="width:100%"/></div>');
		    if(o.isReadonly){
		    	mainHtmlCheck.push('<a class="btn btn-file no-all input-group-btn" style="cursor:default" role="button" data-toggle="modal"><i class="fa fa-group"></i>');
			}else{
				mainHtmlCheck.push('<a class="btn btn-file no-all input-group-btn" href="#" role="button" id="'+openBtnId+'" data-toggle="modal"><i class="fa fa-group"></i>');
			}
		    mainHtmlCheck.push('</a><div class="input-group-btn m-l-xs selection-tree-btn fr">');
		    mainHtmlCheck.push('<a class="a-icon i-trash fr m-b" href="#" id="'+clearBtnId+'"><i class="fa fa-trash"></i>清空</a>');
		    mainHtmlCheck.push('<a class="a-icon i-new zk fr" href="#"><i class="fa fa-chevron-down"></i>展开</a>');
		    mainHtmlCheck.push('<a class="a-icon i-new sq fr" href="#"><i class="fa fa-chevron-up"></i>收起</a></div>');
		    mainHtmlCheck.push('</div><label class="help-block hide"></label>');
			var mainHtmlRadio = // 主页面
				['<div class="select2-wrap input-group w-p100">'];
			mainHtmlRadio.push('<input type="hidden" id="'+inputId+'" name="'+inputName+'" style="width:100%"/>');
			if(o.isReadonly){
				mainHtmlRadio.push('<a class="btn btn-file no-all input-group-btn" style="cursor:default" role="button" data-toggle="modal"><i class="fa fa-group"></i>');
			}else{
				mainHtmlRadio.push('<a class="btn btn-file no-all input-group-btn" href="#" role="button" id="'+openBtnId+'" data-toggle="modal"><i class="fa fa-group"></i>');
			}
			mainHtmlRadio.push('</a></div><label class="help-block hide"></label>');
			var html = // 人员部门界面
				['<div class="modal fade" id="'+openDeptAndPersonDivId+'" aria-hidden="false">'];
			html.push('<div class="modal-dialog modal-tree">');
			html.push('<div class="modal-content">');
				html.push('<div class="modal-header clearfix">');	//onclick="clear(\''+selectPersonId+'\',\''+selectDeptAndPersonId+'\');"
					html.push('<button type="button" class="close" data-dismiss="modal">×</button>');
					html.push('<h4 class="modal-title fl">组织人员选择</h4>');
				html.push('</div>');
				html.push('<div class="modal-body clearfix" style="overflow-x: hidden;">');
					html.push('<div class="w240 fl">');
						html.push('<section class="tree-ul tree-scroll">');
							html.push('<div id="'+treeId+'" class="ztree"></div>'); // 组织树
						html.push('</section>');
						html.push('<section class="m-t-md">');
							html.push('<select id="'+selectPersonId+'" multiple="true" class="w240 tree-select"></select>');	// 人员选择框
						html.push('</section>');
					html.push('</div>');
					html.push('<section class="m-l m-r fl tree-operate">');
						html.push('<a href="#" class="tree-move-down" id="'+rightId+'"><i class="fa fa-double-angle-right"></i></a> ');
						html.push('<a href="#" class="tree-move-up" id="'+leftId+'"><i class="fa fa-double-angle-left"></i></a>');
					html.push('</section>');
					html.push('<section class="fl pos-rlt">');
						html.push('<select id="'+selectDeptAndPersonId+'" multiple="true" class="w200 tree-scroll-right tree-s-r tree-select"></select>');	// 组织与人员选择框
							html.push('<div class="tree-move tree-ryzz"> ');
								html.push('<a href="#" class="tree-move-up" id="'+upId+'"><i class="fa fa-caret-up"></i></a> ');
								html.push('<a href="#" class="tree-move-down" id="'+downId+'"><i class="fa fa-caret-down"></i></a> ');
							html.push('</div>');
						html.push('</section>');
					html.push('</div>');
					html.push('<div class="modal-footer form-btn">');
						html.push('<button id="'+confirmBtnId+'" class="btn dark" type="button">确 定</button>');
						html.push('<button class="btn" type="reset" id="close" onClick=$(\"#'+openDeptAndPersonDivId+'\").modal(\"hide\");>取 消</button>');
					html.push('</div>');
				html.push('</div>');
			html.push('</div>');
		html.push('</div>');
			$this.empty();
			if(o.single){
				$this.append(mainHtmlRadio.join(''));	// 添加主页面
			}else{
				$this.append(mainHtmlCheck.join(''));	// 添加主页面
				$("#"+clearBtnId).bind("click", function(){
					$("#"+inputId).select2("data", "");
					zkAndSqCount = 0;
					select_tree($("#"+inputId));
					if(typeof resetPostscript == 'function'){
						resetPostscript();
					}
					//回调清空数据
					if(o.callbackFunction != null){
						o.callbackFunction($("#"+ inputId).select2("data"));
					}
				});
			}
			$("#"+inputId).on("select2-focus", function(e){
				if(orgPersonData.length == 0){
					mainInputDataInit();
				}
			});
			mainInputInit(inputId, o.echoData, o.callbackFunction, o.isReadonly);	// 主页面文本框数据初始化
			mainBtnInit(openBtnId, treeId, openDeptAndPersonDivId, inputId, html.join(''), selectPersonId, rightId, leftId, selectDeptAndPersonId, upId, downId, confirmBtnId, o.echoData, o.callbackFunction, o.single); // 主页面按钮初始化
		});	
	};

	var init = function(widgetId, widgetName){
		index = parseInt(index) + 1;							// 	控件下标
		if(widgetId == null){
			mainInputId = "mainInput" + index;					//	主页文本框控件ID
			$("#"+mainInputId).select2("val", "");
		}else{
			$("#"+widgetId).select2("val", "");
		}
		if(widgetName == null){
			mainInputName = "mainInputName" + index;			//	主页文本框控件Name
		}
		openBtnId = "openBtn"+ index;							//	主页按钮ID
		openDeptAndPersonDivId = "openDeptAndPerson" + index;	//	部门与人员DIV的ID
		treeId = "tree" + index;								//	树控件的ID
		selectPersonId = "selectPerson" + index;				//	选择人员控件的ID
		selectDeptAndPersonId = "selectDeptAndPerson" + index;	//	选择部门与人员的ID
		rightId = "right" + index;								//	向右按钮ID
		leftId = "left" + index;								//	向左按钮ID
		upId = "up" + index;									//	向上按钮ID
		downId = "down" + index;								//	向下按钮ID
		confirmBtnId = "confirmBtn" + index;					//	确认按钮ID
		clearBtnId  = "clearBtn" + index;
	};
	
	var mainInputDataInit = function(){
		$.ajax({
			//url : getRootPath()+"/system/getStructureAndUser.action",
			url : getRootPath()+"/department/getOrgAndPersonTree.action",
			type : 'post',
			success : function(data) {
				//console.log(data);
				$.each(data, function(i, o) {
					orgData[i] = {
						id : o.id,						//ID
						text : o.name,					//显示的内容
						type : "2",						//组织类型
						isChecked: o.isChecked,
						jp: Pinyin.GetJP(o.name)		//汉字的简拼
					};
					if(o.users.length > 0){
						$.each(o.users, function(ii, oo){
							personData[ii] = {
								id : oo.id,							// ID
								text : oo.displayName,				// 显示的内容
								type : "1",							// 用户类型
								isChecked: oo.isChecked,
								jp: Pinyin.GetJP(oo.displayName)		// 汉字的简拼
							};
						});
					}
				});
			},
			error: function(){
				alertx("获取人员组织失败");
			}
		});
		orgPersonData.push(personData);
		orgPersonData.push(orgData);
	};
	
	var mainInputInit = function(mii, echoData, callback, isReadonly){
		select2InitData(orgPersonData, mii, callback, isReadonly);
		if(echoData != null)
			select2InitEcho(mii, echoData);
	};
	
	/**
	 * 选择框赋值
	 */
	function select2InitData(datas, mii, callback, isReadonly){
		var $mii = $("#"+ mii);
		$mii.select2({
		    placeholder: " ",
		    multiple: true,
		    query: function (query){
		        var data = {results: []};
		        if(datas.length > 0){
		        	$.each(datas, function(){
			        	$.each(this, function(){
				            if(query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0 
				            		|| this.jp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0){
				            	if(this.isChecked==undefined){
				            		data.results.push({id: this.id, text: this.text, type: this.type, disabled: false});
				            	}else{
				            		data.results.push({id: this.id, text: this.text, type: this.type, disabled: this.isChecked==1?false:true});
				            	}
				            }
			        	});
			        });
		        }
		        query.callback(data);
		    }
		});
		
		$mii.on("change", function(e){
			if(callback != null)
				callback($("#"+ mii).select2("data"));
			select_tree($("#"+mii));
		});
		
		if(isReadonly){
			$mii.select2("readonly", true);
		}
	};
	
	/**
	 * 选择框回显值
	 */
	function select2InitEcho(mii, echoData){
		var $mii = $("#"+ mii);
		$mii.select2("data", echoData);
		select_tree($mii);
	};
	
	/**
	 * 主页按钮初始化
	 */
	var mainBtnInit = function(obi, ti, odpdi, mii, h, spi, ri, li, sdapi, ui, di, cbi, selectData, callback, single){
		$(h).empty();
		$(h).appendTo("body");
		
		var $mii = $("#"+ mii);
		var $sdapi = $("#"+sdapi);
		
		//TODO 改造 $sdapi
		
		/**
		 * 按钮事件
		 */
		$("#"+obi).click(function(){
			$sdapi.empty();
			$.each($mii.select2("data"), function(i, v){
				if(v.type == 1){
					$sdapi.append("<option value='"+v.id+","+v.type+"'>"+v.text+"[人员]</option>");
				}else{
					$sdapi.append("<option value='"+v.id+","+v.type+"'>"+v.text+"[组织]</option>");
				}
			});
			var setting = {
				check:{
					enable: true,// 设置 zTree 的节点上是否显示 checkbox/radio
					nocheckInherit: true,// 是否自动继承父节点属性
					chkStyle: "checkbox",// 勾选框类型(checkbox 或 radio)
					chkboxType: { "Y" : "s", "N" : "ps" }
				},
				view:{
					selectedMulti: true,// 设置是否允许同时选中多个节点
					showLine: true// 设置 zTree 是否显示节点之间的连线
				},
				data:{simpleData:{enable:true}},// 确定zTree数据不需要转换为JSON格式,true是需要
				callback:{
					beforeClick: function(treeId, treeNode){
						if(treeNode.isChecked == 0){
							return false;
						}
						return true;
					},
					beforeCheck: function(treeId, treeNode){	// 节点改变删除select框中的数据
						var unSelectNodes = tree.getCheckedNodes(true);
						for(var i=0; i<unSelectNodes.length; i++) {
							if(unSelectNodes[i].users.length > 0){
								for(var j=0;j<unSelectNodes[i].users.length;j++){
									$("#"+spi+" option[value='"+unSelectNodes[i].users[j].id+"']").remove();
								}
							}
						}
					},
					onCheck: function(treeId, treeNode){	// 选中节点把人员添加到select框中
						var selectNodes = tree.getCheckedNodes(true);
						for(var i=0; i<selectNodes.length; i++) {
							if(selectNodes[i].users.length > 0){
								for(var j=0;j<selectNodes[i].users.length;j++){
									$("#"+spi).append("<option value='"+selectNodes[i].users[j].id+"'>"+selectNodes[i].users[j].displayName+"</option>");
								}
							}
						}
					},
					onClick: function(event, treeId, treeNode, clickFlag){	// 用于捕获节点被点击的事件回调函数
					},
					onDblClick: function(event, treeId, treeNode){
						var treeObj = $.fn.zTree.getZTreeObj(ti);
						var nodes = treeObj.getSelectedNodes();
						if(nodes.length==0){
							return false;
						}
						if(single){
							$("#"+sdapi+" option:last").remove();
							$sdapi.append("<option value='"+nodes[0].id+",2'>"+nodes[0].name+"[组织]</option>");
							treeObj.cancelSelectedNode(nodes[0]);
						}else{
							$("#"+sdapi+" option[value='"+nodes[0].id+",2']").remove();
							$sdapi.append("<option value='"+nodes[0].id+",2'>"+nodes[0].name+"[组织]</option>");
							treeObj.cancelSelectedNode(nodes[0]);
						}
					}
				}
			};
			/**
			 * 获取组织人员数据
			 */
			$.getJSON(getRootPath()+"/department/getOrgAndPersonTree.action", function(data) {
				var zNodes = [];
				$.each(data, function(i, o) {
					zNodes[i] = {
						id : o.id,
						pId : o.parentId,
						name : o.name,
						deptType : o.deptType,
						iconSkin : o.deptType == 0 ? "fa-flag" : "fa-office",
						users: o.users,
						isChecked: o.isChecked,
						chkDisabled : o.isChecked == 1 ? false : true
					};
				});
				tree = $.fn.zTree.init($("#"+odpdi+" #"+ti), setting, zNodes);
				tree.expandAll(true);
				$.each($mii.select2("data"), function(i, v){
					if(v.type == 2){
						var node = tree.getNodeByParam("id", v.id, null);
						if(node != null)
							tree.checkNode(node, true, false, true);
					}
				});
			});
			$("#"+ odpdi).modal("show");	//显示部门人员界面
		});
		/**
		 * 右侧按钮
		 */
		$("#"+ri).click(function(){
			var treeObj = $.fn.zTree.getZTreeObj(ti);
			var nodes = treeObj.getSelectedNodes();
			var selectOptionCount = $("#"+spi).find("option:selected").length;
			if(nodes.length <= 0 && selectOptionCount <= 0){
				msgBox.tip({content: "请选择要添加的组织或人员", type:'fail'});
			}else{
				if(nodes.length > 0){
					if(single){
						$("#"+sdapi+" option:last").remove();
						$sdapi.append("<option value='"+nodes[0].id+",2'>"+nodes[0].name+"[组织]</option>");
						treeObj.cancelSelectedNode(nodes[0]);
					}else{
						$("#"+sdapi+" option[value='"+nodes[0].id+",2']").remove();
						$sdapi.append("<option value='"+nodes[0].id+",2'>"+nodes[0].name+"[组织]</option>");
						treeObj.cancelSelectedNode(nodes[0]);
					}
				}
				if(selectOptionCount > 0){
					if(single){
						$.each($("#"+spi).find("option:selected"),function(i, v){
							$("#"+sdapi+" option:last").remove();
							$sdapi.append("<option value='"+v.value+",1'>"+v.text+"[人员]</option>");
						});
					}else{
						$.each($("#"+spi).find("option:selected"),function(i, v){
							$("#"+sdapi+" option[value='"+v.value+",1']").remove();
							$sdapi.append("<option value='"+v.value+",1'>"+v.text+"[人员]</option>");
							$("#"+spi+" option[value='"+v.value+"']").remove();
						});
					}
				}
			}
		});
		/**
		 * 左侧按钮
		 */
		$("#"+li).click(function(){
			var selectOptionCount = $sdapi.find("option:selected").length;
			if(selectOptionCount > 0){
				$.each($sdapi.find("option:selected"),function(i, v){
					if(v.text.indexOf("[人员]") != -1){
						$("#"+spi+" option[value='"+v.value.split(",")[0]+"']").remove();
						$("#"+spi).append("<option value='"+v.value.split(",")[0]+"'>"+v.text.substr(0,v.text.indexOf("[人员]"))+"</option>");
					}
				});
				$sdapi.find("option:selected").remove();
			}else{
				msgBox.tip({content: "请选择要移除的组织或人员", type:'fail'});
			}
		});
		/**
		 * 向上按钮
		 */
		$("#"+ui).click(function(){
			if($sdapi.val() == null){
				msgBox.tip({content: "请选择升序的组织或人员", type:'fail'});
			}else{
				if($sdapi.find("option:selected").length > 1){
					msgBox.tip({content: "请选择一项进行调整", type:'fail'});
				}else{
					var optionIndex = $sdapi.get(0).selectedIndex; 
					if(optionIndex > 0){ 
						$sdapi.find("option:selected").insertBefore($sdapi.find("option:selected").prev('option')); 
					}
				}
			}
		});
		/**
		 * 向下按钮
		 */
		$("#"+di).click(function(){
			if($sdapi.val() == null){
				msgBox.tip({content: "请选择降序的组织或人员", type:'fail'});
			}else{
				if($sdapi.find("option:selected").length > 1){
					msgBox.tip({content: "请选择一项进行调整", type:'fail'});
				}else{
					var optionLength = $sdapi[0].options.length; 
					var optionIndex = $sdapi.get(0).selectedIndex; 
					if(optionIndex < (optionLength-1)){ 
						$sdapi.find('option:selected').insertAfter($sdapi.find('option:selected').next('option')); 
					} 
				}
			}
		});
		/**
		 * 人员select选择框双击事件--向右添加
		 */
		$("#"+spi).dblclick(function(){
			if(single){
				$.each($("#"+spi).find("option:selected"),function(i, v){
					$("#"+sdapi+" option:last").remove();
					$sdapi.append("<option value='"+v.value+",1'>"+v.text+"[人员]</option>");
				});
			}else{
				$.each($("#"+spi).find("option:selected"),function(i, v){
					$("#"+sdapi+" option[value='"+v.value+",1']").remove();
					$sdapi.append("<option value='"+v.value+",1'>"+v.text+"[人员]</option>");
					$("#"+spi+" option[value='"+v.value+"']").remove();
				});
			}
		});
		/**
		 * 人员select选择框双击事件--向左添加
		 */
		$sdapi.dblclick(function(){
			$.each($sdapi.find("option:selected"),function(i, v){
				if(v.text.indexOf("[人员]") != -1){
					$("#"+spi+" option[value='"+v.value.split(",")[0]+"']").remove();
					$("#"+spi).append("<option value='"+v.value.split(",")[0]+"'>"+v.text.substr(0,v.text.indexOf("[人员]"))+"</option>");
				}
			});
			$sdapi.find("option:selected").remove();
		});
		/**
		 * 确定按钮
		 */
		$("#"+cbi).click(function(){
			if($("#"+odpdi+" select[id='"+sdapi+"']").find("option").length > 0){
				var data = [];
				$("#"+odpdi+" select[id='"+sdapi+"']").find("option").each(function(i, val){
					var t = "";
					if(val.value.split(",")[1] == 1){
						t = val.text.substr(0,val.text.indexOf("[人员]"));
					}else{
						t = val.text.substr(0,val.text.indexOf("[组织]"));
					}
					data[i] = {
						id: val.value.split(",")[0],
						type: val.value.split(",")[1],
						text: t
					}
			    });
				$mii.select2("data", data);// 回显到文本框中
				clear(spi, sdapi);
				if(callback != null)
					callback(data);
				removeValidSelect2(mii, single);
				select_tree($mii);
			}else{
				msgBox.info({content:'请选择组织或人员',type:'fail'});
				return false;
			}
			$("#"+odpdi).modal("hide");
		});
	};
	
	/**
	 * 弹出人员部门控件
	 * @param o	参数对象
	 * var o = {
	 * 	echoData: [{id:"111",text:"111",type: 1},{id:"222",text:"222",type: 1}]
	 *	callbackFunction: call
	 * }
	 */
	$.fn.deptAndPersonControl.openDeptAndPerson = function(o) {
		var opts = $.extend({}, $.fn.deptAndPersonControl.openDefaults, o);
		openInit();
		var html = // 人员部门界面
			['<div class="modal fade" id="'+openDeptAndPersonDivId+'" aria-hidden="false">'];
		html.push('<div class="modal-dialog modal-tree w530">');
			html.push('<div class="modal-content">');
				html.push('<div class="modal-header clearfix">');	//onclick="clear(\''+selectPersonId+'\',\''+selectDeptAndPersonId+'\');"
					html.push('<button type="button" class="close" data-dismiss="modal">×</button>');
					html.push('<h4 class="modal-title fl">组织人员选择</h4>');
				html.push('</div>');
				html.push('<div class="modal-body clearfix" style="overflow-x: hidden;">');
					html.push('<div class="w200 fl">');
						html.push('<section class="tree-ul tree-scroll">');
							html.push('<div id="'+treeId+'" class="ztree"></div>'); // 组织树
						html.push('</section>');
						html.push('<section class="m-t-md">');
							html.push('<select id="'+selectPersonId+'" multiple="true" class="w200 tree-select"></select>');	// 人员选择框
						html.push('</section>');
					html.push('</div>');
					html.push('<section class="m-l m-r fl tree-operate">');
						html.push('<a href="#" class="tree-move-down" id="'+rightId+'"><i class="fa fa-double-angle-right"></i></a> ');
						html.push('<a href="#" class="tree-move-up" id="'+leftId+'"><i class="fa fa-double-angle-left"></i></a>');
					html.push('</section>');
					html.push('<section class="fl pos-rlt">');
						html.push('<select id="'+selectDeptAndPersonId+'" multiple="true" class="w170 tree-scroll-right tree-s-r tree-select"></select>');	// 组织与人员选择框
						html.push('<div class="tree-move"> ');
							html.push('<a href="#" class="tree-move-up" id="'+upId+'"><i class="fa fa-caret-up"></i></a> ');
							html.push('<a href="#" class="tree-move-down" id="'+downId+'"><i class="fa fa-caret-down"></i></a> ');
						html.push('</div>');
					html.push('</section>');
					html.push('</div>');
					html.push('<div class="modal-footer form-btn">');
						html.push('<button id="'+confirmBtnId+'" class="btn dark" type="button">确 定</button>' );
						html.push('<button class="btn" type="reset" id="close" onClick=$(\"#'+openDeptAndPersonDivId+'\").modal(\"hide\");>取 消</button>');
				            // '<button class="btn" type="reset">全 选</button>'+
					html.push('</div>');
				html.push('</div>');
			html.push('</div>');
		html.push('</div>');
		open(treeId, openDeptAndPersonDivId, html.join(''), selectPersonId, rightId, leftId, selectDeptAndPersonId, upId, downId, confirmBtnId, opts.callbackFunction, opts.echoData, opts.single);
	};
	
	/**
	 * 打开初始化个ID下标
	 */
	var openInit = function(){
		index = parseInt(index) + 1;							//	控件下标
		openDeptAndPersonDivId = "openDeptAndPerson" + index;	//	部门与人员DIVId
		treeId = "tree" + index;								//	树控件的ID
		selectPersonId = "selectPerson" + index;				//	选择人员控件的ID
		selectDeptAndPersonId = "selectDeptAndPerson" + index;	//	选择部门与人员控件的ID
		rightId = "right" + index;								//	向右按钮ID
		leftId = "left" + index;								//	向左按钮ID
		upId = "up" + index;									//	向上按钮ID
		downId = "down" + index;								//	向下按钮ID
		confirmBtnId = "confirmBtn" + index;					//	确认按钮ID
	};
	
	/**
	 * 打开
	 */
	var open = function(ti, odpdi, h, spi, ri, li, sdapi, ui, di, cbi, callback, echoData, single){
		$(h).empty();
		$(h).appendTo("body");
		
		var $sdapi = $("#"+sdapi);
		
		//回显
		if(echoData != null){
			$.each(echoData, function(i, v){
				if(v.type == 1){
					$sdapi.append("<option value='"+v.id+","+v.type+"'>"+v.text+"[人员]</option>");
				}else{
					$sdapi.append("<option value='"+v.id+","+v.type+"'>"+v.text+"[组织]</option>");
				}
			});
		}
		
		var setting = {
			check:{
				enable: true,// 设置 zTree 的节点上是否显示 checkbox/radio
				nocheckInherit: true,// 是否自动继承父节点属性
				chkStyle: "checkbox",// 勾选框类型(checkbox 或 radio)
				chkboxType: { "Y" : "s", "N" : "ps" }
			},
			view:{
				selectedMulti: false,// 设置是否允许同时选中多个节点
				showLine: true// 设置 zTree 是否显示节点之间的连线
			},
			data:{simpleData:{enable:true}},// 确定zTree数据不需要转换为JSON格式,true是需要
			callback:{
				beforeClick: function(treeId, treeNode){
					if(treeNode.isChecked == 0){
						return false;
					}
					return true;
				},
				beforeCheck: function(treeId, treeNode){	// 节点改变删除select框中的数据
					var unSelectNodes = tree.getCheckedNodes(true);
					for(var i=0; i<unSelectNodes.length; i++) {
						if(unSelectNodes[i].users.length > 0){
							for(var j=0;j<unSelectNodes[i].users.length;j++){
								$("#"+spi+" option[value='"+unSelectNodes[i].users[j].id+"']").remove();
							}
						}
					}
					if(treeNode.isChecked == 0){
						return false;
					}
					return true;
				},
				onCheck: function(treeId, treeNode){	// 选中节点把人员添加到select框中
					var selectNodes = tree.getCheckedNodes(true);
					for(var i=0; i<selectNodes.length; i++) {
						if(selectNodes[i].users.length > 0){
							for(var j=0;j<selectNodes[i].users.length;j++){
								$("#"+spi).append("<option value='"+selectNodes[i].users[j].id+"'>"+selectNodes[i].users[j].displayName+"</option>");
							}
						}
					}
				},
				onClick: function(event, treeId, treeNode, clickFlag){	// 用于捕获节点被点击的事件回调函数
					// console.info(clickFlag);
				},
				onDblClick: function(event, treeId, treeNode){
					var treeObj = $.fn.zTree.getZTreeObj(ti);
					var nodes = treeObj.getSelectedNodes();
					if(nodes.length==0){
						return false;
					}
					if(single){
						$("#"+sdapi+" option:last").remove();
						$sdapi.append("<option value='"+nodes[0].id+",2'>"+nodes[0].name+"[组织]</option>");
						treeObj.cancelSelectedNode(nodes[0]);
					}else{
						$("#"+sdapi+" option[value='"+nodes[0].id+",2']").remove();
						$sdapi.append("<option value='"+nodes[0].id+",2'>"+nodes[0].name+"[组织]</option>");
						treeObj.cancelSelectedNode(nodes[0]);
					}
				}	
			}
		};
		
		$.getJSON(getRootPath()+"/department/getOrgAndPersonTree.action", function(data) {
			var zNodes = [];
			$.each(data, function(i, o) {
				zNodes[i] = {
					id : o.id,
					pId : o.parentId,
					name : o.name,
					deptType : o.deptType,
					iconSkin : o.deptType==0 ? "fa-flag" : "fa-office",
					users: o.users,
					isChecked: o.isChecked,
					chkDisabled : o.isChecked == 1 ? false : true
				};
			});
			tree = $.fn.zTree.init($("#"+odpdi+" #"+ti), setting, zNodes);
			tree.expandAll(true);
			
			if(echoData != null){
				$.each(echoData, function(i, v){
					if(v.type == 2){
						var node = tree.getNodeByParam("id", v.id, null);
						if(node != null){
							tree.checkNode(node, true, false, true);
						}
					}
				});
			}
		});
		
		$("#"+ odpdi).modal("show");
		
		$("#"+ri).click(function(){
			var treeObj = $.fn.zTree.getZTreeObj(ti);
			var nodes = treeObj.getSelectedNodes();
			var selectOptionCount = $("#"+spi).find("option:selected").length;
			if(nodes.length <= 0 && selectOptionCount <= 0){
				msgBox.tip({content: "请选择要添加的组织或人员", type:'fail'});
			}else{
				if(nodes.length > 0){
					if(single){
						$("#"+sdapi+" option:last").remove();
						$sdapi.append("<option value='"+nodes[0].id+",2'>"+nodes[0].name+"[组织]</option>");
						treeObj.cancelSelectedNode(nodes[0]);
					}else{
						$("#"+sdapi+" option[value='"+nodes[0].id+",2']").remove();
						$sdapi.append("<option value='"+nodes[0].id+",2'>"+nodes[0].name+"[组织]</option>");
						treeObj.cancelSelectedNode(nodes[0]);
					}
				}
				if(selectOptionCount > 0){
					if(single){
						$.each($("#"+spi).find("option:selected"),function(i, v){
							$("#"+sdapi+" option:last").remove();
							$sdapi.append("<option value='"+v.value+",1'>"+v.text+"[人员]</option>");
						});
					}else{
						$.each($("#"+spi).find("option:selected"),function(i, v){
							$("#"+sdapi+" option[value='"+v.value+",1']").remove();
							$sdapi.append("<option value='"+v.value+",1'>"+v.text+"[人员]</option>");
							$("#"+spi+" option[value='"+v.value+"']").remove();
						});
					}
					
				}
			}
		});
		
		$("#"+li).click(function(){
			var selectOptionCount = $("#"+sdapi).find("option:selected").length;
			if(selectOptionCount > 0){
				$.each($sdapi.find("option:selected"),function(i, v){
					if(v.text.indexOf("[人员]") != -1){
						$("#"+spi+" option[value='"+v.value.split(",")[0]+"']").remove();
						$("#"+spi).append("<option value='"+v.value.split(",")[0]+"'>"+v.text.substr(0,v.text.indexOf("[人员]"))+"</option>");
					}
				});
				$sdapi.find("option:selected").remove();
			}else{
				msgBox.tip({content: "请选择要移除的组织或人员", type:'fail'});
			}
		});
		
		$("#"+ui).click(function(){
			if($("#"+sdapi).val() == null){
				msgBox.tip({content: "请选择升序的组织或人员", type:'fail'});
			}else{
				if($("#"+sdapi+" option:selected").length > 1){
					msgBox.tip({content: "请选择一项进行调整", type:'fail'});
				}else{
					var optionIndex = $sdapi.get(0).selectedIndex; 
					if(optionIndex > 0){ 
						$('#'+sdapi+' option:selected').insertBefore($('#'+sdapi+' option:selected').prev('option')); 
					} 
				}
			}
		});
			
		$("#"+di).click(function(){
			if($sdapi.val() == null){
				msgBox.tip({content: "请选择降序的组织或人员", type:'fail'});
			}else{
				if($("#"+sdapi+" option:selected").length > 1){
					msgBox.tip({content: "请选择一项进行调整", type:'fail'});
				}else{
					var optionLength = $sdapi[0].options.length; 
					var optionIndex = $sdapi.get(0).selectedIndex; 
					if(optionIndex < (optionLength-1)){ 
						$('#'+sdapi+' option:selected').insertAfter($('#'+sdapi+' option:selected').next('option')); 
					}  
				}
			}
		});
		
		/**
		 * 人员select选择框双击事件
		 */
		$("#"+spi).dblclick(function(){
			if(single){
				$.each($("#"+spi).find("option:selected"),function(i, v){
					$("#"+sdapi+" option:last").remove();
					$sdapi.append("<option value='"+v.value+",1'>"+v.text+"[人员]</option>");
				});
			}else{
				$.each($("#"+spi).find("option:selected"),function(i, v){
					$("#"+sdapi+" option[value='"+v.value+",1']").remove();
					$sdapi.append("<option value='"+v.value+",1'>"+v.text+"[人员]</option>");
					$("#"+spi+" option[value='"+v.value+"']").remove();
				});
			}
		});
		
		/**
		 * 人员select选择框双击事件--向左添加
		 */
		$sdapi.dblclick(function(){
			$.each($sdapi.find("option:selected"),function(i, v){
				if(v.text.indexOf("[人员]") != -1){
					$("#"+spi+" option[value='"+v.value.split(",")[0]+"']").remove();
					$("#"+spi).append("<option value='"+v.value.split(",")[0]+"'>"+v.text.substr(0,v.text.indexOf("[人员]"))+"</option>");
				}
			});
			$sdapi.find("option:selected").remove();
		});
		
		$("#"+cbi).click(function(){
			if($("#"+odpdi+" select[id='"+sdapi+"']").find("option").length > 0){
				var data = [];
				$("#"+odpdi+" select[id='"+sdapi+"']").find("option").each(function(i, val){
					var t = "";
					if(val.value.split(",")[1] == 1){
						t = val.text.substr(0,val.text.indexOf("[人员]"));
					}else{
						t = val.text.substr(0,val.text.indexOf("[组织]"));
					}
					data[i] = {
						id: val.value.split(",")[0],
						type: val.value.split(",")[1],
						text: t
					}
			    });
				clear(spi, sdapi);
				if(callback != null)
					callback(data);
			}else{
				msgBox.info({content:'请选择组织或人员',type:'fail'});
				return false;
			}
			$("#"+odpdi).modal("hide");
		});
	};
	
	function removeValidSelect2(controlId, isCheckOrRadio){
		$("#s2id_"+controlId).removeClass("error"); 
		if(isCheckOrRadio){
			$("#s2id_"+controlId).parent().parent().next(".help-block").html("");
		}else{
			$("#s2id_"+controlId).parent().next(".help-block").html("");
		}
	}
	
	/**
	 * 清除选择框的值
	 */
	function clear(spi, sdpi){
		$("#"+spi).empty();
		$("#"+sdpi).empty();
	};
	
	/**
	 * 人员控件的展开、收起
	 */
	function select_tree(el){
		var el = el.closest(".select2-wrap") || null;
		el.find(".selection-tree-btn .sq").hide();
		var $parent = el.find(".selection-tree-btn").parent();
		//var tree_lh = $parent.find(".select2-choices").height();
		var tree_lh = $parent.find(".select2-choices").actual("height");
		if(tree_lh >= 66){
			el.find(".select2-container").css("max-height","67px");
			el.find(".selection-tree-btn").show();
			if(zkAndSqCount==0){
				el.find(".selection-tree-btn .sq").hide();
				el.find(".selection-tree-btn .zk").show();
				//zkAndSqCount++;
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
		});
		$parent.find(".sq").click(function(){
			var $this = this;
			var $parent = el.find(".selection-tree-btn");
			el.find(".select2-container").css("max-height","67px");
			$($this).hide();
			$parent.find(".zk").show();
			if(typeof resetPostscript == 'function'){
				resetPostscript();
			}
		});
	}
	
	/**
	 * 控件的defaults[非弹出式]
	 */
	$.fn.deptAndPersonControl.defaults = {
		single : false,			//单选或多选
		widgetId : null,		//主页控件ID
		widgetName: null,		//主页控件NAME
		echoData : null,		//回显数据
		isReadonly : false,		//是否只读
		callbackFunction : null	//回调函数
	};
	
	/**
	 * 控件的defaults[弹出式]
	 */
	$.fn.deptAndPersonControl.openDefaults = {
		single : false,			//单选或多选
		echoData : null,		//回显数据
		callbackFunction : null	//回调函数
	};
	
	/**
	 * 清空控件的值
	 * @param controlId 控件的ID
	 */
	$.fn.deptAndPersonControl.clearValue = function(controlId){
		$("#"+controlId).select2("data", "");
	}
	
	$.fn.deptAndPersonControl.getValue = function(controlId){
		return $("#"+controlId).select2("data");
	}
})(jQuery);