<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

 <section class="scrollable padder" id="scrollable">
                            <header class="con-header pull-in" id="header_1">
                                    <h1>文档管理</h1>
                                    <div class="crumbs">
                                        <a href="#">首页</a><i></i>文档管理
                                    </div>
                            </header>
                            <section class="m-t-md panel personal-con">
                            	<div class="personal-con-left">
                                	<div class="personal-icon">
                                		<b class="rounded">
                                        	<i class="fa fa-calculator"></i>
                                            财务管理
                                        </b>
                                   	</div>
                                </div>
                            	<div class="personal-con-right">
                                	<div class="m-b-lg personal-img"><img src="../../images/demoimg/finance.jpg" width="1920" height="215"  alt=""/></div>
                                    <div class="m-l-lg personal-right-con">
                                    	<p><i class="fa fa-stop fl"></i>财务管理</p>
                                    </div>
                              </div>
                            </section>
                  </section>
<script>
  $(function(){
	ie8InRounded();
	var content_height=$("#content").height();
	$(".personal-con").height(content_height-$("#header_1").height()-75)
   })
</script>