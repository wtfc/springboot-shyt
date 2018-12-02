/**
 * 查看月报告状态 js
 * @author 闻瑜
 */
var planController = {};
var num=0;
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
	var yearDay = $('input[name=yearId]').val();
	var monthDay = $('input[name=monthId]').val();
	if(monthDay<10 && monthDay.length<2){
		monthDay = "0"+monthDay;
	}
	var year = yearDay;
    $("#id").val(id);//获取树节点ID
	$("#name").val(treeNode.name);
	$("#code").val(treeNode.subPlanType);
	
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/plan/queryAllDay.action?id="+id+"&year="+year+"&sumCurrentMW="+monthDay,
		dataType : "json",
		success : function(data) {
			if (data) {
				//填充详细数据
				if(data.query !=null && data.query!=""){
					$("#dayWorklogThead").html("");
					$("#dayWorklogTbody").html("");
					planController.dayTable(data.query.length);
					planController.getDay(data.query);
				} else {
					$("#dayWorklogThead").html("");
					$("#dayWorklogTbody").html("");
					planController.dayTable(1);
				}
			}
		}
	});

	$("#datesNowWeek").html(year+"-"+monthDay);
	$('input[name=year]').val(year);
};

//默认选中节点
subPlanTreeToPage.defaultCheck = function(treeId){
	if(subPlanTreeToPage.rootId != null && subPlanTreeToPage.rootName != null){
		//var treeObj = $.fn.zTree.getZTreeObj(treeId);
		//var node = treeObj.getNodeByParam("id", subPlanTreeToPage.rootId);
		//treeObj.selectNode(node, true);
		//node.checkState = true;
		$("#"+subPlanTreeToPage.id).val(subPlanTreeToPage.rootId);
		$("#"+subPlanTreeToPage.name).val(subPlanTreeToPage.rootName);
	}
};

/**
 * 周报告状态初始化方法
 */
planController.dayTable = function(weekLength){
	var year = $('input[name=yearId]').val();
	var month = $('input[name=monthId]').val();
	if(month.length<2){
		month = "0"+month;
	}
	var dates=0;
	if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8
			|| month == 10 || month == 12) {
		dates = 31;
	} else if (month == 4 || month == 6 || month == 9 || month == 11) {
		dates = 30;
	} else if (month == 2) {
		if (year % 4 == 0) {
			dates = 29;
		} else {
			dates = 28;
		}
	}
	dates+=1;
	num = dates;
	var weekForm1="<tr>";
	for(var i=1;i<=dates;i++){
		if(i==1){
			weekForm1 +="<th><span style='white-space:nowrap'>姓名</span></th>";
		} else {
			weekForm1 +="<th><span>"+(i-1)+"</span></th>";
		}
	}
	weekForm1 +="</tr>";
	var weekForm2="<tr>";
	for(var i=1;i<=dates;i++){
		if(i==1){
			weekForm2 +="<th><span></span></th>";
		} else {
			var date = "";
			if((i-1)<10){
				date = "0"+(i-1);
			} else {
				date = i-1;
			}
			var myDate = new Date(year,(month-1),date);//李洪宇  于 2014-08-27 修改
//			var myDate = new Date(year,month,date);//李洪宇  于 2014-08-27 修改
			var day = myDate.getDay();
			var week="";
			if(day==0){
				week ="日";
			} else if(day==1){
				week ="一";
			} else if(day==2){
				week ="二";
			} else if(day==3){
				week ="三";
			} else if(day==4){
				week ="四";
			} else if(day==5){
				week ="五";
			} else if(day==6){
				week ="六";
			}
			weekForm2 +="<th><span>"+week+"</span></th>";
		}
	}
	weekForm2 +="</tr>";
	
	var weekForm="<tr>";
	for(var i=1;i<=dates*weekLength;i++){
		if(i%dates==0){
			weekForm +="<td><span id='day_"+i+"' style='white-space:nowrap'></span></td></tr>";
		} else{
			weekForm +="<td><span id='day_"+i+"' style='white-space:nowrap'></span></td>";
		}
	}
	$("#dayWorklogThead").append(weekForm1+weekForm2);
	$("#dayWorklogTbody").append(weekForm);
};

/**
 * 报告状态数据回显
 */
