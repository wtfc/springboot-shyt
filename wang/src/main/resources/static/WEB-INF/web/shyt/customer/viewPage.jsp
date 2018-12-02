<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@ taglib prefix="c" uri="c"%>
<link href="${sysPath}/css/viewPage.css" rel="stylesheet" type="text/css" />
<section class="scrollable padder jcGOA-section" id="scrollable" style="background:#fff; overflow:auto;" > 
	<header class="con-header pull-in" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<div class="crumbs" id="projectName">
			</div>
		</div>
	</header>
   <div class="table-wrap">
	<br/>
      <font size=4>&nbsp;&nbsp;&nbsp;客户名称：${company}</font>
	  <section class="dis-table" >
          <section class="panel-tab-con dis-table-cell">
                 <!-- tabs -->
             <section class="tabs-wrap tabs-wrap-in">
             
             <div class="widget">
	                 <div class="widget-head" onclick="$('#projectInfo').toggle(500);openView('projectInfo','${sysPath}/shyt/toaShytCustomer/loadForm2.action?id=${Id}')">
	                  <div class="pull-left">基本信息</div> 
	              		 <div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	                <div id="projectInfo" class="widget-content" style="display:none;"></div>
               <shiro:hasPermission name="customerPeople">
	                  <div class="widget-head" onclick="$('#projectTimenode').toggle(500);openView('projectTimenode','${sysPath}/shyt/customerPeople/manage2.action?id=${Id}');">
	                  <div class="pull-left">联系信息</div> 
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="projectTimenode" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="contract">
	              <div class="widget-head" onclick="$('#projectResource').toggle(500);openView('projectResource','${sysPath}/contract/toaContractIncontract/manage2.action?id=${Id}')">
	                  <div class="pull-left">合同信息</div> 
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="projectResource" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="mainInvoce">
	              <div class="widget-head" onclick="$('#projectMaininvoce').toggle(500);openView('projectMaininvoce','${sysPath}/finance/toaFinanceMain/manage2.action?id=${Id}')">
	                  <div class="pull-left">计费信息</div> 
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="projectMaininvoce" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="resourcePrice">
	              <div class="widget-head" onclick="$('resourcePrice').toggle(500);openView('resourcePrice','${sysPath}/finance/toaFinanceMain/manageResource.action?id=${Id}')">
	                  <div class="pull-left">单价信息</div> 
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="resourcePrice" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="machine">
	             <div class="widget-head" onclick="$('#projectMachine').toggle(500);openView('projectMachine','${sysPath}/machine/equipment/khfwPageManage.action?id=${Id}')">
	                  <div class="pull-left">机房信息</div>
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="projectMachine" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="netWork">
	              <div class="widget-head" onclick="$('#projectNet').toggle(500);openView('projectNet','${sysPath}/network/toaNetworkIpaddress/khfwViewManage.action?id=${Id}')">
	                  <div class="pull-left">网络资源信息</div> 
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="projectNet" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="cloud">
	              <div class="widget-head" onclick="$('#projectCloud').toggle(500);openView('projectCloud','${sysPath}/product/toaProductCloud/manageView.action?id=${Id}')">
	                  <div class="pull-left">云资源信息</div> 
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="projectCloud" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="cdn">
	              <div class="widget-head" onclick="$('#projectCDN').toggle(500);openView('projectCDN','${sysPath}/product/toaProductCdn/manageView.action?id=${Id}')">
	                  <div class="pull-left">CDN资源信息</div>
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="projectCDN" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="complain">
	              <div class="widget-head" onclick="$('#projectPlan').toggle(500);openView('projectPlan','${sysPath}/shyt/complain/manage2.action?id=${Id}')">
	                  <div class="pull-left">投诉信息</div> 
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	              <div id="projectPlan" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              
              <shiro:hasPermission name="visit">
	              <div class="widget-head" onclick="$('#projectQuestion').toggle(500);openView('projectQuestion','${sysPath}/shyt/visit/manage2.action?id=${Id}')">
	                  <div class="pull-left">回访信息</div> 
	                  	<div style="float:right">▼</div>
	                  <div class="clearfix"></div>
	                </div>
	             <div id="projectQuestion" class="widget-content" style="display:none;"></div>
              </shiro:hasPermission>
              </div>
              </section>
          </section>
       </section>
</div>
</section>
<script type="text/javascript">
$(document).ready(function(){
	var ids=(${Id});
	$("#projectInfo").toggle(500);
	$("#projectInfo").load("${sysPath}/shyt/toaShytCustomer/loadForm2.action?id="+ids+"");
	});
function openView(div,url){
	$("#"+div+"").load(url);
}
</script>