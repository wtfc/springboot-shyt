<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<style>
	*{
		margin: 0;
		padding: 0;
	}
	
	.inline.checkbox {
		background-color: #787878;width:4.5%;height:50px;margin-right:1px !important;
	}
	.inline.head {
		background-color: #787878;width:4.5%;height:20px;margin-right:1px;margin-left:9px !important;
	}
	
	html,body{
		height: 100%;
		background: #FFFFFF;
	}

	.main{
		height: 100%;
		width: 100%;
		/*background: #FFDDDD;*/
	}

	#shahe2047{
		width: 100%;
		margin-top:30px;
	}

	#shahe2047 tbody tr,#shahe2047 tbody tr td{
		/* border: 1px black solid; */
		width: auto;
		text-align: center;
	}

	#shahe2047 tbody tr td{
		height: 22px;
		line-height:22px;
		white-space:nowrap;
	}

	#shahe2047 .guodao{
		background: #FFFFDD; 
		height: 10px;
	}
	
	.wh{
		width:100%;
		background-color: #787878;
	}
</style>
<section class="scrollable padder jcGOA-section" id="scrollable">
<!-- <header class="con-header pull-in" id="header_1">
    <div class="con-heading fl" >
        <h1>机房机柜管理</h1>
        <div class="crumbs"><a href="#">资源管理</a><i></i>机房平面图</div>
    </div>
</header> -->
<section class="tree-fluid m-t-md">
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">沙河4027机房平面图</h1>
<div >
	<div style="padding-top:30px">
		<button  type="button" class="btn inline head"style="background-color: #CD0000;"></button>公司自用
		<button  type="button" class="btn inline head"style="background-color: #228B22;"></button>整包机柜
		<button  type="button" class="btn inline head" style="background-color: #CDCD00;"></button>散户机柜
		<button  type="button" class="btn inline head"style="background-color: #1C86EE;"></button>预留机柜
		<button  type="button" class="btn inline head"style="background-color: black;"></button>空机柜
		<button  type="button" class="btn inline head"></button>非本公司
	</div>
