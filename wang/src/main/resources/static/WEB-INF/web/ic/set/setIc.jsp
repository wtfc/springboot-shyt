<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<script type="text/javascript">
//显示没有行数据后面的操作按钮
	function oTableSetButtons( source) {
		var edit = "<shiro:hasPermission name='smsSet:update'><a class=\"a-icon i-edit m-r-xs\" href=\"#\" onclick=\"setIc.loadUpdateHtml("+ source.id+ ")\" role=\"button\" data-toggle=\"modal\"><i class=\"fa fa-edit2\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"编辑\"></i></a></shiro:hasPermission>";
		var del = "<shiro:hasPermission name='smsSet:delete'><a class=\"a-icon i-remove\" href=\"#\" onclick=\"setIc.deleteSet("+ source.id+ ")\"><i class=\"fa fa-remove\" data-toggle=\"tooltip\" data-placement=\"top\" title=\"\" data-container=\"body\" data-original-title=\"删除\"></i></a></shiro:hasPermission>";
		return edit+del;
	};
</script>
<section class="scrollable padder jcGOA-section" id="scrollable"> 
	<header class="con-header pull-in">
		<div class="con-heading fl" id="navigationMenu">
	       <h1></h1>
	       <div class="crumbs"></div>
	    </div>
		<shiro:hasPermission name="smsSet:add"><a class="btn dark fr" href="#" id="showAddDiv_t" role="button" data-toggle="modal">新 增</a></shiro:hasPermission>
	</header>
	<section class="panel clearfix search-box search-shrinkage">
		<div class="search-line hide">
	    <!--start 查询条件-->
	    <form class="table-wrap form-table" id="setSearchForm">
	        <table class="table table-td-striped">
	            <tbody>
	                <tr>
	                    <td class="w140">类别</td>
	                    <td style="width:40%;">
	                    	<input type="hidden" id="ssetType" name="ssetType">
	                    	<label class="radio inline">
	                            <input type="radio" name="type-2" id="whole" value="-1">全部
	                        </label>
	                        <label class="radio inline m-l-md">
	                            <input type="radio" name="type-2" id="slevel" value="0">级别
	                        </label>
	                        <label class="radio inline m-l-md">
	                            <input type="radio" name="type-2" id="sspersonal" value="1">个人
	                        </label>
	                    </td>
	                    <td class="w140">名称</td>
	                    <td>
		                    <!-- <select style="display:block" id="srankId">
		                    </select> -->
		                    <dic:select name="srankId" id="srankId" dictName="level" headName="-全部-" headValue="" defaultValue=""/>
		                    <div id="controlTreeSearch" style="display:none"></div>
	                    </td>
	                </tr>
	                <tr>
	                    <td>生效时间</td>
	                    <td >
	                    	<div class="input-group w-p100">
	                            <input  class="datepicker-input" type="text" name="setDateStart" id="setDateStart"  data-date-format="yyyy-MM-dd" data-ref-obj="#setDateEnd lt">
	                            <span class="input-group-btn w30">-</span>
	                            <input class="datepicker-input" type="text" name="setDateEnd" id="setDateEnd"  data-date-format="yyyy-MM-dd" data-ref-obj="#setDateStart gt">
	                        </div>
	                    </td>
	                    <td></td>
	                    <td>
	                    </td>
	                </tr>
	            </tbody>
	        </table>
	        <section class="form-btn m-b-lg">
	            <button class="btn dark query-jump" type="button" id="querySet">查 询</button>
	            <button class="btn" type="button" id="resetSearch">重 置</button>
	        </section>
	    </form>
	    </div>
	<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>	
	    <!--end 查询条件-->
	</section>
	<section class="panel">
	    <!--start 查询结果-->
	    <h2 class="panel-heading clearfix">查询结果</h2>
	    <div class="table-wrap">
	        <table class="table table-striped tab_height" id = "setTable">
	            <thead>
	                <tr>
	                    <th  class="w140">类别</th>
	                    <th>名称</th>
	                    <th class="w66">短信数量</th>
	                    <th class="w170">生效时间</th>
	                    <th class="w100">操作</th>
	                </tr>
	            </thead>
	            <tbody>
	                
	            </tbody>
	        </table>
	    </div>
	    <!--end 查询结果-->
	    <section class="clearfix">
	        <!--start 按钮-->
	        <section class="form-btn fl m-l">
	            <shiro:hasPermission name="smsSet:add"><a class="btn dark query-jump" role="button" id="showAddDiv" data-toggle="modal" href="#">新 增</a></shiro:hasPermission>
	        </section>
	        <!--end 按钮-->
	        <!--start 分页-->
	        <section class="pagination m-r fr m-t-none">
	        </section>
	        <!--end 分页-->
	    </section>
	</section>
