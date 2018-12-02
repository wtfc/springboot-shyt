<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable">
    <section class="panel m-t-md" id="body">
        <h2 class="panel-heading clearfix tc">优秀员工投票统计结果<span style="font-size:15px;color:red">（共计有${topList}人参加了本次投票）</span></h2>
         <div class="table-wrap input-default">
             <table class="table table-striped table-bordered b-light tab_height" id="onlineInfoSearchTable">
                 <thead>
					<tr>
						<th style="text-align:center">编号</th>
						<th style="text-align:center">部门</th>
						<th style="text-align:center">姓名</th>
						<th style="text-align:center">票数</th>
					</tr>
				</thead>
                 <tbody>
                 	<tr>
                 		<td style="text-align:center">1</td>
                 		<td style="text-align:center">运行维护部</td>
                 		<td style="text-align:center">何全</td>
                 		<td style="text-align:center">${hequan}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">2</td>
                 		<td style="text-align:center">运行维护部</td>
                 		<td style="text-align:center">卞剑飞</td>
                 		<td style="text-align:center">${bianjiafei}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">3</td>
                 		<td style="text-align:center">运行维护部</td>
                 		<td style="text-align:center">刘超</td>
                 		<td style="text-align:center">${liuchao}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">4</td>
                 		<td style="text-align:center">运行维护部</td>
                 		<td style="text-align:center">潘继清</td>
                 		<td style="text-align:center">${panjiqing}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">5</td>
                 		<td style="text-align:center">运行维护部</td>
                 		<td style="text-align:center">张磊</td>
                 		<td style="text-align:center">${zhanglei}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">6</td>
                 		<td style="text-align:center">网络管理部</td>
                 		<td style="text-align:center">董铮</td>
                 		<td style="text-align:center">${dongzheng}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">7</td>
                 		<td style="text-align:center">网络管理部</td>
                 		<td style="text-align:center">韩煦</td>
                 		<td style="text-align:center">${hanxuan}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">8</td>
                 		<td style="text-align:center">网络监控部</td>
                 		<td style="text-align:center">曹艳飞</td>
                 		<td style="text-align:center">${caoyanfei}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">9</td>
                 		<td style="text-align:center">系统维护部</td>
                 		<td style="text-align:center">林腾飞</td>
                 		<td style="text-align:center">${lintengfei}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">10</td>
                 		<td style="text-align:center">呼叫中心</td>
                 		<td style="text-align:center">张立元</td>
                 		<td style="text-align:center">${zhangliyuan}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">11</td>
                 		<td style="text-align:center">人事行政部</td>
                 		<td style="text-align:center">姚雪</td>
                 		<td style="text-align:center">${yaoxue}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">12</td>
                 		<td style="text-align:center">战略合作部</td>
                 		<td style="text-align:center">王飞</td>
                 		<td style="text-align:center">${wangfei}</td>
                 	</tr>
                 	<tr>
                 		<td style="text-align:center">13</td>
                 		<td style="text-align:center">战略合作部</td>
                 		<td style="text-align:center">刘燚</td>
                 		<td style="text-align:center">${liuyi}</td>
                 	</tr>
                 </tbody>
             </table>
         </div>
     </section>
 </section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>