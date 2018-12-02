<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<script src="${sysPath}/js/com/sys/noticeMsg/noticeMsg.js" type="text/javascript"></script>
<section class="scrollable padder jcGOA-section" id="scrollable">
<header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
    </div>
</header>
<section class="panel search-box search-shrinkage clearfix">
	<div class="search-line hide">
            <form class="table-wrap form-table" id="noticeQueryForm">
                <table class="table table-td-striped">
                    <tbody>
                        <tr>
                            <td style="width:11%;">标题</td>
                            <td style="width:22%;">
                            	<input type="text" id="title" name="title"/>
                            </td>
                            <td style="width:11%;">类型</td>
                            <td style="width:22%;">
                            	<dic:select name="noticeType" id="noticeType" dictName="noticeType" headName="-全部-" headValue="" defaultValue=""/>
                            </td>
                            <td style="width:11%;">状态</td>
                            <td style="width:40%;">
                            	<select id="readFlag" name="readFlag">
                            		<option value="">-全部-</option>
                            		<option value="1">已读</option>
                            		<option value="0">未读</option>
                            	</select>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <section class="form-btn m-b-lg">
                    <button class="btn dark" type="button" id="queryNoticeMsg">查 询</button>
                    <button class="btn" type="button" id="queryReset">重 置</button>
                </section>
            </form>
        </div>
<%@ include file="/WEB-INF/web/include/searchConditionHide.jsp"%>
</section>
         <section class="panel clearfix">
             <div class="panel-heading clearfix btn-wrap">
                 <h2>查询结果</h2>
             </div>
             <div class="table-wrap input-default">
                 <table class="table table-striped table-bordered b-light first-td-tc tab_height" id="noticeMsgTable">
                     <thead>
                         <tr>
                         	 <th class="w46"><input type="checkbox"/></th>
                             <th>标题</th>
                             <th>类型</th>
                             <th>发送人</th>
                             <th>状态</th>
                             <th>操作</th>
                         </tr>
                     </thead>
                     <tbody>
                     </tbody>
                 </table>
             </div>
             <section class="bp-inline clearfix" id="footer_height">
                <section class="no-all form-btn fl m-l">
                   <button class="btn dark" type="submit" id="readBtn">批量已读</button>
                   <button class="btn" type="submit" id="deleteBtn">批量删除</button>
                </section>
                <!--分页信息!-->
            </section>
         </section>
   </section>
 <%@ include file="/WEB-INF/web/include/foot.jsp"%>