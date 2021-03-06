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
		overflow: auto;
	}

	#fufeng{
		width: 100%;
		margin-top:30px;
	}

	#fufeng tbody tr,#fufeng tbody tr td{
		/* border: 1px black solid; */
		width: auto;
		text-align: center;
	}

	#fufeng tbody tr td{
		height: 22px;
		line-height:22px;
		min-width: 100px;
		white-space:nowrap;
	}
	
	#fufeng .guodao{
		background: #99CC99;
		height: 10px;
		border: none;
	}
	
	.wh{
		/* margin:1px; */
		width:100%;
		background-color: #787878;
	}
</style>
<section class="scrollable padder jcGOA-section" id="scrollable">

<section class="tree-fluid m-t-md">
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">富丰机房平面图</h1>
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
<table class="table table-striped tab_height first-td-tc" id="fufeng">
<tbody>
	
	<!-- A -->
	<tr>
		<td><button id="A01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A01'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A01</button></td>
		<td><button id="A02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A02'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A02</button></td>
		<td><button id="A03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A03'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A03</button></td>
		<td><button id="A04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A04'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A04</button></td>
		<td><button id="A05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A05'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A05</button></td>
		<td><button id="A06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A06'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A06</button></td>
		<td><button id="A07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A07'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A07</button></td>
		<td><button id="A08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A08'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A08</button></td>
		<td><button id="A09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A09'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A09</button></td>
		<td><button id="A10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A10'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A10</button></td>
		<td rowspan="23" style="background: #99CC99; border: none; min-width: 30px;"></td>
		<td><button id="A11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A11'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A11</button></td>
		<td><button id="A12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A12'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A12</button></td>
		<td><button id="A13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A13'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A13</button></td>
		<td><button id="A14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A14'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A14</button></td>
		<td><button id="A15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A15'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A15</button></td>
		<td><button id="A16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A16'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A16</button></td>
		<td><button id="A17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A17'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A17</button></td>
		<td><button id="A18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A18'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A18</button></td>
		<td><button id="A19" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A19'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A19</button></td>
		<td><button id="A20" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A20'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>A20</button></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<!-- 空道 -->
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr><td colspan="10" class="guodao"></td><td colspan="10" class="guodao"></td></tr>
	
	<!-- B -->
	<tr>
		<td><button id="B01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B01'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B01</button></td>
		<td><button id="B02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B02'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B02</button></td>
		<td><button id="B03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B03'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B03</button></td>
		<td><button id="B04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B04'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B04</button></td>
		<td><button id="B05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B05'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B05</button></td>
		<td><button id="B06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B06'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B06</button></td>
		<td><button id="B07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B07'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B07</button></td>
		<td><button id="B08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B08'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B08</button></td>
		<td><button id="B09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B09'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B09</button></td>
		<td><button id="B10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B10'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B10</button></td>
		<!-- 空道 -->
		<td><button id="B11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B11'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B11</button></td>
		<td><button id="B12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B12'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B12</button></td>
		<td><button id="B13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B13'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B13</button></td>
		<td><button id="B14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B14'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B14</button></td>
		<td><button id="B15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B15'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B15</button></td>
		<td><button id="B16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B16'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B16</button></td>
		<td><button id="B17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B17'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B17</button></td>
		<td><button id="B18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B18'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B18</button></td>
		<td><button id="B19" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B19'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B19</button></td>
		<td><button id="B20" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B20'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>B20</button></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td style="background: #FFF;"></td>
		<td></td>
		<td></td>
		<!-- 空道 -->
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr><td colspan="10" class="guodao"></td><td colspan="10" class="guodao"></td></tr>

	<!-- C -->
	<tr>
		<td><button id="C01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C01'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C01</button></td>
		<td><button id="C02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C02'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C02</button></td>
		<td><button id="C03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C03'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C03</button></td>
		<td><button id="C04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C04'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C04</button></td>
		<td><button id="C05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C05'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C05</button></td>
		<td><button id="C06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C06'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C06</button></td>
		<td><button id="C07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C07'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C07</button></td>
		<td><button id="C08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C08'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C08</button></td>
		<td><button id="C09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C09'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C09</button></td>
		<td><button id="C10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C10'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C10</button></td>
		<!-- 空道 -->
		<td><button id="C11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C11'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C11</button></td>
		<td><button id="C12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C12'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C12</button></td>
		<td><button id="C13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C13'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C13</button></td>
		<td><button id="C14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C14'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C14</button></td>
		<td><button id="C15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C15'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C15</button></td>
		<td><button id="C16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C16'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C16</button></td>
		<td><button id="C17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C17'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C17</button></td>
		<td><button id="C18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C18'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C18</button></td>
		<td><button id="C19" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C19'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C19</button></td>
		<td><button id="C20" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C20'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>C20</button></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>飓风互动</td>
		<td>飓风互动</td>
		<!-- 空道 -->
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	<tr><td colspan="10" class="guodao"></td><td colspan="10" class="guodao"></td></tr>

	<!-- D -->
	<tr>
		<td><button id="D01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D01'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D01</button></td>
		<td><button id="D02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D02'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D02</button></td>
		<td><button id="D03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D03'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D03</button></td>
		<td><button id="D04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D04'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D04</button></td>
		<td><button id="D05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D05'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D05</button></td>
		<td><button id="D06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D06'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D06</button></td>
		<td><button id="D07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D07'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D07</button></td>
		<td><button id="D08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D08'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D08</button></td>
		<td rowspan="2" colspan="2">柱子</td>
		<!-- 空道 -->
		<td><button id="D09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D09'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D09</button></td>
		<td><button id="D10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D10'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D10</button></td>
		<td><button id="D11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D11'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D11</button></td>
		<td><button id="D12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D12'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D12</button></td>
		<td><button id="D13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D13'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D13</button></td>
		<td><button id="D14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D14'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D14</button></td>
		<td><button id="D15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D15'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D15</button></td>
		<td><button id="D16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D16'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D16</button></td>
		<td><button id="D17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D17'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D17</button></td>
		<td><button id="D18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D18'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>D18</button></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<!-- 空道 -->
		<td>公司核心</td>
		<td>飞创数码</td>
		<td>飞创数码</td>
		<td>飞创数码</td>
		<td>飞创数码</td>
		<td>飞创数码</td>
		<td>飞流九天</td>
		<td>飞创数码</td>
		<td>飞创数码</td>
		<td>飞创数码</td>
	</tr>
	<tr><td colspan="10" class="guodao"></td><td colspan="10" class="guodao"></td></tr>

	<!-- E -->
	<tr>
		<td><button id="E01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E01'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E01</button></td>
		<td><button id="E02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E02'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E02</button></td>
		<td><button id="E03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E03'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E03</button></td>
		<td><button id="E04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E04'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E04</button></td>
		<td><button id="E05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E05'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E05</button></td>
		<td><button id="E06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E06'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E06</button></td>
		<td><button id="E07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E07'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E07</button></td>
		<td><button id="E08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E08'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E08</button></td>
		<td><button id="E09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E09'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E09</button></td>
		<td><button id="E10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E10'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E10</button></td>
		<!-- 空道 -->
		<td><button id="E11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E11'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E11</button></td>
		<td><button id="E12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E12'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E12</button></td>
		<td><button id="E13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E13'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E13</button></td>
		<td><button id="E14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E14'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E14</button></td>
		<td><button id="E15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E15'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E15</button></td>
		<td><button id="E16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E16'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E16</button></td>
		<td><button id="E17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E17'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E17</button></td>
		<td><button id="E18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E18'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E18</button></td>
		<td><button id="E19" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E19'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E19</button></td>
		<td><button id="E20" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E20'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>E20</button></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>ODF柜</td>
		<!-- 空道 -->
		<td>飞创数码</td>
		<td>飞创数码</td>
		<td>微众文化</td>
		<td>微众文化</td>
		<td>瑞云浩天</td>
		<td>中清龙图</td>
		<td>中清龙图</td>
		<td>飞创数码</td>
		<td>飞创数码</td>
		<td>飞创数码</td>
	</tr>
	<tr><td colspan="10" class="guodao"></td><td colspan="10" class="guodao"></td></tr>

	<!-- F -->
	<tr>
		<td><button id="F01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F01'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F01</button></td>
		<td><button id="F02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F02'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F02</button></td>
		<td><button id="F03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F03'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F03</button></td>
		<td><button id="F04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F04'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F04</button></td>
		<td><button id="F05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F05'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F05</button></td>
		<td><button id="F06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F06'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F06</button></td>
		<td><button id="F07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F07'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F07</button></td>
		<td><button id="F08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F08'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F08</button></td>
		<td><button id="F09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F09'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F09</button></td>
		<td><button id="F10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F10'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F10</button></td>
		<!-- 空道 -->
		<td><button id="F11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F11'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F11</button></td>
		<td><button id="F12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F12'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F12</button></td>
		<td><button id="F13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F13'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F13</button></td>
		<td><button id="F14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F14'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F14</button></td>
		<td><button id="F15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F15'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F15</button></td>
		<td><button id="F16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F16'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F16</button></td>
		<td><button id="F17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F17'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F17</button></td>
		<td><button id="F18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F18'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F18</button></td>
		<td><button id="F19" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F19'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F19</button></td>
		<td><button id="F20" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F20'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>F20</button></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>ODF柜</td>
		<!-- 空道 -->
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
	</tr>
	<tr><td colspan="10" class="guodao"></td><td colspan="10" class="guodao"></td></tr>

	<!-- G -->
	<tr>
		<td><button id="G01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G01'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G01</button></td>
		<td><button id="G02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G02'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G02</button></td>
		<td><button id="G03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G03'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G03</button></td>
		<td><button id="G04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G04'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G04</button></td>
		<td><button id="G05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G05'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G05</button></td>
		<td><button id="G06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G06'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G06</button></td>
		<td><button id="G07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G07'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G07</button></td>
		<td><button id="G08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G08'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G08</button></td>
		<td><button id="G09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G09'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G09</button></td>
		<td><button id="G10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G10'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G10</button></td>
		<!-- 空道 -->
		<td><button id="G11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G11'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G11</button></td>
		<td><button id="G12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G12'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G12</button></td>
		<td><button id="G13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G13'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G13</button></td>
		<td><button id="G14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G14'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G14</button></td>
		<td><button id="G15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G15'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G15</button></td>
		<td><button id="G16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G16'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G16</button></td>
		<td><button id="G17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G17'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G17</button></td>
		<td><button id="G18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G18'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G18</button></td>
		<td><button id="G19" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G19'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G19</button></td>
		<td><button id="G20" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G20'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>G20</button></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<!-- 空道 -->
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
	</tr>
	<tr><td colspan="10" class="guodao"></td><td colspan="10" class="guodao"></td></tr>

	<!-- H -->
	<tr>
		<td><button id="H01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H01'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H01</button></td>
		<td><button id="H02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H02'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H02</button></td>
		<td><button id="H03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H03'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H03</button></td>
		<td><button id="H04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H04'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H04</button></td>
		<td><button id="H05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H05'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H05</button></td>
		<td><button id="H06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H06'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H06</button></td>
		<td><button id="H07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H07'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H07</button></td>
		<td><button id="H08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H08'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H08</button></td>
		<td><button id="H09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H09'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H09</button></td>
		<td><button id="H10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H10'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H10</button></td>
		<!-- 空道 -->
		<td><button id="H11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H11'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H11</button></td>
		<td><button id="H12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H12'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H12</button></td>
		<td><button id="H13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H13'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H13</button></td>
		<td><button id="H14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H14'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H14</button></td>
		<td><button id="H15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H15'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H15</button></td>
		<td><button id="H16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H16'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H16</button></td>
		<td><button id="H17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H17'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H17</button></td>
		<td><button id="H18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H18'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H18</button></td>
		<td><button id="H19" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H19'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H19</button></td>
		<td><button id="H20" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H20'}">  
	     		   style="background-color:${m.type};" onClick="add('富丰二部','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>H20</button></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td>配线柜</td>
		<td>ODF柜</td>
		<!-- 空道 -->
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td>六间房</td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
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