$(document).ready(function(){
	//初始化校验方法
	$("#newTaskForm").validate({
		ignore: ".ignore",//忽略某些元素不验证
        rules: {
        	taskName: 
		   {
			    required: true,
			    maxlength: 60,
			    minlength: 1,
			    specialChar: true
		   },
//		   taskWorkType: 
//		   {
//			    required: true
//		   },
//		   taskOrigin: 
//		   {
//			    required: true
//		   },
		   taskImpCode: 
		   {
			    required: true
		   },
		   sponsorId: 
		   {
			    required: true
		   },
		   directorId: 
		   {
			   // required: true
			   validSelect2 : "showDirectorIdTree"
		   },
//		   prticipantsId: 
//		   {
//			    required: true
//		   },
		   content: 
		   {
			    required: true,
			    maxlength: 150,
			    minlength: 1,
			    specialChar: true
		   },
		   standard: 
		   {
			    required: false,
			    maxlength: 150,
			    specialChar: true
		   },
		   startTime: 
		   {
			    required: true
		   },
		   endTime: 
		   {
			    required: true
		   },
		   reportDay:
		   {
			   maxlength:3,
			   digits:true
		   }	   
	    },	
	    messages: {

		}
	});
});