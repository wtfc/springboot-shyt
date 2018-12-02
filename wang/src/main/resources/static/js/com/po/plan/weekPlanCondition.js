/**
 * 查看月报告状态 js
 */
var planController = {};


var subPlanTreeToPage = {};

subPlanTreeToPage.zNodes = null;
subPlanTreeToPage.id = null;
subPlanTreeToPage.name = null;
subPlanTreeToPage.callback = null;
subPlanTreeToPage.rootId = null;
subPlanTreeToPage.rootName = null;

var tree = null;

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
			return true;
		},
		onCheck: onCheck,
		onClick: onUserClick
	}
};

function onCheck(id, node){
}

function onUserClick(event,treeId,treeNode) {
	var id = getChildNodesId(treeNode, "treeDemo");
	var year = $('input[name=yearId]').val();
    $("#id").val(id);//获取树节点ID
	$("#name").val(treeNode.name);
	$("#code").val(treeNode.subPlanType);
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/plan/queryAllWeek.action?id="+id+"&year="+year,
		dataType : "json",
		success : function(data) {
			if (data) {
				//填充详细数据
				if(data.query !=null && data.query!=""){
					// planController.weekTable(52);
					planController.getWeek(data.query);
				} else {
					planController.getWeekTable();
				}
			}
		}
	});
	$("#datesNowWeek").html(year);
	$("#week").val(year);
};

//默认选中节点
subPlanTreeToPage.defaultCheck = function(treeId){
	if(subPlanTreeToPage.rootId != null && subPlanTreeToPage.rootName != null){
		/*var treeObj = $.fn.zTree.getZTreeObj(treeId);
		var node = treeObj.getNodeByParam("id", subPlanTreeToPage.rootId);
		treeObj.selectNode(node, true);
		node.checkState = true;*/
		$("#"+subPlanTreeToPage.id).val(subPlanTreeToPage.rootId);
		$("#"+subPlanTreeToPage.name).val(subPlanTreeToPage.rootName);
	}
};

/**
 * 周计划状态页面初始化方法
 */
//planController.weekTable = function(weekLength){
planController.weekTable = function(){
	//------------------------------------------------
	var year = $('input[name=yearId]').val();
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/plan/weekListInYear.action?year="+year+"&type="+0,
		dataType : "json",
		success : function(data) {
			if (data) {
				var monthWeekLength=data.weekLengthInMonth;
				var weekFormHead="<tr><th style='width:100px;white-space:nowrap'>姓名</th>";
				for(var i=0;i<monthWeekLength.length;i++){
					var month=i+1;
					weekFormHead+="<th colspan='"+monthWeekLength[i]+"'>"+month+"月</th>";
				}
				weekFormHead +="</tr>";
				var weekFormNumbers="<tr><th><span></span></th>";
				for(var j=1;j<53;j++){
					weekFormNumbers +="<th><span>"+j+"周</span></th>";
				}
				weekFormNumbers +="</tr>";
				$("#weekPlan").append(weekFormHead+weekFormNumbers);
			}
		}
	});
};

// 初始化显示列表
planController.getWeekTable = function () {
	$("#weekPlantable").empty();
	var weekForm="<tr>";
	for(var i=0;i<53;i++){
		if(i!=0 && i%53==0){
			weekForm +="</tr><tr><td><span id='week_"+i+"' style='white-space:nowrap'></span></td>";
		} else {
			weekForm +="<td><span id='week_"+i+"' style='white-space:nowrap'></span></td>";
		}
	}
	weekForm +="</tr>";
	$("#weekPlantable").append(weekForm);
};

/**
 * 报告状态数据回显
 */
