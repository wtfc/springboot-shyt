<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<style type="text/css" media=print>  
.noprint{display : none }  
</style>
<style type="text/css">
.dongfeng tr td:first-child,
	.dongfeng tr td:first-child+td+td,
	.dongfeng tr td:first-child+td+td+td+td {
		text-align: center !important;
		color:#fb6b5b;
	}
	.d-table tr td{
   		text-align:left !important;
   		padding-left:40px !important;
   		font-weight: normal !important;
   	}
   	.d-table tr:first-child td{
   		color: #fb6b5b !important;
   		font-weight: bold !important;
   	}
    .div-line{
   	   text-align: center;
   	   margin:0 auto;
   	   width:190px;
   	}
   	.div-line div{
   	    width:190px;
   	    color:#fb6b5b;
   		border-bottom: 2px solid #fb6b5b;
   		margin-bottom: 3px;
   		text-align: center;
   	}
   	.div-line div+div{
   		width:190px;
   	   border-bottom: 1px solid #fb6b5b;
   	   margin-bottom: 8px;
   	   text-align: center;
   	}
</style>
<!-- IDC账单查看页面 -->
<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header" id="header_1">
		<div class="con-heading fl" id="navigationMenu">
			<h1>账单信息</h1>
			<div class="crumbs"></div>
		</div>
	</header>
	<section class="panel clearfix" >
