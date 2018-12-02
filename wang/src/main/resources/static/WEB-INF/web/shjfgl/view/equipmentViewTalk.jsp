<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<style>
.inline.checkbox {
	background-color: #787878;width:4.5%;height:50px;margin-right:1px !important;
}
.inline.head {
	background-color: #787878;width:4.5%;height:20px;margin-right:1px;margin-left:9px !important;
}
</style>
<section class="scrollable padder jcGOA-section" id="scrollable">
<!-- <header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" >
        <h1>机房机柜管理</h1>
        <div class="crumbs"><a href="#">资源管理</a><i></i>机房平面图</div>
    </div>
</header> -->
<section class="tree-fluid m-t-md">
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">清华园机房平面图</h1>
	<form class="table-wrap  " id="equipmentInOutForm3">
							<!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
			<div class="table-wrap form-table" id="exchange">
				<c:forEach items="${dList}" var="m">
					<span id="neirong">${m.name }:${m.content}&nbsp&nbsp&nbsp&nbsp${m.startDate}<br/>
					</span>
				</c:forEach>
				<input type="hidden" id="token" name="token"value="${data.token}"> 
				<input type="hidden"id="modifyDate" name="modifyDate"> 
				<input type="hidden"id="name" name="name" value="${talkName}" /> 
				<input type="hidden" id="equipmentId" name="equipmentId" value="${idd}"/>
				<textarea id="content" name="content" rows="3" cols="3" style="width:700px"></textarea><br/>
				<%-- <shiro:hasPermission name="user:add4"> --%>
					<button id="exchangeSubmit" class="btn"
						onclick=save()
						type="button" class="btn">提 交</button>
				<%-- </shiro:hasPermission> --%>
			</div>
	</form>
</section>
</section>
<div id="formModuleDiv" ></div>
<script >
//设置每行按钮
function save() {
	if(equipmentInOutModule.subState)return;
		equipmentInOutModule.subState = true;
		var	url="";
	if ($("#equipmentInOutForm3").valid()) {
		if ($("#id").val() != 0) {
			url = getRootPath()+"/machine/exchange/save.action";
		}
		var formData = $("#equipmentInOutForm3").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				equipmentInOutModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
					});
					equipmentInOutModule.clearForm();
					$("#token").val(data.token);
				} 
			},
		});
	} else {
		equipmentInOutModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
function myrefresh() 
{ 
       window.location.reload(); 
} 
/* setTimeout('myrefresh()',100); */
</script>

<%@ include file="/WEB-INF/web/include/foot.jsp"%>