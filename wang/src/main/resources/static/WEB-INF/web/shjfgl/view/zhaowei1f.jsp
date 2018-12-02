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

	#zhaowei{
		width: 100%;
		margin-top:30px;
	}

	#zhaowei tbody tr,#zhaowei tbody tr td{
		/* border: 1px black solid; */
		width: auto;
		text-align: center;
	}

	#zhaowei tbody tr td{
		height: 22px;
		line-height:22px;
		min-width: 100px;
		white-space:nowrap;
	}
	
	#zhaowei .explain{
		background: #FFFFDD; 
		height: 10px;
		text-align: left;
		font-weight: bold;
		/*padding-left: 200px;*/
	}

	#zhaowei .mar-left{
		display: inline-block;
		width: 50px;
	}
	
	.wh{
		/* margin:1px; */
		width:100%;
		background-color: #787878;
	}
</style>
<section class="scrollable padder jcGOA-section" id="scrollable">
<section class="tree-fluid m-t-md">
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">兆维机房平面图</h1>
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
<table class="table table-striped tab_height first-td-tc" id="zhaowei">
<tbody>
	
	<!-- 1层 1-1机房 总数：67 -->
	<tr><td colspan="32" class="explain"><span class="mar-left"></span>1层 1-1机房 总数：67</td></tr>
	<!-- 排分隔 -->
	<tr>
		<td><button id="11#3" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '11#3'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>11#3</button></td>
	</tr>
	
	<!-- 排分隔 -->
	<tr>
		<td><button id="6#1" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#1'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#1</button></td>
		<td><button id="6#2" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#2'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#2</button></td>
		<td><button id="6#3" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#3'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#3</button></td>
		<td><button id="6#4" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#4'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#4</button></td>
		<td><button id="6#5" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#5'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#5</button></td>
		<td><button id="6#6" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#6'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#6</button></td>
		<td><button id="6#7" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#7'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#7</button></td>
		<td><button id="6#8" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#8'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#8</button></td>
		<td><button id="6#9" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#9'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#9</button></td>
		<td><button id="6#10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#10'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#10</button></td>
		<td><button id="6#11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#11'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#11</button></td>
		<td><button id="6#12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#12'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#12</button></td>
		<td><button id="6#13" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#13'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#13</button></td>
		<td><button id="6#14" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#14'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#14</button></td>
		<td><button id="6#15" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#15'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#15</button></td>
		<td><button id="6#16" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#16'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#16</button></td>
		<td><button id="6#17" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#17'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#17</button></td>
		<td><button id="6#18" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '6#18'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>6#18</button></td>
	</tr>
	
	<!-- 排分隔 -->
	<tr>
		<td><button id="5#1" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#1'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#1</button></td>
		<td><button id="5#2" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#2'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#2</button></td>
		<td><button id="5#3" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#3'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#3</button></td>
		<td><button id="5#4" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#4'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#4</button></td>
		<td><button id="5#5" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#5'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#5</button></td>
		<td><button id="5#6" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#6'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#6</button></td>
		<td><button id="5#7" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#7'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#7</button></td>
		<td><button id="5#8" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#8'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#8</button></td>
		<td><button id="5#9" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#9'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#9</button></td>
		<td><button id="5#10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#10'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#10</button></td>
		<td><button id="5#11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#11'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#11</button></td>
		<td><button id="5#12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#12'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#12</button></td>
		<td><button id="5#13" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#13'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#13</button></td>
		<td><button id="5#14" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#14'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#14</button></td>
		<td><button id="5#15" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#15'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#15</button></td>
		<td><button id="5#16" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#16'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#16</button></td>
		<td><button id="5#17" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#17'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#17</button></td>
		<td><button id="5#18" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#18'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#18</button></td>
		<td><button id="5#19" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#19'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#19</button></td>
		<td><button id="5#20" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#20'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#20</button></td>
		<td><button id="5#21" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#21'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#21</button></td>
		<td><button id="5#22" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#22'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#22</button></td>
		<td><button id="5#23" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#23'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#23</button></td>
		<td><button id="5#24" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#24'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#24</button></td>
		<td><button id="5#25" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#25'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#25</button></td>
		<td><button id="5#26" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#26'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#26</button></td>
		<td><button id="5#27" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#27'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#27</button></td>
		<td><button id="5#28" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#28'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#28</button></td>
		<td><button id="5#29" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#29'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#29</button></td>
		<td><button id="5#30" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#30'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#30</button></td>
		<td><button id="5#31" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#31'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#31</button></td>
		<td><button id="5#32" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '5#32'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>5#32</button></td>
	</tr>
	
	<!-- 排分隔 -->
	<tr>
		<td><button id="4#14" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '4#14'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>4#14</button></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	
	<!-- 排分隔 -->
	<tr>
		<td><button id="3#1" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '3#1'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>3#1</button></td>
		<td><button id="3#7" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '3#7'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>3#7</button></td>
		<td><button id="3#8" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '3#8'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>3#8</button></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
	
	<!-- 排分隔 -->
	<tr>
		<td><button id="2#4" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '2#4'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>2#4</button></td>
		<td><button id="2#2" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '2#2'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>2#2</button></td>
		<td><button id="2#3" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '2#3'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>2#3</button></td>
		<td><button id="2#4" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '2#4'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>2#4</button></td>
		<td><button id="2#5" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '2#5'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>2#5</button></td>
		<td><button id="2#6" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '2#6'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>2#6</button></td>
		<td><button id="2#7" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '2#7'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>2#7</button></td>
		<td><button id="2#9" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '2#9'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>2#9</button></td>
	</tr>
	
	<!-- 排分隔 -->
	<tr>
		<td><button id="1#9" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '1#9'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>1#9</button></td>
		<td><button id="1#10" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '1#10'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>1#10</button></td>
		<td><button id="1#11" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '1#11'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>1#11</button></td>
		<td><button id="1#12" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '1#12'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>1#12</button></td>
		<td><button id="1#13" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '1#13'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>1#13</button></td>
	</tr>
	
	<!-- 1层 1-3机房 总数：3 -->
	<tr><td colspan="3" class="explain"><span class="mar-left"></span>1层 1-3机房 总数：3</td></tr>
	<tr>
		<td><button id="1#1" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '1#1'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>1#1</button></td>
		<td><button id="1#2" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '1#2'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>1#2</button></td>
		<td><button id="1#3" type="button" class="btn wh"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq '1#3'}">  
	     		   style="background-color:${m.type};" onClick="add('zhaowei1f','${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>1#3</button></td>
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
//处理 ‘1#9’式的情况：
	number = number.replace("#","%23");
 $("#formModuleDiv").load(getRootPath()+"/machine/view/loadFormView.action?name="+name+"&&number="+number+"",null,function(){
	 viewModule.show();
		});
}
</script>

<%@ include file="/WEB-INF/web/include/foot.jsp"%>