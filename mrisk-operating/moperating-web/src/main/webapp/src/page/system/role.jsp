<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Title</title>
    <%@include file="/src/page/common/common.jsp" %>
    <%@include file="/src/page/common/TempErrorPop.jsp"%>
    <%@include file="/src/page/common/TempSuccessPop.jsp"%>
    <link rel="stylesheet" href="<c:url value="/src/ztree/css/zTreeStyle/zTreeStyle.css"/>" type="text/css">
    <link rel="stylesheet" type="text/css" href="<c:url value="/src/css/page/page_index.css"/>">
    <script type="text/javascript" src="<c:url value="/src/js/lib/jquery-3.1.0.min.js"/>"></script>
    <script type="text/javascript" src="<c:url value="/src/js/app/util/popUtil.js"/>"></script>
    <script type="text/javascript" src="<c:url value='/src/js/app/common/common.js'/>"></script>
    <script type="text/javascript" src="<c:url value="/src/ztree/js/jquery.ztree.all.min.js"/>"></script>
    <script type="text/javascript">
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
        $(function () {
            <%--loadMenu("${param.roleId}");--%>
            $(".close").on("click", function () {
                $(this).closest(".pop-wrapper").hide();
            });
            var addRole = new Pop({
                'wrapper': $(".roleWrapper"),
                'trigger': $("#add-role"),
                'submitBtn': $(".popSubmitBtn"),
                'submitHandel': function () {
                    saveRole();
                },
                'beforeOpen': function ($wrapper) {
                    $wrapper.find(".pop_title").text("添加角色");
                    $wrapper.find(".roleId").val("");
                    $wrapper.find(".roleName").val("");
                    $wrapper.find(".roleDesc").val("");
                    $("#editType").val(1);
                }
            });
            var addRight = new Pop({
                'wrapper': $(".addRight"),
                'trigger': $(".add"),
                'submitBtn': $(".pop_submit_btn"),
                'submitHandel': function () {
                    saveMenus($(".selected").attr('data-id'));
                },
            });
            var editRole = new Pop({
                'wrapper': $(".roleWrapper"),
                'trigger': $(".editRole"),
                'submitHandel': function () {
                    saveRole();
                },
                'beforeOpen': function ($wrapper,$trigger) {
                    $wrapper.find(".pop_title").text("编辑角色");
                    $wrapper.find(".roleName").val($trigger.closest('.clearfix').attr('roleName'));
                    $wrapper.find(".roleDesc").val($trigger.closest('.clearfix').attr('roleDesc'));
                    $wrapper.find(".roleId").val($trigger.closest('.clearfix').attr('data-id'));
                    $("#editType").val(2);
                }
            });
            var confirm_del_role = new Confirm({
                'wrapper': $(".delRole"),
                'trigger': {'parent': $(".clearfix"), 'selector': '.delete'},
                confirmHandel: function ($theBtn) {
                    delById($theBtn.closest('.clearfix').attr('data-id'), '${basePath}/system/delRoleById.do');
                }
            });
            addRole.init();
            addRight.init();
            editRole.init();
            confirm_del_role.init();
        })
        function  loadMenu(roleId) {
            $.ajax({
                type: "POST",
                url: "<c:url value='/system/findMenuByRole.do'/>",
                dataType:'json',
                data:{
                    "roleId":roleId
                },
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    if ( data.nodes ) {
                        $.fn.zTree.init($("#treeDemo"), setting, data.nodes);
                    }
                }
            })
        }
        function saveMenus(id) {
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
            var formValues = formValue+"&roleId="+id;
            $.ajax({
                type: "POST",
                url: "<c:url value='/system/saveRoleRight.do'/>",
                dataType:'json',
                data: formValues,
                error: function(request) {
                    showTempErrorPop("Connection error!");
                },
                success: function(data) {
                    showTempSuccessPop("保存成功！");
                }
            })
        }
        function showColor(thisObject) {
            $(".clearfix").removeClass("selected");
            $(thisObject).addClass("selected");
        }
        function saveRole() {
            var formId = 'editRoleForm';
            var form_exp = "#"+formId;
            var url = '';
            if($("#editType").val() == 1){
                url = $("#insertUrl").val();
            }else{
                url = $("#updateUrl").val();
            }
            submitAjaxForm(form_exp,url,'reload');
        }
    </script>
