/**
 * 住房公积金js
 * @author 刘锡来
 * @version  2014-11-19
 */
var syllArray = new Array;//商业贷款利率数组
syllArray[1] = 4.305;//最新基准利率7折
syllArray[2] = 4.92;//最新基准利率8折
syllArray[3] = 5.228;//最新基准利率8.5折
syllArray[4] = 5.535;//最新基准利率9折
syllArray[5] = 6.15;//最新基准利率
syllArray[6] = 6.765;//最新基准利率1.1倍
syllArray[7] = 7.38;//最新基准利率1.2倍
syllArray[8] = 7.995;//最新基准利率1.3倍


var ggjllArray = new Array;//公积金贷款利率数组
ggjllArray[1] = 3.75;
ggjllArray[2] = 4.25;

//商业按照面积计算
function changeCalcByArea(){
	$("#calcArea").css("display","");
	$("#calcMoney").css("display","none");
	$("#busiMoneyTotal").val("");
}

//商业按照贷款钱数计算
function changeCalcByMoney(){
	$("#calcArea").css("display","none");
	$("#calcMoney").css("display","");
	$("#price").val("");
	$("#area").val("");
}

//公积金按照面积计算
function changeCalcByGjjArea(){
	$("#calcGjjArea").css("display","");
	$("#calcGjjMoney").css("display","none");
	$("#gjjMoneyTotal").val("");
}

//公积金按照贷款钱数计算
function changeCalcByGjjMoney(){
	$("#calcGjjArea").css("display","none");
	$("#calcGjjMoney").css("display","");
	$("#gjjPrice").val("");
	$("#gjjArea").val("");
}

//贷款利率计算(商业)
function showSyll(interestRateValue) {
	switch(interestRateValue){
		case '1':{$("#syllInput").val(syllArray[1]);break;}
		case '2':{$("#syllInput").val(syllArray[2]);break;}
		case '3':{$("#syllInput").val(syllArray[3]);break;}
		case '4':{$("#syllInput").val(syllArray[4]);break;}
		case '5':{$("#syllInput").val(syllArray[5]);break;}
		case '6':{$("#syllInput").val(syllArray[6]);break;}
		case '7':{$("#syllInput").val(syllArray[7]);break;}
		case '8':{$("#syllInput").val(syllArray[8]);break;}
	}
}

//贷款利率计算(公积金)
function showGjjll(interestRateValue) {
	if(interestRateValue >= 72){
		$("#gjjllInput").val(ggjllArray[2]);
	}else{
		$("#gjjllInput").val(ggjllArray[1]);
	}
}


//贷款利率计算(组合贷款-商业)
function showZhsyll(interestRateValue) {
	switch(interestRateValue){
		case '1':{$("#zhsyllInput").val(syllArray[1]);break;}
		case '2':{$("#zhsyllInput").val(syllArray[2]);break;}
		case '3':{$("#zhsyllInput").val(syllArray[3]);break;}
		case '4':{$("#zhsyllInput").val(syllArray[4]);break;}
		case '5':{$("#zhsyllInput").val(syllArray[5]);break;}
		case '6':{$("#zhsyllInput").val(syllArray[6]);break;}
		case '7':{$("#zhsyllInput").val(syllArray[7]);break;}
		case '8':{$("#zhsyllInput").val(syllArray[8]);break;}
	}
}

//贷款利率计算(组合贷款-公积金)
function showZhgjjll(interestRateValue) {
	if(interestRateValue >= 72){
		$("#zhgjjllInput").val(ggjllArray[2]);
	}else{
		$("#zhgjjllInput").val(ggjllArray[1]);
	}
}

