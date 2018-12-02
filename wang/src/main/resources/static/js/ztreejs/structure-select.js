(function($){

	var that = this;
	/**				
	* 组织结构类
	* @class struture 这个类是用于组织结构选择的(select版本)
	* @constructor
	* @version 2.1
	* @author lxx
	* @requires jquery 1.8.3
	* @requires jquery.ztree.all-3.5
	* @requires jquery.multiselect2side.js
	* @requires jquery.quicksearch.js
	* @requires jsonselect.js
	*/
	$.struture = {
		/**
		* 系统核心配置<br>
		* @example
		*  systemSetting.selectfunsetting 父节点与子节点选择设置<br>
		   node - 只可以选节点 <br>
		   child- 只可以选最底层节点 <br>
		   all -  全部都可以选<br>
		*  systemSetting.selectresultsetting 选择结果数量控制<br>
		   radio -最终结果单选 <br>
		   checkbox - 最终结果多选<br>
		*  systemSetting.selectordersetting 对选择结果集是否排序控制<br>
		   true - 排序<br>
		   false - 不排序<br>
		   @type String
		*/
		systemSetting : {
			selectfunsetting:"child", //node - 只可以选节点 child- 只可以选最底层节点 all -  全部都可以选
			selectresultsetting:"checkbox", //radio -最终结果单选 checkbox - 最终结果多选
			selectordersetting:true //对选择结果集是否进行排序
		},
			/**
			* 组织结构初始化方法
			* @param {String} treename 树容器 id
			* @param {String} selectname 选择器 
			* @param {Object} nodes 树 数据 
			* @param {Object} opt 参数配置

			* @example nodes 格式说明<br>
zNodes = [
	{ id:91,  pIds:0,isparent:true, name:"随意勾选 1", open:true},
	{ id:911, pIds:91,isparent:true, name:"随意勾选 1-1", open:true},
	{ id:9111, pIds:911,isparent:true, name:"随意勾选 1-1-1",child:[{childrenid:"1111444444",name:"的顶顶顶顶顶",lxx:"6"}]},
	{ id:9112, pIds:911,isparent:true, name:"随意勾选 1-1-2"},
	{ id:912, pIds:91,isparent:true, name:"随意勾选 1-2", open:true},
	{ id:9121, pIds:912,isparent:true, name:"随意勾选 1-2-1"},
	{ id:9122, pIds:912,isparent:true, name:"随意勾选 1-2-2"},
	{ id:92, pIds:0,isparent:true, name:"随意勾选 2", checked:true, open:true},
	{ id:921, pIds:92,isparent:true, name:"随意勾选 2-1"},
	{ id:922,pIds:92,isparent:true, name:"随意勾选 2-2", open:true},
	{ id:9221, pIds:922,isparent:true, name:"随意勾选 2-2-1", checked:true},
	{ id:9222, pIds:922,isparent:true, name:"随意勾选 2-2-2"},
	{ id:923, pIds:92,isparent:true, name:"随意勾选 2-3"}
];

	* @example opt 格式说明<br>
			
	根据你的数据(后台传递过来的json)结构配置相应的 web json<br>
	请确保下面所配置的属性在传递过来的json串中均是存在的<br>
	opt = {
		id:"id",//相对父节点的主键 唯一 标识
		name:"name",//display name 就是你的展示那个值
		parentid:"pIds",//节点中的父节点标识(相对)
		child:"child",//最底层子节点json结构中的key名称
		childid:"childrenid",//最底层子节点的唯一标识
		parentback:["id","user_online_flag"],//所有根节点返回数据需要的属性
		childback:["childrenid"],//最底层的子节点返回数据需要的属性
		treeSearch:'treeSearch',//搜索的input框id (可选)
		screenattribute:{ // 请一定要确保你传递过来的json中是含有一下配置的属性的 (尤其是childid 下 因为 过滤的是子节点)
			searchType1:'lxx', //页面上过滤select 的id 已经其对应的过滤属性
			searchType:'childrenid'
		},//过滤筛选的属性(可选)
		order:"order"//排序的属性(可选)
	}
			*/
		init : function(treeId,selectId,nodes,option){
			var $init = this; //重新赋予this变量 防止污染
			/**
		    * @class $public
			* init 方法 返回的句柄.使用其句柄可以调用相应方法
			* @type object
			*/
			$public = {};//暴露给外部的对象
			treeSearchNode = []; //用于对树搜索返回的节点数据的保存
			
			defaultopt = {
				id:"id",//相对父节点的主键 唯一 标识
				name:"name",//display name 就是你的展示那个值
				parentid:"pIds",//节点中的父节点标识(相对)
				child:"child",//最底层子节点json结构中的key名称
				childid:"childrenid"

			};
			opt = $.extend(defaultopt,option);

			/**
			* @description 获取当前select对象已选择的数据对象集合
			* @param {String} type 返回数据类型 
			* @example
			*			    parent 只返回父节点 []
			*			    child  只返回子节点 []
			*			    combination 父节点,子节点同时返回有区分 Object{parent[],child[]}
			*			    all 父节点,子节点同时返回无区分 []
			* @returns 返回数组对象
			*/
			$public.getStructureObj = function(type){
				return $.struture.getStructureObj(selectId,type,opt);
			};
			/**
			*@description 重新渲染树
			*@param {Object} nodes_ 为重新渲染树的节点数据
			*
			*/
			$public.TreeRender = function(nodes_){
				$.fn.zTree.init($("#"+treeId), treeSetting, nodes_);
				zTree = $.fn.zTree.getZTreeObj(treeId);
				delUnchecked();
			};
			/**
			*@description  向备选的select中插入人员
			*@param {Array} 需要插入的数据
			*@param {Object} 需要插入的数据父节点对象
			*@param {Array} 需要过滤的数组
			*
			*/
			var setSelectData = function(arrayData,val_parent,screenarr){
				var selectedDomArray=[];//已选择数据存储
				//把已选择的数据存入一个集合$selected_中
				$selected_ = getSelected(1);
				$selected_.each(function(index, el) {
					selectedDomArray.push(el.getAttribute("id"));
				});
				$.each(arrayData || [], function(index, val_child) {
					//添加数据
					sig = true;
					_id = val_child[opt.childid || opt.id]
					if ($.inArray(_id,selectedDomArray)==-1) { //若果未被选中则添加
						if (screenarr) {
							if ($.inArray(val_child[opt.childid],screenarr)==-1) {
								sig =false;
							};
						};
						if (sig) {	
							var $option = $('<option>');
							var $obj={};								
							$obj.id = val_child[opt.childid];
							$obj.isparent = false;//是不是部门节点
							$obj.order = val_child[opt.order];//用于排序
							$obj.z_l_pid = val_parent[opt.id];
							$obj.value = val_child[opt.name]+" ["+val_parent[opt.name]+"]";
							$obj.name = val_child[opt.childid];
							$.each(opt.childback || [], function(index, otherval) { // 其它自然属性
								if(val_child[otherval]){
								 $obj[otherval] = val_child[otherval];
								}
							});
							$.each(opt.screenattribute || {}, function(index, otherval) { // 其它自然属性
							   $obj[otherval] = val_child[otherval];
							});
							$option.attr($obj);
							if($.struture.systemSetting.selectordersetting){
								$('#'+selectId).multiselect2side('addOption',$obj,opt.order)
							}else{
								$('#'+selectId).multiselect2side('addOption',$obj,opt.order)
							}
						};
					}	
				});
				
						
			}
			/**
			* @description  清空备选内容
			*/
			var delUnchecked = function(){
				$('#'+selectId+'ms2side__sx').empty();
			};
			/**
			* @description 获取备选或已选的select项
			* @param{String} sig 0:备选 ; 1:备选
			* @returns 被选中的select 的 jquery dom 对象
			*/
			var getSelected = function(sig){
				var $temp;
				if (sig) {
					$temp = $('#'+selectId+'ms2side__dx>option')
				}else{
					$temp = $('#'+selectId+'ms2side__sx>option')
				}
				return  $temp;
			}
			
			/**
			* @description 对备选内容进行过滤的时候对左右选择按钮的显示进行校验
			*/
			var selectChange = function(){
				$('#'+selectId+'ms2side__dx').trigger('change');
			}
			/**
			* @description 把过滤框全部置为 '全部' 位置
			*/
			var searchChangeAll = function(){
				if (opt.screenattribute && !$.isEmptyObject(opt.screenattribute)) {
					$.each(opt.screenattribute, function(key, val) {
						var count=$("#"+key+" option").length;
						  for(var i=0;i<count;i++)  {
								if($("#"+key).get(0).options[i].value == '') { 
						            $("#"+key).get(0).options[i].selected = true 
						            return;  
						        }  
						  }
					});
				}
			}
			/**
			* @description 触发所有过滤框的选择 
			*/
			var searchTrigger = function(){
				if (opt.screenattribute && !$.isEmptyObject(opt.screenattribute)) {
					$.each(opt.screenattribute, function(key, val) {
						$("#"+key).trigger('change');
					});
				}
			}
			/**
			* @description 树形结构的模糊查询,把符合结果放入treeSearchNode集合中
			* 
			*/
			var treeFuzzySearch = function(array){
				$.each(array, function(index,val) {
					var ishas;
					if(typeof val[opt.id] ==="number"){
						ishas = ':expr(x='+val[opt.id]+'))';
				 	}else{
				 		ishas = ':expr(x="'+val[opt.id]+'"))';
				 	}
					var isSelect = JSONSelect.match(':root>:has(.'+opt.id+ishas,treeSearchNode);
					if (isSelect.length==0) {
							treeSearchNode.push(val);
					};
			
					if(val[opt.parentid] && val[opt.parentid] != "0" && val[opt.parentid] != 0){
						var resultSet;

					 	if(typeof val[opt.parentid] ==="number"){
					 		 resultSet = JSONSelect.match(':root>:has(.'+opt.id+':expr(x='+val[opt.parentid]+'))',nodes);
					 	}else{
					 		 resultSet = JSONSelect.match(':root>:has(.'+opt.id+':expr(x="'+val[opt.parentid]+'"))',nodes);
					 	}
					 	treeFuzzySearch(resultSet);	
					 }else{
					 	return false;
					 }		 
				});
			}
			
			/**
			* @description 人员搜索.重新构建组织结构树.在符合搜索结果的基础上.并把符合的人员结果集显示在备选框中
			*/
			function searchNode_new() {

				treeSearchNode = [];
				var value = $.trim(key.get(0).value).replace(new RegExp('\\\\',"gm"),'\\\\').replace(new RegExp('\\\"',"gm"),'');
				if (value === "") {
					$public.TreeRender(nodes);
				}else{
					
					var contains_value;
					if(typeof value ==="number"){
						contains_value =':contains('+value+'))';
				 	}else{
				 		contains_value = ':contains("'+value+'"))';
				 	}
				 	var resultSet = JSONSelect.match(':root>:has(.'+opt.name+contains_value,nodes);
					treeFuzzySearch(resultSet);
					if (treeSearchNode.length>0) {

						var arr = JSONSelect.match('.'+opt.child+' .'+opt.name+':contains("'+value+'")~.'+opt.childid,treeSearchNode)
					
						$public.TreeRender(treeSearchNode);
						
						 allnode =  zTree.transformToArray(zTree.getNodes());

						  $.each(allnode, function(index, val) {
						  	if (!val.isParent) {
						  		setSelectData(val[opt.child] || [],val,arr)
						  	};
						 	
						  });
					};
				}
				zTree.expandAll(true); // 默认节点全部展开
				//searchTrigger();//如果按照过滤框的选择进行过滤
				searchChangeAll();//如果把过滤框置为全部
			
			}
			/**
			*@deprecated
			*@description 选择树根据搜索内容进行筛选过滤标红
			*
			*
			*/
			var lastValue = "", nodeList = [], fontCss = {};
			function searchNode(e) {
				if (true) {
					var keyType = "name";
					var value = $.trim(key.get(0).value);
					if (lastValue === value) return;
					lastValue = value;
					if (value === "") {
						updateNodes(false);
						return;
					}
					updateNodes(false);
					nodeList = zTree.getNodesByParamFuzzy(keyType, value);
				};
				updateNodes(true);
			}
			/**
			*@deprecated
			*@description 选择树根据搜索内容进行筛选过滤节点更新
			*
			*
			*/
			function updateNodes(highlight) {
				for( var i=0, l=nodeList.length; i<l; i++) {
					nodeList[i].highlight = highlight;
					zTree.updateNode(nodeList[i]);
				}
			}
			/**
			*@deprecated
			*@description 节点标红
			*
			*
			*/
			function getFontCss(treeId, treeNode) {
				return (!!treeNode.highlight) ? {color:"#A60000", "font-weight":"bold"} : {color:"#333", "font-weight":"normal"};
			}
			//------------------功能-------------------------/
			/*
			* @description 鼠标滑过时  用于出现添加选择按钮及其操作
			* @parame treeId 整个树的id
			* @parame treeNode 树节点对象
			*/
			var addHoverDom =  function(treeId, treeNode) {
				var sObj = $("#" + treeNode.tId + "_span");
				if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
				var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
					+ "' title='选中此节点' onfocus='this.blur();'></span>";
				sObj.after(addStr);
				var btn = $("#addBtn_"+treeNode.tId);
				if (btn){
					btn.on("click", function(event){
						event.preventDefault();
						event.stopPropagation();

						var temp = 	$('#'+selectId+'ms2side__dx>option[id="'+treeNode[opt.id]+'"]');
						//$('#'+selectId+' > option[datasig="'+treeNode[opt.id]+'"]');
						if (temp.length<=0) {//如果没有选择过
							var $option = $('<option>');
							var $obj={};
							$.each(opt.parentback || [], function(index, val) { // 其它自然属性
								 $obj[val] = treeNode[val];
							});
							$.each(opt.screenattribute || {}, function(index, otherval) { // 其它自然属性
								if(treeNode[otherval]){
								   $obj[otherval] = treeNode[otherval];
								}
							});
							$obj.id = treeNode[opt.id];
							$obj.isparent = true;
							$obj.order = treeNode[opt.order];//用于排序
							$obj.value = treeNode[opt.name];
							$obj.name = $obj.id;
							$obj.selected = true; //添加父节点的时候认为一定是选中的状态
							if($.struture.systemSetting.selectresultsetting == 'radio'){ //判断最终选择列是不是单选
								$($('.ms2side__options').find('.RemoveAll').get(0)).trigger('click');
							}
							if($.struture.systemSetting.selectordersetting){

								$('#'+selectId).multiselect2side('addOption',$obj,opt.order);
							}else{
								$('#'+selectId).multiselect2side('addOption',$obj,opt.order);
							}
						};

					});
				} 
			};
			/**
			* @description 节点滑动离开时  用于出现添加选择按钮及其操作
			* @parame{String} treeId 整个树的id
			* @parame{Object} treeNode 树节点对象
			*/
			var removeHoverDom =  function(treeId, treeNode) {
				$("#addBtn_"+treeNode.tId).off().remove();
			};
			/*
			* @description用于捕获 checkbox / radio 被勾选 或 取消勾选的事件回调函数
			* @parame{Object} event 函数对象
			* @parame{String} treeId 整个树的id
			* @parame{Object} treeNode 树节点对象
			*/
			var zTreeOnCheck = function(event, treeId, treeNode){
				
				
				//清空 select 左侧数据
				delUnchecked();
				//获取树选择的节点 
				check = zTree.getCheckedNodes(true);
				$.each(check || [], function(index, val) {

					if (!val.getCheckStatus().half) {//不是半选状态
						setSelectData(val[opt.child] || [],val) // 插入相应数据
					};
				});
				searchTrigger();
				selectChange();
			}
			/*
			* @description 点击节点之前执行的回调函数 用于关联复选框选择切换
			* 
			*/
			var zTreeBeforeClick = function(treeId, treeNode, clickFlag) {
				zTree.checkNode(treeNode, null, true, true);
				return false;
			}
			//------------------配置-----------------/
			var treeSetting = {
				check: {
					enable: true
				},
				data: {
					simpleData: {
						enable: true,
						idkey:opt.id,
						pIdKey:opt.parentid
					}
				},
				view:{
					fontCss: getFontCss,
					dblClickExpand:false,
					addHoverDom: addHoverDom,
					removeHoverDom: removeHoverDom,
					selectedMulti: false
				},callback: {
					onCheck : zTreeOnCheck,
					beforeClick : zTreeBeforeClick
				}
			};
			switch ($.struture.systemSetting.selectfunsetting) {
	          case "child":
				treeSetting.view={fontCss: getFontCss}
	          	break;
	          case "node":
	           	treeSetting.check.enable =false;
	          	break;
	          case "all": 
	          	break;       
	        };
	        $.fn.zTree.init($("#"+treeId), treeSetting, nodes);
			zTree = $.fn.zTree.getZTreeObj(treeId);
			zTree.expandAll(true); // 默认节点全部展开
			

			if (treeSearch) {
				key = $("#"+opt.treeSearch);
				key.on("propertychange",searchNode_new).on("input",searchNode_new).on("oninput",searchNode_new);

				if (/msie/.test(navigator.userAgent.toLowerCase())) {
					if ($.support.leadingWhitespace) {
						key.keyup(function(event){ 
				       	 if(event.keyCode === 8){//删除
				       	 	searchNode_new();
				       	 }
				   		});
					};
					
				};
				//$("#searchbtn").on('click',searchNode_new);
			};
			
			//备选人员根据条件过滤
			if (opt.screenattribute && !$.isEmptyObject(opt.screenattribute)) { 
				
				$.each(opt.screenattribute, function(key, val) {
				 	$('#'+key).quicksearch('#'+selectId+'ms2side__sx>*',{
						attrOrHtml:'attr',
						screenattribute:opt.screenattribute,
						'testQuery': function (query, txt, _row) { //这个方法是多个条件过滤时候的过滤条件
							var sig = true;
							$.each(opt.screenattribute, function(key_attr, val_attr) {
								if(query[key_attr] != "" && query[key_attr]!=txt[val_attr]){
									sig = false;
									return false;
								}
							});
							
							return sig;
						},
						'onAfter':function(){//过滤之前
							selectChange();//左右选择按钮显示校验
							$('#'+selectId+'ms2side__dx').trigger('sortselect2side');
						}
					});
				});
			}
			//最多选择数量 -1 为不限制
			var setmaxSelected = -1;
			if($.struture.systemSetting.selectresultsetting == 'radio'){ //如果是单选则最大选择项为 1
				setmaxSelected = 1;
			} 
			//左右选择框初始化
			$('#'+selectId).multiselect2side({
				order:opt.order,
				maxSelected:setmaxSelected,
				removeAllafter:function(){//全部删除后的回调方法
					searchTrigger();//更新搜索
				},
				removeOneafter:function(){//单箭头删除后的回调函数
					searchTrigger();
				},
				deselectBefore:function(rightSelected){//已选框双击回调事件
				
					var sig = true; //为 插件提供 删除判断依据
					if($('[isparent=true][id="'+rightSelected.attr('id')+'"]').length>0){//是父节点并存在
							rightSelected.remove();
							sig =false;
					}else{
						//子节点 当双击时应判断树形结构是否被勾选
						check = zTree.getCheckedNodes(true);
						var pid = $('#'+selectId+'ms2side__dx>option[id="'+rightSelected.attr('id')+'"]').attr("z_l_pid");
						var delsig = true;
						$.each(check || [], function(index, val) {
							if (!val.getCheckStatus().half) {//不是半选状态
								if (pid==val.id) {
									delsig = false; //如果发现父节点已经被勾选 则状态置为false 不在删除节点
								};
							};	
						});
						if (delsig) {
							rightSelected.remove();
							sig =false;
						};
					}	
					return sig;
				}
				
			});
			/**
			* 
			* @description 用来返回init这个方法内部$public的 实际上是通过此方法转换的this. 
			*
			*/
			var returnThisObj  = function(){
				return $init = this.$public;
			}
			return returnThisObj();
			
		},
		/**
		* @ignore
		* @description 获取已选择的数据对象集合
		* @parame selectid
		* @parame type 有如下类型 
		*			    parent 只返回父节点
		*			    child  只返回子节点
		*			    combination 父节点,子节点同时返回有区分
		*			    all 父节点,子节点同时返回无区分
		* @parame sttingback 要返回的值的设置
		*/
		getStructureObj : function(selectId,type,sttingback){
			var type = type || "all";
			var temp;
			if(type=="combination"){
				temp = {} ;
			}else{
				temp = [] ;		
			}
		
			var getdata = function(el){
				var attrs= $(el).get(0).attributes;
				var $option = {}
				var back = [] ;
				if ($(el).attr("isparent")=="true") {
					back = sttingback.parentback;
				}else{
					back = sttingback.childback;
				}

				$.each(attrs, function(index, val) {
					if ($.inArray(val.name, back)>-1) {	
							$option[val.name]=val.value;
					};
				});
				return $option;	
			}
			$('#'+selectId+'ms2side__dx>option').each(function(index, el) {
				switch(type){

					case "parent":
						if($(el).attr("isparent")=="true"){
							temp.push(getdata(el));
						}
						break;
					case "child":
						if($(el).attr("isparent")=="false"){
							temp.push(getdata(el));
						}
						break;
					case "combination":

						if($(el).attr("isparent")=="true"){
							temp.parent = temp.parent || [];
							temp.parent.push(getdata(el));
						}else{
							temp.child = temp.child || [];
							temp.child.push(getdata(el));
						}
						break;
					case "all":
						temp.push(getdata(el));
						break;
				}
				
			});
			return temp;
		}	

	}
})(jQuery)