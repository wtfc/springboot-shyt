<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<script>

function loadinfodata(){
	jQuery.ajax({
        url: getRootPath()+"/im/info/getInfoPortalData.action?columnId=${columnId}&funViewType=${funViewType}&dataRows=${dataRows}&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	if(data.infoList != null && '${funViewType}' == 'pviewType_1'){
        		loadinfomixlist(data.infoList,data.dataRows,"info1${portletId }${functionId}","dataLoad_info${portletId }${functionId}");
        	} else if(data.infoList != null && '${funViewType}' == 'pviewType_9'){
        		loadinfomaxlist(data.infoList,data.dataRows,"info9${portletId }${functionId}","dataLoad_info${portletId }${functionId}");
        	} else if(data.infoPicList != null && '${funViewType}' == 'pviewType_3'){
        		loadinfotxtview(data.infoPicList,data.dataRows,"info3${portletId }${functionId}","dataLoad_info${portletId }${functionId}");
        	} else if(data.infoPicList != null && '${funViewType}' == 'pviewType_4'){
        		loadinforollview(data.infoPicList,data.dataRows,"info4${portletId }${functionId}","dataLoad_info${portletId }${functionId}");
        	} else if(data.infoAnalysisByColumnList != null){
        		loadinfoview(data.infoAnalysisByColumnList,"info8${portletId }${functionId}","dataLoad_info${portletId }${functionId}");
        	} else {
        		$("#dataLoad_info${portletId }${functionId}").fadeOut();
        	}
        },error:function(e){
        	// alert("加载数据错误。");
        	//msgBox.tip({content: "加载数据错误", type:'fail'});
        }
	});
};

function loadinfomixlist(infoList,dataRows,divname,loadingname){
      	var content = "";
      	if(infoList.length > 0){
      		for(var i=0;i<infoList.length;i++){
          		if(i == dataRows)
          			break;
          		content = content + "<tr><td style=\"display: none;\"></td>"+
          			"<td><a href=\"#\" title=\""+infoList[i].infoTitile+"\" onclick=\"window.open(encodeURI(getRootPath() + '/im/info/infoDetail.action?type=1&menuFlag=0&id="+infoList[i].id+"','','/im/info/infoAll.action'));\">"+
          			infoList[i].infoTitile+"</a> </td><td>"+infoList[i].sendName+"</td>"+
          			"<td>"+infoList[i].sendTime+"</td><td style=\"display: none;\"></td></tr>";
          	}
          	$("#"+divname).html(content);
          	$("#"+loadingname).fadeOut();
      	} else {
      		$("#"+divname).html("<tr><td colspan='3' style='text-align:center;font-size: 13px;font-family: inherit;'>暂无数据</td></tr>");
          	$("#"+loadingname).fadeOut();
      	}
      	
};

function loadinfomaxlist(infoList,dataRows,divname,loadingname){
      	var content = "";
      	if(infoList.length > 0){
      		for(var i=0;i<infoList.length;i++){
          		if(i == dataRows)
          			break;
          		content = content +"<li><i class=\"fa fa-clipboard icon fl\"></i><div class=\"fl index-inform-con\">"+
          			"<a href=\"#\" title=\""+infoList[i].infoTitile+"\" onclick=\"window.open(encodeURI(getRootPath() + '/im/info/infoDetail.action?type=1&menuFlag=0&id="+infoList[i].id+"','','/im/info/infoAll.action'));\">"+
      				"<span>"+infoList[i].infoTitile+"</span><!-- 信息标题 -->"+
      				"<div class='index-inform-d'>发布人："+infoList[i].sendName.substr(0,4)+"<span class='fr'>"+infoList[i].sendTime+"</span></div><!-- 发布人 --></a></div>"+
      				"<!-- 发布时间 --></li>";
          	}
          	$("#"+divname).html("<ul>"+content+"</ul>");
          	$("#"+loadingname).fadeOut();
      	} else {
      		$("#"+divname).html("<div style='text-align:center;margin-top: 50px;font-size: 18px;font-family: inherit;'>"+"暂无数据"+"</div>");
          	$("#"+loadingname).fadeOut();
      	}
};

