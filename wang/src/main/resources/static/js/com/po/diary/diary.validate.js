$(document).ready(function() {

	// $.validator.addMethod("otherDeptDutyCla", function(value, element) {
	// return !this.optional(element);
	// }, "必填信息");
	//
	// $.validator.addMethod("otherDeptNoCla", function(value, element) {
	// return !this.optional(element) && /^([+-]?)\d*\.?\d+$/.test(value);
	// }, "请输入6位以内的数字");

//	//初始化校验方法  保存
//	$("#leaderIdeaForm").validate({
//		 rules: {
//			 content: 
//			   {
//				    required: true,
//				    maxlength: 150,
//				    specialChar: true
//			   }
//		 }
//	});	
	
	// 初始化校验方法
	$("#diaryForm").validate({
		rules : {
			title : {
				required : true,
				maxlength : 125,
				minlength : 2
			},
			starttime : {
				required : true,
				dateISO : true

			},
			endtime : {
				required : true,
				dateISO : true
			},
			content : {
				required : true,
				maxlength : 2000,
				minlength : 2
			},
			periodStartdate : {
				required : false,
				dateISO : true
			},
			periodEnddate : {
				required : false,
				dateISO : true
			},
			annoContent: 
			{
			    required: true,
			    maxlength: 150,
			    specialChar: true
			},
			replayAnno: 
			{
				required: true,
			    maxlength: 150,
			    specialChar: true
			}
		},
		messages : {
			title : {
				required : "请填写日程标题"
			},
			content : {
				required : "请填写日程内容"
			}
		}
	});

});