$(document).ready(function(){
	
	$.validator.addMethod("titleFormate", function(value, element) {
		var formate = /^[^`~!@#$%^&*()+=|\\\][\]\{\}:;'\,.<>\/?]{1}[^`~!@$%^&()+=|\\\][\]\{\}:;'\,.<>?]$/; 
		//alert(formate.test(value));
		var reg = new RegExp('^[^\\\\\\/:*?\\"<>|]+$');// 转义 \ 符号也不行
		//alert(reg.test("新建文件\\夹")); // 弹出 true
		return reg.test(value);
		//return true;\\/:*?"<>|
	}, "标题含有非法字符");

//初始化校验方法
	$("#receiveSimulationForm").validate({
		
		ignore: ".ignore",
        rules: {
        	seqValue: 
        	{
        		required: true,
        		maxlength: 50,
			    remote:{
                    url: getRootPath()+"/doc/receive/checkSeqValue.action",
                    type: "get",
                    dataType: 'json',
                    data: {
                        'seqValue': function(){return $("#seqValue").val();},
                        'seqValueOld': function(){return $("#seqValueOld").val();},
                        'seqId': function(){return $("#seqId").val();}
                    }
			    }
        	},
			title: 
			{
				required: true,
				maxlength: 100,
				fileName:true
			},
			suggestContent: 
			{
				required: true,
				maxlength: 1500
			},
			sendDept: 
			{
				required: true,
				maxlength: 80,
				fileName:true
			},
			total: 
			{
				maxlength: 8,
				digits:true
			},
			receiveDate: 
			{
				required: true
			},
			contractorsId: 
			{
				//required: true
				validSelect2:'contractorsTree'
			},
			substitusUserId: 
			{
				//required: true
				validSelect2:'substitusUserTree'
			}
	    },
	    messages: {
	    	seqValue: {remote: "编号已存在"}
		}
	});
});