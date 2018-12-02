<%@ page contentType="text/html;charset=UTF-8" %>

</body>
<script type="text/javascript">
$(document).ready(function(){
	ie8StylePatch();
	iePlaceholderPatch();
	searchConditionControl();
	openIframeMenu();
	$(document).on("click",hiddenOtherModules);
	$(document).on("keydown",function(e){
		if(e.which==27){
			$(".bootstrap-datetimepicker-widget").hide();
		}
		else if (e.keyCode == 8 && e.target.tagName != "INPUT" && e.target.tagName != "TEXTAREA"){
			return false;
		}
	});
});

</script>
</html>