<table class="table table-striped tab_height first-td-tc" id="shahe2047">
<tbody>
	<!-- A -->
	<tr>
		<td><button id="A01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A01</button></td>
		<td><button id="A02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A02</button></td>
		<td><button id="A03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A03</button></td>
		<td><button id="A04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A04</button></td>
		<td><button id="A05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A05</button></td>
		<td>柱子</td>
		<td>柱子</td>
		<td><button id="A06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A06</button></td>
		<td><button id="A07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A07</button></td>
		<td><button id="A08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A08</button></td>
		<!-- <td><button type="button" class="btn inline checkbox wh"data-toggle="tooltip" data-placement="top" data-container="body"
		>交流列头柜</button></td> -->
		<td>交流列头柜</td>
		<!-- <td rowspan="2">null</td> -->
	</tr>
	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- B -->
	<tr>
		<td><button id="B01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B01</button></td>
		<td><button id="B02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B02</button></td>
		<td><button id="B03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B03</button></td>
		<td><button id="B04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B04</button></td>
		<td><button id="B05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B05</button></td>
		<td><button id="B06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B06</button></td>
		<td><button id="B07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B07</button></td>
		<td><button id="B08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B08</button></td>
		<td><button id="B09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B09</button></td>
		<td><button id="B10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B10</button></td>
		<td><button id="B11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B11'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B11</button></td>
		<td><button id="B12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B12'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B12</button></td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- C -->
	<tr>
		<td><button id="C01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C01</button></td>
		<td><button id="C02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C02</button></td>
		<td><button id="C03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C03</button></td>
		<td><button id="C04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C04</button></td>
		<td><button id="C05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C05</button></td>
		<td><button id="C06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C06</button></td>
		<td><button id="C07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C07</button></td>
		<td><button id="C08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C08</button></td>
		<td><button id="C09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C09</button></td>
		<td><button id="C10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C10</button></td>
		<td><button id="C11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C11'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C11</button></td>
		<td>交流列头柜</td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- D -->
	<tr>
		<td><button id="D01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D01</button></td>
		<td><button id="D02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D02</button></td>
		<td><button id="D03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D03</button></td>
		<td><button id="D04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D04</button></td>
		<td><button id="D05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D05</button></td>
		<td >柱子</td>
		<td >柱子</td>
		<td><button id="D06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D06</button></td>
		<td><button id="D07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D07</button></td>
		<td><button id="D08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D08</button></td>
		<td><button id="D09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D09</button></td>
		<td><button id="D10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D10</button></td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- E -->
	<tr>
		<td><button id="E01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E01</button></td>
		<td><button id="E02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E02</button></td>
		<td><button id="E03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E03</button></td>
		<td><button id="E04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E04</button></td>
		<td><button id="E05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E05</button></td>
		<td >柱子</td>
		<td >柱子</td>
		<td><button id="E06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E06</button></td>
		<td><button id="E07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E07</button></td>
		<td><button id="E08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E08</button></td>
		<td><button id="E09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E09</button></td>
		<td rowspan="2">交流列头柜</td>
	</tr>
	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- F -->
	<tr>
		<td><button id="F01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F01</button></td>
		<td><button id="F02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F02</button></td>
		<td><button id="F03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F03</button></td>
		<td><button id="F04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F04</button></td>
		<td><button id="F05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F05</button></td>
		<td><button id="F06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F06</button></td>
		<td><button id="F07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F07</button></td>
		<td><button id="F08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F08</button></td>
		<td><button id="F09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F09</button></td>
		<td><button id="F10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F10</button></td>
		<td><button id="F11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F11'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F11</button></td>
		<td><button id="F12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F12'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F12</button></td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- G -->
	<tr>
		<td><button id="G01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G01</button></td>
		<td><button id="G02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G02</button></td>
		<td><button id="G03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G03</button></td>
		<td><button id="G04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G04</button></td>
		<td><button id="G05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G05</button></td>
		<td><button id="G06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G06</button></td>
		<td><button id="G07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G07</button></td>
		<td><button id="G08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G08</button></td>
		<td><button id="G09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G09</button></td>
		<td><button id="G10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G10</button></td>
		<td><button id="G11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G11'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G11</button></td>
		<td >交流列头柜</td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- H -->
	<tr>
		<td><button id="H01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H01</button></td>
		<td><button id="H02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H02</button></td>
		<td><button id="H03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H03</button></td>
		<td><button id="H04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H04</button></td>
		<td><button id="H05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H05</button></td>
		<td >柱子</td>
		<td >柱子</td>
		<td><button id="H06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H06</button></td>
		<td><button id="H07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H07</button></td>
		<td><button id="H08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H08</button></td>
		<td><button id="H09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H09</button></td>
		<td><button id="H10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H10</button></td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- I -->
	<tr>
		<td><button id="I01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I01</button></td>
		<td><button id="I02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I02'}">  
	     		   ;" style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I02</button></td>
		<td><button id="I03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I03</button></td>
		<td><button id="I04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I04</button></td>
		<td><button id="I05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I05</button></td>
		<td >柱子</td>
		<td >柱子</td>
		<td><button id="I06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I06</button></td>
		<td><button id="I07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I07</button></td>
		<td><button id="I08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I08</button></td>
		<td><button id="I09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>I09</button></td>
		<td >交流列头柜</td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- J -->
	<tr>
		<td><button id="J01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J01</button></td>
		<td><button id="J02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J02</button></td>
		<td><button id="J03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J03</button></td>
		<td><button id="J04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J04</button></td>
		<td><button id="J05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J05</button></td>
		<td><button id="J06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J06</button></td>
		<td><button id="J07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J07</button></td>
		<td><button id="J08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J08</button></td>
		<td><button id="J09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J09</button></td>
		<td><button id="J10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J10</button></td>
		<td><button id="J11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J11'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J11</button></td>
		<td><button id="J12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J12'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J12</button></td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- K -->
	<tr>
		<td><button id="K01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K01</button></td>
		<td><button id="K02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K02</button></td>
		<td><button id="K03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K03</button></td>
		<td><button id="K04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K04</button></td>
		<td><button id="K05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K05</button></td>
		<td><button id="K06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K06</button></td>
		<td><button id="K07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K07</button></td>
		<td><button id="K08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K08</button></td>
		<td><button id="K09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K09</button></td>
		<td><button id="K10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K10</button></td>
		<td><button id="K11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K11'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>K11</button></td>
		<td >交流列头柜</td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- L -->
	<tr>
		<td><button id="K01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'K01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L01</button></td>
		<td><button id="L02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L02</button></td>
		<td><button id="L03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L03</button></td>
		<td><button id="L04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L04</button></td>
		<td><button id="L05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L05</button></td>
		<td >柱子</td>
		<td >柱子</td>
		<td><button id="L06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L06</button></td>
		<td><button id="L07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L07</button></td>
		<td><button id="L08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L08</button></td>
		<td><button id="L09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L09</button></td>
		<td><button id="L10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'L10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>L10</button></td>
	</tr>

	<tr><td colspan="12" class="guodao"></td></tr>
	<!-- M -->
	<tr>
		<td><button id="M01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M01'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M01</button></td>
		<td><button id="M02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M02'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M02</button></td>
		<td><button id="M03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M03'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M03</button></td>
		<td><button id="M04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M04'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M04</button></td>
		<td><button id="M05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M05'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M05</button></td>
		<td><button id="M06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M06'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M06</button></td>
		<td><button id="M07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M07'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M07</button></td>
		<td><button id="M08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M08'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M08</button></td>
		<td><button id="M09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M09'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M09</button></td>
		<td><button id="M10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M10'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M10</button></td>
		<td><button id="M11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'M11'}">  
	     		   style="background-color:${m.type};" onClick="add(4027,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>M11</button></td>
		<td >交流列头柜</td>
	</tr>
</tbody>
</table>
</div>
</section>
</section>
<div id="formModuleDiv" ></div>
<script >
//设置每行按钮
function add(name,number){
 $("#formModuleDiv").load(getRootPath()+"/machine/view/loadFormView.action?name="+name+"&&number="+number+"",null,function(){
	 viewModule.show();
		});
}
</script>

<%@ include file="/WEB-INF/web/include/foot.jsp"%>