<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<script>
function loadVisitdata(){
	jQuery.ajax({
        url: getRootPath()+"/network/toaNetworkShift/getBoxData.action?funViewType=${funViewType}&dataRows=${dataRows}&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	if(data.notelist != null && '${funViewType}' == 'pviewType_1'){
        		loadshiftmixlist(data.notelist,data.dataRows,"shift1${portletId }${functionId}","dataLoad_shift${portletId }${functionId}");
        	} else {
        		$("#dataLoad_shift${portletId }${functionId}").fadeOut();
        	}
        },error:function(e){
        	// alert("加载数据错误。");
        	//msgBox.tip({content: "加载数据错误", type:'fail'});
        }
	});
}
function loadshiftmixlist(notelist,dataRows,divname,loadingname){
  	var content = "";
  	if(notelist.length > 0){
  		for(var i=0;i<notelist.length;i++){
      		if(i == dataRows)
      			break;
      		//var visits = notelist[i].visit;
      		//infocontent=infocontent.replace(/<\s?img[^>]*>/gi,'');
      		//infocontent=infocontent.replace(/<(style|script|iframe)[^>]*?>[\s\S]+?<\/\1\s*>/gi,'').replace(/<[^>]+?>/g,'').replace(/\s+/g,'').replace(/(&nbsp;)/g,"");
      		//if(visits.length > 6){
      		//	visits = visits.substring(0,6)+"....";
      		//}
      		var date="";
      		if(notelist[i].finishDate!=null){
      			date=notelist[i].finishDate;
      		}
      		var status="";
      		if(notelist[i].extStr1==1){
      			status="交接完成";
      		}else{
      			status="待交接确认";
      		}
      		content = content + 
      			"<tr><td>"+notelist[i].executor+"</td><td>"+notelist[i].connectPeople+"</td><td>"+notelist[i].shiftDate+"</td><td>"+date+"</td><td>"+status+"</td></tr>";
      	}
      	$("#"+divname).html(content);
      	$("#"+loadingname).fadeOut();
  	} else {
  		$("#"+divname).html("<tr><td colspan='5' style='text-align:center;font-size: 13px;font-family: inherit;'>暂无数据</td></tr>");
      	$("#"+loadingname).fadeOut();
  	}
  	
};
function visitviewopen(boxContent,boxdate){
	$("#visitview${portletId }${functionId}").html(boxContent);
	$("#visittitle${portletId }${functionId}").html(boxdate);
	$("#visitview-share${portletId }${functionId}").modal("show");
	//$("#boxview${portletId }${functionId}").fadeIn();
}
loadVisitdata();
</script>
<div class="modal fade panel" id="visitiew-share${portletId }${functionId}" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" 
					data-dismiss="modal" >×</button>
				<h4 class="modal-title" id="visittitle${portletId }${functionId}"></h4>
			</div>
			<div class="modal-body" >
				<div id="visitview${portletId }${functionId}"></div>
			</div>
			<div class="modal-footer">
			</div>
		</div>
	</div>
</div>
<input id="morelink_${portletId }" type="hidden" value="${funUrlmore }"/><!-- 更多链接 -->
<input id="morelink_${portletId }_${functionId}" type="hidden" value="${funUrlmore }"/><!-- 更多链接  -->
<c:choose>
	<c:when test="${funViewType == 'pviewType_9' }"><!-- 单一大列表 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_3' }"><!-- 图文混合 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_2' }"><!-- 图表混合 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_4' }"><!-- 单一图片 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_5' }"><!-- 多图滚动 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_6' }"><!-- 单一文本 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_7' }"><!-- 快捷方式 -->
	</c:when>
	<c:when test="${funViewType == 'pviewType_8' }"><!-- 图表 -->
	</c:when>
	<c:otherwise><!-- 单一小列表 -->
	 <div class="index-inform">
		<table class="table table-striped table-move first-td-tc over-hide-wrap">
		<thead>
			<tr>
				<th>执行人</th>
				<th>交接人</th>
				<th>交接时间</th>
				<th>已交接时间</th>
				<th>状态</th>
			</tr>
		</thead>
		<tbody id="shift1${portletId }${functionId}">
		</tbody>
		</table>
	</div>
	</c:otherwise>
</c:choose>
<div class="loading" id="dataLoad_shift${portletId }${functionId}"></div>