//商业贷款计算
function calcBusiness() {
	//验证表单
	if(!calcValid(1)){
		return;
	}
	var calcType = $('input:radio[name="calcType"]:checked').val();//计算方式
	var busiMoneyTotal = "";//贷款金额
	if(calcType == 0){//按贷款额度计算
		busiMoneyTotal = $("#busiMoneyTotal").val(); 
	}else{//按面积算
		busiMoneyTotal = $("#price").val()*$("#area").val(); 
	}
    var syajns = $("#syajns").val(); //按揭月份数
	var syll = $("#syll").val();    //利率
	var syllInput = $("#syllInput").val(); //贷款利率显示值
	var syllPercent =syllInput/100; //利率百分比
	
	//------------------------------等额本息还款 start---------------------------------------------------------
    var sybxAvgPay = getMoneyForInterest(syllPercent, busiMoneyTotal, syajns); //月均还款
    var sybxlxPayTotal = Math.round((sybxAvgPay*syajns-busiMoneyTotal)*100)/100; //支付利息
    var sybxPayTotal = (parseFloat(busiMoneyTotal) + parseFloat(sybxlxPayTotal)).toFixed(2);//还款总额
    
    $("#syTotal").val(busiMoneyTotal);//贷款总额input赋值
    $("#sybxPayTotal").val(sybxPayTotal);//还款总额input赋值
    $("#syMonths").val(syajns);//贷款月数input赋值
    $("#sybxlxPayTotal").val(sybxlxPayTotal.toFixed(2));//等额本息支付利息input赋值
    $("#sybxAvgPay").val(sybxAvgPay.toFixed(2));//等额本息月均还款input赋值
    
	//------------------------------等额本息还款 end-----------------------------------------------------------
    
	//------------------------------等额本金还款 start---------------------------------------------------------
    var sybjPayTotal = 0;//还款总额
    var sybjAvgPay = "";//每月还款额度
    var avgPay = "";
    
    for (var i = 0; i <syajns; i++) {
    	avgPay = getMoneyForCapital(syllPercent,busiMoneyTotal,syajns,i);
    	sybjPayTotal += avgPay;
        avgPay = Math.round(avgPay*100)/100;//月均还款
        sybjAvgPay +=(i+1)+"月,"+avgPay+"(元)\r";
    }
    
    $("#sybjPayTotal").val(sybjPayTotal.toFixed(2));//总贷款额度
    $("#sybjAvgPay").text(sybjAvgPay);
    $("#sybjlxPayTotal").val((sybjPayTotal-busiMoneyTotal).toFixed(2));//支付利息
	//------------------------------等额本金还款 end----------------------------------------------------------- 
}

//公积金贷款计算
function calcGjj(){
	//验证表单
	if(!calcValid(2)){
		return;
	}
	var calcGjjType = $('input:radio[name="calcGjjType"]:checked').val();//计算方式
	var gjjMoneyTotal = "";//贷款金额
	if(calcGjjType == 0){//按贷款额度计算
		gjjMoneyTotal = $("#gjjMoneyTotal").val(); 
	}else{//按面积算
		gjjMoneyTotal = $("#gjjPrice").val()*$("#gjjArea").val(); 
	}
    var gjjajns = $("#gjjajns").val(); //按揭月份数
	var gjjllInput = $("#gjjllInput").val(); //贷款利率显示值
	var gjjllPercent =gjjllInput/100; //利率百分比
	
	//------------------------------等额本息还款 start---------------------------------------------------------
    var gjjbxAvgPay = getMoneyForInterest(gjjllPercent, gjjMoneyTotal, gjjajns); //月均还款
    var gjjbxlxPayTotal = Math.round((gjjbxAvgPay*gjjajns-gjjMoneyTotal)*100)/100; //支付利息
    var gjjbxPayTotal = (parseFloat(gjjMoneyTotal) + parseFloat(gjjbxlxPayTotal)).toFixed(2);//还款总额
    
    $("#gjjTotal").val(gjjMoneyTotal);//贷款总额input赋值
    $("#gjjbxPayTotal").val(gjjbxPayTotal);//还款总额input赋值
    $("#gjjMonths").val(gjjajns);//贷款月数input赋值
    $("#gjjbxlxPayTotal").val(gjjbxlxPayTotal);//等额本息支付利息input赋值
    $("#gjjbxAvgPay").val(gjjbxAvgPay.toFixed(2));//等额本息月均还款input赋值
    
	//------------------------------等额本息还款 end-----------------------------------------------------------
    
	//------------------------------等额本金还款 start---------------------------------------------------------
    var gjjbjPayTotal = 0;//还款总额
    var gjjbjAvgPay = "";//每月还款额度
    var avgPay = "";
    
    for (var i = 0; i <gjjajns; i++) {
    	avgPay = getMoneyForCapital(gjjllPercent,gjjMoneyTotal,gjjajns,i);
    	gjjbjPayTotal += avgPay;
        avgPay = Math.round(avgPay*100)/100;//月均还款
        gjjbjAvgPay +=(i+1)+"月,"+avgPay+"(元)\r";
    }
    
    $("#gjjbjPayTotal").val(gjjbjPayTotal.toFixed(2));//总贷款额度
    $("#gjjbjAvgPay").text(gjjbjAvgPay);
    $("#gjjbjlxPayTotal").val((gjjbjPayTotal-gjjMoneyTotal).toFixed(2));//支付利息
	//------------------------------等额本金还款 end----------------------------------------------------------- 
}

