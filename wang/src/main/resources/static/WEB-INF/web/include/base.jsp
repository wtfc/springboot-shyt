<!--[if IE 8]>
	<link href="${sysPath}/css/JcGoa_skin.${theme}.css" rel="stylesheet" type="text/css"/>
<![endif]-->
<!--[if IE 9]>
	<link href="${sysPath}/css/JcGoa_v2.${theme}.css" rel="stylesheet" type="text/css"/>
<![endif]-->
<!--[if !IE]><!-->
	<link href="${sysPath}/css/JcGoa_v2.${theme}.css" rel="stylesheet" type="text/css"/>
<!--<![endif]-->
<link href="${sysPath}/js/datepicker/datepicker.css" rel="stylesheet" type="text/css" />
<link href="${sysPath}/css/datatable/dataTables.bootstra.css" rel="stylesheet" type="text/css" />
<c:choose>
	<c:when test="${systemState=='debug'}">
		<script type="text/javascript" src='${sysPath}/js/lib/jquery/jquery-1.10.2.js'></script>
		<script type='text/javascript' src='${sysPath}/js/lib/common/common.all.js'></script>
		<script type='text/javascript' src='${sysPath}/js/lib/common/sessionValidate.js'></script>
		<script type="text/javascript" src='${sysPath}/js/lib/datatable/jquery.dataTables.all.js'></script>
		<script type='text/javascript' src='${sysPath}/js/lib/common/jquery.plugin.js'></script>
		<script type="text/javascript">setRootPath('${sysPath}');</script>
		<script type="text/javascript">setTheme('${theme}');</script>
		<script type="text/javascript" src='${sysPath}/js/lib/jquery-validation/jquery.validate.all.js'></script>
		<script type="text/javascript" src="${sysPath}/js/jQuery_jbox_datepicker/jquery.box_datepicker.js"></script>
		<script type="text/javascript" src="${sysPath}/js/app.v2.js"></script>
	</c:when>
	<c:otherwise>
	</c:otherwise>
</c:choose>  
<!-- Bootstrap -->
<!--[if lt IE 9]>
    <script src="${sysPath}/js/ie/html5_pie678.js"></script>
<![endif]-->