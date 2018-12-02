<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<script >
$.ajaxSetup ({
	 cache: false //设置成false将不会从浏览器缓存读取信息
	});
</script>
<script src="${sysPath}/js/lib/jqueryui/jquery-ui.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/portal/jquery.portlet.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/portal/jquery.portlet.min.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/sys/portal/jquery.portlet.pack.js" type="text/javascript"></script>
<style>
.ui-portlet-column {
	float: left;
	padding-bottom: 10px;
}

.ui-portlet-header {
	padding: .4em 0.7em
}

.ui-portlet-header .ui-icon {
	float: right;
}

.ui-portlet-item {
	margin: 0 1em 1em 0;
}

.ui-portlet-content {
	padding: 0.3em;
	overflow-y:auto;
}

.ui-portlet-header .ui-portlet-header-icon {
	float: left;
	margin: 0 0.2em 0 -0.5em;
}

.ui-sortable-placeholder {
	border: 1px dotted black;
	visibility: visible !important;
	height: 50px !important;
}

.ui-sortable-placeholder * {
	visibility: hidden;
}
</style>

<script>


    $(function() {
        $('#portlet-demo').portlet({
            sortable: true,
            singleView: true,
            create: function() {
                //alert('created portlet.');
            },
            removeItem: function() {
                alert('after remove');
            },
            columns: [{
                portlets: [{
                    attrs: {
                        id: 'news1'
                    },
                    title: '<h2 class=m_title><span>督办协办</span></h2>',
                    beforeRefresh: function() {
                        alert("before refresh");
                    },
                    afterRefresh: function(data) {
                        //alert("after refresh: " + data);
                    },
                    content: {
                        style: {
                            height: '320'
                        },
                        type: 'text',
                        text: function() {
                            return $('#div1').html();
                            //return $('#newsTemplate ul');
                        }
                    }
                }]
            },{
                portlets: [{
                    attrs: {
                        id: 'news2'
                    },
                    title: '<h2 class=m_title><span>待办中心</span></h2>',
                    beforeRefresh: function() {
                        alert("before refresh");
                    },
                    afterRefresh: function(data) {
                        //alert("after refresh: " + data);
                    },
                    content: {
                        style: {
                            height: '300'
                        },
                        type: 'text',
                        text: function() {
                            return $('#div2').html();
                            //return $('#newsTemplate ul');
                        }
                    }
                }]
            },{
            	width: '48%',
                portlets: [{
                    attrs: {
                        id: 'news4'
                    },
                    title: '<h2 class=m_title><span>我的日程</span></h2>',
                    beforeRefresh: function() {
                        alert("before refresh");
                    },
                    afterRefresh: function(data) {
                        //alert("after refresh: " + data);
                    },
                    content: {
                        style: {
                            height: '300'
                        },
                        type: 'text',
                        text: function() {
                            return $('#div4').html();
                            //return $('#newsTemplate ul');
                        }
                    }
                }]
            },{
            	width: '48%',
                portlets: [{
                    attrs: {
                        id: 'news5'
                    },
                    title: '<h2 class=m_title><span>系统提醒</span></h2>',
                    beforeRefresh: function() {
                        alert("before refresh");
                    },
                    afterRefresh: function(data) {
                        //alert("after refresh: " + data);
                    },
                    content: {
                        style: {
                            height: '300'
                        },
                        type: 'text',
                        text: function() {
                            return $('#div5').html();
                            //return $('#newsTemplate ul');
                        }
                    }
                }]
            }]
        });
        
        	$('#toggleAll').button({
            	icons: {
               	 	primary: 'ui-icon-refresh'
            	}
       	 	}).click(function() {
            	$('#portlet-demo').portlet('toggleAll');
        	});
        	
        	$( "#tabs" ).tabs();
        	$("#accordion").accordion();
        });

</script>
  
<div id='portlet-demo'></div>


<div id="div1" style="display:none">
<div id="tabs">
<ul class="nav nav-tabs">
   <li><a href="#tabs-1" >Nunc tincidunt</a></li>
   <li><a href="#tabs-2" >Proin dolor</a></li>
   <li><a href="#tabs-3" >Aenean lacinia</a></li>
</ul>
<div class="modules" id="tabs-1">
<div class="index-inform panel" >
<div class="table-wrap input-default">
<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
	<thead>
		<tr>
			<th style="width:46px;"><input type="checkbox" /></th>
			<th>标题</th>
			<th class="w115">日期</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
	</tbody>