</section>
<!--start 新增弹出层-->
<div id="setSmsEdit">
</div> 
<%-- <div class="modal fade panel" id="news" aria-hidden="false">
    <div class="modal-dialog">
    	<form class="table-wrap form-table" id="setForm">
    		
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal">×</button>
	                <h4 class="modal-title" id="saveOrUpdateName">新增</h4>
	            </div>
	            <div class="modal-body">
	                <div class="form-table">
		                <input type="hidden" id="id" name="id" value="0">
	           		    <input type="hidden" id="token" name="token" value="0">
	                    <input type="hidden" id="modifyDate" name="modifyDate">
	                    <input type="hidden" id="statisticsType" name="statisticsType" value="0">
	                    <input type="hidden" id="statisticsRank" name="statisticsRank">
	                    <table class="table table-td-striped">
	                        <tbody>
	                            <tr>
	                                <td class="w140">操作人</td>
	                                <td>
	                                	<input type="hidden" id="createUser" name="createUser"/>
	                                	<input  id="controlUserName" name="controlUserName" readonly/>
	                                </td>
	                            </tr>
	                            <tr>
	                                <td>生效时间</td>
	                                <td><input  id=startDate name="startDate" readonly/></td>
	                            </tr>
	                            <tr>  
	                                <td>类别</td>
	                                <td>
	                                	<input type="hidden" id="setType" name="setType" value="0"> 
	                                    <label class="radio inline">
	                                        <input type="radio" id="setType0" name="type-1"  value="0" checked>级别
	                                    </label>
	                                    <label class="radio inline m-l-md">
	                                        <input type="radio" id="setType1" name="type-1"  value="1">个人
	                                    </label>
	                                </td>
	
	                            </tr>
	                            <tr>  
	                                <td id="change">级别</td>
	                                <td id="selectOrdiv">
	                                	<div id="controlTree" style="display:none"></div>
	                                    <!-- <select style="display:block" id="rankId" name="rankIdSelect" class="rankIdV">
	                                    </select> -->
	                                    <dic:select name="rankIdSelect" id="rankId" dictName="level" headName="-请选择-" headValue="" defaultValue=""/>
	                                    
	                                </td>
	                            </tr>
	                            <tr>  
	                                <td><span class="required">*</span>短信数量</td>
	                                <td><input type="text" id="maxnum" name="maxnum"></td>
	                            </tr>
	                        </tbody>
	                    </table>
	                </div>
	            </div>
	            <div class="modal-footer form-btn">
	                <button class="btn dark" type="button" id="savaOrModify">保存继续</button>
					<button class="btn" type="button" id="savaAndClose">保存退出</button>
					<button class="btn" type="button" id="sava">保 存</button>
					<button class="btn" type="button" data-dismiss="modal">关 闭</button>
	            </div>
	        </div>
        </form>
    </div>
</div> --%>
<!--end 新增 弹出层-->
<script src="${sysPath}/js/com/ic/set/setIc.js" type="text/javascript"></script>
 <%@ include file="/WEB-INF/web/include/foot.jsp"%>
