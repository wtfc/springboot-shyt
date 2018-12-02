//初始化方法
var toaFinanceMainModule = {};
var customerPeople = {};
//重复提交标识
toaFinanceMainModule.subState = false;
//获取修改信息
toaFinanceMainModule.get = function (id) {
	$.ajax({
		type : "GET",
		url : getRootPath()+"/finance/toaFinanceMain/get.action",
		data : {"id": id},
		dataType : "json",
		success : function(data) {
			if (data) {
				//清除验证信息
				hideErrorMessage();
				toaFinanceMainModule.clearForm();
				$("#toaFinanceMainForm").fill(data);
			}
		}
	});
};
//添加修改公用方法
toaFinanceMainModule.saveOrModify = function (hide) {
	if(toaFinanceMainModule.subState)return;
		toaFinanceMainModule.subState = true;
	if ($("#toaFinanceMainForm").valid()) {
		var url = getRootPath()+"/finance/toaFinanceMain/save.action";
		if ($("#id").val() != 0) {
			url = getRootPath()+"/finance/toaFinanceMain/update.action";
		}
		var formData = $("#toaFinanceMainForm").serializeArray();
		jQuery.ajax({
			url : url,
			type : 'POST',
			cache: false,
			data : formData,
			success : function(data) {
				toaFinanceMainModule.subState = false;
				if(data.success == "true"){
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback:function(){
							loadrightmenu("/finance/toaFinanceMain/manage.action","","/finance/toaFinanceMain/manage.action");
						}
					});
					$("#token").val(data.token);
				} else {
					if(data.labelErrorMessage){
						showErrors("monitorForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							type:"fail",
							content: data.errorMessage
						});
					}
					$("#token").val(data.token);
				}
			},
			error : function() {
				toaFinanceMainModule.subState = false;
				msgBox.tip({
					type:"fail",
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	} else {
		toaFinanceMainModule.subState = false;
		msgBox.info({
			content:$.i18n.prop("JC_SYS_067")
		});
	}
};
//清空表单数据
toaFinanceMainModule.clearForm = function(){
	$('#toaFinanceMainForm')[0].reset();
	hideErrorMessage();
};
//提取公司名称
toaFinanceMainModule.getWorkTask=function(){
	customerPeople.backFnServerParams = function(aoData){
		 getTableParameters(customerPeople.backTable, aoData);
		 var companyName = $("#customerForm #companyName1").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	customerPeople.backColumns = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"toaFinanceMainModule.assignment('"+full.id+"','"+full.companyName+"','"+full.department+"','"+full.sale+"','"+full.tradeUser+"','"+full.startIntel+"')\" />";
	    }},
	    {mData: "companyName"},
	    {mData: "department"},
	    {mData: "sale"},
	    {mData: "tradeUser"}
	 ];
	if (customerPeople.backTable == null) {
		customerPeople.backTable = $('#issuedTaskTable').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/shyt/toaShytCustomer/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": customerPeople.backColumns,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				customerPeople.backFnServerParams(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4]}]
		} );
	} else {
		customerPeople.backTable.fnDraw();
	}
};
toaFinanceMainModule.assignment=function(a,b,c,d,e,f){
	$("#companyId").val(a);
	$("#companyName").val(b);
	$("#department").val(c);
	$("#sale").val(d);
	$("#maintenanSale").val(e);
	$("#startIntel").val(f);
};
toaFinanceMainModule.closeWin=function(){
	$("#companyId").val("");
	$("#companyName").val("");
	$("#department").val("");
	$("#sale").val("");
	$("#maintenanSale").val("");
	$("#startIntel").val("");
	toaFinanceMainModule.getWorkTask();
};
toaFinanceMainModule.queryReset = function(){
	$('#customerForm')[0].reset();
};
//日期生成
toaFinanceMainModule.dateReckon = function(dayadd){
	var nowdate = new Date();
	nowdate = nowdate.valueOf();
	nowdate = nowdate - dayadd * 24 * 60 * 60 * 1000;
	nowdate = new Date(nowdate);
	
	var dict = {   
	    "yyyy": nowdate.getFullYear(),   
	    "M": nowdate.getMonth() + 1,   
	    "d": nowdate.getDate(),   
	    "H": nowdate.getHours(),   
	    "m": nowdate.getMinutes(),   
	    "s": nowdate.getSeconds(),   
	    "MM": ("" + (nowdate.getMonth() + 101)).substr(1),   
	    "dd": ("" + (nowdate.getDate() + 100)).substr(1),   
	}; 
	var format = "yyyy-MM-dd";
	return format.replace(/(yyyy|MM?|dd?)/g, function() {   
        return dict[arguments[0]];   
    });
};