planController.getWeek = function (query) {
	$("#weekPlantable").empty();
	var weekForm="<tr>";
	for(var i=0;i<53*query.length;i++){
		if(i!=0 && i%53==0){
			weekForm +="</tr><tr><td><span id='week_"+i+"' style='white-space:nowrap'></span></td>";
		} else {
			weekForm +="<td><span id='week_"+i+"' style='white-space:nowrap'></span></td>";
		}
	}
	weekForm +="</tr>";
	$("#weekPlantable").append(weekForm);
	for(var i=0;i<query.length;i++){
		var num =0;
		if(i!=0){
			num = i*53;
		}
		$("#week_"+(0+num)).html(query[i].userName);
		$("#week_"+(1+num)).html(query[i].weekOne==0?"<i class='fa fa-ok text-green'></i>":query[i].weekOne);       /*周1*/
		$("#week_"+(2+num)).html(query[i].weekTwo==0?"<i class='fa fa-ok text-green'></i>":query[i].weekTwo);       /*周2*/
		$("#week_"+(3+num)).html(query[i].weekthree==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthree);  /*周3*/
		$("#week_"+(4+num)).html(query[i].weekfour==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfour);  /*周4*/
		$("#week_"+(5+num)).html(query[i].weekfive==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfive);  /*周5*/
		$("#week_"+(6+num)).html(query[i].weeksix==0?"<i class='fa fa-ok text-green'></i>":query[i].weeksix);  /*周6*/
		$("#week_"+(7+num)).html(query[i].weekseven==0?"<i class='fa fa-ok text-green'></i>":query[i].weekseven);  /*周7*/
		$("#week_"+(8+num)).html(query[i].weekeight==0?"<i class='fa fa-ok text-green'></i>":query[i].weekeight);  /*周8*/
		$("#week_"+(9+num)).html(query[i].weeknine==0?"<i class='fa fa-ok text-green'></i>":query[i].weeknine);  /*周9*/
		$("#week_"+(10+num)).html(query[i].weekten==0?"<i class='fa fa-ok text-green'></i>":query[i].weekten);  /*周10*/
		$("#week_"+(11+num)).html(query[i].weekeleven==0?"<i class='fa fa-ok text-green'></i>":query[i].weekeleven);  /*周11*/
		$("#week_"+(12+num)).html(query[i].weektwelve==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwelve);  /*周12*/
		$("#week_"+(13+num)).html(query[i].weekthirteen==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirteen);  /*周13*/
		$("#week_"+(14+num)).html(query[i].weekfourteen==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfourteen);  /*周14*/
		$("#week_"+(15+num)).html(query[i].weekfifteen==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfifteen);  /*周15*/
		$("#week_"+(16+num)).html(query[i].weeksixteen==0?"<i class='fa fa-ok text-green'></i>":query[i].weeksixteen); /*周16*/
		$("#week_"+(17+num)).html(query[i].weekseventeen==0?"<i class='fa fa-ok text-green'></i>":query[i].weekseventeen);  /*周17*/
		$("#week_"+(18+num)).html(query[i].weekeighteen==0?"<i class='fa fa-ok text-green'></i>":query[i].weekeighteen);  /*周18*/
		$("#week_"+(19+num)).html(query[i].weeknineteen==0?"<i class='fa fa-ok text-green'></i>":query[i].weeknineteen);  /*周19*/
		$("#week_"+(20+num)).html(query[i].weektwenty==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwenty);  /*周20*/
		$("#week_"+(21+num)).html(query[i].weektwentyOne==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentyOne);  /*周21*/
		$("#week_"+(22+num)).html(query[i].weektwentyTwo==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentyTwo);  /*周22*/
		$("#week_"+(23+num)).html(query[i].weektwentyThree==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentyThree);  /*周23*/
		$("#week_"+(24+num)).html(query[i].weektwentyFour==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentyFour);  /*周24*/
		$("#week_"+(25+num)).html(query[i].weektwentyFive==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentyFive);  /*周25*/
		$("#week_"+(26+num)).html(query[i].weektwentySix==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentySix);  /*周26*/
		$("#week_"+(27+num)).html(query[i].weektwentySeven==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentySeven);  /*周27*/
		$("#week_"+(28+num)).html(query[i].weektwentyEight==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentyEight);  /*周28*/
		$("#week_"+(29+num)).html(query[i].weektwentyNine==0?"<i class='fa fa-ok text-green'></i>":query[i].weektwentyNine);  /*周29*/
		$("#week_"+(30+num)).html(query[i].weekthirty==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirty);  /*周30*/
		$("#week_"+(31+num)).html(query[i].weekthirtyOne==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtyOne);  /*周31*/
		$("#week_"+(32+num)).html(query[i].weekthirtyTwo==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtyTwo);  /*周32*/
		$("#week_"+(33+num)).html(query[i].weekthirtyThree==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtyThree);  /*周33*/
		$("#week_"+(34+num)).html(query[i].weekthirtyFour==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtyFour);  /*周34*/
		$("#week_"+(35+num)).html(query[i].weekthirtyFive==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtyFive);  /*周35*/
		$("#week_"+(36+num)).html(query[i].weekthirtySix==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtySix);  /*周36*/
		$("#week_"+(37+num)).html(query[i].weekthirtySeven==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtySeven); /*周37*/
		$("#week_"+(38+num)).html(query[i].weekthirtyEight==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtyEight);  /*周38*/
		$("#week_"+(39+num)).html(query[i].weekthirtyNine==0?"<i class='fa fa-ok text-green'></i>":query[i].weekthirtyNine);  /*周39*/
		$("#week_"+(40+num)).html(query[i].weekforty==0?"<i class='fa fa-ok text-green'></i>":query[i].weekforty);  /*周40*/
		$("#week_"+(41+num)).html(query[i].weekfortyOne==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortyOne);  /*周41*/
		$("#week_"+(42+num)).html(query[i].weekfortyTwo==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortyTwo);  /*周42*/
		$("#week_"+(43+num)).html(query[i].weekfortyThree==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortyThree);  /*周43*/
		$("#week_"+(44+num)).html(query[i].weekfortyFour==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortyFour);  /*周44*/
		$("#week_"+(45+num)).html(query[i].weekfortyFive==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortyFive);  /*周45*/
		$("#week_"+(46+num)).html(query[i].weekfortySix==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortySix); /*周46*/
		$("#week_"+(47+num)).html(query[i].weekfortySeven==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortySeven);  /*周47*/
		$("#week_"+(48+num)).html(query[i].weekfortyEight==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortyEight);  /*周48*/
		$("#week_"+(49+num)).html(query[i].weekfortyNine==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfortyNine);  /*周49*/
		$("#week_"+(50+num)).html(query[i].weekfifty==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfifty);  /*周50*/
		$("#week_"+(51+num)).html(query[i].weekfiftyOne==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfiftyOne);  /*周51*/
		$("#week_"+(52+num)).html(query[i].weekfiftyTwo==0?"<i class='fa fa-ok text-green'></i>":query[i].weekfiftyTwo); /*周52*/
	}
};

