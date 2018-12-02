<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
                            <header class="con-header pull-in" id="navigationMenu">
                                    <h1>付款合同</h1>
                                    <div class="crumbs">
                                        <a href="#">首页</a><i></i>付款合同
                                    </div>
                            </header>
                            <section class="m-t-md panel personal-con">
                            	<div class="personal-con-left">
                                	<div class="personal-icon">
                                		<b class="rounded">
                                        	<i class="fa fa-official"></i>
                                           		付款合同
                                        </b>
                                   	</div>
                                </div>
                            	<div class="personal-con-right">
                                	<div class="m-b-lg personal-img"><img src="../../images/demoimg/official.jpg" width="1920" height="215"  alt=""/></div>
                                    <div class="m-l-lg personal-right-con">
                                    	<p><i class="fa fa-stop fl"></i>付款合同主要负责公司付款合同的记录、查询、统计功能。</p>
                                    	<p><i class="fa fa-stop fl"></i>实现了付款合同管理及查询的自动化，利用数据的共享性、数据的灵活性为公司各个部门提供有效的信息。</p>
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