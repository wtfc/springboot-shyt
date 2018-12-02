<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>


<section class="panel">
<script >
//设置每行按钮
 function oTableSetButtones (source) {
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/outContract/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>";
	var del = "<a class=\"a-icon i-remove\" href=\"#\" onclick=\"outContract.deleteOutContract("+ source.id+")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" data-container=\"body\" data-original-title=\"删除\"></i></a>";
	return read+del ;
}; 
</script>
<div class="table-wrap">
	<table class="table table-striped tab_height first-td-tc" id="outContractTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>
				<th>公司名称</th>
				<th>合同编码</th>
				<th>合同类别</th>
				<th>合同金额</th>
				<th>合同起始日期</th>
				<th>合同终止日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td class="w46"><input type="checkbox" /></td>
				<td>北京一点网聚科技有限公司</td>
				<td>SHYT201504</td>
				<td>长期合同</td>
				<td>10000.00</td>
				<td>2015-04-01</td>
				<td>2015-08-01</td>
				<td>操作</td>
			</tr>
			<tr>
				<td class="w46"><input type="checkbox" /></td>
				<td>北京一点网聚科技有限公司</td>
				<td>SHYT201609</td>
				<td>长期合同</td>
				<td>10000.00</td>
				<td>2015-09-01</td>
				<td>2016-08-01</td>
				<td>操作</td>
			</tr>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<!-- <a class="btn dark" href="#" role="button" id="addmachineButton" data-toggle="modal">下 单</a> -->
		</section>
	</section>
</section>
<%-- <script src='${sysPath}/js/com/shjfgl/customer/customer.js'></script> --%>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>