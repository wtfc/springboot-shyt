<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
     <div class="con-heading fl" id="navigationMenu">
         <h1>采购申请项目表查询列表</h1>
     </div>
 </header>
 <section class="tree-fluid m-t-md">
     <section class="tree-right">
     	<section class="panel">
            <form class="table-wrap form-table" id="reqItemQueryForm">
                <table class="table table-td-striped">
	            	<tbody>
						<tr>
							<td>创建时间</td>
							<td>
								<input type="text" id="createDate" name = "createDate">
							</td>
						<tr>
							<td style="width:10%;">流程状态</td>
	                        <td style="width:40%;">
	                     		  <%@ include file="/WEB-INF/web/include/workflowSearch.jsp"%>
	                        </td>
						 </td>
						</tr>
		            </tbody>
		        </table>
                <section class="form-btn m-b-lg">
                    <button class="btn dark" type="button" id="queryReqItem">查 询</button>
                    <button class="btn" type="button" id="queryReset">重 置</button>
                </section>
            </form>
        </section>
         <section class="panel m-t-md">
             <div class="panel-heading clearfix btn-wrap">
                 <h2>查询结果</h2>
             </div>
             <div class="table-wrap input-default">
                 <table class="table table-striped table-bordered b-light first-td-tc tab_height" id="reqItemSearchTable">
                     <thead>
						<tr>
							<th class="w170">操作</th>
						</tr>
					</thead>
                     <tbody>
                     </tbody>
                 </table>
             </div>
             <section class="bp-inline clearfix" id="footer_height">
                <!--分页信息!-->
            </section>
         </section>
     </section>
 </section>
</section>

<script src='${sysPath}/js/com/pur/reqItem/reqItemSearch.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>