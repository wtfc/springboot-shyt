<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<style>
.inline.checkbox {
	background-color: #787878;width:4.5%;height:50px;margin-right:1px !important;
}
.inline.head {
	background-color: #787878;width:4.5%;height:20px;margin-right:1px;margin-left:9px !important;
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
<h1 class="panel-heading clearfix" style="text-align: center;font-size: 25px">清华园机房平面图</h1>
<div >
	<div style="padding-top:30px">
		<button  type="button" class="btn inline head"style="background-color: #CD0000;"></button>公司自用
		<button  type="button" class="btn inline head"style="background-color: #228B22;"></button>整包机柜
		<button  type="button" class="btn inline head" style="background-color: #CDCD00;"></button>散户机柜
		<button  type="button" class="btn inline head"style="background-color: #1C86EE;"></button>预留机柜
		<button  type="button" class="btn inline head"style="background-color: black;"></button>空机柜
		<button  type="button" class="btn inline head"></button>非本公司
	</div>
<table class="table table-striped tab_height first-td-tc" id="viewTable">
<tbody>
	<tr>
		<td>
		<button id="NE-7" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-7'}">  
	     		   style="background-color:${m.type};" style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-7</button>
		<button id="NE-6" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-6'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-6</button>
		<button id="NE-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-5'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-5</button>
		<button id="NE-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-4'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-4</button>
		<button id="NE-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-3'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-3</button>
		<button id="NE-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-2'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-2</button>
		<button id="NE-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-1'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-1</button>
		<button id="NE-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NE-0'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NE-0</button>
			<span class=" btn-default">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
		<button id="SE-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-0'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-0</button> 
		<button id="SE-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-1'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-1</button> 
		<button id="SE-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-2'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-2</button> 
		<button id="SE-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-3'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-3</button> 
		<button id="SE-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-4'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-4</button> 
		<button id="SE-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-5'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-5</button> 
		<button id="SE-6" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-6'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-6</button> 
		<button id="SE-7" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-7'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-7</button> 
		<button id="SE-8" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-8'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-8</button> 
		<button id="SE-9" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-9'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-9</button> 
		<button id="SE-10" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-10'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-10</button> 
		<button id="SE-11" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SE-11'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SE-11</button> 
		</td>
	</tr>
	<tr>
		<td>
		<button id="ND-7" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-7'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>ND-7</button>
		<button id="ND-6" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-6'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>ND-6</button>
		<button id="ND-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-5'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>ND-5</button>
		<button id="ND-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-4'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>ND-4</button>
		<button id="ND-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-3'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>ND-3</button>
		<button id="ND-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-2'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>ND-2</button>
		<button id="ND-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-1'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>ND-1</button>
		<button id="ND-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-0'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>ND-0</button>
			<span class=" btn-default">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
		<button id="SD-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SD-0'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SD-0</button> 
		<button id="SD-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SD-1'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SD-1</button> 
		<button id="SD-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SD-2'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SD-2</button> 
		<button id="SD-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SD-3'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SD-3</button> 
		<button id="SD-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SD-4'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SD-4</button> 
		<button id="SD-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SD-5'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SD-5</button> 
		<button id="SD-6" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SD-6'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SD-6</button> 
		</td>
	</tr>
	<tr>
	<td>
		<button id="ND-7" type="button" hidden="hidden" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'ND-7'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>空</button>
		<button id="UPS-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'UPS-1'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>UPS-1</button>
		<button id="NC-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NC-5'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NC-5</button>
		<button id="NC-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NC-4'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NC-4</button>
		<button id="NC-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NC-3'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NC-3</button>
		<button id="NC-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NC-2'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NC-2</button>
		<button id="NC-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NC-1'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NC-1</button>
		<button id="NC-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NC-0'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NC-0</button>
			<span class=" btn-default">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
		<button id="SC-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SC-0'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SC-0</button> 
		<button id="SC-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SC-1'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SC-1</button> 
		<button id="SC-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SC-2'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SC-2</button> 
		<button id="SC-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SC-3'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SC-3</button> 
		<button id="SC-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SC-4'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SC-4</button> 
		<button id="SC-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SC-5'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SC-5</button> 
		<button id="UPS-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'UPS-2'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>UPS-2</button> 
	</td>
	</tr>
	<tr>
		<td>
			<button id="NB-7" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NB-7'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NB-7</button>
		<button id="NB-6" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NB-6'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NB-6</button>
		<button id="NB-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NB-5'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NB-5</button>
		<button id="NB-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NB-4'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NB-4</button>
		<button id="NB-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NB-3'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NB-3</button>
		<button id="NB-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NB-2'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NB-2</button>
		<button id="NB-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NB-1'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NB-1</button>
		<button id="NB-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NB-0'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NB-0</button>
			<span class=" btn-default">&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
		<button id="SB-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SB-0'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SB-0</button> 
		<button id="SB-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SB-1'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SB-1</button> 
		<button id="SB-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SB-2'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SB-2</button> 
		<button id="SB-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SB-3'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SB-3</button> 
		<button id="SB-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SB-4'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SB-4</button> 
		<button id="SB-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SB-5'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SB-5</button>
		<button id="SB-6" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SB-6'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SB-6</button>
		<button id="SB-7" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SB-7'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SB-7</button>
		</td>
	</tr>
	<tr >
		<td>
		<button id="NA-7" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NA-7'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NA-7</button>
		<button id="NA-6" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NA-6'}">  
	     		   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NA-6</button>
		<button id="NA-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NA-5'}">  
	     		 style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NA-5</button>
		<button id="NA-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NA-4'}">  
	     		 style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NA-4</button>
		<button id="NA-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NA-3'}">  
	     		  style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NA-3</button>
		<button id="NA-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NA-2'}">  
	     		  onClick="add(1,'${ m.machinaNumber}')"  style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NA-2</button>
		<button id="NA-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NA-1'}">  
	     		 style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NA-1</button>
		<button id="NA-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
			<c:if test="${m.machinaNumber eq 'NA-0'}">  
	     		 style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
			</c:if>
		</c:forEach>
		>NA-0</button>
			<span >&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</span>
		<button id="SA-0" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SA-0'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SA-0</button> 
		<button id="SA-1" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SA-1'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SA-1</button> 
		<button id="SA-2" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SA-2'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SA-2</button> 
		<button id="SA-3" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SA-3'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SA-3</button> 
		<button id="SA-4" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SA-4'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SA-4</button> 
		<button id="SA-5" type="button" class="btn inline checkbox"data-toggle="tooltip" data-placement="top" data-container="body"
		<c:forEach items="${dList}" var="m">
		<c:if test="${m.machinaNumber eq 'SA-5'}">
			   style="background-color:${m.type};" onClick="add(1,'${ m.machinaNumber}')" data-original-title="机柜编号：${ m.machinaNumber};是否开通：${m.isOpen} ; 机柜容量：${m.valume} ;设备数量：${m.num} ;"
		</c:if>
		</c:forEach>
		>SA-5</button> 
		</td> 
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