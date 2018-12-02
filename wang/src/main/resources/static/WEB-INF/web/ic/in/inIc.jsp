<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/ztree.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script src="${sysPath}/js/com/ic/out/outSearchForPhone.js" type="text/javascript"></script>
<script type="text/javascript">
	function oTableSetButtons( source) {
		var edit = "<a class=\"a-icon i-new m-r-xs\" href=\"#\" onclick=\"inIc.get("+ source.id+")\" data-toggle=\"modal\">查看</a>";
		var del = "<shiro:hasPermission name='in:delete'><a class=\"a-icon i-remove\" href=\"#\" onclick=\"inIc.deleteInMes("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"></i></a></shiro:hasPermission>";
		return edit + del;
	};
	//根据权限判断是否显示外部人员选择树
	function getPhoneUserButtons(itemId) {
		var add = "<a class='btn btn-file no-all input-group-btn' id='"+itemId+"Div' role='button' data-toggle='modal'><i class='fa fa-users'></i></a>";
		return add;
	};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in">
	    <div class="con-heading fl" id="navigationMenu">
	       <h1></h1>
	       <div class="crumbs"></div>
	    </div>
	</header>
	<input id="userPhoneName" type="hidden">
	<input id="userPhoneNum" type="hidden">
	<!-- start 查询条件 -->
	<section class="panel clearfix search-box search-shrinkage">
	 <div class="search-line hide">
	    <form class="table-wrap form-table" id="inQueryForm">
	        <table class="table table-td-striped">
	            <tbody>
	                <tr>
	                    <td class="w140">内容</td>
	                    <td><input type="text" name="text" id="text"/></td>
	                    <td class="w140">发信人</td>
	                    <td>
	                    	<div id="controlTree"></div>
	                    </td>
	                </tr>
	                <tr>
	                	<td class="w140">电话号码</td>
	                    <td style="width:40%;">
	                    	<input type="text" name="originator" id="originator"/>
	                    </td> 
	                    <td class="w140">接收时间</td>
	                    <td>
	                        <div class="input-group w-p100">
	                            <input  class="datepicker-input" type="text" name="inDateStart" id="inDateStart"  data-date-format="yyyy-MM-dd" data-ref-obj="#inDateEnd lt">
	                            <span class="input-group-btn w30">-</span>
	                            <input class="datepicker-input" type="text" name="inDateEnd" id="inDateEnd"  data-date-format="yyyy-MM-dd" data-ref-obj="#inDateStart gt">
	                        </div>
	                    </td>
	                    
	                </tr>
	                <tr>
	                	<td>
	                		外部联系人
	                	</td>
	                	<td>
	                		<span outSideUser="true" name="outIcArea" id="outIcAreaValid">
							</span>
	                	</td>
	                	<td></td>
	                	<td></td>
	                </tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark query-jump" type="button" id="queryIn">查 询</button>
	            <button class="btn" type="button" id="resetIn">重 置</button>
	        </section>
	    </form>
	    </div>
	<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
	</section>
	<!-- end 查询条件 --> 
	<!-- start 查询结果 --> 
	<section class="panel">
	    <h2 class="panel-heading clearfix">
	        查询结果
	    </h2>
	    <div class="table-wrap">
	        <table class="table table-striped tab_height first-td-tc" id="inIcTable">
	            <thead>
	                <tr>
	                	<th class="w46">
	                       <input type="checkbox" />
	                    </th>
	                    <th style="width:15%;">发信人</th>
	                    <th >内容</th>
	                   <!--  <th style="width:15%;">类型</th> -->
	                    <th style="width:15%;">接收时间</th>
	                   <th style="width:15%;">操作</th>
	                </tr>
	            </thead>
	            <tbody>
	                
	            </tbody>
	        </table>
	    </div>
	    <section class="bp-inline clearfix" id="footer_height">
			<section class="form-btn fl m-l">
				<button class="btn dark" type="button" id="deleteInMes">批量删除</button>
			</section>
		</section>
	</section>
</section>
<!-- end 查询结果 -->
<!--start 查看弹出层-->
<div class="modal fade panel" id="contentDiv" aria-hidden="false">
    <div class="modal-dialog">
    	<form class="table-wrap form-table" id="contentForm">
    		
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal">×</button>
	                <h4 class="modal-title">查看</h4>
	            </div>
	            <div class="modal-body">
	                <div class="form-table">
	                    <table class="table table-td-striped">
	                        <tbody>
	                            <tr>
	                                <td class="w140">发信人</td>
	                                <td id="r_originator">
	                                </td>
	                            </tr>
	                            <tr>
	                                <td>内容</td>
	                                <td id="r_text"></td>
	                            </tr>
	                            <tr>
	                                <td>接收时间</td>
	                                <td id="r_receiveDate"></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	                <div class="modal-footer form-btn">
	                    <button class="btn dark" type="button" data-dismiss="modal">关 闭</button>
                	</div>
	            </div>
	        </div>
        </form>
    </div>
</div>
<div id="outSearchUserTreeDiv"></div>
<!--end 查看弹出层-->
<script src="${sysPath}/js/com/ic/in/inIc.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>

