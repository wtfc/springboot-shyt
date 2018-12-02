/**
 * 
 */
var pur_req={};

pur_req.subState = false;
pur_req.itemIndex=0;
pur_req.itemPriceIndex=0;
pur_req.itemTemplate;
pur_req.itemPriceTemplate;
pur_req.itemPriceTemplate2;
pur_req.options;

// 动态添加行 start
/**
 * 添加项目行
 */
pur_req.addItem=function(){
	$(pur_req.itemTemplate(pur_req.itemIndex++,"","","","","","","","","")).appendTo("#itemBody");
};
/**
 * 添加询价行
 */
pur_req.addItemPrice=function(){
	$(pur_req.itemPriceTemplate(pur_req.itemIndex,pur_req.itemPriceIndex++,pur_req.options,"","","","","")).appendTo("#itemPriceBody");
};
/**
 * 添加行政询价行
 */
pur_req.addItemPrice2=function(){
	$(pur_req.itemPriceTemplate2(pur_req.itemIndex,pur_req.itemPriceIndex++,pur_req.options,"","","","","")).appendTo("#itemPriceBody2");
};
/**
 * 项目列表：名称字段监听
 */
pur_req.itemClickListener=function(e){
	var evt = e||window.event;
	var eventTarget = evt.srcElement?evt.srcElement:evt.target;
	if($(eventTarget).is('input')){
		var parentTr=$(eventTarget).closest('tr');
		if(parentTr.next('tr').size()==0 ){
			pur_req.addItem();
		}
	}
};
/**
 * 项目询价列表：供应商字段监听
 */
pur_req.itemPriceClickListener=function(e){
	var evt = e||window.event;
	var eventTarget = evt.srcElement?evt.srcElement:evt.target;
	if($(eventTarget).is('input')){
		var parentTr=$(eventTarget).closest('tr');
		if(parentTr.next('tr').size()==0 ){
			pur_req.addItemPrice();
		}
	}
	
};
/**
 * 项目行政询价列表：供应商字段监听
 */
pur_req.itemPriceClickListener2=function(e){
	var evt = e||window.event;
	var eventTarget = evt.srcElement?evt.srcElement:evt.target;
	if($(eventTarget).is('input')){
		var parentTr=$(eventTarget).closest('tr');
		if(parentTr.next('tr').size()==0 ){
			pur_req.addItemPrice2();
		}
	}
	
};

/**
 * 动态处理询价行的下拉框备选项
 */
pur_req.procSelection=function(){
	pur_req.options='<option value="">-请选择-</option>';
	var items=$('#itemBody tr');
	$.each(items,function(i,n){
		var itemName=$(n).find('input[id*=itemName]');
		var model=$(n).find('input[id*=model]');
		var name=itemName.val();
		var id=itemName.attr('id');
		var model=model.val();
		var index=id.match(/\d{1,}/);
		
		if(name!=""&&name.length>0){
			pur_req.options+=('<option value="'+index+'">'+name+'  '+model+'</option>');
		}
	});
	$('#itemPriceBody select').html(pur_req.options);
	$('#itemPriceBody2 select').html(pur_req.options);
};

/**
 * 下拉框值改变事件
 */
pur_req.selectChange=function(e){
	var evt = e||window.event;
	var eventTarget = evt.srcElement?evt.srcElement:evt.target;
	//如果选择的值不为空，要变更输入框的id/name索引号
	if($(eventTarget).is('select') && $(eventTarget).val()!=""){
		var selectVal=$(eventTarget).val();
		var parentTr=$(eventTarget).closest('tr');
		var input=parentTr.find('input');
		$.each(input,function(i,element){
			var ele=$(element);
			var id=ele.attr('id');
			var name=ele.attr('name');
			id=id.replace(/\d{1,}/,selectVal);
			ele.attr('id',id);
			
			name=name.replace(/\d{1,}/,selectVal);
			ele.attr('name',name);
		});
	}
};

pur_req.deleteItem=function(e){
	var evt = e||window.event;
	var eventTarget = evt.srcElement?evt.srcElement:evt.target;
	
	$(eventTarget).closest('tr').remove();
};

// 动态添加行 end
 

pur_req.init=function(){
	formDetail.initForm();
};