//组合贷款计算
function calcGroup(){
	//验证表单
	if(!calcValid(3)){
		return;
	}
	var zhsyTotal = $("#zhsyTotal").val(); //商业贷款金额
	var zhgjjTotal = $("#zhgjjTotal").val(); //公积金贷款金额
	var zhajns = $("#zhajns").val(); //按揭月份数
	var zhsyllInput = $("#zhsyllInput").val(); //组合商业利率
	var zhsyPercent =zhsyllInput/100; //组合商业利率百分比
	var zhgjjllInput = $("#zhgjjllInput").val(); //组合公积金利率
	var zhgjjPercent =zhgjjllInput/100; //组合公积金利率百分比
	
	//------------------------------等额本息还款 start---------------------------------------------------------
	//贷款总额
	var zhTotal = parseFloat(zhsyTotal) + parseFloat(zhgjjTotal);
	//等额本息月均还款
	var zhbxAvgPay = parseFloat(getMoneyForInterest(zhsyPercent, zhsyTotal, zhajns)) +  
					 parseFloat(getMoneyForInterest(zhgjjPercent, zhgjjTotal, zhajns));
	//等额本息支付利息
	var zhbxlxPayTotal = Math.round((zhbxAvgPay*zhajns-zhTotal)*100)/100;
	//等额本息还款总额
	var zhbxPayTotal = parseFloat(zhTotal) + parseFloat(zhbxlxPayTotal);
	
	$("#zhTotal").val(zhTotal.toFixed(2));//贷款总额
    $("#zhMonths").val(zhajns);//贷款月数
    $("#zhbxPayTotal").val(zhbxPayTotal.toFixed(2));//等额本息还款总额
    $("#zhbxlxPayTotal").val(zhbxlxPayTotal);//等额本息支付利息
    $("#zhbxAvgPay").val(zhbxAvgPay.toFixed(2));//等额本息月均还款
  //------------------------------等额本息还款 end-----------------------------------------------------------

  //------------------------------等额本金还款 start---------------------------------------------------------
    var zhbjPayTotal = 0;//还款总额
    var sybjAvgPay = "";//每月还款额度    
    var avgPay = "";
    
    for (var i = 0; i <zhajns; i++) {
    	avgPay = getMoneyForCapital(zhsyPercent,zhsyTotal,zhajns,i) + getMoneyForCapital(zhgjjPercent,zhgjjTotal,zhajns,i);
    	zhbjPayTotal += avgPay;
        avgPay = Math.round(avgPay*100)/100;//月均还款
        sybjAvgPay +=(i+1)+"月,"+avgPay+"(元)\r";
    }
    
    $("#zhbjPayTotal").val(zhbjPayTotal.toFixed(2));//等额本金还款总额
    $("#zhbjlxPayTotal").val((zhbjPayTotal-zhsyTotal-zhgjjTotal).toFixed(2));//等额本金支付利息
    $("#zhbjAvgPay").text(sybjAvgPay);//等额本金支付利息
  //------------------------------等额本金还款 end-----------------------------------------------------------	
}

