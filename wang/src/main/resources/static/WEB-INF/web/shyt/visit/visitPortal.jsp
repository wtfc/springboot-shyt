<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<script>
function loadVisitdata(){
	jQuery.ajax({
        url: getRootPath()+"/shyt/visit/getBoxData.action?funViewType=${funViewType}&dataRows=${dataRows}&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	if(data.notelist != null && '${funViewType}' == 'pviewType_1'){
        		loadvisitmixlist(data.notelist,data.dataRows,"visit1${portletId }${functionId}","dataLoad_visit${portletId }${functionId}");
        	} else {
        		$("#dataLoad_visit${portletId }${functionId}").fadeOut();
        	}
        },error:function(e){
        	// alert("加载数据错误。");
        	//msgBox.tip({content: "加载数据错误", type:'fail'});
        }
	});
}
function loadvisitmixlist(notelist,dataRows,divname,loadingname){
  	var content = "";
  	if(notelist.length > 0){
  		for(var i=0;i<notelist.length;i++){
      		if(i == dataRows)
      			break;
      		//var visits = notelist[i].visit;
      		//infocontent=infocontent.replace(/<\s?img[^>]*>/gi,'');
      		//infocontent=infocontent.replace(/<(style|script|iframe)[^>]*?>[\s\S]+?<\/\1\s*>/gi,'').replace(/<[^>]+?>/g,'').replace(/\s+/g,'').replace(/(&nbsp;)/g,"");
      		/* if(visits.length > 6){
      			visits = visits.substring(0,6)+"....";
      		} */
      		content = content + 
      			"<tr><td><a href=\"#\" onclick=\"visitviewopen('"+notelist[i].visitDate+"');\">"+
      			notelist[i].companyName+"</a> </td><td>"+notelist[i].visitStatus+"</td><td>"+notelist[i].visitDate+"</td></tr>";
      			/* 
      			*"+notelist[i].visit+"','
      			*"+"<td>"+visits+"</td> 
      			*/
      	}
      	$("#"+divname).html(content);
      	$("#"+loadingname).fadeOut();
  	} else {
  		$("#"+divname).html("<tr><td colspan='3' style='text-align:center;font-size: 13px;font-family: inherit;'>暂无数据</td></tr>");
      	$("#"+loadingname).fadeOut();
  	}
  	
};
function visitviewopen(boxdate){
	/*
	* boxContent,
	*/
	//$("#visitview${portletId }${functionId}").html(boxContent);
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
				<th>公司名称</th>
				<th>回访类型</th>
				<th>回访时间</th>
				<!-- <th>回访内容</th> -->
			</tr>
		</thead>
		<tbody id="visit1${portletId }${functionId}">
		</tbody>
		</table>
	</div>
	</c:otherwise>
</c:choose>
<div class="loading" id="dataLoad_visit${portletId }${functionId}"></div>