<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/taglib.jsp"%>
<%@ include file="/WEB-INF/web/include/selectionTree.jsp"%>
<div class="modal fade panel" id="myModal-edit" aria-hidden="false">
	<div class="modal-dialog w900">
		<form class="table-wrap form-table" id="toaFinanceInvoicesForm">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h4 class="modal-title" id="actionTitle">编 辑</h4>
				</div>
				<div class="modal-body">
					<section class="dis-table">
						<!-- tabs模块下紧接panel模块 -->
						<div class="tab-content" style="overflow:hidden;">
							<div class="tab-pane for fade active in" id="messages1">
								<!-- tab-pane tab切换显示的模块 内部panel的类名替换为panel-in-box -->
								<input type="hidden" id="id" name="id" value="0"> <input
									type="hidden" id="token" name="token" value="${data.token}">
								<input type="hidden" id="modifyDate" name="modifyDate">
								<input type="hidden" id="mainId" name="mainId">
								<input type="hidden" id="resourceType" name="resourceType">
								<div class="table-wrap form-table">
							<table class="table table-td-striped">
								<tbody>
									<tr>
										<td  class=" b-green-dark b-tc">应收金额</td>
										<td>
											<input type="text"  style="width:100%;"id="monthAmount" name="monthAmount" readonly/>
										</td>
										<td  class=" b-green-dark b-tc">是否开票</td>
										<td>
											<select id="invoicesState" name="invoicesState" >
												<option value="是">是</option>
												<option value="否">否</option>
											</select>
										</td>
									</tr>
									<tr>
										<td  class=" b-green-dark b-tc">开票日期</td>
										<td>
											<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="vdateDate" name="vdateDate" />
										</td>
										<td  class=" b-green-dark b-tc">发票号码</td>
										<td>
											<input type="text"  id="invoicesNo" name="invoicesNo" />
										</td>
									</tr>
									<tr>
										<td  class=" b-green-dark b-tc">发票金额</td>
										<td>
											<input type="text" onkeyup="checkInt();" onpaste="checkInt();" oncut="checkInt();" ondrop="checkInt();" onchange="checkInt();" style="width:100%;"id="invoicesAccount" name="invoicesAccount" />
										</td>
										<td class=" b-green-dark b-tc">未开票金额</td>
										<td>
											<input type="text"  style="width:100%;"id="noinvoicesAccount" name="noinvoicesAccount" />
										</td>
									</tr>
									<tr>
										<td  class=" b-green-dark b-tc">是否回款</td>
										<td>
											<select style="width:100%;"id="receivedState" name="receivedState" >
												<option value="">请选择</option>
												<option value="是">是</option>
												<option value="否">否</option>
											</select>
										</td>
										<td  class=" b-green-dark b-tc">回款日期</td>
										<td>
											<input type="text"data-date-format="yyyy-MM-dd"data-pick-time="false" class="datepicker-input"  style="width:100%;"id="receivedDate" name="receivedDate" />
										</td>
									</tr>
									<tr>
										<td  class=" b-green-dark b-tc">回款金额</td>
										<td>
											<input type="text"onkeyup="checkP();" onpaste="checkP();" oncut="checkP();" ondrop="checkP();" onchange="checkP();"  style="width:100%;"id="receivedAccount" name="receivedAccount" />
										</td>
										<td  class=" b-green-dark b-tc">未回款金额</td>
										<td>
											<input type="text"  style="width:100%;"id="arrearage" name="arrearage" />
										</td>
									</tr>
									<tr>
										<td  class=" b-green-dark b-tc">代理费</td>
										<td>
											<input type="text"  style="width:100%;"id="commision" name="commision" />
										</td>
										<td  class=" b-green-dark b-tc">是否添加到绩效提成</td>
										<td>
											<input type="radio"  id="jixiaoticheng" name="jixiaoticheng" value="是" class="leaderCla"/>是
											<input type="radio"  checked="checked" id="jixiaoticheng" name="jixiaoticheng" value="否" class="leaderCla"/>否
										</td>
									</tr>
									<tr>
										<td style="width:10%;" class=" b-green-dark b-tc"><div class="openCaleTd" style="display:none">到账总额</div></td>
										<td>
											<div class="openCaleTd" style="display:none"><input type="text"  style="width:100%;"id="allmoney" name="allmoney" /></div>
										</td>
										<td style="width:10%;" class=" b-green-dark b-tc"><div class="openCaleTd" style="display:none">新增到账</div></td>
										<td>
											<div class="openCaleTd" style="display:none"><input type="text"  style="width:100%;"id="allmoney" name="addmoney" /></div>
										</td>
									</tr>
									<tr>
										<td style="width:10%;" class=" b-green-dark b-tc"><div class="openCaleTd" style="display:none">存量到账</div></td>
										<td>
											<div class="openCaleTd" style="display:none"><input type="text"  style="width:100%;"id="stackMoney" name="stackMoney" /></div>
										</td>
										<td style="width:10%;" class=" b-green-dark b-tc"><div class="openCaleTd" style="display:none">扩容到账</div></td>
										<td>
											<div class="openCaleTd" style="display:none"><input type="text"  style="width:100%;"id="scaleMoney" name="scaleMoney" /></div>
										</td>
									</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					</section>
				</div>
				<div class="modal-footer form-btn">
					<button class="btn" type="button" id="saveAndClose" onclick="toaFinanceInvoicesModule.saveOrModify(true)">保存退出</button>
					<button class="btn" type="button" class="close"data-dismiss="modal">关 闭</button>
				</div>
			</div>
		</form>
	</div>
</div>
<script src="${sysPath}/js/com/finance/toaFinanceInvoices/toaFinanceInvoicesForm.js" type="text/javascript"></script>
<script src="${sysPath}/js/com/finance/toaFinanceInvoices/toaFinanceInvoices.validate.js" type="text/javascript"></script>