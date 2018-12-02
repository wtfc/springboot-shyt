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
		onClick: onClick
	}
};

function onCheck(id, node){
}

function onClick(event, treeId, treeNode) {
	var id = getChildNodesId(treeNode, "treeDemo");
    $("#id").val(id);//获取树节点ID
	$("#name").val(treeNode.name);
	var year = $('input[name=yearId]').val();
	$("#code").val(treeNode.subPlanType);
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/plan/queryAllMonth.action?id="+id+"&year="+year,
		dataType : "json",
		success : function(data) {
			if (data) {
				//填充详细数据
				if(data.query !=null && data.query!=""){
					$("#monthPlanThead").html("");
					$("#monthPlanTbody").html("");
					planController.monthTable(data.query.length==0?1:data.query.length);
					planController.getMonth(data.query);
				} else {
					$("#monthPlanThead").html("");
					$("#monthPlanTbody").html("");
					planController.monthTable(1);
				}
			}
		}
	});

	
	$("#datesNowMonth").html(year);
	$('input[name=monthId]').val(year);
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
 * 月报告状态初始化方法
 */
planController.monthTable = function(monthLength){
	var monthForm2="<tr><th style='width:80px;'>姓名</th>"+
				"<th>一月</th>"+
			    "<th>二月</th>"+
			    "<th>三月</th>"+
			    "<th>四月</th>"+
			    "<th>五月</th>"+
			    "<th>六月</th>"+
			    "<th>七月</th>"+
			    "<th>八月</th>"+
			    "<th>九月</th>"+
			    "<th>十月</th>"+
			    "<th>十一月</th>"+
			    "<th>十二月</th></tr>";
		
	var monthForm="<tr>";
	for(var i=1;i<=monthLength*13;i++){
		if(i%13==0){
			monthForm +="<td><span id='month_"+i+"' style='white-space:nowrap'></span></td></tr>";
		} else{
			monthForm +="<td><span id='month_"+i+"' style='white-space:nowrap'></span></td>";
		}
	}
//	monthForm +="</tr>";
	$("#monthPlanThead").append(monthForm2);
	$("#monthPlanTbody").append(monthForm);
};

/**
 * 月报告状态数据回显
 */
planController.getMonth = function (query) {
	for(var i=0;i<query.length;i++){
		var num =0;
		if(i!=0){
			num = i*13;
		}
		$("#month_"+(1+num)).html(query[i].userName);
		$("#month_"+(2+num)).html(query[i].monthOne==0?"<i class='fa fa-ok text-green'></i>":query[i].monthOne);       /*周1*/
		$("#month_"+(3+num)).html(query[i].monthTwo==0?"<i class='fa fa-ok text-green'></i>":query[i].monthTwo);       /*周2*/
		$("#month_"+(4+num)).html(query[i].monththree==0?"<i class='fa fa-ok text-green'></i>":query[i].monththree);  /*周3*/
		$("#month_"+(5+num)).html(query[i].monthfour==0?"<i class='fa fa-ok text-green'></i>":query[i].monthfour);  /*周4*/
		$("#month_"+(6+num)).html(query[i].monthfive==0?"<i class='fa fa-ok text-green'></i>":query[i].monthfive);  /*周5*/
		$("#month_"+(7+num)).html(query[i].monthsix==0?"<i class='fa fa-ok text-green'></i>":query[i].monthsix);  /*周6*/
		$("#month_"+(8+num)).html(query[i].monthseven==0?"<i class='fa fa-ok text-green'></i>":query[i].monthseven);  /*周7*/
		$("#month_"+(9+num)).html(query[i].montheight==0?"<i class='fa fa-ok text-green'></i>":query[i].montheight);  /*周8*/
		$("#month_"+(10+num)).html(query[i].monthnine==0?"<i class='fa fa-ok text-green'></i>":query[i].monthnine);  /*周9*/
		$("#month_"+(11+num)).html(query[i].monthten==0?"<i class='fa fa-ok text-green'></i>":query[i].monthten);  /*周10*/
		$("#month_"+(12+num)).html(query[i].montheleven==0?"<i class='fa fa-ok text-green'></i>":query[i].montheleven);  /*周11*/
		$("#month_"+(13+num)).html(query[i].monthtwelve==0?"<i class='fa fa-ok text-green'></i>":query[i].monthtwelve);  /*周12*/
		$("#month_"+(14+num)).html(query[i].monththirteen==0?"<i class='fa fa-ok text-green'></i>":query[i].monththirteen);  /*周13*/
	}
};

/**
 * 月设定
 */
function nextMonthYear(month){

	var id = $('input[name=id]').val();
	var year = $('input[name=monthId]').val();
	// var code = $('input[name=code]').val();
	year = Number(year)+Number(month);

	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/plan/queryAllMonth.action?id="+id+"&year="+year,
		dataType : "json",
		success : function(data) {
			if (data) {
				//填充详细数据
				if(data.query !=null && data.query!=""){
					$("#monthPlanThead").html("");
					$("#monthPlanTbody").html("");
					planController.monthTable(data.query.length==0?1:data.query.length);
					planController.getMonth(data.query);
				} else {
					$("#monthPlanThead").html("");
					$("#monthPlanTbody").html("");
					planController.monthTable(1);
				}
			}
		}
	});

	
	$("#datesNowMonth").html(Number(year));
	$('input[name=monthId]').val(Number(year));
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
	        //$("#LeftHeight").height()
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
	planController.monthTable(1);
	var year = $('input[name=yearId]').val();
	$("#datesNowMonth").html(year);
	$('input[name=year]').val(year);
	$('input[name=monthId]').val(year);
});