</head>
<body>
<div class="content">
    <div class="part left" style="width: 100%">
        <div class="part-title">
            <input type="hidden" id="updateUrl" value="${basePath}/system/updateRole.do">
            <input type="hidden" id="insertUrl" value="${basePath}/system/insertRole.do">
            <input type="hidden" id="editType">
            <p class="part-btn" id="add-role">添加角色</p>
            <h2>角色</h2>
        </div>
        <div class="part-cnt">
            <ul>
                <c:forEach items="${roles}" var="item">
                    <li class='clearfix' onclick="showColor(this)" data-id="${item.id}" roleName="${item.roleName}" roleDesc="${item.roleDesc}">
                        <p class='list-icons'>
                            <i class="iconfont add" title='添加' onclick="loadMenu(${item.id})">&#xe6df;</i>
                            <i class="iconfont editRole" title='编辑'>&#xe6f5;</i>
                            <i class="iconfont delete" title='删除'>&#xe738;</i>
                        </p>
                        <h2>
                            <span>${item.roleName}</span>
                        </h2>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<div class="pop-wrapper roleWrapper" style="display: none">
    <div class="pop-black"></div>
    <div class="pop">
        <div class="pop-title">
            <div class="pop-close">关闭</div>
            <h2 class="pop_title">添加角色</h2>
        </div>
        <form id="editRoleForm">
        <div class="pop-cnt">
            <table cellpadding='0' cellspacing='0' class='table-inside'>
                <tr>
                    <td>角色名</td>
                    <td>
                        <div class="input-text" style='width:300px;'><input type="text" class="input roleName" name="roleName"></div>
                    </td>
                </tr>
                <tr>
                    <td>角色描述</td>
                    <td>
                        <div class="input-text" style='width:300px;'>
                            <input type="text" class="input roleId" style="display: none" name="id">
                            <input type="text" class="input roleDesc" name="roleDesc"></div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                        <div class="pop-btns">
                            <div class="btn-blue left popSubmitBtn" style="margin-right: 20px">保存</div>
                            <div class="btn-white left close" style="margin-left: 20px">返回</div>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
            </form>
    </div>
</div>
<div class="pop-wrapper addRight" style="display: none;">
    <div class="pop-black"></div>
    <div class="pop" style="width: 400px;">
        <div class="pop-title">
            <div class="pop-close">关闭</div>
            <h2 class="pop_title">添加权限</h2>
        </div>
        <div class="pop-cnt" style="padding: 0px 40px">
     <table cellpadding='0' cellspacing='0' class='table-inside'>
         <tr>
             <td>
                 <div class="content_wrap" style="height: 300px;overflow-y: scroll">
                     <div class="zTreeDemoBackground left">
                         <ul id="treeDemo" class="ztree"></ul>
                     </div>
                 </div>
             </td>
         </tr>
         <tr>
             <td>
                 <div class="pop-btns">
                     <div class="btn-blue left pop_submit_btn" style="margin-right: 20px">保存</div>
                     <div class="btn-white left close" style="margin-left: 20px">返回</div>
                 </div>
             </td>
         </tr>
     </table>
            </div>
    </div>
</div>
<div class="pop-wrapper delRole" style='display:none;'>
    <div class="pop-black"></div>
    <div class="pop-tips">
        <h2>您确定要执行删除该角色吗？</h2>
        <div class="pop-btns">
            <a href="#" class="btn-blue left orange confirm_btn">确定</a>
            <a href="#" class="btn-white left close">取消</a>
        </div>
    </div>
</div>

<!--确认弹框 end-->
</body>
</html>
