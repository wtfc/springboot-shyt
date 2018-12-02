$(document).ready(function(){
	//初始化校验方法  保存
	$("#leaderIdeaForm").validate({
		 rules: {
			 content: 
			   {
				    required: true,
				    maxlength: 150,
				    specialChar: true
			   }
		 }
	});	
	//提交回复
	$("#leaderIdeaReplayForm").validate({
		 rules: {
			   replayAnno: 
			   {
				    required: true,
				    maxlength: 150,
				    specialChar: true
			   }
		 }
	});	
	
	/**日程单独做的验证，若此处有更改，请提醒日程同步更改*/
});