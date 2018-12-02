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
		<input type="hidden" id="state" name="state" value="2"/>
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
			  	<td style="border-color:white;">本公司开具增值税${toaFinanceBill.ticket}（专票/普票）</td>
			</tr>
			<tr>
			    <td style="color:black;">贵公司使用资源及产生费用详情如下：<span style="color:red">（ 新增：${addmoney}元    扩容：${scaleMoney}元   存量：${stackMoney}元）</span></td>
			</tr>
		</tbody>
		</table>
		<table border="1" cellspacing="0" cellpadding="1" width="100%" style="border-collapse:collapse;" bordercolor="#000">
		<thead align="center" style="font-size:12pt;line-height:1.5;color:black;font-family:宋体" >
		    <tr>
		      <td rowspan="2" align="center">机房</td>
		      <td colspan="2" align="center">账单周期</td>
		      <td rowspan="2" align="center">应收金额<br>(单位:元)</td>
		      <td rowspan="2" align="center">回款金额<br>(单位:元)</td>
		      <td rowspan="2" align="center">未回款金额<br>(单位:元)</td>
		       <c:if test="${toaFinanceBill.state=='1'}">
		      <td rowspan="2" align="center">操作</td>
		      </c:if>
		    </tr>
		   <tr>
		      <td>起始日</td>
		      <td>截止日</td>
		    </tr>  
		</thead>
		<tbody align="center" style="font-size:12pt;line-height:1.5;color:black;font-family:宋体">
		    <c:forEach items="${invoicesList}" var="m">
		    <tr>
		      <td>${m.roomNameValue }</td>
		      <td><fmt:formatDate value="${m.invoicesStartdate }" pattern="yyyy-MM-dd" /></td>
		      <td><fmt:formatDate value="${m.invoicesEnddate }" pattern="yyyy-MM-dd" /></td>
		      <td>${m.monthAmount }</td>
		      <td>${m.receivedAccount}</td>
		      <td>${m.arrearage}</td>
		       <c:if test="${toaFinanceBill.state=='1'}">
		      <td><a class="btn dark" href="#" onclick="toaFinanceBillModule.loadModule(${m.id })" id="addmachineButton" data-toggle="modal">录入</a></td>           
		    	</c:if>
		    </tr>               
		    </c:forEach>              
		        <tr>
		     <td colspan="11"><span style="color:red">应收金额：${toaFinanceBill.billMoney}元  回款金额：${backMoney}元  未回款金额：${nobackkMoney}元</span> </td>
		    </tr>
		    </tbody> 
		 </table>
		<ul style="font-size:12pt;padding:0px;margin:0px;line-height:2;color:black;font-family:宋体">
		<li style="list-style-type:none;color:black;line-height:2;">
		请贵公司于 <fmt:formatDate value="${toaFinanceBill.payDate }" pattern="yyyy年MM月dd日" />以前支付托管费用。</li>
		<li style="list-style-type:none;line-height:2;color:black;">我公司账户信息如下：</li>
		<li style="list-style-type:none;line-height:2;">开户银行: ${toaFinanceBill.obankname }</li>
		<li style="list-style-type:none;line-height:2;">账　　号: ${toaFinanceBill.obankno }</li>
		<li style="list-style-type:none;line-height:2;">公司名称: ${toaFinanceBill.ocompany }</li>
		<li style="list-style-type:none;line-height:2;">客户经理: ${toaFinanceBill.sale }　　电话: ${toaFinanceBill.salePhone }</li>
		<li style="list-style-type:none;line-height:2;color:black;">提示：开具增值税专用发票，请务必于专票开具之日起180天内进行认证。如到期未认证，我公司将不予更换发票。</li>
		<li style="list-style-type:none;padding-top:30px"><span style="float:right;">
		北京森华易腾通信技术有限公司</span>  
		</li>
		<li style="list-style-type:none;"><br>
		<span style="float:right;margin-right:70px;">
		      <fmt:formatDate value="${toaFinanceBill.createDate }" pattern="yyyy年MM月dd日" />
		</span> 
		  </li>
		</ul>  
		</div>
		</form>
	</div>
</section>
</section>
<div id="formModuleDiv" ></div>
<script src="${sysPath}/js/com/oa/archive/docFormView.js" type="text/javascript"></script>
<script type="text/javascript">
function docFormPrint() {
	window.print();
	window.opener = null;
	window.close();
}
</script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>
<script src="${sysPath}/js/com/finance/toaFinanceBill/ispBillForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/finance/toaFinanceBill/toaFinanceBill.validate.js" type="text/javascript"></script>