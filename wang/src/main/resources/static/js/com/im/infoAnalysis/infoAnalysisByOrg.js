var infoAnalysisByOrg = {};

infoAnalysisByOrg.queryInfoAnalysisByOrg = function() {
	$.ajax({
		url : getRootPath()
				+ "/im/infoAnalysis/queryInfoAnalysisByOrg.action",
		data : "begin_time=" + $("#begin_time").val() + "&end_time=" + $("#end_time").val(),
		success : function(data) {
			infoAnalysisByOrg.queryInfoByOrgSuccess(data);
		}
	});
};

infoAnalysisByOrg.queryInfoByOrgSuccess = function(data) {
	var c = $("#queryInfoAnalysisByOrg_Content");
	c.html("");
	var tr1 = $("<tr>");
	tr1.append("<td>组织机构</td><td>发布数量</td><td>评论数量</td><td>查看数量</td>");
	c.append(tr1);
	var y1 = [];
	var y2 = [];
	var y3 = [];
	var x = [];
	$(data.infoList).each(function(i, o) {
		var tr = $("<tr>");
		tr.append("<td>"+o.orgName+"</td><td>"+o.newCount+"</td><td>"+o.reviewCount+"</td><td>"+o.readCount+"</td>");
		x[i] = o.orgName;
		y1[i] = o.newCount;
		y2[i] = o.reviewCount;
		y3[i] = o.readCount;
		c.append(tr);
	});
	var arry = new Array();
	arry.push(y1);
	arry.push(y2);
	arry.push(y3);
	// --图表

	var chart1 = $.createChart({
		title : '组织单位信息统计',
		subTitle : '组织单位信息统计',
		legend: {
	        data:['发布数量','查看数量','评论数量']
	    },
		// 标题水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
		titlePosX : 'left',
		// 标题垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
		titlePosY : 'top',
		elementId : 'queryInfoAnalysisByOrg_div',
		// 启用拖拽重新计算功能
		calculable : true,
		// 使用右上角的工具箱
		toolboxEnabled : true,
		// 鼠标移动到图形上的提示开关
		tooltip : true,
		height : '400',
		// 设定X轴内容
		xAxisType : 'category',
		// X轴显示的内容(动态显示时,先传入空数组)
//		xAxisData : [ "发布数量", "评论数量", "查看数量" ],
		xAxisData : x,
		// 动态X轴显示内容的参数
		//xAxisParm : [],
		// 动态X轴显示内容的请求地址
		// xAxisUrl: getRootPath()+"/example/echartsData/barAxisData.jsp",
		// Y轴自动根据内容生成数值
	    yAxisType: 'value',
		series : [ {
			// 图形类型
			type : 'bar',
			// 请求的参数
			data : y1,
			// 鼠标移动到图形上的提示内容
			name : '发布数量'
		},{
			// 图形类型
			type : 'bar',
			// 请求的参数
			data : y2,
			// 请求地址
			// url : getRootPath() + "/example/echartsData/barData.jsp",
			// 鼠标移动到图形上的提示内容
			name : '评论数量'	
		},{
			// 图形类型
			type : 'bar',
			// 请求的参数
			data : y3,
			// 请求地址
			// url : getRootPath() + "/example/echartsData/barData.jsp",
			// 鼠标移动到图形上的提示内容
			name : '查看数量'
		}]
	});
	// 点击事件
	//chart1.on('click', infoAnalysisByOrg.queryInfoAnalysisByOrgForMonth);
};

$(document).ready(function() {
	// 日历控件重新初始化
	$(".datepicker-input").datepicker();
	$("#getButton").click(function(){infoAnalysisByOrg.queryInfoAnalysisByOrg();});
	init_time_tools("begin_time","end_time");
	infoAnalysisByOrg.queryInfoAnalysisByOrg();
});

//便于chrome中js调试
//@ sourceURL=infoAnalysisByOrg.js 