//提取合同
toaFinanceMainModule.getWorkTask1=function(){
	customerPeople.backFnServerParams1 = function(aoData){
		 getTableParameters(customerPeople.backTable1, aoData);
		 var companyName = $("#toaFinanceMainForm #companyName").val();
		 if(companyName.length > 0){
		 aoData.push({ "name": "companyName", "value": companyName});//任务模板查询创建人为当前登录人
		 }
	};
	//显示列信息
	customerPeople.backColumns1 = [
	    {mData: "id",mRender : function(mData, type, full) {
	    		 /*"<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"toaFinanceMainModule.assignment1('"full.contractNumber+"','"+full.contractMoney+"')\" />";*/
	    		return "<input type=\"radio\" data-dismiss=\"modal\" name=\"optionsRadios\"  onclick=\"toaFinanceMainModule.assignment1('"+full.id+"','"+full.contractNumber+"','"+full.contractMoney+"','"+full.contractStatus+"','"+full.deriveNo+"','"+full.startDate+"','"+full.endDate+"')\" />";
	    }},
	    {mData: "companyName"},
	    {mData: "contractNumber"},
	    {mData: "contractMoney"},
	    {mData: "contractStatus"},
	    {mData: "deriveNo"},
	    {mData: "startDate"},
	    {mData: "endDate"}
	 ];
	if (customerPeople.backTable1 == null) {
		customerPeople.backTable1 = $('#issuedTaskTable1').dataTable( {
			"iDisplayLength": 5,//每页显示多少条记录
			"sAjaxSource": getRootPath()+"/contract/toaContractIncontract/manageList.action",
			"fnServerData": oTableRetrieveData,//查询数据回调函数
			"aoColumns": customerPeople.backColumns1,//table显示列
			"fnServerParams": function ( aoData ) {//传参
				customerPeople.backFnServerParams1(aoData);
			},
			//默认不排序列
	        "aoColumnDefs": [{"bSortable": false, "aTargets": [0,1,2,3,4,5,6,7]}]
		} );
	} else {
		customerPeople.backTable.fnDraw();
	}
};
toaFinanceMainModule.assignment1=function(c,a,b,d,e,f,g){
	$("#cardNo").val(a);
	$("#cardAmount").val(b);
	$("#contractStatus").val(d);
	$("#deriveNo").val(e);
	$("#startDate").val(f);
	$("#endDate").val(g);
};
toaFinanceMainModule.closeWin1=function(){
	$("#cardNo").val("");
	$("#cardAmount").val("");
	toaFinanceMainModule.getWorkTask1();
};


