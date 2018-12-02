var infoAnalysisBySender = {};

infoAnalysisBySender.queryInfoAnalysisBySender = function() {
	$.ajax({
		url : getRootPath()+ "/im/infoAnalysis/queryInfoAnalysisBySender.action",
		
		data : "begin_time=" + $("#begin_time").val() + "&end_time=" + $("#end_time").val(),
		
		success : function(data) {
			infoAnalysisBySender.queryInfoBySenderSuccess(data);
		}
	});
};

infoAnalysisBySender.queryInfoBySenderSuccess = function(data) {

	var c = $("#queryInfoAnalysisBySender_Content");
	c.html("");
	var tr1 = $("<tr>");
	tr1.append("<td>发布人</td><td>发布数量</td>");
	c.append(tr1);
	var x = [];
	var y = [];
	$(data.infoList).each(function(i, o) {
		var tr = $("<tr>");
		tr.append("<td>"+o.displayName+"</td><td>"+o.newCount+"</td>");
		x[i] = o.displayName;
		y[i] = o.newCount;
		c.append(tr);
	});
	// --图表
	var chart1 = $.createChart({
		title : '发布人信息统计',
		subTitle : '发布人信息统计',
		legend: {
	        data:['发布数量']
	    },
		// 标题水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
		titlePosX : 'left',
		// 标题垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
		titlePosY : 'top',
		elementId : 'queryInfoAnalysisBySender_div',
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
			data : y,
			// 请求地址
			// url : getRootPath() + "/example/echartsData/barData.jsp",
			// 鼠标移动到图形上的提示内容
			name : '发布数量'
		}]
	});
	// 点击事件
	//chart1.on('click', infoAnalysisBySender.queryInfoAnalysisBySenderForMonth);
};

$(document).ready(function() {
	// 日历控件重新初始化
	$(".datepicker-input").datepicker();
	$("#getButton").click(function(){infoAnalysisBySender.queryInfoAnalysisBySender();});
	init_time_tools("begin_time","end_time");
	infoAnalysisBySender.queryInfoAnalysisBySender();
});

//便于chrome中js调试
//@ sourceURL=infoAnalysisBySender.js 