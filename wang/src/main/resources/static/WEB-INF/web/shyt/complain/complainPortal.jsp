<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<script>
function loadComplaindata(){
	jQuery.ajax({
        url: getRootPath()+"/shyt/complain/getBoxData.action?funViewType=${funViewType}&dataRows=${dataRows}&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	if(data.notelist != null && '${funViewType}' == 'pviewType_1'){
        		loadcomplainmixlist(data.notelist,data.dataRows,"box1${portletId }${functionId}","dataLoad_complain${portletId }${functionId}");
        	} else {
        		$("#dataLoad_complain${portletId }${functionId}").fadeOut();
        	}
        },error:function(e){
        	// alert("加载数据错误。");
        	//msgBox.tip({content: "加载数据错误", type:'fail'});
        }
	});
}
function loadcomplainmixlist(notelist,dataRows,divname,loadingname){
  	var content = "";
  	if(notelist.length > 0){
  		for(var i=0;i<notelist.length;i++){
      		if(i == dataRows)
      			break;
      		content = content + 
      			"<tr><td>"+notelist[i].companyName+"</td><td>"+notelist[i].partment+"</td><td>"+notelist[i].complainDate+"</td>"+"<td>"+notelist[i].complainStatus+"</td></tr>";
      	}
      	$("#"+divname).html(content);
      	$("#"+loadingname).fadeOut();
  	} else {
  		$("#"+divname).html("<tr><td colspan='4' style='text-align:center;font-size: 13px;font-family: inherit;'>暂无数据</td></tr>");
      	$("#"+loadingname).fadeOut();
  	}
  	
};
function complainviewopen(boxContent,boxdate){
	$("#boxview${portletId }${functionId}").html(boxContent);
	$("#boxtitle${portletId }${functionId}").html(boxdate);
	$("#boxview-share${portletId }${functionId}").modal("show");
	//$("#boxview${portletId }${functionId}").fadeIn();
}
loadComplaindata();
</script>
<div class="modal fade panel" id="boxview-share${portletId }${functionId}" aria-hidden="false">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" 
					data-dismiss="modal" >×</button>
				<h4 class="modal-title" id="boxtitle${portletId }${functionId}"></h4>
			</div>
			<div class="modal-body" >
				<div id="boxview${portletId }${functionId}"></div>
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
				<th>公司名称</th>
				<th>被投诉部门</th>
				<th>投诉日期</th>
				<th>投诉类型</th>
			</tr>
		</thead>
		<tbody id="box1${portletId }${functionId}">
		</tbody>
		</table>
	</div>
	</c:otherwise>
</c:choose>
<div class="loading" id="dataLoad_complain${portletId }${functionId}"></div>