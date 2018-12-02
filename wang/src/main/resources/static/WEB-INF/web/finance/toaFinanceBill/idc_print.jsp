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
<div class="padder jcGOA-section" id="scrollable1">
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
			  	<td style="border-color:white;">本公司开具增值税 <u>${toaFinanceBill.ticket}</u><%-- <select id="ticket" name="ticket" value="${toaFinanceBill.ticket}"><option value="普通">普通</option><option value="专用">专用</option></select> --%>（专票/普票）</td>
			</tr>
			<tr>
			    <td style="color:black;">贵公司使用资源及产生费用详情如下：</td>
			</tr>
		</tbody>
		</table>
		<table border="1" cellspacing="0" cellpadding="1" width="100%" style="border-collapse:collapse;" bordercolor="#000">
		<thead align="center" style="font-size:12pt;line-height:1.5;color:black;font-family:宋体" >
		    <tr>
		      <td rowspan="2" align="center" style="width:20%;">机房</td>
		      <td rowspan="2" align="center" style="width:25%;" class="mouse_hover">资源使用情况</td>
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
		      <td>${m.roomName }</td>
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
		      <td>${m.extStr1}</td>      
		    </tr>               
		    </c:forEach>              
		        <tr>
		      <td colspan="9">合计金额:&nbsp;${toaFinanceBill.billMoney}（元）</td>
		    </tr>
		    </tbody> 
		 </table>
		<ul style="font-size:12pt;padding:0px;margin:0px;line-height:2;color:black;font-family:宋体">
		<li style="list-style-type:none;color:black;line-height:2;">
		请贵公司于<fmt:formatDate value="${toaFinanceBill.payDate }" pattern="yyyy年MM月dd日" />以前支付托管费用。</li>
		<li style="list-style-type:none;line-height:2;color:black;">我公司账户信息如下：</li>
		<li style="list-style-type:none;line-height:2;">开户银行: ${toaFinanceBill.obankname }</li>
		<li style="list-style-type:none;line-height:2;">账　　号: ${toaFinanceBill.obankno }</li>
		<li style="list-style-type:none;line-height:2;">公司名称: ${toaFinanceBill.ocompany }</li>
		<li style="list-style-type:none;line-height:2;">客户经理: ${toaFinanceBill.sale}　　电话: ${toaFinanceBill.salePhone }</li>
		<li style="list-style-type:none;line-height:2;color:black;">提示：开具增值税专用发票，请务必于专票开具之日起180天内进行认证。如到期未认证，我公司将不予更换发票。</li>
		<c:if test="${toaFinanceBill.state=='3' }">
		<li style="list-style-type:none;padding-top:30px">
			<span style="color: red;">回退原因:</span>${toaFinanceBill.extStr1}
		</li>
		</c:if>
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
		<a class="a-icon i-new m-r-xs" href="javascript:prn1_preview()">打印预览</a>
	</section>
</section> 
<script language="javascript" type="text/javascript">   
        var LODOP; //声明为全局变量 
	function prn1_preview() {	
		CreateOneFormPage();	
		LODOP.PREVIEW();	
	};
	function CreateOneFormPage(){
		LODOP=getLodop();  
		LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
		//设置页眉
		var header='<div style="width:660;text-align: right;">';
		header+='<img style="height:45.6px;width:115.2px;right: 0; display: block;" src="${sysPath}/img/logo_n.png">';
		header+='</div>';
		header+='<hr style="">';
		LODOP.ADD_PRINT_HTM('20', '50','660', '60',header);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Horient",2);

        LODOP.SET_PRINT_STYLE("FontSize",12);
		LODOP.SET_PRINT_STYLE("Bold",1);
		/* 	param1:上边距
			param2:左边距
			param3:宽度
			param4:高度
		*/
		LODOP.ADD_PRINT_HTM(100,73,660,420,document.getElementById("scrollable1").innerHTML);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",4);
		LODOP.SET_PRINT_STYLEA(0,"Horient",3);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",3);
		
		//设置页脚
		var foot='<div style="border-top: 1px dashed #000; position: relative; margin-top: 30px;">'
			foot+='<div style="display: inline-block; position: relative;">'
			foot+='<div style="padding-top:10px; font-size:10pt;font-weight:bold;">北京森华易腾通信技术有限公司</div>'
			foot+='<div style="font-size:8pt;">Forest Eternal Communication Tech Co.,Ltd.</div>'
			foot+='</div>'
			foot+='<div id="yejiao" style="margin-top: 10px; font-size:8pt; display: inline-block; position: absolute; right: 0px;">'
			foot+='<div>地址： 北京市海淀区丹棱街1号院1号楼互联网金融中心11层</div>'
			foot+='<div>咨询热线： 400-818-1123  |  网址： www.lenet.com.cn</div>'
			foot+='</div>'
			foot+='</div>'
		LODOP.ADD_PRINT_HTM(530,50,660,50,foot);
		LODOP.SET_PRINT_STYLEA(0,"ItemType",1);
		LODOP.SET_PRINT_STYLEA(0,"Vorient",1);

	};
</script> 
<script src='${sysPath}/js/com/finance/toaFinanceBill/LodopFuncs.js'></script>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>