planController.getDay = function (query) {
	for(var i=0;i<query.length;i++){
		var count =0;
		if(i!=0){
			count = i*num;
		}
		$("#day_"+(1+count)).html(query[i].userName);
		$("#day_"+(2+count)).html(query[i].dayOne==0?"<i class='fa fa-ok text-green'></i>":query[i].dayOne);       /*1*/
		$("#day_"+(3+count)).html(query[i].dayTwo==0?"<i class='fa fa-ok text-green'></i>":query[i].dayTwo);       /*2*/
		$("#day_"+(4+count)).html(query[i].daythree==0?"<i class='fa fa-ok text-green'></i>":query[i].daythree);  /*3*/
		$("#day_"+(5+count)).html(query[i].dayfour==0?"<i class='fa fa-ok text-green'></i>":query[i].dayfour);  /*4*/
		$("#day_"+(6+count)).html(query[i].dayfive==0?"<i class='fa fa-ok text-green'></i>":query[i].dayfive);  /*5*/
		$("#day_"+(7+count)).html(query[i].daysix==0?"<i class='fa fa-ok text-green'></i>":query[i].daysix);  /*6*/
		$("#day_"+(8+count)).html(query[i].dayseven==0?"<i class='fa fa-ok text-green'></i>":query[i].dayseven);  /*7*/
		$("#day_"+(9+count)).html(query[i].dayeight==0?"<i class='fa fa-ok text-green'></i>":query[i].dayeight);  /*8*/
		$("#day_"+(10+count)).html(query[i].daynine==0?"<i class='fa fa-ok text-green'></i>":query[i].daynine);  /*9*/
		$("#day_"+(11+count)).html(query[i].dayten==0?"<i class='fa fa-ok text-green'></i>":query[i].dayten);  /*10*/
		$("#day_"+(12+count)).html(query[i].dayeleven==0?"<i class='fa fa-ok text-green'></i>":query[i].dayeleven);  /*11*/
		$("#day_"+(13+count)).html(query[i].daytwelve==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwelve);  /*12*/
		$("#day_"+(14+count)).html(query[i].daythirteen==0?"<i class='fa fa-ok text-green'></i>":query[i].daythirteen);  /*13*/
		$("#day_"+(15+count)).html(query[i].dayfourteen==0?"<i class='fa fa-ok text-green'></i>":query[i].dayfourteen);  /*14*/
		$("#day_"+(16+count)).html(query[i].dayfifteen==0?"<i class='fa fa-ok text-green'></i>":query[i].dayfifteen);  /*15*/
		$("#day_"+(17+count)).html(query[i].daysixteen==0?"<i class='fa fa-ok text-green'></i>":query[i].daysixteen); /*16*/
		$("#day_"+(18+count)).html(query[i].dayseventeen==0?"<i class='fa fa-ok text-green'></i>":query[i].dayseventeen);  /*17*/
		$("#day_"+(19+count)).html(query[i].dayeighteen==0?"<i class='fa fa-ok text-green'></i>":query[i].dayeighteen);  /*18*/
		$("#day_"+(20+count)).html(query[i].daynineteen==0?"<i class='fa fa-ok text-green'></i>":query[i].daynineteen);  /*19*/
		$("#day_"+(21+count)).html(query[i].daytwenty==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwenty);  /*20*/
		$("#day_"+(22+count)).html(query[i].daytwentyOne==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentyOne);  /*21*/
		$("#day_"+(23+count)).html(query[i].daytwentyTwo==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentyTwo);  /*22*/
		$("#day_"+(24+count)).html(query[i].daytwentyThree==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentyThree);  /*23*/
		$("#day_"+(25+count)).html(query[i].daytwentyFour==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentyFour);  /*24*/
		$("#day_"+(26+count)).html(query[i].daytwentyFive==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentyFive);  /*25*/
		$("#day_"+(27+count)).html(query[i].daytwentySix==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentySix);  /*26*/
		$("#day_"+(28+count)).html(query[i].daytwentySeven==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentySeven);  /*27*/
		$("#day_"+(29+count)).html(query[i].daytwentyEight==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentyEight);  /*28*/
		$("#day_"+(30+count)).html(query[i].daytwentyNine==0?"<i class='fa fa-ok text-green'></i>":query[i].daytwentyNine);  /*29*/
		$("#day_"+(31+count)).html(query[i].daythirty==0?"<i class='fa fa-ok text-green'></i>":query[i].daythirty);  /*30*/
		$("#day_"+(32+count)).html(query[i].daythirtyOne==0?"<i class='fa fa-ok text-green'></i>":query[i].daythirtyOne);  /*31*/
	}
};

planController.getDay1 = function (name) {
	var week = name.split(",");
	for(var i=0;i<week.length;i++){
		if(i==0){
			$("#day_"+(i+1)).html(week[i]);
		} else {
			$("#day_"+(i*num+1)).html(week[i]);
		}
	}
};
/**
 * 日设定
 */
function nextDayYear(week){
	var id = $('input[name=id]').val();
	var yearDay = $('input[name=yearId]').val();
	var monthDay = $('input[name=monthId]').val();
	// var code = $('input[name=code]').val();
	if(week==1 && monthDay==12){
		yearDay = Number(yearDay)+1;
		monthDay = 1;
	} else if(week==-1 && monthDay==1){
		yearDay = Number(yearDay)-1;
		monthDay = 12;
	} else {
		monthDay = Number(monthDay)+week;
	}
	if(monthDay<10){
		monthDay = "0"+monthDay;
	}


	$("#datesNowWeek").html(yearDay+"-"+monthDay);
	$('input[name=yearId]').val(yearDay);
	$('input[name=monthId]').val(monthDay);
	
	$.ajax({
		type : "GET",
		url : getRootPath()+"/po/plan/queryAllDay.action?id="+id+"&year="+yearDay+"&sumCurrentMW="+monthDay,
		dataType : "json",
		success : function(data) {
			if (data) {
				//填充详细数据
				if(data.query !=null && data.query!=""){
					$("#dayWorklogThead").html("");
					$("#dayWorklogTbody").html("");
					planController.dayTable(data.query.length);
					planController.getDay(data.query);
				} else {
					$("#dayWorklogThead").html("");
					$("#dayWorklogTbody").html("");
					planController.dayTable(1);
				}
			}
		}
	});

	
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
	planController.dayTable(1);
	var year = $('input[name=yearId]').val();
	var month = $('input[name=monthId]').val();
	if(month<10){
		month = "0"+month;
	}
	$("#datesNowWeek").html(year+"-"+month);
	$('input[name=year]').val(year+"-"+month);
});