function loadinfotxtview(infoPicList,dataRows,divname,loadingname){
      	var content = "";
      	if(infoPicList.length > 0){
      		for(var i=0;i<infoPicList.length;i++){
          		if(i == dataRows){
          			break;
          		}
          		var infocontent = infoPicList[i].infoContent;
          		infocontent=infocontent.replace(/<\s?img[^>]*>/gi,'');
          		infocontent=infocontent.replace(/<(style|script|iframe)[^>]*?>[\s\S]+?<\/\1\s*>/gi,'').replace(/<[^>]+?>/g,'').replace(/\s+/g,'').replace(/(&nbsp;)/g,"");
          		if(infocontent.length > 70){
          			infocontent = infocontent.substring(0,70)+"......"+
          				"<a href=\"javascript:void(0)\" class=\"play-con-a\" onclick=\"window.open(encodeURI(getRootPath()+'/im/info/infoDetail.action?type=1&menuFlag=0&id="+infoPicList[i].id+"','','/im/info/infoAll.action'));\">查看详细</a>";
          		}
          		content = content + "<li><div class=\"play-img\"><img src=\"./../../"+infoPicList[i].portalPic+"/portalPic.png\" /></div>"+
          			"<div class=\"play-con\">"+
          			"<h2><a href=\"javascript:void(0)\" title=\""+infoPicList[i].infoTitile+"\" onclick=\"window.open(encodeURI(getRootPath() + '/im/info/infoDetail.action?type=1&menuFlag=0&id="+infoPicList[i].id+"','','/im/info/infoAll.action'));\">"+
          			infoPicList[i].infoTitile+"</a></h2><p>"+infocontent+"</p></div></li>";
          	}
          	$("#"+divname).html("<ul>"+content+"</ul>");
          	loadJsFortxtView(divname);
    		$("#"+loadingname).fadeOut();
      	} else {
      		$("#"+divname).html("<div style='text-align:center;margin-top: 50px;font-size: 18px;font-family: inherit;'>"+"暂无数据"+"</div>");
      		$("#"+loadingname).fadeOut();
      	}
      	
};
function loadJsFortxtView(divname){
	//alert($("#infoProjector${portletId }${functionId}").length);
	$("#Projector"+divname).slide({
	       titCell:".play_hd ul",
	       mainCell:".play_bd ul",
	       autoPage:true,
	       effect:"left",
	       autoPlay:true
	});
}

function loadinforollview(infoPicList,dataRows,divname,loadingname){
  	var content = "";
  	if(infoPicList.length > 0){
  		for(var i=0;i<infoPicList.length;i++){
  	  		if(i == dataRows){
  	  			break;
  	  		}
  	  		content = content + "<li><div class=\"rollinfo\"><a href=\"javascript:void(0)\" title=\""+infoPicList[i].infoTitile+"\" onclick=\"window.open(encodeURI(getRootPath() + '/im/info/infoDetail.action?type=1&menuFlag=0&id="+infoPicList[i].id+"','','/im/info/infoAll.action'));\">"+
  	  			"<img class=\"img\" src=\"./../../"+infoPicList[i].portalPic+"/portalPic.png\" /></a><div></li>";
  	  	}
  	  	$("#"+divname).html("<ul>"+content+"</ul>");
  	  	loadJsForRollView(divname);
  		$("#"+loadingname).fadeOut();
  	} else {
  		$("#"+divname).html("<div style='text-align:center;margin-top: 50px;font-size: 18px;font-family: inherit;'>"+"暂无数据"+"</div>");
  		$("#"+loadingname).fadeOut();
  	}
};

function loadJsForRollView(divname){
	$("#slideBox"+divname).slide({ 
        titCell:".play_hd ul",
        mainCell:".play_bd ul",
        autoPage:true,
        effect:"left",
        autoPlay:true,
        trigger:"click"
    });
}

