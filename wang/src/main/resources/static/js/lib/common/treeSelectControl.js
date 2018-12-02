
(function($){
	var treeSelectControlIndex = 0;
	TreeSelectControl = function(option){
		this.id = treeSelectControlIndex++;
		this.init(option);
	};
	var defaultMappings = {id:"id",name:"name",parentId:"parentId",title:"title",nodeType:"nodeType"};
	
	TreeSelectControl.prototype = {
		constructor: TreeSelectControl,
		init:function(option){
			this.option = option;
			this.openerId = "TreeSelectControlOpener"+this.id;
			this.treeId = "TreeSelectControlTree"+this.id;
			this.openBtnId = "TreeSelectControlOpenBtn"+this.id;
			this.okBtnId = "TreeSelectControlOkBtn"+this.id;
			this.closeBtnId = "TreeSelectControlCloseBtn"+this.id;
			this.controlId = option.controlId;
			this.controlName = option.controlName;
			this.multiMode = option.multiMode;
			this.containerId = option.containerId;
			this.url =option.url;
			this.icon = !option.icon?"fa fa-column-selection":option.icon;
			this.initMappings();
			this.treeNodeData = [];
			this.getTreeData();
			this.initUI();
			this.noParent = option.noParent;
			this.callbackFunction = option.callbackFunction;
			//alert($("#"+this.controlId));
			this.initValue();
		},
		
		initMappings:function(){
			this.mappings = !this.option.mappings?defaultMappings:this.option.mappings;
			this.mappings.id = !this.mappings.id?defaultMappings.id:this.mappings.id;
			this.mappings.text =!this.mappings.name?defaultMappings.name:this.mappings.name;//显示的内容
			this.mappings.name =!this.mappings.name?defaultMappings.name:this.mappings.name;
			this.mappings.title =!this.mappings.title?defaultMappings.title:this.mappings.title;
			this.mappings.parentId =!this.mappings.parentId?defaultMappings.parentId:this.mappings.parentId;			//组织父节点
			this.mappings.nodeType =!this.mappings.nodeType?defaultMappings.nodeType:this.mappings.nodeType;
			this.mappings.icon =!this.mappings.icon?defaultMappings.icon:this.mappings.icon;
		},
		
		initUI:function(){
			this.openner = $(this.getOpenerTemplate()).appendTo("body");
			this.select2 = $(this.getSelect2Template());
			$("#" + this.containerId).empty();
			$("#" + this.containerId).append(this.select2);
			
			thisObject =this;
			$("#"+this.controlId).select2({
			    placeholder: " ",						//文本框占位符显示
			    allowClear: true,							//允许清除
			    maximumInputLength: 10,						//最大输入长度
			    multiple: thisObject.multiMode,	//单选or多选
			    query: function (query){
			        var data = {results: []};
			        $.each(thisObject.treeNodeData, function(){
			            if(query.term.length == 0 || this.text.toUpperCase().indexOf(query.term.toUpperCase()) >= 0 
			            		|| this.hp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
			            		|| this.qp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
			            		|| this.jp.toUpperCase().indexOf(query.term.toUpperCase()) >= 0
			            		|| this.wc.toUpperCase().indexOf(query.term.toUpperCase()) >= 0){
			            	//data.results.push({id: this.id+","+this.parentId, text: this.deptType==0?"[部门]":"[机构]"+this.text});
			            	if(this.click!="false"){
			            		 data.results.push({id: this.id, text: this.text});
			            	}
			            }
			        });
			        query.callback(data);
			    }
			});
			this._attachControlEvent();	
		},
		initValue:function(){
			if(this.option.initValue){
				var select2InitValue = this.option.initValue;
				if(!this.multiMode){
					select2InitValue = this.option.initValue[0];
				}
				$("#"+this.controlId).select2("data",select2InitValue);		
			}
		},
		initTree:function(){
			if(!this.tree){
				this.tree = $.fn.zTree.init($("#"+this.treeId),this.getTreeSetting(), this.treeNodeData);
				this.tree.expandAll(true);
			}
			//return this.tree;
		},
		open:function(){
			$(".select2-container").removeClass("error"); 
			$(".help-block").html("");
			this.initTree();
			var treeObj = this.tree;
			var selectedNode = treeObj.getChangeCheckedNodes();
			if(selectedNode.length>0){
				//treeObj.cancelSelectedNode();
				for(var i=0;i<selectedNode.length;i++){
					treeObj.checkNode(selectedNode[i], false, false, true);
				}
				
			}
			var eData = this.getValue();
			if(eData != null){
				$(eData.split(",")).each(function(i, v){
					var node = treeObj.getNodeByParam("id", v, null);
					if(node != null){
						treeObj.checkNode(node, true, false, true);
					}
				});
			}
			this.openner.modal("show");
		},
		close:function(){
			this.openner.modal("hide");
		},
		_attachControlEvent:function(){
			//var self = this;
			//this.openner.on('click', '.treeSelectControlOpener *', $.proxy(this.click, this));
			$("#"+this.openBtnId).on('click', $.proxy(this.open, this));
			$("#"+this.closeBtnId).on('click', $.proxy(this.close, this));
			$("#"+this.okBtnId).on('click', $.proxy(this.setSelectedValue, this));
			$("#"+ this.controlId).on("change", $.proxy(this.valueChanged, this));
		},
		valueChanged:function(e){
			if(this.callbackFunction != null){
				//alert(this.callbackFunction);
				//alert(this.getValueWithName());
				this.callbackFunction(this.getValueWithName());
			}
		},
//		click:function(e){
//			e.stopPropagation();
//		    e.preventDefault();
//		    this._unset = false;
//		    var target = $(e.target).closest('button, a');
//		    if (target.length === 1){
//		    	if(target.attr("id")==this.openBtnId){
//		    		this.open();
//		    	}
//		    	else if(target.attr("id")==this.closeBtnId){
//		    		this.close();
//		    	}
//		    }
//		},
/*		getSelf:function(){
			return this;
		},*/
		getTreeData:function(){
			thisObject = this;
			$.ajax({
				async: false,
				url : this.url,
				type : 'post',
				/*data: {
				},*/
				success : function(data) {
					$.each(data, function(i, o) {
						thisObject.treeNodeData[i] = {
								id : o[thisObject.mappings.id],						//ID
								click:o[thisObject.mappings.click],
								name : o[thisObject.mappings.name],//显示的内容
								text : o[thisObject.mappings.name],
								title : o[thisObject.mappings.title],
								pId: o[thisObject.mappings.parentId],		//组织父节点
								ext1:!thisObject.mappings.ext1?null:o[thisObject.mappings.ext1],
								ext2:!thisObject.mappings.ext2?null:o[thisObject.mappings.ext2],
								nodeType:o[thisObject.mappings.nodeType],			//组织类型
								hp: Pinyin.GetHP(o[thisObject.mappings.name]),		//两个汉字输入全拼,三个以上的汉字，第一个汉字全拼，后面的汉字简拼
								qp: Pinyin.GetQP(o[thisObject.mappings.name]),		//汉字的全拼
								jp: Pinyin.GetJP(o[thisObject.mappings.name]),		//汉字的简拼
								wc: Pinyin.getWordsCode(o[thisObject.mappings.name])	//单词首字母获取
							};
					});
				}
			});
		},
		getTreeSetting:function(){
			//多选的情况
			if(this.multiMode){
				return {
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
							beforeClick: function(id, node){
								return (node.click != "false");
							}
						}
					};
			}
			//只选叶子的情况
			else if(this.noParent==true){
				 return {
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
									return (node.click != "false");
								}
							}
						};
			}
			//单选的情况
			else{
				 return {
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
									return (node.click != "false");
								}
							}
						};
			}
		},
		
		setSelectedValue:function(){
			var nodes = null;
			if(this.noParent){
				nodes = this.tree.getSelectedNodes();
			}else{
				nodes = this.tree.getChangeCheckedNodes();
			}
/*			var result = [];//人员控件返回的数据集合
			$.each(nodes,function(i, val){
				console.log(this);
				result[i] = {
					id: this.id,
					text: this.name
				}
			});*/
			if(this.multiMode){
				$("#"+this.controlId).select2("data", nodes);//多选回显值
			}else{
				if(nodes.length>0){
					$("#"+this.controlId).select2("data",{id: nodes[0].id, text: nodes[0].name});//单选回显值
				}
			}
				
			if(this.callbackFunction != null){
				//alert(this.callbackFunction);
				//alert(this.getValueWithName());
				this.callbackFunction(this.getValueWithName());
			}
			this.close();
		},
		getOpenerTemplate:function(){
			return  '<div class="modal fade treeSelectControlOpener" id="'+this.openerId+'" aria-hidden="false">'+
			'<div class="modal-dialog">'+
			'<div class="modal-content">'+
				'<div class="modal-header">'+
					'<button type="button" class="close" data-dismiss="modal">×</button>'+
					'<h4 class="modal-title">选择</h4>'+
				'</div>'+
				'<div class="modal-body">'+
					'<div id="'+this.treeId+'" class="ztree"></div>'+
				'</div>'+
				'<div class="modal-footer no-all form-btn">'+
					'<button class="btn dark" type="button"  id="'+this.okBtnId+'">确 定</button>'+
					'<button class="btn" type="reset" id="'+this.closeBtnId+'">取消</button>'+
				'</div>'+
			'</div>'+
		'</div>'+
	  '</div>';
	  },
	  getSelect2Template:function(){
		 return  '<div class="select2-wrap input-group  w-p100 treeSelectControlSelect2">'+
		   	'<input type="hidden" id="'+this.controlId+'" name="'+this.controlName+'" search="search" style="width:100%"/>'+
			'<a class="btn btn-file no-all input-group-btn" href="#" id="'+this.openBtnId+'" role="button"'+
				'data-toggle="modal"><i class="'+this.icon+'"></i>'+
			'</a>'+
		   '</div><label class="help-block"></label>';
	  },
	  _getValue:function(containName){
		  var select2Value = $("#"+this.controlId).select2("data");
		  if(select2Value == null || select2Value.length == 0){
				return null;
			}
			var v = "";
			if(select2Value.length > 0){
				$.each(select2Value,function(j, d){
					v += d.id;
					if(containName){
						v+=":"+d.text;
					}
					v+=",";
				});
			}else{
				v += select2Value.id;
				if(containName){
					v+=":"+select2Value.text;
				}
				v+=",";
			}
			return v.substring(0, v.length-1);
	  },
	  getValue:function(){
		  return this._getValue(false);
	  },
	  getValueWithName:function(){
		  return this._getValue(true);
	  }
	};
	
	$.fn.treeSelectControl = function(option){
			return new TreeSelectControl(option);
	}
})(window.jQuery)

function clearTreeSelectControl(controlId){
	$("#"+controlId).select2("data", "");
}