function insert(status,jumpFun) {

	
	var result = true;
	if (pur_req.subState)
		return;
	pur_req.subState = true;

		var url = getRootPath() + "/pur/req/save.action?time=" + new Date();

		var formData = $("#toPurchaseApplyForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});
		
//		pur_req.addFormParameters(formData);
		// 添加其他表单信息
		jQuery.ajax({
			url : url,
			type : 'Post',
			async:false,
			data :  formData,
			success : function(data) {
				
				if (data.success == "true") {
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback : function(){
							pur_req.subState = false;
							jumpFun();
						}
					});
				} else {
					if(data.labelErrorMessage){
						showErrors("toPurchaseApplyForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
					pur_req.subState = false;
					$("#token").val(data.token);
				}
			},
			error : function() {
				pur_req.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	
	return result;
}

function validateForm() {
	return $("#toPurchaseApplyForm").valid() && dynamicTableValid.valid("itemAutoTable","itemBody") && dynamicTableValid.valid("itemPriceAutoTable","itemPriceBody") && dynamicTableValid.valid("itemPriceAutoTable2","itemPriceBody2");
}

function validateFormFail() {
	pur_req.subState = false;
	msgBox.info({
		content : $.i18n.prop("JC_SYS_067"),
	});
	
}

function update(status,jumpFun) {

	
	var result = true;
	if (pur_req.subState)
		return;
	pur_req.subState = true;

		var url = getRootPath() + "/pur/req/update.action?time=" + new Date();

		var formData = $("#toPurchaseApplyForm").serializeArray();
		formData.push({"name": "token", "value": $("#token").val()});
		
//		pur_req.addFormParameters(formData);
		// 添加其他表单信息
		jQuery.ajax({
			url : url,
			type : 'Post',
			async:false,
			data :  formData,
			success : function(data) {
				
				if (data.success == "true") {
					msgBox.tip({
						type:"success",
						content: data.successMessage,
						callback : function(){
							pur_req.subState = false;
							jumpFun();
						}
					});
				} else {
					if(data.labelErrorMessage){
						showErrors("toPurchaseApplyForm", data.labelErrorMessage);
					} else{
						msgBox.info({
							content: data.errorMessage
						});
					}
					pur_req.subState = false;
					$("#token").val(data.token);
				}
			},
			error : function() {
				pur_req.subState = false;
				msgBox.tip({
					content: $.i18n.prop("JC_SYS_002")
				});
			}
		});
	
	return result;
}

pur_req.get = function(){
	var data = $("#jsonData").val();
	if(data != null && data != ""){
		data = eval("("+data+")");
		if(data){
			$("#toPurchaseApplyForm").fill(data);
			$("#applierName").text(data.applierValue);
			$("#applyDeptName").text(data.applyDeptValue);
			$("#createDate").text(data.createDate);
			$("#applyType1").val(data.applyType);
			$("#budget1").val(data.budget);
			$("#isGeneral1").val(data.isGeneral);
			$('#itemBody').empty();
			$('#itemPriceBody').empty();
			$('#itemPriceBody2').empty();
			pur_req.options='<option value="">-请选择-</option>';
			$(data.reqItems).each(function(i,o){
				pur_req.options += "<option value='"+i+"'>"+o.itemName+" "+o.model+"</option>";
			});
			$(data.reqItems).each(function(i,o){
				pur_req.itemIndex=i+1;
				
				$(pur_req.itemTemplate(i,o.itemName==null?"":o.itemName,o.model==null?"":o.model,o.quantity==null?"":o.quantity,o.use==null?"":o.use,o.remark==null?"":o.remark,o.finalVendor==null?"":o.finalVendor,o.finalPrice==null?"":o.finalPrice,o.id==null?"":o.id,o.modifyDate==null?"":o.modifyDate)).appendTo("#itemBody");
				$(o.reqPrices).each(function(index,obj){
					if(obj.type == 0){
						pur_req.itemPriceIndex=index+1;
						var tr = $(pur_req.itemPriceTemplate(i,index,pur_req.options,obj.vendor==null?"":obj.vendor,obj.price==null?"":obj.price,obj.linkman==null?"":obj.linkman,obj.phone==null?"":obj.phone,obj.id==null?"":obj.id));
						$(tr).find("select").eq(0).val(i);
						$(tr).appendTo("#itemPriceBody");
					} else if(obj.type == 1) {
						pur_req.itemPriceIndex=index+1;
						var tr = $(pur_req.itemPriceTemplate2(i,index,pur_req.options,obj.vendor==null?"":obj.vendor,obj.price==null?"":obj.price,obj.linkman==null?"":obj.linkman,obj.phone==null?"":obj.phone,obj.id==null?"":obj.id));
						$(tr).find("select").eq(0).val(i);
						$(tr).appendTo("#itemPriceBody2");
					}
				});
			});
		}
	}
};

pur_req.select=function(e){
	
	var evt = e||window.event;
	var eventTarget = evt.srcElement?evt.srcElement:evt.target;
	//如果选择的值不为空，要变更输入框的id/name索引号
	if($(eventTarget).is('a')){
		var parentTr=$(eventTarget).closest('tr');
		var input=parentTr.find('input');
		var vender = $(input).eq(1);
		var i = $(vender).attr("id").substring(8,9);
		var price = $(input).eq(2);
		var input2 = $("#itemBody").find("tr").eq(i).find("input");
		$(input2).eq(5).val($(vender).val());
		$(input2).eq(6).val($(price).val()*$(input2).eq(2).val());
	}
};


$(document).ready(function(){
	pur_req.itemTemplate = jQuery.validator.format($.trim($("#itemTemplate").val()));
	pur_req.itemPriceTemplate = jQuery.validator.format($.trim($("#quatationTemplate").val()));
	pur_req.itemPriceTemplate2 = jQuery.validator.format($.trim($("#quatationTemplate2").val()));
	
	pur_req.addItem();
	pur_req.addItemPrice();
	pur_req.addItemPrice2();
	
//	$('#itemBody').click(pur_req.itemClickListener);
//	$('#itemPriceBody').click(pur_req.itemPriceClickListener);
//	$('#itemPriceBody2').click(pur_req.itemPriceClickListener2);
	pur_req.get();
	pur_req.init();
	
	//流程变量值传递
	$("#applyType").on("change",function(){
		$("#applyType1").val(this.value);
	});
	$("#budget").on("change",function(){
		$("#budget1").val(this.value);
	});
	$("#isGeneral").on("change",function(){
		$("#isGeneral1").val(this.value);
	});
});
