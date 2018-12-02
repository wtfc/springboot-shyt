/**
 * 下属工作计划js
 */
var subPlan = {};
var tree = null;
var subPlanTreeToPage = {};

subPlanTreeToPage.zNodes = null;
subPlanTreeToPage.id = null;
subPlanTreeToPage.name = null;
subPlanTreeToPage.callback = null;
subPlanTreeToPage.rootId = null;
subPlanTreeToPage.rootName = null;

subPlanTreeToPage.setting = {
	check : {
		enable : false,
		nocheckInherit : true
	},
	view : {
		selectedMulti : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback:{
		beforeClick: function(id, node){
			if(node.type!=0){
				return false;
			}else{
				return true;
			}
		},
		onCheck: onCheck,
		onClick: onClick
	}
};

function onCheck(id, node){

}

function onClick(event, treeId, treeNode) {
    $("#id").val(treeNode.id);//获取树节点ID
	$("#subUserId").attr("value",treeNode.id);
	subPlan.initList();
};

//默认选中节点
subPlanTreeToPage.defaultCheck = function(treeId){
	if(subPlanTreeToPage.rootId != null && subPlanTreeToPage.rootName != null){
		var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var node = treeObj.getNodeByParam("id", subPlanTreeToPage.rootId);
		treeObj.selectNode(node, true);
		node.checkState = true;
		$("#"+subPlanTreeToPage.id).val(subPlanTreeToPage.rootId);
		$("#"+subPlanTreeToPage.name).val(subPlanTreeToPage.rootName);
	}
};

//编制显示列信息
subPlan.oTableAoColumns = [
	{mData: "planTitle"},//计划标题
	{mData: "planTypeValue"},//计划类型
	{mData: "planStartTime"},//计划开始时间
	{mData: "planEndTime"},//计划结束时间
	//设置权限按钮
	{mData:null,"mRender":function(mData,type,full) {
		var opt ={};
		opt.workId = full.workId;
		opt.flowStatus = full.flowStatus;
		opt.processStatus = full.processStatus;
		opt.workflowId = full.workflowId;
		opt.showDeleteBtn = false;//是否显示删除按钮
		opt.entrance = "business";
		opt.entranceType = "myBusiness";
		opt.action="/po/plan/getPlan.action";
		return getWorkflowListButton(opt);
	}}
 ];

//编制组装后台参数
subPlan.oTableFnServerParams = function(aoData){
	//排序条件
	getTableParameters(subPlan.oTable, aoData);
	//组装查询条件
	var subUserId = $("#subUserId").val();//下属人员Id
	if(subUserId != ""){
		aoData.push({ "name": "applyIdsPrimaryKeys", "value": subUserId});
	}else{
		aoData.push({ "name": "applyIdsPrimaryKeys", "value": subPlanTreeToPage.rootId});
	}
	aoData.push({ "name": "flowStatus", "value": 7});
	$.each($("#subPlanForm").serializeArray(), function(i, o) {
		if(o.value != ""){
			aoData.push({ "name": o.name, "value": o.value});
		}
	});
};

//分页查询方法
subPlan.initList = function () {
	if (subPlan.oTable == null) {
		subPlan.oTable = $('#subPlanTable').dataTable( {
			"iDisplayLength": subPlan.planPageRows,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/po/plan/manageSubPlanList.action",//后台分页查询地址url
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": subPlan.oTableAoColumns,//table显示列
			//传参
			"fnServerParams": function ( aoData ) {
				subPlan.oTableFnServerParams(aoData);
			},
			aaSorting:[],//设置表格默认排序列
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,4]}],
	    	fnDrawCallback : function(oSettings) {
	    	    if($("#treeDemo")[0]){
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
		});
	} else {
		//table不为null时重绘表格
		subPlan.oTable.fnDraw();
	}
};

