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

	#dazuSix{
		width: 100%;
		margin-top:30px;
	}

	#dazuSix tbody tr,#dazuSix tbody tr td{
		/* border: 1px black solid; */
		width: auto;
		text-align: center;
	}

	#dazuSix tbody tr td{
		height: 22px;
		line-height:22px;
		min-width: 100px;
		white-space:nowrap;
	}
	
	.wh{
		/* margin:1px; */
		width:100%;
		background-color: #787878;
	}
</style>
<section class="scrollable padder jcGOA-section" id="scrollable">
<section class="tree-fluid m-t-md">
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">亦庄大族六层机房平面图</h1>
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
<table class="table table-striped tab_height first-td-tc" id="dazuSix">
<tbody>
	<tr>
		<!-- <td rowspan="50" colspan="2"></td>
		<td rowspan="18"></td> -->
		<td colspan="11"></td>
		<td colspan="7" rowspan="2" style="background-color: #cc6633">B区</td>
		<td colspan="16"></td>
	</tr>
	<tr>
		<td colspan="11"></td>
		<td colspan="16"></td>
	</tr>
	<tr>
		<td><button id="12-6-B0116" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0116'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0116</button></td>
		<td></td>
		<td><button id="12-6-B0216" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0216'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0216</button></td>
		<td></td>
		<td><button id="12-6-B0316" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0316'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0316</button></td>
		<td></td>
		<td><button id="12-6-B0416" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0416'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0416</button></td>
		<td></td>
		<td><button id="12-6-B0516" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0516'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0516</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0614" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0614'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0614</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0716" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0716'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0716</button></td>
		<td></td>
		<td><button id="12-6-B0816" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0816'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0816</button></td>
		<td></td>
		<td><button id="12-6-B0916" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0916'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0916</button></td>
		<td></td>
		<td><button id="12-6-B1016" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1016'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1016</button></td>
		<td></td>
		<td><button id="12-6-B1116" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1116'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1116</button></td>
		<td></td>
		<td><button id="12-6-B1216" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1216'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1216</button></td>
		<td></td>
		<td><button id="12-6-B1316" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1316'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1316</button></td>
		<td></td>
		<td><button id="12-6-B1416" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1416'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1416</button></td>
		<td></td>
		<td><button id="12-6-B1516" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1516'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1516</button></td>
		<td></td>
		<!-- <td rowspan="1"></td> -->
		<td><button id="12-6-E0116" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0116'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0116</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0115" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0115'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0115</button></td>
		<td></td>
		<td><button id="12-6-B0215" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0215'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0215</button></td>
		<td></td>
		<td><button id="12-6-B0315" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0315'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0315</button></td>
		<td></td>
		<td><button id="12-6-B0415" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0415'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0415</button></td>
		<td></td>
		<td><button id="12-6-B0515" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0515'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0515</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0613" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0613'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0613</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0715" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0715'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0715</button></td>
		<td></td>
		<td><button id="12-6-B0815" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0815'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0815</button></td>
		<td></td>
		<td><button id="12-6-B0915" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0915'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0915</button></td>
		<td></td>
		<td><button id="12-6-B1015" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1015'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1015</button></td>
		<td></td>
		<td><button id="12-6-B1115" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1115'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1115</button></td>
		<td></td>
		<td><button id="12-6-B1215" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1215'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1215</button></td>
		<td></td>
		<td><button id="12-6-B1315" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1315'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1315</button></td>
		<td></td>
		<td><button id="12-6-B1415" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1415'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1415</button></td>
		<td></td>
		<td><button id="12-6-B1515" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1515'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1515</button></td>
		<td></td>
		<!-- <td></td> -->
		<td><button id="12-6-E0115" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0115'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0115</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0114" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0114'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0114</button></td>
		<td></td>
		<td><button id="12-6-B0214" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0214'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0214</button></td>
		<td></td>
		<td><button id="12-6-B0314" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0314'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0314</button></td>
		<td></td>
		<td><button id="12-6-B0414" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0414'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0414</button></td>
		<td></td>
		<td><button id="12-6-B0514" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0514'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0514</button></td>
		<td>燚鑫科技</td>
		<td><button id="12-6-B0612" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0612'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0612</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0714" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0714'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0714</button></td>
		<td></td>
		<td><button id="12-6-B0814" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0814'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0814</button></td>
		<td></td>
		<td><button id="12-6-B0914" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0914'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0914</button></td>
		<td></td>
		<td><button id="12-6-B1014" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1014'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1014</button></td>
		<td></td>
		<td><button id="12-6-B1114" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1114'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1114</button></td>
		<td></td>
		<td><button id="12-6-B1214" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1214'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1214</button></td>
		<td></td>
		<td><button id="12-6-B1314" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1314'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1314</button></td>
		<td></td>
		<td><button id="12-6-B1414" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1414'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1414</button></td>
		<td></td>
		<td><button id="12-6-B1514" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1514'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1514</button></td>
		<td></td>
		<!-- <td></td> -->
		<td><button id="12-6-E0114" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0114'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0114</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0113" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0113'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0113</button></td>
		<td></td>
		<td><button id="12-6-B0213" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0213'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0213</button></td>
		<td></td>
		<td><button id="12-6-B0313" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0313'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0313</button></td>
		<td></td>
		<td><button id="12-6-B0413" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0413'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0413</button></td>
		<td></td>
		<td><button id="12-6-B0513" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0513'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0513</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0611" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0611'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0611</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0713" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0713'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0713</button></td>
		<td></td>
		<td><button id="12-6-B0813" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0813'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0813</button></td>
		<td></td>
		<td><button id="12-6-B0913" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0913'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0913</button></td>
		<td></td>
		<td><button id="12-6-B1013" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1013'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1013</button></td>
		<td></td>
		<td><button id="12-6-B1113" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1113'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1113</button></td>
		<td></td>
		<td><button id="12-6-B1213" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1213'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1213</button></td>
		<td></td>
		<td><button id="12-6-B1313" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1313'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1313</button></td>
		<td></td>
		<td><button id="12-6-B1413" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1413'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1413</button></td>
		<td></td>
		<td><button id="12-6-B1513" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1513'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1513</button></td>
		<td></td>
		<!-- <td></td> -->
		<td><button id="12-6-E0113" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0113'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0113</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0112" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0112'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0112</button></td>
		<td></td>
		<td><button id="12-6-B0212" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0212'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0212</button></td>
		<td></td>
		<td><button id="12-6-B0312" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0312'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0312</button></td>
		<td></td>
		<td><button id="12-6-B0412" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0412'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0412</button></td>
		<td></td>
		<td><button id="12-6-B0512" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0512'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0512</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0610" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0610'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0610</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0712" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0712'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0712</button></td>
		<td></td>
		<td><button id="12-6-B0812" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0812'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0812</button></td>
		<td></td>
		<td><button id="12-6-B0912" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0912'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0912</button></td>
		<td></td>
		<td><button id="12-6-B1012" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1012'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1012</button></td>
		<td></td>
		<td><button id="12-6-B1112" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1112'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1112</button></td>
		<td></td>
		<td><button id="12-6-B1212" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1212'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1212</button></td>
		<td></td>
		<td><button id="12-6-B1312" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1312'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1312</button></td>
		<td></td>
		<td><button id="12-6-B1412" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1412'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1412</button></td>
		<td></td>
		<td><button id="12-6-B1512" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1512'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1512</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0112" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0112'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0112</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0111" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0111'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0111</button></td>
		<td></td>
		<td><button id="12-6-B0211" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0211'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0211</button></td>
		<td></td>
		<td><button id="12-6-B0311" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0311'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0311</button></td>
		<td></td>
		<td><button id="12-6-B0411" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0411'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0411</button></td>
		<td></td>
		<td><button id="12-6-B0511" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0511'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0511</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0609" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0609'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0609</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0711" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0711'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0711</button></td>
		<td></td>
		<td><button id="12-6-B0811" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0811'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0811</button></td>
		<td></td>
		<td><button id="12-6-B0911" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0911'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0911</button></td>
		<td></td>
		<td><button id="12-6-B1011" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1011'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1011</button></td>
		<td></td>
		<td><button id="12-6-B1111" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1111'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1111</button></td>
		<td></td>
		<td><button id="12-6-B1211" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1211'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1211</button></td>
		<td></td>
		<td><button id="12-6-B1311" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1311'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1311</button></td>
		<td></td>
		<td><button id="12-6-B1411" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1411'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1411</button></td>
		<td></td>
		<td><button id="12-6-B1511" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1511'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1511</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0111" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0111'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0111</button></td>
		<td>ODF机柜</td>
	</tr>
	<tr>
		<td><button id="12-6-B0110" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0110'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0110</button></td>
		<td></td>
		<td><button id="12-6-B0210" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0210'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0210</button></td>
		<td></td>
		<td><button id="12-6-B0310" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0310'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0310</button></td>
		<td></td>
		<td><button id="12-6-B0410" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0410'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0410</button></td>
		<td></td>
		<td><button id="12-6-B0510" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0510'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0510</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0608" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0608'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0608</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0710" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0710'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0710</button></td>
		<td></td>
		<td><button id="12-6-B0810" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0810'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0810</button></td>
		<td></td>
		<td><button id="12-6-B0910" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0910'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0910</button></td>
		<td></td>
		<td><button id="12-6-B1010" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1010'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1010</button></td>
		<td></td>
		<td><button id="12-6-B1110" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1110'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1110</button></td>
		<td></td>
		<td><button id="12-6-B1210" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1210'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1210</button></td>
		<td></td>
		<td><button id="12-6-B1310" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1310'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1310</button></td>
		<td></td>
		<td><button id="12-6-B1410" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1410'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1410</button></td>
		<td></td>
		<td><button id="12-6-B1510" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1510'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1510</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0110" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0110'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0110</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0109" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0109'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0109</button></td>
		<td></td>
		<td><button id="12-6-B0209" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0209'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0209</button></td>
		<td></td>
		<td><button id="12-6-B0309" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0309'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0309</button></td>
		<td></td>
		<td><button id="12-6-B0409" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0409'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0409</button></td>
		<td></td>
		<td><button id="12-6-B0509" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0509'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0509</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0607" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0607'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0607</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0709" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0709'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0709</button></td>
		<td></td>
		<td><button id="12-6-B0809" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0809'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0809</button></td>
		<td></td>
		<td><button id="12-6-B0909" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0909'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0909</button></td>
		<td></td>
		<td><button id="12-6-B1009" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1009'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1009</button></td>
		<td></td>
		<td><button id="12-6-B1109" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1109'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1109</button></td>
		<td></td>
		<td><button id="12-6-B1209" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1209'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1209</button></td>
		<td></td>
		<td><button id="12-6-B1309" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1309'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1309</button></td>
		<td></td>
		<td><button id="12-6-B1409" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1409'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1409</button></td>
		<td></td>
		<td><button id="12-6-B1509" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1509'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1509</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0109" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0109'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0109</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0108" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0108'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0108</button></td>
		<td></td>
		<td><button id="12-6-B0208" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0208'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0208</button></td>
		<td></td>
		<td><button id="12-6-B0308" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0308'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0308</button></td>
		<td></td>
		<td><button id="12-6-B0408" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0408'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0408</button></td>
		<td></td>
		<td><button id="12-6-B0508" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0508'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0508</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0606" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0606'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0606</button></td>
		<td>博睿宏远</td>
		<td><button id="12-6-B0708" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0708'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0708</button></td>
		<td></td>
		<td><button id="12-6-B0808" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0808'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0808</button></td>
		<td></td>
		<td><button id="12-6-B0908" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0908'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0908</button></td>
		<td></td>
		<td><button id="12-6-B1008" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1008'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1008</button></td>
		<td></td>
		<td><button id="12-6-B1108" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1108'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1108</button></td>
		<td></td>
		<td><button id="12-6-B1208" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1208'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1208</button></td>
		<td></td>
		<td><button id="12-6-B1308" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1308'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1308</button></td>
		<td></td>
		<td><button id="12-6-B1408" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1408'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1408</button></td>
		<td></td>
		<td><button id="12-6-B1508" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1508'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1508</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0108" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0108'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0108</button></td>
		<td>森华易腾</td>
	</tr>
	<tr>
		<td><button id="12-6-B0107" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0107'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0107</button></td>
		<td></td>
		<td><button id="12-6-B0207" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0207'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0207</button></td>
		<td></td>
		<td><button id="12-6-B0307" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0307'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0307</button></td>
		<td></td>
		<td><button id="12-6-B0407" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0407'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0407</button></td>
		<td></td>
		<td><button id="12-6-B0507" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0507'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0507</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0605" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0605'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0605</button></td>
		<td></td>
		<td><button id="12-6-B0707" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0707'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0707</button></td>
		<td></td>
		<td><button id="12-6-B0807" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0807'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0807</button></td>
		<td></td>
		<td><button id="12-6-B0907" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0907'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0907</button></td>
		<td></td>
		<td><button id="12-6-B1007" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1007'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1007</button></td>
		<td></td>
		<td><button id="12-6-B1107" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1107'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1107</button></td>
		<td></td>
		<td><button id="12-6-B1207" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1207'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1207</button></td>
		<td></td>
		<td><button id="12-6-B1307" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1307'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1307</button></td>
		<td></td>
		<td><button id="12-6-B1407" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1407'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1407</button></td>
		<td></td>
		<td><button id="12-6-B1507" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1507'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1507</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0107" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0107'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0107</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0106" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0106'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0106</button></td>
		<td></td>
		<td><button id="12-6-B0206" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0206'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0206</button></td>
		<td></td>
		<td><button id="12-6-B0306" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0306'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0306</button></td>
		<td></td>
		<td><button id="12-6-B0406" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0406'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0406</button></td>
		<td></td>
		<td><button id="12-6-B0506" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0506'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0506</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0604" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0604'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0604</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0706" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0706'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0706</button></td>
		<td></td>
		<td><button id="12-6-B0806" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0806'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0806</button></td>
		<td></td>
		<td><button id="12-6-B0906" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0906'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0906</button></td>
		<td></td>
		<td><button id="12-6-B1006" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1006'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1006</button></td>
		<td></td>
		<td><button id="12-6-B1106" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1106'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1106</button></td>
		<td></td>
		<td><button id="12-6-B1206" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1206'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1206</button></td>
		<td></td>
		<td><button id="12-6-B1306" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1306'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1306</button></td>
		<td></td>
		<td><button id="12-6-B1406" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1406'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1406</button></td>
		<td></td>
		<td><button id="12-6-B1506" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1506'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1506</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0106" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0106'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0106</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0105" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0105'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0105</button></td>
		<td></td>
		<td><button id="12-6-B0205" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0205'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0205</button></td>
		<td></td>
		<td rowspan="2">立柱（无用柜）</td>
		<td></td>
		<td><button id="12-6-B0405" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0405'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0405</button></td>
		<td></td>
		<td><button id="12-6-B0505" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0505'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0505</button></td>
		<td>完美世界</td>
		<td rowspan="2">立柱（无用柜）</td>
		<td></td>
		<td><button id="12-6-B0705" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0705'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0705</button></td>
		<td></td>
		<td><button id="12-6-B0805" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0805'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0805</button></td>
		<td></td>
		<td rowspan="2">立柱（无用柜）</td>
		<td></td>
		<td><button id="12-6-B1005" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1005'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1005</button></td>
		<td></td>
		<td><button id="12-6-B1105" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1105'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1105</button></td>
		<td></td>
		<td><button id="12-6-B1205" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1205'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1205</button></td>
		<td></td>
		<td rowspan="2">立柱（无用柜）</td>
		<td></td>
		<td><button id="12-6-B1405" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1405'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1405</button></td>
		<td></td>
		<td><button id="12-6-B1505" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1505'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1505</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0105" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0105'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0105</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0104" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0104'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0104</button></td>
		<td></td>
		<td><button id="12-6-B0204" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0204'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0204</button></td>
		<td></td>
		<td></td>
		<td><button id="12-6-B0404" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0404'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0404</button></td>
		<td></td>
		<td><button id="12-6-B0504" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0504'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0504</button></td>
		<td>完美世界</td>
		<td></td>
		<td><button id="12-6-B0704" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0704'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0704</button></td>
		<td></td>
		<td><button id="12-6-B0804" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0804'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0804</button></td>
		<td></td>
		<td></td>
		<td><button id="12-6-B1004" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1004'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1004</button></td>
		<td></td>
		<td><button id="12-6-B1104" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1104'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1104</button></td>
		<td></td>
		<td><button id="12-6-B1204" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1204'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1204</button></td>
		<td></td>
		<td></td>
		<td><button id="12-6-B1404" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1404'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1404</button></td>
		<td></td>
		<td><button id="12-6-B1504" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1504'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1504</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0104" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0104'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0104</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0103" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0103'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0103</button></td>
		<td></td>
		<td><button id="12-6-B0203" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0203'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0203</button></td>
		<td></td>
		<td><button id="12-6-B0303" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0303'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0303</button></td>
		<td></td>
		<td><button id="12-6-B0403" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0403'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0403</button></td>
		<td></td>
		<td><button id="12-6-B0503" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0503'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0503</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0603" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0603'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0603</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0703" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0703'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0703</button></td>
		<td></td>
		<td><button id="12-6-B0803" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0803'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0803</button></td>
		<td></td>
		<td><button id="12-6-B0903" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0903'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0903</button></td>
		<td></td>
		<td><button id="12-6-B1003" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1003'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1003</button></td>
		<td></td>
		<td><button id="12-6-B1103" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1103'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1103</button></td>
		<td></td>
		<td><button id="12-6-B1203" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1203'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1203</button></td>
		<td></td>
		<td><button id="12-6-B1303" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1303'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1303</button></td>
		<td></td>
		<td><button id="12-6-B1403" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1403'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1403</button></td>
		<td></td>
		<td><button id="12-6-B1503" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1503'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1503</button></td>
		<td>完美世界</td>
		<!-- <td></td> -->
		<td><button id="12-6-E0103" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0103'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0103</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-B0102" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0102'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0102</button></td>
		<td></td>
		<td><button id="12-6-B0202" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0202'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0202</button></td>
		<td></td>
		<td><button id="12-6-B0302" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0302'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0302</button></td>
		<td></td>
		<td><button id="12-6-B0402" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0402'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0402</button></td>
		<td></td>
		<td><button id="12-6-B0502" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0502'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0502</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0602" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0602'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0602</button></td>
		<td>完美世界</td>
		<td><button id="12-6-B0702" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0702'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0702</button></td>
		<td></td>
		<td><button id="12-6-B0802" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0802'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0802</button></td>
		<td></td>
		<td><button id="12-6-B0902" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0902'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0902</button></td>
		<td></td>
		<td><button id="12-6-B1002" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1002'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1002</button></td>
		<td></td>
		<td><button id="12-6-B1102" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1102'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1102</button></td>
		<td></td>
		<td><button id="12-6-B1202" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1202'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1202</button></td>
		<td></td>
		<td><button id="12-6-B1302" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1302'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1302</button></td>
		<td></td>
		<td><button id="12-6-B1402" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1402'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1402</button></td>
		<td></td>
		<td><button id="12-6-B1502" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1502'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1502</button></td>
		<td></td>
		<<!-- td></td> -->
		<td><button id="12-6-E0102" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0102'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0102</button></td>
		<td>外线ODF===看丹</td>
	</tr>
	<tr>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-B0201" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0201'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0201</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-B0401" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0401'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0401</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-B0601" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0601'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0601</button></td>
		<td>完美世界</td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-B0801" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B0801'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B0801</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-B1001" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1001'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1001</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-B1201" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1201'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1201</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-B1401" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-B1401'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-B1401</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<!-- <td></td> -->
		<td><button id="12-6-E0101" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-E0101'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-E0101</button></td>
		<td>外线ODF===揽信</td>
	</tr>
	<tr>
		<td>B1</td>
		<td></td>
		<td>B2</td>
		<td></td>
		<td>B3</td>
		<td></td>
		<td>B4</td>
		<td></td>
		<td>B5</td>
		<td></td>
		<td>B6</td>
		<td></td>
		<td>B7</td>
		<td></td>
		<td>B8</td>
		<td></td>
		<td>B9</td>
		<td></td>
		<td>B10</td>
		<td></td>
		<td>B11</td>
		<td></td>
		<td>B12</td>
		<td></td>
		<td>B13</td>
		<td></td>
		<td>B14</td>
		<td></td>
		<td>B15</td>
		<td></td>
		<td></td>
		<td>E01</td>
		<td></td>
	</tr>
	<tr>
		<td colspan="34" class="guodao">维护通道</td>
	</tr>
	<!-- 排分隔 -->	
	<tr>
		<td colspan="11"></td>
		<td colspan="7" rowspan="2" style="background-color: #cc6633;">A区</td>
		<td colspan="10"></td>
	</tr>
	<tr>
		<td colspan="11"></td>
		<td colspan="10"></td>
	</tr>
	<tr>
		<td>A1</td>
		<td></td>
		<td>A2</td>
		<td></td>
		<td>A3</td>
		<td></td>
		<td>A4</td>
		<td></td>
		<td>A5</td>
		<td></td>
		<td>A6</td>
		<td></td>
		<td>A7</td>
		<td></td>
		<td>A8</td>
		<td></td>
		<td>A9</td>
		<td></td>
		<td>A10</td>
		<td></td>
		<td>A11</td>
		<td></td>
		<td>A12</td>
		<td></td>
		<td>A13</td>
		<td></td>
		<td>A14</td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0101" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0101'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0101</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-A0301" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0301'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0301</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-A0501" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0501'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0501</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-A0701" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0701'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0701</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-A0901" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0901'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0901</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-A1101" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1101'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1101</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-A1301" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1301'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1301</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0102" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0102'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0102</button></td>
		<td></td>
		<td><button id="12-6-A0202" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0202'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0202</button></td>
		<td></td>
		<td><button id="12-6-A0302" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0302'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0302</button></td>
		<td></td>
		<td><button id="12-6-A0402" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0402'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0402</button></td>
		<td></td>
		<td><button id="12-6-A0502" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0502'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0502</button></td>
		<td></td>
		<td><button id="12-6-A0602" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0602'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0602</button></td>
		<td></td>
		<td><button id="12-6-A0702" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0702'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0702</button></td>
		<td></td>
		<td><button id="12-6-A0802" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0802'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0802</button></td>
		<td></td>
		<td><button id="12-6-A0902" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0902'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0902</button></td>
		<td></td>
		<td><button id="12-6-A1002" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1002'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1002</button></td>
		<td></td>
		<td><button id="12-6-A1102" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1102'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1102</button></td>
		<td></td>
		<td><button id="12-6-A1202" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1202'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1202</button></td>
		<td></td>
		<td><button id="12-6-A1302" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1302'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1302</button></td>
		<td></td>
		<td><button id="12-6-A1402" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1402'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1402</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0103" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0103'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0103</button></td>
		<td></td>
		<td><button id="12-6-A0203" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0203'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0203</button></td>
		<td></td>
		<td><button id="12-6-A0303" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0303'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0303</button></td>
		<td></td>
		<td><button id="12-6-A0403" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0403'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0403</button></td>
		<td></td>
		<td><button id="12-6-A0503" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0503'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0503</button></td>
		<td></td>
		<td><button id="12-6-A0603" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0603'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0603</button></td>
		<td></td>
		<td><button id="12-6-A0703" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0703'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0703</button></td>
		<td></td>
		<td><button id="12-6-A0803" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0803'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0803</button></td>
		<td></td>
		<td><button id="12-6-A0903" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0903'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0903</button></td>
		<td></td>
		<td><button id="12-6-A1003" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1003'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1003</button></td>
		<td></td>
		<td><button id="12-6-A1103" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1103'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1103</button></td>
		<td></td>
		<td><button id="12-6-A1203" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1203'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1203</button></td>
		<td></td>
		<td><button id="12-6-A1303" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1303'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1303</button></td>
		<td></td>
		<td><button id="12-6-A1403" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1403'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1403</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0104" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0104'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0104</button></td>
		<td></td>
		<td><button id="12-6-A0204" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0204'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0204</button></td>
		<td></td>
		<td><button id="12-6-A0304" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0304'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0304</button></td>
		<td></td>
		<td><button id="12-6-A0404" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0404'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0404</button></td>
		<td></td>
		<td><button id="12-6-A0504" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0504'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0404</button></td>
		<td></td>
		<td><button id="12-6-A0604" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0604'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0604</button></td>
		<td></td>
		<td><button id="12-6-A0704" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0704'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0704</button></td>
		<td></td>
		<td><button id="12-6-A0804" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0804'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0804</button></td>
		<td></td>
		<td><button id="12-6-A0904" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0904'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0904</button></td>
		<td></td>
		<td><button id="12-6-A1004" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1004'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1004</button></td>
		<td></td>
		<td><button id="12-6-A1104" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1104'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1104</button></td>
		<td></td>
		<td><button id="12-6-A1204" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1204'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1204</button></td>
		<td></td>
		<td><button id="12-6-A1304" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1304'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1304</button></td>
		<td></td>
		<td><button id="12-6-A1404" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1404'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1404</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0105" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0105'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0105</button></td>
		<td></td>
		<td><button id="12-6-A0205" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0205'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0205</button></td>
		<td></td>
		<td><button id="12-6-A0305" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0305'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0305</button></td>
		<td></td>
		<td><button id="12-6-A0405" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0405'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0405</button></td>
		<td></td>
		<td><button id="12-6-A0505" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0505'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0505</button></td>
		<td></td>
		<td><button id="12-6-A0605" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0605'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0605</button></td>
		<td></td>
		<td><button id="12-6-A0705" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0705'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0705</button></td>
		<td></td>
		<td><button id="12-6-A0805" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0805'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0805</button></td>
		<td></td>
		<td><button id="12-6-A0905" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0905'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0905</button></td>
		<td></td>
		<td><button id="12-6-A1005" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1005'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1005</button></td>
		<td></td>
		<td><button id="12-6-A1105" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1105'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1105</button></td>
		<td></td>
		<td><button id="12-6-A1205" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1205'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1205</button></td>
		<td></td>
		<td><button id="12-6-A1305" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1305'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1305</button></td>
		<td></td>
		<td><button id="12-6-A1405" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1405'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1405</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0106" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0106'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0106</button></td>
		<td></td>
		<td><button id="12-6-A0206" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0206'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0206</button></td>
		<td></td>
		<td><button id="12-6-A0306" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0306'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0306</button></td>
		<td></td>
		<td><button id="12-6-A0406" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0406'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0406</button></td>
		<td></td>
		<td><button id="12-6-A0506" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0506'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0506</button></td>
		<td></td>
		<td><button id="12-6-A0606" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0606'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0606</button></td>
		<td></td>
		<td><button id="12-6-A0706" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0706'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0706</button></td>
		<td></td>
		<td><button id="12-6-A0806" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0806'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0806</button></td>
		<td></td>
		<td><button id="12-6-A0906" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0906'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0906</button></td>
		<td></td>
		<td><button id="12-6-A1006" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1006'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1006</button></td>
		<td></td>
		<td><button id="12-6-A1106" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1106'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1106</button></td>
		<td></td>
		<td><button id="12-6-A1206" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1206'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1206</button></td>
		<td></td>
		<td><button id="12-6-A01306" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1306'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1306</button></td>
		<td></td>
		<td><button id="12-6-A1406" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1406'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1406</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0107" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0107'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0107</button></td>
		<td></td>
		<td><button id="12-6-A0207" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0207'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0207</button></td>
		<td></td>
		<td><button id="12-6-A0307" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0307'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0307</button></td>
		<td></td>
		<td><button id="12-6-A0407" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0407'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0407</button></td>
		<td></td>
		<td><button id="12-6-A0507" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0507'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0507</button></td>
		<td></td>
		<td><button id="12-6-A0607" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0607'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0607</button></td>
		<td></td>
		<td><button id="12-6-A0707" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0707'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0707</button></td>
		<td></td>
		<td><button id="12-6-A0807" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0807'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0807</button></td>
		<td></td>
		<td><button id="12-6-A0907" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0907'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0907</button></td>
		<td></td>
		<td><button id="12-6-A1007" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1007'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1007</button></td>
		<td></td>
		<td><button id="12-6-A1107" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1107'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1107</button></td>
		<td></td>
		<td><button id="12-6-A1207" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1207'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1207</button></td>
		<td></td>
		<td><button id="12-6-A1307" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1307'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1307</button></td>
		<td></td>
		<td><button id="12-6-A1407" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1407'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1407</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0108" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0108'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0108</button></td>
		<td></td>
		<td><button id="12-6-A0208" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0208'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0208</button></td>
		<td></td>
		<td><button id="12-6-A0308" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0308'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0308</button></td>
		<td></td>
		<td><button id="12-6-A0408" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0408'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0408</button></td>
		<td></td>
		<td><button id="12-6-A0508" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0508'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0508</button></td>
		<td></td>
		<td><button id="12-6-A0608" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0608'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0608</button></td>
		<td></td>
		<td><button id="12-6-A0708" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0708'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0708</button></td>
		<td></td>
		<td><button id="12-6-A0808" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0808'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0808</button></td>
		<td></td>
		<td><button id="12-6-A0908" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0908'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0908</button></td>
		<td></td>
		<td><button id="12-6-A1008" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1008'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1008</button></td>
		<td></td>
		<td><button id="12-6-A1108" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1108'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1108</button></td>
		<td></td>
		<td><button id="12-6-A1208" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1208'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1208</button></td>
		<td></td>
		<td><button id="12-6-A1308" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1308'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1308</button></td>
		<td></td>
		<td><button id="12-6-A1408" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1408'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1408</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0109" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0109'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0109</button></td>
		<td></td>
		<td><button id="12-6-A0209" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0209'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0209</button></td>
		<td></td>
		<td><button id="12-6-A0309" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0309'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0309</button></td>
		<td></td>
		<td><button id="12-6-A0409" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0409'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0409</button></td>
		<td></td>
		<td><button id="12-6-A0509" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0509'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0509</button></td>
		<td></td>
		<td><button id="12-6-A0609" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0609'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0609</button></td>
		<td></td>
		<td><button id="12-6-A0709" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0709'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0709</button></td>
		<td></td>
		<td><button id="12-6-A0809" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0809'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0809</button></td>
		<td></td>
		<td><button id="12-6-A0909" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0909'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0909</button></td>
		<td></td>
		<td><button id="12-6-A1009" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1009'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1009</button></td>
		<td></td>
		<td><button id="12-6-A1109" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1109'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1109</button></td>
		<td></td>
		<td><button id="12-6-A1209" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1209'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1209</button></td>
		<td></td>
		<td><button id="12-6-A1309" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1309'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1309</button></td>
		<td></td>
		<td><button id="12-6-A1409" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1409'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1409</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0110" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0110'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0110</button></td>
		<td></td>
		<td><button id="12-6-A0210" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0210'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0210</button></td>
		<td></td>
		<td><button id="12-6-A0310" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0310'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0310</button></td>
		<td></td>
		<td><button id="12-6-A0410" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0410'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0410</button></td>
		<td></td>
		<td><button id="12-6-A0510" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0510'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0510</button></td>
		<td></td>
		<td><button id="12-6-A0610" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0610'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0610</button></td>
		<td></td>
		<td><button id="12-6-A0710" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0710'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0710</button></td>
		<td></td>
		<td><button id="12-6-A0810" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0810'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0810</button></td>
		<td></td>
		<td><button id="12-6-A0910" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0910'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0910</button></td>
		<td></td>
		<td><button id="12-6-A1010" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1010'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1010</button></td>
		<td></td>
		<td><button id="12-6-A1110" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1110'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1110</button></td>
		<td></td>
		<td><button id="12-6-A1210" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1210'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1210</button></td>
		<td></td>
		<td><button id="12-6-A1310" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1310'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1310</button></td>
		<td></td>
		<td><button id="12-6-A1410" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1410'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1410</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0111" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0111'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0111</button></td>
		<td></td>
		<td><button id="12-6-A0211" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0211'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0211</button></td>
		<td></td>
		<td><button id="12-6-A0311" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0311'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0311</button></td>
		<td></td>
		<td><button id="12-6-A0411" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0411'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0411</button></td>
		<td></td>
		<td><button id="12-6-A0511" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0511'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0511</button></td>
		<td></td>
		<td><button id="12-6-A0611" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0611'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0611</button></td>
		<td></td>
		<td><button id="12-6-A0711" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0711'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0711</button></td>
		<td></td>
		<td><button id="12-6-A0811" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0811'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0811</button></td>
		<td></td>
		<td><button id="12-6-A0911" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0911'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0911</button></td>
		<td></td>
		<td><button id="12-6-A1011" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1011'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1011</button></td>
		<td></td>
		<td><button id="12-6-A1111" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1111'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1111</button></td>
		<td></td>
		<td><button id="12-6-A1211" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1211'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1211</button></td>
		<td></td>
		<td><button id="12-6-A1311" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1311'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1311</button></td>
		<td></td>
		<td><button id="12-6-A1411" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1411'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1411</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0112" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0112'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0112</button></td>
		<td></td>
		<td><button id="12-6-A0212" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0212'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0212</button></td>
		<td></td>
		<td rowspan="2">立柱（无用柜）</td>
		<td></td>
		<td><button id="12-6-A0412" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0412'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0412</button></td>
		<td></td>
		<td><button id="12-6-A0512" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0512'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0512</button></td>
		<td></td>
		<td rowspan="2">立柱（无用柜）</td>
		<td></td>
		<td><button id="12-6-A0712" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0712'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0712</button></td>
		<td></td>
		<td><button id="12-6-A0812" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0812'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0812</button></td>
		<td></td>
		<td rowspan="2">立柱（无用柜）</td>
		<td></td>
		<td><button id="12-6-A1012" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1012'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1012</button></td>
		<td></td>
		<td><button id="12-6-A1112" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1112'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1112</button></td>
		<td></td>
		<td><button id="12-6-A1212" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1212'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1212</button></td>
		<td></td>
		<td rowspan="2">立柱（无用柜）</td>
		<td></td>
		<td><button id="12-6-A1412" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1412'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1412</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0113" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0113'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0113</button></td>
		<td></td>
		<td><button id="12-6-A0213" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0213'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0213</button></td>
		<td></td>
		<td></td>
		<td><button id="12-6-A0413" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0413'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0413</button></td>
		<td></td>
		<td><button id="12-6-A0513" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0513'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0513</button></td>
		<td></td>
		<td></td>
		<td><button id="12-6-A0713" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0713'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0713</button></td>
		<td></td>
		<td><button id="12-6-A0813" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0813'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0813</button></td>
		<td></td>
		<td></td>
		<td><button id="12-6-A1013" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1013'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1013</button></td>
		<td></td>
		<td><button id="12-6-A1113" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1113'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1113</button></td>
		<td></td>
		<td><button id="12-6-A1213" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1213'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1213</button></td>
		<td></td>
		<td></td>
		<td><button id="12-6-A1413" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1413'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1413</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0114" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0114'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0114</button></td>
		<td></td>
		<td><button id="12-6-A0214" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0214'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0214</button></td>
		<td></td>
		<td><button id="12-6-A0312" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0312'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0312</button></td>
		<td></td>
		<td><button id="12-6-A0414" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0414'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0414</button></td>
		<td></td>
		<td><button id="12-6-A0514" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0514'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0514</button></td>
		<td></td>
		<td><button id="12-6-A0612" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0612'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0612</button></td>
		<td></td>
		<td><button id="12-6-A0714" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0714'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0714</button></td>
		<td></td>
		<td><button id="12-6-A0814" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0814'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0814</button></td>
		<td></td>
		<td><button id="12-6-A0912" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0912'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0912</button></td>
		<td></td>
		<td><button id="12-6-A1014" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1014'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1014</button></td>
		<td></td>
		<td><button id="12-6-A1114" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1114'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1114</button></td>
		<td></td>
		<td><button id="12-6-A1214" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1214'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1214</button></td>
		<td></td>
		<td><button id="12-6-A1312" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1312'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1312</button></td>
		<td></td>
		<td><button id="12-6-A1414" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1414'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1414</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0115" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0115'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0115</button></td>
		<td></td>
		<td><button id="12-6-A0215" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0215'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0215</button></td>
		<td></td>
		<td><button id="12-6-A0313" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0313'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0313</button></td>
		<td></td>
		<td><button id="12-6-A0415" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0415'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0415</button></td>
		<td></td>
		<td><button id="12-6-A0515" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0515'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0515</button></td>
		<td></td>
		<td><button id="12-6-A0613" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0613'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0613</button></td>
		<td></td>
		<td><button id="12-6-A0715" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0715'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0715</button></td>
		<td></td>
		<td><button id="12-6-A0815" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0815'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0815</button></td>
		<td></td>
		<td><button id="12-6-A0913" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0913'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0913</button></td>
		<td></td>
		<td><button id="12-6-A1015" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1015'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1015</button></td>
		<td></td>
		<td><button id="12-6-A1115" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1115'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1115</button></td>
		<td></td>
		<td><button id="12-6-A1215" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1215'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1215</button></td>
		<td></td>
		<td><button id="12-6-A1313" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1313'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1313</button></td>
		<td></td>
		<td><button id="12-6-A1415" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1415'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1415</button></td>
		<td></td>
	</tr>
	<tr>
		<td><button id="12-6-A0116" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0116'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0116</button></td>
		<td></td>
		<td><button id="12-6-A0216" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0216'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0216</button></td>
		<td></td>
		<td>列头柜</td>
		<td></td>
		<td><button id="12-6-A0416" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0416'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0416</button></td>
		<td></td>
		<td><button id="12-6-A0516" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0516'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0516</button></td>
		<td></td>
		<td><button id="12-6-A0614" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0614'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0614</button></td>
		<td></td>
		<td><button id="12-6-A0716" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0716'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0716</button></td>
		<td></td>
		<td><button id="12-6-A0816" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0816'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0816</button></td>
		<td></td>
		<td><button id="12-6-A0916" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A0916'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A0916</button></td>
		<td></td>
		<td><button id="12-6-A1016" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1016'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1016</button></td>
		<td></td>
		<td><button id="12-6-A1116" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1116'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1116</button></td>
		<td></td>
		<td><button id="12-6-A1216" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1216'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1216</button></td>
		<td></td>
		<td><button id="12-6-A1314" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1314'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1314</button></td>
		<td></td>
		<td><button id="12-6-A1416" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '12-6-A1416'}">  
	     		   style="background-color:${m.type};" onClick="add('大族6层','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>12-6-A1416</button></td>
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