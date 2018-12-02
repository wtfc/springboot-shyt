<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

<section class="scrollable padder" id="scrollable">
                            <header class="con-header pull-in" id="navigationMenu">
                                    <h1>基本信息</h1>
                                    <div class="crumbs">
                                        <a href="#">首页</a><i></i>基本信息
                                    </div>
                            </header>
                            <section class="m-t-md panel personal-con">
                            	<div class="personal-con-left">
                                	<div class="personal-icon">
                                		<b class="rounded">
                                        	<i class="fa fa-official"></i>
                                          	  基本信息
                                        </b>
                                   	</div>
                                </div>
                            	<div class="personal-con-right">
                                	<div class="m-b-lg personal-img"><img src="../../images/demoimg/official.jpg" width="1920" height="215"  alt=""/></div>
                                    <div class="m-l-lg personal-right-con">
                                    	<p><i class="fa fa-stop fl"></i>客户信息主要包括客户的基本信息、联系信息、离职/之前人员信息。客户信息为了满足业务的需要可以多维度查询，全局查看。</p>
                                    	<p><i class="fa fa-stop fl"></i>实现了客户信息添加、联系信息添加、更新以及一些离职人员联系信息的查询。</p>
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