<div class="padder jcGOA-section" id="scrollable">
		<form id="toaFinanceBillForm" style="margin-top:20px; margin-bottom:10px; margin-left:35px; margin-right:35px; ">
		<div id="div2">
		<input type="hidden" id="id" name="id" value="${toaFinanceBill.id }"> 
		<input type="hidden" id="token" name="token" value="${data.token}">
		<input type="hidden" id="modifyDate" name="modifyDate" value="<fmt:formatDate value="${toaFinanceBill.modifyDate}" pattern="yyyy-MM-dd HH:mm:ss" />">
		<input type="hidden" id="billMoney" name="billMoney" value="${toaFinanceBill.billMoney}"/>
		<input type="hidden" id="state" name="state" value="1"/>
		<h4 align="center"><span style="font-size: 26px;color:black;line-height:1.5;font-weight:600;font-family:宋体"><fmt:formatDate value="${toaFinanceBill.billDate }" pattern="yyyy年MM月" />账单信息</span></h4> 
		<table cellspacing="0" cellpadding="0" width="100%" style="line-height:2;border-color:white;font-size:12pt;color:black;font-family:宋体">
		<tbody>
			<tr>
			  	<td style="border-color:white;">公  司  名  称&nbsp：${toaFinanceBill.companyName}</td>
			</tr> 
			<tr>
			  	<td style="border-color:white;">纳税人识别号：${toaFinanceBill.custmersTaxid }</td>
			</tr> 
			<tr>
			  	<td style="padding-left:115px; text-indent:-115px;border-color:white;">地址 及 电话：${toaFinanceBill.address } ${toaFinanceBill.phone }</td>
			</tr>   
			<tr>
			  	<td style="padding-left:115px; text-indent:-115px;border-color:white;">开户行及账号：${toaFinanceBill.bankName } ${toaFinanceBill.bankNo }</td>
			</tr>
			<tr>
			  	<td style="border-color:white;">本公司开具增值税 <select id="ticket" name="ticket" value="${toaFinanceBill.ticket}"><option value="普票">普票</option><option value="专票">专票</option></select></td>
			</tr>
			<tr>
			    <td style="color:black;">贵公司使用资源及产生费用详情如下：<%-- <span style="color:red">（ 新增：${addmoney}元    扩容：${scaleMoney}元   存量：${stackMoney}元）</span> --%></td>
			</tr>
		</tbody>
		</table>
		<table border="1" cellspacing="0" cellpadding="1" width="100%" style="border-collapse:collapse;" bordercolor="#000">
		<thead align="center" style="font-size:12pt;line-height:1.5;color:black;font-family:宋体" >
		    <tr>
		      <td rowspan="2" align="center" style="width:20%;">机房</td>
		     <!--  <td colspan="4" align="center">资源使用情况</td> -->
		      <td rowspan="2" align="center" style="width:25%;">资源使用情况</td>
		      <td colspan="2" align="center" style="width:30%;">账单周期</td>
		      <td rowspan="2" align="center" style="width:10%;">金额<br>(单位:元)</td>
		      <td rowspan="2" align="center" style="width:15%;"><br>备注</td>
		    </tr>
		    <tr>
		      <td>起始日</td>
		      <td>截止日</td>
		    </tr>  
		</thead>
		<tbody align="center" style="font-size:12pt;line-height:1.5;color:black;font-family:宋体">
		    <c:forEach items="${invoicesList}" var="m">
		    <tr>
		      <td>${m.roomName}</td>
		      <td>
		      	<c:if test="${not empty m.minBandwidth && m.minBandwidth !='0'}">
		      		${m.minBandwidth}M保底带宽
		      	</c:if>
		      	<c:if test="${not empty m.portBandwidth && m.portBandwidth !='0'}">
		      		${m.portBandwidth}M带宽
		      	</c:if>
	      		<c:if test="${not empty m.overflowBandwidth && m.overflowBandwidth !='0'}">
	      			${m.overflowBandwidth}M超流量带宽
		      	</c:if>
		      	<c:if test="${not empty m.cabinetNum && m.cabinetNum !='0'}">
		      		${m.cabinetNum}个机柜
		      	</c:if>
		      	<c:if test="${not empty m.serviceNum && m.serviceNum !='0'}">
		      		${m.serviceNum}台服务器
		      	</c:if>
		      	<c:if test="${not empty m.ipNum && m.ipNum !='0' }">
		      		${m.ipNum}个IP
		      	</c:if>
		      	<c:if test="${not empty m.switchNum && m.switchNum !='0'}">
		      		${m.switchNum}台交换机
		      	</c:if>
		      	<c:if test="${not empty m.odfNum && m.odfNum !='0'}">
		      		${m.odfNum}链路
		      	</c:if>
		      	<c:if test="${not empty m.portNum && m.portNum !='0'}">
		      		${m.portNum}个端口
		      	</c:if>
      			<c:if test="${not empty m.memoryNum && m.memoryNum !='0'}">
		      		${m.memoryNum}G内存
		      	</c:if>
		      	<c:if test="${not empty m.cpuNum && m.cpuNum !='0'}">
		      		${m.cpuNum}CPU
		      	</c:if>
		      	<c:if test="${not empty m.diskNum && m.diskNum !='0'}">
		      		${m.diskNum}硬盘
		      	</c:if>
		      </td>
		      <td><fmt:formatDate value="${m.invoicesStartdate }" pattern="yyyy-MM-dd" /></td>
		      <td><fmt:formatDate value="${m.invoicesEnddate }" pattern="yyyy-MM-dd" /></td>
		      <td>${m.monthAmount }</td>
		      <%-- <td>${m.receivedAccount}</td>
		      <td>${m.arrearage}</td> --%>
		       <td>${m.extStr1}</td> 
		     <%--  <td><a class="btn dark" href="#" onclick="toaFinanceBillModule.loadModule(${m.id })" id="addmachineButton" data-toggle="modal">录入</a></td>       --%>
		    </tr>               
		    </c:forEach>              
		        <tr>
		        	<td colspan="11">合计金额:&nbsp;${toaFinanceBill.billMoney}（元）</td>
		      <%-- <td colspan="11">合计金额（元）：小写：${money}（ 大写：${fmt.UpperCase(money)}）</td> --%>
		      	<%-- <td colspan="11"><span style="color:red">应收金额：${money}元  回款金额：${backMoney}元  未回款金额：${nobackkMoney}元</span> </td> --%>
		    </tr>
		    </tbody> 
		 </table>
		<ul style="font-size:12pt;padding:0px;margin:0px;line-height:2;color:black;font-family:宋体">
		<li style="list-style-type:none;color:black;line-height:2;">
		请贵公司于 <input data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input" value="<fmt:formatDate value="${toaFinanceBill.payDate }" pattern="yyyy年MM月dd日" />"type="text" id="payDate"  name="payDate" />以前支付托管费用。</li>
		<li style="list-style-type:none;line-height:2;color:black;">我公司账户信息如下：</li>
		<li style="list-style-type:none;line-height:2;">开户银行: ${toaFinanceBill.obankname }</li>
		<li style="list-style-type:none;line-height:2;">账　　号: ${toaFinanceBill.obankno }</li>
		<li style="list-style-type:none;line-height:2;">公司名称: ${toaFinanceBill.ocompany }</li>
		<li style="list-style-type:none;line-height:2;">客户经理: <input type="text" id="sale" name="sale" value="${toaFinanceBill.sale }" >　　电话: <input type="text" id="salePhone" name="salePhone" value="${toaFinanceBill.salePhone }"/></li>
		<li style="list-style-type:none;line-height:2;color:black;">提示：开具增值税专用发票，请务必于专票开具之日起180天内进行认证。如到期未认证，我公司将不予更换发票。</li>
		<li style="list-style-type:none;padding-top:30px"><span style="float:right;">
		北京森华易腾通信技术有限公司</span>  
		</li>
		<li style="list-style-type:none;"><br>
		<span style="float:right;margin-right:70px;">
		      <fmt:formatDate value="${toaFinanceBill.createDate }" pattern="yyyy年MM月dd日" />
		</span> 
		  </li>
		  <li>
		  	<span style="color: red">回退原因:</span>
		  	<textarea id="extStr1" name="extStr1"></textarea>
		  </li>
		</ul>  
		</div>
		</form>
		<div class="form-btn m-l m-b noprint">
			<a class="btn dark"  onclick="toaFinanceBillModule.saveModify(true)">提交预审</a>
			<a class="btn dark"  onclick="toaFinanceBillModule.saveOrModify(true)">确认提交</a>
			<a class="btn"  onclick="toaFinanceBillModule.falseModify(true)">账单退回</a>
		</div>     
	</div>
</section>
</section>
<div id="formModuleDiv" ></div>
<!--end 表格-->
<script type="text/javascript">

function docFormPrint() {
	window.print();
	window.opener = null;
	window.close();
}

</script>

<script src="${sysPath}/js/com/finance/toaFinanceBill/toaFinanceBillForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/finance/toaFinanceBill/toaFinanceBill.validate.js" type="text/javascript"></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>