</table>
</div>
</div>
</div>
<div class="modules" id="tabs-2">
<div class="index-inform panel" >
<div class="table-wrap input-default">
<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
	<thead>
		<tr>
			<th style="width:46px;"><input type="checkbox" /></th>
			<th>标题</th>
			<th class="w115">日期</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
	</tbody>
</table>
</div>
</div>
</div>
<div class="modules" id="tabs-3">
<div class="index-inform panel" >
<div class="table-wrap input-default">
<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
	<thead>
		<tr>
			<th style="width:46px;"><input type="checkbox" /></th>
			<th>标题</th>
			<th class="w115">日期</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
	</tbody>
</table>
</div>
</div>
</div>
</div>
</div>
<div id="div2" style="display:none">
<div id="accordion"> 
<h6><a href="#">Section 1</a></h6> 
<div class="modules">
<div class="index-inform panel">
<div class="table-wrap input-default">
<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
	<thead>
		<tr>
			<th style="width:46px;"><input type="checkbox" /></th>
			<th>标题</th>
			<th class="w115">日期</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
	</tbody>
</table>
</div>
</div>
</div>

<h6><a href="#">Section 2</a></h6> 
<div class="modules">
<div class="index-inform panel">
<div class="table-wrap input-default">
<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
	<thead>
		<tr>
			<th style="width:46px;"><input type="checkbox" /></th>
			<th>标题</th>
			<th class="w115">日期</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于部门门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-25</td>
		</tr>
	</tbody>
</table>
</div>
</div>
</div>

<h6><a href="#">Section 3</a></h6> 
<div class="modules">
<div class="index-inform panel">
<div class="table-wrap input-default">
<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
	<thead>
		<tr>
			<th style="width:46px;"><input type="checkbox" /></th>
			<th>标题</th>
			<th class="w115">日期</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于个人门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-28</td>
		</tr>
	</tbody>
</table>
</div>
</div>
</div>

</div>
</div>
<div id="div3" style="display:none">
<div class="modules">
<div class="index-inform panel">
 <div align="center" class="dbzx"><img src="images/demoimg/analysis.jpg"></div>
</div>
</div>
</div>
<div id="div4" class="modules" style="display:none">
<div class="index-inform panel" >
<div class="table-wrap input-default">
<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
	<thead>
		<tr>
			<th style="width:46px;"><input type="checkbox" /></th>
			<th>标题</th>
			<th class="w115">日期</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
	</tbody>
</table>
</div>
</div>
</div>
<div id="div5" class="modules" style="display:none">
<div class="index-inform panel" >
<div class="table-wrap input-default">
<table class="table table-striped table-bordered b-light first-td-tc over-hide-wrap">
	<thead>
		<tr>
			<th style="width:46px;"><input type="checkbox" /></th>
			<th>标题</th>
			<th class="w115">日期</th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
		<tr>
			<td><input type="checkbox" /></td>
			<td class="over-hide">关于首页门户DEMO的模块组成关于首页门户DEMO的模块组成 </td>
			<td>2014-01-22</td>
		</tr>
	</tbody>
</table>
</div>
</div>
</div>
<div id="div6" class="modules" style="display:none">
<div class="index-inform panel">
<div class="glory-box" id="grb">
	<div class="cyclyImg_pager2 com2"></div>
	<div class="cyclyImg_next next"></div>
	<div class="cyclyImg_prev prev"></div>
	<div id="slider" class="case_box">
		<div class="case_con" id="Projector1">
			  <div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img1.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">21</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">11</i></a>
					</div>
			  </div>
				<div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img2.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">20</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">10</i></a>
					</div>
			  </div>
				<div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img3.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">20</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">10</i></a>
					</div>
			  </div>
			  <div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img1.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">22</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">12</i></a>
					</div>
			  </div>
				<div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img2.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">20</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">10</i></a>
					</div>
			  </div>
				<div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img3.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">20</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">10</i></a>
					</div>
			  </div>
			  <div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img1.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">23</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">13</i></a>
					</div>
			  </div>
				<div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img2.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">20</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">10</i></a>
					</div>
			  </div>
				<div class="glory-con">
					<div class="glory-img m-b-sm"><a href="javascript:void(0)"><img src="images/demoimg/glory-img3.jpg" /></a></div>
					<p class="m-b-sm">
						<a href="">销售标兵</a>
						<a href="">蒙哥马利</a>
					</p>
					<div class="glory-btm">
						<a href="javascript:void(0)"><i class="fa fa-heart icon">20</i></a>
						<a href="javascript:void(0)"><i class="fa fa-chat icon">10</i></a>
					</div>
			  </div>
		</div>
	</div>
</div>
</div>
</div>
  	 
  
  	 
   
  	
  	












