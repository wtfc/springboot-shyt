<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
<script src="${sysPath}/js/com/po/personalBox/accumulationFund.js"></script>

<section class="scrollable padder jcGOA-section" id="scrollable"> 
<header class="con-header pull-in">
<div class="con-heading fl" id="navigationMenu">
        <h1></h1>
        <div class="crumbs"></div>
 </div>
</header>

<section class="tabs-wrap m-t-md">
	<ul class="nav nav-tabs">
		<li class="active"><a href="#linkman-1"  data-toggle="tab" id="tab-1" data-id="externalContacts">商业贷款</a></li>
		<li><a href="#linkman-2" data-toggle="tab" id="tab-2" data-id="internalContacts">公积金贷款</a></li>
		<li><a href="#linkman-3" data-toggle="tab" id="tab-3" data-id="internalContacts">组合贷款</a></li>
	</ul>
</section>
<section class="tab-content">
	<section class="panel tab-content search-shrinkage">	
		<div class="tab-pane active fade in" id="linkman-1">
			<section class="tab-content">
            <section class="panel clearfix m-t-md">
            	<div class="table-wrap">
                     <form id="businessCalc" name="businessCalc">
                           <table width="1150" border="0" cellpadding="0" cellspacing="0">
                              <tr>
                                   <td width="40%" valign="top">
                                   <div style="padding-left:20px;">
                                   <div>
	                                   <div style="vertical-align: bottom;border-top:1px dashed #ccc;padding-top:20px;">计算方式：
											<label class="radio inline"><input type="radio" name="calcType" value="0" onclick="changeCalcByMoney();" checked="checked"/>按贷款额度</label>
		                                    <label class="radio inline"><input type="radio" name="calcType" value="1" onclick="changeCalcByArea();" />按面积</label>
	                                   </div>
                                   </div>
                                   <div id="calcMoney" name="calcMoney" style="padding-top:20px;padding-bottom:20px;">
                                        <ul>
                                               <li>贷款金额：&nbsp;<input id="busiMoneyTotal" name="busiMoneyTotal" type="text" maxlength="10" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;"/>&nbsp;&nbsp;元</li>													
                                        </ul>
                                   </div>
                                   <div id="calcArea" name="calcArea" style="padding-top:20px;padding-bottom:20px;display:none;">
                                        <ul>
                                               <li>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：&nbsp;&nbsp;<input id="price" name="price" type="text" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;"/>&nbsp;&nbsp;元/平米</li>
                                               <li style="margin-top:20px;">面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积：&nbsp;&nbsp;<input id="area" name="area" type="text" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;"/>&nbsp;&nbsp;平方米</li>													
                                        </ul>
                                   </div>
                                   
                                   <div style="padding-bottom:20px;">按揭年数：
                                       <select id="syajns" name="syajns" size="1" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;" >
                                           <option value="12">1年(12期)</option>
                                           <option value="24">2年(24期)</option>
                                           <option value="36">3年(36期)</option>
                                           <option value="48">4年(48期)</option>
                                           <option value="60">5年(60期)</option>
                                           <option value="72">6年(72期)</option>
                                           <option value="84">7年(84期)</option>
                                           <option value="96">8年(96期)</option>
                                           <option value="108">9年(108期)</option>
                                           <option value="120">10年(120期)</option>
                                           <option value="132">11年(132期)</option>
                                           <option value="144">12年(144期)</option>
                                           <option value="156">13年(156期)</option>
                                           <option value="168">14年(168期)</option>
                                           <option value="180">15年(180期)</option>
                                           <option value="192">16年(192期)</option>
                                           <option value="204">17年(204期)</option>
                                           <option value="216">18年(216期)</option>
                                           <option value="228">19年(228期)</option>
                                           <option value="240">20年(240期)</option>
                                           <option value="300">25年(300期)</option>
                                           <option value="360" selected="true">30年(360期)</option>
                                       </select>
                                   </div>
                                   
                                   <div style="padding-bottom:20px;">
                                       <div>贷款利率：
                                           <select id="syll" name="syll" style="width:240px;height:33px;margin:0;border:1px solid #dfdfdf;" onchange="showSyll(this.value)">
                                               <option value="1">最新基准利率7折</option>
                                               <option value="2">最新基准利率8折</option>
                                               <option value="3">最新基准利率8.5折</option>
                                               <option value="4">最新基准利率9折</option>
                                               <option value="5" selected="true">最新基准利率</option>
                                               <option value="6">最新基准利率1.1倍</option>
                                               <option value="7">最新基准利率1.2倍</option>
                                               <option value="8">最新基准利率1.3倍</option>
                                           </select>
                                           <input id="syllInput" style="width:50px;height:33px;margin:0;border:1px solid #dfdfdf;" maxlength="6" type="text" readonly="readonly"/>&nbsp;%
                                       </div>
                                   </div>
                                   <div class="form-btn" style="padding:100px 0 0;">
                                       <button class="btn dark" style="width:115px;height:43px;" type="button" onclick="calcBusiness()">计 算</button>
                                       <button type="button" class="btn" style="width:115px;height:43px;" onclick="resetBusiness()">重 置</button>
                                   </div>
                                   </div>
                                   </td>
                                   
                                   <td width="10%" valign="middle"></td>
                                   <td width="50%" valign="top">
                                       <div style="background:#f6f6f6;padding:20px;width:370px;">
                                       <div>
                                            <ul>
                                                <li>贷款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="syTotal" name="syTotal" type="text" readonly="true" />&nbsp;元</li>
                                                <li style="margin-top:20px;">贷款月数：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="syMonths" name="syMonths" type="text" readonly="true" />&nbsp;月</li>
                                            </ul>
                                       </div>
                                       </div>
                                       <div style="background:#f6f6f6;padding:20px;padding-top:0;width:370px;">
                                       <div style="color:#00a65a;">等额本息</div>
                                            <div>
                                                 <ul>
                                                 	 <li style="margin-top:20px;">还款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="sybxPayTotal" name="sybxPayTotal" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">支付利息：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="sybxlxPayTotal" name="sybxlxPayTotal" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">月均还款：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="sybxAvgPay" name="sybxAvgPay" type="text" readonly="true"/>&nbsp;元</li>
                                                 </ul>
                                            </div>
                                       </div>
                                       
                                       <div style="background:#f6f6f6;padding:20px;padding-top:0;width:370px;">
                                       <div style="color:#00a65a;">等额本金</div>
                                            <div>
                                                 <ul>
                                                 	 <li style="margin-top:20px;">还款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="sybjPayTotal" name="sybjPayTotal" type="text" readonly="true" />&nbsp;元</li>
                                                     <li style="margin-top:20px;">支付利息：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="sybjlxPayTotal" name="sybjlxPayTotal" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">月均金额：&nbsp;<textarea style="width:220px;margin:0;border:1px solid #dfdfdf;" id="sybjAvgPay" name="sybjAvgPay" rows="6" cols="4" readonly="true"></textarea></li>
                                                 </ul>
                                            </div>
                                       </div>
                                   </td>
                              </tr>
                           </table>						
                     </form>
                  </div>
               </section>
            </section>
		</div>
		<div class="tab-pane fade in" id="linkman-2">
			<section class="tab-content">
            <section class="panel clearfix m-t-md">
            	<div class="table-wrap">
                     <form id="gjjCalc" name="gjjCalc">
                           <table width="1150" border="0" cellpadding="0" cellspacing="0">
                              <tr>
                                   <td width="40%" valign="top">
                                   <div style="padding-left:20px;">
                                   <div>
	                                   <div style="vertical-align: bottom;border-top:1px dashed #ccc;padding-top:20px;">计算方式：
											<label class="radio inline"><input type="radio" name="calcGjjType" value="0" onclick="changeCalcByGjjMoney();" checked="checked"/>按贷款额度</label>
		                                    <label class="radio inline"><input type="radio" name="calcGjjType" value="1" onclick="changeCalcByGjjArea();" />按面积</label>
	                                   </div>
                                   </div>
                                   <div id="calcGjjMoney" name="calcGjjMoney" style="padding-top:20px;padding-bottom:20px;">
                                        <ul>
                                               <li>贷款金额：&nbsp;<input id="gjjMoneyTotal" name="gjjMoneyTotal" type="text" maxlength="10" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;"/>&nbsp;&nbsp;元</li>													
                                        </ul>
                                   </div>
                                   <div id="calcGjjArea" name="calcGjjArea" style="padding-top:20px;padding-bottom:20px;display:none;">
                                        <ul>
                                               <li>单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;价：&nbsp;&nbsp;<input id="gjjPrice" name="gjjPrice" type="text" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;"/>&nbsp;&nbsp;元/平米</li>
                                               <li style="margin-top:20px;">面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积：&nbsp;&nbsp;<input id="gjjArea" name="gjjArea" type="text" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;"/>&nbsp;&nbsp;平方米</li>													
                                        </ul>
                                   </div>
                                   
                                   <div style="padding-bottom:20px;">按揭年数：
                                       <select id="gjjajns" name="gjjajns" size="1" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;" onchange="showGjjll(this.value)">
                                           <option value="12">1年(12期)</option>
                                           <option value="24">2年(24期)</option>
                                           <option value="36">3年(36期)</option>
                                           <option value="48">4年(48期)</option>
                                           <option value="60">5年(60期)</option>
                                           <option value="72">6年(72期)</option>
                                           <option value="84">7年(84期)</option>
                                           <option value="96">8年(96期)</option>
                                           <option value="108">9年(108期)</option>
                                           <option value="120">10年(120期)</option>
                                           <option value="132">11年(132期)</option>
                                           <option value="144">12年(144期)</option>
                                           <option value="156">13年(156期)</option>
                                           <option value="168">14年(168期)</option>
                                           <option value="180">15年(180期)</option>
                                           <option value="192">16年(192期)</option>
                                           <option value="204">17年(204期)</option>
                                           <option value="216">18年(216期)</option>
                                           <option value="228">19年(228期)</option>
                                           <option value="240">20年(240期)</option>
                                           <option value="300">25年(300期)</option>
                                           <option value="360" selected="true">30年(360期)</option>
                                       </select>
                                   </div>
                                   
                                   <div style="padding-bottom:20px;">
                                       <div>贷款利率：
                                           <input id="gjjllInput" style="width:50px;height:33px;margin:0;border:1px solid #dfdfdf;" maxlength="6" type="text" readonly="readonly"/>&nbsp;%
                                       </div>
                                   </div>
                                   <div class="form-btn" style="padding:100px 0 0;">
                                       <button class="btn dark" style="width:115px;height:43px;" type="button" onclick="calcGjj()">计 算</button>
                                       <button type="button" class="btn" style="width:115px;height:43px;" onclick="resetGjj()">重 置</button>
                                   </div>
                                   </div>
                                   </td>
                                   
                                   <td width="10%" valign="middle"></td>
                                   <td width="50%" valign="top">
                                       <div style="background:#f6f6f6;padding:20px;width:370px;">
                                       <div>
                                            <ul>
                                                <li>贷款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="gjjTotal" name="gjjTotal" type="text" readonly="true" />&nbsp;元</li>
                                                <li style="margin-top:20px;">贷款月数：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="gjjMonths" name="gjjMonths" type="text" readonly="true" />&nbsp;月</li>
                                            </ul>
                                       </div>
                                       </div>
                                       <div style="background:#f6f6f6;padding:20px;padding-top:0;width:370px;">
                                       <div style="color:#00a65a;">等额本息</div>
                                            <div>
                                                 <ul>
                                                 	 <li style="margin-top:20px;">还款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="gjjbxPayTotal" name="gjjbxPayTotal" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">支付利息：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="gjjbxlxPayTotal" name="gjjbxlxPayTotal" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">月均还款：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="gjjbxAvgPay" name="gjjbxAvgPay" type="text" readonly="true"/>&nbsp;元</li>
                                                 </ul>
                                            </div>
                                       </div>
                                       
                                       <div style="background:#f6f6f6;padding:20px;padding-top:0;width:370px;">
                                       <div style="color:#00a65a;">等额本金</div>
                                            <div>
                                                 <ul>
                                                 	 <li style="margin-top:20px;">还款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="gjjbjPayTotal" name="gjjbjPayTotal" type="text" readonly="true" />&nbsp;元</li>
                                                     <li style="margin-top:20px;">支付利息：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="gjjbjlxPayTotal" name="gjjbjAvgPay" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">月均金额：&nbsp;<textarea style="width:220px;margin:0;border:1px solid #dfdfdf;" id="gjjbjAvgPay" name="gjjbjAvgPay" rows="6" cols="4" readonly="true"></textarea></li>
                                                 </ul>
                                            </div>
                                       </div>
                                   </td>
                              </tr>
                           </table>						
                     </form>
                  </div>
               </section>
            </section>
		</div>
		<div class="tab-pane fade in" id="linkman-3">
			<section class="tab-content">
            <section class="panel clearfix m-t-md">
            	<div class="table-wrap">
                     <form id="groupCalc" name="groupCalc">
                           <table width="1150" border="0" cellpadding="0" cellspacing="0">
                              <tr>
                                   <td width="40%" valign="top">
	                                   <div style="padding-left:20px;"><div>
	                                   <div style="vertical-align: middle;border-top:1px dashed #ccc;padding-top:20px;padding-bottom:20px;">商业性贷款金额：
											<input id="zhsyTotal" name="zhsyTotal" type="text" maxlength="10" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;"/>&nbsp;&nbsp;元
	                                   </div>
	                                   
	                                   <div style="vertical-align: middle;padding-bottom:20px;">公积金贷款金额：
											<input id="zhgjjTotal" name="zhgjjTotal" type="text" maxlength="10" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;"/>&nbsp;&nbsp;元
	                                   </div>

	                                   <div style="vertical-align: middle;padding-bottom:20px;">按揭年数：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                                       <select id="zhajns" name="zhajns" size="1" style="width:154px;height:33px;margin:0;border:1px solid #dfdfdf;" onchange="showZhgjjll(this.value)">
	                                           <option value="12">1年(12期)</option>
	                                           <option value="24">2年(24期)</option>
	                                           <option value="36">3年(36期)</option>
	                                           <option value="48">4年(48期)</option>
	                                           <option value="60">5年(60期)</option>
	                                           <option value="72">6年(72期)</option>
	                                           <option value="84">7年(84期)</option>
	                                           <option value="96">8年(96期)</option>
	                                           <option value="108">9年(108期)</option>
	                                           <option value="120">10年(120期)</option>
	                                           <option value="132">11年(132期)</option>
	                                           <option value="144">12年(144期)</option>
	                                           <option value="156">13年(156期)</option>
	                                           <option value="168">14年(168期)</option>
	                                           <option value="180">15年(180期)</option>
	                                           <option value="192">16年(192期)</option>
	                                           <option value="204">17年(204期)</option>
	                                           <option value="216">18年(216期)</option>
	                                           <option value="228">19年(228期)</option>
	                                           <option value="240">20年(240期)</option>
	                                           <option value="300">25年(300期)</option>
	                                           <option value="360" selected="true">30年(360期)</option>
	                                       </select>
	                                   </div>
	                                   
	                                   <div style="padding-bottom:20px;">
	                                       <div>商业贷款利率：&nbsp;&nbsp;&nbsp;
	                                           <select id="zhsyll" name="zhsyll" style="width:240px;height:33px;margin:0;border:1px solid #dfdfdf;" onchange="showZhsyll(this.value)">
	                                               <option value="1">最新基准利率7折</option>
	                                               <option value="2">最新基准利率8折</option>
	                                               <option value="3">最新基准利率8.5折</option>
	                                               <option value="4">最新基准利率9折</option>
	                                               <option value="5" selected="true">最新基准利率</option>
	                                               <option value="6">最新基准利率1.1倍</option>
	                                               <option value="7">最新基准利率1.2倍</option>
	                                               <option value="8">最新基准利率1.3倍</option>
	                                           </select>
	                                           <input id="zhsyllInput" style="width:50px;height:33px;margin:0;border:1px solid #dfdfdf;" maxlength="6" type="text" readonly="readonly"/>&nbsp;%
	                                       </div>
	                                   </div>
	                                   
	                                   <div style="padding-bottom:20px;">
	                                       <div>公积金贷款利率：
	                                           <input id="zhgjjllInput" style="width:50px;height:33px;margin:0;border:1px solid #dfdfdf;" maxlength="6" type="text" readonly="readonly"/>&nbsp;%
	                                       </div>
	                                   </div>
	                                   
		                               <div class="form-btn" style="padding:100px 0 0;">
	                                       <button class="btn dark" style="width:115px;height:43px;" type="button" onclick="calcGroup()">计 算</button>
	                                       <button type="button" class="btn" style="width:115px;height:43px;" onclick="resetGroup()">重 置</button>
	                                   </div>
                                   </td>
                                   
                                   <td width="10%" valign="middle"></td>
                                   <td width="50%" valign="top">
                                       <div style="background:#f6f6f6;padding:20px;width:370px;">
	                                       <div>
	                                            <ul>
	                                                <li>贷款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="zhTotal" name="zhTotal" type="text" readonly="true" />&nbsp;元</li>
	                                                <li style="margin-top:20px;">贷款月数：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="zhMonths" name="zhMonths" type="text" readonly="true" />&nbsp;月</li>
	                                            </ul>
	                                       </div>
                                       </div>
                                       <div style="background:#f6f6f6;padding:20px;padding-top:0;width:370px;">
                                       <div style="color:#00a65a;">等额本息</div>
                                            <div>
                                                 <ul>
                                                 	 <li style="margin-top:20px;">还款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="zhbxPayTotal" name="zhbxPayTotal" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">支付利息：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="zhbxlxPayTotal" name="zhbxlxPayTotal" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">月均还款：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="zhbxAvgPay" name="zhbxAvgPay" type="text" readonly="true"/>&nbsp;元</li>
                                                 </ul>
                                            </div>
                                       </div>
                                       
                                       <div style="background:#f6f6f6;padding:20px;padding-top:0;width:370px;">
                                       <div style="color:#00a65a;">等额本金</div>
                                            <div>
                                                 <ul>
                                                 	 <li style="margin-top:20px;">还款总额：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="zhbjPayTotal" name="zhbjPayTotal" type="text" readonly="true" />&nbsp;元</li>
                                                     <li style="margin-top:20px;">支付利息：&nbsp;<input style="width:220px;height:33px;margin:0;border:1px solid #dfdfdf;" id="zhbjlxPayTotal" name="zhbjlxPayTotal" type="text" readonly="true"/>&nbsp;元</li>
                                                     <li style="margin-top:20px;">月均金额：&nbsp;<textarea style="width:220px;margin:0;border:1px solid #dfdfdf;" id="zhbjAvgPay" name="zhbjAvgPay" rows="6" cols="4" readonly="true"></textarea></li>
                                                 </ul>
                                            </div>
                                       </div>
                                   </td>
                              </tr>
                           </table>						
                     </form>
                  </div>
               </section>
            </section>
		</div>
	</section>
</section>
<%@ include file="/WEB-INF/web/include/foot.jsp"%>