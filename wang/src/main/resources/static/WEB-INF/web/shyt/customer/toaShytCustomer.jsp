<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<script src="${sysPath}/js/lib/common/leftRightSelect.js" type="text/javascript"></script>
<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>客户基本信息表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaShytCustomerQueryForm" >
	        <table class="table table-td-striped">
	            <tbody>
	            	<tr>
		            	<td>客户名称</td>
						<td>
					 		 <input type="text" id="companyName" name = "companyName"/>
						</td>
						<td>客户类型</td>
						 <td>
						 	<select id="memberType" name = "memberType">
							  <option selected value ="">请选择</option>
							  	<option value="VVIP客户">VVIP客户</option>
								<option value="VIP客户">VIP客户</option>
								<option value="大客户">大客户</option>
								<option value="普通客户">普通客户</option>
							</select>
						 </td>
					</tr>
					<tr>
						<td>所属客维</td>
						<td>
							<input id="tradeUser" name="tradeUser" type="text"/>
						</td>
						<td>关联客户名称</td>
						 <td>
						 	<input id="linkUser" name = "linkUser" type="text"/>
						 </td>
					</tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark" type="button" id="queryMachine">查 询</button>
	            <button class="btn" type="button" id="queryReset">重 置</button>
	        </section>
	    </form>
    </div>
    <%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
</section>

<section class="panel">
<script >
//设置每行按钮
 function oTableSetButtones (source) {
	var read = "<a class=\"a-icon i-new m-r-xs\" href=\""+getRootPath()+"/shyt/toaShytCustomer/loadForm.action?id="+source.id+"\" onclick=\"\">编辑</a>"; 
	var del = "<a class=\"a-icon i-remove m-r-xs\" href=\"#\" onclick=\"toaShytCustomer.deleteToaShytCustomer("+ source.id+")\" style=\"padding:1px 4px;\">删除</a>";
	var updateShyt = "<a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"toaShytCustomer.updateToaShytCustomer("+ source.id+")\" style=\"padding:1px 4px;\">更改维护专员</a>";
	return read+del+updateShyt;
}; 
</script>
<h2 class="panel-heading clearfix">客户基本信息表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaShytCustomerTable">
		<thead>
			<tr>
				<th class="w46"><input type="checkbox" /></th>				<th>客户名称</th>				<th>签约拓展组</th>				<th>签约拓展专员</th>				<th>维护专员</th>				<th>关联客户名称</th>				<th>所属行业</th>				<th>客户类型</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			<!-- <a class="btn dark" href="#" role="button" id="addmachineButton" data-toggle="modal">下 单</a> -->
		</section>
	</section>
</section>
</section>
<!--维护专员弹窗 -->
    <div class="modal fade panel" id="myModal-edit" aria-hidden="false">
        <div class="modal-dialog w900">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">修改维护专员</h4>
                </div>
                <div class="modal-body">
                    <form class="table-wrap form-table" id="toaShytCustomerTradeForm">
                    	<input type="hidden" id="id" name="id" value="0"/>
                        <input type="hidden" id="modifyDate" name="modifyDate"/>
                        <table class="table table-td-striped">
                            <tbody>
                           		<tr>
                           			<td style="width:10%;">原始维护专员</td>
									<td>
										<input type="text" readonly  style="width:100%;"id="tradeLastUser" name="tradeLastUser" />
									</td>
                           		</tr>
                           		<tr>
									<td style="width:10%;">更改维护专员</td>
									<td>
										<div id="showTradeUser"></div>
										<!-- <input type="text" style="width:100%;"id="tradeUsers" name="tradeUsers" /> -->
									</td>
								</tr>
                            </tbody>
                        </table>
                    </form>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn dark" type="button" onclick="toaShytCustomer.saveOrModify(true)">提 交</button>
                    <button class="btn" type="button" data-dismiss="modal">关 闭</button>
                </div>
            </div>
        </div>
    </div>
<script src='${sysPath}/js/com/shyt/customer/toaShytCustomer.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>