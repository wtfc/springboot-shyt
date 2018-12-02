<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable">
    <section class="panel m-t-md" id="body">
        <h2 class="panel-heading clearfix tc">优秀团队投票统计结果<span style="font-size:15px;color:red">（共计有${topList}人参加了本次投票）</span></h2>
         <div class="table-wrap input-default">
             <table class="table table-striped table-bordered b-light tab_height" id="onlineInfoSearchTable">
                 <thead>
					<tr>
						<th style="text-align:center">编号</th>
						<th style="text-align:center">部门</th>
						<th style="text-align:center">票数</th>
					</tr>
				</thead>
                 <tbody>
                 	<tr>
                 		<td style="text-align:center">1</td>
                 		<td style="text-align:center">网络安全组</td>
                 		<td style="text-align:center">${yunxingerbu}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">2</td>
                 		<td style="text-align:center">运维五部</td>
                 		<td style="text-align:center">${yunxingwubu}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">3</td>
                 		<td style="text-align:center">网络监控部</td>
                 		<td style="text-align:center">${wangluo}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">4</td>
                 		<td style="text-align:center">销售三组</td>
                 		<td style="text-align:center">${xiaoshou}</td>
                 	</tr>
                 </tbody>
             </table>
         </div>
     </section>
 </section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>