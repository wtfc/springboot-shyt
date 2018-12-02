$(document).ready(function(){
	
//初始化校验方法
	$("#sendSimulationForm").validate({
		ignore: ".ignore",
        rules: {
        	noValue: 
        	{
        		required: true,
        		maxlength: 50,
			    remote:{
                    url: getRootPath()+"/doc/send/checkNoValue.action",
                    type: "get",
                    dataType: 'json',
                    async : false, 
                    data: {
                        'noValue': function(){return $("#noValue").val();},
                        'noValueOld': function(){return $("#noValueOld").val();},
                        'docNoId': function(){return $("#docNoId").val();}
                    }
			    }
        	},
			title: 
			{
				required: true,
				maxlength: 100,
				fileName:true
			},
			mainSendUserName: 
			{
				maxlength: 1000
			},
			remark1: 
			{
				maxlength: 255
			},
			copySendUserName: 
			{
				maxlength: 1000
			},
			docFlowType: 
			{
				required: true
			},
			docType: 
			{
				required: true
			},
			deptId: 
			{
				//required: true
				validSelect2:'deptIdTree'
			},
			substitusUserId: 
			{
				//required: true
				validSelect2:'substitusUserTree'
			},
			signer: 
			{
				required: true,
				maxlength: 64
			},
			sendTime: 
			{
				required: true
			},
			printNum: 
			{
				isPositive:true,
				maxlength: 8
			}
	    },
	    messages: {
	    	noValue: {remote: "文号已存在"}
		}
	});
});