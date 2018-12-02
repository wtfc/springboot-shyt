<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable">
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>付款合同信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	<section class="panel clearfix search-box search-shrinkage">
		<div class="search-line hide">
			<form class="table-wrap form-table" id="outContractQueryForm">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td>合同编码</td>
							<td><input type="text" name="contractNumber"
								id="contractNumber" /></td>
							<td>合同类型</td>
							<td><select id="agreement" name="agreement">
									<option value="">请选择</option>
									<option value="采购">采购</option>
									<option value="基础资源">基础资源</option>
									<option value="其他">其他</option>
							</select></td>
						</tr>
						<tr>
							<td>甲方</td>
							<td><select id="companyName" name="companyName">
									<option value="">请选择</option>
									<option value="北京森华易腾通信技术有限公司">北京森华易腾通信技术有限公司</option>
									<option value="北京网林未名信息技术有限公司">北京网林未名信息技术有限公司</option>
									<option value="北京云众林网络技术有限公司">北京云众林网络技术有限公司</option>
									<option value="廊坊森华易腾通信技术有限公司">廊坊森华易腾通信技术有限公司</option>
							</select></td>
							<td>乙方</td>
							<td><input type="text" style="width:100%;" id="customer"
								name="customer" /></td>
						</tr>
						<tr>
							<td>合同起始日期</td>
							<td><input data-date-format="yyyy-MM-dd"
								data-pick-time="false" class="datepicker-input" type="text"
								name="startDate" id="startDate" /></td>
							<td>合同终止日期</td>
							<td><input data-date-format="yyyy-MM-dd"
								data-pick-time="false" class="datepicker-input" type="text"
								id="endDate" name="endDate" /></td>
						</tr>
					</tbody>
				</table>
				<section class="form-btn m-b-lg">
					<button class="btn dark" type="button" id="queryMachine">查
						询</button>
					<button class="btn" type="button" id="queryReset">重 置</button>
				</section>
			</form>
		</div>
		<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
	</section>

	<section class="panel">
		<script>
//设置每行按钮
 function oTableSetButtones (source) {
	var read = "";
	var del = "";
	var readd="";
	if(source.seal=="是"||source.seal=="否"||source.place!=null){
		readd = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/outContract/loadForm.action?id="+source.id+"\" onclick=\"\">查看</a>";
	}else{
		read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/outContract/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>";
		del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"outContract.deleteOutContract("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	}
	return readd+read+del;
}; 
/**
 *  导出Excel
 */
var excuteExcel = {};
excuteExcel.exportExcel = function () {
    var url = getRootPath()+"/shyt/outContract/exportExcel.action";
    window.location.href=url;
};
jQuery(function($) {
	$("#buttonExport").click(excuteExcel.exportExcel);
});
</script>
		<h2 class="panel-heading clearfix">付款合同信息</h2>
		<div class="table-wrap">
			<table class="table table-striped tab_height first-td-tc"
				id="outContractTable">
				<thead>
					<tr>
						<th class="w46"><input type="checkbox" /></th>
						<th>合同编号</th>
						<th>发起人</th>
						<th>发起日期</th>
						<th>乙方</th>
						<th>甲方</th>
						<th>合同金额</th>
						<th>合同类型</th>
						<th>合同起始日期</th>
						<th>合同终止日期</th>
						<th>操作</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		<section class="clearfix" id="footer_height">
			<section class="form-btn fl m-l">
				<a class="btn dark" href="#" id="buttonExport" data-toggle="modal">导出Excel</a>
			</section>
		</section>
	</section>
</section>
<script src='${sysPath}/js/com/shyt/outContract/outContract.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>