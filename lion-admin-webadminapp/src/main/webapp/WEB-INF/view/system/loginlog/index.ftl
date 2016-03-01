<#assign base = request.contextPath/>
<!DOCTYPE html>
<html lang="en" class="no-js">
<head>
<title>登陆日志</title>
<link href="${base}/resources/global/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/global/plugins/bootstrap-toastr/toastr.css" rel="stylesheet" type="text/css">
<!--DataTable css Start-->
<link rel="stylesheet" type="text/css" href="${base}/resources/global/plugins/select2/select2.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/global/plugins/datatables/extensions/Scroller/css/dataTables.scroller.min.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/global/plugins/datatables/extensions/ColReorder/css/dataTables.colReorder.min.css"/>
<link rel="stylesheet" type="text/css" href="${base}/resources/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.css"/>
<!--DataTable css End-->
<link href="${base}/resources/global/plugins/bootstrap-select/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/global/css/lion.css" rel="stylesheet" type="text/css" />
<link href="${base}/resources/global/css/combotree/combotree.css" rel="stylesheet" type="text/css" />
<!-- DataTables js Start -->
<script type="text/javascript" src="${base}/resources/global/plugins/select2/select2.min.js"></script>
<script type="text/javascript" src="${base}/resources/global/plugins/datatables/media/js/jquery.dataTables.js"></script>
<script type="text/javascript" src="${base}/resources/global/plugins/datatables/extensions/TableTools/js/dataTables.tableTools.js"></script>
<script type="text/javascript" src="${base}/resources/global/plugins/datatables/extensions/ColReorder/js/dataTables.colReorder.js"></script>
<script type="text/javascript" src="${base}/resources/global/plugins/datatables/extensions/Scroller/js/dataTables.scroller.js"></script>
<script type="text/javascript" src="${base}/resources/global/plugins/datatables/plugins/bootstrap/dataTables.bootstrap.js"></script>
<!-- DataTables js End -->
<script src="${base}/resources/global/plugins/bootbox/bootbox.min.js" type="text/javascript"></script>
<script src="${base}/resources/global/plugins/bootstrap-toastr/toastr.min.js"></script>
<script src="${base}/resources/admin/pages/scripts/ui-toastr.js"></script>
<script src="${base}/resources/global/plugins/jquery-validation/js/jquery.validate.min.js" type="text/javascript"></script>
<!--lion UI JS Start-->
<script src="${base}/resources/global/js/lion.js" type="text/javascript"></script>
<script src="${base}/resources/global/js/combo/combonew.js" type="text/javascript"></script>
<script src="${base}/resources/global/js/form/form.fill.js" type="text/javascript"></script>
<script src="${base}/resources/global/js/local/lion-lang-zh_CN.js" type="text/javascript"></script>
<script src="${base}/resources/global/js/combotree/combotree.js" type="text/javascript"></script>
<script src="${base}/resources/global/js/datagrid/datagrids.js" type="text/javascript"></script>
<!--lang-->
<script src="${base}/resources/admin/scripts/admin-common.js" type="text/javascript"></script>
<!--lion UI JS End-->
<script src="${base}/resources/admin/scripts/system/loginlog.js" type="text/javascript"></script>
</head>
<body class="page-header-fixed page-sidebar-closed-hide-logo page-sidebar-closed-hide-logo">
<!-- BEGIN PAGE CONTENT INNER -->
<div class="portlet light">
	<div class="portlet-body">
		<div class="row">
			<div class="col-md-12 margin-bottom-10">
				<form id="queryform" class="form-horizontal">
					<label class="col-md-1" for="userName" >用户名</label>
					<div class="col-md-2">
						<input class="form-control input-small" type="text" size="30" name="userName" id="userName"  placeholder="请输入用户名称"/>					
					</div>
					<div class="col-md-1">
						<a href="javascript:void(0)" class="btn blue" id="btnQuery"><i class="fa fa-search"></i>
						 <@spring.message "common.query.btn.text"/> 
						</a>
					</div>
					<div class="col-md-1">
						<a href="javascript:void(0)" class="btn blue" id="btnReset"><i class="fa fa-reset"></i>
						 <@spring.message "common.reset.btn.text"/> 
						</a>
					</div>
				</form>
			</div>
		    <div class="col-md-12 col-md-offset-11 margin-bottom-10" id="toolbar">
				<a href="javascript:void(0)" id="btnExport"  class="btn btn-sm green">
					<i class="fa  fa-file-excel-o"></i> <@spring.message "common.toolbar.btn.export.text"/> 
				</a>
			</div>
			
			<div class="col-md-12">
				<table class="lion-datagrids table table-striped table-bordered table-hover" id="sys_login_log_list_tb" data-singleselect="true",   data-loadurl="${base}/system/loginlog/list.json" data-checkbox="true" data-pagesize="10">
					<thead>
						<tr>
							<th data-field='username' data-sortDir="asc"    style="width:100px;">
								用户名
							</th>
							<th data-field="osInfo" style="width:100px;">
							 	操作系统
							</th>
							<th data-field="loginType" style="width:100px;">
								操作类型
							</th>
							<th data-field="loginTime" style="width:100px;" >
								操作时间
							</th>							
						</tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<!-- END PAGE CONTENT INNER -->
</body>
</html>
