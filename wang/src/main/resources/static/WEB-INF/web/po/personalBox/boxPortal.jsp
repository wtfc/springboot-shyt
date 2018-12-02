<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<script>
function loadBoxdata(){
	jQuery.ajax({
        url: getRootPath()+"/po/personalBox/getBoxData.action?funViewType=${funViewType}&dataRows=${dataRows}&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	if(data.notelist != null && '${funViewType}' == 'pviewType_1'){
        		loadboxmixlist(data.notelist,data.dataRows,"box1${portletId }${functionId}","dataLoad_box${portletId }${functionId}");
        	} else {
        		$("#dataLoad_box${portletId }${functionId}").fadeOut();
        	}
        },error:function(e){
        	// alert("加载数据错误。");
        	//msgBox.tip({content: "加载数据错误", type:'fail'});
        }
	});
}
function loadboxmixlist(notelist,dataRows,divname,loadingname){
  	var content = "";
  	if(notelist.length > 0){
  		for(var i=0;i<notelist.length;i++){
      		if(i == dataRows)
      			break;
      		var infocontent = notelist[i].content;
      		//infocontent=infocontent.replace(/<\s?img[^>]*>/gi,'');
      		//infocontent=infocontent.replace(/<(style|script|iframe)[^>]*?>[\s\S]+?<\/\1\s*>/gi,'').replace(/<[^>]+?>/g,'').replace(/\s+/g,'').replace(/(&nbsp;)/g,"");
      		if(infocontent.length > 15){
      			infocontent = infocontent.substring(0,15)+"......";
      		}
      		content = content + "<tr><td style=\"display: none;\"></td>"+
      			"<td><a href=\"#\" onclick=\"boxviewopen('"+notelist[i].content+"','"+notelist[i].modifyDate+"');\">"+
      			infocontent+"</a> </td><td>"+notelist[i].modifyDate+"</td><td style=\"display: none;\"></td></tr>";
      	}
      	$("#"+divname).html(content);
      	$("#"+loadingname).fadeOut();
  	} else {
  		$("#"+divname).html("<tr><td colspan='3' style='text-align:center;font-size: 13px;font-family: inherit;'>暂无数据</td></tr>");
      	$("#"+loadingname).fadeOut();
  	}
  	
};
function boxviewopen(boxContent,boxdate){
	$("#boxview${portletId }${functionId}").html(boxContent);
	$("#boxtitle${portletId }${functionId}").html(boxdate);
	$("#boxview-share${portletId }${functionId}").modal("show");
	//$("#boxview${portletId }${functionId}").fadeIn();
}
loadBoxdata();
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
				<th style="display: none;"></th>
				<th>便签内容</th>
				<th>发布时间</th>
				<th style="display: none;"></th>
			</tr>
		</thead>
		<tbody id="box1${portletId }${functionId}">
		</tbody>
		</table>
	</div>
	</c:otherwise>
</c:choose>
<div class="loading" id="dataLoad_box${portletId }${functionId}"></div>
<script src="${sysPath}/js/com/sys/attach/attach.js" type="text/javascript"></script>