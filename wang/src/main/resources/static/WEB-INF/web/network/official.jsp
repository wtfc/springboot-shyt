<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
                            <header class="con-header pull-in" id="navigationMenu">
                                    <h1>带宽统计</h1>
                                    <div class="crumbs">
                                        <a href="#">首页</a><i></i>带宽统计
                                    </div>
                            </header>
                            <section class="m-t-md panel personal-con">
                            	<div class="personal-con-left">
                                	<div class="personal-icon">
                                		<b class="rounded">
                                        	<i class="fa fa-official"></i>
                                          	  回访投诉
                                        </b>
                                   	</div>
                                </div>
                            	<div class="personal-con-right">
                                	<div class="m-b-lg personal-img"><img src="../../images/demoimg/official.jpg" width="1920" height="215"  alt=""/></div>
                                    <div class="m-l-lg personal-right-con">
                                    	<p><i class="fa fa-stop fl"></i>回访投诉主要包括在客户关系维护组进行客户回访记录以及投诉信息记录。</p>
                                    	<p><i class="fa fa-stop fl"></i>实现了统计查询、添加、统计。</p>
                                    </div>
                              </div>
                            </section>
                            
                        </section>

<script>
  $(function(){
	ie8InRounded();
	var content_height=$("#content").height();
	$(".personal-con").height(content_height-$("#navigationMenu").height()-75)
   })
</script>