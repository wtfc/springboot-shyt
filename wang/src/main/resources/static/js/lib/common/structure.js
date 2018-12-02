/**
 * @description 人员选择组件
 */
structure = {}
structure.num = 1;
structure.appentEmelemntId="scrollable";
//人员选择对象
structureItem = function(){};


/**
 * @description 初始化函数
 * opt：userIdEleId:人员id存储元素id
 * 	   userNameEleId：人员名称存储元素id
 * 	   selectType:radio/check 单选多选
 * 	   callback:回调函数
 */
structure.init = function(opt){
	var itemNum = 0;
	$("[userSelectTree]").each(function(){
		var itemNums = $(this).attr("num");
		if(itemNums>itemNum){
			itemNum = itemNums;
		}
	})
	structure.num = parseInt(itemNum)+1;
	return structure.createFramework(opt);
}

/**
 * @description 关闭人员选择树
 */
structureItem.prototype.doSubmit = function(opt){
	var choiceUser = this.structure.getStructureObj("all");
	
	if(choiceUser.length==0){
		alert("请选择人员");
		return;
	}
	
	var choiceUserId = "";
	var choiceUserName = "";
	
	for(var i=0;i<choiceUser.length;i++){
		choiceUserId += choiceUser[i].id+",";
		choiceUserName += choiceUser[i].name+",";
	}
	
	choiceUserId = choiceUserId.substring(0,choiceUserId.length-1);
	choiceUserName = choiceUserName.substring(0,choiceUserName.length-1);
	
	//赋值人员id到页面
	if(opt.userIdEleId!=null){
		if($("#"+opt.userIdEleId).is('input')){
			$("#"+opt.userIdEleId).val(choiceUserId);
		}else{
			$("#"+opt.userIdEleId).attr("user",choiceUserId);
		}
	}
	
	//复制人员name到页面
	if(opt.userNameEleId!=null){
		if($("#"+opt.userNameEleId).is('input')){
			$("#"+opt.userNameEleId).val(choiceUserName);
		}else{
			$("#"+opt.userNameEleId).html(choiceUserName);
		}
	}
	
	if(opt.callback!=null){
		opt.callback(choiceUser);
	}
	
	this.close();
}

/**
 * @description 打开人员选择树
 */
structureItem.prototype.open = function(){
	this.structureModal.modal("show");
}

/**
 * @description 关闭人员选择树
 */
structureItem.prototype.close = function(){
	this.structureModal.modal("hide");
}

/**
 * @description 关闭人员选择树
 */
structureItem.prototype.remove = function(){
	$(document).find("div#"+this.id).remove();
}

/**
 * @description 在页面创建相应的结构框架
 */
structure.createFramework = function(opt){
	//设置默认值
	if(opt.selectType==null||opt.selectType.length==0){
		opt.selectType = "check";
	}
	
	var item = new structureItem(opt);
	item.id="structure"+structure.num;
	var htmlStr = '<div class="modal fade panel" id="'+item.id+'" num="'+structure.num+'" userSelectTree=true aria-hidden="false">'+
        				'<div class="modal-dialog" style="width:840px">'+
        					'<div class="modal-content">'+
        						'<div class="modal-header">'+
        							'<button type="button" class="close" data-dismiss="modal">×</button>'+
        								'<h4 class="modal-title">选择人员</h4>'+
        						'</div>'+
        						'<div class="modal-body">'+
        						'</div>'+
        						'<div class="modal-footer no-all form-btn">'+
        							'<button id="saveBtn" class="btn dark" type="button">确定</button><button class="btn" data-dismiss="modal">取消</button>'+
        						'</div>'+
        					'</div>'+
        				'</div>'+
        			'</div>';
	var structureSiv = $(htmlStr);
	item.structureModal = structureSiv;
	structureSiv.find("#saveBtn").click(function(){
		item.doSubmit(opt);
	});
	if($("#"+structure.appentEmelemntId).length>0){
		$("#"+structure.appentEmelemntId).append(structureSiv);
	}else{
		$(document.body).append(structureSiv);
	}
	
	structureSiv.find(".modal-body").load(getRootPath()+"/system/structureView.action",null,function(data,textStatus,XMLHttpRequest){
		//获得人员数据
		var ajaxData = {
				time:new Date()
		}
		$.ajax({
			url:getRootPath()+"/system/getStructureAndUser.action",
			data:ajaxData,
			method:"get",
			async:false,
			success:function(data){
				var option = {
						id:"id",//相对父节点的主键 唯一 标识
						name:"name",//display name 就是你的展示那个值
						parentid:"parentId",//节点中的父节点标识(相对)
						child:"users",//最底层子节点json结构中的key名称
						childid:"id",//最底层子节点的唯一标识
						parentback:["id","user_online_flag"],//所有根节点返回数据需要的属性
						childback:["id","name","order"],//最底层的子节点返回数据需要的属性
						treeSearch:'treeSearch',//搜索的input框id 可选
						screenattribute:{ // 请一定要确保你传递过来的json中是含有以下配置的属性的 (尤其是childid 下 因为 过滤的是子节点)
							searchType:'deptId', //页面上过滤select 的id 已经其对应的过滤属性
							searchType1:'level'//key值为页面上过滤的select 的id
						},
						order:'orderNo'
					}
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
					$.struture.systemSetting.selectfunsetting="all";
					$.struture.systemSetting.selectresultsetting=opt.selectType;
					$.struture.systemSetting.selectordersetting=true;
					structureSiv.find("#treeDemo").attr("id","treeDemo"+item.id);
					structureSiv.find("#searchable").attr("id","searchable"+item.id).attr("name","searchable"+item.id);
					var strutureTree = $.struture.init("treeDemo"+item.id,"searchable"+item.id,data,option);
					item.structure = strutureTree;
					
					structureSiv.modal({
						backdrop:true,
						keyboard:true,
						show:false
					});
			}
		});
	});
	return item;
}
