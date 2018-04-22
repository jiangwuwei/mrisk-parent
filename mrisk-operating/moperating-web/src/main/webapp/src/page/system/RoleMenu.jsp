<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<HTML>
<HEAD>
	<TITLE> ZTREE DEMO - checkbox</TITLE>
	<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" href="<c:url value="/src/ztree/css/zTreeStyle/zTreeStyle.css"/>" type="text/css">
	<script type="text/javascript" src="<c:url value="/src/ztree/js/jquery-1.4.4.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/src/ztree/js/jquery.ztree.all.min.js"/>"></script>
	<SCRIPT type="text/javascript">
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		function  loadMenu(roleId) {
			$.ajax({
				type: "POST",
				url: "<c:url value='/system/findMenuByRole.do'/>",
				dataType:'json',
				data:{
					"roleId":roleId
				},
				error: function(request) {
					alert("Connection error!");
				},
				success: function(data) {
					if ( data.nodes ) {
						$.fn.zTree.init($("#treeDemo"), setting, data.nodes);
					}
				}
			})
		}

		$(document).ready(function(){
			loadMenu("${param.roleId}");
		});

		function saveMenus() {
			var formValue="";
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var nodes = zTree.getCheckedNodes();
			for( var i = 0 ; i < nodes.length; i++ ){
				if (i!=nodes.length-1){
					formValue  = formValue + "menuIds="+nodes[i].id+"&";
				}else {
					formValue  = formValue + "menuIds="+nodes[i].id;
				}

			}
			$.ajax({
				type: "POST",
				url: "<c:url value='/system/saveRoleRight.do'/>",
				dataType:'json',
				data: formValue,
				error: function(request) {
					alert("Connection error!");
				},
				success: function(data) {
					alert(success);
				}
			})
			alert(formValue);
		}
	</SCRIPT>
</HEAD>
<BODY>
	<div class="content_wrap">
		<div class="zTreeDemoBackground left">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
	</div>
<input type="submit" name="ddd" value="save" onclick="saveMenus()"/>
</BODY>
</HTML>