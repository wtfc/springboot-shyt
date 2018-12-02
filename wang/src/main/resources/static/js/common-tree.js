$(function(){
	
	var content = $("#content").height();
    var headerHeight_1 = $('#header_1').height() || 0;
    var headerHeight_2 = $("#header_2").height() || 0;
    var footer_height = $("#footer_height").height() || 0;
    var panel_heading=$(".table-title").height() || 0
    //附言
    var psHeight = content - headerHeight_1 -42;
    $(".postscript_box").height(psHeight);
    var ps_defaultH =  $(".postscript_box").height();
    if($(".postscript")[0]){
      $("#scrollable").scroll(function() {
            var a = $("#scrollable").scrollTop()>=110?$("#scrollable").scrollTop() + 6:116;
            $(".postscript_box").css("top",a +'px');
          
            if($("#scrollable").scrollTop()>=110){
                $(".postscript_box").height(ps_defaultH+110);

            }else{
                $(".postscript_box").height($("#scrollable").scrollTop()+ps_defaultH);
            }
      });
    }
    //附言end
    //树
    if($("#treeDemo")[0]){
        $(".tree-right").css("padding-left","215px");
//定义高度
		$("#LeftHeight").height(content-80-headerHeight_1-headerHeight_2);
        var lh = $("#LeftHeight").height();
      $("#scrollable").scroll(function() {
        if($("#scrollable").scrollTop()>=113){
            $("#LeftHeight").addClass("fixedNav");
            $("#LeftHeight").height(lh + 113);
        }else{
            var a = $("#scrollable").scrollTop()>=113?113:$("#scrollable").scrollTop();
            $("#LeftHeight").height(lh + a);
            //$("#LeftHeight").height()
            $("#LeftHeight").removeClass("fixedNav");
        } 
    
      });
    }
    //树end
	});