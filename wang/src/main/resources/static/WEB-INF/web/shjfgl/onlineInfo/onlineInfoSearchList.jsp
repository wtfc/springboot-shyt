<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %> 
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<section class="scrollable padder jcGOA-section" id="scrollable">
    <section class="panel m-t-md" id="body">
        <h2 class="panel-heading clearfix tc">统计结果</h2>
         <div class="table-wrap input-default">
             <table class="table table-striped table-bordered b-light tab_height" id="onlineInfoSearchTable">
                 <thead>
					<tr>
						<th style="text-align:center">姓名</th>
						<th style="text-align:center">问卷数量</th>
						<th style="text-align:center">总分数</th>
						<th style="text-align:center">平均分</th>
					</tr>
				</thead>
                 <tbody>
                 <c:forEach items="${topList}" var="m">
                 	<tr>
                 		<td style="text-align:center">${m.extStr1}</td>
                 		<td style="text-align:center">${m.number}</td>
                 		<td style="text-align:center">${m.zongfen}</td>
                 		<td style="text-align:center"><fmt:formatNumber type="number" value="${m.pingjunfen} " maxFractionDigits="2"/>
                 		</td>
                 	</tr>
                 </c:forEach>
                 </tbody>
             </table>
         </div>
     </section>
 </section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>