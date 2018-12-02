<%@ page language="java" pageEncoding="UTF-8"%>
<!--查看领导批注 start-->
<div class="modal fade panel" id="worklog-anno" aria-hidden="false">
<input type="hidden" id="annoListJson"  value='${annoListJson}'/>
    <div class="modal-dialog w900">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" onclick="schedule.closeAnno();">×</button>
                <h4 class="modal-title">查看领导批注</h4>
            </div>
            <div class="modal-body">
                <form id="commentForm">
                <ul class="dialog m-t" id="annoComment">
            	</ul>
            	</form>  
            </div>
            <div class="modal-footer form-btn">
                <button class="btn dark" type="button" data-dismiss="modal" onclick="schedule.closeAnno();">关 闭</button>
            </div>
        </div>
    </div>
</div>
<!--查看领导批注 end-->