function loadinfoview(infoAnalysisByColumnList,divname,loadingname){
	var x = new Array();
	var y1 = new Array();
	var y2 = new Array();
	var y3 = new Array();
	
	for(var i=0;i<infoAnalysisByColumnList.length;i++){
		x.push(infoAnalysisByColumnList[i].columnName);
		y1.push(infoAnalysisByColumnList[i].newCount);
		y2.push(infoAnalysisByColumnList[i].readCount);
		y3.push(infoAnalysisByColumnList[i].reviewCount);
	}
	
	// --图表
	var chart1 = $.createChart({
		title : '栏目信息统计',
		/*subTitle : '按栏目统计',*/
		legend: {
			x: 'center',
			y: 'bottom',
	        data:['发布数量','查看数量','评论数量']
	    },
		// 标题水平安放位置，默认为左侧，可选为：'center' | 'left' | 'right' | {number}（x坐标，单位px）
		titlePosX : 'left',
		// 标题垂直安放位置，默认为全图顶端，可选为：'top' | 'bottom' | 'center' | {number}（y坐标，单位px）
		titlePosY : 'top', 
		elementId : divname,
		// 启用拖拽重新计算功能
		calculable : true,
		// 使用右上角的工具箱
		toolboxEnabled : true,
		// 鼠标移动到图形上的提示开关
		tooltip : true,
		height : '400',
		width : $("#"+divname).closest(".tab-content").width() == null?'100%':$("#"+divname).closest(".tab-content").width()-3,
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
			data : y1,
			// 请求地址
			// url : getRootPath() + "/example/echartsData/barData.jsp",
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
			name : '查看数量'
		},{
			// 图形类型
			type : 'bar',
			// 请求的参数
			data : y3,
			// 请求地址
			// url : getRootPath() + "/example/echartsData/barData.jsp",
			// 鼠标移动到图形上的提示内容
			name : '评论数量'
		}]
	});
	
	$("#"+loadingname).fadeOut();
};
loadinfodata();
</script>
<input id="morelink_${portletId }" type="hidden" value="${funUrlmore }"/><!-- 更多链接 -->
<input id="morelink_${portletId }_${functionId}" type="hidden" value="${funUrlmore }"/><!-- 更多链接  -->
<c:if test="${funViewType != 'pviewType_8' }">
<input id="menubarlink_${portletId }" type="hidden" value="/im/info/infoMy.action"/><!-- 面包屑链接 -->
<input id="menubarlink_${portletId }_${functionId}" type="hidden" value="/im/info/infoMy.action"/><!-- 面包屑链接  -->
</c:if>
<c:choose>
	<c:when test="${funViewType == 'pviewType_9' }"><!-- 单一大列表 -->
	 <div class="index-inform panel" id="info9${portletId }${functionId}">
		
	</div>
	</c:when>
	<c:when test="${funViewType == 'pviewType_3' }"><!-- 图文混合 -->
        <div id="Projectorinfo3${portletId }${functionId}" class="play-list panel Projector">
        <div class="play_bd" id="info3${portletId }${functionId}">
            
        </div>
		<div class="play_hd"><ul></ul></div>
        </div>
	</c:when>
	<c:when test="${funViewType == 'pviewType_2' }"><!-- 图表混合 -->
	
	</c:when>
	<c:when test="${funViewType == 'pviewType_4' }"><!-- 单一图片 -->
		<div id="slideBoxinfo4${portletId }${functionId}" class="slideBox play-list">
          <div class="play_hd">
             <ul></ul>
          </div>
          <div class="play_bd" id="info4${portletId }${functionId}">
             
           </div>
         </div>
	</c:when>
	<c:when test="${funViewType == 'pviewType_5' }"><!-- 多图滚动 -->
		
	</c:when>
	<c:when test="${funViewType == 'pviewType_6' }"><!-- 单一文本 -->
	
	</c:when>
	<c:when test="${funViewType == 'pviewType_7' }"><!-- 快捷方式 -->
		
	</c:when>
	<c:when test="${funViewType == 'pviewType_8' }"><!-- 图表 -->
	 <div id="info8${portletId }${functionId}"></div>
	</c:when>
	<c:otherwise><!-- 单一小列表 -->
	 <div class="index-inform">
		<table class="table table-striped table-move first-td-tc over-hide-wrap">
		<thead>
			<tr>
				<th style="display: none;"></th>
				<th>信息标题</th>
				<th>发布人</th>
				<th >发布时间</th>
				<th style="display: none;"></th>
			</tr>
		</thead>
		<tbody id="info1${portletId }${functionId}">
		</tbody>
		</table>
	</div>
	</c:otherwise>
</c:choose>
<div class="loading" id="dataLoad_info${portletId }${functionId}"></div>	