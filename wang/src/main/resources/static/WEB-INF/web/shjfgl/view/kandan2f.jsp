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

	#kandan2f{
		width: 100%;
		margin-top:30px;
	}

	#kandan2f tbody tr,#kandan2f tbody tr td{
		/* border: 1px black solid; */
		width: auto;
		text-align: center;
	}

	#kandan2f tbody tr td{
		height: 22px;
		line-height:22px;
		min-width: 100px;
		white-space:nowrap;
	}
	
	#kandan2f .guodao{
		background: #FFFFDD;
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
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">看丹二层机房平面图</h1>
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
<table class="table table-striped tab_height first-td-tc" id="kandan2f">
<tbody>
	
	<!-- J -->
	<tr>
		<td><button id="J18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J18'}">  
	     		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J18</button></td>
		<td><button id="J17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J17'}">  
	     		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J17</button></td>
		<td><button id="J16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J16'}">  
	     		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>J16</button></td>
		<td><button id="J15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J15'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J15</button></td>
		<td><button id="J14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J14'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J14</button></td>
		<td><button id="J13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J13'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J13</button></td>
		<td><button id="J12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J12'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J12</button></td>

		<!-- 列分隔 -->
		<td colspan="2" rowspan="21" style="border: none;"></td>

		<td><button id="J11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J11'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J11</button></td>
		<td><button id="J10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J10</button></td>
		<td><button id="J09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J09</button></td>
		<td><button id="J08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J08</button></td>
		<td><button id="J07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J07</button></td>
		<td><button id="J06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J06</button></td>
		<td><button id="J05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J05</button></td>
		<td><button id="J04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J04</button></td>
		<td><button id="J03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J03</button></td>
		<td><button id="J02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J02</button></td>
		<td><button id="J01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'J01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>J01</button></td>
		<td>电源柜</td>
	</tr>	
	<tr>
		<td>昆仑万维</td>
		<td>迎创领航</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>广州爱游4399</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>

		<!-- 列分隔 -->

		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td>昆仑万维</td>
		<td></td>
	</tr>
	
	<!-- 排分隔 I-->	
	<tr>
		<td><button id="I18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I18'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I18</button></td>
		<td><button id="I17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I17'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I17</button></td>
		<td><button id="I16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I16'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I16</button></td>
		<td><button id="I15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I15'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I15</button></td>
		<td><button id="I14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I14'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I14</button></td>
		<td><button id="I13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I13'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I13</button></td>
		<td><button id="I12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I12'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I12</button></td>

		<!-- 列分隔 -->

		<td><button id="I11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I11'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I11</button></td>
		<td><button id="I10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I10</button></td>
		<td><button id="I09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I09</button></td>
		<td><button id="I08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I08</button></td>
		<td><button id="I07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I07</button></td>
		<td><button id="I06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I06</button></td>
		<td><button id="I05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I05</button></td>
		<td><button id="I04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I04</button></td>
		<td><button id="I03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I03</button></td>
		<td><button id="I02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I02</button></td>
		<td><button id="I01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'I01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>I01</button></td>
		<td>电源柜</td>
	</tr>
	<tr>
		<td>兴华晨辉</td>
		<td>广州爱游4399</td>
		<td>昆仑万维</td>
		<td>广州爱游4399</td>
		<td>广州爱游4399</td>
		<td>精灵在线</td>
		<td>金证荣联</td>

		<!-- 列分隔 -->

		<td>明盛时空</td>
		<td>明盛时空</td>
		<td>手游达趣</td>
		<td>世纪超星</td>
		<td>世纪超星（读秀）</td>
		<td>世纪超星（读秀）</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td>精灵在线</td>
		<td>优刻得</td>
		<td>优刻得</td>
		<td></td>
	</tr>

	<!-- 排分隔 H-->	
	<tr>
		<td><button id="H16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H16'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H16</button></td>
		<td><button id="H15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H15'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H15</button></td>
		<td><button id="H14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H14'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H14</button></td>
		<td><button id="H13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H13'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H13</button></td>
		<td><button id="H12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H12'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H12</button></td>
		<td rowspan="2">柱子</td>
		<td><button id="H11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H11'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H11</button></td>
		
		<!-- 列分隔 -->

		<td><button id="H10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H10</button></td>
		<td><button id="H09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H09</button></td>
		<td><button id="H08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H08</button></td>
		<td><button id="H07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H07</button></td>
		<td><button id="H06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H06</button></td>
		<td><button id="H05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H05</button></td>
		<td rowspan="2">柱子</td>
		<td><button id="H04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H04</button></td>
		<td><button id="H03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H03</button></td>
		<td><button id="H02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H02</button></td>
		<td><button id="H01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'H01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>H01</button></td>
		<td>电源柜</td>
	</tr>
	<tr>
		<td>明盛时空</td>
		<td>手游达趣</td>
		<td>世纪超星</td>
		<td>广州菲音</td>
		<td>广州菲音</td>
		<!-- 柱子 -->
		<td>焱石互动</td>

		<!-- 列分隔 -->
		
		<td>辽宁乐股</td>
		<td>辽宁乐股</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td>世纪超星（读秀）</td>
		<td>世纪超星</td>
		<!-- 柱子 -->
		<td>网路时代</td>
		<td>精灵在线</td>
		<td>散户</td>
		<td>散户</td>
		<td></td>
	</tr>
	<!-- 排分隔 G-->	
	<tr>
		<td><button id="G18" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G18'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G18</button></td>
		<td><button id="G17" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G17'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G17</button></td>
		<td><button id="G16" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G16'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G16</button></td>
		<td><button id="G15" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G15'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G15</button></td>
		<td><button id="G14" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G14'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G14</button></td>
		<td><button id="G13" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G13'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G13</button></td>
		<td><button id="G12" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G12'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G12</button></td>

		<!-- 列分隔 -->

		<td><button id="G11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G11'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G11</button></td>
		<td><button id="G10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G10</button></td>
		<td><button id="G09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G09</button></td>
		<td><button id="G08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G08</button></td>
		<td><button id="G07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G07</button></td>
		<td><button id="G06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G06</button></td>
		<td><button id="G05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G05</button></td>
		<td><button id="G04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G04</button></td>
		<td><button id="G03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G03</button></td>
		<td><button id="G02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G02</button></td>
		<td><button id="G01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'G01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>G01</button></td>
		<td>电源柜</td>
	</tr>
	<tr>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td>广州菲音</td>
		<td>广州爱游4399</td>
		<td>广州菲音</td>
		<td>广州菲音</td>
		<td>广州菲音</td>

		<!-- 列分隔 -->

		<td>厦门游家4399</td>
		<td>上海游族</td>
		<td>优刻得</td>
		<td>世纪超星(上海蔚秀)</td>
		<td>世纪超星</td>
		<td>顽石互动</td>
		<td>凤凰学易</td>
		<td>凤凰学易</td>
		<td>广州爱游4399</td>
		<td>广州爱游4399</td>
		<td>星空合众</td>
		<td></td>
	</tr>

	<tr><td colspan="7" class="guodao"></td><td colspan="12" class="guodao"></td></tr>

	<!-- 排分隔 F-->	
	<tr>
		<!-- 留白 -->
		<td colspan="7" rowspan="12" style="border: none;"></td>
		<td rowspan="12" style="border: none;"></td>
		
		<td><button id="F11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F11'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F11</button></td>
		<td><button id="F10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F10</button></td>
		<td><button id="F09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F09</button></td>
		<td><button id="F08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F08</button></td>
		<td><button id="F07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F07</button></td>
		<td><button id="F06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F06</button></td>
		<td><button id="F05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F05</button></td>
		<td><button id="F04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F04</button></td>
		<td><button id="F03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F03</button></td>
		<td><button id="F02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F02</button></td>
		<td><button id="F01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'F01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>F01</button></td>
	</tr>
	<tr>
		<!-- 留白 -->

		<td>迎创领航</td>
		<td>顽石互动</td>
		<td>炫果壳</td>
		<td>优刻得</td>
		<td>优刻得</td>
		<td>优刻得</td>
		<td>优刻得</td>
		<td>优刻得</td>
		<td>优刻得</td>
		<td>优刻得</td>
		<td>优刻得</td>
	</tr>
	<!-- 排分隔 E-->	
	<tr>
		<!-- 留白 -->

		<td><button id="E10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E10</button></td>
		<td><button id="E09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E09</button></td>
		<td><button id="E08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E08</button></td>
		<td><button id="E07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E07</button></td>
		<td><button id="E06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E06</button></td>
		<td rowspan="3">ODF配线架</td>
		<td><button id="E05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E05</button></td>
		<td><button id="E04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E04</button></td>
		<td><button id="E03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E03</button></td>
		<td><button id="E02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E02</button></td>
		<td><button id="E01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'E01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>E01</button></td>
	</tr>
	<tr>
		<!-- 留白 -->

		<td>上海传漾</td>
		<td>上海传漾</td>
		<td>北京快网</td>
		<td>广州爱游4399</td>
		<td>北京快网</td>
		<!-- ODF配线架 -->
		<td>厦门游力</td>
		<td>散户</td>
		<td>散户</td>
		<td>优刻得</td>
		<td>优刻得</td>
	</tr>
	<!-- 排分隔 D-->	
	<tr>
		<!-- 留白 -->

		<td><button id="D09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D09</button></td>
		<td><button id="D08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D08</button></td>
		<td><button id="D07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D07</button></td>
		<td><button id="D06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D06</button></td>
		<!-- ODF配线架 -->
		<td><button id="D05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D05</button></td>
		<td><button id="D04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D04</button></td>
		<td><button id="D03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D03</button></td>
		<td><button id="D02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D02</button></td>
		<td><button id="D01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'D01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>D01</button></td>
		<td>电源柜</td>
	</tr>
	<tr>
		<!-- 留白 -->

		<td>武汉指间缘</td>
		<td>精灵在线</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td></td><!-- ODF配线架 -->
		<td>三海教育</td>
		<td>优刻得</td>
		<td>世纪超星</td>
		<td>厦门游力</td>
		<td></td>	
	</tr>
	<!-- 排分隔 C-->
	<tr>
		<!-- 留白 -->

		<td><button id="C10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C10</button></td>
		<td><button id="C09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C09</button></td>
		<td><button id="C08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C08</button></td>
		<td><button id="C07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C07</button></td>
		<td><button id="C06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C06</button></td>
		<td><button id="C05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C05</button></td>
		<td><button id="C04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C04</button></td>
		<td><button id="C03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C03</button></td>
		<td><button id="C02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C02</button></td>
		<td><button id="C01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'C01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>C01</button></td>
		<td>电源柜</td>
	</tr>
	<tr>
		<!-- 留白 -->

		<td>思远文化(思远文化（盛思播龙）)</td>
		<td>趣游时代（上海同灏）</td>
		<td>趣游时代（上海同灏）</td>
		<td>趣游时代（上海同灏）</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td>世纪超星</td>
		<td></td>
	</tr>	
	<!-- 排分隔 B-->
	<tr>
		<!-- 留白 -->

		<td><button id="B11" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B11'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B11</button></td>
		<td><button id="B10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B10</button></td>
		<td><button id="B09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B09</button></td>
		<td><button id="B08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B08</button></td>
		<td><button id="B07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B07</button></td>
		<td><button id="B06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B06</button></td>
		<td><button id="B05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B05</button></td>
		<td><button id="B04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B04</button></td>
		<td><button id="B03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B03</button></td>
		<td><button id="B02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B02</button></td>
		<td><button id="B01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'B01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>B01</button></td>
	</tr>
	<tr>
		<!-- 留白 -->

		<td>取道兴业</td>
		<td>广州爱游4399</td>
		<td>寰慧科技</td>
		<td>广州爱游4399</td>
		<td>广州爱游4399</td>
		<td>世纪超星</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
		<td>蓝港在线</td>
	</tr>
	<!-- 排分隔 A-->
	<tr>
		<!-- 留白 -->

		<td><button id="A10" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A10'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A10</button></td>
		<td><button id="A09" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A09'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A09</button></td>
		<td><button id="A08" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A08'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A08</button></td>
		<td><button id="A07" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A07'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A07</button></td>
		<td><button id="A06" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A06'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A06</button></td>
		<td rowspan="2">柱子</td>
		<td><button id="A05" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A05'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A05</button></td>
		<td><button id="A04" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A04'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A04</button></td>
		<td><button id="A03" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A03'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A03</button></td>
		<td><button id="A02" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A02'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A02</button></td>
		<td><button id="A01" type="button" class="btn wh" data-toggle="tooltip" data-placement="top" data-container="body"
			<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'A01'}">  
		    		   style="background-color:${m.type};" onClick="add('2F','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		   </c:forEach>
		>A01</button></td>
	</tr>
	<tr>						
		<!-- 留白 -->

		<td>迎创领航</td>
		<td>迎创领航</td>
		<td>优刻得</td>
		<td>优刻得</td>
		<td>境界网</td>
		<!-- 柱子 -->
		<td>三海教育</td>
		<td>公司设备</td>
		<td>公司设备</td>
		<td>公司设备</td>
		<td>公司设备</td>
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