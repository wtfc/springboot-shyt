$(document).ready(function(){
  // 提交回复
	$("#commentForm").validate({
		rules: {
			replayAnno: 
			{
				required: true,
				maxlength: 150,
				specialChar: true
			}
		}
	
	});
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
});