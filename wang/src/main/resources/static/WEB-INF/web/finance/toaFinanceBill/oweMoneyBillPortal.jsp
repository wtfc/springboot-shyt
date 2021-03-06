<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="c"%>
<script>
function loadBilldata(){
	jQuery.ajax({
        url: getRootPath()+"/finance/toaFinanceBill/getBoxData.action?funViewType=${funViewType}&dataRows=${dataRows}&time="+new Date(),
        type: 'post',
        success: function(data, textStatus, xhr) {
        	if(data.notelist != null && '${funViewType}' == 'pviewType_1'){
        		loadBillmixlist(data.notelist,data.dataRows,"bill1${portletId }${functionId}","dataLoad_bill${portletId }${functionId}");
        	} else {
        		$("#dataLoad_bill${portletId }${functionId}").fadeOut();
        	}
        },error:function(e){
        	// alert("加载数据错误。");
        	//msgBox.tip({content: "加载数据错误", type:'fail'});
        }
	});
}
function loadBillmixlist(notelist,dataRows,divname,loadingname){
  	var content = "";
  	if(notelist.length > 0){
  		for(var i=0;i<notelist.length;i++){
      		if(i == dataRows)
      			break;
      		content = content + 
      			"<tr><td>"+notelist[i].companyName+"</td><td>"+notelist[i].billDate+"</td><td>"+notelist[i].billMoney+"</td>"+"<td>"+notelist[i].payDate+"</td><td>"+notelist[i].oweMoney+"</td><td>"+notelist[i].sale+"</td><td>"+notelist[i].salePhone+"</td></tr>";
      	}
      	$("#"+divname).html(content);
      	$("#"+loadingname).fadeOut();
  	} else {
  		$("#"+divname).html("<tr><td colspan='7' style='text-align:center;font-size: 13px;font-family: inherit;'>暂无数据</td></tr>");
      	$("#"+loadingname).fadeOut();
  	}
  	
};
function visitviewopen(boxContent,boxdate){
	$("#visitview${portletId }${functionId}").html(boxContent);
	$("#visittitle${portletId }${functionId}").html(boxdate);
	$("#visitview-share${portletId }${functionId}").modal("show");
	//$("#boxview${portletId }${functionId}").fadeIn();
}
loadBilldata();
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
				<th>客户名称</th>
				<th>账单日期</th>
				<th>应收金额</th>
				<th>应收日期</th>
				<th>欠费金额</th>
				<th>客户经理</th>
				<th>联系方式</th>
			</tr>
		</thead>
		<tbody id="bill1${portletId }${functionId}">
		</tbody>
		</table>
	</div>
	</c:otherwise>
</c:choose>
<div class="loading" id="dataLoad_bill${portletId }${functionId}"></div>