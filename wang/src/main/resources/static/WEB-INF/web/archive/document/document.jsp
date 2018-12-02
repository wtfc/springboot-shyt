<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/web/include/head.jsp"%>

 <section class="scrollable padder" id="scrollable">
                            <header class="con-header pull-in" id="navigationMenu">
                                    <h1>文档管理</h1>
                                    <div class="crumbs">
                                        <a href="#">首页</a><i></i>文档管理
                                    </div>
                            </header>
                            <section class="m-t-md panel personal-con">
                            	<div class="personal-con-left">
                                	<div class="personal-icon">
                                		<b class="rounded">
                                        	<i class="fa fa-document-gl"></i>
                                            文档管理
                                        </b>
                                   	</div>
                                </div>
                            	<div class="personal-con-right">
                                	<div class="m-b-lg personal-img"><img src="../../images/demoimg/document.jpg" width="1920" height="215"  alt=""/></div>
                                    <div class="m-l-lg personal-right-con">
                                    	<p><i class="fa fa-stop fl"></i>文档管理为组织内部的档案资料进行自动或手工归档管理，使文档电子化。实现个人文档、公共文档的平行管控，对于组织内部公共文档的访问权限、个人收藏、文档历史审计等业务需求提供服务。</p>
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