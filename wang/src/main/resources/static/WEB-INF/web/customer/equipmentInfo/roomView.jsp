<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>

<!-- TODO 面包屑 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>设备上架表</h1>
			<div class="crumbs"></div>
		</div>
		
	</header>
	
<section class="panel clearfix search-box search-shrinkage">
    <div class="search-line hide">
    	<form class="table-wrap form-table" id="equipmentQueryForm" name="equipmentQueryForm">
	        <table class="table table-td-striped">
	            <tbody>
		          
					<tr>
						<td style="width:15%">公司名称</td>
						<td style="width:35%">
							<a  onclick="companyModule.closeWin();" type="button" href="#new-agency" role="button" data-toggle="modal"><input style="width:100%;" readOnly type = "text" id="companyName" name="companyName"/></a>
						</td>
						<td style="width:15%">资产编号</td>
						<td style="width:35%">
					 		<input type="text" id="serialNumber" name = "serialNumber"/>
						</td>
					</tr>
					<tr>
					
						<td>上架时间</td>
						 <td>
						 	<input class="datepicker-input" type="text" id="onlineDate" name="onlineDate" data-pick-time="true" data-date-format="yyyy-MM-dd" >
						 </td>
						<td>机房名称</td>
						<td>
							<dic:select  id="contact" name="contact" dictName="room" />
                      	</td>
					</tr>
					<tr>
                      	<td>管理网IP</td>
				     	<td>
						 	<input type="text" id="ip" name = "ip"/>
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
<h2 class="panel-heading clearfix">设备上架表</h2>
<div class="table-wrap">
	
	<table class="table table-striped tab_height first-td-tc" id="equipmentTable">
		<thead>
			<tr>
				<th>公司名称</th>
				<th>品牌型号</th>
				<th>机房</th>
				<th>机柜位置</th>
				<th>机柜编号</th>
				<th>上架时间</th>
				<th>管理网IP</th>
				<th>交换机对应端口</th>
				<th>资产编号</th>
				<th>SN</th>
				<th>操作</th>
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
<script src='${sysPath}/js/com/customer/equipmentInfo/roomView.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>