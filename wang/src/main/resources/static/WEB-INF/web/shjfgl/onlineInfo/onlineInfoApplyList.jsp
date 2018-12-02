<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable">
    <section class="panel m-t-md" id="body">
        <h2 class="panel-heading clearfix tc">统计结果</h2>
         <div class="table-wrap input-default">
             <table class="table table-striped table-bordered b-light tab_height" id="onlineInfoSearchTable">
                 <thead>
					<tr>
						<th style="text-align:center">题号</th>
						<th style="text-align:center">问卷数量</th>
						<th style="text-align:center">选项A</th>
						<th style="text-align:center">选项B</th>
						<th style="text-align:center">选项C</th>
						<th style="text-align:center">选项D</th>
					</tr>
				</thead>
                 <tbody>
                 	<tr>
                 		<td style="text-align:center">第一题</td>
                 		<td style="text-align:center">${topList}</td>
                 		<td style="text-align:center">${num39a }</td>
                 		<td style="text-align:center">${num39b }</td>
                 		<td style="text-align:center">${num39c }</td>
                 		<td style="text-align:center">${num39d }</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">第二题</td>
                 		<td style="text-align:center">${topList}</td>
                 		<td style="text-align:center">${num40a }</td>
                 		<td style="text-align:center">${num40b }</td>
                 		<td style="text-align:center">${num40c }</td>
                 		<td style="text-align:center">${num40d }</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">第三题</td>
                 		<td style="text-align:center">${topList}</td>
                 		<td style="text-align:center">${num41a }</td>
                 		<td style="text-align:center">${num41b }</td>
                 		<td style="text-align:center">${num41c }</td>
                 		<td style="text-align:center">${num41d }</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">第四题</td>
                 		<td style="text-align:center">${topList}</td>
                 		<td style="text-align:center">${num42a }</td>
                 		<td style="text-align:center">${num42b }</td>
                 		<td style="text-align:center">0</td>
                 		<td style="text-align:center">0</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">第五题</td>
                 		<td style="text-align:center">${topList}</td>
                 		<td style="text-align:center">${num43a }</td>
                 		<td style="text-align:center">${num43b }</td>
                 		<td style="text-align:center">0</td>
                 		<td style="text-align:center">0</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">第六题</td>
                 		<td style="text-align:center">${topList}</td>
                 		<td style="text-align:center">${num44a }</td>
                 		<td style="text-align:center">${num44b }</td>
                 		<td style="text-align:center">${num44c }</td>
                 		<td style="text-align:center">${num44d }</td>
                 	</tr>
                 </tbody>
             </table>
         </div>
     </section>
 </section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>