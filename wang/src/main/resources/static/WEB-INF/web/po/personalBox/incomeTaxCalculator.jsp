<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>
 <style type="text/css">
    .tax-box{
      margin:0 auto;
      width:330px;
    }
    .money{
      width: 330px;
      margin: 0 20px 10px 0;
    }
    .money span{
      width:110px;
      display: inline-block;
      text-align:right;
      padding-right:10px;
    }
    .money input,.money select{
      margin-bottom: 0;
      height: 33px;
      width: 183px;
    }
    button.starcount{
      width:100px;
      height:35px;
      line-height:35px;
      text-align:center;
      color:#fff;
      background:#66cc00;
      padding:0;
      border:1px solid #339933;
      margin-right:10px;
      float: right;
    }
    button.reset{
      width: 65px;
      height: 35px;
      line-height: 35px;
      border:1px solid #dfdfdf;
      background: #fff;
      color: #555;
      float: right;
      margin-right:10px;
    }
    .tax-table {
      border: 1px solid #ccc;
    }
    .tax-table th,.tax-table td{
      text-align:center;
      border-left: 1px solid #ccc;
      border-top: 1px solid #ccc;
      height: 35px;
    }
    .tax-table th{
       background: #f7f7f7;
      font-weight: normal;
    }
    .tax-table th:first-child,.tax-table td:first-child{
      text-align:right;
      height: 35px;
      padding-right:20px;
      width:116px;
      background: #f7f7f7;
      border-left: none;
    }
    #linkman-2 .tax-table td:first-child{
      width: 200px;
    }
  </style>
<script type="text/javascript">    
$().ready(function(){
	$("#txtIncome").focus();
}) 

function btnCalc_onClick()
{
    clearResult();
    var income = parseFloat($("#txtIncome").val());
    if(isNaN(income) || income < 0) {
    	msgBox.tip({
			type: "fale",
			content: $.i18n.prop("JC_OA_PO_052")
		});
        $("#txtIncome").focus();
        return;
    }
    $("#txtIncome").val(income); 
    
    var insure = parseFloat($("#txtInsure").val());
    if(isNaN(insure) || insure < 0) {
    	msgBox.tip({
			type: "fale",
			content: $.i18n.prop("JC_OA_PO_053")
		});
        $("#txtInsure").focus();
        return;
    }
    $("#txtInsure").val(insure);   
    var baseLine=$("#baseLine").val();
    
    var taxableIncome = income - insure - baseLine;
    if(taxableIncome <=0){
    	msgBox.tip({
			type: "fale",
			content: $.i18n.prop("JC_OA_PO_054")
		});
        $("#txtIncome").focus();
        return; 
    }
    
    var R,Q;
    var A=taxableIncome;
    A=A.toFixed(2);
    if(A<=1500){R=0.03;Q=0;}
    else if(A>1500 && A<=4500){R=0.1;Q=105}
    else if(A>4500 && A<=9000){R=0.2;Q=555}
    else if(A>9000 && A<=35000){R=0.25;Q=1005}
    else if(A>35000 && A<=55000){R=0.3;Q=2755}
    else if(A>55000 && A<=80000){R=0.35;Q=5505}
    else{R=0.45;Q=13505;} 
    var tax=taxableIncome * R -Q;
    var realIncome=income - insure - tax;            

    $("#txtTax").html(tax.toFixed(2)+" 元");
    $("#txtRealIncome").html(realIncome.toFixed(2)+" 元");
}
function btnReset_onClick()
{
    clearResult();
    $("#txtInsure").val("");
    $("#baseLine").val(3500);
    $("#txtIncome").val("");
    $("#txtIncome").focus();    
    
}
function clearResult()
{
    $("#txtTax").html("");
    $("#txtRealIncome").html("");
}
    </script>
	<section class="jcGOA-wrap">
		<section class="scrollable padder" id="scrollable">
			<header class="con-header pull-in" id="header_1">
				<div class="con-heading fl" id="navigationMenu">
					<h1></h1>
					<div class="crumbs"></div>
				</div>
			</header>
			<form class="clearfix m-t-md">
				<section class="panel">
					<div class="tax-box">
						<h2 class="panel-heading clearfix">请您填写</h2>
						<div class="clearfix m-l-md m-t-md">
							<div class="money">
								<span>收入金额</span> <input type="text" name="txtIncome"
									id="txtIncome" maxlength="10"> 元
							</div>
							<div class="money">
								<span>各项社会保险费</span> <input type="text" name="txtInsure"
									id="txtInsure" maxlength="8"> 元
							</div>
							<div class="money">
								<span>起征额</span> 
								<select id="baseLine">
                                        <option value="3500" selected="selected">3500 </option>
                                        <option value="4800">4800 </option>
                                </select> 元
							</div>
							<div>
								<button type="button" class="reset" onclick="btnReset_onClick()">重置</button>
								<button type="button" class="starcount"
									onclick="btnCalc_onClick()">开始计算</button>
							</div>
						</div>
						<h2 class="panel-heading clearfix">查询结果</h2>
						<table width="300" class="tax-table m-l-md m-t-md m-b-md">
							<tbody>
								<tr>
									<td>应缴税款</td>
									<td id="txtTax"></td>
								</tr>
								<tr>
									<td>税后收入</td>
									<td id="txtRealIncome"></td>
								</tr>
							</tbody>
						</table>
					</div>
				</section>
			</form>
		</section>
	</section>

<%@ include file="/WEB-INF/web/include/foot.jsp"%>