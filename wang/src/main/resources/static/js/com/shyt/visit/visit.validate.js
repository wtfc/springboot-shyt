$(document).ready(function(){
//初始化校验方法
	$("#visitForm").validate({
		ignore: ".ignore",
        rules: {
        	companyName: 
		   {
			    required: true,
			    maxlength:30,
		   },
		   companyAddress:{
			   required: true,
			   maxlength:50,
		   },
		   visitDate: 
		   {
			    required: true,
		   },
		   visitMode:{
			   required: true,
			   maxlength:10,
		   },
		   returnName:{
			   required: true,
			   maxlength:10,
		   },
		   returnDept:{
			   required: true,
			   maxlength:30,
		   },
		   visitStatus: 
		   {
			    required: true,
			    maxlength:10,
		   },
		   personName:{
			   required: true,
			    maxlength:10,
		   },
		   position:{
			   required: true,
			    maxlength:10, 
		   },
		   contactWay:{
			   required: true,
			   maxlength:11,
			   number:true,
			   phone:true,
		   },
		   status: 
		   {
			    required: false,
		   },
		   visitPeople:{
			   required: true,
			   maxlength:30,
		   },
		   visitPay:{
			   required: false,
			   number:true,
		   },
		   contentMain:{
			   required: false,
			   
		   },
		   visitFind:{
			   required: false,
			    
		   },
		   solutionReplation:{
			   required: false,
			   
		   },
		   customerJudge:{
			   required: false,
			   
		   },
		   contentAccess:{
			   required: false,
			   
		   },
		   visitProblem:{
			   required: false,
			   
		   },
		   solutionDispose:{
			   required: false,
			   
		   },
		   customerEvaluation:{
			   required: false,
			   
		   },
		   contentComment:{
			   required: false,
			   
		   },
		   visitTheProblem:{
			   required: false,
			   
		   },
		   solutionSlove:{
			   required: false,
			   
		   },
		   customerReviews:{
			   required: false,
			   
		   },
		   substanceOne:{
			   required: false,
			   
		   },
		   involvoedBranch:{
			   required: false,
			   maxlength:50,
		   },
		   schemePlan:{
			   required: false,
			   
		   },
		   replyAnswer:{
			   required: false,
			   maxlength:10,
		   },
		   customTickling:{
			   required: false,
			   
		   },
		   substanceTwo:{
			   required: false,
			   
		   },
		   involvoedDivision:{
			   required: false,
			   maxlength:50,
		   },
		   schemeBlue:{
			   required: false,
			   
		   },
		   replyRestore:{
			   required: false,
			   maxlength:10,
		   },
		   customFeed:{
			   required: false,
			   
		   },
		   substanceThree:{
			   required: false,
			   
		   },
		   involvoedSection:{
			   required: false,
			   maxlength:50,
		   },
		   schemeProject:{
			   required: false,
			   
		   },
		   replyReflext:{
			   required: false,
			   maxlength:10,
		   },
		   customCouple:{
			   required: false,
			   
		   },
		   serviceStatus:{
			   required: false,
			   maxlength:300,
		   },
		   /*
		   visitPrice: 
		   {
			    required: false,
			    number: true,
		   },
		   visitPay: 
		   {
			    required: false,
			    number: true,
		   },
		   remark: 
		   {
			    required: true,
		   },
		   visit: 
		   {
			    required: true,
		   },*/
	    },	
	    errorPlacement:function(error,element){//第一个参数是错误的提示文字，第二个参数是当前输入框
	    	element.closest("td").append(error);
	        //error.appendTo(element.siblings("span"));　　//用的是jQuery，这里设置的是，错误提示文本显示在当前文本框的兄弟span中
	    }
	});
});