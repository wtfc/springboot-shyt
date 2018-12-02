<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in">
<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
 </div>
</header>

<section class="tabs-wrap m-t-md">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#linkman-1"  data-toggle="tab"  data-id="externalContacts">商业贷款</a></li>
		<li><a href="#linkman-2" data-toggle="tab" data-id="internalContacts">年终奖收入</a></li>
		<li><a href="#linkman-3" data-toggle="tab" data-id="internalContacts">其他收入</a></li>
	</ul>
</section>
<section class="tab-content">
	<section class="panel tab-content search-shrinkage">	
		<div class="tab-pane active fade in" id="linkman-1">
			<table>
        	<div class="table-wrap form-table-h input-textarea plan-table">
            <table class="table table-striped first-td-tc"> 
                <thead>
                </thead>
                <tbody>
			        <div class="table-wrap form-table-h input-textarea plan-table">
			            <table id="preSum" workFlowForm="autoRow" itemName="autoSumTable" class="table table-striped first-td-tc"> 
			                <thead>
			                </thead>
			                <tbody>
			                	<tr>
			                		<td>计算方式</td>
			                		<td>
			                			<input type="radio" name="aa" checked="checked">按贷款额度算
			                			<input type="radio" name="aa">按面积算
			                		</td>
			                		<td>贷款金额</td>
			                		<td>
			                			<input type="text">元
			                		</td>
			                	</tr>
			                	<tr>
			                		<td>贷款期限</td>
			                		<td>
			                			<select>
			                				<option>半年(6期)</option>
			                				<option>1年(12期)</option>
			                				<option>2年(24期)</option>
			                				<option>3年(36期)</option>
			                				<option>4年(48期)</option>
			                				<option>5年(60期)</option>
			                				<option>6年(72期)</option>
			                				<option>7年(84期)</option>
			                				<option>8年(96期)</option>
			                				<option>9年(108期)</option>
			                				<option>10年(120期)</option>
			                				<option>11年(132期)</option>
			                				<option>12年(144期)</option>
			                				<option>13年(156期)</option>
			                				<option>14年(168期)</option>
			                				<option>15年(180期)</option>
			                				<option>16年(192期)</option>
			                				<option>17年(204期)</option>
			                				<option>18年(216期)</option>
			                				<option>19年(228期)</option>
			                				<option>20年(240期)</option>
			                				<option>21年(252期)</option>
			                				<option>22年(264期)</option>
			                				<option>23年(276期)</option>
			                				<option>24年(288期)</option>
			                				<option>25年(300期)</option>
			                				<option>26年(312期)</option>
			                				<option>27年(324期)</option>
			                				<option>28年(336期)</option>
			                				<option>29年(348期)</option>
			                				<option>30年(360期)</option>
			                			</select>
			                		</td>
			                		<td>年利率</td>
			                		<td colspan="3">
			                			<select>
			                				<option>最新基准利率7折</option>
			                				<option>最新基准利率8折</option>
			                				<option>最新基准利率8.5折</option>
			                				<option>最新基准利率9折</option>
			                				<option>最新基准利率</option>
			                				<option>最新基准利率1.1倍</option>
			                				<option>最新基准利率1.2倍</option>
			                				<option>最新基准利率1.3倍</option>
			                			</select>
			                			<input type="text">
			                		</td>
			                	</tr>
			                	<div class="modal-footer form-btn">
									<a class="btn dark" id="toModify">计 算</a>
									<a class="btn dark" id="aaa">重 置</a>
								</div>
								<div>
									<table id="preSum" workFlowForm="autoRow" itemName="autoSumTable" class="table table-striped first-td-tc">
										<thead>
										</thead>
										<tbody>
											<tr>
												<td>11</td>
												<td>11</td>
											</tr>									
										</tbody>
									</table>
								</div>
			                </tbody>
			            </table>
			        </div>
                </tbody>
            </table>
        	</div>
		</div>
		<div class="tab-pane fade in" id="linkman-2">

		</div>
		<div class="tab-pane fade in" id="linkman-3">

		</div>
	</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>