toaFinanceMainModule.date=function(){
	//清空数据
	$("#invoicesMonth12").val("");
	$("#invoicesStartdate12").val("");
	$("#invoicesEnddate12").val("");
	$("#invoicesMonth1").val("");
	$("#invoicesStartdate1").val("");
	$("#invoicesEnddate1").val("");
	$("#invoicesMonth2").val("");
	$("#invoicesStartdate2").val("");
	$("#invoicesEnddate2").val("");
	$("#invoicesMonth3").val("");
	$("#invoicesStartdate3").val("");
	$("#invoicesEnddate3").val("");
	$("#invoicesMonth4").val("");
	$("#invoicesStartdate4").val("");
	$("#invoicesEnddate4").val("");
	$("#invoicesMonth5").val("");
	$("#invoicesStartdate5").val("");
	$("#invoicesEnddate5").val("");
	$("#invoicesMonth6").val("");
	$("#invoicesStartdate6").val("");
	$("#invoicesEnddate6").val("");
	$("#invoicesMonth7").val("");
	$("#invoicesStartdate7").val("");
	$("#invoicesEnddate7").val("");
	$("#invoicesMonth8").val("");
	$("#invoicesStartdate8").val("");
	$("#invoicesEnddate8").val("");
	$("#invoicesMonth9").val("");
	$("#invoicesStartdate9").val("");
	$("#invoicesEnddate9").val("");
	$("#invoicesMonth10").val("");
	$("#invoicesStartdate10").val("");
	$("#invoicesEnddate10").val("");
	$("#invoicesMonth11").val("");
	$("#invoicesStartdate11").val("");
	$("#invoicesEnddate11").val("");
	var type=$("#payType").val();
	if(type!=""&&type!=null){
		if(type=="当月付"){
			var myDate = new Date();
		    var year = myDate.getFullYear();
		    var month = myDate.getMonth()+1;
	    	for(var i = month;i < 13; i++) {    	
	    		var firstMonth =null;
	    		var firstDay=null;
	    		var lastDay=null;
	    		if(i<10){
	    			firstMonth = year+"-0"+i;
	    			firstDay = year+"-0"+i+"-01";
	    			myDate = new Date(year,i,0);
	    			lastDay = year+"-0"+i+"-"+myDate.getDate();
	    		}else{
	    			firstMonth = year+"-"+i;
	    			firstDay = year+"-"+i+"-01";
	    			myDate = new Date(year,i,0);
	    			lastDay = year+"-"+i+"-"+myDate.getDate();
	    		}
				if(i==month){
					$("#invoicesMonth1").val(firstMonth);
					$("#invoicesStartdate1").val(firstDay);
					$("#invoicesEnddate1").val(lastDay);
				}else if(i==month+1){
					$("#invoicesMonth2").val(firstMonth);
					$("#invoicesStartdate2").val(firstDay);
					$("#invoicesEnddate2").val(lastDay);
				}else if(i==month+2){
					$("#invoicesMonth3").val(firstMonth);
					$("#invoicesStartdate3").val(firstDay);
					$("#invoicesEnddate3").val(lastDay);
				}else if(i==month+3){
					$("#invoicesMonth4").val(firstMonth);
					$("#invoicesStartdate4").val(firstDay);
					$("#invoicesEnddate4").val(lastDay);
				}else if(i==month+4){
					$("#invoicesMonth5").val(firstMonth);
					$("#invoicesStartdate5").val(firstDay);
					$("#invoicesEnddate5").val(lastDay);
				}else if(i==month+5){
					$("#invoicesMonth6").val(firstMonth);
					$("#invoicesStartdate6").val(firstDay);
					$("#invoicesEnddate6").val(lastDay);
				}else if(i==month+6){
					$("#invoicesMonth7").val(firstMonth);
					$("#invoicesStartdate7").val(firstDay);
					$("#invoicesEnddate7").val(lastDay);
				}else if(i==month+7){
					$("#invoicesMonth8").val(firstMonth);
					$("#invoicesStartdate8").val(firstDay);
					$("#invoicesEnddate8").val(lastDay);
				}else if(i==month+8){
					$("#invoicesMonth9").val(firstMonth);
					$("#invoicesStartdate9").val(firstDay);
					$("#invoicesEnddate9").val(lastDay);
				}else if(i==month+9){
					$("#invoicesMonth10").val(firstMonth);
					$("#invoicesStartdate10").val(firstDay);
					$("#invoicesEnddate10").val(lastDay);
				}else if(i==month+10){
					$("#invoicesMonth11").val(firstMonth);
					$("#invoicesStartdate11").val(firstDay);
					$("#invoicesEnddate11").val(lastDay);
				}else if(i==month+11){
					$("#invoicesMonth12").val(firstMonth);
					$("#invoicesStartdate12").val(firstDay);
					$("#invoicesEnddate12").val(lastDay);
				}
	    	}
		}else if(type=="后月付"){
			var myDate = new Date();
		    var year = myDate.getFullYear();
		    var month = myDate.getMonth()+2;
		    var firstMonth = null;
		    var firstDay = null;
		    var lastDay = null;
	    	for(var i = month;i < 13; i++) {   
	    		if(i<10){
	    			firstMonth = year+"-0"+i;
	    		}else{
	    			firstMonth = year+"-"+i;
	    		}
	    		var month1 = i-1;
	    		if(month1<10){
	    			firstDay = year+"-0"+month1+"-01";
				    myDate = new Date(year,month1,0);
				    lastDay = year+"-0"+month1+"-"+myDate.getDate();
	    		}else if(10<month1<12){
	    			firstDay = year+"-"+month1+"-01";
				    myDate = new Date(year,month1,0);
				    lastDay = year+"-"+month1+"-"+myDate.getDate();
	    		}
	    		if(month1>12){
	    			month1 = month1-12;
	    			year = year+1;
				    firstDay = year+"-0"+month1+"-01";
				    myDate = new Date(year,month1,0);
				    lastDay = year+"-0"+month1+"-"+myDate.getDate();
	    		}
	    		if(i==month){
					$("#invoicesMonth1").val(firstMonth);
					$("#invoicesStartdate1").val(firstDay);
					$("#invoicesEnddate1").val(lastDay);
				}else if(i==month+1){
					$("#invoicesMonth2").val(firstMonth);
					$("#invoicesStartdate2").val(firstDay);
					$("#invoicesEnddate2").val(lastDay);
				}else if(i==month+2){
					$("#invoicesMonth3").val(firstMonth);
					$("#invoicesStartdate3").val(firstDay);
					$("#invoicesEnddate3").val(lastDay);
				}else if(i==month+3){
					$("#invoicesMonth4").val(firstMonth);
					$("#invoicesStartdate4").val(firstDay);
					$("#invoicesEnddate4").val(lastDay);
				}else if(i==month+4){
					$("#invoicesMonth5").val(firstMonth);
					$("#invoicesStartdate5").val(firstDay);
					$("#invoicesEnddate5").val(lastDay);
				}else if(i==month+5){
					$("#invoicesMonth6").val(firstMonth);
					$("#invoicesStartdate6").val(firstDay);
					$("#invoicesEnddate6").val(lastDay);
				}else if(i==month+6){
					$("#invoicesMonth7").val(firstMonth);
					$("#invoicesStartdate7").val(firstDay);
					$("#invoicesEnddate7").val(lastDay);
				}else if(i==month+7){
					$("#invoicesMonth8").val(firstMonth);
					$("#invoicesStartdate8").val(firstDay);
					$("#invoicesEnddate8").val(lastDay);
				}else if(i==month+8){
					$("#invoicesMonth9").val(firstMonth);
					$("#invoicesStartdate9").val(firstDay);
					$("#invoicesEnddate9").val(lastDay);
				}else if(i==month+9){
					$("#invoicesMonth10").val(firstMonth);
					$("#invoicesStartdate10").val(firstDay);
					$("#invoicesEnddate10").val(lastDay);
				}else if(i==month+10){
					$("#invoicesMonth11").val(firstMonth);
					$("#invoicesStartdate11").val(firstDay);
					$("#invoicesEnddate11").val(lastDay);
				}else if(i==month+11){
					$("#invoicesMonth12").val(firstMonth);
					$("#invoicesStartdate12").val(firstDay);
					$("#invoicesEnddate12").val(lastDay);
				}
	    	}
		}else if(type=="季付"){
			var myDate = new Date();
		    var year = myDate.getFullYear();
		    var month = myDate.getMonth()+1;
		    var j=0;
	    	for(var i = month;i < 13;) {
	    		var firstDay = null;
	    		var lastDay =null;
	    		var firstMonth =null;
	    		if(month<10){
	    			firstMonth = year+"-0"+month;
	    			firstDay = year+"-0"+month+"-01";
	    		}else{
	    			firstMonth = year+"-"+month;
	    			firstDay = year+"-"+month+"-01";
	    		}
			    i=i+3;
			    month=month+2;
			    if(month>12){
			    	year=year+1;
			    	var m=month-12;
			    	myDate = new Date(year,m,0);
			    	if(m<10){
			    		lastDay = year+"-0"+m+"-"+myDate.getDate();
			    	}else{
			    		lastDay = year+"-"+m+"-"+myDate.getDate();
			    	}
			    }else{
			    	myDate = new Date(year,month,0);
			    	if(month<10){
			    		lastDay = year+"-0"+month+"-"+myDate.getDate();
			    	}else{
			    		lastDay = year+"-"+month+"-"+myDate.getDate();
			    	}
			    }
				if(j==0){
					$("#invoicesMonth1").val(firstMonth);
					$("#invoicesStartdate1").val(firstDay);
					$("#invoicesEnddate1").val(lastDay);
				}else if(j==1){
					$("#invoicesMonth2").val(firstMonth);
					$("#invoicesStartdate2").val(firstDay);
					$("#invoicesEnddate2").val(lastDay);
				}else if(j==2){
					$("#invoicesMonth3").val(firstMonth);
					$("#invoicesStartdate3").val(firstDay);
					$("#invoicesEnddate3").val(lastDay);
				}else if(j==3){
					$("#invoicesMonth4").val(firstMonth);
					$("#invoicesStartdate4").val(firstDay);
					$("#invoicesEnddate4").val(lastDay);
				}else if(j==4){
					$("#invoicesMonth5").val(firstMonth);
					$("#invoicesStartdate5").val(firstDay);
					$("#invoicesEnddate5").val(lastDay);
				}
				j=j+1;
				month=month+1;
	    	}
		}else if(type=="后季度付"){
			var myDate = new Date();
		    var year = myDate.getFullYear();
		    var month = myDate.getMonth()+4;
		    var j=0;
	    	for(var i = month;i < 13;) {
	    		var firstDay = null;
	    		var lastDay =null;
	    		var firstMonth =null;
	    		if(month<10){
	    			firstMonth = year+"-0"+month;
	    			firstDay = year+"-0"+month+"-01";
	    		}else{
	    			firstMonth = year+"-"+month;
	    			firstDay = year+"-"+month+"-01";
	    		}
			    i=i+3;
			    month=month+2;
			    if(month>12){
			    	year=year+1;
			    	var m=month-12;
			    	myDate = new Date(year,m,0);
			    	if(m<10){
			    		lastDay = year+"-0"+m+"-"+myDate.getDate();
			    	}else{
			    		lastDay = year+"-"+m+"-"+myDate.getDate();
			    	}
			    }else{
			    	myDate = new Date(year,month,0);
			    	if(month<10){
			    		lastDay = year+"-0"+month+"-"+myDate.getDate();
			    	}else{
			    		lastDay = year+"-"+month+"-"+myDate.getDate();
			    	}
			    }
			    if(j==0){
					$("#invoicesMonth1").val(firstMonth);
					$("#invoicesStartdate1").val(firstDay);
					$("#invoicesEnddate1").val(lastDay);
				}else if(j==1){
					$("#invoicesMonth2").val(firstMonth);
					$("#invoicesStartdate2").val(firstDay);
					$("#invoicesEnddate2").val(lastDay);
				}else if(j==2){
					$("#invoicesMonth3").val(firstMonth);
					$("#invoicesStartdate3").val(firstDay);
					$("#invoicesEnddate3").val(lastDay);
				}else if(j==3){
					$("#invoicesMonth4").val(firstMonth);
					$("#invoicesStartdate4").val(firstDay);
					$("#invoicesEnddate4").val(lastDay);
				}else if(j==4){
					$("#invoicesMonth5").val(firstMonth);
					$("#invoicesStartdate5").val(firstDay);
					$("#invoicesEnddate5").val(lastDay);
				}
				j=j+1;
				month=month+1;
	    	}
		}else if(type=="半年付"){
			var myDate = new Date();
		    var year = myDate.getFullYear();
		    var month = myDate.getMonth()+1;
		    var j=0;
	    	for(var i = month;i < 13;) {
	    		var firstDay = null;
	    		var lastDay =null;
	    		var firstMonth =null;
	    		if(month<10){
	    			firstMonth = year+"-0"+month;
	    			firstDay = year+"-0"+month+"-01";
	    		}else{
	    			firstMonth = year+"-"+month;
	    			firstDay = year+"-"+month+"-01";
	    		}
			    month=month+5;
			    i=i+6;
			    if(month>12){
			    	year=year+1;
			    	var m=month-12;
			    	myDate = new Date(year,m,0);
			    	if(m<10){
			    		lastDay = year+"-0"+m+"-"+myDate.getDate();
			    	}else{
			    		lastDay = year+"-"+m+"-"+myDate.getDate();
			    	}
			    }else{
			    	myDate = new Date(year,month,0);
			    	if(month<10){
			    		lastDay = year+"-0"+month+"-"+myDate.getDate();
			    	}else{
			    		lastDay = year+"-"+month+"-"+myDate.getDate();
			    	}
			    }
			    if(j==0){
					$("#invoicesMonth1").val(firstMonth);
					$("#invoicesStartdate1").val(firstDay);
					$("#invoicesEnddate1").val(lastDay);
				}else if(j==1){
					$("#invoicesMonth2").val(firstMonth);
					$("#invoicesStartdate2").val(firstDay);
					$("#invoicesEnddate2").val(lastDay);
				}else if(j==2){
					$("#invoicesMonth3").val(firstMonth);
					$("#invoicesStartdate3").val(firstDay);
					$("#invoicesEnddate3").val(lastDay);
				}else if(j==3){
					$("#invoicesMonth4").val(firstMonth);
					$("#invoicesStartdate4").val(firstDay);
					$("#invoicesEnddate4").val(lastDay);
				}else if(j==4){
					$("#invoicesMonth5").val(firstMonth);
					$("#invoicesStartdate5").val(firstDay);
					$("#invoicesEnddate5").val(lastDay);
				}
				j=j+1;
				month=month+1;
	    	}
		}else if(type=="年付"){
			var myDate = new Date();
		    var year = myDate.getFullYear();
		    var month = myDate.getMonth()+1;
		    var firstMonth=null;
		    var firstDay=null;
		    var lastDay=null;
	    	if(month<10){
	    		firstMonth = year+"-0"+month;
			    firstDay = year+"-0"+month+"-01";
	    	}else{
	    		firstMonth = year+"-"+month;
			    firstDay = year+"-"+month+"-01";
	    	}
		    month=month+11;
		    if(month>12){
		    	year=year+1;
		    	var m=month-12;
		    	myDate = new Date(year,m,0);
		    	if(m<10){
		    		lastDay = year+"-0"+m+"-"+myDate.getDate();
		    	}else{
		    		lastDay = year+"-"+m+"-"+myDate.getDate();
		    	}
		    }else{
		    	myDate = new Date(year,month,0);
		    	if(month<10){
		    		lastDay = year+"-0"+month+"-"+myDate.getDate();
		    	}else{
		    		lastDay = year+"-"+month+"-"+myDate.getDate();
		    	}
		    }
			$("#invoicesMonth1").val(firstMonth);
			$("#invoicesStartdate1").val(firstDay);
			$("#invoicesEnddate1").val(lastDay);
		}else if(type=="超流量"||type=="临时扩容"||type=="一次性"){
			var myDate = new Date();
		    var year = myDate.getFullYear();
		    var month = myDate.getMonth()+1;
		    var firstMonth=null;
		    var firstDay=null;
		    var lastDay=null;
	    	if(month<10){
	    		firstMonth = year+"-0"+month;
			    firstDay = year+"-0"+month+"-01";
			    myDate = new Date(year,month,0);
		    	lastDay = year+"-0"+month+"-"+myDate.getDate();
	    	}else{
	    		firstMonth = year+"-"+month;
			    firstDay = year+"-"+month+"-01";
			    myDate = new Date(year,month,0);
		    	lastDay = year+"-"+month+"-"+myDate.getDate();
	    	}
			$("#invoicesMonth1").val(firstMonth);
			$("#invoicesStartdate1").val(firstDay);
			$("#invoicesEnddate1").val(lastDay);
		}
	};
};
function checkInt(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt1(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt2(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt3(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt4(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt5(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt6(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt7(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt8(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt9(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt10(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}
function checkInt11(){
	var money = $("#monthAmount12").val(); 
	if(money==""){
		money=0;
	}
	var money1 = $("#monthAmount1").val();
	if(money1==""){
		money1=0;
	}
	var money2 = $("#monthAmount2").val();
	if(money2==""){
		money2=0;
	}
	var money3 = $("#monthAmount3").val();
	if(money3==""){
		money3=0;
	}
	var money4 = $("#monthAmount4").val();
	if(money4==""){
		money4=0;
	}
	var money5 = $("#monthAmount5").val();
	if(money5==""){
		money5=0;
	}
	var money6 = $("#monthAmount6").val();
	if(money6==""){
		money6=0;
	}
	var money7 = $("#monthAmount7").val();
	if(money7==""){
		money7=0;
	}
	var money8 = $("#monthAmount8").val();
	if(money8==""){
		money8=0;
	}
	var money9 = $("#monthAmount9").val();
	if(money9==""){
		money9=0;
	}
	var money10 = $("#monthAmount10").val();
	if(money10==""){
		money10=0;
	}
	var money11 = $("#monthAmount11").val();
	if(money11==""){
		money11=0;
	}
	var number = parseInt(money)+parseInt(money1)+parseInt(money2)+parseInt(money3)
	+parseInt(money4)+parseInt(money5)+parseInt(money6)+parseInt(money7)+parseInt(money8)
	+parseInt(money9)+parseInt(money10)+parseInt(money11);
	$("#allMoneya").text(number);
	$("#extStr1").val(number);
}