/**
 * 周设定
 */
function nextWeekYear(week){
	var id = $('input[name=id]').val();
	var year = $('input[name=week]').val();

	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/plan/queryAllWeek.action?id="+id+"&year="+(Number(year)+week),
		dataType : "json",
		success : function(data) {
			if (data) {
				//填充详细数据
				if(data.query !=null && data.query!=""){
					// planController.weekTable(52);
					planController.getWeek(data.query);
				} else {
					planController.getWeekTable();
				}
			}
		}
	});

	$("#datesNowWeek").html(Number(year)+week);
	$('input[name=week]').val(Number(year)+week);
}

//下属人员树初始化
planController.getZtree = function(){
	var url = getRootPath()+"/po/plan/planControllerTree.action";
	$.ajaxSetup({
	    async : false 
	});
	$.getJSON(url, function(data) {
		if (data.length > 0) {
			subPlanTreeToPage.zNodes = [];
			$.each(data, function(i, o) {
				if(i == 0){
					subPlanTreeToPage.rootId = o.id;
					subPlanTreeToPage.rootName = o.name;
				}
				subPlanTreeToPage.zNodes[i] = {
					id : o.id,
					pId : o.parentId,
					name : o.name + "",
					children: o.users,
					subPlanType : o.deptType,
					code : 1,
					checkState : false,
					type : o.type
				};
			});
			tree = $.fn.zTree.init($("#treeDemo"), subPlanTreeToPage.setting, subPlanTreeToPage.zNodes);
			tree.expandAll(true);
			subPlanTreeToPage.defaultCheck("treeDemo");
		}else{
//			loadrightmenu("/po/plan/planNoPermission.action?time="+new Date());
			loadrightmenu("/po/plan/planNoUnderling.action?time="+new Date(),"copy");
		}
	});
};


/**初始化左侧的树的div宽高*/
planController.initLeftTreeDiv = function(){
	var content = $("#content").height();
	var headerHeight_1 = $('#header_1').height() || 0;
	var headerHeight_2 = $("#header_2").height() || 0;
	
	if($("#treeDemo")[0]){
	    $(".tree-right").css("margin-left","215px");
	//定义高度
		$("#LeftHeight").height(content-80-headerHeight_1-headerHeight_2);
	    var lh = $("#LeftHeight").height();
	
	  $("#scrollable").scroll(function() {
	    if($("#scrollable").scrollTop()>=113){
	        $("#LeftHeight").addClass("fixedNav");
	        $("#LeftHeight").height(lh + 113);
	    }else{
	        var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop();
	        $("#LeftHeight").height(lh + a);
	        $("#LeftHeight").removeClass("fixedNav");
	    } 
	
	  });
	}
};

/**
 * 初始化方法
 */
jQuery(function($){
	planController.initLeftTreeDiv();
	planController.getZtree();
	planController.weekTable(52);
	var year = $('input[name=yearId]').val();
	$("#datesNowWeek").html(year);
	$('input[name=year]').val(year);
	$('input[name=week]').val(year);
	planController.getWeekTable();
});
