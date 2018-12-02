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
		overflow: auto;
	}

	#shahe1037{
		width: 100%;
		margin-top:30px;
	}

	#shahe1037 tbody tr,#shahe1037 tbody tr td{
		/* border: 1px black solid; */
		width: auto;
		text-align: center;
	}

	#shahe1037 tbody tr td{
		height: 22px;
		line-height:22px;
		min-width: 100px;
		white-space:nowrap;
	}

	#shahe1037 .guodao{
		background: #FFFFDD; 
		height: 10px;
	}
	
	.wh{
		/* margin:1px; */
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
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">沙河1037机房平面图</h1>
<div >
	<div style="padding-top:30px">
		<button  type="button" class="btn inline head"style="background-color: #CD0000;"></button>公司自用
		<button  type="button" class="btn inline head"style="background-color: #228B22;"></button>整包机柜
		<button  type="button" class="btn inline head" style="background-color: #CDCD00;"></button>散户机柜
		<button  type="button" class="btn inline head"style="background-color: #1C86EE;"></button>预留机柜
		<button  type="button" class="btn inline head"style="background-color: black;"></button>空机柜
		<button  type="button" class="btn inline head"></button>非本公司
	</div>
<div class="main">
<table class="table table-striped tab_height first-td-tc" id="shahe1037">
<tbody>
	<%-- <tr>
		<td>
		<button id="NE-7" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-7'}">  
	     		   style="background-color:${m.type};" style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-7</button>
	</tr> --%>
	<!-- E -->
	<tr>
		<td><button id="E01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E01'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E01</button></td>
		<td><button id="E02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E02'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E02</button></td>
		<td><button id="E03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E03'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E03</button></td>
		<td><button id="E04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E04'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E04</button></td>
		<td><button id="E05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E05'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E05</button></td>
		<!-- <td >柱子</td>
		<td >柱子</td> -->
		<td><button id="E06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E06'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E06</button></td>
		<td><button id="E07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E07'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E07</button></td>
		<td><button id="E08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E08'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E08</button></td>
		<td><button id="E09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E09'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E09</button></td>
		<td><button id="E10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E10'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E10</button></td>
		<td><button id="E11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E11'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E11</button></td>
		<td><button id="E12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E12'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E12</button></td>
		<td><button id="E13" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E13'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E13</button></td>
		<td><button id="E14" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E14'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E14</button></td>
		<td><button id="E15" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E15'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E15</button></td>
		<td><button id="E16" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E16'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E16</button></td>
		<td><button id="E17" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E17'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E17</button></td>
		<td>交流列头柜</td>
	</tr>
	
	<tr><td colspan="18" class="guodao"></td></tr>
	
	<!-- D -->
	<tr>
		<td><button id="D01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D01'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D01</button></td>
		<td><button id="D02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D02'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D02</button></td>
		<td><button id="D03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D03'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D03</button></td>
		<td><button id="D04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D04'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D04</button></td>
		<td><button id="D05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D05'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D05</button></td>
		<td><button id="D06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D06'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D06</button></td>
		<td><button id="D07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D07'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D07</button></td>
		<td><button id="D08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D08'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D08</button></td>
		<td><button id="D09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D09'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D09</button></td>
		<td><button id="D10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D10'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D10</button></td>
		<td><button id="D11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D11'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D11</button></td>
		<td><button id="D12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D12'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D12</button></td>
		<td><button id="D13" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D13'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D13</button></td>
		<td><button id="D14" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D14'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D14</button></td>
		<td><button id="D15" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D15'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D15</button></td>
		<td><button id="D16" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D16'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D16</button></td>
		<td><button id="D17" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D17'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D17</button></td>
		<td >交流列头柜</td>
	</tr>
	
	<tr><td colspan="18" class="guodao"></td></tr>
	
	<!-- C -->
	<tr>
		<td><button id="C01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C01'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C01</button></td>
		<td><button id="C02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C02'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C02</button></td>
		<td><button id="C03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C03'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C03</button></td>
		<td><button id="C04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C04'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C04</button></td>
		<td><button id="C05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C05'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C05</button></td>
		<td><button id="C06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C06'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C06</button></td>
		<td><button id="C07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C07'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C07</button></td>
		<td><button id="C08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C08'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C08</button></td>
		<td><button id="C09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C09'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C09</button></td>
		<td><button id="C10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C10'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C10</button></td>
		<td><button id="C11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C11'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C11</button></td>
		<td >柱子</td>
		<td >柱子</td>
		<td><button id="C12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C12'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C12</button></td>
		<td><button id="C13" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C13'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C13</button></td>
		<td><button id="C14" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C14'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C14</button></td>
		<td><button id="C15" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C15'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C15</button></td>
		<td>交流列头柜</td>
	</tr>
	
	<tr><td colspan="18" class="guodao"></td></tr>
	
	<!-- B -->
	<tr>
		<td><button id="B01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B01'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B01</button></td>
		<td><button id="B02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B02'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B02</button></td>
		<td><button id="B03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B03'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B03</button></td>
		<td><button id="B04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B04'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B04</button></td>
		<td><button id="B05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B05'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B05</button></td>
		<td><button id="B06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B06'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B06</button></td>
		<td><button id="B07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B07'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B07</button></td>
		<td><button id="B08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B08'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B08</button></td>
		<td><button id="B09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B09'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B09</button></td>
		<td><button id="B10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B10'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B10</button></td>
		<td><button id="B11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B11'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B11</button></td>
		<td><button id="B12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B12'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B12</button></td>
		<td><button id="B13" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B13'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B13</button></td>
		<td><button id="B14" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B14'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B14</button></td>
		<td><button id="B15" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B15'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B15</button></td>
		<td><button id="B16" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B16'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B16</button></td>
		<td><button id="B17" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B17'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B17</button></td>
		<td>交流列头柜</td>
	</tr>
	
	<tr><td colspan="18" class="guodao"></td></tr>
	
	<!-- A -->
	<tr>
		<td><button id="A01" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A01'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A01</button></td>
		<td><button id="A02" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A02'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A02</button></td>
		<td><button id="A03" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A03'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A03</button></td>
		<td><button id="A04" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A04'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A04</button></td>
		<td><button id="A05" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A05'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A05</button></td>
		<td><button id="A06" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A06'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A06</button></td>
		<td><button id="A07" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A07'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A07</button></td>
		<td><button id="A08" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A08'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A08</button></td>
		<td><button id="A09" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A09'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A09</button></td>
		<td><button id="A10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A10'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A10</button></td>
		<td><button id="A11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A11'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A11</button></td>
		<td><button id="A12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A12'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A12</button></td>
		<td><button id="A13" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A13'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A13</button></td>
		<td><button id="A14" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A14'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A14</button></td>
		<td><button id="A15" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A15'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A15</button></td>
		<td><button id="A16" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A16'}">  
	     		   style="background-color:${m.type};" onClick="add(1037,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A16</button></td>
		<td>交流列头柜</td>
	</tr>
</tbody>
</table>
</div>
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