//点击计算按钮,验证方法
function calcValid(type){
	if(type == 1){
		var calcType = $('input:radio[name="calcType"]:checked').val();//计算方式
		var busiMoneyTotal = "";//贷款金额
		if(calcType == 0){//按贷款额度计算
			busiMoneyTotal = $("#busiMoneyTotal").val(); 
			if(isNaN(busiMoneyTotal) || parseFloat(busiMoneyTotal) <= 0 || busiMoneyTotal == "") {
		    	msgBox.tip({
					type: "fale",
					content: "贷款金额输入无效"
				});
		    	$("#busiMoneyTotal").focus();
		        return;
		    }
		}else{//按面积算
			price = $("#price").val();
			area = $("#area").val(); 
			if(isNaN(price) || parseFloat(price) <= 0 || price == "") {
		    	msgBox.tip({
					type: "fale",
					content: "单价输入无效"
				});
		    	$("#price").focus();
		        return;
		    }
			if(isNaN(area) || parseFloat(area) <= 0 || area == "") {
		    	msgBox.tip({
					type: "fale",
					content: "面积输入无效"
				});
		    	$("#area").focus();
		        return;
		    }
		}
	}
	if(type == 2){
		var calcGjjType = $('input:radio[name="calcGjjType"]:checked').val();//计算方式
		var gjjMoneyTotal = "";//贷款金额
		if(calcGjjType == 0){//按贷款额度计算
			gjjMoneyTotal = $("#gjjMoneyTotal").val(); 
			if(isNaN(gjjMoneyTotal) || parseFloat(gjjMoneyTotal) <= 0 || gjjMoneyTotal == "") {
		    	msgBox.tip({
					type: "fale",
					content: "贷款金额输入无效"
				});
		    	$("#gjjMoneyTotal").focus();
		        return;
		    }
		}else{//按面积算
			gjjPrice = $("#gjjPrice").val();
			gjjArea = $("#gjjArea").val(); 
			if(isNaN(gjjPrice) || parseFloat(gjjPrice) <= 0 || gjjPrice == "") {
		    	msgBox.tip({
					type: "fale",
					content: "单价输入无效"
				});
		    	$("#gjjPrice").focus();
		        return;
		    }
			if(isNaN(gjjArea) || parseFloat(gjjArea) <= 0 || gjjArea == "") {
		    	msgBox.tip({
					type: "fale",
					content: "面积输入无效"
				});
		    	$("#gjjArea").focus();
		        return;
		    }
		}
	}
	if(type == 3){
		var zhsyTotal = $("#zhsyTotal").val(); //商业贷款金额
		var zhgjjTotal = $("#zhgjjTotal").val(); //公积金贷款金额
		if(isNaN(zhsyTotal) || parseFloat(zhsyTotal) <= 0 || zhsyTotal == "") {
	    	msgBox.tip({
				type: "fale",
				content: "商业性贷款金额输入无效"
			});
	    	$("#zhsyTotal").focus();
	        return;
	    }
		if(isNaN(zhgjjTotal) || parseFloat(zhgjjTotal) <= 0 || zhgjjTotal == "") {
	    	msgBox.tip({
				type: "fale",
				content: "公积金贷款金额输入无效"
			});
	    	$("#zhgjjTotal").focus();
	        return;
	    }
	}
	return true;
}

//商业贷款重置
function resetBusiness(){
	$('input:radio[name="calcType"]:eq(0)').attr("checked","checked");
	$("#calcArea").css("display","none");
	$("#calcMoney").css("display","");
	$("#busiMoneyTotal").val("");
	$("#price").val("");
	$("#area").val("");
	$("#syajns option").eq(21).attr("selected",true);//按揭年数
	$("#syll option").eq(4).attr("selected",true);//贷款利率
	$("#syllInput").val(syllArray[5]);//默认12年7月6日基准利率
    $("#syTotal").val("");//贷款总额input赋值
    $("#sybxPayTotal").val("");//还款总额input赋值
    $("#syMonths").val("");//贷款月数input赋值
    $("#sybxlxPayTotal").val("");//等额本息支付利息input赋值
    $("#sybxAvgPay").val("");//等额本息月均还款input赋值
    $("#sybjPayTotal").val("");//总贷款额度
    $("#sybjAvgPay").text("");
    $("#sybjlxPayTotal").val("");//支付利息
}