//下属人员树初始化
subPlan.getZtree = function(){
//	$.ajaxSetup({
//	    async : false 
//	});
//	$.getJSON(url, function(data) {
//		if (data.length>0) {
//			subPlanTreeToPage.zNodes = [];
//			$.each(data, function(i, o) {
//				if(i == 0){
//					subPlanTreeToPage.rootId = o.id;
//					subPlanTreeToPage.rootName = o.displayName;
//				}
//				subPlanTreeToPage.zNodes[i] = {
//					id : o.id,
//					pId : o.leaderId,
//					name : o.displayName + "",
//					subPlanType : o.subPlanType,
//					checkState : false
//				};
//			});
//			tree = $.fn.zTree.init($("#treeDemo"), subPlanTreeToPage.setting, subPlanTreeToPage.zNodes);
//			tree.expandAll(true);
//			subPlanTreeToPage.defaultCheck("treeDemo");
//		}else{
//			loadrightmenu('/po/diary/diaryNoUnderling.action');
//		}
//	});
	var url = getRootPath()+"/po/plan/subPlanQueryTree.action";
	jQuery.ajax({
		url : url,
		type : 'Post',
		async : false,
		data : "",
		success : function(data) {
			if (data.length>0) {
				subPlanTreeToPage.zNodes = [];
				var isFrist=false;
				var fristNum;
				$.each(data, function(i, o) {
					if(o.type==0 && isFrist==false){//type 0 人员 1部门
						isFrist=true;
						fristNum=i;
					}
				});
				
				$.each(data, function(i, o) {
					if(o.type==0 && i == fristNum){//type 0 人员 1部门
						subPlanTreeToPage.rootId = o.id;
						subPlanTreeToPage.rootName = o.name;
					}
					subPlanTreeToPage.zNodes[i] = {
//						id : o.id,
//						pId : o.leaderId,
//						name : o.displayName + "",
//						subPlanType : o.subPlanType,
//						checkState : false
						id : o.id,
						pId : o.parentId,
						name : o.name + "",
						checkState : false,
						createUserDept: o.createUserDept,
						type: o.type
					};
				});
				tree = $.fn.zTree.init($("#treeDemo"), subPlanTreeToPage.setting, subPlanTreeToPage.zNodes);
				tree.expandAll(true);
				subPlanTreeToPage.defaultCheck("treeDemo");
			}else{
				loadrightmenu('/po/plan/planNoUnderling.action');
			}
		}
	});
};

//清空查询区域
subPlan.queryFormReset = function(){
	$('#subPlanForm')[0].reset();
};

/**
 * 初始化计划类型
 */
subPlan.initPlanType = function(){
	var jumpType = $("#diaryJumpType").val();
	if(jumpType != ""){
		if(jumpType == 0){
			$("input[name=planType]").each(function(i, v){
				$(v).remove();//需要转换成Jquery对象
				var inputStr = "<input type='hidden' id='planType' name='planType' value='0'>";
				$("#planTypeTd").html(inputStr);
				$("#planTypeTd").append("周计划");
			});
		}else{
			$("input[name=planType]").each(function(i, v){
				$(v).remove();//需要转换成Jquery对象
				var inputStr = "<input type='hidden' id='planType' name='planType' value='1'>";
				$("#planTypeTd").html(inputStr);
				$("#planTypeTd").append("月计划");
			});
		}
	}
};

/**
 * 初始化方法
 */
jQuery(function($){
	//初始化下属人员树
	subPlan.getZtree();
	//计算分页显示条数
	subPlan.pageRows = TabNub > 0 ? TabNub : 10;
	//查询按钮事件绑定
	$("#subPlanQuery").click(subPlan.initList);
	//重置按钮事件绑定
	$("#subPlanReset").click(subPlan.queryFormReset);
	//初始化计划类型
	subPlan.initPlanType();
	//日历控件重新初始化
	$(".datepicker-input").datepicker();
	//初始化列表
	subPlan.initList();
});