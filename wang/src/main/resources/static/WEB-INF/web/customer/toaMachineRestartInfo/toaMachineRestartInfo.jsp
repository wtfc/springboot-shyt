<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>重启操作表</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="toaMachineRestartQueryForm" >
    		<input type = "hidden" id="companyId" name="companyId" value="${companyId}"/>
	        <table class="table table-td-striped">
	            <tbody>
		            <tr>
		            	<td style="width:15%">公司名称</td>
						<td style="width:35%">
							<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName"/></a>
						</td>
		            	<td style="width:15%">工单编号</td>
						<td style="width:35%">
							<input id="equipmentNumber" name="equipmentNumber" style="width:100%"/>
						</td>
					</tr>
					<tr>
						<td>所属机房</td>
						<td>
							<dic:select id="machina" name="machina" dictName="room" headName="-请选择-" headValue="" defaultValue="" cssStyle="width:100%;"/>
						</td>
		            	<td>IP地址</td>
						<td>
							<input id="ip" name="ip" style="width:100%"/>
						</td>
					</tr>
					<tr>
						<td>操作工程师</td>
						<td>
							<input id="caozgcs" name="caozgcs" style="width:100%"/>
						</td>
		            	<td>所处状态</td>
						<td>
							<select id="status" name="status" style="width:100%">
								<option value="">-请选择-</option>
								<option value="0">排队等待</option>
								<option value="1">操作者接单</option>
								<option value="2">到达设备现场</option>
								<option value="3">正在重启</option>
								<option value="4">重启完成,待评分</option>
								<option value="5">工单完成</option>
							</select>
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
<!--公司名称提取 开始  -->
	<div class="modal  panel" id="new-agency" aria-hidden="false">
        <div class="modal-dialog w820">
            <div class="modal-content">
                <div class="modal-header">
                	<button type="button" class="close" data-dismiss="modal">×</button>
                    <h4 class="modal-title">公司名称</h4>
                </div>
                <div class="modal-body">
                <form class="table-wrap  m-20-auto" id="companyForm">
				<table class="table table-td-striped">
					<tbody>
						<tr>
							<td >
								公司名称
							</td>
							<td>
								<input type = "text" id="companyName1" name="companyName1" />
					            <button class="btn dark" type="button" onclick="companyModule.getWorkTask()">查 询</button>
					            <button class="btn" type="button" onclick="companyModule.queryReset()">重 置</button>
							</td>
						</tr>
						</tbody>
					</table>
				</form>
                    <section class="panel-tab-con">
                        <div class="tab-content">
                                <!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
                                <form class="table-wrap">
                                    <table class="table table-striped frist-td-tc" id="issuedTaskTable">
                                        <thead>
                                            <th class="w46">请选择</th>
                                            <th>公司名称</th>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </form>
                            </div>
                    </section>
                </div>
                <div class="modal-footer form-btn">
                    <button class="btn" type="button" data-dismiss="modal" >关 闭</button>
                </div>
            </div>
        </div>
    </div>

<section class="panel">
<script >
//设置每行按钮
 function oTableSetButtones (source) {
	 See="<a class=\"a-icon i-edit m-r-xs\" href=\""+getRootPath()+"/customer/toaMachineRestartInfo/loadSee.action?id="+source.id+"\" onclick=\"\" style=\"padding:0px 5px;\">查 看</a>";
	 if(source.status==4||source.status==5){
		See+="<a class=\"a-icon i-edit m-r-xs\" href=\"#myModal-edit\" onclick=\"toaMachineRestartInfo.saveOrModify("+ source.id+ ")\" style=\"padding:0px 5px;\">进一步操作</a>";
	 }
	return See;
}; 
</script>
<h2 class="panel-heading clearfix">重启操作</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="toaMachineRestartTable">
		<thead>
			<tr>
				<th>公司名称</th>				<th>工单编号</th>				<th>所属机房</th>				<th>IP地址（机柜）</th>				<th>操作工程师</th>				<th>操作时间</th>				<th>完成时间</th>
				<th>工单状态</th>
				<th>工单类型</th>				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		</tbody>
	</table>
</div>
	<section class="clearfix" id="footer_height" >
		<section class="form-btn fl m-l">
			
		</section>
	</section>
</section>
</section>
<div id="formModuleDiv" ></div>
<script src='${sysPath}/js/com/customer/toaMachineRestartInfo/toaMachineRestartInfo.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>