//公积金贷款重置
function resetGjj(){
	$('input:radio[name="calcGjjType"]:eq(0)').attr("checked","checked");
	$("#calcGjjArea").css("display","none");
	$("#calcGjjMoney").css("display","");
	$("#gjjMoneyTotal").val("");
	$("#gjjPrice").val("");
	$("#gjjArea").val("");
	$("#gjjajns option").eq(21).attr("selected",true);//按揭年数
	$("#gjjllInput").val(ggjllArray[2]);
	
    $("#gjjTotal").val("");//贷款总额input赋值
    $("#gjjbxPayTotal").val("");//还款总额input赋值
    $("#gjjMonths").val("");//贷款月数input赋值
    $("#gjjbxlxPayTotal").val("");//等额本息支付利息input赋值
    $("#gjjbxAvgPay").val("");//等额本息月均还款input赋值
    $("#gjjbjPayTotal").val("");//总贷款额度
    $("#gjjbjAvgPay").text("");
    $("#gjjbjlxPayTotal").val("");//支付利息
}


//组合贷款重置
function resetGroup(){
	$("#zhsyTotal").val("");
	$("#zhgjjTotal").val("");
	$("#zhajns option").eq(21).attr("selected",true);
	$("#zhsyll option").eq(4).attr("selected",true);
	$("#zhgjjll option").eq(3).attr("selected",true);
	$("#zhsyllInput").val(syllArray[5]);
	$("#zhgjjllInput").val(ggjllArray[2]);
    $("#zhTotal").val("");//贷款总额input赋值
    $("#zhMonths").val("");//贷款月数input赋值
    $("#zhbxPayTotal").val("");//等额本息还款总额
    $("#zhbxlxPayTotal").val("");//等额本息支付利息
    $("#zhbxAvgPay").val("");//等额本息月均还款
    $("#zhbjPayTotal").val("");//等额本金还款总额
    $("#zhbjlxPayTotal").val("");//等额本金支付利息
    $("#zhbjAvgPay").text("");//等额本金支付利息
}




//等额本息  参数说明(年利率/总贷款金额/贷款月份)
function getMoneyForInterest(irPercent,moneyTotal,syajns) {
    var monthIrPercent = irPercent/12; //月利率
    //每月还款额=贷款本金×[月利率×（1+月利率）^还款月数]÷[（1+月利率）^还款月数—1]
    return (moneyTotal*(monthIrPercent*Math.pow(1+monthIrPercent,syajns))/(Math.pow(1+monthIrPercent,syajns)-1));
}

//等额本金  参数说明(年利率/总贷款金额/贷款月份/当前月)
function getMoneyForCapital(irPercent,moneyTotal,syajns,curMonth){
    var monthIrPercent = irPercent/12; //月利率
    var benjin_money = moneyTotal/syajns;
    //每月还款金额=(贷款本金/还款月数)+(本金—已归还本金累计额)×每月利率
    return (moneyTotal - benjin_money * curMonth) * monthIrPercent + benjin_money;
}

/**
 * 初始化方法
 */
jQuery(function($){
	$(function(){
      $('.nav-tabs a[data-toggle="tab"]').on('show.bs.tab', function (e) {
    	  if(e.target.id == "tab-1"){
    		  resetBusiness();
    	  }else if(e.target.id == "tab-2"){
    		  resetGjj();
    	  }else if(e.target.id == "tab-3"){
    		  resetGroup();
    	  }else{
    	  }
      })
	})
	$("#syllInput").val(syllArray[5]);
	$("#gjjllInput").val(ggjllArray[2]);
	$("#zhsyllInput").val(syllArray[5]);
	$("#zhgjjllInput").val(ggjllArray[2]);
});